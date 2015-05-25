package uk.co.jmr.sdp.service.impl;

import java.text.SimpleDateFormat;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.chemistry.opencmis.commons.impl.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*import com.fasterxml.jackson.core.type.TypeReference;*/
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import uk.co.jmr.sdp.common.Utils;
import uk.co.jmr.sdp.dao.UserDao;
import uk.co.jmr.sdp.domain.BuilderDetailsForm;
import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.sdp.domain.GivingNoticeForm;
import uk.co.jmr.sdp.domain.Inventory;
import uk.co.jmr.sdp.domain.InventoryRooms;
import uk.co.jmr.sdp.domain.PotentialTenantForm;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.RoomDetails;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.TenantDetails;
import uk.co.jmr.sdp.domain.TransactionDetails;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.UtilitiesCompanyDetailsForm;
/*import uk.co.jmr.sdp.domain.UserFormRef;*/

import uk.co.jmr.sdp.domain.forms.FormDefinition;
import uk.co.jmr.sdp.domain.forms.FormFieldValues;
import uk.co.jmr.sdp.email.Attachment;
import uk.co.jmr.sdp.service.BuilderDetailsFormService;
import uk.co.jmr.sdp.service.EmailService;
import uk.co.jmr.sdp.service.FormService;
import uk.co.jmr.sdp.service.GivingNoticeFormService;
import uk.co.jmr.sdp.service.InspectionInventoryService;
import uk.co.jmr.sdp.service.InventoryRoomsService;
import uk.co.jmr.sdp.service.InventoryService;
import uk.co.jmr.sdp.service.PotentialTenantFormService;
import uk.co.jmr.sdp.service.PropertyDetailsFormService;
import uk.co.jmr.sdp.service.RoomDetailsService;
import uk.co.jmr.sdp.service.TenancyFormService;
import uk.co.jmr.sdp.service.TenantDetailsService;
import uk.co.jmr.sdp.service.TransactionDetailService;
import uk.co.jmr.sdp.service.UserService;
import uk.co.jmr.sdp.service.UtilitiesCompanyDetailsFormService;
/*import uk.co.jmr.sdp.service.UserFormRefService;*/
import uk.co.jmr.webforms.db.dao.ArraysDao;
import uk.co.jmr.webforms.db.dao.FormDataDao;
import uk.co.jmr.webforms.db.dao.FormDefsDao;
import uk.co.jmr.webforms.db.dao.FormsDao;
import uk.co.jmr.webforms.db.dao.UserFormsDao;
import uk.co.jmr.webforms.db.pojo.ArrayItems;
import uk.co.jmr.webforms.db.pojo.FormData;
import uk.co.jmr.webforms.db.pojo.FormDefs;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.HtmlFormDef;
import uk.co.jmr.webforms.db.pojo.UserForms;

