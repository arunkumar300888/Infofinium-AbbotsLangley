package uk.co.jmr.sdp.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupCombo;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.SecurityGroupComboService;
import uk.co.jmr.sdp.service.SecurityGroupService;
import uk.co.jmr.sdp.web.util.Util;

@Controller
@RequestMapping(value = "/secGroup")
public class SecurityGroupController {

	@Autowired
	private SecurityGroupService secGroupService;

	@Autowired
	private DtAttributeService dtAttributeService;

	@Autowired
	private DoctypeService doctypeService;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;

	@Autowired
	private SecurityGroupComboService secGroupComboService;

	@RequestMapping(value = "/goShowSecGroups", method = RequestMethod.GET)
	public String showAllSecurityGroups(Model model, HttpSession session) {

		// List<SecurityGroup>
		// secGroupLists=secGroupService.findAllSecurityGroups();
		List<SecurityGroup> secGroupLists = getSecurityGroupsList();
		
		
		model.addAttribute("secGroupLists", Util.securityGroupWithOutOpen(secGroupLists));
		model.addAttribute("title", "Security Group Lists");
		return "showSecurityGroupsLists";
	}

	@RequestMapping(value = "/goShowSecGroupAssignmentList", method = RequestMethod.GET)
	public String showAllSecurityGroupAssignmentList(@RequestParam("id") long secGroupId, Model model, HttpSession session) {

		boolean canShowAddNew = true;
		SecurityGroup secGroup = secGroupService.findSecurityGroupById(secGroupId);
		List<SecurityGroupCombo> secGroupAssignmentList = secGroupComboService.findSecurityGroupCombo(secGroup);
		model.addAttribute("secGroupAssignmentLists", secGroupAssignmentList);
		model.addAttribute("secGroupId", secGroupId);
		String attr1Title = dtAttributeService.findAttributeByOrder(1).getName();
		String attr2Title = dtAttributeService.findAttributeByOrder(2).getName();
		String attr3Title = dtAttributeService.findAttributeByOrder(3).getName();
		/*String attr4Title = dtAttributeService.findAttributeByOrder(4).getName();*/
		setAttributeInSession(attr1Title, attr2Title, attr3Title,  session);
		setTitle(model, session);
		model.addAttribute("canShowAddNew", canShowAddNew);
		// model.addAttribute("attr1Title",session.getAttribute("attr1Title"));
		// model.addAttribute("attr2Title",session.getAttribute("attr2Title"));
		// model.addAttribute("attr3Title",session.getAttribute("attr3Title"));
		// model.addAttribute("attr4Title",session.getAttribute("attr4Title"));
		// model.addAttribute("title", "Security Group Assignment List");
		// System.out.println(secGroupAssignmentList.toString());
		return "showSecGroupAssignmentList";
	}

	private void setAttributeInSession(String attrValue1, String attrValue2, String attrValue3, 
		HttpSession session) {

		session.setAttribute("attr1Title", attrValue1);
		session.setAttribute("attr2Title", attrValue2);
		session.setAttribute("attr3Title", attrValue3);
		/*session.setAttribute("attr4Title", attrValue4);*/
	}

	@RequestMapping(value = "/goDeleteSecGroup", method = RequestMethod.GET)
	public String goDeleteSecurityGroup(@RequestParam("id") long secGroupId, Model model, HttpSession session) {

		SecurityGroup secGroup = secGroupService.findSecurityGroupById(secGroupId);
		secGroup.setIsActive('N');
		secGroupService.saveSecurityGroup(secGroup);
		model.addAttribute("secGroupLists", getSecurityGroupsList());
		model.addAttribute("title", "Security Group Lists");
		return "showSecurityGroupsLists";
	}

	@RequestMapping(value = "/goUnDeleteSecGroup", method = RequestMethod.GET)
	public String goUnDeleteSecurityGroup(@RequestParam("id") long secGroupId, Model model, HttpSession session) {

		SecurityGroup secGroup = secGroupService.findSecurityGroupById(secGroupId);
		secGroup.setIsActive('Y');
		secGroupService.saveSecurityGroup(secGroup);
		model.addAttribute("secGroupLists", getSecurityGroupsList());
		model.addAttribute("title", "Security Group Lists");
		return "showSecurityGroupsLists";
	}

