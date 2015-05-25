package uk.co.jmr.sdp.web;


import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alfresco.webservice.repository.RepositoryFault;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.ardhika.wfar.TaskSummary;

import com.ardhika.wfar.WfCase;

import com.ardhika.wfar.WfAction;
import com.ardhika.wfar.WfAttribute;
import com.ardhika.wfar.WfAttributeType;
import com.ardhika.wfar.WfCaseStatus;
import com.ardhika.wfar.WfModel;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.service.CaseService;

import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.DocumentReference;
import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.domain.GroupRole;
import uk.co.jmr.sdp.domain.ReviewNote;
import uk.co.jmr.sdp.domain.Role;

import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.FeatureService;
import uk.co.jmr.sdp.service.GroupRoleService;
import uk.co.jmr.sdp.service.GroupService;
import uk.co.jmr.sdp.service.ReviewNoteService;
import uk.co.jmr.sdp.service.RoleService;
import uk.co.jmr.sdp.service.UserService;


import uk.co.jmr.sdp.web.util.UserInfo;
import uk.co.jmr.sdp.web.util.Util;
import uk.co.jmr.webforms.db.pojo.UserForms;
import uk.co.jmr.webforms.db.service.UserFormsService;

@Controller
@RequestMapping(value = "/adminTemplate")
public class AdminTemplateController {
	@Autowired
	private FeatureService featureService;
	@Autowired
	private CaseService caseService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserFormsService userFormsService;
	@Autowired
	private ReviewNoteService reviewNoteService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupRoleService groupRoleService;
	@Autowired
	private DtAttributeService dtAttributeService;
	
	
	Logger logger=Logger.getLogger(AdminTemplateController.class);
	


	@RequestMapping(value = "/goAdminTemplate", method = RequestMethod.GET)
	public String showAdminTemplate(Model model, HttpSession session) {
		
		//DOMConfigurator.configure(getClass().getClassLoader().getResource("log4j.xml"));
		//System.out.println("Admin");
		logger.info("/goAdminTemplate Admin");
		// return "adminTemplate";
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		boolean canShowAdminAll = featureService.canDoAdminForFeature(Util.ADMIN_ALL, userInfo);
		boolean canShowAdminAttr = featureService.canDoAdminForFeature(Util.ADMIN_ATTR, userInfo);
		boolean canShowAdminAttrValue = featureService.canDoAdminForFeature(Util.ADMIN_AV, userInfo);
		boolean canShowAdminGroup = featureService.canDoAdminForFeature(Util.ADMIN_GROUP, userInfo);
		boolean canShowAdminRole = featureService.canDoAdminForFeature(Util.ADMIN_ROLE, userInfo);
		boolean canShowAdminTemplate = featureService.canDoAdminForFeature(Util.ADMIN_TM, userInfo);
		boolean canShowAdminUser = featureService.canDoAdminForFeature(Util.ADMIN_USER, userInfo);
		boolean canShowAdminWorkflow = featureService.canDoAdminForFeature(Util.ADMIN_WF, userInfo);
		boolean canShowAdminFeature = featureService.canDoAdminForFeature(Util.ADMIN_FEATURE, userInfo);
		boolean canShowAdminDocType = featureService.canDoAdminForFeature(Util.ADMIN_DT, userInfo);
		boolean canShowAdminSecurityGroup = featureService.canDoAdminForFeature(Util.ADMIN_SG, userInfo);
		boolean canShowAdminMyDocument=featureService.canDoAdminForFeature(Util.ADMIN_MY_DOCUMENT, userInfo);
		boolean canShowAdminFormSecurity=featureService.canDoAdminForFeature(Util.ADMIN_FORM_SECURITY, userInfo);
		// Workflow Adminsitration
		boolean canShowAdminDelete = featureService.canDoAdminForFeature(Util.ADMIN_DELETE, userInfo);
		boolean canShowAdminUnclaim = featureService.canDoAdminForFeature(Util.ADMIN_UNCLAIM, userInfo);
		boolean canShowAdminAssignTask = featureService.canDoAdminForFeature(Util.ADMIN_REASSIGN, userInfo);
		boolean canShowAdminReassignOwner = featureService.canDoAdminForFeature(Util.ADMIN_REASSIGN_OWNER, userInfo);
		String adminUserName = user.getUserName();
		model.addAttribute("title", adminUserName);
		if (canShowAdminAll == true) {
			model.addAttribute("canShowAdminAll", canShowAdminAll);
		}
		else {
			model.addAttribute("canShowAdminAll", canShowAdminAll);
			model.addAttribute("canShowAdminAttr", canShowAdminAttr);
			model.addAttribute("canShowAdminAttrValue", canShowAdminAttrValue);
			model.addAttribute("canShowAdminGroup", canShowAdminGroup);
			model.addAttribute("canShowAdminRole", canShowAdminRole);
			model.addAttribute("canShowAdminTemplate", canShowAdminTemplate);
			model.addAttribute("canShowAdminUser", canShowAdminUser);
			model.addAttribute("canShowAdminWorkflow", canShowAdminWorkflow);
			model.addAttribute("canShowAdminFeature", canShowAdminFeature);
			model.addAttribute("canShowAdminDocType", canShowAdminDocType);
			model.addAttribute("canShowAdminSecurityGroup", canShowAdminSecurityGroup);
			model.addAttribute("canShowAdminDelete", canShowAdminDelete);
			model.addAttribute("canShowAdminUnclaim", canShowAdminUnclaim);
			model.addAttribute("canShowAdminAssignTask", canShowAdminAssignTask);
			model.addAttribute("canShowAdminReassignOwner", canShowAdminReassignOwner);
			model.addAttribute("canShowAdminMyDocument",canShowAdminMyDocument);
			model.addAttribute("canShowAdminFormSecurity",canShowAdminFormSecurity);
			}
		return "adminTemplate";
	}

