package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.UtilitiesCompanyDetailsForm;

public interface UtilitiesCompanyDetailsFormService {

	public void save(UtilitiesCompanyDetailsForm utilitiesCompanyDetailsForm);
	
	public UtilitiesCompanyDetailsForm findUtilitiesCompanyDetailsFormById(long id);
	
	public List<UtilitiesCompanyDetailsForm> findUtilitiesCompanyDetailsFormByCreatedBy(User user);
	
	public List<UtilitiesCompanyDetailsForm> findAllUtilitiesCompanyDetails();
}
