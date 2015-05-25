package uk.co.jmr.sdp.web;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.alfresco.webservice.repository.RepositoryFault;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import uk.co.jmr.sdp.dao.AttributeValueDao;
import uk.co.jmr.sdp.dao.DisciplineDao;
import uk.co.jmr.sdp.dao.DoctypeDao;
import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.DocumentReference;
import uk.co.jmr.sdp.domain.DocumentTrail;
import uk.co.jmr.sdp.domain.DocumentVersion;
import uk.co.jmr.sdp.domain.FormTrail;
import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.domain.GroupRole;
import uk.co.jmr.sdp.domain.ReviewNote;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.TempDocuments;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.forms.FormDefinition;
import uk.co.jmr.sdp.domain.photoupload.UploadPhotos;
import uk.co.jmr.sdp.ds.DocumentStorage;
import uk.co.jmr.sdp.service.CaseUserFormsService;
import uk.co.jmr.sdp.service.CompanyUserService;
import uk.co.jmr.sdp.service.DisciplineService;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.DocumentTrailService;
import uk.co.jmr.sdp.service.DocumentVersionService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.FeatureService;
import uk.co.jmr.sdp.service.FormService;
import uk.co.jmr.sdp.service.FormTrailService;
import uk.co.jmr.sdp.service.GroupRoleService;
import uk.co.jmr.sdp.service.GroupService;
import uk.co.jmr.sdp.service.HolidayService;
import uk.co.jmr.sdp.service.ReviewNoteService;
import uk.co.jmr.sdp.service.RoleService;
import uk.co.jmr.sdp.service.SecurityGroupService;
import uk.co.jmr.sdp.service.TempDocumentsService;
import uk.co.jmr.sdp.service.UserService;
import uk.co.jmr.sdp.web.revision.AbandonedDocument;
import uk.co.jmr.sdp.web.util.DocListInput;
import uk.co.jmr.sdp.web.util.ExtGridBuilder;
import uk.co.jmr.sdp.web.util.GridBuilder;
import uk.co.jmr.sdp.web.util.SearchFilter;
import uk.co.jmr.sdp.web.util.UserInfo;
import uk.co.jmr.sdp.web.util.Util;
import uk.co.jmr.webforms.db.pojo.FormData;
import uk.co.jmr.webforms.db.pojo.UserForms;
import uk.co.jmr.webforms.db.service.FormDataService;
import uk.co.jmr.webforms.db.service.UserFormsService;

import com.ardhika.wfar.CaseDocHistory;
import com.ardhika.wfar.CaseStepInfo;
import com.ardhika.wfar.TaskSummary;
import com.ardhika.wfar.WfAction;
import com.ardhika.wfar.WfAttribute;
import com.ardhika.wfar.WfAttributeType;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfCaseStatus;
import com.ardhika.wfar.WfNodeType;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.WfStepStatus;
import com.ardhika.wfar.service.CaseService;
import com.ardhika.wfar.service.ModelService;
import java.util.TreeSet;
import uk.co.jmr.webforms.db.pojo.UserForms;
import uk.co.jmr.webforms.db.service.FormsReportingService;

/**
 * @author kad019
 * 
 */
