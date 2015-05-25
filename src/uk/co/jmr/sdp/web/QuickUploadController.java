package uk.co.jmr.sdp.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.ServletContext;
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

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.DocumentUUID;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.ModelCombo;
import uk.co.jmr.sdp.domain.photoupload.UploadPhotos;
import uk.co.jmr.sdp.ds.DocumentStorage;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.DocumentUuidService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.ModelComboService;
import uk.co.jmr.sdp.service.SecurityGroupComboService;
import uk.co.jmr.sdp.service.SecurityGroupService;
import uk.co.jmr.sdp.web.util.Util;

@Controller
@RequestMapping(value = "/quickupload")
public class QuickUploadController {

	@Autowired
	private ModelComboService modelComboService;
	@Autowired
	private DtAttributeService dtAttributeService;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private DoctypeService doctypeService;
	@Autowired
	private DocumentStorage documentStorage;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private DocumentUuidService documentUuidService;
	@Autowired
	private SecurityGroupComboService securityGroupComboService;
	@Autowired
	private SecurityGroupService securityGroupService;
	
	Logger logger=Logger.getLogger(QuickUploadController.class);

	@RequestMapping(value = "/attr1", method = RequestMethod.GET)
	public String firstSetAttributes(Model model, HttpSession session) {

		Attribute attribute = dtAttributeService.findAttributeByOrder(1);

		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		model.addAttribute("attr1Name", attribute.getName());
		model.addAttribute("attr1Values", attr1ValuesRestricted);
		// model.addAttribute("attr1Values", attr1Values);
		model.addAttribute("QU", true);
		return "attr1";
	}

