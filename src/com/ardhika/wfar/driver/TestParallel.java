package com.ardhika.wfar.driver;

import org.alfresco.webservice.util.ISO9075;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ardhika.wfar.WfAction;
import com.ardhika.wfar.WfAttribute;
import com.ardhika.wfar.WfAttributeType;
import com.ardhika.wfar.WfCancel;
import com.ardhika.wfar.WfFork;
import com.ardhika.wfar.WfJoin;
import com.ardhika.wfar.WfModel;
import com.ardhika.wfar.WfStop;
import com.ardhika.wfar.WfTask;
import com.ardhika.wfar.service.CaseService;
import com.ardhika.wfar.service.ModelService;

public class TestParallel {

	public static void main(String[] args) throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		ModelService modelservice = context.getBean("modelService", ModelService.class);
		CaseService caseservice = context.getBean("caseService", CaseService.class);
		WfModel model = setupModel18(modelservice);
		System.out.println("Model Saved-> " + model.getName());
	}

	// For Parallel Workflow Config on 09.02.13
	// Tray Label Implemented Full Workflow Config for 23.01.13 release //live
	// working model
	private static WfModel setupModel3(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Full Workflow Test", "Owner", 'Y');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/app:company_home/app:user_homes/cm:DSJV/cm:" + ISO9075.encode("11.0 Construction Process"), false));
		modelservice.saveModel(model);
		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));

		model.addNode(new WfTask("To Check", "R:Checker", "To Check", 'Y'));

		model.addNode(new WfTask("To Review Health & Safety", "RG:Reviewer.Health & Safety", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Environment", "RG:Reviewer.Environment", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Quality", "RG:Reviewer.Quality", "To Review", 'Y'));

		model.addNode(new WfTask("To Publish", "R:Publisher/DMT", "To Publish", 'Y'));
		model.addNode(new WfTask("From Checker To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Rejected To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Reviewer To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Approver To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("To Approve", "R:DSJV Approver", "To Approve", 'Y'));
		model.addNode(new WfTask("To Finalise", "U:{CREATOR}", "To Finalise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Check"), 'N', "Prepared"));
		model.getNode("To Check").addAction(new WfAction("Checked", model.getNode("From Checker To Revise"), 'N', "Checked"));
		model.getNode("To Check").addAction(new WfAction("Rejected", model.getNode("Rejected To Revise"), 'Y', "Rejected"));
		model.getNode("Rejected To Revise").addAction(new WfAction("Revised", model.getNode("To Check"), 'N', "Revised"));

		WfFork wfFork = new WfFork("Fork");
		model.addNode(wfFork);
		modelservice.saveModel(model);

		WfJoin wfJoin = new WfJoin("Join Node", wfFork);
		model.addNode(wfJoin);
		modelservice.saveModel(model);

		model.getNode("From Checker To Revise").addAction(new WfAction("Revised", model.getNode("Fork"), 'N', "Revised"));

		model.getNode("Fork").addAction(new WfAction("HSRevised", model.getNode("To Review Health & Safety"), 'N', "Revised"));
		model.getNode("Fork").addAction(new WfAction("ENVRevised", model.getNode("To Review Environment"), 'N', "Revised"));
		model.getNode("Fork").addAction(new WfAction("QTYRevised", model.getNode("To Review Quality"), 'N', "Revised"));

		model.getNode("To Review Health & Safety").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));
		model.getNode("To Review Environment").addAction(new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));
		model.getNode("To Review Quality").addAction(new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));

		model.getNode("Join Node").addAction(new WfAction("Joined", model.getNode("From Reviewer To Revise"), 'N', "Reviewed"));
		model.getNode("From Reviewer To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));

		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("To Finalise"), 'N', "Approved"));
		model.getNode("To Approve")
			.addAction(new WfAction("Rejected", model.getNode("From Approver To Revise"), 'Y', "Rejected"));
		model.getNode("From Approver To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("To Finalise").addAction(new WfAction("Finalised", model.getNode("To Publish"), 'N', "Finalised"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'N', "Published"));
		model.getNode("To Publish").addAction(new WfAction("Rejected", model.getNode("To Finalise"), 'Y', "Rejected"));
		modelservice.saveModel(model);
		return model;
	}

	// Test wf for New Config
	private static WfModel setupModel4(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Fork Revision Test", "Owner", 'Y');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/app:company_home/app:user_homes/cm:DSJV/cm:" + ISO9075.encode("11.0 Construction Process"), false));
		modelservice.saveModel(model);

		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Check", "R:Checker", "To Check", 'Y'));

		model.addNode(new WfTask("To Review Health & Safety", "RG:Reviewer.Health & Safety", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Environment", "RG:Reviewer.Environment", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Quality", "RG:Reviewer.Quality", "To Review", 'Y'));

		model.addNode(new WfTask("To Publish", "R:Publisher/DMT", "To Publish", 'Y'));
		model.addNode(new WfTask("From Checker To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Rejected To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Reviewer To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Approver To Revise", "U:{CREATOR}", "To Revise", 'N'));

		model.addNode(new WfTask("Reviewed From Health & Safety", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Reviewed From Environment", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Reviewed From Quality", "U:{CREATOR}", "To Revise", 'N'));

		model.addNode(new WfTask("To Approve", "R:DSJV Approver", "To Approve", 'Y'));
		model.addNode(new WfTask("To Finalise", "U:{CREATOR}", "To Finalise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Check"), 'N', "Prepared"));
		model.getNode("To Check").addAction(new WfAction("Checked", model.getNode("From Checker To Revise"), 'N', "Checked"));
		model.getNode("To Check").addAction(new WfAction("Rejected", model.getNode("Rejected To Revise"), 'Y', "Rejected"));
		model.getNode("Rejected To Revise").addAction(new WfAction("Revised", model.getNode("To Check"), 'N', "Revised"));

		WfFork wfFork = new WfFork("Fork");
		model.addNode(wfFork);
		modelservice.saveModel(model);

		WfJoin wfJoin = new WfJoin("Join Node", wfFork);
		model.addNode(wfJoin);
		modelservice.saveModel(model);

		model.getNode("From Checker To Revise").addAction(new WfAction("Revised", model.getNode("Fork"), 'N', "Revised"));

		model.getNode("Fork").addAction(new WfAction("HSRevised", model.getNode("To Review Health & Safety"), 'N', "Revised"));

		model.getNode("To Review Health & Safety").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Health & Safety"), 'N', "Reviewed"));

		model.getNode("Fork").addAction(new WfAction("ENVRevised", model.getNode("To Review Environment"), 'N', "Revised"));

		model.getNode("To Review Environment").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Environment"), 'N', "Reviewed"));

		model.getNode("Fork").addAction(new WfAction("QTYRevised", model.getNode("To Review Quality"), 'N', "Revised"));

		model.getNode("To Review Quality").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Quality"), 'N', "Reviewed"));

		model.getNode("Reviewed From Health & Safety").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));
		model.getNode("Reviewed From Environment").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));
		model.getNode("Reviewed From Quality").addAction(new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));

		model.getNode("Join Node").addAction(new WfAction("Joined", model.getNode("From Reviewer To Revise"), 'N', "Reviewed"));
		model.getNode("From Reviewer To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));

		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("To Finalise"), 'N', "Approved"));
		model.getNode("To Approve")
			.addAction(new WfAction("Rejected", model.getNode("From Approver To Revise"), 'Y', "Rejected"));
		model.getNode("From Approver To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("To Finalise").addAction(new WfAction("Finalised", model.getNode("To Publish"), 'N', "Finalised"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'N', "Published"));
		model.getNode("To Publish").addAction(new WfAction("Rejected", model.getNode("To Finalise"), 'Y', "Rejected"));
		modelservice.saveModel(model);
		return model;
	}

	// Test model creation for MS & SCN forking contains H&S,QTY,ENV,CONS review
	// process

	private static WfModel setupModel5(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Full Workflow Process", "Owner", 'Y');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/app:company_home/app:user_homes/cm:DSJV/cm:" + ISO9075.encode("11.0 Construction Process"), false));
		modelservice.saveModel(model);

		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Check", "R:Checker", "To Check", 'Y'));

		model.addNode(new WfTask("To Review Health & Safety", "RG:Reviewer.Health & Safety", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Environment", "RG:Reviewer.Environment", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Quality", "RG:Reviewer.Quality", "To Review", 'Y'));

		// For new config
		model.addNode(new WfTask("To Review Construction", "RG:Reviewer.Construction", "To Review", 'Y'));

		model.addNode(new WfTask("To Publish", "R:Publisher/DMT", "To Publish", 'Y'));
		model.addNode(new WfTask("From Checker To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Rejected To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Reviewer To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Approver To Revise", "U:{CREATOR}", "To Revise", 'N'));

		model.addNode(new WfTask("Reviewed From Health & Safety", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Reviewed From Environment", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Reviewed From Quality", "U:{CREATOR}", "To Revise", 'N'));

		// For new config
		model.addNode(new WfTask("Reviewed From Construction", "U:{CREATOR}", "To Revise", 'N'));

		model.addNode(new WfTask("To Approve", "R:DSJV Approver", "To Approve", 'Y'));
		model.addNode(new WfTask("To Finalise", "U:{CREATOR}", "To Finalise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Check"), 'N', "Prepared"));
		model.getNode("To Check").addAction(new WfAction("Checked", model.getNode("From Checker To Revise"), 'N', "Checked"));
		model.getNode("To Check").addAction(new WfAction("Rejected", model.getNode("Rejected To Revise"), 'Y', "Rejected"));
		model.getNode("Rejected To Revise").addAction(new WfAction("Revised", model.getNode("To Check"), 'N', "Revised"));

		WfFork wfFork = new WfFork("Fork");
		model.addNode(wfFork);
		modelservice.saveModel(model);

		WfJoin wfJoin = new WfJoin("Join Node", wfFork);
		model.addNode(wfJoin);
		modelservice.saveModel(model);

		model.getNode("From Checker To Revise").addAction(new WfAction("Revised", model.getNode("Fork"), 'N', "Revised"));

		model.getNode("Fork").addAction(new WfAction("HSRevised", model.getNode("To Review Health & Safety"), 'N', "Revised"));

		model.getNode("To Review Health & Safety").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Health & Safety"), 'N', "Reviewed"));

		model.getNode("Fork").addAction(new WfAction("ENVRevised", model.getNode("To Review Environment"), 'N', "Revised"));

		model.getNode("To Review Environment").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Environment"), 'N', "Reviewed"));

		model.getNode("Fork").addAction(new WfAction("QTYRevised", model.getNode("To Review Quality"), 'N', "Revised"));

		model.getNode("To Review Quality").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Quality"), 'N', "Reviewed"));

		// For new config
		model.getNode("Fork").addAction(new WfAction("CONRevised", model.getNode("To Review Construction"), 'N', "Revised"));
		model.getNode("To Review Construction").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Construction"), 'N', "Reviewed"));

		model.getNode("Reviewed From Health & Safety").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));
		model.getNode("Reviewed From Environment").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));
		model.getNode("Reviewed From Quality").addAction(new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));

		// For new config
		model.getNode("Reviewed From Construction").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));

		model.getNode("Join Node").addAction(new WfAction("Joined", model.getNode("From Reviewer To Revise"), 'N', "Reviewed"));
		model.getNode("From Reviewer To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));

		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("To Finalise"), 'N', "Approved"));
		model.getNode("To Approve")
			.addAction(new WfAction("Rejected", model.getNode("From Approver To Revise"), 'Y', "Rejected"));
		model.getNode("From Approver To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("To Finalise").addAction(new WfAction("Finalised", model.getNode("To Publish"), 'N', "Finalised"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'Y', "Published"));
		model.getNode("To Publish").addAction(new WfAction("Rejected", model.getNode("To Finalise"), 'Y', "Rejected"));
		modelservice.saveModel(model);
		return model;
	}

	// Test model creation for ITPS forking contains QTY,CONS,ENGG. review
	// process

	private static WfModel setupModel6(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Full Workflow ITPS", "Owner", 'Y');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/app:company_home/app:user_homes/cm:DSJV/cm:" + ISO9075.encode("11.0 Construction Process"), false));
		modelservice.saveModel(model);

		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Check", "R:Checker", "To Check", 'Y'));

		model.addNode(new WfTask("To Review Construction", "RG:Reviewer.Construction", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Engineering", "RG:Reviewer.Engineering", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Quality", "RG:Reviewer.Quality", "To Review", 'Y'));

		model.addNode(new WfTask("To Publish", "R:Publisher/DMT", "To Publish", 'Y'));
		model.addNode(new WfTask("From Checker To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Rejected To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Reviewer To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Approver To Revise", "U:{CREATOR}", "To Revise", 'N'));

		model.addNode(new WfTask("Reviewed From Construction", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Reviewed From Engineering", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Reviewed From Quality", "U:{CREATOR}", "To Revise", 'N'));

		model.addNode(new WfTask("To Approve", "R:DSJV Approver", "To Approve", 'Y'));
		model.addNode(new WfTask("To Finalise", "U:{CREATOR}", "To Finalise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Check"), 'N', "Prepared"));
		model.getNode("To Check").addAction(new WfAction("Checked", model.getNode("From Checker To Revise"), 'N', "Checked"));
		model.getNode("To Check").addAction(new WfAction("Rejected", model.getNode("Rejected To Revise"), 'Y', "Rejected"));
		model.getNode("Rejected To Revise").addAction(new WfAction("Revised", model.getNode("To Check"), 'N', "Revised"));

		WfFork wfFork = new WfFork("Fork");
		model.addNode(wfFork);
		modelservice.saveModel(model);

		WfJoin wfJoin = new WfJoin("Join Node", wfFork);
		model.addNode(wfJoin);
		modelservice.saveModel(model);

		model.getNode("From Checker To Revise").addAction(new WfAction("Revised", model.getNode("Fork"), 'N', "Revised"));

		model.getNode("Fork").addAction(new WfAction("CONRevised", model.getNode("To Review Construction"), 'N', "Revised"));

		model.getNode("To Review Construction").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Construction"), 'N', "Reviewed"));

		model.getNode("Fork").addAction(new WfAction("ENGGRevised", model.getNode("To Review Engineering"), 'N', "Revised"));

		model.getNode("To Review Engineering").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Engineering"), 'N', "Reviewed"));

		model.getNode("Fork").addAction(new WfAction("QTYRevised", model.getNode("To Review Quality"), 'N', "Revised"));

		model.getNode("To Review Quality").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Quality"), 'N', "Reviewed"));

		model.getNode("Reviewed From Construction").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));
		model.getNode("Reviewed From Engineering").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));
		model.getNode("Reviewed From Quality").addAction(new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));

		model.getNode("Join Node").addAction(new WfAction("Joined", model.getNode("From Reviewer To Revise"), 'N', "Reviewed"));
		model.getNode("From Reviewer To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));

		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("To Finalise"), 'N', "Approved"));
		model.getNode("To Approve")
			.addAction(new WfAction("Rejected", model.getNode("From Approver To Revise"), 'Y', "Rejected"));
		model.getNode("From Approver To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("To Finalise").addAction(new WfAction("Finalised", model.getNode("To Publish"), 'N', "Finalised"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'Y', "Published"));
		model.getNode("To Publish").addAction(new WfAction("Rejected", model.getNode("To Finalise"), 'Y', "Rejected"));
		modelservice.saveModel(model);
		return model;
	}

	// Test model creation for MCR forking contains QTY,CONS review process

	private static WfModel setupModel7(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Full Workflow MCR", "Owner", 'Y');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/app:company_home/app:user_homes/cm:DSJV/cm:" + ISO9075.encode("11.0 Construction Process"), false));
		modelservice.saveModel(model);

		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Check", "R:Checker", "To Check", 'Y'));

		model.addNode(new WfTask("To Review Construction", "RG:Reviewer.Construction", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Quality", "RG:Reviewer.Quality", "To Review", 'Y'));

		model.addNode(new WfTask("To Publish", "R:Publisher/DMT", "To Publish", 'Y'));
		model.addNode(new WfTask("From Checker To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Rejected To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Reviewer To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Approver To Revise", "U:{CREATOR}", "To Revise", 'N'));

		model.addNode(new WfTask("Reviewed From Construction", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Reviewed From Quality", "U:{CREATOR}", "To Revise", 'N'));

		model.addNode(new WfTask("To Approve", "R:DSJV Approver", "To Approve", 'Y'));
		model.addNode(new WfTask("To Finalise", "U:{CREATOR}", "To Finalise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Check"), 'N', "Prepared"));
		model.getNode("To Check").addAction(new WfAction("Checked", model.getNode("From Checker To Revise"), 'N', "Checked"));
		model.getNode("To Check").addAction(new WfAction("Rejected", model.getNode("Rejected To Revise"), 'Y', "Rejected"));
		model.getNode("Rejected To Revise").addAction(new WfAction("Revised", model.getNode("To Check"), 'N', "Revised"));

		WfFork wfFork = new WfFork("Fork");
		model.addNode(wfFork);
		modelservice.saveModel(model);

		WfJoin wfJoin = new WfJoin("Join Node", wfFork);
		model.addNode(wfJoin);
		modelservice.saveModel(model);

		model.getNode("From Checker To Revise").addAction(new WfAction("Revised", model.getNode("Fork"), 'N', "Revised"));

		model.getNode("Fork").addAction(new WfAction("CONRevised", model.getNode("To Review Construction"), 'N', "Revised"));

		model.getNode("To Review Construction").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Construction"), 'N', "Reviewed"));

		model.getNode("Fork").addAction(new WfAction("QTYRevised", model.getNode("To Review Quality"), 'N', "Revised"));

		model.getNode("To Review Quality").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Quality"), 'N', "Reviewed"));

		model.getNode("Reviewed From Construction").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));
		model.getNode("Reviewed From Quality").addAction(new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));

		model.getNode("Join Node").addAction(new WfAction("Joined", model.getNode("From Reviewer To Revise"), 'N', "Reviewed"));
		model.getNode("From Reviewer To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));

		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("To Finalise"), 'N', "Approved"));
		model.getNode("To Approve")
			.addAction(new WfAction("Rejected", model.getNode("From Approver To Revise"), 'Y', "Rejected"));
		model.getNode("From Approver To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("To Finalise").addAction(new WfAction("Finalised", model.getNode("To Publish"), 'N', "Finalised"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'Y', "Published"));
		model.getNode("To Publish").addAction(new WfAction("Rejected", model.getNode("To Finalise"), 'Y', "Rejected"));
		modelservice.saveModel(model);
		return model;
	}

	// New config Approval Only

	private static WfModel setupModel8(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Approval Only", "Owner", 'Y');
		modelservice.saveModel(model);

		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/app:company_home/app:user_homes/cm:DSJV/cm:" + ISO9075.encode("11.0 Construction Process"), false));
		modelservice.saveModel(model);
		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Publish", "R:Publisher/DMT", "To Publish", 'Y'));
		model.addNode(new WfTask("From Approver To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("To Approve", "R:DSJV Approver", "To Approve", 'Y'));
		model.addNode(new WfTask("To Finalise", "U:{CREATOR}", "To Finalise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Approve"), 'N', "Prepared"));
		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("To Finalise"), 'N', "Approved"));
		model.getNode("To Approve")
			.addAction(new WfAction("Rejected", model.getNode("From Approver To Revise"), 'Y', "Rejected"));
		model.getNode("From Approver To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("To Finalise").addAction(new WfAction("Finalised", model.getNode("To Publish"), 'N', "Finalised"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'Y', "Published"));
		model.getNode("To Publish").addAction(new WfAction("Rejected", model.getNode("To Finalise"), 'Y', "Rejected"));

		modelservice.saveModel(model);
		return model;
	}

	// Without Review Config for 23.01.13 release
	private static WfModel setupModel9(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Without Review", "Owner", 'Y');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/app:company_home/app:user_homes/cm:DSJV/cm:" + ISO9075.encode("11.0 Construction Process"), false));
		modelservice.saveModel(model);

		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Check", "R:Checker", "To Check", 'Y'));
		model.addNode(new WfTask("To Publish", "R:Publisher/DMT", "To Publish", 'Y'));
		model.addNode(new WfTask("From Checker To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Rejected To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Approver To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("To Approve", "R:DSJV Approver", "To Approve", 'Y'));
		model.addNode(new WfTask("To Finalise", "U:{CREATOR}", "To Finalise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Check"), 'N', "Prepared"));
		model.getNode("To Check").addAction(new WfAction("Checked", model.getNode("From Checker To Revise"), 'N', "Checked"));
		model.getNode("To Check").addAction(new WfAction("Rejected", model.getNode("Rejected To Revise"), 'Y', "Rejected"));
		model.getNode("Rejected To Revise").addAction(new WfAction("Revised", model.getNode("To Check"), 'N', "Revised"));
		model.getNode("From Checker To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("To Finalise"), 'N', "Approved"));
		model.getNode("To Approve")
			.addAction(new WfAction("Rejected", model.getNode("From Approver To Revise"), 'Y', "Rejected"));
		model.getNode("From Approver To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("To Finalise").addAction(new WfAction("Finalised", model.getNode("To Publish"), 'N', "Finalised"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'Y', "Published"));
		model.getNode("To Publish").addAction(new WfAction("Rejected", model.getNode("To Finalise"), 'Y', "Rejected"));

		modelservice.saveModel(model);
		return model;
	}

	// Without Review Config for 25.01.13 release
	private static WfModel setupModel13(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Without Review Engg", "RG:Owner.Engineering", 'Y');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/User Homes/DSJV/11.0 Construction Process", false));
		modelservice.saveModel(model);

		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Check", "RG:Checker.Engineering", "To Check", 'Y'));
		model.addNode(new WfTask("To Publish", "R:Publisher/DMT", "To Publish", 'Y'));
		model.addNode(new WfTask("From Checker To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Rejected To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Approver To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("To Approve", "RG:Checker.DSJV Approver", "To Approve", 'Y'));
		model.addNode(new WfTask("To Finalise", "U:{CREATOR}", "To Finalise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Check"), 'N', "Prepared"));
		model.getNode("To Check").addAction(new WfAction("Checked", model.getNode("From Checker To Revise"), 'N', "Checked"));
		model.getNode("To Check").addAction(new WfAction("Rejected", model.getNode("Rejected To Revise"), 'Y', "Rejected"));
		model.getNode("Rejected To Revise").addAction(new WfAction("Revised", model.getNode("To Check"), 'N', "Revised"));
		model.getNode("From Checker To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("To Finalise"), 'N', "Approved"));
		model.getNode("To Approve")
			.addAction(new WfAction("Rejected", model.getNode("From Approver To Revise"), 'Y', "Rejected"));
		model.getNode("From Approver To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("To Finalise").addAction(new WfAction("Finalised", model.getNode("To Publish"), 'N', "Finalised"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'Y', "Published"));
		model.getNode("To Publish").addAction(new WfAction("Rejected", model.getNode("To Finalise"), 'Y', "Rejected"));

		modelservice.saveModel(model);
		return model;
	}

	private static WfModel setupModel14(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Full Workflow Engg", "Owner", 'Y');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/app:company_home/app:user_homes/cm:DSJV/cm:" + ISO9075.encode("11.0 Construction Process"), false));
		modelservice.saveModel(model);

		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Check", "R:Checker", "To Check", 'Y'));

		model.addNode(new WfTask("To Review Health & Safety", "RG:Reviewer.Health & Safety", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Environment", "RG:Reviewer.Environment", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Quality", "RG:Reviewer.Quality", "To Review", 'Y'));

		// For new config
		// model.addNode(new
		// WfTask("To Review Construction","RG:Reviewer.Construction","To Review",'Y'));

		model.addNode(new WfTask("To Publish", "R:Publisher/DMT", "To Publish", 'Y'));
		model.addNode(new WfTask("From Checker To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Rejected To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Reviewer To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Approver To Revise", "U:{CREATOR}", "To Revise", 'N'));

		model.addNode(new WfTask("Reviewed From Health & Safety", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Reviewed From Environment", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("Reviewed From Quality", "U:{CREATOR}", "To Revise", 'N'));

		// For new config
		// model.addNode(new WfTask("Reviewed From Construction",
		// "U:{CREATOR}","To Revise",'N'));

		model.addNode(new WfTask("To Approve", "R:DSJV Approver", "To Approve", 'Y'));
		model.addNode(new WfTask("To Finalise", "U:{CREATOR}", "To Finalise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Check"), 'N', "Prepared"));
		model.getNode("To Check").addAction(new WfAction("Checked", model.getNode("From Checker To Revise"), 'N', "Checked"));
		model.getNode("To Check").addAction(new WfAction("Rejected", model.getNode("Rejected To Revise"), 'Y', "Rejected"));
		model.getNode("Rejected To Revise").addAction(new WfAction("Revised", model.getNode("To Check"), 'N', "Revised"));

		WfFork wfFork = new WfFork("Fork");
		model.addNode(wfFork);
		modelservice.saveModel(model);

		WfJoin wfJoin = new WfJoin("Join Node", wfFork);
		model.addNode(wfJoin);
		modelservice.saveModel(model);

		model.getNode("From Checker To Revise").addAction(new WfAction("Revised", model.getNode("Fork"), 'N', "Revised"));

		model.getNode("Fork").addAction(new WfAction("HSRevised", model.getNode("To Review Health & Safety"), 'N', "Revised"));

		model.getNode("To Review Health & Safety").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Health & Safety"), 'N', "Reviewed"));

		model.getNode("Fork").addAction(new WfAction("ENVRevised", model.getNode("To Review Environment"), 'N', "Revised"));

		model.getNode("To Review Environment").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Environment"), 'N', "Reviewed"));

		model.getNode("Fork").addAction(new WfAction("QTYRevised", model.getNode("To Review Quality"), 'N', "Revised"));

		model.getNode("To Review Quality").addAction(
			new WfAction("Reviewed", model.getNode("Reviewed From Quality"), 'N', "Reviewed"));

		// For new config
		// model.getNode("Fork").addAction(new WfAction("CONRevised",
		// model.getNode("To Review Construction"),'N',"Revised"));
		// model.getNode("To Review Construction").addAction(new
		// WfAction("Reviewed",
		// model.getNode("Reviewed From Construction"),'N',"Reviewed"));

		model.getNode("Reviewed From Health & Safety").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));
		model.getNode("Reviewed From Environment").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));
		model.getNode("Reviewed From Quality").addAction(new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Reviewed"));

		// For new config
		// model.getNode("Reviewed From Construction").addAction(new
		// WfAction("Reviewed", model.getNode("Join Node"),'N',"Reviewed"));

		model.getNode("Join Node").addAction(new WfAction("Joined", model.getNode("From Reviewer To Revise"), 'N', "Reviewed"));
		model.getNode("From Reviewer To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));

		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("To Finalise"), 'N', "Approved"));
		model.getNode("To Approve")
			.addAction(new WfAction("Rejected", model.getNode("From Approver To Revise"), 'Y', "Rejected"));
		model.getNode("From Approver To Revise").addAction(new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("To Finalise").addAction(new WfAction("Finalised", model.getNode("To Publish"), 'N', "Finalised"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'Y', "Published"));
		model.getNode("To Publish").addAction(new WfAction("Rejected", model.getNode("To Finalise"), 'Y', "Rejected"));
		modelservice.saveModel(model);
		return model;
	}

	private static WfModel setupModel1(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Parallel Workflow", "Owner", 'Y');
		modelservice.saveModel(model);

		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Reference Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		// model.addAttribute(new WfAttribute("FilePath",
		// WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"+ISO9075.encode("5.0 Health,Safety & Security"),false));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/app:company_home/app:user_homes/cm:DSJV/cm:" + ISO9075.encode("11.0 Construction Process"), false));
		modelservice.saveModel(model);
		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));

		WfFork wfFork = new WfFork("Fork");
		model.addNode(wfFork);

		// model.addNode(new WfTask("To Review", "R:Reviewer","To Review"));
		model.addNode(new WfTask("To Review Health & Safety", "RG:Reviewer.Health & Safety", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Environment", "RG:Reviewer.Environment", "To Review", 'Y'));
		model.addNode(new WfTask("To Review Quality", "RG:Reviewer.Quality", "To Review", 'Y'));
		model.addNode(new WfTask("To Check", "R:Checker", "To Check", 'Y'));
		model.addNode(new WfStop("stop"));
		modelservice.saveModel(model);
		model.setAsStart("To Prepare");
		model.setAsEnd("stop");

		WfJoin wfJoin = new WfJoin("Join Node", wfFork);
		model.addNode(wfJoin);
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("Fork"), 'N', "Prepared"));
		model.getNode("Fork").addAction(new WfAction("Fork1", model.getNode("To Review Health & Safety"), 'N', "Fork1"));
		model.getNode("Fork").addAction(new WfAction("Fork2", model.getNode("To Review Environment"), 'N', "Fork2"));
		model.getNode("Fork").addAction(new WfAction("Fork3", model.getNode("To Review Quality"), 'N', "Fork3"));

		model.getNode("To Review Health & Safety").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Health & Safety Reviewed"));
		model.getNode("To Review Environment").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Environment Reviewed"));
		model.getNode("To Review Quality").addAction(
			new WfAction("Reviewed", model.getNode("Join Node"), 'N', "Quality Reviewed"));
		model.getNode("Join Node").addAction(new WfAction("Joined", model.getNode("To Check"), 'N', "Review Completed"));
		model.getNode("To Check").addAction(new WfAction("Revised", model.getNode("To Check"), 'N', "Checked"));
		modelservice.saveModel(model);
		return model;
	}

	// Fast track Tray label Implemented Config for 13.02.13 release
	private static WfModel setupModel2(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Fast Track", "Owner", 'Y');
		modelservice.saveModel(model);

		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/app:company_home/app:user_homes/cm:DSJV/cm:" + ISO9075.encode("11.0 Construction Process"), false));
		modelservice.saveModel(model);
		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Publish", "R:Publisher/DMT", "To Publish", 'Y'));
		model.addNode(new WfTask("To Finalise", "U:{CREATOR}", "To Finalise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Publish"), 'Y', "Prepared"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'N', "Published"));
		model.getNode("To Publish").addAction(new WfAction("Rejected", model.getNode("To Finalise"), 'Y', "Rejected"));
		model.getNode("To Finalise").addAction(new WfAction("Finalised", model.getNode("To Publish"), 'N', "Finalised"));
		modelservice.saveModel(model);
		return model;
	}
	
	

	private static WfModel setupModel15(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Educational Demo4", "Owner", 'Y');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Reference Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Code Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/User Homes/DSJV/11.0 Construction Process", false));
		modelservice.saveModel(model);
		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Approve", "R:Deputy Head", "To Approve", 'Y'));
		model.addNode(new WfTask("To Publish", "R:Business Manager", "To Publish", 'Y'));
		model.addNode(new WfTask("From Deputy Head To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Business Manager To Revise", "U:{CREATOR}", "To Revise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Approve"), 'Y', "Prepared"));
		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("To Publish"), 'N', "Approved"));
		model.getNode("To Approve").addAction(
			new WfAction("Rejected", model.getNode("From Deputy Head To Revise"), 'Y', "Rejected"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'Y', "Published"));
		model.getNode("To Publish").addAction(
			new WfAction("Rejected", model.getNode("From Business Manager To Revise"), 'Y', "Rejected"));
		model.getNode("From Deputy Head To Revise").addAction(
			new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("From Business Manager To Revise").addAction(
			new WfAction("Revised", model.getNode("To Publish"), 'N', "Revised"));
		modelservice.saveModel(model);
		return model;
	}

	// New Model Creation after Model Category('F'or'D') Implemented
	private static WfModel setupModel16(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Forms Bentley Workflow", "Owner", 'Y', 'F');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Reference Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Code Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/User Homes/DSJV/11.0 Construction Process", false));
		modelservice.saveModel(model);
		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Approve", "R:Deputy Head", "To Approve", 'Y'));
		model.addNode(new WfTask("To Publish", "R:Business Manager", "To Publish", 'Y'));
		model.addNode(new WfTask("From Deputy Head To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfTask("From Business Manager To Revise", "U:{CREATOR}", "To Revise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Approve"), 'Y', "Prepared"));
		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("To Publish"), 'N', "Approved"));
		model.getNode("To Approve").addAction(
			new WfAction("Rejected", model.getNode("From Deputy Head To Revise"), 'Y', "Rejected"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'Y', "Published"));
		model.getNode("To Publish").addAction(
			new WfAction("Rejected", model.getNode("From Business Manager To Revise"), 'Y', "Rejected"));
		model.getNode("From Deputy Head To Revise").addAction(
			new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		model.getNode("From Business Manager To Revise").addAction(
			new WfAction("Revised", model.getNode("To Publish"), 'N', "Revised"));
		modelservice.saveModel(model);
		return model;
	}

	private static WfModel setupModel17(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Events/Functions and Meetings Form", "Owner", 'Y', 'F');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Reference Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Code Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/User Homes/DSJV/11.0 Construction Process", false));
		modelservice.saveModel(model);
		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Approve", "R:Business Manager", "To Approve", 'Y'));
		model.addNode(new WfTask("To Review", "R:Deputy Head", "To Review", 'Y'));
		model.addNode(new WfTask("From Business Manager To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);
		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);
		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Approve"), 'Y', "Prepared"));
		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("Completed"), 'Y', "Published"));
		model.getNode("To Approve").addAction(
			new WfAction("Rejected", model.getNode("From Business Manager To Revise"), 'Y', "Rejected"));
		model.getNode("To Approve").addAction(new WfAction("Escalated", model.getNode("To Review"), 'Y', "Escalated"));
		model.getNode("To Review").addAction(new WfAction("Reviewed", model.getNode("To Approve"), 'N', "Reviewed"));
		model.getNode("From Business Manager To Revise").addAction(
			new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		modelservice.saveModel(model);
		return model;
	}

	private static WfModel setupModel18(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Events/Functions and Meetings Form", "Owner", 'Y', 'F');
		modelservice.saveModel(model);
		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Reference Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Code Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/User Homes/DSJV/11.0 Construction Process", false));
		modelservice.saveModel(model);
		// model.addNode(new WfTask("To Prepare",
		// "U:{CREATOR}","To Prepare",'N'));
		model.addNode(new WfTask("To Approve", "R:Business Manager", "To Approve", 'Y'));
		model.addNode(new WfTask("To Review", "R:Deputy Head", "To Review", 'Y'));
		model.addNode(new WfTask("From Business Manager To Revise", "U:{CREATOR}", "To Revise", 'N'));
		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);
		model.setAsStart("To Approve");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);
		// model.getNode("To Prepare").addAction(new WfAction("Prepared",
		// model.getNode("To Approve"),'Y',"Prepared"));
		model.getNode("To Approve").addAction(new WfAction("Approved", model.getNode("Completed"), 'Y', "Published"));
		model.getNode("To Approve").addAction(
			new WfAction("Rejected", model.getNode("From Business Manager To Revise"), 'Y', "Rejected"));
		model.getNode("To Approve").addAction(new WfAction("Escalated", model.getNode("To Review"), 'Y', "Escalated"));
		model.getNode("To Review").addAction(new WfAction("Reviewed", model.getNode("To Approve"), 'N', "Reviewed"));
		model.getNode("From Business Manager To Revise").addAction(
			new WfAction("Revised", model.getNode("To Approve"), 'N', "Revised"));
		modelservice.saveModel(model);
		return model;
	}
	
	private static WfModel setupModel19(ModelService modelservice) throws Exception {

		WfModel model = new WfModel("Fast Track", "Owner", 'Y');
		modelservice.saveModel(model);

		model.addAttribute(new WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Keywords", WfAttributeType.WF_ATTR_TEXT));
		model.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		model.addAttribute(new WfAttribute("FilePath", WfAttributeType.WF_ATTR_TEXT,
			"/app:company_home/app:user_homes/cm:DSJV/cm:" + ISO9075.encode("11.0 Construction Process"), false));
		modelservice.saveModel(model);
		model.addNode(new WfTask("To Prepare", "U:{CREATOR}", "To Prepare", 'N'));
		model.addNode(new WfTask("To Publish", "R:Publisher/DMT", "To Publish", 'Y'));
		model.addNode(new WfTask("To Finalise", "U:{CREATOR}", "To Finalise", 'N'));

		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("Completed"));
		modelservice.saveModel(model);

		model.setAsStart("To Prepare");
		model.setAsEnd("Completed");
		modelservice.saveModel(model);

		model.getNode("To Prepare").addAction(new WfAction("Prepared", model.getNode("To Publish"), 'Y', "Prepared"));
		model.getNode("To Publish").addAction(new WfAction("Published", model.getNode("Completed"), 'N', "Published"));
		model.getNode("To Publish").addAction(new WfAction("Rejected", model.getNode("To Finalise"), 'Y', "Rejected"));
		model.getNode("To Finalise").addAction(new WfAction("Finalised", model.getNode("To Publish"), 'N', "Finalised"));
		modelservice.saveModel(model);
		return model;
	}

}
