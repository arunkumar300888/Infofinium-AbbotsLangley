package uk.co.jmr.sdp.domain;

import java.util.Date;

public class CheckingOutForm {

	
	private long id;
	private String checkingOutTitle;
	private PropertyDetailsForm propertyDetailsForm;
	private TenancyForm tenancy;
	private String tenantName;
	private String landlordName;
	private String rentedPropertyAddress;
	private Date checkOutAppointmentTime;
	private String nameOfEmployees;
	private String agentCompletingCheckOut;
	private String damage;
	private String notes;
	private String frontDoor;
	private String backDoor;
	//private String roomNumbers;
	private String tenantSignature;
	
	private char status;
	private User createdBy;
	private Date createdOn;
	private Date updatedOn;
	private User updatedBy;
	private String updatedDetails;
	
	
	
	
	public CheckingOutForm()
	{
		super();
		this.id=-1;
	}
	
	public CheckingOutForm(String checkingOutTitle,PropertyDetailsForm propertyDetailsForm,TenancyForm tenancy, String tenantName,
			String landlordName, String rentedPropertyAddress,
			Date checkOutAppointmentTime, String nameOfEmployees,
			String agentCompletingCheckOut, String damage, String notes,
			String frontDoor, String backDoor,
			String tenantSignature, char status,
			User createdBy,Date createdOn,
			Date updatedOn,User updatedBy,String updatedDetails) {
		super();
		this.id=-1;
		this.checkingOutTitle = checkingOutTitle;
		this.propertyDetailsForm=propertyDetailsForm;
		this.tenancy=tenancy;
		this.tenantName = tenantName;
		this.landlordName = landlordName;
		this.rentedPropertyAddress = rentedPropertyAddress;
		this.checkOutAppointmentTime = checkOutAppointmentTime;
		this.nameOfEmployees = nameOfEmployees;
		this.agentCompletingCheckOut = agentCompletingCheckOut;
		this.damage = damage;
		this.notes = notes;
		this.frontDoor = frontDoor;
		this.backDoor = backDoor;
		//this.roomNumbers = roomNumbers;
		this.tenantSignature = tenantSignature;
		this.status=status;
		this.createdBy=createdBy;
		this.createdOn=createdOn;
		this.updatedOn=updatedOn;
		this.updatedBy=updatedBy;
		this.updatedDetails=updatedDetails;
	}
	
	
	public String getCheckingOutTitle() {
		return checkingOutTitle;
	}

	public void setCheckingOutTitle(String checkingOutTitle) {
		this.checkingOutTitle = checkingOutTitle;
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
	
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getLandlordName() {
		return landlordName;
	}
	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}
	public String getRentedPropertyAddress() {
		return rentedPropertyAddress;
	}
	public void setRentedPropertyAddress(String rentedPropertyAddress) {
		this.rentedPropertyAddress = rentedPropertyAddress;
	}
	public Date getCheckOutAppointmentTime() {
		return checkOutAppointmentTime;
	}
	public void setCheckOutAppointmentTime(Date checkOutAppointmentTime) {
		this.checkOutAppointmentTime = checkOutAppointmentTime;
	}
	public String getNameOfEmployees() {
		return nameOfEmployees;
	}
	public void setNameOfEmployees(String nameOfEmployees) {
		this.nameOfEmployees = nameOfEmployees;
	}
	public String getAgentCompletingCheckOut() {
		return agentCompletingCheckOut;
	}
	public void setAgentCompletingCheckOut(String agentCompletingCheckOut) {
		this.agentCompletingCheckOut = agentCompletingCheckOut;
	}
	public String getDamage() {
		return damage;
	}
	public void setDamage(String damage) {
		this.damage = damage;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getFrontDoor() {
		return frontDoor;
	}
	public void setFrontDoor(String frontDoor) {
		this.frontDoor = frontDoor;
	}
	public String getBackDoor() {
		return backDoor;
	}
	public void setBackDoor(String backDoor) {
		this.backDoor = backDoor;
	}
	/*public String getRoomNumbers() {
		return roomNumbers;
	}
	public void setRoomNumbers(String roomNumbers) {
		this.roomNumbers = roomNumbers;
	}*/
	public String getTenantSignature() {
		return tenantSignature;
	}
	public void setTenantSignature(String tenantSignature) {
		this.tenantSignature = tenantSignature;
	}
	

	public PropertyDetailsForm getPropertyDetailsForm() {
		return propertyDetailsForm;
	}

	public void setPropertyDetailsForm(PropertyDetailsForm propertyDetailsForm) {
		this.propertyDetailsForm = propertyDetailsForm;
	}

	public TenancyForm getTenancy() {
		return tenancy;
	}

	public void setTenancy(TenancyForm tenancy) {
		this.tenancy = tenancy;
	}
	
	
}
