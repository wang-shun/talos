package me.ele.bpm.talos.search.model;

public class SearchKnowledgeModel {
	
    private Integer offset = 0;
    private Integer limit = 10;
	private String keyword;

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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
