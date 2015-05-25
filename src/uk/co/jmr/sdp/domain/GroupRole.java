package uk.co.jmr.sdp.domain;

public class GroupRole {

	private long id;
	private Role role;
	private Group group;

	public GroupRole() {

		this.id = -1;
	}

	public GroupRole(Role role, Group group) {

		this.id = -1;
		this.role = role;
		this.group = group;
	}

	public Role getRole() {

		return role;
	}

	public void setRole(Role role) {

		this.role = role;
	}

	public Group getGroup() {

		return group;
	}

	public void setGroup(Group group) {

		this.group = group;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	@Override
	public String toString() {

		return "RoleName=" + role.getRoleName() + ", GroupName=" + group.getGroupName();
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		GroupRole other = (GroupRole) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		}
		else if (!group.equals(other.group))
			return false;
		if (id != other.id)
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		}
		else if (!role.equals(other.role))
			return false;
		return true;
	}
}
