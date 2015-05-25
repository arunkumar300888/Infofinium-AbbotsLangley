package uk.co.jmr.sdp.service;

import uk.co.jmr.sdp.domain.CustomerDetails;

public interface CustomerDetailsService {

	
	public void save(CustomerDetails customerDetailsForm);
	
	
	CustomerDetails findCustomerDetailsFormById(long id);
	
	CustomerDetails findCustomerDetailsFormByUserFormId(long userFormId);
	
	
}
