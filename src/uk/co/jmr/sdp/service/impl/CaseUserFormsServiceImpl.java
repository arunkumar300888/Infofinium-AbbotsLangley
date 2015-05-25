package uk.co.jmr.sdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.CaseUserFormsDao;
import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.sdp.service.CaseUserFormsService;

@Service("caseUserFormService")
public class CaseUserFormsServiceImpl implements CaseUserFormsService {
	@Autowired
	private CaseUserFormsDao caseUserFormsDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveCaseUserForms(CaseUserForms caseUserForms) {

		this.caseUserFormsDao.saveCaseUserForms(caseUserForms);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CaseUserForms findCaseUserFormsById(long id) {

		return this.caseUserFormsDao.findCaseUserFormsById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CaseUserForms findCaseUserFormsByCaseId(long caseId) {

		return this.caseUserFormsDao.findCaseUserFormsByCaseId(caseId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CaseUserForms findCaseUserFormsByUserFormsId(long userFormId) {

		return this.caseUserFormsDao.findCaseUserFormsByUserFormsId(userFormId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CaseUserForms findCaseUserFormsByUserFormsIdAndCaseId(long caseId, long userFormId) {

		return this.caseUserFormsDao.findCaseUserFormsByUserFormsIdAndCaseId(caseId, userFormId);
	}
}