	// For Unpick Document Start Region
	@RequestMapping(value = "/goPickedSteps", method = RequestMethod.GET)
	public String showPickedSteps(Model model) {

		List<WfStep> steps = caseService.findPickedSteps();
		model.addAttribute("pickedSteps", steps);
		model.addAttribute("docStepMap", getStepWithDocsMap());
		model.addAttribute("title", "Unclaim Document");
		return "showPickedSteps";
	}

	// private Map<String,WfStep> getStepWithDocsMap(){
	// private Map<WfStep,String> getStepWithDocsMap(){
	private Map<WfStep, ArrayList<String>> getStepWithDocsMap() {

		List<WfStep> steps = caseService.findPickedSteps();
		// Map<WfStep,String> docStepMap=new HashMap<WfStep, String>();
		Map<WfStep, ArrayList<String>> docStepMap = new HashMap<WfStep, ArrayList<String>>();
		for (WfStep step : steps) {
			if (step.getAssigneeBefore() != null) {
				WfCase wfCase = step.getOwningCase();
				Document doc = documentService.findDocumentForCaseId(wfCase.getId());
				if (doc != null) {
					ArrayList<String> docList = new ArrayList<String>();
					docList.add(doc.getName());
					docList.add(doc.getEbNo());
					docStepMap.put(step, docList);
					// docStepMap.put(step,doc.getName());
				}
				// docStepMap.put(doc.getName(),step);
			}
		}
		return docStepMap;
	}

	@RequestMapping(value = "/unpickStep", method = RequestMethod.GET)
	public String unpickDocumentStep(@RequestParam("stepId") long stepId, Model model) {

		WfStep wfStep = caseService.findStepById(stepId);
		caseService.changeToUnpickStatus(wfStep);
		model.addAttribute("docStepMap", getStepWithDocsMap());
		model.addAttribute("title", "Unpick Document");
		return "showPickedSteps";
	}

	// For Unpick Document End Region
	// For Reassign User Start
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public String showAllUsers(Model model) {

		List<User> userLists = userService.findAllUsers();
		// List<User> userLists=userService.findAllUsersWithInActive();
		model.addAttribute("userLists", userLists);
		model.addAttribute("title", "Reassign Task");
		return "showUsersForTasks";
	}

	@RequestMapping(value = "/getUserTray", method = RequestMethod.GET)
	public String showTrayLabelForUser(@RequestParam("user") long userId, Model model) {

		User user = userService.findUserById(userId);
		UserInfo userInfo = new UserInfo(user);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		List<TaskSummary> trayLabels = new ArrayList<TaskSummary>();
		for (TaskSummary ts : tasks) {
			String trayName = ts.getTaskName();
			if (!trayName.equalsIgnoreCase("My Documents")) {
				trayLabels.add(ts);
			}
		}
		model.addAttribute("tasks", trayLabels);
		return "showUserTasks";
	}

