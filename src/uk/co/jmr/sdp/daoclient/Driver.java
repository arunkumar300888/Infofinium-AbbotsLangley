package uk.co.jmr.sdp.daoclient;

import java.util.List;
//import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import uk.co.jmr.sdp.dao.FeatureDao;
//import uk.co.jmr.sdp.dao.RoleDao;
//import uk.co.jmr.sdp.dao.UserDao;
//import uk.co.jmr.sdp.domain.Feature;
import uk.co.jmr.sdp.domain.Role;
//import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.UserService;

public class Driver {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("root-context.xml");
		UserService userService = (UserService) context.getBean("userService", UserService.class);
		boolean exist = userService.userExistInFeatureWithUserId(7, "fea1");
		boolean existVaish = userService.userExistInFeatureWithUserName("vaish", "fea1");
		boolean existVaths = userService.userExistInFeatureWithUserName("vaths", "fea1");
		//System.out.println("Do User 2 Exist For Feature 'fea1' - " + exist);
		//System.out.println("Do user vaish exist for Feature 'fea1' " + existVaish);
		//System.out.println("Do user vaths exist for Feature 'fea1' " + existVaths);

		List<Role> rls = userService.findUnassignedRoles(4);
		for (Role r : rls) {
			//System.out.println(r);
		}

		// Set<Feature> f = userService.assignedFeatureToUser(3);
		// System.out.println("f in main ->"+f);

		// UserDao userDao = (UserDao)context.getBean("userDao",UserDao.class);
		// RoleDao roleDao = context.getBean("roleDao",RoleDao.class);
		// FeatureDao featureDao =
		// context.getBean("featureDao",FeatureDao.class);
		//
		// Role role = roleDao.findRoleById(1);
		// List<User> users = roleDao.findUsersForRole(role);
		// for(User u : users){
		// System.out.println(u);
		// }

		// Feature f1 = featureDao.findFeatureByFeatureName("fea1");
		//
		// featureDao.removeAccessForRole(f1, role);

		// User user = userDao.checkLogin("vaish","vais123");
		// System.out.println("User " + user);
		// Role role = roleDao.findRoleByRoleName("admin");
		// System.out.println("Role "+role);
		//
		// Role role = roleDao.findRoleById(2);
		//
		// for(Role r : user.getRoles()) {
		// System.out.println(r);
		// }
		// userDao.unAssignRole(user, role);
		// userDao.saveUser(user);

		/*
		 * FeatureDao featureDao =
		 * context.getBean("featureDao",FeatureDao.class);
		 * 
		 * List<Feature> features = featureDao.listAllFeatures(); List<Feature>
		 * fea2 = featureDao.listAllFeatures(); for(Feature f1 : features){
		 * System.out.println(f1); } Feature f2 = new Feature("fea main");
		 * featureDao.save(f2); Feature f1 = featureDao.findFeatureById(2);
		 * System.out.println("Feature with id 1 " +f1); featureDao.delete(f1);
		 * for(Feature fe2 : fea2){ System.out.println(fe2); }
		 * 
		 * 
		 * RoleDao roleDao = context.getBean("roleDao",RoleDao.class);
		 * List<Role> roles = roleDao.findAllRoles(); for(Role r : roles){
		 * System.out.println(r); } Role r1 = roleDao.findRoleById(1);
		 * System.out.println("Role 1 " +r1); Role r2 = new Role("User");
		 * roleDao.saveRole(r2);
		 * 
		 * UserDao userDao = (UserDao)context.getBean("userDao",UserDao.class);
		 * User user = userDao.findUserById(1);
		 * System.out.println("got user with id : 1 -> " + user); User user1 =
		 * new User("Jai","Jaip"); userDao.saveUser(user1);
		 * user.changePassword("vais123"); userDao.saveUser(user);
		 * userDao.deleteUser(user); List<User> users = userDao.findAllUsers();
		 * for(User u : users){ System.out.println(u); }
		 */
	}

}
