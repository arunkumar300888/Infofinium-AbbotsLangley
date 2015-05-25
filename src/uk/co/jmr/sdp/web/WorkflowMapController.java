package uk.co.jmr.sdp.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupCombo;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.ModelCombo;
import uk.co.jmr.sdp.domain.dt.ModelComboValue;
import uk.co.jmr.sdp.service.DoctypeService;
import uk.co.jmr.sdp.service.DtAttributeService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.ModelComboService;
import uk.co.jmr.sdp.service.ModelComboValueService;
import uk.co.jmr.sdp.web.util.Util;

import com.ardhika.wfar.ModelSummary;
import com.ardhika.wfar.service.ModelService;

@Controller
@RequestMapping(value = "/workflowMap")
public class WorkflowMapController {
	@Autowired
	private ModelComboService modelComboService;
	@Autowired
	private ModelComboValueService modelComboValueService;
	@Autowired
	private DoctypeService docTypeService;
	@Autowired
	private ModelService modelService;
	@Autowired
	private DtAttributeService dtAttributeService;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;
	@Autowired
	private DoctypeService doctypeService;

	@RequestMapping(value = "/goShowModels", method = RequestMethod.GET)
	public String showAllModels(Model model, HttpSession session) {

		// List<ModelCombo>
		// modelComboLists=modelComboService.listAllModelCombos();
		List<ModelCombo> modelComboLists = modelComboService.listAllModelCombosWithInActive();
		model.addAttribute("modelComboLists", modelComboLists);
		model.addAttribute("title", "Workflow Model Lists");
		return "showWorkflowModelsMap";
	}

	private List<Doctype> getDocTypeLists() {

		List<Doctype> docTypeLists = docTypeService.findAllDoctype();
		// List<Doctype> docTypeLists =
		// docTypeService.findAllDoctypeWithInActive();
		Collections.sort(docTypeLists);
		return docTypeLists;
	}

	private void showModelsAndDoctypes(Model model) {

		List<Doctype> doctypesList = getDocTypeLists();
		model.addAttribute("doctypesList", doctypesList);
		//List<ModelSummary> modelLists = modelService.listModels();
		List<ModelSummary> modelLists=modelService.listModelsByType('D');
		model.addAttribute("workflowModels", modelLists);
	}

	@RequestMapping(value = "/goCreateNewWorkflowModelMap", method = RequestMethod.GET)
	public String gocreateNew(@RequestParam("id") long modelComboId, Model model, HttpSession session) {

		model.addAttribute("title", "Workflow Model Lists");
		model.addAttribute("modelComboId", modelComboId);
		showModelsAndDoctypes(model);
		// List<Doctype> doctypesList= getDocTypeLists();
		// model.addAttribute("doctypesList", doctypesList);
		// List<ModelSummary> modelLists=modelService.listModels();
		// model.addAttribute("workflowModels", modelLists);

		// if(modelComboId!=-1){
		// SecurityGroup
		// secGroup=secGroupService.findSecurityGroupById(secGroupId);
		// ModelCombo modelCombo=modelComboService.
		// model.addAttribute("secGroupName",secGroup.getName());
		// }
		return "createNewWorkflowModelMap";
	}

	@RequestMapping(value = "/goCreateNewModelMapAssignment", method = RequestMethod.GET)
	public String gocreateNewWorlflowModelAssignment(@RequestParam("id") long modelComboId, Model model, HttpSession session) {

		ModelCombo modelCombo = modelComboService.findModelComboById(modelComboId);
		model.addAttribute("doctype", modelCombo.getDoctype().getDoctypeName());
		model.addAttribute("modelName", modelCombo.getModelName());
		model.addAttribute("modelComboId", modelComboId);
		showMetadata(model);
		model.addAttribute("title", "Workflow Model Assignment");
		return "createModelMapAssignment";
	}

