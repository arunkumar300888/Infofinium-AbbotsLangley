package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.UtilitiesCompanyDetailsFormDao;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.UtilitiesCompanyDetailsForm;

@Repository("utilitiesCompanyDetailsFormDao")
public class UtilitiesCompanyDetailsFormDaoImpl implements UtilitiesCompanyDetailsFormDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(utilitiesCompanyDetailsForm);
	}

	@Override
	public UtilitiesCompanyDetailsForm findUtilitiesCompanyDetailsFormById(
			long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(UtilitiesCompanyDetailsForm.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UtilitiesCompanyDetailsForm> findUtilitiesCompanyDetailsFormByCreatedBy(User user) {
		// TODO Auto-generated method stub
		List<UtilitiesCompanyDetailsForm> forms=hibernateTemplate.find("from UtilitiesCompanyDetailsForm where createdBy=?",user);
		/*if(forms.size()>=1)
			return forms.get(0);*/
		return forms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UtilitiesCompanyDetailsForm> findAllUtilitiesCompanyDetails() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from UtilitiesCompanyDetailsForm");
	}
	
}
