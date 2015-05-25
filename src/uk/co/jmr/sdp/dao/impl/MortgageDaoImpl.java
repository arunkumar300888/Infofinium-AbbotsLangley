package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.MortgageDao;
import uk.co.jmr.sdp.domain.Mortgage;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;

@Repository("mortgageDao")
public class MortgageDaoImpl implements MortgageDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(Mortgage mortgage) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(mortgage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mortgage> findAllMortgages() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from Mortgage");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mortgage> findMortgagesForPropertyDetailsForm(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from Mortgage where propertyDetailsForm=?",propertyDetailsForm);
	}

	@Override
	public Mortgage findMortgageById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(Mortgage.class, id);
	}
	
	

}
