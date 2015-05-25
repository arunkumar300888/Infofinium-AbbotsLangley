package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Feature;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;

public interface RoleDao {

	List<Role> findAllRoles();

	List<Role> findAllRolesWithInActive();

	Role findRoleById(long id);

	Role findRoleByRoleName(String roleName);

	void saveRole(Role role);

	void deleteRole(Role role);

	List<User> findUsersForRole(Role role);

	List<Feature> findFeaturesForRole(Role role);
}
