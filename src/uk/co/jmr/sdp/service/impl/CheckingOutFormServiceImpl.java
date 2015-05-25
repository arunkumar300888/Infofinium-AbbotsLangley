package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.CheckingOutFormDao;
import uk.co.jmr.sdp.domain.CheckingOutForm;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.CheckingOutFormService;

@Service("checkingOutFormService")
public class CheckingOutFormServiceImpl implements CheckingOutFormService {

	
	
	@Autowired
	
	private CheckingOutFormDao checkingOutFormDao;
	
	@Override
	public void save(CheckingOutForm checkingOutForm) {
		// TODO Auto-generated method stub

		this.checkingOutFormDao.save(checkingOutForm);
		
	}

	@Override
	public CheckingOutForm findCheckingOutFormById(long id) {
		// TODO Auto-generated method stub
		return this.checkingOutFormDao.findCheckingOutFormById(id);
	}

	@Override
	public List<CheckingOutForm> findCheckingOutFormByCreatedBy(User user) {
		// TODO Auto-generated method stub
		return this.checkingOutFormDao.findCheckingOutFormByCreatedBy(user);
	}

	@Override
	public List<CheckingOutForm> findAllCheckingOut() {
		// TODO Auto-generated method stub
		return this.checkingOutFormDao.findAllCheckingOut();
	}

	

}
