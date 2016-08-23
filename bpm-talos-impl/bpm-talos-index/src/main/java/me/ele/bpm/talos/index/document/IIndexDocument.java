package me.ele.bpm.talos.index.document;

import java.util.Map;

public interface IIndexDocument{
	
	public static int ADD = 0;
	public static int UPDATE = 1;
	public static int DELETE = 2;

	public String getCluster();
	public String getIndex();
	public String getType();
	public String getId();
	public int getAction();
	public void setAction(int action);
	public Object getPayload();
	public Map<String, Object> toDocument();
//	public XContentBuilder toXContentBuilder() throws IllegalAccessException, IOException;
	
}
