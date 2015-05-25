package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.TenantDetails;

public interface TenantDetailsDao {
	
	public void save(TenantDetails tenantDetails);
	
	public TenantDetails findTenantDetailsById(long id);
	
	TenantDetails findTenantDetailsForTenancy(long tenancyId);

}
