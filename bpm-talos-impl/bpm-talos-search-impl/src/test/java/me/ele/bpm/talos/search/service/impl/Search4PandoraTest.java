package me.ele.bpm.talos.search.service.impl;

import me.ele.bpm.elasticsearch.annotation.*;
import me.ele.bpm.elasticsearch.constant.EsSearchType;
import me.ele.bpm.elasticsearch.constant.MatchType;
import me.ele.bpm.elasticsearch.constant.SortType;
import me.ele.bpm.talos.search.base.TestBase;
import me.ele.bpm.talos.search.model.SearchActivitiesModel;
import me.ele.bpm.talos.search.model.SearchOrderModel;
import me.ele.bpm.talos.search.service.ISearch4BDService;
import me.ele.bpm.talos.search.service.ISearchOrder4Pandora;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yemengying on 15/11/5.
 */
public class Search4PandoraTest extends TestBase{

    @Resource
    private ISearchOrder4Pandora searchOrder4PandoraService;

    @Test
    public void test4bd() throws Exception {

        SearchOrderModel search = new SearchOrderModel();

        search.setOffset(null);
//        search.setLimit(1);
//        search.setCreatedAtBegin("2016-04-01 00:00:00");
        search.setCreatedAtEnd("2016-04-29");
//        search.setCreatedAt("2016-04-25 00:00:00");
//        search.setRestaurantId(Arrays.asList(242079));
        search.setLimit(10);
//        search.setRestaurantName("rstname");
//        search.setRstPhone("rst_phone");
//        search.setUniqueId("100494443702031559");
//        search.setUserPhone("18217672536");
//        search.setMobile("mobile");
//        search.setStatus(Arrays.asList(1));
//        search.setUserName("usr_name");
        searchOrder4PandoraService.searchOrder(search);
    }
}