	private void showMetadata(Model model) {

		List<Attribute> attributeList = dtAttributeService.findAllDtAttrs();
		Set<AttributeValue> attrivalueSetCompany = Util.getAttributeBasedOnId(attributeList, 5);
		Set<AttributeValue> attrivalueSetProject = Util.getAttributeBasedOnId(attributeList, 1);
		Set<AttributeValue> attrivalueSetCategory = Util.getAttributeBasedOnId(attributeList, 2);
		/*Set<AttributeValue> attrivalueSetDiscipline = Util.getAttributeBasedOnId(attributeList, 3);
		Set<AttributeValue> attrivalueSetSite = Util.getAttributeBasedOnId(attributeList, 4);*/
		Set<AttributeValue> attriValueRestrictedSetForProject = Util.getActiveAttributeValues(attrivalueSetProject);
		Set<AttributeValue> attriValueRestrictedSetForCategory = Util.getActiveAttributeValues(attrivalueSetCategory);
		/*Set<AttributeValue> attriValueRestrictedSetForDiscipline = Util.getActiveAttributeValues(attrivalueSetDiscipline);
		Set<AttributeValue> attriValueRestrictedSetForSite = Util.getActiveAttributeValues(attrivalueSetSite);*/
		Set<AttributeValue> attriValueRestrictedSetForCompany = Util.getActiveAttributeValues(attrivalueSetCompany);
		model.addAttribute("companyList", attriValueRestrictedSetForCompany);
		model.addAttribute("projectList", attriValueRestrictedSetForProject);
		model.addAttribute("categoryList", attriValueRestrictedSetForCategory);
		/*model.addAttribute("disciplineList", attriValueRestrictedSetForDiscipline);
		model.addAttribute("siteList", attriValueRestrictedSetForSite);*/
	}

	@RequestMapping(value = "/createWorkflowModelMap", method = RequestMethod.POST)
	public String goCreateWorkflowModelMap(@RequestParam("modelComboId") long modelComboId,
		@RequestParam("modelName") String modelName, @RequestParam("description") String description,
		@RequestParam("doctype") long doctypeId, Model model, HttpSession session) {

		// System.out.println("In Map");
		Doctype doctypeObj = docTypeService.findDoctypeById(doctypeId);
		ModelCombo modelComboCheck = modelComboService.findModelComboByDoctypeAndModelName(doctypeObj, modelName);
		if (modelComboCheck != null) {
			model.addAttribute("title", "Workflow Model Mapping");
			model.addAttribute("result", "Model with doctype already exists");
			model.addAttribute("modelComboId", modelComboId);
			showModelsAndDoctypes(model);
			return "createNewWorkflowModelMap";
		}

		ModelCombo modelCombo = new ModelCombo(modelName, description, doctypeObj, 'Y');
		modelComboService.saveModelCombo(modelCombo);
		model.addAttribute("title", "Workflow Model Mapping");
		model.addAttribute("result", "Model Mapped Successfully");
		model.addAttribute("modelComboId", modelComboId);
		showModelsAndDoctypes(model);
		return "createNewWorkflowModelMap";
	}

	private void setTitle(Model model) {

		String attr1Title = dtAttributeService.findAttributeByOrder(1).getName();
		String attr2Title = dtAttributeService.findAttributeByOrder(2).getName();
		String attr3Title = dtAttributeService.findAttributeByOrder(3).getName();
		/*String attr4Title = dtAttributeService.findAttributeByOrder(4).getName();
		String attr5Title = dtAttributeService.findAttributeByOrder(5).getName();*/
		model.addAttribute("attr1Title", attr1Title);
		model.addAttribute("attr2Title", attr2Title);
		model.addAttribute("attr3Title", attr3Title);
		/*model.addAttribute("attr4Title", attr4Title);
		model.addAttribute("attr5Title", attr5Title);*/
		model.addAttribute("title", "Model Assignment List");
	}

