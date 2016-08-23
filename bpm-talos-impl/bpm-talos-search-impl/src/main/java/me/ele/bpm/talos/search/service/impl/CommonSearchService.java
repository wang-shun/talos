package me.ele.bpm.talos.search.service.impl;

import java.util.*;

import javax.annotation.PostConstruct;

import me.ele.bpm.talos.common.soa.MetricUtils;
import me.ele.bpm.talos.index.exception.ExceptionCode;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.loader.ClusterCenter;
import me.ele.bpm.talos.index.loader.SearchTemplateCenter;
import me.ele.bpm.talos.index.template.SearchTemplate;
import me.ele.bpm.talos.index.util.HuskarUtil;
import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.bpm.talos.search.service.ICommonSearchService;
import me.ele.bpm.talos.search.utils.ResultUtils;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.http.HttpException;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TemplateQueryBuilder;
import org.elasticsearch.script.ScriptService;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

/**
 * 通用搜索模块（模板搜索）
 * Created by yemengying on 16/1/6.
 */
@Service
public class CommonSearchService implements ICommonSearchService{

    private static final String serviceFlag = "bpm.talos.search.";          // 搜索接口标识名称名前置

    private static final String OFFSET = "offset";                          // 搜索参数解析关键字（搜索起始偏移量）
    private static final String LIMIT = "limit";                            // 搜索参数解析关键字（单次搜索返回量）
    private static final int MAX_LIMIT = 1000;                                // 最大单次搜索返回量限定

    private ClusterCenter cluster = new ClusterCenter();                    // 集群控制中心
    private SearchTemplateCenter templates = new SearchTemplateCenter();    // 模板控制中心


    private static Map<String, SortOrder> orderMap = null;                  // 搜索排序解析映射表
    static {
        orderMap = new HashMap<>();
        orderMap.put("desc", SortOrder.DESC);
        orderMap.put("asc", SortOrder.ASC);
    }

    Log log = LogFactory.getLog(CommonSearchService.class);

    /**
     * 加载 搜索引擎 客户端。
     * 当 客户端配置版本 IDCVERSION 不一致时调用。
     * 因此当动态更新客户端配置将会导致多线程同时调用问题
     * */
    @PostConstruct
    private void initClient()
            throws TalosException {
        cluster.loadClients();          // 初始化加载集群客户端
    }

