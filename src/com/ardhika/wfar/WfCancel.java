package com.ardhika.wfar;

public class WfCancel extends WfNode {

	private String assignee = "S:SYSTEM";

	public WfCancel(String name) {

		super(name, WfNodeType.WF_CANCEL_NODE);
	}

	@Override
	public String getAssignee() {

		return assignee;
	}

	public void process(WfCase wfcase) {

		wfcase.close();
	}

	public void setAssignee(String assignee) {

		this.assignee = assignee;
	}

	protected WfCancel() {

	}
}
