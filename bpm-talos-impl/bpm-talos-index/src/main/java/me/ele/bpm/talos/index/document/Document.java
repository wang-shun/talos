package me.ele.bpm.talos.index.document;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Document extends MQDocument implements IIndexDocument{

	private String id;
	private int action;
	protected Map<String, Object> payload;
	
	public Document() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public Document(String id, int action, Object payload, long version) {
		super(version);
		this.id = id;
		this.action = action;
		this.payload = (Map<String, Object>) payload;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getAction() {
		return action;
	}
	
	@Override
	public void setAction(int action) {
		this.action = action;
	}
	
	@Override
    public Object getPayload() {
        return payload;
    }
	
	@Override
	public Map<String, Object> toDocument() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != payload) {
			for (Entry<String, Object> entry : payload.entrySet()) {
				if (null != entry.getValue()) {
					map.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return map;
	}

}
