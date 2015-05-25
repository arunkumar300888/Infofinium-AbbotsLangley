package uk.co.jmr.sdp.domain;

public class Role {

	private long id;
	private String roleName;
	private char isActive;

	public char getIsActive() {

		return isActive;
	}

	public void setIsActive(char isActive) {

		this.isActive = isActive;
	}

	protected Role() {

		super();
		this.id = -1;
	}

	public Role(String roleName) {

		super();
		this.id = -1;
		this.roleName = roleName;
	}

	public Role(String roleName, char isActive) {

		this.id = -1;
		this.roleName = roleName;
		this.isActive = isActive;
	}

	public long getId() {

		return id;
	}

	public String getRoleName() {

		return roleName;
	}

	protected void setId(long id) {

		this.id = id;
	}

	// protected void setRoleName(String roleName) {
	public void setRoleName(String roleName) {

		this.roleName = roleName;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Role))
			return false;
		Role other = (Role) obj;
		return (this.id == other.id && this.roleName.equals(other.roleName));
	}

	@Override
	public String toString() {

		return "[" + this.id + "," + this.roleName + "]";
	}

}
