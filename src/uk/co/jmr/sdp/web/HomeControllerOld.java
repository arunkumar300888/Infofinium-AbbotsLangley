/*
 * package uk.co.jmr.sdp.web;
 * 
 * import java.io.IOException; import java.io.InputStream; import
 * java.rmi.RemoteException; import java.util.ArrayList; import
 * java.util.Collections; import java.util.HashSet; import java.util.List;
 * import java.util.Properties; import java.util.Set;
 * 
 * import javax.servlet.ServletContext; import
 * javax.servlet.http.HttpServletRequest; import javax.servlet.http.HttpSession;
 * 
 * import org.alfresco.webservice.repository.RepositoryFault; import
 * org.alfresco.webservice.util.AuthenticationUtils; import
 * org.hibernate.HibernateException; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.ResponseBody;
 * 
 * import uk.co.jmr.sdp.domain.Doctype; import uk.co.jmr.sdp.domain.Role; import
 * uk.co.jmr.sdp.domain.SecurityGroup; import uk.co.jmr.sdp.domain.User; import
 * uk.co.jmr.sdp.domain.UserSession; import uk.co.jmr.sdp.domain.ds.DMNode;
 * import uk.co.jmr.sdp.domain.dt.Attribute; import
 * uk.co.jmr.sdp.domain.dt.AttributeValue; import
 * uk.co.jmr.sdp.domain.forms.FormDefinition; import
 * uk.co.jmr.sdp.ds.WorkspaceSpider; import
 * uk.co.jmr.sdp.service.DisciplineService; import
 * uk.co.jmr.sdp.service.DoctypeService; import
 * uk.co.jmr.sdp.service.DtAttributeService; import
 * uk.co.jmr.sdp.service.FeatureService; import
 * uk.co.jmr.sdp.service.FormService; import
 * uk.co.jmr.sdp.service.SecurityGroupService; import
 * uk.co.jmr.sdp.service.UserService; import
 * uk.co.jmr.sdp.service.UserSessionService; import
 * uk.co.jmr.sdp.web.util.TreeBuilder; import uk.co.jmr.sdp.web.util.UserInfo;
 * import uk.co.jmr.sdp.web.util.Util;
 * 
 * import com.ardhika.wfar.TaskSummary; import com.ardhika.wfar.WfModel; import
 * com.ardhika.wfar.service.CaseService; import
 * com.ardhika.wfar.service.ModelService; import com.ardhika.wfar.util.Mailer;
 * import com.visural.common.web.client.WebClient;
 * 
 * @Controller public class HomeController {
 * 
 * @Autowired private ServletContext servletContext;
 * 
 * @Autowired private UserService userService;
 * 
 * @Autowired private DisciplineService disciplineService;
 * 
 * @Autowired private DoctypeService doctypeService;
 * 
 * @Autowired private WorkspaceSpider workspaceSpider;
 * 
 * @Autowired private ModelService modelService;
 * 
 * @Autowired private CaseService caseService;
 * 
 * @Autowired private DtAttributeService dtAttributeService;
 * 
 * @Autowired private SecurityGroupService securityGroupService;
 * 
 * @Autowired private UserSessionService userSessionService;
 * 
 * @Autowired private FeatureService featureService;
 * 
 * @Autowired private FormService formService;
 * 
 * 
 * //private static final Logger logger =
 * Logger.getLogger(HomeController.class);
 * 
 * @RequestMapping(value = "/welcome/{id}", method = RequestMethod.GET) public
 * @ResponseBody String greet(@PathVariable("id") long id) { User user =
 * userService.findUserById(id); String msg = (user != null) ? "Hello " +
 * user.getUserName() + " !" : "User not found!"; return msg; }
 * 
 * @RequestMapping(value = "/", method = RequestMethod.GET) public String
 * rootLogin() throws IOException {
 * 
 * if (servletContext.getAttribute("disciplines") == null ||
 * servletContext.getAttribute("sites") == null) {
 * 
 * List<Attribute> attributeList = dtAttributeService.findAllDtAttrs();
 * 
 * if (servletContext.getAttribute("disciplines") == null) {
 * 
 * Set<AttributeValue> attrivalueSet = Util.getAttributeBasedOnId(
 * attributeList, 3);
 * 
 * if (attrivalueSet != null) {
 * 
 * servletContext.setAttribute("disciplines", attrivalueSet); } }
 * 
 * if (servletContext.getAttribute("sites") == null) {
 * 
 * Set<AttributeValue> attrivalueSet = Util.getAttributeBasedOnId(
 * attributeList, 4);
 * 
 * if (attrivalueSet != null) {
 * 
 * servletContext.setAttribute("sites", attrivalueSet); }
 * 
 * } }
 * 
 * if (servletContext.getAttribute("doctypes") == null) {
 * 
 * List<Doctype> docTypeLists = doctypeService.findAllDoctype();
 * Collections.sort(docTypeLists);
 * 
 * servletContext.setAttribute("doctypes", docTypeLists); } Properties props =
 * new Properties(); InputStream is = this.getClass().getResourceAsStream(
 * "/webserviceclient.properties"); props.load(is);
 * servletContext.setAttribute("dsUser", props.getProperty("userName"));
 * servletContext .setAttribute("dsPassword", props.getProperty("password"));
 * servletContext.setAttribute("filePath", props.getProperty("filePath"));
 * servletContext.setAttribute("foldername", props.getProperty("foldername"));
 * 
 * // For Quick Upload on 04.01.13
 * 
 * 
 * servletContext.setAttribute("filePath.quickupload",
 * props.getProperty("filePath.quickupload"));
 * servletContext.setAttribute("foldername.quickupload",
 * props.getProperty("foldername.quickupload"));
 * 
 * 
 * servletContext.setAttribute("filePath.photos",
 * props.getProperty("filePath.photos"));
 * servletContext.setAttribute("foldername.photos",
 * props.getProperty("foldername.photos"));
 * servletContext.setAttribute("filePath.videos",
 * props.getProperty("filePath.videos"));
 * servletContext.setAttribute("foldername.videos",
 * props.getProperty("foldername.videos"));
 * servletContext.setAttribute("filePath.meetings",
 * props.getProperty("filePath.meetings"));
 * servletContext.setAttribute("foldername.meetings",
 * props.getProperty("foldername.meetings"));
 * 
 * servletContext.setAttribute("filePath.template",
 * props.getProperty("filePath.template"));
 * servletContext.setAttribute("foldername.template",
 * props.getProperty("foldername.template"));
 * 
 * servletContext.setAttribute("restServiceUrl",
 * props.getProperty("repository.restservice"));
 * 
 * //Web Services URL For CMIS Start
 * servletContext.setAttribute("restServiceWsdlUrl",
 * props.getProperty("repoServiceUrl"));
 * servletContext.setAttribute("navigationServiceWsdlUrl",
 * props.getProperty("navigationServiceUrl"));
 * servletContext.setAttribute("objectServiceWsdlUrl",
 * props.getProperty("objectServiceUrl"));
 * servletContext.setAttribute("versioningServiceWsdlUrl",
 * props.getProperty("versioningServiceUrl"));
 * servletContext.setAttribute("discoveryServiceWsdlUrl",
 * props.getProperty("discoveryServiceUrl"));
 * servletContext.setAttribute("multifilingServiceWsdlUrl",
 * props.getProperty("multifilingServiceUrl"));
 * servletContext.setAttribute("relationshipServiceWsdlUrl",
 * props.getProperty("relationshipServiceUrl"));
 * servletContext.setAttribute("aclServiceWsdlUrl",
 * props.getProperty("aclServiceUrl"));
 * servletContext.setAttribute("policyServiceWsdlUrl",
 * props.getProperty("policyServiceUrl"));
 * 
 * // Web Services URL For CMIS End
 * 
 * //For Admin Feature Start
 * servletContext.setAttribute("adminFeatureLists",featureService
 * .listAllFeatures());
 * servletContext.setAttribute("adminNone",props.getProperty
 * ("adminSecurityNone")); //Admin Feature End
 * 
 * 
 * 
 * loadSysConfigProperties(); // System.out.println("HOME: USerName-> " +
 * props.getProperty("userName")); // System.out.println("HOME: password-> " +
 * props.getProperty("password")); return "auth/login"; }
 * 
 * 
 * private void loadSysConfigProperties() throws IOException { Properties props
 * = new Properties(); InputStream is =
 * this.getClass().getResourceAsStream("/sysconfig.properties"); props.load(is);
 * servletContext.setAttribute("sgAttrs",
 * props.getProperty("attributesForSecurityGroup"));
 * servletContext.setAttribute("revision", props.getProperty("revision")); }
 * 
 * 
 * private void loadSearchAttr(Model model){ Attribute attr3 =
 * dtAttributeService.findAttributeByOrder(3); Attribute attr4 =
 * dtAttributeService.findAttributeByOrder(4); model.addAttribute("attr3",
 * attr3.getName()); model.addAttribute("attr4", attr4.getName()); }
 * 
 * @RequestMapping(value = "/authenticate", method = RequestMethod.GET) public
 * String login( Model model, HttpSession session) throws RepositoryFault,
 * RemoteException {
 * 
 * //For DSJV Hide Tree Control 17.07.13 //String dsUser = (String)
 * servletContext.getAttribute("dsUser"); //String dsPassword = (String)
 * servletContext.getAttribute("dsPassword"); //For DSJV Hide Tree Control
 * 17.07.13
 * 
 * // System.out.println("dsUser:" + dsUser + " " + "dsPass:" + dsPassword);
 * User user = (User) session.getAttribute("LOGGED_IN_USER");
 * 
 * //System.out.println("User Email:" + user.getEmailId());
 * model.addAttribute("username", user.getUserName()); Set<Role> rs =
 * user.getRoles(); Set<String> roles = new HashSet<String>(); for (Role r : rs)
 * { roles.add(r.getRoleName()); } // System.out.println("roles->" + roles);
 * UserInfo userInfo = new UserInfo(user); //System.out.println("UserInfo->" +
 * userInfo.getUserName()); session.setAttribute("LOGGED_IN_USERINFO",
 * userInfo);
 * 
 * 
 * setSecurityGroups(session, user);
 * 
 * List<WfModel> models = modelService.canCreateCase(userInfo); //
 * System.out.println(dsUser + " " + dsPassword); //
 * System.out.println("Models List:" + models); boolean canShow = true; if
 * (models.isEmpty()) { canShow = false; }
 * 
 * boolean canEnterAdmin=featureService.canEnterAdmin(userInfo);
 * 
 * //For DSJV Hide Tree Control 17.07.13 // DMNode root =
 * workspaceSpider.fetchRoot(dsUser, dsPassword); // TreeBuilder builder = new
 * TreeBuilder(); // String treeHtml = builder.build(root); //
 * model.addAttribute("treeContent", treeHtml); //For DSJV Hide Tree Control
 * 17.07.13 model.addAttribute("formDefinitions", formDefinitionLists());
 * model.addAttribute("canShow", canShow);
 * model.addAttribute("canShowAdminMain", canEnterAdmin); loadSearchAttr(model);
 * refreshContext(); return "dashboard"; }
 * 
 * @RequestMapping(value = "/logout", method = RequestMethod.GET) public String
 * logout(HttpSession session) { if (session != null) session.invalidate();
 * //AuthenticationUtils.endSession(); return "auth/login"; }
 * 
 * @RequestMapping(value = "/login", method = RequestMethod.POST) public String
 * login(@RequestParam("userName") String userName,
 * 
 * @RequestParam("password") String password, Model model, HttpServletRequest
 * req,HttpSession session) throws RepositoryFault, RemoteException { //For DSJV
 * Hide Tree Control 17.07.13 //String dsUser = (String)
 * servletContext.getAttribute("dsUser"); //String dsPassword = (String)
 * servletContext.getAttribute("dsPassword"); //For DSJV Hide Tree Control
 * 17.07.13
 * 
 * // System.out.println("dsUser:" + dsUser + " " + "dsPass:" + dsPassword);
 * //System.out.println("dsUser:"); //logger.info("User Info:Home Controller");
 * 
 * User user = userService.checkLogin(userName, password); if (user == null) {
 * model.addAttribute("message",
 * "Please enter the correct Username and Password"); return "auth/login"; } try
 * { // Set<String> emailId=new HashSet<String>(); //
 * emailId.add(user.getEmailId()); // Mailer.doMail("Test",
 * "Test Message",emailId);
 * 
 * WebClient client = WebClient.detect(req); UserSession userSession =
 * userSessionService .checkForValidSessionById(user.getId()); if (userSession
 * != null && !userSession.getSessionid().equals(session.getId())) {
 * model.addAttribute("userId", user.getId()); return Util.getMachine(client,
 * userSession, req.getRemoteAddr(), model);
 * 
 * }
 * 
 * if (userSession == null) { userSession = new UserSession(user.getId(),
 * session.getId(), req.getRemoteAddr(), client.getUserAgent().name(),
 * client.getPlatform().name(), client.getFullVersion());
 * 
 * userSessionService.saveUserSession(userSession);
 * session.setAttribute("LOGGED_IN_USER", user); } }
 * 
 * catch(HibernateException e) { model.addAttribute("message", "Invalid Login");
 * return "auth/login";
 * 
 * }
 * 
 * 
 * // session.setAttribute("LOGGED_IN_USER", user);
 * //System.out.println("User Email:" + user.getEmailId());
 * model.addAttribute("username", userName); UserInfo userInfo = new
 * UserInfo(user); //System.out.println("UserInfo->" + userInfo.getUserName());
 * //System.out.println("UserInfo grouproles-> "+ userInfo.getGroupRoles());
 * session.setAttribute("LOGGED_IN_USERINFO", userInfo);
 * 
 * // 1988
 * 
 * // List<SecurityGroup> sgs =
 * this.securityGroupService.findSecurityGroupsForUser(user); // List<String>
 * securityGroups = new ArrayList<String>(); // for(SecurityGroup sg: sgs){ //
 * securityGroups.add(sg.getName()); // } //
 * session.setAttribute("securityGroups", securityGroups);
 * setSecurityGroups(session, user); List<WfModel> models =
 * modelService.canCreateCase(userInfo); //System.out.println(dsUser + " " +
 * dsPassword); //System.out.println("Models List:" + models); boolean canShow =
 * true; if (models.isEmpty()) { canShow = false; }
 * 
 * //For Admin Feature boolean
 * canEnterAdmin=featureService.canEnterAdmin(userInfo);
 * 
 * // @SuppressWarnings("unchecked") // List<Feature>
 * featureLists=(List<Feature>)
 * servletContext.getAttribute("adminFeatureLists"); // String
 * featureName=(String) servletContext.getAttribute("adminNone"); // Feature
 * featureObj=featureService.findFeatureByFeatureName(featureName); // Set<Role>
 * featureRoles=featureObj.getRoles(); // Set<Role> userRole=user.getRoles(); //
 * for(Role roleUser : userRole){ // for(Role roleFeature : featureRoles){ //
 * if(roleUser.getRoleName().equalsIgnoreCase(roleFeature.getRoleName())){ //
 * canShowAdminMain=false; // break; // } // } // } //For Admin Feature End
 * //System.out.println(featureLists);
 * 
 * //For DSJV Hide Tree Control 17.07.13 // DMNode root =
 * workspaceSpider.fetchRoot(dsUser, dsPassword); // TreeBuilder builder = new
 * TreeBuilder(); // String treeHtml = builder.build(root); //
 * model.addAttribute("treeContent", treeHtml); //For DSJV Hide Tree Control
 * 17.07.13
 * 
 * 
 * model.addAttribute("formDefinitions", formDefinitionLists());
 * model.addAttribute("canShow", canShow); //For Admin Feature
 * model.addAttribute("canShowAdminMain", canEnterAdmin); loadSearchAttr(model);
 * refreshContext(); return "dashboard"; }
 * 
 * 
 * 
 * private void refreshContext(){ servletContext.removeAttribute("disciplines");
 * servletContext.removeAttribute("sites"); List<Attribute> attributeList =
 * dtAttributeService.findAllDtAttrs(); Set<AttributeValue>
 * attrivalueSetDiscipline = Util.getAttributeBasedOnId( attributeList, 3);
 * Set<AttributeValue>
 * attriValueRestrictedSetForDiscipline=Util.getActiveAttributeValues
 * (attrivalueSetDiscipline); if (attriValueRestrictedSetForDiscipline != null)
 * { servletContext.setAttribute("disciplines",
 * attriValueRestrictedSetForDiscipline); } Set<AttributeValue>
 * attrivalueSetSite = Util.getAttributeBasedOnId( attributeList, 4);
 * Set<AttributeValue>
 * attriValueRestrictedSetForSite=Util.getActiveAttributeValues
 * (attrivalueSetSite); if (attriValueRestrictedSetForSite != null) {
 * servletContext.setAttribute("sites", attriValueRestrictedSetForSite);
 * //servletContext.setAttribute("sites", attriValueRestrictedSet); }
 * 
 * servletContext.removeAttribute("doctypes"); List<Doctype> docTypeLists =
 * doctypeService.findAllDoctype(); Collections.sort(docTypeLists);
 * servletContext.setAttribute("doctypes", docTypeLists);
 * 
 * }
 * 
 * // private void addRolesToUser(UserService userService,RoleService
 * roleService){ // // User user=userService.findUserByUserName("Karthik"); //
 * Role role=roleService.findRoleByRoleName("Owner"); // long
 * userId=user.getId(); // long roleId=role.getId(); //
 * System.out.println("User Id:" +userId+" "+"Role Id:"+role.getId()); //
 * boolean isAssigned=userService.assignRole(userId, roleId); //
 * System.out.println("Assigned:" +isAssigned); // } // // // private void
 * createUser(UserService userService){ // String userName="Karthik"; // String
 * passWord="123"; // User newUser=new User(userName,passWord); //
 * userService.saveUser(newUser); // }
 * 
 * private void setSecurityGroups(HttpSession session,User user) {
 * 
 * List<SecurityGroup> sgs =
 * this.securityGroupService.findSecurityGroupsForUser(user); List<String>
 * securityGroups = new ArrayList<String>(); for(SecurityGroup sg: sgs){
 * securityGroups.add(sg.getName()); } session.setAttribute("securityGroups",
 * securityGroups); }
 * 
 * 
 * @RequestMapping(value = "/getTray", method = RequestMethod.GET) public String
 * getTray(HttpSession session, Model model) throws RepositoryFault,
 * RemoteException { //System.out.println("In getTray"); UserInfo userInfo =
 * (UserInfo) session .getAttribute("LOGGED_IN_USERINFO");
 * //System.out.println("User Info getTray:" + userInfo.getUserName());
 * List<TaskSummary> tasks = caseService .findTrayLabelSummaryForUser(userInfo);
 * 
 * //System.out.println("tasks-> " + tasks.toString());
 * model.addAttribute("tasks", tasks); return "showTray"; }
 * 
 * 
 * @RequestMapping(value = "/logout1", method = RequestMethod.POST) public
 * String logout(long userId, HttpServletRequest req, HttpSession session, Model
 * model) throws RepositoryFault, RemoteException {
 * 
 * WebClient client = WebClient.detect(req);
 * 
 * User user = userService.findUserById(userId);
 * 
 * UserSession userSession = new UserSession(userId, session.getId(),
 * req.getRemoteAddr(), client.getUserAgent().name(), client
 * .getPlatform().name(), client.getFullVersion());
 * 
 * userSessionService.saveOrUpdateUserSession(userSession);
 * 
 * session.setAttribute("LOGGED_IN_USER", user);
 * 
 * return login(model, session); }
 * 
 * 
 * 
 * @RequestMapping(value = "/changepwd", method = RequestMethod.GET) public
 * String changePassword(HttpSession session,Model model) {
 * //System.out.println("In Change Pwd Screen"); User user = (User)
 * session.getAttribute("LOGGED_IN_USER"); model.addAttribute("username",
 * user.getUserName()); return "changePassword"; }
 * 
 * @RequestMapping(value = "/changepassword", method = RequestMethod.GET) public
 * String changePasswordLogin(Model model) { // System.out.println("Inside"); //
 * model.addAttribute("username","Test User"); return "changePassword"; }
 * 
 * 
 * 
 * @SuppressWarnings("finally")
 * 
 * @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
 * 
 * @ResponseBody public String UpdatePassword(
 * 
 * @RequestParam("userName") String userName,
 * 
 * @RequestParam("oldPassword") String oldPassword,
 * 
 * @RequestParam("password") String passWord,
 * 
 * @RequestParam("confirmPassword") String confirmPassword, Model model,
 * HttpSession session){ // User user = (User)
 * session.getAttribute("LOGGED_IN_USER"); int result = 0; User user =
 * userService.checkLogin(userName, oldPassword);
 * 
 * try{
 * 
 * if(userName.isEmpty() || oldPassword.isEmpty() || passWord.isEmpty() ||
 * confirmPassword.isEmpty()){
 * model.addAttribute("message","All fiels are mandatory");
 * model.addAttribute("username", user.getUserName()); return "changePassword";
 * }
 * 
 * if (user == null) { model.addAttribute("message",
 * "Please enter the correct Username and Password"); result=3; return
 * "changePassword"; }
 * 
 * 
 * if(user.getPassword().equals(oldPassword)){
 * 
 * //System.out.println("In Change PWD"); if(passWord.equals(confirmPassword)){
 * user.changePassword(confirmPassword); userService.saveUser(user);
 * model.addAttribute("message","Password updated successfully"); result=1;
 * 
 * // endSession(session,model); } else {
 * model.addAttribute("message","Confirm password mismatch");
 * model.addAttribute("username", user.getUserName()); //
 * endSession(session,model); return "changePassword"; }
 * 
 * }
 * 
 * else{ model.addAttribute("message","Old password entered was wrong");
 * model.addAttribute("username", user.getUserName()); result=2; return
 * "changePassword"; //endSession(session,model); } }
 * 
 * catch (Exception ex) { System.out.println("Exception:"); result=0; }
 * 
 * finally {
 * 
 * 
 * String html =
 * "<script language='javascript' type='text/javascript'> window.top.window.changePasswordCompleted('"
 * +result+"');</script>"; // endSession(session,model); return html; } }
 * 
 * @RequestMapping(value = "/loadHomePage", method = RequestMethod.GET) public
 * String homePage(Model model, HttpServletRequest req,HttpSession session)
 * throws RepositoryFault, RemoteException { // String dsUser = (String)
 * servletContext.getAttribute("dsUser"); // String dsPassword = (String)
 * servletContext.getAttribute("dsPassword"); UserInfo userInfo = (UserInfo)
 * session .getAttribute("LOGGED_IN_USERINFO"); User
 * user=(User)session.getAttribute("LOGGED_IN_USER"); setSecurityGroups(session,
 * user); List<WfModel> models = modelService.canCreateCase(userInfo);
 * //System.out.println(dsUser + " " + dsPassword);
 * //System.out.println("Models List:" + models); boolean canShow = true; if
 * (models.isEmpty()) { canShow = false; } boolean
 * canEnterAdmin=featureService.canEnterAdmin(userInfo); //For DSJV Hide Tree
 * Control 17.07.13 // DMNode root = workspaceSpider.fetchRoot(dsUser,
 * dsPassword); // TreeBuilder builder = new TreeBuilder(); // String treeHtml =
 * builder.build(root); // model.addAttribute("treeContent", treeHtml); //For
 * DSJV Hide Tree Control 17.07.13 model.addAttribute("username",
 * user.getUserName()); model.addAttribute("formDefinitions",
 * formDefinitionLists());
 * 
 * model.addAttribute("canShow", canShow);
 * model.addAttribute("canShowAdminMain", canEnterAdmin); loadSearchAttr(model);
 * refreshContext(); return "dashboard"; }
 * 
 * // @RequestMapping(value = "/updatePassword", method = RequestMethod.POST) //
 * public String UpdatePassword( // @RequestParam("oldPassword") String
 * oldPassword, // @RequestParam("password") String passWord, //
 * @RequestParam("confirmPassword") String confirmPassword, // Model model, //
 * HttpSession session){ // User user = (User)
 * session.getAttribute("LOGGED_IN_USER"); // try{ // //
 * if(oldPassword.isEmpty() || passWord.isEmpty() || confirmPassword.isEmpty()){
 * // model.addAttribute("message","All fiels are mandatory"); // return
 * "changePassword"; // } // //
 * if(user.getPassword().equalsIgnoreCase(oldPassword)){ // //
 * if(passWord.equalsIgnoreCase(confirmPassword)){ //
 * user.changePassword(confirmPassword); // userService.saveUser(user); //
 * model.addAttribute("message","Password updated successfully"); //
 * endSession(session,model); // } // else // { //
 * model.addAttribute("message","Confirm password mismatch"); // return
 * "changePassword"; // } // // } // // else{ //
 * model.addAttribute("message","Old password entered was wrong"); // return
 * "changePassword"; // } // } // // catch (Exception ex) { //
 * System.out.println("Exception:"); // } // // return "auth/login"; // }
 * 
 * public void endSession(HttpSession session,Model model) { if (session !=
 * null) session.invalidate();
 * 
 * }
 * 
 * //Forms Module
 * 
 * //Getting Form Definitions private List<FormDefinition>
 * formDefinitionLists(){ List<FormDefinition>
 * formLists=formService.formDefinitions(); return formLists; }
 * 
 * 
 * }
 */