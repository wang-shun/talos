package me.ele.bpm.talos.index;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import me.ele.bpm.talos.index.base.TestBase;
import me.ele.bpm.talos.index.util.IndexUtil;

import org.junit.Test;

public class IndexUtilTest extends TestBase {
	
	@Resource
	private IndexUtil indexUtil;
	
	private String index = "z_index";
	private String type = "rst_type";
	
	@Test
	public void putMappingTest() throws IOException {
		String file = "mapping_rst";
		indexUtil.putMapping(index, type, file);
	}
	
	@Test
	public void createIndexTest() throws IOException {
		String id = "100";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("is_book", true);
		paramMap.put("order_mode", 7);
		paramMap.put("refund_status", 12);
		indexUtil.createIndex(index, type, id, paramMap);
	}
	
	@Test
	public void deleteTest() {
		String id = "100";
		indexUtil.deleteIndex(index, type, id);
	}
	
	@Test
	public void updateTest() throws IOException {
		String id = "100";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("is_book", true);
		paramMap.put("order_mode", 7);
		paramMap.put("refund_status", 12);
		indexUtil.updateIndex(index, type, id, paramMap);
	}
	
}
