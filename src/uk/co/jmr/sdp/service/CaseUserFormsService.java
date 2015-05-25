package uk.co.jmr.sdp.service;

import uk.co.jmr.sdp.domain.CaseUserForms;

public interface CaseUserFormsService {

	void saveCaseUserForms(CaseUserForms caseUserForms);

	CaseUserForms findCaseUserFormsById(long caseUserFormsId);

	CaseUserForms findCaseUserFormsByCaseId(long caseId);

	CaseUserForms findCaseUserFormsByUserFormsId(long userFormId);

	CaseUserForms findCaseUserFormsByUserFormsIdAndCaseId(long userFormId, long caseId);
}
