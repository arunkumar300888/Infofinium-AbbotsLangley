package uk.co.jmr.sdp.web;


import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uk.co.jmr.sdp.domain.FormCompanyGroup;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupForm;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.FormCompanyGroupService;
import uk.co.jmr.sdp.web.util.Util;
import uk.co.jmr.webforms.db.pojo.FormDefs;
import uk.co.jmr.webforms.db.service.FormDefsService;


@Controller
@RequestMapping(value = "/company")
public class CompanyController {
	
	@Autowired
	private DtAttributeService dtAttributeService;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;
	@Autowired
	private FormDefsService formDefsService;
	@Autowired
	private FormCompanyGroupService formCompanyGroupService;
	
	
	@RequestMapping(value="/goShowCompanyMaster",method = RequestMethod.GET)
	public String showCompany(Model model){
		
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);

		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
	//	Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		model.addAttribute("title", "Company Master");
		model.addAttribute("company", attr1Values);
		return "showCompanyMaster";
	}
	
	@RequestMapping(value="/goShowCreateCompany",method = RequestMethod.GET)
	public String showCreateCompany(Model model){
		model.addAttribute("title", "Company Creation");
		return "createCompany";
	}
	
	@RequestMapping(value="/goShowEditCompany",method = RequestMethod.GET)
	public String showEditCompany(@RequestParam("id")long id,Model model){
		model.addAttribute("title", "Edit Company");
		//Company company=companyService.findCompanyById(id);
		AttributeValue attributeValue=dtAttributeValueService.findDtAttrValueById(id);
		if(attributeValue!=null){
		model.addAttribute("companyName", attributeValue.getValue());
		model.addAttribute("companyAbbr", attributeValue.getAbbreviation());
		model.addAttribute("companyId", id);
		}
		return "editCompany";
	}
	
	@RequestMapping(value="/createNewCompany",method = RequestMethod.POST)
	public String createCompany(@RequestParam("companyName")String companyName,@RequestParam("abbreviation")String abbreviation,Model model){
		
			AttributeValue attributeValue=dtAttributeValueService.findDtAttrValueByName(companyName);
			
			if(attributeValue!=null){
				
				model.addAttribute("title", "Company Master");
				model.addAttribute("result", "Company already Exist");
				return "createCompany";
			}
			
			Attribute attribute = dtAttributeService.findAttributeByOrder(1);
			
			AttributeValue at=new AttributeValue(companyName, attribute);
			at.setAbbreviation(abbreviation);
			at.setIsActive('Y');
			dtAttributeValueService.save(at);
			
			Attribute attribute1 = dtAttributeService.findAttributeByOrder(1);
			Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute1.getAttrValues());
			//Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
			model.addAttribute("title", "Company Master");
			model.addAttribute("company", attr1Values);
			return "showCompanyMaster";
		
		
	}
	
	@RequestMapping(value="/editCompany",method = RequestMethod.POST)
	public String editComp(@RequestParam("companyName")String companyName,@RequestParam("abbreviation")String abbreviation,@RequestParam("companyId")long id,Model model){
		
		AttributeValue attributeValue=dtAttributeValueService.findDtAttrValueByNameAndAbbreviation(companyName, abbreviation);
		if(attributeValue!=null){
			model.addAttribute("result", "Company already Exist");
			model.addAttribute("companyName", attributeValue.getValue());
			model.addAttribute("companyAbbr", attributeValue.getAbbreviation());
			model.addAttribute("companyId", id);
			return "editCompany";
		}else{
			AttributeValue attributeValue2=dtAttributeValueService.findDtAttrValueById(id);
			attributeValue2.setValue(companyName);
			attributeValue2.setAbbreviation(abbreviation);
			dtAttributeValueService.save(attributeValue2);
			Attribute attribute = dtAttributeService.findAttributeByOrder(1);

			Attribute attribute3 = dtAttributeService.findAttributeByOrder(1);
			Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute3.getAttrValues());
			
			model.addAttribute("title", "Company Master");
			model.addAttribute("company", attr1Values);
			
			
			return "showCompanyMaster";
		}
			
		
		
			
		
		
	}
	
	@RequestMapping(value="/goDeleteCompany",method = RequestMethod.GET)
	public String deleteComp(@RequestParam("id")long id,Model model){
		
		
		AttributeValue attributeValue=dtAttributeValueService.findDtAttrValueById(id);
		
		if(attributeValue!=null)
			attributeValue.setIsActive('N');
		dtAttributeValueService.save(attributeValue);
			
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);

		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		
		model.addAttribute("title", "Company Master");
		model.addAttribute("company", attr1Values);
		
			return "showCompanyMaster";
		
		
	}
	
	@RequestMapping(value="/goUnDeleteCompany",method = RequestMethod.GET)
	public String unDeleteComp(@RequestParam("id")long id,Model model){
		
		
		AttributeValue attributeValue=dtAttributeValueService.findDtAttrValueById(id);
		
		if(attributeValue!=null)
			attributeValue.setIsActive('Y');
		dtAttributeValueService.save(attributeValue);
			
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);

		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		
		model.addAttribute("title", "Company Master");
		model.addAttribute("company", attr1Values);
		
			return "showCompanyMaster";
		
		
	}
	
	@RequestMapping(value = "/goShowFormTypes", method = RequestMethod.GET)
	public String getFormTypes(Model model, HttpSession session)
		throws Exception {

		List<FormDefs> fds=formDefsService.findAllFormDefs();
		model.addAttribute("title", "Form Types");
		model.addAttribute("formTypeList", fds);
		return "formTypesListForCompanyGrp";
	}
	
	@RequestMapping(value = "/goShowCompanyGroupAssignment", method = RequestMethod.GET)
	public String getCompanyGrpForFormTypes(@RequestParam("id")long id,Model model, HttpSession session)
		throws Exception {

		FormDefs fd=formDefsService.findFormDefsById(id);
		List<FormCompanyGroup> fcg=formCompanyGroupService.findFormCompanyGroupForFormDef(fd);
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);
		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		
		
		model.addAttribute("companyGrpListsForm", fcg);
		model.addAttribute("formTypeId", id);
		model.addAttribute("companyGrpLists", attr1ValuesRestricted);
		model.addAttribute("formName", fd.getDescription());
		model.addAttribute("title", "Company Assignment");
		
		return "assignCompanyGroupForForm";
	}
	
	@RequestMapping(value = "/deleteCompanyGroupFromForm", method = RequestMethod.GET)
	public String deleteCompanyGrpForForm(@RequestParam("compGrpId")long compGrpId,@RequestParam("formTypeId")long formTypeId,Model model, HttpSession session)
		throws Exception {

		AttributeValue attributeValue=dtAttributeValueService.findDtAttrValueById(compGrpId);
		FormDefs fd=formDefsService.findFormDefsById(formTypeId);
		
		FormCompanyGroup formCompanyGroup=formCompanyGroupService.findFormCompanyGroupForFormDefAndAttributeValue(fd, attributeValue);
		formCompanyGroupService.delete(formCompanyGroup);
		
		List<FormCompanyGroup> fcg=formCompanyGroupService.findFormCompanyGroupForFormDef(fd);
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);
		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		
		
		model.addAttribute("companyGrpListsForm", fcg);
		model.addAttribute("formTypeId", formTypeId);
		model.addAttribute("companyGrpLists", attr1ValuesRestricted);
		model.addAttribute("formName", fd.getDescription());
		model.addAttribute("title", "Company Assignment");
		
		return "assignCompanyGroupForForm";
	}

	@RequestMapping(value = "/addCompanyGroupFromForm", method = RequestMethod.GET)
	public String addCompGrpForForm(@RequestParam("compGrpId")long compGrpId,@RequestParam("formTypeId")long formTypeId,Model model, HttpSession session)
		throws Exception {

		AttributeValue attributeValue=dtAttributeValueService.findDtAttrValueById(compGrpId);
		FormDefs fd=formDefsService.findFormDefsById(formTypeId);
		
		FormCompanyGroup formCompanyGroup=new FormCompanyGroup(fd, attributeValue);
		formCompanyGroupService.save(formCompanyGroup);
		
		List<FormCompanyGroup> fcg=formCompanyGroupService.findFormCompanyGroupForFormDef(fd);
		Attribute attribute = dtAttributeService.findAttributeByOrder(1);
		Set<AttributeValue> attr1Values = new TreeSet<AttributeValue>(attribute.getAttrValues());
		Set<AttributeValue> attr1ValuesRestricted = Util.getActiveAttributeValues(attr1Values);
		
		
		model.addAttribute("companyGrpListsForm", fcg);
		model.addAttribute("formTypeId", formTypeId);
		model.addAttribute("companyGrpLists", attr1ValuesRestricted);
		model.addAttribute("formName", fd.getDescription());
		model.addAttribute("title", "Company Assignment");
		
		return "assignCompanyGroupForForm";
	}
	

}
