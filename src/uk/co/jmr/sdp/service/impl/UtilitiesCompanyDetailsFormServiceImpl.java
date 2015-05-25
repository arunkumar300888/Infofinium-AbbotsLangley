package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.UtilitiesCompanyDetailsFormDao;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.UtilitiesCompanyDetailsForm;
import uk.co.jmr.sdp.service.UtilitiesCompanyDetailsFormService;

@Service("utilitiesCompanyDetailsFormService")
public class UtilitiesCompanyDetailsFormServiceImpl implements UtilitiesCompanyDetailsFormService {

	@Autowired
	private UtilitiesCompanyDetailsFormDao utilitiesCompanyDetailsFormDao;

	@Override
	public void save(UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm) {
		// TODO Auto-generated method stub
		this.utilitiesCompanyDetailsFormDao.save(utilitiesCompanyDetailsForm);
	}

	@Override
	public UtilitiesCompanyDetailsForm findUtilitiesCompanyDetailsFormById(
			long id) {
		// TODO Auto-generated method stub
		return this.utilitiesCompanyDetailsFormDao.findUtilitiesCompanyDetailsFormById(id);
	}

	@Override
	public List<UtilitiesCompanyDetailsForm> findUtilitiesCompanyDetailsFormByCreatedBy(
			User user) {
		// TODO Auto-generated method stub
		return this.utilitiesCompanyDetailsFormDao.findUtilitiesCompanyDetailsFormByCreatedBy(user);
	}

	@Override
	public List<UtilitiesCompanyDetailsForm> findAllUtilitiesCompanyDetails() {
		// TODO Auto-generated method stub
		return this.utilitiesCompanyDetailsFormDao.findAllUtilitiesCompanyDetails();
	}
}
