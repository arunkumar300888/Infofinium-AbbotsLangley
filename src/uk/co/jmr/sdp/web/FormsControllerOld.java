/*package uk.co.jmr.sdp.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import uk.co.jmr.sdp.common.Utils;
import uk.co.jmr.sdp.dao.AttributeValueDao;
import uk.co.jmr.sdp.dao.impl.AttributeValueDaoImpl;
import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.DocumentUUID;
import uk.co.jmr.sdp.domain.DocumentVersion;
import uk.co.jmr.sdp.domain.FormCompanyGroup;
import uk.co.jmr.sdp.domain.FormTrail;
import uk.co.jmr.sdp.domain.FormsModel;
import uk.co.jmr.sdp.domain.ReviewNote;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupForm;
import uk.co.jmr.sdp.domain.TempDocuments;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.ModelCombo;
import uk.co.jmr.sdp.domain.forms.FormFieldValues;
import uk.co.jmr.sdp.domain.photoupload.UploadPhotos;
import uk.co.jmr.sdp.ds.DocumentStorage;
import uk.co.jmr.sdp.service.CaseUserFormsService;
import uk.co.jmr.sdp.service.CompanyUserService;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.DocumentUuidService;
import uk.co.jmr.sdp.service.DocumentVersionService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.FormCompanyGroupService;
import uk.co.jmr.sdp.service.FormService;
import uk.co.jmr.sdp.service.FormTrailService;
import uk.co.jmr.sdp.service.ModelComboService;
import uk.co.jmr.sdp.service.SecurityGroupComboService;
import uk.co.jmr.sdp.service.SecurityGroupFormService;
import uk.co.jmr.sdp.service.SecurityGroupService;
import uk.co.jmr.sdp.service.TempDocumentsService;
import uk.co.jmr.sdp.service.UserService;
import uk.co.jmr.sdp.web.search.QuickSearch;
import uk.co.jmr.sdp.web.util.UserInfo;
import uk.co.jmr.sdp.web.util.Util;
import uk.co.jmr.webforms.db.service.FormDataService;
import uk.co.jmr.webforms.db.service.FormDefsService;
import uk.co.jmr.webforms.db.service.FormsModelService;
import uk.co.jmr.webforms.db.service.FormsService;
import uk.co.jmr.webforms.db.service.HtmlFormDefService;
import uk.co.jmr.webforms.db.service.UserFormsService;

import com.ardhika.wfar.CaseDocHistory;
import com.ardhika.wfar.TaskSummary;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfModel;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.service.CaseService;
import com.ardhika.wfar.service.ModelService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.util.Date;
import java.util.Iterator;

import uk.co.jmr.sdp.service.EmailService;
import uk.co.jmr.webforms.db.dao.UserFormsDao;
import uk.co.jmr.webforms.db.pojo.FormData;
import uk.co.jmr.webforms.db.pojo.FormDefs;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;

@Controller
@RequestMapping(value = "/forms")
public class FormsController {
	@Autowired
	private FormService formService;
	@Autowired
	private ModelService modelService;
	@Autowired
	private CaseService caseService;
	@Autowired
	private UserFormsService userFormsService;
	@Autowired
	private CaseUserFormsService caseUserFormService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private DoctypeService doctypeService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private DocumentStorage documentStorage;
	@Autowired
	private DocumentVersionService documentVersionService;
	@Autowired
	private UserService userService;
	@Autowired
	private FormTrailService formTrailService;
	@Autowired
	private UserFormsDao userFormsDao;
	@Autowired
	private SecurityGroupService securityGroupService;
	@Autowired
	private HtmlFormDefService htmlFormDefService;
	@Autowired
	private DocumentUuidService documentUuidService;
	@Autowired
	private SecurityGroupComboService securityGroupComboService;
	@Autowired
	private ModelComboService modelComboService;
	@Autowired
	private DtAttributeService dtAttributeService;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;
	@Autowired
	private TempDocumentsService tempDocumentsService;
	@Autowired
	private FormDataService formDataService;
	@Autowired
	private FormDefsService formDefsService;
	@Autowired
	private SecurityGroupFormService securityGroupFormService;
	@Autowired
	private CompanyUserService companyUserService;
	@Autowired
	private FormCompanyGroupService formCompanyGroupService;
	@Autowired
	private FormsModelService formsModelService;
	@Autowired
	private FormsService formsService;
	
	Logger logger=Logger.getLogger(FormsController.class);

	String toJavascript(long userFormId, List<FormFieldValues> data) {

		Iterator<FormFieldValues> ffvi = data.iterator();
		StringBuilder sb = new StringBuilder();
		sb.append("{\nuserFormId: ").append(userFormId).append(",\n");
		;
		boolean first = true;
		while (ffvi.hasNext()) {
			FormFieldValues ffv = ffvi.next();
			if (!first) {
				sb.append(",\n");
			}
			sb.append(ffv.getName());
			sb.append(":");
			String[] values = ffv.getValues();
			if (values == null || values.length == 0) {
				sb.append("null");
			}
			else {
				sb.append("[");
				for (int i = 0; i < values.length; i++) {
					if (i != 0) {
						sb.append(", ");
					}
					sb.append("\"");
					sb.append(values[i].replaceAll("\\\\", "\\\\\\\\").replaceAll("\\\n", "\\\\n").replaceAll("\\\"", "\\\\\""));
					sb.append("\"");
				}
				sb.append("]");
			}
			first = false;
		}
		sb.append("}");
		System.out.println(sb.toString());
		return sb.toString();
	}

	@RequestMapping(value = "/formsDefinition", method = RequestMethod.GET)
	public String getFormDefinition(@RequestParam("formDefinitionId") long formDefinitionId, Model model,HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		model.addAttribute("submitVisible", "add");
		model.addAttribute("showCancel", false);
		if(formDefinitionId==3){
			model.addAttribute("title","(Incident Reporting Form)");
		}else if(formDefinitionId==4){
			model.addAttribute("title","(PMO Form)");
		}else if(formDefinitionId==6){
			model.addAttribute("title","(Prospect Form)");
		}
		Forms form = formService.fetchFormDefinition(formDefinitionId, -1);
		model.addAttribute("secGroups", securityGroupFormService.findSecurityGroupFormByformDefs(formDefsService.findFormDefsById(formDefinitionId)));
		if (form != null) {
			model.addAttribute("formDefinition", form.getHandCodedPath());
			
			model.addAttribute("formId", Long.toString(form.getId()));
			model.addAttribute("formName", Utils.formNameVersion(form.getFormDefs().getName(), form.getVersion()));
			List<FormFieldValues> userFormData = formService.getUserFormData(form.getId(), -1);
			model.addAttribute("userFormData", toJavascript(-1, userFormData));
			model.addAttribute("securityGrp",-1);
			model.addAttribute("companyGrp",-1);
			model.addAttribute("modelId",-1);
			Attribute attribute = dtAttributeService.findAttributeByOrder(1);
			Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
			//Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		//	Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
			//Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
			Set<AttributeValue> attr1ValuesRestricted = new HashSet<AttributeValue>();
			Set<AttributeValue> attr2ValuesRestricted = new HashSet<AttributeValue>();
			Set<AttributeValue> attr3ValuesRestricted = new HashSet<AttributeValue>();
			List<FormCompanyGroup> formCompanyGroup=formCompanyGroupService.findFormCompanyGroupForFormDef(form.getFormDefs());
			for(FormCompanyGroup fcg:formCompanyGroup){
				attr2ValuesRestricted.add(fcg.getAttributeValue());
			}
			
			List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
			for(CompanyUser cu:cus){
				attr3ValuesRestricted.add(cu.getAttrValue());
			}
			
			for(AttributeValue atv1:attr2ValuesRestricted){
				for(AttributeValue atv2:attr3ValuesRestricted){
					if(atv1.getValue().equals(atv2.getValue())){
					attr1ValuesRestricted.add(atv1);
					}
				}
			}
			model.addAttribute("compGroups", Util.getActiveAttributeValues(attr1ValuesRestricted));
			model.addAttribute("workflows", form.getModels());
			
		}

		return "forms";
	}

	@RequestMapping(value = "/showStaticForm", method = RequestMethod.GET)
	public String showStaticForm(@RequestParam("userFormId") long userFormId, Model model) {
		
		UserForms uf = userFormsService.findUserFormsById(userFormId);
		String pageHtml = formService.createHtmlRepresentation(uf);
		//System.out.println("Html:" +pageHtml);
		model.addAttribute("pageHtml", pageHtml);
		model.addAttribute("userFormId",userFormId);
		model.addAttribute("userFormId", userFormId);
		return "renderHtml";
	}
	
	@RequestMapping(value = "/showStaticJspForm", method = RequestMethod.GET)
	public String showStaticJspForm(@RequestParam("userFormId") long userFormId, Model model) {
		
		UserForms uf = userFormsService.findUserFormsById(userFormId);
		//String pageHtml = formService.createHtmlRepresentation(uf);
		ArrayList al1=new ArrayList();
		ArrayList al2=new ArrayList();
		HashMap hm=new HashMap();
		String lastField = null;
		Iterator<FormData> fdi = uf.getFormDatas().iterator();
		while (fdi.hasNext()) {
			FormData fd = fdi.next();
			if (!fd.getHtmlFormDef().getFormFieldMap().getFormFieldMaps().isEmpty())
				continue;

			String currentField = fd.getHtmlFormDef().getName();
			if (!currentField.equals(lastField)) {
				lastField = currentField;
				al1.add(fd.getHtmlFormDef().getFormFieldMap().getFields().getDescription());
			}
			al2.add(valueToString(fd.getTextValue(), fd.getDateValue(), fd.getIntValue(), fd.getNumberValue()));
		}
		hm.put(al1, al2);
		String formName=uf.getForms().getFormDefs().getName()+" - "+uf.getForms().getName();
		model.addAttribute("formName", formName);
		model.addAttribute("datas", hm);
		model.addAttribute("userFormId", userFormId);
		return "staticForm";
	}
	
	String valueToString(String s, Date date, Long l, Double d) {

		if (s != null)
			return s;
		if (date != null)
			return Utils.formatTimestamp(date);
		if (l != null)
			return l.toString();
		if (d != null)
			return d.toString();

		return null;
	}

	

	@RequestMapping(value = "/showDraftForm", method = RequestMethod.GET)
	public String showDraftForm(@RequestParam("userFormId") long userFormId, Model model,HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		//model.addAttribute("secGroups", securityGroupService.findAllSecurityGroups());
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		model.addAttribute("submitVisible", "add");
		model.addAttribute("showCancel", false);
		UserForms uf=userFormsService.findUserFormsById(userFormId);
		Forms form = formService.fetchFormDefinition(-1, userFormId);
		model.addAttribute("secGroups", securityGroupFormService.findSecurityGroupFormByformDefs(formDefsService.findFormDefsById(uf.getForms().getFormDefs().getId())));
		if (form != null) {
			model.addAttribute("formDefinition", form.getHandCodedPath());
			model.addAttribute("formId", Long.toString(-1));
			model.addAttribute("formName", Utils.formNameVersion(form.getFormDefs().getName(), form.getVersion()));
			List<FormFieldValues> userFormData = formService.getUserFormData(-1, userFormId);
			model.addAttribute("securityGrp",uf.getSecurityGroupId());
			model.addAttribute("submissionDate",sdf.format(uf.getSubmissionDate()));
			model.addAttribute("userFormData", toJavascript(userFormId, userFormData));
			model.addAttribute("companyGrp", uf.getCompanyId());
			model.addAttribute("modelId", uf.getModelId());
			
			Attribute attribute = dtAttributeService.findAttributeByOrder(1);
			
			
			Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
			//Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
			Set<AttributeValue> attr1ValuesRestricted = new HashSet<AttributeValue>();
			List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
			for(CompanyUser cu:cus){
				attr1ValuesRestricted.add(cu.getAttrValue());
			}
			model.addAttribute("compGroups", Util.getActiveAttributeValues(attr1ValuesRestricted));
			model.addAttribute("workflows", uf.getForms().getModels());
		}

		return "forms";
	}
	
	@RequestMapping(value = "/deleteDraftForm", method = RequestMethod.GET)
	public String draftFormDeletion(@RequestParam("userFormId") long userFormId, Model model,HttpSession session, HttpServletRequest request) {
		UserForms userFormsDeletion=userFormsService.findUserFormsById(userFormId);
		if(userFormsDeletion!=null){
			userFormsDeletion.setActive('N');
			userFormsService.save(userFormsDeletion);
		}
		return listDraftForms(model,session,request);
	}

	@RequestMapping(value = "/showForm", method = RequestMethod.GET)
	public String showFormWithData(@RequestParam("userFormId") long userFormId,@RequestParam("adminMeta")boolean adminMeta, Model model, HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		//long caseid = caseUserFormService.findCaseUserFormsByUserFormsId(userFormId).getCaseId();
		//model.addAttribute("secGroups", securityGroupService.findAllSecurityGroups());
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		long caseid=documentService.findDocumentsByUserFormsId(String.valueOf(userFormId)).getCaseId();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Iterator<WfStep> stepItr = caseService.findAssignedStepsForCase(userInfo, caseid).iterator();
		String submitVisible = "none";
		while (stepItr.hasNext()) {
			stepItr.next();
			submitVisible = "update";
		}
		
		if(adminMeta){
			submitVisible = "update";
		}
		
		model.addAttribute("showCancel", adminMeta ? true:false);
		
		model.addAttribute("submitVisible", submitVisible);
		Forms form = formService.fetchFormDefinition(-1, userFormId);
		UserForms uf=userFormsService.findUserFormsById(userFormId);
		model.addAttribute("secGroups", securityGroupFormService.findSecurityGroupFormByformDefs(formDefsService.findFormDefsById(uf.getForms().getFormDefs().getId())));
		if (form != null) {
			model.addAttribute("formDefinition", form.getHandCodedPath());
			model.addAttribute("formId", Long.toString(-1));
			model.addAttribute("formName", Utils.formNameVersion(form.getFormDefs().getName(), form.getVersion()));
			List<FormFieldValues> userFormData = formService.getUserFormData(-1, userFormId);
			model.addAttribute("securityGrp",uf.getSecurityGroupId());
			model.addAttribute("companyGrp",uf.getCompanyId());
			model.addAttribute("modelId",uf.getModelId());
			model.addAttribute("submissionDate", sdf.format(uf.getSubmissionDate()));
			model.addAttribute("userFormData", toJavascript(userFormId, userFormData));
			Attribute attribute = dtAttributeService.findAttributeByOrder(1);

			Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
			//Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
			Set<AttributeValue> attr1ValuesRestricted = new HashSet<AttributeValue>();
			Set<AttributeValue> attr2ValuesRestricted = new HashSet<AttributeValue>();
			Set<AttributeValue> attr3ValuesRestricted = new HashSet<AttributeValue>();
			List<FormCompanyGroup> formCompanyGroup=formCompanyGroupService.findFormCompanyGroupForFormDef(form.getFormDefs());
			for(FormCompanyGroup fcg:formCompanyGroup){
				attr2ValuesRestricted.add(fcg.getAttributeValue());
			}
			
			List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
			for(CompanyUser cu:cus){
				attr3ValuesRestricted.add(cu.getAttrValue());
			}
			
			for(AttributeValue atv1:attr2ValuesRestricted){
				for(AttributeValue atv2:attr3ValuesRestricted){
					if(atv1.getValue().equals(atv2.getValue())){
					attr1ValuesRestricted.add(atv1);
					}
				}
			}
			
			
			model.addAttribute("compGroups", Util.getActiveAttributeValues(attr1ValuesRestricted));
			model.addAttribute("workflows", uf.getForms().getModels());
		}

		return "forms";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String saveCaseFormData(@RequestParam("secGrp")long securityGroupId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("compGrp")long compGrp,@RequestParam("wrkFlw")long modelId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		//uploadDocumentIntoAlfrescoForForms(session);
		SecurityGroup securityGroup=null;
		if(securityGroupId!=0){
		securityGroup=securityGroupService.findSecurityGroupById(securityGroupId);
		}
		Map<String, String[]> formData = request.getParameterMap();
		long userFormId = formService.saveFormData(userInfo.getUserId(), formData);
		//
		String userFormIdStr = String.valueOf(userFormId);
		//
		UserForms userForms = userFormsService.findUserFormsById(userFormId);
		userForms.setSecurityGroupId(securityGroupId);
		userForms.setSubmissionDate(sdf.parse(submissionDate));
		userForms.setCompanyId(compGrp);
		userForms.setModelId(modelId);
		userFormsService.save(userForms);
		Forms form = userForms.getForms();
		Set<WfModel> modelSet = form.getModels();
		String workflowName = null;
		if (workFlow.equalsIgnoreCase("No")){
			workflowName = (String) servletContext.getAttribute("noWorkflowModleNameForForms");
		}else{
		
			for (WfModel workflowModel : modelSet) {
				if (!workflowModel.getName().equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
				if(workflowModel.getName().equals(modelService.findModelById(modelId).getName())){
				workflowName = workflowModel.getName();
					
				}
			}
		//}
		
		WfModel wfModel = modelService.findModelByName(workflowName);
		//Form naming convention
		
		String formDefName= userForms.getForms().getFormDefs().getName();
		String keyWord="";
		String revisionporp = (String) servletContext.getAttribute("revision");
		String finalFormName=formDefName+"-"+userFormId;
		
		try {
			
			
			WfStep step = modelService.createCase(wfModel.getName(), userInfo);
			WfCase wfCase = step.getOwningCase();
			
			// change the status from draft ('d') to submitted 's'
			formService.updateUserFormStatus(userFormId, 's');
			Map<String, Object> formMetaData = formService.getFormMetaData(userFormId);
			wfCase.setAttribute("Target Date", sdf.parse(submissionDate));
			this.caseService.saveCase(wfCase);
			//Commented by Karthik on 16.11.13
//			CaseUserForms caseUserForms = new CaseUserForms(wfCase.getId(), userFormId,
//				(String) formMetaData.get(CaseUserForms.META_DOCUMENT_NAME), userInfo.getUserName(),
//				(Date) formMetaData.get(CaseUserForms.META_TARGET_DATE), null, null);
//			caseUserFormService.saveCaseUserForms(caseUserForms);
			
			//For Forms & Document Integration
		   // Doctype doctype=doctypeService.findDoctypeByDoctypeName("Forms");
			Doctype doctype=null;
			
			Document newForms=new Document(doctype,null, userInfo.getUserName(),finalFormName,"Forms", wfCase.getId(),new Date(),sdf.parse(submissionDate),null,'Y',userFormIdStr,'F');
			if (workflowName.equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
				//System.out.println("workFlow:::::::::::"+workFlow);
				//newForms.setCaseId(0);
				
				uploadDocumentIntoAlfrescoForForms(session,userFormId);
				newForms.setWip('N');
			}
			newForms.setSecurityGroup(securityGroup);
			documentService.save(newForms);		
			User user=userService.findUserById(userInfo.getUserId());
			
			
			FormTrail ft=new FormTrail(userForms, user, "To Approve", new Date(), "Submitted", "");
			formTrailService.save(ft);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("/create Error message "+e);
			e.printStackTrace();
		}
		model.addAttribute("result", "Form Saved & Case Created");
		return "forms";
	}

	@RequestMapping(value = "/saveForDraft", method = RequestMethod.POST)
	public String saveFormToDraft(@RequestParam("secGrp")long secGrpId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("wrkFlw")long modelId,@RequestParam("compGrp")long compGrp,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		model.addAttribute("secGroups", securityGroupService.findAllSecurityGroups());
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		long userFormId = formService.saveFormData(userInfo.getUserId(), formData);
		UserForms uf=userFormsService.findUserFormsById(userFormId);
		uf.setSecurityGroupId(secGrpId);
		System.out.println(sdf.parse(submissionDate));
		uf.setSubmissionDate(sdf.parse(submissionDate));
		uf.setCompanyId(compGrp);
		uf.setModelId(modelId);
		userFormsService.save(uf);
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);
		
		return "showTray";
	}
	
	@RequestMapping(value = "/clone", method = RequestMethod.GET)
	public String cloneForm(@RequestParam("userFormId") long userFormId, Model model, HttpSession session) {
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		UserForms uf = formService.cloneUserForm(userFormId, userInfo.getUserId());
		return "refresh";
	}
	
	@RequestMapping(value = "/revFormPop", method = RequestMethod.GET)
	public String revisionPop(@RequestParam("userFormId") long userFormId, Model model, HttpSession session) {
		
		model.addAttribute("userFormId", userFormId);
		return "revisionForm";
	}
	// Form Revision 17-11-2014
	@SuppressWarnings("unused")
	@RequestMapping(value = "/revisionForm", method = RequestMethod.GET)
	public String reviseForm( Model model, HttpSession session,HttpServletRequest request) throws Exception {
		
		Map<String, String[]> val=request.getParameterMap();
		Set s = val.entrySet();
        Iterator it = s.iterator();
        String uformId=null;
        Date targetDate=null;
        SimpleDateFormat sdf=new SimpleDateFormat("dd-mm-yyyy");
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            if(key.equals("userFormId")){
            uformId=value[0].toString();
            }else if(key.equals("formTargetDate")){
            	
            	targetDate=sdf.parse(value[0].toString());
            }
            	
            
        }
		UserForms uforms=userFormsService.findUserFormsById(Long.parseLong(uformId));		
		
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		UserForms uf = formService.cloneUserForm(uforms.getId(), userInfo.getUserId());
		
		UserForms userForms = userFormsService.findUserFormsById(Long.parseLong(uformId));
		Forms form = userForms.getForms();
		Set<WfModel> modelSet = form.getModels();
		String workflowName = null;
		for (WfModel workflowModel : modelSet) {
			workflowName = workflowModel.getName();
		}
		
		WfModel wfModel = modelService.findModelByName(workflowName);
		String revisionporp = (String) servletContext.getAttribute("revision");
		Document doc=documentService.findDocumentsByUserFormsId(uformId.toString());
		doc.setRevisionable(false);
		documentService.save(doc);
		String[] formName=doc.getName().split("-");
		String revProp=formName[3];
		String[] remRev=revProp.split(revisionporp);
		long revName=Long.parseLong(remRev[1]);
		WfStep step = modelService.createCase(wfModel.getName(), userInfo);
		WfCase wfCase = step.getOwningCase();
		this.caseService.saveCase(wfCase);
		// change the status from draft ('d') to submitted 's'
		formService.updateUserFormStatus(Long.parseLong(uformId), 's');
		String finalFormName=formName[0]+"-"+formName[1]+"-"+uf.getId()+"-"+revisionporp+""+revName++;
				
		Document newForms=new Document(doctype,null, userInfo.getUserName(),finalFormName,"Forms", wfCase.getId(),new Date(),(Date) formMetaData.get(CaseUserForms.META_TARGET_DATE),null,'Y',userFormIdStr,'F');
		documentService.save(newForms);	
		
		Document newForms=new Document(null,null, userInfo.getUserName(),finalFormName,"Forms", wfCase.getId(),new Date(),targetDate,null,'Y',Long.valueOf(uf.getId()).toString(),'F');
			
			newForms.setRevisionable(true);
		documentService.save(newForms);	
		
		return "refresh";
	}

	@RequestMapping(value = "/saveForUpdate", method = RequestMethod.POST)
	public String saveForUpdate(@RequestParam("secGrp")long secGrpId,@RequestParam("submissionDate")String submissionDate,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		String[] s = formData.get("_userFormId");
		long ufId=-1;
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		
		
		
		if (s != null && s.length != 0) {
			ufId = uk.co.jmr.sdp.common.Utils.parseLong(s[0], -1L);
		}
		
		UserForms userForms=userFormsService.findUserFormsById(ufId);
		List<FormFieldValues> userFormDataPrevious = formService.getUserFormData(-1, ufId);
		
		StringBuilder sb=new StringBuilder();
		
		SecurityGroup sgp=null;
		if(userForms.getSecurityGroupId()!=0)
		sgp=securityGroupService.findSecurityGroupById(userForms.getSecurityGroupId());
		Date oldSubDate=userForms.getSubmissionDate();
		
		if(!sdf.format(oldSubDate).equals(submissionDate))
		sb.append("Submission Date "+" : "+sdf.format(oldSubDate)+" -> "+submissionDate+"#");
		
		String previousSec=null;
		if(sgp!=null)
		previousSec=sgp.getName();
		
		long prevCompId=userForms.getCompanyId();
		long newCompId=compId;
		
		if(prevCompId!=newCompId)
			sb.append("Company "+" : "+prevCompId+" -> "+newCompId+"#");
		
		userForms.setSecurityGroupId(secGrpId);
		userForms.setSubmissionDate(sdf.parse(submissionDate));
	//	userForms.setCompanyId(compId);
		userFormsService.save(userForms);
		SecurityGroup sgn=null;
		if(userForms.getSecurityGroupId()!=0){
		 sgn=securityGroupService.findSecurityGroupById(userForms.getSecurityGroupId());
		}
		String newSec=null;
		if(sgn!=null)
		newSec=sgn.getName();
		
		long userFormId = formService.saveFormData(userInfo.getUserId(), formData);
		Map<String, Object> formMetaData = formService.getFormMetaData(userFormId);
		List<FormFieldValues> userFormDataNew = formService.getUserFormData(-1, userFormId);
		
		HashMap hm=htmlFormDefService.findLableNameForForms(userForms.getForms());
		
		if(!previousSec.equals(newSec))
		sb.append("Security Group "+" : "+previousSec+" -> "+newSec+"#");
		
		HashMap<String, FormFieldValues> oldMap =  new HashMap<String, FormFieldValues>();
		HashMap<String, FormFieldValues> newMap =  new HashMap<String, FormFieldValues>();
		
		
		for(FormFieldValues ffvp:userFormDataPrevious){
			oldMap.put(ffvp.getName(), ffvp);	
		}
		
		
		for(FormFieldValues ffvn:userFormDataNew){
			newMap.put(ffvn.getName(), ffvn);	
		}
		
		Set<String> keySet = newMap.keySet();
		
		Iterator i=keySet.iterator();
		
		while(i.hasNext()){
			
			String key=i.next().toString();
			System.out.println(key);
			FormFieldValues oldFF=oldMap.get(key);
			FormFieldValues newFF=newMap.get(key);
			if(oldFF.getValues()!=null && newFF.getValues()!=null){
			if(oldFF.getValues().length==newFF.getValues().length){
				if(oldFF.getValues().length==1){
					if(!oldFF.getValues()[0].equalsIgnoreCase(newFF.getValues()[0])){
					sb.append(hm.get(oldFF.getName())+" : "+oldFF.getValues()[0]+" -> "+newFF.getValues()[0]+"#");
					}
				}else{
					String[] oldAr=oldFF.getValues();
					String[] newAr=newFF.getValues();
					Arrays.sort(oldAr);
					Arrays.sort(newAr);
					
					if(!Arrays.deepEquals(oldAr, newAr)){
						sb.append(hm.get(oldFF.getName())+" : "+Utils.arrayToString(oldAr)+" -> "+Utils.arrayToString(newAr)+"#");
					}
				}
			}else{
				sb.append(hm.get(oldFF.getName())+" : "+Utils.arrayToString(oldFF.getValues())+" -> "+Utils.arrayToString(newFF.getValues())+"#");
			}
			}else{
				if(newFF.getValues()!=null){
					sb.append(hm.get(oldFF.getName())+" : NIL ->"+Utils.arrayToString(newFF.getValues())+" #");
				}
				if(oldFF.getValues()!=null){
					sb.append(hm.get(oldFF.getName())+" : "+Utils.arrayToString(oldFF.getValues())+" -> NIL #");
				}
				
			}
		}
		System.out.println(sb.toString());
		
		
		
		
		
		String userFormIdStr = String.valueOf(userFormId);
		Document docUserForms=documentService.findDocumentsByUserFormsId(userFormIdStr);
		if(docUserForms!=null){
			docUserForms.setName((String) formMetaData.get(CaseUserForms.META_DOCUMENT_NAME));
			//docUserForms.setTargetDate((Date) formMetaData.get(CaseUserForms.META_TARGET_DATE));
			docUserForms.setTargetDate(sdf.parse(submissionDate));
			docUserForms.setSecurityGroup(sgn);
			documentService.save(docUserForms);
			
			Set<WfModel> modelSet = userForms.getForms().getModels();
			String workflowName = null;
			
			
				for (WfModel workflowModel : modelSet) {
					if (!workflowModel.getName().equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
						workflowName = workflowModel.getName();
						
					}
				
			}
			
			
			WfCase wfCase = caseService.findCaseById(docUserForms.getCaseId());
			
			// change the status from draft ('d') to submitted 's'
			
			wfCase.setAttribute("Target Date", sdf.parse(submissionDate));
			this.caseService.saveCase(wfCase);
		}
		//CaseUserForms caseUserForms = caseUserFormService.findCaseUserFormsByUserFormsId(userFormId);
		// caseUserForms.setAuthor((String)
		// formMetaData.get(CaseUserForms.META_AUTHOR));
		//caseUserForms.setName((String) formMetaData.get(CaseUserForms.META_DOCUMENT_NAME));
		//caseUserForms.setTargetDate((Date) formMetaData.get(CaseUserForms.META_TARGET_DATE));
		//caseUserFormService.saveCaseUserForms(caseUserForms);
		
		User user=userService.findUserById(userInfo.getUserId());
		
		List<WfStep> step=caseService.findAllStepsForCaseForForm(docUserForms.getCaseId());
		
		
		
		FormTrail ft=new FormTrail(userForms,user , step.get(0).getNode().getName(), new Date(), "Update", sb.toString());
		formTrailService.save(ft);
		
		StringBuilder js = new StringBuilder();
		js.append("document.getElementById('btnProperties').click();");
		model.addAttribute("javascript", js.toString());
		return "sendJavascript";
	}
	
	@RequestMapping(value = "/formHistory", method = RequestMethod.GET)
	public String getFormHistory(@RequestParam("userFormId")long userFormId,Model model, HttpSession session, HttpServletRequest request) {
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		
		Document d=documentService.findDocumentsByUserFormsId(String.valueOf(userFormId));
		
		List<FormTrail> fts=formTrailService.findFormTrailForUserFormId(userFormId);
		
		model.addAttribute("docName", d.getName());
		model.addAttribute("formHistory", fts);
		session.setAttribute("formHistory", fts);
		
		return "historyForms";
	}

	@RequestMapping(value = "/listDraftForms", method = RequestMethod.GET)
	public String listDraftForms(Model model, HttpSession session, HttpServletRequest request) {
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Iterator<UserForms> ufli = formService.getFromsByStatus(userInfo.getUserId(), 'd').iterator();
		StringBuilder sb = new StringBuilder();
		//sb.append("<div style='width: 675px; overflow-x: scroll;'>\n");
		sb.append("<div class='my-document complete box-content'><div class='place-table'>\n");
	//	sb.append("<table cellpadding='0' cellspacing='0' border='0' class='display' id='example' width='800px'>\n");
		sb.append("<table class='documents-table display' id='example'>\n");
		sb.append("<thead>\n");
		sb.append("<tr class='first-row'>\n");
		sb.append("<th class='col order'>Form</th>\n");
		sb.append("<th class='col order'>Date</th>\n");
		sb.append("<th class='col order'>Title \\ Type of Injury \\ Project Name</th>\n");
		sb.append("<th class='col order'>Delete</th>\n");
		sb.append("</tr>\n");
		sb.append("</thead>\n");
		sb.append("<tbody>\n");
		while (ufli.hasNext()) {
			UserForms uf = ufli.next();
			sb.append("<tr class='gradeB' title='")
				.append(uf.getForms().getFormDefs().getDescription()).append("'>\n");
			sb.append("<td onclick='loadDraftForm(").append(uf.getId()).append(");'>").append(uf.getForms().getFormDefs().getName()).append("</td>\n");
			sb.append("<td onclick='loadDraftForm(").append(uf.getId()).append(");'>").append(uk.co.jmr.sdp.common.Utils.formatTimestamp(uf.getCreatedOn())).append("</td>\n");
			Iterator<FormData> fdi = uf.getFormDatas().iterator();
			while (fdi.hasNext()) {
				FormData fd = fdi.next();
				if (fd.getHtmlFormDef().getName().equals("f_1_2") || fd.getHtmlFormDef().getName().equals("f_3_2") || fd.getHtmlFormDef().getName().equals("f_4_1") || 
						fd.getHtmlFormDef().getName().equals("f_5_7")) {
					sb.append("<td onclick='loadDraftForm(").append(uf.getId()).append(");'>").append(fd.getTextValue()).append("</td>\n");
				}
			}
			sb.append("<td>").append("<a href='#' onClick='deleteDraftForm(").append(uf.getId()).append(");'><img border='0' src='resources/images/wizart/trash.png'/></a>").append("</td>\n");
		}
		sb.append("</tbody>\n");
		sb.append("</table>\n");
		sb.append("</div>\n");
		model.addAttribute("docs", sb.toString());
		model.addAttribute("taskName", "Draft");
		model.addAttribute("canShowAll", "false");
		return "documentList";
	}

	@RequestMapping(value = "/ajaxDataRequest", method = RequestMethod.GET)
	public String ajaxDataRequest(@RequestParam("requester") String requester, Model model) {
		StringBuilder sb = new StringBuilder();
		List<String> popupDataList = null;
		List<Document> docs=documentService.findAllDocumentsByWip('N');
		if (requester.equals("f_1_3") || requester.equals("f_1_4") || requester.equals("f_3_10") || requester.equals("f_4_5") || requester.equals("f_4_6")
				|| requester.equals("f_4_7") || requester.equals("f_4_8") || requester.equals("f_4_9") || requester.equals("f_4_12")
				|| requester.equals("f_4_15") || requester.equals("f_4_18") || requester.equals("f_4_21") || requester.equals("f_4_24")
				|| requester.equals("f_4_27") || requester.equals("f_4_30") || requester.equals("f_4_36") || requester.equals("f_4_39")
				|| requester.equals("f_4_42") || requester.equals("f_4_45") || requester.equals("f_4_48") || requester.equals("f_4_51")
				|| requester.equals("f_4_54") || requester.equals("f_4_57") || requester.equals("f_4_77") || requester.equals("f_4_81") 
				|| requester.equals("f_4_33") || requester.equals("f_5_8")
				|| requester.equals("f_5_10") || requester.equals("f_5_12"))
		
		{
			// Start/End calendar
			model.addAttribute("title", "From Date/Time");
			model.addAttribute("viewContent", "/WEB-INF/views/calendarWidget.jsp");
		}
		else {
			model.addAttribute("title", "Select Data Item");
			if(requester.equals("f_4_59") || requester.equals("f_4_60") || requester.equals("f_4_61") || requester.equals("f_4_62")
					|| requester.equals("f_4_63") || requester.equals("f_4_64") || requester.equals("f_4_65") || requester.equals("f_4_66")
					|| requester.equals("f_4_67") || requester.equals("f_4_68") || requester.equals("f_4_69") || requester.equals("f_4_70")
					|| requester.equals("f_4_71") || requester.equals("f_4_72") || requester.equals("f_4_73") || requester.equals("f_4_74")){
				
					
					ArrayList<String> al=new ArrayList<String>();
					for(Document d:docs){
						if(d.getDiscriminator()=='F'){
							al.add(d.getName());
						}
						//popupDataList=al;
					}
			}else if(requester.equals("f_7_2")){
				popupDataList=new ArrayList<String>();
				popupDataList.add("Earing");
				popupDataList.add("Necklace");
				popupDataList.add("Ring");
			}else{
			popupDataList = formService.getDialogData(requester);
			}
			model.addAttribute("popupDataList", popupDataList);
			if (requester.equals("f_1_10")) {
				model.addAttribute("viewContent", "/WEB-INF/views/popupResultsListSP.jsp");
				model.addAttribute("qtyField", "yes");
			}else if(requester.equals("f_4_59") || requester.equals("f_4_60") || requester.equals("f_4_61") || requester.equals("f_4_62")
					|| requester.equals("f_4_63") || requester.equals("f_4_64") || requester.equals("f_4_65") || requester.equals("f_4_66")
					|| requester.equals("f_4_67") || requester.equals("f_4_68") || requester.equals("f_4_69") || requester.equals("f_4_70")
					|| requester.equals("f_4_71") || requester.equals("f_4_72") || requester.equals("f_4_73") || requester.equals("f_4_74")
					|| requester.equals("f_4_76") || requester.equals("f_4_80")){
				model.addAttribute("viewContent", "/WEB-INF/views/popupRefDocuments.jsp");
				model.addAttribute("docs", docs);
			}
			else {
				model.addAttribute("viewContent", "/WEB-INF/views/popupResultsList.jsp");
				model.addAttribute("qtyField", "no");
			}

			if (requester.equals("f_1_5") || requester.equals("f_1_13_15") || requester.equals("f_4_59") || requester.equals("f_4_60") || requester.equals("f_4_61") || requester.equals("f_4_62")
					|| requester.equals("f_4_63") || requester.equals("f_4_64") || requester.equals("f_4_65") || requester.equals("f_4_66")
					|| requester.equals("f_4_67") || requester.equals("f_4_68") || requester.equals("f_4_69") || requester.equals("f_4_70")
					|| requester.equals("f_4_71") || requester.equals("f_4_72") || requester.equals("f_4_73") || requester.equals("f_4_74") 
					|| requester.equals("f_4_76") || requester.equals("f_4_80")) {
				model.addAttribute("entryField", "no");
			}
			else {
				model.addAttribute("entryField", "yes");
			}
		}
		model.addAttribute("popupDialogAjaxField", requester);
		return "popupDialog";
	}
	
	@RequestMapping(value = "/printFormTable", method = RequestMethod.GET)
	public String printStaticForm(@RequestParam("userFormId") long userFormId, Model model) {
		
		UserForms uf = userFormsService.findUserFormsById(userFormId);
		String pageHtml = formService.createHtmlRepresentationForPrint(uf);
		//System.out.println("Html:" +pageHtml);
		model.addAttribute("pageHtml", pageHtml);
		model.addAttribute("userFormId",userFormId);
		return "printForm";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/csv", method = RequestMethod.POST)
	public void getCsv(HttpServletResponse response, HttpSession session) {

		List<FormTrail> histories = (List<FormTrail>) session.getAttribute("formHistory");
		StringBuffer sb = new StringBuffer();
		sb.append("Action Date,TaskName, User Performed,Action,Details,Reason\n");
		for (FormTrail c : histories) {
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String formattedActionDate = df.format(c.getPerformedOn());
			sb.append("'");
			sb.append(formattedActionDate);
			sb.append("'");
			sb.append(",");
			sb.append(c.getFormStatus());
			sb.append(",");
			sb.append(c.getUser().getUserName());
			sb.append(",");
			sb.append(c.getAction());
			sb.append(",");
			sb.append(c.getDetails());
			sb.append(",");
			if (c.getReason() != null) {
				sb.append(c.getReason());
			}

			sb.append("\n");
		}
		try {
			response.setContentType("application/csv");
			response.setHeader("Content-disposition", "attachment; filename=Form Audit.csv");
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
	
	
	CSV DOWNLOAD OF FORM
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/pdfDownloadForm", method = RequestMethod.POST)
	public void getFormPdf(@RequestParam("userFormId")long userFormId,
		HttpServletResponse response, HttpSession session) throws IOException, DocumentException {

		 response.setHeader("Expires", "0");
	      response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
	      response.setHeader("Pragma", "public");
		UserForms uf = userFormsService.findUserFormsById(userFormId);
		
	      response.setContentType("application/pdf");

	      response.setContentLength(baos.size());
	      response.setHeader("Content-disposition", "attachment; filename="+uf.getForms().getName()+".pdf");
		
		
		String pageHtml = formService.createHtmlRepresentationForPdfDownload(uf);

		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		   com.itextpdf.text.Document document=new com.itextpdf.text.Document();
		   PdfWriter writer=PdfWriter.getInstance(document, baos);
		    document.open();
		    //XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(pageHtml)); 
		    HTMLWorker htmlWorker = new HTMLWorker(document);
		    htmlWorker.parse(new StringReader(pageHtml));
		    document.close();
		    ServletOutputStream out = response.getOutputStream();
		    baos.writeTo(out);
		    out.flush();
		    baos.flush();
		     
		}
		catch (IOException e) {
			logger.error("/csv1 Error message " +e);
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/docDownload", method = RequestMethod.POST)
	public void downloadDocument(@RequestParam("path") String path, @RequestParam("documentName") String documentName,
		@RequestParam("documentId") String documentId, @RequestParam("stepId") long stepId, HttpServletResponse response,
		HttpSession session) throws Exception {

		// System.out.println(path + " " + documentId);
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		Document d=documentService.findDocumentById(Long.parseLong(documentId));
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");

		ArrayList<String> serviceUrlLists = getServiceUrls();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		// InputStream is = null;
		// OutputStream os = null;
		try {
			if(d.getDoctype().getVersion().equals("Mj")){
				List<DocumentVersion> documentVersions=documentVersionService.findDocumentVersionsByDocumentId(Long.parseLong(documentId));
				String name=null;
				if(!documentVersions.isEmpty()){
					for(DocumentVersion dv:documentVersions){
						name=dv.getDocumentName();
						break;
					}
					
				}else{
					name=documentName;
				}
				bis = new BufferedInputStream(documentStorage.download(dsUser, dsPassword, path, name, serviceUrlLists));
			}else{
			bis = new BufferedInputStream(documentStorage.download(dsUser, dsPassword, path, documentName, serviceUrlLists));
			}
			response.setHeader("Content-Disposition", "attachment;filename=\"" + documentName + "\"");
			byte[] bytes = new byte[2048];
			bos = new BufferedOutputStream(response.getOutputStream());
			int read;
			while ((read = bis.read(bytes)) != -1) {
				bos.write(bytes, 0, read);
			}
		}
		finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.flush();
				bos.close();
			}
		}
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
	
	@RequestMapping(value = "/showDocDownload", method = RequestMethod.GET)
	public String showDoc(@RequestParam("path") String path, @RequestParam("documentName") String documentName,
			@RequestParam("documentId") String documentId, @RequestParam("stepId") long stepId, Model model) {
		
		long id=Long.parseLong(documentId);
		Document d=documentService.findDocumentById(id);
		model.addAttribute("docName", documentName);
		model.addAttribute("docOwner",d.getAuthor());
		model.addAttribute("docType", d.getDoctype().getDoctypeName());
		model.addAttribute("path", path);
		model.addAttribute("caseId", d.getCaseId());
		model.addAttribute("stepId", stepId);
		model.addAttribute("documentId", id);
		return "staticDocument";
	}
	
	String valueToString(String s, Date date, Long l, Double d) {

		if (s != null)
			return s;
		if (date != null)
			return Utils.formatTimestamp(date);
		if (l != null)
			return l.toString();
		if (d != null)
			return d.toString();

		return null;
	}
	
	// Changes done for forms document upload
	
	

	@RequestMapping(value = "/openDoc", method = RequestMethod.GET)
	public String openDocument(@RequestParam("docName") String documentName, Model model) {
		
		
		model.addAttribute("documentName", documentName);
		
		return "downloadPopUp";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/downloadDocForm", method = RequestMethod.POST)
	public void downloadDocumentFromForm(@RequestParam("docName")String name,
		HttpServletResponse response, HttpSession session) throws Exception {
		
		TempDocuments tempDoc=null;
		Document doc=documentService.findDocumentByDocName(name);
		String path=null;
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");

		ArrayList<String> serviceUrlLists = getServiceUrls();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		// InputStream is = null;
		// OutputStream os = null;
		try {
			
			if(doc!=null){
				String downDoc=null;
				
				List<DocumentVersion> documentVersions=documentVersionService.findDocumentVersionsByDocumentId(doc.getId());
				if(!documentVersions.isEmpty()){
				for(DocumentVersion dv:documentVersions){
					downDoc=dv.getDocumentName();
					path=dv.getFilePath();
					break;
				}
				}else{
					downDoc=name;
					String	folderName = (String) servletContext.getAttribute("foldername.quickupload");
					if(doc.getCaseId()==0){
					path=doc.getFilePath()+folderName;
					}else{
						path=doc.getFilePath();
					}
				}	
				
				bis = new BufferedInputStream(documentStorage.download(dsUser, dsPassword, path, downDoc, serviceUrlLists));
				
				response.setHeader("Content-Disposition", "attachment;filename=\"" + doc.getName() + "\"");
			}else{
			tempDoc=tempDocumentsService.findTempDocumentsByDocumentName(name);
			InputStream is = new FileInputStream(new File(tempDoc.getFilePath()+tempDoc.getName()));
			System.out.println(tempDoc.getFilePath()+tempDoc.getName()+" "+tempDoc.getName());
			bis = new BufferedInputStream(is);
			response.setHeader("Content-Disposition", "attachment;filename=\"" + tempDoc.getName() + "\"");
			
			}
			
			
			byte[] bytes = new byte[2048];
			bos = new BufferedOutputStream(response.getOutputStream());
			//FileUtils.copyFile(input, bos);
			int read;
			while ((read = bis.read(bytes)) != -1) {
				bos.write(bytes, 0, read);
			}
		}
		finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.flush();
				bos.close();
			}
		
	}
	}	
	
	
	@RequestMapping(value = "/searchLinksForm", method = RequestMethod.POST)
	public String searchLinks(@RequestParam("disciplineLinks") long disciplineId,
		@RequestParam("sitesLinks") long siteId,
		@RequestParam("documentTypeLinks") long docTypeId,
		@RequestParam("authorLinks") String author,
		@RequestParam("ebNoLinks") String ebNo,
		@RequestParam("keywordsLinks") String keywords,
		@RequestParam("documentNameLinks") String documentName,
		// @RequestParam("dateCreatedFromLinks") String dateCreatedFrom,
		// @RequestParam("dateCreatedToLinks") String dateCreatedTo,
		@RequestParam("relevantdatefromLinks") String relevantdatefrom,
		@RequestParam("relevantdatetoLinks") String relevantdateto,
		@RequestParam("itemId")String itemId,
		@RequestParam("docName")String docName,HttpSession session, Model model) {
		//System.out.println(docId);
		try {
			
			// QuickSearch quickSearch = new QuickSearch(disciplineId, siteId,
			// docTypeId, author, ebNo, keywords, documentName,
			// dateCreatedFrom, dateCreatedTo, relevantdatefrom,
			// relevantdateto);
			
			QuickSearch quickSearch = new QuickSearch(disciplineId, siteId, docTypeId, author, ebNo, keywords, documentName,
				relevantdatefrom, relevantdateto);
			List<Document> searchResults = quickSearch.searchDocs(doctypeService, documentService);
			searchResults = quickSearch.filterSearchDocsBasedOnDiciplineAndSite(searchResults);
			
			UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
			User user=userService.findUserById(userInfo.getUserId());
 			List<SecurityGroup> sgs=securityGroupService.findSecurityGroupsForUser(user);
			List<Document> docs=new ArrayList<Document>();
			for(String r:userInfo.getRoles()){
				if(r.equals("Third Party")){
					for(Document d:searchResults){
						if(d.getSecurityGroup()!=null){
				    		for(SecurityGroup sg1:sgs){
				    	    	if(sg1.getName().equals(d.getSecurityGroup().getName()))
				    	    		docs.add(d);
				    		}
				    	}
				    	if(d.getSecurityGroup()==null){
				    		if(d.getAuthor().equals(userInfo.getUserName())){
				    			docs.add(d);
				    		}
					}
				}
					model.addAttribute("searchResult", docs);
			}else{
				model.addAttribute("searchResult", searchResults);
			}
			}
			
			
			
			if (edit.equals("edit")) {
				model.addAttribute("edit", null);
			}
			else {
				model.addAttribute("edit", edit);
			}
			model.addAttribute("docId", docId);
			//model.addAttribute("adminMeta", isAdminMeta);
			model.addAttribute("itemId",itemId);
			model.addAttribute("documentName", docName);
		}
		catch (Exception e) {
			model.addAttribute("searchResulterror", "Error occurred while searching the document");
			// LOGGER.error(e.getCause());
		}
		return "documentLinksForm";
	}
	
	@RequestMapping(value = "/openForUpload", method = RequestMethod.GET)
	public String uploadDoc(@RequestParam("itemId") String id,@RequestParam("docName")String docName, Model model) {
		
		//System.out.println(id);
		model.addAttribute("itemId", id);
		model.addAttribute("documentName", docName);
		return "uploadDocIntoForm";
	}
	
	@RequestMapping(value = "/docLinksForm", method = RequestMethod.GET)
	public String doclinksForm(Model model) {
		
		//System.out.println(id);
		model.addAttribute("itemId", "");
		List<Document> docs=new ArrayList<Document>();
		model.addAttribute("searchResult", docs);
		return "documentLinksForm";
	}
	
	@RequestMapping(value = "/attr1", method = RequestMethod.GET)
	public String firstSetAttributes(@RequestParam("itemId")String itemId,@RequestParam("docName")String docName,Model model, HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);

		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		//Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		Set<AttributeValue> attr1ValuesRestricted = new HashSet<AttributeValue>();
		List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
		for(CompanyUser cu:cus){
			attr1ValuesRestricted.add(cu.getAttrValue());
		}
		
		model.addAttribute("attr1Name", attribute.getName());
		//model.addAttribute("attr1Values", attr1ValuesRestricted);
		model.addAttribute("attr1Values", Util.getActiveAttributeValues(attr1ValuesRestricted));
		// model.addAttribute("attr1Values", attr1Values);
		model.addAttribute("itemId", itemId);
		model.addAttribute("QU", true);
		model.addAttribute("docName", docName);
		return "attr1Form";
	}

	@RequestMapping(value = "/attr2", method = RequestMethod.GET)
	public String secondSetAttributes(@RequestParam("itemId")String itemId,long attr1Value,@RequestParam("docName")String docName, Model model, HttpSession session) {

		//System.out.println("Attr1Value -> " + attr1Value);
		AttributeValue attr1 = this.dtAttributeValueService.findDtAttrValueById(attr1Value);
		session.setAttribute("attributeValue1", attr1);
		Set<ModelCombo> modelCombo1 = this.modelComboService.findMatchForFirstLevelAttrValue(attr1Value);
		//System.out.println("modelCombo1-> " + modelCombo1);
		session.setAttribute("modelCombo1", modelCombo1);
		Set<AttributeValue> attr2Values = new TreeSet<AttributeValue>(
			this.modelComboService.getSecondLevelAttrValuesFromModelCombos(modelCombo1));
		//System.out.println("attr2 -> " + attr2Values);
		Set<AttributeValue> attr2ValuesRestricted = Util.getActiveAttributeValues(attr2Values);

		Attribute attribute = dtAttributeService.findAttributeByOrder(2);
		model.addAttribute("attr2Name", attribute.getName());
		model.addAttribute("attr2Values", attr2ValuesRestricted);
		model.addAttribute("itemId", itemId);
		model.addAttribute("docName", docName);
		// model.addAttribute("attr2Values", attr2Values);
		model.addAttribute("QU", true);
		return "attr2Form";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr3", method = RequestMethod.GET)
	public String thirdSetAttributes(String itemId,long attr2Value,@RequestParam("docName")String docName, Model model, HttpSession session) {

		//System.out.println("Attr1Value -> " + attr2Value);
		AttributeValue attr2 = this.dtAttributeValueService.findDtAttrValueById(attr2Value);
		session.setAttribute("attributeValue2", attr2);
		Set<ModelCombo> modelCombo1 = (Set<ModelCombo>) session.getAttribute("modelCombo1");
		Set<ModelCombo> modelCombo2 = this.modelComboService.findMatchForSecondLevelAttrValue(modelCombo1, attr2);
		//System.out.println("modelCombo2-> " + modelCombo2);
		session.setAttribute("modelCombo2", modelCombo2);
		Set<AttributeValue> attr3Values = new TreeSet<AttributeValue>(
			this.modelComboService.getThirdLevelAttrValuesFromModelCombos(modelCombo2));
		//System.out.println("attr3 -> " + attr3Values);
		Set<AttributeValue> attr3ValuesRestricted = Util.getActiveAttributeValues(attr3Values);

		Attribute attribute = dtAttributeService.findAttributeByOrder(3);
		model.addAttribute("attr3Name", attribute.getName());
		model.addAttribute("attr3Values", attr3ValuesRestricted);
		model.addAttribute("itemId", itemId);
		model.addAttribute("docName", docName);
		// model.addAttribute("attr3Values", attr3Values);
		model.addAttribute("QU", true);
		return "attr3Form";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr4", method = RequestMethod.GET)
	public String fourthSetAttributes(String itemId,long attr3Value,@RequestParam("docName")String docName, Model model, HttpSession session) {

		AttributeValue attr3 = this.dtAttributeValueService.findDtAttrValueById(attr3Value);
		session.setAttribute("attributeValue3", attr3);
		Set<ModelCombo> modelCombo2 = (Set<ModelCombo>) session.getAttribute("modelCombo2");
		Set<ModelCombo> modelCombo3 = this.modelComboService.findMatchForThirdLevelAttrValue(modelCombo2, attr3);
		//System.out.println("modelCombo2-> " + modelCombo2);
		session.setAttribute("modelCombo3", modelCombo3);
		Set<AttributeValue> attr4Values = new TreeSet<AttributeValue>(
			this.modelComboService.getFourthLevelAttrValuesFromModelCombos(modelCombo3));
		//System.out.println("attr3 -> " + attr3Values);
		Set<AttributeValue> attr4ValuesRestricted = Util.getActiveAttributeValues(attr4Values);

		Attribute attribute = dtAttributeService.findAttributeByOrder(4);
		model.addAttribute("attr4Name", attribute.getName());
		model.addAttribute("attr4Values", attr4ValuesRestricted);
		model.addAttribute("itemId", itemId);
		model.addAttribute("docName", docName);
		// model.addAttribute("attr3Values", attr3Values);
		model.addAttribute("QU", true);
		return "attr4Form";
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr5", method = RequestMethod.GET)
	public String fifthSetAttributes(String itemId,long attr4Value,@RequestParam("docName")String docName, Model model, HttpSession session) {

		AttributeValue attr4 = this.dtAttributeValueService.findDtAttrValueById(attr4Value);
		session.setAttribute("attributeValue4", attr4);

		Set<ModelCombo> modelCombo3 = (Set<ModelCombo>) session.getAttribute("modelCombo3");
		Set<ModelCombo> modelCombo4 = this.modelComboService.findMatchForFourthLevelAttrValue(modelCombo3, attr4);
		//System.out.println("modelCombo3-> " + modelCombo3);
		session.setAttribute("modelCombo4", modelCombo4);
		Set<AttributeValue> attr5Values = new TreeSet<AttributeValue>(
			this.modelComboService.getFifthLevelAttrValuesFromModelCombos(modelCombo4));
		//System.out.println("attr4 -> " + attr4Values);
		Set<AttributeValue> attr5ValuesRestricted = Util.getActiveAttributeValues(attr5Values);

		Attribute attribute = dtAttributeService.findAttributeByOrder(5);
		model.addAttribute("attr5Name", attribute.getName());
		model.addAttribute("attr5Values", attr5ValuesRestricted);
		model.addAttribute("itemId", itemId);
		model.addAttribute("docName", docName);
		// model.addAttribute("attr4Values", attr4Values);
		model.addAttribute("QU", true);
		return "attr5form";
	}
	
	private List<Doctype> getDocTypeLists() {

		List<Doctype> docTypeLists = doctypeService.findAllDoctype();
		Collections.sort(docTypeLists);
		return docTypeLists;
	}

	@RequestMapping(value = "/qupload", method = RequestMethod.GET)
	public String quickUpload(String itemId,long attr5Value,@RequestParam("docName")String docName, Model model, HttpSession session) throws Exception {

		AttributeValue attr5 = this.dtAttributeValueService.findDtAttrValueById(attr5Value);
		session.setAttribute("attributeValue5", attr5);
		model.addAttribute("doctypes", getDocTypeLists());
		model.addAttribute("itemId", itemId);
		model.addAttribute("docName", docName);
		// model.addAttribute("doctypes",
		// servletContext.getAttribute("doctypes"));
		return "quickUploadForm";
	}

	@RequestMapping(value = "/getSecurityGroups", method = RequestMethod.GET)
	public String getSecurityGroups(@RequestParam("itemId")String itemId,@RequestParam("documentTypeWof") long docType,@RequestParam("docName")String docName, Model model, HttpSession session)
		throws Exception {

		AttributeValue attrv1 = (AttributeValue) session.getAttribute("attributeValue1");
		AttributeValue attrv2 = (AttributeValue) session.getAttribute("attributeValue2");
		AttributeValue attrv3 = (AttributeValue) session.getAttribute("attributeValue3");
		AttributeValue attrv4 = (AttributeValue) session.getAttribute("attributeValue4");
		AttributeValue attrv5 = (AttributeValue) session.getAttribute("attributeValue5");
		Doctype doctype = doctypeService.findDoctypeById(docType);

		List<AttributeValue> attrVals = new ArrayList<AttributeValue>();
		attrVals.add(attrv1);
		attrVals.add(attrv2);
		attrVals.add(attrv3);
		attrVals.add(attrv4);
		attrVals.add(attrv5);
		String sgAttrs = (String) servletContext.getAttribute("sgAttrs");
		SecurityGroup securityGroup = this.securityGroupComboService.findSecurityGroup(attrVals, sgAttrs, doctype);

		List<SecurityGroup> secGroups = this.securityGroupComboService.findDefaultSecurityGroups(attrVals, sgAttrs, doctype);
		SecurityGroup sg=securityGroupService.findSecurityGroupByName("Open");
		session.setAttribute("sgForAttr", securityGroup);
		//System.out.println("SEC GROUPPPPPPPP -> " + securityGroup);
		model.addAttribute("open", sg.getId());
		model.addAttribute("secGroups", secGroups);
		model.addAttribute("defaultSG", securityGroup);
		model.addAttribute("itemId", itemId);
		model.addAttribute("docName", docName);
		return "securityGroupsQuplForm";

	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/upload", method = RequestMethod.POST, headers = { "content-type=multipart/form-data" })
	@ResponseBody
	public String quickUploads(@RequestParam("itemId")String itemId,@RequestParam("quickUpl") CommonsMultipartFile file,
		@RequestParam("securitySettingQupl") String securitySetting, @RequestParam("datepickerqUp") String targetDate,
		@RequestParam("keywordsWof") String keywords, @RequestParam("documentTypeWof") int docType,
		@RequestParam("ebnumberWof") String eb_number,@RequestParam("docName")String docName, HttpSession session, Model model, HttpServletResponse response) {

		String msg = null;
		String FilePath = null;
		String folderName = null;
		String sgAttrs = (String) servletContext.getAttribute("sgAttrs");
		int result = 0;
		Date date = null;
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");


		User user = (User) session.getAttribute("LOGGED_IN_USER");

		Doctype doctype = doctypeService.findDoctypeById(docType);

		FilePath = (String) servletContext.getAttribute("filePath.quickupload");
		folderName = (String) servletContext.getAttribute("foldername.quickupload");
		String filePathLocal=(String) servletContext.getAttribute("foldername.localUpload");

		Attribute attribute1 = dtAttributeService.findAttributeByOrder(1);
		Attribute attribute2 = dtAttributeService.findAttributeByOrder(2);
		Attribute attribute3 = dtAttributeService.findAttributeByOrder(3);
		Attribute attribute4 = dtAttributeService.findAttributeByOrder(4);
		Attribute attribute5 = dtAttributeService.findAttributeByOrder(5);

		AttributeValue attributeValue1 = (AttributeValue) session.getAttribute("attributeValue1");
		AttributeValue attributeValue2 = (AttributeValue) session.getAttribute("attributeValue2");
		AttributeValue attributeValue3 = (AttributeValue) session.getAttribute("attributeValue3");
		AttributeValue attributeValue4 = (AttributeValue) session.getAttribute("attributeValue4");
		AttributeValue attributeValue5 = (AttributeValue) session.getAttribute("attributeValue5");

		String fileName = file.getOriginalFilename();
		StringBuilder tempfilenamesb = Util.generateFileName(attributeValue1.getAbbreviation(), doctype.getAbbreviation(),
			keywords, attributeValue5.getAbbreviation(), attributeValue4.getAbbreviation());

		DocumentUUID docUuid = new DocumentUUID(0, UUID.randomUUID().toString());

		long docUid = documentUuidService.save(docUuid);

		fileName = tempfilenamesb.append(docUid).toString().concat(Util.getType(fileName));

		
	//	UploadPhotos uploadPhotos = new UploadPhotos(FilePath, dsUser, dsPassword, file);

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

			if (targetDate == null || targetDate.equals("")) {
				date = new Date();

			}
			else {

				date = (Date) dateFormat.parseObject(targetDate);
			}

			File fileSaveLocal= new File(filePathLocal+fileName);
			FileUtils.writeByteArrayToFile(fileSaveLocal, file.getBytes());

			TempDocuments td=new TempDocuments();
			td.setDoctypeId(Long.valueOf(docType));
			td.setTargetDate(date);
			td.setFilePath(filePathLocal);
			td.setAuthor(user.getUserName());
			td.setKeywords(keywords);
			td.setName(fileName);
			td.setWip('N');
			td.setCreatedDate(new Date());
			td.setRevisionable(true);
			td.setAbandon(false);
			td.setDiscriminator('D');
			td.setAttributeValue1(attributeValue1.getId());
			td.setAttributeValue2(attributeValue2.getId());
			td.setAttributeValue3(attributeValue3.getId());
			td.setAttributeValue4(attributeValue4.getId());
			td.setAttributeValue5(attributeValue5.getId());
			if (securitySetting.equals("D")) {
				long sgId = Long.parseLong(securitySetting);
				//SecurityGroup sg = this.securityGroupComboService.findDefaultSecurityGroupForCombo(doc, sgAttrs);
				//System.out.println("DefaultSG " + sg);
				td.setSecurityGroupId(sgId);
			}
			else if (securitySetting.equals("N")) {
				//td.setSecurityGroupId(null);
			}
			else {
			
			
				long sgId = Long.parseLong(securitySetting);
				
				//System.out.println("############## SG=====> " + sg);
				td.setSecurityGroupId(sgId);
			

			tempDocumentsService.save(td);
			result = 1;
			}

		}

		catch (Exception e) {
			logger.error("/upload Error message "+e);
			e.printStackTrace();
			msg = "Unknown Error";
			result = 0;
		}

		finally {
			String html = "<script language='javascript' type='text/javascript'> window.top.window.quickUploadCompletedForm("
				+ result + ",'" + fileName + "','"+itemId+"','"+docName+"');</script>";
			return html;
		}

	}
	
	
	
	
	@RequestMapping(value = "/removeTempDoc", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void getSecurityGroups(@RequestParam("docName")String documentName, Model model, HttpSession session)
		throws Exception {

		TempDocuments tempDoc=tempDocumentsService.findTempDocumentsByDocumentName(documentName);
		if(tempDoc!=null){
		File fileL=new File(tempDoc.getFilePath()+tempDoc.getName());
		fileL.delete();
		tempDocumentsService.delete(tempDoc);
		}
	}
	
	
	@RequestMapping(value = "/goShowFormTypes", method = RequestMethod.GET)
	public String getFormTypes(Model model, HttpSession session)
		throws Exception {

		List<FormDefs> fds=formDefsService.findAllFormDefs();
		model.addAttribute("title", "Form Types");
		model.addAttribute("formTypeList", fds);
		return "formTypesListForSecGrp";
	}
	

	@RequestMapping(value = "/goShowSecGroupAssignment", method = RequestMethod.GET)
	public String getSecGrpForFormTypes(@RequestParam("id")long id,Model model, HttpSession session)
		throws Exception {

		FormDefs fd=formDefsService.findFormDefsById(id);
		List<SecurityGroup> sgs=securityGroupService.findAllSecurityGroups();
		List<SecurityGroupForm> sgf=securityGroupFormService.findSecurityGroupFormByformDefs(fd);
		
		model.addAttribute("secGrpListsForm", sgf);
		model.addAttribute("formTypeId", id);
		model.addAttribute("secGrpLists", sgs);
		model.addAttribute("formName", fd.getDescription());
		model.addAttribute("title", "Security Group Assignment");
		
		return "assignSecurityGroupForForm";
	}
	
	@RequestMapping(value = "/deleteSecGroupFromForm", method = RequestMethod.GET)
	public String deleteSecGrpFromForm(@RequestParam("secGrpId")long secGrpId,@RequestParam("formTypeId")long formTypeId,Model model, HttpSession session)
		throws Exception {

		SecurityGroup sg=securityGroupService.findSecurityGroupById(secGrpId);
		FormDefs fd=formDefsService.findFormDefsById(formTypeId);
		
		SecurityGroupForm securityGroupForm=securityGroupFormService.findSecurityGroupForm(sg, fd);
		if(securityGroupForm!=null)
			securityGroupFormService.delete(securityGroupForm);
			
		List<SecurityGroup> sgs=securityGroupService.findAllSecurityGroups();
		List<SecurityGroupForm> sgf=securityGroupFormService.findSecurityGroupFormByformDefs(fd);
		
		model.addAttribute("secGrpListsForm", sgf);
		model.addAttribute("formTypeId", formTypeId);
		model.addAttribute("secGrpLists", sgs);
		model.addAttribute("formName", fd.getDescription());
		model.addAttribute("title", "Security Group Assignment");
		
		return "assignSecurityGroupForForm";
	}

	@RequestMapping(value = "/addSecGroupFromForm", method = RequestMethod.GET)
	public String addSecGrpFromForm(@RequestParam("secGrpId")long secGrpId,@RequestParam("formTypeId")long formTypeId,Model model, HttpSession session)
		throws Exception {

		SecurityGroup sg=securityGroupService.findSecurityGroupById(secGrpId);
		FormDefs fd=formDefsService.findFormDefsById(formTypeId);
		
		SecurityGroupForm securityGroupForm=securityGroupFormService.findSecurityGroupForm(sg, fd);
		if(securityGroupForm==null){
		securityGroupForm=new SecurityGroupForm(sg, fd);
		securityGroupFormService.save(securityGroupForm);
		}
		
		List<SecurityGroup> sgs=securityGroupService.findAllSecurityGroups();
		List<SecurityGroupForm> sgf=securityGroupFormService.findSecurityGroupFormByformDefs(fd);
		
		model.addAttribute("secGrpListsForm", sgf);
		model.addAttribute("formTypeId", formTypeId);
		model.addAttribute("secGrpLists", sgs);
		model.addAttribute("formName", fd.getDescription());
		model.addAttribute("title", "Security Group Assignment");
		
		return "assignSecurityGroupForForm";
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

			AttributeValue attributeValue1 = (AttributeValue) session.getAttribute("attributeValue1");
			AttributeValue attributeValue2 = (AttributeValue) session.getAttribute("attributeValue2");
			AttributeValue attributeValue3 = (AttributeValue) session.getAttribute("attributeValue3");
			AttributeValue attributeValue4 = (AttributeValue) session.getAttribute("attributeValue4");
			AttributeValue attributeValue5 = (AttributeValue) session.getAttribute("attributeValue5");
			
			AttributeValue at=attributeValueDao.findAttributeValueById(105);
			System.out.println(at.getValue());
			
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
					
					
					tempDocumentsService.delete(tempDoc);
					DocumentAttribute da1 = new DocumentAttribute(doc, attribute1, attributeValue1);
					DocumentAttribute da2 = new DocumentAttribute(doc, attribute2, attributeValue2);
					DocumentAttribute da3 = new DocumentAttribute(doc, attribute3, attributeValue3);
					DocumentAttribute da4 = new DocumentAttribute(doc, attribute4, attributeValue4);
					DocumentAttribute da5 = new DocumentAttribute(doc, attribute5, attributeValue5);
					Set<DocumentAttribute> docAttrs = new HashSet<DocumentAttribute>();
					docAttrs.add(da1);
					docAttrs.add(da2);
					docAttrs.add(da3);
					docAttrs.add(da4);
					docAttrs.add(da5);
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
		
		@RequestMapping(value = "/goShowFormTypesForModel", method = RequestMethod.GET)
		public String getFormTypesForModel(Model model, HttpSession session)
			throws Exception {

			List<FormDefs> fds=formDefsService.findAllFormDefs();
			model.addAttribute("title", "Form Type WorkFlow Management");
			model.addAttribute("formTypeList", fds);
			return "formsModelManagement";
		}
		
		@RequestMapping(value = "/goShowFrmModelAssignment", method = RequestMethod.GET)
		public String getModelForFormTypes(@RequestParam("id")long id,Model model, HttpSession session)
			throws Exception {

			FormDefs fd=formDefsService.findFormDefsById(id);
			Forms forms=formsService.findFormsByFormDefId(fd);
			Set<WfModel> formsModel=forms.getModels();
			
			model.addAttribute("modelListsForm", formsModel);
			model.addAttribute("formTypeId", id);
			model.addAttribute("modelLists", modelService.listModelsByType('F'));
			model.addAttribute("formName", fd.getDescription());
			model.addAttribute("title", "WorkFlow Assignment");
			
			return "assignModelForForm";
		}
		
		@RequestMapping(value = "/deleteModelFromForm", method = RequestMethod.GET)
		public String deleteModelFromForm(@RequestParam("modelId")long modelId,@RequestParam("formTypeId")long formTypeId,Model model, HttpSession session)
			throws Exception {

			WfModel wfModel=modelService.findModelById(modelId);
			FormDefs fd=formDefsService.findFormDefsById(formTypeId);
			Forms form=formsService.findFormsByFormDefId(fd);
			
			FormsModel formsModel=formsModelService.findFormsModelByFormIdAndModelId(form, wfModel);
			if(formsModel!=null)
				formsModelService.delete(formsModel);
				
			Forms newform=formsService.findFormsByFormDefId(fd);
			Set<WfModel> formModels=newform.getModels();
			
			model.addAttribute("modelListsForm", formModels);
			model.addAttribute("formTypeId", formTypeId);
			model.addAttribute("modelLists",modelService.listModelsByType('F'));
			model.addAttribute("formName", fd.getDescription());
			model.addAttribute("title", "WorkFlow Assignment");
			
			return "assignModelForForm";
		}

		@RequestMapping(value = "/addModelForForm", method = RequestMethod.GET)
		public String addModelFromForm(@RequestParam("modelId")long modelId,@RequestParam("formTypeId")long formTypeId,Model model, HttpSession session)
			throws Exception {

			WfModel wfModel=modelService.findModelById(modelId);
			FormDefs fd=formDefsService.findFormDefsById(formTypeId);
			Forms form=formsService.findFormsByFormDefId(fd);
			
			FormsModel formsModel=formsModelService.findFormsModelByFormIdAndModelId(form, wfModel);
			if(formsModel==null){
			formsModel=new FormsModel(form, wfModel);
			formsModelService.save(formsModel);
			}
			Forms newform=formsService.findFormsByFormDefId(fd);
			Set<WfModel> formModels=newform.getModels();
			
			model.addAttribute("modelListsForm", formModels);
			model.addAttribute("formTypeId", formTypeId);
			model.addAttribute("modelLists", modelService.listModelsByType('F'));
			model.addAttribute("formName", fd.getDescription());
			model.addAttribute("title", "WorkFlow Assignment");
			
			return "assignModelForForm";
		}
	
}
	
*/