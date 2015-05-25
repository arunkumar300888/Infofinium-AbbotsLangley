package com.ardhika.wfar;

import java.util.ArrayList;
import java.util.List;

public class CaseStepInfo {

	private WfCase wfCase;
	private List<WfStep> stepsList = new ArrayList<WfStep>();

	public CaseStepInfo() {

	}

	public CaseStepInfo(WfCase wfCase) {

		this.wfCase = wfCase;
	}

	public WfCase getWfCase() {

		return wfCase;
	}

	public void setWfCase(WfCase wfCase) {

		this.wfCase = wfCase;
	}

	public List<WfStep> getStepsList() {

		return stepsList;
	}

	public void setStepsList(List<WfStep> stepsList) {

		this.stepsList = stepsList;
	}

	@Override
	public String toString() {

		return "CaseStepInfo [wfCase=" + wfCase + ", stepsList=" + stepsList + "]";
	}
}