	@RequestMapping(value = "/goDeleteSecGroupAssignment", method = RequestMethod.GET)
	public String goDeleteSecurityGroupAssignment(@RequestParam("id") long secAssignmentId,
		@RequestParam("secGroupId") long secGroupId, Model model, HttpSession session) {

		// System.out.println("In Deleted");
		boolean canShowAddNew = true;
		SecurityGroupCombo secGroupCombo = secGroupComboService.findSecurityGroupComboById(secAssignmentId);
		secGroupComboService.deleteSgCombo(secGroupCombo);
		SecurityGroup secGroup = secGroupService.findSecurityGroupById(secGroupId);
		List<SecurityGroupCombo> secGroupAssignmentList = secGroupComboService.findSecurityGroupCombo(secGroup);
		model.addAttribute("secGroupAssignmentLists", secGroupAssignmentList);
		model.addAttribute("secGroupId", secGroupId);
		setTitle(model, session);
		model.addAttribute("canShowAddNew", canShowAddNew);

		// model.addAttribute("attr1Title",session.getAttribute("attr1Title"));
		// model.addAttribute("attr2Title",session.getAttribute("attr2Title"));
		// model.addAttribute("attr3Title",session.getAttribute("attr3Title"));
		// model.addAttribute("attr4Title",session.getAttribute("attr4Title"));
		// model.addAttribute("title", "Security Group Assignment List");
		return "showSecGroupAssignmentList";
	}

	private void setTitle(Model model, HttpSession session) {

		model.addAttribute("attr1Title", session.getAttribute("attr1Title"));
		model.addAttribute("attr2Title", session.getAttribute("attr2Title"));
		model.addAttribute("attr3Title", session.getAttribute("attr3Title"));
		model.addAttribute("attr4Title", session.getAttribute("attr4Title"));
		model.addAttribute("title", "Security Group Assignment List");
	}

	private List<SecurityGroup> getSecurityGroupsList() {

		// List<SecurityGroup>
		// secGroupLists=secGroupService.findAllSecurityGroups();
		List<SecurityGroup> secGroupLists = secGroupService.findAllSecurityGroupsWithInActive();
		return secGroupLists;
	}

	@RequestMapping(value = "/goCreateNewSecGroupAssignment", method = RequestMethod.GET)
	public String gocreateNewSecurityGroupAssignment(@RequestParam("id") long secGroupId, Model model, HttpSession session) {

		SecurityGroup secGroubObj = secGroupService.findSecurityGroupById(secGroupId);
		// List<Attribute> attributeList = dtAttributeService.findAllDtAttrs();
		// Set<AttributeValue> attrivalueSetProject =
		// Util.getAttributeBasedOnId(
		// attributeList, 1);
		// Set<AttributeValue> attrivalueSetCategory =
		// Util.getAttributeBasedOnId(
		// attributeList, 2);
		// Set<AttributeValue> attrivalueSetDiscipline =
		// Util.getAttributeBasedOnId(
		// attributeList, 3);
		// Set<AttributeValue> attrivalueSetSite = Util.getAttributeBasedOnId(
		// attributeList, 4);
		// Set<AttributeValue>
		// attriValueRestrictedSetForProject=Util.getActiveAttributeValues(attrivalueSetProject);
		// Set<AttributeValue>
		// attriValueRestrictedSetForCategory=Util.getActiveAttributeValues(attrivalueSetCategory);
		// Set<AttributeValue>
		// attriValueRestrictedSetForDiscipline=Util.getActiveAttributeValues(attrivalueSetDiscipline);
		// Set<AttributeValue>
		// attriValueRestrictedSetForSite=Util.getActiveAttributeValues(attrivalueSetSite);
		// List<Doctype> docTypeLists = doctypeService.findAllDoctype();
		// Collections.sort(docTypeLists);
		// model.addAttribute("projectList", attriValueRestrictedSetForProject);
		// model.addAttribute("categoryList",attriValueRestrictedSetForCategory);
		// model.addAttribute("disciplineList",
		// attriValueRestrictedSetForDiscipline);
		// model.addAttribute("siteList", attriValueRestrictedSetForSite);
		// model.addAttribute("doctypesList", docTypeLists);
		setTitle(model, session);
		showMetadata(model);
		model.addAttribute("secGroupName", secGroubObj.getName());
		model.addAttribute("secGroupId", secGroupId);
		model.addAttribute("title", "Security Group Assignment");
		return "createSecurityGroupAssignment";
	}

