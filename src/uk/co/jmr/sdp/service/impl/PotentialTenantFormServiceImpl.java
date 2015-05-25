package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.PotentialTenantFormDao;
import uk.co.jmr.sdp.domain.PotentialTenantForm;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.PotentialTenantFormService;

@Service("potentialTenantFormService")
public class PotentialTenantFormServiceImpl implements PotentialTenantFormService{
	
	@Autowired
	private PotentialTenantFormDao potentialTenantFormDao;

	@Override
	public void save(PotentialTenantForm potentialTenantForm) {
		// TODO Auto-generated method stub
		this.potentialTenantFormDao.save(potentialTenantForm);
	}

	@Override
	public PotentialTenantForm findPotentialTenantFormById(long id) {
		// TODO Auto-generated method stub
		return this.potentialTenantFormDao.findPotentialTenantFormById(id);
	}

	@Override
	public List<PotentialTenantForm> findPotentialTenantFormByCreatedBy(User user) {
		// TODO Auto-generated method stub
		return this.potentialTenantFormDao.findPotentialTenantFormByCreatedBy(user);
	}

	@Override
	public List<PotentialTenantForm> findAllPotentialTenants() {
		// TODO Auto-generated method stub
		return this.potentialTenantFormDao.findAllPotentialTenants();
	}
	
	

}
