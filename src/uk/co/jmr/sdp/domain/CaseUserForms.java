package uk.co.jmr.sdp.domain;

import java.util.Date;

public class CaseUserForms {
	public static final String META_DOCUMENT_NAME = "document_name";
	public static final String META_TARGET_DATE = "target_date";
	public static final String META_AUTHOR = "author";
	private long id;
	private long caseId;
	private long userFormsId;
	private Date targetDate;
	private String author;
	private String name;
	private String keywords;
	private String ebNo;
	private boolean abandon = false;

	public CaseUserForms() {

		this.id = -1;
	}

	public CaseUserForms(long caseId, long userFormsId, String name, String author, Date targetDate, String keywords, String ebNo) {
		this.id = -1;
		this.caseId = caseId;
		this.userFormsId = userFormsId;
		this.author = author;
		this.name = name;
		this.targetDate = targetDate;
		this.keywords = keywords;
		this.ebNo = ebNo;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public long getCaseId() {

		return caseId;
	}

	public void setCaseId(long caseId) {

		this.caseId = caseId;
	}

	public long getUserFormsId() {

		return userFormsId;
	}

	public void setUserFormsId(long userFormsId) {

		this.userFormsId = userFormsId;
	}

	/**
	 * @return the targetDate
	 */
	public Date getTargetDate() {

		return targetDate;
	}

	/**
	 * @param targetDate
	 *            the targetDate to set
	 */
	public void setTargetDate(Date targetDate) {

		this.targetDate = targetDate;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {

		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {

		this.author = author;
	}

	/**
	 * @return the name
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {

		this.name = name;
	}

	public String getKeywords() {

		return keywords;
	}

	public void setKeywords(String keywords) {

		this.keywords = keywords;
	}

	public String getEbNo() {

		return ebNo;
	}

	public void setEbNo(String ebNo) {

		this.ebNo = ebNo;
	}

	/**
	 * @return the abandon
	 */
	public boolean isAbandon() {

		return abandon;
	}

	/**
	 * @param abandon
	 *            the abandon to set
	 */
	public void setAbandon(boolean abandon) {

		this.abandon = abandon;
	}
}
