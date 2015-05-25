package uk.co.jmr.sdp.domain;

import java.util.Date;
public class PropertyDetailsForm {
	
	

	private long id;
	
	private String client;
	private String firstName;
	private String lastName;
	private String landlineNumber;
	private String mobileNumber;
	private String emailAddress;
	private String address;
//	private String accountDetails;
	private String accountNumber;
	private String accountHoldersName;
	private String sortCode;
	private String vatDetails;
	private String referencesForPayment;
	private String propertyAddressLine1;
	private String propertyAddressLine2;
	private String town;
	private String cityCountry;
	private String propertyPostCode;
	private String propertyType;
	private String propertyDescription;
	private String houseMeasurements;
	private String gasMeterLocation;
	private String electricMeterLocation;
	private String waterMeterLocation;
	private Date  dateOfPerchase;
	private String priceOfPurchase;
	private String estimatedValue;
	private Date asOfDate;
	private String annualRent;
	private String pictures;
	private String localAuthority;
	private String rentalType;
	private String frontDoorKeyCode;
	private String backDoorKeyCode;
	private String porchDoorKeyCode;
	private String flatDoor;
	private String others;
	private long numberOfBedrooms;
	/*private String bedroomDoor;
	private String bedroomWindow;*/
	private String tenancyAgreement;
	private String insuranceCertificate;
	private String floorPlan;
	private String epcCertificate;
	private String gasCertificate;
	private String electricCertificate;
	private String hmoLicence;
	private String contractsAndWarranties;
	private String landRegistry;
	private String currentTenancyForm;
	private String propertyTimeline;
	private String linkToJobs;
	private String lendingInformation;
	
