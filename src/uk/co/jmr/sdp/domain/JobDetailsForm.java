package uk.co.jmr.sdp.domain;

import java.util.Date;

public class JobDetailsForm {
	private long id;
	
	private Date dateRaised;
	private String jobNumber;
	private String descriptionOfJob;
	private String photoOfIssue;
	private String isUrgency;
	private Date appoinmentDate;
	private Date appoinmentTimeRange;
	private String photoOfFixes;
	private PropertyDetailsForm linkOfPropertyForm;
	private TenancyForm linkOfTenantForm;
	private String linkToInvoiceForJob;
	private String linkToCompanyContactInfo;
	private String jobTitle;
	private String status;
	private User createdBy;
	private Date createdOn;
	private Date updatedOn;
	private User updatedBy;
	private String updatedDetails;
	private User builder;
	
	public JobDetailsForm(){
		super();
		this.id=-1;
	}
	
	
	

	public JobDetailsForm(Date dateRaised, String jobNumber,
			String descriptionOfJob, String photoOfIssue, String isUrgency,
			Date appoinmentDate, Date appoinmentTimeRange, String photoOfFixes,
			PropertyDetailsForm linkOfPropertyForm,
			TenancyForm linkOfTenantForm, String linkToInvoiceForJob,
			String linkToCompanyContactInfo, String jobTitle, String status,
			User createdBy, Date createdOn, Date updatedOn, User updatedBy,
			String updatedDetails, User builder) {
		super();
		this.id=-1;
		this.dateRaised = dateRaised;
		this.jobNumber = jobNumber;
		this.descriptionOfJob = descriptionOfJob;
		this.photoOfIssue = photoOfIssue;
		this.isUrgency = isUrgency;
		this.appoinmentDate = appoinmentDate;
		this.appoinmentTimeRange = appoinmentTimeRange;
		this.photoOfFixes = photoOfFixes;
		this.linkOfPropertyForm = linkOfPropertyForm;
		this.linkOfTenantForm = linkOfTenantForm;
		this.linkToInvoiceForJob = linkToInvoiceForJob;
		this.linkToCompanyContactInfo = linkToCompanyContactInfo;
		this.jobTitle = jobTitle;
		this.status = status;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
		this.updatedDetails = updatedDetails;
		this.builder = builder;
	}




	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDateRaised() {
		return dateRaised;
	}

	public void setDateRaised(Date dateRaised) {
		this.dateRaised = dateRaised;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getDescriptionOfJob() {
		return descriptionOfJob;
	}

	public void setDescriptionOfJob(String descriptionOfJob) {
		this.descriptionOfJob = descriptionOfJob;
	}

	public String getPhotoOfIssue() {
		return photoOfIssue;
	}

	public void setPhotoOfIssue(String photoOfIssue) {
		this.photoOfIssue = photoOfIssue;
	}

	public String getIsUrgency() {
		return isUrgency;
	}

	public void setIsUrgency(String isUrgency) {
		this.isUrgency = isUrgency;
	}

	public Date getAppoinmentDate() {
		return appoinmentDate;
	}

	public void setAppoinmentDate(Date appoinmentDate) {
		this.appoinmentDate = appoinmentDate;
	}

	public Date getAppoinmentTimeRange() {
		return appoinmentTimeRange;
	}

	public void setAppoinmentTimeRange(Date appoinmentTimeRange) {
		this.appoinmentTimeRange = appoinmentTimeRange;
	}

	public String getPhotoOfFixes() {
		return photoOfFixes;
	}

	public void setPhotoOfFixes(String photoOfFixes) {
		this.photoOfFixes = photoOfFixes;
	}

	public PropertyDetailsForm getLinkOfPropertyForm() {
		return linkOfPropertyForm;
	}

	public void setLinkOfPropertyForm(PropertyDetailsForm linkOfPropertyForm) {
		this.linkOfPropertyForm = linkOfPropertyForm;
	}

	public TenancyForm getLinkOfTenantForm() {
		return linkOfTenantForm;
	}

	public void setLinkOfTenantForm(TenancyForm linkOfTenantForm) {
		this.linkOfTenantForm = linkOfTenantForm;
	}

	public String getLinkToInvoiceForJob() {
		return linkToInvoiceForJob;
	}

	public void setLinkToInvoiceForJob(String linkToInvoiceForJob) {
		this.linkToInvoiceForJob = linkToInvoiceForJob;
	}

	public String getLinkToCompanyContactInfo() {
		return linkToCompanyContactInfo;
	}

	public void setLinkToCompanyContactInfo(String linkToCompanyContactInfo) {
		this.linkToCompanyContactInfo = linkToCompanyContactInfo;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public User getBuilder() {
		return builder;
	}

	public void setBuilder(User builder) {
		this.builder = builder;
	}
	
		
}
