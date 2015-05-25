package uk.co.jmr.sdp.domain;

import java.util.Date;

public class KeepAlive {

	private long id;
	private Date lastPacketReceived;
	private String message;
	
	public KeepAlive(){
		super();
		this.id=-1;
	}
	
	public KeepAlive(Date lastPacketReceived,String message){
		super();
		this.id = -1;
		this.lastPacketReceived=lastPacketReceived;
		this.message=message;			
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getLastPacketReceived() {
		return lastPacketReceived;
	}
	public void setLastPacketReceived(Date lastPacketReceived) {
		this.lastPacketReceived = lastPacketReceived;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
