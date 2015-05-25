package uk.co.jmr.sdp.domain;

import java.util.Date;

public class UtilitiesCompanyDetailsForm {

	private long id;
	private String utilitiesCompanyTitle;
	private String companyName;
	private String companyType;
	private String companyTelephone;
	private String companyAddress;
	private String openingAndClosingTimes;
	private String mainContactName;
	private String mainContactTelephone;
	private String mainContactEmailAddress;
	
	private char status;
	private User createdBy;
	private Date createdOn;
	private Date updatedOn;
	private User updatedBy;
	private String updatedDetails;
	
	public  UtilitiesCompanyDetailsForm(){
		super();
		this.id=-1;
	}
	
	public UtilitiesCompanyDetailsForm(String utilitiesCompanyTitle, String companyName,
			String companyType, String companyTelephone, String companyAddress,
			String openingAndClosingTimes, String mainContactName,
			String mainContactTelephone, String mainContactEmailAddress,char status,
			User createdBy,Date createdOn,
			Date updatedOn,User updatedBy,String updatedDetails) {
		super();
		this.id=-1;
		this.utilitiesCompanyTitle = utilitiesCompanyTitle;
		this.companyName = companyName;
		this.companyType = companyType;
		this.companyTelephone = companyTelephone;
		this.companyAddress = companyAddress;
		this.openingAndClosingTimes = openingAndClosingTimes;
		this.mainContactName = mainContactName;
		this.mainContactTelephone=mainContactTelephone;
		this.mainContactEmailAddress = mainContactEmailAddress;
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

	

	public String getUtilitiesCompanyTitle() {
		return utilitiesCompanyTitle;
	}

	public void setUtilitiesCompanyTitle(String utilitiesCompanyTitle) {
		this.utilitiesCompanyTitle = utilitiesCompanyTitle;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCompanyTelephone() {
		return companyTelephone;
	}

	public void setCompanyTelephone(String companyTelephone) {
		this.companyTelephone = companyTelephone;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getOpeningAndClosingTimes() {
		return openingAndClosingTimes;
	}

	public void setOpeningAndClosingTimes(String openingAndClosingTimes) {
		this.openingAndClosingTimes = openingAndClosingTimes;
	}

	public String getMainContactName() {
		return mainContactName;
	}

	public void setMainContactName(String mainContactName) {
		this.mainContactName = mainContactName;
	}

	

	public String getMainContactEmailAddress() {
		return mainContactEmailAddress;
	}

	public void setMainContactEmailAddress(String mainContactEmailAddress) {
		this.mainContactEmailAddress = mainContactEmailAddress;
	}

	public String getMainContactTelephone() {
		return mainContactTelephone;
	}

	public void setMainContactTelephone(String mainContactTelephone) {
		this.mainContactTelephone = mainContactTelephone;
	}
}
