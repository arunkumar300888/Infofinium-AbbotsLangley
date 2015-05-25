package com.ardhika.wfar.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ardhika.wfar.TaskSummary;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfCaseStatus;
import com.ardhika.wfar.WfNode;
import com.ardhika.wfar.WfNodeType;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.WfStepStatus;
import com.ardhika.wfar.WfTask;
import com.ardhika.wfar.WfUserInfo;
import com.ardhika.wfar.dao.CaseDao;
import com.ardhika.wfar.service.CaseService;
import uk.co.jmr.sdp.dao.CaseUserFormsDao;
import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.service.CompanyUserService;
import uk.co.jmr.sdp.service.DocumentService;
import uk.co.jmr.sdp.service.DtAttributeValueService;
import uk.co.jmr.sdp.service.FormService;
import uk.co.jmr.sdp.service.UserService;
import uk.co.jmr.webforms.db.pojo.UserForms;
import uk.co.jmr.webforms.db.service.UserFormsService;

@Service("caseService")
public class CaseServiceImpl implements CaseService {
	@Autowired
	private CaseDao caseDao;
	@Autowired
	private CaseUserFormsDao caseUserFormsDao;
	@Autowired
	private FormService formService;
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyUserService companyUserService;
	@Autowired
	private DtAttributeValueService dtAttributeValueService;
	@Autowired
	private UserFormsService userFormsService;
	
	//For Send Email
	@Autowired
	private DocumentService documentService;

	@Override
	public WfCase findCaseById(long id) {

		return this.caseDao.findCaseById(id);
	}

	@Override
	public void saveCase(WfCase wfcase) {

		this.caseDao.saveCase(wfcase);
	}

	@Override
	public List<WfStep> findAssignedSteps(WfUserInfo userInfo) {

		List<WfStep> steps = this.caseDao.findAssignedSteps(userInfo);
		return filterAssignedTasks(steps, userInfo);
	}

	@Override
	public void saveStep(WfStep step) {

		this.caseDao.saveStep(step);
	}

	@Override
	public WfStep findStepById(long stepId) {

		return this.caseDao.findStepById(stepId);
	}

	@Override
	public List<WfStep> findAssignedStepsForTask(WfUserInfo userInfo, String taskname, String modelName) {

		return this.caseDao.findAssignedStepsForTask(userInfo, taskname, modelName);
	}

	@Override
	public List<WfStep> findAssignedStepsForUser(WfUserInfo userInfo, String taskName) {

		return this.caseDao.findAssignedStepsForUser(userInfo, taskName);
	}

	@Override
	public void performAction(long stepId, String actionName, String actionReason, WfUserInfo userInfo) throws Exception {

		//System.out.println("////////////////////////////////////////");
		//System.out.println("In Case Service userinfo:" + userInfo.getEmailId());
		WfStep step = caseDao.findStepById(stepId);
		//System.out.println("Task : " + step.getNode().getName());
		//System.out.println("Action : " + actionName);
		//System.out.println("////////////////////////////////////////");
		step.getOwningCase().doAction(step, actionName, userInfo.getUserName(), actionReason, userInfo.getEmailId());
		List<WfStep> pSteps = new ArrayList<WfStep>();
		pSteps.addAll(step.getOwningCase().getProcessedSteps());
		//System.out.println(step.getOwningCase().getProcessedSteps());
		step.getOwningCase().getProcessedSteps().clear();
		//System.out.println(step.getOwningCase().getProcessedSteps());
		//System.out.println("Size of steps returned by doAction : " + pSteps.size());
		WfCase wfcase = null;
		for (WfStep s : pSteps) {
			if (s != null) {
				//System.out.println("***********************************");
				//System.out.println("B4 step save-> Date created: " + s.getDateCreated() + "DAteComp: " + s.getDateCompleted()
				//	+ " User: " + s.getUserCompleted());
				caseDao.saveStep(s);
				//System.out.println("***********************************");
			}
			else {
				//System.out.println(".. Step == null.. ");
				wfcase = caseDao.findCaseById(step.getOwningCase().getId());
				wfcase.close();
				//System.out.println("^^^^ " + wfcase.getStatus());
				caseDao.saveCase(wfcase);
			}
		}

		if (step.getOwningCase().getModel().getModelCategory() == 'F') {
			if ("Approved".equals(actionName)) {
				//CaseUserForms ucf = caseUserFormsDao.findCaseUserFormsByCaseId(step.getOwningCase().getId());
				Document docUserForms=documentService.findDocumentForCaseId(step.getOwningCase().getId());
				long userFormId=Long.valueOf(docUserForms.getUserFormId()).longValue();
//				if (ucf != null)
//					formService.processWorkflowEvent(ucf.getUserFormsId(), ucf.getAuthor(), actionName);
				if(docUserForms!=null)
					formService.processWorkflowEvent(userFormId,docUserForms.getAuthor(),actionName);
			}
		}
		// caseDao.saveCase(wfcase);
	}

