package me.ele.bpm.talos.timer.model;

/**
 * 数据库配置节点
 * <p>单个数据库配置
 * @author jianming.zhou
 *
 */
public class DataSourceNode {
	
	private String url;
	private String username;
	private String password;
	private String driver;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	@Override
	public String toString() {
		return "DataSourceNode [url=" + url + ", username=" + username
				+ ", password=" + password + ", driver=" + driver + "]";
	}
	
}
