package me.ele.bpm.talos.index.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ele.bpm.talos.index.exception.ExceptionCode;
import me.ele.bpm.talos.index.exception.TalosException;

import org.codehaus.jackson.JsonNode;

/**
 * Created by jeffor on 16/2/22.
 */
public class SearchTemplate extends Template {

    private static String TEMPLATE = "template";
    private static String SORT = "sort";
    private static String DEFAULOFFSET = "default_offset";
    private static String DEFAULLIMIT = "default_limit";

    private String template;

    private List<Map<String, String>> sortList;

    private int defaultOffset = 0;

    private int defaultLimit = 10;

    @SuppressWarnings("unchecked")
    public SearchTemplate(String templateContext) throws TalosException {
        super(templateContext);
        JsonNode bean = getAppendConfig(); //提取APPEND_CONFIG后的JsonNode lpd
        template = getJsonNode(bean, TEMPLATE).asText(); //提取 TEMPLATE 后的的JsonNode 转为字符串 lpd
        defaultOffset = getJsonNode(bean, DEFAULOFFSET).asInt(); //提取默认偏移
        defaultLimit = getJsonNode(bean, DEFAULLIMIT).asInt();  //提取默认极限
        try {
            sortList = mapper.readValue(getJsonNode(bean, SORT), //提取排序方式
                    new ArrayList<HashMap<String, String>>().getClass());
            if(sortList == null){
                sortList = new ArrayList<>();
            }
        } catch (Exception e) {
            throw new TalosException(ExceptionCode.TEMPLATE_READ_EXCEPTION);
        }
    }

    public List<Map<String, String>> getSortList() {
        return sortList;
    }

    public void setSortList(List<Map<String, String>> sortList) {
        this.sortList = sortList;
    }

    public int getDefaultOffset() {
        return defaultOffset;
    }

    public void setDefaultOffset(int defaultOffset) {
        this.defaultOffset = defaultOffset;
    }

    public int getDefaultLimit() {
        return defaultLimit;
    }

    public void setDefaultLimit(int defaultLimit) {
        this.defaultLimit = defaultLimit;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
