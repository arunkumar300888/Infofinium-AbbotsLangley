package uk.co.jmr.sdp.domain;

import java.util.HashSet;
import java.util.Set;

public class Group {

	private long id;
	private String groupName;
	private char isActive;

	public char getIsActive() {

		return isActive;
	}

	public void setIsActive(char isActive) {

		this.isActive = isActive;
	}

	private Group parentGroup;
	private Set<Group> childGroups = new HashSet<Group>();

	private Set<GroupRole> groupRoles = new HashSet<GroupRole>();

	protected Group() {

		this.id = -1;
	}

	public String getGroupName() {

		return groupName;
	}

	public void setGroupName(String groupName) {

		this.groupName = groupName;
	}

	public Group(String groupName) {

		this.id = -1;
		this.groupName = groupName;
	}

	public Group(String groupName, Group parentGroup) {

		this.groupName = groupName;
		this.parentGroup = parentGroup;
	}

	public Group(String groupName, Group parentGroup, char isActive) {

		this.id = -1;
		this.groupName = groupName;
		this.parentGroup = parentGroup;
		this.isActive = isActive;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public Group getParentGroup() {

		return parentGroup;
	}

	public void setParentGroup(Group parentGroup) {

		this.parentGroup = parentGroup;
	}

	public Set<Group> getChildGroups() {

		return childGroups;
	}

	public void setChildGroups(Set<Group> childGroups) {

		this.childGroups = childGroups;
	}

	public Set<GroupRole> getGroupRoles() {

		return groupRoles;
	}

	public void setGroupRoles(Set<GroupRole> groupRoles) {

		this.groupRoles = groupRoles;
	}

	public void addRolesToGroup(Role role) {

		GroupRole groupRole = new GroupRole(role, this);
		this.groupRoles.add(groupRole);
	}

	/*
	 * public void removeFromRoles(Role role){ this.groupRoles.remove(role); }
	 */

	public void addChildToParent(Group group) {

		this.childGroups.add(group);
	}

	public void removeChildFromParent(Group group) {

		this.childGroups.remove(group);
	}

	@Override
	public String toString() {

		return "Group [id=" + id + ", groupName=" + groupName + ", roles=" + groupRoles + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
