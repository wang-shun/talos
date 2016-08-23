package me.ele.bpm.talos.timer.model;

/**
 * SQL操作运算符
 * @author jianming.zhou
 *
 */
public enum SQLOperator {
	
	EQ("="),IN("IN"),GT(">");
	
	private String operator;

	private SQLOperator(String operator) {
		this.operator = operator;
	}

	public String getOperator() {
		return operator;
	}

}
