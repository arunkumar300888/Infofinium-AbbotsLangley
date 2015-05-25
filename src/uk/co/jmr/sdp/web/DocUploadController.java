package uk.co.jmr.sdp.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javassist.compiler.ast.NewExpr;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.DocumentReference;
import uk.co.jmr.sdp.domain.DocumentTrail;
import uk.co.jmr.sdp.domain.DocumentUUID;
import uk.co.jmr.sdp.domain.DocumentVersion;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.ModelCombo;
import uk.co.jmr.sdp.domain.photoupload.UploadPhotos;
import uk.co.jmr.sdp.ds.DocumentStorage;
import uk.co.jmr.sdp.service.CompanyUserService;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.DocumentTrailService;
import uk.co.jmr.sdp.service.DocumentUuidService;
import uk.co.jmr.sdp.service.DocumentVersionService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.ModelComboService;
import uk.co.jmr.sdp.service.SecurityGroupComboService;
import uk.co.jmr.sdp.service.SecurityGroupService;
import uk.co.jmr.sdp.service.UserService;
import uk.co.jmr.sdp.web.revision.CloneDocument;
import uk.co.jmr.sdp.web.search.QuickSearch;
import uk.co.jmr.sdp.web.util.UserInfo;
import uk.co.jmr.sdp.web.util.Util;

import com.ardhika.wfar.WfAttribute;
import com.ardhika.wfar.WfAttributeType;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfModel;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.service.CaseService;
import com.ardhika.wfar.service.ModelService;

