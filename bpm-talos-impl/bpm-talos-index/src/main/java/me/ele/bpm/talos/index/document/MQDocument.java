package me.ele.bpm.talos.index.document;

public abstract class MQDocument implements Comparable<MQDocument>{
	
	private long version;

	public MQDocument() {
		super();
	}
	public MQDocument(long version) {
		super();
		this.version = version;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
	@Override
	public int compareTo(MQDocument o) {
		if (this.version > o.getVersion()) return 1;
		if (this.version < o.getVersion()) return -1;
		return 0;
	}
	
}
