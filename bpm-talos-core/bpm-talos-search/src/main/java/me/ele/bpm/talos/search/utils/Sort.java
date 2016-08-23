package me.ele.bpm.talos.search.utils;

/**
 * Created by yemengying on 16/1/6.
 */
public class Sort {
    private String fieldName = "";
    private SortType type;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public SortType getType() {
        return type;
    }

    public void setType(SortType type) {
        this.type = type;
    }
}
