package me.ele.bpm.talos.search.soa;

import me.ele.contract.iface.IInvokeHandler;
import me.ele.contract.iface.IServiceChecker;
import me.ele.contract.iface.IServiceDumper;
import me.ele.contract.iface.IServiceInitializer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SearchServiceInitializer implements IServiceInitializer {
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void init() {
		applicationContext = new ClassPathXmlApplicationContext("classpath:bpm-talos-search.xml");
	}

	@Override
    public Object getImpl(Class<?> iface) {
		return applicationContext.getBean(iface);
    }

    @Override
    public IInvokeHandler getHandler(Class<?> iface) {
    	return null;
    }

    @Override
    public IServiceChecker getChecker() {
        return new SearchServiceChecker();
    }

    @Override
    public IServiceDumper getDumper() {
        return new SearchServiceDumper();
    }
}
