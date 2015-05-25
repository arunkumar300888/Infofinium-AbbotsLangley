package uk.co.jmr.sdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import uk.co.jmr.sdp.dao.UsefulInformationForTenantsDao;
import uk.co.jmr.sdp.domain.UsefulInformationForTenants;
import uk.co.jmr.sdp.service.UsefulInformationForTenantsService;;

@Service("usefulInformationForTenantsService")
public class UsefulInformationForTenantsServiceImpl implements UsefulInformationForTenantsService {
	
	@Autowired
	private UsefulInformationForTenantsDao usefulInformationForTenantsDao;

	
public void save(UsefulInformationForTenants usefulInformationForTenants){
	this.usefulInformationForTenantsDao.save(usefulInformationForTenants);
	}
	
	public UsefulInformationForTenants findUsefulInformationForTenantsDetailsFormById(long id)
	{
		return this.usefulInformationForTenantsDao.findUsefulInformationForTenantsDetailsFormById(id);
	}
	
	public UsefulInformationForTenants findUsefulInformationForTenantsDetailsFormByUserFormId(long userFormId)
	{
		return this.usefulInformationForTenantsDao.findUsefulInformationForTenantsDetailsFormByUserFormId(userFormId);
	}

}
