package com.ardhika.wfar;

public class WfTask extends WfNode {
	private String assignee;
	private char excludeOwner = 'N';

	public WfTask(String name, String assigneeRole, String labelName, char excludeOwner) {

		super(name, WfNodeType.WF_TASK_NODE, labelName);
		this.assignee = assigneeRole;
		this.excludeOwner = Character.toUpperCase(excludeOwner);
	}

	public String getAssignee() {

		return this.assignee;
	}

	protected void setAssignee(String assignee) {

		this.assignee = assignee;
	}

	@Override
	public String toString() {

		return super.toString() + "WfTask [assignee=" + assignee + "]";
	}

	public char getExcludeOwner() {

		return excludeOwner;
	}

	public void setExcludeOwner(char excludeOwner) {

		this.excludeOwner = excludeOwner;
	}

	protected WfTask() {

	}

}
