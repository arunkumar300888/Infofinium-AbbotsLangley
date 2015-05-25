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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import uk.co.jmr.sdp.domain.BuilderDetailsForm;
import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.CustomerDetails;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.DocumentUUID;
import uk.co.jmr.sdp.domain.DocumentVersion;
import uk.co.jmr.sdp.domain.FormCompanyGroup;
import uk.co.jmr.sdp.domain.FormTrail;
import uk.co.jmr.sdp.domain.FormVals;
import uk.co.jmr.sdp.domain.ProductOrder;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.RetailerOrder;
import uk.co.jmr.sdp.domain.RoomDetails;
import uk.co.jmr.sdp.domain.StandardProductForm;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.TenantDetails;
import uk.co.jmr.sdp.domain.UtilitiesCompanyDetailsForm;
import uk.co.jmr.sdp.domain.UserFormRef;

import uk.co.jmr.sdp.domain.FormsModel;
import uk.co.jmr.sdp.domain.PotentialTenantForm;
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
import uk.co.jmr.sdp.service.BuilderDetailsFormService;
import uk.co.jmr.sdp.service.CaseUserFormsService;
import uk.co.jmr.sdp.service.CompanyUserService;
import uk.co.jmr.sdp.service.CustomerDetailsService;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.DocumentUuidService;
import uk.co.jmr.sdp.service.DocumentVersionService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.FormCompanyGroupService;
import uk.co.jmr.sdp.service.FormService;
import uk.co.jmr.sdp.service.FormTrailService;
import uk.co.jmr.sdp.service.FormValsService;
import uk.co.jmr.sdp.service.ModelComboService;
import uk.co.jmr.sdp.service.PotentialTenantFormService;
import uk.co.jmr.sdp.service.ProductOrderService;
import uk.co.jmr.sdp.service.PropertyDetailsFormService;
import uk.co.jmr.sdp.service.RetailerOrderService;
import uk.co.jmr.sdp.service.RoomDetailsService;
import uk.co.jmr.sdp.service.SecurityGroupComboService;
import uk.co.jmr.sdp.service.SecurityGroupFormService;
import uk.co.jmr.sdp.service.SecurityGroupService;
import uk.co.jmr.sdp.service.StandardProductFormService;
import uk.co.jmr.sdp.service.TempDocumentsService;
import uk.co.jmr.sdp.service.TenancyFormService;
import uk.co.jmr.sdp.service.TenantDetailsService;
import uk.co.jmr.sdp.service.UtilitiesCompanyDetailsFormService;
import uk.co.jmr.sdp.service.UserFormRefService;
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
import com.ardhika.wfar.ModelSummary;
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
import com.sun.org.apache.xerces.internal.impl.dv.xs.DecimalDV;

import java.util.Date;
import java.util.Iterator;

import uk.co.jmr.sdp.service.EmailService;
import uk.co.jmr.webforms.db.dao.UserFormsDao;
import uk.co.jmr.webforms.db.pojo.FormData;
import uk.co.jmr.webforms.db.pojo.FormDefs;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;

@Controller
@RequestMapping(value = "/formsNew")
public class FormsControllerNew {
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
	@Autowired
	private FormValsService formValsService;
	@Autowired
	private UserFormRefService userFormRefService;
	@Autowired
	private PotentialTenantFormService potentialTenantFormService;
	@Autowired
	private PropertyDetailsFormService propertyDetailsFormService;
	@Autowired
	private RoomDetailsService roomDetailsService;
	@Autowired
	private StandardProductFormService standardProductFormService;
	@Autowired
	private TenancyFormService tenancyFormService;
	@Autowired
	private TenantDetailsService tenantDetailsService;
	@Autowired
	private CustomerDetailsService customerDetailsService;
	@Autowired
	private RetailerOrderService retailerOrderService;
	@Autowired
	private ProductOrderService productOrderService;
	@Autowired
	private BuilderDetailsFormService builderDetailsFormService;
	@Autowired
	private UtilitiesCompanyDetailsFormService utilitiesCompanyDetailsFormService;
	
	
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
			model.addAttribute("formDefId", formDefinitionId);
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
			
			Set<WfModel> wfs=form.getModels();
			boolean subDate=true;
			
