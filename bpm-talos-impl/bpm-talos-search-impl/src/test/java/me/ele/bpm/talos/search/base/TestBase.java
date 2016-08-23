package me.ele.bpm.talos.search.base;

import me.ele.bpm.talos.common.soa.ClientUtils;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:bpm-talos-search.xml"
	})
public class TestBase {
	
	public TestBase() {
		ClientUtils.initClients();
	}
}
