package uk.co.jmr.sdp.dao;

import uk.co.jmr.sdp.domain.CustomerDetails;

public interface CustomerDetailsDao {
	
	
	public void save(CustomerDetails customerDetailsForm);
	
	CustomerDetails findCustomerDetailsFormById(long id);
	
	CustomerDetails findCustomerDetailsFormByUserFormId(long userFormId);
	
	

}
