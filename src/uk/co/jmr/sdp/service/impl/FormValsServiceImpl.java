package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.FormValsDao;
import uk.co.jmr.sdp.domain.FormVals;
import uk.co.jmr.sdp.service.FormValsService;

@Service("formValsService")
public class FormValsServiceImpl implements FormValsService{
	
	@Autowired
	private FormValsDao formValsDao;

	@Override
	public FormVals findFormValsById(long id) {
		// TODO Auto-generated method stub
		return this.formValsDao.findFormValsById(id);
	}

	/*@Override
	public FormVals findFormValsByUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		return this.formValsDao.findFormValsByUserFormId(userFormId);
	}*/

	@Override
	public List<FormVals> findAllFormVals() {
		// TODO Auto-generated method stub
		return this.formValsDao.findAllFormVals();
	}

	@Override
	public void save(FormVals formVals) {
		// TODO Auto-generated method stub
		this.formValsDao.save(formVals);
	}
	
	

}
