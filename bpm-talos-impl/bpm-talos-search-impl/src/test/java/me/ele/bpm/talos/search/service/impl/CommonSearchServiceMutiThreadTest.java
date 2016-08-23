package me.ele.bpm.talos.search.service.impl;

import me.ele.bpm.talos.index.util.HuskarUtil;
import me.ele.bpm.talos.search.base.TestBase;
import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.config.HuskarHandle;

import org.codehaus.jackson.map.ObjectMapper;
import org.hyperic.sigar.test.TestThreads;
import org.junit.Test;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.*;

/**
 * Created by lupeidong on 16/6/27.
 */
public class CommonSearchServiceMutiThreadTest extends TestBase {

    private static int CASENUM = 2;

    Map<String, Object> map = new HashMap<>();


    int i = 0;

    @Resource
    private CommonSearchService commonSearchService;

    @Test
    public void testSearch() throws Exception {
        //System.out.println(commonSearchService.search("PANDORA_ORDER", map));
        List<Thread> pool = new LinkedList<>();
        Runnable myrun = new Runnable() {
            public void run() {
                int version = -1;
                int testCount = 0;
                try {
                    //int version = -1;
                    //int testCount = 0;
                    do {
                        int versionNow = HuskarUtil.getIDCListVersion();
                        if(version != versionNow) {
                            SearchResult result = commonSearchService.search("PANDORA_ORDER", map);
                            System.out.println(result);
                            testCount++;
                        }
                        version = versionNow;
                    }while(testCount < 2);
                } catch (Exception e) {
                    System.out.println("搜素失败");
                    e.printStackTrace();
                }
            }
        };

        Thread thread1 = new Thread(myrun);
        pool.add(thread1);
        thread1.start();


        Thread thread2 = new Thread(myrun);
        pool.add(thread2);
        thread2.start();

        while(true){
            if(!thread1.isAlive()){
                pool.remove(thread1);
            }
            if(!thread2.isAlive()){
                pool.remove(thread2);
            }
            if(pool.isEmpty()) break;
        }
    }
}

