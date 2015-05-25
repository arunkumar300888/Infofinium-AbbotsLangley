package uk.co.jmr.sdp.domain;

import java.util.Date;

public class BuilderDetailsForm {

	private long id;
	private String builderTitle;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String landlineNumber;
	private String mobileNumber;
	private String companyName;
	private String companyAddress;
	private String companyNumber;
	private String vatNumber;
	private String accountHoldersName;
	private String accountNumber;
	private String sortCode;
	private String linkToInvoice;
	private String linkToJobs;
	private char status;
	private User createdBy;
	private Date createdOn;
	private Date updatedOn;
	private User updatedBy;
	private String updatedDetails;
	
	public BuilderDetailsForm(){
		super();
		this.id=-1;
	}
	
	public BuilderDetailsForm(String builderTitle, String firstName,
			String lastName, String emailAddress, String landlineNumber,
			String mobileNumber, String companyName, String companyAddress,
			String companyNumber, String vatNumber, String accountHoldersName,
			String accountNumber, String sortCode, String linkToInvoice,
			String linkToJobs,char status,
			User createdBy,Date createdOn,
			Date updatedOn,User updatedBy,String updatedDetails) {
		super();
		this.id=-1;
		this.builderTitle = builderTitle;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.landlineNumber = landlineNumber;
		this.mobileNumber = mobileNumber;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.companyNumber = companyNumber;
		this.vatNumber=vatNumber;
		this.accountHoldersName = accountHoldersName;
		this.accountNumber = accountNumber;
		this.sortCode = sortCode;
		this.linkToInvoice = linkToInvoice;
		this.linkToJobs = linkToJobs;
		this.status=status;
		this.createdBy=createdBy;
		this.createdOn=createdOn;
		this.updatedOn=updatedOn;
		this.updatedBy=updatedBy;
		this.updatedDetails=updatedDetails;
	}
	

	public String getBuilderTitle() {
		return builderTitle;
	}

	public void setBuilderTitle(String builderTitle) {
		this.builderTitle = builderTitle;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	

	public String getAccountHoldersName() {
		return accountHoldersName;
	}

	public void setAccountHoldersName(String accountHoldersName) {
		this.accountHoldersName = accountHoldersName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getLinkToInvoice() {
		return linkToInvoice;
	}

	public void setLinkToInvoice(String linkToInvoice) {
		this.linkToInvoice = linkToInvoice;
	}

	public String getLinkToJobs() {
		return linkToJobs;
	}

	public void setLinkToJobs(String linkToJobs) {
		this.linkToJobs = linkToJobs;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	
	
}
