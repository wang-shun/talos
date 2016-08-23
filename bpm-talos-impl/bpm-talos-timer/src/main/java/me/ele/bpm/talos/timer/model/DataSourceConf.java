package me.ele.bpm.talos.timer.model;

import java.util.Map;

/**
 * 数据库配置
 * <p>包含所有数据库配置
 * @author jianming.zhou
 *
 */
public class DataSourceConf {

	Map<String, DataSourceNode> dataSourceConf;

	public Map<String, DataSourceNode> getDataSourceConf() {
		return dataSourceConf;
	}
	public void setDataSourceConf(Map<String, DataSourceNode> dataSourceConf) {
		this.dataSourceConf = dataSourceConf;
	}

	@Override
	public String toString() {
		return "DataSourceConf [dataSourceConf=" + dataSourceConf + "]";
	}
	
}
