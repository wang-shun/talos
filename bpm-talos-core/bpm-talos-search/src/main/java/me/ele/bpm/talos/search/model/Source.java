package me.ele.bpm.talos.search.model;

/**
 * Created by yemengying on 15/12/16.
 */
public class Source {

    private String documentId;
    private String jsonString;

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Source(String documentId, String jsonString) {
        this.documentId = documentId;
        this.jsonString = jsonString;
    }

    public Source() {
    }

	@Override
	public String toString() {
		return "Source [documentId=" + documentId + ", jsonString="
				+ jsonString + "]";
	}

}
