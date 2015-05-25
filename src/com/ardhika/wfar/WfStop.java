package com.ardhika.wfar;

public class WfStop extends WfNode {

	private String assignee = "S:SYSTEM";

	public WfStop(String name) {

		super(name, WfNodeType.WF_STOP_NODE);
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

	protected WfStop() {

	}

}
