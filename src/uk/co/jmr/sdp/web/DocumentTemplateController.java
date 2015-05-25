package uk.co.jmr.sdp.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.alfresco.webservice.util.ISO9075;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ardhika.wfar.WfModel;

import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.DtTemplateCombo;
import uk.co.jmr.sdp.domain.dt.DtTemplateComboValue;
import uk.co.jmr.sdp.ds.DocumentStorage;
import uk.co.jmr.sdp.service.CompanyUserService;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.DtTemplateComboService;
import uk.co.jmr.sdp.service.DtTemplateComboValueService;
import uk.co.jmr.sdp.web.util.Util;

@Controller
@RequestMapping(value = "/template")
public class DocumentTemplateController {

	@Autowired
	private DtTemplateComboService dtTemplateComboService;
	@Autowired
	private DtTemplateComboValueService dtTemplateComboValueService;
	@Autowired
	private DtAttributeService dtAttributeService;
	@Autowired
	private DoctypeService doctypeService;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private DocumentStorage documentStorage;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private CompanyUserService companyUserService;
	
	Logger logger=Logger.getLogger(DocumentTemplateController.class);

	@RequestMapping(value = "/attr1", method = RequestMethod.GET)
	public String firstSetAttributes(Model model, HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);

		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		Set<AttributeValue> attr1ValuesRestricted = new HashSet<AttributeValue>();
		
		List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
		for(CompanyUser cu:cus){
			attr1ValuesRestricted.add(cu.getAttrValue());
		}
		
