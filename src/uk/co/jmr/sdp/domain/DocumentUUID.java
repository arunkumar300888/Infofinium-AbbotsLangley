package uk.co.jmr.sdp.domain;

public class DocumentUUID {

	private long id;
	private String uuid;

	public DocumentUUID() {

		super();
		// TODO Auto-generated constructor stub
	}

	public DocumentUUID(long id, String uuid) {

		super();
		this.id = id;
		this.uuid = uuid;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public String getUuid() {

		return uuid;
	}

	public void setUuid(String uuid) {

		this.uuid = uuid;
	}

}
