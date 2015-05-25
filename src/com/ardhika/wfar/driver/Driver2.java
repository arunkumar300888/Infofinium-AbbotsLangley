package com.ardhika.wfar.driver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javassist.compiler.ast.NewExpr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ardhika.wfar.WfUserInfo;
import com.ardhika.wfar.dao.CaseDao;

import uk.co.jmr.sdp.dao.GroupDao;
import uk.co.jmr.sdp.dao.GroupRoleDao;
import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.domain.GroupRole;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.GroupRoleService;
import uk.co.jmr.sdp.service.GroupService;
import uk.co.jmr.sdp.service.RoleService;
import uk.co.jmr.sdp.service.UserService;

public class Driver2 {

	public static void main(String[] args) {

		System.out.println("Enter Main");
		ApplicationContext context = new ClassPathXmlApplicationContext("root-context.xml");
		System.out.println("B4 userServvice");
		UserService userService = context.getBean("userService", UserService.class);
		// Driver2.createUser(userService);

		RoleService roleService = context.getBean("roleService", RoleService.class);
		GroupService groupService = context.getBean("groupService", GroupService.class);
		GroupRoleService groupRoleService = context.getBean("groupRoleService", GroupRoleService.class);
		GroupRoleDao groupRoleDao = context.getBean("groupRoleDao", GroupRoleDao.class);
		CaseDao caseDao = context.getBean("caseDao", CaseDao.class);

		WfUserInfo userInfo = UserInfoRepo.UI1;
		System.out.println(caseDao.findTrayLabelSummaryForUser(userInfo));

		/*
		 * System.out.println(groupService.findFirstLevelGroups()); GroupRole gr
		 * = groupRoleService.findGroupRoleById(3); Group g =
		 * groupService.findGroupById(1); //
		 * System.out.println("g-> "+g.getGroupRoles());
		 * 
		 * // System.out.println("%%% "+ groupRoleDao.findUsersFromGroup(g));
		 * 
		 * 
		 * List<User> us = groupRoleDao.getUsersFromGroupRole(gr);
		 * System.out.println("us-> "+us); for(User u: us){
		 * System.out.println(u.getId() +" -- "+ u.getUserName()); }
		 * 
		 * User u1 = userService.findUserById(1);
		 * System.out.println("u1-> "+u1);
		 * 
		 * // parentChildReferentialCheck(groupService);
		 * 
		 * // addRolesToGroup(roleService, groupService);
		 * 
		 * // addUsersToGroupRole(userService, groupRoleService, u1);
		 */
	}

	private static void parentChildReferentialCheck(GroupService groupService) {

		System.out.println("B4 group Save");
		Group grp = new Group("Finance");
		groupService.saveGroup(grp);
		System.out.println("group saved -> " + grp);

		Group parent = new Group("HEAD");
		groupService.saveGroup(parent);

		Group manager = new Group("Manager");
		groupService.saveGroup(manager);

		grp.setParentGroup(parent);
		groupService.saveGroup(grp);
		System.out.println("parent ->" + grp.getParentGroup());

		parent.addChildToParent(manager);
		parent.addChildToParent(grp);
		groupService.saveGroup(parent);
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("Parent Head Has Childrens->> " + parent.getChildGroups());
	}

	private static void addUsersToGroupRole(UserService userService, GroupRoleService groupRoleService, User u1) {

		GroupRole gr1 = groupRoleService.findGroupRoleById(4);
		System.out.println("GroupRole1 ->" + gr1.getGroup().getGroupName());
		u1.addToGroupRole(gr1);
		userService.saveUser(u1);
		System.out.println("### " + u1.getRoles());
		System.out.println("*** " + u1.getGroupRoles());
		Set<GroupRole> grls = u1.getGroupRoles();
		Set<Group> gp = new HashSet<Group>();
		for (GroupRole p : grls) {
			gp.add(p.getGroup());
		}
		System.out.println(gp);
	}

	private static void addRolesToGroup(RoleService roleService, GroupService groupService) {

		Group group = groupService.findGroupById(1);
		System.out.println("Group -> " + group.getGroupName());

		Role r1 = roleService.findRoleById(1);
		Role r2 = roleService.findRoleById(2);

		group.addRolesToGroup(r1);
		group.addRolesToGroup(r2);
		groupService.saveGroup(group);
	}

	/*private static void createUser(UserService userService) {

		String userName = "Karthik";
		String passWord = "123";
		boolean thirdParty=true;
		User user = userService.checkLogin(userName, passWord);
		if (user == null) {
			User newUser = new User(userName, passWord);
		}

	}*/
}