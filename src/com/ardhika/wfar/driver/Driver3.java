package com.ardhika.wfar.driver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.alfresco.webservice.util.ISO9075;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ardhika.wfar.WfAction;
import com.ardhika.wfar.WfAssignment;
import com.ardhika.wfar.WfAttribute;
import com.ardhika.wfar.WfAttributeType;
import com.ardhika.wfar.WfCancel;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfDecision;
import com.ardhika.wfar.WfDecisionOperator;
import com.ardhika.wfar.WfFork;
import com.ardhika.wfar.WfJoin;
import com.ardhika.wfar.WfModel;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.WfStop;
import com.ardhika.wfar.WfTask;
import com.ardhika.wfar.WfUserInfo;
import com.ardhika.wfar.service.CaseService;
import com.ardhika.wfar.service.ModelService;

/*
 repository.location=http://115.249.105.154:8080/alfresco/api
 userName=admin
 password=root*/
public class Driver3 {

	public static void main(String[] args) throws Exception {

		// CaseManager mgr = new CaseManager();
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		ModelService modelservice = context.getBean("modelService", ModelService.class);
		CaseService caseservice = context.getBean("caseService", CaseService.class);
		// WfModel model = setupModel1(modelservice);
		// WfModel m = modelservice.findModelById(model.getId());
		// WfModel m = modelservice.findModelByName("MethodStatement");
		// System.out.println(model.getName());
		// itest1(modelservice,caseservice);
		// testFork(modelservice, caseservice);
		testJoin(modelservice, caseservice);
		// itest2(caseservice);
		// itest3(modelservice);
	}

