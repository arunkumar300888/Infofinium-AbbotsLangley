package uk.co.jmr.sdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.StandardProductFormDao;
import uk.co.jmr.sdp.domain.StandardProductForm;
import uk.co.jmr.sdp.service.StandardProductFormService;

@Service("standardProductFormService")
public class StandardProductFormServiceImpl implements StandardProductFormService{
	
	@Autowired
	private StandardProductFormDao standardProductFormDao;

	@Override
	public void save(StandardProductForm standardProductForm) {
		// TODO Auto-generated method stub
		this.standardProductFormDao.save(standardProductForm);
		
	}

	@Override
	public StandardProductForm findStandardProductFormById(long id) {
		// TODO Auto-generated method stub
		return this.standardProductFormDao.findStandardProductFormById(id);
	}

	@Override
	public StandardProductForm findStandardProductFormByUserFormId(
			long userFormId) {
		// TODO Auto-generated method stub
		return this.standardProductFormDao.findStandardProductFormByUserFormId(userFormId);
	}
	
	
}
