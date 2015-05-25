package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.TenantDetailsDao;
import uk.co.jmr.sdp.domain.TenantDetails;

@Repository("tenantDetailsDao")
public class TenantDetailsDaoImpl implements TenantDetailsDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(TenantDetails tenantDetails) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(tenantDetails);
	}

	@Override
	public TenantDetails findTenantDetailsById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(TenantDetails.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TenantDetails findTenantDetailsForTenancy(long tenancyId) {
		// TODO Auto-generated method stub
		List<TenantDetails> tenantDetails=hibernateTemplate.find("from TenantDetails where tenancyId=?",tenancyId);
		if(tenantDetails.size()>=1)
			return tenantDetails.get(0);
		return null;
	}
	
	

}
