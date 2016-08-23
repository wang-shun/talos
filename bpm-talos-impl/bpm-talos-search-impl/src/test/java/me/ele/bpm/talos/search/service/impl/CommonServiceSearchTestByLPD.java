package me.ele.bpm.talos.search.service.impl;
import me.ele.bpm.talos.index.util.HuskarUtil;
import me.ele.bpm.talos.search.base.TestBase;
import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.config.HuskarHandle;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.*;
/**
 * Created by lupeidong on 16/6/23.
 */
public class CommonServiceSearchTestByLPD extends TestBase {

    @Resource
    private CommonSearchService commonSearchService;
    public void testSearch() throws Exception {
        Map<String, Object> map = new HashMap<>();
//        map.put("priority", Arrays.asList(0));
//        map.put("status", Arrays.asList(1));
//        map.put("group_id", 2);
//        map.put("restauraname", "测试");
//        map.put("from", 0);

//        map.put("id", "100600248726323584");
//        map.put("begin_date", "2016-04-27");
//        map.put("end_date", "2016-04-28");
//        map.put("restaurant_id", Arrays.asList(810018));
//        map.put("limit", 10);
//        map.put("offset", null);

        int version = -1;

        int testCount = 0;
        do {
            int versionNow = HuskarUtil.getIDCListVersion();
            if(version != versionNow){
                SearchResult result = commonSearchService.search("PANDORA_ORDER", map);
                System.out.println(result);
                testCount++;
            }
            version = versionNow;
        }while(testCount < 2);
    }

}
