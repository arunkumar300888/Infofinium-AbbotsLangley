package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.FeatureDao;
import uk.co.jmr.sdp.dao.RoleDao;
import uk.co.jmr.sdp.dao.UserDao;
import uk.co.jmr.sdp.domain.Feature;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private FeatureDao featureDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<User> findAllUsers() {

		return this.userDao.findAllUsers();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<User> findAllUsersWithInActive() {

		return this.userDao.findAllUsersWithInActive();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User findUserById(long id) {

		return this.userDao.findUserById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User findUserByUserName(String userName) {

		// System.out.println("name->"+userName);
		return this.userDao.findUserByUserName(userName);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User findUserByNameOrEmail(String userName, String email) {

		return this.userDao.findUserByNameOrEmail(userName, email);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUser(User user) {

		this.userDao.saveUser(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(long id) {

		User user = this.userDao.findUserById(id);
		this.userDao.deleteUser(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean assignRole(long userId, long roleId) {

		User user = this.userDao.findUserById(userId);
		Role role = this.roleDao.findRoleById(roleId);
		return this.userDao.assignRole(user, role);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean unAssignRole(long userId, long roleId) {

		User user = this.userDao.findUserById(userId);
		Role role = this.roleDao.findRoleById(roleId);
		return this.userDao.unAssignRole(user, role);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User checkLogin(String userName, String password) {

		return this.userDao.checkLogin(userName, password);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean userExistInFeatureWithUserId(long userId, String featureName) {

		User user = this.userDao.findUserById(userId);
		Feature feature = this.featureDao.findFeatureByFeatureName(featureName);
		for (Role r : feature.getRoles()) {
			if (user.hasRole(r)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean userExistInFeatureWithUserName(String userName, String featureName) {

		User user = this.userDao.findUserByUserName(userName);
		Feature feature = this.featureDao.findFeatureByFeatureName(featureName);
		for (Role r : feature.getRoles()) {
			if (user.hasRole(r)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Role> findUnassignedRoles(long userId) {

		User user = this.userDao.findUserById(userId);
		List<Role> availableRoles = roleDao.findAllRoles();
		for (Role r : user.getRoles()) {
			availableRoles.remove(r);
		}
		return availableRoles;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User checkEmailForUser(String userName, String emailId) {

		return this.userDao.checkEmailForUser(userName, emailId);
	}

	@Override
	public User findUserByNameAndEmail(String userName, String email) {
		// TODO Auto-generated method stub
		return this.userDao.findUserByNameAndEmail(userName, email);
	}
}
