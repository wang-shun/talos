package me.ele.bpm.talos.search.service.impl;

import me.ele.bpm.talos.search.base.TestBase;
import me.ele.bpm.talos.search.model.SearchActivitiesModel;
import me.ele.bpm.talos.search.service.ISearch4BDService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by yemengying on 15/11/4.
 */
public class Search4BDServiceTest extends TestBase{

    @Resource
    private ISearch4BDService search4BDService;

    @Test
    public void test4bd() throws Exception {

        SearchActivitiesModel search = new SearchActivitiesModel();

//        search.setActivityId(Long.valueOf("1084"));
//        search.setBuType(Long.valueOf("2"));
//        search.setActivityType(Arrays.asList(Long.valueOf("7")));
//        search.setRgId(Arrays.asList(11L,11L,111L));
        Long[] ll = {2l};
        search.setBuType(Arrays.asList(2L));
//        search.setEndDateStart("2015-11-09");
//        search.setCampId(Long.valueOf("23"));
//        search.setApplicationId(Long.valueOf("33333"));
//        search.setOffset(1);
//        search.setLimit(2);
//        search.setBeginDateStart(new Date());
//        search.setBeginDateEnd(new Date());
//        search.setEndDateEnd(new Date());
//        search.setEndDateStart("2015-09-08 00:00:00");
        search4BDService.searchRstActivity(search);
    }

}
