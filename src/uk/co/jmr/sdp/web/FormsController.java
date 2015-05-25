package uk.co.jmr.sdp.web;

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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.WeakReferenceMonitor.ReleaseListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import uk.co.jmr.sdp.common.Utils;
import uk.co.jmr.sdp.dao.AttributeValueDao;
import uk.co.jmr.sdp.dao.impl.AttributeValueDaoImpl;
import uk.co.jmr.sdp.domain.AuditLogs;
import uk.co.jmr.sdp.domain.BuilderDetailsForm;
import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.sdp.domain.CheckingOutForm;
import uk.co.jmr.sdp.domain.CheckingOutRooms;
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
import uk.co.jmr.sdp.domain.GivingNoticeForm;
import uk.co.jmr.sdp.domain.Inventory;
import uk.co.jmr.sdp.domain.InventoryRooms;
import uk.co.jmr.sdp.domain.JobDetailsForm;
import uk.co.jmr.sdp.domain.JsonObjectParse;
import uk.co.jmr.sdp.domain.ProductOrder;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.PropertyRooms;
import uk.co.jmr.sdp.domain.RentReceipt;
import uk.co.jmr.sdp.domain.RetailerOrder;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.RoomDetails;
import uk.co.jmr.sdp.domain.StandardProductForm;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.TransactionDetails;
import uk.co.jmr.sdp.domain.UsefulInformationForTenants;
import uk.co.jmr.sdp.domain.UserRole;
import uk.co.jmr.sdp.domain.UtilitiesCompanyDetailsForm;
/*import uk.co.jmr.sdp.domain.UserFormRef;*/

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
import uk.co.jmr.sdp.service.AuditLogsService;
import uk.co.jmr.sdp.service.BuilderDetailsFormService;
import uk.co.jmr.sdp.service.CaseUserFormsService;
import uk.co.jmr.sdp.service.CheckingOutFormService;
import uk.co.jmr.sdp.service.CheckingOutRoomsService;
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
import uk.co.jmr.sdp.service.GivingNoticeFormService;
import uk.co.jmr.sdp.service.InspectionInventoryService;
import uk.co.jmr.sdp.service.InventoryRoomsService;
import uk.co.jmr.sdp.service.InventoryService;
import uk.co.jmr.sdp.service.JobDetailsFormService;
import uk.co.jmr.sdp.service.ModelComboService;
import uk.co.jmr.sdp.service.PotentialTenantFormService;
import uk.co.jmr.sdp.service.ProductOrderService;
import uk.co.jmr.sdp.service.PropertyDetailsFormService;
import uk.co.jmr.sdp.service.PropertyRoomsService;
import uk.co.jmr.sdp.service.RentReceiptService;
import uk.co.jmr.sdp.service.RetailerOrderService;
import uk.co.jmr.sdp.service.RoleService;
import uk.co.jmr.sdp.service.RoomDetailsService;
import uk.co.jmr.sdp.service.SecurityGroupComboService;
import uk.co.jmr.sdp.service.SecurityGroupFormService;
import uk.co.jmr.sdp.service.SecurityGroupService;
import uk.co.jmr.sdp.service.StandardProductFormService;
import uk.co.jmr.sdp.service.TempDocumentsService;
import uk.co.jmr.sdp.service.TenancyFormService;
import uk.co.jmr.sdp.service.TransactionDetailService;
import uk.co.jmr.sdp.service.UsefulInformationForTenantsService;
import uk.co.jmr.sdp.service.UtilitiesCompanyDetailsFormService;
/*import uk.co.jmr.sdp.service.UserFormRefService;*/
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
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DecimalDV;

import java.util.Date;
import java.util.Iterator;

