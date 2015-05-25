package com.ardhika.wfar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WfCase {
	private WfModel model;
	// private WfStep currentStep;
	private Map<String, WfAttribute> attributeMap = new HashMap<String, WfAttribute>();
	private Set<WfAttribute> attributes = new HashSet<WfAttribute>();
	private String creator;
	private String creatorGroup;
	private Set<WfStep> steps = new HashSet<WfStep>();

	public WfCase(WfModel model, String creator, String creatorGroup) {

		this.model = model;
		this.creator = creator;
		this.creatorGroup = creatorGroup;
		// System.out.println("Copying attributeMap.................");
		for (WfAttribute attrM : model.getAttribs()) {
			WfAttribute attrC = new WfAttribute(attrM.getName(), attrM.getType(), attrM.getValue(), attrM.getUserEditable()
				.equalsIgnoreCase("Y"));
			attributeMap.put(attrC.getName(), attrC);
			attributes.add(attrC);
		}
	}

	public void setStatus(WfCaseStatus status) {

		this.status = status;
	}

	public WfStep start() throws Exception {

		status = WfCaseStatus.WF_CASE_OPEN;
		WfNode node = model.getStartNode();

		WfStep step = new WfStep(node, this, "New");
		step.setDateCreated(new Date());
		// this.currentStep = step;
		step.start();
		return step;
	}

	public void close() {

		status = WfCaseStatus.WF_CASE_CLOSED;
		dateCompleted = new Date();
	}

	public void cancel() {

		status = WfCaseStatus.WF_CASE_CANCELLED;
		dateCompleted = new Date();
	}

	private WfStep findStepWithNodeName(String nodeName) {

		boolean stepFound = false;
		WfStep foundStep = null;
		for (WfStep step : this.steps) {
			String stepNodeName = step.getNode().getName();
			if (stepNodeName.equalsIgnoreCase(nodeName)) {
				stepFound = true;
				foundStep = step;
				break;
			}
		}
		if (stepFound)
			return foundStep;
		for (WfStep step : this.processedSteps) {
			String stepNodeName = step.getNode().getName();
			if (stepNodeName.equalsIgnoreCase(nodeName)) {
				stepFound = true;
				foundStep = step;
			}
		}

		return foundStep;

	}

	public void doAction(WfStep step, String actionName, String username, String reason, String emailId) throws Exception {

		WfNode newNode = step.doAction(actionName, username, reason, emailId);
		//System.out.println("Step Id in doAction..." + step.getId());
		// save completed step

		addToProcessedSteps(step);
		WfStep newStep;
		if (newNode != null) {
			String statusLabel = step.getNode().getAction(actionName).getTargetStatus();
			if (newNode.getNodeType() != WfNodeType.WF_JOIN_NODE) {
				newStep = new WfStep(newNode, this, statusLabel);
			}
			else {
				WfStep joinStep = this.findStepWithNodeName(newNode.getName());
				if (joinStep == null) {
					newStep = new WfStep(newNode, this, statusLabel);
				}
				else {
					newStep = joinStep;
					newStep.setStatusLabel(statusLabel);
				}
			}

			// currentStep = newStep;//can remove this line
			// save new step
			addToProcessedSteps(newStep);
			newStep.start();
		}
		else {
			//System.out.println("***** End Node Name : " + model.getEndNode().getName());
			//System.out.println("***** Step Task Name : " + step.getNode().getName());
			if (!model.getEndNode().getName().equalsIgnoreCase(step.getNode().getName()))
				throw new Exception("Unexpected end of flow");
			else
				addToProcessedSteps(null);
		}
		// System.out.println("Size of processed steps : " +
		// processedSteps.size());
		// List<WfStep> retSteps = new ArrayList<WfStep>();
		// retSteps.addAll(processedSteps);
		// processedSteps.clear();
		// return retSteps;
	}

	public Object getAttribute(String attributeName) {

		if (!attributeMap.containsKey(attributeName))
			return null;
		else
			return attributeMap.get(attributeName).getValue();

	}

	public WfAttribute getWfAttribute(String attributeName) {

		if (!attributeMap.containsKey(attributeName))
			return null;
		else
			return attributeMap.get(attributeName);
	}

	public WfAttributeType getAttributeType(String attributeName) throws Exception {

		if (!attributeMap.containsKey(attributeName))
			throw new Exception("Invalid attribute");
		else
			return attributeMap.get(attributeName).getType();
	}

	public void setAttribute(String attributeName, Object value) {

		if (attributeMap.containsKey(attributeName)) {
			WfAttribute attr = attributeMap.get(attributeName);
			attr.setValue(value);
		}
	}

	// For Hibernate

	private long id = -1;
	private Date dateCreated;
	private Date dateCompleted;
	private WfCaseStatus status = WfCaseStatus.WF_CASE_OPEN;

	public WfCase() {

	}

	public WfModel getModel() {

		return model;
	}

	public void setModel(WfModel model) {

		this.model = model;
	}

	public Set<WfAttribute> getAttributes() {

		return this.attributes;
	}

	public void setAttributes(Set<WfAttribute> attributes) {

		this.attributes = attributes;
		this.attributeMap.clear();
		for (WfAttribute attr : attributes)
			this.attributeMap.put(attr.getName(), attr);
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public Date getDateCreated() {

		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {

		this.dateCreated = dateCreated;
	}

	public Date getDateCompleted() {

		return dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {

		this.dateCompleted = dateCompleted;
	}

	public String getStatus() {

		return this.status.toString();
	}

	public void setStatus(String status) {

		this.status = WfCaseStatus.valueOf(status);
	}

	public WfCaseStatus getCaseStatus() {

		return this.status;
	}

	public void setCaseStatus(WfCaseStatus status) {

		this.status = status;
	}

	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
	}

	public String getCreatorGroup() {

		return creatorGroup;
	}

	public void setCreatorGroup(String creatorGroup) {

		this.creatorGroup = creatorGroup;
	}

	private List<WfStep> processedSteps = new ArrayList<WfStep>();

	public List<WfStep> getProcessedSteps() {

		return processedSteps;
	}

	private void addToProcessedSteps(WfStep step) {

		if (step == null) {
			processedSteps.add(step);
			return;
		}

		for (int i = 0; i < processedSteps.size(); i++) {
			if (processedSteps.get(i) == step) {
				//System.out.println("step replacing" + step.getId());
				processedSteps.set(i, step);
				return;
			}
		}
		processedSteps.add(step);
	}

	public Set<WfStep> getSteps() {

		return steps;
	}

	protected void setSteps(Set<WfStep> steps) {

		this.steps = steps;
	}

}
