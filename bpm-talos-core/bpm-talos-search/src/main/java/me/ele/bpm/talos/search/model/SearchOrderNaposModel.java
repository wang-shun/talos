package me.ele.bpm.talos.search.model;

import me.ele.bpm.elasticsearch.annotation.*;
import me.ele.bpm.elasticsearch.constant.EsSearchType;
import me.ele.bpm.elasticsearch.constant.SortType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yemengying on 15/11/25.
 */
public class SearchOrderNaposModel {

    private List<Integer> restaurantIds;

    private String phone;

    private String beginTime;

    private String endTime;

    private Integer offset = 0;

    private Integer limit = 10;

    public Map<String, Object> toMap(){
        HashMap<String, Object> searchmap= new HashMap<>();
        searchmap.put("restaurant_id", restaurantIds);
        searchmap.put("phone", phone);
        if(beginTime!=null){
            searchmap.put("begin_date", beginTime.trim().replace(" ","T"));
        }
        if(endTime!=null){
            searchmap.put("end_date", endTime.trim().replace(" ", "T"));
        }
        searchmap.put("limit", limit);
        searchmap.put("offset", offset);
        return searchmap;
    }

    public List<Integer> getRestaurantIds() {
        return restaurantIds;
    }

    public void setRestaurantIds(List<Integer> restaurantIds) {
        this.restaurantIds = restaurantIds;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
}