	// RESS Sheet

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("RESS Sheet","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("Reference Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("TargetDate", WfAttributeType.WF_ATTR_DATE));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("11.0 Construction Process"),false));
	 * 
	 * modelservice.saveModel(model); model.addNode(new WfTask("Prepare",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Approve", "R:Publisher/DMT"));
	 * model.addNode(new WfTask("Finalise", "U:{CREATOR}"));
	 * 
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Prepare"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Prepare").addAction(new WfAction("Issue to Approver",
	 * model.getNode("Approve"),'N')); model.getNode("Approve").addAction(new
	 * WfAction("Approved", model.getNode("stop"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Reject",
	 * model.getNode("Finalise"),'Y')); model.getNode("Finalise").addAction(new
	 * WfAction("Issue to Approver", model.getNode("Approve"),'N'));
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	// After Tray label Implemented

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("RESS Sheet","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("Eb Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("Reference Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("11.0 Construction Process"),false));
	 * 
	 * modelservice.saveModel(model); model.addNode(new WfTask("To Prepare",
	 * "U:{CREATOR}","To Prepare")); model.addNode(new WfTask("To Approve",
	 * "R:DSJV Approver","To Approve")); model.addNode(new
	 * WfTask("From Approver To Revise", "U:{CREATOR}","To Revise"));
	 * 
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("To Prepare"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("To Prepare").addAction(new WfAction("Prepared",
	 * model.getNode("To Approve"),'N'));
	 * model.getNode("To Approve").addAction(new WfAction("Approved",
	 * model.getNode("stop"),'N')); model.getNode("To Approve").addAction(new
	 * WfAction("Rejected", model.getNode("From Approver To Revise"),'Y'));
	 * model.getNode("From Approver To Revise").addAction(new
	 * WfAction("Finalised", model.getNode("To Approve"),'N'));
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	// Fast track Tray label Implemented
	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("Fast Track ITPS","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("Eb Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("Reference Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
	 * //model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("5.0 Health,Safety & Security"),false));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("4.0 Quality"),false)); modelservice.saveModel(model);
	 * model.addNode(new WfTask("To Prepare", "U:{CREATOR}","To Prepare"));
	 * model.addNode(new WfTask("To Publish", "R:Publisher/DMT","To Publish"));
	 * model.addNode(new WfTask("To Finalise", "U:{CREATOR}","To Finalise"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("To Prepare"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("To Prepare").addAction(new WfAction("Prepared",
	 * model.getNode("To Publish"),'N'));
	 * model.getNode("To Publish").addAction(new WfAction("Published",
	 * model.getNode("stop"),'N')); model.getNode("To Publish").addAction(new
	 * WfAction("Rejected", model.getNode("To Finalise"),'Y'));
	 * model.getNode("To Finalise").addAction(new WfAction("Finalised",
	 * model.getNode("To Publish"),'N'));
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	// Tray Label Approval Only
	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new
	 * WfModel("Approval Only ITPS","Owner",'Y'); modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("Eb Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("Reference Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
	 * //model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("5.0 Health,Safety & Security"),false));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("4.0 Quality"),false)); modelservice.saveModel(model);
	 * model.addNode(new WfTask("To Prepare", "U:{CREATOR}","To Prepare"));
	 * model.addNode(new WfTask("To Publish", "R:Publisher/DMT","To Publish"));
	 * model.addNode(new WfTask("From Approver To Revise",
	 * "U:{CREATOR}","To Revise")); model.addNode(new
	 * WfTask("To Approve","R:DSJV Approver","To Approve")); model.addNode(new
	 * WfTask("To Finalise", "U:{CREATOR}","To Finalise"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("To Prepare"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("To Prepare").addAction(new WfAction("Prepared",
	 * model.getNode("To Approve"),'N'));
	 * model.getNode("To Approve").addAction(new WfAction("Approved",
	 * model.getNode("To Finalise"),'N'));
	 * model.getNode("To Approve").addAction(new WfAction("Rejected",
	 * model.getNode("From Approver To Revise"),'Y'));
	 * model.getNode("From Approver To Revise").addAction(new
	 * WfAction("Revised", model.getNode("To Approve"),'N'));
	 * model.getNode("To Finalise").addAction(new WfAction("Finalised",
	 * model.getNode("To Publish"),'N'));
	 * model.getNode("To Publish").addAction(new WfAction("Published",
	 * model.getNode("stop"),'N')); model.getNode("To Publish").addAction(new
	 * WfAction("Rejected", model.getNode("To Finalise"),'Y'));
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	// Fast track

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("Fast Track","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("Reference Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("TargetDate", WfAttributeType.WF_ATTR_DATE));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("5.0 Health,Safety & Security"),false));
	 * 
	 * modelservice.saveModel(model); model.addNode(new WfTask("Prepare",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Publish", "R:Publisher/DMT"));
	 * model.addNode(new WfTask("Finalise", "U:{CREATOR}"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Prepare"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Prepare").addAction(new WfAction("Issue to Publisher",
	 * model.getNode("Publish"),'N')); model.getNode("Publish").addAction(new
	 * WfAction("Published", model.getNode("stop"),'N'));
	 * model.getNode("Publish").addAction(new WfAction("Reject",
	 * model.getNode("Finalise"),'Y')); model.getNode("Finalise").addAction(new
	 * WfAction("Issue to Publisher", model.getNode("Publish"),'N'));
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	// Approve Only

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("Approval Only","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("Reference Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("TargetDate", WfAttributeType.WF_ATTR_DATE));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("5.0 Health,Safety & Security"),false));
	 * 
	 * modelservice.saveModel(model); model.addNode(new WfTask("Prepare",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Publish", "R:Publisher/DMT"));
	 * model.addNode(new WfTask("Revise", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Approve","R:DSJV Approver")); model.addNode(new
	 * WfTask("Finalise", "U:{CREATOR}"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Prepare"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Prepare").addAction(new WfAction("Issue to Approver",
	 * model.getNode("Approve"),'N')); model.getNode("Approve").addAction(new
	 * WfAction("Approved", model.getNode("Finalise"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Revise").addAction(new
	 * WfAction("Issue to Approver", model.getNode("Approve"),'N'));
	 * model.getNode("Finalise").addAction(new WfAction("Issue to Publisher",
	 * model.getNode("Publish"),'N')); model.getNode("Publish").addAction(new
	 * WfAction("Published", model.getNode("stop"),'N'));
	 * model.getNode("Publish").addAction(new WfAction("Reject",
	 * model.getNode("Finalise"),'Y'));
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	// Without Review

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("Without Review","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("Reference Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("TargetDate", WfAttributeType.WF_ATTR_DATE));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("5.0 Health,Safety & Security"),false));
	 * 
	 * modelservice.saveModel(model); model.addNode(new WfTask("Prepare",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Check", "R:Checker"));
	 * model.addNode(new WfTask("Publish", "R:Publisher/DMT"));
	 * model.addNode(new WfTask("Revise", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Approve","R:DSJV Approver")); model.addNode(new
	 * WfTask("Finalise", "U:{CREATOR}"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Prepare"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Prepare").addAction(new WfAction("Issue to Checker",
	 * model.getNode("Check"),'N')); model.getNode("Check").addAction(new
	 * WfAction("Checked", model.getNode("Revise"),'N'));
	 * model.getNode("Revise").addAction(new WfAction("Issue to Approver",
	 * model.getNode("Approve"),'N')); model.getNode("Approve").addAction(new
	 * WfAction("Approved", model.getNode("Finalise"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Finalise").addAction(new
	 * WfAction("Issue to Publisher", model.getNode("Publish"),'N'));
	 * model.getNode("Publish").addAction(new WfAction("Published",
	 * model.getNode("stop"),'N')); model.getNode("Publish").addAction(new
	 * WfAction("Reject", model.getNode("Finalise"),'Y'));
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	// Without Review Tray Label Implemented

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new
	 * WfModel("Without Review ITPS","Owner",'Y'); //WfModel model = new
	 * WfModel("Without Review ITPS","Owner",'Y');
	 * modelservice.saveModel(model); model.addAttribute(new
	 * WfAttribute("Eb Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("MDL Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("Reference Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("Target Date",
	 * WfAttributeType.WF_ATTR_DATE)); //model.addAttribute(new
	 * WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("5.0 Health,Safety & Security"),false));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("4.0 Quality"),false)); modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("To Prepare", "U:{CREATOR}","To Prepare"));
	 * model.addNode(new WfTask("To Check", "R:Checker","To Check"));
	 * model.addNode(new WfTask("To Publish", "R:Publisher/DMT","To Publish"));
	 * model.addNode(new WfTask("From Checker To Revise",
	 * "U:{CREATOR}","To Revise")); model.addNode(new
	 * WfTask("From Approver To Revise", "U:{CREATOR}","To Revise"));
	 * model.addNode(new WfTask("To Approve","R:DSJV Approver","To Approve"));
	 * model.addNode(new WfTask("To Finalise", "U:{CREATOR}","To Finalise"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("To Prepare"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("To Prepare").addAction(new WfAction("Prepared",
	 * model.getNode("To Check"),'N')); model.getNode("To Check").addAction(new
	 * WfAction("Checked", model.getNode("From Checker To Revise"),'N'));
	 * model.getNode("From Checker To Revise").addAction(new WfAction("Revised",
	 * model.getNode("To Approve"),'N'));
	 * model.getNode("To Approve").addAction(new WfAction("Approved",
	 * model.getNode("To Finalise"),'N'));
	 * model.getNode("To Approve").addAction(new WfAction("Rejected",
	 * model.getNode("From Approver To Revise"),'Y'));
	 * model.getNode("From Approver To Revise").addAction(new
	 * WfAction("Revised", model.getNode("To Approve"),'Y'));
	 * model.getNode("To Finalise").addAction(new WfAction("Finalised",
	 * model.getNode("To Publish"),'N'));
	 * model.getNode("To Publish").addAction(new WfAction("Published",
	 * model.getNode("stop"),'N')); model.getNode("To Publish").addAction(new
	 * WfAction("Rejected", model.getNode("To Finalise"),'Y'));
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	// Tray Label Implemented Full Workflow
	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new
	 * WfModel("Full Workflow ITPS","Owner",'Y'); modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("Eb Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("Reference Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
	 * //model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("5.0 Health,Safety & Security"),false));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("4.0 Quality"),false)); modelservice.saveModel(model);
	 * model.addNode(new WfTask("To Prepare", "U:{CREATOR}","To Prepare"));
	 * model.addNode(new WfTask("To Check", "R:Checker","To Check"));
	 * model.addNode(new WfTask("To Review", "R:Reviewer","To Review"));
	 * model.addNode(new WfTask("To Publish", "R:Publisher/DMT","To Publish"));
	 * model.addNode(new WfTask("From Checker To Revise",
	 * "U:{CREATOR}","To Revise")); model.addNode(new
	 * WfTask("From Reviewer To Revise", "U:{CREATOR}","To Revise"));
	 * model.addNode(new WfTask("From Approver To Revise",
	 * "U:{CREATOR}","To Revise")); model.addNode(new
	 * WfTask("To Approve","R:DSJV Approver","To Approve")); model.addNode(new
	 * WfTask("To Finalise", "U:{CREATOR}","To Finalise"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("To Prepare"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("To Prepare").addAction(new WfAction("Prepared",
	 * model.getNode("To Check"),'N')); model.getNode("To Check").addAction(new
	 * WfAction("Checked", model.getNode("From Checker To Revise"),'N'));
	 * model.getNode("From Checker To Revise").addAction(new WfAction("Revised",
	 * model.getNode("To Review"),'N'));
	 * model.getNode("To Review").addAction(new WfAction("Reviewed",
	 * model.getNode("From Reviewer To Revise"),'N'));
	 * model.getNode("From Reviewer To Revise").addAction(new
	 * WfAction("Revised", model.getNode("To Approve"),'N'));
	 * model.getNode("To Approve").addAction(new WfAction("Approved",
	 * model.getNode("To Finalise"),'N'));
	 * model.getNode("To Approve").addAction(new WfAction("Rejected",
	 * model.getNode("From Approver To Revise"),'Y'));
	 * model.getNode("From Approver To Revise").addAction(new
	 * WfAction("Revised", model.getNode("To Approve"),'Y'));
	 * model.getNode("To Finalise").addAction(new WfAction("Finalised",
	 * model.getNode("To Publish"),'N'));
	 * model.getNode("To Publish").addAction(new WfAction("Published",
	 * model.getNode("stop"),'N')); model.getNode("To Publish").addAction(new
	 * WfAction("Rejected", model.getNode("To Finalise"),'Y'));
	 * modelservice.saveModel(model); return model; }
	 */

	// Full Workflow

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("Full Workflow","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("Reference Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("TargetDate", WfAttributeType.WF_ATTR_DATE));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("5.0 Health,Safety & Security"),false));
	 * 
	 * modelservice.saveModel(model); model.addNode(new WfTask("Prepare",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Check", "R:Checker"));
	 * model.addNode(new WfTask("Review", "R:Reviewer")); model.addNode(new
	 * WfTask("Publish", "R:Publisher/DMT")); model.addNode(new WfTask("Revise",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Approve","R:DSJV Approver"));
	 * model.addNode(new WfTask("Finalise", "U:{CREATOR}"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Prepare"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Prepare").addAction(new WfAction("Issue to Checker",
	 * model.getNode("Check"),'N')); model.getNode("Check").addAction(new
	 * WfAction("Checked", model.getNode("Revise"),'N'));
	 * model.getNode("Revise").addAction(new WfAction("Issue to Reviewer",
	 * model.getNode("Review"),'N')); model.getNode("Review").addAction(new
	 * WfAction("Reviewed", model.getNode("Revise"),'N'));
	 * model.getNode("Revise").addAction(new WfAction("Issue to Approver",
	 * model.getNode("Approve"),'N')); model.getNode("Approve").addAction(new
	 * WfAction("Approved", model.getNode("Finalise"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Finalise").addAction(new
	 * WfAction("Issue to Publisher", model.getNode("Publish"),'N'));
	 * model.getNode("Publish").addAction(new WfAction("Published",
	 * model.getNode("stop"),'N')); model.getNode("Publish").addAction(new
	 * WfAction("Reject", model.getNode("Finalise"),'Y'));
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	// For High Risk

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("Low Risk","Owner",'N');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("Reference Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("TargetDate", WfAttributeType.WF_ATTR_DATE));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("5.0 Health,Safety & Security"),false));
	 * 
	 * modelservice.saveModel(model); model.addNode(new WfTask("Prepare",
	 * "U:{CREATOR}","Initial")); model.addNode(new WfTask("Check",
	 * "R:Checker","Initial")); model.addNode(new WfTask("Review",
	 * "R:Reviewer","Intermediate")); model.addNode(new WfTask("Publish",
	 * "R:Publisher/DMT","Final")); model.addNode(new WfTask("Revise",
	 * "U:{CREATOR}","Intermediate")); model.addNode(new
	 * WfTask("Approve","R:DSJV Approver","Final")); model.addNode(new
	 * WfTask("Finalise", "U:{CREATOR}","Final"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Prepare"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Prepare").addAction(new WfAction("Issue",
	 * model.getNode("Check"),'N')); model.getNode("Check").addAction(new
	 * WfAction("Checked", model.getNode("Revise"),'N'));
	 * model.getNode("Revise").addAction(new WfAction("Issue to reviewer",
	 * model.getNode("Review"),'N')); model.getNode("Review").addAction(new
	 * WfAction("Reviewed", model.getNode("Revise"),'N'));
	 * model.getNode("Revise").addAction(new WfAction("Issue to approver",
	 * model.getNode("Approve"),'N')); model.getNode("Approve").addAction(new
	 * WfAction("Approved", model.getNode("Finalise"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Revise").addAction(new
	 * WfAction("Release", model.getNode("Approve"),'N'));
	 * model.getNode("Finalise").addAction(new WfAction("Issue",
	 * model.getNode("Publish"),'N')); model.getNode("Publish").addAction(new
	 * WfAction("Published", model.getNode("stop"),'N'));
	 * model.getNode("Publish").addAction(new WfAction("Reject",
	 * model.getNode("Finalise"),'Y'));
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("Low Risk","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); //model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT,"C12.003",true));
	 * model.addAttribute(new WfAttribute("MDL Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("TargetDate", WfAttributeType.WF_ATTR_DATE));
	 * model.addAttribute(new WfAttribute("Reference Link",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJVNew/cm:"
	 * +ISO9075.encode("11.0 Construction Process"),false));
	 * modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Check", "R:Checker")); model.addNode(new WfTask("Review",
	 * "R:Reviewer")); model.addNode(new WfTask("Publish", "R:Publisher/DMT"));
	 * model.addNode(new WfTask("Checked","U:{CREATOR}")); model.addNode(new
	 * WfTask("Reviewed","U:{CREATOR}")); model.addNode(new WfTask("Revise",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Approve","R:DSJV Approver"));
	 * model.addNode(new WfTask("Approved", "U:{CREATOR}"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Release",
	 * model.getNode("Check"),'N')); model.getNode("Check").addAction(new
	 * WfAction("Checked", model.getNode("Checked"),'N'));
	 * model.getNode("Checked").addAction(new WfAction("Issue",
	 * model.getNode("Review"),'N')); model.getNode("Review").addAction(new
	 * WfAction("Reviewed", model.getNode("Reviewed"),'N'));
	 * model.getNode("Reviewed").addAction(new WfAction("Issue",
	 * model.getNode("Approve"),'N')); model.getNode("Approve").addAction(new
	 * WfAction("Approve", model.getNode("Approved"),'N'));
	 * model.getNode("Approved").addAction(new WfAction("Complete",
	 * model.getNode("Publish"),'N')); model.getNode("Publish").addAction(new
	 * WfAction("Close", model.getNode("stop"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Revise").addAction(new
	 * WfAction("Release", model.getNode("Approve"),'N'));
	 * modelservice.saveModel(model); return model; }
	 */

	// private static WfModel setupModel1(ModelService modelservice) throws
	// Exception {
	// WfModel model = new WfModel("TestAssign72","Owner",'Y');
	// modelservice.saveModel(model);
	// model.addAttribute(new WfAttribute("x", WfAttributeType.WF_ATTR_NUMBER));
	// model.addAttribute(new WfAttribute("y", WfAttributeType.WF_ATTR_TEXT));
	// model.addAttribute(new WfAttribute("Eb Number",
	// WfAttributeType.WF_ATTR_TEXT));
	// model.addAttribute(new WfAttribute("Target Date",
	// WfAttributeType.WF_ATTR_DATE));
	//
	// modelservice.saveModel(model);
	// /*model.addNode(new WfTask("To Prepare", "U:{CREATOR}","To Prepare"));
	// WfFork wfFork = new WfFork("Fork");
	// model.addNode(wfFork);
	// model.addNode(new WfTask("Produce", "U:{CREATOR}","Produce"));
	// model.addNode(new WfTask("Review", "U:{CREATOR}","Review"));
	// model.addNode(new WfTask("Prepare 1", "U:{CREATOR}","Prepare 1"));
	// model.addNode(new WfTask("Prepare 2", "U:{CREATOR}","Prepare 1"));
	// model.addNode(new WfStop("Stop"));
	// */
	// // testing fork with Decision node
	// model.addNode(new WfTask("To Prepare", "U:{CREATOR}","To Prepare"));
	//
	// WfFork wfFork = new WfFork("Fork");
	// model.addNode(wfFork);
	//
	// model.addNode(new WfDecision("Check1", model.getAttribute("x"),
	// WfDecisionOperator.WF_DECISION_OP_LT, new Long(200)));
	// WfAttribute wfAttribute = new WfAttribute("Target Date",
	// WfAttributeType.WF_ATTR_DATE);
	// WfAssignment assign1 = new
	// WfAssignment("Assign Date",model.getAttribute("Target Date"),new Date());
	// model.addNode(assign1);
	// model.addNode(new WfTask("Review", "RG:Reviewer.HealthSafety","Review"));
	// WfAttribute wfAttribute1 = new WfAttribute("Eb Number",
	// WfAttributeType.WF_ATTR_TEXT);
	// WfAssignment assign2 = new
	// WfAssignment("Assign Text",model.getAttribute("Eb Number"),"Hello");
	// model.addNode(assign2);
	// WfJoin wfJoin = new WfJoin("Join Node",wfFork);
	// model.addNode(wfJoin);
	// model.addNode(new WfStop("Stop"));
	//
	// modelservice.saveModel(model);
	// model.setAsStart("To Prepare");
	// model.setAsEnd("Stop");
	// modelservice.saveModel(model);
	//
	// model.getNode("To Prepare").addAction(new WfAction("fork",
	// model.getNode("Fork"),'N',"Fork"));
	// model.getNode("Fork").addAction(new WfAction("Fork1",
	// model.getNode("Check1"),'N',"Fork1Completed"));
	// model.getNode("Check1").addAction(new WfAction("Yes",
	// model.getNode("Assign Date")));
	// model.getNode("Check1").addAction(new WfAction("No",
	// model.getNode("Review")));
	// model.getNode("Assign Date").addAction(new WfAction("Done",
	// model.getNode("Join Node")));
	// model.getNode("Review").addAction(new WfAction("Reviewed",
	// model.getNode("Join Node")));
	// model.getNode("Fork").addAction(new WfAction("Fork2",
	// model.getNode("Assign Text"),'N',"Fork2Completed"));
	// model.getNode("Assign Text").addAction(new WfAction("Done",
	// model.getNode("Join Node"),'N',"Assigned"));
	// model.getNode("Join Node").addAction(new WfAction("Joined",
	// model.getNode("Stop"),'N',"END"));
	// modelservice.saveModel(model);
	//
	// //Fork for Join
	// /*model.addNode(new WfTask("To Prepare", "U:{CREATOR}","To Prepare"));
	// WfFork wfFork = new WfFork("Fork");
	// model.addNode(wfFork);
	//
	// // WfAttribute wfAttribute = new WfAttribute("Target Date",
	// WfAttributeType.WF_ATTR_DATE);
	// WfAssignment assign1 = new
	// WfAssignment("Assign Date",model.getAttribute("Target Date"),new Date());
	// model.addNode(assign1);
	// WfAssignment assign2 = new
	// WfAssignment("Assign Date1",model.getAttribute("Target Date"),new
	// Date());
	// model.addNode(assign2);
	// WfJoin wfJoin = new WfJoin("Join Node",wfFork);
	// model.addNode(wfJoin);
	// model.addNode(new WfStop("Stop"));
	// System.out.println("Before Model Save");
	// modelservice.saveModel(model);
	// model.setAsStart("To Prepare");
	// model.setAsEnd("Stop");
	// modelservice.saveModel(model);*/
	//
	// System.out.println("start node ...."+model.getStartNode());
	// System.out.println("End node......"+model.getEndNode());
	// System.out.println("Model Created..." + model.getId());
	// /*model.getNode("To Prepare").addAction(new WfAction("fork",
	// model.getNode("Fork"),'N'));
	// model.getNode("Fork").addAction(new WfAction("Fork1",
	// model.getNode("Assign Date"),'N'));
	// model.getNode("Fork").addAction(new WfAction("Fork2",
	// model.getNode("Assign Date1"),'N'));
	// model.getNode("Assign Date").addAction(new WfAction("Done",
	// model.getNode("Join Node"),'N'));
	// model.getNode("Assign Date1").addAction(new WfAction("Done",
	// model.getNode("Join Node"),'N'));
	// model.getNode("Join Node").addAction(new WfAction("Joined",
	// model.getNode("Stop"),'N'));*/
	//
	// return model;
	// }
	/*
	 * model.addNode(new WfTask("To Prepare", "U:{CREATOR}","To Prepare"));
	 * System.out.println("-------Before Assignment------"); WfAttribute
	 * wfAttribute = new WfAttribute("WelcomeMsg",
	 * WfAttributeType.WF_ATTR_DATE); WfAssignment assign = new
	 * WfAssignment("assign",model.getAttribute("Target Date"),"",new Date());
	 * model.addNode(assign);
	 * System.out.println("-------After Assignment------"); model.addNode(new
	 * WfStop("stop")); System.out.println("-------Before Save Model------");
	 * modelservice.saveModel(model); model.setAsStart("To Prepare");
	 * model.setAsEnd("stop"); modelservice.saveModel(model);
	 */
	private static void testJoin(ModelService ms, CaseService cs) throws Exception {

		System.out.println("------------------------");
		WfUserInfo userInfo1 = UserInfoRepo.UI1;
		WfUserInfo userInfo2 = UserInfoRepo.UI2;

		WfStep mystep = ms.createCase("TestAssign72", userInfo1);
		long stepId = mystep.getId();
		System.out.println("--------------Step Id----------" + stepId);
		WfCase wfcase = mystep.getOwningCase();
		wfcase.setAttribute("x", new Long(199));
		wfcase.setAttribute("y", "jim");
		cs.saveCase(wfcase);
		cs.performAction(stepId, "fork", "To Split", userInfo1);
		// cs.performAction(stepId, "Yes", "if yes", userInfo1);
		// cs.performAction(stepId, "No", "if no", userInfo1);
		// cs.performAction(stepId, "Reviewed", "Review Done", userInfo1);

		// cs.performAction(stepId, "Fork1", "Fork1", userInfo1);
		// cs.performAction(stepId, "Fork2", "Fork2", userInfo1);
		// cs.performAction(stepId, "Done", "Value assigned", userInfo1);

		// cs.performAction(stepId, "Reviewed", "Doc Reviewed", userInfo1);
		// cs.performAction(stepId, "Joined", "Nodes Joined", userInfo1);

	}

	private static void testFork(ModelService ms, CaseService cs) throws Exception {

		System.out.println("------------------------");
		WfUserInfo userInfo1 = UserInfoRepo.UI1;
		WfUserInfo userInfo2 = UserInfoRepo.UI2;

		WfStep mystep = ms.createCase("TestAssign49", userInfo1);
		long stepId = mystep.getId();
		System.out.println("--------------Step Id----------" + stepId);
		WfCase wfcase = mystep.getOwningCase();
		wfcase.setAttribute("x", new Long(299));
		wfcase.setAttribute("y", "jim");
		cs.saveCase(wfcase);
		System.out.println("Wfcase id...." + wfcase.getId());

		cs.performAction(stepId, "fork", "To Split", userInfo1);
		cs.performAction(stepId, "Fork1", "Decision node", userInfo1);
		cs.performAction(stepId, "Yes", "if yes", userInfo1);
		cs.performAction(stepId, "No", "if no", userInfo2);
		cs.performAction(stepId, "Fork2", "Text Assigned", userInfo2);
		cs.performAction(stepId, "Fork3", "Review Node", userInfo1);
		cs.performAction(stepId, "Reviewed", "Review Done", userInfo1);

		// cs.performAction(stepId+3, "Approve", "actionReason", userInfo6);
		// cs.performAction(stepId+4, "Approve", "actionReason", userInfo1);
		// cs.performAction(stepId+5, "Approve", "actionReason", userInfo2);

	}

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("Pudding Mill","Owner",'Y');
	 * modelservice.saveModel(model); //model.addAttribute(new
	 * WfAttribute("Title"
	 * ,WfAttributeType.WF_ATTR_TEXT,"Canary Wharf Spider Crane",false));
	 * model.addAttribute(new
	 * WfAttribute("Category",WfAttributeType.WF_ATTR_TEXT
	 * ,"Contractual",false)); model.addAttribute(new
	 * WfAttribute("Site",WfAttributeType.WF_ATTR_TEXT,"Pudding Mill",false));
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); //model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT,"C12.003",true));
	 * model.addAttribute(new WfAttribute("MDL Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("TargetDate", WfAttributeType.WF_ATTR_DATE));
	 * model.addAttribute(new WfAttribute("Reference Link",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJVNew/cm:"
	 * +ISO9075.encode("11.0 Construction Process"),false));
	 * //model.addAttribute(new WfAttribute("Template",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder"
	 * )+"/cm:MethodTemplateDsjv.doc",false));
	 * //model.addDocTypeAttribute("Limmo",
	 * "/app:company_home/app:user_homes/cm:DSJVNew/cm:"
	 * +ISO9075.encode("2.0 Project Controls")+"/cm:MethodTemplateDsjv.doc");
	 * model.addDocTypeAttribute("Blank Method Statement",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("2.0 Project Controls")+"/cm:"
	 * +ISO9075.encode("Blank Method Statement Template.doc"));
	 * //model.addDocTypeAttribute("Canary Wharf Station Test",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:"+ISO9075.encode(
	 * "Canary Wharf Method Statement Spider Crane Template.doc"));
	 * modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Check", "R:Checker")); model.addNode(new WfTask("Review",
	 * "R:Reviewer")); model.addNode(new WfTask("Publish", "R:Publisher/DMT"));
	 * model.addNode(new WfTask("Checked","U:{CREATOR}")); model.addNode(new
	 * WfTask("Reviewed","U:{CREATOR}")); model.addNode(new WfTask("Revise",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Approve","R:DSJV Approver"));
	 * model.addNode(new WfTask("Approved", "U:{CREATOR}"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Release",
	 * model.getNode("Check"),'N')); model.getNode("Check").addAction(new
	 * WfAction("Checked", model.getNode("Checked"),'N'));
	 * model.getNode("Checked").addAction(new WfAction("Issue",
	 * model.getNode("Review"),'N')); model.getNode("Review").addAction(new
	 * WfAction("Reviewed", model.getNode("Reviewed"),'N'));
	 * model.getNode("Reviewed").addAction(new WfAction("Issue",
	 * model.getNode("Approve"),'N')); model.getNode("Approve").addAction(new
	 * WfAction("Approve", model.getNode("Approved"),'N'));
	 * model.getNode("Approved").addAction(new WfAction("Complete",
	 * model.getNode("Publish"),'N')); model.getNode("Publish").addAction(new
	 * WfAction("Close", model.getNode("stop"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Revise").addAction(new
	 * WfAction("Release", model.getNode("Approve"),'N'));
	 * modelservice.saveModel(model); return model; }
	 */

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("Limmo","Owner",'Y');
	 * modelservice.saveModel(model); model.addAttribute(new
	 * WfAttribute("Title",WfAttributeType.WF_ATTR_TEXT,"Limmo",false));
	 * model.addAttribute(new
	 * WfAttribute("Category",WfAttributeType.WF_ATTR_TEXT
	 * ,"Contractual",false)); model.addAttribute(new
	 * WfAttribute("Site",WfAttributeType.WF_ATTR_TEXT,"Limmo",false));
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); // model.addAttribute(new
	 * WfAttribute("MDL Number", WfAttributeType.WF_ATTR_TEXT,"C12.003",true));
	 * model.addAttribute(new WfAttribute("MDL Number",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("TargetDate", WfAttributeType.WF_ATTR_DATE));
	 * model.addAttribute(new WfAttribute("Reference Link",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJVNew",false)); //
	 * model.addAttribute(new WfAttribute("Template",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder"
	 * )+"/cm:MethodTemplateDsjv.doc",false));
	 * //model.addDocTypeAttribute("Limmo",
	 * "/app:company_home/app:user_homes/cm:DSJVNew/cm:"
	 * +ISO9075.encode("2.0 Project Controls")+"/cm:MethodTemplateDsjv.doc");
	 * model.addDocTypeAttribute("Limmo",
	 * "/app:company_home/app:user_homes/cm:DSJVNew/cm:"
	 * +ISO9075.encode("2.0 Project Controls")+"/cm:"
	 * +ISO9075.encode("Limmo Method Statement Template.doc"));
	 * //model.addDocTypeAttribute("Canary Wharf Station Test",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:"+ISO9075.encode(
	 * "Canary Wharf Method Statement Spider Crane Template.doc"));
	 * modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Check", "R:Checker")); model.addNode(new WfTask("Review",
	 * "R:Reviewer")); model.addNode(new WfTask("Publish", "R:Publisher/DMT"));
	 * model.addNode(new WfTask("Checked","U:{CREATOR}")); model.addNode(new
	 * WfTask("Reviewed","U:{CREATOR}")); model.addNode(new WfTask("Revise",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Approve","R:DSJV Approver"));
	 * model.addNode(new WfTask("Approved", "U:{CREATOR}"));
	 * 
	 * 
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Release",
	 * model.getNode("Check"),'N')); model.getNode("Check").addAction(new
	 * WfAction("Checked", model.getNode("Checked"),'N'));
	 * model.getNode("Checked").addAction(new WfAction("Issue",
	 * model.getNode("Review"),'N')); model.getNode("Review").addAction(new
	 * WfAction("Reviewed", model.getNode("Reviewed"),'N'));
	 * model.getNode("Reviewed").addAction(new WfAction("Issue",
	 * model.getNode("Approve"),'N')); model.getNode("Approve").addAction(new
	 * WfAction("Approve", model.getNode("Approved"),'N'));
	 * model.getNode("Approved").addAction(new WfAction("Complete",
	 * model.getNode("Publish"),'N')); model.getNode("Publish").addAction(new
	 * WfAction("Close", model.getNode("stop"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Revise").addAction(new
	 * WfAction("Release", model.getNode("Approve"),'N'));
	 * modelservice.saveModel(model); return model; }
	 */

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new
	 * WfModel("Method Statement Workflow Demo","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("MDLNumber", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("TargetDate",
	 * WfAttributeType.WF_ATTR_DATE)); model.addAttribute(new
	 * WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV"
	 * ,false)); // model.addAttribute(new WfAttribute("Template",
	 * WfAttributeType
	 * .WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode
	 * ("18.0 Common Folder")+"/cm:MethodTemplateDsjv.doc",false));
	 * model.addDocTypeAttribute("Blank Method Statement Test",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:MethodTemplateDsjv.doc");
	 * model.addDocTypeAttribute("Canary Wharf Station Test",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:"+ISO9075.encode(
	 * "Canary Wharf Method Statement Spider Crane Template.doc"));
	 * modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Check", "R:Checker")); model.addNode(new WfTask("Review",
	 * "R:Reviewer")); model.addNode(new WfTask("Checked","U:{CREATOR}"));
	 * model.addNode(new WfTask("Reviewed","U:{CREATOR}")); model.addNode(new
	 * WfTask("Revise", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Approve","R:DSJV Approver")); model.addNode(new
	 * WfTask("Approved", "U:{CREATOR}"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Release",
	 * model.getNode("Check"),'Y')); model.getNode("Check").addAction(new
	 * WfAction("Checked", model.getNode("Checked"),'Y'));
	 * model.getNode("Checked").addAction(new WfAction("Issue",
	 * model.getNode("Review"),'Y')); model.getNode("Review").addAction(new
	 * WfAction("Reviewed", model.getNode("Reviewed"),'Y'));
	 * model.getNode("Reviewed").addAction(new WfAction("Complete",
	 * model.getNode("Approve"),'Y')); model.getNode("Approve").addAction(new
	 * WfAction("Approve", model.getNode("Approved"),'Y'));
	 * model.getNode("Approved").addAction(new WfAction("Close",
	 * model.getNode("stop"),'Y')); model.getNode("Approve").addAction(new
	 * WfAction("Reject", model.getNode("Revise"),'Y'));
	 * model.getNode("Revise").addAction(new WfAction("Release",
	 * model.getNode("Approve"),'Y')); modelservice.saveModel(model); return
	 * model; }
	 */

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new
	 * WfModel("New Method Statement Workflow","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV"
	 * ,false)); // model.addAttribute(new WfAttribute("Template",
	 * WfAttributeType
	 * .WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode
	 * ("18.0 Common Folder")+"/cm:MethodTemplateDsjv.doc",false));
	 * model.addDocTypeAttribute("Blank Method Statement",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:MethodTemplateDsjv.doc");
	 * model.addDocTypeAttribute("Canary Wharf Station",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:"+ISO9075.encode(
	 * "Canary Wharf Method Statement Spider Crane Template.doc"));
	 * modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Check", "R:Checker")); model.addNode(new WfTask("Review",
	 * "R:Reviewer")); model.addNode(new
	 * WfTask("Finalise Check","U:{CREATOR}")); model.addNode(new
	 * WfTask("Finalise Review","U:{CREATOR}")); model.addNode(new
	 * WfTask("Revise", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Approve","R:DSJV Approver")); model.addNode(new
	 * WfTask("Approved", "U:{CREATOR}"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Release",
	 * model.getNode("Check"),'Y')); model.getNode("Check").addAction(new
	 * WfAction("Checked", model.getNode("Finalise Check"),'Y'));
	 * model.getNode("Finalise Check").addAction(new WfAction("Issue",
	 * model.getNode("Review"),'Y')); model.getNode("Review").addAction(new
	 * WfAction("Reviewed", model.getNode("Finalise Review"),'Y'));
	 * model.getNode("Finalise Review").addAction(new WfAction("Complete",
	 * model.getNode("Approve"),'Y')); model.getNode("Approve").addAction(new
	 * WfAction("Approve", model.getNode("Approved"),'Y'));
	 * model.getNode("Approved").addAction(new WfAction("Close",
	 * model.getNode("stop"),'Y')); model.getNode("Approve").addAction(new
	 * WfAction("Reject", model.getNode("Revise"),'Y'));
	 * model.getNode("Revise").addAction(new WfAction("Release",
	 * model.getNode("Approve"),'Y')); modelservice.saveModel(model); return
	 * model; }
	 */

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new
	 * WfModel("New Method Statement Workflow2","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV"
	 * ,false)); // model.addAttribute(new WfAttribute("Template",
	 * WfAttributeType
	 * .WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode
	 * ("18.0 Common Folder")+"/cm:MethodTemplateDsjv.doc",false));
	 * model.addDocTypeAttribute("Blank Method Statement",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:MethodTemplateDsjv.doc");
	 * model.addDocTypeAttribute("Canary Wharf Station",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:"+ISO9075.encode(
	 * "Canary Wharf Method Statement Spider Crane Template.doc"));
	 * modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Check", "R:Checker")); model.addNode(new WfTask("Review",
	 * "R:Reviewer")); model.addNode(new
	 * WfTask("Finalise Check","U:{CREATOR}")); model.addNode(new
	 * WfTask("Finalise Review","U:{CREATOR}")); model.addNode(new
	 * WfTask("Revise", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Approve","R:DSJV Approver")); model.addNode(new
	 * WfTask("Approved", "U:{CREATOR}"));
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model); modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("Approved");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Release",
	 * model.getNode("Check"),'Y')); model.getNode("Check").addAction(new
	 * WfAction("Checked", model.getNode("Finalize Check"),'Y'));
	 * model.getNode("Finalize Check").addAction(new WfAction("Issue",
	 * model.getNode("Review"),'Y')); model.getNode("Review").addAction(new
	 * WfAction("Reviewed", model.getNode("Finalize Review"),'Y'));
	 * model.getNode("Finalize Review").addAction(new WfAction("Complete",
	 * model.getNode("Approve"),'Y')); model.getNode("Approve").addAction(new
	 * WfAction("Approve", model.getNode("Approved"),'Y'));
	 * model.getNode("Approve").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Revise").addAction(new
	 * WfAction("Release", model.getNode("Approve"),'Y'));
	 * modelservice.saveModel(model); return model; }
	 */

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new
	 * WfModel("DSJV Approved Method Statement","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV"
	 * ,false)); // model.addAttribute(new WfAttribute("Template",
	 * WfAttributeType
	 * .WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode
	 * ("18.0 Common Folder")+"/cm:MethodTemplateDsjv.doc",false));
	 * model.addDocTypeAttribute("Blank Method Statement",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:MethodTemplateDsjv.doc");
	 * model.addDocTypeAttribute("Canary Wharf Station",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:"+ISO9075.encode(
	 * "Canary Wharf Method Statement Spider Crane Template.doc"));
	 * modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Revise", "U:{CREATOR}"));
	 * 
	 * model.addNode(new WfTask("Publish", "R:Publisher/DMT"));
	 * 
	 * WfMail wfmail = new
	 * WfMail("mailNode","DSJV Approved Method Statement Workflow Process"
	 * ,"Document Published"); model.addNode(wfmail);
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model); System.out.println("----------->" +
	 * wfmail.getId()); wfmail.addToRecipients("U:{CREATOR}");
	 * wfmail.addToRecipients("R:Publisher/DMT"); modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("stop");
	 * modelservice.saveModel(model);
	 * 
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Release",
	 * model.getNode("Publish"),'Y')); model.getNode("Publish").addAction(new
	 * WfAction("Publish", model.getNode("mailNode"),'Y'));
	 * model.getNode("Publish").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Revise").addAction(new
	 * WfAction("Release", model.getNode("Publish"),'Y'));
	 * model.getNode("mailNode").addAction(new WfAction("Mailed",
	 * model.getNode("stop"),'Y')); modelservice.saveModel(model); return model;
	 * }
	 */

	// Viswa given Model

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("Test1","Reviewer",'Y');
	 * modelservice.saveModel(model); model.addAttribute(new
	 * WfAttribute("EbNumber", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:JMR",false));
	 * model.addAttribute(new WfAttribute("Template",
	 * WfAttributeType.WF_ATTR_TEXT,
	 * "/app:company_home/app:user_homes/cm:JMR/cm:MethodStatement/cm:MethodTemplate.docx"
	 * ,false)); modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Review", "R:Reviewer")); model.addNode(new
	 * WfTask("Finalise","U:{CREATOR}")); model.addNode(new WfTask("Revise",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Publish", "R:Publisher"));
	 * model.addNode(new WfTask("Check", "R:Checker")); model.addNode(new
	 * WfTask("Approve", "R:Approver")); WfMail wfmail = new
	 * WfMail("mailNode","Test","this is testing mail"); model.addNode(wfmail);
	 * 
	 * model.addNode(new WfCancel("Cancel")); model.addNode(new WfStop("stop"));
	 * modelservice.saveModel(model); System.out.println("----------->" +
	 * wfmail.getId()); wfmail.addToRecipients("U:{CREATOR}");
	 * wfmail.addToRecipients("R:Publisher"); modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("Publish");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Release",
	 * model.getNode("Review"),'Y')); model.getNode("Review").addAction(new
	 * WfAction("Complete", model.getNode("Finalise"),'N'));
	 * model.getNode("Finalise").addAction(new WfAction("Issue",
	 * model.getNode("Check"),'Y')); model.getNode("Check").addAction(new
	 * WfAction("Pass", model.getNode("Approve"),'Y'));
	 * model.getNode("Check").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'N')); model.getNode("Approve").addAction(new
	 * WfAction("Pass", model.getNode("Publish"),'Y'));
	 * model.getNode("Approve").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'N')); model.getNode("Revise").addAction(new
	 * WfAction("Release", model.getNode("stop"),'Y'));
	 * 
	 * model.getNode("Publish").addAction(new WfAction("Pass",
	 * model.getNode("mailNode"),'Y')); model.getNode("mailNode").addAction(new
	 * WfAction("Mailed", model.getNode("stop"),'Y'));
	 * 
	 * modelservice.saveModel(model);
	 * 
	 * return model; }
	 */

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new
	 * WfModel("Quality Workflow New7","Owner",'Y');
	 * modelservice.saveModel(model); model.addAttribute(new
	 * WfAttribute("EbNumber", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("TargetDate",
	 * WfAttributeType.WF_ATTR_DATE)); model.addAttribute(new
	 * WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("04.0 Quality"),false)); //model.addAttribute(new
	 * WfAttribute("Template",
	 * WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +
	 * ISO9075.encode("18.0 Common Folder")+"/cm:MethodTemplateDsjv.doc",false))
	 * ; model.addDocTypeAttribute("Meetings",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:test6.xls");
	 * model.addDocTypeAttribute("Audits",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:discipline.txt");
	 * //model.addDocTypeAttribute("Plans",
	 * "/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder")+"/cm:discipline.txt");
	 * 
	 * 
	 * modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Revise","U:{CREATOR}")); model.addNode(new WfTask("Check",
	 * "R:Checker")); model.addNode(new WfTask("Approve", "R:DSJV Approver"));
	 * model.addNode(new WfTask("Publish", "R:Publisher/DMT"));
	 * model.addNode(new WfTask("Published", "R:Publisher/DMT"));
	 * 
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("Published");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Issue",
	 * model.getNode("Check"),'N')); model.getNode("Check").addAction(new
	 * WfAction("Approve", model.getNode("Approve"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Approve",
	 * model.getNode("Publish"),'N')); model.getNode("Publish").addAction(new
	 * WfAction("Approve", model.getNode("Published"),'N'));
	 * model.getNode("Publish").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Revise").addAction(new
	 * WfAction("Issue", model.getNode("Check"),'N'));
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new
	 * WfModel("Health and Safety Workflow","Owner",'Y');
	 * modelservice.saveModel(model); model.addAttribute(new
	 * WfAttribute("EbNumber", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("TargetDate",
	 * WfAttributeType.WF_ATTR_DATE)); model.addAttribute(new
	 * WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("03.0 Health & Safety"),false)); model.addAttribute(new
	 * WfAttribute("Template",
	 * WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +
	 * ISO9075.encode("18.0 Common Folder")+"/cm:MethodTemplateDsjv.doc",false))
	 * ; modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Published","U:{CREATOR}")); model.addNode(new WfTask("Check",
	 * "R:Checker")); model.addNode(new WfTask("Approve", "R:DSJV Approver"));
	 * model.addNode(new WfTask("Publish", "R:Publisher/DMT"));
	 * 
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("Published");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Issue",
	 * model.getNode("Check"),'N')); model.getNode("Check").addAction(new
	 * WfAction("Approve", model.getNode("Approve"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Approve",
	 * model.getNode("Publish"),'N')); model.getNode("Publish").addAction(new
	 * WfAction("Approve", model.getNode("Published"),'N'));
	 * modelservice.saveModel(model); return model; }
	 */

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new
	 * WfModel("Environmental Workflow","Owner",'Y');
	 * modelservice.saveModel(model); model.addAttribute(new
	 * WfAttribute("EbNumber", WfAttributeType.WF_ATTR_TEXT));
	 * model.addAttribute(new WfAttribute("TargetDate",
	 * WfAttributeType.WF_ATTR_DATE)); model.addAttribute(new
	 * WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("05.0 Environmental"),false)); model.addAttribute(new
	 * WfAttribute("Template",
	 * WfAttributeType.WF_ATTR_TEXT,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +
	 * ISO9075.encode("18.0 Common Folder")+"/cm:MethodTemplateDsjv.doc",false))
	 * ; modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Revise","U:{CREATOR}")); model.addNode(new WfTask("Published",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Review", "R:Reviewer"));
	 * model.addNode(new WfTask("Check", "R:Checker")); model.addNode(new
	 * WfTask("Check", "R:Checker")); model.addNode(new
	 * WfTask("Approve","R:DSJV Approver")); model.addNode(new WfTask("Publish",
	 * "R:Publisher/DMT"));
	 * 
	 * modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("Published");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Issue",
	 * model.getNode("Review"),'N')); model.getNode("Review").addAction(new
	 * WfAction("Approve", model.getNode("Check"),'N'));
	 * model.getNode("Review").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Check").addAction(new
	 * WfAction("Approve", model.getNode("Approve"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Approve",
	 * model.getNode("Publish"),'N')); model.getNode("Publish").addAction(new
	 * WfAction("Approve", model.getNode("Published"),'N'));
	 * model.getNode("Publish").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Revise").addAction(new
	 * WfAction("Issue", model.getNode("Review"),'N'));
	 * 
	 * 
	 * modelservice.saveModel(model); return model; }
	 */

	/*
	 * private static WfModel setupModel1(ModelService modelservice) throws
	 * Exception { WfModel model = new WfModel("MethodStatement","Owner",'Y');
	 * modelservice.saveModel(model);
	 * 
	 * model.addAttribute(new WfAttribute("EbNumber",
	 * WfAttributeType.WF_ATTR_TEXT)); model.addAttribute(new
	 * WfAttribute("TargetDate", WfAttributeType.WF_ATTR_DATE));
	 * model.addAttribute(new WfAttribute("FilePath",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV",false));
	 * model.addAttribute(new WfAttribute("Template",
	 * WfAttributeType.WF_ATTR_TEXT
	 * ,"/app:company_home/app:user_homes/cm:DSJV/cm:"
	 * +ISO9075.encode("18.0 Common Folder"
	 * )+"/cm:MethodTemplateDsjv.doc",false)); modelservice.saveModel(model);
	 * 
	 * model.addNode(new WfTask("Produce", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Review", "R:Reviewer")); model.addNode(new
	 * WfTask("Finalise","U:{CREATOR}")); model.addNode(new WfTask("Revise",
	 * "U:{CREATOR}")); model.addNode(new WfTask("Publish", "R:Publisher/DMT"));
	 * model.addNode(new WfTask("Check", "R:Checker")); model.addNode(new
	 * WfTask("Approve","R:DSJV Approver")); modelservice.saveModel(model);
	 * 
	 * model.setAsStart("Produce"); model.setAsEnd("Publish");
	 * modelservice.saveModel(model);
	 * 
	 * model.getNode("Produce").addAction(new WfAction("Release",
	 * model.getNode("Review"),'N')); model.getNode("Review").addAction(new
	 * WfAction("Complete", model.getNode("Finalise"),'N'));
	 * model.getNode("Finalise").addAction(new WfAction("Issue",
	 * model.getNode("Check"),'N')); model.getNode("Check").addAction(new
	 * WfAction("Approve", model.getNode("Approve"),'N'));
	 * model.getNode("Check").addAction(new WfAction("Reject",
	 * model.getNode("Revise"),'Y')); model.getNode("Approve").addAction(new
	 * WfAction("Approve", model.getNode("Publish"),'N'));
	 * model.getNode("Approve").addAction(new WfAction("Reject",
	 * model.getNode("Check"),'Y')); model.getNode("Revise").addAction(new
	 * WfAction("Release", model.getNode("Review"),'N'));
	 * modelservice.saveModel(model);
	 * 
	 * return model; }
	 */

	/*
	 * private static WfModel setupModel2(ModelService ms) throws Exception {
	 * WfModel model = new WfModel("Test4","Assigner",'Y'); ms.saveModel(model);
	 * 
	 * //model.addNode(new WfTask("CreateDoc", "U:{CREATOR}"));
	 * model.addNode(new WfTask("CreateDoc", "U:{CREATOR}")); model.addNode(new
	 * WfTask("Review1", "R:DepManager")); model.addNode(new WfTask("Review2",
	 * "R:CFO")); model.addNode(new WfTask("Publish", "U:CFO"));
	 * ms.saveModel(model);
	 * 
	 * model.setAsStart("CreateDoc"); model.setAsEnd("Publish");
	 * ms.saveModel(model);
	 * 
	 * model.getNode("CreateDoc").addAction( new WfAction("Release",
	 * model.getNode("Review1"))); model.getNode("Review1").addAction( new
	 * WfAction("Approve", model.getNode("Review2")));
	 * model.getNode("Review1").addAction( new WfAction("Reject",
	 * model.getNode("CreateDoc"))); model.getNode("Review2").addAction( new
	 * WfAction("Approve", model.getNode("Publish")));
	 * model.getNode("Review2").addAction( new WfAction("Reject",
	 * model.getNode("CreateDoc"))); model.getNode("Publish").addAction(new
	 * WfAction("Close", null)); ms.saveModel(model); return model; }
	 */
	private static void itest1(ModelService ms, CaseService cs) throws Exception {

		System.out.println("------------------------");
		WfUserInfo userInfo1 = UserInfoRepo.UI1;
		WfUserInfo userInfo2 = UserInfoRepo.UI2;
		WfUserInfo userInfo3 = UserInfoRepo.UI3;
		WfUserInfo userInfo4 = UserInfoRepo.UI4;
		WfUserInfo userInfo5 = UserInfoRepo.UI5;
		WfUserInfo userInfo6 = UserInfoRepo.UI6;

		WfStep mystep = ms.createCase("TestAssign33", userInfo1);
		long stepId = mystep.getId();
		System.out.println("------------------------");
		WfCase wfcase = mystep.getOwningCase();
		System.out.println("Wfcase id...." + wfcase.getId());

		cs.performAction(stepId, "fork", "actionReason", userInfo2);

		// cs.performAction(stepId+2, "Issue", "actionReason", userInfo5);
		// cs.performAction(stepId+3, "Approve", "actionReason", userInfo6);
		// cs.performAction(stepId+4, "Approve", "actionReason", userInfo1);
		// cs.performAction(stepId+5, "Approve", "actionReason", userInfo2);

	}

	private static void itest2(CaseService cs) throws Exception {

		// cs.performAction(3, "Issue", "kanaga");
		// cs.performAction(2, "Complete", "viswa");
	}

	private static void itest3(ModelService ms) {

		WfUserInfo userInfo = UserInfoRepo.UI6;
		List<WfModel> models = ms.canCreateCase(userInfo);
		for (WfModel m : models) {
			System.out.println(m.getName());
		}

	}
}
