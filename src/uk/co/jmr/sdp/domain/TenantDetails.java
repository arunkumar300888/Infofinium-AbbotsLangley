package uk.co.jmr.sdp.domain;

import java.util.Date;

public class TenantDetails {
	
	private long id;
	private long tenancyId;
	private String title;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String landlineNumber;
	private String mobileNumber;
	private String emailAddress;
	private String nationalInsuranceNumber;
	private String currentAddress;
	private String passport;
	private String driversLicense;
	private String creditCheck;
	private String referenceCheck;
	private String additionalRemarks;
	
	
	public TenantDetails(long tenancyId, String title, String firstName,
			String lastName, Date dateOfBirth, String landlineNumber,
			String mobileNumber, String emailAddress,
			String nationalInsuranceNumber, String currentAddress,
			String passport, String driversLicense, String creditCheck,
			String referenceCheck, String additionalRemarks) {
		super();
		this.id=-1;
		this.tenancyId = tenancyId;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.landlineNumber = landlineNumber;
		this.mobileNumber = mobileNumber;
		this.emailAddress = emailAddress;
		this.nationalInsuranceNumber = nationalInsuranceNumber;
		this.currentAddress = currentAddress;
		this.passport = passport;
		this.driversLicense = driversLicense;
		this.creditCheck = creditCheck;
		this.referenceCheck = referenceCheck;
		this.additionalRemarks = additionalRemarks;
	}


	public TenantDetails() {
		super();
		this.id = -1;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getTenancyId() {
		return tenancyId;
	}


	public void setTenancyId(long tenancyId) {
		this.tenancyId = tenancyId;
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


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getLandlineNumber() {
		return landlineNumber;
	}


	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public String getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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


	public String getPassport() {
		return passport;
	}


	public void setPassport(String passport) {
		this.passport = passport;
	}


	public String getDriversLicense() {
		return driversLicense;
	}


	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}


	public String getCreditCheck() {
		return creditCheck;
	}


	public void setCreditCheck(String creditCheck) {
		this.creditCheck = creditCheck;
	}


	public String getReferenceCheck() {
		return referenceCheck;
	}


	public void setReferenceCheck(String referenceCheck) {
		this.referenceCheck = referenceCheck;
	}


	public String getAdditionalRemarks() {
		return additionalRemarks;
	}


	public void setAdditionalRemarks(String additionalRemarks) {
		this.additionalRemarks = additionalRemarks;
	}
	
	
	
	

}
