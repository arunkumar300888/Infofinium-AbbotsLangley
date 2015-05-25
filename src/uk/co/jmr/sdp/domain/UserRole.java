package uk.co.jmr.sdp.domain;

public class UserRole 
{

private long userId;
private long roleId;
	


public UserRole()
{
	super();
}

public UserRole(long userId, long roleId)
{
	super();
	this.userId = userId;
	this.roleId = roleId;
}

public long getUserId() {
	return userId;
}

public void setUserId(long userId) {
	this.userId = userId;
}

public long getRoleId() {
	return roleId;
}

public void setRoleId(long roleId) {
	this.roleId = roleId;
}




}
