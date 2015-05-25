package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.FormTrailDao;
import uk.co.jmr.sdp.domain.FormTrail;
import uk.co.jmr.sdp.service.FormTrailService;

@Service("formTrailService")
public class FormTrailServiceImpl implements FormTrailService{

	@Autowired
	private FormTrailDao formTrailDao;

	@Override
	public void save(FormTrail formTrail) {
		// TODO Auto-generated method stub
	this.formTrailDao.save(formTrail);	
	}

	@Override
	public List<FormTrail> findFormTrailForUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		return this.formTrailDao.findFormTrailForUserFormId(userFormId);
	}
	
	
	
}
