package com.ardhika.wfar;

import java.util.HashSet;
import java.util.Set;

public class WfFork extends WfNode {
	private String assignee = "S:SYSTEM";

	public WfFork(String name) {

		super(name, WfNodeType.WF_FORK_NODE);
	}

	protected WfFork() {

	}

	public void setAssignee(String assignee) {

		this.assignee = assignee;
	}

	@Override
	public String getAssignee() {

		return assignee;
	}

}
