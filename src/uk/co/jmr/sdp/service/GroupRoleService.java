package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.domain.GroupRole;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;

public interface GroupRoleService {

	GroupRole findGroupRoleById(long grId);

	void saveGroupRole(GroupRole groupRole);

	List<User> getUsersFromGroupRole(GroupRole groupRole);

	GroupRole findGroupRole(Group group, Role role);

	List<GroupRole> findAllGroupRoles();

}
