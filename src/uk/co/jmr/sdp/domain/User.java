package uk.co.jmr.sdp.domain;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class User {

	private long id;
	private String userName;
	private String password;
	private String emailId;

	private String mobileNo;
	private java.util.Date holidayFromDate;
	private java.util.Date holidayToDate;
	public String getMobileNo() {

		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {

		this.mobileNo = mobileNo;
	}

	private char isActive;


	public char getIsActive() {

		return isActive;
	}

	public void setIsActive(char isActive) {

		this.isActive = isActive;
	}

	private Set<Role> roles = new HashSet<Role>();

	private Set<GroupRole> groupRoles = new HashSet<GroupRole>();
	private Set<SecurityGroup> secGroups = new HashSet<SecurityGroup>();

	public Set<SecurityGroup> getSecGroups() {

		return secGroups;
	}

	public void setSecGroups(Set<SecurityGroup> secGroups) {

		this.secGroups = secGroups;
	}

	protected User() {

		super();
		this.id = -1;
	}

	public User(String userName, String password) {

		super();
		this.id = -1;
		this.userName = userName;
		this.password = password;
	}

	public User(String userName, String password, String emailId, char isActive, String mobileNo) {

		super();
		this.id = -1;
		this.userName = userName;
		this.password = password;
		this.emailId = emailId;
		this.isActive = isActive;
		this.mobileNo = mobileNo;
	}
	
	public User(String userName, String password, String emailId, char isActive, String mobileNo,Date holidayFromDate,Date holidayToDate) {

		super();
		this.id = -1;
		this.userName = userName;
		this.password = password;
		this.emailId = emailId;
		this.isActive = isActive;
		this.mobileNo = mobileNo;
		this.setHolidayFromDate(holidayFromDate);
		this.setHolidayToDate(holidayToDate);
		
	}

	
	public long getId() {

		return id;
	}

	public String getUserName() {

		return userName;
	}

	public String getPassword() {

		return password;
	}

	public Set<Role> getRoles() {

		return roles;
	}

	protected void setId(long id) {

		this.id = id;
	}

	protected void setUserName(String userName) {

		this.userName = userName;
	}

	protected void setPassword(String password) {

		this.password = password;
	}

	protected void setRoles(Set<Role> roles) {

		this.roles = roles;
	}

	public String getEmailId() {

		return emailId;
	}

	public void setEmailId(String emailId) {

		this.emailId = emailId;
	}

	public Set<GroupRole> getGroupRoles() {

		return groupRoles;
	}

	protected void setGroupRoles(Set<GroupRole> groupRoles) {

		this.groupRoles = groupRoles;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return (this.id == other.id && this.userName.equals(other.userName) && this.password.equals(other.password));
	}

	@Override
	public String toString() {

		return "[" + this.id + ", " + this.userName + ", " + this.password + "]";
	}

	public void changePassword(String newPassword) {

		this.password = newPassword;
	}

	public boolean addToRoles(Role role) {

		return this.roles.add(role);
	}

	public boolean removeFromRoles(Role role) {

		return this.roles.remove(role);
	}

	public boolean hasRole(Role role) {

		return this.roles.contains(role);
	}

	public boolean addToGroupRole(GroupRole groupRole) {

		return this.groupRoles.add(groupRole);
	}

	public boolean removeFromGroupRole(GroupRole groupRole) {

		return this.groupRoles.remove(groupRole);
	}

	public boolean addToSecurityGroup(SecurityGroup secGroup) {

		return this.secGroups.add(secGroup);
	}

	public boolean removeFromSecurityGroup(SecurityGroup secGroup) {

		return this.secGroups.remove(secGroup);
	}


	public java.util.Date getHolidayFromDate() {
		return holidayFromDate;
	}

	public void setHolidayFromDate(java.util.Date holidayFromDate) {
		this.holidayFromDate = holidayFromDate;
	}

	public java.util.Date getHolidayToDate() {
		return holidayToDate;
	}

	public void setHolidayToDate(java.util.Date holidayToDate) {
		this.holidayToDate = holidayToDate;
	}

	

	

}
