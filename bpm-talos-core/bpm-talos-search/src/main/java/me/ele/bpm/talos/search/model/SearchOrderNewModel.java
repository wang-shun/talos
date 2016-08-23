package me.ele.bpm.talos.search.model;

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

import java.util.HashMap;
import java.util.Map;

/**
 * 为订单运营后台 提供的查询model
 * @author yemengying
 *
 */
public class SearchOrderNewModel {
	
	private String userName;
	
	private Integer statusCode;

	private Integer restaurantId;
	
	private Integer orderMode;
	
	private String userPhone;
	
	private Integer comeFrom;
	
	private String restaurantName;
	
	private Integer isOnlineBook;
	
	private Integer isBook;
	
    private String createdAtBegin;
    
    private String createdAtEnd;

    private Integer offset = 0;

    private Integer limit = 10;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Integer getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(Integer orderMode) {
		this.orderMode = orderMode;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Integer getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(Integer comeFrom) {
		this.comeFrom = comeFrom;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public Integer getIsOnlineBook() {
		return isOnlineBook;
	}

	public void setIsOnlineBook(Integer isOnlineBook) {
		this.isOnlineBook = isOnlineBook;
	}

	public Integer getIsBook() {
		return isBook;
	}

	public void setIsBook(Integer isBook) {
		this.isBook = isBook;
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

	public SearchOrderNewModel() {
		super();
	}

	public Map<String, Object> toMap(){
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("user_name", userName);
		searchMap.put("status_code", statusCode);
		searchMap.put("restaurant_id", restaurantId);
		searchMap.put("order_mode", orderMode);
		searchMap.put("usr_phone", userPhone);
		searchMap.put("come_from", comeFrom);
		searchMap.put("restaurant_name", restaurantName);
		searchMap.put("is_online_paid", isOnlineBook);
		searchMap.put("is_book", isBook);
		if(createdAtBegin != null){
			searchMap.put("begin_date", createdAtBegin.trim().replace(" ","T"));
		}
		if(createdAtEnd != null){
			searchMap.put("end_date", createdAtEnd.trim().replace(" ", "T"));
		}
		searchMap.put("limit", limit);
		searchMap.put("offset", offset);
		return searchMap;
	}

	@Override
	public String toString() {
		return "SearchOrderNewModel [userName=" + userName + ", statusCode="
				+ statusCode + ", restaurantId=" + restaurantId
				+ ", orderMode=" + orderMode + ", userPhone=" + userPhone
				+ ", comeFrom=" + comeFrom + ", restaurantName="
				+ restaurantName + ", isOnlineBook=" + isOnlineBook
				+ ", isBook=" + isBook + ", createdAtBegin=" + createdAtBegin
				+ ", createdAtEnd=" + createdAtEnd + ", offset=" + offset
				+ ", limit=" + limit + "]";
	}

}
