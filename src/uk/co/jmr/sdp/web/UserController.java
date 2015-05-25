package uk.co.jmr.sdp.web;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.apache.catalina.ant.FindLeaksTask;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.jmr.sdp.domain.Company;
import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.domain.GroupRole;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupUser;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.service.CompanyUserService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.GroupRoleService;
import uk.co.jmr.sdp.service.GroupService;
import uk.co.jmr.sdp.service.RoleService;
import uk.co.jmr.sdp.service.SecurityGroupService;
import uk.co.jmr.sdp.service.SecurityGroupUserService;
import uk.co.jmr.sdp.service.UserService;
import uk.co.jmr.sdp.web.util.UserInfo;
import uk.co.jmr.sdp.web.util.Util;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupRoleService grpRoleService;
	@Autowired
	private SecurityGroupService secGroupService;
	@Autowired
	private SecurityGroupUserService securityGroupUserService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private DtAttributeService dtAttributeService;
	@Autowired
	private CompanyUserService companyUserService;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;

	@RequestMapping(value = "/goCreate", method = RequestMethod.GET)
	public String createForm(Model model, HttpSession session) {

		// UserInfo userInfo = (UserInfo) session
		// .getAttribute("LOGGED_IN_USERINFO");
		// List<WfModel> models = modelService.canCreateCase(userInfo);
		// model.addAttribute("models", models);
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);

		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		model.addAttribute("companyList",attr1ValuesRestricted);
		model.addAttribute("title", "User Creation");
		return "createUser";
	}

	private void showGroupAndRoles(Model model, long userId) {

		List<Group> groups = groupService.findFirstLevelGroups();
		List<Role> roles = roleService.findAllRoles();
		model.addAttribute("title", "Group Role Creation");
		model.addAttribute("groups", groups);
		model.addAttribute("roles", roles);
		model.addAttribute("userId", userId);

	}

	@RequestMapping(value = "/showCreateGroupRole", method = RequestMethod.GET)
	public String showCreateGroupRoleForm(@RequestParam("userId") long userId, Model model, HttpSession session) {

		showGroupAndRoles(model, userId);
		return "createNewGroupRole";
	}

	@RequestMapping(value = "/createGroupRole", method = RequestMethod.POST)
	public String createNewGroupRole(@RequestParam("group") long groupId, @RequestParam("role") long roleId,
		@RequestParam("userId") long userId, Model model, HttpSession session) {

		Group grpObj = groupService.findGroupById(groupId);
		Role roleObj = roleService.findRoleById(roleId);
		GroupRole groupRoleCheck = grpRoleService.findGroupRole(grpObj, roleObj);
		if (groupRoleCheck != null) {
			model.addAttribute("result", "Group role already exists");
			showGroupAndRoles(model, userId);
			return "createNewGroupRole";
		}
		GroupRole groupRole = new GroupRole(roleObj, grpObj);
		grpRoleService.saveGroupRole(groupRole);
		model.addAttribute("result", "Group role created successfully");
		showGroupAndRoles(model, userId);
		return "createNewGroupRole";
	}

	@RequestMapping(value = "/goShowUserAssignment", method = RequestMethod.GET)
	public String showUserAssignment(@RequestParam("id") long userId, Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		model.addAttribute("userName", user.getUserName());
		Set<Role> userRoles = user.getRoles();
		return "showUserAssignment";
	}
	
	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public String editUserForm(@RequestParam("userId") long userId,Model model, HttpSession session) {
		model.addAttribute("title","User Updation");
		User user=userService.findUserById(userId);
		model.addAttribute("userData",user);
		return "editUser";
	}
	
	private void showAllUsers(Model model){
		List<User> userLists=userService.findAllUsersWithInActive();
		model.addAttribute("userLists", userLists);
		model.addAttribute("title","User Lists");
	}
	
	@RequestMapping(value = "/goShowUsers", method = RequestMethod.GET)
	public String showAllUsers(Model model, HttpSession session) {

		// List<User> userLists=userService.findAllUsers();
		List<User> userLists = userService.findAllUsersWithInActive();
		model.addAttribute("userLists", userLists);
		model.addAttribute("title", "User Lists");
		return "showUsers";
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public String gocreateNewUser(@RequestParam("createNewUser") String userName,
		@RequestParam("createNewPassword") String password, @RequestParam("createNewEmail") String emailId,
		@RequestParam("mobileNo") String mobileNo,@RequestParam("companyId")long[] attributeValueId, Model model, HttpSession session) {

		// User user=userService.findUserByUserName(userName);
		userName = userName.trim();
		password = password.trim();
		emailId = emailId.trim();
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);
		User user = userService.findUserByNameOrEmail(userName, emailId);
		if (user != null) {
			Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
			Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
			model.addAttribute("companyList",attr1ValuesRestricted);
			model.addAttribute("title", "User Creation");
			model.addAttribute("result", "User name or email id already exists");
			return "createUser";
		}

		userName = WordUtils.capitalizeFully(userName);
		// User newUser=new User(userName,password,emailId,'Y');
		User newUser = new User(userName, password, emailId, 'Y', mobileNo);
		userService.saveUser(newUser);
		model.addAttribute("title", "User Creation");
		model.addAttribute("result", "User Created Successfully");
		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		model.addAttribute("companyList",attr1ValuesRestricted);
		SecurityGroup sg=secGroupService.findSecurityGroupByName("Open");
		sg.addToUsers(newUser);
		secGroupService.saveSecurityGroup(sg);
		
		for(long compId:attributeValueId){
			AttributeValue av=dtAttributeValueService.findDtAttrValueById(compId);
			if(av!=null){
			CompanyUser cu=new CompanyUser( av, newUser);
			companyUserService.save(cu);
			}
		}
		
		return "createUser";
	}

	@RequestMapping(value = "/goShowUserSecGroupAssignment", method = RequestMethod.GET)
	public String showUserSecGroupAssignment(@RequestParam("id") long userId, Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		Set<SecurityGroup> userSecGroups = user.getSecGroups();
		model.addAttribute("title", "User Security Group Assignment");
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("userSecGroupLists", userSecGroups);
		List<SecurityGroup> secGroupLists = secGroupService.findAllSecurityGroups();
		model.addAttribute("userId", userId);
		model.addAttribute("secGroupLists", secGroupLists);
		return "showUserSecGroupAssignment";
	}

	
	
	@RequestMapping(value = "/goShowUserCompanyGroupAssignment", method = RequestMethod.GET)
	public String showUserCompanyAssignment(@RequestParam("id") long userId, Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		List<CompanyUser> userAttributeValues = companyUserService.findCompanyUsersForUserId(user);
		List<AttributeValue> atvs=new ArrayList<AttributeValue>();
		for(CompanyUser companyUser: userAttributeValues){
			atvs.add(companyUser.getAttrValue());
		}
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);

		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		model.addAttribute("title", "User Company Group Assignment");
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("companyListUser", atvs);
		model.addAttribute("companyList", attr1ValuesRestricted);
		model.addAttribute("userId", userId);
		return "assignCompanyToUser";
	}
	
	@RequestMapping(value = "/goShowUserRoleAssignment", method = RequestMethod.GET)
	public String showUserRoleAssignment(@RequestParam("id") long userId, Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		Set<Role> userRoles = user.getRoles();
		model.addAttribute("title", "User Role Assignment");
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("userRoleLists", userRoles);

		// Set<SecurityGroup> userSecGroups=user.getSecGroups();
		// System.out.println("Sec Groups:" +userSecGroups);

		List<Role> roles = roleService.findAllRoles();
		// model.addAttribute("title1","Available Role Lists");
		model.addAttribute("userId", userId);
		model.addAttribute("roleLists", roles);
		return "showUserRoleAssignment";
	}

	private List<GroupRole> getActiveGroupRole(List<GroupRole> grpRoles) {

		List<GroupRole> activeGroupRoles = new ArrayList<GroupRole>();
		for (GroupRole grpRole : grpRoles) {
			if (grpRole.getGroup().getIsActive() == 'Y' && grpRole.getRole().getIsActive() == 'Y')
				activeGroupRoles.add(grpRole);
		}
		return activeGroupRoles;
	}

	@RequestMapping(value = "/goShowUserGroupRoleAssignment", method = RequestMethod.GET)
	public String showUserGroupRoleAssignment(@RequestParam("id") long userId, Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		Set<GroupRole> userGroupRole = user.getGroupRoles();
		model.addAttribute("title", "User Group Role Assignment");
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("userGroupRoleLists", userGroupRole);
		List<GroupRole> groupRoles = grpRoleService.findAllGroupRoles();
		List<GroupRole> groupRolesActive = getActiveGroupRole(groupRoles);
		// model.addAttribute("groupRoleLists",groupRoles);
		model.addAttribute("groupRoleLists", groupRolesActive);
		model.addAttribute("userId", userId);
		return "showUserGroupRoleAssignment";
	}
	
	

	@RequestMapping(value = "/addGroupRoleToUser", method = RequestMethod.GET)
	public String addGroupRoleToUserAssignment(@RequestParam("grpRoleId") long grpRoleId, @RequestParam("userId") long userId,
		Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		GroupRole groupRole = grpRoleService.findGroupRoleById(grpRoleId);
		user.addToGroupRole(groupRole);
		userService.saveUser(user);
		Set<GroupRole> userGroupRoles = user.getGroupRoles();
		model.addAttribute("title", "User Group Role Assignment");
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("userGroupRoleLists", userGroupRoles);
		List<GroupRole> groupRoles = grpRoleService.findAllGroupRoles();
		List<GroupRole> groupRolesActive = getActiveGroupRole(groupRoles);
		model.addAttribute("groupRoleLists", groupRolesActive);
		// model.addAttribute("groupRoleLists",groupRoles);
		model.addAttribute("userId", userId);
		return "showUserGroupRoleAssignment";
	}

	@RequestMapping(value = "/addRoleToUser", method = RequestMethod.GET)
	public String addRoleToUserAssignment(@RequestParam("roleId") long roleId, @RequestParam("userId") long userId, Model model,
		HttpSession session) {
		int result= 0;
		User user = userService.findUserById(userId);
		Role role = roleService.findRoleById(roleId);
		List<Role> rolesList=roleService.findAllRoles();
		List<String> newRoleList=new ArrayList<String>();
		String returnResult=null;
		
		for(Role r:rolesList){
			newRoleList.add(r.getRoleName());	
		}
		
		UserInfo ui=new UserInfo(user);
		List<String> rls=new ArrayList<String>();
		for(String rl:ui.getRoles()){
			rls.add(rl);
		}
		boolean contains=false;
		
		for(String s1:ui.getRoles()){
			for(String s2:newRoleList){
				if(s1.equals(s2)){
					contains=true;
				}
			}
		}
		
		if(!rls.isEmpty()){
		for(String s:ui.getRoles()){
		if(s.equals("Third Party")){
				result= 1;
				model.addAttribute("result",result);
				returnResult=addRoleMethod(role, user, model, userId);
				return returnResult;
				
		}else{
			if(contains){
				if(role.getRoleName().equals("Third Party")){
					result= 2;
					model.addAttribute("result", result);
					returnResult=addRoleMethod(role, user, model, userId);
					return returnResult;
					
				}else{
					user.addToRoles(role);
					userService.saveUser(user);
					returnResult=addRoleMethod(role, user, model, userId);
					return returnResult;
				}
			}
		  }
		}
	  }
		else{
			user.addToRoles(role);
			userService.saveUser(user);
			returnResult=addRoleMethod(role, user, model, userId);
		return returnResult;
	}
		return addRoleMethod(role, user, model, userId);
	}
	@RequestMapping(value = "/addSecGroupToUser", method = RequestMethod.GET)
	public String addSecGroupToUserAssignment(@RequestParam("secGroupId") long secGroupId, @RequestParam("userId") long userId,
		Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		SecurityGroup secGroup = secGroupService.findSecurityGroupById(secGroupId);
		SecurityGroupUser secGroupUser = securityGroupUserService.findSecurityGroupUser(secGroup, user);
		if (secGroupUser != null)
			return null;
		user.addToSecurityGroup(secGroup);
		userService.saveUser(user);
		Set<SecurityGroup> userSecGroups = user.getSecGroups();
		model.addAttribute("title", "User Security Group Assignment");
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("userSecGroupLists", userSecGroups);
		List<SecurityGroup> secGroupLists = secGroupService.findAllSecurityGroups();
		model.addAttribute("userId", userId);
		model.addAttribute("secGroupLists", secGroupLists);
		return "showUserSecGroupAssignment";
	}
	
	@RequestMapping(value = "/addCompanyToUser", method = RequestMethod.GET)
	public String addCompanyToUserAssignment(@RequestParam("attrValueId") long attrValueId, @RequestParam("userId") long userId,
		Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		AttributeValue atv=dtAttributeValueService.findDtAttrValueById(attrValueId);
		CompanyUser cu=companyUserService.findCompanyUserByUserAndAttrValue(user, atv);
		if(cu==null){
			cu=new CompanyUser( atv, user);
			companyUserService.save(cu);
		}
		List<CompanyUser> userAttributeValues = companyUserService.findCompanyUsersForUserId(user);
		List<AttributeValue> atvs=new ArrayList<AttributeValue>();
		for(CompanyUser companyUsers: userAttributeValues){
			atvs.add(companyUsers.getAttrValue());
		}
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);

		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		model.addAttribute("title", "User Company Group Assignment");
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("companyListUser", atvs);
		model.addAttribute("companyList", attr1ValuesRestricted);
		model.addAttribute("userId", userId);
		return "assignCompanyToUser";
	}

	@RequestMapping(value = "/goDeleteUser", method = RequestMethod.GET)
	public String deleteUserAssignment(@RequestParam("id") long userId, Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		user.setIsActive('N');
		userService.saveUser(user);
		// List<User> userLists=userService.findAllUsers();
		List<User> userLists = userService.findAllUsersWithInActive();
		model.addAttribute("userLists", userLists);
		model.addAttribute("title", "User Lists");
		return "showUsers";
	}

	@RequestMapping(value = "/goUnDeleteUser", method = RequestMethod.GET)
	public String unDeleteUserAssignment(@RequestParam("id") long userId, Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		user.setIsActive('Y');
		userService.saveUser(user);
		// List<User> userLists=userService.findAllUsers();
		List<User> userLists = userService.findAllUsersWithInActive();
		model.addAttribute("userLists", userLists);
		model.addAttribute("title", "User Lists");
		return "showUsers";
	}

	@RequestMapping(value = "/deleteSecGroupFromUser", method = RequestMethod.GET)
	public String deleteSecGroupFromUserAssignment(@RequestParam("secGroupId") long secGroupId,
		@RequestParam("userId") long userId, Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		SecurityGroup secGroup = secGroupService.findSecurityGroupById(secGroupId);
		SecurityGroupUser secGroupUser = securityGroupUserService.findSecurityGroupUser(secGroup, user);
		if (secGroupUser != null)
			securityGroupUserService.delete(secGroupUser);

		// user.removeFromSecurityGroup(secGroup);
		// userService.saveUser(user);

		User userUpdated = userService.findUserById(userId);
		Set<SecurityGroup> userSecGroups = userUpdated.getSecGroups();
		model.addAttribute("title", "User Security Group Assignment");
		model.addAttribute("userName", userUpdated.getUserName());
		model.addAttribute("userSecGroupLists", userSecGroups);
		List<SecurityGroup> secGroupLists = secGroupService.findAllSecurityGroups();
		model.addAttribute("userId", userId);
		model.addAttribute("secGroupLists", secGroupLists);
		return "showUserSecGroupAssignment";
	}
	
	@RequestMapping(value = "/deleteCompanyFromUser", method = RequestMethod.GET)
	public String deleteCompanyGroupFromUserAssignment(@RequestParam("attrValueId") long attrValueId,
		@RequestParam("userId") long userId, Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		AttributeValue av=dtAttributeValueService.findDtAttrValueById(attrValueId);
		
		//List<CompanyUser> companyUsers=companyUserService.findCompanyUsersForUserId(user);
		CompanyUser companyUser=companyUserService.findCompanyUserByUserAndAttrValue(user, av);
		if(companyUser!=null)
			companyUserService.delete(companyUser);
		// user.removeFromSecurityGroup(secGroup);
		// userService.saveUser(user);
		List<CompanyUser> userAttributeValues = companyUserService.findCompanyUsersForUserId(user);
		List<AttributeValue> atvs=new ArrayList<AttributeValue>();
		for(CompanyUser companyUsers: userAttributeValues){
			atvs.add(companyUsers.getAttrValue());
		}
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);

		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		model.addAttribute("title", "User Company Group Assignment");
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("companyListUser", atvs);
		model.addAttribute("companyList", attr1ValuesRestricted);
		model.addAttribute("userId", userId);
		return "assignCompanyToUser";
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/emailUserDetails", method = RequestMethod.POST)
	@ResponseBody
	public String emailDetailsToUser(@RequestParam("userId") long userId,
			Model model, HttpSession session) {

		int result = 0;
		User user = userService.findUserById(userId);
		try {

			String message = "Please see your user credentials for the Forms Portal below\nUser credential Details:\nUserName: " + user.getUserName()
					+ "\nPassword: " + user.getPassword() + "\nEmail-Id: "
					+ user.getEmailId() + "\nMobileNo: " + user.getMobileNo();
			// Mailer mailer = new Mailer();
			// mailer.doEmail("Password Recovery Mail", message, emailId);
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(user.getEmailId());
			email.setSubject("User Credentials");
			email.setText(message);
			mailSender.send(email);
			model.addAttribute("message", "Mail sent successfully");
			result = 1;
			
		} catch (Exception ex) {
			// System.out.println("Exception:");
			result = 0;
		} finally {
			String html = "<script language='javascript' type='text/javascript'> window.top.window.emailCompleted('"
					+ result + "');</script>";
			return html;
		}
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUserForm(@RequestParam("userId") long userId,@RequestParam("updateEmail") String emailId,@RequestParam("updatemobileNo")String mobileNo,
			Model model, HttpSession session) {
		model.addAttribute("title","User Updation");
		User user=userService.findUserById(userId);
		user.setEmailId(emailId);
		user.setMobileNo(mobileNo);
		userService.saveUser(user);
		showAllUsers(model);
		return "showUsers";
	}

	@RequestMapping(value = "/deleteGroupRoleFromUser", method = RequestMethod.GET)
	public String deletegroupRoleFromUserAssignment(@RequestParam("grpRoleId") long grpRoleId,
		@RequestParam("userId") long userId, Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		GroupRole groupRole = grpRoleService.findGroupRoleById(grpRoleId);
		user.removeFromGroupRole(groupRole);
		userService.saveUser(user);
		Set<GroupRole> userGroupRoles = user.getGroupRoles();
		model.addAttribute("title", "User Group Role Assignment");
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("userGroupRoleLists", userGroupRoles);
		List<GroupRole> groupRoles = grpRoleService.findAllGroupRoles();
		model.addAttribute("groupRoleLists", groupRoles);
		model.addAttribute("userId", userId);
		return "showUserGroupRoleAssignment";
	}

	@RequestMapping(value = "/deleteRoleFromUser", method = RequestMethod.GET)
	public String deleteRoleFromUserAssignment(@RequestParam("roleId") long roleId, @RequestParam("userId") long userId,
		Model model, HttpSession session) {

		User user = userService.findUserById(userId);
		Role role = roleService.findRoleById(roleId);
		user.removeFromRoles(role);
		userService.saveUser(user);
		Set<Role> userRoles = user.getRoles();
		model.addAttribute("title", "User Role Assignment");
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("userRoleLists", userRoles);
		List<Role> roles = roleService.findAllRoles();
		// model.addAttribute("title1","Available Role Lists");
		model.addAttribute("userId", userId);
		model.addAttribute("roleLists", roles);
		return "showUserRoleAssignment";
	}

	public String addRoleMethod(Role role,User user,Model model,long userId){
		
		Set<Role> userRoles = user.getRoles();
		model.addAttribute("title", "User Role Assignment");
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("userRoleLists", userRoles);
		List<Role> roles = roleService.findAllRoles();
		// model.addAttribute("title1","Available Role Lists");
		model.addAttribute("userId", userId);
		model.addAttribute("roleLists", roles);
		return "showUserRoleAssignment";
	}
	
	@RequestMapping(value = "/setHolidayDateForUser", method = RequestMethod.POST)
	public String holidayForUser(@RequestParam("userId")long userId,@RequestParam("holidayDateFrom") String holidayFrom,@RequestParam("holidayDateTo") String holidayTo,Model model, HttpSession session) throws ParseException {
		User user=userService.findUserById(userId);
		UserInfo userInfo=new UserInfo(user);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date holidayDateFrom=dateFormat.parse(holidayFrom);
		java.util.Date holidayDateTo=dateFormat.parse(holidayTo);
		int result=0;
		boolean hasRole=false;
		String roleName=null;
		
		List<String> rls=new ArrayList<String>();
		
		for(String s:userInfo.getRoles()){
			if(!s.equals("Owner")){
				rls.add(s);
			}
		}
		
		for(String s1:rls){
			List<User> userRoles=roleService.findUsersForRoleName(s1);
			for(User s2:userRoles){
				if(!s2.getUserName().equals(userInfo.getUserName())){
					hasRole=true;	
				}else{
					roleName=s1;
				}
			}
		}
		
		if(hasRole){
			user.setHolidayFromDate(holidayDateFrom);
			user.setHolidayToDate(holidayDateTo);
			userService.saveUser(user);
			List<User> userLists = userService.findAllUsersWithInActive();
			model.addAttribute("userLists", userLists);
			model.addAttribute("title", "User Lists");
			return "showUsers";
		}
		else{
			if(roleName!=null){
				result=1;
				model.addAttribute("result", "Cannot add date.Only this user has the task "+roleName);
			}else{
				result=2;
				model.addAttribute("result", "Cannot add date.User has no task ");
			}model.addAttribute("title", "Holiday Master");
			if(user.getHolidayFromDate()!=null && user.getHolidayToDate()==null){
				model.addAttribute("holidayDateFrom", dateFormat.format(user.getHolidayFromDate()));
				
			}else if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
				model.addAttribute("holidayDateFrom", dateFormat.format(user.getHolidayFromDate()));
				model.addAttribute("holidayDateTo", dateFormat.format(user.getHolidayToDate()));
			}
			
			return "holidayForUser";
		}
	}
	
	@RequestMapping(value = "/goshowHoliday", method = RequestMethod.GET)
	public String showSelectHolidayForUser(@RequestParam("userId") long userId,
		Model model, HttpSession session) {
		
		User user=userService.findUserById(userId);
		model.addAttribute("title", "Holiday Master");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		if(user.getHolidayFromDate()!=null && user.getHolidayToDate()==null){
			model.addAttribute("holidayDateFrom", dateFormat.format(user.getHolidayFromDate()));
			
		}else if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
			model.addAttribute("holidayDateFrom", dateFormat.format(user.getHolidayFromDate()));
			model.addAttribute("holidayDateTo", dateFormat.format(user.getHolidayToDate()));
		}
		model.addAttribute("userId",userId);
		return "holidayForUser";
	}   
}
