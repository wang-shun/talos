package me.ele.bpm.talos.timer.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 数据库通用Mapper
 * @author jianming.zhou
 *
 */
public interface CommonMapper {
	
	@Select("SELECT ${columns} FROM ${tableName}")
	public List<Map<String, Object>> selectAll(@Param("tableName") String tableName, @Param("columns") String columns);
	
	@Select("SELECT ${columns} FROM ${tableName} WHERE ${accordingKey} > #{accordingValue} ${precondition}")
	public List<Map<String, Object>> selectGT(@Param("tableName") String tableName, @Param("columns") String columns, 
			@Param("accordingKey") String accordingKey, @Param("accordingValue") Object accordingValue, 
			@Param("precondition") String precondition);
	
	@Select("SELECT ${columns} FROM ${tableName} WHERE ${accordingKey} IN (#{accordingValue}) ${precondition}")
	public List<Map<String, Object>> selectIN(@Param("tableName") String tableName, @Param("columns") String columns, 
			@Param("accordingKey") String accordingKey, @Param("accordingValue") Object accordingValue, 
			@Param("precondition") String precondition);
	
}
