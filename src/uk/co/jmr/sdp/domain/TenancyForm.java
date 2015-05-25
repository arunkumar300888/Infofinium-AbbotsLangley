package uk.co.jmr.sdp.domain;

import java.util.Date;

public class TenancyForm {
	
	private long id;
	private PropertyDetailsForm propertyDetailsForm;
	private String tenancyTitle;
	
	private String propertyAddress;
	private String typeOfRental;
	private String roomNumber;
	private String landLordFirstName;
	private String landLordLastName;
	private String landLordAddress;
	private String landLordEmailAddress;
	private String landLordContactNumber;
	private String employerFirstName;
	private String employerLastName;
	private String employerAddress;
	private String employerEmailAddress;
	private String employerContactNumber;
	private String guarantorFirstName;
	private String guarantorLastName;
	private String guarantorAddress;
	private String guarantorEmailAddress;
	private String guarantorContactNumber;
	private String kinFirstName;
	private String kinLastName;
	private String kinAddress;
	private String kinEmailAddress;
	private String kinContactNumber;
	private String tenancyOpenedBy;
	private Date tenancyStartedDate;
	private String tenancyClosedBy;
	private Date tenancyClosedDate;
	private String checkingOutForm;
	private String checkingInForm;
	private String linkToProperty;
	private String linkToTenancyAgreement;
	private String linkToRentAccounts;
	private String legalNotifications;
	private String tenancyDepositCertificate;
	private String councilTaxRegistration;
	private String additionalLinks;
	private String correspondenceWithTenants;
	private String correspondenceLink;
	private String employerCompany;
	private String guarantorCompany;
	private String tenantTitle;
	private String tenantFirstName;
	private String tenantLastName;
	private Date tenantDateOfBirth;
	private String tenantLandlineNumber;
	private String tenantMobileNumber;
	private String tenantEmailAddress;
	private String tenantNationalInsuranceNumber;
	private String tenantCurrentAddress;
	private String tenantPassport;
	private String tenantDriversLicense;
	private String tenantCreditCheck;
	private String tenantReferenceCheck;
	private String tenantAdditionalRemarks;
	private Date tenancyFinishDate; 
	private String status;
	private User createdBy;
	private Date createdOn;
	private Date updatedOn;
	private User updatedBy;
	private String updatedDetails;
	private int rentDueDate;
	
