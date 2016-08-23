package me.ele.bpm.talos.index.router;

import java.util.Map;

import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.register.AbstractRegister;

import org.codehaus.jackson.JsonNode;

/**
 * Created by jeffor on 16/1/18.
 * {
 *     field
 * }
 * @author jeffor
 * @version 1.0.0
 */
public interface IRouter extends AbstractRegister.IRegistable{

    /**
     * 获取需要设置的参数描述映射表
     * */
    public Map<String, String> getRequiredParams();

    /**
     * 计算路由结果，返回路由目的名称
     * @param doc 文档表单
     * @return 路由目标集合 */
    public String[] destination(Map<String, Object> doc, JsonNode routerParams) throws TalosException;

}
