package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.TenancyFormDao;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.TenancyFormService;

@Service("tenancyFormService")
public class TenancyFormServiceImpl implements TenancyFormService{
	
	@Autowired
	private TenancyFormDao tenancyFormDao;

	@Override
	public void save(TenancyForm tenancyForm) {
		// TODO Auto-generated method stub
		this.tenancyFormDao.save(tenancyForm);
	}

	@Override
	public TenancyForm findTenancyFormById(long id) {
		// TODO Auto-generated method stub
		return this.tenancyFormDao.findTenancyFormById(id);
	}

	@Override
	public List<TenancyForm> findTenancyFormByCreatedBy(User user) {
		// TODO Auto-generated method stub
		return this.tenancyFormDao.findTenancyFormByCreatedBy(user);
	}

	@Override
	public List<TenancyForm> findTenancyFormByPropertyDetailsForm(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return this.tenancyFormDao.findTenancyFormByPropertyDetailsForm(propertyDetailsForm);
	}

	@Override
	public List<TenancyForm> findAllTenancy() {
		// TODO Auto-generated method stub
		return this.tenancyFormDao.findAllTenancy();
	}

	@Override
	public List<TenancyForm> findAllTenancyWithAllStatus() {
		// TODO Auto-generated method stub
		return this.tenancyFormDao.findAllTenancyWithAllStatus();
	}
	
	

}
