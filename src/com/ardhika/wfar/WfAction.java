package com.ardhika.wfar;

public class WfAction {
	private String name;
	private WfNode targetNode;

	private String targetStatus;

	private char reasonRequired = 'N';

	public WfAction(String name, WfNode targetNode, char reasonRequired, String targetStatus) {

		this.name = name;
		this.targetNode = targetNode;
		this.reasonRequired = reasonRequired;
		this.targetStatus = targetStatus;

	}

	public WfAction(String name, WfNode targetNode) {

		this.name = name;
		this.targetNode = targetNode;
	}

	public String getName() {

		return name;
	}

	public WfNode getTargetNode() {

		return targetNode;
	}

	@Override
	public String toString() {

		return "WfAction [name=" + name + ", target=" + ((targetNode != null) ? targetNode.getName() : "null") + "]";
	}

	// for Hibernate Mapping and Model management
	private long id = -1;

	protected WfAction() {

	}

	public long getId() {

		return id;
	}

	protected void setName(String name) {

		this.name = name;
	}

	protected void setTargetNode(WfNode targetNode) {

		this.targetNode = targetNode;
	}

	protected void setId(long id) {

		this.id = id;
	}

	public char getReasonRequired() {

		return reasonRequired;
	}

	public void setReasonRequired(char reasonRequired) {

		this.reasonRequired = reasonRequired;
	}

	public String getTargetStatus() {

		return targetStatus;
	}

	public void setTargetStatus(String targetStatus) {

		this.targetStatus = targetStatus;
	}

}
