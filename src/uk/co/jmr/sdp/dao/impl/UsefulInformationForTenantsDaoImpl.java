package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.UsefulInformationForTenantsDao;
//import uk.co.jmr.sdp.domain.BuilderDetailsForm;
import uk.co.jmr.sdp.domain.UsefulInformationForTenants;;

@Repository("usefulInformationForTenantsDao")
public class UsefulInformationForTenantsDaoImpl implements  UsefulInformationForTenantsDao{
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(UsefulInformationForTenants usefulInformationForTenants) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(usefulInformationForTenants);
	}

	@Override
	public UsefulInformationForTenants findUsefulInformationForTenantsDetailsFormById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(UsefulInformationForTenants.class, id);
	}

	@Override
	public UsefulInformationForTenants findUsefulInformationForTenantsDetailsFormByUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		List<UsefulInformationForTenants> detailsForms=hibernateTemplate.find("from UsefulInformationForTenants where userFormId=?",userFormId);
		if(detailsForms.size()>=1)
			return detailsForms.get(0);
		return null;
	}

}
