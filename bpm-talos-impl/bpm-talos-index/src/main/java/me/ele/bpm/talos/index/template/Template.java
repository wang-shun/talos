package me.ele.bpm.talos.index.template;

import java.util.Map;

import me.ele.bpm.talos.common.model.Pair;
import me.ele.bpm.talos.index.exception.ExceptionCode;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.register.RouterRegister;
import me.ele.bpm.talos.index.router.IRouter;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by jeffor on 16/1/20.
 * 模板类
 */
public class Template{

    private int id;

    private String businessName;

    private boolean enable;

    private String clusterName;

    private IRouter indexRouter;

    private JsonNode indexRouterParams;

    private IRouter doctypeRouter;

    private JsonNode doctypeRouterParams;

    private JsonNode appendConfig;

    protected ObjectMapper mapper = new ObjectMapper();

    private Log log = LogFactory.getLog(Template.class);

    public Template(String templateContent) throws TalosException {

        JsonNode bean = null;
        try {
            bean = mapper.readTree(templateContent);
        }catch (Exception e){
            log.error("搜索模板解析错误: \n 模板内容 {} \n 错误描述: {}", templateContent, e.getMessage());
            throw new TalosException(ExceptionCode.TEMPLATE_READ_EXCEPTION);
        }

        RouterRegister register = RouterRegister.getRegister();     // 获得路由器注册器

        businessName = getBussinessName(bean);
        enable = getEnable(bean);
        clusterName = getClusterName(bean);

        Pair<String, JsonNode> indexRouterTemplate = getIndexRouter(bean);
        indexRouter = register.getRouter(indexRouterTemplate.first);
        indexRouterParams = indexRouterTemplate.second;

        Pair<String, JsonNode> doctypeRouterTemplate = getDoctypeRouter(bean);
        doctypeRouter = register.getRouter(doctypeRouterTemplate.first);
        doctypeRouterParams = doctypeRouterTemplate.second;

        appendConfig = getAppendConfig(bean);
    }

    public int getId() {
        return id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getClusterName() {
        return clusterName;
    }

    public IRouter getIndexRouter() {
        return indexRouter;
    }

    public IRouter getDoctypeRouter() {
        return doctypeRouter;
    }

    public JsonNode getAppendConfig() {
        return appendConfig;
    }

    /** 提取索引路由目标的字符串列表 */
    public String[] indexDestination(
            Map<String, Object> doc) throws TalosException {
        return indexRouter.destination(doc, indexRouterParams);
    }

    /** 提取文档路由目标的字符串列表 */
    public String[] doctypeDestination(
            Map<String, Object> doc) throws TalosException {
        return doctypeRouter.destination(doc, doctypeRouterParams);
    }

    private static String ID = "id";

    private static String BUSINESS_NAME= "businessName";

    private static String ENABLE = "enable";

    private static String  CLUSTER_NAME = "clusterName";

    private static String INDEX_ROUTER = "indexRouter";

    private static String ROUTER_NAME = "routerName";

    private static String ROUTER_PARAMS = "routerParams";

    private static String DOCTYPE_ROUTER = "doctypeRouter";

    private static String APPEND_CONFIG = "appendConfig";


    protected JsonNode getJsonNode(
            JsonNode node, String key) throws TalosException {
        JsonNode target = node.get(key);
        if (target == null){
            log.error("搜索模板中无法提取关键元素[{}]", key);
            throw  new TalosException(ExceptionCode.TEMPLATE_READ_EXCEPTION);
        }
        return target;
    }

    public String getBussinessName(
            JsonNode node) throws TalosException {
        return  getJsonNode(node, BUSINESS_NAME).asText();
    }

    public boolean getEnable(JsonNode node)
            throws TalosException {
        return getJsonNode(node, ENABLE).asBoolean();
    }

    public String getClusterName(
            JsonNode node) throws TalosException {
        return  getJsonNode(node, CLUSTER_NAME).asText();
    }

    public Pair<String, JsonNode> getIndexRouter(
            JsonNode node) throws TalosException {
        JsonNode router = getJsonNode(node, INDEX_ROUTER);
        String routerName = getJsonNode(router, ROUTER_NAME).asText();
        JsonNode routerParams = getJsonNode(router, ROUTER_PARAMS);
        return new Pair<>(routerName, routerParams);

    }


    public Pair<String, JsonNode> getDoctypeRouter(
            JsonNode node) throws TalosException {
        JsonNode router = getJsonNode(node, DOCTYPE_ROUTER);
        String routerName = getJsonNode(router, ROUTER_NAME).asText();
        JsonNode routerParams = getJsonNode(router, ROUTER_PARAMS);
        return new Pair<>(routerName, routerParams);

    }


    public JsonNode getAppendConfig(
            JsonNode node) throws TalosException {
        return getJsonNode(node, APPEND_CONFIG);
    }
}