	private String companyName;
	private String managementCompany;
	private String propertyTitle;
	private String status;
	private User createdBy;
	private Date createdOn;
	private Date updatedOn;
	private User updatedBy;
	private String updatedDetails;
	
	
	public PropertyDetailsForm(String client, String firstName,
			String lastName, String landlineNumber, String mobileNumber,
			String emailAddress, String address, 
			String accountNumber, String accountHoldersName, String sortCode,
			String vatDetails, String referencesForPayment,
			String propertyAddressLine1, String propertyAddressLine2,
			String town, String cityCountry, String propertyPostCode,
			String propertyType, String propertyDescription,
			String houseMeasurements, String gasMeterLocation,
			String electricMeterLocation, String waterMeterLocation,
			Date dateOfPerchase, String priceOfPurchase, String estimatedValue,
			Date asOfDate, String annualRent, String pictures,
			String localAuthority, String rentalType, String frontDoorKeyCode,
			String backDoorKeyCode, String porchDoorKeyCode, String flatDoor,
			String others, long numberOfBedrooms,  String tenancyAgreement,
			String insuranceCertificate, String floorPlan,
			String epcCertificate, String gasCertificate,
			String electricCertificate, String hmoLicence,
			String contractsAndWarranties, String landRegistry,
			String currentTenancyForm, String propertyTimeline,
			String linkToJobs, String lendingInformation,String managementCompany,String companyName,String propertyTitle,String status,
			User createdBy,Date createdOn,
			Date updatedOn,User updatedBy,String updatedDetails) {
		super();
		this.id=-1;
		this.propertyTitle=propertyTitle;
		this.client = client;
		this.firstName = firstName;
		this.lastName = lastName;
		this.landlineNumber = landlineNumber;
		this.mobileNumber = mobileNumber;
		this.emailAddress = emailAddress;
		this.address = address;
	//	this.accountDetails = accountDetails;
		this.accountNumber = accountNumber;
		this.accountHoldersName = accountHoldersName;
		this.sortCode = sortCode;
		this.vatDetails = vatDetails;
		this.referencesForPayment = referencesForPayment;
		this.propertyAddressLine1 = propertyAddressLine1;
		this.propertyAddressLine2 = propertyAddressLine2;
		this.town = town;
		this.cityCountry = cityCountry;
		this.propertyPostCode = propertyPostCode;
		this.propertyType = propertyType;
		this.propertyDescription = propertyDescription;
		this.houseMeasurements = houseMeasurements;
		this.gasMeterLocation = gasMeterLocation;
		this.electricMeterLocation = electricMeterLocation;
		this.waterMeterLocation = waterMeterLocation;
		this.dateOfPerchase = dateOfPerchase;
		this.priceOfPurchase = priceOfPurchase;
		this.estimatedValue = estimatedValue;
		this.asOfDate = asOfDate;
		this.annualRent = annualRent;
		this.pictures = pictures;
		this.localAuthority = localAuthority;
		this.rentalType = rentalType;
		this.frontDoorKeyCode = frontDoorKeyCode;
		this.backDoorKeyCode = backDoorKeyCode;
		this.porchDoorKeyCode = porchDoorKeyCode;
		this.flatDoor = flatDoor;
		this.others = others;
		this.numberOfBedrooms = numberOfBedrooms;
		/*this.bedroomDoor = bedroomDoor;
		this.bedroomWindow = bedroomWindow;*/
		this.tenancyAgreement = tenancyAgreement;
		this.insuranceCertificate = insuranceCertificate;
		this.floorPlan = floorPlan;
		this.epcCertificate = epcCertificate;
		this.gasCertificate = gasCertificate;
		this.electricCertificate = electricCertificate;
		this.hmoLicence = hmoLicence;
		this.contractsAndWarranties = contractsAndWarranties;
		this.landRegistry = landRegistry;
		this.currentTenancyForm = currentTenancyForm;
		this.propertyTimeline = propertyTimeline;
		this.linkToJobs = linkToJobs;
		this.lendingInformation = lendingInformation;
		this.managementCompany=managementCompany;
		this.companyName=companyName;
		this.status=status;
		this.createdBy=createdBy;
		this.createdOn=createdOn;
		this.updatedOn=updatedOn;
		this.updatedBy=updatedBy;
		this.updatedDetails=updatedDetails;
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

	public PropertyDetailsForm(){
		super();
		this.id=-1;
	}
	
	
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/*public String getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(String accountDetails) {
		this.accountDetails = accountDetails;
	}*/

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHoldersName() {
		return accountHoldersName;
	}

	public void setAccountHoldersName(String accountHoldersName) {
		this.accountHoldersName = accountHoldersName;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getVatDetails() {
		return vatDetails;
	}

	public void setVatDetails(String vatDetails) {
		this.vatDetails = vatDetails;
	}

	public String getReferencesForPayment() {
		return referencesForPayment;
	}

	public void setReferencesForPayment(String referencesForPayment) {
		this.referencesForPayment = referencesForPayment;
	}

	public String getPropertyAddressLine1() {
		return propertyAddressLine1;
	}

	public void setPropertyAddressLine1(String propertyAddressLine1) {
		this.propertyAddressLine1 = propertyAddressLine1;
	}

	public String getPropertyAddressLine2() {
		return propertyAddressLine2;
	}

	public void setPropertyAddressLine2(String propertyAddressLine2) {
		this.propertyAddressLine2 = propertyAddressLine2;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCityCountry() {
		return cityCountry;
	}

	public void setCityCountry(String cityCountry) {
		this.cityCountry = cityCountry;
	}

	public String getPropertyPostCode() {
		return propertyPostCode;
	}

	public void setPropertyPostCode(String propertyPostCode) {
		this.propertyPostCode = propertyPostCode;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyDescription() {
		return propertyDescription;
	}

	public void setPropertyDescription(String propertyDescription) {
		this.propertyDescription = propertyDescription;
	}

	public String getHouseMeasurements() {
		return houseMeasurements;
	}

	public void setHouseMeasurements(String houseMeasurements) {
		this.houseMeasurements = houseMeasurements;
	}

	public String getGasMeterLocation() {
		return gasMeterLocation;
	}

	public void setGasMeterLocation(String gasMeterLocation) {
		this.gasMeterLocation = gasMeterLocation;
	}

	public String getElectricMeterLocation() {
		return electricMeterLocation;
	}

	public void setElectricMeterLocation(String electricMeterLocation) {
		this.electricMeterLocation = electricMeterLocation;
	}

	public String getWaterMeterLocation() {
		return waterMeterLocation;
	}

	public void setWaterMeterLocation(String waterMeterLocation) {
		this.waterMeterLocation = waterMeterLocation;
	}

	public Date getDateOfPerchase() {
		return dateOfPerchase;
	}

	public void setDateOfPerchase(Date dateOfPerchase) {
		this.dateOfPerchase = dateOfPerchase;
	}

	public String getPriceOfPurchase() {
		return priceOfPurchase;
	}

	public void setPriceOfPurchase(String priceOfPurchase) {
		this.priceOfPurchase = priceOfPurchase;
	}

	public String getEstimatedValue() {
		return estimatedValue;
	}

	public void setEstimatedValue(String estimatedValue) {
		this.estimatedValue = estimatedValue;
	}

	public Date getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(Date asOfDate) {
		this.asOfDate = asOfDate;
	}

	public String getAnnualRent() {
		return annualRent;
	}

	public void setAnnualRent(String annualRent) {
		this.annualRent = annualRent;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getLocalAuthority() {
		return localAuthority;
	}

	public void setLocalAuthority(String localAuthority) {
		this.localAuthority = localAuthority;
	}

	public String getRentalType() {
		return rentalType;
	}

	public void setRentalType(String rentalType) {
		this.rentalType = rentalType;
	}

	public String getFrontDoorKeyCode() {
		return frontDoorKeyCode;
	}

	public void setFrontDoorKeyCode(String frontDoorKeyCode) {
		this.frontDoorKeyCode = frontDoorKeyCode;
	}

	public String getBackDoorKeyCode() {
		return backDoorKeyCode;
	}

	public void setBackDoorKeyCode(String backDoorKeyCode) {
		this.backDoorKeyCode = backDoorKeyCode;
	}

	public String getPorchDoorKeyCode() {
		return porchDoorKeyCode;
	}

	public void setPorchDoorKeyCode(String porchDoorKeyCode) {
		this.porchDoorKeyCode = porchDoorKeyCode;
	}

	public String getFlatDoor() {
		return flatDoor;
	}

	public void setFlatDoor(String flatDoor) {
		this.flatDoor = flatDoor;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public long getNumberOfBedrooms() {
		return numberOfBedrooms;
	}

	public void setNumberOfBedrooms(long numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}

	/*public String getBedroomDoor() {
		return bedroomDoor;
	}

	public void setBedroomDoor(String bedroomDoor) {
		this.bedroomDoor = bedroomDoor;
	}

	public String getBedroomWindow() {
		return bedroomWindow;
	}

	public void setBedroomWindow(String bedroomWindow) {
		this.bedroomWindow = bedroomWindow;
	}*/

	public String getTenancyAgreement() {
		return tenancyAgreement;
	}

	public void setTenancyAgreement(String tenancyAgreement) {
		this.tenancyAgreement = tenancyAgreement;
	}

	public String getInsuranceCertificate() {
		return insuranceCertificate;
	}

	public void setInsuranceCertificate(String insuranceCertificate) {
		this.insuranceCertificate = insuranceCertificate;
	}

	public String getFloorPlan() {
		return floorPlan;
	}

	public void setFloorPlan(String floorPlan) {
		this.floorPlan = floorPlan;
	}

	public String getEpcCertificate() {
		return epcCertificate;
	}

	public void setEpcCertificate(String epcCertificate) {
		this.epcCertificate = epcCertificate;
	}

	public String getGasCertificate() {
		return gasCertificate;
	}

	public void setGasCertificate(String gasCertificate) {
		this.gasCertificate = gasCertificate;
	}

	public String getElectricCertificate() {
		return electricCertificate;
	}

	public void setElectricCertificate(String electricCertificate) {
		this.electricCertificate = electricCertificate;
	}

	public String getHmoLicence() {
		return hmoLicence;
	}

	public void setHmoLicence(String hmoLicence) {
		this.hmoLicence = hmoLicence;
	}

	public String getContractsAndWarranties() {
		return contractsAndWarranties;
	}

	public void setContractsAndWarranties(String contractsAndWarranties) {
		this.contractsAndWarranties = contractsAndWarranties;
	}

	public String getLandRegistry() {
		return landRegistry;
	}

	public void setLandRegistry(String landRegistry) {
		this.landRegistry = landRegistry;
	}

	public String getCurrentTenancyForm() {
		return currentTenancyForm;
	}

	public void setCurrentTenancyForm(String currentTenancyForm) {
		this.currentTenancyForm = currentTenancyForm;
	}

	public String getPropertyTimeline() {
		return propertyTimeline;
	}

	public void setPropertyTimeline(String propertyTimeline) {
		this.propertyTimeline = propertyTimeline;
	}

	public String getLinkToJobs() {
		return linkToJobs;
	}

	public void setLinkToJobs(String linkToJobs) {
		this.linkToJobs = linkToJobs;
	}

	public String getLendingInformation() {
		return lendingInformation;
	}

	public void setLendingInformation(String lendingInformation) {
		this.lendingInformation = lendingInformation;
	}

	
	
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getManagementCompany() {
		return managementCompany;
	}

	public void setManagementCompany(String managementCompany) {
		this.managementCompany = managementCompany;
	}


	public String getPropertyTitle() {
		return propertyTitle;
	}


	public void setPropertyTitle(String propertyTitle) {
		this.propertyTitle = propertyTitle;
	}

	@Override
	public String toString(){
		return propertyTitle;
	}




	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}




	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
