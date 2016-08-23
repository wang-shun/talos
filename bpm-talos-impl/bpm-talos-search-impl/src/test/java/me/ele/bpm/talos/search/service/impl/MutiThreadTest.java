//package me.ele.bpm.talos.search.service.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import me.ele.bpm.talos.mq.model.MessageBo;
//import me.ele.bpm.talos.mq.service.IMessageProductorService;
//import me.ele.bpm.talos.search.base.TestBase;
//import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
//import net.sourceforge.groboutils.junit.v1.TestRunnable;
//
//import org.junit.Test;
//   
//public class MutiThreadTest extends TestBase{  
//	
//	@Resource
//	private IMessageProductorService messageProductorService; 
//   
//    @Test  
//    public void testThreadJunit() throws Throwable {   
//        //Runner数组，想当于并发多少个。 
//		TestRunnable[] trs = new TestRunnable[10];
//		for (int i = 0; i < 10; i++) {
//			trs[i] = new ThreadA();
//		}
// 
//        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入 
//        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);  
//         
//        // 开发并发执行数组里定义的内容 
//        mttr.runTestRunnables();  
//    }  
//   
//    private class ThreadA extends TestRunnable {  
//        @Override  
//        public void runTest() throws Throwable {  
//            // 测试内容
//            myCommMethod();  
//        }  
//    }  
//   
//    public void myCommMethod() throws Exception {  
//        System.out.println("===" + Thread.currentThread().getId() + "begin to execute myCommMethod"); 
//        List<MessageBo> list = new ArrayList<MessageBo>();
//		for (int i = 1; i <= 10; i++) {
//			Map<String, Object> payloadMap = new HashMap<String, Object>();
//			payloadMap.put("id", i);
//			payloadMap.put("is_book", i);
//			payloadMap.put("order_mode", i);
//			payloadMap.put("refund_status", i);
//			list.add(new MessageBo().setVersion(i).setAction(0)
//					.setBusiness("bd").setDataStruct("restaurant").setPayload(payloadMap));
//			list.add(new MessageBo().setVersion(i).setAction(0)
//					.setBusiness("eos").setDataStruct("order").setPayload(payloadMap));
//			
//		}
//		messageProductorService.batchPushMessage(list);
//        System.out.println("===" + Thread.currentThread().getId() + "end to execute myCommMethod");  
//    }  
//}
