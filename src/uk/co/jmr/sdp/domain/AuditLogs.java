package uk.co.jmr.sdp.domain;

import java.util.Date;

public class AuditLogs 
{
	private long id;
	private String formname;
	private String action;
	private Date time;
	private User userId;
	
	public AuditLogs(){
		super();
		this.id=-1;
	}
	
	public AuditLogs( String formname,
			String action,
			Date time, 
			User userId) {
		super();
		this.id=-1;
		this.formname = formname;
		this.action = action;
		this.time = time;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}
	
	
	
}