	@RequestMapping(value = "/goCreateNewSecGroup", method = RequestMethod.GET)
	public String gocreateNew(@RequestParam("id") long secGroupId, Model model, HttpSession session) {

		// List<Attribute> attributeList = dtAttributeService.findAllDtAttrs();
		// Set<AttributeValue> attrivalueSetProject =
		// Util.getAttributeBasedOnId(
		// attributeList, 1);
		// Set<AttributeValue> attrivalueSetCategory =
		// Util.getAttributeBasedOnId(
		// attributeList, 2);
		// Set<AttributeValue> attrivalueSetDiscipline =
		// Util.getAttributeBasedOnId(
		// attributeList, 3);
		// Set<AttributeValue> attrivalueSetSite = Util.getAttributeBasedOnId(
		// attributeList, 4);
		// Set<AttributeValue>
		// attriValueRestrictedSetForProject=Util.getActiveAttributeValues(attrivalueSetProject);
		// Set<AttributeValue>
		// attriValueRestrictedSetForCategory=Util.getActiveAttributeValues(attrivalueSetCategory);
		// Set<AttributeValue>
		// attriValueRestrictedSetForDiscipline=Util.getActiveAttributeValues(attrivalueSetDiscipline);
		// Set<AttributeValue>
		// attriValueRestrictedSetForSite=Util.getActiveAttributeValues(attrivalueSetSite);
		// List<Doctype> docTypeLists = doctypeService.findAllDoctype();
		// Collections.sort(docTypeLists);
		// model.addAttribute("projectList", attriValueRestrictedSetForProject);
		// model.addAttribute("categoryList",attriValueRestrictedSetForCategory);
		// model.addAttribute("disciplineList",
		// attriValueRestrictedSetForDiscipline);
		// model.addAttribute("siteList", attriValueRestrictedSetForSite);
		// model.addAttribute("doctypesList", docTypeLists);

		model.addAttribute("title", "Security Group");
		model.addAttribute("secGroupId", secGroupId);
		if (secGroupId != -1) {
			SecurityGroup secGroup = secGroupService.findSecurityGroupById(secGroupId);
			model.addAttribute("secGroupName", secGroup.getName());
		}
		return "createNewSecGroup";
	}

	@RequestMapping(value = "/createSecGroupAssignment", method = RequestMethod.POST)
	public String goCreateSecurityGroupAssignment(@RequestParam("projectSec") long projectId,
		@RequestParam("categorySec") long categoryId, @RequestParam("disciplineSec") long disciplineId,
		 @RequestParam("doctypeSec") long doctypeId,
		@RequestParam("secGroupId") long secGroupId, @RequestParam("secGroupName") String secGroupName, Model model,
		HttpSession session) {

		boolean canShowAddNew = false;
		boolean canShowTitle1 = true;
		secGroupName = WordUtils.capitalizeFully(secGroupName).trim();
		SecurityGroup secGroup = secGroupService.findSecurityGroupById(secGroupId);
		Doctype doctypeObj = doctypeService.findDoctypeById(doctypeId);
		AttributeValue attributeValue1 = dtAttributeValueService.findDtAttrValueById(projectId);
		AttributeValue attributeValue2 = dtAttributeValueService.findDtAttrValueById(categoryId);
		AttributeValue attributeValue3 = dtAttributeValueService.findDtAttrValueById(disciplineId);
		//AttributeValue attributeValue4 = dtAttributeValueService.findDtAttrValueById(siteId);
		//Added by Karthik.V on 01.03.14 for security group combo duplicate removal 
		List<AttributeValue> attrVals=new ArrayList<>();
		attrVals.add(attributeValue1);
		attrVals.add(attributeValue2);
		attrVals.add(attributeValue3);
		//attrVals.add(attributeValue4);
		SecurityGroupCombo secGroupComboExists=secGroupComboService.findSecurityGroupComboByAttrAndDoctype(attrVals, secGroup, doctypeObj);
		if(secGroupComboExists==null){
			SecurityGroupCombo secGroupCombo = new SecurityGroupCombo(secGroup, doctypeObj, attributeValue1, attributeValue2,
					attributeValue3, 'Y');
				secGroupComboService.saveSgCombo(secGroupCombo);
		}
		model.addAttribute("title", "Security Group Assignment");
		model.addAttribute("title1", "Security Group Assignment List");
		model.addAttribute("result", "Security group created successfully");
		model.addAttribute("secGroupName", secGroupName);
		model.addAttribute("secGroupId", secGroupId);
		model.addAttribute("canShowAddNew", canShowAddNew);
		model.addAttribute("canShowTitle1", canShowTitle1);
		// showMetadata(model);
		List<SecurityGroupCombo> secGroupAssignmentList = secGroupComboService.findSecurityGroupCombo(secGroup);
		model.addAttribute("secGroupAssignmentLists", secGroupAssignmentList);
		return "showSecGroupAssignmentList";
		// return "createSecurityGroupAssignment";
	}