@Controller
@RequestMapping(value = "/docupload")
public class DocUploadController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private ModelComboService modelComboService;
	@Autowired
	private DtAttributeService dtAttributeService;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;
	@Autowired
	private DocumentStorage documentStorage;
	@Autowired
	private ModelService modelService;
	@Autowired
	private DoctypeService doctypeService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private DocumentTrailService documentTrailService;
	@Autowired
	private CaseService caseService;
	@Autowired
	private DocumentUuidService documentUuidService;
	@Autowired
	private SecurityGroupComboService securityGroupComboService;
	@Autowired
	private SecurityGroupService securityGroupService;
	@Autowired
	private UserService userService;
	@Autowired
	private DocumentVersionService documentVersionService;
	@Autowired
	private CompanyUserService companyUserService;

	Logger logger=Logger.getLogger(DocUploadController.class);
	
	@RequestMapping(value = "/attr1", method = RequestMethod.GET)
	public String firstSetAttributes(Model model, HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		session.removeAttribute("referencedocs");
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);
		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		Set<AttributeValue> attr1ValuesRestricted=new HashSet<AttributeValue>();
		//Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
		for(CompanyUser cu:cus){
			attr1ValuesRestricted.add(cu.getAttrValue());
		}
		
		model.addAttribute("attr1Name", attribute.getName());
		model.addAttribute("attr1Values", Util.getActiveAttributeValues(attr1ValuesRestricted));
		// model.addAttribute("attr1Values", attr1Values);
		model.addAttribute("DT", false);
		return "attr1";
	}

	@RequestMapping(value = "/attr2", method = RequestMethod.GET)
	public String secondSetAttributes(long attr1Value, Model model, HttpSession session) {

		// System.out.println("Attr1Value -> " + attr1Value);
		AttributeValue attr1 = this.dtAttributeValueService.findDtAttrValueById(attr1Value);
		session.setAttribute("attributeValue1", attr1);
		Set<ModelCombo> modelCombo1 = this.modelComboService.findMatchForFirstLevelAttrValue(attr1Value);
		//System.out.println("modelCombo1-> " + modelCombo1);
		session.setAttribute("modelCombo1", modelCombo1);
		Set<AttributeValue> attr2Values = new TreeSet<AttributeValue>(
			this.modelComboService.getSecondLevelAttrValuesFromModelCombos(modelCombo1));
		// System.out.println("attr2 -> " + attr2Values);
		Set<AttributeValue> attr2ValuesRestricted = Util.getActiveAttributeValues(attr2Values);
		Attribute attribute = dtAttributeService.findAttributeByOrder(2);
		model.addAttribute("attr2Name", attribute.getName());
		model.addAttribute("attr2Values", attr2ValuesRestricted);
		// model.addAttribute("attr2Values", attr2Values);
		model.addAttribute("DT", false);
		return "attr2";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr3", method = RequestMethod.GET)
	public String thirdSetAttributes(long attr2Value, Model model, HttpSession session) {

		// System.out.println("Attr1Value -> " + attr2Value);
		AttributeValue attr2 = this.dtAttributeValueService.findDtAttrValueById(attr2Value);
		session.setAttribute("attributeValue2", attr2);
		Set<ModelCombo> modelCombo1 = (Set<ModelCombo>) session.getAttribute("modelCombo1");
		Set<ModelCombo> modelCombo2 = this.modelComboService.findMatchForSecondLevelAttrValue(modelCombo1, attr2);
		// System.out.println("modelCombo2-> " + modelCombo2);
		session.setAttribute("modelCombo2", modelCombo2);
		Set<AttributeValue> attr3Values = new TreeSet<AttributeValue>(
			this.modelComboService.getThirdLevelAttrValuesFromModelCombos(modelCombo2));
		// System.out.println("attr3 -> " + attr3Values);
		Set<AttributeValue> attr3ValuesRestricted = Util.getActiveAttributeValues(attr3Values);
		Attribute attribute = dtAttributeService.findAttributeByOrder(3);
		model.addAttribute("attr3Name", attribute.getName());
		model.addAttribute("attr3Values", attr3ValuesRestricted);
		// model.addAttribute("attr3Values", attr3Values);
		model.addAttribute("DT", false);
		return "attr3";
		
		
		
	}

	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr4", method = RequestMethod.GET)
	public String fourthSetAttributes(long attr3Value, Model model, HttpSession session) {

		AttributeValue attr3 = this.dtAttributeValueService.findDtAttrValueById(attr3Value);
		session.setAttribute("attributeValue3", attr3);
		Set<ModelCombo> modelCombo2 = (Set<ModelCombo>) session.getAttribute("modelCombo2");
		Set<ModelCombo> modelCombo3 = this.modelComboService.findMatchForThirdLevelAttrValue(modelCombo2, attr3);
		// System.out.println("modelCombo3-> " + modelCombo3);
		session.setAttribute("modelCombo3", modelCombo3);
		Set<AttributeValue> attr4Values = new TreeSet<AttributeValue>(
			this.modelComboService.getFourthLevelAttrValuesFromModelCombos(modelCombo3));
		// System.out.println("attr4 -> " + attr4Values);
		Set<AttributeValue> attr4ValuesRestricted = Util.getActiveAttributeValues(attr4Values);
		Attribute attribute = dtAttributeService.findAttributeByOrder(4);
		model.addAttribute("attr4Name", attribute.getName());
		model.addAttribute("attr4Values", attr4ValuesRestricted);
		// model.addAttribute("attr4Values", attr4Values);
		model.addAttribute("DT", false);
		return "attr4";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr5", method = RequestMethod.GET)
	public String fifthhSetAttributes(long attr4Value, Model model, HttpSession session) {

		AttributeValue attr4 = this.dtAttributeValueService.findDtAttrValueById(attr4Value);
		session.setAttribute("attributeValue4", attr4);
		Set<ModelCombo> modelCombo3 = (Set<ModelCombo>) session.getAttribute("modelCombo3");
		Set<ModelCombo> modelCombo4 = this.modelComboService.findMatchForFourthLevelAttrValue(modelCombo3, attr4);
		// System.out.println("modelCombo3-> " + modelCombo3);
		session.setAttribute("modelCombo4", modelCombo4);
		Set<AttributeValue> attr5Values = new TreeSet<AttributeValue>(
			this.modelComboService.getFifthLevelAttrValuesFromModelCombos(modelCombo3));
		// System.out.println("attr4 -> " + attr4Values);
		Set<AttributeValue> attr4ValuesRestricted = Util.getActiveAttributeValues(attr5Values);
		Attribute attribute = dtAttributeService.findAttributeByOrder(5);
		model.addAttribute("attr5Name", attribute.getName());
		model.addAttribute("attr5Values", attr4ValuesRestricted);
		// model.addAttribute("attr4Values", attr4Values);
		model.addAttribute("DT", false);
		return "attr5";
	}*/
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDoctypes", method = RequestMethod.GET)
	public String getDoctypes(long attr3Value, Model model, HttpSession session) {

		AttributeValue attr3 = this.dtAttributeValueService.findDtAttrValueById(attr3Value);
		session.setAttribute("attributeValue3", attr3);
		Set<ModelCombo> modelCombo2 = (Set<ModelCombo>) session.getAttribute("modelCombo2");
		Set<ModelCombo> modelCombo3 = (Set<ModelCombo>) this.modelComboService.findMatchForThirdLevelAttrValue(modelCombo2,
			attr3);
		session.setAttribute("modelCombo3", modelCombo3);
		// System.out.println("modelCombo4->> " + modelCombo4);
		Set<Doctype> doctypes = new TreeSet<Doctype>();
		for (ModelCombo m : modelCombo3) {
			// doctypes.add(m.getDoctype());
			// For Active Doc types
			char isActive = m.getDoctype().getIsActive();
			// && m.getDoctype().isWorkflow()==true
			if (isActive == 'Y')
				doctypes.add(m.getDoctype());
		}
		// System.out.println("doctypes-> " + doctypes);
		model.addAttribute("doctypes", doctypes);
		model.addAttribute("DT", false);
		return "listDoctypes";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectModels", method = RequestMethod.GET)
	public String listModels(long doctypeId, Model model, HttpSession session) {

		Doctype doctype = this.doctypeService.findDoctypeById(doctypeId);
		// System.out.println("*** Doc type:" + doctype);
		session.setAttribute("doctype", doctype);
		Set<ModelCombo> modelCombo3 = (Set<ModelCombo>) session.getAttribute("modelCombo3");
		Set<ModelCombo> modelsSet = new TreeSet<ModelCombo>(this.modelComboService.getModelsMatchForDoctype(modelCombo3,
			doctypeId));
		session.setAttribute("modelsSet", modelsSet);
		// System.out.println("Model List:" + modelsSet.size());
		model.addAttribute("DT", false);
		return "listModels";
	}

	@RequestMapping(value = "/editAttr", method = RequestMethod.GET)
	public String editAttr(String modelName, Model model, HttpSession session) throws Exception {

		model.addAttribute("modelName", modelName);
		//System.out.println("modelName- > " + modelName);
		WfModel wfModel = this.modelService.findModelByName(modelName);
		Set<WfAttribute> modelAttr = wfModel.getAttribs();
		Set<WfAttribute> attrSet = new HashSet<WfAttribute>();
		boolean isMdlName = false;
		boolean isMdlDisp = false;
		for (WfAttribute a : modelAttr) {
			if (a.getUserEditable().equalsIgnoreCase("Y")) {
				//System.out.println("a-> " + a);
				attrSet.add(a);
			}
			if (a.getName().equals("MDL Number")) {
				isMdlName = true;
			}
		}
		WfAttribute[] attr = attrSet.toArray(new WfAttribute[attrSet.size()]);
		Arrays.sort(attr);
		Doctype doctype = (Doctype) session.getAttribute("doctype");
		if (isMdlName == true && doctype.getDoctypeName().equals("Method Statement")) {
			isMdlDisp = true;
		}
		//System.out.println("attr-> " + attr);
		model.addAttribute("modelName", modelName);
		model.addAttribute("modelId", wfModel.getId());
		model.addAttribute("timeBound", wfModel.getTimeBound());
		model.addAttribute("attr", attr);
		model.addAttribute("isMdlDisp", isMdlDisp);
		// Added For Default Security Setting to be filled
		// Start From Here - 192
		AttributeValue attrv1 = (AttributeValue) session.getAttribute("attributeValue1");
		AttributeValue attrv2 = (AttributeValue) session.getAttribute("attributeValue2");
		AttributeValue attrv3 = (AttributeValue) session.getAttribute("attributeValue3");
		/*AttributeValue attrv4 = (AttributeValue) session.getAttribute("attributeValue4");
		AttributeValue attrv5 = (AttributeValue) session.getAttribute("attributeValue5");*/
		List<AttributeValue> attrVals = new ArrayList<AttributeValue>();
		attrVals.add(attrv1);
		attrVals.add(attrv2);
		attrVals.add(attrv3);
		/*attrVals.add(attrv4);
		attrVals.add(attrv5);*/
		// Collections.sort(attrVals);
		//System.out.println("ATTRVAL " + attrVals);
		String sgAttrs = (String) servletContext.getAttribute("sgAttrs");
		SecurityGroup securityGroup = this.securityGroupComboService.findSecurityGroup(attrVals, sgAttrs, doctype);
		List<SecurityGroup> secGroups = this.securityGroupComboService.findDefaultSecurityGroups(attrVals, sgAttrs, doctype);
		// For Duplicate Removal
		Set<SecurityGroup> secGroupsSet = new HashSet<SecurityGroup>();
		for (SecurityGroup secGroup : secGroups) {
			secGroupsSet.add(secGroup);
		}
		SecurityGroup sgO=securityGroupService.findSecurityGroupByName("Open");
		session.setAttribute("sgForAttr", securityGroup);
		//System.out.println("SEC GROUPPPPPPPP -> " + securityGroup);
		// model.addAttribute("secGroups", secGroups);
		model.addAttribute("secGroups", secGroupsSet);
		model.addAttribute("defaultSG", securityGroup);
		model.addAttribute("open", sgO.getId());
		return "editAttributes";
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/uploadPhotos", method = RequestMethod.POST, headers = { "content-type=multipart/form-data" })
	@ResponseBody
	public String uploadPhotos(@RequestParam("photo") CommonsMultipartFile file, Model model, HttpServletResponse response) {
		String msg = null;
		int result = 0;
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");
		String FilePath = (String) servletContext.getAttribute("filePath");
		String folderName = (String) servletContext.getAttribute("foldername");
		UploadPhotos uploadPhotos = new UploadPhotos(FilePath, dsUser, dsPassword, file);
		try {
			uploadPhotos.upload(documentStorage, folderName);
			result = 1;
		}
		catch (Exception e) {
			msg = "Unknown Error";
			logger.error("/uploadPhoto Error message "+e);
			result = 0;
		}
		finally {
			String html = "<script language='javascript' type='text/javascript'> window.top.window.uploadPhotoCompleted("
				+ result + ");</script>";
			return html;
		}
	}

	// For Quick Upload with all metadata change req on 04.01.13
	// For Quickupload with doc type only on 03.01.13
	@RequestMapping(value = "/docwithoutworkflow", method = RequestMethod.GET)
	public String docwithoutworkflow(Model model, HttpSession session) {

		List<Doctype> docTypeLists = doctypeService.findAllDoctype();
		Collections.sort(docTypeLists);
		model.addAttribute("doctypes", docTypeLists);
		return "quickUpload";
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/quickUpload", method = RequestMethod.POST, headers = { "content-type=multipart/form-data" })
	@ResponseBody
	public String quickUploads(@RequestParam("quickUpl") CommonsMultipartFile file, @RequestParam("documentType") int docType,
		HttpSession session, Model model, HttpServletResponse response) {

		String msg = null;
		String FilePath = null;
		String folderName = null;
		int result = 0;
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		Doctype documentType = doctypeService.findDoctypeById(docType);
		if (documentType.getDoctypeName().equals("Photo")) {
			FilePath = (String) servletContext.getAttribute("filePath.photos");
			folderName = (String) servletContext.getAttribute("foldername.photos");
		}
		else if (documentType.getDoctypeName().equals("Minutes of the Meeting")) {
			FilePath = (String) servletContext.getAttribute("filePath.meetings");
			folderName = (String) servletContext.getAttribute("foldername.meetings");
		}
		else {
			FilePath = (String) servletContext.getAttribute("filePath.videos");
			folderName = (String) servletContext.getAttribute("foldername.videos");
		}
		UploadPhotos uploadPhotos = new UploadPhotos(FilePath, dsUser, dsPassword, file);
		try {
			uploadPhotos.upload(documentStorage, folderName);
			Document doc = new Document();
			doc.setAuthor(user.getUserName());
			doc.setDoctype(documentType);
			doc.setFilePath(FilePath + Util.encode(folderName));
			doc.setName(file.getOriginalFilename());
			documentService.save(doc);
			result = 1;
		}
		catch (Exception e) {
			msg = "Unknown Error";
			result = 0;
		}
		finally {
			String html = "<script language='javascript' type='text/javascript'> window.top.window.quickUploadCompleted("
				+ result + ");</script>";
			return html;
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

	@SuppressWarnings("finally")
	@RequestMapping(value = "/saveCaseUploadDoc", method = RequestMethod.POST, headers = { "content-type=multipart/form-data" })
	@ResponseBody
	public String saveCaseAttr(@RequestParam("modelName") String modelName,
		@RequestParam("referencelinkvalues") String[] referencelinkvalues, @RequestParam("timeBound") String timeBound,
		@RequestParam("docFile") CommonsMultipartFile file, @RequestParam("securitySetting") String securitySetting,
		HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response) {

		// System.out.println("In SAVE CASE");
		// System.out.println("sECURITY sETTING= "+ securitySetting);
		
		boolean sgAvailable=true;
		String sgAttrs = (String) servletContext.getAttribute("sgAttrs");
		//String rootpath = (String) servletContext.getAttribute("alfresco.rootpath");
		/*
		 * reference document are tested before upload
		 */
		Set<Document> docreference = new HashSet<Document>();
		if (session.getAttribute("referencedocs") != null) {
			@SuppressWarnings("unchecked")
			Set<Long> docids = (Set<Long>) session.getAttribute("referencedocs");
			docreference = new HashSet<Document>(documentService.findDocsByIds(docids));
			session.removeAttribute("referencedocs");
		}
		// SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");
		String revisionporp = (String) servletContext.getAttribute("revision");
		// String restServiceUrl = (String) servletContext
		// .getAttribute("restServiceUrl");
		ArrayList<String> serviceUrlLists = getServiceUrls();
		WfModel wfModel = modelService.findModelByName(modelName);
		Set<WfAttribute> attributes = wfModel.getAttribs();
		String path = "";
		String msg = null;
		int result = 0;
		String fileName = file.getOriginalFilename();
		for (WfAttribute a : attributes) {
			if (a.getName().equalsIgnoreCase("FilePath")) {
				path = (String) a.getValue();
			}
		}
		try {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			//System.out.println("user-> " + user);
			UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
			for(String s:userInfo.getRoles()){
				if(s.equals("Third Party")){
					List<SecurityGroup> sgs=securityGroupService.findSecurityGroupsForUser(user);
					sgAvailable=false;
					result=3;
					if(sgs!=null)
					for(SecurityGroup sgrp:sgs){
						
						long sgId = Long.parseLong(securitySetting);
						SecurityGroup sg = this.securityGroupService.findSecurityGroupById(sgId);
						if(sgrp.getName().equals(sg.getName())){
							sgAvailable=true;
						}else{
							result=3;
							sgAvailable=false;
						}
					}
				}
			}
			
			if (securitySetting.equals("D")) {
				sgAvailable=true;
			}
			else if (securitySetting.equals("N")) {
				sgAvailable=true;
			}
			
			if(sgAvailable==true){
			String contentType = file.getContentType();
			//System.out.println("contentType-> " + contentType);
			Attribute attribute1 = dtAttributeService.findAttributeByOrder(1);
			Attribute attribute2 = dtAttributeService.findAttributeByOrder(2);
			Attribute attribute3 = dtAttributeService.findAttributeByOrder(3);
			/*Attribute attribute4 = dtAttributeService.findAttributeByOrder(4);
			Attribute attribute5 = dtAttributeService.findAttributeByOrder(5);*/
			AttributeValue attributeValue1 = (AttributeValue) session.getAttribute("attributeValue1");
			AttributeValue attributeValue2 = (AttributeValue) session.getAttribute("attributeValue2");
			AttributeValue attributeValue3 = (AttributeValue) session.getAttribute("attributeValue3");
			/*AttributeValue attributeValue4 = (AttributeValue) session.getAttribute("attributeValue4");
			AttributeValue attributeValue5 = (AttributeValue) session.getAttribute("attributeValue5");*/
			// For Naming Convention on 04.01.13
			Doctype doctype = (Doctype) session.getAttribute("doctype");
			String keywords = request.getParameter("it_Keywords");
			// Commenting For Naming Convention with Abbreviation
			StringBuilder tempfilenamesb = Util.generateFileName(attributeValue1.getAbbreviation(), doctype.getAbbreviation(),
				keywords, attributeValue3.getAbbreviation(), attributeValue2.getAbbreviation());
			// StringBuilder tempfilenamesb = Util.generateFileName(
			// attributeValue1.getValue(), doctype.getAbbreviation(),
			// keywords, attributeValue4.getValue(),
			// attributeValue3.getValue());
			DocumentUUID docUuid = new DocumentUUID(0, UUID.randomUUID().toString());
			long docUid = documentUuidService.save(docUuid);
			
			// fileName =
			// tempfilenamesb.append(docUid).toString().concat(Util.getType(fileName));
			if(doctype.getVersion().equals("Mj")){
			fileName = tempfilenamesb.append(docUid).append("-" + revisionporp + 1.0).toString().concat(Util.getType(fileName));
			}
			else{
				fileName = tempfilenamesb.append(docUid).append("-" + revisionporp + 1).toString().concat(Util.getType(fileName));
			}
			byte[] bytes = file.getBytes();
			// Test Ctrl Upload Large Files
			// documentStorage.createContent(dsUser, dsPassword, path, fileName,
			// contentType, bytes);
			//
			// documentStorage.createContent(dsUser, dsPassword, path, fileName,
			// contentType, file, restServiceUrl);
			
			documentStorage.createContent(dsUser, dsPassword, path, fileName, contentType, file, serviceUrlLists);
			WfCase wfCase = saveCaseWithAttributes(request, userInfo, wfModel);
			//System.out.println("case " + wfCase.getCreator());
			//System.out.println("Doctyp " + doctype);
			//System.out.println(path + " , " + user.getUserName() + " , " + fileName + " , " + wfCase.getId());
			Date target = wfCase.getWfAttribute("Target Date").getAttrDate();
			String ebn=null;
			
			if(wfCase.getWfAttribute("Eb Number")!=null){
			ebn = wfCase.getWfAttribute("Eb Number").getAttrText();
			}else if(wfCase.getWfAttribute("Reference Number")!=null){
				ebn=wfCase.getWfAttribute("Reference Number").getAttrText();
			}
			
			
			//For Document use the 'D' discriminator
//			Document doc = new Document(doctype, path, user.getUserName(), fileName, keywords, wfCase.getId(), new Date(),
//				target, ebn, 'Y');
			String userFormId=null;
			Document doc=null;
			if(wfModel.getName().equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
				 doc = new Document(doctype, path, user.getUserName(), fileName, keywords, wfCase.getId(), new Date(),
						target, ebn, 'N',userFormId,'D');
			}else{
			 doc = new Document(doctype, path, user.getUserName(), fileName, keywords, wfCase.getId(), new Date(),
			target, ebn, 'Y',userFormId,'D');
			}
			
			//System.out.println("PRint it: " + doc);
			DocumentAttribute da1 = new DocumentAttribute(doc, attribute1, attributeValue1);
			DocumentAttribute da2 = new DocumentAttribute(doc, attribute2, attributeValue2);
			DocumentAttribute da3 = new DocumentAttribute(doc, attribute3, attributeValue3);
			/*DocumentAttribute da4 = new DocumentAttribute(doc, attribute4, attributeValue4);
			DocumentAttribute da5 = new DocumentAttribute(doc, attribute5, attributeValue5);*/
			//System.out.println("da1-> " + da1 + ", " + da2 + ", " + da3 + ", " + da4);
			Set<DocumentAttribute> docAttrs = new HashSet<DocumentAttribute>();
			docAttrs.add(da1);
			docAttrs.add(da2);
			docAttrs.add(da3);
			/*docAttrs.add(da4);
			docAttrs.add(da5);*/
			doc.setDocumentAttributes(docAttrs);
			/*
			 * setting documentreference set object in doc object
			 */
			Set<DocumentReference> docrefset = new HashSet<DocumentReference>();
			for (Document docref : docreference) {
				DocumentReference documentReference = new DocumentReference();
				documentReference.setDocumentId(doc.getId());
				documentReference.setReferenceDocument(docref);
				docrefset.add(documentReference);
			}
			if (!docrefset.isEmpty()) {
				doc.setDocumentReference(docrefset);
			}
			/*
			 * ends here
			 */
			// Insert Code For adding security grp to document - 1011
			if (securitySetting.equals("D")) {
				SecurityGroup sg = this.securityGroupComboService.findDefaultSecurityGroupForCombo(doc, sgAttrs);
				//System.out.println("DefaultSG " + sg);
				doc.setSecurityGroup(sg);
			}
			else if (securitySetting.equals("N")) {
				doc.setSecurityGroup(null);
			}
			else {
				long sgId = Long.parseLong(securitySetting);
				SecurityGroup sg = this.securityGroupService.findSecurityGroupById(sgId);
							doc.setSecurityGroup(sg);
						}
				//System.out.println("############## SG=====> " + sg);
				
			
			this.documentService.save(doc);
			// Commented by Karthik.V during intergration from Ardhika
			// DocumentTrail documentTrail = new DocumentTrail(doc,
			// user,"UploadedDuringNewCase", new Date());
			DocumentTrail documentTrail = new DocumentTrail(doc, user, "UploadedDuringNewCase", new Date(), "To Prepare");
			//System.out.println("document Trail create-> " + documentTrail);
			this.documentTrailService.save(documentTrail);
			msg = "SUCCESS";
			result = 1;
			}
			//System.out.println("msg---->" + msg);
		}
		catch (Exception e) {
			//System.out.println(e.getLocalizedMessage());
			msg = "Unknown Error";
			result = 0;
			e.printStackTrace();
			//System.out.println("***** msg---->" + msg);
		}
		finally {
			response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			/*
			 * String html =
			 * "<script language='javascript' type='text/javascript'> window.top.window.uploadNewDocCompleted("
			 * + result + ",'"+fileName+"');</script>";
			 */
			String html = "<script language='javascript' type='text/javascript'> window.top.window.uploadNewDocCompleted("
				+ result + ",'" + fileName + "','" + modelName + "');</script>";
			//System.out.println("****** html:" + html);
			// return hidMsgCreate;
			return html;
		}
	}

	private WfCase saveCaseWithAttributes(HttpServletRequest request, UserInfo userInfo, WfModel wfModel) throws Exception {

		// UserInfo userInfo = (UserInfo) session
		// .getAttribute("LOGGED_IN_USERINFO");
		WfStep step = modelService.createCase(wfModel.getName(), userInfo);
		WfCase wfCase = step.getOwningCase();
		String s;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (WfAttribute a : wfCase.getAttributes()) {
			if (a.getUserEditable().equalsIgnoreCase("Y")) {
				if (a.getType() == WfAttributeType.WF_ATTR_TEXT) {
					//System.out.println("*** Name:" + a.getName());
					s = request.getParameter("it_" + a.getName());
					// s=a.getName();
					//System.out.println("***** s:" + s);
					if (s.trim().length() > 0) {
						wfCase.setAttribute(a.getName(), s);
					}
				}
				else if (a.getType() == WfAttributeType.WF_ATTR_DATE) {
					s = request.getParameter("id_" + a.getName());
					//System.out.println("date s" + s);
					if (s != null && s.trim().length() != 0) {
						// date = dateFormat.parse(s);
						wfCase.setAttribute(a.getName(), dateFormat.parse(s));
					}
				}
				if (a.getType() == WfAttributeType.WF_ATTR_DECIMAL) {
					s = request.getParameter("if_" + a.getName());
					Double d = doubleParse(s);
					wfCase.setAttribute(a.getName(), d);
				}
				if (a.getType() == WfAttributeType.WF_ATTR_NUMBER) {
					s = request.getParameter("in_" + a.getName());
					Long l = longParse(s);
					wfCase.setAttribute(a.getName(), l);
				}
			}
		}
		this.caseService.saveCase(wfCase);
		//System.out.println("case--> " + wfCase);
		return wfCase;
	}

	private double doubleParse(String s) {

		try {
			double d = Double.parseDouble(s);
			return d;
		}
		catch (Exception e) {
			return 0.0d;
		}
	}

	private long longParse(String s) {

		try {
			long l = Long.parseLong(s);
			return l;
		}
		catch (Exception e) {
			return 0;
		}
	}

	// Document Referencing
	@RequestMapping(value = "/attachLinks", method = RequestMethod.GET)
	public String attachLinks() {

		return "AttachLinks";
	}

	@RequestMapping(value = "/updateLinks", method = RequestMethod.GET)
	public String updateLinks(@RequestParam("docId") long docId, @RequestParam("stepList") String stepList,
		@RequestParam("path") String path, @RequestParam("docName") String docName, @RequestParam("caseId") long caseId,
		@RequestParam("adminMeta")String isAdminMeta,Model model) {

		// Document document = documentService.findDocumentById(docId);
		model.addAttribute("edit", "update");
		model.addAttribute("docId", docId);
		model.addAttribute("docname", docName);
		model.addAttribute("filepath", path);
		model.addAttribute("caseid", caseId);
		model.addAttribute("stepList", stepList);
		Attribute attr3 = dtAttributeService.findAttributeByOrder(3);
		Attribute attr4 = dtAttributeService.findAttributeByOrder(4);
		model.addAttribute("attr3", attr3.getName());
		model.addAttribute("attr4", attr4.getName());
		model.addAttribute("adminMeta", isAdminMeta);
		return "AttachLinks";
	}

	@RequestMapping(value = "/editLinks", method = RequestMethod.GET)
	public String editLinks(Model model) {

		model.addAttribute("edit", "edit");
		return "AttachLinks";
	}

	@RequestMapping(value = "/updatereferencelinks", method = RequestMethod.GET)
	public String updatereferencelinks(@RequestParam("docId") long docId, HttpServletRequest request, Model model) {

		if (docId != 0) {
			Document document = documentService.findDocumentById(docId);
			Set<DocumentReference> docref = document.getDocumentReference();
			Set<Document> searchResult = new HashSet<Document>();
			for (Iterator<DocumentReference> iterator = docref.iterator(); iterator.hasNext();) {
				DocumentReference documentReference = (DocumentReference) iterator.next();
				searchResult.add(documentReference.getReferenceDocument());
			}
			String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort()
				+ request.getContextPath();
			model.addAttribute("url", url);
			model.addAttribute("docId", docId);
			model.addAttribute("searchResult", searchResult);
		}
		return "updateLinks";
	}

	@RequestMapping(value = "/loadeditreferencelinks", method = RequestMethod.GET)
	public String loadEditReferenceLinks(HttpSession session, Model model) {

		Set<Long> docids = (Set<Long>) session.getAttribute("referencedocs");
		Set<Document> docreference = new HashSet<Document>();
		if (docids != null && (!docids.isEmpty())) {
			docreference = new HashSet<Document>(documentService.findDocsByIds(docids));
			model.addAttribute("edit", "edit");
			model.addAttribute("searchResult", docreference);
		}
		return "documentLinks";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/savereference", method = RequestMethod.GET)
	public String savereference(@RequestParam("docids") Long[] docids, HttpSession session, Model model) {

		Set<Long> referencedocs;
		if (session.getAttribute("referencedocs") != null) {
			referencedocs = (Set<Long>) session.getAttribute("referencedocs");
		}
		else {
			referencedocs = new HashSet<Long>();
		}
		Collections.addAll(referencedocs, docids);
		session.setAttribute("referencedocs", referencedocs);
		return loadEditReferenceLinks(session, model);
	}

	@RequestMapping(value = "/editreference", method = RequestMethod.GET)
	public String editreference(@RequestParam("docids") Long[] docids, HttpSession session, Model model) {

		if (docids.length == 0) {
			session.removeAttribute("referencedocs");
			return loadEditReferenceLinks(session, model);
		}
		Set<Long> referencedocs;
		referencedocs = new HashSet<Long>();
		Collections.addAll(referencedocs, docids);
		session.setAttribute("referencedocs", referencedocs);
		return loadEditReferenceLinks(session, model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validatereference", method = RequestMethod.GET)
	public String validateReference(HttpSession session, Model model) {

		model.addAttribute("showEditLinks", 0);
		if (session.getAttribute("referencedocs") != null) {
			Set<Long> referencedocs = (Set<Long>) session.getAttribute("referencedocs");
			if (!referencedocs.isEmpty()) {
				model.addAttribute("showEditLinks", 1);
			}
		}
		return "show";
	}

	@RequestMapping(value = "/updatereferencedocs", method = RequestMethod.GET)
	public String updatereferencedocs(@RequestParam("docids") Long[] docids, @RequestParam("docId") long docId,
			@RequestParam("adminMeta")String isAdminMeta, Model model,
		HttpServletRequest request) {

		Set<Long> docIdsSet = new HashSet<Long>();
		Collections.addAll(docIdsSet, docids);
		Document document = documentService.findDocumentById(docId);
		Set<DocumentReference> docrefSet = document.getDocumentReference();
		if (!docIdsSet.isEmpty()) {
			List<Document> ds = documentService.findDocsByIds(docIdsSet);
			for (Document doc : ds) {
				DocumentReference docref = new DocumentReference();
				docref.setDocumentId(document.getId());
				docref.setReferenceDocument(doc);
				docrefSet.add(docref);
			}
		}
		document.setDocumentReference(docrefSet);
		documentService.save(document);
		model.addAttribute("edit", "update");
		model.addAttribute("adminMeta", isAdminMeta);
		return updatereferencelinks(docId, request, model);
		// return "updateLinks";
	}

	@RequestMapping(value = "/updatereferencelinks1", method = RequestMethod.GET)
	public String updatereferencelinks1(@RequestParam("docids") Long[] docids, @RequestParam("docId") long docId,
		HttpServletRequest request, Model model) {

		Set<Long> docIdsSet = new HashSet<Long>();
		Collections.addAll(docIdsSet, docids);
		Set<DocumentReference> docrefSet = new HashSet<DocumentReference>();
		Document document = documentService.findDocumentById(docId);
		if (!docIdsSet.isEmpty()) {
			List<Document> ds = documentService.findDocsByIds(docIdsSet);
			for (Document doc : ds) {
				DocumentReference docref = new DocumentReference();
				docref.setDocumentId(document.getId());
				docref.setReferenceDocument(doc);
				docrefSet.add(docref);
			}
		}
		document.setDocumentReference(docrefSet);
		// Error Occured During Save
		documentService.save(document);
		model.addAttribute("edit", "update");
		return updatereferencelinks(docId, request, model);
		// return "updateLinks";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatereference", method = RequestMethod.GET)
	public String updateReference(HttpSession session, Model model) {

		model.addAttribute("showEditLinks", 0);
		if (session.getAttribute("referencedocs") != null) {
			Set<Long> referencedocs = (Set<Long>) session.getAttribute("referencedocs");
			if (!referencedocs.isEmpty()) {
				model.addAttribute("showEditLinks", 1);
			}
		}
		return "show";
	}

	@RequestMapping(value = "/searchLinks", method = RequestMethod.POST)
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
		@RequestParam("relevantdatetoLinks") String relevantdateto, @RequestParam("edit") String edit,
		@RequestParam("docId") String docId,@RequestParam("adminMeta")String isAdminMeta, HttpSession session, Model model) {
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
			model.addAttribute("adminMeta", isAdminMeta);
		}
		catch (Exception e) {
			model.addAttribute("searchResulterror", "Error occurred while searching the document");
			// LOGGER.error(e.getCause());
		}
		return "documentLinks";
	}

	@RequestMapping(value = "/securitySetting", method = RequestMethod.GET)
	public String securitySetting(Model model) {

		return "securitySetting";
	}

	@RequestMapping(value = "/listSecGrps", method = RequestMethod.GET)
	public String listSecurityGroups(Model model, HttpSession session) {

		//System.out.println("In listsec Function");
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		List<SecurityGroup> securityGroups = this.securityGroupService.findSecurityGroupsForUser(user);
		//System.out.println("SGSS-> " + securityGroups);
		model.addAttribute("sgForAttr", session.getAttribute("sgForAttr"));
		model.addAttribute("securityGroups", securityGroups);
		return "securityGroups";
	}

	@RequestMapping(value = "/getSecGrp", method = RequestMethod.GET)
	public String getSecurityGroupForAttributes(Model model, HttpSession session) {

		//System.out.println("In getSecuritygrp Function");
		AttributeValue attrv1 = (AttributeValue) session.getAttribute("attributeValue1");
		AttributeValue attrv2 = (AttributeValue) session.getAttribute("attributeValue2");
		AttributeValue attrv3 = (AttributeValue) session.getAttribute("attributeValue3");
		AttributeValue attrv4 = (AttributeValue) session.getAttribute("attributeValue4");
		List<AttributeValue> attrVals = new ArrayList<AttributeValue>();
		attrVals.add(attrv1);
		attrVals.add(attrv2);
		attrVals.add(attrv3);
		attrVals.add(attrv4);
		Collections.sort(attrVals);
		Doctype doctype = (Doctype) session.getAttribute("doctype");
		String sgAttrs = (String) servletContext.getAttribute("sgAttrs");
		SecurityGroup securityGroup = this.securityGroupComboService.findSecurityGroup(attrVals, sgAttrs, doctype);
		return "securityGroups";
	}

	@RequestMapping(value = "/revision", method = RequestMethod.GET)
	public String createRevision(@RequestParam("docId") long docId, HttpServletRequest request, Model model) {

		Set<ModelCombo> modelComboSet = null;
		Document doc = documentService.findDocumentById(docId);
		Set<DocumentAttribute> documentAttributeSet = doc.getDocumentAttributes();
		// modelComboService.findMatchForFirstLevelAttrValue(attrValueId);
		Doctype doctype = doc.getDoctype();
		for (DocumentAttribute documentAttribute : documentAttributeSet) {
			if (documentAttribute.getAttribute().getOrder() == 1) {
				AttributeValue ss = documentAttribute.getAttributeValue();
				modelComboSet = modelComboService.findMatchForFirstLevelAttrValue(ss.getId());
			}
		}
		Set<ModelCombo> modelsSet = new TreeSet<ModelCombo>(this.modelComboService.getModelsMatchForDoctype(modelComboSet,
			doctype.getId()));
		model.addAttribute("docId", docId);
		model.addAttribute("modelsSet", modelsSet);
		return "revisionDoc";
	}

	@RequestMapping(value = "/editattrcloned", method = RequestMethod.GET)
	public String editAttrcloned(@RequestParam("modelName") String modelName, Model model, HttpSession session) throws Exception {

		model.addAttribute("modelName", modelName);
		//System.out.println("modelName- > " + modelName);
		WfModel wfModel = this.modelService.findModelByName(modelName);
		Set<WfAttribute> modelAttr = wfModel.getAttribs();
		Set<WfAttribute> attrSet = new HashSet<WfAttribute>();
		for (WfAttribute a : modelAttr) {
			if (a.getUserEditable().equalsIgnoreCase("Y")) {
				//System.out.println("a-> " + a);
				attrSet.add(a);
			}
		}
		WfAttribute[] attr = attrSet.toArray(new WfAttribute[attrSet.size()]);
		Arrays.sort(attr);
		model.addAttribute("attr", attr);
		return "revisionAttrDoc";
	}

	@RequestMapping(value = "/clonedoc", method = RequestMethod.GET)
	public String cloneDocument(@RequestParam("docId") long docId, @RequestParam("modelName") String modelName,
		@RequestParam("date") String date, HttpSession session, Model model) throws Exception {

		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");
		ArrayList<String> serviceUrlLists = getServiceUrls();
		Document doc = documentService.findDocumentById(docId);
		WfCase wfcase = caseService.findCaseById(doc.getCaseId());
		WfModel wfModel = modelService.findModelByName(modelName);
		Set<WfAttribute> wfattribute = wfModel.getAttribs();
		String path = Util.getFilePathFromWfAttribute(wfattribute);
		String tempName = Util.removeFileExtention(doc.getName());
		int revision = Util.getCurrentFileVersion(doc.getName(), (String) servletContext.getAttribute("revision"));
		tempName = generateRevisionedFileName(tempName, revision, doc.getName(), path);
		if(doc.getDoctype().getVersion().equals("Mj")){
			List<DocumentVersion> dvs=documentVersionService.findDocumentVersionsByDocumentId(docId);
			if(!dvs.isEmpty()){
			String docName=null;
			for(DocumentVersion dv:dvs){
				docName=dv.getDocumentName();
				break;
			}
			documentStorage.copy(dsUser, dsPassword, doc.getFilePath(), path, docName, tempName, serviceUrlLists);
			}else{
				documentStorage.copy(dsUser, dsPassword, doc.getFilePath(), path, doc.getName(), tempName, serviceUrlLists);
			}
		}else{
		documentStorage.copy(dsUser, dsPassword, doc.getFilePath(), path, doc.getName(), tempName, serviceUrlLists);
		}
		WfCase newWFCase = saveCaseWithAttributes(wfcase, session, wfModel, date);
		CloneDocument cloneDocument = new CloneDocument(doc);
		Document newDoc = cloneDocument.createClonedDoc(tempName, newWFCase);
		this.documentService.save(newDoc);
		doc.setRevisionable(false);
		this.documentService.save(doc);
		this.saveDocumentTrail(session, newDoc);
		return "refresh";
	}

	private WfCase saveCaseWithAttributes(WfCase wfcase, HttpSession session, WfModel wfModel, String date) throws Exception {

		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		WfStep step = modelService.createCase(wfModel.getName(), userInfo);
		WfCase newWFCase = step.getOwningCase();
		for (WfAttribute wfattr : newWFCase.getAttributes()) {
			if (wfattr.getUserEditable().equalsIgnoreCase("Y")) {
				if (wfattr.getType() == WfAttributeType.WF_ATTR_TEXT) {
					newWFCase.setAttribute(wfattr.getName(), wfcase.getAttribute(wfattr.getName()));
				}
				else if (wfattr.getType() == WfAttributeType.WF_ATTR_DATE) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					newWFCase.setAttribute(wfattr.getName(), dateFormat.parse(date));
				}
			}
			if (wfattr.getType() == WfAttributeType.WF_ATTR_DECIMAL) {
				Double d = doubleParse(wfcase.getAttribute(wfattr.getName()).toString());
				newWFCase.setAttribute(wfattr.getName(), d);
			}
			if (wfattr.getType() == WfAttributeType.WF_ATTR_NUMBER) {
				Long l = longParse(wfcase.getAttribute(wfattr.getName()).toString());
				wfcase.setAttribute(wfattr.getName(), l);
			}
		}
		this.caseService.saveCase(newWFCase);
		return newWFCase;
	}

	private String generateRevisionedFileName(String tempName, int revision, String orginalFileName, String path) {
		
		Document document=documentService.findDocumentByDocNameAndPath(orginalFileName, path);
		String docTypeVersion=document.getDoctype().getVersion();
		
		String revisionporp = (String) servletContext.getAttribute("revision");
		for (int i = revision + 1;; i++) {
			String[] tempArrName = tempName.split("-");
			StringBuilder sBr = new StringBuilder();
			String tempNameFormation = null;
			if (tempArrName != null) {
				for (int value = 0; value < tempArrName.length - 1; value++) {
					// System.out.println("Value:"
					// +tempArrName[value].toString());
					sBr.append(tempArrName[value].toString());
					sBr.append("-");
				}
				tempNameFormation = sBr.toString();
				// System.out.println("Name Form:" +tNameForm);
			}
			// Commented for replacing all text contains REV
			// tempName = tempName.replaceAll("-" + revisionporp + ".*", "");
			//
			// tempName = tempName.concat("-" + revisionporp + i).concat(
			// Util.getType(orginalFileName));
			if(docTypeVersion.equals("Mj")){
			tempName = tempNameFormation.concat(revisionporp + i+".0").concat(Util.getType(orginalFileName));
			}
			else{
			tempName = tempNameFormation.concat(revisionporp + i).concat(Util.getType(orginalFileName));
			}
			Document doctemp = documentService.findDocumentByDocNameAndPath(tempName, path);
			if (doctemp == null) {
				break;
			}
		}
		return tempName;
	}

	private void saveDocumentTrail(HttpSession session, Document newDocument) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		DocumentTrail documentTrail = new DocumentTrail(newDocument, user, "RevisionDuringNewCase", new Date(), "To Prepare");
		this.documentTrailService.save(documentTrail);
	}

	// For Delete Document Start
	@RequestMapping(value = "/deleteDocument", method = RequestMethod.POST)
	public String deleteDocumentAndCase(@RequestParam("documentId") long documentId, @RequestParam("reason") String reason,
		Model model) {
		// System.out.println("Inside");
		Document document = documentService.findDocumentById(documentId);
		WfCase wfCase = caseService.findCaseById(document.getCaseId());
		if (wfCase != null)
			caseService.abandonCase(wfCase.getId());
		document.setDeletionReason(reason);
		document.setWip('D');
		documentService.save(document);
		getDocumentListWithWip(model);
		return "showDocumentLists";
	}

	@RequestMapping(value = "/goOpenCasesWithDocs", method = RequestMethod.GET)
	public String showOpenCasesWithDocs(Model model) {

		getDocumentListWithWip(model);
		return "showDocumentLists";
	}

	@RequestMapping(value = "/goReasonForDeletion", method = RequestMethod.GET)
	public String showReasonBoxForDeletion(@RequestParam("documentId") long documentId, Model model) {

		model.addAttribute("documentId", documentId);
		return "enterReasonForDeletion";
	}

	private String getStatusAction(List<WfStep> steps, WfCase wfCase) {

		String actionStatus = "";
		if (wfCase.getStatus().equalsIgnoreCase("WF_CASE_CANCELLED")) {
			actionStatus = "Abandoned";
			return actionStatus;
		}
		if (!steps.isEmpty()) {
			for (WfStep s : steps) {
				// actionStatus="";
				actionStatus = actionStatus + s.getStatusLabel() + " - " + s.getNode().getName();
				actionStatus += "|";
				// System.out.println("Action:" +actionStatus);
			}
			// return actionStatus;
			return actionStatus.substring(0, actionStatus.length() - 1);
		}
		return null;
	}

	private void getDocumentListWithWip(Model model) {
		char wip = 'Y';
		List<Document> documentLists = documentService.findAllDocumentsByWip(wip);
		Map<Document, String> docStatusActionMap = new HashMap<Document, String>();
		for (Document doc : documentLists) {
			WfCase wfCase = caseService.findCaseById(doc.getCaseId());
			List<WfStep> steps = caseService.findAssignedStepsForCase(wfCase.getId());
			docStatusActionMap.put(doc, getStatusAction(steps, wfCase));
		}
		this.getDocumentListNotInWip(docStatusActionMap);
		model.addAttribute("title", "Delete Document");
		model.addAttribute("docStatusActionMap", docStatusActionMap);
	}
	
	private void getDocumentListNotInWip(Map<Document, String>  docStatusActionMap) {
		List<Document> documentLists = documentService.findAllDocumentsByWip('N');
		String actionStatus = "";
		for (Document doc : documentLists) {
				actionStatus = "Published";
				docStatusActionMap.put(doc,actionStatus);
		}
	}
	
	// Delete Document End
}