package me.ele.bpm.talos.index.exception;

/**
 * Created by jeffor on 16/1/18.
 */
public enum ExceptionCode {

    UNKNOW_EXCEPTION("UNKNOW_EXCEPTION", "未知异常"),

    /** 表单参数异常code */
    FORM_VALUE_ASSIGNMENT_EXCEPTION("FORM_VALUE_ASSIGNMENT_EXCEPTION", "表单参数赋值异常"),
    FORM_VALUE_GET_EXCEPTION("FORM_VALUE_GET_EXCEPTION", "表单参数获取"),

    /** 模板解析异常 */
    TEMPLATE_READ_EXCEPTION("TEMPLATE_READ_EXCEPTION", "模板读取失败"),
    TEMPLATE_WRITE_EXCEPTION("TEMPLATE_WRITE_EXCEPTION", "模板写入失败"),

    /** 参数赋值异常 */
    PARAM_ALREADY_EXIST_EXCEPTION("PARAM_ALREADY_EXIST_EXCEPTION", "参数已经存在，不能重复设置"),
    PARAM_READ_EXCEPTION("PARAM_READ_EXCEPTION", "参数读取失败"),
    PARAM_INVALID_EXCEPTION("PARAM_INVALID_EXCEPTION", "参数无效"),

    /** 搜索异常 */
    SEARCH_PARAM_EXCEPTION("SEARCH_PARAM_EXCEPTION", "搜索参数异常"),
    SEARCH_EXCEPTION("SEARCH_EXCEPTION", "搜索异常"),
    SWITCH_OFF_EXCEPTION("SWITCH_OFF_EXCEPTION", "暂时不提供搜索支持"),
    CLUSTER_NOT_EXIST_EXCEPTION("CLUSTER_NOT_EXIST_EXCEPTION", "搜索集群不存在"),

    /** huskar 配置异常*/
    HUSKAR_CONFIG_EXCEPTION("HUSKAR_CONFIG_EXCEPTION", "huskar配置出错"),

    /** json解析异常 */
    HUSKAR_JSON_PARSE_EXCEPTION("HUSKAR_JSON_PARSE_EXCEPTION", "huskar数据解析出现异常"),
    ;

    private String code;
    private String message;

    private ExceptionCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