		model.addAttribute("attr1Name", attribute.getName());
		model.addAttribute("attr1Values", Util.getActiveAttributeValues(attr1ValuesRestricted));
		// model.addAttribute("attr1Values", attr1Values);
		model.addAttribute("DT", true);
		return "attr1";
	}

	@RequestMapping(value = "/attr2", method = RequestMethod.GET)
	public String secondSetAttributes(long attr1Value, Model model, HttpSession session) {

		//System.out.println("Attr1Value -> " + attr1Value);
		Set<DtTemplateCombo> dtCombo1 = this.dtTemplateComboService.findMatchForFirstLevelAttrValue(attr1Value);
		//System.out.println("dtCombo1-> " + dtCombo1);
		session.setAttribute("dtCombo1", dtCombo1);
		Set<AttributeValue> attr2Values = this.dtTemplateComboService.getSecondLevelAttrValuesFromTemplateCombos(dtCombo1);
		//System.out.println("attr2 -> " + attr2Values);
		Set<AttributeValue> attr2ValuesRestricted = Util.getActiveAttributeValues(attr2Values);
		Attribute attribute = dtAttributeService.findAttributeByOrder(2);
		model.addAttribute("attr2Name", attribute.getName());
		model.addAttribute("attr2Values", attr2ValuesRestricted);
		// model.addAttribute("attr2Values", attr2Values);
		model.addAttribute("attr2Size", attr2Values.size());
		model.addAttribute("DT", true);

		return "attr2";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr3", method = RequestMethod.GET)
	public String thirdSetAttributes(long attr2Value, Model model, HttpSession session) {

		//System.out.println("Attr1Value -> " + attr2Value);
		AttributeValue attr2 = this.dtAttributeValueService.findDtAttrValueById(attr2Value);
		Set<DtTemplateCombo> dtCombo1 = (Set<DtTemplateCombo>) session.getAttribute("dtCombo1");
		Set<DtTemplateCombo> dtCombo2 = this.dtTemplateComboService.findMatchForSecondLevelAttrValue(dtCombo1, attr2);
		//System.out.println("dtCombo2-> " + dtCombo2);
		session.setAttribute("dtCombo2", dtCombo2);
		Set<AttributeValue> attr3Values = new TreeSet<AttributeValue>(
			this.dtTemplateComboService.getThirdLevelAttrValuesFromTemplateCombos(dtCombo2));
		//System.out.println("attr3 -> " + attr3Values);
		Set<AttributeValue> attr3ValuesRestricted = Util.getActiveAttributeValues(attr3Values);
		Attribute attribute = dtAttributeService.findAttributeByOrder(3);
		model.addAttribute("attr3Name", attribute.getName());
		model.addAttribute("attr3Values", attr3ValuesRestricted);
		// model.addAttribute("attr3Values", attr3Values);
		model.addAttribute("attr3Size", attr3Values.size());
		model.addAttribute("DT", true);
		return "attr3";
	}
	
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr4", method = RequestMethod.GET)
	public String fourthSetAttributes(long attr3Value, Model model, HttpSession session) {

		//System.out.println("Attr1Value -> " + attr2Value);
		AttributeValue attr3 = this.dtAttributeValueService.findDtAttrValueById(attr3Value);
		Set<DtTemplateCombo> dtCombo2 = (Set<DtTemplateCombo>) session.getAttribute("dtCombo2");
		Set<DtTemplateCombo> dtCombo3 = this.dtTemplateComboService.findMatchForThirdLevelAttrValue(dtCombo2, attr3);
		//System.out.println("dtCombo2-> " + dtCombo2);
		session.setAttribute("dtCombo3", dtCombo3);
		Set<AttributeValue> attr4Values = new TreeSet<AttributeValue>(
			this.dtTemplateComboService.getFourthLevelAttrValuesFromTemplateCombos(dtCombo3));
		//System.out.println("attr3 -> " + attr3Values);
		Set<AttributeValue> attr4ValuesRestricted = Util.getActiveAttributeValues(attr4Values);
		Attribute attribute = dtAttributeService.findAttributeByOrder(4);
		model.addAttribute("attr4Name", attribute.getName());
		model.addAttribute("attr4Values", attr4ValuesRestricted);
		// model.addAttribute("attr3Values", attr3Values);
		model.addAttribute("attr4Size", attr4Values.size());
		model.addAttribute("DT", true);
		return "attr4";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr5", method = RequestMethod.GET)
	public String fifthSetAttributes(long attr4Value, Model model, HttpSession session) {

		AttributeValue attr4 = this.dtAttributeValueService.findDtAttrValueById(attr4Value);

		Set<DtTemplateCombo> dtCombo3 = (Set<DtTemplateCombo>) session.getAttribute("dtCombo3");
		Set<DtTemplateCombo> dtCombo4 = this.dtTemplateComboService.findMatchForFourthLevelAttrValue(dtCombo3, attr4);
		//System.out.println("dtCombo3-> " + dtCombo3);
		session.setAttribute("dtCombo4", dtCombo4);
		Set<AttributeValue> attr5Values = new TreeSet<AttributeValue>(
			this.dtTemplateComboService.getFifthLevelAttrValuesFromTemplateCombos(dtCombo4));
		//System.out.println("attr4 -> " + attr4Values);
		Set<AttributeValue> attr5ValuesRestricted = Util.getActiveAttributeValues(attr5Values);
		Attribute attribute = dtAttributeService.findAttributeByOrder(5);
		model.addAttribute("attr5Name", attribute.getName());
		// model.addAttribute("attr4Values", attr4Values);
		model.addAttribute("attr5Values", attr5ValuesRestricted);
		model.addAttribute("attr5Size", attr5Values.size());
		model.addAttribute("DT", true);
		return "attr5";
	}*/

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDoctypes", method = RequestMethod.GET)
	public String getDoctypes(long attr3Value, Model model, HttpSession session) {

		AttributeValue attr3 = this.dtAttributeValueService.findDtAttrValueById(attr3Value);
		Set<DtTemplateCombo> dtCombo2 = (Set<DtTemplateCombo>) session.getAttribute("dtCombo2");
		Set<DtTemplateCombo> dtCombo3 = (Set<DtTemplateCombo>) this.dtTemplateComboService.findMatchForThirdLevelAttrValue(
			dtCombo2, attr3);
		session.setAttribute("dtCombo3", dtCombo3);
		//System.out.println("dtcombo4->> " + dtCombo4);
		Set<Doctype> doctypes = new TreeSet<Doctype>();
		for (DtTemplateCombo d : dtCombo3) {
			// For Active Doc types
			char isActive = d.getDoctype().getIsActive();
			if (isActive == 'Y')
				doctypes.add(d.getDoctype());
			// doctypes.add(d.getDoctype());
		}
		model.addAttribute("doctypes", doctypes);

		model.addAttribute("DT", true);
		return "listDoctypes";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectTemplates", method = RequestMethod.GET)
	public String getTemplates(long doctypeId, Model model, HttpSession session) {

		//System.out.println("doctypeId-> " + doctypeId);
		Set<DtTemplateCombo> dtCombo3 = (Set<DtTemplateCombo>) session.getAttribute("dtCombo3");
		Set<DtTemplateCombo> dtTemplates = new TreeSet<DtTemplateCombo>();
		dtTemplates = (Set<DtTemplateCombo>) this.dtTemplateComboService.getTemplatesMatchForDoctype(dtCombo3, doctypeId);
		session.setAttribute("dtTemplates", dtTemplates);
		DtTemplateCombo dt = new DtTemplateCombo();
		// System.out.println("**** GEt Template Name:" +dt.getTemplateName());
		model.addAttribute("DT", true);
		return "listTemplates";
	}

	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST)
	public void downloadTemplate(HttpSession session, HttpServletResponse response,
		@RequestParam("templateUrl") String templateUrl) throws Exception {

		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");
		ArrayList<String> serviceUrlLists = getServiceUrls();
		//System.out.println("In downloadtemp ");

		InputStream is = documentStorage.downloadTemplate(dsUser, dsPassword, templateUrl, serviceUrlLists);
		// String docName =
		// templateUrl.substring(templateUrl.lastIndexOf(":")+1);
		String docName = templateUrl.substring(templateUrl.lastIndexOf("/") + 1);
		docName = ISO9075.decode(docName);
		//System.out.println("****** docName-> " + docName);
		response.setHeader("Content-Disposition", "attachment;filename=\"" + docName + "\"");
		byte[] bytes = new byte[2048];
		OutputStream os = response.getOutputStream();
		int read;
		while ((read = is.read(bytes)) != -1) {
			os.write(bytes, 0, read);
		}
		os.flush();
		os.close();
		is.close();
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

	@RequestMapping(value = "/downloadReferenceLinks", method = RequestMethod.POST)
	public void downloadTemplate(HttpSession session, HttpServletResponse response, @RequestParam("path") String path,
		@RequestParam("name") String name) throws Exception {

		Document doc=documentService.findDocumentByDocName(name);
		
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");
		ArrayList<String> serviceUrlLists = getServiceUrls();

		//System.out.println("In downloadtemp " + path + "Name:" + name);
		// String templateUrl=path+"/cm:"+ISO9075.encode(name);
		//String rootpath = (String) servletContext.getAttribute("alfresco.rootpath");
		String templateUrl = path.concat("/").concat(name);
	//	String folderName = (String) servletContext.getAttribute("foldername.quickupload");
		if(doc.getCaseId()==0)
		templateUrl = path+"/"+name;
		
		InputStream is = documentStorage.downloadTemplate(dsUser, dsPassword, templateUrl, serviceUrlLists);
		String docName = templateUrl.substring(templateUrl.lastIndexOf("/") + 1);
		docName = ISO9075.decode(docName);
		//System.out.println("****** docName-> " + docName);
		response.setHeader("Content-Disposition", "attachment;filename=\"" + docName + "\"");
		byte[] bytes = new byte[2048];
		OutputStream os = response.getOutputStream();
		int read;
		while ((read = is.read(bytes)) != -1) {
			os.write(bytes, 0, read);
		}
		os.flush();
		os.close();
		is.close();
	}

	private void showMetadata(Model model) {

		List<Attribute> attributeList = dtAttributeService.findAllDtAttrs();
		Set<AttributeValue> attrivalueSetCompany = Util.getAttributeBasedOnId(attributeList, 5);
		Set<AttributeValue> attrivalueSetProject = Util.getAttributeBasedOnId(attributeList, 1);
		Set<AttributeValue> attrivalueSetCategory = Util.getAttributeBasedOnId(attributeList, 2);
		/*Set<AttributeValue> attrivalueSetDiscipline = Util.getAttributeBasedOnId(attributeList, 3);
		Set<AttributeValue> attrivalueSetSite = Util.getAttributeBasedOnId(attributeList, 4);*/
		Set<AttributeValue> attriValueRestrictedSetForCompany = Util.getActiveAttributeValues(attrivalueSetCompany);
		Set<AttributeValue> attriValueRestrictedSetForProject = Util.getActiveAttributeValues(attrivalueSetProject);
		Set<AttributeValue> attriValueRestrictedSetForCategory = Util.getActiveAttributeValues(attrivalueSetCategory);
		/*Set<AttributeValue> attriValueRestrictedSetForDiscipline = Util.getActiveAttributeValues(attrivalueSetDiscipline);
		Set<AttributeValue> attriValueRestrictedSetForSite = Util.getActiveAttributeValues(attrivalueSetSite);*/
		List<Doctype> docTypeLists = doctypeService.findAllDoctype();
		Collections.sort(docTypeLists);
		model.addAttribute("companyList", attriValueRestrictedSetForCompany);
		model.addAttribute("projectList", attriValueRestrictedSetForProject);
		model.addAttribute("categoryList", attriValueRestrictedSetForCategory);
		/*model.addAttribute("disciplineList", attriValueRestrictedSetForDiscipline);
		model.addAttribute("siteList", attriValueRestrictedSetForSite);*/
		model.addAttribute("doctypesList", docTypeLists);
	}

	private List<DtTemplateCombo> getTemplateLists(Model model) {

		List<DtTemplateCombo> templateLists = dtTemplateComboService.listAllTemplates();
		model.addAttribute("templateLists", templateLists);
		model.addAttribute("title", "Template Lists");
		return templateLists;

	}

	@RequestMapping(value = "/goShowTemplates", method = RequestMethod.GET)
	public String showAllTemplates(Model model, HttpSession session) {

		getTemplateLists(model);
		return "showTemplates";
	}

	@RequestMapping(value = "/goCreateNewTemplate", method = RequestMethod.GET)
	public String gocreateNew(@RequestParam("id") long templateId, Model model, HttpSession session) {

		model.addAttribute("title", "Template Creation");
		// model.addAttribute("templateId",templateId);
		showMetadata(model);
		/*return "createNewTemplate";*/
		return "createNewTemplateMultiselect";
	}

	@RequestMapping(value = "/goDeleteTemplateAssignment", method = RequestMethod.GET)
	public String deleteTemplateAssignment(@RequestParam("id") long templateId, Model model, HttpSession session) {

		DtTemplateCombo templateCombo = dtTemplateComboService.findTemplateById(templateId);
		List<DtTemplateComboValue> dtTemplateComboValueLists = dtTemplateComboValueService.findTemplateComboValues(templateCombo);
		// System.out.println("List:" +dtTemplateComboValueLists);
		for (DtTemplateComboValue templateComboValues : dtTemplateComboValueLists) {
			dtTemplateComboValueService.delete(templateComboValues);
		}

		DtTemplateCombo templateComboUpdate = dtTemplateComboService.findTemplateById(templateId);
		dtTemplateComboService.delete(templateComboUpdate);
		getTemplateLists(model);
		return "showTemplates";
	}

	@RequestMapping(value = "/goShowTemplateAssignment", method = RequestMethod.GET)
	public String showTemplateAssignment(@RequestParam("id") long templateId, Model model, HttpSession session) {

		DtTemplateCombo templateCombo = dtTemplateComboService.findTemplateById(templateId);
		Set<DtTemplateComboValue> templateComboValues = templateCombo.getTemplateComboValues();

		String project = null;
		String category = null;
		String discipline = null;
		String site = null;
		String company = null;
		for (Iterator<DtTemplateComboValue> iterator = templateComboValues.iterator(); iterator.hasNext();) {
			DtTemplateComboValue dtTemplateComboValue = (DtTemplateComboValue) iterator.next();

			if (dtTemplateComboValue.getAttribute().getId() == 1) {

				project = dtTemplateComboValue.getAttributeValue().getValue();

			}
			else if (dtTemplateComboValue.getAttribute().getId() == 2) {

				category = dtTemplateComboValue.getAttributeValue().getValue();

			}
			/*else if (dtTemplateComboValue.getAttribute().getId() == 3) {
				discipline = dtTemplateComboValue.getAttributeValue().getValue();

			}
			else if (dtTemplateComboValue.getAttribute().getId() == 4) {

				site = dtTemplateComboValue.getAttributeValue().getValue();
			}*/
			else if (dtTemplateComboValue.getAttribute().getId() == 5) {

				company = dtTemplateComboValue.getAttributeValue().getValue();
			}

		}

		setTitle(model, session);
		model.addAttribute("templateName", templateCombo.getTemplateName());
		model.addAttribute("company", company);
		model.addAttribute("project", project);
		model.addAttribute("category", category);
		/*model.addAttribute("discipline", discipline);
		model.addAttribute("site", site);*/
		model.addAttribute("doctype", templateCombo.getDoctype().getDoctypeName());
		model.addAttribute("templateId", templateId);
		model.addAttribute("title", "Template Assignment");
		return "showTemplateAssignment";
	}

	private void setTitle(Model model, HttpSession session) {

		Attribute attribute1 = dtAttributeService.findAttributeByOrder(1);
		Attribute attribute2 = dtAttributeService.findAttributeByOrder(2);
		Attribute attribute3 = dtAttributeService.findAttributeByOrder(3);
		/*Attribute attribute4 = dtAttributeService.findAttributeByOrder(4);
		Attribute attribute5 = dtAttributeService.findAttributeByOrder(5);*/
		model.addAttribute("attr1Title", attribute1.getName());
		model.addAttribute("attr2Title", attribute2.getName());
		model.addAttribute("attr3Title", attribute3.getName());
		/*model.addAttribute("attr4Title", attribute4.getName());
		model.addAttribute("attr5Title", attribute5.getName());*/

	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/uploadNewTemplate", method = RequestMethod.POST, headers = { "content-type=multipart/form-data" })
	@ResponseBody
	public String goUploadNewTemplate(@RequestParam("companyTemplate")long[] companyId,@RequestParam("projectTemplate") long[] projectId,
		@RequestParam("categoryTemplate") long[] categoryId,  @RequestParam("doctypeTemplate") long[] doctypeId,
		@RequestParam("templateFile") CommonsMultipartFile file, Model model, HttpSession session) {

		// @RequestParam("templateId") long templateId,
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");
		
		
		ArrayList<String> serviceUrlLists = getServiceUrls();
		String path = null;
		String fileName = null;
		String templateUrl = null;
		int result = 0;
		String contentType = file.getContentType();
		path = (String) servletContext.getAttribute("filePath.template")
			+ (String) servletContext.getAttribute("foldername.template");
		fileName = file.getOriginalFilename();
		try {
			documentStorage.createContent(dsUser, dsPassword, path, fileName, contentType, file, serviceUrlLists);
			templateUrl = path + "/" + fileName;
		for(long doctypeI: doctypeId){
			for(long companyI: companyId){
				for(long projectI: projectId){
					for(long categoryI : categoryId){
						/*for(long disciplineI:disciplineId){
							for(long siteI:siteId){*/
			Doctype doctypeObj = doctypeService.findDoctypeById(doctypeI);
			DtTemplateCombo templateCombo = new DtTemplateCombo(doctypeObj, templateUrl, fileName);
			Attribute attribute1 = dtAttributeService.findAttributeByOrder(1);
			Attribute attribute2 = dtAttributeService.findAttributeByOrder(2);
			Attribute attribute3 = dtAttributeService.findAttributeByOrder(3);
			/*Attribute attribute4 = dtAttributeService.findAttributeByOrder(4);
			Attribute attribute5 = dtAttributeService.findAttributeByOrder(5);*/
			
			AttributeValue attributeValue5 = dtAttributeValueService.findDtAttrValueById(companyI);
			AttributeValue attributeValue1 = dtAttributeValueService.findDtAttrValueById(projectI);
			AttributeValue attributeValue2 = dtAttributeValueService.findDtAttrValueById(categoryI);
			/*AttributeValue attributeValue3 = dtAttributeValueService.findDtAttrValueById(disciplineI);
			AttributeValue attributeValue4 = dtAttributeValueService.findDtAttrValueById(siteI);*/
			

			DtTemplateComboValue templateComboValue1 = new DtTemplateComboValue(templateCombo, attribute2, attributeValue1);
			DtTemplateComboValue templateComboValue2 = new DtTemplateComboValue(templateCombo, attribute3, attributeValue2);
			/*DtTemplateComboValue templateComboValue3 = new DtTemplateComboValue(templateCombo, attribute4, attributeValue3);
			DtTemplateComboValue templateComboValue4 = new DtTemplateComboValue(templateCombo, attribute5, attributeValue4);*/
			DtTemplateComboValue templateComboValue5 = new DtTemplateComboValue(templateCombo, attribute1, attributeValue5);

			Set<DtTemplateComboValue> dtTemplateComboValueSet = new HashSet<DtTemplateComboValue>();
			dtTemplateComboValueSet.add(templateComboValue1);
			dtTemplateComboValueSet.add(templateComboValue2);
			/*dtTemplateComboValueSet.add(templateComboValue3);
			dtTemplateComboValueSet.add(templateComboValue4);*/
			dtTemplateComboValueSet.add(templateComboValue5);
			templateCombo.setTemplateComboValues(dtTemplateComboValueSet);
			this.dtTemplateComboService.save(templateCombo);
			result = 1;
							}
						}
					}
				}
			/*}
		  }*/
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("/uploadNewTemplate Error message "+e );
			result = 0;
		}
		finally {
			String html = "<script language='javascript' type='text/javascript'> window.top.window.uploadTemplateCompleted('"
				+ result + "','" + fileName + "');</script>";
			return html;
		}
	}
	
	/*@SuppressWarnings("finally")
	@RequestMapping(value = "/uploadNewTemplate", method = RequestMethod.POST, headers = { "content-type=multipart/form-data" })
	@ResponseBody
	public String goUploadNewTemplate(@RequestParam("projectTemplate") long projectId,
		@RequestParam("categoryTemplate") long categoryId, @RequestParam("disciplineTemplate") long disciplineId,
		@RequestParam("siteTemplate") long siteId, @RequestParam("doctypeTemplate") long doctypeId,
		@RequestParam("templateFile") CommonsMultipartFile file, Model model, HttpSession session) {

		// @RequestParam("templateId") long templateId,
		String dsUser = (String) servletContext.getAttribute("dsUser");
		String dsPassword = (String) servletContext.getAttribute("dsPassword");
		
		
		ArrayList<String> serviceUrlLists = getServiceUrls();
		String path = null;
		String fileName = null;
		String templateUrl = null;
		int result = 0;
		String contentType = file.getContentType();
		path = (String) servletContext.getAttribute("filePath.template")
			+ (String) servletContext.getAttribute("foldername.template");
		fileName = file.getOriginalFilename();
		try {
			documentStorage.createContent(dsUser, dsPassword, path, fileName, contentType, file, serviceUrlLists);
			templateUrl = path + "/" + fileName;

			Doctype doctypeObj = doctypeService.findDoctypeById(doctypeId);
			DtTemplateCombo templateCombo = new DtTemplateCombo(doctypeObj, templateUrl, fileName);
			Attribute attribute1 = dtAttributeService.findAttributeByOrder(1);
			Attribute attribute2 = dtAttributeService.findAttributeByOrder(2);
			Attribute attribute3 = dtAttributeService.findAttributeByOrder(3);
			Attribute attribute4 = dtAttributeService.findAttributeByOrder(4);
			
			AttributeValue attributeValue1 = dtAttributeValueService.findDtAttrValueById(projectId);
			AttributeValue attributeValue2 = dtAttributeValueService.findDtAttrValueById(categoryId);
			AttributeValue attributeValue3 = dtAttributeValueService.findDtAttrValueById(disciplineId);
			AttributeValue attributeValue4 = dtAttributeValueService.findDtAttrValueById(siteId);
			
			

			DtTemplateComboValue templateComboValue1 = new DtTemplateComboValue(templateCombo, attribute1, attributeValue1);
			DtTemplateComboValue templateComboValue2 = new DtTemplateComboValue(templateCombo, attribute2, attributeValue2);
			DtTemplateComboValue templateComboValue3 = new DtTemplateComboValue(templateCombo, attribute3, attributeValue3);
			DtTemplateComboValue templateComboValue4 = new DtTemplateComboValue(templateCombo, attribute4, attributeValue4);
		
			Set<DtTemplateComboValue> dtTemplateComboValueSet = new HashSet<DtTemplateComboValue>();
			dtTemplateComboValueSet.add(templateComboValue1);
			dtTemplateComboValueSet.add(templateComboValue2);
			dtTemplateComboValueSet.add(templateComboValue3);
			dtTemplateComboValueSet.add(templateComboValue4);
			templateCombo.setTemplateComboValues(dtTemplateComboValueSet);
			
			
				this.dtTemplateComboService.save(templateCombo);
				result = 1;	
				

		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("/uploadNewTemplate Error message "+e );
			result = 0;
		}
		finally {
			String html = "<script language='javascript' type='text/javascript'> window.top.window.uploadTemplateCompleted('"
				+ result + "','" + fileName + "');</script>";
			return html;
		}
	}*/

}