	@Override
	public List<TaskSummary> findTaskSummary(String modelName, WfUserInfo userInfo) {

		return this.caseDao.findTaskSummaryForModel(modelName, userInfo);
	}

	@Override
	public List<WfStep> findAssignedStepsForCase(WfUserInfo userInfo, long caseId) {

		WfCase wfcase = this.caseDao.findCaseById(caseId);
		List<WfStep> steps = this.caseDao.findAssignedStepsForCase(userInfo, wfcase);
		return filterAssignedTasks(steps, userInfo);
	}

	@Override
	public List<WfStep> findCompletedStepsForCase(WfUserInfo userInfo, long caseId) {

		WfCase wfcase = this.caseDao.findCaseById(caseId);
		return this.caseDao.findCompletedStepsForCase(userInfo, wfcase);
	}

	@Override
	public List<WfCase> getCases(ArrayList<Long> caseIds) {

		return this.caseDao.getCases(caseIds);
	}

	@Override
	public List<WfStep> findAllStepsForCase(long caseId) {

		WfCase wfcase = this.caseDao.findCaseById(caseId);
		return this.caseDao.findAllStepsForCase(wfcase);
	}
	
	@Override
	public List<WfStep> findAllStepsForCaseForForm(long caseId) {

		WfCase wfcase = this.caseDao.findCaseById(caseId);
		return this.caseDao.findAllStepsForCaseForForm(wfcase);
	}

	@Override
	public List<TaskSummary> findTaskSummaryForUser(WfUserInfo userInfo) {

		return this.caseDao.findTaskSummaryForUser(userInfo);
	}

	@Override
	public void haveBeenRead(long stepId) {

		WfStep step = this.caseDao.findStepById(stepId);
		caseDao.haveBeenRead(step);
	}

	/*@Override
	public List<TaskSummary> findTrayLabelSummaryForUser(WfUserInfo userInfo) {

		// return this.caseDao.findTrayLabelSummaryForUser(userInfo);
		
		
		List<WfStep> steps = this.findAssignedSteps(userInfo);
		
		//System.out.println("Steps-> " + steps);
		Map<String, Integer> stat = new HashMap<String, Integer>();
		int myDocs = this.countCasesByOwner(userInfo.getUserName());
		if (myDocs > 0)
			stat.put("My Documents", myDocs);

		Map<String, Set<Long>> caseStat = new HashMap<String, Set<Long>>();
		for (WfStep step : steps) {
			String trayLabel = step.getNode().getTrayLabel();
			Set<Long> caseIds = caseStat.get(trayLabel);
			if (caseIds == null) {
				caseIds = new HashSet<Long>();
			}

			caseIds.add(step.getOwningCase().getId());
			caseStat.put(trayLabel, caseIds);
			
		}

		for (String tl : caseStat.keySet()) {
			int count = caseStat.get(tl).size();
			stat.put(tl, count);
		}

		//System.out.println("MAP-> " + stat);
		List<TaskSummary> trayLabelList = new ArrayList<TaskSummary>();
		for (String key : stat.keySet()) {
			Integer val = stat.get(key);
			TaskSummary ts = new TaskSummary(key, val);
			if ("My Documents".equals(key)) {
				trayLabelList.add(0, ts);
			}
			else {
				trayLabelList.add(ts);
			}
		}

		return trayLabelList;
	}*/

	@Override
	public List<TaskSummary> findTrayLabelSummaryForUser(WfUserInfo userInfo) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date=new Date();
		Date holidayFromDate=null;
		Date holidayToDate=null;
		boolean stepCheck=true;
		
