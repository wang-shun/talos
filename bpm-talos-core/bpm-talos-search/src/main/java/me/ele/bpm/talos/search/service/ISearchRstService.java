package me.ele.bpm.talos.search.service;

import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.bpm.talos.search.model.SearchResultNew;
import me.ele.bpm.talos.search.model.SearchRst4FamilyModel;

/**
 * Created by yemengying on 15/11/27.
 */
public interface ISearchRstService {

    public SearchResult searchRst4Family(SearchRst4FamilyModel search);

    public SearchResultNew searchRst4FamilyNew(SearchRst4FamilyModel search);
}
