package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.PotentialTenantForm;
import uk.co.jmr.sdp.domain.User;

public interface PotentialTenantFormDao {

	
	public void save(PotentialTenantForm potentialTenantForm);
	
	PotentialTenantForm findPotentialTenantFormById(long id);
	
	List<PotentialTenantForm> findPotentialTenantFormByCreatedBy(User user);
	
	List<PotentialTenantForm> findAllPotentialTenants();
}