	@RequestMapping(value = "/attr2", method = RequestMethod.GET)
	public String secondSetAttributes(long attr1Value, Model model, HttpSession session) {

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
		// model.addAttribute("attr2Values", attr2Values);k
		model.addAttribute("QU", true);
		return "attr2";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr3", method = RequestMethod.GET)
	public String thirdSetAttributes(long attr2Value, Model model, HttpSession session) {

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
		// model.addAttribute("attr3Values", attr3Values);
		model.addAttribute("QU", true);
		return "attr3";
	}

	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr4", method = RequestMethod.GET)
	public String fourthSetAttributes(long attr3Value, Model model, HttpSession session) {

		AttributeValue attr3 = this.dtAttributeValueService.findDtAttrValueById(attr3Value);
		session.setAttribute("attributeValue3", attr3);

		Set<ModelCombo> modelCombo2 = (Set<ModelCombo>) session.getAttribute("modelCombo2");
		Set<ModelCombo> modelCombo3 = this.modelComboService.findMatchForThirdLevelAttrValue(modelCombo2, attr3);
		//System.out.println("modelCombo3-> " + modelCombo3);
		session.setAttribute("modelCombo3", modelCombo3);
		Set<AttributeValue> attr4Values = new TreeSet<AttributeValue>(
			this.modelComboService.getFourthLevelAttrValuesFromModelCombos(modelCombo3));
		//System.out.println("attr4 -> " + attr4Values);
		Set<AttributeValue> attr4ValuesRestricted = Util.getActiveAttributeValues(attr4Values);

		Attribute attribute = dtAttributeService.findAttributeByOrder(4);
		model.addAttribute("attr4Name", attribute.getName());
		model.addAttribute("attr4Values", attr4ValuesRestricted);
		// model.addAttribute("attr4Values", attr4Values);
		model.addAttribute("QU", true);
		return "attr4";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/attr5", method = RequestMethod.GET)
	public String fifthSetAttributes(long attr4Value, Model model, HttpSession session) {

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
		model.addAttribute("attr4Name", attribute.getName());
		model.addAttribute("attr4Values", attr5ValuesRestricted);
		// model.addAttribute("attr4Values", attr4Values);
		model.addAttribute("QU", true);
		return "attr4";
	}*/
	
	private List<Doctype> getDocTypeLists() {

		List<Doctype> docTypeLists = doctypeService.findAllDoctype();
		Collections.sort(docTypeLists);
		return docTypeLists;
	}

	@RequestMapping(value = "/qupload", method = RequestMethod.GET)
	public String quickUpload(long attr3Value, Model model, HttpSession session) throws Exception {

		AttributeValue attr3 = this.dtAttributeValueService.findDtAttrValueById(attr3Value);
		session.setAttribute("attributeValue3", attr3);
		model.addAttribute("doctypes", getDocTypeLists());
		// model.addAttribute("doctypes",
		// servletContext.getAttribute("doctypes"));
		return "quickUpload";
	}

	@RequestMapping(value = "/getSecurityGroups", method = RequestMethod.GET)
	public String getSecurityGroups(@RequestParam("documentTypeWof") long docType, Model model, HttpSession session)
		throws Exception {

		AttributeValue attrv1 = (AttributeValue) session.getAttribute("attributeValue1");
		AttributeValue attrv2 = (AttributeValue) session.getAttribute("attributeValue2");
		AttributeValue attrv3 = (AttributeValue) session.getAttribute("attributeValue3");
		/*AttributeValue attrv4 = (AttributeValue) session.getAttribute("attributeValue4");
*/
		Doctype doctype = doctypeService.findDoctypeById(docType);

		List<AttributeValue> attrVals = new ArrayList<AttributeValue>();
		attrVals.add(attrv1);
		attrVals.add(attrv2);
		attrVals.add(attrv3);
		/*attrVals.add(attrv4);*/
		String sgAttrs = (String) servletContext.getAttribute("sgAttrs");
		SecurityGroup securityGroup = this.securityGroupComboService.findSecurityGroup(attrVals, sgAttrs, doctype);

		List<SecurityGroup> secGroups = this.securityGroupComboService.findDefaultSecurityGroups(attrVals, sgAttrs, doctype);
		SecurityGroup sg=securityGroupService.findSecurityGroupByName("Open");
		//session.setAttribute("sgForAttr", securityGroup);
		//System.out.println("SEC GROUPPPPPPPP -> " + securityGroup);
		model.addAttribute("open", sg.getId());
		session.setAttribute("sgForAttr", securityGroup);
		//System.out.println("SEC GROUPPPPPPPP -> " + securityGroup);
		model.addAttribute("secGroups", secGroups);
		model.addAttribute("defaultSG", securityGroup);
		return "securityGroupsQupl";

	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/upload", method = RequestMethod.POST, headers = { "content-type=multipart/form-data" })
	@ResponseBody
	public String quickUploads(@RequestParam("quickUpl") CommonsMultipartFile file,
		@RequestParam("securitySettingQupl") String securitySetting, @RequestParam("datepickerqUp") String targetDate,
		@RequestParam("keywordsWof") String keywords, @RequestParam("documentTypeWof") int docType,
		@RequestParam("ebnumberWof") String eb_number, HttpSession session, Model model, HttpServletResponse response) {

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

		Doctype doctype = doctypeService.findDoctypeById(docType);

		FilePath = (String) servletContext.getAttribute("filePath.quickupload");
		folderName = (String) servletContext.getAttribute("foldername.quickupload");

		Attribute attribute1 = dtAttributeService.findAttributeByOrder(1);
		Attribute attribute2 = dtAttributeService.findAttributeByOrder(2);
		Attribute attribute3 = dtAttributeService.findAttributeByOrder(3);
		/*Attribute attribute4 = dtAttributeService.findAttributeByOrder(4);*/

		AttributeValue attributeValue1 = (AttributeValue) session.getAttribute("attributeValue1");
		AttributeValue attributeValue2 = (AttributeValue) session.getAttribute("attributeValue2");
		AttributeValue attributeValue3 = (AttributeValue) session.getAttribute("attributeValue3");
		/*AttributeValue attributeValue4 = (AttributeValue) session.getAttribute("attributeValue4");*/

		String fileName = file.getOriginalFilename();
		StringBuilder tempfilenamesb = Util.generateFileName(attributeValue1.getAbbreviation(), doctype.getAbbreviation(),
			keywords, attributeValue3.getAbbreviation(), attributeValue2.getAbbreviation());
		// Commenting For Naming Convention with Abbreviation

		// StringBuilder tempfilenamesb = Util.generateFileName(
		// attributeValue1.getValue(), doctype.getAbbreviation(),
		// keywords, attributeValue4.getValue(),
		// attributeValue3.getValue());

		DocumentUUID docUuid = new DocumentUUID(0, UUID.randomUUID().toString());

		long docUid = documentUuidService.save(docUuid);

		fileName = tempfilenamesb.append(docUid).toString().concat(Util.getType(fileName));

		UploadPhotos uploadPhotos = new UploadPhotos(FilePath, dsUser, dsPassword, file);

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

			if (targetDate == null || targetDate.equals("")) {
				date = new Date();

			}
			else {

				date = (Date) dateFormat.parseObject(targetDate);
			}

			// uploadPhotos.upload(documentStorage, folderName, fileName);
			// uploadPhotos.upload(documentStorage, folderName,
			// fileName,restApiUrl);
			uploadPhotos.upload(documentStorage, folderName, fileName, serviceUrlLists);
			Document doc = new Document();
			doc.setAuthor(user.getUserName());
			doc.setDoctype(doctype);
			doc.setTargetDate(date);
			doc.setKeywords(keywords);
			doc.setFilePath(FilePath.concat(folderName));
			// doc.setFilePath(FilePath + Util.encode(folderName));
			doc.setName(fileName);
			doc.setWip('N');

			if (!eb_number.equals("")) {

				doc.setEbNo(eb_number);

			}
			DocumentAttribute da1 = new DocumentAttribute(doc, attribute1, attributeValue1);
			DocumentAttribute da2 = new DocumentAttribute(doc, attribute2, attributeValue2);
			DocumentAttribute da3 = new DocumentAttribute(doc, attribute3, attributeValue3);
		//	DocumentAttribute da4 = new DocumentAttribute(doc, attribute4, attributeValue4);
			Set<DocumentAttribute> docAttrs = new HashSet<DocumentAttribute>();
			docAttrs.add(da1);
			docAttrs.add(da2);
			docAttrs.add(da3);
			//docAttrs.add(da4);
			doc.setDocumentAttributes(docAttrs);

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
				//System.out.println("############## SG=====> " + sg);
				doc.setSecurityGroup(sg);
			}
			doc.setDiscriminator('D');
			documentService.save(doc);
			result = 1;
		}

		catch (Exception e) {
			logger.error("/upload Error message "+e);
			e.printStackTrace();
			msg = "Unknown Error";
			result = 0;
		}

		finally {
			String html = "<script language='javascript' type='text/javascript'> window.top.window.quickUploadCompleted("
				+ result + ",'" + fileName + "');</script>";
			return html;
		}

	}

}
