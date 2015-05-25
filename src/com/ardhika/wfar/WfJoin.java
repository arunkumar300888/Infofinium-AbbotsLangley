package com.ardhika.wfar;

public class WfJoin extends WfNode {
	private String assignee = "S:SYSTEM";

	private WfFork owningFork;

	protected WfJoin() {

	}

	public WfJoin(String name, WfFork owningFork) {

		super(name, WfNodeType.WF_JOIN_NODE);
		this.owningFork = owningFork;

	}

	public boolean processInlets(WfStep step) {

		int totalInlets = this.owningFork.getActions().size();
		//System.out.println("Total Inlets..." + totalInlets);
		return (step.getInletsCameIn() == totalInlets);

	}

	public void setAssignee(String assignee) {

		this.assignee = assignee;
	}

	@Override
	public String getAssignee() {

		return assignee;
	}

	public WfFork getOwningFork() {

		return owningFork;
	}

	public void setOwningFork(WfFork owningFork) {

		this.owningFork = owningFork;
	}

}
