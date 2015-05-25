package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.RoleDao;
import uk.co.jmr.sdp.domain.Feature;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findAllRoles() {

		char isActive = 'Y';
		return hibernateTemplate.find("from Role r where r.isActive=?", isActive);
		// return hibernateTemplate.find("from Role");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findAllRolesWithInActive() {

		return hibernateTemplate.find("from Role");
	}

	@Override
	public Role findRoleById(long id) {

		return hibernateTemplate.get(Role.class, id);
	}

	@Override
	public void saveRole(Role role) {

		hibernateTemplate.saveOrUpdate(role);
	}

	@Override
	public void deleteRole(Role role) {

		hibernateTemplate.delete(role);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Role findRoleByRoleName(String roleName) {

		String query = "from Role as r where r.roleName=?";
		List<Role> result = (List<Role>) hibernateTemplate.find(query, roleName);
		if (result.size() >= 1)
			return result.get(0);
		return null;
		// return
		// (Role)hibernateTemplate.find("from Role as r where r.roleName=?",
		// roleName).get(0);
		// return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersForRole(Role role) {

		String query = "from User as u where " + role.getId() + " in(select r.id from u.roles as r)";
		return (List<User>) hibernateTemplate.find(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Feature> findFeaturesForRole(Role role) {

		String query = "from Feature as f where " + role.getId() + " in(select r.id from f.roles as r)";
		return (List<Feature>) hibernateTemplate.find(query);
	}

}
