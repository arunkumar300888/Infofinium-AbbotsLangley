package uk.co.jmr.sdp.dao;

import uk.co.jmr.sdp.domain.CaseUserForms;

public interface CaseUserFormsDao {

	void saveCaseUserForms(CaseUserForms caseUserForms);

	CaseUserForms findCaseUserFormsById(long id);

	CaseUserForms findCaseUserFormsByCaseId(long caseId);

	CaseUserForms findCaseUserFormsByUserFormsId(long userFormId);

	CaseUserForms findCaseUserFormsByUserFormsIdAndCaseId(long userFormId, long caseId);
}
