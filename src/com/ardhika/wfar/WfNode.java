package com.ardhika.wfar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class WfNode {

	private String name;
	private String trayLabel;
	private WfNodeType nodeType;
	private Map<String, WfAction> actionMap = new HashMap<String, WfAction>();
	private Set<WfAction> actions = new HashSet<WfAction>();

	protected WfNode(String name, WfNodeType nodeTYpe, String trayLabel) {

		this.name = name;
		this.nodeType = nodeTYpe;
		this.trayLabel = trayLabel;
	}

	protected WfNode(String name, WfNodeType nodeTYpe) {

		this.name = name;
		this.nodeType = nodeTYpe;
	}

	public String getName() {

		return this.name;
	}

	public WfNodeType getNodeType() {

		return nodeType;
	}

	public String getTrayLabel() {

		return trayLabel;
	}

	protected void setTrayLabel(String trayLabel) {

		this.trayLabel = trayLabel;
	}

	Set<String> getActionNames() {

		return actionMap.keySet();
	}

	public boolean addAction(WfAction action) {

		if (actionMap.containsKey(action.getName()))
			return false;
		actionMap.put(action.getName(), action);
		actions.add(action);
		return true;
	}

	public void removeAction(String actionName) {

		if (actionMap.containsKey(actionName)) {
			WfAction action = actionMap.get(actionName);
			actions.remove(actionMap.remove(action.getName()));
		}
	}

	public WfAction getAction(String actionName) {

		if (actionMap.containsKey(actionName))
			return actionMap.get(actionName);
		return null;
	}

	@Override
	public String toString() {

		return "WfNode [name=" + name + ", nodeType=" + nodeType + "]";
	}

	// for Hibernate Mapping and Model management
	private long id = -1;

	protected WfNode() {

	}

	public Set<WfAction> getActions() {

		return this.actions;
	}

	public long getId() {

		return id;
	}

	protected String getType() {

		return nodeType.toString();
	}

	protected void setName(String name) {

		this.name = name;
	}

	protected void setType(String nodeType) {

		this.nodeType = WfNodeType.valueOf(nodeType);
	}

	protected void setActions(Set<WfAction> actions) {

		this.actions = actions;
		this.actionMap.clear();
		for (WfAction action : actions)
			this.actionMap.put(action.getName(), action);
	}

	protected void setId(long id) {

		this.id = id;
	}

	public abstract String getAssignee();
}
