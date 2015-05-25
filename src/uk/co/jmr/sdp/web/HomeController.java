package uk.co.jmr.sdp.web;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alfresco.webservice.repository.RepositoryFault;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.DocumentTrail;
import uk.co.jmr.sdp.domain.FormCompanyGroup;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.UserSession;
import uk.co.jmr.sdp.domain.ds.DMNode;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.forms.FormDefinition;
import uk.co.jmr.sdp.ds.WorkspaceSpider;
import uk.co.jmr.sdp.service.CompanyUserService;
import uk.co.jmr.sdp.service.DisciplineService;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.DocumentTrailService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.FeatureService;
import uk.co.jmr.sdp.service.FormCompanyGroupService;
import uk.co.jmr.sdp.service.FormService;
import uk.co.jmr.sdp.service.SecurityGroupService;
import uk.co.jmr.sdp.service.UserService;
import uk.co.jmr.sdp.service.UserSessionService;
import uk.co.jmr.sdp.web.util.TreeBuilder;
import uk.co.jmr.sdp.web.util.UserInfo;
import uk.co.jmr.sdp.web.util.Util;
import uk.co.jmr.webforms.db.dao.FormDefsDao;
import uk.co.jmr.webforms.db.pojo.FormDefs;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;
import uk.co.jmr.webforms.db.service.UserFormsService;