	private void showMetadata(Model model) {

		List<Attribute> attributeList = dtAttributeService.findAllDtAttrs();
		Set<AttributeValue> attrivalueSetDepartment = Util.getAttributeBasedOnId(attributeList, 1);
		Set<AttributeValue> attrivalueSetArea = Util.getAttributeBasedOnId(attributeList, 2);
		Set<AttributeValue> attrivalueSetCompany = Util.getAttributeBasedOnId(attributeList, 5);
		/*Set<AttributeValue> attrivalueSetDiscipline = Util.getAttributeBasedOnId(attributeList, 3);
		Set<AttributeValue> attrivalueSetSite = Util.getAttributeBasedOnId(attributeList, 4);*/
		Set<AttributeValue> attriValueRestrictedSetForCompany = Util.getActiveAttributeValues(attrivalueSetCompany);
		Set<AttributeValue> attriValueRestrictedSetForDepartment = Util.getActiveAttributeValues(attrivalueSetDepartment);
		Set<AttributeValue> attriValueRestrictedSetForArea = Util.getActiveAttributeValues(attrivalueSetArea);
		/*Set<AttributeValue> attriValueRestrictedSetForSite = Util.getActiveAttributeValues(attrivalueSetSite);*/
		List<Doctype> docTypeLists = doctypeService.findAllDoctype();
		Collections.sort(docTypeLists);
		model.addAttribute("companyList", attriValueRestrictedSetForCompany);
		model.addAttribute("departmentList", attriValueRestrictedSetForDepartment);
		model.addAttribute("areaList", attriValueRestrictedSetForArea);
		/*model.addAttribute("siteList", attriValueRestrictedSetForSite);*/
		model.addAttribute("doctypesList", docTypeLists);
	}

	@RequestMapping(value = "/createSecGroup", method = RequestMethod.POST)
	public String goCreateSecGroup(@RequestParam("secGroupName") String secGroupName,
		@RequestParam("secGroupId") long secGroupId, Model model, HttpSession session) {

		secGroupName = WordUtils.capitalizeFully(secGroupName).trim();
		SecurityGroup secGroupExists = secGroupService.findSecurityGroupByName(secGroupName);
		if (secGroupExists != null) {
			model.addAttribute("title", "Security Group");
			model.addAttribute("result", "Security group already exist");
			model.addAttribute("secGroupLists", getSecurityGroupsList());
			model.addAttribute("secGroupId",secGroupId);
			model.addAttribute("secGroupName", secGroupName);
			//return "showSecurityGroupsLists";
			// model.addAttribute("result","Security group already exists");
			 return "createNewSecGroup";
		}
		if (secGroupId != -1) {
			SecurityGroup updateSecGroup = secGroupService.findSecurityGroupById(secGroupId);
			updateSecGroup.setName(secGroupName);
			secGroupService.saveSecurityGroup(updateSecGroup);
			model.addAttribute("title", "Security Group");
			model.addAttribute("secGroupLists", getSecurityGroupsList());
			return "showSecurityGroupsLists";
			// model.addAttribute("result","Security group updated successfully");
			// System.out.println("Updation");
			// return "createNewSecGroup";
		}

		SecurityGroup secGroup = new SecurityGroup(secGroupName, 'Y');
		secGroupService.saveSecurityGroup(secGroup);
		model.addAttribute("title", "Security Group");
		model.addAttribute("result", "Security group created successfully");
		model.addAttribute("secGroupId", secGroupId);
		return "createNewSecGroup";
	}
}
