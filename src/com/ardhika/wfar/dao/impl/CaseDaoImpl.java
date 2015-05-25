package com.ardhika.wfar.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.ardhika.wfar.TaskSummary;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfCaseStatus;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.WfStepStatus;
import com.ardhika.wfar.WfUserInfo;
import com.ardhika.wfar.dao.CaseDao;

@Repository("caseDao")
public class CaseDaoImpl implements CaseDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public WfCase findCaseById(long id) {

		WfCase wfcase = hibernateTemplate.get(WfCase.class, id);
		return wfcase;
	}

	@Override
	public void saveCase(WfCase wfcase) {

		hibernateTemplate.saveOrUpdate(wfcase);
	}

	@Override
	public void saveStep(WfStep step) {

		hibernateTemplate.saveOrUpdate(step);
	}

	@Override
	public WfStep findStepById(long stepId) {

		WfStep step = hibernateTemplate.get(WfStep.class, stepId);
		return step;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfStep> findAssignedSteps(WfUserInfo userInfo) {

		Set<String> roles = userInfo.getRoles();
		StringBuffer assigneesBuf = new StringBuffer();
		for (String role : roles) {
			assigneesBuf.append("'");
			assigneesBuf.append("R:" + role + "'");
			assigneesBuf.append(",");
		}

		Set<String> groupRoles = userInfo.getGroupRoles();
		for (String groupRole : groupRoles) {
			//System.out.println("GroupRole->" + groupRole);
			assigneesBuf.append("'");
			assigneesBuf.append("RG:" + groupRole + "'");
			assigneesBuf.append(",");
		}

		assigneesBuf.append("'U:" + userInfo.getUserName() + "'");
		//System.out.println(assigneesBuf);
		String query = "from WfStep s where s.assignee in (" + assigneesBuf.toString() + ")and s.status in ('"
			+ WfStepStatus.WF_STEP_ASSIGNED.toString() + "','" + WfStepStatus.WF_STEP_PICKED.toString() + "')";
		List<WfStep> steps = hibernateTemplate.find(query);
		return steps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfStep> findAssignedStepsForTask(WfUserInfo userInfo, String taskname, String modelName) {

		Set<String> roles = userInfo.getRoles();
		StringBuffer assigneesBuf = new StringBuffer();
		for (String role : roles) {
			assigneesBuf.append("'");
			assigneesBuf.append("R:" + role + "'");
			assigneesBuf.append(",");
		}

		Set<String> groupRoles = userInfo.getGroupRoles();
		for (String groupRole : groupRoles) {
			//System.out.println("GroupRole->" + groupRole);
			assigneesBuf.append("'");
			assigneesBuf.append("RG:" + groupRole + "'");
			assigneesBuf.append(",");
		}
		assigneesBuf.append("'U:" + userInfo.getUserName() + "'");
		//System.out.println(assigneesBuf);
		String query = "from WfStep s where s.status =? and s.assignee in (" + assigneesBuf.toString()
			+ ") and s.node.name=? and s.owningCase.model.name=?";
		List<WfStep> steps = hibernateTemplate.find(query, WfStepStatus.WF_STEP_ASSIGNED.toString(), taskname, modelName);
		return steps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfStep> findAssignedStepsForUser(WfUserInfo userInfo, String taskName) {

		Set<String> roles = userInfo.getRoles();
		StringBuffer assigneesBuf = new StringBuffer();
		for (String role : roles) {
			assigneesBuf.append("'");
			assigneesBuf.append("R:" + role + "'");
			assigneesBuf.append(",");
		}
		Set<String> groupRoles = userInfo.getGroupRoles();
		for (String groupRole : groupRoles) {
			//System.out.println("GroupRole->" + groupRole);
			assigneesBuf.append("'");
			assigneesBuf.append("RG:" + groupRole + "'");
			assigneesBuf.append(",");
		}

		assigneesBuf.append("'U:" + userInfo.getUserName() + "'");
		//System.out.println(assigneesBuf);
		String query = "from WfStep s where s.status =? and s.assignee in (" + assigneesBuf.toString() + ") and s.node.name=?";
		List<WfStep> steps = hibernateTemplate.find(query, WfStepStatus.WF_STEP_ASSIGNED.toString(), taskName);
		return steps;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<TaskSummary> findTaskSummaryForModel(String modelName, WfUserInfo userInfo) {

		List<TaskSummary> taskList = new ArrayList<TaskSummary>();
		Set<String> roles = userInfo.getRoles();
		StringBuffer assigneesBuf = new StringBuffer();
		for (String role : roles) {
			assigneesBuf.append("'");
			assigneesBuf.append("R:" + role + "'");
			assigneesBuf.append(",");
		}
		Set<String> groupRoles = userInfo.getGroupRoles();
		for (String groupRole : groupRoles) {
			//System.out.println("GroupRole->" + groupRole);
			assigneesBuf.append("'");
			assigneesBuf.append("RG:" + groupRole + "'");
			assigneesBuf.append(",");
		}

		assigneesBuf.append("'U:" + userInfo.getUserName() + "'");
		//System.out.println(assigneesBuf);
		String query = "select s.node.name, count(s.node.name) from WfStep s  where s.status = ? and s.assignee in ("
			+ assigneesBuf.toString() + ") and s.owningCase.model.name=? group by s.node.name";
		List result = hibernateTemplate.find(query, WfStepStatus.WF_STEP_ASSIGNED.toString(), modelName);

		Iterator itr = result.iterator();
		while (itr.hasNext()) {
			Object obj[] = (Object[]) itr.next();
			String t = (String) obj[0];
			long count = (Long) obj[1];
			// System.out.println("--------->" + t +", " + count);
			TaskSummary task = new TaskSummary(t, count);
			taskList.add(task);
		}
		return taskList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfStep> findAssignedStepsForCase(WfUserInfo userInfo, WfCase wfcase) {

		//System.out.println("User Info CaseDaoImpl:" + userInfo.getUserName());
		Set<String> roles = userInfo.getRoles();
		StringBuffer assigneesBuf = new StringBuffer();
		for (String role : roles) {
			assigneesBuf.append("'");
			assigneesBuf.append("R:" + role + "'");
			assigneesBuf.append(",");
		}
		Set<String> groupRoles = userInfo.getGroupRoles();
		for (String groupRole : groupRoles) {
			//System.out.println("GroupRole->" + groupRole);
			assigneesBuf.append("'");
			assigneesBuf.append("RG:" + groupRole + "'");
			assigneesBuf.append(",");
		}

		assigneesBuf.append("'U:" + userInfo.getUserName() + "'");
		//System.out.println(assigneesBuf);
		String query = "from WfStep s where s.assignee in (" + assigneesBuf.toString() + ") and s.status in ('"
			+ WfStepStatus.WF_STEP_ASSIGNED.toString() + "','" + WfStepStatus.WF_STEP_PICKED.toString() + "')"
			+ "and s.owningCase=?";
		List<WfStep> steps = hibernateTemplate.find(query, wfcase);
		return steps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfStep> findCompletedStepsForCase(WfUserInfo userInfo, WfCase wfcase) {

		Set<String> roles = userInfo.getRoles();
		StringBuffer assigneesBuf = new StringBuffer();
		for (String role : roles) {
			assigneesBuf.append("'");
			assigneesBuf.append("R:" + role + "'");
			assigneesBuf.append(",");
		}
		Set<String> groupRoles = userInfo.getGroupRoles();
		for (String groupRole : groupRoles) {
			//System.out.println("GroupRole->" + groupRole);
			assigneesBuf.append("'");
			assigneesBuf.append("RG:" + groupRole + "'");
			assigneesBuf.append(",");
		}
		assigneesBuf.append("'U:" + userInfo.getUserName() + "'");
		//System.out.println(assigneesBuf);
		String query = "from WfStep s where s.status = ? and s.assignee in (" + assigneesBuf.toString() + ") and s.owningCase=?";
		List<WfStep> steps = hibernateTemplate.find(query, WfStepStatus.WF_STEP_COMPLETED.toString(), wfcase);
		return steps;
	}

	@Override
	public List<WfCase> getCases(ArrayList<Long> caseIds) {

		StringBuffer assigneesBuf = new StringBuffer();
		ListIterator<Long> it = caseIds.listIterator();
		while (it.hasNext()) {
			assigneesBuf.append(it.next());
			if (it.hasNext())
				assigneesBuf.append(",");
		}

		String query = "from WfCase c where c.id in (" + assigneesBuf + ")";
		@SuppressWarnings("unchecked")
		List<WfCase> cases = hibernateTemplate.find(query);

		return cases;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfStep> findAllStepsForCase(WfCase wfcase) {

		String query = "from WfStep s where s.owningCase=?";

		List<WfStep> steps = hibernateTemplate.find(query, wfcase);
		return steps;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<TaskSummary> findTaskSummaryForUser(WfUserInfo userInfo) {

		List<TaskSummary> taskList = new ArrayList<TaskSummary>();
		Set<String> roles = userInfo.getRoles();
		StringBuffer assigneesBuf = new StringBuffer();
		for (String role : roles) {
			assigneesBuf.append("'");
			assigneesBuf.append("R:" + role + "'");
			assigneesBuf.append(",");
		}
		Set<String> groupRoles = userInfo.getGroupRoles();
		for (String groupRole : groupRoles) {
			//System.out.println("GroupRole->" + groupRole);
			assigneesBuf.append("'");
			assigneesBuf.append("RG:" + groupRole + "'");
			assigneesBuf.append(",");
		}
		assigneesBuf.append("'U:" + userInfo.getUserName() + "'");
		//System.out.println(assigneesBuf);
		String query = "select s.node.name, count(s.node.name) from WfStep s  where s.status = ? and s.assignee in ("
			+ assigneesBuf.toString() + ") group by s.node.name";
		List result = hibernateTemplate.find(query, WfStepStatus.WF_STEP_ASSIGNED.toString());

		Iterator itr = result.iterator();
		while (itr.hasNext()) {
			Object obj[] = (Object[]) itr.next();
			String t = (String) obj[0];
			long count = (Long) obj[1];
			// System.out.println("--------->" + t +", " + count);
			TaskSummary task = new TaskSummary(t, count);
			taskList.add(task);
		}
		return taskList;
	}

	@Override
	public void haveBeenRead(WfStep step) {

		if (step.getReadStatus() == 'U') {
			step.setReadStatus('R');
			hibernateTemplate.saveOrUpdate(step);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<TaskSummary> findTrayLabelSummaryForUser(WfUserInfo userInfo) {

		List<TaskSummary> trayLabelList = new ArrayList<TaskSummary>();
		Set<String> roles = userInfo.getRoles();
		StringBuffer assigneesBuf = new StringBuffer();
		for (String role : roles) {
			//System.out.println("Role-> " + role);
			assigneesBuf.append("'");
			assigneesBuf.append("R:" + role + "'");
			assigneesBuf.append(",");
		}

		Set<String> groupRoles = userInfo.getGroupRoles();
		for (String groupRole : groupRoles) {
			//System.out.println("GroupRole->" + groupRole);
			assigneesBuf.append("'");
			assigneesBuf.append("RG:" + groupRole + "'");
			assigneesBuf.append(",");
		}
		assigneesBuf.append("'U:" + userInfo.getUserName() + "'");
		//System.out.println("******** " + assigneesBuf.toString());
		String query = "select s.node.trayLabel, count(s.node.trayLabel) from WfStep s  where s.assignee in ("
			+ assigneesBuf.toString() + ") and s.status in ('" + WfStepStatus.WF_STEP_ASSIGNED.toString() + "','"
			+ WfStepStatus.WF_STEP_PICKED.toString() + "')" + " group by s.node.trayLabel";
		List result = hibernateTemplate.find(query);
System.out.println(result);
		Iterator itr = result.iterator();
		System.out.println();
		while (itr.hasNext()) {
			Object obj[] = (Object[]) itr.next();
			String t = (String) obj[0];
			System.out.println(t);
			long count = (Long) obj[1];
			System.out.println(count);
			// System.out.println("--------->" + t +", " + count);
			TaskSummary trayLabel = new TaskSummary(t, count);
			trayLabelList.add(trayLabel);
		}
		return trayLabelList;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WfStep> findAssignedStepsForTrayLabel(WfUserInfo userInfo, String trayLabel) {

		Set<String> roles = userInfo.getRoles();
		StringBuffer assigneesBuf = new StringBuffer();
		for (String role : roles) {
			assigneesBuf.append("'");
			assigneesBuf.append("R:" + role + "'");
			assigneesBuf.append(",");
		}
		Set<String> groupRoles = userInfo.getGroupRoles();
		for (String groupRole : groupRoles) {
			//System.out.println("GroupRole->" + groupRole);
			assigneesBuf.append("'");
			assigneesBuf.append("RG:" + groupRole + "'");
			assigneesBuf.append(",");
		}
		assigneesBuf.append("'U:" + userInfo.getUserName() + "'");
		//System.out.println(assigneesBuf);
		String query = "from WfStep s where s.assignee in (" + assigneesBuf.toString() + ") and s.status in ('"
			+ WfStepStatus.WF_STEP_ASSIGNED.toString() + "','" + WfStepStatus.WF_STEP_PICKED.toString()
			+ "')and  s.node.trayLabel=?";
		List<WfStep> steps = hibernateTemplate.find(query, trayLabel);
		return steps;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<WfStep> findStepsByCaseOwner(String owner) {

		String query = "from WfStep s where s.owningCase.creator=?" + " and s.status in ('"
			+ WfStepStatus.WF_STEP_ASSIGNED.toString() + "','" + WfStepStatus.WF_STEP_PICKED.toString()
			+ "') and s.owningCase.status in ('" + WfCaseStatus.WF_CASE_OPEN + "') and s.assignee not in('S:SYSTEM')";

		List<WfStep> steps = hibernateTemplate.find(query, owner);
		return steps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfCase> findCasesByOwner(String owner) {

		String query = "select s.owningCase from WfStep as s where s.owningCase.creator=?" + " and s.status in ('"
			+ WfStepStatus.WF_STEP_ASSIGNED.toString() + "','" + WfStepStatus.WF_STEP_PICKED.toString()
			+ "') and s.owningCase.status in ('" + WfCaseStatus.WF_CASE_OPEN + "') and s.assignee not in('S:SYSTEM')"
			+ "group by s.owningCase";

		List<WfCase> cases = hibernateTemplate.find(query, owner);
		return cases;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfCase> findAssignedCases(WfUserInfo userInfo) {

		Set<String> roles = userInfo.getRoles();
		StringBuffer assigneesBuf = new StringBuffer();
		for (String role : roles) {
			assigneesBuf.append("'");
			assigneesBuf.append("R:" + role + "'");
			assigneesBuf.append(",");
		}

		Set<String> groupRoles = userInfo.getGroupRoles();
		for (String groupRole : groupRoles) {
			//System.out.println("GroupRole->" + groupRole);
			assigneesBuf.append("'");
			assigneesBuf.append("RG:" + groupRole + "'");
			assigneesBuf.append(",");
		}

		assigneesBuf.append("'U:" + userInfo.getUserName() + "'");
		//System.out.println(assigneesBuf);
		String query = "select distinct s.owningCase from WfStep s where s.assignee in (" + assigneesBuf.toString()
			+ ")and s.status in ('" + WfStepStatus.WF_STEP_ASSIGNED.toString() + "','" + WfStepStatus.WF_STEP_PICKED.toString()
			+ "')" + "and s.owningCase.status='WF_CASE_OPEN'";
		//System.out.println("Query->" + query);
		List<WfCase> cases = hibernateTemplate.find(query);
		return cases;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfStep> findPickedSteps() {

		// String query = "from WfStep s where s.status =?";
		String query = "from WfStep s where s.status =? and s.owningCase.status!=?";
		// List<WfStep> steps = hibernateTemplate.find(query,
		// WfStepStatus.WF_STEP_PICKED.toString());
		List<WfStep> steps = hibernateTemplate.find(query, WfStepStatus.WF_STEP_PICKED.toString(),
			WfCaseStatus.WF_CASE_CANCELLED.toString());
		return steps;
	}

	@Override
	public List<WfStep> findAssignedStepsForCase(WfCase wfcase) {

		String query = "from WfStep s where s.owningCase=? and s.status in ('" + WfStepStatus.WF_STEP_ASSIGNED.toString() + "','"
			+ WfStepStatus.WF_STEP_PICKED.toString() + "')";
		List<WfStep> steps = hibernateTemplate.find(query, wfcase);
		return steps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfStep> findStepForLastActionedCase() {
		List<WfStep> wfStep=hibernateTemplate.find("from WfStep as w where w.status='WF_STEP_COMPLETED' order by w.dateCompleted desc");
		/*if(wfStep.size()>=1){
			return wfStep.get(0);
		}*/
		return wfStep;
	}

	@Override
	public List<WfStep> findAllStepsForCaseForForm(WfCase wfcase) {
		// TODO Auto-generated method stub
		String query = "from WfStep s where s.owningCase=? order by s.id DESC LIMIT 1";

		List<WfStep> steps = hibernateTemplate.find(query, wfcase);
		return steps;
	}
}