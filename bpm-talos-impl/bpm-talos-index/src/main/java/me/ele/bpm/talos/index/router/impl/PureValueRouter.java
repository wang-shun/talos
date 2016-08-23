package me.ele.bpm.talos.index.router.impl;

import java.util.Map;
import java.util.TreeMap;

import me.ele.bpm.talos.index.exception.ExceptionCode;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.router.IRouter;

import org.codehaus.jackson.JsonNode;

/**
 * 纯值路由器
 */
public class PureValueRouter implements IRouter{

    private static final Map<String, String> requiredParams = new TreeMap<>();
    private static final String name = "pureValueRouter";

    /** 初始化路由器参数映射表 */
    static {
        requiredParams.put("name", "名称");
    }


    @Override
    public String getNameForRegist() {
        return name;
    }

    /**
     * params: {"name": "router name"}
     * */
    @Override
    public Map<String, String> getRequiredParams() {
        return requiredParams;
    }


    @Override
    public String[] destination(Map<String, Object> doc, JsonNode routerParams) throws TalosException {
        try {
            String[] value = {routerParams.get("name").asText()};
            return value;
        }catch (Exception e){
            throw new TalosException(ExceptionCode.PARAM_READ_EXCEPTION);
        }
    }
}
