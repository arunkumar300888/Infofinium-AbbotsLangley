package uk.co.jmr.sdp.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SecurityGroup {

	private long id = -1;
	private String name;
	private char isActive;

	public char getIsActive() {

		return isActive;
	}

	public void setIsActive(char isActive) {

		this.isActive = isActive;
	}

	private Set<SecurityGroupUser> securityGroupUsers = new HashSet<SecurityGroupUser>();

	public SecurityGroup() {

		this.id = -1;
	}

	// public SecurityGroup(String name) {
	// this.id = -1;
	// this.name = name;
	// }

	public SecurityGroup(String name, char isActive) {

		this.id = -1;
		this.name = name;
		this.isActive = isActive;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void addToUsers(User user) {

		SecurityGroupUser sgu = new SecurityGroupUser(this, user);
		this.securityGroupUsers.add(sgu);
	}

	public void removeFromUser(User user) {

		SecurityGroupUser sguFound = null;
		for (SecurityGroupUser sgu : this.securityGroupUsers) {
			User u = sgu.getUser();
			if (u.getId() == user.getId()) {
				sguFound = sgu;
				break;
			}
		}
		if (sguFound != null) {
			this.securityGroupUsers.remove(sguFound);
		}
	}

	public List<User> getUsers() {

		ArrayList<User> users = new ArrayList<User>();
		for (SecurityGroupUser sgu : this.securityGroupUsers) {
			users.add(sgu.getUser());
		}
		return users;
	}

	protected Set<SecurityGroupUser> getSecurityGroupUsers() {

		return securityGroupUsers;
	}

	protected void setSecurityGroupUsers(Set<SecurityGroupUser> securityGroupUsers) {

		this.securityGroupUsers = securityGroupUsers;
	}

	@Override
	public String toString() {

		return "SecurityGroup [name=" + name + "]";
	}
}
