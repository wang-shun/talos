package me.ele.bpm.talos.search.model;

import me.ele.bpm.elasticsearch.annotation.*;
import me.ele.bpm.elasticsearch.constant.EsSearchType;
import me.ele.bpm.elasticsearch.constant.SortType;

/**
 * Created by yemengying on 15/11/26.
 */
public class SearchActivities4AadminModel {

    private Integer cityId;

    @EsField("id")
    @Bool(type = EsSearchType.TERM)
    private Integer activityGroupId;

    @EsField("activity_id")
    @Bool(type = EsSearchType.TERM)
    private Integer activityId;

    @EsField("status")
    @Bool(type = EsSearchType.TERM)
    private Integer status;

    @EsField("type")
    @Bool(type = EsSearchType.TERM)
    private Integer type;

    @EsField("count")
    @Bool(type = EsSearchType.RANGE_GT)
    private Integer countBegin;

    @EsField("count")
    @Bool(type = EsSearchType.RANGE_LTE)
    private Integer countEnd;

    @EsField("begin_date")
    @Bool(type = EsSearchType.RANGE_GTE)
    private String beginDateStart;

    @EsField("begin_date")
    @Bool(type = EsSearchType.RANGE_LTE)
    private String beginDateEnd;

    @EsField("end_date")
    @Bool(type = EsSearchType.RANGE_GTE)
    private String endDateStart;

    @EsField("end_date")
    @Bool(type = EsSearchType.RANGE_LTE)
    private String endDateEnd;

    @EsField("updated_at")
    @Bool(type = EsSearchType.RANGE_GTE)
    private String updatedAtBegin;

    @EsField("updated_at")
    @Bool(type = EsSearchType.RANGE_LTE)
    private String updatedAtEnd;

    @EsField("created_at")
    @Bool(type = EsSearchType.RANGE_GTE)
    @Sort(type = SortType.DESC)
    private String createdAtBegin;

    @EsField("created_at")
    @Bool(type = EsSearchType.RANGE_LTE)
    private String createdAtEnd;

    @From
    private Integer offset;

    @Size
    private Integer limit;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getActivityGroupId() {
        return activityGroupId;
    }

    public void setActivityGroupId(Integer activityGroupId) {
        this.activityGroupId = activityGroupId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBeginDateStart() {
        return beginDateStart;
    }

    public void setBeginDateStart(String beginDateStart) {
        this.beginDateStart = beginDateStart;
    }

    public String getBeginDateEnd() {
        return beginDateEnd;
    }

    public void setBeginDateEnd(String beginDateEnd) {
        this.beginDateEnd = beginDateEnd;
    }

    public String getEndDateStart() {
        return endDateStart;
    }

    public void setEndDateStart(String endDateStart) {
        this.endDateStart = endDateStart;
    }

    public String getEndDateEnd() {
        return endDateEnd;
    }

    public void setEndDateEnd(String endDateEnd) {
        this.endDateEnd = endDateEnd;
    }

    public String getUpdatedAtBegin() {
        return updatedAtBegin;
    }

    public void setUpdatedAtBegin(String updatedAtBegin) {
        this.updatedAtBegin = updatedAtBegin;
    }

    public String getUpdatedAtEnd() {
        return updatedAtEnd;
    }

    public void setUpdatedAtEnd(String updatedAtEnd) {
        this.updatedAtEnd = updatedAtEnd;
    }

    public String getCreatedAtBegin() {
        return createdAtBegin;
    }

    public void setCreatedAtBegin(String createdAtBegin) {
        this.createdAtBegin = createdAtBegin;
    }

    public String getCreatedAtEnd() {
        return createdAtEnd;
    }

    public void setCreatedAtEnd(String createdAtEnd) {
        this.createdAtEnd = createdAtEnd;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCountBegin() {
        return countBegin;
    }

    public void setCountBegin(Integer countBegin) {
        this.countBegin = countBegin;
    }

    public Integer getCountEnd() {
        return countEnd;
    }

    public void setCountEnd(Integer countEnd) {
        this.countEnd = countEnd;
    }

    @Override
    public String toString() {
        return "SearchActivities4AadminModel{" +
                "cityId=" + cityId +
                ", activityGroupId=" + activityGroupId +
                ", activityId=" + activityId +
                ", status=" + status +
                ", type=" + type +
                ", countBegin=" + countBegin +
                ", countEnd=" + countEnd +
                ", beginDateStart='" + beginDateStart + '\'' +
                ", beginDateEnd='" + beginDateEnd + '\'' +
                ", endDateStart='" + endDateStart + '\'' +
                ", endDateEnd='" + endDateEnd + '\'' +
                ", updatedAtBegin='" + updatedAtBegin + '\'' +
                ", updatedAtEnd='" + updatedAtEnd + '\'' +
                ", createdAtBegin='" + createdAtBegin + '\'' +
                ", createdAtEnd='" + createdAtEnd + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