	private String isOccupied;
	
	
	public TenancyForm(PropertyDetailsForm propertyDetailsForm,
			String tenancyTitle,  String propertyAddress,
			String typeOfRental, String roomNumber, String landLordFirstName,
			String landLordLastName, String landLordAddress,
			String landLordEmailAddress, String landLordContactNumber,
			String employerFirstName, String employerLastName,
			String employerAddress, String employerEmailAddress,
			String employerContactNumber, String guarantorFirstName,
			String guarantorLastName, String guarantorAddress,
			String guarantorEmailAddress, String guarantorContactNumber,
			String kinFirstName, String kinLastName, String kinAddress,
			String kinEmailAddress, String kinContactNumber,
			String tenancyOpenedBy, Date tenancyStartedDate,
			String tenancyClosedBy, Date tenancyClosedDate,
			String checkingOutForm, String checkingInForm,
			String linkToProperty, String linkToTenancyAgreement,
			String linkToRentAccounts, String legalNotifications,
			String tenancyDepositCertificate, String councilTaxRegistration,
			String additionalLinks, String correspondenceWithTenants,
			String correspondenceLink, String employerCompany,
			String guarantorCompany, String tenantTitle,
			String tenantFirstName, String tenantLastName,
			Date tenantDateOfBirth, String tenantLandlineNumber,
			String tenantMobileNumber, String tenantEmailAddress,
			String tenantNationalInsuranceNumber, String tenantCurrentAddress,
			String tenantPassport, String tenantDriversLicense,
			String tenantCreditCheck, String tenantReferenceCheck,
			String tenantAdditionalRemarks, Date tenancyFinishDate,
			String status, User createdBy, Date createdOn, Date updatedOn,
			User updatedBy, String updatedDetails, int rentDueDate,
			 String isOccupied) {
		super();
		this.id=-1;
		this.propertyDetailsForm = propertyDetailsForm;
		this.tenancyTitle = tenancyTitle;
		
		this.propertyAddress = propertyAddress;
		this.typeOfRental = typeOfRental;
		this.roomNumber = roomNumber;
		this.landLordFirstName = landLordFirstName;
		this.landLordLastName = landLordLastName;
		this.landLordAddress = landLordAddress;
		this.landLordEmailAddress = landLordEmailAddress;
		this.landLordContactNumber = landLordContactNumber;
		this.employerFirstName = employerFirstName;
		this.employerLastName = employerLastName;
		this.employerAddress = employerAddress;
		this.employerEmailAddress = employerEmailAddress;
		this.employerContactNumber = employerContactNumber;
		this.guarantorFirstName = guarantorFirstName;
		this.guarantorLastName = guarantorLastName;
		this.guarantorAddress = guarantorAddress;
		this.guarantorEmailAddress = guarantorEmailAddress;
		this.guarantorContactNumber = guarantorContactNumber;
		this.kinFirstName = kinFirstName;
		this.kinLastName = kinLastName;
		this.kinAddress = kinAddress;
		this.kinEmailAddress = kinEmailAddress;
		this.kinContactNumber = kinContactNumber;
		this.tenancyOpenedBy = tenancyOpenedBy;
		this.tenancyStartedDate = tenancyStartedDate;
		this.tenancyClosedBy = tenancyClosedBy;
		this.tenancyClosedDate = tenancyClosedDate;
		this.checkingOutForm = checkingOutForm;
		this.checkingInForm = checkingInForm;
		this.linkToProperty = linkToProperty;
		this.linkToTenancyAgreement = linkToTenancyAgreement;
		this.linkToRentAccounts = linkToRentAccounts;
		this.legalNotifications = legalNotifications;
		this.tenancyDepositCertificate = tenancyDepositCertificate;
		this.councilTaxRegistration = councilTaxRegistration;
		this.additionalLinks = additionalLinks;
		this.correspondenceWithTenants = correspondenceWithTenants;
		this.correspondenceLink = correspondenceLink;
		this.employerCompany = employerCompany;
		this.guarantorCompany = guarantorCompany;
		this.tenantTitle = tenantTitle;
		this.tenantFirstName = tenantFirstName;
		this.tenantLastName = tenantLastName;
		this.tenantDateOfBirth = tenantDateOfBirth;
		this.tenantLandlineNumber = tenantLandlineNumber;
		this.tenantMobileNumber = tenantMobileNumber;
		this.tenantEmailAddress = tenantEmailAddress;
		this.tenantNationalInsuranceNumber = tenantNationalInsuranceNumber;
		this.tenantCurrentAddress = tenantCurrentAddress;
		this.tenantPassport = tenantPassport;
		this.tenantDriversLicense = tenantDriversLicense;
		this.tenantCreditCheck = tenantCreditCheck;
		this.tenantReferenceCheck = tenantReferenceCheck;
		this.tenantAdditionalRemarks = tenantAdditionalRemarks;
		this.tenancyFinishDate=tenancyFinishDate;
		this.setStatus(status);
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
		this.updatedDetails = updatedDetails;
		this.setRentDueDate(rentDueDate);
		
		this.isOccupied = isOccupied;
	}

	public String getCheckingInForm() {
		return checkingInForm;
	}
	public void setCheckingInForm(String checkingInForm) {
		this.checkingInForm = checkingInForm;
	}

	public String getIsOccupied() {
		return isOccupied;
	}

	public void setIsOccupied(String isOccupied) {
		this.isOccupied = isOccupied;
	}

	public String getTenancyTitle() {
		return tenancyTitle;
	}

	public void setTenancyTitle(String tenancyTitle) {
		this.tenancyTitle = tenancyTitle;
	}

	public String getTenantFirstName() {
		return tenantFirstName;
	}







	public void setTenantFirstName(String tenantFirstName) {
		this.tenantFirstName = tenantFirstName;
	}







	public String getTenantLastName() {
		return tenantLastName;
	}







	public void setTenantLastName(String tenantLastName) {
		this.tenantLastName = tenantLastName;
	}


	public String getTenantLandlineNumber() {
		return tenantLandlineNumber;
	}


	public void setTenantLandlineNumber(String tenantLandlineNumber) {
		this.tenantLandlineNumber = tenantLandlineNumber;
	}

	public String getTenantMobileNumber() {
		return tenantMobileNumber;
	}







	public void setTenantMobileNumber(String tenantMobileNumber) {
		this.tenantMobileNumber = tenantMobileNumber;
	}

	public String getTenantEmailAddress() {
		return tenantEmailAddress;
	}

	public void setTenantEmailAddress(String tenantEmailAddress) {
		this.tenantEmailAddress = tenantEmailAddress;
	}

	public String getTenantNationalInsuranceNumber() {
		return tenantNationalInsuranceNumber;
	}

	public void setTenantNationalInsuranceNumber(
			String tenantNationalInsuranceNumber) {
		this.tenantNationalInsuranceNumber = tenantNationalInsuranceNumber;
	}

	public String getTenantCurrentAddress() {
		return tenantCurrentAddress;
	}

	public void setTenantCurrentAddress(String tenantCurrentAddress) {
		this.tenantCurrentAddress = tenantCurrentAddress;
	}

	public String getTenantPassport() {
		return tenantPassport;
	}

	public void setTenantPassport(String tenantPassport) {
		this.tenantPassport = tenantPassport;
	}