		User user=userService.findUserById(userInfo.getUserId());
		if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
			if(user.getHolidayFromDate().equals(user.getHolidayToDate())){
				holidayFromDate=user.getHolidayFromDate();
				 holidayToDate=holidayFromDate;
			}else{
				holidayFromDate=user.getHolidayFromDate();
				holidayToDate=user.getHolidayToDate();
			}
		}
		String currentDate=dateFormat.format(date);
		
		// return this.caseDao.findTrayLabelSummaryForUser(userInfo);
		List<WfStep> steps = this.findAssignedSteps(userInfo);
		
		 List<CompanyUser> cus=companyUserService.findCompanyUsersForUserId(user);
		    List<WfStep> newSteps=new ArrayList<WfStep>();
		    for(WfStep st:steps){
		    	for(CompanyUser cu:cus){
		    		Document d=documentService.findDocumentForCaseId(st.getOwningCase().getId());
		    		if(d.getDiscriminator()!='F'){
		    		if(documentService.findDocumentAttributeByDocAttrValue(d, cu.getAttrValue())!=null){
		    			newSteps.add(st);
		    		}
		    	}else{
		    		UserForms uf=userFormsService.findUserFormsById(Long.parseLong(d.getUserFormId()));
		    		AttributeValue atv=dtAttributeValueService.findDtAttrValueById(uf.getCompanyId());
		    		if((atv!=null ? atv.getValue() : null).equals(cu.getAttrValue()!=null ? cu.getAttrValue().getValue():null)){
		    			newSteps.add(st);
		    			}
		    		}
		    	}
		    }
		
		
		//System.out.println("Steps-> " + steps);
		Map<String, Integer> stat = new HashMap<String, Integer>();
		int myDocs = this.countCasesByOwner(userInfo.getUserName());
		if (myDocs > 0)
			stat.put("My Documents", myDocs);

		Map<String, Set<Long>> caseStat = new HashMap<String, Set<Long>>();
		for (WfStep step : newSteps) {
			stepCheck=true;
			
			WfCase caseId=step.getOwningCase();
			Document d=documentService.findDocumentForCaseId(caseId.getId());
			if(holidayFromDate!=null){
			if(!date.after(holidayToDate))
			stepCheck=stepCheck(user, dateFormat, holidayFromDate, holidayToDate, holidayToDate, currentDate, step, stepCheck,caseId,d);
			
			if(dateFormat.format(date).equals(dateFormat.format(holidayToDate)))
			stepCheck=stepCheck(user, dateFormat, holidayFromDate, holidayToDate, holidayToDate, currentDate, step, stepCheck,caseId,d);
			}
			if(stepCheck==true){	
			String trayLabel = step.getNode().getTrayLabel();
			Set<Long> caseIds=null;
			
			caseIds = caseStat.get(trayLabel);
			if (caseIds == null) {
				caseIds = new HashSet<Long>();
			}
			
			
			caseIds.add(step.getOwningCase().getId());
			caseStat.put(trayLabel, caseIds);
		
			}
		}
		for (String tl : caseStat.keySet()) {
			int count = caseStat.get(tl).size();
			stat.put(tl, count);
		}
		
		//System.out.println("MAP-> " + stat);
		List<TaskSummary> trayLabelList = new ArrayList<TaskSummary>();
		for (String key : stat.keySet()) {
			Integer val = stat.get(key);
			TaskSummary ts = new TaskSummary(key, val);
			if ("My Documents".equals(key)) {
				trayLabelList.add(0, ts);
			}
			else {
				trayLabelList.add(ts);
			}
		}

		return trayLabelList;
	}

	public boolean stepCheck(User user,SimpleDateFormat dateFormat,Date holidayFromDate,Date holidayToDate,Date date,String currentDate,WfStep step,
			boolean stepCheck,WfCase caseId,Document d){
		if(user.getHolidayFromDate()!=null && user.getHolidayToDate()!=null){
			if(dateFormat.format(holidayFromDate).equals(dateFormat.format(date))){	
			if(step.getStatus().equals("WF_STEP_ASSIGNED"))	{
				
				if(d.getAuthor().equals(user.getUserName())){
					stepCheck=true;
				}else{
			if(step.getDateCreated().before(holidayFromDate)){
						stepCheck=true;
					 }
				if(step.getDateCreated().after(holidayFromDate)){
					stepCheck=false;
				 }
				if(dateFormat.format(step.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
					stepCheck=false;
				}
			
			}
			}
			}else if(!holidayFromDate.after(date)){
				if(holidayToDate.after(date)){
				if(step.getStatus().equals("WF_STEP_ASSIGNED"))	{
					
					if(d.getAuthor().equals(user.getUserName())){
						stepCheck=true;
					}else{
				if(step.getDateCreated().before(holidayFromDate)){
						stepCheck=true;
					 }
				if(step.getDateCreated().after(holidayFromDate)){
					stepCheck=false;
				 }
				if(dateFormat.format(step.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
					stepCheck=false;
				}
				}
				}
				}else if(dateFormat.format(holidayToDate).equals(dateFormat.format(date))){
					if(step.getStatus().equals("WF_STEP_ASSIGNED"))	{
					
						if(d.getAuthor().equals(user.getUserName())){
							stepCheck=true;
						}else{
					if(step.getDateCreated().before(holidayFromDate)){
						stepCheck=true;
					 }
				if(step.getDateCreated().after(holidayFromDate)){
					stepCheck=false;
				 }
				if(dateFormat.format(step.getDateCreated()).equals(dateFormat.format(holidayFromDate))){
					stepCheck=false;
				}
					}
					}
			}
			}
			
		}
		return stepCheck;
	}
	
	
	@Override
	public List<WfStep> findAssignedStepsForTrayLabel(WfUserInfo userInfo, String trayLabel) {

		if (trayLabel.equalsIgnoreCase("My Documents")) {
			return this.findStepsByCaseOwner(userInfo.getUserName());
		}

		List<WfStep> steps = this.caseDao.findAssignedStepsForTrayLabel(userInfo, trayLabel);
		return filterAssignedTasks(steps, userInfo);
	}

	@Override
	public void changeToPickStatus(WfStep step, WfUserInfo userInfo) {
		String user = "U:"+userInfo.getUserName();
		String originalAssignee=step.getAssignee();
		if((originalAssignee.startsWith("R:")) || (originalAssignee.startsWith("RG:"))){
			step.setAssigneeBefore(originalAssignee);
			step.setAssignee(user);
			step.setDatePicked(new Date());
			step.changeStatusToPicked();
			this.saveStep(step);
		}
		
//		String user = "U:" + userInfo.getUserName();
//		if ((step.getAssignee().startsWith("R:")) || (step.getAssignee().startsWith("RG:"))) {
//			step.setAssignee(user);
//			step.setDatePicked(new Date());
//			step.changeStatusToPicked();
//			this.saveStep(step);
//		}
	}

	private List<WfStep> filterAssignedTasks(List<WfStep> steps, WfUserInfo userInfo) {

		List<WfStep> filteredSteps = new ArrayList<WfStep>();

		// System.out.println("Input Steps "+steps);
		for (WfStep step : steps) {
			WfCase owningCase = step.getOwningCase();
			WfCaseStatus caseStatus = owningCase.getCaseStatus();
			WfNode node = step.getNode();

			// System.out.print("Task : "+ node.getName());
			if (caseStatus == WfCaseStatus.WF_CASE_CANCELLED || caseStatus == WfCaseStatus.WF_CASE_CLOSED) {
				// System.out.println("    Skippedd");
				continue;
			}

			// Check steps Node as Task Node And
			if (node.getNodeType() == WfNodeType.WF_TASK_NODE) {
				WfTask task = (WfTask) node;
				if (task.getExcludeOwner() == 'Y' && owningCase.getCreator().equals(userInfo.getUserName())) {
					// System.out.println("    Skippedd");
					continue;
				}
			}

			// System.out.println("     Added");
			filteredSteps.add(step);
		}

		// System.out.println("Filtered Steps-> "+ filteredSteps);
		return filteredSteps;
	}

	@Override
	public void abandonCaseForStep(long stepId) {

		WfStep step = this.caseDao.findStepById(stepId);
		WfCase owningCase = step.getOwningCase();
		owningCase.cancel();

		// owningCase.setCaseStatus(WfCaseStatus.WF_CASE_CANCELLED);
		this.caseDao.saveCase(owningCase);
	}

	@Override
	public List<WfStep> findStepsByCaseOwner(String owner) {

		return this.caseDao.findStepsByCaseOwner(owner);
	}

	@Override
	public int countStepsByCaseOwner(String owner) {

		return this.findStepsByCaseOwner(owner).size();
	}

	@Override
	public List<WfCase> findCasesByOwner(String owner) {

		return this.caseDao.findCasesByOwner(owner);
	}

	@Override
	public int countCasesByOwner(String owner) {

		return this.findCasesByOwner(owner).size();
	}

	@Override
	public void changeToUnpickStatus(WfStep step) {

		String originalAssignee = step.getAssigneeBefore();
		step.setAssignee(originalAssignee);
		// step.setDatePicked(new Date());
		step.setStatus(WfStepStatus.WF_STEP_ASSIGNED.toString());
		this.saveStep(step);
	}

	@Override
	public List<WfStep> findPickedSteps() {

		return this.caseDao.findPickedSteps();
	}

	@Override
	public void abandonCase(long caseId) {

		WfCase wfCase = this.caseDao.findCaseById(caseId);
		wfCase.cancel();
		this.caseDao.saveCase(wfCase);
	}

	@Override
	public List<WfStep> findAssignedStepsForCase(long caseId) {

		WfCase wfcase = this.caseDao.findCaseById(caseId);
		return this.caseDao.findAssignedStepsForCase(wfcase);
	}

	@Override
	public List<WfStep> findStepForLastActionedCase() {
		
		return this.caseDao.findStepForLastActionedCase();
	}

	
}