package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.User;

public interface PropertyDetailsFormService {
	
	public void save(PropertyDetailsForm propertyDetailsForm);
	
	PropertyDetailsForm findPropertyDetailsFormById(long id);
	
	public List<PropertyDetailsForm> findAllPropertyDetailsForm();
	
	public void delete(PropertyDetailsForm propertyDetailsForms);

	List<PropertyDetailsForm> findPropertyDetailsFormByCreatedBy(User createdBy);
	
	public List<PropertyDetailsForm> findAllPropertyDetailsFormWithAllStatus();
	
	public List<PropertyDetailsForm> findAllPropertyDetailsFormWithDraftStatus();

}
