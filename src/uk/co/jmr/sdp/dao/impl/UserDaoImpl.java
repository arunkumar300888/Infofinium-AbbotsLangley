package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.UserDao;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllUsers() {

		char isActive = 'Y';
		return (List<User>) hibernateTemplate.find("from User u where u.isActive=? order by u.userName", isActive);
		// return (List<User>)hibernateTemplate.find("from User");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllUsersWithInActive() {

		return (List<User>) hibernateTemplate.find("from User");
	}

	@Override
	public User findUserById(long id) {

		return hibernateTemplate.get(User.class, id);
	}

	@Override
	public void saveUser(User user) {

		hibernateTemplate.saveOrUpdate(user);
	}

	@Override
	public void deleteUser(User user) {

		hibernateTemplate.delete(user);
	}

	@Override
	public boolean assignRole(User user, Role role) {

		if (user.addToRoles(role)) {
			this.saveUser(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean unAssignRole(User user, Role role) {

		if (user.removeFromRoles(role)) {
			this.saveUser(user);
			return true;
		}
		return false;
	}

	@Override
	public User findUserByNameOrEmail(String userName, String email) {

		List<User> users = hibernateTemplate.find("from User as u where u.userName=? or u.emailId=?", userName, email);
		if (users.size() >= 1)
			return users.get(0);
		return null;
	}
	
	@Override
	public User findUserByNameAndEmail(String userName, String email) {

		List<User> users = hibernateTemplate.find("from User as u where u.userName=? and u.emailId=?", userName, email);
		if (users.size() >= 1)
			return users.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByUserName(String userName) {

		// System.out.println("userNm in Dao"+userName);
		List<User> users = hibernateTemplate.find("from User as u where u.userName=?", userName);
		if (users.size() >= 1)
			return users.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User checkLogin(String userName, String password) {
		char isActive = 'Y';
//		 List<User> users =
//		 hibernateTemplate.find("from User as u where u.userName=? and u.password=?",
//		 userName,password);
		List<User> users = hibernateTemplate.find("from User as u where u.userName=? and u.password=? and u.isActive=?",userName, password, isActive);
		if (users.size() >= 1) {
			if (users.get(0).getPassword().contains(password)) {
				return users.get(0);
			}
			else {
				return null;
			}
		}
		// return users.get(0);
		return null;
	}

	@Override
	public User checkEmailForUser(String userName, String emailId) {

		char isActive = 'Y';
		@SuppressWarnings("unchecked")
		List<User> users = hibernateTemplate.find("from User as u where u.userName=? and u.emailId=? and u.isActive=?", userName,
			emailId, isActive);
		if (users.size() >= 1) {
			if (users.get(0).getEmailId().contains(emailId)) {
				return users.get(0);
			}
			else {
				return null;
			}
		}
		return null;
	}
}
