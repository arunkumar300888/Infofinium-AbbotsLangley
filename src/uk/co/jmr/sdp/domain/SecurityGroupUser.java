package uk.co.jmr.sdp.domain;

public class SecurityGroupUser {

	private long id = -1;
	private SecurityGroup securityGroup;
	private User user;

	public SecurityGroupUser() {

		this.id = -1;
	}

	public SecurityGroupUser(SecurityGroup securityGroup, User user) {

		this.id = -1;
		this.securityGroup = securityGroup;
		this.user = user;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public SecurityGroup getSecurityGroup() {

		return securityGroup;
	}

	public void setSecurityGroup(SecurityGroup securityGroup) {

		this.securityGroup = securityGroup;
	}

	public User getUser() {

		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}

	@Override
	public String toString() {

		return "SecurityGroupUser [securityGroup=" + securityGroup + ", user=" + user + "]";
	}

}