	private void setAssignmentMetdata(Model model, long modelComboId) {

		boolean canShowDelete = false;
		ModelCombo modelCombo = modelComboService.findModelComboById(modelComboId);

		Set<ModelComboValue> modelComboValues = modelCombo.getModelComboValues();
		StringBuilder project = new StringBuilder();
		StringBuilder category = new StringBuilder();
		StringBuilder discipline = new StringBuilder();
		StringBuilder site = new StringBuilder();
		StringBuilder company = new StringBuilder();
		Set<String> projectSet = new HashSet<String>();
		Set<String> categorySet = new HashSet<String>();
		Set<String> disciplineSet = new HashSet<String>();
		Set<String> siteSet = new HashSet<String>();
		Set<String> companySet = new HashSet<String>();

		for (Iterator<ModelComboValue> iterator = modelComboValues.iterator(); iterator.hasNext();) {
			ModelComboValue modelComboValue = (ModelComboValue) iterator.next();

			if (modelComboValue.getAttribute().getId() == 1) {
				projectSet.add(modelComboValue.getAttributeValue().getValue());
				// project.append(modelComboValue.getAttributeValue().getValue()).append("|");

			}
			else if (modelComboValue.getAttribute().getId() == 2) {
				categorySet.add(modelComboValue.getAttributeValue().getValue());
				// category.append(modelComboValue.getAttributeValue().getValue()).append("|");

			}
			/*else if (modelComboValue.getAttribute().getId() == 3) {
				disciplineSet.add(modelComboValue.getAttributeValue().getValue());
				// discipline.append(modelComboValue.getAttributeValue().getValue()).append("|");

			}
			else if (modelComboValue.getAttribute().getId() == 4) {
				siteSet.add(modelComboValue.getAttributeValue().getValue());
				// site.append(modelComboValue.getAttributeValue().getValue()).append("|");
			}*/
			
			else if (modelComboValue.getAttribute().getId() == 5) {
				companySet.add(modelComboValue.getAttributeValue().getValue());
				// site.append(modelComboValue.getAttributeValue().getValue()).append("|");
			}

		}
		
		for (String companyName : companySet) {
			company.append(companyName);
			company.append("|");
		}
		for (String projectName : projectSet) {
			project.append(projectName);
			project.append("|");
		}
		for (String categoryName : categorySet) {
			category.append(categoryName);
			category.append("|");
		}
		/*for (String disciplineName : disciplineSet) {
			discipline.append(disciplineName);
			discipline.append("|");
		}
		for (String siteName : siteSet) {
			site.append(siteName);
			site.append("|");
		}*/
		

		if (modelComboValues.size() >= 1) {
			canShowDelete = true;
			model.addAttribute("company", company.toString().substring(0, company.length() - 1));
			model.addAttribute("project", project.toString().substring(0, project.length() - 1));
			model.addAttribute("category", category.toString().substring(0, category.length() - 1));
			/*model.addAttribute("discipline", discipline.toString().substring(0, discipline.length() - 1));
			model.addAttribute("site", site.toString().substring(0, site.length() - 1));*/
			model.addAttribute("doctype", modelCombo.getDoctype().getDoctypeName());
			model.addAttribute("modelName", modelCombo.getModelName());
			// model.addAttribute("canShowDelete", canShowDelete);
		}

		// model.addAttribute("modelComboValueSet",modelComboValues);
		model.addAttribute("modelComboId", modelComboId);
		model.addAttribute("canShowDelete", canShowDelete);
		setTitle(model);

	}