@Controller
@RequestMapping(value = "/dash")
public class DashboardController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private DocumentStorage documentStorage;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private DisciplineDao disciplineDao;
	@Autowired
	private DoctypeDao docTypeDao;
	@Autowired
	private CaseService caseService;
	@Autowired
	private ModelService modelService;
	@Autowired
	private DoctypeService docTypeService;
	@Autowired
	private DisciplineService disciplineService;
	@Autowired
	private ReviewNoteService reviewNoteService;
	@Autowired
	private DocumentTrailService documentTrailService;
	@Autowired
	private HolidayService holidayService;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;
	@Autowired
	private DtAttributeService dtAttributeService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupRoleService groupRoleService;
	@Autowired
	private FormService formService;
	@Autowired
	private UserService userService;
	@Autowired
	private CaseUserFormsService caseUserFormsService;
	@Autowired
	private UserFormsService userFormsService;
	@Autowired
	private FormsReportingService formsReportingService;
	@Autowired
	private FeatureService featureService;
	@Autowired
	private SecurityGroupService securityGroupService;
	@Autowired
	private DocumentVersionService documentVersionService;
	@Autowired
	private FormTrailService formTrailService;
	@Autowired
	private TempDocumentsService tempDocumentsService;
	@Autowired
	private DoctypeService doctypeService;
	@Autowired
	private FormDataService formDataService;
	@Autowired
	private AttributeValueDao attributeValueDao;
	@Autowired
	private CompanyUserService companyUserService;
	
	
	Logger logger=Logger.getLogger(DashboardController.class);
	
	
	@RequestMapping(value = "/docs", method = RequestMethod.GET)
	public String getDocumentList(@RequestParam("path") String path, HttpSession session, Model model) throws ParseException {

		DocListInput dli = new DocListInput();
		dli.setOrigin("TP");
		dli.setPath(path);
		session.setAttribute("dli", dli);
		try {
			if (path.equals(((String) servletContext.getAttribute("filePath.photos"))
				+ Util.encode((String) servletContext.getAttribute("foldername.photos")))) {
				String FolderName = Util.encode((String) servletContext.getAttribute("foldername.photos"));
				model.addAttribute(
					"imageList",
					showDocs((String) servletContext.getAttribute("dsUser"), (String) servletContext.getAttribute("dsPassword"),
						(String) servletContext.getAttribute("filePath.photos") + FolderName));

				return "photosDownload";
			}
			else if (path.equals(((String) servletContext.getAttribute("filePath.videos"))
				+ Util.encode((String) servletContext.getAttribute("foldername.videos")))) {
				String FolderName = Util.encode((String) servletContext.getAttribute("foldername.videos"));
				model.addAttribute(
					"imageList",
					showDocs((String) servletContext.getAttribute("dsUser"), (String) servletContext.getAttribute("dsPassword"),
						(String) servletContext.getAttribute("filePath.videos") + FolderName));

				return "photosDownload";
			}
			else if (path.equals(((String) servletContext.getAttribute("filePath.meetings"))
				+ Util.encode((String) servletContext.getAttribute("foldername.meetings")))) {
				String FolderName = Util.encode((String) servletContext.getAttribute("foldername.meetings"));
				model.addAttribute(
					"imageList",
					showDocs((String) servletContext.getAttribute("dsUser"), (String) servletContext.getAttribute("dsPassword"),
						(String) servletContext.getAttribute("filePath.meetings") + FolderName));

				return "photosDownload";
			}

			return showDocs(session, model);
		}
		catch (RepositoryFault e) {
			e.printStackTrace();
			logger.error("/docs Error message " +e);
			model.addAttribute("photoserror", "Repository cannot be accessed at this time.Please try again later");
		}
		catch (RemoteException e) {
			e.printStackTrace();
			logger.error("/docs Error message "+e);
			model.addAttribute("photoserror", "Remote machine cannot be accessed at this time.Please try again later");
		}

		return null;
	}

	public List<uk.co.jmr.sdp.domain.ds.Document> showDocs(String dsUser, String dsPassword, String path) throws RepositoryFault,
		RemoteException {

		return documentStorage.getDocuments(dsUser, dsPassword, path);
	}

	@RequestMapping(value = "/btd", method = RequestMethod.GET)
	public String backToDocs(HttpSession session, Model model) throws RepositoryFault, RemoteException, ParseException {

		return showDocs(session, model);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchDocumentList(@RequestParam("documentType") String docTypeId, @RequestParam("author") String author,
		@RequestParam("ebNo") String ebNo, @RequestParam("documentName") String documentName,
		@RequestParam("relevantdatefrom") String relevantDateFrom, @RequestParam("relevantdateto") String relevantDateTo,
		HttpSession session, Model model) throws RepositoryFault, RemoteException, ParseException {
		DocListInput dli = new DocListInput();
		dli.setDocTypeId(Long.parseLong(docTypeId));
		dli.setOrigin("SF");
		dli.setAuthor(author);
		dli.setEbNo(ebNo);
		dli.setDocumentName(documentName);
		dli.setRelevantDateFrom(relevantDateFrom);
		dli.setRelevantDateTo(relevantDateTo);
		session.setAttribute("dli", dli);
		return showDocs(session, model);
	}

	
	// Forms Module
	// Getting Form Definitions
	private List<FormDefinition> formDefinitionLists() {

		List<FormDefinition> formLists = formService.formDefinitions();
		return formLists;
	}
	
	@RequestMapping(value="/goshowDocuments",method=RequestMethod.GET)
	public String showDocuments(HttpSession session,Model model){
	return "dashboardSearchDocuments";
	}
	
	@RequestMapping(value="/goshowForms",method=RequestMethod.GET)
	public String showForms(HttpSession session,Model model){
	model.addAttribute("formDefinitions", formDefinitionLists());	
	return "dashboardSearchForms";
	}
	
	
	@RequestMapping(value = "/searchForms", method = RequestMethod.POST)
	public String searchOnlyFormsList(@RequestParam("userId") long userId,@RequestParam("formType") String docTypeId, @RequestParam("owner") String owner,
		@RequestParam("refNo") String refNo, @RequestParam("documentName") String documentName,@RequestParam("keywords") String keywords,
		@RequestParam("dateCreatedFrom") String raisedDateFrom, @RequestParam("dateCreatedTo") String raisedDateTo,
		@RequestParam("relevantdatefrom") String relevantDateFrom, @RequestParam("relevantdateto") String relevantDateTo,@RequestParam("specificdata")String specificData,
		HttpSession session, Model model) throws RepositoryFault, RemoteException, ParseException {
		DocListInput dli = new DocListInput();
		dli.setDocTypeId(Long.parseLong(docTypeId));
		dli.setOrigin("SF");
		dli.setAuthor(owner);
		dli.setEbNo(refNo);
		dli.setKeywords(keywords);
		dli.setDocumentName(documentName);
		dli.setDateCreatedFrom(raisedDateFrom);
		dli.setDateCreatedTo(raisedDateTo);
		dli.setRelevantDateFrom(relevantDateFrom);
		dli.setRelevantDateTo(relevantDateTo);
		dli.setUserId(userId);
		dli.setSpecificData(specificData);
		session.setAttribute("dli", dli);
		return showDocs(session, model);
	}
	
	@RequestMapping(value = "/searchDocuments", method = RequestMethod.POST)
	public String searchOnlyDocumentList(
			@RequestParam("userId") long userId,
			@RequestParam("documentName") String documentName,
			@RequestParam("keywords") String keywords,
			@RequestParam("owner") String owner,
			@RequestParam("refNo") String refNo,
			@RequestParam("dateCreatedFrom") String raisedDateFrom, 
			@RequestParam("dateCreatedTo") String raisedDateTo,
			@RequestParam("discriminator") char discriminator,
			@RequestParam("relevantdatefrom") String relevantDateFrom,
			@RequestParam("relevantdateto") String relevantDateTo,
			@RequestParam("documentType") String docTypeId,
			@RequestParam("metadata1") String metadata1,
			@RequestParam("metadata2") String metadata2,
			@RequestParam("metadata3") String metadata3,
			/*@RequestParam("metadata4") String metadata4,*/
			HttpSession session, Model model) throws RepositoryFault,
			RemoteException, ParseException {
		
		if ((metadata1 != null && metadata1 != "")
				&& (!metadata1.equals("-1"))) {
			session.setAttribute("projectId", Long.parseLong(metadata1));

		} else {

			session.removeAttribute("projectId");
		}
		if ((metadata2 != null && metadata2 != "")
				&& (!metadata2.equals("-1"))) {
			session.setAttribute("categoryId", Long.parseLong(metadata2));

		} else {

			session.removeAttribute("categoryId");
		}
		
		if ((metadata3 != null && metadata3 != "")
				&& (!metadata3.equals("-1"))) {
			session.setAttribute("disciplineId", Long.parseLong(metadata3));

		} else {

			session.removeAttribute("disciplineId");
		}
		/*if (metadata4 != null & metadata4 != "" && (!metadata4.equals("-1"))) {

			session.setAttribute("siteId", Long.parseLong(metadata4));
		} else {

			session.removeAttribute("siteId");
		}*/

		Doctype docType = docTypeDao.findDoctypeById(Long.parseLong(docTypeId));
		DocListInput dli = new DocListInput();
		dli.setOrigin("SF");
		dli.setAuthor(owner);
		dli.setEbNo(refNo);
		dli.setKeywords(keywords);
		dli.setDocumentName(documentName);
		dli.setDateCreatedFrom(raisedDateFrom);
		dli.setDateCreatedTo(raisedDateTo);
		dli.setDiscriminator(discriminator);
		dli.setRelevantDateFrom(relevantDateFrom);
		dli.setRelevantDateTo(relevantDateTo);
	 	dli.setDocType(docType);
		dli.setDocTypeId(Long.parseLong(docTypeId));
		dli.setUserId(userId);
		session.setAttribute("dli", dli);
		return showDocs(session, model);
	}
		
	@RequestMapping(value = "/searchAll", method = RequestMethod.POST)
	public String searchAllDocumentList(@RequestParam("userId") long userId,@RequestParam("documentName") String documentName,
		@RequestParam("keywords") String keywords,
		@RequestParam("owner") String owner,
		@RequestParam("refNo") String refNo,
		@RequestParam("dateCreatedFrom") String raisedDateFrom, 
		@RequestParam("dateCreatedTo") String raisedDateTo,
		@RequestParam("discriminator") char discriminator,
		
		HttpSession session, Model model) throws RepositoryFault, RemoteException, ParseException {
		DocListInput dli = new DocListInput();
		dli.setOrigin("SF");
		dli.setAuthor(owner);
		dli.setEbNo(refNo);
		dli.setKeywords(keywords);
		dli.setDocumentName(documentName);
		dli.setDateCreatedFrom(raisedDateFrom);
		dli.setDateCreatedTo(raisedDateTo);
		dli.setDiscriminator(discriminator);
		dli.setUserId(userId);
		session.setAttribute("dli", dli);
		return showDocs(session, model);
	}
	
	private String regexSplit(String documentName) {

		if (documentName.contains("*"))
			documentName = documentName.replace("*", "&");

		return documentName;
	}

	private String viewMetadataForm(String path, String formName, HttpSession session, Model model, Long caseId, String stps,boolean isAdminMeta) {
		WfCase wfCase = caseService.findCaseById(caseId);
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		if (formName.trim().length() <= 0) {
			formName = "-";
		}
	
		//to find the count of the comments for the form
		List<ReviewNote> commentsList =  reviewNoteService.findAllReviewsByCaseId(caseId);
		if(commentsList != null){
			System.out.println(">>>>>>>>>>>>>"+commentsList.size());
			model.addAttribute("commentsCount", commentsList.size());
		}else{
			System.out.println(">>>>>>>>>>>>>");
			model.addAttribute("commentsCount", "0");
		}
		HashMap<String, String> result = new LinkedHashMap<String, String>();
		boolean formAttached = false;
		boolean stepAssigned = false;
		boolean isOwner = false;
		boolean isStartStep = false;
		boolean isCheckedout = false;
		boolean canPick = false;
		boolean canShowDownload = false;
		boolean canShowRefLinks = false;
		boolean canShowRevision = false;
		boolean canShowCommentsPopUp = true;
		boolean canShowAbandon = false;
		boolean canShowCancel = false;
		boolean canShowClone=false;

		// For Claim
		boolean canShowAllButtons = false;
		if (!(formName.equals("-")) || formName != null)
			formAttached = true;

		List<WfStep> stepList = new ArrayList<WfStep>();
		if (!stps.equals("Nil")) {
			List<Long> stepListLong = new ArrayList<Long>();
			for (String s : stps.split(",")) {
				stepListLong.add(Long.parseLong(s));
			}

			for (Long l : stepListLong) {
				stepList.add(caseService.findStepById(l));
			}
		}
		else {
			model.addAttribute("stepId", "0");
		}

		List<Boolean> assignedStatusList = new ArrayList<Boolean>();
		boolean sa = false;
		String actionStatus = "";
		if (stepList.size() > 0) {
			for (WfStep s : stepList) {
				Long stepId = s.getId();
				if (stepId != 0) {
					sa = checkAssigned(caseId, stepId, userInfo);
					assignedStatusList.add(sa);
					WfStep step = caseService.findStepById(stepId);
					actionStatus = actionStatus + step.getStatusLabel() + " - " + step.getNode().getName();
					actionStatus += "|";
					model.addAttribute("ActionStatus", actionStatus.substring(0, actionStatus.length() - 1));
				}
				else {
					if (wfCase.getCaseStatus() == WfCaseStatus.WF_CASE_CANCELLED)
						actionStatus = "Abandoned";
					else
						actionStatus = "Published";

					model.addAttribute("ActionStatus", actionStatus);
				}
			}

			model.addAttribute("stepId", stepList.get(0).getId());
			for (boolean b : assignedStatusList) {
				if (b) {
					stepAssigned = true;
				}
				else {
					stepAssigned = false;
					break;
				}
			}
		}
		else {
			if (wfCase.getCaseStatus() == WfCaseStatus.WF_CASE_CANCELLED)
				actionStatus = "Abandoned";
			else
				actionStatus = "Published";

			model.addAttribute("ActionStatus", actionStatus);
		}

		result.put("Form Name", formName);
		Document d=documentService.findDocumentByDocName(formName);
		UserForms uf=userFormsService.findUserFormsById(Long.parseLong(d.getUserFormId()));
		result.put("Company", dtAttributeValueService.findDtAttrValueById(uf.getCompanyId()).getValue());
		if (actionStatus.equalsIgnoreCase("Published") || actionStatus.equalsIgnoreCase("Abandoned")) {
			result.put("Status-Action", actionStatus);
		}
		else {
			result.put("Status-Action", actionStatus.substring(0, actionStatus.length() - 1));
		}
		result.put("Workflow Process", wfCase.getModel().getName());
		Set<WfAttribute> attr1 = wfCase.getAttributes();
		for (WfAttribute at : attr1) {
			if (at.getValue() == null)
				at.setValue("-");

			if ((at.getType() == WfAttributeType.WF_ATTR_TEXT) || (at.getType() == WfAttributeType.WF_ATTR_DECIMAL)
				|| (at.getType() == WfAttributeType.WF_ATTR_NUMBER)) {
				if (!at.getName().equalsIgnoreCase("FilePath")) {
					result.put(at.getName(), at.getValue().toString());
				}
			}
			else if ((at.getType() == WfAttributeType.WF_ATTR_DATE)) {
				result.put(at.getName(), at.getValue().toString());
			}
		}
		Document docUserForms=documentService.findDocumentForCaseId(caseId);
		long l = Long.parseLong(docUserForms.getUserFormId());
		UserForms userForms = userFormsService.findUserFormsById(l);
		result.put("Raised Date & Time", dateTimeFormat.format(userForms.getCreatedOn()));
		result.put("Owner", wfCase.getCreator());
		
		if(docUserForms.getSecurityGroup()!=null)
		result.put("Security Group", docUserForms.getSecurityGroup().getName());
		else
			result.put("Security Group", "Open");
		
		// For Action Status
		

	//	CaseUserForms caseUserForms = caseUserFormsService.findCaseUserFormsByCaseId(caseId);
	
		if(docUserForms.getWip()=='N'){
			canShowClone=true;
		}
		//UserForms userForms = userFormsService.findUserFormsById(caseUserForms.getUserFormsId());
		
		
		if (!docUserForms.getDocumentReference().isEmpty()) {
			Set<DocumentReference> docReference = docUserForms.getDocumentReference();
			List<String> securityGroups = (List<String>) session.getAttribute("securityGroups");
			Set<DocumentReference> downloadableDocReference = new HashSet<DocumentReference>();
			Set<DocumentReference> unDownloadableDocReference = new HashSet<DocumentReference>();
			for (DocumentReference documentReference : docReference) {
				if (documentReference.getReferenceDocument().getSecurityGroup() == null) {
					downloadableDocReference.add(documentReference);	
					//Form Based URL download restriction
//					if(documentReference.getReferenceDocument().getDiscriminator()=='F'){
//						unDownloadableDocReference.add(documentReference);
//					}
//					else{
//						downloadableDocReference.add(documentReference);	
//					}
				}
				else if (securityGroups.contains(documentReference.getReferenceDocument().getSecurityGroup().getName())) {
					downloadableDocReference.add(documentReference);
				}
				else {
					unDownloadableDocReference.add(documentReference);
				}
			}

			model.addAttribute("downloadablereferenceDoc", downloadableDocReference);
			model.addAttribute("undownloadablereferenceDoc", unDownloadableDocReference);
		}
		
		
	
		// For Workflow Raised Date & time
		
		
		// docAttached,reviewDocs Code
		List<ReviewNote> reviews = reviewNoteService.findAllReviewsByWfCase(wfCase);
		Collections.sort(reviews, Collections.reverseOrder());

		// For Comments
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		ReviewNote LastReviewNotes = reviewNoteService.findByReviewerLastComment(user, wfCase);
		if (LastReviewNotes != null) {
			canShowCommentsPopUp = false;
		}

		model.addAttribute("canShowCommentsPopUp", canShowCommentsPopUp);
		model.addAttribute("reviewDocs", reviews);
		model.addAttribute("formName", formName);
		Set<WfAttribute> attr = wfCase.getAttributes();
		Set<WfAttribute> attributeSet = new HashSet<WfAttribute>();
		for (WfAttribute at : attr) {
			if (at.getUserEditable().equalsIgnoreCase("Y")) {
				attributeSet.add(at);
			}
		}

		WfAttribute[] attributes = attributeSet.toArray(new WfAttribute[attributeSet.size()]);
		Arrays.sort(attributes);

		// For Formating the date in edit while coming for the first time
		String tgtDate = null;
		String ebn = null;
		for (WfAttribute at : attributes) {
			if (!at.getValue().equals("-")) {
				if (at.getType() == WfAttributeType.WF_ATTR_DATE) {
					at.setValue(dateFormat.format(at.getValue()));
					if (at.getName().equalsIgnoreCase("Target Date"))
						tgtDate = (String) at.getValue();
				}

				if ((at.getType() == WfAttributeType.WF_ATTR_TEXT) && (at.getName().equalsIgnoreCase("Eb Number"))) {
					ebn = (String) at.getValue();
				}
			}
		}

		if (stepList.size() > 0) {
			for (WfStep s : stepList) {
				WfStep step = caseService.findStepById(s.getId());
				if (s.getId() != 0) {
					if(!isAdminMeta)
					if (stepAssigned)
						caseService.haveBeenRead(step.getId());

					model.addAttribute("currentStep", step);
					if (step.getNode().getName().equalsIgnoreCase(wfCase.getModel().getStartNode().getName()))
						isStartStep = true;

					Set<WfAction> actions = step.getNode().getActions();
					String jsonVals = "{'vals' : [";
					for (WfAction act : actions) {
						jsonVals += "{'n':'" + act.getName() + "','r':";
						if (act.getReasonRequired() == 'Y') {
							jsonVals += "'y'},";
						}
						else {
							jsonVals += "'n'},";
						}
					}

					jsonVals = jsonVals.substring(0, jsonVals.length() - 1);
					jsonVals += "]}";
					String userAssigned = step.getAssignee();
					if ((userAssigned.startsWith("R:"))) {
						List<User> users = roleService.findUsersForRoleName(userAssigned.substring(2));
						if (users.size() > 1) {
							canPick = true;
						}
					}

					if ((userAssigned.startsWith("RG:"))) {
						String ss = userAssigned.substring(3);
						String[] grSplit = ss.split("\\.");
						Group group = groupService.findGroupByName(grSplit[1]);
						Role role = roleService.findRoleByRoleName(grSplit[0]);
						GroupRole groupRole = groupRoleService.findGroupRole(group, role);
						List<User> users = groupRoleService.getUsersFromGroupRole(groupRole);
						if (users.size() > 1)
							canPick = true;
					}

					model.addAttribute("canPick", canPick);
					model.addAttribute("jsonVals", jsonVals);
					model.addAttribute("actions", step.getActionNames());
				}
			}
		}

		if (wfCase.getCreator().equals(userInfo.getUserName()))
			isOwner = true;

		if (canPick == false || isOwner == true) {
			canShowAllButtons = true;
		}

		if (stepAssigned == true || canShowDownload == true) {
			canShowRefLinks = true;
		}

		canShowAbandon = decideCanShowAbandon(userInfo, caseId);
		String s = wfCase.getStatus();
		if (s.equals("WF_CASE_CLOSED") && canShowAbandon) {
			canShowCancel = canShowAbandon;
			canShowAbandon = false;
		}
		
		if(isAdminMeta){
			if (s.equals("WF_CASE_CANCELLED"))
			canShowAbandon = true;
		}
		
		//For Feature Code Changes Start
		showFeatures(model,userInfo);
		//End

		//caseUserForms = caseUserFormsService.findCaseUserFormsByCaseId(caseId);
		docUserForms=documentService.findDocumentForCaseId(caseId);
		//model.addAttribute("userFormId", caseUserForms.getUserFormsId());
		model.addAttribute("canShowClone", canShowClone);
		model.addAttribute("userFormId", docUserForms.getUserFormId());
		model.addAttribute("documentId",docUserForms.getId());
		model.addAttribute("canShowRefLinks", canShowRefLinks);
		model.addAttribute("canShowRevision", canShowRevision);
		model.addAttribute("canShowAbandon", canShowAbandon);
		model.addAttribute("canShowCancel", canShowCancel);
		model.addAttribute("docName", formName);
		model.addAttribute("canShowAllButtons", canShowAllButtons);
		model.addAttribute("stepList", stps);
		model.addAttribute("metaDataForm", result);
		model.addAttribute("caseId", caseId);
		model.addAttribute("path", path);
		model.addAttribute("formAttached", formAttached);
		model.addAttribute("stepAssigned", stepAssigned);
		model.addAttribute("isStartStep", isStartStep);
		model.addAttribute("isOwner", isOwner);
		model.addAttribute("isCheckedout", isCheckedout);
		model.addAttribute("attributes", attributes);
		model.addAttribute("trgDte", tgtDate);
		model.addAttribute("ebn", ebn);
		model.addAttribute("modelName", wfCase.getModel().getName());
		if(isAdminMeta){
			model.addAttribute("formAttached", true);
			model.addAttribute("stepAssigned", true);
			return "metaDataFormAdmin";
		}else{
		return "metaDataForm";
	}
	}
	
	private void showFeatures(Model model,UserInfo userInfo){		
		boolean canShowAdminAll = featureService.canDoAdminForFeature(Util.ADMIN_ALL, userInfo);
		boolean canShowAdminHistory=featureService.canDoAdminForFeature(Util.ADMIN_AUDIT_HISTORY,userInfo);
		boolean canShowAdminUpdateProperties=featureService.canDoAdminForFeature(Util.ADMIN_UP_PROPERTIES,userInfo);
		boolean canShowAdminComments=featureService.canDoAdminForFeature(Util.ADMIN_COMMENTS,userInfo);
		boolean canShowAdminClaim=featureService.canDoAdminForFeature(Util.ADMIN_CLAIM, userInfo);
		boolean canShowAdminDownload=featureService.canDoAdminForFeature(Util.ADMIN_DOWNLOAD, userInfo);
		boolean canShowAdminSubVersions=featureService.canDoAdminForFeature(Util.ADMIN_SUB_VERSION, userInfo);
		boolean canShowAdminCheckOut=featureService.canDoAdminForFeature(Util.ADMIN_CHECKOUT, userInfo);
		boolean canShowAdminCheckInUndoCheckOut=featureService.canDoAdminForFeature(Util.ADMIN_CHECKIN_UNDO_CHECKOUT, userInfo);
		boolean canShowAdminRefLinks=featureService.canDoAdminForFeature(Util.ADMIN_REF_LINKING, userInfo);
		boolean canShowAdminAbandon=featureService.canDoAdminForFeature(Util.ADMIN_ABANDON, userInfo);
		boolean canShowAdminRevision=featureService.canDoAdminForFeature(Util.ADMIN_REVISION, userInfo);
		boolean canShowAdminMyDocument=featureService.canDoAdminForFeature(Util.ADMIN_MY_DOCUMENT, userInfo);
		/*boolean canShowAdminFormSecurity=featureService.canDoAdminForFeature(Util.ADMIN_FORM_SECURITY, userInfo);*/
		if(canShowAdminAll==true){
			model.addAttribute("canShowAdminAll", canShowAdminAll);
		}
		else{
		model.addAttribute("canShowAdminHistory", canShowAdminHistory);
		model.addAttribute("canShowAdminUpdateProperties", canShowAdminUpdateProperties);
		model.addAttribute("canShowAdminComments", canShowAdminComments);
		model.addAttribute("canShowAdminClaim",canShowAdminClaim);
		model.addAttribute("canShowAdminDownload",canShowAdminDownload);
		model.addAttribute("canShowAdminCheckOut",canShowAdminCheckOut);
		model.addAttribute("canShowAdminCheckInUndoCheckOut",canShowAdminCheckInUndoCheckOut);
		model.addAttribute("canShowAdminRefLinks",canShowAdminRefLinks);
		model.addAttribute("canShowAdminAbandon",canShowAdminAbandon);
		model.addAttribute("canShowAdminRevision",canShowAdminRevision);
		model.addAttribute("canShowAdminMyDocument",canShowAdminMyDocument);
		model.addAttribute("canShowAdminSubVersions",canShowAdminSubVersions);
		/*model.addAttribute("canShowAdminFormSecurity",canShowAdminFormSecurity);*/
		}
	}
	

	boolean decideCanShowAbandon(UserInfo userInfo, long caseId) {

		//CaseUserForms ucf = caseUserFormsService.findCaseUserFormsByCaseId(caseId);
		Document ucf=documentService.findDocumentForCaseId(caseId);
		if (ucf != null) {
			if (ucf.isAbandon())
				return false;
			if (ucf.getAuthor().equals(userInfo.getUserName()))
				return true;
		}

		TreeSet<String> tset = new TreeSet<String>();
		Set<String> roles = userInfo.getRoles();
		for (String role : roles) {
			tset.add("R:" + role);
		}

		Set<String> groupRoles = userInfo.getGroupRoles();
		for (String groupRole : groupRoles) {
			tset.add("RG:" + groupRole);
		}

		tset.add("U:" + userInfo.getUserName());
		for (WfStep step : caseService.findCaseById(caseId).getSteps()) {
			if (tset.contains(step.getAssignee()))
				return true;
		}

		return false;
	}

	@RequestMapping(value = "/metaDataForForm", method = RequestMethod.GET)
	public String getMetaDataForForm(@RequestParam("path") String path, @RequestParam("caseId") long caseId,
		@RequestParam("formName") String formName, @RequestParam("stepList") String stepList,@RequestParam("adminMeta")boolean isAdminMeta, HttpSession session, Model model) {
		//formName = caseUserFormsService.findCaseUserFormsByCaseId(caseId).getName();
		Document docUserForms=documentService.findDocumentForCaseId(caseId);
		formName=docUserForms.getName();
		return viewMetadataForm(path, formName, session, model, caseId, stepList,isAdminMeta);
	}

	@RequestMapping(value = "/metaData", method = RequestMethod.GET)
	public String get
		(@RequestParam("path") String path, @RequestParam("caseId") long caseId,
		@RequestParam("documentName") String documentName, @RequestParam("stepList") String stepList,@RequestParam("adminMeta") boolean isAdminMeta, HttpSession session,
		Model model) {

		documentName = regexSplit(documentName);
		Document d=documentService.findDocumentByDocName(documentName);
		if (caseId == 0) {
			return viewMetadataNonWorkflow(path, documentName, session, model, caseId,isAdminMeta);
		}
		
		WfCase wfCase = caseService.findCaseById(caseId);
		if(d.getDiscriminator()=='F'){
		if (wfCase.getModel().getModelCategory() == 'F') {
			
			return viewMetadataForm(path, documentName, session, model, caseId, stepList,isAdminMeta);
		}
		}
		
		return viewMetadata(path, documentName, session, model, caseId, stepList,isAdminMeta);
	}

	// For Quickupload on 03.01.13
	private String viewMetadataNonWorkflow(String path, String documentName, HttpSession session, Model model, Long caseId,boolean adminMeta) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		if (documentName.trim().length() <= 0) {
			documentName = "-";
		}
		
		//to find the count of the comments for the form
		List<ReviewNote> commentsList =null;
		if(caseId!=0){
			commentsList	=  reviewNoteService.findAllReviewsByCaseId(caseId);
		}
				if(commentsList != null){
					System.out.println(">>>>>>>>>>>>>"+commentsList.size());
					model.addAttribute("commentsCount", commentsList.size());
				}else{
					System.out.println(">>>>>>>>>>>>>");
					model.addAttribute("commentsCount", "0");
				}

		HashMap<String, String> result = new LinkedHashMap<String, String>();
		boolean docAttached = false;
		if (!(documentName.equals("-"))) {
			docAttached = true;
			uk.co.jmr.sdp.domain.Document document = documentService.findDocumentByDocNameAndPath(documentName, path);
			Set<DocumentAttribute> documentAttributeList = document.getDocumentAttributes();
			String department = null;
			String area = null;
			
			String company = null;
			for (Iterator<DocumentAttribute> iterator = documentAttributeList.iterator(); iterator.hasNext();) {
				DocumentAttribute documentAttribute = (DocumentAttribute) iterator.next();
				if (documentAttribute.getAttribute().getId() == 1) {
					department = documentAttribute.getAttributeValue().getValue();
				}
				else if (documentAttribute.getAttribute().getId() == 2) {
					area = documentAttribute.getAttributeValue().getValue();
				}
				/*else if (documentAttribute.getAttribute().getId() == 3) {
					discipline = documentAttribute.getAttributeValue().getValue();
				}
				else if (documentAttribute.getAttribute().getId() == 4) {
					site = documentAttribute.getAttributeValue().getValue();
				}*/
				else if (documentAttribute.getAttribute().getId() == 5) {
					company = documentAttribute.getAttributeValue().getValue();
				}
			}

			boolean canShowDownload = false;
			String securityGroup = "-";
			List<String> securityGroups = (List<String>) session.getAttribute("securityGroups");
			if (document.getWip() == 'N') {
				if (document.getSecurityGroup() == null) {
					canShowDownload = true;
					securityGroup = "Open";
				}
				else {
					for (String s : securityGroups) {
						if (document.getSecurityGroup().getName().equalsIgnoreCase(s))
							canShowDownload = true;

						securityGroup = document.getSecurityGroup().getName();
					}
				}
			}

			model.addAttribute("canShowDownload", canShowDownload);
			result.put("Name", document.getName());
			result.put("Document Type", document.getDoctype().getDoctypeName());
			result.put("Company", company);
			result.put("Area", area);
			result.put("Department", department);
			/*result.put("Category", category);*/
			result.put("Relevant Date", dateFormat.format(document.getTargetDate()));
			result.put("Raised Date & Time", dateTimeFormat.format(document.getDateCreated()));
			result.put("Keywords", document.getKeywords());
			/*if (category.equalsIgnoreCase("Internal")) {
				result.put("Unique Doc ID/Eb Number", document.getEbNo());
			}
			else {*/
				result.put("Eb Number", document.getEbNo());
			//}

			result.put("Owner", document.getAuthor());
			result.put("Security Group", securityGroup);
			model.addAttribute("docName", documentName);
			model.addAttribute("documentId", document.getId());
			model.addAttribute("path", path);
			model.addAttribute("metaDataNonWorkflow", result);
			model.addAttribute("docAttached", docAttached);
		}
		
		return "metadataNonWorkflow";
	}

	private String viewMetadata(String path, String documentName, HttpSession session, Model model, Long caseId, String stps,boolean adminMeta) {

		Document d=documentService.findDocumentForCaseId(caseId);
		WfCase wfCase = caseService.findCaseById(caseId);
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		if (documentName.trim().length() <= 0) {
			documentName = "-";
		}

		//to find the count of the comments for the form
				List<ReviewNote> commentsList =  reviewNoteService.findAllReviewsByCaseId(caseId);
				if(commentsList != null){
					System.out.println(">>>>>>>>>>>>>"+commentsList.size());
					model.addAttribute("commentsCount", commentsList.size());
				}else{
					System.out.println(">>>>>>>>>>>>>");
					model.addAttribute("commentsCount", "0");
				}
		HashMap<String, String> result = new LinkedHashMap<String, String>();
		boolean docAttached = false;
		boolean stepAssigned = false;
		boolean isOwner = false;
		boolean isStartStep = false;
		boolean isCheckedout = false;
		boolean canPick = false;
		boolean canShowDownload = false;
		boolean canShowRefLinks = false;
		boolean canShowRevision = false;
		boolean canShowCommentsPopUp = true;

		// For Claim
		boolean canShowAllButtons = false;
		List<WfStep> stepList = new ArrayList<WfStep>();
		if (!stps.equals("Nil")) {
			List<Long> stepListLong = new ArrayList<Long>();
			for (String s : stps.split(",")) {
				//System.out.println("Step inFo:" +s);
				stepListLong.add(Long.parseLong(s));
			}

			for (Long l : stepListLong) {
				stepList.add(caseService.findStepById(l));
			}
		}
		else {
			model.addAttribute("stepId", "0");
		}

		List<Boolean> assignedStatusList = new ArrayList<Boolean>();
		boolean sa = false;
		String actionStatus = "";
		if (stepList.size() > 0) {
			for (WfStep s : stepList) {
				Long stepId = s.getId();
				if (stepId != 0) {
					sa = checkAssigned(caseId, stepId, userInfo);
					assignedStatusList.add(sa);
					WfStep step = caseService.findStepById(stepId);
					actionStatus = actionStatus + step.getStatusLabel() + " - " + step.getNode().getName();
					actionStatus += "|";
					model.addAttribute("ActionStatus", actionStatus.substring(0, actionStatus.length() - 1));
				}
				else {
					if (wfCase.getCaseStatus() == WfCaseStatus.WF_CASE_CANCELLED)
						actionStatus = "Abandoned";
					else
						actionStatus = "Published";

					model.addAttribute("ActionStatus", actionStatus);
				}
			}

			model.addAttribute("stepId", stepList.get(0).getId());
			for (boolean b : assignedStatusList) {
				if (b) {
					stepAssigned = true;
				}
				else {
					stepAssigned = false;
					break;
				}
			}
		}
		else {
			if (wfCase.getCaseStatus() == WfCaseStatus.WF_CASE_CANCELLED)
				actionStatus = "Abandoned";
			else
				actionStatus = "Published";

			model.addAttribute("ActionStatus", actionStatus);
		}

		if (!(documentName.equals("-"))) {
			docAttached = true;
			uk.co.jmr.sdp.domain.Document document = documentService.findDocumentByDocNameAndPath(documentName, path);
			boolean isActiveDoctype = false;
			boolean isActiveMetadata = true;
			boolean isActiveSecGroup = false;
			Set<DocumentAttribute> docAttr = document.getDocumentAttributes();
			for (DocumentAttribute dtAttr : docAttr) {
				if (dtAttr.getAttributeValue().getIsActive() == 'N') {
					isActiveMetadata = false;
					break;
				}
			}

			// For Active Security Group Start
			if (document.getSecurityGroup() != null) {
				if (document.getSecurityGroup().getIsActive() == 'Y')
					isActiveSecGroup = true;
			}
			else {
				isActiveSecGroup = true;
			}

			// End of Security Group
			if (document.getDoctype().getIsActive() == 'Y' && isActiveMetadata && isActiveSecGroup) {
				isActiveDoctype = true;
			}

			model.addAttribute("isActive", isActiveDoctype);

			/*
			 * commented lines needs to be enabled for reference document
			 * list.Result needs to be set in any following maps
			 */
			/*
			 * if (!document.getDocumentReference().isEmpty()) {
			 * Set<DocumentReference> docReference = document
			 * .getDocumentReference(); model.addAttribute("referenceDoc",
			 * docReference); }
			 */
			
			if (!document.getDocumentReference().isEmpty()) {
				Set<DocumentReference> docReference = document.getDocumentReference();
				List<String> securityGroups = (List<String>) session.getAttribute("securityGroups");
				Set<DocumentReference> downloadableDocReference = new HashSet<DocumentReference>();
				Set<DocumentReference> unDownloadableDocReference = new HashSet<DocumentReference>();
				for (DocumentReference documentReference : docReference) {
					if (documentReference.getReferenceDocument().getSecurityGroup() == null) {
						downloadableDocReference.add(documentReference);
						//Form Based URL download restriction
//						if(documentReference.getReferenceDocument().getDiscriminator()=='F'){
//							unDownloadableDocReference.add(documentReference);
//						}
//						else{
//							downloadableDocReference.add(documentReference);	
//						}
					}
					else if (securityGroups.contains(documentReference.getReferenceDocument().getSecurityGroup().getName())) {
						downloadableDocReference.add(documentReference);
					}
					else {
						unDownloadableDocReference.add(documentReference);
					}
				}

				model.addAttribute("downloadablereferenceDoc", downloadableDocReference);
				model.addAttribute("undownloadablereferenceDoc", unDownloadableDocReference);
			}

			// For reviews
			Set<DocumentAttribute> documentAttributeList = document.getDocumentAttributes();
			String department = null;
			String area = null;
			/*String discipline = null;
			String site = null;*/
			String company = null;
			for (Iterator<DocumentAttribute> iterator = documentAttributeList.iterator(); iterator.hasNext();) {
				DocumentAttribute documentAttribute = (DocumentAttribute) iterator.next();
				if (documentAttribute.getAttribute().getId() == 1) {
					department = documentAttribute.getAttributeValue().getValue();
				}
				else if (documentAttribute.getAttribute().getId() == 2) {
					area = documentAttribute.getAttributeValue().getValue();
				}
				/*else if (documentAttribute.getAttribute().getId() == 3) {
					discipline = documentAttribute.getAttributeValue().getValue();
				}
				else if (documentAttribute.getAttribute().getId() == 4) {
					site = documentAttribute.getAttributeValue().getValue();
				}*/
				
				else if (documentAttribute.getAttribute().getId() == 5) {
					company = documentAttribute.getAttributeValue().getValue();
				}
			}

			// List<ReviewNote> reviews = reviewNoteService
			// .findAllReviewsByDocument(document);
			List<ReviewNote> reviews = reviewNoteService.findAllReviewsByWfCase(wfCase);
			Collections.sort(reviews, Collections.reverseOrder());

			// For Comments
			User user = (User) session.getAttribute("LOGGED_IN_USER");

			// ReviewNote LastReviewNotes = reviewNoteService
			// .findByReviewerLastComment(user, document);
			ReviewNote LastReviewNotes = reviewNoteService.findByReviewerLastComment(user, wfCase);
			if (LastReviewNotes != null) {
				canShowCommentsPopUp = false;
			}

			model.addAttribute("canShowCommentsPopUp", canShowCommentsPopUp);
			result.put("Name", document.getName());
			result.put("Document Type", document.getDoctype().getDoctypeName());
			result.put(dtAttributeService.findAttributeByOrder(1).getName(), company);
			result.put(dtAttributeService.findAttributeByOrder(2).getName(), department);
			/*result.put(dtAttributeService.findAttributeByOrder(5).getName(), site);
			result.put(dtAttributeService.findAttributeByOrder(4).getName(), discipline);*/
			result.put(dtAttributeService.findAttributeByOrder(3).getName(), area);
			

			if (actionStatus.equalsIgnoreCase("Published") || actionStatus.equalsIgnoreCase("Abandoned")) {
				result.put("Status-Action", actionStatus);
			}
			else {
				result.put("Status-Action", actionStatus.substring(0, actionStatus.length() - 1));
			}

			result.put("Workflow Process", wfCase.getModel().getName());
			Set<WfAttribute> attr = wfCase.getAttributes();
			for (WfAttribute at : attr) {
				if (at.getValue() == null)
					at.setValue("-");

				if ((at.getType() == WfAttributeType.WF_ATTR_TEXT) || (at.getType() == WfAttributeType.WF_ATTR_DECIMAL)
					|| (at.getType() == WfAttributeType.WF_ATTR_NUMBER)) {
					if (!at.getName().equalsIgnoreCase("FilePath")) {
						// Adding by Karthik.V for Eb Number Name Change
						boolean isInternal = false;
						/*if (at.getName().equalsIgnoreCase("Eb Number") && category.equalsIgnoreCase("Internal")) {
							result.put("Unique Doc ID/Eb Number", at.getValue().toString());
							isInternal = true;
							model.addAttribute("isInternal", isInternal);
						}
						else {*/
							result.put(at.getName(), at.getValue().toString());
						//}
					}
				}
				else if ((at.getType() == WfAttributeType.WF_ATTR_DATE) && (!at.getValue().equals("-"))) {
					result.put(at.getName(), dateFormat.format(at.getValue()));
				}
			}

			result.put("Raised Date & Time", dateTimeFormat.format(document.getDateCreated()));

			// For Edit
			if(document.getReassignOwner()==null){
			result.put("Owner", document.getAuthor());
			}else{
				result.put("Owner", document.getReassignOwner());
			}
			isCheckedout = document.getLocked().equalsIgnoreCase("YES");
			if (isCheckedout == true) {
				if (wfCase.getCaseStatus() == WfCaseStatus.WF_CASE_CANCELLED) {
					result.put("Document Status", "Abandoned");
					model.addAttribute("DocumentStatus", "Abandoned");
				}
				else {
					result.put("Document Status", "Checked out");
					model.addAttribute("DocumentStatus", "Checked out");
				}
			}
			else {
				if (wfCase.getCaseStatus() == WfCaseStatus.WF_CASE_CANCELLED) {
					result.put("Document Status", "Abandoned");
					model.addAttribute("DocumentStatus", "Abandoned");
				}
				else {
					result.put("Document Status", "Checked in");
					model.addAttribute("DocumentStatus", "Checked in");
				}
			}

			String securityGroup;
			List<String> securityGroups = (List<String>) session.getAttribute("securityGroups");

			// For Workflow Completed
			if (document.getWip() == 'N') {
				canShowRevision = true;
				if (document.getSecurityGroup() == null) {
					canShowDownload = true;
					securityGroup = "Open";
					result.put("Security Group", securityGroup);
				}
				else {
					for (String s : securityGroups) {
						if (document.getSecurityGroup().getName().equalsIgnoreCase(s))
							canShowDownload = true;
						securityGroup = document.getSecurityGroup().getName();
						result.put("Security Group", securityGroup);
					}
				}
			}
			else {
				if (document.getSecurityGroup() == null) {
					securityGroup = "Open";
					result.put("Security Group", securityGroup);
				}
				else {
					securityGroup = document.getSecurityGroup().getName();
					result.put("Security Group", securityGroup);
				}
			}

			model.addAttribute("canShowDownload", canShowDownload);
			model.addAttribute("docName", documentName);
			model.addAttribute("documentId", document.getId());
			model.addAttribute("revisionable", document.isRevisionable());
			model.addAttribute("path", path);
			model.addAttribute("reviewDocs", reviews);
		}

		Set<WfAttribute> attr = wfCase.getAttributes();
		attr.removeAll(Util.getDummyWfAttribute());
		Set<WfAttribute> attributeSet = new HashSet<WfAttribute>();
		for (WfAttribute at : attr) {
			if (at.getUserEditable().equalsIgnoreCase("Y")) {
				attributeSet.add(at);
			}
		}

		WfAttribute[] attributes = attributeSet.toArray(new WfAttribute[attributeSet.size()]);
		Arrays.sort(attributes);

		// For Formating the date in edit while coming for the first time
		String tgtDate = null;
		String ebn = null;
		for (WfAttribute at : attributes) {
			if (!at.getValue().equals("-")) {
				if (at.getType() == WfAttributeType.WF_ATTR_DATE) {
					at.setValue(dateFormat.format(at.getValue()));
					if (at.getName().equalsIgnoreCase("Target Date"))
						tgtDate = (String) at.getValue();
				}

				if ((at.getType() == WfAttributeType.WF_ATTR_TEXT) && (at.getName().equalsIgnoreCase("Eb Number"))) {
					ebn = (String) at.getValue();
				}
			}
		}

		if (stepList.size() > 0) {
			for (WfStep s : stepList) {
				WfStep step = caseService.findStepById(s.getId());
				if (s.getId() != 0) {
					if(!adminMeta)
					if (stepAssigned)
						caseService.haveBeenRead(step.getId());

					model.addAttribute("currentStep", step);
					if (step.getNode().getName().equalsIgnoreCase(wfCase.getModel().getStartNode().getName()))
						isStartStep = true;

					Set<WfAction> actions = step.getNode().getActions();
					String jsonVals = "{'vals' : [";
					for (WfAction act : actions) {
						jsonVals += "{'n':'" + act.getName() + "','r':";
						if (act.getReasonRequired() == 'Y') {
							jsonVals += "'y'},";
						}
						else {
							jsonVals += "'n'},";
						}
					}

					jsonVals = jsonVals.substring(0, jsonVals.length() - 1);
					jsonVals += "]}";
					String userAssigned = step.getAssignee();
					if ((userAssigned.startsWith("R:"))) {
						List<User> users = roleService.findUsersForRoleName(userAssigned.substring(2));
						if (users.size() > 1) {
							canPick = true;
						}
					}

					if ((userAssigned.startsWith("RG:"))) {
						String ss = userAssigned.substring(3);
						String[] grSplit = ss.split("\\.");
						Group group = groupService.findGroupByName(grSplit[1]);
						Role role = roleService.findRoleByRoleName(grSplit[0]);
						GroupRole groupRole = groupRoleService.findGroupRole(group, role);
						List<User> users = groupRoleService.getUsersFromGroupRole(groupRole);
						if (users.size() > 1)
							canPick = true;
					}

					model.addAttribute("canPick", canPick);
					model.addAttribute("jsonVals", jsonVals);
					model.addAttribute("actions", step.getActionNames());
				}
			}
		}

		if (wfCase.getCreator().equals(userInfo.getUserName()))
			isOwner = true;

		if (canPick == false || isOwner == true) {
			canShowAllButtons = true;
		}

		if (stepAssigned == true || canShowDownload == true) {
			canShowRefLinks = true;
		}
		//For Feature Code Changes Start
		showFeatures(model,userInfo);
		//End
		if(d.getWip()=='N'){
			model.addAttribute("canShowAdminAbandon", false);
		}
		boolean yesAd=false;
		for(String r:userInfo.getRoles()){
			if(r.equalsIgnoreCase("Admin")){
				yesAd=true;
			}
		}
		if(!yesAd){
		if(d.getDoctype().getVersion().equals("Mj")){
			model.addAttribute("canShowAdminSubVersions", true);
		}
		}
		model.addAttribute("canShowRefLinks", canShowRefLinks);
		model.addAttribute("canShowRevision", canShowRevision);
		model.addAttribute("canShowAllButtons", canShowAllButtons);
		model.addAttribute("stepList", stps);
		model.addAttribute("metaData", result);
		model.addAttribute("caseId", caseId);
		model.addAttribute("path", path);
		model.addAttribute("docAttached", docAttached);
		model.addAttribute("stepAssigned", stepAssigned);
		model.addAttribute("isStartStep", isStartStep);
		model.addAttribute("isOwner", isOwner);
		model.addAttribute("isCheckedout", isCheckedout);
		model.addAttribute("attributes", attributes);
		model.addAttribute("trgDte", tgtDate);
		model.addAttribute("ebn", ebn);
		model.addAttribute("modelName", wfCase.getModel().getName());
		//System.out.println(adminMeta);
		return adminMeta ? "metaDataAdmin" : "metaData";
		
		}

	private boolean checkAssigned(Long caseId, Long stepId, UserInfo userInfo) {

		List<WfStep> steps = caseService.findAssignedStepsForCase(userInfo, caseId);
		for (WfStep s : steps) {
			if (s.getId() == stepId) {
				return true;
			}
		}

		return false;
	}

	private void docListFromTree(Model model, DocListInput docListInput) {

		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");
		List<Document> docList = documentService.findDocumentsForPath(docListInput.getPath());
		List<WfCase> cases = new ArrayList<WfCase>();
		ArrayList<Long> caseIds = new ArrayList<Long>();
		if (docList.size() > 0) {
			for (Document d : docList) {
				caseIds.add(d.getCaseId());
			}

			cases = caseService.getCases(caseIds);
		}

		GridBuilder gb = new GridBuilder();
//		model.addAttribute("docs", gb.buildDocumentList(docList, cases, new ArrayList<WfCase>(), null, null, false,
//			holidayService, caseUserFormsService, userFormsService, userService));
		model.addAttribute("docs", gb.buildDocumentList(docList, cases, new ArrayList<WfCase>(), null, null, false,
				holidayService, caseUserFormsService, userFormsService, userService,documentService));
		model.addAttribute("canCreateNew", true);
		model.addAttribute("path", docListInput.getPath());
		model.addAttribute("taskName", "Filters");
	}
	
	/*private void docListFromTray(Model model, DocListInput docListInput, boolean canShowAll, UserInfo userInfo) {

		List<Long> caseIds = new ArrayList<Long>();
		Map<Long, WfCase> caseMap = new HashMap<Long, WfCase>();
		Map<Long, Document> docMap = new HashMap<Long, Document>();
		Map<Long, CaseStepInfo> csi = new HashMap<Long, CaseStepInfo>();
		Map<Long, Character> readStatusMap = new HashMap<Long, Character>();
		String trayLabel = docListInput.getTaskName();
		List<WfStep> steps = caseService.findAssignedStepsForTrayLabel(userInfo, trayLabel);
		for (WfStep s : steps) {
			caseIds.add(s.getOwningCase().getId());
			caseMap.put(s.getOwningCase().getId(), s.getOwningCase());
			readStatusMap.put(s.getOwningCase().getId(), s.getReadStatus());
		}

		Set<Long> caseidsSet = new HashSet<Long>(caseIds);
		for (long l : caseidsSet) {
			WfCase cs = caseService.findCaseById(l);
			CaseStepInfo ci = new CaseStepInfo(cs);
			List<WfStep> stepList = new ArrayList<WfStep>();
			for (WfStep s : steps) {
				if (s.getOwningCase().getId() == l) {
					stepList.add(s);
					ci.setStepsList(stepList);
					csi.put(cs.getId(), ci);
				}
			}
		}

		List<uk.co.jmr.sdp.domain.Document> trayDocs = documentService.findDocumentsForCase(caseIds);
		for (Document d : trayDocs) {
			docMap.put(d.getCaseId(), d);
		}

		ExtGridBuilder gb = new ExtGridBuilder();
		model.addAttribute("docs", gb.buildDocumentList(csi, steps, docMap, true, readStatusMap, holidayService,
			caseUserFormsService, userFormsService, userService,documentService));

		if (trayLabel == null) {
			model.addAttribute("taskName", "No Task");
		}
		else {
			model.addAttribute("canShowAll", canShowAll);
			model.addAttribute("taskName", trayLabel);
		}
	}

	private void docListAll(Model model, boolean canShowAll, UserInfo userInfo) {
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		Map<Long, CaseStepInfo> csi = new HashMap<Long, CaseStepInfo>();
		Map<Long, Character> readStatusMap = new HashMap<Long, Character>();
		if (tasks.isEmpty()) {
			ExtGridBuilder gb = new ExtGridBuilder();
			model.addAttribute("docs", gb.buildDocumentList(csi, null, null, true, readStatusMap, holidayService,
				caseUserFormsService, userFormsService, userService,documentService));
			model.addAttribute("taskName", "No Task");
			
		}
		else {
			List<Long> caseIds = new ArrayList<Long>();
			Map<Long, WfCase> caseMap = new HashMap<Long, WfCase>();
			Map<Long, Document> docMap = new HashMap<Long, Document>();
			List<WfStep> steps = caseService.findAssignedSteps(userInfo);
			for (WfStep s : steps) {
				caseIds.add(s.getOwningCase().getId());
				caseMap.put(s.getOwningCase().getId(), s.getOwningCase());
				readStatusMap.put(s.getOwningCase().getId(), s.getReadStatus());
			}

			Set<Long> caseidsSet = new HashSet<Long>(caseIds);
			for (long l : caseidsSet) {
				WfCase cs = caseService.findCaseById(l);
				CaseStepInfo ci = new CaseStepInfo(cs);
				List<WfStep> stepList = new ArrayList<WfStep>();
				for (WfStep s : steps) {
					if (s.getOwningCase().getId() == l) {
						stepList.add(s);
						ci.setStepsList(stepList);
						csi.put(cs.getId(), ci);
					}
				}
			}

			List<uk.co.jmr.sdp.domain.Document> trayDocs = documentService.findDocumentsForCase(caseIds);
			for (Document d : trayDocs) {
				docMap.put(d.getCaseId(), d);
			}

			ExtGridBuilder gb = new ExtGridBuilder();
			model.addAttribute("docs", gb.buildDocumentList(csi, steps, docMap, true, readStatusMap, holidayService,
				caseUserFormsService, userFormsService, userService,documentService));
			model.addAttribute("taskName", "All");
			model.addAttribute("canShowAll", canShowAll);
		}
	}*/
	
	/*private boolean stepCheck(WfStep s,User user,SimpleDateFormat dateFormat,Date holidayFromDate,Date holidayToDate,Date date
			,String currentDate,boolean stepCheck){
		WfCase caseId=s.getOwningCase();
		Document d=documentService.findDocumentForCaseId(caseId.getId());
		
		
		if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
			if(dateFormat.format(holidayFromDate).equals(dateFormat.format(date))){	
		if(s.getStatus().equals("WF_STEP_ASSIGNED"))	{
			if(d.getAuthor().equals(user.getUserName())){
				stepCheck=true;
			}else{
			String stpDate=dateFormat.format(s.getDateCreated());
		if(stpDate.equals(currentDate)){
			stepCheck=false;
		}
		}
		}
		}else if(!holidayFromDate.after(date)){
			if(holidayToDate.after(date)){
			if(s.getStatus().equals("WF_STEP_ASSIGNED"))	{
				
				if(d.getAuthor().equals(user.getUserName())){
					stepCheck=true;
				}else{
			if(s.getDateCreated().before(holidayFromDate)){
					stepCheck=true;
				 }
			if(s.getDateCreated().after(holidayFromDate)){
				stepCheck=false;
			 }
			if(dateFormat.format(s.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
				stepCheck=false;
			}
			}
			}
			}else if(dateFormat.format(holidayToDate).equals(dateFormat.format(date))){
				if(s.getStatus().equals("WF_STEP_ASSIGNED"))	{
					
					if(d.getAuthor().equals(user.getUserName())){
						stepCheck=true;
					}else{
				if(s.getDateCreated().before(holidayFromDate)){
					stepCheck=true;
				 }
			if(s.getDateCreated().after(holidayFromDate)){
				stepCheck=false;
			 }
			if(dateFormat.format(s.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
				stepCheck=false;
			}
				}
			}
			}
		}
		}
		
		return stepCheck;
	}
	
	private boolean docCheckSupport(boolean docCheck,Date holidayFromDate,Document d,User user,List<WfStep> stps,SimpleDateFormat dateFormat){
		for(WfStep stp:stps){
			if(stp.getStatus().equals("WF_STEP_ASSIGNED"))	{
				if(d.getAuthor().equals(user.getUserName())){
					docCheck=true;
				}else{
			if(stp.getDateCreated().before(holidayFromDate)){
				docCheck=true;
			 }
		if(stp.getDateCreated().after(holidayFromDate)){
			docCheck=false;
		 }
		if(dateFormat.format(stp.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
			docCheck=false;
		}
		}
			}
	}
		return docCheck;
	}
	
	private boolean docCheck(User user,SimpleDateFormat dateFormat,Date holidayFromDate,Date holidayToDate,Date date,Document d,
			boolean docCheck){
		if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
			if(dateFormat.format(holidayFromDate).equals(dateFormat.format(date))){	
			long caseId=d.getCaseId();
			List<WfStep> stps=caseService.findAssignedStepsForCase(caseId);
			docCheck=docCheckSupport(docCheck, holidayFromDate, d, user, stps, dateFormat);
		}else if(!holidayFromDate.after(date)){
			if(holidayToDate.after(date)){
			long caseId=d.getCaseId();
			List<WfStep> stps=caseService.findAssignedStepsForCase(caseId);
			docCheck=docCheckSupport(docCheck, holidayFromDate, d, user, stps, dateFormat);
			}
			else if(dateFormat.format(holidayToDate).equals(dateFormat.format(date))){
				long caseId=d.getCaseId();
				List<WfStep> stps=caseService.findAssignedStepsForCase(caseId);
				docCheck=docCheckSupport(docCheck, holidayFromDate, d, user, stps, dateFormat);
			}
		  }
		}
		return docCheck;
	}*/
	
	private boolean stepCheck(WfStep s,User user,SimpleDateFormat dateFormat,Date holidayFromDate,Date holidayToDate,Date date
			,String currentDate,boolean stepCheck) throws ParseException{
		WfCase caseId=s.getOwningCase();
		Document d=documentService.findDocumentForCaseId(caseId.getId());
		
		
		if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
			if(dateFormat.format(holidayFromDate).equals(dateFormat.format(date))){	
		if(s.getStatus().equals("WF_STEP_ASSIGNED"))	{
			if(d.getAuthor().equals(user.getUserName())){
				stepCheck=true;
			}else{
			String stpDate=dateFormat.format(s.getDateCreated());
		if(stpDate.equals(currentDate)){
			stepCheck=false;
		}
		}
		}
		}else if(!holidayFromDate.after(date)){
			Date ht=dateFormat.parse(dateFormat.format(holidayToDate));
			Date hf=dateFormat.parse(dateFormat.format(holidayFromDate));
			Date da=dateFormat.parse(currentDate);
			String st=dateFormat.format(s.getDateCreated());
			Date std=dateFormat.parse(st);
			
			if(!ht.after(da)){
			if(s.getStatus().equals("WF_STEP_ASSIGNED"))	{
				if(d.getAuthor().equals(user.getUserName())){
					stepCheck=true;
				}else{
					if(std.after(hf) || std.equals(hf)){
						stepCheck=false;
					}
					
			
			}
			}
			}
			
		}
		}
		
		return stepCheck;
	}
	
	private boolean docCheckSupport(boolean docCheck,Date holidayFromDate,Document d,User user,List<WfStep> stps,SimpleDateFormat dateFormat){
		for(WfStep stp:stps){
			if(stp.getStatus().equals("WF_STEP_ASSIGNED"))	{
				if(d.getAuthor().equals(user.getUserName())){
					docCheck=true;
				}else{
			if(stp.getDateCreated().before(holidayFromDate)){
				docCheck=true;
			 }
		if(stp.getDateCreated().after(holidayFromDate)){
			docCheck=false;
		 }
		if(dateFormat.format(stp.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
			docCheck=false;
		}
		}
			}
	}
		return docCheck;
	}
	
	private boolean docCheck(User user,SimpleDateFormat dateFormat,Date holidayFromDate,Date holidayToDate,Date date,Document d,
			boolean docCheck) throws ParseException{
		long caseId;
		List<WfStep> stps=null;
		Date ht=dateFormat.parse(dateFormat.format(holidayToDate));
		Date hf=dateFormat.parse(dateFormat.format(holidayFromDate));
		Date td=dateFormat.parse(dateFormat.format(date));
		if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
			if(dateFormat.format(holidayFromDate).equals(dateFormat.format(date))){	
			caseId=d.getCaseId();
			stps=caseService.findAssignedStepsForCase(caseId);
			docCheck=docCheckSupport(docCheck, holidayFromDate, d, user, stps, dateFormat);
		}	
			else if(!hf.after(td)){
			if(ht.after(td) || ht.equals(td)){
			caseId=d.getCaseId();
			stps=caseService.findAssignedStepsForCase(caseId);
			docCheck=docCheckSupport(docCheck, holidayFromDate, d, user, stps, dateFormat);
			}
			
		  }
		}
		return docCheck;
	}
	
	
	private void docListFromTray(Model model, DocListInput docListInput, boolean canShowAll, UserInfo userInfo) throws ParseException {

		List<Long> caseIds = new ArrayList<Long>();
		Map<Long, WfCase> caseMap = new HashMap<Long, WfCase>();
		Map<Long, Document> docMap = new HashMap<Long, Document>();
		Map<Long, CaseStepInfo> csi = new HashMap<Long, CaseStepInfo>();
		Map<Long, Character> readStatusMap = new HashMap<Long, Character>();
		String trayLabel = docListInput.getTaskName();
		
		User user=userService.findUserById(userInfo.getUserId());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date=new Date();
		Date holidayFromDate=null;
		Date holidayToDate=null;
		boolean stepCheck=true;
		boolean docCheck=true;
		if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
			if(user.getHolidayFromDate().equals(user.getHolidayToDate())){
				holidayFromDate=user.getHolidayFromDate();
				 holidayToDate=holidayFromDate;
			}else{
				holidayFromDate=user.getHolidayFromDate();
				holidayToDate=user.getHolidayToDate();
			}
		}
		String currentDate=dateFormat.format(date);
		
		List<WfStep> steps = caseService.findAssignedStepsForTrayLabel(userInfo, trayLabel);
		for (WfStep s : steps) {
			stepCheck=true;
			if(holidayFromDate!=null){
			if(!date.after(holidayToDate))
			stepCheck=stepCheck(s, user, dateFormat, holidayFromDate, holidayToDate, date, currentDate, stepCheck);
			
			if(dateFormat.format(date).equals(dateFormat.format(holidayToDate)))
			stepCheck=stepCheck(s, user, dateFormat, holidayFromDate, holidayToDate, date, currentDate, stepCheck);
			}
			if(stepCheck==true){
				Document d=documentService.findDocumentForCaseId(s.getOwningCase().getId());
				Attribute attribute=dtAttributeService.findAttributeByOrder(1);
				DocumentAttribute da=documentService.findDocumentAttributeByDocAttrId(d, attribute);
				if(da!=null){
					CompanyUser cu=companyUserService.findCompanyUserByUserAndAttrValue(user, da.getAttributeValue());
					if(da.getAttributeValue().getValue().equals(cu !=null ? cu.getAttrValue().getValue():null)){
				caseIds.add(s.getOwningCase().getId());
				caseMap.put(s.getOwningCase().getId(), s.getOwningCase());
				readStatusMap.put(s.getOwningCase().getId(), s.getReadStatus());
					}
				}else{
					UserForms uf=userFormsService.findUserFormsById(Long.parseLong(d.getUserFormId()));
					AttributeValue at=dtAttributeValueService.findDtAttrValueById(uf.getCompanyId());
					if(at!=null){
					CompanyUser cu=companyUserService.findCompanyUserByUserAndAttrValue(user, dtAttributeValueService.findDtAttrValueById(uf.getCompanyId()));
					if(at.getValue().equals(cu!=null ? cu.getAttrValue().getValue():null)){
						caseIds.add(s.getOwningCase().getId());
						caseMap.put(s.getOwningCase().getId(), s.getOwningCase());
						readStatusMap.put(s.getOwningCase().getId(), s.getReadStatus());
						}
					}
				}
			}
		}
		Set<Long> caseidsSet = new HashSet<Long>(caseIds);
		for (long l : caseidsSet) {
			WfCase cs = caseService.findCaseById(l);
			CaseStepInfo ci = new CaseStepInfo(cs);
			List<WfStep> stepList = new ArrayList<WfStep>();
			for (WfStep s : steps) {
				if (s.getOwningCase().getId() == l) {
					stepList.add(s);
					ci.setStepsList(stepList);
					csi.put(cs.getId(), ci);
								
					}
			}
		}
		List<uk.co.jmr.sdp.domain.Document> trayDocs = documentService.findDocumentsForCase(caseIds);
		List<Document> newTrayDocs=new ArrayList<Document>();
		List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
		
		for(Document d:trayDocs){
		Set<DocumentAttribute> das=d.getDocumentAttributes();
		
		if(!das.isEmpty()){
		for(DocumentAttribute da:das){
		if(da.getAttribute().getOrder()==1){
		for(CompanyUser cu:cus){
		if(da.getAttributeValue().getValue().equals(cu.getAttrValue().getValue())){
		newTrayDocs.add(d);
					}
				}
			}
		}
		}else{
			UserForms uf=userFormsService.findUserFormsById(Long.parseLong(d.getUserFormId()));
			AttributeValue atv=dtAttributeValueService.findDtAttrValueById(uf.getCompanyId());
			for(CompanyUser cu:cus){
				if(atv.getValue().equals(cu.getAttrValue().getValue())){
					newTrayDocs.add(d);
				}
			}
		}
	}
		for (Document d : newTrayDocs) {
			docCheck=true;
			if(holidayFromDate!=null){
			if(!date.after(holidayToDate))
			docCheck=docCheck(user, dateFormat, holidayFromDate, holidayToDate, date, d, docCheck);
			
			if(dateFormat.format(date).equals(dateFormat.format(holidayToDate)))
			docCheck=docCheck(user, dateFormat, holidayFromDate, holidayToDate, date, d, docCheck);
			}
			
			// Sujith for Holiday wise document filter
			  if(docCheck==true){
				docMap.put(d.getCaseId(),d);
			}
			
			// Sujith for Company Wise Document Filter
			/*if(docCheck==true){
				Set<DocumentAttribute> das=d.getDocumentAttributes();
				List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
				for(DocumentAttribute da:das){
				if(da.getAttribute().getOrder()==1){
				for(CompanyUser cu:cus){
				if(da.getAttributeValue().equals(cu.getAttrValue().getValue())){
				docMap.put(d.getCaseId(),d);
							}
						}
					}
				}
			}*/
		}

		ExtGridBuilder gb = new ExtGridBuilder();
		model.addAttribute("docs", gb.buildDocumentList(csi, steps, docMap, true, readStatusMap, holidayService,
			caseUserFormsService, userFormsService, userService,documentService, reviewNoteService));

		if (trayLabel == null) {
			model.addAttribute("taskName", "No Task");
		}
		else {
			model.addAttribute("canShowAll", canShowAll);
			model.addAttribute("taskName", trayLabel);
		}
	}

	private void docListAll(Model model, boolean canShowAll, UserInfo userInfo) throws ParseException {
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		Map<Long, CaseStepInfo> csi = new HashMap<Long, CaseStepInfo>();
		Map<Long, Character> readStatusMap = new HashMap<Long, Character>();
		
		User user=userService.findUserById(userInfo.getUserId());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date=new Date();
		Date holidayFromDate=null;
		Date holidayToDate=null;
		boolean stepCheck=true;
		boolean docCheck=true;
		if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
			if(user.getHolidayFromDate().equals(user.getHolidayToDate())){
				holidayFromDate=user.getHolidayFromDate();
				 holidayToDate=holidayFromDate;
			}else{
				holidayFromDate=user.getHolidayFromDate();
				holidayToDate=user.getHolidayToDate();
			}
		}
		String currentDate=dateFormat.format(date);
		
		if (tasks.isEmpty()) {
			ExtGridBuilder gb = new ExtGridBuilder();
			model.addAttribute("docs", gb.buildDocumentList(csi, null, null, true, readStatusMap, holidayService,
				caseUserFormsService, userFormsService, userService,documentService, reviewNoteService));
			model.addAttribute("taskName", "No Task");
			
		}
		else {
			List<Long> caseIds = new ArrayList<Long>();
			Map<Long, WfCase> caseMap = new HashMap<Long, WfCase>();
			Map<Long, Document> docMap = new HashMap<Long, Document>();
			List<WfStep> steps = caseService.findAssignedSteps(userInfo);
			for (WfStep s : steps) {
				stepCheck=true;
				if(holidayFromDate!=null){
				if(!date.after(holidayToDate))
				stepCheck=stepCheck(s, user, dateFormat, holidayFromDate, holidayToDate, date, currentDate, stepCheck);
				
				if(dateFormat.format(date).equals(dateFormat.format(holidayToDate)))
				stepCheck=stepCheck(s, user, dateFormat, holidayFromDate, holidayToDate, date, currentDate, stepCheck);
				}
				/*WfCase caseId=s.getOwningCase();
				Document d=documentService.findDocumentForCaseId(caseId.getId());
				
				if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
					if(dateFormat.format(holidayFromDate).equals(dateFormat.format(date))){	
				if(s.getStatus().equals("WF_STEP_ASSIGNED"))	{
					if(d.getAuthor().equals(user.getUserName())){
						stepCheck=true;
					}else{
					
				if(s.getDateCreated().before(holidayFromDate)){
						stepCheck=true;
					 }
				if(s.getDateCreated().after(holidayFromDate)){
					stepCheck=false;
				 }
				if(dateFormat.format(s.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
					stepCheck=false;
				}
				}
				}
				}else if(!holidayFromDate.after(date)){
				if(holidayToDate.after(date)){
					if(s.getStatus().equals("WF_STEP_ASSIGNED"))	{
						String stpDate=dateFormat.format(s.getDateCreated());
						if(d.getAuthor().equals(user.getUserName())){
							stepCheck=true;
						}else{
					if(s.getDateCreated().before(holidayFromDate)){
						stepCheck=true;
					 }
				if(s.getDateCreated().after(holidayFromDate)){
					stepCheck=false;
				 }
				if(dateFormat.format(s.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
					stepCheck=false;
				}
					}
					}
				}else if(dateFormat.format(holidayToDate).equals(dateFormat.format(date))){
					if(s.getStatus().equals("WF_STEP_ASSIGNED"))	{
						
						if(d.getAuthor().equals(user.getUserName())){
							stepCheck=true;
						}else{
						
					if(s.getDateCreated().before(holidayFromDate)){
						stepCheck=true;
					 }
				if(s.getDateCreated().after(holidayFromDate)){
					stepCheck=false;
				 }
				if(dateFormat.format(s.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
					stepCheck=false;
				}
					}
				}
				}
				}
				}*/
				if(stepCheck==true){
					Document d=documentService.findDocumentForCaseId(s.getOwningCase().getId());
					Attribute attribute=dtAttributeService.findAttributeByOrder(1);
					DocumentAttribute da=documentService.findDocumentAttributeByDocAttrId(d, attribute);
					if(da!=null){
						CompanyUser cu=companyUserService.findCompanyUserByUserAndAttrValue(user, da.getAttributeValue());
						if(da.getAttributeValue().getValue().equals(cu !=null ? cu.getAttrValue().getValue():null)){
					caseIds.add(s.getOwningCase().getId());
					caseMap.put(s.getOwningCase().getId(), s.getOwningCase());
					readStatusMap.put(s.getOwningCase().getId(), s.getReadStatus());
						}
				}else{
					UserForms uf=userFormsService.findUserFormsById(Long.parseLong(d.getUserFormId()));
					//CompanyUser cu=companyUserService.findCompanyUserByUserAndAttrValue(user, dtAttributeValueService.findDtAttrValueById(uf.getCompanyId()));
					AttributeValue at=dtAttributeValueService.findDtAttrValueById(uf.getCompanyId());
					if(at!=null){
					CompanyUser cu=companyUserService.findCompanyUserByUserAndAttrValue(user, dtAttributeValueService.findDtAttrValueById(uf.getCompanyId()));
					if(at.getValue().equals(cu!=null ? cu.getAttrValue().getValue():null)){
						caseIds.add(s.getOwningCase().getId());
						caseMap.put(s.getOwningCase().getId(), s.getOwningCase());
						readStatusMap.put(s.getOwningCase().getId(), s.getReadStatus());
						  }
					   }
					}
				}
			}
			

			Set<Long> caseidsSet = new HashSet<Long>(caseIds);
			for (long l : caseidsSet) {
				WfCase cs = caseService.findCaseById(l);
				CaseStepInfo ci = new CaseStepInfo(cs);
				List<WfStep> stepList = new ArrayList<WfStep>();
				for (WfStep s : steps) {
					if (s.getOwningCase().getId() == l) {
						stepList.add(s);
						ci.setStepsList(stepList);
						csi.put(cs.getId(), ci);
									
						}
				}
			}

			List<uk.co.jmr.sdp.domain.Document> trayDocs = documentService.findDocumentsForCase(caseIds);
			
			List<Document> newTrayDocs=new ArrayList<Document>();
			List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
			
			for(Document d:trayDocs){
			Set<DocumentAttribute> das=d.getDocumentAttributes();
			
			if(!das.isEmpty()){
			for(DocumentAttribute da:das){
			if(da.getAttribute().getOrder()==1){
			for(CompanyUser cu:cus){
			if(da.getAttributeValue().getValue().equals(cu.getAttrValue().getValue())){
			newTrayDocs.add(d);
						}
					}
				}
			}
			}else{
				UserForms uf=userFormsService.findUserFormsById(Long.parseLong(d.getUserFormId()));
				AttributeValue atv=dtAttributeValueService.findDtAttrValueById(uf.getCompanyId());
				for(CompanyUser cu:cus){
					if(atv.getValue().equals(cu.getAttrValue().getValue())){
						newTrayDocs.add(d);
					}
				}
			}
		}
			
			for (Document d : newTrayDocs) {
				docCheck=true;
				if(holidayFromDate!=null){
				if(!date.after(holidayToDate))
				docCheck=docCheck(user, dateFormat, holidayFromDate, holidayToDate, date, d, docCheck);
				
				if(dateFormat.format(date).equals(dateFormat.format(holidayToDate)))
				docCheck=docCheck(user, dateFormat, holidayFromDate, holidayToDate, date, d, docCheck);
				}
				/*if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
					if(dateFormat.format(holidayFromDate).equals(dateFormat.format(date))){
					long caseId=d.getCaseId();
					List<WfStep> stps=caseService.findAssignedStepsForCase(caseId);
					for(WfStep stp:stps){
						if(stp.getStatus().equals("WF_STEP_ASSIGNED"))	{
							if(d.getAuthor().equals(user.getUserName())){
								docCheck=true;
							}else{
						if(stp.getDateCreated().before(holidayFromDate)){
						docCheck=true;
					 }
				if(stp.getDateCreated().after(holidayFromDate)){
					docCheck=false;
				 }
				if(dateFormat.format(stp.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
					docCheck=false;
				}
					}
						}
					
				}
				}else if(!holidayFromDate.after(date)){
					if(holidayToDate.after(date)){
					long caseId=d.getCaseId();
					List<WfStep> stps=caseService.findAssignedStepsForCase(caseId);
					for(WfStep stp:stps){
						if(stp.getStatus().equals("WF_STEP_ASSIGNED"))	{
							if(d.getAuthor().equals(user.getUserName())){
								docCheck=true;
							}else{
						if(stp.getDateCreated().before(holidayFromDate)){
						docCheck=true;
					 }
				if(stp.getDateCreated().after(holidayFromDate)){
					docCheck=false;
				 }
				if(dateFormat.format(stp.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
					docCheck=false;
				}
						}
						}
					}
				}else if(dateFormat.format(holidayToDate).equals(dateFormat.format(date))){
					long caseId=d.getCaseId();
					List<WfStep> stps=caseService.findAssignedStepsForCase(caseId);
					for(WfStep stp:stps){
						if(stp.getStatus().equals("WF_STEP_ASSIGNED"))	{
							if(d.getAuthor().equals(user.getUserName())){
								docCheck=true;
							}else{
						if(stp.getDateCreated().before(holidayFromDate)){
						docCheck=true;
					 }
				if(stp.getDateCreated().after(holidayFromDate)){
					docCheck=false;
				 }
				if(dateFormat.format(stp.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
					docCheck=false;
				}
					}
						}
				}
				}
				}
				}*/
				// Sujith for Holiday wise document filter
				  if(docCheck==true){
					docMap.put(d.getCaseId(),d);
				}
				
				// Sujith for Company Wise Document Filter
				/*if(docCheck==true){
					Set<DocumentAttribute> das=d.getDocumentAttributes();
					List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
					for(DocumentAttribute da:das){
					if(da.getAttribute().getOrder()==1){
					for(CompanyUser cu:cus){
					if(da.getAttributeValue().equals(cu.getAttrValue().getValue())){
					docMap.put(d.getCaseId(),d);
								}
							}
						}
					}
				}*/
			
				
			}
			
			ExtGridBuilder gb = new ExtGridBuilder();
			model.addAttribute("docs", gb.buildDocumentList(csi, steps, docMap, true, readStatusMap, holidayService,
				caseUserFormsService, userFormsService, userService,documentService, reviewNoteService));
			model.addAttribute("taskName", "All");
			model.addAttribute("canShowAll", canShowAll);
		}
		
		
	}
	
	
	private void docListFromSearchForms(HttpSession session, Model model, DocListInput docListInput, boolean canShowAll,
			UserInfo userInfo){
		List<uk.co.jmr.sdp.domain.Document> searchedDocs = null;
		List<WfCase> casesWithDocs = new ArrayList<WfCase>();
		List<WfCase> casesWithoutDocs = new ArrayList<WfCase>();
		User user=userService.findUserById(userInfo.getUserId());
		//Util.setToString(user.getSecGroups());
		// get any matching forms
		casesWithoutDocs.addAll(formsReportingService.searchForms(docListInput,user.getSecGroups()));
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		
		
		if (!tasks.isEmpty()) {
			model.addAttribute("canShowAll", canShowAll);
		}

		List<WfCase> searchedDocWithoutOpen=new ArrayList<WfCase>();
		List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
		List<WfCase> newCaseWithoutDocs=new ArrayList<WfCase>();
		
		for(WfCase wfc:casesWithoutDocs){
			Document d=documentService.findDocumentForCaseId(wfc.getId());
			UserForms uf=userFormsService.findUserFormsById(Long.parseLong(d.getUserFormId()));
			AttributeValue at=dtAttributeValueService.findDtAttrValueById(uf.getCompanyId());
			for(CompanyUser cu:cus){
				if((cu.getAttrValue()!=null ? cu.getAttrValue().getValue() : null).equals(at!=null ? at.getValue() : null)){
					newCaseWithoutDocs.add(wfc);
				}
			}
		}
		
		
		for(WfCase c:newCaseWithoutDocs){
			if(userInfo.getRoles().equals("Third Party"))
				searchedDocWithoutOpen.add(c);
		}
		
		
	    boolean isThirdParty=false;
	    
	    
	    for(String s:userInfo.getRoles()){
	    	if(s.equals("Third Party")){
	    		isThirdParty=true;
	    	}
	    }
	if(isThirdParty==true){
		if (searchedDocWithoutOpen.isEmpty()) {
			model.addAttribute("taskName", "No items match your search");
		}

		else {
			model.addAttribute("taskName", "Search Results");
		}
	    
	    GridBuilder gb = new GridBuilder();
		model.addAttribute("canCreateNew", false);
//		model.addAttribute("docs", gb.buildDocumentList(searchedDocs, casesWithDocs, casesWithoutDocs, null, null, false,
//			holidayService, caseUserFormsService, userFormsService, userService));
		model.addAttribute("docs", gb.buildDocumentList(searchedDocs, casesWithDocs, searchedDocWithoutOpen, null, null, false,
				holidayService, caseUserFormsService, userFormsService, userService,documentService));
	}
	
//		if (!casesWithoutDocs.isEmpty() && docListInput.getDocTypeId() != -1) {
//			String[] keywordsArray = Util.split(docListInput.getKeywords());
//			String[] ebNoArray = Util.split(docListInput.getEbNo());
//			searchedDocs = documentService.findDocumentsForSearch(docListInput.getDocType(), docListInput.getAuthor(), ebNoArray,
//				docListInput.getDocumentName(), keywordsArray, docListInput.getRelevantDateFrom(),
//				docListInput.getRelevantDateTo());
//
//			Long disciplineId = (Long) session.getAttribute("disciplineId");
//			Long siteId = (Long) session.getAttribute("siteId");
//			SearchFilter filter;
//			if ((disciplineId != null && siteId != null)) {
//				filter = new SearchFilter(searchedDocs);
//				searchedDocs = filter.restrictListBasedOnAttributeValue(disciplineId, siteId);
//			}
//			else if (disciplineId != null || siteId != null) {
//				filter = new SearchFilter(searchedDocs);
//				long id = (disciplineId != null) ? disciplineId : siteId;
//				searchedDocs = filter.restrictListBasedOnAttributeValue(id);
//			}
//
//			if (searchedDocs.isEmpty()) {
//				model.addAttribute("taskName", "No items match your search");
//			}
//			else {
//				model.addAttribute("taskName", "Search Results");
//			}
//
//			Collections.sort(searchedDocs, Collections.reverseOrder());
//			ArrayList<Long> caseIds = new ArrayList<Long>();
//			if (searchedDocs.size() > 0) {
//				for (Document d : searchedDocs) {
//					caseIds.add(d.getCaseId());
//				}
//
//				casesWithDocs.addAll(caseService.getCases(caseIds));
//			}
//		}
	else{
		
		if (newCaseWithoutDocs.isEmpty()) {
			model.addAttribute("taskName", "No items match your search");
		}

		else {
			model.addAttribute("taskName", "Search Results");
		}
		
		GridBuilder gb = new GridBuilder();
		model.addAttribute("canCreateNew", false);
//		model.addAttribute("docs", gb.buildDocumentList(searchedDocs, casesWithDocs, casesWithoutDocs, null, null, false,
//			holidayService, caseUserFormsService, userFormsService, userService));
		model.addAttribute("docs", gb.buildDocumentList(searchedDocs, casesWithDocs, newCaseWithoutDocs, null, null, false,
				holidayService, caseUserFormsService, userFormsService, userService,documentService));
		}
	}
	
	
	private void docListFromSearchDocuments(HttpSession session, Model model, DocListInput docListInput, boolean canShowAll,
			UserInfo userInfo){
		
		User user=userService.findUserById(docListInput.getUserId());
		List<uk.co.jmr.sdp.domain.Document> searchedDocs = null;
		List<WfCase> casesWithDocs = new ArrayList<WfCase>();
		List<WfCase> casesWithoutDocs = new ArrayList<WfCase>();
		
		List<TaskSummary> tasks = caseService
				.findTrayLabelSummaryForUser(userInfo);
		if (!tasks.isEmpty()) {
			model.addAttribute("canShowAll", canShowAll);
		}
		String[] keywordsArray = Util.split(docListInput.getKeywords());
		String[] ebNoArray=Util.split(docListInput.getEbNo());
	    searchedDocs = documentService
				.findDocumentsOnlySearch(docListInput.getAuthor(),ebNoArray,
						docListInput.getDocumentName(), keywordsArray,
						docListInput.getDateCreatedFrom(),
						docListInput.getDateCreatedTo(),docListInput.getRelevantDateFrom(),docListInput.getRelevantDateTo(),docListInput.getDocType());
	    
	    
	    List<Document> searchedDocWithoutOpen=new ArrayList<Document>();
	    boolean isThirdParty=false;
	    
	    
	    List<SecurityGroup> sg=securityGroupService.findSecurityGroupsForUser(user);
	    
	    
	    for(String s:userInfo.getRoles()){
	    	if(s.equals("Third Party")){
	    		isThirdParty=true;
	    	}
	    }
	    
	    List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
	    List<Document> newDocsWithoutOpen=new ArrayList<Document>();
	    for(Document docs:searchedDocs){
	    	for(CompanyUser cu:cus){
	    		if(documentService.findDocumentAttributeByDocAttrValue(docs, cu.getAttrValue())!=null){
	    			newDocsWithoutOpen.add(docs);
	    		}
	    	}
	    }
	    
	if(isThirdParty==true){
		
		
	    	
	    for(Document d:newDocsWithoutOpen){
	    	if(d.getSecurityGroup()!=null){
	    		for(SecurityGroup sg1:sg){
	    	    	if(sg1.getName().equals(d.getSecurityGroup().getName()))
	    	    		searchedDocWithoutOpen.add(d);
	    		}
	    	}
	    	if(d.getSecurityGroup()==null){
	    		if(d.getAuthor().equals(userInfo.getUserName())){
	    			searchedDocWithoutOpen.add(d);
	    		}
	    	}
	    }
	    
	    
	    
	    
	    
	    Long projectId = (Long) session.getAttribute("projectId");
		Long categoryId = (Long) session.getAttribute("categoryId");
	    Long disciplineId = (Long) session.getAttribute("disciplineId");
		Long siteId = (Long) session.getAttribute("siteId");
		SearchFilter filter = null;
		if ((projectId!=null && categoryId!=null  && disciplineId != null && siteId != null)) {
			filter = new SearchFilter(newDocsWithoutOpen);
			searchedDocs=filter.restrictListBasedOnAttributeValue(projectId, categoryId, disciplineId, siteId);

		}else if (projectId!=null || categoryId!=null || disciplineId != null || siteId != null) {
			if(projectId!=null){
				filter = new SearchFilter(searchedDocWithoutOpen);
				searchedDocWithoutOpen=filter.restrictListBasedOnAttributeValue(projectId);
			}
			if(categoryId!=null){   
				filter = new SearchFilter(searchedDocWithoutOpen);
				searchedDocWithoutOpen=filter.restrictListBasedOnAttributeValue(categoryId);
			}
			if(disciplineId!=null){
				filter = new SearchFilter(searchedDocWithoutOpen);
				searchedDocWithoutOpen=filter.restrictListBasedOnAttributeValue(disciplineId);
			}
			if(siteId!=null){
				filter = new SearchFilter(searchedDocWithoutOpen);
				searchedDocWithoutOpen=filter.restrictListBasedOnAttributeValue(siteId);
			}	
		}
	    
		if (searchedDocWithoutOpen.isEmpty()) {
			model.addAttribute("taskName", "No items match your search");
		}

		else {
			model.addAttribute("taskName", "Search Results");
		}
		Collections.sort(searchedDocWithoutOpen, Collections.reverseOrder());
		ArrayList<Long> caseIds = new ArrayList<Long>();
		if (searchedDocs.size() > 0) {
			for (Document d : searchedDocs) {
				caseIds.add(d.getCaseId());
			}
			casesWithDocs = caseService.getCases(caseIds);
		}
		GridBuilder gb = new GridBuilder();
		model.addAttribute("canCreateNew", false);
		model.addAttribute("docs", gb.buildDocumentList(searchedDocWithoutOpen, casesWithDocs, casesWithoutDocs, null, null, false,
				holidayService, caseUserFormsService, userFormsService, userService,documentService));
	}
	 else{
		 
		 
		 
	    Long projectId = (Long) session.getAttribute("projectId");
		Long categoryId = (Long) session.getAttribute("categoryId");
	    Long disciplineId = (Long) session.getAttribute("disciplineId");
		Long siteId = (Long) session.getAttribute("siteId");
		SearchFilter filter = null;
		if ((projectId!=null && categoryId!=null  && disciplineId != null && siteId != null)) {
			filter = new SearchFilter(newDocsWithoutOpen);
			searchedDocs=filter.restrictListBasedOnAttributeValue(projectId, categoryId, disciplineId, siteId);

		}else if (projectId!=null || categoryId!=null || disciplineId != null || siteId != null) {
			if(projectId!=null){
				filter = new SearchFilter(newDocsWithoutOpen);
				searchedDocs=filter.restrictListBasedOnAttributeValue(projectId);
			}
			if(categoryId!=null){   
				filter = new SearchFilter(newDocsWithoutOpen);
				searchedDocs=filter.restrictListBasedOnAttributeValue(categoryId);
			}
			if(disciplineId!=null){
				filter = new SearchFilter(newDocsWithoutOpen);
				searchedDocs=filter.restrictListBasedOnAttributeValue(disciplineId);
			}
			if(siteId!=null){
				filter = new SearchFilter(newDocsWithoutOpen);
				searchedDocs=filter.restrictListBasedOnAttributeValue(siteId);
			}	
		}
	    
		if (searchedDocs.isEmpty()) {
			model.addAttribute("taskName", "No items match your search");
		}

		else {
			model.addAttribute("taskName", "Search Results");
		}
		Collections.sort(searchedDocs, Collections.reverseOrder());
		ArrayList<Long> caseIds = new ArrayList<Long>();
		if (searchedDocs.size() > 0) {
			for (Document d : searchedDocs) {
				caseIds.add(d.getCaseId());
			}
			casesWithDocs = caseService.getCases(caseIds);
		}
		GridBuilder gb = new GridBuilder();
		model.addAttribute("canCreateNew", false);
		model.addAttribute("docs", gb.buildDocumentList(searchedDocs, casesWithDocs, casesWithoutDocs, null, null, false,
				holidayService, caseUserFormsService, userFormsService, userService,documentService));
		
	    }
	    }
	
	private void docListFromSearchAll(HttpSession session, Model model, DocListInput docListInput, boolean canShowAll,
			UserInfo userInfo){
		
		User user=userService.findUserById(docListInput.getUserId());
		List<uk.co.jmr.sdp.domain.Document> searchedDocs = null;
		List<WfCase> casesWithDocs = new ArrayList<WfCase>();
		List<WfCase> casesWithoutDocs = new ArrayList<WfCase>();
		
		List<TaskSummary> tasks = caseService
				.findTrayLabelSummaryForUser(userInfo);
		if (!tasks.isEmpty()) {
			model.addAttribute("canShowAll", canShowAll);
		}
		String[] keywordsArray = Util.split(docListInput.getKeywords());
		String[] ebNoArray=Util.split(docListInput.getEbNo());
	    searchedDocs = documentService
				.findDocumentsForSearchAll(docListInput.getAuthor(),ebNoArray,
						docListInput.getDocumentName(), keywordsArray,
						docListInput.getDateCreatedFrom(),
						docListInput.getDateCreatedTo(),user.getSecGroups());
	    
	    List<Document> searchedDocWithoutOpen=new ArrayList<Document>();
	    
	    List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
	    List<Document> newDocsWithoutOpen=new ArrayList<Document>();
	    for(Document docs:searchedDocs){
	    	for(CompanyUser cu:cus){
	    		if(docs.getDiscriminator()!='F'){
	    		if(documentService.findDocumentAttributeByDocAttrValue(docs, cu.getAttrValue())!=null){
	    			newDocsWithoutOpen.add(docs);
	    		}
	    	}else{
	    		UserForms uf=userFormsService.findUserFormsById(Long.parseLong(docs.getUserFormId()));
	    		AttributeValue atv=dtAttributeValueService.findDtAttrValueById(uf.getCompanyId());
	    		if((atv!=null ? atv.getValue() : null).equals(cu.getAttrValue()!=null ? cu.getAttrValue().getValue():null)){
	    			newDocsWithoutOpen.add(docs);
	    			}
	    		}
	    	}
	    }
	    
	    
	    boolean isThirdParty=false;
	    
	    
	    List<SecurityGroup> sg=securityGroupService.findSecurityGroupsForUser(user);
	    
	    
	    for(String s:userInfo.getRoles()){
	    	if(s.equals("Third Party")){
	    		isThirdParty=true;
	    	}
	    }
	    if(isThirdParty==true){
	    for(Document d:newDocsWithoutOpen){
	    	if(d.getSecurityGroup()!=null){
	    		for(SecurityGroup sg1:sg){
	    	    	if(sg1.getName().equals(d.getSecurityGroup().getName()))
	    	    		searchedDocWithoutOpen.add(d);
	    		}
	    	}
	    	if(d.getSecurityGroup()==null){
	    		if(d.getAuthor().equals(userInfo.getUserName())){
	    			searchedDocWithoutOpen.add(d);
	    		}
	    	}
	    }
	    
	    if (searchedDocWithoutOpen.isEmpty()) {
			model.addAttribute("taskName", "No items match your search");
		}

		else {
			model.addAttribute("taskName", "Search Results");
		}
		Collections.sort(searchedDocWithoutOpen, Collections.reverseOrder());
		ArrayList<Long> caseIds = new ArrayList<Long>();
		if (newDocsWithoutOpen.size() > 0) {
			for (Document d : newDocsWithoutOpen) {
				caseIds.add(d.getCaseId());
			}
			casesWithDocs = caseService.getCases(caseIds);
		}
		GridBuilder gb = new GridBuilder();
		model.addAttribute("canCreateNew", false);
		model.addAttribute("docs", gb.buildDocumentList(searchedDocWithoutOpen, casesWithDocs, casesWithoutDocs, null, null, false,
				holidayService, caseUserFormsService, userFormsService, userService,documentService));
	    }
	    
	    
	    else{
		if (newDocsWithoutOpen.isEmpty()) {
			model.addAttribute("taskName", "No items match your search");
		}

		else {
			model.addAttribute("taskName", "Search Results");
		}
		Collections.sort(newDocsWithoutOpen, Collections.reverseOrder());
		ArrayList<Long> caseIds = new ArrayList<Long>();
		if (newDocsWithoutOpen.size() > 0) {
			for (Document d : newDocsWithoutOpen) {
				caseIds.add(d.getCaseId());
			}
			casesWithDocs = caseService.getCases(caseIds);
		}
		GridBuilder gb = new GridBuilder();
		model.addAttribute("canCreateNew", false);
		model.addAttribute("docs", gb.buildDocumentList(newDocsWithoutOpen, casesWithDocs, casesWithoutDocs, null, null, false,
				holidayService, caseUserFormsService, userFormsService, userService,documentService));
	    }
	    }
	

	private void docListFromSearch(HttpSession session, Model model, DocListInput docListInput, boolean canShowAll,
		UserInfo userInfo) {

		List<uk.co.jmr.sdp.domain.Document> searchedDocs = null;
		List<WfCase> casesWithDocs = new ArrayList<WfCase>();
		List<WfCase> casesWithoutDocs = new ArrayList<WfCase>();
		User user=userService.findUserById(userInfo.getUserId());
		//Util.setToString(user.getSecGroups());
		// get any matching forms
		casesWithoutDocs.addAll(formsReportingService.searchForms(docListInput,user.getSecGroups()));
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		if (!tasks.isEmpty()) {
			model.addAttribute("canShowAll", canShowAll);
		}

		if (!casesWithoutDocs.isEmpty() && docListInput.getDocTypeId() != -1) {
			String[] keywordsArray = Util.split(docListInput.getKeywords());
			String[] ebNoArray = Util.split(docListInput.getEbNo());
			searchedDocs = documentService.findDocumentsForSearch(docListInput.getDocType(), docListInput.getAuthor(), ebNoArray,
				docListInput.getDocumentName(), keywordsArray, docListInput.getRelevantDateFrom(),
				docListInput.getRelevantDateTo());

			Long disciplineId = (Long) session.getAttribute("disciplineId");
			Long siteId = (Long) session.getAttribute("siteId");
			SearchFilter filter;
			if ((disciplineId != null && siteId != null)) {
				filter = new SearchFilter(searchedDocs);
				searchedDocs = filter.restrictListBasedOnAttributeValue(disciplineId, siteId);
			}
			else if (disciplineId != null || siteId != null) {
				filter = new SearchFilter(searchedDocs);
				long id = (disciplineId != null) ? disciplineId : siteId;
				searchedDocs = filter.restrictListBasedOnAttributeValue(id);
			}

			if (searchedDocs.isEmpty()) {
				model.addAttribute("taskName", "No items match your search");
			}
			else {
				model.addAttribute("taskName", "Search Results");
			}

			Collections.sort(searchedDocs, Collections.reverseOrder());
			ArrayList<Long> caseIds = new ArrayList<Long>();
			if (searchedDocs.size() > 0) {
				for (Document d : searchedDocs) {
					caseIds.add(d.getCaseId());
				}

				casesWithDocs.addAll(caseService.getCases(caseIds));
			}
		}

		GridBuilder gb = new GridBuilder();
		model.addAttribute("canCreateNew", false);
//		model.addAttribute("docs", gb.buildDocumentList(searchedDocs, casesWithDocs, casesWithoutDocs, null, null, false,
//			holidayService, caseUserFormsService, userFormsService, userService));
		model.addAttribute("docs", gb.buildDocumentList(searchedDocs, casesWithDocs, casesWithoutDocs, null, null, false,
				holidayService, caseUserFormsService, userFormsService, userService,documentService));
	}

	private String showDocs(HttpSession session, Model model) throws RepositoryFault, RemoteException, ParseException {

		DocListInput docListInput = (DocListInput) session.getAttribute("dli");
		boolean canShowAll = true;
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		if (docListInput.getOrigin().equalsIgnoreCase("TP")) {
			docListFromTree(model, docListInput);
		}
		else if (docListInput.getOrigin().equalsIgnoreCase("SF")) {
			//docListFromSearch(session, model, docListInput, canShowAll, userInfo);
			if(docListInput.getDiscriminator()=='M'){
				docListFromSearchAll(session, model, docListInput, canShowAll, userInfo);
			}
			else if(docListInput.getDiscriminator()=='D'){
				docListFromSearchDocuments(session,model,docListInput,canShowAll,userInfo);	
			}
			else{
				docListFromSearchForms(session,model,docListInput,canShowAll,userInfo);
			}
		}
		else if (docListInput.getOrigin().equalsIgnoreCase("TF")) {
			docListFromTray(model, docListInput, canShowAll, userInfo);
		}
		else if (docListInput.getOrigin().equalsIgnoreCase("GF")) {
			docListAll(model, canShowAll, userInfo);
		}

		return "documentList";
	}

	@RequestMapping(value = "/trayDocs", method = RequestMethod.GET)
	public String getTrayDocuments(@RequestParam("trayLabel") String trayLabel, HttpSession session, Model model)
		throws RepositoryFault, RemoteException, ParseException {

		DocListInput dli = new DocListInput();
		dli.setOrigin("TF");
		dli.setTaskName(trayLabel);
		session.setAttribute("dli", dli);
		return showDocs(session, model);
	}

	@RequestMapping(value = "/getAllDocuments", method = RequestMethod.GET)
	public String getAllDocuments(HttpSession session, Model model) throws RepositoryFault, RemoteException, ParseException {

		DocListInput dli = new DocListInput();
		dli.setOrigin("GF");
		session.setAttribute("dli", dli);
		return showDocs(session, model);
	}

	@RequestMapping(value = "/saveAttr", method = RequestMethod.POST)
	public String saveAttributes(HttpServletRequest request, @RequestParam("path") String path,
		@RequestParam("caseId") String caseIdString, @RequestParam("documentName") String documentName,
		@RequestParam("stepList") String stepList,@RequestParam("adminMeta")boolean isAdminMeta, HttpSession session, Model model) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String s;
		String ebn = null;
		String keywords = null;
		Date date = null;
		long caseId = Long.parseLong(caseIdString);
		WfCase wfCase = caseService.findCaseById(caseId);
		for (WfAttribute a : wfCase.getAttributes()) {
			if (a.getUserEditable().equalsIgnoreCase("Y")) {
				if (a.getType() == WfAttributeType.WF_ATTR_TEXT) {
					s = request.getParameter("it_" + a.getName());
					if (s != null && s.length() > 0) {
						if (s.trim().length() > 0) {
							wfCase.setAttribute(a.getName(), s);
						}
						if (a.getName().equals("Eb Number")) {
							ebn = s;
						}
						else if (a.getName().equals("Ref. Number")) {
							ebn = s;
						}
						else if (a.getName().equals("Keywords")) {
							String[] sa = s.trim().split(" ");
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < sa.length; i++) {
								s = sa[i].trim();
								if (s.length() > 0)
									sb.append(s).append(" ");
							}

							if (sb.length() > 0)
								keywords = " " + sb.toString();
						}
					}
				}
				else if (a.getType() == WfAttributeType.WF_ATTR_DATE) {
					s = request.getParameter("id_" + a.getName());
					if (s != null && s.trim().length() != 0 && !s.equals("-")) {
						date = dateFormat.parse(s);
						wfCase.setAttribute(a.getName(), date);
						Document d=documentService.findDocumentForCaseId(caseId);
						d.setTargetDate(date);
						documentService.save(d);
					}
				}
				else if (a.getType() == WfAttributeType.WF_ATTR_DECIMAL) {
					s = request.getParameter("if_" + a.getName());
					Double d = doubleParse(s);
					wfCase.setAttribute(a.getName(), d);
				}
				else if (a.getType() == WfAttributeType.WF_ATTR_NUMBER) {
					s = request.getParameter("in_" + a.getName());
					Long l = longParse(s);
					wfCase.setAttribute(a.getName(), l);
				}
			}
		}

		this.caseService.saveCase(wfCase);
		// For Save Attr in Update Prop,separate form & document
		if (wfCase.getModel().getModelCategory() == 'F') {
			Document doc = documentService.findDocumentForCaseId(caseId);
			doc.setEbNo(ebn);
			doc.setKeywords(keywords);
			documentService.save(doc);
//			CaseUserForms cuf = caseUserFormsService.findCaseUserFormsByCaseId(caseId);
//			cuf.setKeywords(keywords);
//			cuf.setEbNo(ebn);
//			caseUserFormsService.saveCaseUserForms(cuf);
		}
		else if (wfCase.getModel().getModelCategory() == 'D') {
			Document doc = documentService.findDocumentForCaseId(caseId);
			doc.setEbNo(ebn);
			doc.setKeywords(keywords);
			if (date != null) {
				doc.setTargetDate(date);
			}

			documentService.save(doc);
			return viewMetadata(path, documentName, session, model, caseId, stepList,isAdminMeta);
		}

		return viewMetadataForm(path, documentName, session, model, caseId, stepList,isAdminMeta);
	}

	private double doubleParse(String s) {

		try {
			double d = Double.parseDouble(s);
			return d;
		}
		catch (Exception e) {
			logger.error("DashboardController doubleParse() method Error message "+e);
			return 0.0d;
		}
	}

	private long longParse(String s) {

		try {
			long l = Long.parseLong(s);
			return l;
		}
		catch (Exception e) {
			logger.error("DashboardController longParse() method Error message "+e);
			return 0;
		}
	}
	
	

	@RequestMapping(value = "/goAction", method = RequestMethod.POST)
	public void performAction(@RequestParam("actions") String actionName, @RequestParam("actionReason") String actionReason,
		@RequestParam("actionComments") String actionComments, @RequestParam("actionDocName") String documentName,
		@RequestParam("actionDocPath") String path, @RequestParam("stepList") String stepString,
		@RequestParam("caseId") long caseId, HttpSession session) throws Exception {

		Document document=documentService.findDocumentForCaseId(caseId);
		WfCase wfCase=caseService.findCaseById(caseId);
		String revisionporp = (String) servletContext.getAttribute("revision");
		boolean yes=false;
		if(wfCase.getModel().getName().equalsIgnoreCase("Fast Track")){
			if(actionName.equalsIgnoreCase("Approved")){
			yes=true;
			}
		}else{
			if(actionName.equalsIgnoreCase("Published"))
			yes=true;
			
		}
		
		
		if(yes){
		if(document.getDoctype().getVersion().equals("Mj")){
			DocumentVersion dv=new DocumentVersion(document.getId(), document.getFilePath(), document.getName());
			documentVersionService.save(dv);
			String tempName = Util.removeFileExtention(document.getName());
			String[] sp=tempName.split("-");
			int length=sp.length-1;
			String newName=sp[length].toString();
			String rev=null;
			int i3=0;
			if(newName.contains(".")){
				String[] sp2=newName.split("\\.");
				rev=sp2[0];
				/*int i=rev.length();
				String i2=rev.substring(i-1);
				i3=Integer.parseInt(i2);
				i3++;*/
				/*revisionporp+i3*/
			}
			tempName=sp[0]+"-"+sp[1]+"-"+sp[2]+"-"+sp[3]+"-"+sp[4]+"-"+sp[5]+"-"+rev+""+Util.getType(document.getName());
			documentName=tempName;
			document.setName(documentName);
			documentService.save(document);
			/*ArrayList<String> serviceUrlLists = getServiceUrls();
			String dsUser = (String) servletContext.getAttribute("dsUser");
			String dsPassword = (String) servletContext.getAttribute("dsPassword");*/
			//InputStream is=documentStorage.download(dsUser, dsPassword, document.getFilePath(), documentName, serviceUrlLists);
			//byte[] bytes=IOUtils.toByteArray(is);
			
			//documentStorage.createContent(dsUser, dsPassword, path, documentName, contentType, bytes);
			
		}
		}
		
		if (!actionComments.isEmpty()) {
			saveComments(documentName, path, actionComments, caseId, session);
		}
		// Start From here on monday
		List<Long> stps = new ArrayList<Long>();
		for (String s : stepString.split(",")) {
			stps.add(Long.parseLong(s));
		}

		List<WfStep> stepList = new ArrayList<WfStep>();
		for (Long l : stps) {
			stepList.add(caseService.findStepById(l));
		}

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		for (WfStep s : stepList) {
			//System.out.println(s.getAssignee().startsWith("U:"));
			if (s.getAssignee().startsWith("U:")) {
				String assignee = s.getAssignee().substring(2);
				if (assignee.equals(user.getUserName())) {
					caseService.performAction(s.getId(), actionName, actionReason, userInfo);
				}
				else {
				}
			}
			else {
				caseService.performAction(s.getId(), actionName, actionReason, userInfo);
			}
		}
		long ufid=0;
		if(document.getUserFormId()!=null){
			UserForms uf=userFormsService.findUserFormsById(Long.parseLong(document.getUserFormId()));
			ufid=uf.getId();
		FormTrail ft=new FormTrail(uf, user,stepList.get(0).getNode().getName() , new Date(), actionName, "",actionReason);
		formTrailService.save(ft);
		}
		endDocumentWip(stepList.get(0), user,session,ufid);
	}

	private void endDocumentWip(WfStep wfstep, User user,HttpSession session,long ufid) throws URISyntaxException, Exception {

		WfCase wfCase = caseService.findCaseById(wfstep.getOwningCase().getId());
		if (wfCase.getStatus().equalsIgnoreCase(WfCaseStatus.WF_CASE_CLOSED.toString())) {
			Document document = this.documentService.findDocumentForCaseId(wfCase.getId());
			this.documentService.endWip(document);
			if(ufid!=0){
			uploadDocumentIntoAlfrescoForForms(session,ufid);
			}
//			if (wfCase.getModel().getModelCategory() == 'D') {
//				Document document = this.documentService.findDocumentForCaseId(wfCase.getId());
//				this.documentService.endWip(document);
//			}
		}
	}

	
	
	
	private void saveComments(String documentName, String path, String comments, long caseId, HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		WfCase wfCase = caseService.findCaseById(caseId);
		ReviewNote LastReviewNotes = reviewNoteService.findByReviewerLastComment(user, wfCase);
		if (LastReviewNotes != null && comments.equals("No Comments")) {
			return;
		}

		if (comments.contains("�")) {
			comments = comments.replace("�", "\"");
		}

		if (comments.contains("�")) {
			comments = comments.replace("�", "\"");
		}

		if (comments.contains("�")) {
			comments = comments.replace("�", "\'");
		}

		if (comments.contains("<b>")) {
			comments = comments.replace("<b>", "<b><strong>");
			if (comments.contains("</b>")) {
				comments = comments.replace("</b>", "</strong></b>");
			}
		}
		else if (comments.contains("<strong>")) {
			comments = comments.replace("<strong>", "<b><strong>");
			if (comments.contains("</strong>")) {
				comments = comments.replace("</strong>", "</strong></b>");
			}
		}

		comments = comments.replace("<a", "<a target=\"_blank\"");
		ReviewNote reviewNote = new ReviewNote(new Date(), comments.trim(), wfCase, user);
		reviewNoteService.save(reviewNote);
	}

	@RequestMapping(value = "/postReview", method = RequestMethod.POST)
	public String reviewPost(@RequestParam("comments") String comments, @RequestParam("documentName") String documentName,
		@RequestParam("path") String path, @RequestParam("caseId") String caseIdString,
		@RequestParam("stepList") String stepList, @RequestParam("stepAssigned") boolean stepAssigned,@RequestParam("adminMeta")boolean isAdminMeta, HttpSession session,
		Model model) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		long caseId = Long.parseLong(caseIdString);
		WfCase wfCase = caseService.findCaseById(caseId);
		if (comments.trim().isEmpty() || comments.trim().equalsIgnoreCase("<br>")
			|| comments.trim().equalsIgnoreCase("<p>&nbsp;</p>") || comments.trim().startsWith("&nbsp;")) {
			if (wfCase.getModel().getModelCategory() == 'F') {
				documentName = getFormDefName(caseId);
				return viewMetadataForm(path, documentName, session, model, caseId, stepList,isAdminMeta);
			}

			return viewMetadata(path, documentName, session, model, caseId, stepList,isAdminMeta);
		}
		else {
			saveComments(documentName, path, comments, caseId, session);
			if (wfCase.getModel().getModelCategory() == 'F') {
				documentName = getFormDefName(caseId);
				return viewMetadataForm(path, documentName, session, model, caseId, stepList,isAdminMeta);
			}

			return viewMetadata(path, documentName, session, model, caseId, stepList,isAdminMeta);
		}
	}

	private String getFormDefName(long caseId) {
//		CaseUserForms caseUserForms = caseUserFormsService.findCaseUserFormsByCaseId(caseId);
//		String formName = caseUserForms.getName();
		Document docUserForms=documentService.findDocumentForCaseId(caseId);
		String formName=docUserForms.getName();
		return formName;
	}

	@RequestMapping(value = "/history", method = RequestMethod.POST)
	public String viewHistory(@RequestParam("caseIdFromUrlBlock") long caseId, HttpSession session, Model model) {

		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		if (userInfo == null) {
			return "error";
		}

		List<CaseDocHistory> caseDocHistories = new ArrayList<CaseDocHistory>();
		List<CaseDocHistory> cdhTrail = new ArrayList<CaseDocHistory>();
		WfCase wfCase = caseService.findCaseById(caseId);

		// For Document Based History
		if (wfCase.getModel().getModelCategory() == 'D') {
			Document document = this.documentService.findDocumentForCaseId(caseId);
			String docName = document.getName();
			List<DocumentTrail> docTrail = this.documentTrailService.findDocTrailForDocument(document.getId());
			for (DocumentTrail d : docTrail) {
				CaseDocHistory cd = new CaseDocHistory(d.getUser().getUserName(), d.getDate(), d.getAction());
				cd.setTaskName(d.getTaskName());
				cdhTrail.add(cd);
			}

			Collections.sort(cdhTrail);
			for (CaseDocHistory c : cdhTrail) {
			}

			model.addAttribute("docName", docName);
		}
		else if (wfCase.getModel().getModelCategory() == 'F') { // Form Based
			// For Case Forms
			model.addAttribute("docName", getFormDefName(caseId));
		}

		List<WfStep> stepList = caseService.findAllStepsForCase(caseId);
		List<WfStep> steps = new ArrayList<WfStep>();
		for (WfStep s : stepList) {
			if (s.getNode().getNodeType() == WfNodeType.WF_TASK_NODE) {
				steps.add(s);
			}
		}

		List<CaseDocHistory> cdhAssigned = new ArrayList<CaseDocHistory>();
		for (WfStep s : steps) {
			CaseDocHistory cdStart = new CaseDocHistory(s.getDateCreated(), s.getNode().getName(), s.getAssignee());
			cdStart.setAction("Assigned");
			cdhAssigned.add(cdStart);
		}

		Collections.sort(cdhAssigned);
		List<CaseDocHistory> cdhCompleted = new ArrayList<CaseDocHistory>();
		for (WfStep s : steps) {
			if (s.getStatus().equals(WfStepStatus.WF_STEP_COMPLETED.toString())) {
				CaseDocHistory cdEnd = new CaseDocHistory(s.getDateCompleted(), s.getNode().getName(), s.getUserCompleted());
				cdEnd.setReason(s.getActionReason());
				String actStr = "Completed";
				if ((s.getActionApplied() != null) && (s.getActionApplied().getName() != null)
					&& (s.getActionApplied().getName().trim().length() > 0)) {
					actStr += " with {" + s.getActionApplied().getName() + "}";
				}

				cdEnd.setAction(actStr);
				cdhCompleted.add(cdEnd);
			}
		}

		Collections.sort(cdhCompleted);
		for (CaseDocHistory cdh : cdhAssigned) {
			int i = 0;
			for (; i < cdhCompleted.size(); i++) {
				if (cdhCompleted.get(i).getTaskName().equalsIgnoreCase(cdh.getTaskName()))
					break;
			}

			caseDocHistories.add(cdh);
			int compSize = cdhCompleted.size();
			if (i >= 0 && i < compSize) {
				CaseDocHistory cdhEnd = cdhCompleted.remove(i);
				int j = 0;
				for (; j < cdhTrail.size(); j++) {
					if (cdhEnd.getActionDate().before(cdhTrail.get(j).getActionDate()))
						break;
				}

				for (int k = 0; k < j; k++) {
					caseDocHistories.add(cdhTrail.remove(0));
				}

				caseDocHistories.add(cdhEnd);
			}
			else {
				int trailLength = cdhTrail.size();
				for (int l = 0; l < trailLength; l++) {
					caseDocHistories.add(cdhTrail.remove(0));
				}
			}
		}

		Collections.sort(caseDocHistories);
		for (CaseDocHistory c : caseDocHistories) {
		}

		session.setAttribute("histories", caseDocHistories);
		return "history";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/csv", method = RequestMethod.POST)
	public void getCsv(HttpServletResponse response, HttpSession session) {

		List<CaseDocHistory> histories = (List<CaseDocHistory>) session.getAttribute("histories");
		StringBuffer sb = new StringBuffer();
		sb.append("Action Date,TaskName, User Performed,Action,Reason\n");
		for (CaseDocHistory c : histories) {
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String formattedActionDate = df.format(c.getActionDate());
			sb.append("'");
			sb.append(formattedActionDate);
			sb.append("'");
			sb.append(",");
			sb.append(c.getTaskName());
			sb.append(",");
			sb.append(c.getPerformedUser());
			sb.append(",");
			sb.append(c.getAction());
			sb.append(",");
			if (c.getReason() != null) {
				sb.append(c.getReason());
			}

			sb.append("\n");
		}
		try {
			response.setContentType("application/csv");
			response.setHeader("Content-disposition", "attachment; filename=History.csv");
			InputStream in = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
			OutputStream os = response.getOutputStream();
			byte[] bytes = new byte[4096];

			// copy binary contect to output stream
			int read;
			while ((read = in.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}

			os.flush();
			os.close();
			in.close();
		}
		catch (IOException e) {
			logger.error("/csv  Error message "+e);
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/csv1", method = RequestMethod.POST)
	public void getCsv1(@RequestParam("csv_text") String[] csvDatatable, @RequestParam("category") String category,
		HttpServletResponse response, HttpSession session) throws IOException {

		response.setContentType("application/csv");
		if (category.equalsIgnoreCase("TC") || category.equalsIgnoreCase("LA")) {
			response.setHeader("Content-disposition", "attachment; filename=MyDocuments.csv");
		}
		else {
			response.setHeader("Content-disposition", "attachment; filename=QuickSearchResults.csv");
		}

		BufferedWriter bs = null;
		try {
			bs = new BufferedWriter(response.getWriter());
			bs.write("Name");
			bs.write(",");
			bs.write("Status-Action");
			bs.write(",");
			bs.write("Submission Date");
			bs.write(",");
			bs.write("Document Owner");
			bs.write(",");
			bs.write("Workflow Process");
			bs.write(",");
			bs.write("Document Type");
			bs.write(",");
			bs.write("Eb No");
			bs.write(",");
			bs.write("Raised Date & Time");
			if (category.equalsIgnoreCase("TC") || category.equalsIgnoreCase("LA")) {
				bs.write(",");
				bs.write("Model Category");
			}

			bs.write("\n");
			int endindex = 9;
			List<String> ls = Arrays.asList(csvDatatable);
			List<List<String>> ls1 = new ArrayList<List<String>>();
			if (category.equalsIgnoreCase("TC") || category.equalsIgnoreCase("LA")) {
				endindex = 11;
				for (int i = 2; i < ls.size(); i++) {
					List<String> ls2 = ls.subList(i, endindex);
					ls1.add(ls2);
					i = endindex + 1;
					endindex = endindex + 11;
				}
			}
			else {
				for (int i = 1; i < ls.size(); i--) {
					List<String> ls2 = ls.subList(i, endindex);
					ls1.add(ls2);
					i = endindex + 2;
					endindex = endindex + 9;
				}
			}
			for (List<String> list : ls1) {
				for (String string : list) {
					string = string.replace("&amp;", "&");
					bs.write(string);
					bs.write(",");
				}

				bs.write("\n");
			}
		}
		catch (IOException e) {
			logger.error("/csv1 Error message " +e);
			e.printStackTrace();
		}
		finally {
			if (bs != null) {
				bs.flush();
				bs.close();
			}
		}
	}

	@RequestMapping(value = "/pickForm", method = RequestMethod.GET)
	public String pickedForm(@RequestParam("path") String path, @RequestParam("caseId") long caseId,
		@RequestParam("formName") String formName, @RequestParam("stepId") long stepId,@RequestParam("adminMeta")boolean isAdminMeta, HttpSession session, Model model) {

		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		WfStep wfStep = caseService.findStepById(stepId);
		caseService.changeToPickStatus(wfStep, userInfo);
		String stepList = String.valueOf(wfStep.getId());
		return viewMetadataForm(path, formName, session, model, caseId, stepList,isAdminMeta);
	}

	@RequestMapping(value = "/pick", method = RequestMethod.GET)
	public String picked(@RequestParam("path") String path, @RequestParam("caseId") long caseId,
		@RequestParam("docName") String documentName, @RequestParam("stepId") long stepId,@RequestParam("adminMeta")boolean isAdminMeta, HttpSession session, Model model) {

		documentName = regexSplit(documentName);
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		WfStep wfStep = caseService.findStepById(stepId);
		caseService.changeToPickStatus(wfStep, userInfo);
		String stepList = String.valueOf(wfStep.getId());
		return viewMetadata(path, documentName, session, model, caseId, stepList,isAdminMeta);
	}

	@RequestMapping(value = "/checkinUpload", method = RequestMethod.GET)
	public String upload(@RequestParam("documentId") long documentId, @RequestParam("path") String path,
		@RequestParam("stepId") long stepId, @RequestParam("docname") String docname,@RequestParam("adminMeta") boolean adminMeta, Model model, HttpServletRequest request) {
		docname = regexSplit(docname);
		Document d=documentService.findDocumentById(documentId);
		String workingCopyName = Util.getWorkingCopyName(docname);
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort()
			+ request.getContextPath();
		model.addAttribute("adminMeta", adminMeta);
		model.addAttribute("documentId", documentId);
		model.addAttribute("path", path);
		model.addAttribute("url", url);
		model.addAttribute("stepId", stepId);
		model.addAttribute("workingCopyName", workingCopyName);
		return "checkIn";
	}

	// For History Url Block : Security issue
	@RequestMapping(value = "/historyUrlBlock", method = RequestMethod.GET)
	public String dummy(@RequestParam("caseId") long caseId, HttpSession session, Model model) {

		model.addAttribute("caseIdForHistory", caseId);
		return "historyUrlBlock";
	}

	private ArrayList<String> getServiceUrls() {

		ArrayList<String> serviceUrlLists = new ArrayList<String>();
		serviceUrlLists.add((String) servletContext.getAttribute("restServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("navigationServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("objectServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("versioningServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("discoveryServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("multifilingServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("relationshipServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("aclServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("policyServiceWsdlUrl"));
		return serviceUrlLists;
	}

	@ResponseBody
	@RequestMapping(value = "/abandon", method = RequestMethod.GET)
	public String abandonCase(@RequestParam("stepId") long stepId, HttpSession session) throws Exception {
		
		this.caseService.abandonCaseForStep(stepId);
		WfStep step = caseService.findStepById(stepId);
		WfCase wfCase = step.getOwningCase();
		if (wfCase.getModel().getModelCategory() == 'F') {
			boolean requireNotificationEmail = false;
			if ("Published".equals(step.getStatusLabel())) {
				requireNotificationEmail = true;
			}
			
//			CaseUserForms caseUserForms = caseUserFormsService.findCaseUserFormsByCaseId(wfCase.getId());
//			caseUserForms.setAbandon(true);
//			caseUserFormsService.saveCaseUserForms(caseUserForms);
			Document docUserForms=documentService.findDocumentForCaseId(wfCase.getId());
			docUserForms.setAbandon(true);
			documentService.save(docUserForms);
			
			if (requireNotificationEmail) {
				long userFormId=Long.parseLong(docUserForms.getUserFormId());
				formService.processWorkflowEvent(userFormId, docUserForms.getAuthor(), "Abandon");
				//formService.processWorkflowEvent(caseUserForms.getUserFormsId(), caseUserForms.getAuthor(), "Abandon");
			}
		}
		else {
			Document doc=documentService.findDocumentForCaseId(wfCase.getId());
			boolean isM=false;
			String newOld=doc.getName();
			if(stepId!=0){
				if(doc.getDoctype().getVersion().equalsIgnoreCase("Mj")){
				DocumentVersion dv=new DocumentVersion(doc.getId(), doc.getFilePath(), doc.getName());
				documentVersionService.save(dv);
				String tempName = Util.removeFileExtention(doc.getName());
				String[] sp=tempName.split("-");
				int length=sp.length-1;
				String newName=sp[length].toString();
				String rev=null;
				int i3=0;
				if(newName.contains(".")){
					String[] sp2=newName.split("\\.");
					rev=sp2[0];
					/*int i=rev.length();
					String i2=rev.substring(i-1);
					i3=Integer.parseInt(i2);
					i3++;*/
					/*revisionporp+i3*/
				}
				tempName=sp[0]+"-"+sp[1]+"-"+sp[2]+"-"+sp[3]+"-"+sp[4]+"-"+sp[5]+"-"+rev+""+Util.getType(doc.getName());
				String documentName=tempName;
				doc.setName(documentName);
				documentService.save(doc);
				isM=true;
				}
			}
			Document document = this.documentService.findDocumentForCaseId(wfCase.getId());
			String oldname = document.getName();
			ArrayList<String> serviceUrlLists = getServiceUrls();
			Document revisionedDoc = getOriginalRevision(Util.removeFileExtention(document.getName()),
				Util.getCurrentFileVersion(document.getName(), (String) servletContext.getAttribute("revision")),
				document.getName(), document.getFilePath());

			AbandonedDocument abandonedDocument = new AbandonedDocument(document);
			abandonedDocument.setDocumentAbandoned();
			if(isM){
				documentStorage.update((String) servletContext.getAttribute("dsUser"),
						(String) servletContext.getAttribute("dsPassword"), document.getFilePath(), newOld, newOld,
						serviceUrlLists);
			}else{
			documentStorage.update((String) servletContext.getAttribute("dsUser"),
				(String) servletContext.getAttribute("dsPassword"), document.getFilePath(), oldname, document.getName(),
				serviceUrlLists);
			}
			documentService.save(document);
			if (revisionedDoc != null) {
				revisionedDoc.setRevisionable(true);
				documentService.save(revisionedDoc);
			}

			User user = (User) session.getAttribute("LOGGED_IN_USER");
			String taskName = step.getNode().getName();
			DocumentTrail documentTrail = new DocumentTrail(document, user, "Abandoned", new Date(), taskName);
			this.documentTrailService.save(documentTrail);
		}

		return "Success";
	}

	private Document getOriginalRevision(String tempName, int revision, String orginalFileName, String path) {

		String revisionporp = (String) servletContext.getAttribute("revision");
		Document doctemp = null;
		for (int i = revision - 1; i >= 1; i--) {
			String[] tempArrName = tempName.split("-");
			StringBuilder sBr = new StringBuilder();
			String tempNameFormation = null;
			if (tempArrName != null) {
				for (int value = 0; value < tempArrName.length - 1; value++) {
					sBr.append(tempArrName[value].toString());
					sBr.append("-");
				}
				tempNameFormation = sBr.toString();
			}

			// Commented for replacing all text contains REV
			// tempName = tempName.replaceAll("-" + revisionporp + ".*", "");
			// tempName = tempName.concat("-" + revisionporp + i).concat(
			// Util.getType(orginalFileName));
			tempName = tempNameFormation.concat(revisionporp + i).concat(Util.getType(orginalFileName));
			doctemp = documentService.findDocumentByDocNameAndPath(tempName, path);
			if (doctemp != null) {
				return doctemp;
			}
		}

		return doctemp;
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String printDatatable(@RequestParam("caseId") long caseId, HttpSession session, Model model) {

		String docName = null;
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		if (user == null) {
			return "error";
		}

		WfCase wfCase = caseService.findCaseById(caseId);
		if (wfCase.getModel().getModelCategory() == 'F') {
//			CaseUserForms caseUserForms = caseUserFormsService.findCaseUserFormsByCaseId(caseId);
//			UserForms userForms = userFormsService.findUserFormsById(caseUserForms.getUserFormsId());
			Document docUserForms = documentService.findDocumentForCaseId(caseId);
			docName=docUserForms.getName();
			//UserForms userForms=userFormsService.findUserFormsById(Long.parseLong(document.getUserFormId()));
			//docName = userForms.getForms().getFormDefs().getName();
		} 
		else if (wfCase.getModel().getModelCategory() == 'D') {
			Document document = documentService.findDocumentForCaseId(caseId);
			docName = document.getName();
		}

		List<ReviewNote> reviews = reviewNoteService.findAllReviewsByWfCase(wfCase);
		Collections.sort(reviews, Collections.reverseOrder());
		model.addAttribute("reviewDocs", reviews);
		model.addAttribute("docName", docName);
		return "print";
	}
	
	@RequestMapping(value = "/simpleSearch", method = RequestMethod.POST)
	public String searchAll(@RequestParam("userId")long userId,
		@RequestParam("simple") String value,
		HttpSession session, Model model) throws RepositoryFault, RemoteException {
		
		List<uk.co.jmr.sdp.domain.Document> searchedDocs = null;
		List<WfCase> casesWithDocs = new ArrayList<WfCase>();
		List<WfCase> casesWithoutDocs = new ArrayList<WfCase>();
		 User user=userService.findUserById(userId);
		
	    searchedDocs = documentService.findDocumentsOnSimpleSearch(value,user.getSecGroups());
	    
	   // User user=userService.findUserById(userId);
	    UserInfo userInfo=new UserInfo(user);
	    
	    List<Document> searchedDocWithoutOpen=new ArrayList<Document>();
	    boolean isThirdParty=false;
	   
	    List<SecurityGroup> sg=securityGroupService.findSecurityGroupsForUser(user);
	    
	    
	    for(String s:userInfo.getRoles()){
	    	if(s.equals("Third Party")){
	    		isThirdParty=true;
	    	}
	    }
	    List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
	    List<Document> newSearchedDocs=new ArrayList<Document>();
	    for(Document d:searchedDocs){
	    	for(CompanyUser cu:cus){
	    	if(d.getDiscriminator()!='F'){
	    		if(documentService.findDocumentAttributeByDocAttrValue(d, cu.getAttrValue())!=null){
	    			newSearchedDocs.add(d);
	    		}
	    		
	    	}else{
	    		UserForms uf=userFormsService.findUserFormsById(Long.parseLong(d.getUserFormId()));
	    		AttributeValue atv=dtAttributeValueService.findDtAttrValueById(uf.getCompanyId());
	    		if((atv!=null ? atv.getValue() : null).equals(cu.getAttrValue()!=null ? cu.getAttrValue().getValue():null)){
	    			newSearchedDocs.add(d);
	    	   }
	    	}
	      }
	    }
	    
	if(isThirdParty==true){
	    	
	    for(Document d:newSearchedDocs){
	    	if(d.getSecurityGroup()!=null){
	    		for(SecurityGroup sg1:sg){
	    	    	if(sg1.getName().equals(d.getSecurityGroup().getName()))
	    	    		searchedDocWithoutOpen.add(d);
	    		}
	    	}
	    	if(d.getSecurityGroup()==null){
	    		if(d.getAuthor().equals(userInfo.getUserName())){
	    			searchedDocWithoutOpen.add(d);
	    		}	
	    	}
	    }
	    if (searchedDocWithoutOpen.isEmpty()) {
			model.addAttribute("taskName", "No items match your search");
		}
        
		else {
			model.addAttribute("taskName", "Search Results");
		}
		Collections.sort(searchedDocWithoutOpen, Collections.reverseOrder());
		ArrayList<Long> caseIds = new ArrayList<Long>();
		if (searchedDocs.size() > 0) {
			for (Document d : searchedDocs) {
				caseIds.add(d.getCaseId());
			}
			casesWithDocs = caseService.getCases(caseIds);
		}
		GridBuilder gb = new GridBuilder();
		model.addAttribute("canCreateNew", false);
		model.addAttribute("docs", gb.buildDocumentList(searchedDocWithoutOpen, casesWithDocs, casesWithoutDocs, null, null, false,
				holidayService, caseUserFormsService, userFormsService, userService,documentService));
		return "documentList";
	}
	 else{
		if (newSearchedDocs.isEmpty()) {
			model.addAttribute("taskName", "No items match your search");
		}
        
		else {
			model.addAttribute("taskName", "Search Results");
		}
		Collections.sort(newSearchedDocs, Collections.reverseOrder());
		ArrayList<Long> caseIds = new ArrayList<Long>();
		if (newSearchedDocs.size() > 0) {
			for (Document d : newSearchedDocs) {
				caseIds.add(d.getCaseId());
			}
			casesWithDocs = caseService.getCases(caseIds);
		}
		GridBuilder gb = new GridBuilder();
		model.addAttribute("canCreateNew", false);
		model.addAttribute("docs", gb.buildDocumentList(newSearchedDocs, casesWithDocs, casesWithoutDocs, null, null, false,
				holidayService, caseUserFormsService, userFormsService, userService,documentService));
		return "documentList"; 
	}
	}
	//Help Icon Functionality
	@RequestMapping(value = "/getHelpIconContent", method = RequestMethod.GET)
	public String getHelpIconPage(HttpSession session, Model model) throws RepositoryFault, RemoteException {
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		model.addAttribute("title",user.getUserName());
		return "adminGeneralHelp";
	}

	
	//Document upload into alfresco from form
	
	public void uploadDocumentIntoAlfrescoForForms( HttpSession session,long userFormId) throws URISyntaxException, Exception{
		String msg = null;
		String FilePath = null;
		String folderName = null;
		String sgAttrs = (String) servletContext.getAttribute("sgAttrs");
		int result = 0;
		Date date = null;
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");

		// String restApiUrl=(String)
		// servletContext.getAttribute("restServiceUrl");

		ArrayList<String> serviceUrlLists = new ArrayList<String>();
		serviceUrlLists.add((String) servletContext.getAttribute("restServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("navigationServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("objectServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("versioningServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("discoveryServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("multifilingServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("relationshipServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("aclServiceWsdlUrl"));
		serviceUrlLists.add((String) servletContext.getAttribute("policyServiceWsdlUrl"));

		User user = (User) session.getAttribute("LOGGED_IN_USER");

		

		FilePath = (String) servletContext.getAttribute("filePath.quickupload");
		folderName = (String) servletContext.getAttribute("foldername.quickupload");

		Attribute attribute1 = dtAttributeService.findAttributeByOrder(1);
		Attribute attribute2 = dtAttributeService.findAttributeByOrder(2);
		Attribute attribute3 = dtAttributeService.findAttributeByOrder(3);
		Attribute attribute4 = dtAttributeService.findAttributeByOrder(4);
		Attribute attribute5 = dtAttributeService.findAttributeByOrder(5);

		AttributeValue attributeValue1 = null;
		AttributeValue attributeValue2 = null;
		AttributeValue attributeValue3 = null;
		AttributeValue attributeValue4 = null;
		AttributeValue attributeValue5 = null;

		UserForms uf=userFormsService.findUserFormsById(userFormId);
		List<FormData> fds=formDataService.findFormDatasByUserForm(uf);

		List<TempDocuments> tempDocs=new ArrayList<TempDocuments>();
		for(FormData formData:fds){
			TempDocuments tempDocuments=tempDocumentsService.findTempDocumentsByDocumentName(formData.getTextValue());
			if(tempDocuments!=null)
			tempDocs.add(tempDocuments);
		}
		
		if(!tempDocs.isEmpty()){
		for(TempDocuments tempDoc:tempDocs){
				File fileL=new File(tempDoc.getFilePath()+tempDoc.getName());
				//System.out.println(">>>>>>>>"+fileL.length()+"----"+fileL.getName()+";;;;;;;"+fileL.getParentFile());
				String fileName = fileL.getName();
											
				//To Upload Files into alfresco from local directory
				UploadPhotos uploadPhotos = new UploadPhotos(FilePath, dsUser, dsPassword, fileL);
				uploadPhotos.uploadLocalFile(documentStorage, folderName, fileName, serviceUrlLists);
				//System.out.println(Util.removeFileExtention(fileName));
				Doctype doctype=doctypeService.findDoctypeById(tempDoc.getDoctypeId());
				Document doc=new Document(doctype, FilePath, tempDoc.getAuthor(), tempDoc.getName(), tempDoc.getKeywords(), tempDoc.getCaseId(), tempDoc.getCreatedDate(), tempDoc.getTargetDate(), tempDoc.getEbNo(), 'N');
				doc.setRevisionable(true);
				doc.setDiscriminator('D');
				doc.setLocked(tempDoc.getLocked());
				
				
				
				
				attributeValue1 = attributeValueDao.findAttributeValueById(tempDoc.getAttributeValue1());
				attributeValue2 = attributeValueDao.findAttributeValueById(tempDoc.getAttributeValue2());
				attributeValue3 = attributeValueDao.findAttributeValueById(tempDoc.getAttributeValue3());
				/*attributeValue4 = attributeValueDao.findAttributeValueById(tempDoc.getAttributeValue4());
				attributeValue5 = attributeValueDao.findAttributeValueById(tempDoc.getAttributeValue5());*/

				tempDocumentsService.delete(tempDoc);
				
				DocumentAttribute da1 = new DocumentAttribute(doc, attribute1, attributeValue1);
				DocumentAttribute da2 = new DocumentAttribute(doc, attribute2, attributeValue2);
				DocumentAttribute da3 = new DocumentAttribute(doc, attribute3, attributeValue3);
				/*DocumentAttribute da4 = new DocumentAttribute(doc, attribute4, attributeValue4);
				DocumentAttribute da5 = new DocumentAttribute(doc, attribute5, attributeValue5);*/
				Set<DocumentAttribute> docAttrs = new HashSet<DocumentAttribute>();
				docAttrs.add(da1);
				docAttrs.add(da2);
				docAttrs.add(da3);
				/*docAttrs.add(da4);
				docAttrs.add(da5);*/
				doc.setDocumentAttributes(docAttrs);
				documentService.save(doc);
				fileL.delete();
			//CommonsMultipartFile cmf=new CommonsMultipartFile()

			// uploadPhotos.upload(documentStorage, folderName, fileName);
			// uploadPhotos.upload(documentStorage, folderName,
			// fileName,restApiUrl);
			
			}
		}
	}
	
}
