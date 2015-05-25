package com.ardhika.wfar.dao;

import java.util.ArrayList;
import java.util.List;

import com.ardhika.wfar.TaskSummary;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.WfUserInfo;

public interface CaseDao {
	WfCase findCaseById(long id);

	void saveCase(WfCase wfcase);

	List<WfStep> findAssignedSteps(WfUserInfo userInfo);

	List<WfStep> findAssignedStepsForTask(WfUserInfo userInfo, String taskname, String modelName);

	List<WfStep> findAssignedStepsForUser(WfUserInfo userInfo, String taskName);

	List<TaskSummary> findTaskSummaryForModel(String modelName, WfUserInfo userInfo);

	void saveStep(WfStep step);

	WfStep findStepById(long stepId);

	List<WfStep> findAssignedStepsForCase(WfUserInfo userInfo, WfCase wfcase);

	List<WfStep> findCompletedStepsForCase(WfUserInfo userInfo, WfCase wfcase);

	List<WfCase> getCases(ArrayList<Long> caseIds);

	List<WfStep> findAllStepsForCase(WfCase wfcase);

	List<TaskSummary> findTaskSummaryForUser(WfUserInfo userInfo);

	void haveBeenRead(WfStep step);

	List<TaskSummary> findTrayLabelSummaryForUser(WfUserInfo userInfo);// Unused

	List<WfStep> findAssignedStepsForTrayLabel(WfUserInfo userInfo, String trayLabel);

	List<WfStep> findStepsByCaseOwner(String owner);

	List<WfCase> findCasesByOwner(String owner);

	List<WfCase> findAssignedCases(WfUserInfo userInfo);

	List<WfStep> findPickedSteps();

	List<WfStep> findAssignedStepsForCase(WfCase wfcase); // For Delete Doc
															// Status-Action
	List<WfStep> findStepForLastActionedCase();

	List<WfStep> findAllStepsForCaseForForm(WfCase wfcase);

	
}
