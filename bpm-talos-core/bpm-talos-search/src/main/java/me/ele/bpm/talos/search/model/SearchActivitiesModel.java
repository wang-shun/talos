package me.ele.bpm.talos.search.model;

import java.util.List;

import me.ele.bpm.elasticsearch.annotation.Bool;
import me.ele.bpm.elasticsearch.annotation.DocumentType;
import me.ele.bpm.elasticsearch.annotation.EsField;
import me.ele.bpm.elasticsearch.annotation.From;
import me.ele.bpm.elasticsearch.annotation.Index;
import me.ele.bpm.elasticsearch.annotation.Size;
import me.ele.bpm.elasticsearch.annotation.Sort;
import me.ele.bpm.elasticsearch.constant.EsSearchType;
import me.ele.bpm.elasticsearch.constant.MatchType;
import me.ele.bpm.elasticsearch.constant.SortType;

/**
 * model for bd
 * Created by yemengying on 15/11/3.
 */
@Index("bd")
@DocumentType("search")
public class SearchActivitiesModel {

    @EsField("application_id")
    @Bool(value = MatchType.MUST_NOT)
    private List<Long> applicationId;

    @EsField("activity_id")
    @Bool
    private List<Long> activityId;

    @EsField("review_status")
    @Bool
    @Sort
    private List<Long> reviewStatus;

    @EsField("activity_type")
    @Bool
    private List<Long> activityType;

    @EsField("utp_id")
    @Bool
    private List<Long> utpId;

    @EsField("camp_id")
    @Bool
    private List<Long> campId;

    @EsField("group_id")
    @Bool
    private List<Long> groupId;

    @EsField("zone_id")
    @Bool
    private List<Long> zoneId;

    @EsField("rg_id")
    @Bool
    private List<Long> rgId;

    @EsField("bu_type")
    @Bool
    private List<Long> buType;

    @From
    private Integer offset;
    @Size
    private Integer limit;

    @EsField("begin_date")
    @Bool
    private String beginDate;

    @EsField("end_date")
    @Bool
    private String endDate;

    @EsField("updated_at")
    @Bool
    private String updatedAt;

    @EsField("application_time")
    @Bool
    @Sort(type = SortType.DESC)
    private String applicationTime;

    @EsField("begin_date")
    @Bool(type = EsSearchType.RANGE_GT)
    private String beginDateStart;

    @EsField("begin_date")
    @Bool(type = EsSearchType.RANGE_LTE)
    private String beginDateEnd;

    @EsField("end_date")
    @Bool(type = EsSearchType.RANGE_GTE)
    private String endDateStart;

    @EsField("end_date")
    @Bool(type = EsSearchType.RANGE_LT)
    private String endDateEnd;

    @EsField("application_time")
    @Bool(type = EsSearchType.RANGE_GT)
    private String applicationTimeStart;

    @EsField("application_time")
    @Bool(type = EsSearchType.RANGE_LT)
    private String applicationTimeEnd;

    @EsField("updated_at")
    @Bool(type = EsSearchType.RANGE_GT)
    private String updatedAtStart;

    @EsField("updated_at")
    @Bool(type = EsSearchType.RANGE_LT)
    private String updatedAtEnd;


    public List<Long> getApplicationId() {
        return applicationId;
    }

