package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.PotentialTenantFormDao;
import uk.co.jmr.sdp.domain.PotentialTenantForm;
import uk.co.jmr.sdp.domain.User;

@Repository("potentialTenantFormDao")
public class PotentialTenantFormDaoImpl implements PotentialTenantFormDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(PotentialTenantForm potentialTenantForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(potentialTenantForm);
	}

	@Override
	public PotentialTenantForm findPotentialTenantFormById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(PotentialTenantForm.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PotentialTenantForm> findPotentialTenantFormByCreatedBy(User user) {
		// TODO Auto-generated method stub
		List<PotentialTenantForm> potentialTenantForms=hibernateTemplate.find("from PotentialTenantForm where createdBy=?",user);
		/*if(potentialTenantForms.size()>=1)
			return potentialTenantForms.get(0);*/
		return potentialTenantForms;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PotentialTenantForm> findAllPotentialTenants() {
		// TODO Auto-generated method stub
		List<PotentialTenantForm> potentialTenantForms=hibernateTemplate.find("from PotentialTenantForm");
		return potentialTenantForms;
	}
	
	
}
