package uk.co.jmr.sdp.domain;

public class DocumentVersion {
	
	private long id;
	private long documentId;
	private String filePath;
	private String documentName;
	
	public DocumentVersion(){
		super();
		this.id=-1;
	}
	
	public DocumentVersion(long documentId, String filePath,String documentName) {

		super();
		this.id = -1;
		this.documentId = documentId;
		this.filePath = filePath;
		this.documentName = documentName;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

}
