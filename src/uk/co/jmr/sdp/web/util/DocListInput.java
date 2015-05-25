package uk.co.jmr.sdp.web.util;

import uk.co.jmr.sdp.domain.Discipline;
import uk.co.jmr.sdp.domain.Doctype;

public class DocListInput {
	private String origin;
	// for tree
	private String path;
	// For search form
	private Discipline discipline;
	private Doctype docType;
	private long docTypeId = -1;
	private String author;
	private String ebNo;
	private String documentName;
	private String keywords;
	private String relevantDateFrom;
	private String relevantDateTo;
	private String dateCreatedFrom;
	private String dateCreatedTo;
	private String specificData;
	private long userId;
	//For Discriminating D,F,M
	private char discriminator;
	

	public char getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(char discriminator) {
		this.discriminator = discriminator;
	}

	public String getKeywords() {

		return keywords;
	}

	public void setKeywords(String keywords) {

		this.keywords = keywords;
	}

	public String getRelevantDateFrom() {

		return relevantDateFrom;
	}

	public void setRelevantDateFrom(String relevantDateFrom) {

		this.relevantDateFrom = relevantDateFrom;
	}

	public String getRelevantDateTo() {

		return relevantDateTo;
	}

	public void setRelevantDateTo(String relevantDateTo) {

		this.relevantDateTo = relevantDateTo;
	}

	 public String getDateCreatedTo() {
	 return dateCreatedTo;
	 }
	 public void setDateCreatedTo(String dateCreatedTo) {
	 this.dateCreatedTo = dateCreatedTo;
	 }
	 public String getDateCreatedFrom() {
	 return dateCreatedFrom;
	 }
	 public void setDateCreatedFrom(String dateCreatedFrom) {
	 this.dateCreatedFrom = dateCreatedFrom;
	 }
	// For Tray Form
	private String taskName;
	private String modelName;

	public String getOrigin() {

		return origin;
	}

	public void setOrigin(String origin) {

		this.origin = origin;
	}

	public String getPath() {

		return path;
	}

	public void setPath(String path) {

		this.path = path;
	}

	public Discipline getDiscipline() {

		return discipline;
	}

	public void setDiscipline(Discipline discipline) {

		this.discipline = discipline;
	}

	public Doctype getDocType() {

		return docType;
	}

	public void setDocType(Doctype docType) {

		this.docType = docType;
	}

	public String getAuthor() {

		return author;
	}

	public void setAuthor(String author) {

		this.author = author;
	}

	public String getEbNo() {

		return ebNo;
	}

	public void setEbNo(String ebNo) {

		this.ebNo = ebNo;
	}

	public String getDocumentName() {

		return documentName;
	}

	public void setDocumentName(String documentName) {

		this.documentName = documentName;
	}

	public String getTaskName() {

		return taskName;
	}

	public void setTaskName(String taskName) {

		this.taskName = taskName;
	}

	public String getModelName() {

		return modelName;
	}

	public void setModelName(String modelName) {

		this.modelName = modelName;
	}

	/**
	 * @return the docTypeId
	 */
	public long getDocTypeId() {

		return docTypeId;
	}

	/**
	 * @param docTypeId
	 *            the docTypeId to set
	 */
	public void setDocTypeId(long docTypeId) {

		this.docTypeId = docTypeId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getSpecificData() {
		return specificData;
	}

	public void setSpecificData(String specificData) {
		this.specificData = specificData;
	}
}
