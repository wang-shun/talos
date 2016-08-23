package me.ele.bpm.talos.search.service;

import me.ele.bpm.talos.search.model.SearchKnowledgeModel;
import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.contract.exception.ServiceException;

/**
 * 知识库接口
 * @author jianming.zhou
 *
 */
public interface ISearchKnowledgeBaseService {
	
	public SearchResult searchKnowledge(SearchKnowledgeModel searchModel) throws ServiceException;

}
