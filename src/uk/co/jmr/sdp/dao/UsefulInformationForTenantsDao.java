package uk.co.jmr.sdp.dao;

import uk.co.jmr.sdp.domain.UsefulInformationForTenants;;

public interface UsefulInformationForTenantsDao {
	
	public void save(UsefulInformationForTenants usefulInformationForTenants);
	
	public UsefulInformationForTenants findUsefulInformationForTenantsDetailsFormById(long id);
	
	public UsefulInformationForTenants findUsefulInformationForTenantsDetailsFormByUserFormId(long userFormId);
}
