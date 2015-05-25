/*package com.ardhika.wfar.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ardhika.wfar.TaskSummary;
import com.ardhika.wfar.WfAction;
import com.ardhika.wfar.WfCancel;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfModel;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.WfStop;
import com.ardhika.wfar.WfTask;
import com.ardhika.wfar.WfUserInfo;
import com.ardhika.wfar.dao.CaseDao;
import com.ardhika.wfar.service.CaseService;
import com.ardhika.wfar.service.ModelService;
public class Test1 {

	public static void main(String[] args) throws Exception {
//		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//		ModelService modelservice=context.getBean("modelService",ModelService.class);
//		CaseService caseservice=context.getBean("caseService", CaseService.class);
		ApplicationContext context = new ClassPathXmlApplicationContext("root-context.xml");
		ModelService modelservice=context.getBean("modelService",ModelService.class);
		CaseService caseservice=context.getBean("caseService", CaseService.class);		
//		itest1(modelservice);
		itest2(modelservice);
		itest3(modelservice,caseservice);
		itest4(modelservice,caseservice);
		itest5(modelservice,caseservice);
//		itest6(modelservice,caseservice);
//		itest7(modelservice,caseservice);
//		itest8(modelservice, caseservice);
//		itest9(modelservice,caseservice);
//		itest10(modelservice,caseservice);
	}

	private static void itest1(ModelService modelservice) throws Exception {
		WfModel model = setupModel1(modelservice);
		modelservice.saveModel(model);
	}
	
//	private static void itest2(ModelService modelservice) throws Exception {
//		modelservice.listModels();
//		WfModel model = modelservice.findModelByName("Test1");
//		Set<WfAction> action = model.getNode("CreateDoc").getActions();
//		for(WfAction a : action)
//		System.out.println(a.getName());
//	}
	
	private static WfModel setupModel1(ModelService ms) throws Exception {
		WfModel model = new WfModel("Test2");
		ms.saveModel(model);

		//model.addNode(new WfTask("CreateDoc", "U:{CREATOR}"));
		model.addNode(new WfTask("CreateDoc", "R:Writer"));
		model.addNode(new WfTask("Review1", "R:DepManager"));
		model.addNode(new WfTask("Review2", "R:CFO"));
		model.addNode(new WfTask("Publish", "U:CFO"));
		model.addNode(new WfCancel("Cancel"));
		model.addNode(new WfStop("stop"));
		ms.saveModel(model);

		model.setAsStart("CreateDoc");
		model.setAsEnd("Publish");
		ms.saveModel(model);

		model.getNode("CreateDoc").addAction(
				new WfAction("Release", model.getNode("Review1")));
		model.getNode("Review1").addAction(
				new WfAction("Approve", model.getNode("Review2")));
		model.getNode("Review1").addAction(
				new WfAction("Reject", model.getNode("CreateDoc")));
		model.getNode("Review2").addAction(
				new WfAction("Approve", model.getNode("Publish")));
		model.getNode("Review2").addAction(
				new WfAction("Reject", model.getNode("stop")));
		model.getNode("Publish").addAction(new WfAction("Close", null));
		ms.saveModel(model);
		return model;
	}
	
	private static void itest2(ModelService ms) throws Exception {
		System.out.println("------------------------");
		WfUserInfo userInfo = UserInfoRepo.UI2;
		WfStep mystep = ms.createCase("Test2",userInfo);
		System.out.println("------------------------");
	}
	
	private static void itest3(ModelService ms,CaseService cs)
	{
		ms.listModels();
		WfUserInfo userInfo = UserInfoRepo.UI2;
		List<WfStep> steps = cs.findAssignedSteps(userInfo);
		System.out.println("------------------------");
		for(WfStep s : steps)
		{
			System.out.println(s);
			for(String a : s.getActionNames() )
			System.out.println(a);
		}
		System.out.println("------------------------");
		steps = cs.findAssignedStepsForTask(userInfo, "CreateDoc","Test2");
		for(WfStep s : steps)
			System.out.println(s);
	}
	private static void itest4(ModelService ms,CaseService cs) throws Exception
	{
		ms.listModels();
		WfUserInfo userInfo = UserInfoRepo.UI1;
		List<WfStep> steps = cs.findAssignedSteps(userInfo);
		WfUserInfo userInfo2 = UserInfoRepo.UI2;
//		if(steps.size()>0)
//		          cs.performAction(steps.get(0).getId(), "Release", "U:viswa");
		steps = cs.findAssignedSteps(userInfo2);
		System.out.println("------------------------");
		for(WfStep s : steps)
		{
			System.out.println(s);
			for(String a : s.getActionNames() )
			System.out.println(a);
		}	
	}
	
	private static void itest5(ModelService ms,CaseService cs) throws Exception
	{
		ms.listModels();
		WfUserInfo userInfo = UserInfoRepo.UI2;
		List<WfStep> steps = cs.findAssignedSteps(userInfo);
		WfUserInfo userInfo2 = UserInfoRepo.UI3;
		if(steps.size()>0)
//			cs.performAction(steps.get(0).getId(), "Approve", "U:naran");
		steps = cs.findAssignedSteps(userInfo2);
		System.out.println("------------------------");
		for(WfStep s : steps)
		{
			System.out.println(s);
			for(String a : s.getActionNames() )
			System.out.println(a);
		}	
	}
	
//	private static void itest6(ModelService ms,CaseService cs) throws Exception
//	{
//		ms.listModels();
//		WfUserInfo userInfo = UserInfoRepo.UI3;
//		List<WfStep> steps = cs.findAssignedSteps(userInfo);
//		if(steps.size()>0)
//			cs.performAction(steps.get(0).getId(), "Approve", "U:abc");
//		WfUserInfo userInfo2 = UserInfoRepo.UI4;
//		steps = cs.findAssignedSteps(userInfo2);
//		System.out.println("------------------------");
//		for(WfStep s : steps)
//		{
//			System.out.println(s);
//			for(String a : s.getActionNames() )
//			System.out.println(a);
//		}	
//	}
	private static void itest7(ModelService ms,CaseService cs) throws Exception
	{
		ms.listModels();
		WfUserInfo userInfo = UserInfoRepo.UI4;
		List<WfStep> steps = cs.findAssignedSteps(userInfo);
//		if(steps.size()>0)
//			cs.performAction(steps.get(0).getId(), "Close", "U:xyz");
		for(WfStep s : steps)
		{
			System.out.println( s.getId());
		}	
	}
	private static void itest8(ModelService ms,CaseService cs) throws Exception
	{
		ms.listModels();
		WfUserInfo userInfo = UserInfoRepo.UI2;
		List<TaskSummary> tasks = cs.findTaskSummary("Test1", userInfo);
		for(TaskSummary s : tasks)
		{
			System.out.println( s.getTaskName() + " -> " + s.getTaskCount());
		}	
	}
	private static void itest9(ModelService ms,CaseService cs)
	{
		ms.listModels();
		WfUserInfo userInfo = UserInfoRepo.UI2;
		System.out.println("------------------------");
		List<WfStep> steps = cs.findAssignedStepsForCase(userInfo,9 );
		for(WfStep s : steps)
			System.out.println(s);
	}
	private static void itest10(ModelService ms,CaseService cs)
	{
		ArrayList<Long> caseIds = new ArrayList();
		caseIds.add(new Long(1));
		caseIds.add(new Long(2));
		
		List<WfCase> cases = cs.getCases(caseIds);
		for(WfCase c : cases)
		{
			System.out.println("List of Cases ........." +c.getStatus() );
		}
		
	}
}
 */