	@RequestMapping(value = "/goShowModelMapAssignmentList", method = RequestMethod.GET)
	public String showAllModelMapAssignmentList(@RequestParam("id") long modelComboId, Model model, HttpSession session) {

		boolean canShowAddNew = true;
		model.addAttribute("canShowAddNew", canShowAddNew);
		setAssignmentMetdata(model, modelComboId);

		// boolean canShowAddNew=true;
		// boolean canShowDelete=false;
		// ModelCombo
		// modelCombo=modelComboService.findModelComboById(modelComboId);
		//
		// Set<ModelComboValue>
		// modelComboValues=modelCombo.getModelComboValues();
		// StringBuilder project=new StringBuilder();
		// StringBuilder category=new StringBuilder();
		// StringBuilder discipline=new StringBuilder();
		// StringBuilder site=new StringBuilder();
		// for (Iterator<ModelComboValue> iterator =
		// modelComboValues.iterator(); iterator.hasNext();) {
		// ModelComboValue modelComboValue = (ModelComboValue) iterator.next();
		//
		// if (modelComboValue.getAttribute().getId() == 1) {
		//
		// project.append(modelComboValue.getAttributeValue().getValue()).append("|");
		//
		// } else if (modelComboValue.getAttribute().getId() == 2) {
		//
		// category.append(modelComboValue.getAttributeValue().getValue()).append("|");
		//
		// } else if (modelComboValue.getAttribute().getId() == 3) {
		// discipline.append(modelComboValue.getAttributeValue().getValue()).append("|");
		//
		// } else if (modelComboValue.getAttribute().getId() == 4) {
		//
		// site.append(modelComboValue.getAttributeValue().getValue()).append("|");
		// }
		//
		// }
		//
		// if(modelComboValues.size()>=1){
		// canShowDelete=true;
		// model.addAttribute("project", project.toString().substring(0,
		// project.length()-1));
		// model.addAttribute("category", category.toString().substring(0,
		// category.length()-1));
		// model.addAttribute("discipline", discipline.toString().substring(0,
		// discipline.length()-1));
		// model.addAttribute("site", site.toString().substring(0,
		// site.length()-1));
		// model.addAttribute("doctype",
		// modelCombo.getDoctype().getDoctypeName());
		// model.addAttribute("modelName",modelCombo.getModelName());
		// }
		// model.addAttribute("modelComboId", modelComboId);
		// model.addAttribute("canShowDelete", canShowDelete);
		// setTitle(model);
		// model.addAttribute("canShowAddNew",canShowAddNew);
		// return "showModelMapAssignmentList";
		return "showModelMapAssignmentList";
	}

	@RequestMapping(value = "/goDeleteModelMap", method = RequestMethod.GET)
	public String goDeleteWorkflowModelMap(@RequestParam("id") long modelComboId, Model model, HttpSession session) {

		ModelCombo modelCombo = modelComboService.findModelComboById(modelComboId);
		List<ModelComboValue> modelComboValues = modelComboValueService.findModelComboValueByModelCombo(modelCombo);
		if (!modelComboValues.isEmpty()) {
			for (ModelComboValue modelComboValue : modelComboValues) {
				modelComboValueService.deleteModelComboValue(modelComboValue);
			}
		}
		ModelCombo modelComboUpdate = modelComboService.findModelComboById(modelComboId);
		modelComboUpdate.setIsActive('N');
		modelComboService.saveModelCombo(modelComboUpdate);
		List<ModelCombo> modelComboLists = modelComboService.listAllModelCombosWithInActive();
		model.addAttribute("modelComboLists", modelComboLists);
		model.addAttribute("title", "Workflow Model Lists");
		return "showWorkflowModelsMap";
	}

	@RequestMapping(value = "/goUnDeleteModelMap", method = RequestMethod.GET)
	public String goUnDeleteWorkflowModelMap(@RequestParam("id") long modelComboId, Model model, HttpSession session) {

		ModelCombo modelComboUpdate = modelComboService.findModelComboById(modelComboId);
		modelComboUpdate.setIsActive('Y');
		modelComboService.saveModelCombo(modelComboUpdate);
		List<ModelCombo> modelComboLists = modelComboService.listAllModelCombosWithInActive();
		model.addAttribute("modelComboLists", modelComboLists);
		model.addAttribute("title", "Workflow Model Lists");
		return "showWorkflowModelsMap";
	}