	@RequestMapping(value = "/trayDocs", method = RequestMethod.GET)
	public String showTrayDocuments(@RequestParam("userId") long userId, @RequestParam("trayLabel") String trayLabel, Model model) {

		User user = userService.findUserById(userId);
		UserInfo userInfo = new UserInfo(user);
		// List<Long> caseIds = new ArrayList<Long>();
		List<WfStep> steps = caseService.findAssignedStepsForTrayLabel(userInfo, trayLabel);
		// for (WfStep s : steps) {
		// caseIds.add(s.getOwningCase().getId());
		// }
		// List<uk.co.jmr.sdp.domain.Document> trayDocs = documentService
		// .findDocumentsForCase(caseIds);
		// Map<String,WfStep> docStepMap=new TreeMap<String, WfStep>();
		Map<ArrayList<String>, WfStep> documentStepMap = new HashMap<ArrayList<String>, WfStep>();
		for (WfStep step : steps) {
			WfCase wfCase = step.getOwningCase();
			Document doc = documentService.findDocumentForCaseId(wfCase.getId());
			if (doc != null) {
				// docStepMap.put(doc.getName(),step);
				ArrayList<String> docList = new ArrayList<String>();
				docList.add(doc.getName());
				docList.add(doc.getEbNo());
				documentStepMap.put(docList, step);
			}
		}
		model.addAttribute("userId", userId);
		// model.addAttribute("trayDocs",docStepMap);
		model.addAttribute("trayDocs", documentStepMap);
		return "showDocumentListsReassign";
	}

	@RequestMapping(value = "/reassignUser", method = RequestMethod.GET)
	public String showReassignUser(@RequestParam("caseId") long caseId, @RequestParam("stepId") long stepId,
		@RequestParam("userId") long userId, Model model) {

		//System.out.println("Case:" +caseId+":" +stepId +":" +userId);
		logger.info("/reassignUser  Case:" +caseId+":" +stepId +":" +userId);
		List<User> userLists = userService.findAllUsers();
		// Removal of Owner
		WfCase wfCase = caseService.findCaseById(caseId);
		User ownerRemoval = userService.findUserByUserName(wfCase.getCreator());
		userLists.remove(ownerRemoval);
		User userRemoval = userService.findUserById(userId);
		userLists.remove(userRemoval);
		model.addAttribute("userLists", userLists);
		model.addAttribute("caseId", caseId);
		model.addAttribute("stepId", stepId);
		return "reassignUser";
	}

	@RequestMapping(value = "/reassign", method = RequestMethod.GET)
	public String reassignUserStep(@RequestParam("caseId") long caseId, @RequestParam("stepId") long stepId,
		@RequestParam("userId") long userId, Model model) {

		// System.out.println("Case:" +caseId+":" +stepId +":" +userId);
		logger.info("/reassign  Case:" +caseId+":" +stepId +":" +userId);
		WfStep wfStep = caseService.findStepById(stepId);
		User userToAssign = userService.findUserById(userId);
		String assignee = "U:" + userToAssign.getUserName();
		wfStep.setAssignee(assignee);
		caseService.saveStep(wfStep);
		return "reassignUser";
	}

	// For Reassign User End
	// For Reassign Owner Start
	private void listPublishedDocuments(Model model) {

		boolean revision = true;
		long caseId = 0;
		List<Document> publishedDocs = documentService.findAllDocumentsByWipAndRevisionWithoutQuickUpl('N', revision, caseId);
		model.addAttribute("title", "Reassign Document Owner");
		model.addAttribute("publishedDocs", publishedDocs);
	}

	@RequestMapping(value = "/showPublishedDocs", method = RequestMethod.GET)
	public String showPublishedDocs(Model model) {

		listPublishedDocuments(model);
		return "showPublishedDocuments";
	}

	@RequestMapping(value = "/reassignOwner", method = RequestMethod.GET)
	public String showReassignOwner(@RequestParam("documentId") long documentId, Model model) {

		List<User> userLists = userService.findAllUsers();
		model.addAttribute("documentId", documentId);
		model.addAttribute("userLists", userLists);
		return "reassignOwner";
	}

