package me.ele.bpm.talos.search.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * model for javis
 * Created by yemengying on 15/11/5.
 */
@Index("eos")
@DocumentType("search_order")
public class SearchOrderModel {

    @EsField("created_at")
    @Bool(type = EsSearchType.RANGE_GTE)
    private String createdAtBegin;

    @EsField("created_at")
    @Bool(type = EsSearchType.RANGE_LTE)
    private String createdAtEnd;

    @EsField("created_at")
    @Bool(type = EsSearchType.TERM)
    @Sort(type = SortType.DESC)
    private String createdAt;
    @EsField("restaurant_name")
    @Bool(type = EsSearchType.QUERY_STRING, escape = true)
    private String restaurantName;     
    @EsField("rst_phone")
    @Bool(type = EsSearchType.TERM)
    private String rstPhone;
    @EsField("unique_id")
    @Bool(type = EsSearchType.TERM)
    private String uniqueId;
    @EsField("usr_phone")
    @Bool(value = MatchType.MUST, type = EsSearchType.SHOULD_TERM)
    private String userPhone;
    @EsField
    @Bool(value = MatchType.MUST, type = EsSearchType.SHOULD_TERM)
    private String mobile;
    @EsField("status_code")
    @Bool
    private List<Integer> status;
    @EsField("restaurant_id")
    @Bool
    private List<Integer> restaurantId;
    @EsField("user_name")
    @Bool(type = EsSearchType.QUERY_STRING, escape = true)
    private String userName;
    @From
    private Integer offset;
    @Size
    private Integer limit;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRstPhone() {
		return rstPhone;
	}
	public void setRstPhone(String rstPhone) {
		this.rstPhone = rstPhone;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public List<Integer> getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(List<Integer> restaurantId) {
        this.restaurantId = restaurantId;
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

    public Map<String, Object> toMap(){
        Map<String, Object> searchMap = new HashMap<>();
        if(createdAtBegin != null){
            searchMap.put("begin_date", createdAtBegin.trim().replace(" ", "T"));
        }
        if(createdAtEnd != null){
            searchMap.put("end_date", createdAtEnd.trim().replace(" ", "T"));
        }
        searchMap.put("restaurant_name", restaurantName);
        searchMap.put("rst_phone", rstPhone);
        searchMap.put("uid", uniqueId);
        searchMap.put("usr_phone", userPhone);
        searchMap.put("status_code", status);
        searchMap.put("restaurant_id", restaurantId);
        searchMap.put("user_name", userName);
        searchMap.put("limit", limit);
        searchMap.put("offset", offset);
        return searchMap;
    }

    @Override
    public String toString() {
        return "SearchOrderModel{" +
                "createdAtBegin='" + createdAtBegin + '\'' +
                ", createdAtEnd='" + createdAtEnd + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", rstPhone='" + rstPhone + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                ", restaurantId=" + restaurantId +
                ", userName='" + userName + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
