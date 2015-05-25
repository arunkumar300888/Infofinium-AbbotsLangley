package uk.co.jmr.sdp.domain;

import java.util.Date;

import com.ardhika.wfar.WfCase;

public class ReviewNote implements Comparable<ReviewNote> {
	private long id;
	private Date reviewDate;
	private String comments;
	// private Document document;
	private WfCase wfCase;
	private User reviewer;

	public ReviewNote() {

		super();
		this.id = -1;
	}

	// public ReviewNote(Date reviewDate, String comments,
	// Document document, User reviewer) {
	public ReviewNote(Date reviewDate, String comments, WfCase wfCase, User reviewer) {

		super();
		this.id = -1;
		this.reviewDate = reviewDate;
		this.comments = comments;
		this.wfCase = wfCase;
		// this.document = document;
		this.reviewer = reviewer;
	}

	public long getId() {

		return id;
	}

	public Date getReviewDate() {

		return reviewDate;
	}

	public String getComments() {

		return comments;
	}

	// public Document getDocument() {
	// return document;
	// }
	public User getReviewer() {

		return reviewer;
	}

	protected void setId(long id) {

		this.id = id;
	}

	protected void setReviewDate(Date reviewDate) {

		this.reviewDate = reviewDate;
	}

	protected void setComments(String comments) {

		this.comments = comments;
	}

	// protected void setDocument(Document document) {
	// this.document = document;
	// }
	protected void setReviewer(User reviewer) {

		this.reviewer = reviewer;
	}

	public WfCase getWfCase() {

		return wfCase;
	}

	protected void setWfCase(WfCase wfCase) {

		this.wfCase = wfCase;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		// result = prime * result
		// + ((document == null) ? 0 : document.hashCode());
		result = prime * result + ((wfCase == null) ? 0 : wfCase.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((reviewDate == null) ? 0 : reviewDate.hashCode());
		result = prime * result + ((reviewer == null) ? 0 : reviewer.hashCode());
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
		ReviewNote other = (ReviewNote) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		}
		else if (!comments.equals(other.comments))
			return false;
		// if (document == null) {
		// if (other.document != null)
		// return false;
		// } else if (!document.equals(other.document))
		// return false;
		if (wfCase == null) {
			if (other.wfCase != null)
				return false;
		}
		else if (!wfCase.equals(other.wfCase))
			return false;
		if (id != other.id)
			return false;
		if (reviewDate == null) {
			if (other.reviewDate != null)
				return false;
		}
		else if (!reviewDate.equals(other.reviewDate))
			return false;
		if (reviewer == null) {
			if (other.reviewer != null)
				return false;
		}
		else if (!reviewer.equals(other.reviewer))
			return false;
		return true;
	}

	@Override
	public int compareTo(ReviewNote o) {

		if (o != null) {
			if (this.reviewDate.after(o.reviewDate)) {
				return 1;
			}
			else if (this.reviewDate.before(o.reviewDate)) {
				return -1;
			}
			else if (this.reviewDate.equals(o.reviewDate)) {
				return 0;
			}
		}
		return 0;
	}

	// @Override
	// public String toString() {
	// return "ReviewNote [id=" + id + ", reviewDate=" + reviewDate
	// + ", comments=" + comments + ", document=" + document
	// + ", reviewer=" + reviewer + "]";
	// }
	@Override
	public String toString() {

		return "ReviewNoteCase [id=" + id + ", reviewDate=" + reviewDate + ", comments=" + comments + ", wfCase=" + wfCase
			+ ", reviewer=" + reviewer + "]";
	}
}