	public String getTenantDriversLicense() {
		return tenantDriversLicense;
	}

	public void setTenantDriversLicense(String tenantDriversLicense) {
		this.tenantDriversLicense = tenantDriversLicense;
	}

	public String getTenantCreditCheck() {
		return tenantCreditCheck;
	}

	public void setTenantCreditCheck(String tenantCreditCheck) {
		this.tenantCreditCheck = tenantCreditCheck;
	}

	public String getTenantReferenceCheck() {
		return tenantReferenceCheck;
	}

	public void setTenantReferenceCheck(String tenantReferenceCheck) {
		this.tenantReferenceCheck = tenantReferenceCheck;
	}

	public String getTenantAdditionalRemarks() {
		return tenantAdditionalRemarks;
	}

	public void setTenantAdditionalRemarks(String tenantAdditionalRemarks) {
		this.tenantAdditionalRemarks = tenantAdditionalRemarks;
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

	public TenancyForm() {
		super();
		this.id = -1;
	}

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getPropertyAddress() {
		return propertyAddress;
	}



	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}



	public String getTypeOfRental() {
		return typeOfRental;
	}



	public void setTypeOfRental(String typeOfRental) {
		this.typeOfRental = typeOfRental;
	}



	



	public String getLandLordFirstName() {
		return landLordFirstName;
	}



	public void setLandLordFirstName(String landLordFirstName) {
		this.landLordFirstName = landLordFirstName;
	}



	public String getLandLordLastName() {
		return landLordLastName;
	}



	public void setLandLordLastName(String landLordLastName) {
		this.landLordLastName = landLordLastName;
	}



	public String getLandLordAddress() {
		return landLordAddress;
	}



	public void setLandLordAddress(String landLordAddress) {
		this.landLordAddress = landLordAddress;
	}



	public String getLandLordEmailAddress() {
		return landLordEmailAddress;
	}



	public void setLandLordEmailAddress(String landLordEmailAddress) {
		this.landLordEmailAddress = landLordEmailAddress;
	}



	public String getLandLordContactNumber() {
		return landLordContactNumber;
	}



	public void setLandLordContactNumber(String landLordContactNumber) {
		this.landLordContactNumber = landLordContactNumber;
	}



	public String getEmployerFirstName() {
		return employerFirstName;
	}



	public void setEmployerFirstName(String employerFirstName) {
		this.employerFirstName = employerFirstName;
	}



	public String getEmployerLastName() {
		return employerLastName;
	}



	public void setEmployerLastName(String employerLastName) {
		this.employerLastName = employerLastName;
	}



	public String getEmployerAddress() {
		return employerAddress;
	}



	public void setEmployerAddress(String employerAddress) {
		this.employerAddress = employerAddress;
	}



	public String getEmployerEmailAddress() {
		return employerEmailAddress;
	}



	public void setEmployerEmailAddress(String employerEmailAddress) {
		this.employerEmailAddress = employerEmailAddress;
	}



	public String getEmployerContactNumber() {
		return employerContactNumber;
	}



	public void setEmployerContactNumber(String employerContactNumber) {
		this.employerContactNumber = employerContactNumber;
	}



	public String getGuarantorFirstName() {
		return guarantorFirstName;
	}



	public void setGuarantorFirstName(String guarantorFirstName) {
		this.guarantorFirstName = guarantorFirstName;
	}



	public String getGuarantorLastName() {
		return guarantorLastName;
	}



	public void setGuarantorLastName(String guarantorLastName) {
		this.guarantorLastName = guarantorLastName;
	}



	public String getGuarantorAddress() {
		return guarantorAddress;
	}



	public void setGuarantorAddress(String guarantorAddress) {
		this.guarantorAddress = guarantorAddress;
	}



	public String getGuarantorEmailAddress() {
		return guarantorEmailAddress;
	}



	public void setGuarantorEmailAddress(String guarantorEmailAddress) {
		this.guarantorEmailAddress = guarantorEmailAddress;
	}



	public String getGuarantorContactNumber() {
		return guarantorContactNumber;
	}



	public void setGuarantorContactNumber(String guarantorContactNumber) {
		this.guarantorContactNumber = guarantorContactNumber;
	}



	public String getKinFirstName() {
		return kinFirstName;
	}



	public void setKinFirstName(String kinFirstName) {
		this.kinFirstName = kinFirstName;
	}



	public String getKinLastName() {
		return kinLastName;
	}



	public void setKinLastName(String kinLastName) {
		this.kinLastName = kinLastName;
	}



	public String getKinAddress() {
		return kinAddress;
	}



	public void setKinAddress(String kinAddress) {
		this.kinAddress = kinAddress;
	}



	public String getKinEmailAddress() {
		return kinEmailAddress;
	}



