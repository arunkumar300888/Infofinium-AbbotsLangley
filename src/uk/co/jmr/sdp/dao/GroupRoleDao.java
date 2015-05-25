package uk.co.jmr.sdp.dao;

import java.util.List;
import java.util.Set;

import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.domain.GroupRole;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;

public interface GroupRoleDao {

	List<GroupRole> findAllGroupRoles();

	GroupRole findGroupRoleById(long id);

	void saveGroupRole(GroupRole groupRole);

	GroupRole findGroupRole(Group group, Role role);

	Set<Role> getRoles(Group group);

	List<User> getUsersFromGroupRole(GroupRole groupRole);

	List<User> findUsersFromGroup(Group group);

	Set<GroupRole> getGroupRolesForUser(User user);

	GroupRole getgroupRole(Group group, Role role);

	Set<User> getUsersFromRoleAndGroup(Role role, Group group);

	boolean haveRoleInGroup(Role role, Group group);

}
