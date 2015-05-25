package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.Feature;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;

public interface RoleService {
	List<Role> findAllRoles();

	List<Role> findAllRolesWithInActive();

	Role findRoleById(long id);

	Role findRoleByRoleName(String roleName);

	void saveRole(Role role);

	void deleteRole(long id);

	List<User> findUsersForRole(long id);

	List<User> findUsersForRoleName(String roleName);

	List<Feature> findFeaturesForRole(long id);
}