import com.ardhika.wfar.CaseStepInfo;
import com.ardhika.wfar.TaskSummary;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfModel;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.WfTask;
import com.ardhika.wfar.service.CaseService;
import com.ardhika.wfar.service.ModelService;
import com.ardhika.wfar.util.Mailer;
import com.visural.common.web.client.WebClient;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController{
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private UserService userService;
	@Autowired
	private DisciplineService disciplineService;
	@Autowired
	private DoctypeService doctypeService;
	@Autowired
	private WorkspaceSpider workspaceSpider;
	@Autowired
	private ModelService modelService;
	@Autowired
	private CaseService caseService;
	@Autowired
	private DtAttributeService dtAttributeService;
	@Autowired
	private SecurityGroupService securityGroupService;
	@Autowired
	private UserSessionService userSessionService;
	@Autowired
	private FeatureService featureService;
	@Autowired
	private FormService formService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private DocumentTrailService documentTrailService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private UserFormsService userFormsService;
	@Autowired
	private CompanyUserService companyUserService;
	@Autowired
	private FormCompanyGroupService formCompanyGroupService;
	@Autowired
	private FormDefsDao formDefsDao;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;
	
	Logger logger=Logger.getLogger(HomeController.class);

	// private static final Logger logger =
	// Logger.getLogger(HomeController.class);
	@RequestMapping(value = "/welcome/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String greet(@PathVariable("id") long id) {

		User user = userService.findUserById(id);
		String msg = (user != null) ? "Hello " + user.getUserName() + " !" : "User not found!";
		return msg;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String rootLogin() throws IOException {

		if (servletContext.getAttribute("disciplines") == null || servletContext.getAttribute("sites") == null) {
			List<Attribute> attributeList = dtAttributeService.findAllDtAttrs();
			if (servletContext.getAttribute("disciplines") == null) {
				Set<AttributeValue> attrivalueSet = Util.getAttributeBasedOnId(attributeList, 3);
				if (attrivalueSet != null) {
					servletContext.setAttribute("disciplines", attrivalueSet);
				}
			}
			if (servletContext.getAttribute("sites") == null) {
				Set<AttributeValue> attrivalueSet = Util.getAttributeBasedOnId(attributeList, 4);
				if (attrivalueSet != null) {
					servletContext.setAttribute("sites", attrivalueSet);
				}
			}
		}
		if (servletContext.getAttribute("doctypes") == null) {
			List<Doctype> docTypeLists = doctypeService.findAllDoctype();
			Collections.sort(docTypeLists);
			servletContext.setAttribute("doctypes", docTypeLists);
		}
		Properties props = new Properties();
		InputStream is = this.getClass().getResourceAsStream("/webserviceclient.properties");
		props.load(is);
		servletContext.setAttribute("foldername.inventoryRooms", props.getProperty("foldername.inventoryRooms"));
		servletContext.setAttribute("dsUser", props.getProperty("userName"));
		servletContext.setAttribute("dsPassword", props.getProperty("password"));
		servletContext.setAttribute("filePath", props.getProperty("filePath"));
		servletContext.setAttribute("foldername", props.getProperty("foldername"));
		// For Quick Upload on 04.01.13
		servletContext.setAttribute("filePath.quickupload", props.getProperty("filePath.quickupload"));
		servletContext.setAttribute("foldername.quickupload", props.getProperty("foldername.quickupload"));
		servletContext.setAttribute("foldername.localUpload", props.getProperty("foldername.localUpload"));
		servletContext.setAttribute("filePath.photos", props.getProperty("filePath.photos"));
		servletContext.setAttribute("foldername.photos", props.getProperty("foldername.photos"));
		servletContext.setAttribute("filePath.videos", props.getProperty("filePath.videos"));
		servletContext.setAttribute("foldername.videos", props.getProperty("foldername.videos"));
		servletContext.setAttribute("filePath.meetings", props.getProperty("filePath.meetings"));
		servletContext.setAttribute("foldername.meetings", props.getProperty("foldername.meetings"));
		servletContext.setAttribute("filePath.template", props.getProperty("filePath.template"));
		servletContext.setAttribute("foldername.template", props.getProperty("foldername.template"));
		servletContext.setAttribute("restServiceUrl", props.getProperty("repository.restservice"));
		servletContext.setAttribute("alfresco.rootpath", props.getProperty("alfresco.rootpath"));
		
		// Web Services URL For CMIS Start
		servletContext.setAttribute("restServiceWsdlUrl", props.getProperty("repoServiceUrl"));
		servletContext.setAttribute("navigationServiceWsdlUrl", props.getProperty("navigationServiceUrl"));
		servletContext.setAttribute("objectServiceWsdlUrl", props.getProperty("objectServiceUrl"));
		servletContext.setAttribute("versioningServiceWsdlUrl", props.getProperty("versioningServiceUrl"));
		servletContext.setAttribute("discoveryServiceWsdlUrl", props.getProperty("discoveryServiceUrl"));
		servletContext.setAttribute("multifilingServiceWsdlUrl", props.getProperty("multifilingServiceUrl"));
		servletContext.setAttribute("relationshipServiceWsdlUrl", props.getProperty("relationshipServiceUrl"));
		servletContext.setAttribute("aclServiceWsdlUrl", props.getProperty("aclServiceUrl"));
		servletContext.setAttribute("policyServiceWsdlUrl", props.getProperty("policyServiceUrl"));
		
		// Web Services URL For CMIS End
		// For Admin Feature Start
		servletContext.setAttribute("adminFeatureLists", featureService.listAllFeatures());
		servletContext.setAttribute("adminNone", props.getProperty("adminSecurityNone"));
		// Admin Feature End
		loadSysConfigProperties();
		// System.out.println("HOME: USerName-> " +
		// props.getProperty("userName"));
		// System.out.println("HOME: password-> " +
		// props.getProperty("password"));
		return "auth/login";
	}

	private void loadSysConfigProperties() throws IOException {

		Properties props = new Properties();
		InputStream is = this.getClass().getResourceAsStream("/sysconfig.properties");
		props.load(is);
		servletContext.setAttribute("sgAttrs", props.getProperty("attributesForSecurityGroup"));
		servletContext.setAttribute("revision", props.getProperty("revision"));
		servletContext.setAttribute("noWorkflowModleNameForForms", props.getProperty("noWorkflowModleNameForForms"));
	}

	private void loadSearchAttr(Model model,HttpSession session) {
		Attribute attr1 = dtAttributeService.findAttributeByOrder(1);
		Attribute attr2 = dtAttributeService.findAttributeByOrder(2);
		Attribute attr3 = dtAttributeService.findAttributeByOrder(3);
		//Attribute attr4 = dtAttributeService.findAttributeByOrder(4);
		session.setAttribute("attr1Search", attr1.getName());
		session.setAttribute("attr2Search", attr2.getName());
		session.setAttribute("attr3Search", attr3.getName());
	//	session.setAttribute("attr4Search", attr4.getName());
		//model.addAttribute("attr3", attr3.getName());
		//model.addAttribute("attr4", attr4.getName());
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.GET)
	public String login(Model model, HttpSession session) throws RepositoryFault, RemoteException {

		// For DSJV Hide Tree Control 17.07.13
		// String dsUser = (String) servletContext.getAttribute("dsUser");
		// String dsPassword = (String)
		// servletContext.getAttribute("dsPassword");
		// For DSJV Hide Tree Control 17.07.13
		// System.out.println("dsUser:" + dsUser + " " + "dsPass:" +
		// dsPassword);
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		// System.out.println("User Email:" + user.getEmailId());
		
		model.addAttribute("username", user.getUserName());
		Set<Role> rs = user.getRoles();
		Set<String> roles = new HashSet<String>();
		for (Role r : rs) {
			roles.add(r.getRoleName());
		}
		// System.out.println("roles->" + roles);
		UserInfo userInfo = new UserInfo(user);
		// System.out.println("UserInfo->" + userInfo.getUserName());
		session.setAttribute("LOGGED_IN_USERINFO", userInfo);
		setSecurityGroups(session, user);
		List<WfModel> models = modelService.canCreateCase(userInfo);
		// System.out.println(dsUser + " " + dsPassword);
		// System.out.println("Models List:" + models);
		boolean canShow = true;
		if (models.isEmpty()) {
			canShow = false;
		}
		
		if (canShow==true){
			model.addAttribute("canShowDownloadTemplate", true);
			model.addAttribute("canShowForm", true);
			model.addAttribute("canShowLastDA",true);
		}else{
			
			for(String s:userInfo.getRoles()){
				System.out.println("Roles >>>>>>>>>"+ s);
				if(s.equals("Third Party")){
					canShow=true;
					model.addAttribute("canShowDownloadTemplate", false);
					model.addAttribute("canShowForm", false);
					model.addAttribute("canShowLastDA",false);
				}else if(s.equals("Tenant")){
					model.addAttribute("isTenant", true);
				}
				if(s.equals("Builder")){
					model.addAttribute("isBuilder", true);
				}
		}
		}
		
		boolean canEnterAdmin = featureService.canEnterAdmin(userInfo);
		// For DSJV Hide Tree Control 17.07.13
		// DMNode root = workspaceSpider.fetchRoot(dsUser, dsPassword);
		// TreeBuilder builder = new TreeBuilder();
		// String treeHtml = builder.build(root);
		// model.addAttribute("treeContent", treeHtml);
		// For DSJV Hide Tree Control 17.07.13
		model.addAttribute("formDefinitions", formDefinitionLists(user));
		model.addAttribute("canShow", canShow);
		model.addAttribute("canShowAdminMain", canEnterAdmin);
		model.addAttribute("userId",user.getId());
		loadSearchAttr(model,session);
		loadFeatures(model,userInfo);
		refreshContext(session);
		/*lastDownloadedDocument(model,session);
		lastActionedDocument(model,session);*/
		return "dashboard";
	}
	
	private void loadFeatures(Model model,UserInfo userInfo){
		//For Feature Transaction Code Changes Start
				boolean canShowAdminAll = featureService.canDoAdminForFeature(Util.ADMIN_ALL, userInfo);
				boolean canShowAdminSearch=featureService.canDoAdminForFeature(Util.ADMIN_SEARCH,userInfo);
				if(canShowAdminAll==true){
					model.addAttribute("canShowAdminAll", canShowAdminAll);
				}
				else{
				model.addAttribute("canShowAdminSearch", canShowAdminSearch);
				}
				//End
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {

		if (session != null)
			session.invalidate();
		// AuthenticationUtils.endSession();
		return "auth/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("userName") String userName, @RequestParam("password") String password, Model model,
		HttpServletRequest req, HttpSession session) throws RepositoryFault, RemoteException {

		// For DSJV Hide Tree Control 17.07.13
		// String dsUser = (String) servletContext.getAttribute("dsUser");
		// String dsPassword = (String)
		// servletContext.getAttribute("dsPassword");
		// For DSJV Hide Tree Control 17.07.13
		// System.out.println("dsUser:" + dsUser + " " + "dsPass:" +
		// dsPassword);
		// System.out.println("dsUser:");
		// logger.info("User Info:Home Controller");
		
		User user = userService.checkLogin(userName, password);
		boolean thirdParty=false;
		if(user!=null){
			UserInfo uInfo=new UserInfo(user);
			for(String r:uInfo.getRoles()){
				if(r.equals("Third Party"))
				thirdParty=true;
			}
		}
		
		
			
		
		if (user == null) {
			model.addAttribute("message", "Incorrect Username or Password");
			return "auth/login";
		}
		try {
			// Set<String> emailId=new HashSet<String>();
			// emailId.add(user.getEmailId());
			// Mailer.doMail("Test", "Test Message",emailId);
			WebClient client = WebClient.detect(req);
			UserSession userSession = userSessionService.checkForValidSessionById(user.getId());
			if (userSession != null && !userSession.getSessionid().equals(session.getId())) {
				model.addAttribute("userId", user.getId());
				return Util.getMachine(client, userSession, req.getRemoteAddr(), model);
			}
			if (userSession == null) {
				userSession = new UserSession(user.getId(), session.getId(), req.getRemoteAddr(), client.getUserAgent().name(),
					client.getPlatform().name(), client.getFullVersion());
				userSessionService.saveUserSession(userSession);
				session.setAttribute("LOGGED_IN_USER", user);
			}
		}
		catch (HibernateException e) {
			logger.error("/login Error message "+e);
			model.addAttribute("message", "Invalid Login");
			return "auth/login";
		}
		// session.setAttribute("LOGGED_IN_USER", user);
		// System.out.println("User Email:" + user.getEmailId());
		
		model.addAttribute("username", userName);
		UserInfo userInfo = new UserInfo(user);
		// System.out.println("UserInfo->" + userInfo.getUserName());
		// System.out.println("UserInfo grouproles-> "+
		// userInfo.getGroupRoles());
		session.setAttribute("LOGGED_IN_USERINFO", userInfo);
		// 1988
		// List<SecurityGroup> sgs =
		// this.securityGroupService.findSecurityGroupsForUser(user);
		// List<String> securityGroups = new ArrayList<String>();
		// for(SecurityGroup sg: sgs){
		// securityGroups.add(sg.getName());
		// }
		// session.setAttribute("securityGroups", securityGroups);
		setSecurityGroups(session, user);
		List<WfModel> models = modelService.canCreateCase(userInfo);
		// System.out.println(dsUser + " " + dsPassword);
		// System.out.println("Models List:" + models);
		boolean canShow = true;
		if (models.isEmpty()) {
			canShow = false;
		}
		
		for(String s:userInfo.getRoles()){
			System.out.println("All Roles "+s.trim());
			if(s.equalsIgnoreCase("Builder")){
				System.out.println("In builder ");
				model.addAttribute("isBuilder", true);
			}
		}
		
		if (canShow==true){
			model.addAttribute("canShowDownloadTemplate", true);
			model.addAttribute("canShowForm", true);
			model.addAttribute("canShowLastDA",true);
		}else{
			for(String s:userInfo.getRoles()){
				if(s.equals("Third Party")){
					canShow=true;
					model.addAttribute("canShowDownloadTemplate", false);
					model.addAttribute("canShowForm", false);
					model.addAttribute("canShowLastDA",false);
				}else if(s.equals("Tenant")){
					model.addAttribute("isTenant", true);
				}
		}
		}
		
		
		// For Admin Feature
		boolean canEnterAdmin = featureService.canEnterAdmin(userInfo);
		// @SuppressWarnings("unchecked")
		// List<Feature> featureLists=(List<Feature>)
		// servletContext.getAttribute("adminFeatureLists");
		// String featureName=(String) servletContext.getAttribute("adminNone");
		// Feature
		// featureObj=featureService.findFeatureByFeatureName(featureName);
		// Set<Role> featureRoles=featureObj.getRoles();
		// Set<Role> userRole=user.getRoles();
		// for(Role roleUser : userRole){
		// for(Role roleFeature : featureRoles){
		// if(roleUser.getRoleName().equalsIgnoreCase(roleFeature.getRoleName())){
		// canShowAdminMain=false;
		// break;
		// }
		// }
		// }
		// For Admin Feature End
		// System.out.println(featureLists);
		// For DSJV Hide Tree Control 17.07.13
		// DMNode root = workspaceSpider.fetchRoot(dsUser, dsPassword);
		// TreeBuilder builder = new TreeBuilder();
		// String treeHtml = builder.build(root);
		// model.addAttribute("treeContent", treeHtml);
		// For DSJV Hide Tree Control 17.07.13
		model.addAttribute("formDefinitions", formDefinitionLists(user));
		model.addAttribute("canShow", canShow);
		// For Admin Feature
		model.addAttribute("canShowAdminMain", canEnterAdmin);
		model.addAttribute("userId",user.getId());
		loadSearchAttr(model,session);
		loadFeatures(model,userInfo);
		refreshContext(session);
		/*lastDownloadedDocument(model,session);
		lastActionedDocument(model,session);*/
		return "dashboard";
	}

	private void refreshContext(HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		servletContext.removeAttribute("disciplines");
		servletContext.removeAttribute("sites");
		List<Attribute> attributeList = dtAttributeService.findAllDtAttrs();
		//For Attr1 & Attr2
		Set<AttributeValue> attrivalueSetProject = Util.getAttributeBasedOnId(attributeList, 5);
		/*Set<AttributeValue> attriValueRestrictedSetForProject = Util.getActiveAttributeValues(attrivalueSetProject);
		if (attriValueRestrictedSetForProject != null) {
			servletContext.setAttribute("metadata1", attriValueRestrictedSetForProject);
		}*/
		
		List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
		
		Set<AttributeValue> attriValueRestrictedSetForProject = new HashSet<AttributeValue>();
		
		for(CompanyUser cu:cus){
			/*System.out.println(cu.getUser());
			System.out.println(cu.getAttrValue());*/
			attriValueRestrictedSetForProject.add(cu.getAttrValue());
		}
		
		if (attriValueRestrictedSetForProject != null) {
			servletContext.setAttribute("metadata1", attriValueRestrictedSetForProject);
		}
		
		Set<AttributeValue> attrivalueSetCategory = Util.getAttributeBasedOnId(attributeList, 1);
		Set<AttributeValue> attriValueRestrictedSetForCategory = Util.getActiveAttributeValues(attrivalueSetCategory);
		if (attriValueRestrictedSetForCategory != null) {
			servletContext.setAttribute("metadata2", attriValueRestrictedSetForCategory);
		}
		Set<AttributeValue> attrivalueSetDiscipline = Util.getAttributeBasedOnId(attributeList, 2);
		Set<AttributeValue> attriValueRestrictedSetForDiscipline = Util.getActiveAttributeValues(attrivalueSetDiscipline);
		if (attriValueRestrictedSetForDiscipline != null) {
			servletContext.setAttribute("metadata3", attriValueRestrictedSetForDiscipline);
		}
	/*	Set<AttributeValue> attrivalueSetSite = Util.getAttributeBasedOnId(attributeList, 3);
		Set<AttributeValue> attriValueRestrictedSetForSite = Util.getActiveAttributeValues(attrivalueSetSite);
		if (attriValueRestrictedSetForSite != null) {
			servletContext.setAttribute("metadata4", attriValueRestrictedSetForSite);
			// servletContext.setAttribute("sites", attriValueRestrictedSet);
		}*/
		servletContext.removeAttribute("doctypes");
		List<Doctype> docTypeLists = doctypeService.findAllDoctype();
		Collections.sort(docTypeLists);
		servletContext.setAttribute("doctypes", docTypeLists);
	}
	
	

	// private void addRolesToUser(UserService userService,RoleService
	// roleService){
	//
	// User user=userService.findUserByUserName("Karthik");
	// Role role=roleService.findRoleByRoleName("Owner");
	// long userId=user.getId();
	// long roleId=role.getId();
	// System.out.println("User Id:" +userId+" "+"Role Id:"+role.getId());
	// boolean isAssigned=userService.assignRole(userId, roleId);
	// System.out.println("Assigned:" +isAssigned);
	// }
	//
	//
	// private void createUser(UserService userService){
	// String userName="Karthik";
	// String passWord="123";
	// User newUser=new User(userName,passWord);
	// userService.saveUser(newUser);
	// }
	private void setSecurityGroups(HttpSession session, User user) {

		//System.out.println(user.toString());
		List<SecurityGroup> sgs = this.securityGroupService.findSecurityGroupsForUser(user);
		List<String> securityGroups = new ArrayList<String>();
		for (SecurityGroup sg : sgs) {
			securityGroups.add(sg.getName());
			//System.out.println(sg.getName());
		}
		session.setAttribute("securityGroups", securityGroups);
	}
	
	private void lastDownloadedDocument(Model model,HttpSession session){
		/*DocumentTrail docTrail=this.documentTrailService.findDocTrailByLastDownloadedDocument();
		
		if(docTrail!=null){
			Date lastDownloadTime=docTrail.getDate();
			Document lastDownloadedDocObj=docTrail.getDocument();
			long caseId=lastDownloadedDocObj.getCaseId();
			WfCase wfCase=caseService.findCaseById(caseId);
			model.addAttribute("lastDownloadedTime",lastDownloadTime);
			model.addAttribute("lastDownloadedDocument",lastDownloadedDocObj);
			model.addAttribute("workflowName",wfCase.getModel().getName());
		}*/
		//boolean company=false;
		
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		//Set<SecurityGroup> sgs=user.getSecGroups();
		List<DocumentTrail> docTrails=this.documentTrailService.findDocTrailByLastDownloadedDocument();
		
		//List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
		
		
			/*for(CompanyUser cu:cus){*/
			//	AttributeValue atv=	dtAttributeValueService.findDtAttrValueById(cu.getAttrValue().getId());
				/*Set<DocumentAttribute> das=docTrail.getDocument().getDocumentAttributes();
				for(DocumentAttribute da:das){
					if(da.getAttributeValue().getValue().equals(atv.getValue())){*/
						//company=true;
						supportForLastDownloadedDocument(docTrails,user, model);
						//break;
					/*}
				}
			}*/
		//}
		/*if(company){
		if(docTrails!=null){
			for(DocumentTrail docTrail:docTrails){
				
				}
			}
		}*/
	}
	
	
	public void supportForLastDownloadedDocument(List<DocumentTrail> docTrails,User user,Model model){
		/*for(SecurityGroup sg:sgs){*/
		for(DocumentTrail docTrail:docTrails){
			if(docTrail.getDocument().getAuthor().equals(user.getUserName())){
				Date lastDownloadTime=docTrail.getDate();
				Document lastDownloadedDocObj=docTrail.getDocument();
				long caseId=lastDownloadedDocObj.getCaseId();
				WfCase wfCase=caseService.findCaseById(caseId);
				model.addAttribute("lastDownloadedTime",lastDownloadTime);
				model.addAttribute("lastDownloadedDocument",lastDownloadedDocObj);
				model.addAttribute("workflowName",wfCase.getModel().getName());
				model.addAttribute("caseId", caseId);
				break;
					/*}
				}*/
			}
		}
	}
	
	private void lastActionedDocument(Model model,HttpSession session){
		/*WfStep wfStep=this.caseService.findStepForLastActionedCase();
		if(wfStep!=null){
			long caseId=wfStep.getOwningCase().getId();
			WfCase wfCase=caseService.findCaseById(caseId);
			Document document=documentService.findDocumentForCaseId(caseId);
			if(document!=null){
			model.addAttribute("lastActionedDocumentObj",document);
			model.addAttribute("lastActionedDocumentStep",wfStep);
			model.addAttribute("workflowName",wfCase.getModel().getName());
			if(document.getDiscriminator()=='D'){
				model.addAttribute("docType",document.getDoctype().getDoctypeName());
			}else if (document.getDiscriminator()=='F'){
				long userFId=Long.parseLong(document.getUserFormId());
				UserForms userForms = userFormsService.findUserFormsById(userFId);
	              if(userForms!=null){
				model.addAttribute("docType",userForms.getForms().getFormDefs().getName());
			}
			}			
		}
		}*/
		//boolean company=true;
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		//Set<SecurityGroup> sgs=user.getSecGroups();
		List<WfStep> wfSteps=this.caseService.findStepForLastActionedCase();
		supportForLastActioned(model, wfSteps,user);
		//List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
		
		
			/*for(CompanyUser cu:cus){*/
				//AttributeValue atv=	dtAttributeValueService.findDtAttrValueById(cu.getAttrValue().getId());
				
				
				//if(document.getDiscriminator()!='F'){
				//Set<DocumentAttribute> das=documentService.findDocumentForCaseId(wfStep.getOwningCase().getId()).getDocumentAttributes();
					
					//break;
				/*for(DocumentAttribute da:das){
					if(da.getAttributeValue().getValue().equals(atv.getValue())){
						//company=true;
						supportForLastActioned(model, wfStep, sgs);
						break;
						}
					}*/
				/*}else{
					UserForms uf=userFormsService.findUserFormsById(Long.parseLong(document.getUserFormId()));
					AttributeValue av=dtAttributeValueService.findDtAttrValueById(uf.getCompanyId());
					if(av.getValue().equals(atv.getValue())){
						//company=true;
						supportForLastActioned(model, wfStep, sgs);
						break;
					}
				}
					
				}*/
			//}
	//	}
		
		/*if(company){
		if(wfSteps!=null){
			for(WfStep wfStep:wfSteps){
				Document d=documentService.findDocumentForCaseId(wfStep.getOwningCase().getId());
				for(SecurityGroup sg:sgs){
					if(d.getSecurityGroup().getName().equals(sg.getName())){
						long caseId=wfStep.getOwningCase().getId();
						WfCase wfCase=caseService.findCaseById(caseId);
						Document document=documentService.findDocumentForCaseId(caseId);
						if(document!=null){
						model.addAttribute("lastActionedDocumentObj",document);
						model.addAttribute("lastActionedDocumentStep",wfStep);
						if(wfStep.getOwningCase().getModel().getName().equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
							model.addAttribute("actionedBy",document.getAuthor());
						}else{
							model.addAttribute("actionedBy",wfStep.getUserCompleted());
						}
						model.addAttribute("workflowName",wfCase.getModel().getName());
						if(document.getDiscriminator()=='D'){
							model.addAttribute("actionApplied", wfStep.getActionApplied().getName());
							model.addAttribute("docType",document.getDoctype().getDoctypeName());
						}else if (document.getDiscriminator()=='F'){
							if(wfStep.getActionApplied()==null){
							model.addAttribute("actionApplied", "Submit");
							}else{
								model.addAttribute("actionApplied", wfStep.getActionApplied().getName());
							}
							long userFId=Long.parseLong(document.getUserFormId());
							UserForms userForms = userFormsService.findUserFormsById(userFId);
				              if(userForms!=null){
							model.addAttribute("docType",userForms.getForms().getFormDefs().getName());
				              		}
								}			
							}
						}
					}
				}
			}
		}	*/
		
	}
	
	public void supportForLastActioned(Model model,List<WfStep> wfSteps,User user){
		/*Document d=documentService.findDocumentForCaseId(wfStep.getOwningCase().getId());
		for(SecurityGroup sg:sgs){
			if(d.getSecurityGroup().getName().equals(sg.getName())){*/
		for(WfStep wfStep:wfSteps){
			Document document=documentService.findDocumentForCaseId(wfStep.getOwningCase().getId());
				long caseId=wfStep.getOwningCase().getId();
				WfCase wfCase=caseService.findCaseById(caseId);
				//Document document=documentService.findDocumentForCaseId(caseId);
				if(document!=null){
					if(document.getAuthor().equalsIgnoreCase(user.getUserName())){
				model.addAttribute("lastActionedDocumentObj",document);
				model.addAttribute("lastActionedDocumentStep",wfStep);
				if(wfStep.getOwningCase().getModel().getName().equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
					model.addAttribute("actionedBy",document.getAuthor());
				}else{
					model.addAttribute("actionedBy",wfStep.getUserCompleted());
				}
				model.addAttribute("workflowName",wfCase.getModel().getName());
				if(document.getDiscriminator()=='D'){
					if(wfStep.getActionApplied()!=null){
					model.addAttribute("actionApplied", wfStep.getActionApplied().getName());
					}else{
						model.addAttribute("actionApplied", "Published");
					}
					model.addAttribute("docType",document.getDoctype().getDoctypeName());
				}else if (document.getDiscriminator()=='F'){
					if(wfStep.getActionApplied()==null){
					model.addAttribute("actionApplied", "Submit");
					}else{
						model.addAttribute("actionApplied", wfStep.getActionApplied().getName());
					}
					long userFId=Long.parseLong(document.getUserFormId());
					UserForms userForms = userFormsService.findUserFormsById(userFId);
		              if(userForms!=null){
					model.addAttribute("docType",userForms.getForms().getFormDefs().getName());
		              		}
		             
						}	
				break;
				}
				}
		}
				/*}
				
				}
			
			}*/
	}

	@RequestMapping(value = "/getTray", method = RequestMethod.GET)
	public String getTray(HttpSession session, Model model) throws RepositoryFault, RemoteException {

		// System.out.println("In getTray");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		// System.out.println("User Info getTray:" + userInfo.getUserName());
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		// System.out.println("tasks-> " + tasks.toString());
		/*List<TaskSummary> task = caseService.findTrayLabelCountForUser(userInfo);*/
		
		model.addAttribute("tasks", tasks);
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		HashMap<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		model.addAttribute("draftTray", stat);
		return "showTray";
	}
	
	
	
	
	@RequestMapping(value="/getDownloadedDocument",method=RequestMethod.GET)
	public String getLastDownloadedDocument(HttpSession session,Model model){
		lastDownloadedDocument(model,session);
		return "lastDownloadedDocument";
	}
	
	@RequestMapping(value="/getActionedDocument",method=RequestMethod.GET)
	public String getLastActionedDocument(HttpSession session,Model model){
		lastActionedDocument(model,session);
		return "lastActionedDocument";
	}
	

	@RequestMapping(value = "/logout1", method = RequestMethod.POST)
	public String logout(long userId, HttpServletRequest req, HttpSession session, Model model) throws RepositoryFault,
		RemoteException {

		WebClient client = WebClient.detect(req);
		User user = userService.findUserById(userId);
		UserSession userSession = new UserSession(userId, session.getId(), req.getRemoteAddr(), client.getUserAgent().name(),
			client.getPlatform().name(), client.getFullVersion());
		userSessionService.saveOrUpdateUserSession(userSession);
		session.setAttribute("LOGGED_IN_USER", user);
		return login(model, session);
	}

	@RequestMapping(value = "/changepwd", method = RequestMethod.GET)
	public String changePassword(HttpSession session, Model model) {

		// System.out.println("In Change Pwd Screen");
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		model.addAttribute("username", user.getUserName());
		return "changePassword";
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public String changePasswordLogin(Model model) {

		// System.out.println("Inside");
		// model.addAttribute("username","Test User");
		return "changePassword";
	}

	@RequestMapping(value = "/forgotoldpassword", method = RequestMethod.GET)
	public String forgotOldPasswordLogin(Model model) {

		// System.out.println("Inside");
		// model.addAttribute("username","Test User");
		return "resetPassword";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String forgotPasswordLogin(Model model) {

		List<User> userLists = userService.findAllUsers();
		model.addAttribute("userLists", userLists);
		return "forgotPassword";
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/requestPassword", method = RequestMethod.POST)
	@ResponseBody
	public String RequestPassword(@RequestParam("userName") String userName, @RequestParam("emailId") String emailId, Model model) {

		int result = 0;
		User user = userService.checkEmailForUser(userName, emailId);
		try {
			if (userName.isEmpty() || emailId.isEmpty()) {
				model.addAttribute("message", "All fiels are mandatory");
				model.addAttribute("username", user.getUserName());
				return "forgotPassword";
			}
			if (user == null) {
				model.addAttribute("message", "Please enter the correct username and email id");
				result = 3;
				return "forgotPassword";
			}
			String message = "User Name: " + user.getUserName() + "\nPassword: " + user.getPassword();
			// Mailer mailer = new Mailer();
			// mailer.doEmail("Password Recovery Mail", message, emailId);
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(emailId);
			email.setSubject("Password Recovery Mail");
			email.setText(message);
			mailSender.send(email);
			model.addAttribute("message", "Password sent successfully");
			result = 1;
		}
		catch (Exception ex) {
			logger.error("/requestPassword Error message "+ex);
			// System.out.println("Exception:");
			result = 0;
		}
		finally {
			String html = "<script language='javascript' type='text/javascript'> window.top.window.forgotPasswordCompleted('"
				+ result + "');</script>";
			return html;
		}
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public String UpdatePassword(@RequestParam("userName") String userName, @RequestParam("oldPassword") String oldPassword,
		@RequestParam("password") String passWord, @RequestParam("confirmPassword") String confirmPassword, Model model,
		HttpSession session) {

		// User user = (User) session.getAttribute("LOGGED_IN_USER");
		int result = 0;
		User user = userService.checkLogin(userName, oldPassword);
		try {
			if (userName.isEmpty() || oldPassword.isEmpty() || passWord.isEmpty() || confirmPassword.isEmpty()) {
				model.addAttribute("message", "All fiels are mandatory");
				model.addAttribute("username", user.getUserName());
				return "changePassword";
			}
			if (user == null) {
				model.addAttribute("message", "Please enter the correct Username and Password");
				result = 3;
				return "changePassword";
			}
			if (user.getPassword().equals(oldPassword)) {
				// System.out.println("In Change PWD");
				if (passWord.equals(confirmPassword)) {
					user.changePassword(confirmPassword);
					userService.saveUser(user);
					model.addAttribute("message", "Password updated successfully");
					result = 1;
					// endSession(session,model);
				}
				else {
					model.addAttribute("message", "Confirm password mismatch");
					model.addAttribute("username", user.getUserName());
					// endSession(session,model);
					return "changePassword";
				}
			}
			else {
				model.addAttribute("message", "Old password entered was wrong");
				model.addAttribute("username", user.getUserName());
				result = 2;
				return "changePassword";
				// endSession(session,model);
			}
		}
		catch (Exception ex) {
			logger.error("/updatePassword Error message "+ex );
			//System.out.println("Exception:");
			result = 0;
		}
		finally {
			String html = "<script language='javascript' type='text/javascript'> window.top.window.changePasswordCompleted('"
				+ result + "');</script>";
			// endSession(session,model);
			return html;
		}
	}

	@RequestMapping(value = "/loadHomePage", method = RequestMethod.GET)
	public String homePage(Model model, HttpServletRequest req, HttpSession session) throws RepositoryFault, RemoteException {

		// String dsUser = (String) servletContext.getAttribute("dsUser");
		// String dsPassword = (String)
		// servletContext.getAttribute("dsPassword");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		setSecurityGroups(session, user);
		List<WfModel> models = modelService.canCreateCase(userInfo);
		// System.out.println(dsUser + " " + dsPassword);
		// System.out.println("Models List:" + models);
		boolean canShow = true;
		if (models.isEmpty()) {
			canShow = false;
		}
		
		if (canShow==true){
			model.addAttribute("canShowDownloadTemplate", true);
			model.addAttribute("canShowForm", true);
			model.addAttribute("canShowLastDA",true);
		}else{
			for(String s:userInfo.getRoles()){
				if(s.equals("Third Party")){
					canShow=true;
					model.addAttribute("canShowDownloadTemplate", false);
					model.addAttribute("canShowForm", false);
					model.addAttribute("canShowLastDA",false);
				}else if(s.equals("Tenant")){
					model.addAttribute("isTenant", true);
				}
			}
		}
		
		boolean canEnterAdmin = featureService.canEnterAdmin(userInfo);
		// For DSJV Hide Tree Control 17.07.13
		// DMNode root = workspaceSpider.fetchRoot(dsUser, dsPassword);
		// TreeBuilder builder = new TreeBuilder();
		// String treeHtml = builder.build(root);
		// model.addAttribute("treeContent", treeHtml);
		// For DSJV Hide Tree Control 17.07.13
		model.addAttribute("username", user.getUserName());
		model.addAttribute("formDefinitions", formDefinitionLists(user));
		model.addAttribute("canShow", canShow);
		model.addAttribute("canShowAdminMain", canEnterAdmin);
		model.addAttribute("userId",user.getId());
		loadSearchAttr(model,session);
		loadFeatures(model,userInfo);
		refreshContext(session);
		/*lastDownloadedDocument(model,session);
		lastActionedDocument(model,session);*/
		return "dashboard";
	}

	// @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	// public String UpdatePassword(
	// @RequestParam("oldPassword") String oldPassword,
	// @RequestParam("password") String passWord,
	// @RequestParam("confirmPassword") String confirmPassword,
	// Model model,
	// HttpSession session){
	// User user = (User) session.getAttribute("LOGGED_IN_USER");
	// try{
	//
	// if(oldPassword.isEmpty() || passWord.isEmpty() ||
	// confirmPassword.isEmpty()){
	// model.addAttribute("message","All fiels are mandatory");
	// return "changePassword";
	// }
	//
	// if(user.getPassword().equalsIgnoreCase(oldPassword)){
	//
	// if(passWord.equalsIgnoreCase(confirmPassword)){
	// user.changePassword(confirmPassword);
	// userService.saveUser(user);
	// model.addAttribute("message","Password updated successfully");
	// endSession(session,model);
	// }
	// else
	// {
	// model.addAttribute("message","Confirm password mismatch");
	// return "changePassword";
	// }
	//
	// }
	//
	// else{
	// model.addAttribute("message","Old password entered was wrong");
	// return "changePassword";
	// }
	// }
	//
	// catch (Exception ex) {
	// System.out.println("Exception:");
	// }
	//
	// return "auth/login";
	// }
	public void endSession(HttpSession session, Model model) {

		if (session != null)
			session.invalidate();
	}

	// Forms Module
	// Getting Form Definitions
	private List<FormDefinition> formDefinitionLists(User user) {
		
		List<FormDefinition> formLists =new ArrayList<FormDefinition>();
		HashMap<String,FormDefinition> formMaps =new HashMap<String,FormDefinition>();
		List<FormDefs> formDefs = formDefsDao.findAllFormDefs();
		List<CompanyUser> companyUsers=companyUserService.findCompanyUsersForUserId(user);
		
		Iterator<FormDefs> fdi = formDefs.iterator();
		while (fdi.hasNext()) {
			FormDefs fd = fdi.next();
			List<FormCompanyGroup> fcgs=formCompanyGroupService.findFormCompanyGroupForFormDef(fd);
			for(CompanyUser cu:companyUsers){
				for(FormCompanyGroup fcg:fcgs){
					if(cu.getAttrValue().getValue().equals(fcg.getAttributeValue().getValue())){
						FormDefinition formDef = new FormDefinition(fd.getId(), fd.getName(), fd.getDescription(), fd.getActive());
						formMaps.put(formDef.getName(), formDef);
						//formLists.add(formDef);
					}
				}
			}
		}
		
		Iterator<String> it1 = formMaps.keySet().iterator();
		
		/* for(String s:formMaps.keySet()){
	            System.out.println("Map Value:"+s);
	            }*/
		
        while(it1.hasNext()){
            String key = it1.next();
           formLists.add(formMaps.get(key));
        }
		
		return formLists;
	}
}
