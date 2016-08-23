package me.ele.bpm.talos.search.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 新的搜索返回结果model 带有id
 * Created by yemengying on 15/12/3.
 */
public class SearchResultNew {

    private long total;
    private List<Data> resultList = new ArrayList<Data>();

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Data> getResultList() {
        return resultList;
    }

    public void setResultList(List<Data> resultList) {
        this.resultList = resultList;
    }

    @Override
    public String toString() {
        return "SearchResultNew{" +
                "total=" + total +
                ", resultList=" + resultList +
                '}';
    }
}
