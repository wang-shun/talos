package me.ele.bpm.talos.search.model;

import me.ele.bpm.elasticsearch.annotation.*;
import me.ele.bpm.elasticsearch.constant.EsSearchType;
import me.ele.bpm.elasticsearch.constant.SortType;

import java.util.HashMap;
import java.util.Map;

/**
 * pandora订单查询model
 * Created by yemengying on 15/11/18.
 */
public class SearchOrderPandoraModel {

    private Long id;

    private String createdAtBegin;

    private String createdAtEnd;

    private String restaurantName;

    private String restaurantPhone;

    private String userPhone;

    private Long restaurantId;

    private Long restaurantNumber;

    private Long userId;

    private Integer offset = 0;

    private Integer limit = 15;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getRestaurantNumber() {
        return restaurantNumber;
    }

    public void setRestaurantNumber(Long restaurantNumber) {
        this.restaurantNumber = restaurantNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        return "SearchOrderPandoraModel{" +
                "id=" + id +
                ", createdAtBegin='" + createdAtBegin + '\'' +
                ", createdAtEnd='" + createdAtEnd + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantPhone='" + restaurantPhone + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", restaurantId=" + restaurantId +
                ", restaurantNumber=" + restaurantNumber +
                ", userId=" + userId +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        if(createdAtBegin != null){
            map.put("begin_date", createdAtBegin.trim().replace(" ", "T"));
        }
        if(createdAtEnd != null){
            map.put("end_date", createdAtEnd.trim().replace(" ", "T"));
        }
        map.put("restaurant_id", restaurantId);
        map.put("restaurant_name", restaurantName);
        map.put("restaurant_phone", restaurantPhone);
        map.put("restaurant_number", restaurantNumber);
        map.put("user_id", userId);
        map.put("usr_phone", userPhone);
        map.put("limit", limit);
        map.put("offset", offset);
        return map;
    }
}
