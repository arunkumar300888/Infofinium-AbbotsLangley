package uk.co.jmr.sdp.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Document implements Comparable<Document> {
	private long id;
	// private Discipline discipline;
	private Doctype doctype;
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
	private SecurityGroup securityGroup;
	private Set<DocumentAttribute> documentAttributes = new HashSet<DocumentAttribute>();
	private Set<DocumentReference> documentReference = new HashSet<DocumentReference>();
	
	private boolean revisionable = true;
	private boolean abandon;
	
	private char discriminator;
	
	public char getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(char discriminator) {
		this.discriminator = discriminator;
	}

	private String userFormId;

	public String getUserFormId() {
		return userFormId;
	}

	public void setUserFormId(String userFormId) {
		this.userFormId = userFormId;
	}

	public boolean isAbandon() {

		return abandon;
	}

	public void setAbandon(boolean abandon) {

		this.abandon = abandon;
	}

	public boolean isRevisionable() {

		return revisionable;
	}

	public void setRevisionable(boolean revisionable) {

		this.revisionable = revisionable;
	}

	public Set<DocumentReference> getDocumentReference() {

		return documentReference;
	}

	public void setDocumentReference(Set<DocumentReference> documentReference) {

		this.documentReference = documentReference;
	}

	public Document() {

		super();
		this.id = -1;
	}

	public Document(Doctype doctype, String filePath, String author, String name, long caseId, Date dateCreated, Date targetDate,
		String ebNo) {

		this.id = -1;
		this.doctype = doctype;
		this.filePath = filePath;
		this.author = author;
		this.name = name;
		this.caseId = caseId;
		this.dateCreated = dateCreated;
		this.targetDate = targetDate;
		this.ebNo = ebNo;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Document(Doctype doctype, String filePath, String author, String name, String keywords, long caseId, Date dateCreated,
		Date targetDate, String ebNo, char wip) {

		this.id = -1;
		this.doctype = doctype;
		this.filePath = filePath;
		this.author = author;
		this.name = name;
		this.keywords = keywords;
		this.caseId = caseId;
		this.dateCreated = dateCreated;
		this.targetDate = targetDate;
		this.ebNo = ebNo;
		this.wip = wip;
	}
	
	
	public Document(Doctype doctype, String filePath, String author, String name, String keywords, long caseId, Date dateCreated,
			Date targetDate, String ebNo, char wip,String userFormId) {
			this.id = -1;
			this.doctype = doctype;
			this.filePath = filePath;
			this.author = author;
			this.name = name;
			this.keywords = keywords;
			this.caseId = caseId;
			this.dateCreated = dateCreated;
			this.targetDate = targetDate;
			this.ebNo = ebNo;
			this.wip = wip;
			this.userFormId=userFormId;
		}
	
	public Document(Doctype doctype, String filePath, String author, String name, String keywords, long caseId, Date dateCreated,
			Date targetDate, String ebNo, char wip,String userFormId,char discriminator) {
			this.id = -1;
			this.doctype = doctype;
			this.filePath = filePath;
			this.author = author;
			this.name = name;
			this.keywords = keywords;
			this.caseId = caseId;
			this.dateCreated = dateCreated;
			this.targetDate = targetDate;
			this.ebNo = ebNo;
			this.wip = wip;
			this.userFormId=userFormId;
			this.discriminator=discriminator;
		}

	
	
	public String getKeywords() {

		return keywords;
	}

	public void setKeywords(String keywords) {

		this.keywords = keywords;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public char getWip() {

		return wip;
	}

	public void setWip(char wip) {

		this.wip = wip;
	}

	public SecurityGroup getSecurityGroup() {

		return securityGroup;
	}

	public void setSecurityGroup(SecurityGroup securityGroup) {

		this.securityGroup = securityGroup;
	}

	public Doctype getDoctype() {

		return doctype;
	}

	public void setDoctype(Doctype doctype) {

		this.doctype = doctype;
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

	public Set<DocumentAttribute> getDocumentAttributes() {

		return documentAttributes;
	}

	public void setDocumentAttributes(Set<DocumentAttribute> documentAttributes) {

		this.documentAttributes = documentAttributes;
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

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((doctype == null) ? 0 : doctype.hashCode());
		result = prime * result + ((ebNo == null) ? 0 : ebNo.hashCode());
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((targetDate == null) ? 0 : targetDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		}
		else if (!author.equals(other.author))
			return false;
		if (doctype == null) {
			if (other.doctype != null)
				return false;
		}
		else if (!doctype.equals(other.doctype))
			return false;
		if (ebNo == null) {
			if (other.ebNo != null)
				return false;
		}
		else if (!ebNo.equals(other.ebNo))
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		}
		else if (!filePath.equals(other.filePath))
			return false;
		if (id != other.id)
			return false;
		if (targetDate == null) {
			if (other.targetDate != null)
				return false;
		}
		else if (!targetDate.equals(other.targetDate))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "Document [id=" + id + ", doctype=" + doctype + ", filePath=" + filePath + ", author=" + author + ", name=" + name
			+ ", caseId=" + caseId + ", dateCreated=" + dateCreated + "]";
	}

	@Override
	public int compareTo(Document o) {
		String name1 = this.stripNameBeforerevision(this.name);
		String name2 = this.stripNameBeforerevision(o.name);
		int retval = name1.compareTo(name2);
		if (retval != 0)
			return retval;
		return this.dateCreated.compareTo(o.dateCreated);
	}

	private String stripNameBeforerevision(String name) {
		int l = name.lastIndexOf("-REV");
		if (l >= 0)
			return name.substring(0, l);
		return name;
	}
}
