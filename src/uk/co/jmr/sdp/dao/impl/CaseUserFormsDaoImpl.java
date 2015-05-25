package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.CaseUserFormsDao;
import uk.co.jmr.sdp.domain.CaseUserForms;
import uk.co.jmr.webforms.db.pojo.UserForms;

@Repository("caseUserFormsDao")
public class CaseUserFormsDaoImpl implements CaseUserFormsDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void saveCaseUserForms(CaseUserForms caseUserForms) {

		this.hibernateTemplate.saveOrUpdate(caseUserForms);
	}

	@Override
	public CaseUserForms findCaseUserFormsById(long id) {

		return hibernateTemplate.get(CaseUserForms.class, id);
	}

	@Override
	public CaseUserForms findCaseUserFormsByCaseId(long caseId) {

		@SuppressWarnings("unchecked")
		List<CaseUserForms> caseUserForms = hibernateTemplate.find("from CaseUserForms where case_id=?", caseId);
		if (caseUserForms.size() >= 1)
			return caseUserForms.get(0);
		return null;
	}

	@Override
	public CaseUserForms findCaseUserFormsByUserFormsId(long userFormId) {

		@SuppressWarnings("unchecked")
		List<CaseUserForms> caseUserForms = hibernateTemplate.find("from CaseUserForms where user_form_id=?", userFormId);
		if (caseUserForms.size() >= 1)
			return caseUserForms.get(0);
		return null;
	}

	@Override
	public CaseUserForms findCaseUserFormsByUserFormsIdAndCaseId(long userFormId, long caseId) {

		@SuppressWarnings("unchecked")
		List<CaseUserForms> caseUserForms = hibernateTemplate.find("from CaseUserForms where user_form_id=? and case_id=?",
			userFormId, caseId);
		if (caseUserForms.size() >= 1)
			return caseUserForms.get(0);
		return null;
	}

}
