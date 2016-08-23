package me.ele.bpm.talos.search.service.impl;

import me.ele.bpm.elasticsearch.annotation.Bool;
import me.ele.bpm.elasticsearch.annotation.EsField;
import me.ele.bpm.elasticsearch.constant.EsSearchType;
import me.ele.bpm.talos.search.base.TestBase;
import me.ele.bpm.talos.search.model.SearchActivitiesModel;
import me.ele.bpm.talos.search.model.SearchOrderNaposModel;
import me.ele.bpm.talos.search.model.SearchOrderNewModel;
import me.ele.bpm.talos.search.model.SearchOrderPandoraModel;
import me.ele.bpm.talos.search.service.ISearch4BDService;
import me.ele.bpm.talos.search.service.ISearchOrderService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by yemengying on 15/11/4.
 */
public class SearchOrderServiceTest extends TestBase{

    @Resource
    private ISearchOrderService iSearchOrderService;

    @Test
    public void searchOrderForPandora() throws Exception {
        SearchOrderPandoraModel model = new SearchOrderPandoraModel();
//        model.setCreatedAtBegin("2015-02-16T00:00:00");
//        model.setCreatedAtEnd("2016-03-29T00:00:00");
//        model.setId(100025386438228903l);
//        model.setId(1l);
//        model.setCreatedAtBegin("2016-04-12T00:00:00");
//        model.setCreatedAtEnd("2016-04-28T23:59:59");
//        model.setLimit(25);
//        model.setOffset(0);
//        model.setUserId(13524069l);
        model.setId(100600248726323584l);
//        model.setRestaurantId(123l);
//        model.setRestaurantName("123");
//        model.setRestaurantNumber(123l);
//        model.setRestaurantPhone("123");
//        model.setUserId(123l);
//        model.setUserPhone("123");
        String result = iSearchOrderService.searchOrder4Pandora(model);
        return;
    }

    @Test
    public  void  searchOrderNewTest() throws Exception{
        SearchOrderNewModel model = new SearchOrderNewModel();
        model.setCreatedAtBegin("2016-04-20T00:00:00");
        model.setCreatedAtEnd("2016-04-27T00:00:00");
//        model.setComeFrom(1);
//        model.setIsBook(1);
//        model.setIsOnlineBook(1);
//        model.setRestaurantName("rstName");
//        model.setRestaurantId(1);
//        model.setUserPhone("usrPhone");
//        model.setUserName("usrName");
//        model.setStatusCode(1);
//        model.setOrderMode(1);
        model.setOffset(0);
        model.setLimit(20);
        iSearchOrderService.searchOrderNew(model);

    }


    @Test
    public  void  naposOrderSearchTest() throws Exception{
        SearchOrderNaposModel model = new SearchOrderNaposModel();
//        model.setBeginTime("2016-01-26T00:00:00");
//        model.setEndTime("2016-04-28T23:59:59");
//        model.setRestaurantIds(Arrays.asList(1,2,3));
//        model.setOffset(0);
//        model.setLimit(20);

        iSearchOrderService.searchOrder4Napos(model);

    }

}
