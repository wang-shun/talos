package me.ele.bpm.talos.timer.session;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import me.ele.bpm.talos.common.util.JsonHelper;
import me.ele.bpm.talos.timer.model.DataSourceConf;
import me.ele.bpm.talos.timer.model.DataSourceNode;
import me.ele.contract.exception.ServerException;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * DataSource工厂类
 * @author jianming.zhou
 *
 */
public class DataSourceFactory {
	
	private static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
	
	static {
		try {
			DataSourceConf dataSourceConf = JsonHelper.getMapper().readValue(new File("datasource/database.json"), DataSourceConf.class);
			for (Entry<String, DataSourceNode> entry : dataSourceConf.getDataSourceConf().entrySet()) {
				DataSourceNode dataSourceNode = entry.getValue();
				DataSource dataSource = new DriverManagerDataSource(dataSourceNode.getUrl(), dataSourceNode.getUsername(), dataSourceNode.getPassword());
				dataSourceMap.put(entry.getKey(), dataSource);
			}
		} catch (IOException e) {
			new ServerException("Init DataSource field!");
		}
	}
	
	public static DataSource getDataSource(String databaseName) {
		return dataSourceMap.get(databaseName);
	}

}
