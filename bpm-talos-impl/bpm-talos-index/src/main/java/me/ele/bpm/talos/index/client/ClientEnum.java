package me.ele.bpm.talos.index.client;

@Deprecated
public enum ClientEnum {
	
	BD("bd"),PANDORA("pandora");
	
	private String desc;

	private ClientEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
