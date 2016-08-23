package me.ele.bpm.talos.timer.model;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 索引单步骤节点
 * <p>单表处理步骤
 * @author jianming.zhou
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessNode {

	/** 数据库名称 */
	private String database_name;
	/** 数据表名称 */
	private String table_name;
	/** 数据表前置处理条件 */
	private List<List<Object>> table_preconditions;
	/** 文档关联字段 */
	private String doc_according_key;
	/** 数据库关联字段 */
	private String table_according_key;
	/** 数据表中需要拉取的字段 */
//	private List<String> select_columns;
	/** 索引文档字段-数据表字段 映射规则 */
	private Map<String, String> column_map;
	/** 文档中必须包含的字段 */
	private List<String> necessary_columns;
	
	public String getDatabase_name() {
		return database_name;
	}
	public void setDatabase_name(String database_name) {
		this.database_name = database_name;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public List<List<Object>> getTable_preconditions() {
		return table_preconditions;
	}
	public void setTable_preconditions(List<List<Object>> table_preconditions) {
		this.table_preconditions = table_preconditions;
	}
	public String getDoc_according_key() {
		return doc_according_key;
	}
	public void setDoc_according_key(String doc_according_key) {
		this.doc_according_key = doc_according_key;
	}
	public String getTable_according_key() {
		return table_according_key;
	}
	public void setTable_according_key(String table_according_key) {
		this.table_according_key = table_according_key;
	}
	public Map<String, String> getColumn_map() {
		return column_map;
	}
	public void setColumn_map(Map<String, String> column_map) {
		this.column_map = column_map;
	}
	public List<String> getNecessary_columns() {
		return necessary_columns;
	}
	public void setNecessary_columns(List<String> necessary_columns) {
		this.necessary_columns = necessary_columns;
	}
	
	@Override
	public String toString() {
		return "ProcessNode [database_name=" + database_name + ", table_name="
				+ table_name + ", table_preconditions=" + table_preconditions
				+ ", doc_according_key=" + doc_according_key
				+ ", table_according_key=" + table_according_key
				+ ", column_map=" + column_map + ", necessary_columns="
				+ necessary_columns + "]";
	}
	
}
