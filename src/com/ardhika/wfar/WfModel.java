package com.ardhika.wfar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WfModel {
	private String name;
	private WfNode start;
	private WfNode end;
	private Map<String, WfNode> nodeMap = new HashMap<String, WfNode>();
	private Set<WfNode> nodes = new HashSet<WfNode>();
	private Map<String, WfAttribute> attributes = new HashMap<String, WfAttribute>();
	private Set<WfAttribute> attribs = new HashSet<WfAttribute>();
	private String creatorRole;
	private char timeBound;

	private Set<uk.co.jmr.webforms.db.pojo.Forms> forms = new HashSet<uk.co.jmr.webforms.db.pojo.Forms>();

	// For Separate Document,Forms & Mix of Models ('D','F','M')
	private char modelCategory;

	public char getModelCategory() {

		return modelCategory;
	}

	public void setModelCategory(char modelCategory) {

		this.modelCategory = modelCategory;
	}

	public WfModel(String name) {

		this.name = name;
	}

	public WfModel(String name, String creatorRole, char timeBound) {

		this.name = name;
		this.creatorRole = creatorRole;
		this.timeBound = timeBound;
	}

	public WfModel(String name, String creatorRole, char timeBound, char modelCategory) {

		this.name = name;
		this.creatorRole = creatorRole;
		this.timeBound = timeBound;
		this.modelCategory = modelCategory;
	}

	public boolean addNode(WfNode node) {

		if (nodeMap.containsKey(node.getName()))
			return false;
		nodeMap.put(node.getName(), node);
		nodes.add(node);
		return true;
	}

	public void removeNode(String nodeName) {

		if (nodeMap.containsKey(nodeName)) {
			WfNode node = nodeMap.get(nodeName);
			nodes.remove(nodeMap.remove(node.getName()));
			if (node.getName().equals(start.getName()))
				start = null;
			if (node.getName().equals(end.getName()))
				end = null;
		}
	}

	public WfNode getNode(String nodeName) {

		if (nodeMap.containsKey(nodeName))
			return nodeMap.get(nodeName);
		return null;
	}

	public boolean setAsStart(String nodeName) throws Exception {

		if (!nodeMap.containsKey(nodeName))
			return false;
		WfNode node = nodeMap.get(nodeName);
		if (node.getNodeType() != WfNodeType.WF_TASK_NODE)
			throw new Exception("Unsupported start node");
		this.start = node;
		return true;
	}

	public boolean setAsEnd(String nodeName) throws Exception {

		if (!nodeMap.containsKey(nodeName))
			return false;
		WfNode node = nodeMap.get(nodeName);
		/*
		 * if (node.getNodeType() != WfNodeType.WF_TASK_NODE) throw new
		 * Exception("Unsupported start node");
		 */
		this.end = node;
		return true;
	}

	public WfNode getStartNode() {

		return this.start;
	}

	public WfNode getEndNode() {

		return this.end;
	}

	public String getName() {

		return this.name;
	}

	public boolean addAttribute(WfAttribute attribute) {

		if (attributes.containsKey(attribute.getName()))
			return false;
		attributes.put(attribute.getName(), attribute);
		attribs.add(attribute);
		return true;
	}

	public void removeAttribute(String attributeName) {

		if (attributes.containsKey(attributeName)) {
			WfAttribute attribute = attributes.get(attributeName);
			attribs.remove(attributes.remove((attribute).getName()));
		}
	}

	public WfAttribute getAttribute(String attributeName) {

		if (attributes.containsKey(attributeName))
			return attributes.get(attributeName);
		return null;
	}

	public boolean addDocTypeAttribute(String docType, String value) {

		String attrName = "dt:" + docType;
		if (attributes.containsKey(attrName))
			return false;

		WfAttribute attr = new WfAttribute(attrName, WfAttributeType.WF_ATTR_TEXT, value, false);
		attributes.put(attr.getName(), attr);
		attribs.add(attr);
		return true;
	}

	public void removeDocTypeAttribute(String docType) {

		String attrName = "dt:" + docType;
		if (attributes.containsKey(attrName)) {
			WfAttribute attribute = attributes.get(attrName);
			attribs.remove(attributes.remove((attribute).getName()));
		}
	}

	public Set<WfAttribute> getAttribs() {

		return this.attribs;
	}

	// for Hibernate Mapping and Model management
	private long id = -1;

	protected WfModel() {

	}

	protected WfNode getStart() {

		return start;
	}

	protected WfNode getEnd() {

		return end;
	}

	protected Set<WfNode> getNodes() {

		return this.nodes;
	}

	public long getId() {

		return id;
	}

	public String getCreatorRole() {

		return creatorRole;
	}

	protected void setCreatorRole(String creatorRole) {

		this.creatorRole = creatorRole;
	}

	protected void setName(String name) {

		this.name = name;
	}

	protected void setStart(WfNode start) {

		this.start = start;
	}

	protected void setEnd(WfNode end) {

		this.end = end;
	}

	protected void setNodes(Set<WfNode> nodes) {

		this.nodes = nodes;
		this.nodeMap.clear();
		for (WfNode node : nodes)
			this.addNode(node);
	}

	protected void setAttribs(Set<WfAttribute> attribs) {

		this.attribs = attribs;
		this.attributes.clear();
		for (WfAttribute attribute : attribs)
			this.addAttribute(attribute);
	}

	protected void setId(long id) {

		this.id = id;
	}

	public char getTimeBound() {

		return timeBound;
	}

	public void setTimeBound(char timeBound) {

		this.timeBound = timeBound;
	}

	public Set<uk.co.jmr.webforms.db.pojo.Forms> getForms() {

		return forms;
	}

	public void setForms(Set<uk.co.jmr.webforms.db.pojo.Forms> forms) {

		this.forms = forms;
	}
}
