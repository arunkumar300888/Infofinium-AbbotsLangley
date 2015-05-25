package uk.co.jmr.sdp.domain;

import java.util.Date;

import uk.co.jmr.webforms.db.pojo.UserForms;

public class FormTrail {
	
	private UserForms userForm;
	private User user;
	private long id;
	private String formStatus;
	private Date performedOn;
	private String action;
	private String details;
	private String reason;
	
	public FormTrail(){
		super();
		this.id=-1;
	}
	
	public FormTrail(UserForms userForms,User user,String formStatus,Date performedOn,String action,String details){
		super();
		this.id=-1;
		this.userForm=userForms;
		this.user=user;
		this.formStatus=formStatus;
		this.performedOn=performedOn;
		this.action=action;
		this.details=details;
	}
	
	public FormTrail(UserForms userForms,User user,String formStatus,Date performedOn,String action,String details,String reason){
		super();
		this.id=-1;
		this.userForm=userForms;
		this.user=user;
		this.formStatus=formStatus;
		this.performedOn=performedOn;
		this.action=action;
		this.details=details;
		this.reason=reason;
				
	}
	
	public UserForms getUserForm() {
		return userForm;
	}
	public void setUserForm(UserForms userForm) {
		this.userForm = userForm;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFormStatus() {
		return formStatus;
	}
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	public Date getPerformedOn() {
		return performedOn;
	}
	public void setPerformedOn(Date performedOn) {
		this.performedOn = performedOn;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
