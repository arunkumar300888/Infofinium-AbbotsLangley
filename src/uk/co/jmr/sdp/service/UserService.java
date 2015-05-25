package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;

public interface UserService {
	List<User> findAllUsers();

	List<User> findAllUsersWithInActive();

	User findUserById(long id);

	User findUserByUserName(String userName);

	void saveUser(User user);

	void deleteUser(long id);

	boolean assignRole(long userId, long roleId);

	boolean unAssignRole(long userId, long roleId);

	User checkLogin(String userName, String password);

	boolean userExistInFeatureWithUserId(long userId, String featureName);

	boolean userExistInFeatureWithUserName(String userName, String featureName);

	List<Role> findUnassignedRoles(long userId);

	User findUserByNameOrEmail(String userName, String Email);

	User checkEmailForUser(String userName, String emailId);
	
	User findUserByNameAndEmail(String userName, String email);
}
