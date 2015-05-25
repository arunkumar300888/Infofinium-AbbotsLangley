package uk.co.jmr.sdp.domain;

import java.util.Date;

public class PotentialTenantForm {
	
	private long id;
	private String potentialTenantTitle;
	private String title;
	private String firstName;
	private String lastName;
	private String landLineNumber;
	private String mobileNumber;
	private String emailAddress;
	private String nationalInsuranceNumber;
	private String currentAddress;
	private String type;
	private long numberOfBedRooms;
	private String garden;
	private String offRoadParking;
	private String garage;
	private String other;
	private char status;
	private User createdBy;
	private Date createdOn;
	private Date updatedOn;
	private User updatedBy;
	private String updatedDetails;
	
	public PotentialTenantForm(){
		super();
		this.setId(-1);		
	}
	
	

	public PotentialTenantForm(String potentialTenantTitle, String title, String firstName,
			String lastName, String landLineNumber, String mobileNumber,
			String emailAddress, String nationalInsuranceNumber,
			String currentAddress, String type, long numberOfBedRooms,
			String garden, String offRoadParking, String garage, String other,char status,
			User createdBy,Date createdOn,
			Date updatedOn,User updatedBy,String updatedDetails) {
		super();
		this.id=-1;
		this.potentialTenantTitle = potentialTenantTitle;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.landLineNumber = landLineNumber;
		this.mobileNumber = mobileNumber;
		this.emailAddress = emailAddress;
		this.nationalInsuranceNumber = nationalInsuranceNumber;
		this.currentAddress = currentAddress;
		this.type = type;
		this.numberOfBedRooms = numberOfBedRooms;
		this.garden = garden;
		this.offRoadParking = offRoadParking;
		this.garage = garage;
		this.other = other;
		this.status=status;
		this.createdBy=createdBy;
		this.createdOn=createdOn;
		this.updatedOn=updatedOn;
		this.updatedBy=updatedBy;
		this.updatedDetails=updatedDetails;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLandLineNumber() {
		return landLineNumber;
	}

	public void setLandLineNumber(String landLineNumber) {
		this.landLineNumber = landLineNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getNationalInsuranceNumber() {
		return nationalInsuranceNumber;
	}

	public void setNationalInsuranceNumber(String nationalInsuranceNumber) {
		this.nationalInsuranceNumber = nationalInsuranceNumber;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getNumberOfBedRooms() {
		return numberOfBedRooms;
	}

	public void setNumberOfBedRooms(long numberOfBedRooms) {
		this.numberOfBedRooms = numberOfBedRooms;
	}

	public String getGarden() {
		return garden;
	}

	public void setGarden(String garden) {
		this.garden = garden;
	}

	public String getOffRoadParking() {
		return offRoadParking;
	}

	public void setOffRoadParking(String offRoadParking) {
		this.offRoadParking = offRoadParking;
	}

	public String getGarage() {
		return garage;
	}

	public void setGarage(String garage) {
		this.garage = garage;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}



	public String getPotentialTenantTitle() {
		return potentialTenantTitle;
	}



	public void setPotentialTenantTitle(String potentialTenantTitle) {
		this.potentialTenantTitle = potentialTenantTitle;
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

	
	
	
	

}