	@RequestMapping(value = "/reassignDocumentOwner", method = RequestMethod.GET)
	public String reassignDocumentOwner(@RequestParam("documentId") long documentId, @RequestParam("userId") long userId,
		Model model) {
		Document document = documentService.findDocumentById(documentId);
		User user = userService.findUserById(userId);
		document.setReassignOwner(user.getUserName());
		documentService.save(document);
		listPublishedDocuments(model);
		return "showPublishedDocuments";
	}
	// Reassign Owner End
	
	@RequestMapping(value = "/showWipDocs", method = RequestMethod.GET)
	public String getAllWipDocuments(
		HttpSession session, Model model) throws RepositoryFault, RemoteException {
		
		HashMap<ArrayList, String> documents=new LinkedHashMap<ArrayList,String>();
		HashMap<ArrayList, String> reports = new LinkedHashMap<ArrayList, String>();
		
		List<Document> docs=documentService.findAllDocumentsByWip('Y');
		List<Document> docList=new ArrayList<Document>();
		
		for(Document doc:docs){
			long caseId=doc.getCaseId();
			WfCase cId=caseService.findCaseById(caseId);
			if(!cId.getStatus().equals("WF_CASE_CANCELLED")){
				docList.add(doc);
			}
		}
		
		for (Document document : docList) {
			long caseId = document.getCaseId();
			WfCase wfCase = caseService.findCaseById(caseId);
			ArrayList reportArray = new ArrayList();
			
			reportArray.add(document.getName());
			reportArray.add(document.getTargetDate());
			reportArray.add(wfCase.getCreator());
			if(document.getDoctype()!=null){
			reportArray.add(document.getDoctype().getDoctypeName());
			reportArray.add(true);
			}else{
				reportArray.add("-");
				reportArray.add(false);
				
			}
			reportArray.add(document.getDateCreated());
			

			
		//	reportArray.add(document.getMdlNo());
			
			

		//	long caseId = document.getCaseId();
			String modelName = null;
			if (caseId != 0) {
				
				String taskStatus = "";
				List<WfStep> stepsList=caseService.findAllStepsForCase(caseId);
				WfStep lastStepInfo=stepsList.get(stepsList.size()-1);
				taskStatus += lastStepInfo.getStatusLabel() + " - " + lastStepInfo.getNode().getName();
				taskStatus += "|";
				
				String documentStatus = null;
				boolean isCheckedout = document.getLocked().equalsIgnoreCase("YES");
				if (isCheckedout == true) {

					if (wfCase.getCaseStatus() == WfCaseStatus.WF_CASE_CANCELLED) {
						documentStatus = "Abandoned";
						reportArray.add(documentStatus);

					}

					else {
						// System.out.println("*** in checked out");
						documentStatus = "Checked out";
						reportArray.add(documentStatus);

					}

				}

				else {
					if (wfCase.getCaseStatus() == WfCaseStatus.WF_CASE_CANCELLED) {
						documentStatus = "Abandoned";
						reportArray.add(documentStatus);
					}
					else {
						reportArray.add(taskStatus.substring(0, taskStatus.length() - 1));
					}
				}

				/*reportArray.add(taskStatus.substring(0, taskStatus.length() - 1));*/
				WfModel wfModel = wfCase.getModel();
				modelName = wfModel.getName();
				reportArray.add(document.getDiscriminator());
				reportArray.add(caseId);
				if(document.getFilePath()!=null){
				reportArray.add(document.getFilePath());
				}else{
					reportArray.add("-");
				}
				/*StringBuilder stBuf = new StringBuilder();*/
				/*stBuf.append("[");*/
				String stepList=null;
				for (WfStep s : stepsList) {
					if(s.getStatus().equals("WF_STEP_PICKED") || s.getStatus().equals("WF_STEP_ASSIGNED")){
						stepList=Long.toString(s.getId());
					}
						
					/*stBuf.append(s.getId());
					stBuf.append(",");*/
				}

				/*String stepList = stBuf.substring(0, stBuf.length() - 1);*/
				/*stepList += "]";*/
				reportArray.add(stepList);
				
			}
			reports.put(reportArray, modelName);
		}

		model.addAttribute("docList", reports);
		return "documentListAdmin";
	}
	
	
}
