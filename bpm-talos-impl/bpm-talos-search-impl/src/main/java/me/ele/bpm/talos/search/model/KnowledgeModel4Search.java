package me.ele.bpm.talos.search.model;

import me.ele.bpm.elasticsearch.annotation.Bool;
import me.ele.bpm.elasticsearch.annotation.DocumentType;
import me.ele.bpm.elasticsearch.annotation.EsField;
import me.ele.bpm.elasticsearch.annotation.From;
import me.ele.bpm.elasticsearch.annotation.Index;
import me.ele.bpm.elasticsearch.annotation.Size;
import me.ele.bpm.elasticsearch.constant.EsSearchType;
import me.ele.bpm.elasticsearch.constant.MatchType;

@Index("pandora_wiki")
@DocumentType("wiki")
public class KnowledgeModel4Search {
	
    @From
    private Integer offset = 0;
    @Size
    private Integer limit = 10;
	
	@EsField
	@Bool(type = EsSearchType.MATCH, value = MatchType.SHOULD, boost = 10f)
	private String title;
	
    @EsField
    @Bool(type = EsSearchType.MATCH, value = MatchType.SHOULD, boost = 2f)
	private String labels;
	
    @EsField
    @Bool(type = EsSearchType.MATCH, value = MatchType.SHOULD, boost = 3f)
	private String content;
    
    @EsField("category_name")
    @Bool(type = EsSearchType.MATCH, value = MatchType.SHOULD, boost = 2f)
    private String categoryName;
    
    @EsField("creator_name")
    @Bool(type = EsSearchType.MATCH, value = MatchType.SHOULD, boost = 1.0f)
    private String creatorName;
    
    @EsField
    @Bool(type = EsSearchType.TERM)
    private short status = 1;
    
	public KnowledgeModel4Search() {
		
	}
	public KnowledgeModel4Search(SearchKnowledgeModel search) {
		if (null != search.getOffset()) {
			this.offset = search.getOffset();
		}
		if (null != search.getLimit()) {
			this.limit = search.getLimit();
		}
		this.title = search.getKeyword();
		this.labels = search.getKeyword();
		this.content = search.getKeyword();
		this.categoryName = search.getKeyword();
		this.creatorName = search.getKeyword();
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	
}
