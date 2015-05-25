package com.ardhika.wfar;

import uk.co.jmr.sdp.web.util.Util;
import java.util.Date;

public class CaseDocHistory implements Comparable<CaseDocHistory> {

	// For steps
	private Date actionDate;
	private String taskName;
	private String performedUser;

	// DocumentTrail
	private String action;
	private String reason;

	public CaseDocHistory() {

	}

	public CaseDocHistory(Date actionDate, String taskName, String userAssigned) {

		super();
		this.actionDate = actionDate;
		this.taskName = taskName;
		// this.performedUser = userAssigned;
		this.performedUser = Util.splitUserName(userAssigned);
	}

	public CaseDocHistory(String userAssigned, Date actionDate, String action) {

		super();
		// this.performedUser = userAssigned;
		this.performedUser = Util.splitUserName(userAssigned);
		this.actionDate = actionDate;
		this.action = action;
	}

	public Date getActionDate() {

		return actionDate;
	}

	public String getTaskName() {

		return taskName;
	}

	public String getPerformedUser() {

		return performedUser;
	}

	public String getAction() {

		return action;
	}

	public void setActionDate(Date actionDate) {

		this.actionDate = actionDate;
	}

	public void setTaskName(String taskName) {

		this.taskName = taskName;
	}

	public void setPerformedUser(String performedUser) {

		// this.performedUser = performedUser;
		this.performedUser = Util.splitUserName(performedUser);
	}

	public void setAction(String action) {

		this.action = action;
	}

	public String getReason() {

		return reason;
	}

	public void setReason(String reason) {

		this.reason = reason;
	}

	@Override
	public String toString() {

		return "CaseDocHistory [actionDate=" + actionDate + ", taskName=" + taskName + ", performedUser=" + performedUser
			+ ", action=" + action + "]";
	}

	@Override
	public int compareTo(CaseDocHistory o) {

		if (this.getActionDate() == null || o.getActionDate() == null)
			return 0;
		return this.actionDate.compareTo(o.getActionDate());
	}
}