			if(wfs.size()==1){
				for(WfModel wfm:wfs){
					String workflowName=wfm.getName();
				if (workflowName.equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
					subDate=false;
				}
				}
			}
			model.addAttribute("subDate", subDate);
			model.addAttribute("compGroups", Util.getActiveAttributeValues(attr1ValuesRestricted));
			model.addAttribute("workflows", form.getModels());
			model.addAttribute("jsonStringValue", "");
			model.addAttribute("userFormId", -1);
		}

		return form.getName().trim();
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
		
		UserFormRef userFormRef=userFormRefService.findUserFormRefByUserFormId(uf);
		
		Forms form = formService.fetchFormDefinition(-1, userFormId);
		model.addAttribute("secGroups", securityGroupFormService.findSecurityGroupFormByformDefs(formDefsService.findFormDefsById(uf.getForms().getFormDefs().getId())));
		if (form != null) {
			
			if(uf.getFormTable().equalsIgnoreCase("PotentialTenantForm")){
				
				PotentialTenantForm potentialTenantForm=potentialTenantFormService.findPotentialTenantFormByUserFormId(userFormId);
				String title=potentialTenantForm.getTitle();
		    	 String firstName = potentialTenantForm.getFirstName();
		    	 String lastName=potentialTenantForm.getLastName();
		    	 String landLineNumber=potentialTenantForm.getLandLineNumber();
		    	 String mobileNumber=potentialTenantForm.getMobileNumber();
		    	 String emailAddress=potentialTenantForm.getEmailAddress();
		    	 String nationalInsuranceNumber=potentialTenantForm.getNationalInsuranceNumber();
		    	 String currentAddress=potentialTenantForm.getCurrentAddress();
		    	 String type=potentialTenantForm.getType();
		    	 long numberOfBedRooms = potentialTenantForm.getNumberOfBedRooms();
		    	 String garden = potentialTenantForm.getGarden();
		    	 String offRoadParking=potentialTenantForm.getOffRoadParking();
		    	 String garage=potentialTenantForm.getGarage();
		    	 String other=potentialTenantForm.getOther();
				
				
				model.addAttribute("title", title);
				model.addAttribute("firstName", firstName);
				model.addAttribute("lastName", lastName);
				model.addAttribute("landLineNumber", landLineNumber);
				model.addAttribute("mobileNumber", mobileNumber);
				model.addAttribute("emailAddress", emailAddress);
				model.addAttribute("nationalInsuranceNumber", nationalInsuranceNumber);
				model.addAttribute("currentAddress", currentAddress);
				model.addAttribute("type", type);
				model.addAttribute("numberOfBedRooms", numberOfBedRooms);
				model.addAttribute("garden", garden);
				model.addAttribute("offRoadParking", offRoadParking);
				model.addAttribute("garage", garage);
				model.addAttribute("other", other);
				
				
			}
			if(uf.getFormTable().equalsIgnoreCase("PropertyDetailsForm")){
				
				PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormByUserFormId(userFormId);
				List<RoomDetails> roomDetails=roomDetailsService.findRoomDetailsByPropertyDetailsFormId(propertyDetailsForm);
				
				String client=propertyDetailsForm.getClient();
		    	 String firstName=propertyDetailsForm.getFirstName();
		    	 String lastName=propertyDetailsForm.getLastName();
		    	 String landlineNumber=propertyDetailsForm.getLandlineNumber();
		    	 String mobileNumber=propertyDetailsForm.getMobileNumber();
		    	 String emailAddress=propertyDetailsForm.getEmailAddress();
		    	 String address=propertyDetailsForm.getAddress();
		    	// String accountDetails=null;
		    	 String accountNumber=propertyDetailsForm.getAccountNumber();
		    	 String accountHoldersName=propertyDetailsForm.getAccountHoldersName();
		    	 String sortCode=propertyDetailsForm.getSortCode();
		    	 String vatDetails=propertyDetailsForm.getVatDetails();
		    	 String referencesForPayment=propertyDetailsForm.getReferencesForPayment();
		    	 String propertyAddressLine1=propertyDetailsForm.getPropertyAddressLine1();
		    	 String propertyAddressLine2=propertyDetailsForm.getPropertyAddressLine2();
		    	 String town=propertyDetailsForm.getTown();
		    	 String cityCountry=propertyDetailsForm.getCityCountry();
		    	 String propertyPostCode=propertyDetailsForm.getPropertyPostCode();
		    	 String propertyType=propertyDetailsForm.getPropertyType();
		    	 String propertyDescription=propertyDetailsForm.getPropertyDescription();
		    	 String houseMeasurements=propertyDetailsForm.getHouseMeasurements();
		    	 String gasMeterLocation=propertyDetailsForm.getGasMeterLocation();
		    	 String electricMeterLocation=propertyDetailsForm.getElectricMeterLocation();
		    	 String waterMeterLocation=propertyDetailsForm.getWaterMeterLocation();
		    	 Date  dateOfPerchase=propertyDetailsForm.getDateOfPerchase();
		    	 String priceOfPurchase=propertyDetailsForm.getPriceOfPurchase();
		    	 String estimatedValue=propertyDetailsForm.getEstimatedValue();
		    	 Date asOfDate=propertyDetailsForm.getAsOfDate();
		    	 String annualRent=propertyDetailsForm.getAnnualRent();
		    	 String pictures=propertyDetailsForm.getPictures();
		    	 String localAuthority=propertyDetailsForm.getLocalAuthority();
		    	 String rentalType=propertyDetailsForm.getRentalType();
		    	 String frontDoorKeyCode=propertyDetailsForm.getFrontDoorKeyCode();
		    	 String backDoorKeyCode=propertyDetailsForm.getBackDoorKeyCode();
		    	 String porchDoorKeyCode=propertyDetailsForm.getPorchDoorKeyCode();
		    	 String flatDoor=propertyDetailsForm.getFlatDoor();
		    	 String others=propertyDetailsForm.getOthers();
		    	 long numberOfBedrooms=propertyDetailsForm.getNumberOfBedrooms();
		    	 String bedroomDoor=null;
		    	 String bedroomWindow=null;
		    	 String tenancyAgreement=propertyDetailsForm.getTenancyAgreement();
		    	 String insuranceCertificate=propertyDetailsForm.getInsuranceCertificate();
		    	 String floorPlan=propertyDetailsForm.getFloorPlan();
		    	 String epcCertificate=propertyDetailsForm.getEpcCertificate();
		    	 String gasCertificate=propertyDetailsForm.getGasCertificate();
		    	 String electricCertificate=propertyDetailsForm.getElectricCertificate();
		    	 String hmoLicence=propertyDetailsForm.getHmoLicence();
		    	 String contractsAndWarranties=propertyDetailsForm.getContractsAndWarranties();
		    	 String landRegistry=propertyDetailsForm.getLandRegistry();
		    	 String currentTenancyForm=propertyDetailsForm.getCurrentTenancyForm();
		    	 String propertyTimeline=propertyDetailsForm.getPropertyTimeline();
		    	 String linkToJobs=propertyDetailsForm.getLinkToJobs();
		    	 String lendingInformation=propertyDetailsForm.getLendingInformation();
		    	 String managementCompany=propertyDetailsForm.getManagementCompany();
		    	 String companyName=propertyDetailsForm.getCompanyName();
				
		    	 model.addAttribute("client", client);
		    	 model.addAttribute("firstName", firstName);
		    	 model.addAttribute("lastName", lastName);
		    	 model.addAttribute("landlineNumber", landlineNumber);
		    	 model.addAttribute("mobileNumber", mobileNumber);
		    	 model.addAttribute("emailAddress", emailAddress);
		    	 model.addAttribute("accountNumber", accountNumber);
		    	 model.addAttribute("address", address);
		    	 model.addAttribute("accountHoldersName", accountHoldersName);
		    	 model.addAttribute("sortCode", sortCode);
		    	 model.addAttribute("vatDetails", vatDetails);
		    	 model.addAttribute("referencesForPayment", referencesForPayment);
		    	 model.addAttribute("propertyAddressLine1", propertyAddressLine1);
		    	 model.addAttribute("propertyAddressLine2", propertyAddressLine2);
		    	 model.addAttribute("town", town);
		    	 model.addAttribute("cityCountry", cityCountry);
		    	 model.addAttribute("propertyPostCode", propertyPostCode);
		    	 model.addAttribute("propertyType", propertyType);
		    	 model.addAttribute("propertyDescription", propertyDescription);
		    	 model.addAttribute("houseMeasurements", houseMeasurements);
		    	 model.addAttribute("gasMeterLocation", gasMeterLocation);
		    	 model.addAttribute("electricMeterLocation", electricMeterLocation);
		    	 model.addAttribute("waterMeterLocation", waterMeterLocation);
		    	 if(dateOfPerchase!=null){
		    	 model.addAttribute("dateOfPerchase", sdf.format(dateOfPerchase));
		    	 }
		    	 model.addAttribute("priceOfPurchase", priceOfPurchase);
		    	 model.addAttribute("estimatedValue", estimatedValue);
		    	 if(asOfDate!=null){
		    	 model.addAttribute("asOfDate", sdf.format(asOfDate));
		    	 }
		    	 model.addAttribute("annualRent", annualRent);
		    	 model.addAttribute("pictures", pictures);
		    	 model.addAttribute("localAuthority", localAuthority);
		    	 model.addAttribute("rentalType", rentalType);
		    	 model.addAttribute("frontDoorKeyCode", frontDoorKeyCode);
		    	 model.addAttribute("backDoorKeyCode", backDoorKeyCode);
		    	 model.addAttribute("porchDoorKeyCode", porchDoorKeyCode);
		    	 model.addAttribute("flatDoor", flatDoor);
		    	 model.addAttribute("others", others);
		    	 model.addAttribute("numberOfBedrooms", numberOfBedrooms);
		    	 model.addAttribute("tenancyAgreement", tenancyAgreement);
		    	 model.addAttribute("insuranceCertificate", insuranceCertificate);
		    	 model.addAttribute("floorPlan", floorPlan);
		    	 model.addAttribute("epcCertificate", epcCertificate);
		    	 model.addAttribute("gasCertificate", gasCertificate);
		    	 model.addAttribute("electricCertificate", electricCertificate);
		    	 model.addAttribute("hmoLicence", hmoLicence);
		    	 model.addAttribute("contractsAndWarranties", contractsAndWarranties);
		    	 model.addAttribute("landRegistry", landRegistry);
		    	 model.addAttribute("currentTenancyForm", currentTenancyForm);
		    	 model.addAttribute("propertyTimeline", propertyTimeline);
		    	 model.addAttribute("linkToJobs", linkToJobs);
		    	 model.addAttribute("lendingInformation", lendingInformation);
		    	 model.addAttribute("managementCompany", managementCompany);
		    	 model.addAttribute("companyName", companyName);
				
		    	List<String> roomDets=new ArrayList<String>();
		    	StringBuilder roomD=new StringBuilder();
		    	for(RoomDetails rd:roomDetails){
		    		//roomDets.add(rd.getRoomName());
		    		roomD.append(rd.getRoomName()+",");
		    		System.out.println(rd.getRoomName());
		    	}
		    	
		    	System.out.println(roomD.toString());
		    	System.out.println(roomD.deleteCharAt(roomD.length()-1).toString());
		    	 model.addAttribute("roomDetailsMap", roomD.toString());
			}
			
			
			
			if(uf.getFormTable().equalsIgnoreCase("StandardProductForm")){
				
				StandardProductForm standardProductForm=standardProductFormService.findStandardProductFormByUserFormId(userFormId);
				String productName=standardProductForm.getProductName();
		    	 String productType = standardProductForm.getProductType();
		    	 String productCode=standardProductForm.getProductCode();
		    	 String productDescription=standardProductForm.getProductDescription();
		    	 String sellingPricePerUnit=standardProductForm.getSellingPricePerUnit();
		    	 String picture1=standardProductForm.getPicture1();
		    	 String picture2=standardProductForm.getPicture2();
		    	 String picture3=standardProductForm.getPicture3();
		    	 String picture4=standardProductForm.getPicture4();
		    	 String picture5 = standardProductForm.getPicture5();
		    	 String stockLevels = standardProductForm.getStockLevel();
		    	 String agentDistributionOfStock=standardProductForm.getAgentDistributionOfStock();
		    	 long stockLevelNewSupplierOrderAlert=standardProductForm.getStockLevelNewSupplierOrderAlert();
		    	// String other=potentialTenantForm.getOther();
				
				
				model.addAttribute("productName", productName);
				model.addAttribute("productType", productType);
				model.addAttribute("productCode", productCode);
				model.addAttribute("productDescription", productDescription);
				model.addAttribute("sellingPricePerUnit", sellingPricePerUnit);
				model.addAttribute("picture1", picture1);
				model.addAttribute("picture2", picture2);
				model.addAttribute("picture3", picture3);
				model.addAttribute("picture4", picture4);
				model.addAttribute("picture5", picture5);
				model.addAttribute("stockLevels", stockLevels);
				model.addAttribute("agentDistributionOfStock", agentDistributionOfStock);
				model.addAttribute("stockLevelNewSupplierOrderAlert", stockLevelNewSupplierOrderAlert);
				//model.addAttribute("other", other);
				
				
			}
			
			
			if(uf.getFormTable().equalsIgnoreCase("TenancyForm")){
				
				TenancyForm tenancyForm=tenancyFormService.findTenancyFormByUserFormId(userFormId);
				String propertyAddress=tenancyForm.getPropertyAddress();
				String typeOfRental=tenancyForm.getTypeOfRental();
				long roomNumber=tenancyForm.getRoomNumber();
				String landLordFirstName=tenancyForm.getLandLordFirstName();
				String landLordLastName=tenancyForm.getLandLordLastName();
				String landLordAddress=tenancyForm.getLandLordAddress();
				String landLordEmailAddress=tenancyForm.getLandLordEmailAddress();
				String landLordContactNumber=tenancyForm.getLandLordContactNumber();
				String employerFirstName=tenancyForm.getEmployerFirstName();
				String employerLastName=tenancyForm.getEmployerLastName();
				String employerAddress=tenancyForm.getEmployerAddress();
				String employerEmailAddress=tenancyForm.getEmployerEmailAddress();
				String employerContactNumber=tenancyForm.getEmployerContactNumber();
				String guarantorFirstName=tenancyForm.getGuarantorFirstName();
				String guarantorLastName=tenancyForm.getGuarantorLastName();
				String guarantorAddress=tenancyForm.getGuarantorAddress();
				String guarantorEmailAddress=tenancyForm.getGuarantorEmailAddress();
				String guarantorContactNumber=tenancyForm.getGuarantorContactNumber();
				String kinFirstName=tenancyForm.getKinFirstName();
				String kinLastName=tenancyForm.getKinLastName();
				String kinAddress=tenancyForm.getKinAddress();
				String kinEmailAddress=tenancyForm.getKinEmailAddress();
				String kinContactNumber=tenancyForm.getKinContactNumber();
				Date tenancyStartedDate=tenancyForm.getTenancyStartedDate();
				String tenancyOpenedBy=tenancyForm.getTenancyOpenedBy();
				String tenancyClosedBy=tenancyForm.getTenancyClosedBy();
				Date tenancyClosedDate=tenancyForm.getTenancyClosedDate();
				String checkingOutForm=tenancyForm.getCheckingOutForm();
				String linkToProperty=tenancyForm.getLinkToProperty();
				String linkToTenancyAgreement=tenancyForm.getLinkToTenancyAgreement();
				String linkToRentAccounts=tenancyForm.getLinkToRentAccounts();
				String legalNotification=tenancyForm.getLegalNotifications();
				String tenancyDepositCertificate=tenancyForm.getTenancyDepositCertificate();
				String councilTaxRegistration=tenancyForm.getCouncilTaxRegistration();
				String additionalLinks=tenancyForm.getAdditionalLinks();
				String correspondenceWithTenants=tenancyForm.getCorrespondenceWithTenants();
				String correspondenceLinks=tenancyForm.getCorrespondenceLink();
				String employerCompany=tenancyForm.getEmployerCompany();
				String guarantorCompany=tenancyForm.getGuarantorCompany();
				
				List<TenantDetails> tenantDetails=tenantDetailsService.findTenantDetailsForTenancy(tenancyForm.getId());
				
				String tenantFirstName=null;
				String tenantTitle=null;
				String tenantLastName=null;
				Date tenantDateOfBirth=null;
				String tenantLandlineNumber=null;
				String tenantMobileNumber=null;
				String tenantEmailAddress=null;
				String tenantNationalInsuranceNumber=null;
				String tenantCurrentAddress=null;
				String tenantPassport=null;
				String tenantDriverLicense=null;
				String tenantCreditCheck=null;
				String tenantReferenceCheck=null;
				String tenantAdditionalRemark=null;
				
				for(TenantDetails td:tenantDetails){
				
					 tenantFirstName=td.getFirstName();
					 tenantTitle=td.getTitle();
					 tenantLastName=td.getLastName();
					 tenantDateOfBirth=td.getDateOfBirth();
					 tenantLandlineNumber=td.getLandlineNumber();
					 tenantMobileNumber=td.getMobileNumber();
					 tenantEmailAddress=td.getEmailAddress();
					 tenantNationalInsuranceNumber=td.getNationalInsuranceNumber();
					 tenantCurrentAddress=td.getCurrentAddress();
					 tenantPassport=td.getPassport();
					 tenantDriverLicense=td.getDriversLicense();
					 tenantCreditCheck=td.getCreditCheck();
					 tenantReferenceCheck=td.getReferenceCheck();
					 tenantAdditionalRemark=td.getAdditionalRemarks();
				}
				
		    	 model.addAttribute("propertyAddress", propertyAddress);
		    	 model.addAttribute("typeOfRental", typeOfRental);
		    	 model.addAttribute("roomNumber", roomNumber);
		    	 model.addAttribute("landLordFirstName", landLordFirstName);
		    	 model.addAttribute("landLordLastName", landLordLastName);
		    	 model.addAttribute("landLordAddress", landLordAddress);
		    	 model.addAttribute("landLordEmailAddress", landLordEmailAddress);
		    	 model.addAttribute("landLordContactNumber", landLordContactNumber);
		    	 model.addAttribute("employerFirstName", employerFirstName);
		    	 model.addAttribute("employerLastName", employerLastName);
		    	 model.addAttribute("employerAddress", employerAddress);
		    	 model.addAttribute("employerEmailAddress", employerEmailAddress);
		    	 model.addAttribute("employerContactNumber", employerContactNumber);
		    	 model.addAttribute("guarantorFirstName", guarantorFirstName);
		    	 model.addAttribute("guarantorLastName", guarantorLastName);
		    	 model.addAttribute("guarantorAddress", guarantorAddress);
		    	 model.addAttribute("guarantorEmailAddress", guarantorEmailAddress);
		    	 model.addAttribute("guarantorContactNumber", guarantorContactNumber);
		    	 model.addAttribute("kinFirstName", kinFirstName);
		    	 model.addAttribute("kinLastName", kinLastName);
		    	 model.addAttribute("kinAddress", kinAddress);
		    	 model.addAttribute("kinEmailAddress", kinEmailAddress);
		    	 model.addAttribute("kinContactNumber", kinContactNumber);
		    	 if(tenancyStartedDate!=null){
		    	 model.addAttribute("tenancyStartedDate", sdf.format(tenancyStartedDate));
		    	 }
		    	 model.addAttribute("tenancyOpenedBy", tenancyOpenedBy);
		    	 model.addAttribute("tenancyClosedBy", tenancyClosedBy);
		    	 if(tenancyClosedDate!=null){
		    	 model.addAttribute("tenancyClosedDate", sdf.format(tenancyClosedDate));
		    	 }
		    	 model.addAttribute("checkingOutForm", checkingOutForm);
		    	 model.addAttribute("linkToProperty", linkToProperty);
		    	 model.addAttribute("linkToTenancyAgreement", linkToTenancyAgreement);
		    	 model.addAttribute("linkToRentAccounts", linkToRentAccounts);
		    	 model.addAttribute("legalNotification", legalNotification);
		    	 model.addAttribute("tenancyDepositCertificate", tenancyDepositCertificate);
		    	 model.addAttribute("councilTaxRegistration", councilTaxRegistration);
		    	 model.addAttribute("additionalLinks", additionalLinks);
		    	 model.addAttribute("correspondenceWithTenants", correspondenceWithTenants);
		    	 model.addAttribute("correspondenceLinks", correspondenceLinks);
		    	 model.addAttribute("employerCompany", employerCompany);
		    	 model.addAttribute("guarantorCompany", guarantorCompany);
		    	 
		    	 
		    	
		    	 if(tenantDateOfBirth!=null){
		    	 model.addAttribute("tenantDateOfBirth", sdf.format(tenantDateOfBirth));
		    	 }
		    	 model.addAttribute("tenantFirstName", tenantFirstName);
		    	 model.addAttribute("tenantTitle", tenantTitle);
		    	 model.addAttribute("tenantLastName", tenantLastName);
		    	 model.addAttribute("tenantLandlineNumber", tenantLandlineNumber);
		    	 model.addAttribute("tenantMobileNumber", tenantMobileNumber);
		    	 model.addAttribute("tenantEmailAddress", tenantEmailAddress);
		    	 model.addAttribute("tenantNationalInsuranceNumber", tenantNationalInsuranceNumber);
		    	 model.addAttribute("tenantCurrentAddress", tenantCurrentAddress);
		    	 model.addAttribute("tenantPassport", tenantPassport);
		    	 model.addAttribute("tenantDriverLicense", tenantDriverLicense);
		    	 model.addAttribute("tenantCreditCheck", tenantCreditCheck);
		    	 model.addAttribute("tenantReferenceCheck", tenantReferenceCheck);
		    	 model.addAttribute("tenantAdditionalRemark", tenantAdditionalRemark);
		    	
		    	 
		    	 
				
		    	List<String> tenatDet=new ArrayList<String>();
		    	StringBuilder tenantD=new StringBuilder();
		    	for(RoomDetails rd:roomDetails){
		    		//roomDets.add(rd.getRoomName());
		    		roomD.append(rd.getRoomName()+",");
		    		System.out.println(rd.getRoomName());
		    	}
		    	
		    	System.out.println(roomD.toString());
		    	System.out.println(roomD.deleteCharAt(roomD.length()-1).toString());
		    	 model.addAttribute("roomDetailsMap", roomD.toString());
				
				
			}
			
			if(uf.getFormTable().equalsIgnoreCase("CustomerDetails")){
				
				CustomerDetails customerDetails=customerDetailsService.findCustomerDetailsFormByUserFormId(userFormId);
				
				String title=customerDetails.getTitle();
				String firstName=customerDetails.getFirstName();
				String lastName=customerDetails.getLastName();
				String contactNumber=customerDetails.getContactNumber();
				String mobileNumber=customerDetails.getMobileNumber();
				String emailAddress=customerDetails.getEmailAddress();
				String billingAddress=customerDetails.getBillingAddress();
				String shippingAddress=customerDetails.getShippingAddress();
				String personalHandDelivered=customerDetails.getPersonalHandDelivered();
				String paymentDetails=customerDetails.getPaymentDetails();
				String preferredMethodOfPayment=customerDetails.getPreferredMethodOfPayment();
				String mailingList=customerDetails.getMailingList();
				String orderHistory=customerDetails.getOrderHistory();
				String  appointmentHistory=customerDetails.getAppointmentHistory();
				String notes=customerDetails.getNotes();
				
				model.addAttribute("title", title);
				model.addAttribute("firstName", firstName);
				model.addAttribute("lastName", lastName);
				model.addAttribute("contactNumber", contactNumber);
				model.addAttribute("mobileNumber", mobileNumber);
				model.addAttribute("emailAddress", emailAddress);
				model.addAttribute("billingAddress", billingAddress);
				model.addAttribute("shippingAddress", shippingAddress);
				model.addAttribute("personalHandDelivered", personalHandDelivered);
				model.addAttribute("paymentDetails", paymentDetails);
				model.addAttribute("preferredMethodOfPayment", preferredMethodOfPayment);
				model.addAttribute("mailingList", mailingList);
				model.addAttribute("orderHistory", orderHistory);
				model.addAttribute("appointmentHistory", appointmentHistory);
				model.addAttribute("notes", notes);
			}
			
			if(uf.getFormTable().equalsIgnoreCase("RetailerOrder")){
				
				RetailerOrder retailerOrder=retailerOrderService.findRetailerOrderByUserFormId(userFormId);
				
				String title=retailerOrder.getTitle();
				String firstName=retailerOrder.getFirstName();
				String lastName=retailerOrder.getLastName();
				String contactNumber=retailerOrder.getContactNumber();
				String mobileNumber=retailerOrder.getMobileNumber();
				String emailAddress=retailerOrder.getEmailAddress();
				String billingAddress=retailerOrder.getBillingAddress();
				String shippingAddress=retailerOrder.getShippingAddress();
				String paymentDetails=retailerOrder.getPaymentDetails();
				String preferredMethodOfPayment=retailerOrder.getPreferredMethodOfPayment();
				String termsAndConditions=retailerOrder.getTermsAndConditions();
			    String notes=retailerOrder.getNotes();
			    String orderStatus=retailerOrder.getOrderStatus();
			    String companyName=retailerOrder.getCompanyName();
					
					String productCodeToBeOrdered=null;
					long quantity=0;
					double  price=0;
					double totals=0;
					
					StringBuilder sb=new StringBuilder();
					int i=0;
					int j=2;
					int k=2;
					int l=2;
					List<ProductOrder> productOrders=productOrderService.findProductOrderByRetailerOrderId(retailerOrder.getId());
					
					for(ProductOrder productOrder:productOrders){
						i++;
						if(productOrder.getFieldName().equals("totals")){
						totals=Double.parseDouble(productOrder.getValue());
						}
						
						else if(productOrder.getFieldName().equals("productCodeToBeOrdered1")){
							productCodeToBeOrdered=productOrder.getValue();
						}
						else if(productOrder.getFieldName().equals("quantity1")){
							quantity=Long.parseLong(productOrder.getValue());
						}
						else if(productOrder.getFieldName().equals("price1")){
							price=Double.parseDouble(productOrder.getValue());
						}else if(productOrder.getFieldName().startsWith("productCodeToBeOrdered")){
							//model.addAttribute("productCodeToBeOrdered"+j, productOrder.getValue());
							//j++;
								sb.append(productOrder.getFieldName()+"-"+productOrder.getValue()+",");
						}else if(productOrder.getFieldName().startsWith("quantity")){
							//model.addAttribute("quantity"+k, productOrder.getValue());
							//k++;
							sb.append(productOrder.getFieldName()+"-"+productOrder.getValue()+",");
						}else if(productOrder.getFieldName().startsWith("price")){
							//model.addAttribute("price"+l, productOrder.getValue());
							//l++;
							sb.append(productOrder.getFieldName()+"-"+Double.parseDouble(productOrder.getValue())+",");
							}
					}
				System.out.println("SB values for product order :   "+sb.toString());
				System.out.println("Table rows"+(i-4)/3);
				model.addAttribute("title", title);
				model.addAttribute("firstName", firstName);
				model.addAttribute("lastName", lastName);
				model.addAttribute("companyName", companyName);
				model.addAttribute("contactNumber", contactNumber);
				model.addAttribute("mobileNumber", mobileNumber);
				model.addAttribute("emailAddress", emailAddress);
				model.addAttribute("billingAddress", billingAddress);
				model.addAttribute("shippingAddress", shippingAddress);
				model.addAttribute("termsAndConditions", termsAndConditions);
				model.addAttribute("paymentDetails", paymentDetails);
				model.addAttribute("preferredMethodOfPayment", preferredMethodOfPayment);
				model.addAttribute("notes", notes);
				model.addAttribute("productCodeToBeOrdered", productCodeToBeOrdered);
				model.addAttribute("quantity", quantity);
				model.addAttribute("price", price);
				model.addAttribute("totals", totals);
				model.addAttribute("orderStatus", orderStatus);
				model.addAttribute("tableValues", sb.toString());
				model.addAttribute("tableRows", (i-4)/3);
			}
			
			
			if(uf.getFormTable().equalsIgnoreCase("BuilderDetailsForm")){
				BuilderDetailsForm builderDetailsForm=builderDetailsFormService.findBuilderDetailsFormByUserFormId(uf.getId());
				String firstName=builderDetailsForm.getFirstName();
		    	 String lastName=builderDetailsForm.getLastName();
		    	 String emailAddress=builderDetailsForm.getEmailAddress();
		    	 String landlineNumber=builderDetailsForm.getLandlineNumber();
		    	 String mobileNumber=builderDetailsForm.getMobileNumber();
		    	 String companyName=builderDetailsForm.getCompanyName();
		    	 String companyAddress=builderDetailsForm.getCompanyAddress();
		    	 String companyNumber=builderDetailsForm.getCompanyNumber();
		    	 String vatNumber=builderDetailsForm.getVatNumber();
		    	 String accountHoldersName=builderDetailsForm.getAccountHoldersName();
		    	 String accountNumber=builderDetailsForm.getAccountNumber();
		    	 String sortCode=builderDetailsForm.getSortCode();
		    	 String linkToInvoice=builderDetailsForm.getLinkToInvoice();
		    	 String linkToJobs=builderDetailsForm.getLinkToJobs();
		    	 
		    	 model.addAttribute("firstName", firstName);
		    	 model.addAttribute("lastName", lastName);
		    	 model.addAttribute("emailAddress", emailAddress);
		    	 model.addAttribute("landlineNumber", landlineNumber);
		    	 model.addAttribute("mobileNumber", mobileNumber);
		    	 model.addAttribute("companyName", companyName);
		    	 model.addAttribute("companyAddress", companyAddress);
		    	 model.addAttribute("companyNumber", companyNumber);
		    	 model.addAttribute("vatNumber", vatNumber);
		    	 model.addAttribute("accountHoldersName", accountHoldersName);
		    	 model.addAttribute("accountNumber", accountNumber);
		    	 model.addAttribute("sortCode", sortCode);
		    	 model.addAttribute("linkToInvoice", linkToInvoice);
		    	 model.addAttribute("linkToJobs", linkToJobs);
		    	 
			}
			
			if(uf.getFormTable().equalsIgnoreCase("UtilitiesCompanyDetailsForm")){
				UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=utilitiesCompanyDetailsFormService.findUtilitiesCompanyDetailsFormByUserFormId(userFormId);
			String companyName =	utilitiesCompanyDetailsForm.getCompanyName();
	        	String companyAddress=	utilitiesCompanyDetailsForm.getCompanyAddress();
	        	String companyType=	utilitiesCompanyDetailsForm.getCompanyType();
	        	String companyTelephone=	utilitiesCompanyDetailsForm.getCompanyTelephone();
	        	String openingAndClosingTimes=	utilitiesCompanyDetailsForm.getOpeningAndClosingTimes();
	        	String mainContactName=	utilitiesCompanyDetailsForm.getMainContactName();
	        	String mainContactTelephone=	utilitiesCompanyDetailsForm.getMainContactTelephone();
	        	String mainContactEmailAddress=	utilitiesCompanyDetailsForm.getMainContactEmailAddress();
		    	 
		    	 model.addAttribute("companyName", companyName);
		    	 model.addAttribute("companyAddress", companyAddress);
		    	 model.addAttribute("companyType", companyType);
		    	 model.addAttribute("companyTelephone", companyTelephone);
		    	 model.addAttribute("openingAndClosingTimes", openingAndClosingTimes);
		    	 model.addAttribute("mainContactName", mainContactName);
		    	 model.addAttribute("mainContactTelephone", mainContactTelephone);
		    	 model.addAttribute("mainContactEmailAddress", mainContactEmailAddress);
		    	 
		    	 
			}
			
			
			
		model.addAttribute("userFormId", userFormId);	
			model.addAttribute("formDefinition", form.getHandCodedPath());
			model.addAttribute("formId", Long.toString(-1));
			model.addAttribute("formName", Utils.formNameVersion(form.getFormDefs().getName(), form.getVersion()));
			List<FormFieldValues> userFormData = formService.getUserFormData(-1, userFormId);
			model.addAttribute("securityGrp",uf.getSecurityGroupId());
			model.addAttribute("submissionDate",sdf.format(uf.getSubmissionDate()));
			model.addAttribute("userFormData", toJavascript(userFormId, userFormData));
			model.addAttribute("companyGrp", uf.getCompanyId());
			model.addAttribute("modelId", uf.getModelId());
			model.addAttribute("formDefId", uf.getForms().getFormDefs().getId());
			Attribute attribute = dtAttributeService.findAttributeByOrder(1);
			
			
			Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
			//Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
			Set<AttributeValue> attr1ValuesRestricted = new HashSet<AttributeValue>();
			List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
			for(CompanyUser cu:cus){
				attr1ValuesRestricted.add(cu.getAttrValue());
			}
			model.addAttribute("compGroups", Util.getActiveAttributeValues(attr1ValuesRestricted));
			
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
			
			Set<WfModel> wfs=form.getModels();
			boolean subDate=true;
			
			if(wfs.size()==1){
				for(WfModel wfm:wfs){
					String workflowName=wfm.getName();
				if (workflowName.equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
					subDate=false;
				}
				}
			}
			model.addAttribute("subDate", subDate);
			model.addAttribute("compGroups", Util.getActiveAttributeValues(attr1ValuesRestricted));
			model.addAttribute("workflows", uf.getForms().getModels());
		}

		return form.getName();
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
			model.addAttribute("formDefId", uf.getForms().getFormDefs().getId());
		}

		return "forms";
	}

	@RequestMapping(value = "/createPotentialTenant", method = RequestMethod.POST)
	public String saveCaseFormDataForPotentialTenantForm(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long securityGroupId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("compGrp")long compGrp,@RequestParam("wrkFlw")long modelId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
         String title=null;
    	 String firstName = null;
    	 String lastName=null;
    	 String landLineNumber=null;
    	 String mobileNumber=null;
    	 String emailAddress=null;
    	 String nationalInsuranceNumber=null;
    	 String currentAddress=null;
    	 String type=null;
    	 long numberOfBedRooms = 0;
    	 String garden = null;
    	 String offRoadParking=null;
    	 String garage=null;
    	 String other=null;
        
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            
            System.out.println("Key is "+key);

            

                if(value.length>1){    
                    for (int i = 0; i < value.length; i++) {
                        System.out.println( value[i].toString());
                      
                    }
                }else
                {
            	System.out.println("Value is "+value[0].toString());
            	
            	 if(key.equals("title"))
              	   title=value[0].toString();
                 else if(key.equals("firstName"))
              	   firstName=value[0].toString();
                 else if(key.equals("lastName"))
              	   lastName=value[0].toString();
                 else if(key.equals("mobileNumber"))   
              	   mobileNumber=value[0].toString();
                 else if(key.equals("landLineNumber"))
              	   landLineNumber=value[0].toString();
                 else if(key.equals("emailAddress"))	   
              	   emailAddress=value[0].toString();
                 else if(key.equals("nationalInsuranceNumber"))
              	   nationalInsuranceNumber=value[0].toString();
                 else if(key.equals("currentAddress"))
              	   currentAddress=value[0].toString();
                 else if(key.equals("type")) 
              	   type=value[0].toString();
              	   else if(key.equals("numberOfBedRooms")){
              		   if(value[0].toString()!="")
              		   numberOfBedRooms=Long.parseLong(value[0].toString());
              	   }
              		   else if(key.equals("garden"))
              			   garden=value[0].toString();
              			   else if(key.equals("offRoadParking"))
              				   offRoadParking=value[0].toString();
              				   else if(key.equals("garage"))
              					   garage=value[0].toString();
              					   else if(key.equals("other"))
              						   other=value[0].toString();
                        
                }
        }
        
        
        UserForms uf=null;
        
        if(userFormId==-1){
        	FormDefs fds=formDefsService.findFormDefsById(formDefId);
            Forms forms=formService.fetchFormDefinition(formDefId, -1);
            
        uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 's', securityGroupId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"PotentialTenantForm");
       
        userFormsService.save(uf);
        uf.setTitle(fds.getName()+"-"+uf.getId());
        userFormsService.save(uf);
        
        userFormId=uf.getId();
        
        PotentialTenantForm potentialTenantForm=new PotentialTenantForm(uf.getId(),title, firstName, lastName, landLineNumber, mobileNumber, emailAddress, nationalInsuranceNumber, currentAddress, type, numberOfBedRooms, garden, offRoadParking, garage, other);
       // potentialTenantForm.setEmailAddress(emailAddress);
        potentialTenantFormService.save(potentialTenantForm);
        
        UserFormRef userFormRef=new UserFormRef(uf, "PotentialTenantForm");
        userFormRefService.save(userFormRef);
        }
        else{
        	uf=userFormsService.findUserFormsById(userFormId);
        	PotentialTenantForm ptf=potentialTenantFormService.findPotentialTenantFormByUserFormId(userFormId);
        	ptf.setTitle(title);
        	ptf.setFirstName(firstName);
        	ptf.setLastName(lastName);
        	ptf.setLandLineNumber(landLineNumber);
        	ptf.setMobileNumber(mobileNumber);
        	ptf.setEmailAddress(emailAddress);
        	ptf.setNationalInsuranceNumber(nationalInsuranceNumber);
        	ptf.setCurrentAddress(currentAddress);
        	ptf.setType(type);
        	ptf.setNumberOfBedRooms(numberOfBedRooms);
        	ptf.setGarden(garden);
        	ptf.setOffRoadParking(offRoadParking);
        	ptf.setGarage(garage);
        	ptf.setOther(other);
        	 potentialTenantFormService.save(ptf);
        }
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);

		Forms form = uf.getForms();
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
			WfModel wfModel = modelService.findModelByName(workflowName);
			
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
			String userFormIdStr = String.valueOf(uf.getId());
			SecurityGroup securityGroup=null;
			if(securityGroupId!=0){
			securityGroup=securityGroupService.findSecurityGroupById(securityGroupId);
			}
			Doctype doctype=null;
			//String formDefName= uf.getForms().getFormDefs().getName();
			//String finalFormName=uf.getTitle();
			Document newForms=new Document(doctype,null, userInfo.getUserName(),uf.getTitle(),"Forms", wfCase.getId(),new Date(),sdf.parse(submissionDate),null,'Y',userFormIdStr,'F');
			if (workflowName.equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
				//System.out.println("workFlow:::::::::::"+workFlow);
				//newForms.setCaseId(0);
				
				uploadDocumentIntoAlfrescoForForms(session,userFormId);
				newForms.setWip('N');
			}
			newForms.setSecurityGroup(securityGroup);
			documentService.save(newForms);		
			User user=userService.findUserById(userInfo.getUserId());
			
			
			FormTrail ft=new FormTrail(uf, user, "To Approve", new Date(), "Submitted", "");
			formTrailService.save(ft);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("/create Error message "+e);
			e.printStackTrace();
		}
		//model.addAttribute("result", "Form Saved & Case Created");
		return "refresh";
	}
	
	@RequestMapping(value = "/createPropertyDetails", method = RequestMethod.POST)
	public String saveCaseFormDataForPropertyDetailsForm(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long securityGroupId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("compGrp")long compGrp,@RequestParam("wrkFlw")long modelId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        String client=null;
    	
   	 String firstName=null;
   	 String lastName=null;
   	 String landlineNumber=null;
   	 String mobileNumber=null;
   	 String emailAddress=null;
   	 String address=null;
   	// String accountDetails=null;
   	 String accountNumber=null;
   	 String accountHoldersName=null;
   	 String sortCode=null;
   	 String vatDetails=null;
   	 String referencesForPayment=null;
   	 String propertyAddressLine1=null;
   	 String propertyAddressLine2=null;
   	 String town=null;
   	 String cityCountry=null;
   	 String propertyPostCode=null;
   	 String propertyType=null;
   	 String propertyDescription=null;
   	 String houseMeasurements=null;
   	 String gasMeterLocation=null;
   	 String electricMeterLocation=null;
   	 String waterMeterLocation=null;
   	 Date  dateOfPerchase=null;
   	 String priceOfPurchase=null;
   	 String estimatedValue=null;
   	 Date asOfDate=null;
   	 String annualRent=null;
   	 String pictures=null;
   	 String localAuthority=null;
   	 String rentalType=null;
   	 String frontDoorKeyCode=null;
   	 String backDoorKeyCode=null;
   	 String porchDoorKeyCode=null;
   	 String flatDoor=null;
   	 String others=null;
   	 long numberOfBedrooms=0;
   	 String bedroomDoor=null;
   	 String bedroomWindow=null;
   	 String tenancyAgreement=null;
   	 String insuranceCertificate=null;
   	 String floorPlan=null;
   	 String epcCertificate=null;
   	 String gasCertificate=null;
   	 String electricCertificate=null;
   	 String hmoLicence=null;
   	 String contractsAndWarranties=null;
   	 String landRegistry=null;
   	 String currentTenancyForm=null;
   	 String propertyTimeline=null;
   	 String linkToJobs=null;
   	 String lendingInformation=null;
   	 String managementCompany=null;
   	 String companyName=null;
       
      HashMap<String , String> roomDet=new HashMap<String,String>();
       while(it.hasNext()){
      	 
           Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

           String key             = entry.getKey();
           String[] value         = entry.getValue();
           
           System.out.println("Key is "+key);

           

               if(value.length>1){    
                   for (int i = 0; i < value.length; i++) {
                       System.out.println( value[i].toString());
                     
                   }
               }else
               {
           	System.out.println("Value is "+value[0].toString());
           	
           	 if(key.equals("client"))
             	   client=value[0].toString();
                else if(key.equals("firstName"))
             	   firstName=value[0].toString();
                else if(key.equals("lastName"))
             	   lastName=value[0].toString();
                else if(key.equals("landlineNumber"))   
               	   landlineNumber=value[0].toString();
                else if(key.equals("mobileNumber"))   
             	   mobileNumber=value[0].toString();
                else if(key.equals("landLineNumber"))
             	   landlineNumber=value[0].toString();
                else if(key.equals("emailAddress"))	   
             	   emailAddress=value[0].toString();
                else if(key.equals("address"))
             	   address=value[0].toString();
                else if(key.equals("accountNumber"))
               	 accountNumber=value[0].toString();
                else if(key.equals("accountHoldersName")) 
               	 accountHoldersName=value[0].toString();
             	   else if(key.equals("sortCode"))
             		 sortCode=value[0].toString();
             		   else if(key.equals("vatDetails"))
             			 vatDetails=value[0].toString();
             			   else if(key.equals("referencesForPayment"))
             				 referencesForPayment=value[0].toString();
             				   else if(key.equals("propertyAddressLine1"))
             					 propertyAddressLine1=value[0].toString();
             					   else if(key.equals("propertyAddressLine2"))
             						 propertyAddressLine2=value[0].toString();
             					 else if(key.equals("town"))
             						town=value[0].toString();
             					else if(key.equals("cityCountry"))
             						cityCountry=value[0].toString();
             					else if(key.equals("propertyPostCode"))
             						propertyPostCode=value[0].toString();
             					else if(key.equals("propertyType"))
             						propertyType=value[0].toString();
             					else if(key.equals("propertyDescription"))
             						propertyDescription=value[0].toString();
             					else if(key.equals("houseMeasurements"))
             						houseMeasurements=value[0].toString();
             					else if(key.equals("gasMeterLocation"))
             						gasMeterLocation=value[0].toString();
             					else if(key.equals("electricMeterLocation"))
            						 electricMeterLocation=value[0].toString();
             					else if(key.equals("waterMeterLocation"))
            						 waterMeterLocation=value[0].toString();
             					else if(key.equals("dateOfPerchase")){
             						if(value[0].toString()!="")
            						 dateOfPerchase=sdf.parse(value[0].toString());
             					}
             					else if(key.equals("priceOfPurchase"))
            						 priceOfPurchase=value[0].toString();
             					else if(key.equals("estimatedValue"))
            						 estimatedValue=value[0].toString();
             					else if(key.equals("asOfDate"))
             					{
             						if(value[0].toString()!="")
            						 asOfDate=sdf.parse(value[0].toString());
             					}
             					else if(key.equals("annualRent"))
            						 annualRent=value[0].toString();
             					else if(key.equals("pictures"))
            						 pictures=value[0].toString();
             					else if(key.equals("localAuthority"))
            						 localAuthority=value[0].toString();
             					else if(key.equals("rentalType"))
            						 rentalType=value[0].toString();
             					else if(key.equals("frontDoorKeyCode"))
           						 frontDoorKeyCode=value[0].toString();
             					else if(key.equals("backDoorKeyCode"))
           						 backDoorKeyCode=value[0].toString();
             					else if(key.equals("porchDoorKeyCode"))
           						 porchDoorKeyCode=value[0].toString();
             					else if(key.equals("flatDoor"))
           						 flatDoor=value[0].toString();
             					else if(key.equals("others"))
           						 others=value[0].toString();
             					else if(key.equals("numberOfBedrooms")){
             						if(value[0].toString()!="")
           						 numberOfBedrooms=Long.parseLong(value[0].toString());
             					}
             					else if(key.equals("tenancyAgreement"))
           						 tenancyAgreement=value[0].toString();
             					else if(key.equals("insuranceCertificate"))
           						 insuranceCertificate=value[0].toString();
             					else if(key.equals("floorPlan"))
           						 floorPlan=value[0].toString();
             					else if(key.equals("epcCertificate"))
           						 epcCertificate=value[0].toString();
             					else if(key.equals("gasCertificate"))
           						 gasCertificate=value[0].toString();
             					else if(key.equals("electricCertificate"))
           						 electricCertificate=value[0].toString();
             					else if(key.equals("hmoLicence"))
           						 hmoLicence=value[0].toString();
             					else if(key.equals("contractsAndWarranties"))
           						 contractsAndWarranties=value[0].toString();
             					else if(key.equals("landRegistry"))
           						 landRegistry=value[0].toString();
             					else if(key.equals("currentTenancyForm"))
           						 currentTenancyForm=value[0].toString();
             					else if(key.equals("propertyTimeline"))
           						 propertyTimeline=value[0].toString();
             					else if(key.equals("linkToJobs"))
           						 linkToJobs=value[0].toString();
             					else if(key.equals("lendingInformation"))
           						 lendingInformation=value[0].toString();
             					else if(key.equals("managementCompany"))
             						managementCompany=value[0].toString();
             					else if(key.equals("companyName"))
             						companyName=value[0].toString();
             					else{
             						if(!key.equals("formDefId") && !key.equals("secGrp") && !key.equals("submissionDate") && !key.equals("wrkFlw") && !key.equals("compGrp")){
             							System.out.println(value[0].toString());
             							roomDet.put(key, value[0].toString());
             						}
             						if(key.startsWith("bedroom")){
             							System.out.println(value[0].toString());
             							roomDet.put(key, value[0].toString());
             						}
             					}
             					
           	 
                       
               }
       }
       UserForms uf=null;
       if(userFormId==-1){
       FormDefs fds=formDefsService.findFormDefsById(formDefId);
       Forms forms=formService.fetchFormDefinition(formDefId, -1);
       uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', securityGroupId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"PropertyDetailsForm");
       userFormsService.save(uf);
       uf.setTitle(fds.getName()+"-"+uf.getId());
       userFormsService.save(uf);
       System.out.println(fds.getName()+"-"+uf.getId());
      
       userFormId=uf.getId();
       
      PropertyDetailsForm propertyDetailsForm=new PropertyDetailsForm(uf.getId(), client, firstName, lastName, landlineNumber, mobileNumber, emailAddress, address, accountNumber, accountHoldersName, sortCode, vatDetails, referencesForPayment, propertyAddressLine1, propertyAddressLine2, town, cityCountry, propertyPostCode, propertyType, propertyDescription, houseMeasurements, gasMeterLocation, electricMeterLocation, waterMeterLocation, dateOfPerchase, priceOfPurchase, estimatedValue, asOfDate, annualRent, pictures, localAuthority, rentalType, frontDoorKeyCode, backDoorKeyCode, porchDoorKeyCode, flatDoor, others, numberOfBedrooms, tenancyAgreement, insuranceCertificate, floorPlan, epcCertificate, gasCertificate, electricCertificate, hmoLicence, contractsAndWarranties, landRegistry, currentTenancyForm, propertyTimeline, linkToJobs, lendingInformation,managementCompany,companyName);
      propertyDetailsFormService.save(propertyDetailsForm);
      
     Set<Entry<String, String>> setRoomDet=roomDet.entrySet();
     Iterator ite=setRoomDet.iterator();
     
     while(ite.hasNext()){
   	  Map.Entry<String,String> entry = (Map.Entry<String,String>)ite.next();

         String key             = entry.getKey();
         String value         = entry.getValue();
         
         RoomDetails roomDetails=new RoomDetails(propertyDetailsForm, key, value);
         roomDetailsService.save(roomDetails);
     }
      
      
      
       UserFormRef userFormRef=new UserFormRef(uf, "PropertyDetailsForm");
       userFormRefService.save(userFormRef);
       
       }else{
    	   uf=userFormsService.findUserFormsById(userFormId);
       	PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormByUserFormId(userFormId);
       	pdf.setClient(client);
       	pdf.setFirstName(firstName);
       	pdf.setLastName(lastName);
       	pdf.setLandlineNumber(landlineNumber);
       	pdf.setMobileNumber(mobileNumber);
       	pdf.setEmailAddress(emailAddress);
       	pdf.setAddress(address);
       	pdf.setAccountNumber(accountNumber);
       	pdf.setAccountHoldersName(accountHoldersName);
       	pdf.setSortCode(sortCode);
       	pdf.setVatDetails(vatDetails);
       	pdf.setReferencesForPayment(referencesForPayment);
       	pdf.setPropertyAddressLine1(propertyAddressLine1);
       	pdf.setPropertyAddressLine2(propertyAddressLine2);
       	pdf.setTown(town);
       	pdf.setCityCountry(cityCountry);
       	pdf.setPropertyPostCode(propertyPostCode);
       	pdf.setPropertyType(propertyType);
       	pdf.setPropertyDescription(propertyDescription);
       	pdf.setHouseMeasurements(houseMeasurements);
       	pdf.setGasMeterLocation(gasMeterLocation);
       	pdf.setElectricMeterLocation(electricMeterLocation);
       	pdf.setWaterMeterLocation(waterMeterLocation);
       	pdf.setDateOfPerchase(dateOfPerchase);
       	pdf.setPriceOfPurchase(priceOfPurchase);
       	pdf.setEstimatedValue(estimatedValue);
       	pdf.setAsOfDate(asOfDate);
       	pdf.setAnnualRent(annualRent);
       	pdf.setPictures(pictures);
       	pdf.setLocalAuthority(localAuthority);
       	pdf.setRentalType(rentalType);
       	pdf.setFrontDoorKeyCode(frontDoorKeyCode);
       	pdf.setBackDoorKeyCode(backDoorKeyCode);
       	pdf.setPorchDoorKeyCode(porchDoorKeyCode);
       	pdf.setFlatDoor(flatDoor);
       	pdf.setOthers(others);
       	pdf.setNumberOfBedrooms(numberOfBedrooms);
       	pdf.setTenancyAgreement(tenancyAgreement);
       	pdf.setInsuranceCertificate(insuranceCertificate);
       	pdf.setFloorPlan(floorPlan);
       	pdf.setEpcCertificate(epcCertificate);
       	pdf.setGasCertificate(gasCertificate);
       	pdf.setElectricCertificate(electricCertificate);
       	pdf.setHmoLicence(hmoLicence);
       	pdf.setContractsAndWarranties(contractsAndWarranties);
       	pdf.setLandRegistry(landRegistry);
       	pdf.setCurrentTenancyForm(currentTenancyForm);
       	pdf.setPropertyTimeline(propertyTimeline);
       	pdf.setLinkToJobs(linkToJobs);
       	pdf.setLendingInformation(lendingInformation);
       	pdf.setManagementCompany(managementCompany);
       	pdf.setCompanyName(companyName);
       	
       	propertyDetailsFormService.save(pdf);
       	
       	 List<RoomDetails> roomDetails=roomDetailsService.findRoomDetailsByPropertyDetailsFormId(pdf);
            roomDetailsService.delete(roomDetails);
       	
       	 Set<Entry<String, String>> setRoomDet=roomDet.entrySet();
            Iterator ite=setRoomDet.iterator();
            
            while(ite.hasNext()){
          	  Map.Entry<String,String> entry = (Map.Entry<String,String>)ite.next();

                String key             = entry.getKey();
                String value         = entry.getValue();
                
                
              RoomDetails roomDetail=new RoomDetails(pdf, key, value);
              roomDetailsService.save(roomDetail);
                
            }
       	
       }
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);

		Forms form = uf.getForms();
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
			WfModel wfModel = modelService.findModelByName(workflowName);
			
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
			String userFormIdStr = String.valueOf(uf.getId());
			SecurityGroup securityGroup=null;
			if(securityGroupId!=0){
			securityGroup=securityGroupService.findSecurityGroupById(securityGroupId);
			}
			Doctype doctype=null;
			//String formDefName= uf.getForms().getFormDefs().getName();
			//String finalFormName=uf.getTitle();
			Document newForms=new Document(doctype,null, userInfo.getUserName(),uf.getTitle(),"Forms", wfCase.getId(),new Date(),sdf.parse(submissionDate),null,'Y',userFormIdStr,'F');
			if (workflowName.equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
				//System.out.println("workFlow:::::::::::"+workFlow);
				//newForms.setCaseId(0);
				
				uploadDocumentIntoAlfrescoForForms(session,userFormId);
				newForms.setWip('N');
			}
			newForms.setSecurityGroup(securityGroup);
			documentService.save(newForms);		
			User user=userService.findUserById(userInfo.getUserId());
			
			
			FormTrail ft=new FormTrail(uf, user, "To Approve", new Date(), "Submitted", "");
			formTrailService.save(ft);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("/create Error message "+e);
			e.printStackTrace();
		}
		//model.addAttribute("result", "Form Saved & Case Created");
		return "refresh";
	}
	
	@RequestMapping(value = "/createTenancy", method = RequestMethod.POST)
	public String saveCaseFormDataForTenancyForm(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long securityGroupId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("compGrp")long compGrp,@RequestParam("wrkFlw")long modelId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        String propertyAddress=null;
		String typeOfRental=null;
		long roomNumber=0;
		String landLordFirstName=null;
		String landLordLastName=null;
		String landLordAddress=null;
		String landLordEmailAddress=null;
		String landLordContactNumber=null;
		String employerFirstName=null;
		String employerLastName=null;
		String employerAddress=null;
		String employerEmailAddress=null;
		String employerContactNumber=null;
		String guarantorFirstName=null;
		String guarantorLastName=null;
		String guarantorAddress=null;
		String guarantorEmailAddress=null;
		String guarantorContactNumber=null;
		String kinFirstName=null;
		String kinLastName=null;
		String kinAddress=null;
		String kinEmailAddress=null;
		String kinContactNumber=null;
		Date tenancyStartedDate=null;
		String tenancyOpenedBy=null;
		String tenancyClosedBy=null;
		Date tenancyClosedDate=null;
		String checkingOutForm=null;
		String linkToProperty=null;
		String linkToTenancyAgreement=null;
		String linkToRentAccounts=null;
		String legalNotification=null;
		String tenancyDepositCertificate=null;
		String councilTaxRegistration=null;
		String additionalLinks=null;
		String correspondenceWithTenants=null;
		String correspondenceLinks=null;
		String employerCompany=null;
		String guarantorCompany=null;
		
		
		String tenantFirstName=null;
		String tenantTitle=null;
		String tenantLastName=null;
		Date tenantDateOfBirth=null;
		String tenantLandlineNumber=null;
		String tenantMobileNumber=null;
		String tenantEmailAddress=null;
		String tenantNationalInsuranceNumber=null;
		String tenantCurrentAddress=null;
		String tenantPassport=null;
		String tenantDriverLicense=null;
		String tenantCreditCheck=null;
		String tenantReferenceCheck=null;
		String tenantAdditionalRemark=null;
		
				
		
		
        
       HashMap<String , String> roomDet=new HashMap<String,String>();
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            
            System.out.println("Key is "+key);

            

                if(value.length>1){    
                    for (int i = 0; i < value.length; i++) {
                        System.out.println( value[i].toString());
                      
                    }
                }else
                {
            	System.out.println("Value is "+value[0].toString());
            	
            	 if(key.equals("propertyAddress"))
            		 propertyAddress=value[0].toString();
                 else if(key.equals("typeOfRental"))
                	 typeOfRental=value[0].toString();
                 else if(key.equals("roomNumber")){
                	 if(value[0].toString()!="")
                	 roomNumber=Long.parseLong(value[0].toString());
                 }
                 else if(key.equals("landLordFirstName"))   
                	 landLordFirstName=value[0].toString();
                 else if(key.equals("landLordLastName"))   
                	 landLordLastName=value[0].toString();
                 else if(key.equals("landLordAddress"))
                	 landLordAddress=value[0].toString();
                 else if(key.equals("landLordEmailAddress"))	   
                	 landLordEmailAddress=value[0].toString();
                 else if(key.equals("landLordContactNumber"))
                	 landLordContactNumber=value[0].toString();
                 else if(key.equals("employerFirstName"))
                	 employerFirstName=value[0].toString();
                 else if(key.equals("employerLastName")) 
                	 employerLastName=value[0].toString();
              	   else if(key.equals("employerAddress"))
              		 employerAddress=value[0].toString();
              		   else if(key.equals("employerEmailAddress"))
              			 employerEmailAddress=value[0].toString();
              		 else if(key.equals("employerContactNumber"))
              			 employerContactNumber=value[0].toString();
              		  else if(key.equals("guarantorFirstName"))
              			guarantorFirstName=value[0].toString();
                      else if(key.equals("guarantorLastName")) 
                    	  guarantorLastName=value[0].toString();
                   	   else if(key.equals("guarantorAddress"))
                   		guarantorAddress=value[0].toString();
                   		   else if(key.equals("guarantorEmailAddress"))
                   			guarantorEmailAddress=value[0].toString();
              			   else if(key.equals("guarantorContactNumber"))
              				 guarantorContactNumber=value[0].toString();
              				   else if(key.equals("kinFirstName"))
              					 kinFirstName=value[0].toString();
              					   else if(key.equals("kinLastName"))
              						 kinLastName=value[0].toString();
              					 else if(key.equals("kinAddress"))
              						kinAddress=value[0].toString();
              					else if(key.equals("kinEmailAddress"))
              						kinEmailAddress=value[0].toString();
              					else if(key.equals("kinContactNumber"))
              						kinContactNumber=value[0].toString();
              					else if(key.equals("tenancyOpenedBy"))
              						tenancyOpenedBy=value[0].toString();
              					else if(key.equals("tenancyClosedBy"))
              						tenancyClosedBy=value[0].toString();
              					else if(key.equals("checkingOutForm"))
              						checkingOutForm=value[0].toString();
              					else if(key.equals("linkToProperty"))
              						linkToProperty=value[0].toString();
              					else if(key.equals("linkToTenancyAgreement"))
              						linkToTenancyAgreement=value[0].toString();
              					else if(key.equals("linkToRentAccounts"))
              						linkToRentAccounts=value[0].toString();
              					else if(key.equals("tenancyStartedDate")){
              						if(value[0].toString()!="")
              							tenancyStartedDate=sdf.parse(value[0].toString());
              					}
              					else if(key.equals("legalNotification"))
              						legalNotification=value[0].toString();
              					else if(key.equals("tenancyDepositCertificate"))
              						tenancyDepositCertificate=value[0].toString();
              					else if(key.equals("tenancyClosedDate"))
              					{
              						if(value[0].toString()!="")
              							tenancyClosedDate=sdf.parse(value[0].toString());
              					}
              					else if(key.equals("councilTaxRegistration"))
              						councilTaxRegistration=value[0].toString();
              					else if(key.equals("additionalLinks"))
              						additionalLinks=value[0].toString();
              					else if(key.equals("correspondenceWithTenants"))
              						correspondenceWithTenants=value[0].toString();
              					else if(key.equals("correspondenceLinks"))
              						correspondenceLinks=value[0].toString();
              					else if(key.equals("employerCompany"))
              						employerCompany=value[0].toString();
              					else if(key.equals("guarantorCompany"))
              						guarantorCompany=value[0].toString();
              					
            	 
              					else if(key.equals("tenantFirstName"))
              						tenantFirstName=value[0].toString();
              					else if(key.equals("tenantTitle"))
              						tenantTitle=value[0].toString();
              					else if(key.equals("tenantLastName"))
              						tenantLastName=value[0].toString();
              					else if(key.equals("tenantCreditCheck"))
              						tenantCreditCheck=value[0].toString();
              					else if(key.equals("tenantEmailAddress"))
              						tenantEmailAddress=value[0].toString();
              					else if(key.equals("tenantDateOfBirth")){
              						if(value[0].toString()!=null && value[0].toString()!="")
              						tenantDateOfBirth=sdf.parse(value[0].toString());
              					}
              					else if(key.equals("tenantLandlineNumber"))
              						tenantLandlineNumber=value[0].toString();
              					else if(key.equals("tenantNationalInsuranceNumber"))
              						tenantNationalInsuranceNumber=value[0].toString();
              					else if(key.equals("tenantCurrentAddress"))
              						tenantCurrentAddress=value[0].toString();
              					else if(key.equals("tenantPassport"))
              						tenantPassport=value[0].toString();
              					else if(key.equals("tenantDriverLicense"))
              						tenantDriverLicense=value[0].toString();
              					else if(key.equals("tenantReferenceCheck"))
              						tenantReferenceCheck=value[0].toString();
              					else if(key.equals("tenantAdditionalRemark"))
              						tenantAdditionalRemark=value[0].toString();
              					else if(key.equals("tenantMobileNumber"))
              						tenantMobileNumber=value[0].toString();
              					
              					
                }
        }
        UserForms uf=null;
        if(userFormId==-1){
        FormDefs fds=formDefsService.findFormDefsById(formDefId);
        Forms forms=formService.fetchFormDefinition(formDefId, -1);
        uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', securityGroupId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"TenancyForm");
        userFormsService.save(uf);
        uf.setTitle(fds.getName()+"-"+uf.getId());
        userFormsService.save(uf);
        System.out.println(fds.getName()+"-"+uf.getId());
        
        userFormId=uf.getId();
       
        TenancyForm tenancyForm=new TenancyForm(uf.getId(), propertyAddress, typeOfRental, roomNumber, landLordFirstName, landLordLastName, landLordAddress, landLordEmailAddress, landLordContactNumber, employerFirstName, employerLastName, employerAddress, employerEmailAddress, employerContactNumber, guarantorFirstName, guarantorLastName, guarantorAddress, guarantorEmailAddress, guarantorContactNumber, kinFirstName, kinLastName, kinAddress, kinEmailAddress, kinContactNumber, tenancyOpenedBy, tenancyStartedDate, tenancyClosedBy, tenancyClosedDate, checkingOutForm, linkToProperty, linkToTenancyAgreement, linkToRentAccounts, legalNotification, tenancyDepositCertificate, councilTaxRegistration, additionalLinks, correspondenceWithTenants, correspondenceLinks,employerCompany,guarantorCompany);
     
       tenancyFormService.save(tenancyForm);
     
          TenantDetails tenantDetails=new TenantDetails(tenancyForm.getId(), tenantTitle, tenantFirstName, tenantLastName, tenantDateOfBirth, tenantLandlineNumber, tenantMobileNumber, tenantEmailAddress, tenantNationalInsuranceNumber, tenantCurrentAddress, tenantPassport, tenantDriverLicense, tenantCreditCheck, tenantReferenceCheck, tenantAdditionalRemark);
         tenantDetailsService.save(tenantDetails);
     
       
        
        }else{
        	uf=userFormsService.findUserFormsById(userFormId);
        	TenancyForm tenancyForm=tenancyFormService.findTenancyFormByUserFormId(userFormId);
        	tenancyForm.setPropertyAddress(propertyAddress);
        	tenancyForm.setTypeOfRental(typeOfRental);
        	tenancyForm.setRoomNumber(roomNumber);
        	tenancyForm.setLandLordFirstName(landLordFirstName);
        	tenancyForm.setLandLordLastName(landLordLastName);
        	tenancyForm.setLandLordAddress(landLordAddress);
        	tenancyForm.setLandLordEmailAddress(landLordEmailAddress);
        	tenancyForm.setLandLordContactNumber(landLordContactNumber);
        	tenancyForm.setEmployerFirstName(employerFirstName);
        	tenancyForm.setEmployerLastName(employerLastName);
        	tenancyForm.setEmployerAddress(employerAddress);
        	tenancyForm.setEmployerEmailAddress(employerEmailAddress);
        	tenancyForm.setEmployerContactNumber(employerContactNumber);
        	tenancyForm.setKinFirstName(kinFirstName);
        	tenancyForm.setKinLastName(kinLastName);
        	tenancyForm.setKinAddress(kinAddress);
        	tenancyForm.setKinEmailAddress(kinEmailAddress);
        	tenancyForm.setKinContactNumber(kinContactNumber);
        	tenancyForm.setTenancyStartedDate(tenancyStartedDate);
        	tenancyForm.setTenancyOpenedBy(tenancyOpenedBy);
        	tenancyForm.setTenancyClosedDate(tenancyClosedDate);
        	tenancyForm.setTenancyClosedBy(tenancyClosedBy);
        	tenancyForm.setTenancyDepositCertificate(tenancyDepositCertificate);
        	tenancyForm.setLinkToProperty(linkToProperty);
        	tenancyForm.setCheckingOutForm(checkingOutForm);
        	tenancyForm.setLinkToTenancyAgreement(linkToTenancyAgreement);
        	tenancyForm.setAdditionalLinks(additionalLinks);
        	tenancyForm.setCouncilTaxRegistration(councilTaxRegistration);
        	tenancyForm.setCorrespondenceLink(correspondenceLinks);
        	tenancyForm.setCorrespondenceWithTenants(correspondenceWithTenants);
        	tenancyForm.setGuarantorFirstName(guarantorFirstName);
        	tenancyForm.setGuarantorLastName(guarantorLastName);
        	tenancyForm.setGuarantorAddress(guarantorAddress);
        	tenancyForm.setGuarantorEmailAddress(guarantorEmailAddress);
        	tenancyForm.setGuarantorContactNumber(guarantorContactNumber);
        	tenancyForm.setLinkToRentAccounts(linkToRentAccounts);
        	tenancyForm.setLegalNotifications(legalNotification);
        	tenancyForm.setEmployerCompany(employerCompany);
        	tenancyForm.setGuarantorCompany(guarantorCompany);
        	
        	tenancyFormService.save(tenancyForm);
        	
        	 Set<Entry<String, String>> setRoomDet=roomDet.entrySet();
             Iterator ite=setRoomDet.iterator();
             
             while(ite.hasNext()){
           	  Map.Entry<String,String> entry = (Map.Entry<String,String>)ite.next();

                 String key             = entry.getKey();
                 String value         = entry.getValue();
                 
                 List<TenantDetails> tenantDetails=tenantDetailsService.findTenantDetailsForTenancy(tenancyForm.getId());
                 
                 for(TenantDetails td:tenantDetails){
                	 td.setFirstName(tenantFirstName);
                	 td.setLastName(tenantLastName);
                	 td.setTitle(tenantTitle);
                	 td.setLandlineNumber(tenantLandlineNumber);
                	 td.setAdditionalRemarks(tenantAdditionalRemark);
                	 td.setCreditCheck(tenantCreditCheck);
                	 td.setCurrentAddress(tenantCurrentAddress);
                	 td.setDateOfBirth(tenantDateOfBirth);
                	 td.setDriversLicense(tenantDriverLicense);
                	 td.setEmailAddress(tenantEmailAddress);
                	 td.setNationalInsuranceNumber(tenantNationalInsuranceNumber);
                	 td.setPassport(tenantPassport);
                	 td.setReferenceCheck(tenantReferenceCheck);
                	 td.setReferenceCheck(tenantReferenceCheck);
                	 td.setMobileNumber(tenantMobileNumber);
                	 tenantDetailsService.save(td);
                	 
                 }
             
        }
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);

		Forms form = uf.getForms();
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
			WfModel wfModel = modelService.findModelByName(workflowName);
			
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
			String userFormIdStr = String.valueOf(uf.getId());
			SecurityGroup securityGroup=null;
			if(securityGroupId!=0){
			securityGroup=securityGroupService.findSecurityGroupById(securityGroupId);
			}
			Doctype doctype=null;
			//String formDefName= uf.getForms().getFormDefs().getName();
			//String finalFormName=uf.getTitle();
			Document newForms=new Document(doctype,null, userInfo.getUserName(),uf.getTitle(),"Forms", wfCase.getId(),new Date(),sdf.parse(submissionDate),null,'Y',userFormIdStr,'F');
			if (workflowName.equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
				//System.out.println("workFlow:::::::::::"+workFlow);
				//newForms.setCaseId(0);
				
				uploadDocumentIntoAlfrescoForForms(session,userFormId);
				newForms.setWip('N');
			}
			newForms.setSecurityGroup(securityGroup);
			documentService.save(newForms);		
			User user=userService.findUserById(userInfo.getUserId());
			
			
			FormTrail ft=new FormTrail(uf, user, "To Approve", new Date(), "Submitted", "");
			formTrailService.save(ft);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("/create Error message "+e);
			e.printStackTrace();
		}
		//model.addAttribute("result", "Form Saved & Case Created");
		return "refresh";
	}
	
	@RequestMapping(value = "/createBuilderDetail", method = RequestMethod.POST)
	public String saveCaseFormDataForBuilderDetails(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long securityGroupId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("compGrp")long compGrp,@RequestParam("wrkFlw")long modelId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        String firstName=null;
   	 String lastName=null;
   	 String emailAddress=null;
   	 String landlineNumber=null;
   	 String mobileNumber=null;
   	 String companyName=null;
   	 String companyAddress=null;
   	 String companyNumber=null;
   	 String vatNumber=null;
   	 String accountHoldersName=null;
   	 String accountNumber=null;
   	 String sortCode=null;
   	 String linkToInvoice=null;
   	 String linkToJobs=null;
   	
		
       while(it.hasNext()){
      	 
           Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

           String key             = entry.getKey();
           String[] value         = entry.getValue();
           
           System.out.println("Key is "+key);

           

               if(value.length>1){    
                   for (int i = 0; i < value.length; i++) {
                       System.out.println( value[i].toString());
                     
                   }
               }else
               {
           	System.out.println("Value is "+value[0].toString());
           	
           	 
           	 if(key.equals("firstName"))
               	 firstName=value[0].toString();
                
                else if(key.equals("lastName"))   
               	 lastName=value[0].toString();
                else if(key.equals("emailAddress"))   
               	 emailAddress=value[0].toString();
                else if(key.equals("landlineNumber"))   
               	 landlineNumber=value[0].toString();
               
                else if(key.equals("mobileNumber"))
               	 mobileNumber=value[0].toString();
                
               
                else if(key.equals("companyName")) 
               	 companyName=value[0].toString();
                else if(key.equals("companyAddress"))	   
               	 companyAddress=value[0].toString();
                else if(key.equals("companyNumber"))	   
               	 companyNumber=value[0].toString();
                else if(key.equals("vatNumber"))	   
               	 vatNumber=value[0].toString();
                else if(key.equals("accountHoldersName"))	   
               	 accountHoldersName=value[0].toString();
                else if(key.equals("accountNumber"))	   
               	 accountNumber=value[0].toString();
                else if(key.equals("sortCode"))	   
               	 sortCode=value[0].toString();
                else if(key.equals("linkToInvoice"))	   
               	 linkToInvoice=value[0].toString();
                else if(key.equals("linkToJobs"))	   
               	 linkToJobs=value[0].toString();
           	 
           	 
               
             	   
                }
       }
       UserForms uf=null;
       if(userFormId==-1){
       FormDefs fds=formDefsService.findFormDefsById(formDefId);
       Forms forms=formService.fetchFormDefinition(formDefId, -1);
       uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', securityGroupId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"BuilderDetailsForm");
       userFormsService.save(uf);
       uf.setTitle(fds.getName()+"-"+uf.getId());
       userFormsService.save(uf);
       System.out.println(fds.getName()+"-"+uf.getId());
      
     userFormId=uf.getId();
     
     BuilderDetailsForm builderDetailsForm=new BuilderDetailsForm(userFormId, firstName, lastName, emailAddress, landlineNumber, mobileNumber, companyName, companyAddress, companyNumber, vatNumber, accountHoldersName, accountNumber, sortCode, linkToInvoice, linkToJobs);
     
      builderDetailsFormService.save(builderDetailsForm);
     
       	
       }else{
       	uf=userFormsService.findUserFormsById(userFormId);
       	BuilderDetailsForm builderDetailsForm=builderDetailsFormService.findBuilderDetailsFormByUserFormId(userFormId);
       	builderDetailsForm.setFirstName(firstName);
       	builderDetailsForm.setLastName(lastName);
       	builderDetailsForm.setEmailAddress(emailAddress);
       	builderDetailsForm.setLandlineNumber(landlineNumber);
       	builderDetailsForm.setMobileNumber(mobileNumber);
       	builderDetailsForm.setCompanyName(companyName);
       	builderDetailsForm.setCompanyAddress(companyAddress);
       	builderDetailsForm.setCompanyNumber(companyNumber);
       	builderDetailsForm.setVatNumber(vatNumber);
       	builderDetailsForm.setAccountHoldersName(accountHoldersName);
       	builderDetailsForm.setAccountNumber(accountNumber);
       	builderDetailsForm.setSortCode(sortCode);
       	builderDetailsForm.setLinkToInvoice(linkToInvoice);
       	builderDetailsForm.setLinkToJobs(linkToJobs);
       	builderDetailsFormService.save(builderDetailsForm);
       	
       }
             
        
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);

		Forms form = uf.getForms();
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
			WfModel wfModel = modelService.findModelByName(workflowName);
			
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
			String userFormIdStr = String.valueOf(uf.getId());
			SecurityGroup securityGroup=null;
			if(securityGroupId!=0){
			securityGroup=securityGroupService.findSecurityGroupById(securityGroupId);
			}
			Doctype doctype=null;
			//String formDefName= uf.getForms().getFormDefs().getName();
			//String finalFormName=uf.getTitle();
			Document newForms=new Document(doctype,null, userInfo.getUserName(),uf.getTitle(),"Forms", wfCase.getId(),new Date(),sdf.parse(submissionDate),null,'Y',userFormIdStr,'F');
			if (workflowName.equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
				//System.out.println("workFlow:::::::::::"+workFlow);
				//newForms.setCaseId(0);
				
				uploadDocumentIntoAlfrescoForForms(session,userFormId);
				newForms.setWip('N');
			}
			newForms.setSecurityGroup(securityGroup);
			documentService.save(newForms);		
			User user=userService.findUserById(userInfo.getUserId());
			
			
			FormTrail ft=new FormTrail(uf, user, "To Approve", new Date(), "Submitted", "");
			formTrailService.save(ft);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("/create Error message "+e);
			e.printStackTrace();
		}
		//model.addAttribute("result", "Form Saved & Case Created");
		return "refresh";
	}

	@RequestMapping(value = "/createUtilitiesCompanyDetails", method = RequestMethod.POST)
	public String saveCaseFormDataForUtilitiesCompanyDeatils(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long securityGroupId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("compGrp")long compGrp,@RequestParam("wrkFlw")long modelId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        String companyName=null;
   	 String companyType=null;
   	 String companyTelephone=null;
   	 String companyAddress=null;
   	 String openingAndClosingTimes=null;
   	 String mainContactName=null;
   	 String mainContactTelephone=null;
   	 String mainContactEmailAddress=null;
   	
		
       while(it.hasNext()){
      	 
           Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

           String key             = entry.getKey();
           String[] value         = entry.getValue();
           
           System.out.println("Key is "+key);

           

               if(value.length>1){    
                   for (int i = 0; i < value.length; i++) {
                       System.out.println( value[i].toString());
                     
                   }
               }else
               {
           	System.out.println("Value is "+value[0].toString());
           	
           	 
           	 if(key.equals("companyName"))
           		 companyName=value[0].toString();
                
                else if(key.equals("companyType"))   
               	 companyType=value[0].toString();
                else if(key.equals("companyTelephone"))   
               	 companyTelephone=value[0].toString();
                else if(key.equals("companyAddress"))   
               	 companyAddress=value[0].toString();
               
                else if(key.equals("openingAndClosingTimes"))
               	 openingAndClosingTimes=value[0].toString();
                
               
                else if(key.equals("mainContactName")) 
               	 mainContactName=value[0].toString();
               
                else if(key.equals("mainContactTelephone"))	   
               	 mainContactTelephone=value[0].toString();
                else if(key.equals("mainContactEmailAddress"))	   
               	 mainContactEmailAddress=value[0].toString();
               
           	 
           	 
               
             	   
                }
       }
       UserForms uf=null;
       if(userFormId==-1){
       FormDefs fds=formDefsService.findFormDefsById(formDefId);
       Forms forms=formService.fetchFormDefinition(formDefId, -1);
       uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', securityGroupId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"UtilitiesCompanyDetailsForm");
       userFormsService.save(uf);
       uf.setTitle(fds.getName()+"-"+uf.getId());
       userFormsService.save(uf);
       System.out.println(fds.getName()+"-"+uf.getId());
      
     userFormId=uf.getId();
     
    UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=new UtilitiesCompanyDetailsForm(userFormId, companyName, companyType, companyTelephone, companyAddress, openingAndClosingTimes, mainContactName, mainContactTelephone, mainContactEmailAddress);
     utilitiesCompanyDetailsFormService.save(utilitiesCompanyDetailsForm);
       	
       }else{
       	uf=userFormsService.findUserFormsById(userFormId);
       	UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=utilitiesCompanyDetailsFormService.findUtilitiesCompanyDetailsFormByUserFormId(userFormId);
       	utilitiesCompanyDetailsForm.setCompanyName(companyName);
       	utilitiesCompanyDetailsForm.setCompanyAddress(companyAddress);
       	utilitiesCompanyDetailsForm.setCompanyType(companyType);
       	utilitiesCompanyDetailsForm.setCompanyTelephone(companyTelephone);
       	utilitiesCompanyDetailsForm.setOpeningAndClosingTimes(openingAndClosingTimes);
       	utilitiesCompanyDetailsForm.setMainContactName(mainContactName);
       	utilitiesCompanyDetailsForm.setMainContactTelephone(mainContactTelephone);
       	utilitiesCompanyDetailsForm.setMainContactEmailAddress(mainContactEmailAddress);
       	utilitiesCompanyDetailsFormService.save(utilitiesCompanyDetailsForm);
       	
       }
             
        
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);

		Forms form = uf.getForms();
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
			WfModel wfModel = modelService.findModelByName(workflowName);
			
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
			String userFormIdStr = String.valueOf(uf.getId());
			SecurityGroup securityGroup=null;
			if(securityGroupId!=0){
			securityGroup=securityGroupService.findSecurityGroupById(securityGroupId);
			}
			Doctype doctype=null;
			//String formDefName= uf.getForms().getFormDefs().getName();
			//String finalFormName=uf.getTitle();
			Document newForms=new Document(doctype,null, userInfo.getUserName(),uf.getTitle(),"Forms", wfCase.getId(),new Date(),sdf.parse(submissionDate),null,'Y',userFormIdStr,'F');
			if (workflowName.equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
				//System.out.println("workFlow:::::::::::"+workFlow);
				//newForms.setCaseId(0);
				
				uploadDocumentIntoAlfrescoForForms(session,userFormId);
				newForms.setWip('N');
			}
			newForms.setSecurityGroup(securityGroup);
			documentService.save(newForms);		
			User user=userService.findUserById(userInfo.getUserId());
			
			
			FormTrail ft=new FormTrail(uf, user, "To Approve", new Date(), "Submitted", "");
			formTrailService.save(ft);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("/create Error message "+e);
			e.printStackTrace();
		}
		//model.addAttribute("result", "Form Saved & Case Created");
		return "refresh";
	}

	
	@RequestMapping(value = "/savePotentialTenantForDraft", method = RequestMethod.POST)
	public String savePotentialTenantFormToDraft(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long secGrpId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("wrkFlw")long modelId,@RequestParam("compGrp")long compGrp,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
         String title=null;
    	 String firstName = null;
    	 String lastName=null;
    	 String landLineNumber=null;
    	 String mobileNumber=null;
    	 String emailAddress=null;
    	 String nationalInsuranceNumber=null;
    	 String currentAddress=null;
    	 String type=null;
    	 long numberOfBedRooms = 0;
    	 String garden = null;
    	 String offRoadParking=null;
    	 String garage=null;
    	 String other=null;
        
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            
            System.out.println("Key is "+key);

            

                if(value.length>1){    
                    for (int i = 0; i < value.length; i++) {
                        System.out.println( value[i].toString());
                      
                    }
                }else
                {
            	System.out.println("Value is "+value[0].toString());
            	
            	 if(key.equals("title"))
              	   title=value[0].toString();
                 else if(key.equals("firstName"))
              	   firstName=value[0].toString();
                 else if(key.equals("lastName"))
              	   lastName=value[0].toString();
                 else if(key.equals("mobileNumber"))   
              	   mobileNumber=value[0].toString();
                 else if(key.equals("landLineNumber"))
              	   landLineNumber=value[0].toString();
                 else if(key.equals("emailAddress"))	   
              	   emailAddress=value[0].toString();
                 else if(key.equals("nationalInsuranceNumber"))
              	   nationalInsuranceNumber=value[0].toString();
                 else if(key.equals("currentAddress"))
              	   currentAddress=value[0].toString();
                 else if(key.equals("type")) 
              	   type=value[0].toString();
              	   else if(key.equals("numberOfBedRooms")){
              		   if(value[0].toString()!="")
              		   numberOfBedRooms=Long.parseLong(value[0].toString());
              	   }
              		   else if(key.equals("garden"))
              			   garden=value[0].toString();
              			   else if(key.equals("offRoadParking"))
              				   offRoadParking=value[0].toString();
              				   else if(key.equals("garage"))
              					   garage=value[0].toString();
              					   else if(key.equals("other"))
              						   other=value[0].toString();
                        
                }
        }
        
        
        
        
        if(userFormId==-1){
        	FormDefs fds=formDefsService.findFormDefsById(formDefId);
            Forms forms=formService.fetchFormDefinition(formDefId, -1);
            
        UserForms uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', secGrpId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"PotentialTenantForm");
       
        userFormsService.save(uf);
        uf.setTitle(fds.getName()+"-"+uf.getId());
        userFormsService.save(uf);
        
        PotentialTenantForm potentialTenantForm=new PotentialTenantForm(uf.getId(),title, firstName, lastName, landLineNumber, mobileNumber, emailAddress, nationalInsuranceNumber, currentAddress, type, numberOfBedRooms, garden, offRoadParking, garage, other);
       // potentialTenantForm.setEmailAddress(emailAddress);
        potentialTenantFormService.save(potentialTenantForm);
        
        UserFormRef userFormRef=new UserFormRef(uf, "PotentialTenantForm");
        userFormRefService.save(userFormRef);
        }
        else{
        	PotentialTenantForm ptf=potentialTenantFormService.findPotentialTenantFormByUserFormId(userFormId);
        	ptf.setTitle(title);
        	ptf.setFirstName(firstName);
        	ptf.setLastName(lastName);
        	ptf.setLandLineNumber(landLineNumber);
        	ptf.setMobileNumber(mobileNumber);
        	ptf.setEmailAddress(emailAddress);
        	ptf.setNationalInsuranceNumber(nationalInsuranceNumber);
        	ptf.setCurrentAddress(currentAddress);
        	ptf.setType(type);
        	ptf.setNumberOfBedRooms(numberOfBedRooms);
        	ptf.setGarden(garden);
        	ptf.setOffRoadParking(offRoadParking);
        	ptf.setGarage(garage);
        	ptf.setOther(other);
        	 potentialTenantFormService.save(ptf);
        }
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);
		
		return "refresh";
	}
	
	@RequestMapping(value = "/savePropertyDetailsFormForDraft", method = RequestMethod.POST)
	public String saveProprtyDetailsFormToDraft(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long secGrpId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("wrkFlw")long modelId,@RequestParam("compGrp")long compGrp,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        
    	
    	
        
    	 String client=null;
    	
    	 String firstName=null;
    	 String lastName=null;
    	 String landlineNumber=null;
    	 String mobileNumber=null;
    	 String emailAddress=null;
    	 String address=null;
    	// String accountDetails=null;
    	 String accountNumber=null;
    	 String accountHoldersName=null;
    	 String sortCode=null;
    	 String vatDetails=null;
    	 String referencesForPayment=null;
    	 String propertyAddressLine1=null;
    	 String propertyAddressLine2=null;
    	 String town=null;
    	 String cityCountry=null;
    	 String propertyPostCode=null;
    	 String propertyType=null;
    	 String propertyDescription=null;
    	 String houseMeasurements=null;
    	 String gasMeterLocation=null;
    	 String electricMeterLocation=null;
    	 String waterMeterLocation=null;
    	 Date  dateOfPerchase=null;
    	 String priceOfPurchase=null;
    	 String estimatedValue=null;
    	 Date asOfDate=null;
    	 String annualRent=null;
    	 String pictures=null;
    	 String localAuthority=null;
    	 String rentalType=null;
    	 String frontDoorKeyCode=null;
    	 String backDoorKeyCode=null;
    	 String porchDoorKeyCode=null;
    	 String flatDoor=null;
    	 String others=null;
    	 long numberOfBedrooms=0;
    	 String bedroomDoor=null;
    	 String bedroomWindow=null;
    	 String tenancyAgreement=null;
    	 String insuranceCertificate=null;
    	 String floorPlan=null;
    	 String epcCertificate=null;
    	 String gasCertificate=null;
    	 String electricCertificate=null;
    	 String hmoLicence=null;
    	 String contractsAndWarranties=null;
    	 String landRegistry=null;
    	 String currentTenancyForm=null;
    	 String propertyTimeline=null;
    	 String linkToJobs=null;
    	 String lendingInformation=null;
    	 String managementCompany=null;
    	 String companyName=null;
        
       HashMap<String , String> roomDet=new HashMap<String,String>();
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            
            System.out.println("Key is "+key);

            

                if(value.length>1){    
                    for (int i = 0; i < value.length; i++) {
                        System.out.println( value[i].toString());
                      
                    }
                }else
                {
            	System.out.println("Value is "+value[0].toString());
            	
            	 if(key.equals("client"))
              	   client=value[0].toString();
                 else if(key.equals("firstName"))
              	   firstName=value[0].toString();
                 else if(key.equals("lastName"))
              	   lastName=value[0].toString();
                 else if(key.equals("landlineNumber"))   
                	   landlineNumber=value[0].toString();
                 else if(key.equals("mobileNumber"))   
              	   mobileNumber=value[0].toString();
                 else if(key.equals("landLineNumber"))
              	   landlineNumber=value[0].toString();
                 else if(key.equals("emailAddress"))	   
              	   emailAddress=value[0].toString();
                 else if(key.equals("address"))
              	   address=value[0].toString();
                 else if(key.equals("accountNumber"))
                	 accountNumber=value[0].toString();
                 else if(key.equals("accountHoldersName")) 
                	 accountHoldersName=value[0].toString();
              	   else if(key.equals("sortCode"))
              		 sortCode=value[0].toString();
              		   else if(key.equals("vatDetails"))
              			 vatDetails=value[0].toString();
              			   else if(key.equals("referencesForPayment"))
              				 referencesForPayment=value[0].toString();
              				   else if(key.equals("propertyAddressLine1"))
              					 propertyAddressLine1=value[0].toString();
              					   else if(key.equals("propertyAddressLine2"))
              						 propertyAddressLine2=value[0].toString();
              					 else if(key.equals("town"))
              						town=value[0].toString();
              					else if(key.equals("cityCountry"))
              						cityCountry=value[0].toString();
              					else if(key.equals("propertyPostCode"))
              						propertyPostCode=value[0].toString();
              					else if(key.equals("propertyType"))
              						propertyType=value[0].toString();
              					else if(key.equals("propertyDescription"))
              						propertyDescription=value[0].toString();
              					else if(key.equals("houseMeasurements"))
              						houseMeasurements=value[0].toString();
              					else if(key.equals("gasMeterLocation"))
              						gasMeterLocation=value[0].toString();
              					else if(key.equals("electricMeterLocation"))
             						 electricMeterLocation=value[0].toString();
              					else if(key.equals("waterMeterLocation"))
             						 waterMeterLocation=value[0].toString();
              					else if(key.equals("dateOfPerchase")){
              						if(value[0].toString()!="")
             						 dateOfPerchase=sdf.parse(value[0].toString());
              					}
              					else if(key.equals("priceOfPurchase"))
             						 priceOfPurchase=value[0].toString();
              					else if(key.equals("estimatedValue"))
             						 estimatedValue=value[0].toString();
              					else if(key.equals("asOfDate"))
              					{
              						if(value[0].toString()!="")
             						 asOfDate=sdf.parse(value[0].toString());
              					}
              					else if(key.equals("annualRent"))
             						 annualRent=value[0].toString();
              					else if(key.equals("pictures"))
             						 pictures=value[0].toString();
              					else if(key.equals("localAuthority"))
             						 localAuthority=value[0].toString();
              					else if(key.equals("rentalType"))
             						 rentalType=value[0].toString();
              					else if(key.equals("frontDoorKeyCode"))
            						 frontDoorKeyCode=value[0].toString();
              					else if(key.equals("backDoorKeyCode"))
            						 backDoorKeyCode=value[0].toString();
              					else if(key.equals("porchDoorKeyCode"))
            						 porchDoorKeyCode=value[0].toString();
              					else if(key.equals("flatDoor"))
            						 flatDoor=value[0].toString();
              					else if(key.equals("others"))
            						 others=value[0].toString();
              					else if(key.equals("numberOfBedrooms")){
              						if(value[0].toString()!="")
            						 numberOfBedrooms=Long.parseLong(value[0].toString());
              					}
              					else if(key.equals("tenancyAgreement"))
            						 tenancyAgreement=value[0].toString();
              					else if(key.equals("insuranceCertificate"))
            						 insuranceCertificate=value[0].toString();
              					else if(key.equals("floorPlan"))
            						 floorPlan=value[0].toString();
              					else if(key.equals("epcCertificate"))
            						 epcCertificate=value[0].toString();
              					else if(key.equals("gasCertificate"))
            						 gasCertificate=value[0].toString();
              					else if(key.equals("electricCertificate"))
            						 electricCertificate=value[0].toString();
              					else if(key.equals("hmoLicence"))
            						 hmoLicence=value[0].toString();
              					else if(key.equals("contractsAndWarranties"))
            						 contractsAndWarranties=value[0].toString();
              					else if(key.equals("landRegistry"))
            						 landRegistry=value[0].toString();
              					else if(key.equals("currentTenancyForm"))
            						 currentTenancyForm=value[0].toString();
              					else if(key.equals("propertyTimeline"))
            						 propertyTimeline=value[0].toString();
              					else if(key.equals("linkToJobs"))
            						 linkToJobs=value[0].toString();
              					else if(key.equals("lendingInformation"))
            						 lendingInformation=value[0].toString();
              					else if(key.equals("managementCompany"))
              						managementCompany=value[0].toString();
              					else if(key.equals("companyName"))
              						companyName=value[0].toString();
              					else{
              						if(!key.equals("formDefId") && !key.equals("secGrp") && !key.equals("submissionDate") && !key.equals("wrkFlw") && !key.equals("compGrp")){
              							System.out.println(value[0].toString());
              							roomDet.put(key, value[0].toString());
              						}
              						if(key.startsWith("bedroom")){
              							System.out.println(value[0].toString());
              							roomDet.put(key, value[0].toString());
              						}
              					}
              					
            	 
                        
                }
        }
        
        if(userFormId==-1){
        FormDefs fds=formDefsService.findFormDefsById(formDefId);
        Forms forms=formService.fetchFormDefinition(formDefId, -1);
        UserForms uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', secGrpId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"PropertyDetailsForm");
        userFormsService.save(uf);
        uf.setTitle(fds.getName()+"-"+uf.getId());
        userFormsService.save(uf);
        System.out.println(fds.getName()+"-"+uf.getId());
       
        
       PropertyDetailsForm propertyDetailsForm=new PropertyDetailsForm(uf.getId(), client, firstName, lastName, landlineNumber, mobileNumber, emailAddress, address, accountNumber, accountHoldersName, sortCode, vatDetails, referencesForPayment, propertyAddressLine1, propertyAddressLine2, town, cityCountry, propertyPostCode, propertyType, propertyDescription, houseMeasurements, gasMeterLocation, electricMeterLocation, waterMeterLocation, dateOfPerchase, priceOfPurchase, estimatedValue, asOfDate, annualRent, pictures, localAuthority, rentalType, frontDoorKeyCode, backDoorKeyCode, porchDoorKeyCode, flatDoor, others, numberOfBedrooms, tenancyAgreement, insuranceCertificate, floorPlan, epcCertificate, gasCertificate, electricCertificate, hmoLicence, contractsAndWarranties, landRegistry, currentTenancyForm, propertyTimeline, linkToJobs, lendingInformation,managementCompany,companyName);
       propertyDetailsFormService.save(propertyDetailsForm);
       
      Set<Entry<String, String>> setRoomDet=roomDet.entrySet();
      Iterator ite=setRoomDet.iterator();
      
      while(ite.hasNext()){
    	  Map.Entry<String,String> entry = (Map.Entry<String,String>)ite.next();

          String key             = entry.getKey();
          String value         = entry.getValue();
          
          RoomDetails roomDetails=new RoomDetails(propertyDetailsForm, key, value);
          roomDetailsService.save(roomDetails);
      }
       
       
       
        UserFormRef userFormRef=new UserFormRef(uf, "PropertyDetailsForm");
        userFormRefService.save(userFormRef);
        
        }else{
        	PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormByUserFormId(userFormId);
        	pdf.setClient(client);
        	pdf.setFirstName(firstName);
        	pdf.setLastName(lastName);
        	pdf.setLandlineNumber(landlineNumber);
        	pdf.setMobileNumber(mobileNumber);
        	pdf.setEmailAddress(emailAddress);
        	pdf.setAddress(address);
        	pdf.setAccountNumber(accountNumber);
        	pdf.setAccountHoldersName(accountHoldersName);
        	pdf.setSortCode(sortCode);
        	pdf.setVatDetails(vatDetails);
        	pdf.setReferencesForPayment(referencesForPayment);
        	pdf.setPropertyAddressLine1(propertyAddressLine1);
        	pdf.setPropertyAddressLine2(propertyAddressLine2);
        	pdf.setTown(town);
        	pdf.setCityCountry(cityCountry);
        	pdf.setPropertyPostCode(propertyPostCode);
        	pdf.setPropertyType(propertyType);
        	pdf.setPropertyDescription(propertyDescription);
        	pdf.setHouseMeasurements(houseMeasurements);
        	pdf.setGasMeterLocation(gasMeterLocation);
        	pdf.setElectricMeterLocation(electricMeterLocation);
        	pdf.setWaterMeterLocation(waterMeterLocation);
        	pdf.setDateOfPerchase(dateOfPerchase);
        	pdf.setPriceOfPurchase(priceOfPurchase);
        	pdf.setEstimatedValue(estimatedValue);
        	pdf.setAsOfDate(asOfDate);
        	pdf.setAnnualRent(annualRent);
        	pdf.setPictures(pictures);
        	pdf.setLocalAuthority(localAuthority);
        	pdf.setRentalType(rentalType);
        	pdf.setFrontDoorKeyCode(frontDoorKeyCode);
        	pdf.setBackDoorKeyCode(backDoorKeyCode);
        	pdf.setPorchDoorKeyCode(porchDoorKeyCode);
        	pdf.setFlatDoor(flatDoor);
        	pdf.setOthers(others);
        	pdf.setNumberOfBedrooms(numberOfBedrooms);
        	pdf.setTenancyAgreement(tenancyAgreement);
        	pdf.setInsuranceCertificate(insuranceCertificate);
        	pdf.setFloorPlan(floorPlan);
        	pdf.setEpcCertificate(epcCertificate);
        	pdf.setGasCertificate(gasCertificate);
        	pdf.setElectricCertificate(electricCertificate);
        	pdf.setHmoLicence(hmoLicence);
        	pdf.setContractsAndWarranties(contractsAndWarranties);
        	pdf.setLandRegistry(landRegistry);
        	pdf.setCurrentTenancyForm(currentTenancyForm);
        	pdf.setPropertyTimeline(propertyTimeline);
        	pdf.setLinkToJobs(linkToJobs);
        	pdf.setLendingInformation(lendingInformation);
        	pdf.setManagementCompany(managementCompany);
        	pdf.setCompanyName(companyName);
        	
        	propertyDetailsFormService.save(pdf);
        	
        	 List<RoomDetails> roomDetails=roomDetailsService.findRoomDetailsByPropertyDetailsFormId(pdf);
             roomDetailsService.delete(roomDetails);
        	
        	 Set<Entry<String, String>> setRoomDet=roomDet.entrySet();
             Iterator ite=setRoomDet.iterator();
             
             while(ite.hasNext()){
           	  Map.Entry<String,String> entry = (Map.Entry<String,String>)ite.next();

                 String key             = entry.getKey();
                 String value         = entry.getValue();
                 
                 
               RoomDetails roomDetail=new RoomDetails(pdf, key, value);
               roomDetailsService.save(roomDetail);
                 
             }
        	
        }
        
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);
		
		return "refresh";
	}
	
	@RequestMapping(value = "/saveStandardProductForDraft", method = RequestMethod.POST)
	public String saveStandardProductFormToDraft(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long secGrpId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("wrkFlw")long modelId,@RequestParam("compGrp")long compGrp,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        String productName=null;
   	 String productType = null;
   	 String productCode=null;
   	 String productDescription=null;
   	 String sellingPricePerUnit=null;
   	 String picture1=null;
   	 String picture2=null;
   	 String picture3=null;
   	 String picture4=null;
   	 String picture5 = null;
   	 String stockLevel = null;
   	 String agentDistributionOfStock=null;
   	 long stockLevelNewSupplierOrderAlert=0;
        
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            
            System.out.println("Key is "+key);

            

                if(value.length>1){    
                    for (int i = 0; i < value.length; i++) {
                        System.out.println( value[i].toString());
                      
                    }
                }else
                {
            	System.out.println("Value is "+value[0].toString());
            	
            	 if(key.equals("productName"))
            		 productName=value[0].toString();
                 else if(key.equals("productType"))
                	 productType=value[0].toString();
                 else if(key.equals("productCode"))
                	 productCode=value[0].toString();
                 else if(key.equals("productDescription"))   
                	 productDescription=value[0].toString();
                 else if(key.equals("sellingPricePerUnit"))
                	 sellingPricePerUnit=value[0].toString();
                 else if(key.equals("picture1"))	   
                	 picture1=value[0].toString();
                 else if(key.equals("picture2"))
                	 picture2=value[0].toString();
                 else if(key.equals("picture3"))
                	 picture3=value[0].toString();
                 else if(key.equals("picture4")) 
                	 picture4=value[0].toString();
              	   else if(key.equals("stockLevelNewSupplierOrderAlert")){
              		   if(value[0].toString()!="")
              			 stockLevelNewSupplierOrderAlert=Long.parseLong(value[0].toString());
              	   }
              		   else if(key.equals("picture5"))
              			 picture5=value[0].toString();
              			   else if(key.equals("stockLevels"))
              				 stockLevel=value[0].toString();
              				   else if(key.equals("agentDistributionOfStock"))
              					 agentDistributionOfStock=value[0].toString();
              					   
                }
        }
        
        
        
        
        if(userFormId==-1){
        	FormDefs fds=formDefsService.findFormDefsById(formDefId);
            Forms forms=formService.fetchFormDefinition(formDefId, -1);
            
        UserForms uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', secGrpId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"StandardProductForm");
       
        userFormsService.save(uf);
        uf.setTitle(fds.getName()+"-"+uf.getId());
        userFormsService.save(uf);
        
        
        StandardProductForm standardProductForm=new StandardProductForm(uf.getId(),productName, productType, productCode, productDescription, sellingPricePerUnit, picture1, picture2, picture3, picture4, picture5, stockLevel, agentDistributionOfStock, stockLevelNewSupplierOrderAlert);
        standardProductFormService.save(standardProductForm);
        UserFormRef userFormRef=new UserFormRef(uf, "PotentialTenantForm");
        userFormRefService.save(userFormRef);
        }
        else{
        	StandardProductForm standardProductForm=standardProductFormService.findStandardProductFormByUserFormId(userFormId);
        	standardProductForm.setUserFormId(userFormId);
        	standardProductForm.setProductName(productName);
        	standardProductForm.setProductType(productType);
        	standardProductForm.setProductCode(productCode);
        	standardProductForm.setProductDescription(productDescription);
        	standardProductForm.setSellingPricePerUnit(sellingPricePerUnit);
        	standardProductForm.setPicture1(picture1);
        	standardProductForm.setPicture2(picture2);
        	standardProductForm.setPicture3(picture3);
        	standardProductForm.setPicture4(picture4);
        	standardProductForm.setPicture5(picture5);
        	standardProductForm.setStockLevel(stockLevel);
        	standardProductForm.setAgentDistributionOfStock(agentDistributionOfStock);
        	standardProductForm.setStockLevelNewSupplierOrderAlert(stockLevelNewSupplierOrderAlert);
        	standardProductFormService.save(standardProductForm);
        }
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);
		
		return "refresh";
	}
	
	@RequestMapping(value = "/saveTenancyFormForDraft", method = RequestMethod.POST)
	public String saveTenancyFormToDraft(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long secGrpId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("wrkFlw")long modelId,@RequestParam("compGrp")long compGrp,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        
    	
    	
        
        String propertyAddress=null;
		String typeOfRental=null;
		long roomNumber=0;
		String landLordFirstName=null;
		String landLordLastName=null;
		String landLordAddress=null;
		String landLordEmailAddress=null;
		String landLordContactNumber=null;
		String employerFirstName=null;
		String employerLastName=null;
		String employerAddress=null;
		String employerEmailAddress=null;
		String employerContactNumber=null;
		String guarantorFirstName=null;
		String guarantorLastName=null;
		String guarantorAddress=null;
		String guarantorEmailAddress=null;
		String guarantorContactNumber=null;
		String kinFirstName=null;
		String kinLastName=null;
		String kinAddress=null;
		String kinEmailAddress=null;
		String kinContactNumber=null;
		Date tenancyStartedDate=null;
		String tenancyOpenedBy=null;
		String tenancyClosedBy=null;
		Date tenancyClosedDate=null;
		String checkingOutForm=null;
		String linkToProperty=null;
		String linkToTenancyAgreement=null;
		String linkToRentAccounts=null;
		String legalNotification=null;
		String tenancyDepositCertificate=null;
		String councilTaxRegistration=null;
		String additionalLinks=null;
		String correspondenceWithTenants=null;
		String correspondenceLinks=null;
		String employerCompany=null;
		String guarantorCompany=null;
		
		String tenantFirstName=null;
		String tenantTitle=null;
		String tenantLastName=null;
		Date tenantDateOfBirth=null;
		String tenantLandlineNumber=null;
		String tenantMobileNumber=null;
		String tenantEmailAddress=null;
		String tenantNationalInsuranceNumber=null;
		String tenantCurrentAddress=null;
		String tenantPassport=null;
		String tenantDriverLicense=null;
		String tenantCreditCheck=null;
		String tenantReferenceCheck=null;
		String tenantAdditionalRemark=null;
		
				
		
		
        
       HashMap<String , String> roomDet=new HashMap<String,String>();
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            
            System.out.println("Key is "+key);

            

                if(value.length>1){    
                    for (int i = 0; i < value.length; i++) {
                        System.out.println( value[i].toString());
                      
                    }
                }else
                {
            	System.out.println("Value is "+value[0].toString());
            	
            	 if(key.equals("propertyAddress"))
            		 propertyAddress=value[0].toString();
                 else if(key.equals("typeOfRental"))
                	 typeOfRental=value[0].toString();
                 else if(key.equals("roomNumber")){
                	 if(value[0].toString()!="")
                	 roomNumber=Long.parseLong(value[0].toString());
                 }
                 else if(key.equals("landLordFirstName"))   
                	 landLordFirstName=value[0].toString();
                 else if(key.equals("landLordLastName"))   
                	 landLordLastName=value[0].toString();
                 else if(key.equals("landLordAddress"))
                	 landLordAddress=value[0].toString();
                 else if(key.equals("landLordEmailAddress"))	   
                	 landLordEmailAddress=value[0].toString();
                 else if(key.equals("landLordContactNumber"))
                	 landLordContactNumber=value[0].toString();
                 else if(key.equals("employerFirstName"))
                	 employerFirstName=value[0].toString();
                 else if(key.equals("employerLastName")) 
                	 employerLastName=value[0].toString();
              	   else if(key.equals("employerAddress"))
              		 employerAddress=value[0].toString();
              		   else if(key.equals("employerEmailAddress"))
              			 employerEmailAddress=value[0].toString();
              		 else if(key.equals("employerContactNumber"))
              			 employerContactNumber=value[0].toString();
              		  else if(key.equals("guarantorFirstName"))
              			guarantorFirstName=value[0].toString();
                      else if(key.equals("guarantorLastName")) 
                    	  guarantorLastName=value[0].toString();
                   	   else if(key.equals("guarantorAddress"))
                   		guarantorAddress=value[0].toString();
                   		   else if(key.equals("guarantorEmailAddress"))
                   			guarantorEmailAddress=value[0].toString();
              			   else if(key.equals("guarantorContactNumber"))
              				 guarantorContactNumber=value[0].toString();
              				   else if(key.equals("kinFirstName"))
              					 kinFirstName=value[0].toString();
              					   else if(key.equals("kinLastName"))
              						 kinLastName=value[0].toString();
              					 else if(key.equals("kinAddress"))
              						kinAddress=value[0].toString();
              					else if(key.equals("kinEmailAddress"))
              						kinEmailAddress=value[0].toString();
              					else if(key.equals("kinContactNumber"))
              						kinContactNumber=value[0].toString();
              					else if(key.equals("tenancyOpenedBy"))
              						tenancyOpenedBy=value[0].toString();
              					else if(key.equals("tenancyClosedBy"))
              						tenancyClosedBy=value[0].toString();
              					else if(key.equals("checkingOutForm"))
              						checkingOutForm=value[0].toString();
              					else if(key.equals("linkToProperty"))
              						linkToProperty=value[0].toString();
              					else if(key.equals("linkToTenancyAgreement"))
              						linkToTenancyAgreement=value[0].toString();
              					else if(key.equals("linkToRentAccounts"))
              						linkToRentAccounts=value[0].toString();
              					else if(key.equals("tenancyStartedDate")){
              						if(value[0].toString()!="")
              							tenancyStartedDate=sdf.parse(value[0].toString());
              					}
              					else if(key.equals("legalNotification"))
              						legalNotification=value[0].toString();
              					else if(key.equals("tenancyDepositCertificate"))
              						tenancyDepositCertificate=value[0].toString();
              					else if(key.equals("tenancyClosedDate"))
              					{
              						if(value[0].toString()!="")
              							tenancyClosedDate=sdf.parse(value[0].toString());
              					}
              					else if(key.equals("councilTaxRegistration"))
              						councilTaxRegistration=value[0].toString();
              					else if(key.equals("additionalLinks"))
              						additionalLinks=value[0].toString();
              					else if(key.equals("correspondenceWithTenants"))
              						correspondenceWithTenants=value[0].toString();
              					else if(key.equals("correspondenceLinks"))
              						correspondenceLinks=value[0].toString();
              					else if(key.equals("employerCompany"))
              						employerCompany=value[0].toString();
              					else if(key.equals("guarantorCompany"))
              						guarantorCompany=value[0].toString();
              					
            	 
              					else if(key.equals("tenantFirstName"))
              						tenantFirstName=value[0].toString();
              					else if(key.equals("tenantTitle"))
              						tenantTitle=value[0].toString();
              					else if(key.equals("tenantLastName"))
              						tenantLastName=value[0].toString();
              					else if(key.equals("tenantCreditCheck"))
              						tenantCreditCheck=value[0].toString();
              					else if(key.equals("tenantEmailAddress"))
              						tenantEmailAddress=value[0].toString();
              					else if(key.equals("tenantDateOfBirth")){
              						if(value[0].toString()!=null && value[0].toString()!="")
              						tenantDateOfBirth=sdf.parse(value[0].toString());
              					}
              					else if(key.equals("tenantLandlineNumber"))
              						tenantLandlineNumber=value[0].toString();
              					else if(key.equals("tenantNationalInsuranceNumber"))
              						tenantNationalInsuranceNumber=value[0].toString();
              					else if(key.equals("tenantCurrentAddress"))
              						tenantCurrentAddress=value[0].toString();
              					else if(key.equals("tenantPassport"))
              						tenantPassport=value[0].toString();
              					else if(key.equals("tenantDriverLicense"))
              						tenantDriverLicense=value[0].toString();
              					else if(key.equals("tenantReferenceCheck"))
              						tenantReferenceCheck=value[0].toString();
              					else if(key.equals("tenantAdditionalRemark"))
              						tenantAdditionalRemark=value[0].toString();
              					else if(key.equals("tenantMobileNumber"))
              						tenantMobileNumber=value[0].toString();
              					
              					
                }
        }
        
        if(userFormId==-1){
        FormDefs fds=formDefsService.findFormDefsById(formDefId);
        Forms forms=formService.fetchFormDefinition(formDefId, -1);
        UserForms uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', secGrpId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"TenancyForm");
        userFormsService.save(uf);
        uf.setTitle(fds.getName()+"-"+uf.getId());
        userFormsService.save(uf);
        System.out.println(fds.getName()+"-"+uf.getId());
       
        TenancyForm tenancyForm=new TenancyForm(uf.getId(), propertyAddress, typeOfRental, roomNumber, landLordFirstName, landLordLastName, landLordAddress, landLordEmailAddress, landLordContactNumber, employerFirstName, employerLastName, employerAddress, employerEmailAddress, employerContactNumber, guarantorFirstName, guarantorLastName, guarantorAddress, guarantorEmailAddress, guarantorContactNumber, kinFirstName, kinLastName, kinAddress, kinEmailAddress, kinContactNumber, tenancyOpenedBy, tenancyStartedDate, tenancyClosedBy, tenancyClosedDate, checkingOutForm, linkToProperty, linkToTenancyAgreement, linkToRentAccounts, legalNotification, tenancyDepositCertificate, councilTaxRegistration, additionalLinks, correspondenceWithTenants, correspondenceLinks,employerCompany,guarantorCompany);
     
       tenancyFormService.save(tenancyForm);
      Set<Entry<String, String>> setRoomDet=roomDet.entrySet();
      Iterator ite=setRoomDet.iterator();
      
      while(ite.hasNext()){
    	  Map.Entry<String,String> entry = (Map.Entry<String,String>)ite.next();

          String key             = entry.getKey();
          String value         = entry.getValue();
          TenantDetails tenantDetails=new TenantDetails(tenancyForm.getId(), tenantTitle, tenantFirstName, tenantLastName, tenantDateOfBirth, tenantLandlineNumber, tenantMobileNumber, tenantEmailAddress, tenantNationalInsuranceNumber, tenantCurrentAddress, tenantPassport, tenantDriverLicense, tenantCreditCheck, tenantReferenceCheck, tenantAdditionalRemark);
         tenantDetailsService.save(tenantDetails);
      //}
       
        
        }else{
        	TenancyForm tenancyForm=tenancyFormService.findTenancyFormByUserFormId(userFormId);
        	tenancyForm.setPropertyAddress(propertyAddress);
        	tenancyForm.setTypeOfRental(typeOfRental);
        	tenancyForm.setRoomNumber(roomNumber);
        	tenancyForm.setLandLordFirstName(landLordFirstName);
        	tenancyForm.setLandLordLastName(landLordLastName);
        	tenancyForm.setLandLordAddress(landLordAddress);
        	tenancyForm.setLandLordEmailAddress(landLordEmailAddress);
        	tenancyForm.setLandLordContactNumber(landLordContactNumber);
        	tenancyForm.setEmployerFirstName(employerFirstName);
        	tenancyForm.setEmployerLastName(employerLastName);
        	tenancyForm.setEmployerAddress(employerAddress);
        	tenancyForm.setEmployerEmailAddress(employerEmailAddress);
        	tenancyForm.setEmployerContactNumber(employerContactNumber);
        	tenancyForm.setKinFirstName(kinFirstName);
        	tenancyForm.setKinLastName(kinLastName);
        	tenancyForm.setKinAddress(kinAddress);
        	tenancyForm.setKinEmailAddress(kinEmailAddress);
        	tenancyForm.setKinContactNumber(kinContactNumber);
        	tenancyForm.setTenancyStartedDate(tenancyStartedDate);
        	tenancyForm.setTenancyOpenedBy(tenancyOpenedBy);
        	tenancyForm.setTenancyClosedDate(tenancyClosedDate);
        	tenancyForm.setTenancyClosedBy(tenancyClosedBy);
        	tenancyForm.setTenancyDepositCertificate(tenancyDepositCertificate);
        	tenancyForm.setLinkToProperty(linkToProperty);
        	tenancyForm.setCheckingOutForm(checkingOutForm);
        	tenancyForm.setLinkToTenancyAgreement(linkToTenancyAgreement);
        	tenancyForm.setAdditionalLinks(additionalLinks);
        	tenancyForm.setCouncilTaxRegistration(councilTaxRegistration);
        	tenancyForm.setCorrespondenceLink(correspondenceLinks);
        	tenancyForm.setCorrespondenceWithTenants(correspondenceWithTenants);
        	tenancyForm.setGuarantorFirstName(guarantorFirstName);
        	tenancyForm.setGuarantorLastName(guarantorLastName);
        	tenancyForm.setGuarantorAddress(guarantorAddress);
        	tenancyForm.setGuarantorEmailAddress(guarantorEmailAddress);
        	tenancyForm.setGuarantorContactNumber(guarantorContactNumber);
        	tenancyForm.setLinkToRentAccounts(linkToRentAccounts);
        	tenancyForm.setLegalNotifications(legalNotification);
        	tenancyForm.setEmployerCompany(employerCompany);
        	tenancyForm.setGuarantorCompany(guarantorCompany);
        	
        	tenancyFormService.save(tenancyForm);
        	
        	 Set<Entry<String, String>> setRoomDet=roomDet.entrySet();
             Iterator ite=setRoomDet.iterator();
             
             while(ite.hasNext()){
           	  Map.Entry<String,String> entry = (Map.Entry<String,String>)ite.next();

                 String key             = entry.getKey();
                 String value         = entry.getValue();
                 
                 List<TenantDetails> tenantDetails=tenantDetailsService.findTenantDetailsForTenancy(tenancyForm.getId());
                 
                 for(TenantDetails td:tenantDetails){
                	 td.setFirstName(tenantFirstName);
                	 td.setLastName(tenantLastName);
                	 td.setTitle(tenantTitle);
                	 td.setLandlineNumber(tenantLandlineNumber);
                	 td.setAdditionalRemarks(tenantAdditionalRemark);
                	 td.setCreditCheck(tenantCreditCheck);
                	 td.setCurrentAddress(tenantCurrentAddress);
                	 td.setDateOfBirth(tenantDateOfBirth);
                	 td.setDriversLicense(tenantDriverLicense);
                	 td.setEmailAddress(tenantEmailAddress);
                	 td.setNationalInsuranceNumber(tenantNationalInsuranceNumber);
                	 td.setPassport(tenantPassport);
                	 td.setReferenceCheck(tenantReferenceCheck);
                	 td.setReferenceCheck(tenantReferenceCheck);
                	 td.setMobileNumber(tenantMobileNumber);
                	 tenantDetailsService.save(td);
                	 
                 //}
                 
                RoomDetails roomDetails=roomDetailsService.findRoomDetailsByPropertyDetailsFormIdAndRoomNo(pdf,key);
                 if(roomDetails!=null){
                	// roomDetails.setRoomNo(key);
                	 roomDetails.setRoomName(value);
                 roomDetailsService.save(roomDetails);
                 }else{
                	 RoomDetails rds=new RoomDetails(pdf, key, value);
                     roomDetailsService.save(rds);
                 }
                 
             }
        	
        }
        
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);
		
		return "refresh";
	}
	
	
	@RequestMapping(value = "/saveCustomerDetailsForDraft", method = RequestMethod.POST)
	public String saveCustomerDetailsToDraft(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long secGrpId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("wrkFlw")long modelId,@RequestParam("compGrp")long compGrp,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        
        String title=null;
		String firstName=null;
		String lastName=null;
		String contactNumber=null;
		String mobileNumber=null;
		String emailAddress=null;
		String billingAddress=null;
		String shippingAddress=null;
		String personalHandDelivered=null;
		String paymentDetails=null;
		String preferredMethodOfPayment=null;
		String mailingList=null;
		String orderHistory=null;
		String  appointmentHistory=null;
		String notes=null;
		
		
				
		
		
        
       HashMap<String , String> roomDet=new HashMap<String,String>();
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            
            System.out.println("Key is "+key);

            

                if(value.length>1){    
                    for (int i = 0; i < value.length; i++) {
                        System.out.println( value[i].toString());
                      
                    }
                }else
                {
            	System.out.println("Value is "+value[0].toString());
            	
            	 if(key.equals("title"))
            		 title=value[0].toString();
                 else if(key.equals("firstName"))
                	 firstName=value[0].toString();
                 
                 else if(key.equals("lastName"))   
                	 lastName=value[0].toString();
                 else if(key.equals("contactNumber"))   
                	 contactNumber=value[0].toString();
                 else if(key.equals("mobileNumber"))
                	 mobileNumber=value[0].toString();
                 else if(key.equals("emailAddress"))	   
                	 emailAddress=value[0].toString();
                 else if(key.equals("billingAddress"))
                	 billingAddress=value[0].toString();
                 else if(key.equals("shippingAddress"))
                	 shippingAddress=value[0].toString();
                 else if(key.equals("personalHandDelivered")) 
                	 personalHandDelivered=value[0].toString();
              	   else if(key.equals("paymentDetails"))
              		 paymentDetails=value[0].toString();
              		   else if(key.equals("preferredMethodOfPayment"))
              			 preferredMethodOfPayment=value[0].toString();
              		 else if(key.equals("notes")) 
                   	  notes=value[0].toString();
              		 else if(key.equals("mailingList"))
              			mailingList=value[0].toString();
              		  else if(key.equals("orderHistory"))
              			orderHistory=value[0].toString();
                      else if(key.equals("appointmentHistory")) 
                    	  appointmentHistory=value[0].toString();
                 }
        }
        
        if(userFormId==-1){
        FormDefs fds=formDefsService.findFormDefsById(formDefId);
        Forms forms=formService.fetchFormDefinition(formDefId, -1);
        UserForms uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', secGrpId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"CustomerDetails");
        userFormsService.save(uf);
        uf.setTitle(fds.getName()+"-"+uf.getId());
        userFormsService.save(uf);
        System.out.println(fds.getName()+"-"+uf.getId());
       
        CustomerDetails customerDetails=new CustomerDetails(uf.getId(),title, firstName, lastName, contactNumber, mobileNumber, emailAddress, billingAddress,shippingAddress, personalHandDelivered, paymentDetails, preferredMethodOfPayment, notes, mailingList, orderHistory, appointmentHistory);
     
          customerDetailsService.save(customerDetails);
     
       
        
        }else{
        	CustomerDetails customerDetails=customerDetailsService.findCustomerDetailsFormByUserFormId(userFormId);
        	customerDetails.setTitle(title);
        	customerDetails.setFirstName(firstName);
        	customerDetails.setLastName(lastName);
        	customerDetails.setContactNumber(contactNumber);
        	customerDetails.setMobileNumber(mobileNumber);
        	customerDetails.setEmailAddress(emailAddress);
        	customerDetails.setBillingAddress(billingAddress);
        	customerDetails.setShippingAddress(shippingAddress);
        	customerDetails.setPersonalHandDelivered(personalHandDelivered);
        	customerDetails.setPaymentDetails(paymentDetails);
        	customerDetails.setPreferredMethodOfPayment(preferredMethodOfPayment);
        	customerDetails.setNotes(notes);
        	customerDetails.setMailingList(mailingList);
        	customerDetails.setOrderHistory(orderHistory);
        	customerDetails.setAppointmentHistory(appointmentHistory);
        	customerDetailsService.save(customerDetails);
        	
        }
        
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);
		
		return "refresh";
	}
	
	
	@RequestMapping(value = "/saveRetailerOrderForDraft", method = RequestMethod.POST)
	public String saveRetailerOrderToDraft(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long secGrpId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("wrkFlw")long modelId,@RequestParam("compGrp")long compGrp,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        
       String termsAndConditions=null;
        String title=null;
		String firstName=null;
		String lastName=null;
		String companyName=null;
		String contactNumber=null;
		String mobileNumber=null;
		String emailAddress=null;
		String billingAddress=null;
		String shippingAddress=null;
		String orderStatus=null;
		String paymentDetails=null;
		String preferredMethodOfPayment=null;
		String notes=null;
		
		String productCodeToBeOrdered=null;
		long quantity=0;
		double  price=0;
		double totals=0;
		
		HashMap<String,String> hmap=new HashMap<String,String>();
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            
            System.out.println("Key is "+key);

            

                if(value.length>1){    
                    for (int i = 0; i < value.length; i++) {
                        System.out.println( value[i].toString());
                      
                    }
                }else
                {
            	System.out.println("Value is "+value[0].toString());
            	
            	 if(key.equals("title"))
            		 title=value[0].toString();
            	 else if(key.equals("termsAndConditions"))
            		 termsAndConditions=value[0].toString();
            	 else if(key.equals("firstName"))
                	 firstName=value[0].toString();
                 
                 else if(key.equals("lastName"))   
                	 lastName=value[0].toString();
                 else if(key.equals("contactNumber"))   
                	 contactNumber=value[0].toString();
                 else if(key.equals("mobileNumber"))
                	 mobileNumber=value[0].toString();
                 else if(key.equals("emailAddress"))	   
                	 emailAddress=value[0].toString();
                 else if(key.equals("billingAddress"))
                	 billingAddress=value[0].toString();
                 else if(key.equals("shippingAddress"))
                	 shippingAddress=value[0].toString();
                 else if(key.equals("companyName")) 
                	 companyName=value[0].toString();
                 else if(key.equals("orderStatus")) 
                	 orderStatus=value[0].toString();
              	   else if(key.equals("paymentDetails"))
              		 paymentDetails=value[0].toString();
              		   else if(key.equals("preferredMethodOfPayment"))
              			 preferredMethodOfPayment=value[0].toString();
              		 else if(key.equals("notes")) 
                   	  notes=value[0].toString();
              		 else if(key.startsWith("productCodeToBeOrdered")){
              			 hmap.put(key, value[0].toString());
              			//productCodeToBeOrdered=value[0].toString();
              		 }
              		  else if(key.startsWith("price")){
              			 if(value[0].toString()!=null && value[0].toString()!="")
              				hmap.put(key, value[0].toString());
              			//price=Double.parseDouble(value[0].toString());
              		  }
                      else if(key.startsWith("quantity")) {
                    	  if(value[0].toString()!=null && value[0].toString()!="")
                    		  hmap.put(key, value[0].toString());
                    		  //quantity=Long.parseLong(value[0].toString());
                      }
                      else if(key.equals("totals")) {
                    	  if(value[0].toString()!=null && value[0].toString()!="")
                    		  hmap.put(key, value[0].toString());
                    		  //totals=Double.parseDouble(value[0].toString());
                      }
                 }
        }
        
        if(userFormId==-1){
        FormDefs fds=formDefsService.findFormDefsById(formDefId);
        Forms forms=formService.fetchFormDefinition(formDefId, -1);
        UserForms uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', secGrpId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"RetailerOrder");
        userFormsService.save(uf);
        uf.setTitle(fds.getName()+"-"+uf.getId());
        userFormsService.save(uf);
        System.out.println(fds.getName()+"-"+uf.getId());
       
       RetailerOrder retailerOrder=new RetailerOrder(uf.getId(), termsAndConditions, title, firstName, lastName, companyName, contactNumber, mobileNumber, emailAddress, billingAddress, shippingAddress, notes, preferredMethodOfPayment, paymentDetails, orderStatus);
       retailerOrderService.save(retailerOrder);
       
       Set<Entry<String, String>> set = hmap.entrySet();
       Iterator ite = set.iterator();
       
       while(ite.hasNext()){
           Map.Entry<String,String> entry = (Map.Entry<String,String>)ite.next();
           String key             = entry.getKey();
           String value         = entry.getValue();
        	   ProductOrder productOrder=new ProductOrder(retailerOrder.getId(), key, value.toString());
        	   productOrderService.save(productOrder);
       		}
        	
        }else{
        	RetailerOrder retailerOrder=retailerOrderService.findRetailerOrderByUserFormId(userFormId);
        	retailerOrder.setTermsAndConditions(termsAndConditions);
        	retailerOrder.setTitle(title);
        	retailerOrder.setFirstName(firstName);
        	retailerOrder.setLastName(lastName);
        	retailerOrder.setCompanyName(companyName);
        	retailerOrder.setContactNumber(contactNumber);
        	retailerOrder.setMobileNumber(mobileNumber);
        	retailerOrder.setEmailAddress(emailAddress);
        	retailerOrder.setBillingAddress(billingAddress);
        	retailerOrder.setShippingAddress(shippingAddress);
        	retailerOrder.setOrderStatus(orderStatus);
        	retailerOrder.setPaymentDetails(paymentDetails);
        	retailerOrder.setPreferredMethodOfPayment(preferredMethodOfPayment);
        	retailerOrder.setNotes(notes);
        	retailerOrderService.save(retailerOrder);
        	
        	List<ProductOrder> productOrders=productOrderService.findProductOrderByRetailerOrderId(retailerOrder.getId());
            
            productOrderService.delete(productOrders);
        	
        	 Set set = hmap.entrySet();
             Iterator ite = set.iterator();
             
             while(ite.hasNext()){
               	 
                 Map.Entry<String,String> entry = (Map.Entry<String,String>)ite.next();
                 
                 String key             = entry.getKey();
                 String value         = entry.getValue();
               
                ProductOrder productOrder=new ProductOrder(retailerOrder.getId(), key, value);
                productOrderService.save(productOrder);
             		}
             }
        
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);
		
		return "refresh";
	}
	
	@RequestMapping(value = "/saveBuilderDetailForDraft", method = RequestMethod.POST)
	public String saveBuilderDetailToDraft(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long secGrpId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("wrkFlw")long modelId,@RequestParam("compGrp")long compGrp,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        
        
    	 String firstName=null;
    	 String lastName=null;
    	 String emailAddress=null;
    	 String landlineNumber=null;
    	 String mobileNumber=null;
    	 String companyName=null;
    	 String companyAddress=null;
    	 String companyNumber=null;
    	 String vatNumber=null;
    	 String accountHoldersName=null;
    	 String accountNumber=null;
    	 String sortCode=null;
    	 String linkToInvoice=null;
    	 String linkToJobs=null;
    	
		
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            
            System.out.println("Key is "+key);

            

                if(value.length>1){    
                    for (int i = 0; i < value.length; i++) {
                        System.out.println( value[i].toString());
                      
                    }
                }else
                {
            	System.out.println("Value is "+value[0].toString());
            	
            	 
            	 if(key.equals("firstName"))
                	 firstName=value[0].toString();
                 
                 else if(key.equals("lastName"))   
                	 lastName=value[0].toString();
                 else if(key.equals("emailAddress"))   
                	 emailAddress=value[0].toString();
                 else if(key.equals("landlineNumber"))   
                	 landlineNumber=value[0].toString();
                
                 else if(key.equals("mobileNumber"))
                	 mobileNumber=value[0].toString();
                 
                
                 else if(key.equals("companyName")) 
                	 companyName=value[0].toString();
                 else if(key.equals("companyAddress"))	   
                	 companyAddress=value[0].toString();
                 else if(key.equals("companyNumber"))	   
                	 companyNumber=value[0].toString();
                 else if(key.equals("vatNumber"))	   
                	 vatNumber=value[0].toString();
                 else if(key.equals("accountHoldersName"))	   
                	 accountHoldersName=value[0].toString();
                 else if(key.equals("accountNumber"))	   
                	 accountNumber=value[0].toString();
                 else if(key.equals("sortCode"))	   
                	 sortCode=value[0].toString();
                 else if(key.equals("linkToInvoice"))	   
                	 linkToInvoice=value[0].toString();
                 else if(key.equals("linkToJobs"))	   
                	 linkToJobs=value[0].toString();
            	 
            	 
                
              	   
                 }
        }
        UserForms uf=null;
        if(userFormId==-1){
        FormDefs fds=formDefsService.findFormDefsById(formDefId);
        Forms forms=formService.fetchFormDefinition(formDefId, -1);
        uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', secGrpId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"BuilderDetailsForm");
        userFormsService.save(uf);
        uf.setTitle(fds.getName()+"-"+uf.getId());
        userFormsService.save(uf);
        System.out.println(fds.getName()+"-"+uf.getId());
       
      userFormId=uf.getId();
      
      BuilderDetailsForm builderDetailsForm=new BuilderDetailsForm(userFormId, firstName, lastName, emailAddress, landlineNumber, mobileNumber, companyName, companyAddress, companyNumber, vatNumber, accountHoldersName, accountNumber, sortCode, linkToInvoice, linkToJobs);
      
       builderDetailsFormService.save(builderDetailsForm);
      
        	
        }else{
        	uf=userFormsService.findUserFormsById(userFormId);
        	BuilderDetailsForm builderDetailsForm=builderDetailsFormService.findBuilderDetailsFormByUserFormId(userFormId);
        	builderDetailsForm.setFirstName(firstName);
        	builderDetailsForm.setLastName(lastName);
        	builderDetailsForm.setEmailAddress(emailAddress);
        	builderDetailsForm.setLandlineNumber(landlineNumber);
        	builderDetailsForm.setMobileNumber(mobileNumber);
        	builderDetailsForm.setCompanyName(companyName);
        	builderDetailsForm.setCompanyAddress(companyAddress);
        	builderDetailsForm.setCompanyNumber(companyNumber);
        	builderDetailsForm.setVatNumber(vatNumber);
        	builderDetailsForm.setAccountHoldersName(accountHoldersName);
        	builderDetailsForm.setAccountNumber(accountNumber);
        	builderDetailsForm.setSortCode(sortCode);
        	builderDetailsForm.setLinkToInvoice(linkToInvoice);
        	builderDetailsForm.setLinkToJobs(linkToJobs);
        	builderDetailsFormService.save(builderDetailsForm);
        	
        }
        
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);
		
		return "refresh";
	}
	
	
	@RequestMapping(value = "/saveUtilitiesCompanyDetailsForDraft", method = RequestMethod.POST)
	public String saveUtilitiesCompanyDetailsToDraft(@RequestParam("userFormId")long userFormId,@RequestParam("formDefId")long formDefId,@RequestParam("secGrp")long secGrpId,@RequestParam("submissionDate")String submissionDate,
			@RequestParam("wrkFlw")long modelId,@RequestParam("compGrp")long compGrp,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        
        
         String companyName=null;
    	 String companyType=null;
    	 String companyTelephone=null;
    	 String companyAddress=null;
    	 String openingAndClosingTimes=null;
    	 String mainContactName=null;
    	 String mainContactTelephone=null;
    	 String mainContactEmailAddress=null;
    	
		
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            
            System.out.println("Key is "+key);

            

                if(value.length>1){    
                    for (int i = 0; i < value.length; i++) {
                        System.out.println( value[i].toString());
                      
                    }
                }else
                {
            	System.out.println("Value is "+value[0].toString());
            	
            	 
            	 if(key.equals("companyName"))
            		 companyName=value[0].toString();
                 
                 else if(key.equals("companyType"))   
                	 companyType=value[0].toString();
                 else if(key.equals("companyTelephone"))   
                	 companyTelephone=value[0].toString();
                 else if(key.equals("companyAddress"))   
                	 companyAddress=value[0].toString();
                
                 else if(key.equals("openingAndClosingTimes"))
                	 openingAndClosingTimes=value[0].toString();
                 
                
                 else if(key.equals("mainContactName")) 
                	 mainContactName=value[0].toString();
                
                 else if(key.equals("mainContactTelephone"))	   
                	 mainContactTelephone=value[0].toString();
                 else if(key.equals("mainContactEmailAddress"))	   
                	 mainContactEmailAddress=value[0].toString();
                
            	 
            	 
                
              	   
                 }
        }
        UserForms uf=null;
        if(userFormId==-1){
        FormDefs fds=formDefsService.findFormDefsById(formDefId);
        Forms forms=formService.fetchFormDefinition(formDefId, -1);
        uf=new UserForms(forms, userInfo.getUserId(), new Date(), null, 'd', secGrpId, sdf.parse(submissionDate), compGrp, modelId, 'Y',"UtilitiesCompanyDetailsForm");
        userFormsService.save(uf);
        uf.setTitle(fds.getName()+"-"+uf.getId());
        userFormsService.save(uf);
        System.out.println(fds.getName()+"-"+uf.getId());
       
      userFormId=uf.getId();
      
     UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=new UtilitiesCompanyDetailsForm(userFormId, companyName, companyType, companyTelephone, companyAddress, openingAndClosingTimes, mainContactName, mainContactTelephone, mainContactEmailAddress);
      utilitiesCompanyDetailsFormService.save(utilitiesCompanyDetailsForm);
        	
        }else{
        	uf=userFormsService.findUserFormsById(userFormId);
        	UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=utilitiesCompanyDetailsFormService.findUtilitiesCompanyDetailsFormByUserFormId(userFormId);
        	utilitiesCompanyDetailsForm.setCompanyName(companyName);
        	utilitiesCompanyDetailsForm.setCompanyAddress(companyAddress);
        	utilitiesCompanyDetailsForm.setCompanyType(companyType);
        	utilitiesCompanyDetailsForm.setCompanyTelephone(companyTelephone);
        	utilitiesCompanyDetailsForm.setOpeningAndClosingTimes(openingAndClosingTimes);
        	utilitiesCompanyDetailsForm.setMainContactName(mainContactName);
        	utilitiesCompanyDetailsForm.setMainContactTelephone(mainContactTelephone);
        	utilitiesCompanyDetailsForm.setMainContactEmailAddress(mainContactEmailAddress);
        	utilitiesCompanyDetailsFormService.save(utilitiesCompanyDetailsForm);
        	
        }
        
		int draftCount = formService.fetchDraftFormCount(userInfo.getUserId());
		Map<String, Integer> stat = new HashMap<String, Integer>();
		stat.put("Draft", draftCount);
		List<TaskSummary> tasks = caseService.findTrayLabelSummaryForUser(userInfo);
		model.addAttribute("tasks", tasks);
		model.addAttribute("draftTray", stat);
		
		return "refresh";
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
		sb.append("<th class='col order'>Title</th>\n");
		sb.append("<th class='col order'>Delete</th>\n");
		sb.append("</tr>\n");
		sb.append("</thead>\n");
		sb.append("<tbody>\n");
		while (ufli.hasNext()) {
			UserForms uf = ufli.next();
			//UserFormRef ufr=userFormRefService.findUserFormRefById(uf.getId());
			sb.append("<tr class='gradeB' title='")
				.append(uf.getForms().getFormDefs().getDescription()).append("'>\n");
			
			sb.append("<td onclick='loadDraftForm(").append(uf.getId()).append(");'>").append(uf.getForms().getFormDefs().getName()).append("</td>\n");
			sb.append("<td onclick='loadDraftForm(").append(uf.getId()).append(");'>").append(uk.co.jmr.sdp.common.Utils.formatTimestamp(uf.getCreatedOn())).append("</td>\n");
			sb.append("<td onclick='loadDraftForm(").append(uf.getId()).append(");'>").append(uf.getTitle()).append("</td>\n");
			Iterator<FormData> fdi = uf.getFormDatas().iterator();
			while (fdi.hasNext()) {
				FormData fd = fdi.next();
				if (fd.getHtmlFormDef().getName().equals("f_1_2") || fd.getHtmlFormDef().getName().equals("f_3_2") || fd.getHtmlFormDef().getName().equals("f_4_1") || 
						fd.getHtmlFormDef().getName().equals("f_5_7")) {
					sb.append("<td onclick='loadDraftForm(").append(uf.getId()).append(");'>").append(fd.getTextValue()).append("</td>\n");
				}else{
					sb.append("<td onclick='loadDraftForm(").append(uf.getId()).append(");'>").append(uf.getTitle()).append("</td>\n");
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
	public String searchLinks(
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
			//long
			QuickSearch quickSearch = new QuickSearch(-1, -1, docTypeId, author, ebNo, keywords, documentName,
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
	public String quickUpload(String itemId,long attr3Value,@RequestParam("docName")String docName, Model model, HttpSession session) throws Exception {

		AttributeValue attr3 = this.dtAttributeValueService.findDtAttrValueById(attr3Value);
		session.setAttribute("attributeValue3", attr3);
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
			keywords, attributeValue3.getAbbreviation(), attributeValue2.getAbbreviation());

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
			
			List<ModelSummary> modelList=new ArrayList<ModelSummary>();
			for(ModelSummary modelSummary:modelService.listModelsByType('F')){
				if(modelSummary.getName().equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
					modelList.add(modelSummary);
				}
			}
			
			model.addAttribute("modelListsForm", formsModel);
			model.addAttribute("formTypeId", id);
			model.addAttribute("modelLists", modelList);
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
	
		
		@RequestMapping(value = "/saveFormNew", method = RequestMethod.POST)
		public String saveFormNewToDraft(@RequestParam("borrow")String jsonObject,Model model, HttpSession session, HttpServletRequest request) throws Exception {
			
			FormVals fv=new FormVals(jsonObject);
			formValsService.save(fv);
			System.out.println("completed successfully");
			
			return "currentBorrowingForm";
		}
}
	
*/