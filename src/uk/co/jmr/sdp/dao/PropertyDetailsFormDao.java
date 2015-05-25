package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.User;

public interface PropertyDetailsFormDao {
	
	public void save(PropertyDetailsForm propertyDetailsForm);
	
	PropertyDetailsForm findPropertyDetailsFormById(long id);
	
	List<PropertyDetailsForm> findPropertyDetailsFormByCreatedBy(User createdBy);
	
	public void delete(PropertyDetailsForm propertyDetailsForms);
	
	public List<PropertyDetailsForm> findAllPropertyDetailsForm();
	
	public List<PropertyDetailsForm> findAllPropertyDetailsFormWithAllStatus();
	
	public List<PropertyDetailsForm> findAllPropertyDetailsFormWithDraftStatus();
}
