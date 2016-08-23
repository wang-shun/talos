package me.ele.bpm.talos.search.utils;

/**
 * Created by yemengying on 16/1/6.
 */
public enum EsSearchType {
    TERM("TERM"),
    TERMS("TERMS") ,
    RANGE_FROM("RANGE_FROM"),
    RANGE_TO("RANGE_TO"),
    RANGE_GT("RANGE_GT"),
    RANGE_LT("RANGE_LT"),
    RANGE_GTE("RANGE_GTE"),
    RANGE_LTE("RANGE_LTE"),
    FUZZY("FUZZY"),
    QUERY_STRING("QUERY_STRING"),
    MATCH("MATCH"),
    WILDCARD_SEARCH("WILDCARD_SEARCH");

    private String code;
    private EsSearchType(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
