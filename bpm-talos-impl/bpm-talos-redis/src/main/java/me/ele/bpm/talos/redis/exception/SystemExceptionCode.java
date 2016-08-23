package me.ele.bpm.talos.redis.exception;

public enum SystemExceptionCode {
	
	SYSTEM_REDIS_CLIENT_CREATE_FAILED("redis客户端创建失败"),
	SYSTEM_JSON_PARSE_FAILED("json解析失败"),
	SYSTEM_DB_OPER_FAILED("数据库读写失败");
	
	private String dec;

	private SystemExceptionCode(String dec) {
		this.dec = dec;
	}

	public String getDec() {
		return dec;
	}
	public void setDec(String dec) {
		this.dec = dec;
	}

}
