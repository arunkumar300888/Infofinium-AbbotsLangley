package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.PropertyDetailsFormDao;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.PropertyDetailsFormService;

@Service("propertyDetailsFormService")
public class PropertyDetailsFormServiceImpl implements PropertyDetailsFormService {

	@Autowired
	private PropertyDetailsFormDao propertyDetailsFormDao;

	@Override
	public void save(PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		this.propertyDetailsFormDao.save(propertyDetailsForm);
	}

	
	@Override
	public PropertyDetailsForm findPropertyDetailsFormById(long id) {
		// TODO Auto-generated method stub
		return this.propertyDetailsFormDao.findPropertyDetailsFormById(id);
	}


	@Override
	public List<PropertyDetailsForm> findPropertyDetailsFormByCreatedBy(
			User createdBy) {
		// TODO Auto-generated method stub
		return this.propertyDetailsFormDao.findPropertyDetailsFormByCreatedBy(createdBy);
	}


	@Override
	public void delete(PropertyDetailsForm propertyDetailsForms) {
		// TODO Auto-generated method stub
		this.propertyDetailsFormDao.delete(propertyDetailsForms);
	}


	@Override
	public List<PropertyDetailsForm> findAllPropertyDetailsForm() {
		// TODO Auto-generated method stub
		return this.propertyDetailsFormDao.findAllPropertyDetailsForm();
	}


	@Override
	public List<PropertyDetailsForm> findAllPropertyDetailsFormWithAllStatus() {
		// TODO Auto-generated method stub
		return this.propertyDetailsFormDao.findAllPropertyDetailsFormWithAllStatus();
	}


	@Override
	public List<PropertyDetailsForm> findAllPropertyDetailsFormWithDraftStatus() {
		// TODO Auto-generated method stub
		return this.propertyDetailsFormDao.findAllPropertyDetailsFormWithDraftStatus();
	}
	
	
	
}
