package uk.co.jmr.sdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.CustomerDetailsDao;
import uk.co.jmr.sdp.domain.CustomerDetails;
import uk.co.jmr.sdp.service.CustomerDetailsService;

@Service("customerDetailsService")
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

	
	@Autowired
	private CustomerDetailsDao customerDetailsDao;
	@Override
	public void save(CustomerDetails customerDetailsForm) {
		// TODO Auto-generated method stub
		this.customerDetailsDao.save(customerDetailsForm);
		
	}

	@Override
	public CustomerDetails findCustomerDetailsFormById(long id) {
		// TODO Auto-generated method stub
		return this.customerDetailsDao.findCustomerDetailsFormById(id);
	}

	@Override
	public CustomerDetails findCustomerDetailsFormByUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		return this.customerDetailsDao.findCustomerDetailsFormByUserFormId(userFormId);
	}

}
