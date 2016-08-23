package me.ele.bpm.talos.search.utils;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

/**
 * Created by yemengying on 16/1/6.
 */
public class ConfigUtils {

    static final Log log = LogFactory.getLog(ConfigUtils.class);
    private static final int MAX_LIMIT = 200;


    /**
     * 根据配置和参数 生成查询语句
     * @param fieldMap
     * @param map
     * @param boolMap
     * @return
     */
    public static QueryBuilder getQueryBuilder( Map<String, Map<String,Object>> fieldMap, Map<String, Object> map, Map<String, Map<String,String>> boolMap) {
        QueryBuilder qb = null;
        for(String key : map.keySet()){
            //如果没有配置指定字段的查询方式跳过
            if(!fieldMap.containsKey(key)){
                continue;
            }
            //default value
            QueryBuilder nestQb = termQuery(key, map.get(key));
            Object value = map.get(key);

            //如果字段类型为String 并且指明需要转义特殊字符
            if("String".equals((String)fieldMap.get(key).get("type")) && (Integer)fieldMap.get(key).get("is_escaped") == 1){
                value = QueryParser.escape((String) value);
            }

            switch ((String)fieldMap.get(key).get("search_type")){
                case "TERMS":
                    //I don't why it's turn out to an error when pass f.get(obj) to termsQuery() directly
                    //so I cast f.get(obj) to Collection
                    Collection<?> list= (Collection<?>)value;
                    nestQb = termsQuery(key, list);
                    break;
                case "TERM":
                    nestQb = termQuery(key, value);
                    break;
                case "RANGE_GT":
                    nestQb = rangeQuery(key).gt(value);
                    break;
                case "RANGE_GTE":
                    nestQb = rangeQuery(key).gte(value);
                    break;
                case "RANGE_LT":
                    nestQb = rangeQuery(key).lt(value);
                    break;
                case "RANGE_LTE":
                    nestQb = rangeQuery(key).lte(value);
                    break;
                case "RANGE_FROM":
                    nestQb = rangeQuery(key).from(value);
                    break;
                case "RANGE_TO":
                    nestQb = rangeQuery(key).to(value);
                    break;
                case "FUZZY":
                    nestQb =  fuzzyQuery(key, value).fuzziness(Fuzziness.AUTO);
                    break;
                case "SHOULD_TERM":
                    nestQb = boolQuery().should(termQuery(key, value));
                    break;
                case "QUERY_STRING":
                    nestQb = queryStringQuery((String)value).defaultField(key).defaultOperator(QueryStringQueryBuilder.Operator.AND);
                    break;
                case "MATCH":
                    String minimumShouldMatch = (String)fieldMap.get(key).get("minimum_match");
                    nestQb = matchQuery(key, value).minimumShouldMatch(minimumShouldMatch);
                    break;
                case "WILDCARD_SEARCH":
                    nestQb = wildcardQuery(key, "*" + value + "*");
                    break;
                }

            Map<String,String> must = (boolMap.get("must") == null)?new HashMap<String, String>() : boolMap.get("must");
            Map<String,String> must_not = (boolMap.get("must_not") == null) ? new HashMap<String, String>():boolMap.get("must_not");
            Map<String,String> should = (boolMap.get("should") == null)? new HashMap<String, String>() : boolMap.get("should");

            String type = "";
            if(must.containsKey(key)) {
                type = must.get(key);
                nestQb = getNestQueryBuilder(type, nestQb);
                qb = (qb == null ) ? boolQuery().must(nestQb) : ((BoolQueryBuilder) qb).must(nestQb);
            }else if(must_not.containsKey(key)) {
                type = must_not.get(key);
                nestQb = getNestQueryBuilder(type, nestQb);
                qb = (qb == null ) ? boolQuery().mustNot(nestQb) : ((BoolQueryBuilder) qb).mustNot(nestQb);
            }else if(should.containsKey(key)){
                type = should.get(key);
                nestQb = getNestQueryBuilder(type, nestQb);
                qb = (qb == null ) ? boolQuery().should(nestQb) : ((BoolQueryBuilder) qb).should(nestQb);
            }else{
                log.error("查询字段：{}，没有配置具体查询类型", key);
            }


        }
        return qb;
    }


    private static QueryBuilder getNestQueryBuilder(String type, QueryBuilder nestQb){
        if("must".equals(type)){
            return boolQuery().must(nestQb);
        }else if ("should".equals(type)){
            return boolQuery().should(nestQb);
        }else if ("must_not".equals(type)) {
            return boolQuery().mustNot(nestQb);
        }else{
            return nestQb;
        }

    }


    public static SearchRequestBuilder getSearchRequest(Client client, String[] indices, String[] documents) throws IllegalAccessException {

        if(indices == null || documents == null){
            // todo: throw exception
            log.error("索引和文档不能为null");
        }
        SearchRequestBuilder sq = new SearchRequestBuilder(client);

        sq = client.prepareSearch(indices);

        sq = sq.setTypes(documents);

        return sq;
    }









}
