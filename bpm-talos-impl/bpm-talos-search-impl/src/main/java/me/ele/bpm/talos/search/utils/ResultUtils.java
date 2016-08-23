package me.ele.bpm.talos.search.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ele.bpm.talos.search.model.Data;
import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.bpm.talos.search.model.SearchResultNew;
import me.ele.bpm.talos.search.model.Source;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;


/**
 * deal with data return from es
 * Created by yemengying on 15/12/3.
 */
public class ResultUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static SearchResultNew dealSearchResultNew(SearchResponse searchResponse){

        SearchResultNew result = new SearchResultNew();
        List<Data> resultList = new ArrayList<Data>();

        long total = searchResponse.getHits().getTotalHits();

        result.setTotal(total);

        searchResponse.getHits().getHits();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            //Handle the hit...
            resultList.add(new Data(hit.getId(), hit.sourceAsString()));
        }

        result.setResultList(resultList);
        return result;
    }


    public static String fieldsToMap(Map<String, SearchHitField> hits){
        Map<String, Object> rst = new HashMap<>();
        for(SearchHitField field: hits.values()){
            rst.put(field.getName(), field.getValue());
        }
        try {
            return mapper.writeValueAsString(rst);
        }catch (Exception e){
            return "";
        }
    }


    public static SearchResult dealSearchResult(SearchResponse searchResponse){

        SearchResult result = new SearchResult();
        List<Source> resultList = new ArrayList<Source>();

        long total = searchResponse.getHits().getTotalHits();

        result.setTotal(total);

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            //Handle the hit...
            resultList.add(
                    new Source(hit.getId(), hit.getSource() == null ?
                            fieldsToMap(hit.getFields()) : hit.getSourceAsString()));
        }

        result.setResultList(resultList);
        return result;
    }


}
