package me.ele.bpm.talos.search.service.impl;

import javax.annotation.Resource;

import me.ele.bpm.talos.search.base.TestBase;
import me.ele.bpm.talos.search.model.SearchKnowledgeModel;
import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.bpm.talos.search.service.ISearchKnowledgeBaseService;
import me.ele.contract.exception.ServiceException;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.junit.Test;

public class SearchKnowledgeBaseServiceTest extends TestBase{
	
	private Log log  = LogFactory.getLog(SearchKnowledgeBaseServiceTest.class);
	
	@Resource
	private ISearchKnowledgeBaseService searchKnowledgeBaseService;
	
	@Test
	public void testSearchKnowledge() throws ServiceException {
		SearchKnowledgeModel searchModel = new SearchKnowledgeModel();
		searchModel.setKeyword("文章");
		SearchResult result = searchKnowledgeBaseService.searchKnowledge(searchModel);
		log.info(result);
	}

}