	@RequestMapping(value = "/goDeleteModelMapAssignment", method = RequestMethod.GET)
	public String goDeleteWorkflowModelMapAssignment(@RequestParam("id") long modelComboId, Model model, HttpSession session) {

		// System.out.println("In Deleted");
		// boolean canShowAddNew=true;
		ModelCombo modelCombo = modelComboService.findModelComboById(modelComboId);
		List<ModelComboValue> modelComboValues = modelComboValueService.findModelComboValueByModelCombo(modelCombo);
		for (ModelComboValue modelComboValue : modelComboValues) {
			modelComboValueService.deleteModelComboValue(modelComboValue);
		}

		ModelCombo modelComboUpdate = modelComboService.findModelComboById(modelComboId);
		modelComboUpdate.setIsActive('N');
		modelComboService.saveModelCombo(modelComboUpdate);

		// List<ModelCombo>
		// modelComboLists=modelComboService.listAllModelCombos();
		List<ModelCombo> modelComboLists = modelComboService.listAllModelCombosWithInActive();
		model.addAttribute("modelComboLists", modelComboLists);
		model.addAttribute("title", "Workflow Model Lists");
		// setTitle(model);
		// model.addAttribute("canShowAddNew",canShowAddNew);
		return "showWorkflowModelsMap";
	}

	@RequestMapping(value = "/createModelMapAssignment", method = RequestMethod.POST)
	public String goCreateModelMapAssignment(@RequestParam("companyModel")long companyId,@RequestParam("projectModel") long projectId,
		@RequestParam("categoryModel") long categoryId,@RequestParam("modelComboId") long modelComboId,
		@RequestParam("doctypeName") String doctypeName, @RequestParam("modelName") String modelName, Model model,
		HttpSession session) {

		boolean canShowAddNew = false;
		ModelCombo modelCombo = modelComboService.findModelComboById(modelComboId);
		Attribute attribute1 = dtAttributeService.findAttributeByOrder(1);
		Attribute attribute2 = dtAttributeService.findAttributeByOrder(2);
		Attribute attribute3 = dtAttributeService.findAttributeByOrder(3);
		/*Attribute attribute4 = dtAttributeService.findAttributeByOrder(4);
		Attribute attribute5 = dtAttributeService.findAttributeByOrder(5);*/

		AttributeValue attributeValue1 = dtAttributeValueService.findDtAttrValueById(companyId);
		AttributeValue attributeValue2 = dtAttributeValueService.findDtAttrValueById(projectId);
		AttributeValue attributeValue3 = dtAttributeValueService.findDtAttrValueById(categoryId);
		/*AttributeValue attributeValue4 = dtAttributeValueService.findDtAttrValueById(disciplineId);
		AttributeValue attributeValue5 = dtAttributeValueService.findDtAttrValueById(siteId);*/

		ModelComboValue modelComboValue1 = new ModelComboValue(modelCombo, attribute1, attributeValue1);
		ModelComboValue modelComboValue2 = new ModelComboValue(modelCombo, attribute2, attributeValue2);
		ModelComboValue modelComboValue3 = new ModelComboValue(modelCombo, attribute3, attributeValue3);
		/*ModelComboValue modelComboValue4 = new ModelComboValue(modelCombo, attribute4, attributeValue4);
		ModelComboValue modelComboValue5 = new ModelComboValue(modelCombo, attribute5, attributeValue5);*/

		Set<ModelComboValue> modelComboValueSet = new HashSet<ModelComboValue>();
		modelComboValueSet.add(modelComboValue1);
		modelComboValueSet.add(modelComboValue2);
		modelComboValueSet.add(modelComboValue3);
		/*modelComboValueSet.add(modelComboValue4);
		modelComboValueSet.add(modelComboValue5);*/
		for (ModelComboValue modelComboValue : modelComboValueSet) {
			this.modelComboValueService.save(modelComboValue);
		}
		model.addAttribute("result", "Assignment Created Successfully");
		setAssignmentMetdata(model, modelComboId);
		model.addAttribute("canShowAddNew", canShowAddNew);
		return "showModelMapAssignmentList";
	}
}
