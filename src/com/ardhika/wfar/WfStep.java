package com.ardhika.wfar;

import java.util.Date;
import java.util.Set;

public class WfStep implements Comparable<WfStep> {
	private WfCase owningCase;
	private WfNode node;
	private WfStepStatus status = WfStepStatus.WF_STEP_ASSIGNED;
	private String assignee;
	private char readStatus = 'U';
	private String emailId;
	private int inletsCameIn = 0;
	private String statusLabel;

	private String assigneeBefore;

	public int getInletsCameIn() {

		return inletsCameIn;
	}

	public void setInletsCameIn(int inletsCameIn) {

		this.inletsCameIn = inletsCameIn;
	}

	public WfStep(WfNode node, WfCase owner, String statusLabel) {

		this.node = node;
		this.owningCase = owner;
		this.statusLabel = statusLabel;
		if (node.getAssignee().equalsIgnoreCase("U:{CREATOR}"))
			this.assignee = "U:" + owner.getCreator();
		else
			this.assignee = node.getAssignee();
	}

	public WfNode getNode() {

		return node;
	}

	public Set<String> getActionNames() {

		return node.getActionNames();
	}

	public void start() throws Exception {

		if (node.getAssignee().startsWith("U:")) {
			//System.out.println("In If setting PICKED ");
			this.status = WfStepStatus.WF_STEP_PICKED;
			this.datePicked = new Date();
		}
		else {
			//System.out.println("ASSIGNEDDDD ");
			this.status = WfStepStatus.WF_STEP_ASSIGNED;
		}
		this.setDateCreated(new Date());
		//System.out.println(this);
		if (node.getNodeType() == WfNodeType.WF_DECISION_NODE) {
			WfDecision decNode = (WfDecision) node;
			if (decNode.evaluate(owningCase))
				owningCase.doAction(this, "Yes", "SYSTEM", "", "");
			else
				owningCase.doAction(this, "No", "SYSTEM", "", "");
		}
		// For Parallel Flow
		else if (node.getNodeType() == WfNodeType.WF_ASSIGN_NODE) {
			//System.out.println("--------------In Assign Node ----");
			WfAssignment assignNode = (WfAssignment) node;
			assignNode.assignValue(owningCase);
			owningCase.doAction(this, "Done", "SYSTEM", "", "");
		}
		else if (node.getNodeType() == WfNodeType.WF_FORK_NODE) {
			//System.out.println("----In Fork Node");
			WfFork wfFork = (WfFork) node;
			// Loop thru' the actions and call doAction() on case
			for (String action : wfFork.getActionNames()) {
				//System.out.println("Action Name...." + action + "," + this.getId());
				owningCase.doAction(this, action, "SYSTEM", "", "");
			}

		}
		else if (node.getNodeType() == WfNodeType.WF_JOIN_NODE) {
			WfJoin wfJoin = (WfJoin) node;
			this.inletsCameIn++;
			if (wfJoin.processInlets(this))
				owningCase.doAction(this, "Joined", "SYSTEM", "", "");
		}
		else if (node.getNodeType() == WfNodeType.WF_STOP_NODE) {
			//System.out.println("-------In Stop Node---------");
			WfStop stopNode = (WfStop) node;
			stopNode.process(owningCase);
			autoComplete("SYSTEM");
		}
		else if (node.getNodeType() == WfNodeType.WF_CANCEL_NODE) {
			WfCancel cancelNode = (WfCancel) node;
			cancelNode.process(owningCase);
			autoComplete("SYSTEM");
		}
		else if (node.getNodeType() == WfNodeType.WF_MAIL_NODE) {
			WfMail mailNode = (WfMail) node;
			mailNode.process(owningCase);
			//System.out.println("$$$$$$$$$$$$" + owningCase.getSteps());
			owningCase.doAction(this, "Mailed", "SYSTEM", "", "");
		}
	}

