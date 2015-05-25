package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.User;

public interface TenancyFormDao {

	public void save(TenancyForm tenancyForm);
	
	public TenancyForm findTenancyFormById(long id);
	
	public List<TenancyForm> findTenancyFormByCreatedBy(User user);

	public List<TenancyForm> findTenancyFormByPropertyDetailsForm(PropertyDetailsForm propertyDetailsForm);
	
	List<TenancyForm> findAllTenancy();
	
	List<TenancyForm> findAllTenancyWithAllStatus();
}
