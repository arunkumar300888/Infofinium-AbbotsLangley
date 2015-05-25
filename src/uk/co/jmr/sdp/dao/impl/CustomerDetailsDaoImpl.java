package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.CustomerDetailsDao;
import uk.co.jmr.sdp.domain.CustomerDetails;

@Repository("customerDetailsDao")
public class CustomerDetailsDaoImpl implements CustomerDetailsDao {

	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(CustomerDetails customerDetailsForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(customerDetailsForm);
	}

	@Override
	public CustomerDetails findCustomerDetailsFormById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(CustomerDetails.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerDetails findCustomerDetailsFormByUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		List<CustomerDetails> customerDetails=hibernateTemplate.find("from CustomerDetails where userFormId=?",userFormId);
		if(customerDetails.size()>=1)
			return customerDetails.get(0);
		return null;
	}
	
	

}