    public SearchActivitiesModel setApplicationId(List<Long> applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public List<Long> getActivityId() {
        return activityId;
    }

    public SearchActivitiesModel setActivityId(List<Long> activityId) {
        this.activityId = activityId;
        return this;
    }

    public List<Long> getReviewStatus() {
        return reviewStatus;
    }

    public SearchActivitiesModel setReviewStatus(List<Long> reviewStatus) {
        this.reviewStatus = reviewStatus;
        return this;
    }

    public List<Long> getActivityType() {
        return activityType;
    }

    public SearchActivitiesModel setActivityType(List<Long> activityType) {
        this.activityType = activityType;
        return this;
    }

    public List<Long> getUtpId() {
        return utpId;
    }

    public SearchActivitiesModel setUtpId(List<Long> utpId) {
        this.utpId = utpId;
        return this;
    }

    public List<Long> getCampId() {
        return campId;
    }

    public SearchActivitiesModel setCampId(List<Long> campId) {
        this.campId = campId;
        return this;
    }

    public List<Long> getGroupId() {
        return groupId;
    }

    public SearchActivitiesModel setGroupId(List<Long> groupId) {
        this.groupId = groupId;
        return this;
    }

    public List<Long> getZoneId() {
        return zoneId;
    }

    public SearchActivitiesModel setZoneId(List<Long> zoneId) {
        this.zoneId = zoneId;
        return this;
    }

    public List<Long> getRgId() {
        return rgId;
    }

    public SearchActivitiesModel setRgId(List<Long> rgId) {
        this.rgId = rgId;
        return this;
    }

    public List<Long> getBuType() {
        return buType;
    }

    public SearchActivitiesModel setBuType(List<Long> buType) {
        this.buType = buType;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public SearchActivitiesModel setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public SearchActivitiesModel setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public SearchActivitiesModel setBeginDate(String beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public SearchActivitiesModel setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public SearchActivitiesModel setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public SearchActivitiesModel setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
        return this;
    }

    public String getBeginDateStart() {
        return beginDateStart;
    }

    public SearchActivitiesModel setBeginDateStart(String beginDateStart) {
        this.beginDateStart = beginDateStart;
        return this;
    }

    public String getBeginDateEnd() {
        return beginDateEnd;
    }

    public SearchActivitiesModel setBeginDateEnd(String beginDateEnd) {
        this.beginDateEnd = beginDateEnd;
        return this;
    }

    public String getEndDateStart() {
        return endDateStart;
    }

    public SearchActivitiesModel setEndDateStart(String endDateStart) {
        this.endDateStart = endDateStart;
        return this;
    }

    public String getEndDateEnd() {
        return endDateEnd;
    }

    public SearchActivitiesModel setEndDateEnd(String endDateEnd) {
        this.endDateEnd = endDateEnd;
        return this;
    }

    public String getApplicationTimeStart() {
        return applicationTimeStart;
    }

    public SearchActivitiesModel setApplicationTimeStart(String applicationTimeStart) {
        this.applicationTimeStart = applicationTimeStart;
        return this;
    }

    public String getApplicationTimeEnd() {
        return applicationTimeEnd;
    }

    public SearchActivitiesModel setApplicationTimeEnd(String applicationTimeEnd) {
        this.applicationTimeEnd = applicationTimeEnd;
        return this;
    }

    public String getUpdatedAtStart() {
        return updatedAtStart;
    }

    public SearchActivitiesModel setUpdatedAtStart(String updatedAtStart) {
        this.updatedAtStart = updatedAtStart;
        return this;
    }

    public String getUpdatedAtEnd() {
        return updatedAtEnd;
    }

    public SearchActivitiesModel setUpdatedAtEnd(String updatedAtEnd) {
        this.updatedAtEnd = updatedAtEnd;
        return this;
    }

    @Override
    public String toString() {
        return "SearchActivitiesModel{" +
                "applicationId=" + applicationId +
                ", activityId=" + activityId +
                ", reviewStatus=" + reviewStatus +
                ", activityType=" + activityType +
                ", utpId=" + utpId +
                ", campId=" + campId +
                ", groupId=" + groupId +
                ", zoneId=" + zoneId +
                ", rgId=" + rgId +
                ", buType=" + buType +
                ", offset=" + offset +
                ", limit=" + limit +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", applicationTime='" + applicationTime + '\'' +
                ", beginDateStart='" + beginDateStart + '\'' +
                ", beginDateEnd='" + beginDateEnd + '\'' +
                ", endDateStart='" + endDateStart + '\'' +
                ", endDateEnd='" + endDateEnd + '\'' +
                ", applicationTimeStart='" + applicationTimeStart + '\'' +
                ", applicationTimeEnd='" + applicationTimeEnd + '\'' +
                ", updatedAtStart='" + updatedAtStart + '\'' +
                ", updatedAtEnd='" + updatedAtEnd + '\'' +
                '}';
    }
}
