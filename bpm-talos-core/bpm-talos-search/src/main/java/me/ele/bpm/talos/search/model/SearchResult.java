package me.ele.bpm.talos.search.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索返回结果model
 * Created by yemengying on 15/12/3.
 */
public class SearchResult {

    private long total;
    private List<Source> resultList = new ArrayList<Source>();

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Source> getResultList() {
        return resultList;
    }

    public void setResultList(List<Source> resultList) {
        this.resultList = resultList;
    }

	@Override
	public String toString() {
		return "SearchResult [total=" + total + ", resultList=" + resultList
				+ "]";
	}

}
