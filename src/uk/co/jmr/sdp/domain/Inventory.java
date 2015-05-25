package uk.co.jmr.sdp.domain;

import java.util.Date;

public class Inventory {

	private long id;
	private PropertyDetailsForm propertyDetailsForm;
	private String tenantName;
	private String tenantAddress;
	private String room;
	private String everythingOk;
	private String notes;
	private String inventoryTitle;
	private char status;
	private User createdBy;
	private Date createdOn;
	private Date updatedOn;
	private User updatedBy;
	private String updatedDetails;
	
	public Inventory(){
		super();
		this.id=-1;
	}
	
	public Inventory(PropertyDetailsForm propertyDetailsForm,String inventoryTitle,String tenantName,String tenantAddress,String room,
			String everythingOk,
			String notes,char status,
			User createdBy,Date createdOn,
			Date updatedOn,User updatedBy,String updatedDetails) {
		super();
		this.id=-1;
		this.propertyDetailsForm = propertyDetailsForm;
		this.tenantName = tenantName;
		this.tenantAddress = tenantAddress;
		this.room=room;
		this.inventoryTitle = inventoryTitle;
		this.everythingOk=everythingOk;
		this.notes=notes;
		this.status=status;
		this.createdBy=createdBy;
		this.createdOn=createdOn;
		this.updatedOn=updatedOn;
		this.updatedBy=updatedBy;
		this.updatedDetails=updatedDetails;
	}

	public PropertyDetailsForm getPropertyDetailsForm() {
		return propertyDetailsForm;
	}

	public void setPropertyDetailsForm(PropertyDetailsForm propertyDetailsForm) {
		this.propertyDetailsForm = propertyDetailsForm;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getTenantAddress() {
		return tenantAddress;
	}

	public void setTenantAddress(String tenantAddress) {
		this.tenantAddress = tenantAddress;
	}

	public String getEverythingOk() {
		return everythingOk;
	}

	public void setEverythingOk(String everythingOk) {
		this.everythingOk = everythingOk;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getInventoryTitle() {
		return inventoryTitle;
	}

	public void setInventoryTitle(String inventoryTitle) {
		this.inventoryTitle = inventoryTitle;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDetails() {
		return updatedDetails;
	}

	public void setUpdatedDetails(String updatedDetails) {
		this.updatedDetails = updatedDetails;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	

	
}
