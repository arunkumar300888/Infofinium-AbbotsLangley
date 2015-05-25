package com.ardhika.wfar;

import java.util.Set;

import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.domain.GroupRole;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;

public interface WfUserInfo {
	String getUserName();

	Set<String> getRoles();

	String getActiveRole();

	Set<String> getGroupRoles();

	String getActiveGroup();

	String getEmailId();

	long getUserId();

	/*
	 * Set<GroupRole> getGroupRoles(User user); Set<Group> getGroups(User user);
	 * 
	 * Set<User> getUsersFromGroupAndRole(Group group, Role role); Set<User>
	 * getUsersFromGroup(Group group);
	 */

}