@Service("formService")
public class FormServiceImpl implements FormService {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private FormDefsDao formDefsDao;
	@Autowired
	private FormsDao formsDao;
	@Autowired
	private UserFormsDao userFormsDao;
	@Autowired
	private FormDataDao formDataDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ArraysDao arraysDao;
	@Autowired
	private EmailService emailService;
	/*@Autowired
	private UserFormRefService userFormRefService;*/
	@Autowired
	private PotentialTenantFormService potentialTenantFormService;
	@Autowired
	private PropertyDetailsFormService propertyDetailsFormService;
	@Autowired
	private RoomDetailsService roomDetailsService;
	@Autowired
	private TenancyFormService tenancyFormService;
	@Autowired
	private TenantDetailsService tenantDetailsService;
	@Autowired
	private BuilderDetailsFormService builderDetailsFormService;
	@Autowired
	private UtilitiesCompanyDetailsFormService utilitiesCompanyDetailsFormService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private InspectionInventoryService inspectionInventoryService;
	@Autowired
	private InventoryRoomsService inventoryRoomsService;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private GivingNoticeFormService givingNoticeFormService;
	@Autowired
	private UserService userService;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormDefinition> formDefinitions() {

		List<FormDefinition> formDefList = new ArrayList<FormDefinition>();
		List<FormDefs> formDefs = formDefsDao.findAllFormDefs();
		Iterator<FormDefs> fdi = formDefs.iterator();
		while (fdi.hasNext()) {
			FormDefs fd = fdi.next();
			FormDefinition formDef = new FormDefinition(fd.getId(), fd.getName(), fd.getDescription(), fd.getActive());
			formDefList.add(formDef);
		}

		return formDefList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getFormDefinitionId(long userFormId) {

		return userFormsDao.findUserFormsById(userFormId).getForms().getId();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Forms fetchFormDefinition(long formDefinitionId, long userFormId) {

		Forms f = null;
		if (userFormId == -1) {
			Set<Forms> fl = formDefsDao.findFormDefsById(formDefinitionId).getFormses();
			if (fl != null && !fl.isEmpty()) {
				f = fl.iterator().next();
			}
		}
		else {
			f = userFormsDao.findUserFormsById(userFormId).getForms();
		}

		if (f == null || f.getHandCodedPath() == null) {
			f = null;
		}

		return f;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int fetchDraftFormCount(long userid) {

		int count = userFormsDao.findUserFormsByUserIdByStatus(userid, 'd').size();
		return count;
	}

	private String[] deDupe(String[] s) {

		ArrayList<String> ar = new ArrayList<String>();
		for (int i = 0; i < s.length; i++) {
			if (!ar.contains(s[i])) {
				ar.add(s[i]);
			}
		}

		return ar.toArray(new String[0]);
	}

	@Override
	//Commented Transactional since its throws exception in returning the userform.getId()
	//@Transactional(propagation = Propagation.REQUIRED) 
	public long saveFormData(long userId, Map<String, String[]> formData) {

		String[] s = formData.get("_userFormId");
		long userFormId = -1;
		long formId = -1;
		UserForms userForms = null;
		if (s != null && s.length != 0) {
			userFormId = uk.co.jmr.sdp.common.Utils.parseLong(s[0], -1L);
		}

		if (userFormId != -1) {
			userForms = userFormsDao.findUserFormsById(userFormId);
		}
		else {
			s = formData.get("_formId");
			if (s != null && s.length != 0) {
				formId = uk.co.jmr.sdp.common.Utils.parseLong(s[0], -1L);
			}

			if (formId != -1) {
				userForms = new UserForms();
				userForms.setUserId(userId);
				userForms.setForms(formsDao.findFormsById(formId));
				userForms.setStatus('d');
			}
		}

		if (userForms == null) {
			return -1;
		}

		/*
		 * OK, Have a UserForm either existing or new
		 */
		Set<FormData> currentData = userForms.getFormDatas();
		Iterator<FormData> fdi = currentData.iterator();
		HashMap<String, FormData> existingMap = new HashMap<String, FormData>();
		while (fdi.hasNext()) {
			FormData f = fdi.next();
			existingMap.put(generateHashKey(f.getHtmlFormDef().getName(), f.getVindex()), f);
		}

		Iterator<HtmlFormDef> hfdi = userForms.getForms().getHtmlFormDefs().iterator();
		ArrayList<FormData> newData = new ArrayList<FormData>();
		while (hfdi.hasNext()) {
			HtmlFormDef hfd = hfdi.next();
			String fieldName = hfd.getName();
			s = formData.get(fieldName);
			if (s == null || s.length == 0) {
				continue;
			}

			if (s.length > 1) {
				s = deDupe(s);
			}

			for (int i = 0; i < s.length; i++) {
				String value = s[i];
				FormData f = existingMap.remove(generateHashKey(fieldName, i));
				if (f == null) {
					f = new FormData();
					f.setHtmlFormDef(hfd);
					f.setUserForms(userForms);
					f.setVindex(i);
					newData.add(f);
				}

				stringToFormDataValue(f, value);
			}
		}
		// remove the old unused values
		fdi = existingMap.values().iterator();
		while (fdi.hasNext()) {
			FormData f = (FormData) fdi.next();
			currentData.remove(f);
			formDataDao.delete(f);
		}

		// add the new values
		currentData.addAll(newData);
		userFormsDao.save(userForms);
		//System.out.println(userForms.getId());
		return userForms.getId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUserFormStatus(long userFormId, char status) {

		UserForms uf = userFormsDao.findUserFormsById(userFormId);
		uf.setStatus(status);
		userFormsDao.save(uf);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeUserForm(long userFormId) {

		UserForms uf = userFormsDao.findUserFormsById(userFormId);
		userFormsDao.delete(uf);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormFieldValues> getUserFormData(long formId, long userFormId) {

		HashMap<String, ArrayList<String>> currentData = new HashMap<String, ArrayList<String>>();
		ArrayList<FormFieldValues> userData = new ArrayList<FormFieldValues>();
		String fieldName;
		if (userFormId != -1) {
			UserForms uf = userFormsDao.findUserFormsById(userFormId);
			formId = uf.getForms().getId();
			Iterator<FormData> fdi = uf.getFormDatas().iterator();
			while (fdi.hasNext()) {
				FormData fd = fdi.next();
				fieldName = fd.getHtmlFormDef().getName();
				ArrayList<String> ar = currentData.get(fieldName);
				if (ar == null) {
					ar = new ArrayList<String>();
					currentData.put(fieldName, ar);
				}

				ar.add(formDataValueToString(fd));
			}
		}

		Iterator<HtmlFormDef> hfdi = formsDao.findFormsById(formId).getHtmlFormDefs().iterator();
		while (hfdi.hasNext()) {
			HtmlFormDef hfd = hfdi.next();
			fieldName = hfd.getName();
			String[] values = null;
			if (currentData.containsKey(fieldName)) {
				values = currentData.remove(fieldName).toArray(new String[0]);
			}

			userData.add(new FormFieldValues(fieldName, values));
		}

		return userData;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserForms> getFromsByStatus(long userId, char status) {

		List<UserForms> al = userFormsDao.findUserFormsByUserIdByStatus(userId, 'd');
		return al;
	}

	/*
	 * Just a helper function to ensure we use same key for Hashmap in the
	 * saveFormData method
	 */
	private String generateHashKey(String s, int i) {

		return (s + "[" + i + "]");
	}

	void stringToFormDataValue(FormData fd, String value) {

		String type = fd.getHtmlFormDef().getFormFieldMap().getFields().getFieldTypes().getName();
		if (type.equals("integer")) {
			fd.setIntValue(uk.co.jmr.sdp.common.Utils.parseLong(value, -1));
		}
		else if (type.equals("number")) {
			fd.setNumberValue(uk.co.jmr.sdp.common.Utils.parseDouble(value, -1.0));
		}
		else if (type.equals("date")) {
			fd.setDateValue(uk.co.jmr.sdp.common.Utils.parseDate(value));
		}
		else if (type.equals("timestamp")) {
			fd.setDateValue(uk.co.jmr.sdp.common.Utils.parseTimestamp(value));
		}
		else {
			fd.setTextValue(value);
		}
	}

	String formDataValueToString(FormData fd) {

		String type = fd.getHtmlFormDef().getFormFieldMap().getFields().getFieldTypes().getName();
		String s;
		if (type.equals("integer")) {
			s = Long.toString(fd.getIntValue());
		}
		else if (type.equals("number")) {
			s = Double.toString(fd.getNumberValue());
		}
		else if (type.equals("date")) {
			s = uk.co.jmr.sdp.common.Utils.formatDate(fd.getDateValue());
		}
		else if (type.equals("timestamp")) {
			s = uk.co.jmr.sdp.common.Utils.formatTimestamp(fd.getDateValue());
		}
		else {
			s = fd.getTextValue();
		}

		return s;
	}

	/*
	 * This method needs to be done again to be generic, (use the elemnt nam eto
	 * work out which hndler provides the actual data
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<String> getDialogData(String element) {

		ArrayList<String> data;
		if (element.equals("f_1_5") || element.equals("f_1_7")) {
			// staff list
			Iterator<User> iterator = userDao.findAllUsers().iterator();
			data = new ArrayList<String>();
			while (iterator.hasNext()) {
				String s = iterator.next().getUserName();
				if (!data.contains(s)) {
					data.add(s);
				}
			}
		}
		else {
			String name;
			if (element.equals("f_1_13_15") || element.equals("f_1_8")) {
				// Hospitality Location list
				name = "locations";
			}
			else if (element.equals("f_1_10")) {
				// Required equipment list
				name = "equipmentList";
			}
			else if (element.equals("f_4_2")) {
				// Required equipment list
				name = "projectManager";
			}
			else // (f_1_13_16)
			{
				// Hospitality requirement
				name = "hospitalityRequirement";
			}
			Iterator<ArrayItems> iterator = arraysDao.findArraysByName(name).getArrayItemses().iterator();
			data = new ArrayList<String>();
			while (iterator.hasNext()) {
				String s = iterator.next().getValue();
				if (!data.contains(s)) {
					data.add(s);
				}
			}
		}

		return data;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, Object> getFormMetaData(long userFormId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		Iterator<FormData> fdi = userFormsDao.findUserFormsById(userFormId).getFormDatas().iterator();
		while (fdi.hasNext()) {
			FormData fd = fdi.next();
			if (fd.getHtmlFormDef().getName().equals("f_1_2") || fd.getHtmlFormDef().getName().equals("f_3_2") || fd.getHtmlFormDef().getName().equals("f_5_7") || fd.getHtmlFormDef().getName().equals("f_4_1")) {
				map.put(CaseUserForms.META_DOCUMENT_NAME, fd.getTextValue());
			}
			else if (fd.getHtmlFormDef().getName().equals("f_1_3")|| fd.getHtmlFormDef().getName().equals("f_3_10") || fd.getHtmlFormDef().getName().equals("f_5_12") || fd.getHtmlFormDef().getName().equals("f_4_8")) {
				map.put(CaseUserForms.META_TARGET_DATE, fd.getDateValue());
			}
			else if (fd.getHtmlFormDef().getName().equals("f_1_5") || fd.getHtmlFormDef().getName().equals("f_3_9_16") || fd.getHtmlFormDef().getName().equals("f_4_2")) {
				map.put(CaseUserForms.META_AUTHOR, fd.getTextValue());
			}
		}

		return map;
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

	

	/*@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String createHtmlRepresentation(UserForms userForm) {

		StringBuilder sb = new StringBuilder();
		Iterator<FormData> fdi = userForm.getFormDatas().iterator();
		String lastField = null;
		sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p>")
			.append(userForm.getForms().getFormDefs().getName()).append(" - ")
			.append(userForm.getForms().getFormDefs().getDescription()).append("</p><p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>")
			.append("<tr>").append("<td style='text-align:center;background:silver'>").append("Field").append("</td>")
			.append("<td style='text-align:center;background:silver'>").append("Value");
		while (fdi.hasNext()) {
			FormData fd = fdi.next();
			System.out.println(fd.getHtmlFormDef().getFormFieldMap().getFormFieldMaps());
			System.out.println(fd.getHtmlFormDef().getFormFieldMap().getFormFieldMaps().isEmpty());
			System.out.println(fd.getHtmlFormDef().getName());
			if (!fd.getHtmlFormDef().getFormFieldMap().getFormFieldMaps().isEmpty())
				continue;

			String currentField = fd.getHtmlFormDef().getName();
			if (!currentField.equals(lastField)) {
				lastField = currentField;
				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
					.append(fd.getHtmlFormDef().getFormFieldMap().getFields().getDescription()).append("</td>").append("<td>");
			}
			else
				sb.append("<br />");
			
			if(fd.getHtmlFormDef().getFormFieldMap().getFields().getFieldTypes().getName().equalsIgnoreCase("file")){
				sb.append("<a href='#' onclick='openDoc(\"").append(valueToString(fd.getTextValue(), fd.getDateValue(), fd.getIntValue(), fd.getNumberValue())).append("\");'>");
				sb.append(valueToString(fd.getTextValue(), fd.getDateValue(), fd.getIntValue(), fd.getNumberValue()));
				sb.append("</a>");
				
				
				
			}else{
			sb.append(valueToString(fd.getTextValue(), fd.getDateValue(), fd.getIntValue(), fd.getNumberValue()));
			}
		}

		sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");

		return sb.toString();
	}*/
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String createHtmlRepresentation(UserForms userForm) {

		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		
		/*UserFormRef ufr=userFormRefService.findUserFormRefByUserFormId(userForm);*/
		HashMap<String, String> hm=new HashMap<String,String>();
		/*if(userForm.getFormTable().equalsIgnoreCase("PotentialTenantForm")){
			PotentialTenantForm ptf=potentialTenantFormService.findPotentialTenantFormByUserFormId(userForm.getId());
			
			if(!ptf.getTitle().trim().equals("") && ptf.getTitle()!=null){
				hm.put("Title", ptf.getTitle());
			}
			if(!ptf.getFirstName().trim().equals("") && ptf.getFirstName()!=null){
				hm.put("First Name", ptf.getFirstName());
			}
			if(!ptf.getLastName().trim().equals("") && ptf.getLastName()!=null){
				hm.put("Last Name", ptf.getTitle());
			}
			if(!ptf.getLandLineNumber().trim().equals("") && ptf.getLandLineNumber()!=null){
				hm.put("Landline Number", ptf.getLandLineNumber());
			}
			if(!ptf.getMobileNumber().trim().equals("") && ptf.getMobileNumber()!=null){
				hm.put("Mobile Number", ptf.getMobileNumber());
			}
			if(!ptf.getEmailAddress().trim().equals("") && ptf.getEmailAddress()!=null){
				hm.put("Email Address", ptf.getEmailAddress());
			}
			
			
			if(!ptf.getNationalInsuranceNumber().trim().equals("") && ptf.getNationalInsuranceNumber()!=null){
				hm.put("National Insurance Number", ptf.getNationalInsuranceNumber());
			}
			if(!ptf.getCurrentAddress().trim().equals("") && ptf.getCurrentAddress()!=null){
				hm.put("Current Address", ptf.getCurrentAddress());
			}
			if(!ptf.getType().trim().equals("-1")){
				hm.put("Property Type", ptf.getType());
			}
			if(ptf.getNumberOfBedRooms()!=0){
				hm.put("Number Of Bedrooms", String.valueOf(ptf.getNumberOfBedRooms()));
			}
			
				hm.put("Garden", ptf.getGarden());
				if(!ptf.getOffRoadParking().trim().equals("") && ptf.getOffRoadParking()!=null){
					hm.put("Off Road Parking", ptf.getOffRoadParking());
				}
				if(!ptf.getGarage().trim().equals("") && ptf.getGarage()!=null){
					hm.put("Garage", ptf.getGarage());
				}
				if(!ptf.getOther().trim().equals("") && ptf.getOther()!=null){
					hm.put("Other", ptf.getOther());
				}
		
		
		Set<Entry<String, String>> setHm=hm.entrySet();
		Iterator<Entry<String, String>> fdi=setHm.iterator();
		
		sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p>")
		.append(userForm.getForms().getFormDefs().getName()).append(" - ")
		.append(userForm.getForms().getFormDefs().getDescription()).append("</p><p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>")
		.append("<tr>").append("<td style='text-align:center;background:silver'>").append("Field").append("</td>")
		.append("<td style='text-align:center;background:silver'>").append("Value");
		
		while (fdi.hasNext()) {
			Entry<String, String> fd = fdi.next();
				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
					.append(fd.getKey()).append("</td>").append("<td>");
			
			sb.append(fd.getValue());
			}
		sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");
		}*/
		
		/*if(userForm.getFormTable().equalsIgnoreCase("PropertyDetailsForm")){
			
			PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormByUserFormId(userForm.getId());
			List<RoomDetails> roomDetails=roomDetailsService.findRoomDetailsByPropertyDetailsFormId(propertyDetailsForm);
			
			
			if(propertyDetailsForm.getClient()!=null){
				if(!propertyDetailsForm.getClient().trim().equals(""))
				hm.put("Client", propertyDetailsForm.getClient());
			}
	    	
	    	 if(propertyDetailsForm.getFirstName()!=null){
	    		 if(!propertyDetailsForm.getFirstName().trim().equals(""))
					hm.put("First Name", propertyDetailsForm.getFirstName());
				}
	    	 
	    	 if( propertyDetailsForm.getLastName()!=null){
	    		 if(!propertyDetailsForm.getLastName().trim().equals(""))
					hm.put("Last Name", propertyDetailsForm.getLastName());
				}
	    	 
	    	 if(propertyDetailsForm.getLandlineNumber()!=null){
	    		 if(!propertyDetailsForm.getLandlineNumber().trim().equals(""))
					hm.put("Landline Number", propertyDetailsForm.getLandlineNumber());
				}
	    	 
	    	 if( propertyDetailsForm.getMobileNumber()!=null){
	    		 if(!propertyDetailsForm.getMobileNumber().trim().equals(""))
					hm.put("Mobile Number", propertyDetailsForm.getMobileNumber());
				}
	    	
	    	 if( propertyDetailsForm.getEmailAddress()!=null){
	    		 if(!propertyDetailsForm.getEmailAddress().trim().equals(""))
					hm.put("Email Address", propertyDetailsForm.getEmailAddress());
				}
	    	 
	    	 if( propertyDetailsForm.getAddress()!=null){
	    		 if(!propertyDetailsForm.getAddress().trim().equals(""))
					hm.put("Address", propertyDetailsForm.getAddress());
				}
	    	
	    	 if( propertyDetailsForm.getAccountNumber()!=null){
	    		 if(!propertyDetailsForm.getAccountNumber().trim().equals(""))
					hm.put("Account Number", propertyDetailsForm.getAccountNumber());
				}
	    	
	    	 if( propertyDetailsForm.getAccountHoldersName()!=null){
	    		 if(!propertyDetailsForm.getAccountHoldersName().trim().equals(""))
					hm.put("Account Holder's Name", propertyDetailsForm.getAccountHoldersName());
				}
	    	
	    	 if( propertyDetailsForm.getSortCode()!=null){
	    		 if(!propertyDetailsForm.getSortCode().trim().equals(""))
					hm.put("Sort Code", propertyDetailsForm.getSortCode());
				}
	    	
	    	 if( propertyDetailsForm.getVatDetails()!=null){
	    		 if(!propertyDetailsForm.getVatDetails().trim().equals(""))
					hm.put("Vat Details", propertyDetailsForm.getVatDetails());
				}
	    	
	    	 if( propertyDetailsForm.getReferencesForPayment()!=null){
	    		 if(!propertyDetailsForm.getReferencesForPayment().trim().equals(""))
					hm.put("References For Payment", propertyDetailsForm.getReferencesForPayment());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyAddressLine1()!=null){
	    		 if(!propertyDetailsForm.getPropertyAddressLine1().trim().equals(""))
					hm.put("Property AddressLine1", propertyDetailsForm.getPropertyAddressLine1());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyAddressLine2()!=null){
	    		 if(!propertyDetailsForm.getPropertyAddressLine2().trim().equals(""))
					hm.put("Property AddressLine2", propertyDetailsForm.getPropertyAddressLine2());
				}
	    	
	    	 if(propertyDetailsForm.getTown()!=null){
	    		 if(!propertyDetailsForm.getTown().trim().equals(""))
					hm.put("Town", propertyDetailsForm.getTown());
				}
	    	
	    	 if( propertyDetailsForm.getCityCountry()!=null){
	    		 if(!propertyDetailsForm.getCityCountry().trim().equals(""))
					hm.put("City/Country", propertyDetailsForm.getCityCountry());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyPostCode()!=null){
	    		 if(!propertyDetailsForm.getPropertyPostCode().trim().equals(""))
					hm.put("Property PostCode", propertyDetailsForm.getPropertyPostCode());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyType()!=null){
	    		 if(!propertyDetailsForm.getPropertyType().trim().equals("-1") )
					hm.put("Property Type", propertyDetailsForm.getPropertyType());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyDescription()!=null){
	    		 if(!propertyDetailsForm.getPropertyDescription().trim().equals(""))
					hm.put("Property Description", propertyDetailsForm.getPropertyDescription());
				}
	    	
	    	 if( propertyDetailsForm.getHouseMeasurements()!=null){
	    		 if(!propertyDetailsForm.getHouseMeasurements().trim().equals(""))
					hm.put("House Measurements in Sq.Ft", propertyDetailsForm.getHouseMeasurements());
				}
	    	
	    	 if( propertyDetailsForm.getGasMeterLocation()!=null){
	    		 if(!propertyDetailsForm.getGasMeterLocation().trim().equals(""))
					hm.put("Gas Meter Location", propertyDetailsForm.getGasMeterLocation());
				}
	    	
	    	 if( propertyDetailsForm.getElectricMeterLocation()!=null){
	    		 if(!propertyDetailsForm.getElectricMeterLocation().trim().equals(""))
					hm.put("Electric Meter Location", propertyDetailsForm.getElectricMeterLocation());
				}
	    	
	    	 if( propertyDetailsForm.getWaterMeterLocation()!=null){
	    		 if(!propertyDetailsForm.getWaterMeterLocation().trim().equals(""))
					hm.put("Water Meter Location", propertyDetailsForm.getWaterMeterLocation());
				}
	    	
	    	 if( propertyDetailsForm.getDateOfPerchase()!=null){
	    		
					hm.put("Date Of Perchase", sdf.format(propertyDetailsForm.getDateOfPerchase()));
				}
	    	
	    	 if(propertyDetailsForm.getPriceOfPurchase()!=null){
	    		 if(!propertyDetailsForm.getPriceOfPurchase().trim().equals(""))
					hm.put("Price Of Purchase", propertyDetailsForm.getPriceOfPurchase());
				}
	    	
	    	 if(propertyDetailsForm.getEstimatedValue()!=null){
	    		 if(!propertyDetailsForm.getEstimatedValue().trim().equals(""))
					hm.put("Estimated Value", propertyDetailsForm.getEstimatedValue());
				}
	    	
	    	 if( propertyDetailsForm.getAsOfDate()!=null){
					hm.put("As Of Date", sdf.format(propertyDetailsForm.getAsOfDate()));
				}
	    	
	    	 if( propertyDetailsForm.getAnnualRent()!=null){
	    		 if(!propertyDetailsForm.getAnnualRent().trim().equals(""))
					hm.put("Annual Rent", propertyDetailsForm.getAnnualRent());
				}
	    	
	    	 if( propertyDetailsForm.getPictures()!=null){
	    		 if(!propertyDetailsForm.getPictures().trim().equals(""))
					hm.put("Picture", propertyDetailsForm.getPictures());
				}
	    	
	    	 if( propertyDetailsForm.getLocalAuthority()!=null){
	    		 if(!propertyDetailsForm.getLocalAuthority().trim().equals(""))
					hm.put("Local Authority", propertyDetailsForm.getLocalAuthority());
				}
	    	
	    	 if( propertyDetailsForm.getRentalType()!=null){
	    		 if(!propertyDetailsForm.getRentalType().trim().equals("-1") )
					hm.put("Rental Type", propertyDetailsForm.getRentalType());
				}
	    	
	    	 if(propertyDetailsForm.getFrontDoorKeyCode()!=null){
	    		 if(!propertyDetailsForm.getFrontDoorKeyCode().trim().equals(""))
					hm.put("Front Door Key Code", propertyDetailsForm.getFrontDoorKeyCode());
				}
	    	
	    	 if( propertyDetailsForm.getBackDoorKeyCode()!=null){
	    		 if(!propertyDetailsForm.getBackDoorKeyCode().trim().equals(""))
					hm.put("Back Door Key Code", propertyDetailsForm.getBackDoorKeyCode());
				}
	    	
	    	 if( propertyDetailsForm.getPorchDoorKeyCode()!=null){
	    		 if(!propertyDetailsForm.getPorchDoorKeyCode().trim().equals(""))
					hm.put("Porch Door Key Code", propertyDetailsForm.getPorchDoorKeyCode());
				}
	    	
	    	 if( propertyDetailsForm.getFlatDoor()!=null){
	    		 if(!propertyDetailsForm.getFlatDoor().trim().equals(""))
					hm.put("Flat Door", propertyDetailsForm.getFlatDoor());
				}
	    	
	    	 if(propertyDetailsForm.getOthers()!=null){
	    		 if(!propertyDetailsForm.getOthers().trim().equals(""))
					hm.put("Notes", propertyDetailsForm.getOthers());
				}
	    	
	    	 if(  propertyDetailsForm.getMobileNumber()!=null){
	    		 if(propertyDetailsForm.getNumberOfBedrooms()!=0)
					hm.put("Number Of Bedrooms", String.valueOf(propertyDetailsForm.getNumberOfBedrooms()));
				}
	    	
	    	
	    	 if(propertyDetailsForm.getTenancyAgreement()!=null){
	    		 if(!propertyDetailsForm.getTenancyAgreement().trim().equals(""))
					hm.put("Tenancy Agreement", propertyDetailsForm.getTenancyAgreement());
				}
	    	
	    	 if( propertyDetailsForm.getInsuranceCertificate()!=null){
	    		 if(!propertyDetailsForm.getInsuranceCertificate().trim().equals(""))
					hm.put("Insurance Certificate", propertyDetailsForm.getInsuranceCertificate());
				}
	    	
	    	 if(propertyDetailsForm.getFloorPlan()!=null){
	    		 if(!propertyDetailsForm.getFloorPlan().trim().equals(""))
					hm.put("Floor Plan", propertyDetailsForm.getFloorPlan());
				}
	    	
	    	 if( propertyDetailsForm.getEpcCertificate()!=null){
	    		 if(!propertyDetailsForm.getEpcCertificate().trim().equals(""))
					hm.put("EPC Certificate", propertyDetailsForm.getEpcCertificate());
				}
	    	
	    	 if( propertyDetailsForm.getGasCertificate()!=null){
	    		 if(!propertyDetailsForm.getGasCertificate().trim().equals(""))
					hm.put("Gas Certificate", propertyDetailsForm.getGasCertificate());
				}
	    	
	    	 if(propertyDetailsForm.getElectricCertificate()!=null){
	    		 if(!propertyDetailsForm.getElectricCertificate().trim().equals(""))
					hm.put("Electric Certificate", propertyDetailsForm.getElectricCertificate());
				}
	    	
	    	 if( propertyDetailsForm.getHmoLicence()!=null){
	    		 if(!propertyDetailsForm.getHmoLicence().trim().equals(""))
					hm.put("HMO Licence", propertyDetailsForm.getHmoLicence());
				}
	    	
	    	 if( propertyDetailsForm.getContractsAndWarranties()!=null){
	    		 if(!propertyDetailsForm.getContractsAndWarranties().trim().equals(""))
					hm.put("Contracts And Warranties", propertyDetailsForm.getContractsAndWarranties());
				}
	    	
	    	 if(propertyDetailsForm.getLandRegistry()!=null){
	    		 if(!propertyDetailsForm.getLandRegistry().trim().equals(""))
					hm.put("Land Registry", propertyDetailsForm.getLandRegistry());
				}
	    	
	    	 if(propertyDetailsForm.getCurrentTenancyForm()!=null){
	    		 if(!propertyDetailsForm.getCurrentTenancyForm().trim().equals(""))
					hm.put("Current Tenancy Form", propertyDetailsForm.getCurrentTenancyForm());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyTimeline()!=null){
	    		 if(!propertyDetailsForm.getPropertyTimeline().trim().equals(""))
					hm.put("Property Timeline", propertyDetailsForm.getPropertyTimeline());
				}
	    	
	    	 if(!propertyDetailsForm.getLinkToJobs().trim().equals("") && propertyDetailsForm.getLinkToJobs()!=null){
	    		 if(!propertyDetailsForm.getLinkToJobs().trim().equals(""))
					hm.put("Link To Jobs", propertyDetailsForm.getLinkToJobs());
				}
	    	
	    	 if( propertyDetailsForm.getLendingInformation()!=null){
	    		 if(!propertyDetailsForm.getLendingInformation().trim().equals(""))
					hm.put("Lending Information", propertyDetailsForm.getLendingInformation());
				}
	    	
	    	 if(propertyDetailsForm.getManagementCompany()!=null){
	    		 if(!propertyDetailsForm.getManagementCompany().trim().equals(""))
					hm.put("Management Company", propertyDetailsForm.getManagementCompany());
				}
	    	 
	    	 if(propertyDetailsForm.getCompanyName()!=null){
	    		 if(!propertyDetailsForm.getCompanyName().trim().equals(""))
					hm.put("Company Name", propertyDetailsForm.getCompanyName());
				}
			
	    	 for(RoomDetails rd:roomDetails){
	    		 hm.put(rd.getRoomNo(), rd.getRoomName());
	    	 }
	    	 
	    	 Set<Entry<String, String>> setHm=hm.entrySet();
	 		Iterator<Entry<String, String>> fdi=setHm.iterator();
	 		
	 		sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p>")
	 		.append(userForm.getForms().getFormDefs().getName()).append(" - ")
	 		.append(userForm.getForms().getFormDefs().getDescription()).append("</p><p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>")
	 		.append("<tr>").append("<td style='text-align:center;background:silver'>").append("Field").append("</td>")
	 		.append("<td style='text-align:center;background:silver'>").append("Value");
	 		
	 		while (fdi.hasNext()) {
	 			Entry<String, String> fd = fdi.next();
	 			if(fd.getKey().equalsIgnoreCase("floor plan") || fd.getKey().equalsIgnoreCase("picture") || fd.getKey().equalsIgnoreCase("tenancy agreement")
	 					|| fd.getKey().equalsIgnoreCase("insurance certificate") || fd.getKey().equalsIgnoreCase("epc certificate") || fd.getKey().equalsIgnoreCase("gas certificate")
	 					|| fd.getKey().equalsIgnoreCase("electric certificate") || fd.getKey().equalsIgnoreCase("hmo license") || fd.getKey().equalsIgnoreCase("contracts & warrnties")
	 					|| fd.getKey().equalsIgnoreCase("land registry") || fd.getKey().equalsIgnoreCase("curent tenancy form") || fd.getKey().equalsIgnoreCase("property timeline")
	 					|| fd.getKey().equalsIgnoreCase("link to jobs") || fd.getKey().equalsIgnoreCase("lending information")){
	 				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
	 					.append(fd.getKey()).append("</td>").append("<td>");
	 				sb.append("<a href='#' onclick='openDoc(\"").append(fd.getValue()).append("\");'>");
	 			sb.append(fd.getValue()+"</a>");
	 			
	 			}
	 			else{
	 				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
 					.append(fd.getKey()).append("</td>").append("<td>");
	 				sb.append(fd.getValue());
	 			}
	 			}
	 		sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");
		}*/
		
		/*if(userForm.getFormTable().equalsIgnoreCase("TenancyForm")){
			TenancyForm tenancyForm=tenancyFormService.findTenancyFormByUserFormId(userForm.getId());
			
			if( tenancyForm.getEmployerCompany()!=null){
				if(!tenancyForm.getEmployerCompany().trim().equals(""))
				hm.put("Employer Company", tenancyForm.getEmployerCompany());
			}
			
			if( tenancyForm.getGuarantorCompany()!=null){
				if(!tenancyForm.getGuarantorCompany().trim().equals(""))
				hm.put("Guarantor Company", tenancyForm.getGuarantorCompany());
			}
			
			
			if( tenancyForm.getPropertyAddress()!=null){
				if(!tenancyForm.getPropertyAddress().trim().equals(""))
				hm.put("Property Address", tenancyForm.getPropertyAddress());
			}
			
			if( tenancyForm.getTypeOfRental()!=null){
				if(!tenancyForm.getTypeOfRental().trim().equals(""))
				hm.put("Type Of Rental", tenancyForm.getTypeOfRental());
			}
			
			if(tenancyForm.getRoomNumber()!=null ){
				if(tenancyForm.getRoomNumber()!="" )
				hm.put("Room Number", tenancyForm.getRoomNumber());
			}
		
			if(tenancyForm.getLandLordFirstName()!=null){
				if(!tenancyForm.getLandLordFirstName().trim().equals(""))
				hm.put("Landlord First Name", tenancyForm.getLandLordFirstName());
			}
			
			if(tenancyForm.getLandLordLastName()!=null){
				if(!tenancyForm.getLandLordLastName().trim().equals(""))
				hm.put("Landlord Last Name", tenancyForm.getLandLordLastName());
			}
			
			if( tenancyForm.getLandLordAddress()!=null){
				if(!tenancyForm.getLandLordAddress().trim().equals(""))
				hm.put("Landlord Address", tenancyForm.getLandLordAddress());
			}
			
			if( tenancyForm.getLandLordEmailAddress()!=null){
				if(!tenancyForm.getLandLordEmailAddress().trim().equals(""))
				hm.put("Landlord Email Address", tenancyForm.getLandLordEmailAddress());
			}
			
			if(tenancyForm.getLandLordContactNumber()!=null){
				if(!tenancyForm.getLandLordContactNumber().trim().equals(""))
				hm.put("Landlord Contact Number", tenancyForm.getLandLordContactNumber());
			}
			
			if( tenancyForm.getEmployerFirstName()!=null){
				if(!tenancyForm.getEmployerFirstName().trim().equals(""))
				hm.put("Employer First Name", tenancyForm.getEmployerFirstName());
			}
		
			if( tenancyForm.getEmployerLastName()!=null){
				if(!tenancyForm.getEmployerLastName().trim().equals(""))
				hm.put("Employer Last Name", tenancyForm.getEmployerLastName());
			}
			
			if( tenancyForm.getEmployerAddress()!=null){
				if(!tenancyForm.getEmployerAddress().trim().equals(""))
				hm.put("Employer Address", tenancyForm.getEmployerAddress());
			}
			
			if(tenancyForm.getEmployerEmailAddress()!=null){
				if(!tenancyForm.getEmployerEmailAddress().trim().equals(""))
				hm.put("Employer Email Address", tenancyForm.getEmployerEmailAddress());
			}
			
			if(tenancyForm.getEmployerContactNumber()!=null){
				if(!tenancyForm.getEmployerContactNumber().trim().equals(""))
				hm.put("Employer Contact Number", tenancyForm.getEmployerContactNumber());
			}
			
			if( tenancyForm.getGuarantorFirstName()!=null){
				if(!tenancyForm.getGuarantorFirstName().trim().equals(""))
				hm.put("Guarantor First Name", tenancyForm.getGuarantorFirstName());
			}
			
			if( tenancyForm.getGuarantorLastName()!=null){
				if(!tenancyForm.getGuarantorLastName().trim().equals(""))
				hm.put("Guarantor Last Name", tenancyForm.getGuarantorLastName());
			}
			
			if( tenancyForm.getGuarantorAddress()!=null){
				if(!tenancyForm.getGuarantorAddress().trim().equals(""))
				hm.put("Guarantor Address", tenancyForm.getGuarantorAddress());
			}
			
			if( tenancyForm.getGuarantorEmailAddress()!=null){
				if(!tenancyForm.getGuarantorEmailAddress().trim().equals(""))
				hm.put("Guarantor Email Address", tenancyForm.getGuarantorEmailAddress());
			}
			
			if( tenancyForm.getGuarantorContactNumber()!=null){
				if(!tenancyForm.getGuarantorContactNumber().trim().equals(""))
				hm.put("Guarantor Contact Number", tenancyForm.getGuarantorContactNumber());
			}
			
			if( tenancyForm.getKinFirstName()!=null){
				if(!tenancyForm.getKinFirstName().trim().equals(""))
				hm.put("Next of Kin First Name ", tenancyForm.getKinFirstName());
			}
			
			if( tenancyForm.getKinLastName()!=null){
				if(!tenancyForm.getKinLastName().trim().equals(""))
				hm.put("Next of Kin Last Name", tenancyForm.getKinLastName());
			}
			
			if( tenancyForm.getKinAddress()!=null){
				if(!tenancyForm.getKinAddress().trim().equals(""))
				hm.put("Next of Kin Address ", tenancyForm.getKinAddress());
			}
			
			if( tenancyForm.getKinEmailAddress()!=null){
				if(!tenancyForm.getKinEmailAddress().trim().equals(""))
				hm.put("Next of kin Email Address", tenancyForm.getKinEmailAddress());
			}
			
			if(tenancyForm.getKinContactNumber()!=null){
				if(!tenancyForm.getKinContactNumber().trim().equals(""))
				hm.put("Next of kin Contact Number", tenancyForm.getKinContactNumber());
			}
			
			if( tenancyForm.getTenancyStartedDate()!=null){
				
				hm.put("Tenancy Start Date", sdf.format(tenancyForm.getTenancyStartedDate()));
			}
			
			if(tenancyForm.getTenancyOpenedBy()!=null){
				if(!tenancyForm.getTenancyOpenedBy().trim().equals(""))
				hm.put("Tenancy Opened By", tenancyForm.getTenancyOpenedBy());
			}
			
			if( tenancyForm.getTenancyClosedBy()!=null){
				if(!tenancyForm.getPropertyAddress().trim().equals(""))
				hm.put("Tenancy Closed By", tenancyForm.getTenancyClosedBy());
			}
			
			if( tenancyForm.getTenancyClosedDate()!=null){
				
				hm.put("Tenancy Closed Date", sdf.format(tenancyForm.getTenancyClosedDate()));
			}
			
			if(tenancyForm.getCheckingOutForm()!=null){
				if(!tenancyForm.getCheckingOutForm().trim().equals(""))
				hm.put("CheckingOut Form", tenancyForm.getCheckingOutForm());
			}
			
			if( tenancyForm.getLinkToProperty()!=null){
				if(!tenancyForm.getLinkToProperty().trim().equals(""))
				hm.put("Link To Property", tenancyForm.getLinkToProperty());
			}
			
			if(tenancyForm.getLinkToTenancyAgreement()!=null){
				if(!tenancyForm.getLinkToTenancyAgreement().trim().equals(""))
				hm.put("Link To Tenancy Agreement", tenancyForm.getLinkToTenancyAgreement());
			}
			
			if(tenancyForm.getLinkToRentAccounts()!=null){
				if(!tenancyForm.getLinkToRentAccounts().trim().equals(""))
				hm.put("Link To Rent Accounts", tenancyForm.getLinkToRentAccounts());
			}
			
			if( tenancyForm.getLegalNotifications()!=null){
				if(!tenancyForm.getLegalNotifications().trim().equals(""))
				hm.put("Legal Notification", tenancyForm.getLegalNotifications());
			}
			
			if(tenancyForm.getTenancyDepositCertificate()!=null){
				if(!tenancyForm.getTenancyDepositCertificate().trim().equals(""))
				hm.put("Tenancy Deposit Certificate", tenancyForm.getTenancyDepositCertificate());
			}
			
			if(tenancyForm.getCouncilTaxRegistration()!=null){
				if(!tenancyForm.getCouncilTaxRegistration().trim().equals(""))
				hm.put("Council Tax Registration", tenancyForm.getCouncilTaxRegistration());
			}
			
			if(tenancyForm.getAdditionalLinks()!=null){
				if(!tenancyForm.getAdditionalLinks().trim().equals(""))
				hm.put("Additional Links", tenancyForm.getAdditionalLinks());
			}
			
			if( tenancyForm.getCorrespondenceWithTenants()!=null){
				if(!tenancyForm.getCorrespondenceWithTenants().trim().equals(""))
				hm.put("Correspondence With Tenant", tenancyForm.getCorrespondenceWithTenants());
			}
		
			if( tenancyForm.getCorrespondenceLink()!=null){
				if(!tenancyForm.getCorrespondenceLink().trim().equals(""))
				hm.put("Correspondence Links", tenancyForm.getCorrespondenceLink());
			}
			
			TenantDetails td=tenantDetailsService.findTenantDetailsForTenancy(tenancyForm.getId());
			
			for(TenantDetails td:tenantDetails){
			
				
				 if(td.getFirstName()!=null){
					 if(!td.getFirstName().trim().equals(""))
						hm.put("Tenant First Name", td.getFirstName());
					}
				
				 if( td.getTitle()!=null){
					 if(!td.getTitle().trim().equals(""))
						hm.put("Tenant Title", td.getTitle());
					}
				 
				 if(td.getLastName()!=null){
					 if(!td.getLastName().trim().equals(""))
						hm.put("Tenant Last Name", td.getLastName());
					}
				
				 if( td.getDateOfBirth()!=null){
						hm.put("Tenant Date Of Birth", sdf.format(td.getDateOfBirth()));
					}
				
				 if(td.getLandlineNumber()!=null){
					 if(!td.getLandlineNumber().trim().equals(""))
						hm.put("Tenant Landline Number", td.getLandlineNumber());
					}
			
				 if(td.getMobileNumber()!=null){
					 if(!td.getMobileNumber().trim().equals(""))
						hm.put("Tenant Mobile Number", td.getMobileNumber());
					}
				
				 if( td.getEmailAddress()!=null){
					 if(!td.getEmailAddress().trim().equals(""))
						hm.put("Tenant Email Address", td.getEmailAddress());
					}
				
				 if( td.getNationalInsuranceNumber()!=null){
					 if(!td.getNationalInsuranceNumber().trim().equals(""))
						hm.put("Tenant National Insurance Number Information", td.getNationalInsuranceNumber());
					}
				
				 if( td.getCurrentAddress()!=null){
					 if(!td.getCurrentAddress().trim().equals(""))
						hm.put("Tenant Current Address", td.getCurrentAddress());
					}
				 
				 if(td.getPassport()!=null){
					 if(!td.getPassport().trim().equals(""))
						hm.put("Tenant Passport", td.getPassport());
					}
				
				 if(td.getDriversLicense()!=null){
					 if(!td.getDriversLicense().trim().equals(""))
						hm.put("Tenant Driver License", td.getPassport());
					}
			
				 if(td.getCreditCheck()!=null){
					 if(!td.getCreditCheck().trim().equals(""))
						hm.put("Tenant Credit Check", td.getCreditCheck());
					}
			
				 if( td.getReferenceCheck()!=null){
					 if(!td.getReferenceCheck().trim().equals(""))
						hm.put("Tenant Reference Check", td.getReferenceCheck());
					}
				
				 if(td.getAdditionalRemarks()!=null){
					 if(!td.getAdditionalRemarks().trim().equals(""))
						hm.put("Tenant Additional Remark", td.getAdditionalRemarks());
					}
			
		
			//}
			Set<Entry<String, String>> setHm=hm.entrySet();
	 		Iterator<Entry<String, String>> fdi=setHm.iterator();
	 		
	 		sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p>")
	 		.append(userForm.getForms().getFormDefs().getName()).append(" - ")
	 		.append(userForm.getForms().getFormDefs().getDescription()).append("</p><p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>")
	 		.append("<tr>").append("<td style='text-align:center;background:silver'>").append("Field").append("</td>")
	 		.append("<td style='text-align:center;background:silver'>").append("Value");
	 		
	 		while (fdi.hasNext()) {
	 			Entry<String, String> fd = fdi.next();
	 				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
	 					.append(fd.getKey()).append("</td>").append("<td>");
	 			
	 			sb.append(fd.getValue());
	 			}
	 		sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");
		}*/
		
		/*if(userForm.getFormTable().equalsIgnoreCase("BuilderDetailsForm")){
			BuilderDetailsForm builderDetailsForm=builderDetailsFormService.findBuilderDetailsFormByUserFormId(userForm.getId());
			
			if(builderDetailsForm.getFirstName()!=null){
				if(!builderDetailsForm.getFirstName().trim().equals("") )
				hm.put("First Name", builderDetailsForm.getFirstName());
			}
	    	
	    	 if( builderDetailsForm.getLastName()!=null){
	    		 if(!builderDetailsForm.getLastName().trim().equals("") )
					hm.put("Last Name", builderDetailsForm.getLastName());
				}
	    	
	    	 if( builderDetailsForm.getEmailAddress()!=null){
	    		 if(!builderDetailsForm.getEmailAddress().trim().equals("") )
					hm.put("Email Address", builderDetailsForm.getEmailAddress());
				}
	    	
	    	 if( builderDetailsForm.getLandlineNumber()!=null){
	    		 if(!builderDetailsForm.getLandlineNumber().trim().equals("") )
					hm.put("Landline Number", builderDetailsForm.getLandlineNumber());
				}
	    	
	    	 if(builderDetailsForm.getMobileNumber()!=null){
	    		 if(!builderDetailsForm.getMobileNumber().trim().equals("") )
					hm.put("Mobile Number", builderDetailsForm.getMobileNumber());
				}
	    	
	    	 if(builderDetailsForm.getCompanyName()!=null){
	    		 if(!builderDetailsForm.getCompanyName().trim().equals("") )
					hm.put("Company Name", builderDetailsForm.getCompanyName());
				}
	    	 
	    	 if(builderDetailsForm.getCompanyAddress()!=null){
	    		 if(!builderDetailsForm.getCompanyAddress().trim().equals("") )
					hm.put("Company Address", builderDetailsForm.getCompanyAddress());
				}
	    	
	    	 if(builderDetailsForm.getCompanyNumber()!=null){
	    		 if(!builderDetailsForm.getCompanyNumber().trim().equals("") )
					hm.put("Company Number", builderDetailsForm.getCompanyNumber());
				}
	    	
	    	 if(builderDetailsForm.getVatNumber()!=null){
	    		 if(!builderDetailsForm.getVatNumber().trim().equals("") )
					hm.put("VAT Number", builderDetailsForm.getVatNumber());
				}
	    	
	    	 if(builderDetailsForm.getAccountHoldersName()!=null){
	    		 if(!builderDetailsForm.getAccountHoldersName().trim().equals("") )
					hm.put("Account Holder's Name", builderDetailsForm.getAccountHoldersName());
				}
	    	
	    	 if(builderDetailsForm.getAccountNumber()!=null){
	    		 if(!builderDetailsForm.getAccountNumber().trim().equals("") )
					hm.put("Account Number", builderDetailsForm.getAccountNumber());
				}
	    	
	    	 if( builderDetailsForm.getSortCode()!=null){
	    		 if(!builderDetailsForm.getSortCode().trim().equals("") )
					hm.put("Sort Code", builderDetailsForm.getSortCode());
				}
	    	
	    	 if(builderDetailsForm.getLinkToInvoice()!=null){
	    		 if(!builderDetailsForm.getLinkToInvoice().trim().equals("") )
					hm.put("Link To Invoice", builderDetailsForm.getLinkToInvoice());
				}
	    	
	    	 if(builderDetailsForm.getLinkToJobs()!=null){
	    		 if(!builderDetailsForm.getLinkToJobs().trim().equals("") )
					hm.put("Link To Jobs", builderDetailsForm.getLinkToJobs());
				}
	    	 Set<Entry<String, String>> setHm=hm.entrySet();
		 		Iterator<Entry<String, String>> fdi=setHm.iterator();
		 		
		 		sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p>")
		 		.append(userForm.getForms().getFormDefs().getName()).append(" - ")
		 		.append(userForm.getForms().getFormDefs().getDescription()).append("</p><p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>")
		 		.append("<tr>").append("<td style='text-align:center;background:silver'>").append("Field").append("</td>")
		 		.append("<td style='text-align:center;background:silver'>").append("Value");
		 		
		 		while (fdi.hasNext()) {
		 			Entry<String, String> fd = fdi.next();
		 			System.out.println(fd.getKey().trim());
		 			if(fd.getKey().equalsIgnoreCase("link to jobs") || fd.getKey().equalsIgnoreCase("link to invoice")){
		 				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
		 					.append(fd.getKey()).append("</td>").append("<td>");
		 				sb.append("<a href='#' onclick='openDoc(\"").append(fd.getValue()).append("\");'>");
		 			sb.append(fd.getValue()+"</a>");
		 			
		 			}
		 			else{
		 				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
	 					.append(fd.getKey()).append("</td>").append("<td>");
		 				sb.append(fd.getValue());
		 			}
		 			}
		 		sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");
		}
		
		if(userForm.getFormTable().equalsIgnoreCase("UtilitiesCompanyDetailsForm")){
			UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=utilitiesCompanyDetailsFormService.findUtilitiesCompanyDetailsFormByUserFormId(userForm.getId());
			
			if( utilitiesCompanyDetailsForm.getCompanyName()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Company Name", utilitiesCompanyDetailsForm.getCompanyName());
			}
	    	
			if(utilitiesCompanyDetailsForm.getCompanyType()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Company Type", utilitiesCompanyDetailsForm.getCompanyType());
			}
	    	
	    	
			if( utilitiesCompanyDetailsForm.getCompanyAddress()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Company Address", utilitiesCompanyDetailsForm.getCompanyAddress());
			}
	    	
	    	
			if(utilitiesCompanyDetailsForm.getCompanyTelephone()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Company Telephone", utilitiesCompanyDetailsForm.getCompanyTelephone());
			}
	    	
	    	
			if(utilitiesCompanyDetailsForm.getOpeningAndClosingTimes()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Opening and Closing Times", utilitiesCompanyDetailsForm.getOpeningAndClosingTimes());
			}
	    	
	    	
			if(utilitiesCompanyDetailsForm.getMainContactName()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Main Contact Name", utilitiesCompanyDetailsForm.getMainContactName());
			}
	    	
	    	 
			if(utilitiesCompanyDetailsForm.getMainContactTelephone()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Main Contact Telephone", utilitiesCompanyDetailsForm.getMainContactTelephone());
			}
	    	
	    	
			if(utilitiesCompanyDetailsForm.getMainContactEmailAddress()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Main Contact Email Address", utilitiesCompanyDetailsForm.getMainContactEmailAddress());
			}
	    	
			Set<Entry<String, String>> setHm=hm.entrySet();
	 		Iterator<Entry<String, String>> fdi=setHm.iterator();
	 		
	 		sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p>")
	 		.append(userForm.getForms().getFormDefs().getName()).append(" - ")
	 		.append(userForm.getForms().getFormDefs().getDescription()).append("</p><p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>")
	 		.append("<tr>").append("<td style='text-align:center;background:silver'>").append("Field").append("</td>")
	 		.append("<td style='text-align:center;background:silver'>").append("Value");
	 		
	 		while (fdi.hasNext()) {
	 			Entry<String, String> fd = fdi.next();
	 				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
	 					.append(fd.getKey()).append("</td>").append("<td>");
	 			
	 			sb.append(fd.getValue());
	 			}
	 		sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");
	    	 
		}*/
		
/*if(userForm.getFormTable().equalsIgnoreCase("Inventory")){
			
			Inventory inventory=inventoryService.findInventoryFormByUserFormId(userForm.getId());
			
			if(inventory.getTenantName()!=null){
				if(!inventory.getTenantName().trim().equals("") )
				hm.put("Tenant Name", inventory.getTenantName());
			}
	    	
			if(inventory.getAddress()!=null){
				if(!inventory.getAddress().trim().equals("") )
				hm.put("Tenant Address", inventory.getAddress());
			}
	    	
	    	
			if(inventory.getRoom()!=null){
				if(!inventory.getRoom().trim().equals("") )
				hm.put("Rooms", inventory.getRoom());
			}
	    	
	    	
			if(inventory.getFurnishingsList()!=null){
				if(!inventory.getFurnishingsList().trim().equals("") )
				hm.put("List Of Furnishes", inventory.getFurnishingsList());
			}
	    	
	    	
			if(inventory.getOk()!=null){
				if(!inventory.getOk().trim().equals("-1") )
				hm.put("Everything OK?", inventory.getOk());
			}
	    	
	    	
			if(inventory.getNotes()!=null){
				if(!inventory.getNotes().trim().equals("") )
				hm.put("Notes/Details", inventory.getNotes());
			}
	    	
	    	 
			if(inventory.getPhotos()!=null){
				if(!inventory.getPhotos().trim().equals("") )
				hm.put("Photo", inventory.getPhotos());
			}
	    	
			StringBuilder jsonString=new StringBuilder();
			HashMap<String,String> map =null;
			if(inventory.getPropertyForm()!=null){
				if(!inventory.getPropertyForm().trim().equals("") )
				hm.put("Property Form", inventory.getPropertyForm());
			}
			
				List<InventoryRooms> inventoryRooms=inventoryRoomsService.findInventoryRoomsForInventory(inventory);
				for(InventoryRooms inr:inventoryRooms){
				
				if(inr.getFieldName().equals("json")){
				 jsonString= inr.getFieldValue();
				 System.out.println();
				map= new Gson().fromJson(jsonString, new TypeToken<HashMap<String, String>>(){}.getType());
					jsonString.append(inr.getFieldValue());
					jsonString.deleteCharAt(0);
					jsonString.deleteCharAt(jsonString.length()-1);
					System.out.println(jsonString.toString());
					 map= new HashMap<String,String>();
					ObjectMapper mapper = new ObjectMapper();
				 
					try {
				 
						//convert JSON string to Map
						map = mapper.readValue(jsonString.toString(), 
						    new TypeReference<HashMap<String,String>>(){});
				 
						System.out.println(map);
				 
					} catch (Exception e) {
						e.printStackTrace();
					}
				System.out.println(map);
				}else{
					hm.put(inr.getFieldName(), inr.getFieldValue());
				}		
			}
				if(map!=null){
					Set<Entry<String, String>> setmap=map.entrySet();
			 		Iterator<Entry<String, String>> fdjson=setmap.iterator();
			 		
			 		while (fdjson.hasNext()) {
			 			
			 			Entry<String, String> fdjs = fdjson.next();	
			 			if(fdjs.getKey().startsWith("item")){
			 				hm.put(fdjs.getKey(), fdjs.getValue());
			 			}
			 		}
				}
			 Set<Entry<String, String>> setHm=hm.entrySet();
		 		Iterator<Entry<String, String>> fdi=setHm.iterator();
		 		
		 		//sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");
				Set<Entry<String, String>> setHm=hm.entrySet();
		 		Iterator<Entry<String, String>> fdi=setHm.iterator();
		 		
		 		sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p>")
		 		.append(userForm.getForms().getFormDefs().getName()).append(" - ")
		 		.append(userForm.getForms().getFormDefs().getDescription()).append("</p><p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>")
		 		.append("<tr>").append("<td style='text-align:center;background:silver'>").append("Field").append("</td>")
		 		.append("<td style='text-align:center;background:silver'>").append("Value");
		 		
			while (fdi.hasNext()) {
	 			Entry<String, String> fd = fdi.next();
	 			System.out.println(fd.getKey().trim());
	 			if(fd.getKey().equalsIgnoreCase("property form") || fd.getKey().startsWith("photo")){
	 				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
	 					.append(fd.getKey()).append("</td>").append("<td>");
	 				sb.append("<a href='#' onclick='openDoc(\"").append(fd.getValue()).append("\");'>");
	 			sb.append(fd.getValue()+"</a>");
	 			
	 			}
	 			else{
	 				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
 					.append(fd.getKey()).append("</td>").append("<td>");
	 				sb.append(fd.getValue());
	 			}
	 			}
			sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");
	 		
}*/
		
		return sb.toString();
	}
	

	/*@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String createHtmlRepresentationForPdfDownload(UserForms userForm) {

		StringBuilder sb = new StringBuilder();
		Iterator<FormData> fdi = userForm.getFormDatas().iterator();
		String lastField = null;
		sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p>")
			.append(userForm.getForms().getFormDefs().getName()).append(" - ")
			.append(userForm.getForms().getFormDefs().getDescription()).append("</p><p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>")
			.append("<tr>").append("<td style='text-align:center;background:silver'>").append("Field").append("</td>")
			.append("<td style='text-align:center;background:silver'>").append("Value");
		while (fdi.hasNext()) {
			FormData fd = fdi.next();
			System.out.println(fd.getHtmlFormDef().getFormFieldMap().getFormFieldMaps());
			System.out.println(fd.getHtmlFormDef().getFormFieldMap().getFormFieldMaps().isEmpty());
			System.out.println(fd.getHtmlFormDef().getName());
			if (!fd.getHtmlFormDef().getFormFieldMap().getFormFieldMaps().isEmpty())
				continue;

			String currentField = fd.getHtmlFormDef().getName();
			if (!currentField.equals(lastField)) {
				lastField = currentField;
				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
					.append(fd.getHtmlFormDef().getFormFieldMap().getFields().getDescription()).append("</td>").append("<td>");
			}
			else
				sb.append("<br />");
			
			
			sb.append(valueToString(fd.getTextValue(), fd.getDateValue(), fd.getIntValue(), fd.getNumberValue()));
			
		}

		sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");

		return sb.toString();
	}*/
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String createHtmlRepresentationForPdfDownload(UserForms userForm) {

		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		
		/*UserFormRef ufr=userFormRefService.findUserFormRefByUserFormId(userForm);*/
		HashMap<String, String> hm=new HashMap<String,String>();
	/*	if(userForm.getFormTable().equalsIgnoreCase("PotentialTenantForm")){
			PotentialTenantForm ptf=potentialTenantFormService.findPotentialTenantFormByUserFormId(userForm.getId());
			
			if(ptf.getTitle().trim()!="" && ptf.getTitle()!=null){
				hm.put("Title", ptf.getTitle());
			}
			if(ptf.getFirstName()!="" && ptf.getFirstName()!=null){
				hm.put("First Name", ptf.getFirstName());
			}
			if(ptf.getLastName()!="" && ptf.getLastName()!=null){
				hm.put("Last Name", ptf.getTitle());
			}
			if(ptf.getLandLineNumber()!="" && ptf.getLandLineNumber()!=null){
				hm.put("Landline Number", ptf.getLandLineNumber());
			}
			if(ptf.getMobileNumber()!="" && ptf.getMobileNumber()!=null){
				hm.put("Mobile Number", ptf.getMobileNumber());
			}
			if(ptf.getEmailAddress()!="" && ptf.getEmailAddress()!=null){
				hm.put("Email Address", ptf.getEmailAddress());
			}
			
			
			if(ptf.getNationalInsuranceNumber()!="" && ptf.getNationalInsuranceNumber()!=null){
				hm.put("National Insurance Number", ptf.getNationalInsuranceNumber());
			}
			if(ptf.getCurrentAddress()!="" && ptf.getCurrentAddress()!=null){
				hm.put("Current Address", ptf.getCurrentAddress());
			}
			if(ptf.getType()!="-1"){
				hm.put("Type", ptf.getType());
			}
			if(ptf.getNumberOfBedRooms()!=0){
				hm.put("Number Of Bedrooms", String.valueOf(ptf.getNumberOfBedRooms()));
			}
			
				hm.put("Garden", ptf.getGarden());
				if(ptf.getOffRoadParking()!="" && ptf.getOffRoadParking()!=null){
					hm.put("Off Road Parking", ptf.getOffRoadParking());
				}
				if(ptf.getGarage()!="" && ptf.getGarage()!=null){
					hm.put("Garage", ptf.getGarage());
				}
				if(ptf.getOther()!="" && ptf.getOther()!=null){
					hm.put("Other", ptf.getOther());
				}
		}*/
		
		
		/*if(userForm.getFormTable().equalsIgnoreCase("PropertyDetailsForm")){
			
			PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormByUserFormId(userForm.getId());
			List<RoomDetails> roomDetails=roomDetailsService.findRoomDetailsByPropertyDetailsFormId(propertyDetailsForm);
			
			
			if(propertyDetailsForm.getClient()!=null){
				if(!propertyDetailsForm.getClient().trim().equals(""))
				hm.put("Client", propertyDetailsForm.getClient());
			}
	    	
	    	 if(propertyDetailsForm.getFirstName()!=null){
	    		 if(!propertyDetailsForm.getFirstName().trim().equals(""))
					hm.put("First Name", propertyDetailsForm.getFirstName());
				}
	    	 
	    	 if( propertyDetailsForm.getLastName()!=null){
	    		 if(!propertyDetailsForm.getLastName().trim().equals(""))
					hm.put("Last Name", propertyDetailsForm.getLastName());
				}
	    	 
	    	 if(propertyDetailsForm.getLandlineNumber()!=null){
	    		 if(!propertyDetailsForm.getLandlineNumber().trim().equals(""))
					hm.put("Landline Number", propertyDetailsForm.getLandlineNumber());
				}
	    	 
	    	 if( propertyDetailsForm.getMobileNumber()!=null){
	    		 if(!propertyDetailsForm.getMobileNumber().trim().equals(""))
					hm.put("Mobile Number", propertyDetailsForm.getMobileNumber());
				}
	    	
	    	 if( propertyDetailsForm.getEmailAddress()!=null){
	    		 if(!propertyDetailsForm.getEmailAddress().trim().equals(""))
					hm.put("Email Address", propertyDetailsForm.getEmailAddress());
				}
	    	 
	    	 if( propertyDetailsForm.getAddress()!=null){
	    		 if(!propertyDetailsForm.getAddress().trim().equals(""))
					hm.put("Address", propertyDetailsForm.getAddress());
				}
	    	
	    	 if( propertyDetailsForm.getAccountNumber()!=null){
	    		 if(!propertyDetailsForm.getAccountNumber().trim().equals(""))
					hm.put("Account Number", propertyDetailsForm.getAccountNumber());
				}
	    	
	    	 if( propertyDetailsForm.getAccountHoldersName()!=null){
	    		 if(!propertyDetailsForm.getAccountHoldersName().trim().equals(""))
					hm.put("Account Holder's Name", propertyDetailsForm.getAccountHoldersName());
				}
	    	
	    	 if( propertyDetailsForm.getSortCode()!=null){
	    		 if(!propertyDetailsForm.getSortCode().trim().equals(""))
					hm.put("Sort Code", propertyDetailsForm.getSortCode());
				}
	    	
	    	 if( propertyDetailsForm.getVatDetails()!=null){
	    		 if(!propertyDetailsForm.getVatDetails().trim().equals(""))
					hm.put("Vat Details", propertyDetailsForm.getVatDetails());
				}
	    	
	    	 if( propertyDetailsForm.getReferencesForPayment()!=null){
	    		 if(!propertyDetailsForm.getReferencesForPayment().trim().equals(""))
					hm.put("References For Payment", propertyDetailsForm.getReferencesForPayment());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyAddressLine1()!=null){
	    		 if(!propertyDetailsForm.getPropertyAddressLine1().trim().equals(""))
					hm.put("Property AddressLine1", propertyDetailsForm.getPropertyAddressLine1());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyAddressLine2()!=null){
	    		 if(!propertyDetailsForm.getPropertyAddressLine2().trim().equals(""))
					hm.put("Property AddressLine2", propertyDetailsForm.getPropertyAddressLine2());
				}
	    	
	    	 if(propertyDetailsForm.getTown()!=null){
	    		 if(!propertyDetailsForm.getTown().trim().equals(""))
					hm.put("Town", propertyDetailsForm.getTown());
				}
	    	
	    	 if( propertyDetailsForm.getCityCountry()!=null){
	    		 if(!propertyDetailsForm.getCityCountry().trim().equals(""))
					hm.put("City/Country", propertyDetailsForm.getCityCountry());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyPostCode()!=null){
	    		 if(!propertyDetailsForm.getPropertyPostCode().trim().equals(""))
					hm.put("Property PostCode", propertyDetailsForm.getPropertyPostCode());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyType()!=null){
	    		 if(!propertyDetailsForm.getPropertyType().trim().equals("-1") )
					hm.put("Property Type", propertyDetailsForm.getPropertyType());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyDescription()!=null){
	    		 if(!propertyDetailsForm.getPropertyDescription().trim().equals(""))
					hm.put("Property Description", propertyDetailsForm.getPropertyDescription());
				}
	    	
	    	 if( propertyDetailsForm.getHouseMeasurements()!=null){
	    		 if(!propertyDetailsForm.getHouseMeasurements().trim().equals(""))
					hm.put("House Measurements in Sq.Ft", propertyDetailsForm.getHouseMeasurements());
				}
	    	
	    	 if( propertyDetailsForm.getGasMeterLocation()!=null){
	    		 if(!propertyDetailsForm.getGasMeterLocation().trim().equals(""))
					hm.put("Gas Meter Location", propertyDetailsForm.getGasMeterLocation());
				}
	    	
	    	 if( propertyDetailsForm.getElectricMeterLocation()!=null){
	    		 if(!propertyDetailsForm.getElectricMeterLocation().trim().equals(""))
					hm.put("Electric Meter Location", propertyDetailsForm.getElectricMeterLocation());
				}
	    	
	    	 if( propertyDetailsForm.getWaterMeterLocation()!=null){
	    		 if(!propertyDetailsForm.getWaterMeterLocation().trim().equals(""))
					hm.put("Water Meter Location", propertyDetailsForm.getWaterMeterLocation());
				}
	    	
	    	 if( propertyDetailsForm.getDateOfPerchase()!=null){
	    		
					hm.put("Date Of Perchase", sdf.format(propertyDetailsForm.getDateOfPerchase()));
				}
	    	
	    	 if(propertyDetailsForm.getPriceOfPurchase()!=null){
	    		 if(!propertyDetailsForm.getPriceOfPurchase().trim().equals(""))
					hm.put("Price Of Purchase", propertyDetailsForm.getPriceOfPurchase());
				}
	    	
	    	 if(propertyDetailsForm.getEstimatedValue()!=null){
	    		 if(!propertyDetailsForm.getEstimatedValue().trim().equals(""))
					hm.put("Estimated Value", propertyDetailsForm.getEstimatedValue());
				}
	    	
	    	 if( propertyDetailsForm.getAsOfDate()!=null){
					hm.put("As Of Date", sdf.format(propertyDetailsForm.getAsOfDate()));
				}
	    	
	    	 if( propertyDetailsForm.getAnnualRent()!=null){
	    		 if(!propertyDetailsForm.getAnnualRent().trim().equals(""))
					hm.put("Annual Rent", propertyDetailsForm.getAnnualRent());
				}
	    	
	    	 if( propertyDetailsForm.getPictures()!=null){
	    		 if(!propertyDetailsForm.getPictures().trim().equals(""))
					hm.put("Picture", propertyDetailsForm.getPictures());
				}
	    	
	    	 if( propertyDetailsForm.getLocalAuthority()!=null){
	    		 if(!propertyDetailsForm.getLocalAuthority().trim().equals(""))
					hm.put("Local Authority", propertyDetailsForm.getLocalAuthority());
				}
	    	
	    	 if( propertyDetailsForm.getRentalType()!=null){
	    		 if(!propertyDetailsForm.getRentalType().trim().equals("-1") )
					hm.put("Rental Type", propertyDetailsForm.getRentalType());
				}
	    	
	    	 if(propertyDetailsForm.getFrontDoorKeyCode()!=null){
	    		 if(!propertyDetailsForm.getFrontDoorKeyCode().trim().equals(""))
					hm.put("Front Door Key Code", propertyDetailsForm.getFrontDoorKeyCode());
				}
	    	
	    	 if( propertyDetailsForm.getBackDoorKeyCode()!=null){
	    		 if(!propertyDetailsForm.getBackDoorKeyCode().trim().equals(""))
					hm.put("Back Door Key Code", propertyDetailsForm.getBackDoorKeyCode());
				}
	    	
	    	 if( propertyDetailsForm.getPorchDoorKeyCode()!=null){
	    		 if(!propertyDetailsForm.getPorchDoorKeyCode().trim().equals(""))
					hm.put("Porch Door Key Code", propertyDetailsForm.getPorchDoorKeyCode());
				}
	    	
	    	 if( propertyDetailsForm.getFlatDoor()!=null){
	    		 if(!propertyDetailsForm.getFlatDoor().trim().equals(""))
					hm.put("Flat Door", propertyDetailsForm.getFlatDoor());
				}
	    	
	    	 if(propertyDetailsForm.getOthers()!=null){
	    		 if(!propertyDetailsForm.getOthers().trim().equals(""))
					hm.put("Notes", propertyDetailsForm.getOthers());
				}
	    	
	    	 if(  propertyDetailsForm.getMobileNumber()!=null){
	    		 if(propertyDetailsForm.getNumberOfBedrooms()!=0)
					hm.put("Number Of Bedrooms", String.valueOf(propertyDetailsForm.getNumberOfBedrooms()));
				}
	    	
	    	
	    	 if(propertyDetailsForm.getTenancyAgreement()!=null){
	    		 if(!propertyDetailsForm.getTenancyAgreement().trim().equals(""))
					hm.put("Tenancy Agreement", propertyDetailsForm.getTenancyAgreement());
				}
	    	
	    	 if( propertyDetailsForm.getInsuranceCertificate()!=null){
	    		 if(!propertyDetailsForm.getInsuranceCertificate().trim().equals(""))
					hm.put("Insurance Certificate", propertyDetailsForm.getInsuranceCertificate());
				}
	    	
	    	 if(propertyDetailsForm.getFloorPlan()!=null){
	    		 if(!propertyDetailsForm.getFloorPlan().trim().equals(""))
					hm.put("Floor Plan", propertyDetailsForm.getFloorPlan());
				}
	    	
	    	 if( propertyDetailsForm.getEpcCertificate()!=null){
	    		 if(!propertyDetailsForm.getEpcCertificate().trim().equals(""))
					hm.put("EPC Certificate", propertyDetailsForm.getEpcCertificate());
				}
	    	
	    	 if( propertyDetailsForm.getGasCertificate()!=null){
	    		 if(!propertyDetailsForm.getGasCertificate().trim().equals(""))
					hm.put("Gas Certificate", propertyDetailsForm.getGasCertificate());
				}
	    	
	    	 if(propertyDetailsForm.getElectricCertificate()!=null){
	    		 if(!propertyDetailsForm.getElectricCertificate().trim().equals(""))
					hm.put("Electric Certificate", propertyDetailsForm.getElectricCertificate());
				}
	    	
	    	 if( propertyDetailsForm.getHmoLicence()!=null){
	    		 if(!propertyDetailsForm.getHmoLicence().trim().equals(""))
					hm.put("HMO Licence", propertyDetailsForm.getHmoLicence());
				}
	    	
	    	 if( propertyDetailsForm.getContractsAndWarranties()!=null){
	    		 if(!propertyDetailsForm.getContractsAndWarranties().trim().equals(""))
					hm.put("Contracts And Warranties", propertyDetailsForm.getContractsAndWarranties());
				}
	    	
	    	 if(propertyDetailsForm.getLandRegistry()!=null){
	    		 if(!propertyDetailsForm.getLandRegistry().trim().equals(""))
					hm.put("Land Registry", propertyDetailsForm.getLandRegistry());
				}
	    	
	    	 if(propertyDetailsForm.getCurrentTenancyForm()!=null){
	    		 if(!propertyDetailsForm.getCurrentTenancyForm().trim().equals(""))
					hm.put("Current Tenancy Form", propertyDetailsForm.getCurrentTenancyForm());
				}
	    	
	    	 if( propertyDetailsForm.getPropertyTimeline()!=null){
	    		 if(!propertyDetailsForm.getPropertyTimeline().trim().equals(""))
					hm.put("Property Timeline", propertyDetailsForm.getPropertyTimeline());
				}
	    	
	    	 if(!propertyDetailsForm.getLinkToJobs().trim().equals("") && propertyDetailsForm.getLinkToJobs()!=null){
	    		 if(!propertyDetailsForm.getLinkToJobs().trim().equals(""))
					hm.put("Link To Jobs", propertyDetailsForm.getLinkToJobs());
				}
	    	
	    	 if( propertyDetailsForm.getLendingInformation()!=null){
	    		 if(!propertyDetailsForm.getLendingInformation().trim().equals(""))
					hm.put("Lending Information", propertyDetailsForm.getLendingInformation());
				}
	    	
	    	 if(propertyDetailsForm.getManagementCompany()!=null){
	    		 if(!propertyDetailsForm.getManagementCompany().trim().equals(""))
					hm.put("Management Company", propertyDetailsForm.getManagementCompany());
				}
	    	 
	    	 if(propertyDetailsForm.getCompanyName()!=null){
	    		 if(!propertyDetailsForm.getCompanyName().trim().equals(""))
					hm.put("Company Name", propertyDetailsForm.getCompanyName());
				}
			
	    	 for(RoomDetails rd:roomDetails){
	    		 hm.put(rd.getRoomNo(), rd.getRoomName());
	    	 }
		}*/
		
		
		
		/*if(userForm.getFormTable().equalsIgnoreCase("TenancyForm")){
			TenancyForm tenancyForm=tenancyFormService.findTenancyFormByUserFormId(userForm.getId());
			
		
			if( tenancyForm.getEmployerCompany()!=null){
				if(!tenancyForm.getEmployerCompany().trim().equals(""))
				hm.put("Employer Company", tenancyForm.getEmployerCompany());
			}
			
			if( tenancyForm.getGuarantorCompany()!=null){
				if(!tenancyForm.getGuarantorCompany().trim().equals(""))
				hm.put("Guarantor Company", tenancyForm.getGuarantorCompany());
			}
			
			if( tenancyForm.getPropertyAddress()!=null){
				if(!tenancyForm.getPropertyAddress().trim().equals(""))
				hm.put("Property Address", tenancyForm.getPropertyAddress());
			}
			
			if( tenancyForm.getTypeOfRental()!=null){
				if(!tenancyForm.getTypeOfRental().trim().equals(""))
				hm.put("Type Of Rental", tenancyForm.getTypeOfRental());
			}
			
			if(tenancyForm.getRoomNumber()!=null ){
				if(tenancyForm.getRoomNumber()!="" )
				hm.put("Room Number", tenancyForm.getRoomNumber());
			}
		
			if(tenancyForm.getLandLordFirstName()!=null){
				if(!tenancyForm.getLandLordFirstName().trim().equals(""))
				hm.put("Landlord First Name", tenancyForm.getLandLordFirstName());
			}
			
			if(tenancyForm.getLandLordLastName()!=null){
				if(!tenancyForm.getLandLordLastName().trim().equals(""))
				hm.put("Landlord Last Name", tenancyForm.getLandLordLastName());
			}
			
			if( tenancyForm.getLandLordAddress()!=null){
				if(!tenancyForm.getLandLordAddress().trim().equals(""))
				hm.put("Landlord Address", tenancyForm.getLandLordAddress());
			}
			
			if( tenancyForm.getLandLordEmailAddress()!=null){
				if(!tenancyForm.getLandLordEmailAddress().trim().equals(""))
				hm.put("Landlord Email Address", tenancyForm.getLandLordEmailAddress());
			}
			
			if(tenancyForm.getLandLordContactNumber()!=null){
				if(!tenancyForm.getLandLordContactNumber().trim().equals(""))
				hm.put("Landlord Contact Number", tenancyForm.getLandLordContactNumber());
			}
			
			if( tenancyForm.getEmployerFirstName()!=null){
				if(!tenancyForm.getEmployerFirstName().trim().equals(""))
				hm.put("Employer First Name", tenancyForm.getEmployerFirstName());
			}
		
			if( tenancyForm.getEmployerLastName()!=null){
				if(!tenancyForm.getEmployerLastName().trim().equals(""))
				hm.put("Employer Last Name", tenancyForm.getEmployerLastName());
			}
			
			if( tenancyForm.getEmployerAddress()!=null){
				if(!tenancyForm.getEmployerAddress().trim().equals(""))
				hm.put("Employer Address", tenancyForm.getEmployerAddress());
			}
			
			if(tenancyForm.getEmployerEmailAddress()!=null){
				if(!tenancyForm.getEmployerEmailAddress().trim().equals(""))
				hm.put("Employer Email Address", tenancyForm.getEmployerEmailAddress());
			}
			
			if(tenancyForm.getEmployerContactNumber()!=null){
				if(!tenancyForm.getEmployerContactNumber().trim().equals(""))
				hm.put("Employer Contact Number", tenancyForm.getEmployerContactNumber());
			}
			
			if( tenancyForm.getGuarantorFirstName()!=null){
				if(!tenancyForm.getGuarantorFirstName().trim().equals(""))
				hm.put("Guarantor First Name", tenancyForm.getGuarantorFirstName());
			}
			
			if( tenancyForm.getGuarantorLastName()!=null){
				if(!tenancyForm.getGuarantorLastName().trim().equals(""))
				hm.put("Guarantor Last Name", tenancyForm.getGuarantorLastName());
			}
			
			if( tenancyForm.getGuarantorAddress()!=null){
				if(!tenancyForm.getGuarantorAddress().trim().equals(""))
				hm.put("Guarantor Address", tenancyForm.getGuarantorAddress());
			}
			
			if( tenancyForm.getGuarantorEmailAddress()!=null){
				if(!tenancyForm.getGuarantorEmailAddress().trim().equals(""))
				hm.put("Guarantor Email Address", tenancyForm.getGuarantorEmailAddress());
			}
			
			if( tenancyForm.getGuarantorContactNumber()!=null){
				if(!tenancyForm.getGuarantorContactNumber().trim().equals(""))
				hm.put("Guarantor Contact Number", tenancyForm.getGuarantorContactNumber());
			}
			
			if( tenancyForm.getKinFirstName()!=null){
				if(!tenancyForm.getKinFirstName().trim().equals(""))
				hm.put("Next of Kin First Name ", tenancyForm.getKinFirstName());
			}
			
			if( tenancyForm.getKinLastName()!=null){
				if(!tenancyForm.getKinLastName().trim().equals(""))
				hm.put("Next of Kin Last Name", tenancyForm.getKinLastName());
			}
			
			if( tenancyForm.getKinAddress()!=null){
				if(!tenancyForm.getKinAddress().trim().equals(""))
				hm.put("Next of Kin Address ", tenancyForm.getKinAddress());
			}
			
			if( tenancyForm.getKinEmailAddress()!=null){
				if(!tenancyForm.getKinEmailAddress().trim().equals(""))
				hm.put("Next of kin Email Address", tenancyForm.getKinEmailAddress());
			}
			
			if(tenancyForm.getKinContactNumber()!=null){
				if(!tenancyForm.getKinContactNumber().trim().equals(""))
				hm.put("Next of kin Contact Number", tenancyForm.getKinContactNumber());
			}
			
			if( tenancyForm.getTenancyStartedDate()!=null){
				
				hm.put("Tenancy Start Date", sdf.format(tenancyForm.getTenancyStartedDate()));
			}
			
			if(tenancyForm.getTenancyOpenedBy()!=null){
				if(!tenancyForm.getTenancyOpenedBy().trim().equals(""))
				hm.put("Tenancy Opened By", tenancyForm.getTenancyOpenedBy());
			}
			
			if( tenancyForm.getTenancyClosedBy()!=null){
				if(!tenancyForm.getPropertyAddress().trim().equals(""))
				hm.put("Tenancy Closed By", tenancyForm.getTenancyClosedBy());
			}
			
			if( tenancyForm.getTenancyClosedDate()!=null){
				
				hm.put("Tenancy Closed Date", sdf.format(tenancyForm.getTenancyClosedDate()));
			}
			
			if(tenancyForm.getCheckingOutForm()!=null){
				if(!tenancyForm.getCheckingOutForm().trim().equals(""))
				hm.put("CheckingOut Form", tenancyForm.getCheckingOutForm());
			}
			
			if( tenancyForm.getLinkToProperty()!=null){
				if(!tenancyForm.getLinkToProperty().trim().equals(""))
				hm.put("Link To Property", tenancyForm.getLinkToProperty());
			}
			
			if(tenancyForm.getLinkToTenancyAgreement()!=null){
				if(!tenancyForm.getLinkToTenancyAgreement().trim().equals(""))
				hm.put("Link To Tenancy Agreement", tenancyForm.getLinkToTenancyAgreement());
			}
			
			if(tenancyForm.getLinkToRentAccounts()!=null){
				if(!tenancyForm.getLinkToRentAccounts().trim().equals(""))
				hm.put("Link To Rent Accounts", tenancyForm.getLinkToRentAccounts());
			}
			
			if( tenancyForm.getLegalNotifications()!=null){
				if(!tenancyForm.getLegalNotifications().trim().equals(""))
				hm.put("Legal Notification", tenancyForm.getLegalNotifications());
			}
			
			if(tenancyForm.getTenancyDepositCertificate()!=null){
				if(!tenancyForm.getTenancyDepositCertificate().trim().equals(""))
				hm.put("Tenancy Deposit Certificate", tenancyForm.getTenancyDepositCertificate());
			}
			
			if(tenancyForm.getCouncilTaxRegistration()!=null){
				if(!tenancyForm.getCouncilTaxRegistration().trim().equals(""))
				hm.put("Council Tax Registration", tenancyForm.getCouncilTaxRegistration());
			}
			
			if(tenancyForm.getAdditionalLinks()!=null){
				if(!tenancyForm.getAdditionalLinks().trim().equals(""))
				hm.put("Additional Links", tenancyForm.getAdditionalLinks());
			}
			
			if( tenancyForm.getCorrespondenceWithTenants()!=null){
				if(!tenancyForm.getCorrespondenceWithTenants().trim().equals(""))
				hm.put("Correspondence With Tenant", tenancyForm.getCorrespondenceWithTenants());
			}
		
			if( tenancyForm.getCorrespondenceLink()!=null){
				if(!tenancyForm.getCorrespondenceLink().trim().equals(""))
				hm.put("Correspondence Links", tenancyForm.getCorrespondenceLink());
			}
			
			TenantDetails td=tenantDetailsService.findTenantDetailsForTenancy(tenancyForm.getId());
			
			for(TenantDetails td:tenantDetails){
			
				
				 if(td.getFirstName()!=null){
					 if(!td.getFirstName().trim().equals(""))
						hm.put("Tenant First Name", td.getFirstName());
					}
				
				 if( td.getTitle()!=null){
					 if(!td.getTitle().trim().equals(""))
						hm.put("Tenant Title", td.getTitle());
					}
				 
				 if(td.getLastName()!=null){
					 if(!td.getLastName().trim().equals(""))
						hm.put("Tenant Last Name", td.getLastName());
					}
				
				 if( td.getDateOfBirth()!=null){
						hm.put("Tenant Date Of Birth", sdf.format(td.getDateOfBirth()));
					}
				
				 if(td.getLandlineNumber()!=null){
					 if(!td.getLandlineNumber().trim().equals(""))
						hm.put("Tenant Landline Number", td.getLandlineNumber());
					}
			
				 if(td.getMobileNumber()!=null){
					 if(!td.getMobileNumber().trim().equals(""))
						hm.put("Tenant Mobile Number", td.getMobileNumber());
					}
				
				 if( td.getEmailAddress()!=null){
					 if(!td.getEmailAddress().trim().equals(""))
						hm.put("Tenant Email Address", td.getEmailAddress());
					}
				
				 if( td.getNationalInsuranceNumber()!=null){
					 if(!td.getNationalInsuranceNumber().trim().equals(""))
						hm.put("Tenant National Insurance Number Information", td.getNationalInsuranceNumber());
					}
				
				 if( td.getCurrentAddress()!=null){
					 if(!td.getCurrentAddress().trim().equals(""))
						hm.put("Tenant Current Address", td.getCurrentAddress());
					}
				 
				 if(td.getPassport()!=null){
					 if(!td.getPassport().trim().equals(""))
						hm.put("Tenant Passport", td.getPassport());
					}
				
				 if(td.getDriversLicense()!=null){
					 if(!td.getDriversLicense().trim().equals(""))
						hm.put("Tenant Driver License", td.getPassport());
					}
			
				 if(td.getCreditCheck()!=null){
					 if(!td.getCreditCheck().trim().equals(""))
						hm.put("Tenant Credit Check", td.getCreditCheck());
					}
			
				 if( td.getReferenceCheck()!=null){
					 if(!td.getReferenceCheck().trim().equals(""))
						hm.put("Tenant Reference Check", td.getReferenceCheck());
					}
				
				 if(td.getAdditionalRemarks()!=null){
					 if(!td.getAdditionalRemarks().trim().equals(""))
						hm.put("Tenant Additional Remark", td.getAdditionalRemarks());
					}
			}*/
	//	}
		
		/*if(userForm.getFormTable().equalsIgnoreCase("UtilitiesCompanyDetailsForm")){
			UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=utilitiesCompanyDetailsFormService.findUtilitiesCompanyDetailsFormByUserFormId(userForm.getId());
			
			if( utilitiesCompanyDetailsForm.getCompanyName()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Company Name", utilitiesCompanyDetailsForm.getCompanyName());
			}
	    	
			if(utilitiesCompanyDetailsForm.getCompanyType()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Company Type", utilitiesCompanyDetailsForm.getCompanyType());
			}
	    	
	    	
			if( utilitiesCompanyDetailsForm.getCompanyAddress()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Company Address", utilitiesCompanyDetailsForm.getCompanyAddress());
			}
	    	
	    	
			if(utilitiesCompanyDetailsForm.getCompanyTelephone()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Company Telephone", utilitiesCompanyDetailsForm.getCompanyTelephone());
			}
	    	
	    	
			if(utilitiesCompanyDetailsForm.getOpeningAndClosingTimes()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Opening and Closing Times", utilitiesCompanyDetailsForm.getOpeningAndClosingTimes());
			}
	    	
	    	
			if(utilitiesCompanyDetailsForm.getMainContactName()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Main Contact Name", utilitiesCompanyDetailsForm.getMainContactName());
			}
	    	
	    	 
			if(utilitiesCompanyDetailsForm.getMainContactTelephone()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Main Contact Telephone", utilitiesCompanyDetailsForm.getMainContactTelephone());
			}
	    	
	    	
			if(utilitiesCompanyDetailsForm.getMainContactEmailAddress()!=null){
				if(!utilitiesCompanyDetailsForm.getCompanyName().trim().equals("") )
				hm.put("Main Contact Email Address", utilitiesCompanyDetailsForm.getMainContactEmailAddress());
			}
	    	
	    	
	    	 
		}*/
		
		/*if(userForm.getFormTable().equalsIgnoreCase("BuilderDetailsForm")){
			BuilderDetailsForm builderDetailsForm=builderDetailsFormService.findBuilderDetailsFormByUserFormId(userForm.getId());
			
			if(builderDetailsForm.getFirstName()!=null){
				if(!builderDetailsForm.getFirstName().trim().equals("") )
				hm.put("First Name", builderDetailsForm.getFirstName());
			}
	    	
	    	 if( builderDetailsForm.getLastName()!=null){
	    		 if(!builderDetailsForm.getLastName().trim().equals("") )
					hm.put("Last Name", builderDetailsForm.getLastName());
				}
	    	
	    	 if( builderDetailsForm.getEmailAddress()!=null){
	    		 if(!builderDetailsForm.getEmailAddress().trim().equals("") )
					hm.put("Email Address", builderDetailsForm.getEmailAddress());
				}
	    	
	    	 if( builderDetailsForm.getLandlineNumber()!=null){
	    		 if(!builderDetailsForm.getLandlineNumber().trim().equals("") )
					hm.put("Landline Number", builderDetailsForm.getLandlineNumber());
				}
	    	
	    	 if(builderDetailsForm.getMobileNumber()!=null){
	    		 if(!builderDetailsForm.getMobileNumber().trim().equals("") )
					hm.put("Mobile Number", builderDetailsForm.getMobileNumber());
				}
	    	
	    	 if(builderDetailsForm.getCompanyName()!=null){
	    		 if(!builderDetailsForm.getCompanyName().trim().equals("") )
					hm.put("Company Name", builderDetailsForm.getCompanyName());
				}
	    	 
	    	 if(builderDetailsForm.getCompanyAddress()!=null){
	    		 if(!builderDetailsForm.getCompanyAddress().trim().equals("") )
					hm.put("Company Address", builderDetailsForm.getCompanyAddress());
				}
	    	
	    	 if(builderDetailsForm.getCompanyNumber()!=null){
	    		 if(!builderDetailsForm.getCompanyNumber().trim().equals("") )
					hm.put("Company Number", builderDetailsForm.getCompanyNumber());
				}
	    	
	    	 if(builderDetailsForm.getVatNumber()!=null){
	    		 if(!builderDetailsForm.getVatNumber().trim().equals("") )
					hm.put("VAT Number", builderDetailsForm.getVatNumber());
				}
	    	
	    	 if(builderDetailsForm.getAccountHoldersName()!=null){
	    		 if(!builderDetailsForm.getAccountHoldersName().trim().equals("") )
					hm.put("Account Holder's Name", builderDetailsForm.getAccountHoldersName());
				}
	    	
	    	 if(builderDetailsForm.getAccountNumber()!=null){
	    		 if(!builderDetailsForm.getAccountNumber().trim().equals("") )
					hm.put("Account Number", builderDetailsForm.getAccountNumber());
				}
	    	
	    	 if( builderDetailsForm.getSortCode()!=null){
	    		 if(!builderDetailsForm.getSortCode().trim().equals("") )
					hm.put("Sort Code", builderDetailsForm.getSortCode());
				}
	    	
	    	 if(builderDetailsForm.getLinkToInvoice()!=null){
	    		 if(!builderDetailsForm.getLinkToInvoice().trim().equals("") )
					hm.put("Link To Invoice", builderDetailsForm.getLinkToInvoice());
				}
	    	
	    	 if(builderDetailsForm.getLinkToJobs()!=null){
	    		 if(!builderDetailsForm.getLinkToJobs().trim().equals("") )
					hm.put("Link To Jobs", builderDetailsForm.getLinkToJobs());
				}
		}*/
		
/*if(userForm.getFormTable().equalsIgnoreCase("Inventory")){
			
			Inventory inventory=inventoryService.findInventoryFormByUserFormId(userForm.getId());
			
			if(inventory.getTenantName()!=null){
				if(!inventory.getTenantName().trim().equals("") )
				hm.put("Tenant Name", inventory.getTenantName());
			}
	    	
			if(inventory.getAddress()!=null){
				if(!inventory.getAddress().trim().equals("") )
				hm.put("Tenant Address", inventory.getAddress());
			}
	    	
	    	
			if(inventory.getRoom()!=null){
				if(!inventory.getRoom().trim().equals("") )
				hm.put("Rooms", inventory.getRoom());
			}
	    	
	    	
			if(inventory.getFurnishingsList()!=null){
				if(!inventory.getFurnishingsList().trim().equals("") )
				hm.put("List Of Furnishes", inventory.getFurnishingsList());
			}
	    	
	    	
			if(inventory.getOk()!=null){
				if(!inventory.getOk().trim().equals("-1") )
				hm.put("Everything OK?", inventory.getOk());
			}
	    	
	    	
			if(inventory.getNotes()!=null){
				if(!inventory.getNotes().trim().equals("") )
				hm.put("Notes/Details", inventory.getNotes());
			}
	    	
	    	 
			if(inventory.getPhotos()!=null){
				if(!inventory.getPhotos().trim().equals("") )
				hm.put("Photo", inventory.getPhotos());
			}
	    	
			StringBuilder jsonString=new StringBuilder();
			HashMap<String,String> map =null;
			if(inventory.getPropertyForm()!=null){
				if(!inventory.getPropertyForm().trim().equals("") )
				hm.put("Property Form", inventory.getPropertyForm());
			}
			
				List<InventoryRooms> inventoryRooms=inventoryRoomsService.findInventoryRoomsForInventory(inventory);
				for(InventoryRooms inr:inventoryRooms){
				
				if(inr.getFieldName().equals("json")){
				 jsonString= inr.getFieldValue();
				 System.out.println();
				map= new Gson().fromJson(jsonString, new TypeToken<HashMap<String, String>>(){}.getType());
					jsonString.append(inr.getFieldValue());
					jsonString.deleteCharAt(0);
					jsonString.deleteCharAt(jsonString.length()-1);
					System.out.println(jsonString.toString());
					 map= new HashMap<String,String>();
					ObjectMapper mapper = new ObjectMapper();
				 
					try {
				 
						//convert JSON string to Map
						map = mapper.readValue(jsonString.toString(), 
						    new TypeReference<HashMap<String,String>>(){});
				 
						System.out.println(map);
				 
					} catch (Exception e) {
						e.printStackTrace();
					}
				System.out.println(map);
				}else{
					hm.put(inr.getFieldName(), inr.getFieldValue());
				}		
			}
				if(map!=null){
					Set<Entry<String, String>> setmap=map.entrySet();
			 		Iterator<Entry<String, String>> fdjson=setmap.iterator();
			 		
			 		while (fdjson.hasNext()) {
			 			
			 			Entry<String, String> fdjs = fdjson.next();	
			 			if(fdjs.getKey().startsWith("item")){
			 				hm.put(fdjs.getKey(), fdjs.getValue());
			 			}
			 		}
				}
	    	
	    	}
		
		
		Set<Entry<String, String>> setHm=hm.entrySet();
		Iterator<Entry<String, String>> fdi=setHm.iterator();
		
		sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p>")
		.append(userForm.getForms().getFormDefs().getName()).append(" - ")
		.append(userForm.getForms().getFormDefs().getDescription()).append("</p><p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>")
		.append("<tr>").append("<td style='text-align:center;background:silver'>").append("Field").append("</td>")
		.append("<td style='text-align:center;background:silver'>").append("Value");
		
		while (fdi.hasNext()) {
			Entry<String, String> fd = fdi.next();
			
			
			
				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
					.append(fd.getKey()).append("</td>").append("<td>");
			
			
			
			
			sb.append(fd.getValue());
			}*/
		sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");
		return sb.toString();
	}
	
	
	/*Data table format for csv download
	 * @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String createHtmlRepresentation(UserForms userForm) {

		StringBuilder sb = new StringBuilder();
		Iterator<FormData> fdi = userForm.getFormDatas().iterator();
		String lastField = null;
		sb.append("<html>").append("<head>").append("</head>").append("<body><div class='main'><div class='spacer'><div class='bor' >").append("<p>")
			.append(userForm.getForms().getFormDefs().getName()).append(" - ")
			.append(userForm.getForms().getFormDefs().getDescription()).append("</p>").append("<table class='display' id='formTableVal' border='1'>")
			.append("<thead><tr class='first-row'>").append("<th class='ceas-col'>").append("Field").append("</th>")
			.append("<th class='col order'>").append("Value");
		while (fdi.hasNext()) {
			FormData fd = fdi.next();
			if (!fd.getHtmlFormDef().getFormFieldMap().getFormFieldMaps().isEmpty())
				continue;

			String currentField = fd.getHtmlFormDef().getName();
			if (!currentField.equals(lastField)) {
				lastField = currentField;
				sb.append("</th>").append("</tr></thead>").append("<tr>").append("<td>")
					.append(fd.getHtmlFormDef().getFormFieldMap().getFields().getDescription()).append("</td>").append("<td>");
			}
			else
				sb.append("<br />");

			sb.append(valueToString(fd.getTextValue(), fd.getDateValue(), fd.getIntValue(), fd.getNumberValue()));
		}

		sb.append("</td>").append("</tr>").append("</table>").append("</div></div></div></body>").append("</html>");

		return sb.toString();
	}*/
	
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String createHtmlRepresentationForPrint(UserForms userForm) {

		StringBuilder sb = new StringBuilder();
		Iterator<FormData> fdi = userForm.getFormDatas().iterator();
		String lastField = null;
		sb.append("<html>").append("<head>").append("</head>").append("<body onload='window.print(); window.close();'>").append("<p>")
			.append(userForm.getForms().getFormDefs().getName()).append(" - ")
			.append(userForm.getForms().getFormDefs().getDescription()).append("</p>").append("<table border='1'>")
			.append("<tr>").append("<td style='text-align:center;background:silver'>").append("Field").append("</td>")
			.append("<td style='text-align:center;background:silver'>").append("Value");
		while (fdi.hasNext()) {
			FormData fd = fdi.next();
			if (!fd.getHtmlFormDef().getFormFieldMap().getFormFieldMaps().isEmpty())
				continue;
			
			String currentField = fd.getHtmlFormDef().getName();
			if (!currentField.equals(lastField)) {
				lastField = currentField;
				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
					.append(fd.getHtmlFormDef().getFormFieldMap().getFields().getDescription()).append("</td>").append("<td>");
			}
			else
				sb.append("<br />");

			sb.append(valueToString(fd.getTextValue(), fd.getDateValue(), fd.getIntValue(), fd.getNumberValue()));
		}

		sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");

		return sb.toString();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void processWorkflowEvent(long userFormId, String author, String event) {

		String title = null;
		Date start = null;
		Date end = null;
		HashSet<String> cc = new HashSet<String>();
		HashSet<String> to = new HashSet<String>();
		String s;
		User u;
		UserForms userForm = userFormsDao.findUserFormsById(userFormId);
		Iterator<FormData> fdi = userForm.getFormDatas().iterator();
		while (fdi.hasNext()) {
			FormData fd = fdi.next();
			if (fd.getHtmlFormDef().getName().equals("f_1_2")) {
				title = fd.getTextValue();
			}
			else if (fd.getHtmlFormDef().getName().equals("f_1_3")) {
				start = fd.getDateValue();
			}
			else if (fd.getHtmlFormDef().getName().equals("f_1_4")) {
				end = fd.getDateValue();
			}
			else if (fd.getHtmlFormDef().getName().equals("f_1_5") || fd.getHtmlFormDef().getName().equals("f_1_7")) {
				u = userDao.findUserByUserName(fd.getTextValue());
				if (u == null)
					continue;

				s = u.getEmailId();
				if (s == null)
					continue;

				s = s.trim();
				if (s.length() == 0)
					continue;

				if (fd.getHtmlFormDef().getName().equals("f_1_5")) {
					to.add(s);
				}
				else {
					cc.add(s);
				}
			}
		}

		u = userDao.findUserByUserName(author);
		if (u != null) {
			s = u.getEmailId();
			if (s != null) {
				s = s.trim();

				if (s.length() > 0)
					to.add(s);
			}
		}

		Iterator<String> itr = to.iterator();
		while (itr.hasNext()) {
			s = itr.next();
			cc.remove(s);
		}

		String uid = userForm.getId() + "-" + userForm.getCreatedOn().getTime();
		
		Date createOn = userForm.getCreatedOn();
		Date updatedOn = userForm.getUpdatedOn();
		if (updatedOn == null) {
			updatedOn = createOn;
		}

		boolean invitation = "Approved".equals(event);
		s = createICS(invitation, uid, title, author, start, end, createOn, updatedOn);
		Attachment[] ics = new Attachment[2];
		ics[0] = new Attachment();
		ics[0].setInline(false);
		ics[0].setMimeType("application/ics");
		ics[0].setName("invite.ics");
		ics[0].setIns(new ByteArrayResource(s.getBytes()));
		s = createHtmlRepresentation(userForm);
		ics[1] = new Attachment();
		ics[1].setInline(false);
		ics[1].setMimeType("text/html");
		ics[1].setName("form.html");
		ics[1].setIns(new ByteArrayResource(s.getBytes()));

		if (invitation) {
			emailService.sendEmail(to.toArray(new String[0]), cc.toArray(new String[0]), null, "Invitation:  " + title,
				"Invite to " + title + " event/function/meeting. See/open attachment to add to your calendar.", ics);
		}
		else {
			emailService.sendEmail(to.toArray(new String[0]), cc.toArray(new String[0]), null, "Cancelled Event: " + title, title
				+ " event/function/meeting has been cancelled. See/open attachment to remove from your calendar.", ics);
		}
	}

	String createICS(boolean isInvite, String uid, String title, String author, Date start, Date end, Date created, Date modified) {
		
		SimpleDateFormat sf = new SimpleDateFormat(Utils.CAL_TIMESTAMP_FORMAT_STR);
		/*StringBuilder sb = new StringBuilder("BEGIN:VCALENDAR\r\n").append("PRODID:infoFINIUM\r\n").append("VERSION:2.0\r\n")
			.append("CALSCALE:GREGORIAN\r\n").append("METHOD:").append((isInvite) ? "REQUEST" : "CANCEL").append("\r\n")
			.append("BEGIN:VEVENT\r\n").append("CREATED:").append(sf.format(created)).append("\r\n").append("DESCRIPTION:")
			.append(title).append("\r\n").append("DTEND:").append(sf.format(end)).append("\r\n").append("DTSTAMP:")
			.append(sf.format(new Date(0))).append("\r\n").append("DTSTART:").append(sf.format(start)).append("\r\n")
			.append("LAST-MODIFIED:").append(sf.format(modified)).append("\r\n").append("LOCATION:office\r\n")
			.append("ORGANIZER;CN=").append(author).append("\r\n").append("SEQUENCE:").append((isInvite) ? "0" : "1")
			.append("\r\n").append("STATUS:").append((isInvite) ? "CONFIRMED" : "CANCELLED").append("\r\n").append("SUMMARY:")
			.append(title).append("\r\n").append("TRANSP:OPAQUE\r\n").append("UID:").append(uid).append("\r\n")
			.append("END:VEVENT\r\n").append("END:VCALENDAR\r\n");*/
		StringBuilder sb = new StringBuilder("BEGIN:VCALENDAR\r\n").append("PRODID:infoFINIUM\r\n").append("VERSION:2.0\r\n")
				.append("CALSCALE:GREGORIAN\r\n").append("METHOD:").append((isInvite) ? "REQUEST" : "CANCEL").append("\r\n")
				.append("BEGIN:VEVENT\r\n").append("CREATED:").append("").append("\r\n").append("DESCRIPTION:")
				.append(title).append("\r\n").append("DTEND:").append("").append("\r\n").append("DTSTAMP:")
				.append(sf.format(new Date(0))).append("\r\n").append("DTSTART:").append("").append("\r\n")
				.append("LAST-MODIFIED:").append("").append("\r\n").append("LOCATION:office\r\n")
				.append("ORGANIZER;CN=").append(author).append("\r\n").append("SEQUENCE:").append((isInvite) ? "0" : "1")
				.append("\r\n").append("STATUS:").append((isInvite) ? "CONFIRMED" : "CANCELLED").append("\r\n").append("SUMMARY:")
				.append(title).append("\r\n").append("TRANSP:OPAQUE\r\n").append("UID:").append(uid).append("\r\n")
				.append("END:VEVENT\r\n").append("END:VCALENDAR\r\n");

		return sb.toString();
	}
	
	@Override
	public UserForms cloneUserForm(long userFormId, long userId) {

		UserForms uf = userFormsDao.findUserFormsById(userFormId);
		return cloneUserForm(uf, userId);
	}
	
	@Override
	public UserForms cloneUserForm(UserForms userForm, long userId) {
		UserForms newUserForm = new UserForms();
		newUserForm.setForms(userForm.getForms());
		newUserForm.setStatus('d');
		newUserForm.setUserId(userId);
		newUserForm.setSecurityGroupId(userForm.getSecurityGroupId());
		newUserForm.setCompanyId(userForm.getCompanyId());
		newUserForm.setSubmissionDate(userForm.getSubmissionDate());
		newUserForm.setModelId(userForm.getModelId());
		newUserForm.setActive('Y');
		newUserForm.setFormTable(userForm.getFormTable());
		userFormsDao.save(newUserForm);
		newUserForm.setTitle(userForm.getForms().getFormDefs().getName()+"-"+newUserForm.getId());
		userFormsDao.save(newUserForm);
		/*Set<FormData> newFormData = newUserForm.getFormDatas();
		Iterator<FormData> oldFormData = userForm.getFormDatas().iterator();
		while (oldFormData.hasNext()) {
			FormData old = oldFormData.next();
			FormData fd = new FormData();
			fd.setUserForms(newUserForm);
			fd.setHtmlFormDef(old.getHtmlFormDef());
			fd.setDateValue(old.getDateValue());
			fd.setIntValue(old.getIntValue());
			fd.setNumberValue(old.getNumberValue());
			fd.setTextValue(old.getTextValue());
			fd.setVindex(old.getVindex());
			newFormData.add(fd);
		}*/
		
		/*if(userForm.getFormTable().equalsIgnoreCase("PotentialTenantForm")){
			PotentialTenantForm potentialTenantForm=potentialTenantFormService.findPotentialTenantFormByUserFormId(userForm.getId());
			if(potentialTenantForm!=null){
			PotentialTenantForm newPotentialTenantForm=new PotentialTenantForm(newUserForm.getId(), potentialTenantForm.getTitle(), potentialTenantForm.getFirstName(), potentialTenantForm.getLastName(), potentialTenantForm.getLandLineNumber(), potentialTenantForm.getMobileNumber(), potentialTenantForm.getEmailAddress(), potentialTenantForm.getNationalInsuranceNumber(), potentialTenantForm.getCurrentAddress(), potentialTenantForm.getType(), potentialTenantForm.getNumberOfBedRooms(), potentialTenantForm.getGarden(), potentialTenantForm.getOffRoadParking(), potentialTenantForm.getGarage(), potentialTenantForm.getGarden());
			potentialTenantFormService.save(newPotentialTenantForm);
			}
		}*/
		
		/*if(userForm.getFormTable().equalsIgnoreCase("PropertyDetailsForm")){
			PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormByUserFormId(userForm.getId());
			if(propertyDetailsForm!=null){
			PropertyDetailsForm newPropertyDetailsForm=new PropertyDetailsForm(newUserForm.getId(), propertyDetailsForm.getClient(), propertyDetailsForm.getFirstName(), propertyDetailsForm.getLastName(), propertyDetailsForm.getLandlineNumber(), propertyDetailsForm.getMobileNumber(), propertyDetailsForm.getEmailAddress(), propertyDetailsForm.getAddress(), propertyDetailsForm.getAccountNumber(), propertyDetailsForm.getAccountHoldersName(), propertyDetailsForm.getSortCode(), propertyDetailsForm.getVatDetails(), propertyDetailsForm.getReferencesForPayment(), propertyDetailsForm.getPropertyAddressLine1(), propertyDetailsForm.getPropertyAddressLine2(), propertyDetailsForm.getTown(), propertyDetailsForm.getCityCountry(), propertyDetailsForm.getPropertyPostCode(), propertyDetailsForm.getPropertyType(), propertyDetailsForm.getPropertyDescription(), propertyDetailsForm.getHouseMeasurements(), propertyDetailsForm.getGasMeterLocation(), propertyDetailsForm.getElectricMeterLocation(), propertyDetailsForm.getWaterMeterLocation(), propertyDetailsForm.getDateOfPerchase(), propertyDetailsForm.getPriceOfPurchase(), propertyDetailsForm.getEstimatedValue(), propertyDetailsForm.getAsOfDate(), propertyDetailsForm.getAnnualRent(), propertyDetailsForm.getPictures(), propertyDetailsForm.getLocalAuthority(), propertyDetailsForm.getRentalType(), propertyDetailsForm.getFrontDoorKeyCode(), propertyDetailsForm.getBackDoorKeyCode(), propertyDetailsForm.getPorchDoorKeyCode(), propertyDetailsForm.getFlatDoor(), propertyDetailsForm.getOthers(), propertyDetailsForm.getNumberOfBedrooms(), propertyDetailsForm.getTenancyAgreement(), propertyDetailsForm.getInsuranceCertificate(), propertyDetailsForm.getFloorPlan(), propertyDetailsForm.getEpcCertificate(), propertyDetailsForm.getGasCertificate(), propertyDetailsForm.getElectricCertificate(), propertyDetailsForm.getHmoLicence(), propertyDetailsForm.getContractsAndWarranties(), propertyDetailsForm.getLandRegistry(), propertyDetailsForm.getCurrentTenancyForm(), propertyDetailsForm.getPropertyTimeline(), propertyDetailsForm.getLinkToJobs(), propertyDetailsForm.getLendingInformation(), propertyDetailsForm.getManagementCompany(), propertyDetailsForm.getCompanyName());
			propertyDetailsFormService.save(newPropertyDetailsForm);
			List<RoomDetails> roomDetails=roomDetailsService.findRoomDetailsByPropertyDetailsFormId(propertyDetailsForm);
			if(roomDetails!=null){
				for(RoomDetails rd:roomDetails){
					RoomDetails newRoomDetails=new RoomDetails(newPropertyDetailsForm, rd.getRoomNo(), rd.getRoomName());
					roomDetailsService.save(newRoomDetails);
				}
			}
			
			}
		}*/
		
	/*	if(userForm.getFormTable().equalsIgnoreCase("TenancyForm")){
			TenancyForm tenancyForm=tenancyFormService.findTenancyFormByUserFormId(userForm.getId());
			if(tenancyForm!=null){
			TenancyForm newTenancyForm=new TenancyForm(newUserForm.getId(), tenancyForm.getPropertyAddress(), tenancyForm.getTypeOfRental(), tenancyForm.getRoomNumber(), tenancyForm.getLandLordFirstName(), tenancyForm.getLandLordLastName(), tenancyForm.getLandLordAddress(), tenancyForm.getLandLordEmailAddress(), tenancyForm.getLandLordContactNumber(), tenancyForm.getEmployerFirstName(), tenancyForm.getLandLordLastName(), tenancyForm.getEmployerAddress(), tenancyForm.getEmployerEmailAddress(), tenancyForm.getEmployerContactNumber(), tenancyForm.getGuarantorFirstName(), tenancyForm.getGuarantorLastName(), tenancyForm.getGuarantorAddress(), tenancyForm.getGuarantorEmailAddress(), tenancyForm.getGuarantorContactNumber(), tenancyForm.getKinFirstName(), tenancyForm.getKinLastName(), tenancyForm.getKinAddress(), tenancyForm.getKinEmailAddress(), tenancyForm.getKinContactNumber(), tenancyForm.getTenancyOpenedBy(), tenancyForm.getTenancyStartedDate(), tenancyForm.getTenancyClosedBy(), tenancyForm.getTenancyClosedDate(), tenancyForm.getCheckingOutForm(), tenancyForm.getLinkToProperty(), tenancyForm.getLinkToTenancyAgreement(), tenancyForm.getLinkToRentAccounts(), tenancyForm.getLegalNotifications(), tenancyForm.getTenancyDepositCertificate(), tenancyForm.getCouncilTaxRegistration(), tenancyForm.getAdditionalLinks(), tenancyForm.getCorrespondenceWithTenants(), tenancyForm.getCorrespondenceLink(),tenancyForm.getEmployerCompany(),tenancyForm.getGuarantorCompany());
			tenancyFormService.save(newTenancyForm);
			TenantDetails td=tenantDetailsService.findTenantDetailsForTenancy(tenancyForm.getId());
			if(td!=null){
				for(TenantDetails td:tenantDetails){
					TenantDetails newTenantDetails=new TenantDetails(newTenancyForm.getId(), td.getTitle(), td.getFirstName(), td.getLastName(), td.getDateOfBirth(), td.getLandlineNumber(), td.getMobileNumber(), td.getEmailAddress(), td.getNationalInsuranceNumber(), td.getCurrentAddress(), td.getPassport(), td.getDriversLicense(), td.getCreditCheck(), td.getReferenceCheck(), td.getAdditionalRemarks());
					tenantDetailsService.save(newTenantDetails);
				//}
			}
			
			}
		}*/
		
		
		
	/*	if(userForm.getFormTable().equalsIgnoreCase("BuilderDetailsForm")){
			BuilderDetailsForm builderDetailsForm=builderDetailsFormService.findBuilderDetailsFormByUserFormId(userForm.getId());
			if(builderDetailsForm!=null){
			BuilderDetailsForm newBuilderDetailsForm=new BuilderDetailsForm(newUserForm.getId(), builderDetailsForm.getFirstName(), builderDetailsForm.getLastName(), builderDetailsForm.getEmailAddress(), builderDetailsForm.getLandlineNumber(), builderDetailsForm.getMobileNumber(), builderDetailsForm.getCompanyName(), builderDetailsForm.getCompanyAddress(), builderDetailsForm.getCompanyNumber(), builderDetailsForm.getVatNumber(), builderDetailsForm.getAccountHoldersName(), builderDetailsForm.getAccountNumber(), builderDetailsForm.getSortCode(), builderDetailsForm.getLinkToInvoice(), builderDetailsForm.getLinkToJobs());
			builderDetailsFormService.save(newBuilderDetailsForm);
			
			
			
			}
		}*/
		
		/*if(userForm.getFormTable().equalsIgnoreCase("UtilitiesCompanyDetailsForm")){
			UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm=utilitiesCompanyDetailsFormService.findUtilitiesCompanyDetailsFormByUserFormId(userForm.getId());
			if(utilitiesCompanyDetailsForm!=null){
			UtilitiesCompanyDetailsForm newCompanyDetailsForm=new UtilitiesCompanyDetailsForm(newUserForm.getId(), utilitiesCompanyDetailsForm.getCompanyName(), utilitiesCompanyDetailsForm.getCompanyType(), utilitiesCompanyDetailsForm.getCompanyTelephone(), utilitiesCompanyDetailsForm.getCompanyAddress(), utilitiesCompanyDetailsForm.getOpeningAndClosingTimes(), utilitiesCompanyDetailsForm.getMainContactName(), utilitiesCompanyDetailsForm.getMainContactTelephone(), utilitiesCompanyDetailsForm.getMainContactEmailAddress());
			utilitiesCompanyDetailsFormService.save(newCompanyDetailsForm);
			}
		}*/
		
		/*if(userForm.getFormTable().equalsIgnoreCase("Inventory")){
			Inventory inventory=inventoryService.findInventoryFormByUserFormId(userForm.getId());
			if(inventory!=null){
			Inventory inventory2=new Inventory(userForm.getId(),  inventory.getPropertyForm());
			inventoryService.save(inventory2);
			}
		}*/
		
		
		return newUserForm;
	}

	@Override
	public Forms findFormsByName(String name) {
		// TODO Auto-generated method stub
		List<Forms> forms=hibernateTemplate.find("from Forms where name=?",name);
		if(forms.size()>=1)
			return forms.get(0);
		return null;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String createHtmlRepresentationforRentPaymentTransactionHistorybyUserID(long userId){
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		HashMap<String, String> hm=new HashMap<String,String>();
		
		System.out.println("<<<<<<<<<<<<<<>>control in createHtmlRepresentationforRentPaymentTransactionHistorybyUserID");
		
	    List<TransactionDetails> lstTransactionDetails = transactionDetailService.findTransactionbyUserid(userId);
	    
	    sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>");
	    sb.append("<tr><td colspan=7 style='text-align:center;background:silver'>").append("RentPaymentTransactionHistory").append("</td></tr>");
	    sb.append("<tr>").append("<td style='text-align:center;background:silver'>").append("S.No")
	    .append("<td style='text-align:center;background:silver'>").append("Tenancy")
 		.append("</td>").append("<td style='text-align:center;background:silver'>").append("Property")
 		.append("</td>").append("<td style='text-align:center;background:silver'>").append("Transaction Type")
 		.append("</td>").append("<td style='text-align:center;background:silver'>").append("Transaction Date")
 		.append("</td>").append("<td style='text-align:center;background:silver'>").append("Amount")
 		.append("</td>").append("<td style='text-align:center;background:silver'>").append("Rent Received");
	    
	    if(lstTransactionDetails.size()>0)
	    {
	   
		    List<TransactionDetails> transList = new ArrayList<TransactionDetails>();
			Iterator<TransactionDetails> fdi = lstTransactionDetails.iterator();
			int i=1;
			while (fdi.hasNext()) {
				TransactionDetails td = fdi.next();
				
				sb.append("<tr>").append("<td>").append(i++).append("</td>").append("<td>").append(td.getTenancyId()).append("</td>").append("<td>").append(td.getPropertyId()).append("</td>").append("<td>").append(td.getTransactiontype()).append("</td>").append("<td>").append(td.getTransactiondate()).append("</td>").append("<td>").append(td.getAmount()).append("</td>").append("<td>").append(td.getRentreceived()).append("</td>");
				System.out.println("Tenancy ID"+ String.valueOf(td.getPropertyId()));
				System.out.println("Rentreceived"+ td.getRentreceived());
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			}
	    }
	    else
	    {
	    	sb.append("<tr><td colspan=7 style='text-align:center'>");
	    	sb.append("No data available for the user");
	    }
	    
 		sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");
	    
	return sb.toString();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String createHtmlRepresentationforViewGivingNoticeForm(long userId){
		
		User user=userService.findUserById(userId);
		
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		HashMap<String, String> hm=new HashMap<String,String>();
		System.out.println("<<<<<<<<<<<<<<>>control in createHtmlRepresentationforViewGivingNoticeForm");
		GivingNoticeForm givingNoticeForm = givingNoticeFormService.findGivingNoticeStatusByUserId(user);
		
		TenancyForm tenancyForm=tenancyFormService.findTenancyFormById(givingNoticeForm.getTenancyId());
		PropertyDetailsForm propertyDetailsForm=propertyDetailsFormService.findPropertyDetailsFormById(givingNoticeForm.getPropertyId());
		
		if(givingNoticeForm.getTenancyId() != 0)
			hm.put("Tenancy ID", String.valueOf(tenancyForm.getTenantFirstName()+" "+ tenancyForm.getTenantLastName()));
		
		if(givingNoticeForm.getPropertyId() != 0)
			hm.put("Property ID", String.valueOf(propertyDetailsForm.getPropertyTitle()));
		
		if(givingNoticeForm.getRentaccountsId() != 0)
			hm.put("Rentaccounts ID", String.valueOf(givingNoticeForm.getRentaccountsId()));
		
		if(givingNoticeForm.getTenantName() != null)
			hm.put("Tenant Name", givingNoticeForm.getTenantName());
		
		if(givingNoticeForm.getAddress() != null)
			hm.put("Address", givingNoticeForm.getAddress());
		
		if(givingNoticeForm.getLeavingDate() != null)
			hm.put("Leaving Date", givingNoticeForm.getLeavingDate());
		
		if(givingNoticeForm.getForwardAddress() != null)
			hm.put("Forward Address", givingNoticeForm.getForwardAddress());
		
		if(givingNoticeForm.getAccountName() != null)
			hm.put("Account Name", givingNoticeForm.getAccountName());
		
		if(givingNoticeForm.getAccountNumber() != null)
			hm.put("Account Number", givingNoticeForm.getAccountNumber());
		
		if(givingNoticeForm.getSortCode() != null)
			hm.put("Sort Code", givingNoticeForm.getSortCode());
		
		
		Set<Entry<String, String>> setHm=hm.entrySet();
 		Iterator<Entry<String, String>> fdi=setHm.iterator();
 		
 		sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>")
 		.append("<tr><td colspan=7 style='text-align:center;background:silver'>").append("Giving Notice Details").append("</td></tr>")
 		.append("<tr>").append("<td style='text-align:center;background:silver'>").append("Field").append("</td>")
 		.append("<td style='text-align:center;background:silver'>").append("Value");
 		
 		while (fdi.hasNext()) {
 			Entry<String, String> fd = fdi.next();
 				sb.append("</td>").append("</tr>").append("<tr>").append("<td valign='top'>")
 					.append(fd.getKey()).append("</td>").append("<td>");
 			
 			sb.append(fd.getValue());
 			}
 		sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");
		
		return sb.toString();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String createHtmlRepresentationforRentReceiptbyUserID(long userId){
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		List<TransactionDetails> lstTransactionDetails = transactionDetailService.findTransactionbyUseridandRentReceivedEqualY(userId);
		sb.append("<html>").append("<head>").append("</head>").append("<body>").append("<p style='display : none;'>&nbsp;</p>").append("<table id='formTableVal' border='1'>");
	    sb.append("<tr><td colspan=7 style='text-align:center;background:silver'>").append("Rent Receipt Details").append("</td></tr>");
	    sb.append("<tr>").append("<td style='text-align:center;background:silver'>").append("S.No")
    	.append("<td style='text-align:center;background:silver'>").append("Tenancy")
    	.append("</td>").append("<td style='text-align:center;background:silver'>").append("Property")
    	.append("</td>").append("<td style='text-align:center;background:silver'>").append("Transaction Date")
 		.append("</td>").append("<td style='text-align:center;background:silver'>").append("Transaction Mode")
 		.append("</td>").append("<td style='text-align:center;background:silver'>").append("Payables")
 		.append("</td>").append("<td style='text-align:center;background:silver'>").append("Received");
	    long totalPayable = 0, totalPaid = 0, balanceTobePaid=0;
	    if(lstTransactionDetails.size()>0)
	    {
	    	
	    	Iterator<TransactionDetails> fdi = lstTransactionDetails.iterator();
	    	int i=1;
			while (fdi.hasNext()) {
				TransactionDetails td = fdi.next();
				sb.append("<tr>")
				.append("<td>").append(i++).append("</td>")
				.append("<td>").append(td.getTenancyId()).append("</td>")
				.append("<td>").append(td.getPropertyId()).append("</td>")
				.append("<td>").append(sdf.format(td.getTransactiondate())).append("</td>")
				.append("<td>").append(td.getTransactionMode()).append("</td>");
				if(td.getTransactiontype().equalsIgnoreCase("d"))
				{
					sb.append("<td>").append(td.getAmount()).append("</td>");
					sb.append("<td>").append("").append("</td></tr>");
					totalPayable = totalPayable + Long.parseLong(td.getAmount()) ;
				}
				if(td.getTransactiontype().equalsIgnoreCase("r"))
				{
					sb.append("<td>").append("").append("</td>");
					sb.append("<td>").append(td.getAmount()).append("</td></tr>");
					
					totalPaid = totalPaid + Long.parseLong(td.getAmount()) ;
				}
			}
			balanceTobePaid = totalPayable - totalPaid;
			sb.append("<tr><td colspan=6 style='text-align:right;background:silver'>").append("Balance").append("</td>");
			sb.append("<td>").append(balanceTobePaid).append("</td></tr>");
	    }
	    else
	    {
	    	sb.append("<tr><td colspan=7 style='text-align:center'>");
	    	sb.append("No data available for the user");
	    	
	    }
	    sb.append("</td>").append("</tr>").append("</table>").append("</body>").append("</html>");
		return sb.toString();	
	}
}
