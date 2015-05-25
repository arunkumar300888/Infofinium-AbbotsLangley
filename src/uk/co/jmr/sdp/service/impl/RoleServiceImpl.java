package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.RoleDao;
import uk.co.jmr.sdp.domain.Feature;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Role> findAllRoles() {

		return this.roleDao.findAllRoles();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Role> findAllRolesWithInActive() {

		return this.roleDao.findAllRolesWithInActive();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Role findRoleById(long id) {

		return this.roleDao.findRoleById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Role findRoleByRoleName(String roleName) {

		return this.roleDao.findRoleByRoleName(roleName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRole(Role role) {

		this.roleDao.saveRole(role);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(long id) {

		Role role = this.roleDao.findRoleById(id);
		this.roleDao.deleteRole(role);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<User> findUsersForRole(long id) {

		Role role = this.roleDao.findRoleById(id);
		return this.roleDao.findUsersForRole(role);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Feature> findFeaturesForRole(long id) {

		Role role = this.roleDao.findRoleById(id);
		return this.roleDao.findFeaturesForRole(role);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<User> findUsersForRoleName(String roleName) {

		Role role = this.roleDao.findRoleByRoleName(roleName);
		return this.roleDao.findUsersForRole(role);
	}

}
