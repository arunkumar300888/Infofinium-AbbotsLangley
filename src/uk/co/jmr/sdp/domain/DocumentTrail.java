package uk.co.jmr.sdp.domain;

import java.util.Date;

public class DocumentTrail {

	private long id;
	private Document document;
	private User user;
	private String action;
	private Date date;
	private String taskName;

	public DocumentTrail() {

		super();
		this.id = -1;
	}

	public DocumentTrail(Document document, User user, String action, Date date, String taskName) {

		super();
		this.id = -1;
		this.document = document;
		this.action = action;
		this.date = date;
		this.user = user;
		this.taskName = taskName;
	}

	public long getId() {

		return id;
	}

	protected void setId(long id) {

		this.id = id;
	}

	public Document getDocument() {

		return document;
	}

	protected void setDocument(Document document) {

		this.document = document;
	}

	public User getUser() {

		return user;
	}

	protected void setUser(User user) {

		this.user = user;
	}

	public String getAction() {

		return action;
	}

	protected void setAction(String action) {

		this.action = action;
	}

	public Date getDate() {

		return date;
	}

	protected void setDate(Date date) {

		this.date = date;
	}

	public String getTaskName() {

		return taskName;
	}

	public void setTaskName(String taskName) {

		this.taskName = taskName;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((document == null) ? 0 : document.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if ((obj instanceof User))
			return false;
		if ((obj instanceof Document))
			return false;
		if (!(obj instanceof DocumentTrail))
			return false;
		DocumentTrail other = (DocumentTrail) obj;
		return (this.id == other.id && this.date.equals(other.date) && this.action.equals(other.action));
	}

	// @Override
	// public String toString() {
	// return "DocumentTrail [id=" + id + ",Date=" + date + ", action="
	// + action + ", userid="
	// + user.getId()
	// + ", documentId=" + document.getId() + "]";
	// }

	@Override
	public String toString() {

		return "DocumentTrail [id=" + id + ", document=" + document + ", user=" + user + ", action=" + action + ", date=" + date
			+ ", taskName=" + taskName + "]";
	}

}