	void autoComplete(String username) throws Exception {

		setUserCompleted(username);
		setDateCompleted(new Date());
		this.complete();
		owningCase.doAction(this, "", "", "", "");
	}

	void complete() {

		this.status = WfStepStatus.WF_STEP_COMPLETED;
		//System.out.println(this);
	}

	public WfNode doAction(String actionName, String username, String reason, String emailId) throws Exception {

		//System.out.println("Action name in doAction..." + actionName);
		if (actionName == null || actionName.length() == 0) {
			// step got autocompleted
			return null;
		}

		WfNode nextNode = null;
		//System.out.println("Action for Step" + this.id);
		WfAction action = node.getAction(actionName);

		if (action == null)
			throw new Exception("Action not supported");
		setActionApplied(action);
		setActionReason(reason);
		setUserCompleted(username);
		setDateCompleted(new Date());
		setEmailId(emailId);
		this.complete();
		nextNode = action.getTargetNode();
		return nextNode;
	}

	@Override
	public String toString() {

		// return "WfStep [node=" + node + ", status=" + status + "]";
		return "WfStep [id=" + id + " owningCase Id" + owningCase.getId() + ", status=" + status + "]";
	}

	// for Hibernate Mapping

	private long id = -1;
	private WfAction actionApplied;
	private String userCompleted;
	private Date dateCreated;
	private Date dateCompleted;
	private Date datePicked;
	private String actionReason;

	public WfStep() {

	}

	public void setId(long id) {

		this.id = id;
	}

	protected void setActionApplied(WfAction actionApplied) {

		this.actionApplied = actionApplied;
	}

	public void setUserCompleted(String userCompleted) {

		this.userCompleted = userCompleted;
	}

	public void setDateCreated(Date dateCreated) {

		this.dateCreated = dateCreated;
	}

	public void setDateCompleted(Date dateCompleted) {

		this.dateCompleted = dateCompleted;
	}

	public void setNode(WfNode node) {

		this.node = node;
	}

	public void setStatus(String status) {

		this.status = WfStepStatus.valueOf(status);
	}

	public long getId() {

		return id;
	}

	public String getStatus() {

		return status.toString();
	}

	public WfAction getActionApplied() {

		return actionApplied;
	}

	public String getUserCompleted() {

		return userCompleted;
	}

	public Date getDateCreated() {

		return dateCreated;
	}

	public Date getDateCompleted() {

		return dateCompleted;
	}

	public WfCase getOwningCase() {

		return owningCase;
	}

	public void setOwningCase(WfCase owningCase) {

		this.owningCase = owningCase;
	}

	public String getAssignee() {

		return assignee;
	}

	public void setAssignee(String assignee) {

		this.assignee = assignee;
	}

	public char getReadStatus() {

		return readStatus;
	}

	public void setReadStatus(char readStatus) {

		this.readStatus = readStatus;
	}

	public String getActionReason() {

		return actionReason;
	}

	public void setActionReason(String actionReason) {

		this.actionReason = actionReason;
	}

	public String getEmailId() {

		return emailId;
	}

	public void setEmailId(String emailId) {

		this.emailId = emailId;
	}

	public void setDatePicked(Date datePicked) {

		this.datePicked = datePicked;
	}

	public Date getDatePicked() {

		return datePicked;
	}

	public void changeStatusToPicked() {

		this.status = WfStepStatus.WF_STEP_PICKED;
	}

	@Override
	public int compareTo(WfStep o) {

		return this.dateCompleted.compareTo(o.dateCompleted);
	}

	public String getStatusLabel() {

		return statusLabel;
	}

	public void setStatusLabel(String statusLabel) {

		this.statusLabel = statusLabel;
	}

	public String getAssigneeBefore() {

		return assigneeBefore;
	}

	public void setAssigneeBefore(String assigneeBefore) {

		this.assigneeBefore = assigneeBefore;
	}
}
