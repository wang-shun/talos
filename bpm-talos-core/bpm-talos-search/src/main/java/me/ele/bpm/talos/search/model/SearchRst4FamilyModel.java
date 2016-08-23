package me.ele.bpm.talos.search.model;

import me.ele.bpm.elasticsearch.annotation.*;
import me.ele.bpm.elasticsearch.constant.EsSearchType;
import me.ele.bpm.elasticsearch.constant.MatchType;
import me.ele.bpm.elasticsearch.constant.SortType;

/**
 * Created by yemengying on 15/11/27.
 */
@Index("ers_restaurant")
@DocumentType("restaurant")
public class SearchRst4FamilyModel {

    @EsField("id")
    @Bool(type = EsSearchType.TERM)
    @Sort(type = SortType.DESC)
    private Integer id;
    @EsField("name")
    @Bool(type = EsSearchType.MATCH)
    private String name;
    @EsField("city_id")
    @Bool(type = EsSearchType.TERM)
    private Integer cityId;
    @EsField("busy_level")
    @Bool(type = EsSearchType.TERM)
    private Integer busyLevel;
    @EsField("is_valid")
    @Bool(type = EsSearchType.TERM)
    private Integer isValid;
    @EsField("admin_name")
    @Bool(type = EsSearchType.FUZZY)
    private String adminName;
    @EsField("admin_phone")
    @Bool(type = EsSearchType.TERM)
    private String adminPhone;
    @EsField("activities")
    @Bool(type = EsSearchType.TERM)
    private Integer containActivity;
    @EsField("activities")
    @Bool(type = EsSearchType.TERM, value = MatchType.MUST_NOT)
    private Integer notContainActivity;
    @EsField("created_at")
    @Bool(type = EsSearchType.TERM)
    private String createdAt;
    @From
    private Integer offset = 0;
    @Size
    private Integer limit = 10;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getBusyLevel() {
        return busyLevel;
    }

    public void setBusyLevel(Integer busyLevel) {
        this.busyLevel = busyLevel;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public Integer getContainActivity() {
        return containActivity;
    }

    public void setContainActivity(Integer containActivity) {
        this.containActivity = containActivity;
    }

    public Integer getNotContainActivity() {
        return notContainActivity;
    }

    public void setNotContainActivity(Integer notContainActivity) {
        this.notContainActivity = notContainActivity;
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

    @Override
    public String toString() {
        return "SearchRst4FamilyModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cityId=" + cityId +
                ", busyLevel=" + busyLevel +
                ", isValid=" + isValid +
                ", adminName='" + adminName + '\'' +
                ", adminPhone='" + adminPhone + '\'' +
                ", containActivity=" + containActivity +
                ", notContainActivity=" + notContainActivity +
                ", createdAt='" + createdAt + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
