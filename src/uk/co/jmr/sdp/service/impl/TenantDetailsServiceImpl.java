package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.TenantDetailsDao;
import uk.co.jmr.sdp.domain.TenantDetails;
import uk.co.jmr.sdp.service.TenantDetailsService;

@Service("tenantDetailsService")
public class TenantDetailsServiceImpl implements TenantDetailsService{

	@Autowired
	private TenantDetailsDao tenantDetailsDao;

	@Override
	public void save(TenantDetails tenantDetails) {
		// TODO Auto-generated method stub
		this.tenantDetailsDao.save(tenantDetails);
	}

	@Override
	public TenantDetails findTenantDetailsById(long id) {
		// TODO Auto-generated method stub
		return this.tenantDetailsDao.findTenantDetailsById(id);
	}

	@Override
	public TenantDetails findTenantDetailsForTenancy(long tenancyId) {
		// TODO Auto-generated method stub
		return this.tenantDetailsDao.findTenantDetailsForTenancy(tenancyId);
	}
	
	
}
