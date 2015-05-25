package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.TenantDetails;

public interface TenantDetailsService {
	
	public void save(TenantDetails tenantDetails);
	
	public TenantDetails findTenantDetailsById(long id);
	
	TenantDetails findTenantDetailsForTenancy(long tenancyId);


}
