package com.ardhika.wfar.service;

import java.util.ArrayList;
import java.util.List;

import com.ardhika.wfar.TaskSummary;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.WfUserInfo;

public interface CaseService {
	WfCase findCaseById(long id);

	// Steps related
	List<WfStep> findAssignedSteps(WfUserInfo userInfo); // dashCont - 769

	List<WfStep> findAssignedStepsForTrayLabel(WfUserInfo userInfo, String trayLabel); // DashCont
																						// -showDocs
																						// 716

	List<WfStep> findAssignedStepsForCase(WfUserInfo userInfo, long caseId); // DashCont
																				// -
																				// ViewMetadata
																				// 382

	List<WfStep> findAllStepsForCase(long caseId); // DashCont-viewHistory 1039

	List<WfStep> findCompletedStepsForCase(WfUserInfo userInfo, long caseId);// Unused

	List<WfStep> findAssignedStepsForUser(WfUserInfo userInfo, String taskName); // Unused

	List<WfStep> findAssignedStepsForTask(WfUserInfo userInfo, String taskname, String modelName); // Unused
	// End Steps related

	// TaskSummary
	List<TaskSummary> findTrayLabelSummaryForUser(WfUserInfo userInfo); // DashCon
																		// -
																		// showDocs,
																		// HomeCont
																		// -
																		// getTray

	List<TaskSummary> findTaskSummary(String modelName, WfUserInfo userInfo); // Unused

	List<TaskSummary> findTaskSummaryForUser(WfUserInfo userInfo); // unused
	// End taskSummary

	void performAction(long stepId, String actionName, String actionReason, WfUserInfo userInfo) throws Exception;

	void saveCase(WfCase wfcase);

	void saveStep(WfStep step);

	List<WfCase> getCases(ArrayList<Long> caseIds);

	void haveBeenRead(long stepId);

	void changeToPickStatus(WfStep wfStep, WfUserInfo userInfo);

	void changeToUnpickStatus(WfStep step); // Unclaim Doc

	WfStep findStepById(long stepId);

	void abandonCaseForStep(long stepId);

	void abandonCase(long caseId); // Abandon Case

	List<WfStep> findStepsByCaseOwner(String owner);

	int countStepsByCaseOwner(String owner);

	List<WfCase> findCasesByOwner(String owner);

	int countCasesByOwner(String owner);

	List<WfStep> findPickedSteps(); // Unclaim Doc

	List<WfStep> findAssignedStepsForCase(long caseId); // For Delete Doc
														// Status-Action
	List<WfStep> findStepForLastActionedCase();

	List<WfStep> findAllStepsForCaseForForm(long caseId);
	
	
}
