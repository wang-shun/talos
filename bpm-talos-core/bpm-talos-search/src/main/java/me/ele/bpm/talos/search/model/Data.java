package me.ele.bpm.talos.search.model;

/**
 * Created by yemengying on 15/12/28.
 */
public class Data {

    private String id;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Data(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public Data() {
    }

	@Override
	public String toString() {
		return "Data [id=" + id + ", content=" + content + "]";
	}
    
}
