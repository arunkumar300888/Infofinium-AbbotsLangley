package uk.co.jmr.webforms.db.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UserForms generated by hbm2java
 */
public class UserForms implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private Forms forms;
	private long userId;
	private String title;
	private Date createdOn;
	private Date updatedOn;
	private char status;
	private long securityGroupId;
	private Date submissionDate;
	private long companyId;
	private long modelId;
	private Set<FormData> formDatas = new HashSet<FormData>(0);
	private char active='Y';
	private String formTable;

	public char getActive() {
		return active;
	}

	public void setActive(char active) {
		this.active = active;
	}
	
	public UserForms(Forms forms, long userId, Date createdOn, Date updatedOn,
			char status, long securityGroupId, Date submissionDate,
			long companyId, long modelId, char active,String formTable) {
		super();
		this.id=-1;
		this.forms = forms;
		this.userId = userId;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.status = status;
		this.securityGroupId = securityGroupId;
		this.submissionDate = submissionDate;
		this.companyId = companyId;
		this.modelId = modelId;
		this.active = active;
		this.formTable=formTable;
	}

	public UserForms() {

		super();
		this.id = -1L;
	}

	public UserForms(long id, Forms forms, long userId, char status, Date createdOn) {

		this.id = id;
		this.forms = forms;
		this.userId = userId;
		this.status = status;
		this.createdOn = createdOn;
	}

	public UserForms(long id, Forms forms, long userId, char status, Date createdOn, Date updatedOn, Set<FormData> formDatas) {

		this.id = id;
		this.forms = forms;
		this.userId = userId;
		this.status = status;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.formDatas = formDatas;
	}

	public long getId() {

		return this.id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public Forms getForms() {

		return this.forms;
	}

	public void setForms(Forms forms) {

		this.forms = forms;
	}

	public long getUserId() {

		return this.userId;
	}

	public void setUserId(long userId) {

		this.userId = userId;
	}

	public char getStatus() {

		return this.status;
	}

	public void setStatus(char status) {

		this.status = status;
	}

	public Date getCreatedOn() {

		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {

		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {

		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {

		this.updatedOn = updatedOn;
	}

	public Set<FormData> getFormDatas() {

		return this.formDatas;
	}

	public void setFormDatas(Set<FormData> formDatas) {

		this.formDatas = formDatas;
	}

	public long getSecurityGroupId() {
		return securityGroupId;
	}

	public void setSecurityGroupId(long securityGroupId) {
		this.securityGroupId = securityGroupId;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getModelId() {
		return modelId;
	}

	public void setModelId(long modelId) {
		this.modelId = modelId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFormTable() {
		return formTable;
	}

	public void setFormTable(String formTable) {
		this.formTable = formTable;
	}
}