import uk.co.jmr.sdp.service.EmailService;
import uk.co.jmr.sdp.service.impl.UserRoleServiceImpl;
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
	@Autowired
	private FormValsService formValsService;
	/*@Autowired
	private UserFormRefService userFormRefService;*/
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
	private CustomerDetailsService customerDetailsService;
	@Autowired
	private RetailerOrderService retailerOrderService;
	@Autowired
	private ProductOrderService productOrderService;
	@Autowired
	private BuilderDetailsFormService builderDetailsFormService;
	@Autowired
	private UtilitiesCompanyDetailsFormService utilitiesCompanyDetailsFormService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private InspectionInventoryService inspectionInventoryService;
	@Autowired
	private PropertyRoomsService propertyRoomsService;
	@Autowired
	private InventoryRoomsService inventoryRoomsService;
	@Autowired
	private UsefulInformationForTenantsService usefulInformationForTenantsService;
	@Autowired
	private CheckingOutFormService checkingOutFormService;
	@Autowired
	private CheckingOutRoomsService checkingOutRoomsService;
	@Autowired
	private GivingNoticeFormService givingNoticeFormService;
	@Autowired
	private AttributeValueDao attributeValueDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RentReceiptService rentReceiptService;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private JobDetailsFormService jobDetailsFormService;
	/*@Autowired
	private UserRoleServiceImpl userRoleService;
	*/
	@Autowired
	private AuditLogsService auditLogsService;
	
	
	AuditLogs auditLogs= null;
	String auditLogsAction = null;
	
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
	
	
	@RequestMapping(value = "/usefulInfoForTenants", method = RequestMethod.GET)
	public String getUsefulInfoForTenants(Model model,HttpSession session) {

		UsefulInformationForTenants usefulInformationForTenants = usefulInformationForTenantsService.findUsefulInformationForTenantsDetailsFormById(1);
		
	if(usefulInformationForTenants!=null){
		//System.out.println(usefulInformationForTenants);
		 String instructions = usefulInformationForTenants.getInstructions();
    	 String companyContactDetails = usefulInformationForTenants.getCompanyContactDetails();
    	 String companyName = usefulInformationForTenants.getCompanyName();
    	 String telephoneNumber = usefulInformationForTenants.getTelephoneNumber();
    	 String emailAddress = usefulInformationForTenants.getEmailAddress();
    	 String address = usefulInformationForTenants.getAddress();
    	 String maintenanceQuery = usefulInformationForTenants.getMaintenanceQuery();
    	 String rentQuery = usefulInformationForTenants.getRentQuery();
    	System.out.println(instructions+companyContactDetails+companyName);
    	model.addAttribute("instructions", instructions);
    	model.addAttribute("companyContactDetails", companyContactDetails);
    	model.addAttribute("companyName", companyName);
    	model.addAttribute("telephoneNumber", telephoneNumber);
    	model.addAttribute("emailAddress", emailAddress);
    	model.addAttribute("address", address);
    	model.addAttribute("maintenanceQuery", maintenanceQuery);
    	model.addAttribute("rentQuery", rentQuery);
	}
		
		return "usefulInformationForTenants";
	}
	
	
	
	@RequestMapping(value = "/saveUsefulInfoForTenants", method = RequestMethod.POST)
	public String saveUsefulInfoForTenants(Model model,HttpSession session, HttpServletRequest request) {

		UsefulInformationForTenants usefulInformationForTenants = usefulInformationForTenantsService.findUsefulInformationForTenantsDetailsFormById(1);
		
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
         String instructions = null;
    	 String companyContactDetails = null;
    	 String companyName = null;
    	 String telephoneNumber = null;
    	 String emailAddress = null;
    	 String address = null;
    	 String maintenanceQuery = null;
    	 String rentQuery = null;
		
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
             	
             	 
             	 if(key.equals("instructions"))
             		instructions=value[0].toString();
                  else if(key.equals("companyContactDetails"))   
                	  companyContactDetails=value[0].toString();
                  else if(key.equals("companyName"))   
                	  companyName=value[0].toString();
                  else if(key.equals("telephoneNumber"))
                	  telephoneNumber=value[0].toString();
                  else if(key.equals("emailAddress"))	   
                	  emailAddress=value[0].toString();
                  else if(key.equals("address"))	   
                	  address=value[0].toString();
                  else if(key.equals("maintenanceQuery"))	   
                	  maintenanceQuery=value[0].toString();
                  else if(key.equals("rentQuery"))	   
                	  rentQuery=value[0].toString(); 
                  }
         }
         
		
		
	if(usefulInformationForTenants==null){
		//System.out.println(usefulInformationForTenants);
		
         
        usefulInformationForTenants=new UsefulInformationForTenants(instructions, companyContactDetails, companyName, telephoneNumber, emailAddress, address, maintenanceQuery, rentQuery);
        usefulInformationForTenantsService.save(usefulInformationForTenants);
        
          	
          }else{
        	  
          	usefulInformationForTenants.setInstructions(instructions);
          	usefulInformationForTenants.setCompanyContactDetails(companyContactDetails);
          	usefulInformationForTenants.setCompanyName(companyName);
          	usefulInformationForTenants.setTelephoneNumber(telephoneNumber);
          	usefulInformationForTenants.setEmailAddress(emailAddress);
          	usefulInformationForTenants.setAddress(address);
          	usefulInformationForTenants.setMaintenanceQuery(maintenanceQuery);
          	usefulInformationForTenants.setRentQuery(rentQuery);
          	usefulInformationForTenantsService.save(usefulInformationForTenants);
          	
          }
	
	//UsefulInformationForTenants usefulInformationForTenants = usefulInformationForTenantsService.findUsefulInformationForTenantsDetailsFormById(1);
	
	/*if(usefulInformationForTenants!=null){
		usefulInformationForTenants = usefulInformationForTenantsService.findUsefulInformationForTenantsDetailsFormById(1);
		//System.out.println(usefulInformationForTenants);
		 String instructions1 = usefulInformationForTenants.getInstructions();
    	 String companyContactDetails1 = usefulInformationForTenants.getCompanyContactDetails();
    	 String companyName1 = usefulInformationForTenants.getCompanyName();
    	 String telephoneNumber1 = usefulInformationForTenants.getTelephoneNumber();
    	 String emailAddress1 = usefulInformationForTenants.getEmailAddress();
    	 String address1 = usefulInformationForTenants.getAddress();
    	 String maintenanceQuery1 = usefulInformationForTenants.getMaintenanceQuery();
    	 String rentQuery1 = usefulInformationForTenants.getRentQuery();
    	//System.out.println(instructions+companyContactDetails+companyName);
    	model.addAttribute("instructions", instructions1);
    	model.addAttribute("companyContactDetails", companyContactDetails1);
    	model.addAttribute("companyName", companyName1);
    	model.addAttribute("telephoneNumber", telephoneNumber1);
    	model.addAttribute("emailAddress", emailAddress1);
    	model.addAttribute("address", address1);
    	model.addAttribute("maintenanceQuery", maintenanceQuery1);
    	model.addAttribute("rentQuery", rentQuery1);
	}*/
		
	return "refresh";
	//return "usefulInformationForTenants";
		
	}
	
	@RequestMapping(value = "/getAllJobs", method = RequestMethod.GET)
	public String getAllJobDetails(Model model,HttpSession session) {
		System.out.println("Im getAllJobs>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		List<JobDetailsForm> jobDetailsForm = null;
		for(Role r: user.getRoles())	
		{
			System.out.println(">>>>>My Role>>"+ r.getRoleName());
			if(r.getRoleName().equalsIgnoreCase("Admin"))
			{
				System.out.println("Im ADMIN");
				jobDetailsForm=jobDetailsFormService.findAllJobDetails();
				break;
			}
			
			if(r.getRoleName().equalsIgnoreCase("Tenant"))
			{
				System.out.println("Im Tenant");
				System.out.println("My user ID>>>>>>"+ user.getId());
				long currentUserId = user.getId();
				jobDetailsForm=jobDetailsFormService.findJobDetailsFormByCreatedBy(user);
				break;
			}
			
			if(r.getRoleName().equalsIgnoreCase("Builder"))
			{
				System.out.println("Im Builder");
				System.out.println("My user ID>>>>>>"+ user.getId());
				long currentUserId = user.getId();
				jobDetailsForm=jobDetailsFormService.findJobDetailsFormByBuilderName(user);
				break;
			}
		}
		
		
		//List<JobDetailsForm> jobDetailsForm=jobDetailsFormService.findAllJobDetails();
		model.addAttribute("jobsList", jobDetailsForm);
		return "newJobFormsTable";
	}

	@RequestMapping(value = "/getAllProperties", method = RequestMethod.GET)
	public String getAllPropertyDetails(Model model,HttpSession session) {

		List<PropertyDetailsForm> propertyDetailsForm=propertyDetailsFormService.findAllPropertyDetailsFormWithAllStatus();
		
		model.addAttribute("propertiesList", propertyDetailsForm);
		model.addAttribute("taskName","Property Details");
		
		return "propertyFormsTable";
	}
	
	
	
	@RequestMapping(value = "/getAllTenancy", method = RequestMethod.GET)
	public String getAllTenancy(Model model,HttpSession session) {

		List<TenancyForm> tenancyForms=tenancyFormService.findAllTenancyWithAllStatus();
		
		model.addAttribute("tenancyList", tenancyForms);
		
		
		return "tenancyFormsTable";
	}
	
	@RequestMapping(value = "/getAllRentReceipt", method = RequestMethod.GET)
	public String getAllRentReceipt(Model model,HttpSession session) {
		
		User user = (User) session.getAttribute("LOGGED_IN_USER");
	
		/*List<TransactionDetails> transactionDetails=transactionDetailService.findTransactionbyUserid(user.getId());
		model.addAttribute("transactionDetailsList", transactionDetails);*/
		
		List<RentReceipt> rentReceipt=rentReceiptService.findAllRentReceiptsForUser(user.getId());
		for(RentReceipt rr:rentReceipt){
			model.addAttribute("tenancyId", rr.getTenancyId().getId());
			break;
		}
		model.addAttribute("rentReceipts", rentReceipt);
		return "rentReceiptTable";
	}
	
	@RequestMapping(value = "/getGivingNotice", method = RequestMethod.GET)
	public String getAllGivingNotice(Model model,HttpSession session) {
		
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		
		GivingNoticeForm givingNoticeForms=givingNoticeFormService.findGivingNoticeStatusByUserId(user);
		List<GivingNoticeForm> givingNoticeList=new ArrayList<GivingNoticeForm>();
		if(givingNoticeForms!=null)
		givingNoticeList.add(givingNoticeForms);
		model.addAttribute("givingNoticeList", givingNoticeList);
		List<TransactionDetails> td=transactionDetailService.findTransactionbyUserid(user.getId());
		model.addAttribute("property", !td.isEmpty()?td.get(0).getPropertyId().getId():0);
		model.addAttribute("tenancy", !td.isEmpty()?td.get(0).getTenancyId().getId():0);
		
		return "givingNoticeTable";
	}
	

	@RequestMapping(value = "/getAllInventory", method = RequestMethod.GET)
	public String getAllInventory(Model model,HttpSession session) {

		List<Inventory> inventories=inventoryService.findAllInventory();
		
		model.addAttribute("inventoryList", inventories);
	
		
		return "inventoryTable";
	}
	
	@RequestMapping(value = "/getAllBuilderDetails", method = RequestMethod.GET)
	public String getAllBuilderDetailsDetails(Model model,HttpSession session) {

		List<BuilderDetailsForm> builderDetailsForms=builderDetailsFormService.findAllBuilderDetailsForm();
		
		model.addAttribute("builderList", builderDetailsForms);
		
		
		return "builderDetailsFormsTable";
	}
	
	@RequestMapping(value = "/getAllUtilitiesCompanyDetails", method = RequestMethod.GET)
	public String getAllUtilitiesCompanyDetails(Model model,HttpSession session) {

		List<PropertyDetailsForm> propertyDetailsForm=propertyDetailsFormService.findAllPropertyDetailsForm();
		
		model.addAttribute("propertiesList", propertyDetailsForm);
		model.addAttribute("taskName","Property Details");
		
		return "utilitiesCompanyFormsTable";
	}
	
	@RequestMapping(value = "/getAllPotentialTenant", method = RequestMethod.GET)
	public String getAllPotentialTenants(Model model,HttpSession session) {

		List<PotentialTenantForm> potentialTenantForms=potentialTenantFormService.findAllPotentialTenants();
		
		model.addAttribute("potentialTenantList", potentialTenantForms);
		
		
		return "potentialTenantsFormsTable";
	}
	
	@RequestMapping(value = "/getAllUtilitiesCompany", method = RequestMethod.GET)
	public String getAllUtilitiesCompany(Model model,HttpSession session) {
		List<UtilitiesCompanyDetailsForm> utilitiesCompanyDetailsForms=utilitiesCompanyDetailsFormService.findAllUtilitiesCompanyDetails();
		
		model.addAttribute("utilitiesCompanyList", utilitiesCompanyDetailsForms);
		return "utilitiesCompanyFormsTable";
	}
	
	@RequestMapping(value = "/getAllCheckingOut", method = RequestMethod.GET)
	public String getAllCheckingOut(Model model,HttpSession session) {
		List<CheckingOutForm> checkingOutForms=checkingOutFormService.findAllCheckingOut();
		
		model.addAttribute("checkingOutList", checkingOutForms);
		return "checkingOutFormsTable";
	}
	
	@RequestMapping(value = "/showJobDetails", method = RequestMethod.GET)
	public String getShowJobDetails(@RequestParam("jobId")long jobId ,Model model,HttpSession session) {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		System.out.println(">>>>>Im in showJobDetails");
		System.out.println(">>>>>jobId:::::"+ jobId);
			
		List<PropertyDetailsForm> pdf=propertyDetailsFormService.findAllPropertyDetailsForm();
		model.addAttribute("properties", pdf);
		
		for(PropertyDetailsForm r:pdf){
			//sbRole.append(r.getId()).append(",");
			//System.out.println(r.getPropertyTitle());
			r.getPropertyTitle();
		}
		
		User user = (User) session.getAttribute("LOGGED_IN_USER");
		StringBuilder sbRole = new StringBuilder();
		Set<Role>  roles=user.getRoles();
		for(Role r:roles){
			sbRole.append(r.getId()).append(",");
			System.out.println(r.getRoleName());
		}
		String strSbRole = sbRole.toString();
		model.addAttribute("sbRole", strSbRole);
		
		List<User> buildersList = 	roleService.findUsersForRoleName("Builder");
		model.addAttribute("buildersList", buildersList);
		System.out.println(">>>>>>>>>>>>>>>>>"+ buildersList.size());
		for(User b: buildersList)
		{
			//b.getUserName();
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+ b.getUserName());
		}
		JobDetailsForm jobDetailsForm=null;
		if(jobId!=0)
		{
		 jobDetailsForm = jobDetailsFormService.findJobDetailsFormById(jobId);

		System.out.println(jobDetailsForm.getId());
		System.out.println(jobDetailsForm.getLinkOfPropertyForm());
		System.out.println(jobDetailsForm.getLinkOfTenantForm());
		System.out.println(jobDetailsForm.getBuilder());
		System.out.println(jobDetailsForm.getDateRaised());
		System.out.println(jobDetailsForm.getJobNumber());
		System.out.println(jobDetailsForm.getJobTitle());
		System.out.println(jobDetailsForm.getIsUrgency());
		System.out.println(jobDetailsForm.getAppoinmentDate());
		System.out.println(jobDetailsForm.getAppoinmentTimeRange());
		System.out.println(jobDetailsForm.getLinkToCompanyContactInfo());
		System.out.println(jobDetailsForm.getLinkToInvoiceForJob());
		System.out.println(jobDetailsForm.getStatus());
		
		if(jobDetailsForm.getStatus()!=null)
			model.addAttribute("status", jobDetailsForm.getStatus() );
		if(jobDetailsForm.getLinkOfPropertyForm() != null)
		model.addAttribute("linkOfPropertyForm", jobDetailsForm.getLinkOfPropertyForm() );
		if(jobDetailsForm.getLinkOfTenantForm()!= null)
		model.addAttribute("linkToTenancy", jobDetailsForm.getLinkOfTenantForm() );
		if(jobDetailsForm.getBuilder()!=null)
			if(jobDetailsForm.getBuilder().getUserName() != null)
				model.addAttribute("linkOfBuilderForm", jobDetailsForm.getBuilder().getUserName() );
		if(jobDetailsForm.getDateRaised()!=null)
		model.addAttribute("dateRaised",sdf.format(jobDetailsForm.getDateRaised()));
		if(jobDetailsForm.getJobNumber()!=null)
		model.addAttribute("jobNumber",jobDetailsForm.getJobNumber());
		if(jobDetailsForm.getDescriptionOfJob()!=null)
		model.addAttribute("descriptionOfJob",jobDetailsForm.getDescriptionOfJob());
		if(jobDetailsForm.getIsUrgency()!=null)
		model.addAttribute("isUrgency",jobDetailsForm.getIsUrgency());
		if(jobDetailsForm.getAppoinmentDate()!=null)
		model.addAttribute("appoinmentDate",sdf.format(jobDetailsForm.getAppoinmentDate()));
		if(jobDetailsForm.getAppoinmentTimeRange()!=null)
		model.addAttribute("appoinmentTimeRange",sdf.format(jobDetailsForm.getAppoinmentTimeRange()));
		if(jobDetailsForm.getLinkToInvoiceForJob()!=null)
		model.addAttribute("linkToInvoiceForJob",jobDetailsForm.getLinkToInvoiceForJob());
		if(jobDetailsForm.getLinkToCompanyContactInfo()!=null)
		model.addAttribute("linkToCompanyContactInfo",jobDetailsForm.getLinkToCompanyContactInfo());
		}
		
		model.addAttribute("jobId",jobId);
		return "newJobForm";
	}
	
	
	
	
	
	
	@RequestMapping(value = "/showGivingNotice", method = RequestMethod.GET)
	public String getGivingNoticeForm(@RequestParam("propertyId")long propertyId,@RequestParam("tenancyId")long tenancyId ,Model model,HttpSession session) {
		
		/*List<PropertyDetailsForm> propertyDetailsForm=propertyDetailsFormService.findAllPropertyDetailsForm();
		model.addAttribute("properties", propertyDetailsForm);*/
		PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
		TenancyForm tf=tenancyFormService.findTenancyFormById(tenancyId);
		model.addAttribute("propertyId", propertyId);
		model.addAttribute("tenancyId", tenancyId);
		model.addAttribute("tenantName", tf.getTenantFirstName());
		model.addAttribute("address", tf.getTenantCurrentAddress());
		model.addAttribute("propertyTitle", pdf.getPropertyTitle());
		model.addAttribute("tenancyTitle", tf.getTenancyTitle());
		return "givingNoticeForm";
	}
	
	@RequestMapping(value = "/showPropertyDetails", method = RequestMethod.GET)
	public String getPropertyForm(@RequestParam("propertyId") long propertyId, Model model,HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		if(propertyId!=0){
			
			PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
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
	    	
	    	/*System.out.println(roomD.toString());
	    	System.out.println(roomD.deleteCharAt(roomD.length()-1).toString());*/
	    	 model.addAttribute("roomDetailsMap", roomD.toString());
		}
		
		model.addAttribute("propertyId", propertyId);
		
		return "propertyDetailsForm";
	}
	
	@RequestMapping(value = "/showRentAccountsForm", method = RequestMethod.GET)
	public String getRentAccountsForm(@RequestParam("propertyId")long propertyId,@RequestParam("tenancyId")long tenancyId,Model model,HttpSession session) {
		List<PropertyDetailsForm> propertyDetailsForm=new ArrayList<PropertyDetailsForm>();
		PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
		List<TenancyForm> tenancyForms=tenancyFormService.findTenancyFormByPropertyDetailsForm(pdf);
		propertyDetailsForm.add(pdf);
		model.addAttribute("properties", propertyDetailsForm);
		model.addAttribute("propertyId", propertyId);
		model.addAttribute("tenancyId", tenancyId);
		model.addAttribute("tenancyForms", tenancyForms);
		return "rentAccountsForm";
	}
	
	@RequestMapping(value = "/showJobForm", method = RequestMethod.GET)
	public String getJobForm(@RequestParam("jobId")long jobId,Model model,HttpSession session) {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		
		if(jobId!=0){
			
			JobDetailsForm jobDetailsForm = jobDetailsFormService.findJobDetailsFormById(jobId);
			
			Date dateRaised = jobDetailsForm.getDateRaised();
			String jobNumber = jobDetailsForm.getJobNumber();
			String descriptionOfJob = jobDetailsForm.getDescriptionOfJob();
			String photoOfIssue = jobDetailsForm.getPhotoOfIssue();
			String isUrgency = jobDetailsForm.getIsUrgency();
			Date appoinmentDate = jobDetailsForm.getAppoinmentDate();
			Date appoinmentTimeRange = jobDetailsForm.getAppoinmentTimeRange();
			String photoOfFixes = jobDetailsForm.getPhotoOfFixes();
			PropertyDetailsForm linkOfPropertyForm = jobDetailsForm.getLinkOfPropertyForm();
			TenancyForm linkOfTenantForm = jobDetailsForm.getLinkOfTenantForm();
			
	    	String linkToInvoiceForJob= jobDetailsForm.getLinkToInvoiceForJob();
	    	String linkToCompanyContactInfo= jobDetailsForm.getLinkToCompanyContactInfo();
	    	
	    	model.addAttribute("dateRaised", dateRaised);
	    	model.addAttribute("jobNumber", jobNumber);
	    	model.addAttribute("descriptionOfJob", descriptionOfJob);
	    	model.addAttribute("photoOfIssue", photoOfIssue);
	    	model.addAttribute("isUrgency", isUrgency);
	    	model.addAttribute("appoinmentDate", appoinmentDate);
	    	model.addAttribute("appoinmentTimeRange", appoinmentTimeRange);
	    	model.addAttribute("photoOfFixes", photoOfFixes);
	    	model.addAttribute("linkOfPropertyForm", linkOfPropertyForm);
	    	model.addAttribute("linkOfTenantForm", linkOfTenantForm);
	    	
	    	
	    	model.addAttribute("linkToInvoiceForJob", linkToInvoiceForJob);
	    	model.addAttribute("linkToCompanyContactInfo", linkToCompanyContactInfo);

				
		}
		return "newJobsForm";
	}

	
	@RequestMapping(value = "/showTenancy", method = RequestMethod.GET)
	public String getTenancyForm(@RequestParam("tenancyId") long tenancyId, Model model,HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		Set<PropertyDetailsForm> propertyDetailsForm=new HashSet<PropertyDetailsForm>();
		if(tenancyId!=0){

			TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(tenancyId);
			String propertyAddress=tenancyForm.getPropertyAddress();
			String typeOfRental=tenancyForm.getTypeOfRental();
			String roomNumber=tenancyForm.getRoomNumber();
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
			String checkingInForm=tenancyForm.getCheckingInForm();
			
			
			String tenantFirstName=null;
			String tenantTitle=null;
			String tenantLastName=null;
			Date	tenantDateOfBirth=null;
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
						
				 tenantFirstName=tenancyForm.getTenantFirstName();
				 tenantTitle=tenancyForm.getTenantTitle();
				 tenantLastName=tenancyForm.getTenantLastName();
				 tenantDateOfBirth=tenancyForm.getTenantDateOfBirth();
				 tenantLandlineNumber=tenancyForm.getTenantLandlineNumber();
				 tenantMobileNumber=tenancyForm.getTenantMobileNumber();
				 tenantEmailAddress=tenancyForm.getTenantEmailAddress();
				 tenantNationalInsuranceNumber=tenancyForm.getTenantNationalInsuranceNumber();
				 tenantCurrentAddress=tenancyForm.getTenantCurrentAddress();
				 tenantPassport=tenancyForm.getTenantPassport();
				 tenantDriverLicense=tenancyForm.getTenantDriversLicense();
				 tenantCreditCheck=tenancyForm.getTenantCreditCheck();
				 tenantReferenceCheck=tenancyForm.getTenantReferenceCheck();
				 tenantAdditionalRemark=tenancyForm.getTenantAdditionalRemarks();
				 propertyDetailsForm.add(tenancyForm.getPropertyDetailsForm());
			
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
	    	 model.addAttribute("tenancyFinishDate", tenancyForm.getTenancyFinishDate()!=null ? sdf.format(tenancyForm.getTenancyFinishDate()):"");
	    	model.addAttribute("rentDueDate", tenancyForm.getRentDueDate());
	    	 model.addAttribute("checkingInForm", checkingInForm);
	    	List<PropertyRooms> prs=propertyRoomsService.findRoomByPropertyDetails(tenancyForm.getPropertyDetailsForm());
	    	
	    	
	    	
	    	StringBuilder sb=new StringBuilder();
	    	for(PropertyRooms pr:prs){
	    		sb.append(pr.getRoomName()+",");
	    	}
	    	
	    	
	    	
	    	sb.append(tenancyForm.getRoomNumber());
	    	
	    	
	    	if(sb.length()>=1)
				sb.deleteCharAt(sb.length()-1);
	    	
	    	model.addAttribute("roomNumber", tenancyForm.getRoomNumber());
	    	 model.addAttribute("roomnames", sb.toString());
	    	 model.addAttribute("status",tenancyForm.getStatus());
	    	 model.addAttribute("isOccupied",tenancyForm.getIsOccupied());
	    	 User u=userService.findUserByNameAndEmail(tenancyForm.getTenantFirstName(), tenancyForm.getTenantEmailAddress());
	    	 model.addAttribute("tenantUserName",u!=null?u.getUserName():tenantFirstName);
	    	 model.addAttribute("tenantPassword",u!=null?u.getPassword():tenantFirstName);
		}else{
			model.addAttribute("status","");
			model.addAttribute("isOccupied","N");
		}
		
	
		
		List<PropertyRooms> propertyRooms=propertyRoomsService.findRoomByIsOccupied('N');
		
		for(PropertyRooms pr:propertyRooms){
			if(pr.getPropertyDetailsForm().getStatus().equalsIgnoreCase("Property Submitted"))
			propertyDetailsForm.add(pr.getPropertyDetailsForm());
			
		}
		
		model.addAttribute("properties", propertyDetailsForm);
		model.addAttribute("tenancyId", tenancyId);
		
		return "tenancyForm";
	}
	
	
	
	@RequestMapping(value = "/showInventory", method = RequestMethod.GET)
	public String getInventory(@RequestParam("inventoryId") long inventoryId, Model model,HttpSession session) {
		
		if(inventoryId!=0){
		Inventory inventory=inventoryService.findInventoryFormById(inventoryId);
		
		
	   	// String propertyForm=inventory.getPropertyDetailsForm()!=null ? "-1":String.valueOf(inventory.getPropertyDetailsForm().getId());
	   	 String roomNameSel="";
	   	 String jsonobject="";
	   	 StringBuilder pictures=new StringBuilder();
	 
	
	 model.addAttribute("propertyName", inventory.getPropertyDetailsForm().getId());
	 
	 StringBuilder sb1=new StringBuilder();
	
	 
		
		PropertyDetailsForm pdf=inventory.getPropertyDetailsForm();
		
		List<PropertyRooms> prs=propertyRoomsService.findAllRoomsForPropertyDetailsForm(pdf);
	 
	 for(PropertyRooms pr:prs){
			
			sb1.append(pr.getRoomName()+",");
		}
	 StringBuilder sb=new StringBuilder();
	 List<InventoryRooms> inventoryRooms=inventoryRoomsService.findInventoryRoomsForInventory(inventory);
	 for(InventoryRooms inRooms:inventoryRooms){
		 if(inRooms.getFieldName().equalsIgnoreCase("room")){
			 roomNameSel=inRooms.getFieldValue();
		 }
		 else if(inRooms.getFieldName().equalsIgnoreCase("json")){
			 jsonobject=inRooms.getFieldValue();
		 }else if(inRooms.getFieldName().equals("photo1")){
			 sb.append(inRooms.getFieldName()+","+inRooms.getFieldValue()+",");
		 }
		 else if(inRooms.getFieldName().startsWith("photo")){
			 pictures.append(inRooms.getFieldName()+","+inRooms.getFieldValue()+","); 
		 }else if(!inRooms.getFieldName().equalsIgnoreCase("rooms") && !inRooms.getFieldName().equalsIgnoreCase("json")){
		 sb.append(inRooms.getFieldName()+","+inRooms.getFieldValue()+",");
		 }
	 }
	 
	 model.addAttribute("tenantName", inventory.getTenantName());
	 model.addAttribute("tenantAddress", inventory.getTenantAddress());
	 model.addAttribute("notes", inventory.getNotes());
	 model.addAttribute("everythingOk", inventory.getEverythingOk());
	 model.addAttribute("room", inventory.getRoom());
	 
	 //sb.deleteCharAt(sb.lastIndexOf(","));
	 model.addAttribute("tableValues", sb.toString());
	
	 model.addAttribute("roomNamesRet", roomNameSel);
	 model.addAttribute("tab", jsonobject);
	 model.addAttribute("pictures", pictures.toString());
	 
	 System.out.println(jsonobject);
	 System.out.println(sb.toString());
	 System.out.println(pictures.toString());
		
		
		
		
		for(PropertyRooms pr:prs){
			sb1.append(pr.getRoomName()+",");
}
		model.addAttribute("roomNamesRet", sb1.toString());
		}
		List<PropertyDetailsForm> propertyDetailsForm=propertyDetailsFormService.findAllPropertyDetailsForm();
		model.addAttribute("inventoryId",inventoryId);
		model.addAttribute("properties", propertyDetailsForm);
		return "inventory";
	}
	
	@RequestMapping(value = "/showBuilderDetails", method = RequestMethod.GET)
	public String getBuilderDetails(@RequestParam("builderDetailsId") long builderDetailsId, Model model,HttpSession session) {
		
		if(builderDetailsId!=-1){
		
		BuilderDetailsForm builderDetailsForm=builderDetailsFormService.findBuilderDetailsFormById(builderDetailsId);
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
		model.addAttribute("builderDetailsId", builderDetailsId);
		return "builderDetailsForm";
	}
	
	@RequestMapping(value = "/showPotentialTenant", method = RequestMethod.GET)
	public String getPotentialTenant(@RequestParam("potentialTenantId") long potentialTenantId, Model model,HttpSession session) {
		
		if(potentialTenantId!=0){
			PotentialTenantForm potentialTenantForm=potentialTenantFormService.findPotentialTenantFormById(potentialTenantId);
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
		model.addAttribute("potentialTenantId", potentialTenantId);
		
		return "potentialTenantForm";
	}
	
	@RequestMapping(value = "/showUtilitiesCompanyDetails", method = RequestMethod.GET)
	public String getUtiliesCompanyDetails(@RequestParam("utilitiesCompanyId") long utilitiesCompanyId, Model model,HttpSession session) {
	
		if(utilitiesCompanyId!=-1){
		UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=utilitiesCompanyDetailsFormService.findUtilitiesCompanyDetailsFormById(utilitiesCompanyId);
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
		model.addAttribute("utilitiesCompanyId", utilitiesCompanyId);
		return "utilitiesCompanyDetailsForm";
	}
	
	
	@RequestMapping(value = "/showCheckingOut", method = RequestMethod.GET)
	public String getCheckingOut(@RequestParam("checkingoutId") long checkingoutId, Model model,HttpSession session) {
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		
		if(checkingoutId!=0){
			
			CheckingOutForm checkingOutForm=checkingOutFormService.findCheckingOutFormById(checkingoutId);
			StringBuilder sb=new StringBuilder();
			 String tenantName=checkingOutForm.getTenantName();
	    	 String landlordName=checkingOutForm.getLandlordName();
	    	 String rentedPropertyAddress=checkingOutForm.getRentedPropertyAddress();
	    	 Date checkOutAppointmentTime=checkingOutForm.getCheckOutAppointmentTime();
	    	 String nameOfEmployees=checkingOutForm.getNameOfEmployees();
	    	 String agentCompletingCheckOut=checkingOutForm.getAgentCompletingCheckOut();
	    	 String damage=checkingOutForm.getDamage();
	    	 String notes=checkingOutForm.getNotes();
	    	 String frontDoor=checkingOutForm.getFrontDoor();
	    	 String backDoor=checkingOutForm.getBackDoor();
	    	// String roomNumbers=checkingOutForm.get;
	    	 String tenantSignature=checkingOutForm.getTenantSignature();
	    	 String propertyForm=checkingOutForm.getPropertyDetailsForm()!=null ? String.valueOf((checkingOutForm.getPropertyDetailsForm().getId())) : "0";
	    	 String tenancyForm=checkingOutForm.getTenancy()!=null ? String.valueOf((checkingOutForm.getTenancy().getId())) : "0";
	    	 
	    	 List<CheckingOutRooms> checkingOutRooms=checkingOutRoomsService.findCheckingOutRoomsByCheckingOutFormId(checkingOutForm);
			
	    	 for(CheckingOutRooms cr:checkingOutRooms){
	    		 sb.append(cr.getRoomId()+","+cr.getRoomName()+",");
	    	 }
	    	 
	    	// sb.deleteCharAt(sb.length()-1);
	    	 
	    	 model.addAttribute("tenantName", tenantName);
	    	 model.addAttribute("landlordName", landlordName);
	    	 model.addAttribute("rentedPropertyAddress", rentedPropertyAddress);
	    	 model.addAttribute("checkOutAppointmentTime",checkOutAppointmentTime!=null  ?  sdf.format(checkOutAppointmentTime) : null);
	    	 model.addAttribute("nameOfEmployees", nameOfEmployees);
	    	 model.addAttribute("agentCompletingCheckOut", agentCompletingCheckOut);
	    	 model.addAttribute("damage", damage);
	    	 model.addAttribute("notes", notes);
	    	 model.addAttribute("frontDoor", frontDoor);
	    	 model.addAttribute("backDoor", backDoor);
	    	 model.addAttribute("tenantSignature", tenantSignature);
	    	 model.addAttribute("propertyName", propertyForm);
	    	 model.addAttribute("tenancyName", tenancyForm);
	    	 model.addAttribute("selectedRooms", sb.toString());
	    	 List<PropertyDetailsForm> propertyDetailsForm=new ArrayList<PropertyDetailsForm>();
	 		
	 		
	 		propertyDetailsForm.addAll(propertyDetailsFormService.findAllPropertyDetailsForm());
	 		propertyDetailsForm.add(checkingOutForm.getPropertyDetailsForm());
	 	
	 		
	 		model.addAttribute("properties", propertyDetailsForm);
			
		}
		
		else{
		model.addAttribute("properties", propertyDetailsFormService.findAllPropertyDetailsForm());
		}
		model.addAttribute("checkingoutId", checkingoutId);
		return "checkingOutForm";
	}
	
	@RequestMapping(value = "/formsDefinition", method = RequestMethod.GET)
	public String getFormDefinition(@RequestParam("formDefinitionId") long formDefinitionId, Model model,HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		/*model.addAttribute("submitVisible", "add");
		model.addAttribute("showCancel", false);*/
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
			/*Attribute attribute = dtAttributeService.findAttributeByOrder(1);
			Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());*/
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
			
			if(form.getName().equalsIgnoreCase("tenancyForm") || form.getName().equalsIgnoreCase("inventory") || form.getName().equalsIgnoreCase("checkingOutForm")){
				Forms forms=formService.findFormsByName("propertyDetailsForm");
				List<UserForms> userForms=userFormsService.findUserFormsByFormsAndStatus(forms, 's');
				List<Document> documents=new ArrayList<Document>();
				for(UserForms ufs:userForms){
					Document document=documentService.findDocumentByUserFormIdAndWip(String.valueOf(ufs.getId()));
					documents.add(document);
				}
				model.addAttribute("properties", documents);
				model.addAttribute("tab", "");
				 model.addAttribute("pictures", "");
			}
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
	
	/*@RequestMapping(value = "/showStaticJspForm", method = RequestMethod.GET)
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
	}*/

	

	@RequestMapping(value = "/showDraftForm", method = RequestMethod.GET)
	public String showDraftForm(@RequestParam("userFormId") long userFormId, Model model,HttpSession session) {

		User user = (User) session.getAttribute("LOGGED_IN_USER");
		//model.addAttribute("secGroups", securityGroupService.findAllSecurityGroups());
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		model.addAttribute("submitVisible", "add");
		model.addAttribute("showCancel", false);
		UserForms uf=userFormsService.findUserFormsById(userFormId);
		
		/*UserFormRef userFormRef=userFormRefService.findUserFormRefByUserFormId(uf);*/
		
		Forms form = formService.fetchFormDefinition(-1, userFormId);
		model.addAttribute("secGroups", securityGroupFormService.findSecurityGroupFormByformDefs(formDefsService.findFormDefsById(uf.getForms().getFormDefs().getId())));
		if (form != null) {
			
			/*if(uf.getFormTable().equalsIgnoreCase("PotentialTenantForm")){
				
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
				
				
			}*/
			/*if(uf.getFormTable().equalsIgnoreCase("PropertyDetailsForm")){
				
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
			*/
			
			
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
			
			
			/*if(uf.getFormTable().equalsIgnoreCase("TenancyForm")){
				
				TenancyForm tenancyForm=tenancyFormService.findTenancyFormByUserFormId(userFormId);
				String propertyAddress=tenancyForm.getPropertyAddress();
				String typeOfRental=tenancyForm.getTypeOfRental();
				String roomNumber=tenancyForm.getRoomNumber();
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
				
				TenantDetails td=tenantDetailsService.findTenantDetailsForTenancy(tenancyForm.getId());
				
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
		    	 
		    	
		    	 
		    	List<PropertyRooms> prs=propertyRoomsService.findRoomByPropertyDetails(tenancyForm.getPropertyDetailsForm());
		    	
		    	StringBuilder sb=new StringBuilder();
		    	for(PropertyRooms pr:prs){
		    		sb.append(pr.getRoomName()+",");
		    	}
		    	
		    	
		    	
		    	sb.append(tenancyForm.getRoomNumber());
		    	
		    	
		    	if(sb.length()>=1)
					sb.deleteCharAt(sb.length()-1);
		    	
		    	 model.addAttribute("roomnames", sb.toString());
		    	 
				
		    	
				
				
			}*/
			
			
			
			
			
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
			
			
			/*if(uf.getFormTable().equalsIgnoreCase("BuilderDetailsForm")){
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
		    	 
			}*/
			
			/*if(uf.getFormTable().equalsIgnoreCase("UtilitiesCompanyDetailsForm")){
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
		    	 
		    	 
			}*/
			
			/*if(uf.getFormTable().equalsIgnoreCase("Inventory")){
				Inventory inventory=inventoryService.findInventoryFormByUserFormId(userFormId);
				
				   	 String propertyForm=inventory.getPropertyForm();
				   	 String roomNameSel="";
				   	 String jsonobject="";
				   	 StringBuilder pictures=new StringBuilder();
		    	 
		    	
		    	 model.addAttribute("propertyName", propertyForm);
		    	 
		    	 StringBuilder sb1=new StringBuilder();
		    	
		    	 Document d=documentService.findDocumentByDocName(inventory.getPropertyForm());
					
					UserForms userForms=userFormsService.findUserFormsById(Long.parseLong(d.getUserFormId()));
					
					PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormByUserFormId(userForms.getId());
					
					//List<PropertyRooms> prs=propertyRoomsService.findAllRoomsForPropertyDetailsForm(pdf);
		    	 
		    	 for(PropertyRooms pr:prs){
						String tenantName="";
						String tenantAddress="";
						
						if(pr.getOccupiedBy()!=null){
							
							TenantDetails td=tenantDetailsService.findTenantDetailsForTenancy(pr.getOccupiedBy().getId());
							tenantName=td.getFirstName();
							tenantAddress=td.getCurrentAddress();
						}
						sb1.append(pr.getRoomName()+","+tenantName+","+tenantAddress+",");
					}
		    	 StringBuilder sb=new StringBuilder();
		    	 List<InventoryRooms> inventoryRooms=inventoryRoomsService.findInventoryRoomsForInventory(inventory);
		    	 for(InventoryRooms inRooms:inventoryRooms){
		    		 if(inRooms.getFieldName().equalsIgnoreCase("rooms")){
		    			 roomNameSel=inRooms.getFieldValue();
		    		 }
		    		 else  if(inRooms.getFieldName().equalsIgnoreCase("json")){
		    			 jsonobject=inRooms.getFieldValue();
		    		 }else if(inRooms.getFieldName().equals("photo1")){
		    			 sb.append(inRooms.getFieldName()+","+inRooms.getFieldValue()+",");
		    		 }
		    		 else if(inRooms.getFieldName().startsWith("photo")){
		    			 pictures.append(inRooms.getFieldName()+","+inRooms.getFieldValue()+","); 
		    		 }else if(!inRooms.getFieldName().equalsIgnoreCase("rooms") && !inRooms.getFieldName().equalsIgnoreCase("json")){
		    		 sb.append(inRooms.getFieldName()+","+inRooms.getFieldValue()+",");
		    		 }
		    	 }
		    	 
		    	 sb.deleteCharAt(sb.lastIndexOf(","));
		    	 model.addAttribute("tableValues", sb.toString());
		    	// model.addAttribute("roomNamesRet", sb1.toString());
		    	 model.addAttribute("roomNameSel", roomNameSel);
		    	 model.addAttribute("tab", jsonobject);
		    	 model.addAttribute("pictures", pictures.toString());
		    	 System.out.println(jsonobject);
		    	 System.out.println(sb.toString());
		    	 System.out.println(pictures.toString());
					
					List<PropertyRooms> prs=propertyRoomsService.findAllRoomsForPropertyDetailsForm(pdf);
					
					
					for(PropertyRooms pr:prs){
						sb1.append(pr.getRoomName()+",");
			}
					model.addAttribute("roomNamesRet", sb1.toString());
			}
			*/
			
			/*if(uf.getFormTable().equalsIgnoreCase("CheckingOutForm")){
				
				CheckingOutForm checkingOutForm=checkingOutFormService.findCheckingOutFormByUserId(userFormId);
				StringBuilder sb=new StringBuilder();
				 String tenantName=checkingOutForm.getTenantName();
		    	 String landlordName=checkingOutForm.getLandlordName();
		    	 String rentedPropertyAddress=checkingOutForm.getRentedPropertyAddress();
		    	 Date checkOutAppointmentTime=checkingOutForm.getCheckOutAppointmentTime();
		    	 String nameOfEmployees=checkingOutForm.getNameOfEmployees();
		    	 String agentCompletingCheckOut=checkingOutForm.getAgentCompletingCheckOut();
		    	 String damage=checkingOutForm.getDamage();
		    	 String notes=checkingOutForm.getNotes();
		    	 String frontDoor=checkingOutForm.getFrontDoor();
		    	 String backDoor=checkingOutForm.getBackDoor();
		    	// String roomNumbers=checkingOutForm.get;
		    	 String tenantSignature=checkingOutForm.getTenantSignature();
		    	 String propertyForm=checkingOutForm.getPropertyForm();
		    	 String tenancyForm=checkingOutForm.getTenancyForm();
		    	 
		    	 List<CheckingOutRooms> checkingOutRooms=checkingOutRoomsService.findCheckingOutRoomsByCheckingOutFormId(checkingOutForm);
				
		    	 for(CheckingOutRooms cr:checkingOutRooms){
		    		 sb.append(cr.getRoomId()+","+cr.getRoomName()+",");
		    	 }
		    	 
		    	 sb.deleteCharAt(sb.length()-1);
		    	 
		    	 model.addAttribute("tenantName", tenantName);
		    	 model.addAttribute("landlordName", landlordName);
		    	 model.addAttribute("rentedPropertyAddress", rentedPropertyAddress);
		    	 model.addAttribute("checkOutAppointmentTime",checkOutAppointmentTime!=null  ?  sdf.format(checkOutAppointmentTime) : null);
		    	 model.addAttribute("nameOfEmployees", nameOfEmployees);
		    	 model.addAttribute("agentCompletingCheckOut", agentCompletingCheckOut);
		    	 model.addAttribute("damage", damage);
		    	 model.addAttribute("notes", notes);
		    	 model.addAttribute("frontDoor", frontDoor);
		    	 model.addAttribute("backDoor", backDoor);
		    	 model.addAttribute("tenantSignature", tenantSignature);
		    	 model.addAttribute("propertyName", propertyForm);
		    	 model.addAttribute("tenancyName", tenancyForm);
		    	 model.addAttribute("selectedRooms", sb.toString());
				
			}*/
			
			
			if(form.getName().equalsIgnoreCase("tenancyForm") || form.getName().equalsIgnoreCase("inventory") || form.getName().equalsIgnoreCase("checkingOutForm")){
				Forms forms=formService.findFormsByName("propertyDetailsForm");
				List<UserForms> userForms=userFormsService.findUserFormsByFormsAndStatus(forms, 's');
				List<Document> documents=new ArrayList<Document>();
				for(UserForms ufs:userForms){
					Document document=documentService.findDocumentByUserFormIdAndWip(String.valueOf(ufs.getId()));
					documents.add(document);
				}
				model.addAttribute("properties", documents);
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
			
			
			/*Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
			//Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
			Set<AttributeValue> attr1ValuesRestricted = new HashSet<AttributeValue>();*/
			/*List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
			for(CompanyUser cu:cus){
				attr1ValuesRestricted.add(cu.getAttrValue());
			}
			model.addAttribute("compGroups", Util.getActiveAttributeValues(attr1ValuesRestricted));*/
			
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
	public String saveCaseFormDataForPotentialTenantForm(@RequestParam("potentialTenantId")long potentialTenantId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		User user=userService.findUserById(userInfo.getUserId());
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
        
        
      
        
        if(potentialTenantId==0){
        	PotentialTenantForm potentialTenantForm=new PotentialTenantForm(firstName, title, firstName, lastName, landLineNumber, mobileNumber, emailAddress, nationalInsuranceNumber, currentAddress, type, numberOfBedRooms, garden, offRoadParking, garage, other, 's', user, new Date(), null, null, null);
        	potentialTenantFormService.save(potentialTenantForm);
        	
        	UpdateAuditLogs("Created by ", "PotentialTenantForm", user);
        	
        }
        else{         	
        	PotentialTenantForm ptf=potentialTenantFormService.findPotentialTenantFormById(potentialTenantId);
        	ptf.setPotentialTenantTitle(firstName);
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
        	ptf.setStatus('s');
        	ptf.setUpdatedBy(user);
        	ptf.setUpdatedOn(new Date());
        	ptf.setUpdatedDetails(null);
        	potentialTenantFormService.save(ptf);
        	
        	UpdateAuditLogs("Modified by ", "PotentialTenantForm", user);
        	       	
        }
		
		return "refresh";
	}
	
	@RequestMapping(value = "/createPropertyDetails", method = RequestMethod.POST)
	public String saveCaseFormDataForPropertyDetailsForm(@RequestParam("propertyId")long propertyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		User user=userService.findUserById(userInfo.getUserId());
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
   	/* String bedroomDoor=null;
   	 String bedroomWindow=null;*/
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
             						/*if(!key.equals("formDefId") && !key.equals("secGrp") && !key.equals("submissionDate") && !key.equals("wrkFlw") && !key.equals("compGrp")){
             							System.out.println(value[0].toString());
             							roomDet.put(key, value[0].toString());
             						}*/
             						if(key.startsWith("bedroom")){
             							System.out.println(value[0].toString());
             							roomDet.put(key, value[0].toString());
             						}
             					}
             					
           	 
                       
               }
       }
     
       if(propertyId==0){
      /* FormDefs fds=formDefsService.findFormDefsById(formDefId);
       Forms forms=formService.fetchFormDefinition(formDefId, -1);*/
       
    	   PropertyDetailsForm propertyDetailsForm=new PropertyDetailsForm(client, firstName, lastName, landlineNumber, mobileNumber, emailAddress, address, accountNumber, accountHoldersName, sortCode, vatDetails, referencesForPayment, propertyAddressLine1, propertyAddressLine2, town, cityCountry, propertyPostCode, propertyType, propertyDescription, houseMeasurements, gasMeterLocation, electricMeterLocation, waterMeterLocation, dateOfPerchase, priceOfPurchase, estimatedValue, asOfDate, annualRent, pictures, localAuthority, rentalType, frontDoorKeyCode, backDoorKeyCode, porchDoorKeyCode, flatDoor, others, numberOfBedrooms, tenancyAgreement, insuranceCertificate, floorPlan, epcCertificate, gasCertificate, electricCertificate, hmoLicence, contractsAndWarranties, landRegistry, currentTenancyForm, propertyTimeline, linkToJobs, lendingInformation, managementCompany, companyName,
           		firstName, "Property Submitted", user, new Date(), null, null, null);
            propertyDetailsFormService.save(propertyDetailsForm);
            
            UpdateAuditLogs("Created by ", "PotentialTenantForm", user);
            
      for(int i=1;i<=numberOfBedrooms;i++){
   	   PropertyRooms propertyRooms=new PropertyRooms(propertyDetailsForm, "Room "+i , 'N', null);
   	   propertyRoomsService.save(propertyRooms);
      }
      
     Set<Entry<String, String>> setRoomDet=roomDet.entrySet();
     Iterator ite=setRoomDet.iterator();
     
     while(ite.hasNext()){
   	  Map.Entry<String,String> entry = (Map.Entry<String,String>)ite.next();

         String key             = entry.getKey();
         String value         = entry.getValue();
         
         RoomDetails roomDetails=new RoomDetails(propertyDetailsForm, key, value);
         roomDetailsService.save(roomDetails);
     }
      
      
      
     
       
       }else{
    	   
       	
       	PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
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
       	pdf.setPropertyTitle(firstName);
       	pdf.setUpdatedBy(user);
       	pdf.setUpdatedOn(new Date());
       	
       	pdf.setStatus("Property Submitted");
       	propertyDetailsFormService.save(pdf);
       	
       	UpdateAuditLogs("Modified by ", "PotentialTenantForm", user);
       	       	
       	List<PropertyRooms> propertyRooms=propertyRoomsService.findRoomByPropertyDetails(pdf);
    	propertyRoomsService.delete(propertyRooms);
    	
    	for(int i=1;i<=numberOfBedrooms;i++){
     	   PropertyRooms propertyRoom=new PropertyRooms(pdf, "Room "+i , 'N', null);
     	   propertyRoomsService.save(propertyRoom);
        }
       	
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
		
		
		return "refresh";
	}
	
	
	
	
	@RequestMapping(value = "/createTenancy", method = RequestMethod.POST)
	public String saveCaseFormDataForTenancyForm(@RequestParam("tenancyId")long tenancyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		
		User user=userService.findUserById(userInfo.getUserId());
		
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        
    	
    	
        
        String propertyAddress=null;
		String typeOfRental=null;
		StringBuilder roomNumber=new StringBuilder();
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
		PropertyDetailsForm propertyDetailsForm=null;
				
		Date tenancyFinishDate=null;
		String checkingInForm=null;
		int rentDueDate=1; 
		
        
       HashMap<String , String> roomDet=new HashMap<String,String>();
        while(it.hasNext()){
       	 
            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();
            
            System.out.println("Key is "+key);

            

                if(value.length>1){    
                    for (int i = 0; i < value.length; i++) {
                        System.out.println( value[i].toString().trim());
                        if(key.equals("roomNumber")){
                          	 if(value[i].toString()!="")
                          	 roomNumber.append(value[i].toString()+",");
                           }
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
                	roomNumber.append(value[0].toString()+",");
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
              					else if(key.equals("checkingInForm"))
              						checkingInForm=value[0].toString();
              					else if(key.equals("rentDueDate"))
              						rentDueDate=Integer.parseInt(value[0].toString());
            	 
              					else if(key.equals("tenancyFinishDate"))
              						tenancyFinishDate=!value[0].toString().equals("") ? sdf.parse(value[0].toString()): null;
              					
              					
                }
        }
        
        // User Creation on tenancy Form Creation
        User newUser=userService.findUserByNameAndEmail(tenantFirstName, tenantEmailAddress);
        
        if(newUser==null){
        newUser=new User(tenantFirstName, tenantFirstName, tenantEmailAddress, 'Y', tenantMobileNumber);
        Role role=roleService.findRoleByRoleName("Tenant");
        newUser.addToRoles(role);
        userService.saveUser(newUser);
        }
        
      
        if(tenancyId==0){
        propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(linkToProperty));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
        TenancyForm tenancyForm=new TenancyForm(propertyDetailsForm, tenantFirstName, propertyAddress, typeOfRental, roomNumber.toString(), landLordFirstName, landLordLastName, landLordAddress, landLordEmailAddress, landLordContactNumber, employerFirstName, employerLastName, employerAddress, employerEmailAddress, employerContactNumber, guarantorFirstName, guarantorLastName, guarantorAddress, guarantorEmailAddress, guarantorContactNumber, kinFirstName, kinLastName, kinAddress, kinEmailAddress, kinContactNumber, tenancyOpenedBy, tenancyStartedDate, tenancyClosedBy, tenancyClosedDate, checkingOutForm, checkingInForm, linkToProperty, linkToTenancyAgreement, linkToRentAccounts, legalNotification, tenancyDepositCertificate, councilTaxRegistration, additionalLinks, correspondenceWithTenants, correspondenceLinks, employerCompany, guarantorCompany, tenantTitle, tenantFirstName, tenantLastName, tenantDateOfBirth, tenantLandlineNumber, tenantMobileNumber, tenantEmailAddress, tenantNationalInsuranceNumber, tenantCurrentAddress, tenantPassport, tenantDriverLicense, tenantCreditCheck, tenantReferenceCheck, tenantAdditionalRemark, tenancyFinishDate, "Checked In", user, new Date(), null, null, null, rentDueDate, "Y");
        tenancyFormService.save(tenancyForm);
     
        UpdateAuditLogs("Created by ", "TenancyForm", user);
                
       if(typeOfRental.equals("wholeHouse")){
          	
         	 
  		 List<PropertyRooms> propertyRooms=propertyRoomsService.findRoomByPropertyDetails(propertyDetailsForm);
     	  for(PropertyRooms propRoom:propertyRooms){
     		  propRoom.setOccupiedBy(null);
     		  propRoom.setIsOccupied('N');
     		 propertyRoomsService.save(propRoom);
     	  }
     	  
     	  
     	  
     	  
     	  for(PropertyRooms pr:propertyRooms){
     		  pr.setIsOccupied('Y');
     		  pr.setOccupiedBy(tenancyForm);
     		  propertyRoomsService.save(pr);
     	  }
        }else{
     	  
      	  PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(linkToProperty));
      	  List<PropertyRooms> propRooms=propertyRoomsService.findRoomByTenancyDetails(tenancyForm);
         	  for(PropertyRooms propRoom:propRooms){
         		  propRoom.setOccupiedBy(null);
         		  propRoom.setIsOccupied('N');
         		 propertyRoomsService.save(propRoom);
         	  }
      	  String[] rooms=roomNumber.toString().trim().split(",");
      	  for(int i=0;i<=rooms.length-1;i++){
      		 PropertyRooms pr=propertyRoomsService.findRoomByPropertyDetailsAndRoomName(pdf, rooms[i]);
      		 if(pr!=null){
      		  pr.setIsOccupied('Y');
      		  pr.setOccupiedBy(tenancyForm);
      		  propertyRoomsService.save(pr);
      		 }
      	 }
      	  
        }
       TransactionDetails transactionDetails=new TransactionDetails(tenancyForm, propertyDetailsForm, "A", tenancyStartedDate, String.valueOf(propertyDetailsForm.getAnnualRent()), "n", newUser.getId(),"");
       transactionDetailService.save(transactionDetails);
       
       UpdateAuditLogs("Created by ", "Transaction Details", user);
     
        }else{
        	
        	TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(tenancyId);
        	tenancyForm.setPropertyAddress(propertyAddress);
        	tenancyForm.setTypeOfRental(typeOfRental);
        	tenancyForm.setRoomNumber(roomNumber.toString().trim());
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
        	tenancyForm.setTenantFirstName(tenantFirstName);
        	tenancyForm.setTenantMobileNumber(tenantMobileNumber);
        	tenancyForm.setTenantLastName(tenantLastName);
        	tenancyForm.setTenantLandlineNumber(tenantLandlineNumber);
        	tenancyForm.setTenantAdditionalRemarks(tenantAdditionalRemark);
        	tenancyForm.setTenantCreditCheck(tenantCreditCheck);
        	tenancyForm.setTenantCurrentAddress(tenantCurrentAddress);
        	tenancyForm.setTenantDateOfBirth(tenantDateOfBirth);
        	tenancyForm.setTenantDriversLicense(tenantDriverLicense);
        	tenancyForm.setTenantEmailAddress(tenantEmailAddress);
        	tenancyForm.setTenantNationalInsuranceNumber(tenantNationalInsuranceNumber);
        	tenancyForm.setTenantPassport(tenantPassport);
        	tenancyForm.setTenantReferenceCheck(tenantReferenceCheck);
        	tenancyForm.setTenantTitle(tenantTitle);
        	tenancyForm.setUpdatedBy(user);
        	tenancyForm.setUpdatedOn(new Date());
        	tenancyForm.setUpdatedDetails(null);
        	tenancyForm.setStatus("Checked In");
        	tenancyForm.setTenancyTitle(tenantFirstName);
        	tenancyForm.setCheckingInForm(checkingInForm);
        	tenancyForm.setRentDueDate(rentDueDate);
        	tenancyForm.setTenancyFinishDate(tenancyFinishDate);
        	tenancyForm.setIsOccupied("Y");

        	if(linkToProperty!=null){
            propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(linkToProperty));
            tenancyForm.setPropertyDetailsForm(propertyDetailsForm);
            
        	}
        	tenancyFormService.save(tenancyForm);
        	UpdateAuditLogs("Modified by ", "TenancyForm", user);
        	TransactionDetails transactionDetails=transactionDetailService.findTransactionByTenancyId(tenancyForm);
        	if(transactionDetails!=null){
        	transactionDetails.setTransactiontype("A");
        	transactionDetails.setTransactiondate(tenancyStartedDate);
        	transactionDetails.setAmount(String.valueOf(propertyDetailsForm.getAnnualRent()));
        	transactionDetails.setRentreceived("n");
        	transactionDetails.setUserId(newUser.getId());
        	
        	}else{
        		transactionDetails=new TransactionDetails(tenancyForm, propertyDetailsForm,
        				"A", tenancyStartedDate, String.valueOf(propertyDetailsForm.getAnnualRent()), "n", newUser.getId(), "");
        	}
    	    transactionDetailService.save(transactionDetails);
        	
        	 if(typeOfRental.equals("wholeHouse")){
           	
           	 
        		 List<PropertyRooms> propertyRooms=propertyRoomsService.findRoomByPropertyDetails(propertyDetailsForm);
           	  for(PropertyRooms propRoom:propertyRooms){
           		  propRoom.setOccupiedBy(null);
           		  propRoom.setIsOccupied('N');
           		 propertyRoomsService.save(propRoom);
           	  }
           	  
           	  
           	  
           	  
           	  for(PropertyRooms pr:propertyRooms){
           		  pr.setIsOccupied('Y');
           		  pr.setOccupiedBy(tenancyForm);
           		  propertyRoomsService.save(pr);
           	  }
              }else{
           	  
            	  PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(linkToProperty));
            	  List<PropertyRooms> propRooms=propertyRoomsService.findRoomByTenancyDetails(tenancyForm);
               	  for(PropertyRooms propRoom:propRooms){
               		  propRoom.setOccupiedBy(null);
               		  propRoom.setIsOccupied('N');
               		 propertyRoomsService.save(propRoom);
               	  }
            	  String[] rooms=roomNumber.toString().trim().split(",");
            	  for(int i=0;i<=rooms.length-1;i++){
            		 PropertyRooms pr=propertyRoomsService.findRoomByPropertyDetailsAndRoomName(pdf, rooms[i]);
            		 if(pr!=null){
            		  pr.setIsOccupied('Y');
            		  pr.setOccupiedBy(tenancyForm);
            		  propertyRoomsService.save(pr);
            		 }
            	 }
            	  
              }
        	 
        	 }
		return "refresh";
	}
	
	@RequestMapping(value = "/createBuilderDetail", method = RequestMethod.POST)
	public String saveCaseFormDataForBuilderDetails(@RequestParam("builderDetailsId")long builderDetailsId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		User user=userService.findUserById(userInfo.getUserId());
		
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
      
       User newUser=userService.findUserByNameAndEmail(firstName, emailAddress);
       
       if(newUser==null){
       newUser=new User(firstName, firstName, emailAddress, 'Y', mobileNumber);
       Role role=roleService.findRoleByRoleName("Builder");
       newUser.addToRoles(role);
       userService.saveUser(newUser);
       }
       
       
       if(builderDetailsId==-1){
      
     BuilderDetailsForm builderDetailsForm=new BuilderDetailsForm(firstName, firstName, lastName, emailAddress, landlineNumber, mobileNumber, companyName, companyAddress, companyNumber, vatNumber, accountHoldersName, accountNumber, sortCode, linkToInvoice, linkToJobs, 's', user, new Date(), null, null, null);
     builderDetailsFormService.save(builderDetailsForm);
     
     UpdateAuditLogs("Created by ", "BuilderDetailsForm", user);
       	
       }else{
       
       	BuilderDetailsForm builderDetailsForm=builderDetailsFormService.findBuilderDetailsFormById(builderDetailsId);
       	builderDetailsForm.setBuilderTitle(firstName);
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
       	builderDetailsForm.setUpdatedBy(user);
       	builderDetailsForm.setUpdatedOn(new Date());
       	builderDetailsForm.setUpdatedDetails(null);
       	builderDetailsForm.setStatus('s');
       	builderDetailsFormService.save(builderDetailsForm);
       	UpdateAuditLogs("Modified by ", "BuilderDetailsForm", user);
       }
             
        
		
		return "refresh";
	}

	@RequestMapping(value = "/createUtilitiesCompanyDetails", method = RequestMethod.POST)
	public String saveCaseFormDataForUtilitiesCompanyDeatils(@RequestParam("utilitiesCompanyId")long utilitiesCompanyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		User user=userService.findUserById(userInfo.getUserId());
		
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
       
       if(utilitiesCompanyId==-1){
       
   UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=new UtilitiesCompanyDetailsForm(companyName, companyName, companyType, companyTelephone, companyAddress, openingAndClosingTimes, mainContactName, mainContactTelephone, mainContactEmailAddress, 's', user, new Date(), null, null, null);
       	utilitiesCompanyDetailsFormService.save(utilitiesCompanyDetailsForm);
       	UpdateAuditLogs("Created by ", "UtilitiesCompanyDetailsForm", user);
       }else{
       	
       	UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=utilitiesCompanyDetailsFormService.findUtilitiesCompanyDetailsFormById(utilitiesCompanyId);
       	utilitiesCompanyDetailsForm.setCompanyName(companyName);
       	utilitiesCompanyDetailsForm.setCompanyAddress(companyAddress);
       	utilitiesCompanyDetailsForm.setCompanyType(companyType);
       	utilitiesCompanyDetailsForm.setCompanyTelephone(companyTelephone);
       	utilitiesCompanyDetailsForm.setOpeningAndClosingTimes(openingAndClosingTimes);
       	utilitiesCompanyDetailsForm.setMainContactName(mainContactName);
       	utilitiesCompanyDetailsForm.setMainContactTelephone(mainContactTelephone);
       	utilitiesCompanyDetailsForm.setMainContactEmailAddress(mainContactEmailAddress);
       	
       	utilitiesCompanyDetailsForm.setUpdatedBy(user);
       	utilitiesCompanyDetailsForm.setUpdatedOn(new Date());
       	utilitiesCompanyDetailsForm.setUpdatedDetails("");
       	utilitiesCompanyDetailsForm.setStatus('s');
       	utilitiesCompanyDetailsFormService.save(utilitiesCompanyDetailsForm);
       	UpdateAuditLogs("Modified by ", "UtilitiesCompanyDetailsForm", user);
       }
             
        
		
		return "refresh";
	}

	
	@RequestMapping(value = "/createInventory", method = RequestMethod.POST)
	public String saveCaseFormDataForInventory(@RequestParam("inventoryId")long inventoryId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		User user=userService.findUserById(userInfo.getUserId());
		
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        String tenantName=null;
   	 String tenantAddress=null;
   	 String room=null;
   	 String furnishingsList=null;
   	 String everythingOk=null;
   	 String notes=null;
   	 long propertyId=0;
   	// String photos=null;
   	// String inventoryTitle=null;
   	HashMap<String, String> inRoom=new HashMap<String,String>();
		
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
           	
           	 
           	 if(key.equals("tenantName"))
           		 tenantName=value[0].toString();
                
                else if(key.equals("tenantAddress"))   
               	 tenantAddress=value[0].toString();
                else if(key.equals("room"))   
               	 room=value[0].toString();
                else if(key.startsWith("furnishingsList"))   
               	 inRoom.put(key,value[0].toString());
               
                else if(key.equals("everythingOk"))
               	 everythingOk=value[0].toString();
                
               
                else if(key.equals("notes")) 
               	 notes=value[0].toString();
               
                else if(key.startsWith("photo"))	   
               	 inRoom.put(key,value[0].toString());
           	 
                else if(key.equals("json"))	   
               	 inRoom.put(key,value[0].toString());
                else if(key.equals("propertyForm"))	   
                  	 propertyId=Long.parseLong(value[0].toString());
           	
               
             	   
                }
       }
       
       
       if(inventoryId==0){
    	   PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
      
     Inventory inventory=new Inventory(propertyDetailsForm, tenantName, tenantName, tenantAddress, room, everythingOk, notes, 's', user, new Date(), null, null, "");
   inventoryService.save(inventory);
   Set roomSet = inRoom.entrySet();
   Iterator itRoom = roomSet.iterator();
   while(itRoom.hasNext()){
     	 
       Map.Entry<String,String> entry = (Map.Entry<String,String>)itRoom.next();

       String key             = entry.getKey();
       String value         = entry.getValue();
       
       InventoryRooms inventoryRooms=new InventoryRooms(inventory, key, value);
       inventoryRoomsService.save(inventoryRooms);
   }
   UpdateAuditLogs("Created by ", "InventoryForm", user);
   
       }else{
    	   PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
       	
       	Inventory inventory=inventoryService.findInventoryFormById(inventoryId);
       	inventory.setTenantName(tenantName);
       	inventory.setTenantAddress(tenantAddress);
       	inventory.setRoom(room);
       	inventory.setEverythingOk(everythingOk);
       	inventory.setNotes(notes);
       	inventory.setInventoryTitle(tenantName);
       	inventory.setStatus('s');
       	inventory.setUpdatedBy(user);
       	inventory.setUpdatedOn(new Date());
       	inventory.setUpdatedDetails(null);
       inventory.setPropertyDetailsForm(propertyDetailsForm);
       	inventoryService.save(inventory);
       	
       	List<InventoryRooms> invRooms=inventoryRoomsService.findInventoryRoomsForInventory(inventory);
       	inventoryRoomsService.delete(invRooms);
       	
       	Set roomSet = inRoom.entrySet();
           Iterator itRoom = roomSet.iterator();
           while(it.hasNext()){
             	 
               Map.Entry<String,String> entry = (Map.Entry<String,String>)itRoom.next();

               String key             = entry.getKey();
               String value         = entry.getValue();
               
               InventoryRooms inventoryRooms=new InventoryRooms(inventory, key, value);
               inventoryRoomsService.save(inventoryRooms);
           }
           UpdateAuditLogs("Modified by ", "InventoryForm", user);
       }
             
       
		return "refresh";
	}
	
	@RequestMapping(value = "/createCheckingOut", method = RequestMethod.POST)
	public String saveCaseFormDataForCheckingout(@RequestParam("checkingoutId")long checkingoutId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		
		User user=userService.findUserById(userInfo.getUserId());
		
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
       // System.out.println(jsonObjectParse.getKey()+" "+jsonObjectParse.getValue());
        
        
         String tenantName=null;
    	 String landlordName=null;
    	 String rentedPropertyAddress=null;
    	 Date checkOutAppointmentTime=null;
    	 String nameOfEmployees=null;
    	 String agentCompletingCheckOut=null;
    	 String damage=null;
    	 String notes=null;
    	 String frontDoor=null;
    	 String backDoor=null;
    	// String roomNumbers=null;
    	 String tenantSignature=null;
    	 String propertyForm=null;
    	 String tenancyForm=null;
        
         
    	HashMap<String, String> checkRoom=new HashMap<String,String>();
		
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
            	
            	 
            	
                if(key.startsWith("room"))   
                	 checkRoom.put(key,value[0].toString());
                 else if(key.equals("tenantName"))   
                	 tenantName=value[0].toString();
                 else if(key.equals("landlordName"))   
                	 landlordName=value[0].toString();
                 else if(key.equals("rentedPropertyAddress"))   
                	 rentedPropertyAddress=value[0].toString();
                 else if(key.equals("checkOutAppointmentTime"))   {
                	if(!value[0].toString().equals(""))
                	 checkOutAppointmentTime=sdf.parse(value[0].toString());
                 }
                 else if(key.equals("nameOfEmployees"))   
                	 nameOfEmployees=value[0].toString();
                 else if(key.equals("agentCompletingCheckOut"))   
                	 agentCompletingCheckOut=value[0].toString();
                 else if(key.equals("damage"))   
                	 damage=value[0].toString();
                 else if(key.equals("notes"))   
                	 notes=value[0].toString();
                 else if(key.equals("frontDoor"))   
                	 frontDoor=value[0].toString();
                 else if(key.equals("backDoor"))   
                	 backDoor=value[0].toString();
                 else if(key.equals("tenantSignature"))   
                	 tenantSignature=value[0].toString();
                 else if(key.equals("linkToProperty"))   
                	 propertyForm=value[0].toString();
                 else if(key.equals("linkToTenancy"))   
                	 tenancyForm=value[0].toString();
                 }
        }
       
        if(checkingoutId==0){
        
        
      
      PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(propertyForm));
      TenancyForm tenancy=tenancyFormService.findTenancyFormById(Long.parseLong(tenancyForm));
     
    
      
    CheckingOutForm checkingOutForm=new CheckingOutForm(tenantName, propertyDetailsForm, tenancy, tenantName, landlordName, rentedPropertyAddress, checkOutAppointmentTime, nameOfEmployees, agentCompletingCheckOut, damage, notes, frontDoor, backDoor, tenantSignature, 'd', user, new Date(), null, null, null);
    checkingOutFormService.save(checkingOutForm);
    UpdateAuditLogs("Created by ", "CheckingOutForm", user);
    
    tenancy.setIsOccupied("N");
    tenancy.setStatus("Checked Out");
    tenancy.setRoomNumber("");
    tenancyFormService.save(tenancy);
    
    List<PropertyRooms> prs=propertyRoomsService.findRoomByTenancyDetails(tenancy);
    
    for(PropertyRooms pr:prs){
    	pr.setOccupiedBy(null);
    	pr.setIsOccupied('N');
    	propertyRoomsService.save(pr);
    }
    
    Set checkRoomSet = checkRoom.entrySet();
    Iterator itRoom = checkRoomSet.iterator();
    while(itRoom.hasNext()){
      	 
        Map.Entry<String,String> entry = (Map.Entry<String,String>)itRoom.next();

        String key             = entry.getKey();
        String value         = entry.getValue();
        
       CheckingOutRooms checkingOutRooms=new CheckingOutRooms(checkingOutForm, key, value);
       checkingOutRoomsService.save(checkingOutRooms);
       
       
    }
    
    
        }else{
        	
            	
             
        	 PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(propertyForm));
             TenancyForm tenancy=tenancyFormService.findTenancyFormById(Long.parseLong(tenancyForm));
             
        	
             CheckingOutForm checkingOutForm=checkingOutFormService.findCheckingOutFormById(checkingoutId);
             checkingOutForm.setPropertyDetailsForm(propertyDetailsForm);
             checkingOutForm.setTenancy(tenancy);
             checkingOutForm.setTenantName(tenantName);
             checkingOutForm.setLandlordName(landlordName);
             checkingOutForm.setRentedPropertyAddress(rentedPropertyAddress);
             checkingOutForm.setCheckOutAppointmentTime(checkOutAppointmentTime);
             checkingOutForm.setNameOfEmployees(nameOfEmployees);
             checkingOutForm.setAgentCompletingCheckOut(agentCompletingCheckOut);
             checkingOutForm.setDamage(damage);
             checkingOutForm.setNotes(notes);
             checkingOutForm.setFrontDoor(frontDoor);
             checkingOutForm.setBackDoor(backDoor);
             checkingOutForm.setTenantSignature(tenantSignature);
             checkingOutForm.setUpdatedBy(user);
             checkingOutForm.setUpdatedOn(new Date());
             checkingOutForm.setUpdatedDetails(null);
             checkingOutForm.setCheckingOutTitle(tenantName);
             
        	checkingOutFormService.save(checkingOutForm);
        	UpdateAuditLogs("Modified by ", "CheckingOutForm", user);
        	tenancy.setStatus("Checked Out");
            tenancyFormService.save(tenancy);
            UpdateAuditLogs("Modified by ", "CheckingOutForm", user);
            UpdateAuditLogs("Modified by ", "TenancyForm", user);
        	
             List<CheckingOutRooms> checkingOutRooms=checkingOutRoomsService.findCheckingOutRoomsByCheckingOutFormId(checkingOutForm);
             checkingOutRoomsService.delete(checkingOutRooms);
             
             Set checkRoomSet = checkRoom.entrySet();
             Iterator itRoom = checkRoomSet.iterator();
             while(itRoom.hasNext()){
               	 
                 Map.Entry<String,String> entry = (Map.Entry<String,String>)itRoom.next();

                 String key             = entry.getKey();
                 String value         = entry.getValue();
                 
                CheckingOutRooms newCheckingOutRooms=new CheckingOutRooms(checkingOutForm, key, value);
                checkingOutFormService.save(checkingOutForm);
             }
        }
             
        
		
		return "refresh";
	}

	
	
	@RequestMapping(value = "/savePotentialTenantForDraft", method = RequestMethod.POST)
	public String savePotentialTenantFormToDraft(@RequestParam("potentialTenantId")long potentialTenantId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		

		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		User user=userService.findUserById(userInfo.getUserId());
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
        
        
      
        
        if(potentialTenantId==0){
        	PotentialTenantForm potentialTenantForm=new PotentialTenantForm(firstName, title, firstName, lastName, landLineNumber, mobileNumber, emailAddress, nationalInsuranceNumber, currentAddress, type, numberOfBedRooms, garden, offRoadParking, garage, other, 's', user, new Date(), null, null, null);
        	potentialTenantFormService.save(potentialTenantForm);
        	UpdateAuditLogs("Created by ", "PotentialTenantForm", user);
        }
        else{
        	
        	PotentialTenantForm ptf=potentialTenantFormService.findPotentialTenantFormById(potentialTenantId);
        	ptf.setPotentialTenantTitle(firstName);
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
        	ptf.setStatus('s');
        	ptf.setUpdatedBy(user);
        	ptf.setUpdatedOn(new Date());
        	ptf.setUpdatedDetails(null);
        	potentialTenantFormService.save(ptf);
        	UpdateAuditLogs("Modified by ", "PotentialTenantForm", user);
        }
		
		return "refresh";
	}
	
	@RequestMapping(value = "/savePropertyDetailsFormForDraft", method = RequestMethod.POST)
	public String saveProprtyDetailsFormToDraft(@RequestParam("propertyId")long propertyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		User user=userService.findUserById(userInfo.getUserId());
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
    	/* String bedroomDoor=null;
    	 String bedroomWindow=null;*/
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
              						/*if(!key.equals("formDefId") && !key.equals("secGrp") && !key.equals("submissionDate") && !key.equals("wrkFlw") && !key.equals("compGrp")){
              							System.out.println(value[0].toString());
              							roomDet.put(key, value[0].toString());
              						}*/
              						if(key.startsWith("bedroom")){
              							System.out.println(value[0].toString());
              							roomDet.put(key, value[0].toString());
              						}
              					}
              					
            	 
                        
                }
        }
        if(propertyId==0){
            /*FormDefs fds=formDefsService.findFormDefsById(formDefId);
            Forms forms=formService.fetchFormDefinition(formDefId, -1);*/
            
            PropertyDetailsForm propertyDetailsForm=new PropertyDetailsForm(client, firstName, lastName, landlineNumber, mobileNumber, emailAddress, address, accountNumber, accountHoldersName, sortCode, vatDetails, referencesForPayment, propertyAddressLine1, propertyAddressLine2, town, cityCountry, propertyPostCode, propertyType, propertyDescription, houseMeasurements, gasMeterLocation, electricMeterLocation, waterMeterLocation, dateOfPerchase, priceOfPurchase, estimatedValue, asOfDate, annualRent, pictures, localAuthority, rentalType, frontDoorKeyCode, backDoorKeyCode, porchDoorKeyCode, flatDoor, others, numberOfBedrooms, tenancyAgreement, insuranceCertificate, floorPlan, epcCertificate, gasCertificate, electricCertificate, hmoLicence, contractsAndWarranties, landRegistry, currentTenancyForm, propertyTimeline, linkToJobs, lendingInformation, managementCompany, companyName,
            		firstName, "Property Created", user, new Date(), null, null, null);
           
           propertyDetailsFormService.save(propertyDetailsForm);
           for(int i=1;i<=numberOfBedrooms;i++){
        	   PropertyRooms propertyRooms=new PropertyRooms(propertyDetailsForm, "Room "+i , 'N', null);
        	   propertyRoomsService.save(propertyRooms);
           }
           
          Set<Entry<String, String>> setRoomDet=roomDet.entrySet();
          Iterator ite=setRoomDet.iterator();
          
          while(ite.hasNext()){
        	  Map.Entry<String,String> entry = (Map.Entry<String,String>)ite.next();

              String key             = entry.getKey();
              String value         = entry.getValue();
              
              RoomDetails roomDetails=new RoomDetails(propertyDetailsForm, key, value);
              roomDetailsService.save(roomDetails);
          }
           
           
           
          
            
            }else{
         	   
            	
            	PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
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
            	pdf.setPropertyTitle(firstName);
            	pdf.setUpdatedBy(user);
            	pdf.setUpdatedOn(new Date());
            	pdf.setStatus("Property Created");
            	
            	propertyDetailsFormService.save(pdf);
            	
            	List<PropertyRooms> propertyRooms=propertyRoomsService.findRoomByPropertyDetails(pdf);
         	propertyRoomsService.delete(propertyRooms);
         	
         	for(int i=1;i<=numberOfBedrooms;i++){
          	   PropertyRooms propertyRoom=new PropertyRooms(pdf, "Room "+i , 'N', null);
          	   propertyRoomsService.save(propertyRoom);
             }
            	
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
       /* UserFormRef userFormRef=new UserFormRef(uf, "PotentialTenantForm");
        userFormRefService.save(userFormRef);*/
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
	public String saveTenancyFormToDraft(@RequestParam("tenancyId")long tenancyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
				UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
				
				User user=userService.findUserById(userInfo.getUserId());
				
				Map<String, String[]> formData = request.getParameterMap();
				Set s = formData.entrySet();
		        Iterator it = s.iterator();
				
		        
		    	
		    	
		        
		        String propertyAddress=null;
				String typeOfRental=null;
				StringBuilder roomNumber=new StringBuilder();
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
				PropertyDetailsForm propertyDetailsForm=null;
						
				Date tenancyFinishDate=null;
				String checkingInForm=null;
				int rentDueDate=1; 
				
		        
		       HashMap<String , String> roomDet=new HashMap<String,String>();
		        while(it.hasNext()){
		       	 
		            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

		            String key             = entry.getKey();
		            String[] value         = entry.getValue();
		            
		            System.out.println("Key is "+key);

		            

		                if(value.length>1){    
		                    for (int i = 0; i < value.length; i++) {
		                        System.out.println( value[i].toString().trim());
		                        if(key.equals("roomNumber")){
		                          	 if(value[i].toString()!="")
		                          	 roomNumber.append(value[i].toString()+",");
		                           }
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
		                	roomNumber.append(value[0].toString()+",");
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
		              					else if(key.equals("checkingInForm"))
		              						checkingInForm=value[0].toString();
		              					else if(key.equals("rentDueDate"))
		              						rentDueDate=Integer.parseInt(value[0].toString());
		            	 
		              					else if(key.equals("tenancyFinishDate"))
		              						tenancyFinishDate=!value[0].toString().equals("") ? sdf.parse(value[0].toString()): null;
		              					
		              					
		                }
		        }
		        
		        // User Creation on tenancy Form Creation
		        
		       /* User newUser=new User(tenantFirstName, tenantFirstName, tenantEmailAddress, 'Y', tenantMobileNumber);
		        
		        
		        Role role=roleService.findRoleByRoleName("Tenant");
		        newUser.addToRoles(role);
		        userService.saveUser(newUser);*/
		        
		        
		      
		        if(tenancyId==0){
		        propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(linkToProperty));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		        TenancyForm tenancyForm=new TenancyForm(propertyDetailsForm, tenantFirstName, propertyAddress, typeOfRental, roomNumber.toString(), landLordFirstName, landLordLastName, landLordAddress, landLordEmailAddress, landLordContactNumber, employerFirstName, employerLastName, employerAddress, employerEmailAddress, employerContactNumber, guarantorFirstName, guarantorLastName, guarantorAddress, guarantorEmailAddress, guarantorContactNumber, kinFirstName, kinLastName, kinAddress, kinEmailAddress, kinContactNumber, tenancyOpenedBy, tenancyStartedDate, tenancyClosedBy, tenancyClosedDate, checkingOutForm, checkingInForm, linkToProperty, linkToTenancyAgreement, linkToRentAccounts, legalNotification, tenancyDepositCertificate, councilTaxRegistration, additionalLinks, correspondenceWithTenants, correspondenceLinks, employerCompany, guarantorCompany, tenantTitle, tenantFirstName, tenantLastName, tenantDateOfBirth, tenantLandlineNumber, tenantMobileNumber, tenantEmailAddress, tenantNationalInsuranceNumber, tenantCurrentAddress, tenantPassport, tenantDriverLicense, tenantCreditCheck, tenantReferenceCheck, tenantAdditionalRemark, tenancyFinishDate, "Tenancy Created", user, new Date(), null, null, null, rentDueDate, "N");
		       
		       tenancyFormService.save(tenancyForm);
		     
		       if(typeOfRental.equals("wholeHouse")){
		          	
		         	 
		  		 List<PropertyRooms> propertyRooms=propertyRoomsService.findRoomByPropertyDetails(propertyDetailsForm);
		     	  for(PropertyRooms propRoom:propertyRooms){
		     		  propRoom.setOccupiedBy(null);
		     		  propRoom.setIsOccupied('N');
		     		 propertyRoomsService.save(propRoom);
		     	  }
		     	  
		     	  
		     	  
		     	  
		     	  for(PropertyRooms pr:propertyRooms){
		     		  pr.setIsOccupied('Y');
		     		  pr.setOccupiedBy(tenancyForm);
		     		  propertyRoomsService.save(pr);
		     	  }
		        }else{
		     	  
		      	  PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(linkToProperty));
		      	  List<PropertyRooms> propRooms=propertyRoomsService.findRoomByTenancyDetails(tenancyForm);
		         	  for(PropertyRooms propRoom:propRooms){
		         		  propRoom.setOccupiedBy(null);
		         		  propRoom.setIsOccupied('N');
		         		 propertyRoomsService.save(propRoom);
		         	  }
		      	  String[] rooms=roomNumber.toString().trim().split(",");
		      	  for(int i=0;i<=rooms.length-1;i++){
		      		 PropertyRooms pr=propertyRoomsService.findRoomByPropertyDetailsAndRoomName(pdf, rooms[i]);
		      		 if(pr!=null){
		      		  pr.setIsOccupied('Y');
		      		  pr.setOccupiedBy(tenancyForm);
		      		  propertyRoomsService.save(pr);
		      		 }
		      	 }
		      	  
		        }
		       
		     
		         
		     
		       
		        
		        }else{
		        	
		        	TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(tenancyId);
		        	tenancyForm.setPropertyAddress(propertyAddress);
		        	tenancyForm.setTypeOfRental(typeOfRental);
		        	tenancyForm.setRoomNumber(roomNumber.toString().trim());
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
		        	tenancyForm.setTenantFirstName(tenantFirstName);
		        	tenancyForm.setTenantMobileNumber(tenantMobileNumber);
		        	tenancyForm.setTenantLastName(tenantLastName);
		        	tenancyForm.setTenantLandlineNumber(tenantLandlineNumber);
		        	tenancyForm.setTenantAdditionalRemarks(tenantAdditionalRemark);
		        	tenancyForm.setTenantCreditCheck(tenantCreditCheck);
		        	tenancyForm.setTenantCurrentAddress(tenantCurrentAddress);
		        	tenancyForm.setTenantDateOfBirth(tenantDateOfBirth);
		        	tenancyForm.setTenantDriversLicense(tenantDriverLicense);
		        	tenancyForm.setTenantEmailAddress(tenantEmailAddress);
		        	tenancyForm.setTenantNationalInsuranceNumber(tenantNationalInsuranceNumber);
		        	tenancyForm.setTenantPassport(tenantPassport);
		        	tenancyForm.setTenantReferenceCheck(tenantReferenceCheck);
		        	tenancyForm.setTenantTitle(tenantTitle);
		        	tenancyForm.setUpdatedBy(user);
		        	tenancyForm.setUpdatedOn(new Date());
		        	tenancyForm.setUpdatedDetails(null);
		        	tenancyForm.setStatus("TenancyCreated");
		        	tenancyForm.setTenancyTitle(tenantFirstName);
		        	tenancyForm.setCheckingInForm(checkingInForm);
		        	tenancyForm.setRentDueDate(rentDueDate);
		        	tenancyForm.setTenancyFinishDate(tenancyFinishDate);
		        	

		        	
		            propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(linkToProperty));
		            tenancyForm.setPropertyDetailsForm(propertyDetailsForm);
		        	tenancyFormService.save(tenancyForm);
		        	
		        	 if(typeOfRental.equals("wholeHouse")){
		           	
		           	 
		        		 List<PropertyRooms> propertyRooms=propertyRoomsService.findRoomByPropertyDetails(propertyDetailsForm);
		           	  for(PropertyRooms propRoom:propertyRooms){
		           		  propRoom.setOccupiedBy(null);
		           		  propRoom.setIsOccupied('N');
		           		 propertyRoomsService.save(propRoom);
		           	  }
		           	  
		           	  
		           	  
		           	  
		           	  for(PropertyRooms pr:propertyRooms){
		           		  pr.setIsOccupied('Y');
		           		  pr.setOccupiedBy(tenancyForm);
		           		  propertyRoomsService.save(pr);
		           	  }
		              }else{
		           	  
		            	  PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(linkToProperty));
		            	  List<PropertyRooms> propRooms=propertyRoomsService.findRoomByTenancyDetails(tenancyForm);
		               	  for(PropertyRooms propRoom:propRooms){
		               		  propRoom.setOccupiedBy(null);
		               		  propRoom.setIsOccupied('N');
		               		 propertyRoomsService.save(propRoom);
		               	  }
		            	  String[] rooms=roomNumber.toString().trim().split(",");
		            	  for(int i=0;i<=rooms.length-1;i++){
		            		 PropertyRooms pr=propertyRoomsService.findRoomByPropertyDetailsAndRoomName(pdf, rooms[i]);
		            		 if(pr!=null){
		            		  pr.setIsOccupied('Y');
		            		  pr.setOccupiedBy(tenancyForm);
		            		  propertyRoomsService.save(pr);
		            		 }
		            	 }
		            	  
		              }
		        	 }
        
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
	public String saveBuilderDetailToDraft(@RequestParam("builderDetailsId")long builderDetailsId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		User user=userService.findUserById(userInfo.getUserId());
		
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
      
       if(builderDetailsId==-1){
      
     BuilderDetailsForm builderDetailsForm=new BuilderDetailsForm(firstName, firstName, lastName, emailAddress, landlineNumber, mobileNumber, companyName, companyAddress, companyNumber, vatNumber, accountHoldersName, accountNumber, sortCode, linkToInvoice, linkToJobs, 's', user, new Date(), null, null, null);
     
      builderDetailsFormService.save(builderDetailsForm);
     
       	
       }else{
       
       	BuilderDetailsForm builderDetailsForm=builderDetailsFormService.findBuilderDetailsFormById(builderDetailsId);
       	builderDetailsForm.setBuilderTitle(firstName);
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
       	builderDetailsForm.setUpdatedBy(user);
       	builderDetailsForm.setUpdatedOn(new Date());
       	builderDetailsForm.setUpdatedDetails(null);
       	builderDetailsForm.setStatus('s');
       	builderDetailsFormService.save(builderDetailsForm);
       	
       }
		
		return "refresh";
	}
	
	
	@RequestMapping(value = "/saveUtilitiesCompanyDetailsForDraft", method = RequestMethod.POST)
	public String saveUtilitiesCompanyDetailsToDraft(@RequestParam("utilitiesCompanyId")long utilitiesCompanyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		User user=userService.findUserById(userInfo.getUserId());
		
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
       
       if(utilitiesCompanyId==-1){
       
   UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=new UtilitiesCompanyDetailsForm(companyName, companyName, companyType, companyTelephone, companyAddress, openingAndClosingTimes, mainContactName, mainContactTelephone, mainContactEmailAddress, 'd', user, new Date(), null, null, null);
       	utilitiesCompanyDetailsFormService.save(utilitiesCompanyDetailsForm);
       }else{
       	
       	UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=utilitiesCompanyDetailsFormService.findUtilitiesCompanyDetailsFormById(utilitiesCompanyId);
       	utilitiesCompanyDetailsForm.setCompanyName(companyName);
       	utilitiesCompanyDetailsForm.setCompanyAddress(companyAddress);
       	utilitiesCompanyDetailsForm.setCompanyType(companyType);
       	utilitiesCompanyDetailsForm.setCompanyTelephone(companyTelephone);
       	utilitiesCompanyDetailsForm.setOpeningAndClosingTimes(openingAndClosingTimes);
       	utilitiesCompanyDetailsForm.setMainContactName(mainContactName);
       	utilitiesCompanyDetailsForm.setMainContactTelephone(mainContactTelephone);
       	utilitiesCompanyDetailsForm.setMainContactEmailAddress(mainContactEmailAddress);
       	
       	utilitiesCompanyDetailsForm.setUpdatedBy(user);
       	utilitiesCompanyDetailsForm.setUpdatedOn(new Date());
       	utilitiesCompanyDetailsForm.setUpdatedDetails("");
       	utilitiesCompanyDetailsForm.setStatus('d');
       	utilitiesCompanyDetailsFormService.save(utilitiesCompanyDetailsForm);
       	
       }
             
        
		
		return "refresh";
		
		
	}
	
	@RequestMapping(value = "/saveCheckingOutForDraft", method = RequestMethod.POST)
	public String saveCheckingOutToDraft(@RequestParam("checkingoutId")long checkingoutId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		//System.out.println("Security Grp Id"+secGrpId+"SubmissionDate "+submissionDate+"Model Id"+modelId+"Company ID"+compGrp);
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		
		User user=userService.findUserById(userInfo.getUserId());
		
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
       // System.out.println(jsonObjectParse.getKey()+" "+jsonObjectParse.getValue());
        
        
         String tenantName=null;
    	 String landlordName=null;
    	 String rentedPropertyAddress=null;
    	 Date checkOutAppointmentTime=null;
    	 String nameOfEmployees=null;
    	 String agentCompletingCheckOut=null;
    	 String damage=null;
    	 String notes=null;
    	 String frontDoor=null;
    	 String backDoor=null;
    	// String roomNumbers=null;
    	 String tenantSignature=null;
    	 String propertyForm=null;
    	 String tenancyForm=null;
        
         
    	HashMap<String, String> checkRoom=new HashMap<String,String>();
		
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
            	
            	 
            	
                if(key.startsWith("room"))   
                	 checkRoom.put(key,value[0].toString());
                 else if(key.equals("tenantName"))   
                	 tenantName=value[0].toString();
                 else if(key.equals("landlordName"))   
                	 landlordName=value[0].toString();
                 else if(key.equals("rentedPropertyAddress"))   
                	 rentedPropertyAddress=value[0].toString();
                 else if(key.equals("checkOutAppointmentTime"))   {
                	if(!value[0].toString().equals(""))
                	 checkOutAppointmentTime=sdf.parse(value[0].toString());
                 }
                 else if(key.equals("nameOfEmployees"))   
                	 nameOfEmployees=value[0].toString();
                 else if(key.equals("agentCompletingCheckOut"))   
                	 agentCompletingCheckOut=value[0].toString();
                 else if(key.equals("damage"))   
                	 damage=value[0].toString();
                 else if(key.equals("notes"))   
                	 notes=value[0].toString();
                 else if(key.equals("frontDoor"))   
                	 frontDoor=value[0].toString();
                 else if(key.equals("backDoor"))   
                	 backDoor=value[0].toString();
                 else if(key.equals("tenantSignature"))   
                	 tenantSignature=value[0].toString();
                 else if(key.equals("linkToProperty"))   
                	 propertyForm=value[0].toString();
                 else if(key.equals("linkToTenancy"))   
                	 tenancyForm=value[0].toString();
                 }
        }
       
        if(checkingoutId==0){
        
        
      
      PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(propertyForm));
      TenancyForm tenancy=tenancyFormService.findTenancyFormById(Long.parseLong(tenancyForm));
      
   CheckingOutForm checkingOutForm=new CheckingOutForm(tenantName, propertyDetailsForm, tenancy, tenantName, landlordName, rentedPropertyAddress, checkOutAppointmentTime, nameOfEmployees, agentCompletingCheckOut, damage, notes, frontDoor, backDoor, tenantSignature, 'd', user, new Date(), null, null, null);
    checkingOutFormService.save(checkingOutForm);
    
    Set checkRoomSet = checkRoom.entrySet();
    Iterator itRoom = checkRoomSet.iterator();
    while(itRoom.hasNext()){
      	 
        Map.Entry<String,String> entry = (Map.Entry<String,String>)itRoom.next();

        String key             = entry.getKey();
        String value         = entry.getValue();
        
       CheckingOutRooms checkingOutRooms=new CheckingOutRooms(checkingOutForm, key, value);
       checkingOutRoomsService.save(checkingOutRooms);
    }
    
    
        }else{
        	
            	
             
        	 PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(Long.parseLong(propertyForm));
             TenancyForm tenancy=tenancyFormService.findTenancyFormById(Long.parseLong(tenancyForm));
             
        	
             CheckingOutForm checkingOutForm=checkingOutFormService.findCheckingOutFormById(checkingoutId);
             checkingOutForm.setPropertyDetailsForm(propertyDetailsForm);
             checkingOutForm.setTenancy(tenancy);
             checkingOutForm.setTenantName(tenantName);
             checkingOutForm.setLandlordName(landlordName);
             checkingOutForm.setRentedPropertyAddress(rentedPropertyAddress);
             checkingOutForm.setCheckOutAppointmentTime(checkOutAppointmentTime);
             checkingOutForm.setNameOfEmployees(nameOfEmployees);
             checkingOutForm.setAgentCompletingCheckOut(agentCompletingCheckOut);
             checkingOutForm.setDamage(damage);
             checkingOutForm.setNotes(notes);
             checkingOutForm.setFrontDoor(frontDoor);
             checkingOutForm.setBackDoor(backDoor);
             checkingOutForm.setTenantSignature(tenantSignature);
             checkingOutForm.setUpdatedBy(user);
             checkingOutForm.setUpdatedOn(new Date());
             checkingOutForm.setUpdatedDetails(null);
             checkingOutForm.setCheckingOutTitle(tenantName);
             
        	checkingOutFormService.save(checkingOutForm);
        	
             List<CheckingOutRooms> checkingOutRooms=checkingOutRoomsService.findCheckingOutRoomsByCheckingOutFormId(checkingOutForm);
             checkingOutRoomsService.delete(checkingOutRooms);
             
             Set checkRoomSet = checkRoom.entrySet();
             Iterator itRoom = checkRoomSet.iterator();
             while(itRoom.hasNext()){
               	 
                 Map.Entry<String,String> entry = (Map.Entry<String,String>)itRoom.next();

                 String key             = entry.getKey();
                 String value         = entry.getValue();
                 
                CheckingOutRooms newCheckingOutRooms=new CheckingOutRooms(checkingOutForm, key, value);
                checkingOutFormService.save(checkingOutForm);
             }
        }
        
		return "refresh";
	}
	
	
	
	@RequestMapping(value = "/saveInventoryForDraft", method = RequestMethod.POST)
	public String saveInventoryToDraft(@RequestParam("inventoryId")long inventoryId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
		User user=userService.findUserById(userInfo.getUserId());
		
		Map<String, String[]> formData = request.getParameterMap();
		Set s = formData.entrySet();
        Iterator it = s.iterator();
		
        String tenantName=null;
   	 String tenantAddress=null;
   	 String room=null;
   	 String furnishingsList=null;
   	 String everythingOk=null;
   	 String notes=null;
   	 long propertyId=0;
   	// String photos=null;
   	// String inventoryTitle=null;
   	HashMap<String, String> inRoom=new HashMap<String,String>();
		
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
           	
           	 
           	 if(key.equals("tenantName"))
           		 tenantName=value[0].toString();
                
                else if(key.equals("tenantAddress"))   
               	 tenantAddress=value[0].toString();
                else if(key.equals("room"))   
               	 room=value[0].toString();
                else if(key.startsWith("furnishingsList"))   
               	 inRoom.put(key,value[0].toString());
               
                else if(key.equals("everythingOk"))
               	 everythingOk=value[0].toString();
                
               
                else if(key.equals("notes")) 
               	 notes=value[0].toString();
               
                else if(key.startsWith("photo"))	   
               	 inRoom.put(key,value[0].toString());
           	 
                else if(key.equals("json"))	   
               	 inRoom.put(key,value[0].toString());
                else if(key.equals("propertyForm"))	   
                  	 propertyId=Long.parseLong(value[0].toString());
           	
               
             	   
                }
       }
       
       
       if(inventoryId==0){
    	   PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
      
     Inventory inventory=new Inventory(propertyDetailsForm, tenantName, tenantName, tenantAddress, room, everythingOk, notes, 'd', user, new Date(), null, null, "");
   inventoryService.save(inventory);
   Set roomSet = inRoom.entrySet();
   Iterator itRoom = roomSet.iterator();
   while(itRoom.hasNext()){
     	 
       Map.Entry<String,String> entry = (Map.Entry<String,String>)itRoom.next();

       String key             = entry.getKey();
       String value         = entry.getValue();
       
       InventoryRooms inventoryRooms=new InventoryRooms(inventory, key, value);
       inventoryRoomsService.save(inventoryRooms);
   }
   
   
       }else{
    	   PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
       	
       	Inventory inventory=inventoryService.findInventoryFormById(inventoryId);
       	inventory.setTenantName(tenantName);
       	inventory.setTenantAddress(tenantAddress);
       	inventory.setRoom(room);
       	inventory.setEverythingOk(everythingOk);
       	inventory.setNotes(notes);
       	inventory.setInventoryTitle(tenantName);
       	inventory.setStatus('d');
       	inventory.setUpdatedBy(user);
       	inventory.setUpdatedOn(new Date());
       	inventory.setUpdatedDetails(null);
       inventory.setPropertyDetailsForm(propertyDetailsForm);
       	inventoryService.save(inventory);
       	
       	List<InventoryRooms> invRooms=inventoryRoomsService.findInventoryRoomsForInventory(inventory);
       	inventoryRoomsService.delete(invRooms);
       	
       	Set roomSet = inRoom.entrySet();
           Iterator itRoom = roomSet.iterator();
           while(it.hasNext()){
             	 
               Map.Entry<String,String> entry = (Map.Entry<String,String>)itRoom.next();

               String key             = entry.getKey();
               String value         = entry.getValue();
               
               InventoryRooms inventoryRooms=new InventoryRooms(inventory, key, value);
               inventoryRoomsService.save(inventoryRooms);
           }
       	
       }
		
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
				
		/*Document newForms=new Document(doctype,null, userInfo.getUserName(),finalFormName,"Forms", wfCase.getId(),new Date(),(Date) formMetaData.get(CaseUserForms.META_TARGET_DATE),null,'Y',userFormIdStr,'F');
		documentService.save(newForms);	*/
		
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
		
		/*long prevCompId=userForms.getCompanyId();
		long newCompId=compId;
		
		if(prevCompId!=newCompId)
			sb.append("Company "+" : "+prevCompId+" -> "+newCompId+"#");*/
		
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
			/*docUserForms.setName((String) formMetaData.get(CaseUserForms.META_DOCUMENT_NAME));*/
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
	
	
	/*CSV DOWNLOAD OF FORM*/
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/pdfDownloadForm", method = RequestMethod.GET)
	public void getFormPdf(@RequestParam("downloadTypehidden") String downloadTypehidden, HttpServletResponse response, HttpSession session) throws IOException, DocumentException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println("downloadTypeHidden from controller"); //downloadTypeHidden);
		System.out.println("downloadTypeHidden from controller>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ downloadTypehidden);
		
		response.setContentType("application/pdf");
		
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
	    long userId = userInfo.getUserId();

	    
		
		
		
		
		try {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		com.itextpdf.text.Document document=new com.itextpdf.text.Document();
		PdfWriter writer=PdfWriter.getInstance(document, baos);
		//PdfHeaderHelper pdfHeaderHelper = new PdfHeaderHelper();
	   
	    document.setPageSize(PageSize.LETTER);
	   
	    document.open();
	   
	    
	   
	    if(downloadTypehidden.equalsIgnoreCase("RentPaymentTransactionHistory"))
	    {
	    	System.out.println("Inside if of RentPaymentTransactionHistory");
		
					
		    //Header header = new Header(new Phrase("Add Header Part Here"));
		    		    
		    PdfPTable table = new PdfPTable(7);
		    table.setWidths(new int[]{15, 30,30,75,60,40,50});
		    PdfPCell cell = new PdfPCell();
		    cell.setColspan(7);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell.setPhrase(new Phrase("Abbottslangley Rent Receipt Detail"));
		    table.addCell(cell);
		    table.completeRow();
		    		    
		    cell.setColspan(1);
		    cell.setPhrase(new Phrase("S.No"));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase("Tenancy"));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase("Property"));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase("Transaction Type"));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase("Date"));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase("Amount"));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase("Rent Received"));
		    table.addCell(cell);
		    
		    table.completeRow();
		    
		    
		    
		    List<TransactionDetails> lstTransactionDetails = transactionDetailService.findTransactionbyUserid(userId);
		    if(lstTransactionDetails.size()>0)
		    {
		    	Iterator<TransactionDetails> fdi = lstTransactionDetails.iterator();
		    	long i=1;
				while (fdi.hasNext()) {
					
					
					TransactionDetails td = fdi.next();
					System.out.println(sdf.format(td.getTransactiondate()));
					table.addCell(String.valueOf(i++));
					table.addCell(String.valueOf(td.getTenancyId()));
					table.addCell(String.valueOf(td.getPropertyId()));
					table.addCell(td.getTransactiontype());
					table.addCell(sdf.format(td.getTransactiondate()));
					table.addCell(td.getAmount());
					if(td.getRentreceived().equalsIgnoreCase("y"))
					{
					table.addCell("Yes");
					}
					else if(td.getRentreceived().equalsIgnoreCase("n"))
					{
						table.addCell("No");	
					}
				table.completeRow();	
				}
		    }
		    else
		    {
		    	Iterator<TransactionDetails> fdi = lstTransactionDetails.iterator();
				while (fdi.hasNext()) {
					TransactionDetails td = fdi.next();
					table.addCell("No data availale");
					table.addCell("No data availale");
					table.addCell("No data availale");
					table.addCell("No data availale");
					table.addCell("No data availale");
					table.addCell("No data availale");
				table.completeRow();	
				}
		    }
		    
		    document.addTitle("Abbottslangley Rent Receipt Detail");
		    document.add(table);
		    //XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(pageHtml)); 
		   // HTMLWorker htmlWorker = new HTMLWorker(document);
		  //  htmlWorker.parse(new StringReader(pageHtml));
		   
		     
		
	    }
	    else if (downloadTypehidden.equalsIgnoreCase("Generic Rent Receipt"))
	    {
	    	System.out.println("Im in else if of Generic Rent Receipt");
	    	long totalPayable = 0, totalPaid = 0, balanceTobePaid=0;
	    	PdfPTable table = new PdfPTable(7);
	    	table.setWidths(new int[]{25, 40,40,50,75,40,40});
	    	PdfPCell cell = new PdfPCell();
		    cell.setColspan(7);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell.setPhrase(new Phrase("Generic Rent Receipt"));
		    table.addCell(cell);
		    table.completeRow();
		    
		    cell.setColspan(1);
		    cell.setPhrase(new Phrase("S.No"));
		    table.addCell(cell);
		    cell.setColspan(1);
		    cell.setPhrase(new Phrase("Tenancy"));
		    table.addCell(cell);
		    cell.setColspan(1);
		    cell.setPhrase(new Phrase("Property"));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase("Date"));
		    table.addCell(cell);
		    cell.setNoWrap(true);
		    cell.setPhrase(new Phrase("Transaction Mode"));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase("Payables"));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase("Received"));
		    table.addCell(cell);
		    table.completeRow();
		    
		  
		    
		    List<TransactionDetails> lstTransactionDetails = transactionDetailService.findTransactionbyUseridandRentReceivedEqualY(userId);
		    if(lstTransactionDetails.size()>0)
		    {
		    	Iterator<TransactionDetails> fdi = lstTransactionDetails.iterator();
		    	long i=1;
				while (fdi.hasNext()) {
					TransactionDetails td = fdi.next();
					table.addCell(String.valueOf(i++));
					table.addCell(String.valueOf(td.getTenancyId()));
					table.addCell(String.valueOf(td.getPropertyId()));
					table.addCell(sdf.format(td.getTransactiondate()));
					table.addCell(td.getTransactionMode());
					if(td.getTransactiontype().equalsIgnoreCase("d"))
					{
						table.addCell(td.getAmount());
						table.addCell("");
						totalPayable = totalPayable + Long.parseLong(td.getAmount()) ; 
					}
					if(td.getTransactiontype().equalsIgnoreCase("r"))
					{
						
						table.addCell("");
						table.addCell(td.getAmount());
						totalPaid = totalPaid + Long.parseLong(td.getAmount()) ;
						
					}	
					
				table.completeRow();
				}
				balanceTobePaid = totalPayable - totalPaid;
			
				cell.setColspan(7);
			    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    cell.setPhrase(new Phrase("Balance : "+ String.valueOf(balanceTobePaid)));
			    table.addCell(cell);
			    table.completeRow();
		    }
		    else
		    {
		    	Iterator<TransactionDetails> fdi = lstTransactionDetails.iterator();
				while (fdi.hasNext()) {
					TransactionDetails td = fdi.next();
					table.addCell("No data availale");
					table.addCell("No data availale");
					table.addCell("No data availale");
					table.addCell("No data availale");
					table.addCell("No data availale");
					table.addCell("No data availale");
					table.addCell("No data availale");
				table.completeRow();	
				}
	   
		}
		    document.add(table);
	    }
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
			
			
			
			/*if (edit.equals("edit")) {
				model.addAttribute("edit", null);
			}
			else {
				model.addAttribute("edit", edit);
			}
			model.addAttribute("docId", docId);*/
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

	/*@SuppressWarnings("unchecked")
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
	}*/
	
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
		/*AttributeValue attrv4 = (AttributeValue) session.getAttribute("attributeValue4");
		AttributeValue attrv5 = (AttributeValue) session.getAttribute("attributeValue5");*/
		Doctype doctype = doctypeService.findDoctypeById(docType);

		List<AttributeValue> attrVals = new ArrayList<AttributeValue>();
		attrVals.add(attrv1);
		attrVals.add(attrv2);
		attrVals.add(attrv3);
		/*attrVals.add(attrv4);
		attrVals.add(attrv5);*/
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
		/*Attribute attribute4 = dtAttributeService.findAttributeByOrder(4);
		Attribute attribute5 = dtAttributeService.findAttributeByOrder(5);*/

		AttributeValue attributeValue1 = (AttributeValue) session.getAttribute("attributeValue1");
		AttributeValue attributeValue2 = (AttributeValue) session.getAttribute("attributeValue2");
		AttributeValue attributeValue3 = (AttributeValue) session.getAttribute("attributeValue3");
		/*AttributeValue attributeValue4 = (AttributeValue) session.getAttribute("attributeValue4");
		AttributeValue attributeValue5 = (AttributeValue) session.getAttribute("attributeValue5");*/

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
			/*td.setAttributeValue4(attributeValue4.getId());
			td.setAttributeValue5(attributeValue5.getId());*/
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
			
			/*AttributeValue at=attributeValueDao.findAttributeValueById(105);
			System.out.println(at.getValue());*/
			
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
			
			List<ModelSummary> modelList=new ArrayList<ModelSummary>();
			for(ModelSummary modelSummary:modelService.listModelsByType('F')){
				if(modelSummary.getName().equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
					modelList.add(modelSummary);
				}
			}
			
			model.addAttribute("modelListsForm", formModels);
			model.addAttribute("formTypeId", formTypeId);
			model.addAttribute("modelLists",modelList);
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
			
			List<ModelSummary> modelList=new ArrayList<ModelSummary>();
			for(ModelSummary modelSummary:modelService.listModelsByType('F')){
				if(modelSummary.getName().equalsIgnoreCase((String) servletContext.getAttribute("noWorkflowModleNameForForms"))){
					modelList.add(modelSummary);
				}
			}
			
			model.addAttribute("modelListsForm", formModels);
			model.addAttribute("formTypeId", formTypeId);
			model.addAttribute("modelLists", modelList);
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
		
		@RequestMapping(value = "/tenancyRooms", method = RequestMethod.GET)
		@ResponseBody
		public String tenancyRooms(@RequestParam("propertyId")long propertyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
			
			
			
			StringBuilder sb=new StringBuilder();
			String typeOfRental=null;
			String propertyAddress=null;
			
			PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
			
			List<PropertyRooms> propertyRooms=propertyRoomsService.findRoomByPropertyDetails(propertyDetailsForm);
			/*List<PropertyRooms> propertyRoomsFormTenancy=null;
			
			
				
					TenancyForm tf=tenancyFormService.findTenancyFormByUserFormId(userFormId);
				propertyRoomsFormTenancy=propertyRoomsService.findRoomByTenancyDetailsIncludingVacantRooms(tf);*/
				for(PropertyRooms propertyRoom:propertyRooms){
					sb.append(propertyRoom.getRoomName()+",");
				}
				
			
			if(sb.length()>=1)
			sb.deleteCharAt(sb.length()-1);
			
			typeOfRental=propertyDetailsForm.getRentalType();
			propertyAddress=propertyDetailsForm.getPropertyAddressLine1()+"\n"+propertyDetailsForm.getPropertyAddressLine2();
			sb.append("/"+typeOfRental+"/"+propertyAddress);
			
			
			return sb.toString();
		}
		
		@RequestMapping(value = "/getTenancyForInventory", method = RequestMethod.GET)
		@ResponseBody
		public String getTenancy(@RequestParam("propertyId")long propertyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
			
			
			
			StringBuilder sb=new StringBuilder();
			
			
			PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
			
			List<PropertyRooms> prs=propertyRoomsService.findAllRoomsForPropertyDetailsForm(pdf);
			
			
			for(PropertyRooms pr:prs){
				String tenantName="";
				String tenantAddress="";
				
				/*if(pr.getOccupiedBy()!=null){
					
					TenantDetails td=tenantDetailsService.findTenantDetailsForTenancy(pr.getOccupiedBy().getId());
					tenantName=td.getFirstName();
					tenantAddress=td.getCurrentAddress();
				}*/
				
				sb.append(pr.getRoomName()+",");
			}
			if(sb.length()>=1)
				sb.deleteCharAt(sb.length()-1);
			
			
			return sb.toString();
		}
		
		@RequestMapping(value = "/getRoomsForTenancy", method = RequestMethod.GET)
		@ResponseBody
		public String getRoomsForTenancy(@RequestParam("tenancyName")long tenancyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
			
			
			
			StringBuilder sb=new StringBuilder();
			
				
			
			
		//	PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormByUserFormId(userForms.getId());
			
			TenancyForm tf=tenancyFormService.findTenancyFormById(tenancyId);
			
			List<PropertyRooms> prs=propertyRoomsService.findRoomByTenancyDetails(tf);
			//TenantDetails tds=null;
			for(PropertyRooms pr:prs){
				if(pr.getOccupiedBy()!=null){
				/*tds=tenantDetailsService.findTenantDetailsForTenancy(pr.getOccupiedBy().getId());
				UserForms ufs=userFormsService.findUserFormsById(pr.getOccupiedBy().getUserFormId());*/
				sb.append(pr.getRoomName()+",");
				}
			}
			/*if(sb.length()>=2){
			sb.deleteCharAt(sb.length()-1);
			}*/
			
			
			return sb.toString();
		}
		
		@RequestMapping(value = "/getTenancyDetailsForRoom", method = RequestMethod.GET)
		@ResponseBody
		public String getTenancyDetailsForRoom(@RequestParam("roomName")String roomName,Model model, HttpSession session, HttpServletRequest request) throws Exception {
			
			
			
			StringBuilder sb=new StringBuilder();
			
			PropertyRooms pr=propertyRoomsService.findPropertyRoomByRoomName(roomName);
			
			if(pr!=null){
				TenancyForm tf=pr.getOccupiedBy();
				if(tf!=null){
					/*TenantDetails td=tenantDetailsService.findTenantDetailsForTenancy(tf.getId());*/
					sb.append(tf.getTenantFirstName()+","+tf.getTenantCurrentAddress());
				}
			}
			
			
			return sb.toString();
		}
		
		
		@RequestMapping(value = "/getBuilderList", method = RequestMethod.GET)
		public String getBuilderList(Model model,HttpSession session) {
			StringBuilder sb = new StringBuilder();
			List<User> buildersList = 	roleService.findUsersForRoleName("Builder");
			System.out.println(">>>>>>>>>>>>>>>>>"+ buildersList.size());
			for(User builder: buildersList)
			{
				sb.append(builder.getUserName()).append(",");
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+ builder.getUserName());
			}
			return sb.toString();
		}
		
		@RequestMapping(value = "/getTenancyList", method = RequestMethod.GET)
		@ResponseBody
		public String getTenancyListForProperty(@RequestParam("propertyId")long propertyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
			
			
			
			StringBuilder sb=new StringBuilder();
			
			
			
			PropertyDetailsForm pdf=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
			
			List<TenancyForm> tfs=tenancyFormService.findTenancyFormByPropertyDetailsForm(pdf);
			
			List<PropertyRooms> prs=propertyRoomsService.findAllRoomsForPropertyDetailsForm(pdf);
			
			
			for(TenancyForm tf:tfs){
			
				
				
				sb.append(tf.getTenantFirstName()+","+tf.getId()+",");
		
		}
			if(sb.length()>1)
			sb.deleteCharAt(sb.length()-1);	
			
			return sb.toString();
		}
		
		
		
		@RequestMapping(value = "/getTenancyDetailsForCheckOutForm", method = RequestMethod.GET)
		@ResponseBody
		public String getTenancyDetailsForCheckingOut(@RequestParam("tenancyId")long tenancyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
			
			
			
			StringBuilder sb=new StringBuilder();
			
			
			
			TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(tenancyId);
			
			
			
			sb.append(tenancyForm.getTenantFirstName()+","+tenancyForm.getLandLordFirstName()+","+tenancyForm.getPropertyAddress()+","+tenancyForm.getPropertyDetailsForm().getFrontDoorKeyCode()+","+tenancyForm.getPropertyDetailsForm().getBackDoorKeyCode()+"-");
			
			List<PropertyRooms> prs=propertyRoomsService.findRoomByTenancyDetails(tenancyForm);
			
			for(PropertyRooms propertyRooms:prs){
				sb.append(propertyRooms.getRoomName()+",");
			}
			
			sb.deleteCharAt(sb.length()-1);
			
			return sb.toString();
		}
		
		
		@RequestMapping(value = "/getTenancyDetailsForGivingNotice", method = RequestMethod.GET)
		@ResponseBody
		public String getTenancyDetailsForGivingNoticeForm(@RequestParam("tenancyId")long tenancyId,Model model, HttpSession session, HttpServletRequest request) throws Exception {
			
			
			
			StringBuilder sb=new StringBuilder();
			
			
			
			TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(tenancyId);
			
		
			
			
			if(tenancyForm!=null)
			sb.append(tenancyForm.getTenantFirstName()+" "+tenancyForm.getTenantLastName()+","+tenancyForm.getTenantCurrentAddress()+",");
			
			if(sb.length()>1)
			sb.deleteCharAt(sb.length()-1);	
			
			return sb.toString();
		}
		
		@RequestMapping(value = "/getIsGivingNoticeStatus", method = RequestMethod.GET)
		@ResponseBody
		public String getIsGivingNoticeStatus( HttpSession session) {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			GivingNoticeForm givingNoticeForm=givingNoticeFormService.findGivingNoticeStatusByUserId(user);
			String isGivingNoticeStatus="";
			// required null check for givingNoticeForm before proceed to the next line
			if(givingNoticeForm!=null){
			 isGivingNoticeStatus = String.valueOf(givingNoticeForm.getIsGivenNotice());
			}
			else{
				isGivingNoticeStatus = "user not available";
			}
			System.out.println(">>>>>>>>>>>>>>>>>>>>>isGivingNoticeStatus>>>>>>>>>>>>>>>>>>"+ isGivingNoticeStatus);
			StringBuilder sb = new StringBuilder();
			sb.append(isGivingNoticeStatus);
			return sb.toString();	
		}
		
		@RequestMapping(value = "/createGivingNoticeFormDetail", method = RequestMethod.POST)
		public String saveCaseFormDataForGivingNoticeFormDetails(Model model, HttpSession session, HttpServletRequest request) throws Exception 
		{
			
			
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			Map<String, String[]> formData = request.getParameterMap();
			Set s = formData.entrySet();
	        Iterator it = s.iterator();
	        
	         long tenancyId = 0;
	    	 long propertyId = 0;
	    	 long rentaccountsId = 0;
	    	 String tenantName = null;
	    	 String address = null;
	    	 String leavingDate = null;
	    	 String forwardAddress = null;
	    	 String accountName = null;
	    	 String accountNumber = null;
	    	 String sortCode = null;
	    	 long userId = userInfo.getUserId();
	    	 
	    	 
	    	 while(it.hasNext()){
	          	 
	             Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

	             String key             = entry.getKey();
	             String[] value         = entry.getValue();
	             
	             System.out.println("Key is "+key);

	             

	                 if(value.length>1){    
	                     for (int i = 0; i < value.length; i++) 
	                     {
	                         System.out.println( value[i].toString());
	                       
	                     }
	                 }else
	                 {
	             	System.out.println("Value is "+value[0].toString());
	             	
	             	 
	             	 if(key.equals("tenancyId"))
	             		tenancyId=Long.parseLong(value[0].toString());
	                  else if(key.equals("propertyId"))   
	                	  propertyId=Long.parseLong(value[0].toString());
	                  else if(key.equals("rentaccountsId"))   
	                	  rentaccountsId=value[0].toString()!="" ? Long.parseLong(value[0].toString()):0;
	                  else if(key.equals("tenantName"))   
	                	  tenantName=value[0].toString();
	                  else if(key.equals("address"))
	                	  address=value[0].toString();
	                  else if(key.equals("leavingDate")) 
	                	  leavingDate=value[0].toString();
	                  else if(key.equals("forwardAddress"))	   
	                	  forwardAddress=value[0].toString();
	                  else if(key.equals("accountName"))	   
	                	  accountName=value[0].toString();
	                  else if(key.equals("accountNumber"))	   
	                	  accountNumber=value[0].toString();    
	                  else if(key.equals("sortCode"))	   
	                 	 sortCode=value[0].toString();
	                  }
	         }
	    	
	    	 TenancyForm tenancy=tenancyFormService.findTenancyFormById(tenancyId);
	    	 User newUser=null;
	    	 if(tenancy!=null){
	    	 tenancy.setStatus("Given Notice");
	    	 tenancy.setTenancyClosedDate(sdf.parse(leavingDate));
	    	 tenancy.setTenancyClosedBy(user.getUserName());
	    	    tenancyFormService.save(tenancy);
	    	    newUser=userService.findUserByNameAndEmail(tenancy.getTenantFirstName(), tenancy.getTenantEmailAddress());
	    	 }
	    	
	    	 
	    	GivingNoticeForm givingNoticeForm = new GivingNoticeForm(tenancyId, propertyId, rentaccountsId, tenantName, address, leavingDate,forwardAddress, accountName,accountNumber, sortCode, 'y', newUser);
	 	    givingNoticeFormService.save(givingNoticeForm);
	       
	 	    return "refresh";	 
		}
		
		@RequestMapping(value = "/GivingNoticeEqualsofY", method = RequestMethod.GET)
		@ResponseBody
		public String GivingNoticeEqualsofY( HttpSession session) {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			System.out.println(">>>>>>>>>>>ifGivingNoticeEqualsofY>>>>>>>>ifGivingNoticeEqualsofY");
			StringBuilder sb = new StringBuilder();
			GivingNoticeForm givingNoticeForm=givingNoticeFormService.findGivingNoticeStatusByUserId(user);
			sb.append(givingNoticeForm.getTenantName()+"|");
			sb.append(givingNoticeForm.getAddress()+"|");
			sb.append(givingNoticeForm.getLeavingDate()+"|");
			sb.append(givingNoticeForm.getForwardAddress()+"|");
			sb.append(givingNoticeForm.getAccountName()+"|");
			sb.append(givingNoticeForm.getAccountNumber()+"|");
			sb.append(givingNoticeForm.getSortCode()+"|");
			System.out.println(">>>>>>>>>>>sb>>>>>>>>"+sb);	
			return sb.toString();
		}
		
		@RequestMapping(value = "/createRentReceiptDetail", method = RequestMethod.POST)
		public String saveCaseFormDataForRentReceiptDetail(HttpSession session, HttpServletRequest request) throws Exception 
		{
			System.out.println(">>>>>>>>>>>>>>>>>>>>>I m in>>>>>>>createRentReceiptDetail");
			/*System.out.println(">>>>>>>>>>>>>>>>>>>>>formDefId is>>>>>>>"+ formDefId);*/
			
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
			Map<String, String[]> formData = request.getParameterMap();
			Set s = formData.entrySet();
	        Iterator it = s.iterator();
	        long currentLoginUser = userInfo.getUserId();
	        
	         long tenancyId=0;
	    	 long propertyId=0;
	    	 String paymenttype=null;
	    	 String reference=null;
	    	 String pay_date=null;
	    	 String rent_amount=null;
	    	 //String rentreceived=null;
	    	 
	    	 while(it.hasNext()){
	          	 
	             Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

	             String key             = entry.getKey();
	             String[] value         = entry.getValue();
	             
	             System.out.println("Key is "+key);

	             

	                 if(value.length>1){    
	                     for (int i = 0; i < value.length; i++) 
	                     {
	                         System.out.println( value[i].toString());
	                       
	                     }
	                 }else
	                 {
	             	System.out.println("Value is "+value[0].toString());
	             	
	             	 
	             	 if(key.equals("tenancyId"))
	             		tenancyId=Long.parseLong(value[0].toString());
	                  else if(key.equals("propertyId"))   
	                	  propertyId=Long.parseLong(value[0].toString());
	                  else if(key.equals("paymenttype"))   
	                	  paymenttype=value[0].toString();
	                  else if(key.equals("reference"))
	                	  reference=value[0].toString();
	                  else if(key.equals("pay_date")) 
	                	  pay_date=value[0].toString();
	                  else if(key.equals("rent_amount"))	   
	                	  rent_amount=value[0].toString();
	                   
	                  
	                  }
	         }
	    	 
	    	 
	    	 PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
	    	 TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(tenancyId);
	    	 
	    	 User user=userService.findUserByNameAndEmail(tenancyForm.getTenantFirstName(), tenancyForm.getTenantEmailAddress());
	 
	    RentReceipt rentReceipt = new RentReceipt(tenancyForm, propertyDetailsForm, paymenttype,  reference,  pay_date,  rent_amount, "y", user.getId());
	    rentReceiptService.save(rentReceipt);
	    
	    TransactionDetails transactionDetails = new TransactionDetails(tenancyForm, propertyDetailsForm, "R", sdf.parse(pay_date), rent_amount, "y", user.getId(),paymenttype);
	    transactionDetailService.save(transactionDetails);
	    
		return "refresh";
		}
		
		@RequestMapping(value = "/cancelRentReceiptDetail", method = RequestMethod.POST)
		public String cancelRentReceiptDetail(HttpSession session, HttpServletRequest request) throws Exception 
		{
			
			
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
			Map<String, String[]> formData = request.getParameterMap();
			Set s = formData.entrySet();
	        Iterator it = s.iterator();
			
	        long currentLoginUser = userInfo.getUserId();
	        
	         long tenancyId=0;
	    	 long propertyId=0;
	    	 String paymenttype=null;
	    	// String reference=null;
	    	 String pay_date=null;
	    	 String rent_amount=null;
	    	 //String rentreceived=null;
	    	 
	    	 while(it.hasNext()){
	          	 
	             Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

	             String key             = entry.getKey();
	             String[] value         = entry.getValue();
	             
	             System.out.println("Key is "+key);

	             

	                 if(value.length>1){    
	                     for (int i = 0; i < value.length; i++) 
	                     {
	                         System.out.println( value[i].toString());
	                       
	                     }
	                 }else
	                 {
	             	System.out.println("Value is "+value[0].toString());
	             	
	             	 
	             	 if(key.equals("tenancyId"))
	             		tenancyId=Long.parseLong(value[0].toString());
	                  else if(key.equals("propertyId"))   
	                	  propertyId=Long.parseLong(value[0].toString());
	                  else if(key.equals("paymenttype"))   
	                	  paymenttype=value[0].toString(); 
	                  else if(key.equals("pay_date")) 
	                	  pay_date=value[0].toString();
	                  else if(key.equals("rent_amount"))	   
	                	  rent_amount=value[0].toString();
	                   
	                  
	                  }
	    	 }
	    	 
	    	 PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(propertyId);
	    	 TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(tenancyId);
		
	    	 TransactionDetails transactionDetails = new TransactionDetails(tenancyForm, propertyDetailsForm, "R", sdf.parse(pay_date), rent_amount, "n", currentLoginUser,paymenttype);
	    	 
	    	 transactionDetailService.save(transactionDetails);
	    	 
		return "refresh";	
		}
		
		@RequestMapping(value = "/viewRentPaymentTransactionHistorybyUserID", method = RequestMethod.GET)
		public String viewRentPaymentTransactionHistorybyUserID( Model model,HttpSession session) {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			System.out.println("<<<<<<<<<<<<<<viewRentPaymentTransactionHistorybyUserID");
			 List<TransactionDetails> lstDetails = transactionDetailService.findTransactionbyUserid(user.getId());
			//UserForms uf = userFormsService.findUserFormsById(userFormId);
			String pageHtml = formService.createHtmlRepresentationforRentPaymentTransactionHistorybyUserID(user.getId());
			//System.out.println("Html:" +pageHtml);
			model.addAttribute("pageHtml", pageHtml);
			model.addAttribute("lstDetails_Size", String.valueOf(lstDetails.size()));
			//model.addAttribute("userFormId",userFormId);
			//model.addAttribute("userFormId", userFormId);
			return "renderHtml";
		}
		

		@RequestMapping(value = "/viewGivingNoticeForm", method = RequestMethod.GET)
		public String viewGivingNoticeForm(@RequestParam("tenancyId")long tenancyId, Model model,HttpSession session) {
			User user = (User) session.getAttribute("LOGGED_IN_USER");
			System.out.println("<<<<<<<<<<<<<</viewGivingNoticeForm>>>>>>>>>>>/viewGivingNoticeForm");
			//UserForms uf = userFormsService.findUserFormsById(userFormId);
			TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(tenancyId);
			User newUser=userService.findUserByNameAndEmail(tenancyForm.getTenantFirstName(), tenancyForm.getTenantEmailAddress());
			String pageHtml = formService.createHtmlRepresentationforViewGivingNoticeForm(newUser.getId());
			//System.out.println("Html:" +pageHtml);
			model.addAttribute("pageHtml", pageHtml);
			//model.addAttribute("userFormId",userFormId);
			//model.addAttribute("userFormId", userFormId);
			return "renderHtml";
		}
		
		@RequestMapping(value = "/showCheckOutForTenancy", method = RequestMethod.GET)
		public String getCheckingOutForTenancy(@RequestParam("checkingoutId") long checkingoutId, Model model,HttpSession session) {
			
			
			
			model.addAttribute("properties", propertyDetailsFormService.findAllPropertyDetailsForm());
			model.addAttribute("checkingoutId", checkingoutId);
			return "checkOutForTenancy";
		}
		
		
		@RequestMapping(value = "/savePhotoForm", method = RequestMethod.POST)
		public String savePhotoToLocal(@RequestParam("itemPhoto") MultipartFile file, @RequestParam("propertyForm") String property,
				@RequestParam("tenant") String tenant,Model model,HttpSession session, HttpServletRequest request) throws Exception{
			//System.out.println("inside");
			String path="";
			
			try{
				
			if(!file.isEmpty()){
				System.out.println("Inside the controller");
				String filePathLocal=(String) servletContext.getAttribute("foldername.inventoryRooms");
				File filePath=new File(filePathLocal);
				if(!filePath.exists()){
					System.out.println("creating inventory folder");
					filePath.mkdir();
				}
				byte[] bytes=file.getBytes();
				/*String fileName = file.getOriginalFilename();
				File fileSaveLocal= new File(filePathLocal+fileName);
				FileUtils.writeByteArrayToFile(fileSaveLocal, file.getBytes());
				model.addAttribute("path",filePathLocal+fileName);*/
				String rootPath=filePathLocal+property+"_"+tenant;
				File dir=new File(rootPath);
				if(!dir.exists()){
					dir.mkdir();
				}
				File imgFile = new File(dir.getAbsolutePath()+File.separator+file.getOriginalFilename());
				if(!imgFile.exists()){
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(imgFile));
					stream.write(bytes);
					stream.close();
					path=rootPath+file.getOriginalFilename();
//					request.setAttribute("path",rootPath+file.getOriginalFilename());
					model.addAttribute("path",rootPath+file.getName());
				}
				
				
			}
			}
			finally{
			String html = "<script language='javascript' type='text/javascript'> window.top.window.uploadPic('"+path+"');</script>";
				//System.out.println("****** html:" + html);
				// return hidMsgCreate;
				return html;
			}
			
		}
			
		
		@RequestMapping(value = "/deletePhoto", method = RequestMethod.GET)
		public void deletePhoto(@RequestParam("folderName") String folderName,@RequestParam("fileName") String fileName){
			String filePathLocal=(String) servletContext.getAttribute("foldername.inventoryRooms");
			File file=new File(filePathLocal+folderName+"/"+fileName);
			//File file=new File(rootPath.getAbsolutePath()+File.separator+fileName);
			if(file.exists()){
				file.delete();
			//	System.out.println("Deleted Successfully");
			}
		}
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/csvOfgetCsvofTransactionDetails", method = RequestMethod.POST)
		public void getCsvofTransactionDetails(@RequestParam("tenancy")long tenancyId,HttpServletResponse response, HttpSession session) {

			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>csvOfgetCsvofTransactionDetails");
			TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(tenancyId);
			User user=userService.findUserByNameAndEmail(tenancyForm.getTenantFirstName(), tenancyForm.getTenantEmailAddress());
			long userId = user.getId();
		    
		    long totalPayable = 0, totalPaid = 0, balanceTobePaid=0;
		    List<TransactionDetails> lstTransactionDetails = transactionDetailService.findTransactionbyUseridandRentReceivedEqualY(userId);
		    long i=1;
		    int rowNum=1;
		    
		    
		    
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Worksheet1");
			
			HSSFFont font = workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.WHITE.index);
			
			HSSFCellStyle cellStyleHeader = workbook.createCellStyle();
			cellStyleHeader.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyleHeader.setFont(font);
			
			HSSFCellStyle cellStylealignRight = workbook.createCellStyle();
			cellStylealignRight.setAlignment(cellStylealignRight.ALIGN_RIGHT);
			
			HSSFCellStyle cellStylealignLeft = workbook.createCellStyle();
			cellStylealignLeft.setAlignment(cellStylealignRight.ALIGN_LEFT);
			
			HSSFFont fontBold = workbook.createFont();
			fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			
			HSSFCellStyle cellStyleBold = workbook.createCellStyle();
			cellStyleBold.setFont(fontBold);
			
			HSSFRow rowHeader = worksheet.createRow(rowNum++);
			HSSFCell cellSno = rowHeader.createCell(1); cellSno.setCellValue("S No");cellSno.setCellStyle(cellStyleHeader);
			HSSFCell cellTenancy = rowHeader.createCell(2); cellTenancy.setCellValue("Tenancy");cellTenancy.setCellStyle(cellStyleHeader);
			HSSFCell cellProperty = rowHeader.createCell(3); cellProperty.setCellValue("Property");cellProperty.setCellStyle(cellStyleHeader);
			HSSFCell cellDate = rowHeader.createCell(4); cellDate.setCellValue("Date");cellDate.setCellStyle(cellStyleHeader);
			HSSFCell cellTransactionMode = rowHeader.createCell(5); cellTransactionMode.setCellValue("Transaction Mode");cellTransactionMode.setCellStyle(cellStyleHeader);
			HSSFCell cellPayables = rowHeader.createCell(6); cellPayables.setCellValue("Payables");cellPayables.setCellStyle(cellStyleHeader);
			HSSFCell cellReceived = rowHeader.createCell(7); cellReceived.setCellValue("Received");cellReceived.setCellStyle(cellStyleHeader);
			
			HSSFRow rowRecordWriter=null;
			HSSFCell cellSnoRecordWriter=null; 
			HSSFCell cellTenancyRecordWriter=null;
			HSSFCell cellPropertyRecordWriter=null;
			HSSFCell cellDateRecordWriter=null;
			HSSFCell cellTransactionModeRecordWriter=null;
			HSSFCell cellPayablesRecordWriter=null;
			HSSFCell cellReceivedRecordWriter=null;
			HSSFCell cellBalanceCaption=null;
			HSSFCell cellBalance=null;
			
			
			 if(lstTransactionDetails.size()>0)
			    {		    	
			    	Iterator<TransactionDetails> fdi = lstTransactionDetails.iterator();
			    	
					while (fdi.hasNext()) {
						System.out.println(">>>>>>>>>>>>>>>>>>>rowNum::::"+rowNum);
						rowRecordWriter = worksheet.createRow(rowNum++);
						TransactionDetails td = fdi.next();
						cellSnoRecordWriter = rowRecordWriter.createCell(1);cellSnoRecordWriter.setCellValue(i++);
						cellTenancyRecordWriter = rowRecordWriter.createCell(2);cellTenancyRecordWriter.setCellValue(td.getTenancyId().getTenancyTitle());
						cellPropertyRecordWriter = rowRecordWriter.createCell(3);cellPropertyRecordWriter.setCellValue(td.getPropertyId().getPropertyTitle());
						cellDateRecordWriter = rowRecordWriter.createCell(4);cellDateRecordWriter.setCellValue(td.getTransactiondate());
						cellTransactionModeRecordWriter = rowRecordWriter.createCell(5);cellTransactionModeRecordWriter.setCellValue(td.getTransactionMode());
						
						if(td.getTransactiontype().equalsIgnoreCase("d"))
						{
							cellPayablesRecordWriter = rowRecordWriter.createCell(6);cellPayablesRecordWriter.setCellValue(td.getAmount());cellPayablesRecordWriter.setCellStyle(cellStylealignRight);
							cellTransactionModeRecordWriter = rowRecordWriter.createCell(7);cellTransactionModeRecordWriter.setCellValue("");cellTransactionModeRecordWriter.setCellStyle(cellStylealignRight);
							totalPayable = totalPayable + Long.parseLong(td.getAmount()) ; 
						}
						if(td.getTransactiontype().equalsIgnoreCase("r"))
						{
							cellPayablesRecordWriter = rowRecordWriter.createCell(6);cellPayablesRecordWriter.setCellValue("");cellPayablesRecordWriter.setCellStyle(cellStylealignRight);
							cellTransactionModeRecordWriter = rowRecordWriter.createCell(7);cellTransactionModeRecordWriter.setCellValue(td.getAmount());cellTransactionModeRecordWriter.setCellStyle(cellStylealignRight);
							totalPaid = totalPaid + Long.parseLong(td.getAmount()) ;
						}		
					}
					balanceTobePaid = totalPayable - totalPaid;
					rowRecordWriter = worksheet.createRow(rowNum++);
					cellBalanceCaption = rowRecordWriter.createCell(6);cellBalanceCaption.setCellValue("Balance");cellBalanceCaption.setCellStyle(cellStyleBold);	
					cellBalance = rowRecordWriter.createCell(7);cellBalance.setCellValue(balanceTobePaid);	cellBalance.setCellStyle(cellStyleBold);
				}
			 else 
			 {
				 rowRecordWriter = worksheet.createRow(rowNum++);
				 cellSnoRecordWriter = rowRecordWriter.createCell(1);cellSnoRecordWriter.setCellValue(i++);
					cellTenancyRecordWriter = rowRecordWriter.createCell(2);cellTenancyRecordWriter.setCellValue("No data available");
					cellPropertyRecordWriter = rowRecordWriter.createCell(3);cellPropertyRecordWriter.setCellValue("No data available");
					cellDateRecordWriter = rowRecordWriter.createCell(4);cellDateRecordWriter.setCellValue("No data available");
					cellTransactionModeRecordWriter = rowRecordWriter.createCell(5);cellTransactionModeRecordWriter.setCellValue("No data available");
					cellPayablesRecordWriter = rowRecordWriter.createCell(6);cellPayablesRecordWriter.setCellValue("No data available");
					cellTransactionModeRecordWriter = rowRecordWriter.createCell(7);cellTransactionModeRecordWriter.setCellValue("No data available");
			 }
			    
			System.out.println("Before ServletOutputStream ");

		    try{	   
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=Generic_Rent_Receipt.xls");
		        ServletOutputStream out = response.getOutputStream();  
		        workbook.write(out); 
				out.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		    System.out.println("After ServletOutputStream ");
		    
		}
		
		@RequestMapping(value = "/generateRentReceiptbyUserID", method = RequestMethod.GET)
		public String generateRentReceiptbyUserID(@RequestParam("tenancy")long tenancyId,Model model, HttpSession session) {
			System.out.println("<<<<<<<<<<<<<<generateRentReceiptbyLoginUserID");
			UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
			TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(tenancyId);
			User user=userService.findUserByNameAndEmail(tenancyForm.getTenantFirstName(), tenancyForm.getTenantEmailAddress());
			long userId = user.getId();
			 List<TransactionDetails> lstDetails = transactionDetailService.findTransactionbyUseridandRentReceivedEqualY(userId);
			String pageHtml = formService.createHtmlRepresentationforRentReceiptbyUserID(userId);
			model.addAttribute("pageHtml", pageHtml);
			model.addAttribute("lstDetails_Size", String.valueOf(lstDetails.size()));
			model.addAttribute("tenancyId", tenancyId);
			return "renderHtml";
		}

		
		// Job Details
		
		@RequestMapping(value = "/createJobDetail", method = RequestMethod.POST)
		public String saveCaseFormDataForJobDetails(@RequestParam("jobId")long jobId,Model model, HttpSession session, HttpServletRequest request) throws Exception		
		{
			System.out.println(">>>>>>>>>>>>>>>>>>>>>I m in>>>>>>>createJobDetail");
			//System.out.println(">>>>>>>>>>>>>>>>>>>>>formDefId is>>>>>>>"+ formDefId);
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			UserInfo userInfo = (UserInfo) session.getAttribute("LOGGED_IN_USERINFO");
			
			User user=userService.findUserById(userInfo.getUserId());
			
			Map<String, String[]> formData = request.getParameterMap();
			Set s = formData.entrySet();
	        Iterator it = s.iterator();
			
	    	 Date dateRaised = null;
	    	 String jobNumber = null;
	    	 String descriptionOfJob = null;
	    	 String photoOfIssue = null;
	    	 String isUrgency=null;
	    	 Date appoinmentDate=null;
	    	 Date appoinmentTimeRange=null;
	    	 String photoOfFixes=null;
	    	 long linkOfPropertyForm=0;
	    	 long linkToTenancy=0;
	    	 long linkOfBuilderForm=0;
	    	 String linkToInvoiceForJob=null;
	    	 String linkToCompanyContactInfo=null;
	    	 String status=null;
	    	 User builder=null;
	    	 
	    	 String currentUserRole= null;
	    	 Set<Role>  roles=user.getRoles();
	 		for(Role r:roles)
	 		{
	 			//sbRole.append(r.getId()).append(",");
	 			System.out.println(">>>>>>>>>>Role::::"+r.getRoleName()+":::"+r.getId());
	 			if(r.getRoleName().equalsIgnoreCase("Admin"))
	 			{
	 				status = "Job Assigned";
	 				UpdateAuditLogs("Job Assinged by "+ r.getRoleName(), "New Job Form", user);
	 				break;
	 				
	 			}
	 			else if(r.getRoleName().equalsIgnoreCase("Tenant"))
	 			{
	 				status = "Job Created";
	 				UpdateAuditLogs("Job Created by "+ r.getRoleName(), "New Job Form", user);
	 				break;
	 			}
	 			else if(r.getRoleName().equalsIgnoreCase("Builder"))
	 			{
	 				builder = user;
	 				status = "Job Completed";
	 				UpdateAuditLogs("Job Completed by "+ r.getRoleName(), "New Job Form", user);
	 				break;
	 			}
	 		}	    	 
	    	 
	         while(it.hasNext()){
	          	 
	             Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

	             String key             = entry.getKey();
	             String[] value         = entry.getValue();
	             
	             System.out.println("Key is "+key);

	             

	                 if(value.length>1){   
	                	 System.out.println(">>>>>>>>>>>>>Control in value setting if PART ");
	                     for (int i = 0; i < value.length; i++) 
	                     {
	                    	 
	                    	 System.out.println("");
	                         System.out.println( value[i].toString());
	                       
	                     }
	                 }
	                 else
	                 {
	             	System.out.println("Value is "+value[0].toString());
	             	System.out.println(">>>>>>>>>>>>>Control in value setting else PART");
	             	 
	             	 if(key.equals("dateRaised"))
	             		dateRaised= null;
	                  
	                  else if(key.equals("jobNumber"))   
	                	  jobNumber=value[0].toString();
	                  else if(key.equals("descriptionOfJob"))   
	                	  descriptionOfJob=value[0].toString();
	                  else if(key.equals("photoOfIssue"))   
	                	  photoOfIssue=value[0].toString();
	                 
	                  else if(key.equals("isUrgency"))
	                	  isUrgency=value[0].toString();
	         
	                  else if(key.equals("appoinmentDate")) 
	                	  appoinmentDate= null; //sdf.parse(value[0].toString());
	                  else if(key.equals("appoinmentTimeRange"))	   
	                	  appoinmentTimeRange=   null; //sdf.parse(value[0].toString());
	                  else if(key.equals("f_5_5_PhotoOfFixes"))	   
	                	  photoOfFixes=value[0].toString();
	                  else if(key.equals("linkOfPropertyForm"))	   
	                	  linkOfPropertyForm= Long.parseLong(value[0].toString());
	                  else if(key.equals("linkToTenancy"))	   
	                	  linkToTenancy=Long.parseLong(value[0].toString());
	                  else if(key.equals("linkOfBuilderForm"))	   
	                	  linkOfBuilderForm=Long.parseLong(value[0].toString());
	                  else if(key.equals("linkToInvoiceForJob"))	   
	                	  linkToInvoiceForJob=value[0].toString();
	                  else if(key.equals("linkToCompanyContactInfo"))	   
	                	  linkToCompanyContactInfo=value[0].toString();
	                 
	             	}
	         }
	         	        
	        
	         PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(linkOfPropertyForm);
        	 TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(linkToTenancy);
	         builder = userService.findUserById(linkOfBuilderForm);
	         
	         if(jobId==0){
	      	    
	           JobDetailsForm jobDetailsForm = new JobDetailsForm(dateRaised, jobNumber, descriptionOfJob, photoOfIssue, isUrgency, appoinmentDate, appoinmentTimeRange, photoOfFixes, propertyDetailsForm, tenancyForm, linkToInvoiceForJob, linkToCompanyContactInfo, jobNumber, status, user, new Date(), null, null, null,builder);
	           jobDetailsFormService.save(jobDetailsForm);
	           
	           /*AuditLogs auditLogs = new AuditLogs("Job Details Form", "Save", new Date().toString(), userInfo.getUserId());
	           auditLogsService.save(auditLogs);*/
	           }else{
	              
	              	JobDetailsForm jobDetailsForm =jobDetailsFormService.findJobDetailsFormById(jobId);
	              	jobDetailsForm.setDateRaised(dateRaised);
	              	jobDetailsForm.setJobNumber(jobNumber);
	              	jobDetailsForm.setDescriptionOfJob(descriptionOfJob);
	              	jobDetailsForm.setPhotoOfIssue(photoOfIssue);
	              	jobDetailsForm.setIsUrgency(isUrgency);
	              	jobDetailsForm.setAppoinmentDate(appoinmentDate);
	              	jobDetailsForm.setAppoinmentTimeRange(appoinmentTimeRange);
	              	jobDetailsForm.setPhotoOfFixes(photoOfFixes);
	              	
	              	jobDetailsForm.setLinkOfPropertyForm(propertyDetailsForm);
	              	jobDetailsForm.setLinkOfTenantForm(tenancyForm);
	              	jobDetailsForm.setLinkToInvoiceForJob(linkToInvoiceForJob);
	              	jobDetailsForm.setLinkToCompanyContactInfo(linkToCompanyContactInfo);
	              	jobDetailsForm.setUpdatedBy(user);
	              	jobDetailsForm.setUpdatedOn(new Date());
	              	jobDetailsForm.setStatus(status);
	              	
	              	jobDetailsForm.setBuilder(builder);
	              	jobDetailsFormService.save(jobDetailsForm);
	               	
	               }
			return "refresh";
		}
		
		public void UpdateAuditLogs(String Action, String form, User user)
		{
			   for(Role r: user.getRoles())
		        {
		                if(r.getRoleName()!=null)
		                {
		                			auditLogsAction = Action + r.getRoleName();
		                			break;
		                }
		        }
		        auditLogs = new AuditLogs(form , Action, new Date(), user);
		        auditLogsService.save(auditLogs);
		}
		
}
	