    /**
     * 模板搜索实现
     * @param template 模板
     * @param params 模板参数
     * @return SearchResponse 搜索结果
     * @throws TalosException
     * */
    @SuppressWarnings("unchecked")
	private SearchResponse TemplateSearch(
            SearchTemplate template,
            Map<String, Object> params)
            throws TalosException {

        log.warn("businessName = {}, params={}", template.getBusinessName(), params);
        /** 获得搜索引擎查询句柄 */
        TransportClient client = cluster.getStableClient(template.getClusterName());

        /** 获得待搜索的索引名称列表 和 文档类型列表 */
        String[] indices = template.indexDestination(params);
        String[] docTypes = template.doctypeDestination(params);

        /** 计算查询偏移量和结果返回量 */
        int offset = params.get(OFFSET) == null?
                template.getDefaultOffset(): (Integer)params.get(OFFSET);

        if (offset < 0){
            log.warn("无效搜索参数: offset[{}]小于零", offset);
            throw new TalosException(ExceptionCode.PARAM_INVALID_EXCEPTION);
        }

        int limit = params.get(LIMIT) == null?
                template.getDefaultLimit():(Integer)params.get(LIMIT);

        if (limit < 0 || params.get("fields") == null? limit > MAX_LIMIT/10: limit > MAX_LIMIT){
            log.warn("无效搜索参数: limit[{}] 大于上限[{}] 或者小于[0]", limit, MAX_LIMIT);
            throw new TalosException(ExceptionCode.PARAM_INVALID_EXCEPTION);
        }

        /** 创建搜索语句，获得查询结果 */
        QueryBuilder builder = new TemplateQueryBuilder(
                template.getTemplate(),
                ScriptService.ScriptType.INLINE,
                params);

        /** 创建查询语句 */
        SearchRequestBuilder query = client.prepareSearch(indices).
                setQuery(builder).setFrom(offset).
                setSize(limit).setTimeout("3s");

        /** 添加排序规则 */
        List<Map<String, String>> sortList = template.getSortList();
        for (Map<String, String> sort : sortList){
            for(Map.Entry<String, String> entry: sort.entrySet()){
                query.addSort(entry.getKey(), orderMap.get(entry.getValue()));
            }
        }
        if(params.get("fields") != null){
        	query.addFields((String[]) ((List<String>)params.get("fields")).toArray(new String[]{}));
        }
        /** 添加文档类型限定 */
        if(docTypes.length > 0){
            query.setTypes(docTypes);
        }
        log.debug("查询语句：{}", query.toString());

        SearchResponse response;
        try {
            response = query.execute().actionGet();
            cluster.success();
        }catch (Exception e){
            cluster.fail();
            log.error("查询失败，机房名称 [{}], 模板名称 [{}], 查询语句 [{}], 报错信息 [{}]",
                    cluster.getCurrentIDCName(), template.getBusinessName(),
                    query.toString(), e.getMessage());
            throw new TalosException(ExceptionCode.SEARCH_EXCEPTION);
        }

        /** 慢查询日志记录 */
        if(HuskarUtil.isSlowQuery(response.getTookInMillis())){
            log.warn("slow search query: business_name [{}], indexes [{}], doctypes [{}], query [{}]",
                    template.getBusinessName(), indices, docTypes, template.getAppendConfig(), query.toString());
        }
        return response;
    }

    /** 搜索接口
     * @param templateCode 模板名称
     * @param params 参数映射表
     * @return SearchResult
     * @throws TalosException
     * */
    @Override
    public SearchResult search(String  templateCode, Map<String, Object> params) throws TalosException {

        SearchTemplate template = templates.get(templateCode);        // 根据templateId取Template

        if (template == null){
            log.error("search template file [{}] not exist", templateCode);
            throw new TalosException(ExceptionCode.TEMPLATE_READ_EXCEPTION);
        }

        /** 业务监控 */
        String business = serviceFlag + template.getBusinessName();                 // 计算可监控业务名（打点之用）
        MetricUtils.record(business);                                               // 调用次数打点

        /** 熔断开关*/
        if (!template.isEnable()){
            log.warn("business search [{}] is turn off", template.getBusinessName());
            throw new TalosException(ExceptionCode.SWITCH_OFF_EXCEPTION);
        }

        /** 获得搜索结果 */
        SearchResponse response = TemplateSearch(template, params);

        MetricUtils.recordTimes(business, response.getTookInMillis());              // 搜索耗时打点
        return ResultUtils.dealSearchResult(response);
    }

    @Override
    public String searchString(String templateCode, Map<String, Object> params) throws TalosException {
        SearchTemplate template = templates.get(templateCode);        // 根据templateId取Template

        if (template == null){
            log.error("search template [{}] not exist", templateCode);
            throw new TalosException(ExceptionCode.TEMPLATE_READ_EXCEPTION);
        }

        /** 业务监控 */
        String business = serviceFlag + template.getBusinessName();                 // 计算可监控业务名（打点之用）
        MetricUtils.record(business);                                               // 调用次数打点

        /** 熔断开关*/
        if (!template.isEnable()){
            log.warn("business search [{}] is turn off", template.getBusinessName());
            throw new TalosException(ExceptionCode.SWITCH_OFF_EXCEPTION);
        }

        /** 获得搜索结果 */
        SearchResponse response = TemplateSearch(template, params);

        MetricUtils.recordTimes(business, response.getTookInMillis());              // 搜索耗时打点
        return response.toString();
    }

    @Override
    public boolean ping() {
        return true;
    }

}
