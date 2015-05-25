package uk.co.jmr.sdp.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TempDocuments {
	private long id;
	// private Discipline discipline;
	private long doctypeId;
	private Date targetDate;
	private String filePath;
	private String ebNo;
	private String author;
	private String keywords;
	private String name;
	private long caseId;
	private String locked = "NO";
	private Date dateCreated;
	private char wip;
	private String reassignOwner;
	private String deletionReason;
	private long securityGroupId;	
	private boolean revisionable = true;
	private boolean abandon;
	private char discriminator;
	private String userFormId;
	private Date createdDate;
	private long attributeValue1;
	private long attributeValue2;
	private long attributeValue3;
	/*private long attributeValue4;
	private long attributeValue5;*/
	
	public TempDocuments(){
		super();
		this.id=-1;
	}
	
	public TempDocuments(long doctypeId,Date targetDate,Date createdDate,String filePath,String ebNo,String author,String keywords,String name,long caseId,
			Date dateCreated,char wip,long securityGroupId,char dicriminator){
		this.doctypeId=doctypeId;
		this.targetDate=targetDate;
		this.createdDate=createdDate;
		this.filePath=filePath;
		this.ebNo=ebNo;
		this.author=author;
		this.keywords=keywords;
		this.name=name;
		this.caseId=caseId;
		this.dateCreated=dateCreated;
		this.wip=wip;
		this.securityGroupId=securityGroupId;
		this.discriminator=dicriminator;
		
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDoctypeId() {
		return doctypeId;
	}
	public void setDoctypeId(long doctypeId) {
		this.doctypeId = doctypeId;
	}
	public Date getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getEbNo() {
		return ebNo;
	}
	public void setEbNo(String ebNo) {
		this.ebNo = ebNo;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCaseId() {
		return caseId;
	}
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public char getWip() {
		return wip;
	}
	public void setWip(char wip) {
		this.wip = wip;
	}
	public String getReassignOwner() {
		return reassignOwner;
	}
	public void setReassignOwner(String reassignOwner) {
		this.reassignOwner = reassignOwner;
	}
	public String getDeletionReason() {
		return deletionReason;
	}
	public void setDeletionReason(String deletionReason) {
		this.deletionReason = deletionReason;
	}
	public long getSecurityGroupId() {
		return securityGroupId;
	}
	public void setSecurityGroupId(long securityGroupId) {
		this.securityGroupId = securityGroupId;
	}
	public boolean isRevisionable() {
		return revisionable;
	}
	public void setRevisionable(boolean revisionable) {
		this.revisionable = revisionable;
	}
	public boolean isAbandon() {
		return abandon;
	}
	public void setAbandon(boolean abandon) {
		this.abandon = abandon;
	}
	public char getDiscriminator() {
		return discriminator;
	}
	public void setDiscriminator(char discriminator) {
		this.discriminator = discriminator;
	}
	public String getUserFormId() {
		return userFormId;
	}
	public void setUserFormId(String userFormId) {
		this.userFormId = userFormId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public long getAttributeValue1() {
		return attributeValue1;
	}

	public void setAttributeValue1(long attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	public long getAttributeValue2() {
		return attributeValue2;
	}

	public void setAttributeValue2(long attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	public long getAttributeValue3() {
		return attributeValue3;
	}

	public void setAttributeValue3(long attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	/*public long getAttributeValue4() {
		return attributeValue4;
	}

	public void setAttributeValue4(long attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}

	public long getAttributeValue5() {
		return attributeValue5;
	}

	public void setAttributeValue5(long attributeValue5) {
		this.attributeValue5 = attributeValue5;
	}*/

}