	public void setKinEmailAddress(String kinEmailAddress) {
		this.kinEmailAddress = kinEmailAddress;
	}



	public String getKinContactNumber() {
		return kinContactNumber;
	}



	public void setKinContactNumber(String kinContactNumber) {
		this.kinContactNumber = kinContactNumber;
	}



	public String getTenancyOpenedBy() {
		return tenancyOpenedBy;
	}



	public void setTenancyOpenedBy(String tenancyOpenedBy) {
		this.tenancyOpenedBy = tenancyOpenedBy;
	}



	public Date getTenancyStartedDate() {
		return tenancyStartedDate;
	}



	public void setTenancyStartedDate(Date tenancyStartedDate) {
		this.tenancyStartedDate = tenancyStartedDate;
	}



	public String getTenancyClosedBy() {
		return tenancyClosedBy;
	}



	public void setTenancyClosedBy(String tenancyClosedBy) {
		this.tenancyClosedBy = tenancyClosedBy;
	}



	public Date getTenancyClosedDate() {
		return tenancyClosedDate;
	}



	public void setTenancyClosedDate(Date tenancyClosedDate) {
		this.tenancyClosedDate = tenancyClosedDate;
	}



	public String getCheckingOutForm() {
		return checkingOutForm;
	}



	public void setCheckingOutForm(String checkingOutForm) {
		this.checkingOutForm = checkingOutForm;
	}



	public String getLinkToProperty() {
		return linkToProperty;
	}



	public void setLinkToProperty(String linkToProperty) {
		this.linkToProperty = linkToProperty;
	}



	public String getLinkToTenancyAgreement() {
		return linkToTenancyAgreement;
	}



	public void setLinkToTenancyAgreement(String linkToTenancyAgreement) {
		this.linkToTenancyAgreement = linkToTenancyAgreement;
	}



	public String getLinkToRentAccounts() {
		return linkToRentAccounts;
	}



	public void setLinkToRentAccounts(String linkToRentAccounts) {
		this.linkToRentAccounts = linkToRentAccounts;
	}



	public String getLegalNotifications() {
		return legalNotifications;
	}



	public void setLegalNotifications(String legalNotifications) {
		this.legalNotifications = legalNotifications;
	}



	public String getTenancyDepositCertificate() {
		return tenancyDepositCertificate;
	}



	public void setTenancyDepositCertificate(String tenancyDepositCertificate) {
		this.tenancyDepositCertificate = tenancyDepositCertificate;
	}



	public String getCouncilTaxRegistration() {
		return councilTaxRegistration;
	}



	public void setCouncilTaxRegistration(String councilTaxRegistration) {
		this.councilTaxRegistration = councilTaxRegistration;
	}



	public String getAdditionalLinks() {
		return additionalLinks;
	}



	public void setAdditionalLinks(String additionalLinks) {
		this.additionalLinks = additionalLinks;
	}



	public String getCorrespondenceWithTenants() {
		return correspondenceWithTenants;
	}



	public void setCorrespondenceWithTenants(String correspondenceWithTenants) {
		this.correspondenceWithTenants = correspondenceWithTenants;
	}



	public String getCorrespondenceLink() {
		return correspondenceLink;
	}



	public void setCorrespondenceLink(String correspondenceLink) {
		this.correspondenceLink = correspondenceLink;
	}





	public String getEmployerCompany() {
		return employerCompany;
	}



	public void setEmployerCompany(String employerCompany) {
		this.employerCompany = employerCompany;
	}



	public String getGuarantorCompany() {
		return guarantorCompany;
	}



	public void setGuarantorCompany(String guarantorCompany) {
		this.guarantorCompany = guarantorCompany;
	}



	public PropertyDetailsForm getPropertyDetailsForm() {
		return propertyDetailsForm;
	}



	public void setPropertyDetailsForm(PropertyDetailsForm propertyDetailsForm) {
		this.propertyDetailsForm = propertyDetailsForm;
	}



	public String getRoomNumber() {
		return roomNumber;
	}



	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}







	public String getTenantTitle() {
		return tenantTitle;
	}







	public void setTenantTitle(String tenantTitle) {
		this.tenantTitle = tenantTitle;
	}







	public Date getTenantDateOfBirth() {
		return tenantDateOfBirth;
	}







	public void setTenantDateOfBirth(Date tenantDateOfBirth) {
		this.tenantDateOfBirth = tenantDateOfBirth;
	}







	public Date getTenancyFinishDate() {
		return tenancyFinishDate;
	}







	public void setTenancyFinishDate(Date tenancyFinishDate) {
		this.tenancyFinishDate = tenancyFinishDate;
	}

	public int getRentDueDate() {
		return rentDueDate;
	}

	public void setRentDueDate(int rentDueDate) {
		this.rentDueDate = rentDueDate;
	}
	
	@Override
	public String toString(){
		return tenantFirstName;
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
