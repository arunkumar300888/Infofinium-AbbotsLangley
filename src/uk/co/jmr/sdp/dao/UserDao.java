package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;

public interface UserDao {
	List<User> findAllUsers();

	List<User> findAllUsersWithInActive();

	User findUserById(long id);

	User findUserByUserName(String userName);

	void saveUser(User user);

	void deleteUser(User user);

	boolean assignRole(User user, Role role);

	boolean unAssignRole(User user, Role role);

	User checkLogin(String userName, String password);

	User checkEmailForUser(String userName, String emailId);

	User findUserByNameOrEmail(String userName, String Email);

	User findUserByNameAndEmail(String userName, String email);
}
