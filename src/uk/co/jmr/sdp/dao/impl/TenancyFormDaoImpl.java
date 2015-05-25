package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.TenancyFormDao;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.User;

@Repository("tenancyFormDao")
public class TenancyFormDaoImpl implements TenancyFormDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(TenancyForm tenancyForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(tenancyForm);
	}

	@Override
	public TenancyForm findTenancyFormById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(TenancyForm.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TenancyForm> findTenancyFormByCreatedBy(User user) {
		// TODO Auto-generated method stub
		List<TenancyForm> tenancyForms=hibernateTemplate.find("from TenancyForm where createdBy=?",user);
		/*if(tenancyForms.size()>=1)
			return tenancyForms.get(0);*/
		return tenancyForms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TenancyForm> findTenancyFormByPropertyDetailsForm(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		List<TenancyForm> tenancyForms=hibernateTemplate.find("from TenancyForm where propertyDetailsForm=?",propertyDetailsForm);
		
		return tenancyForms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TenancyForm> findAllTenancy() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from TenancyForm where status=?","Checked In");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TenancyForm> findAllTenancyWithAllStatus() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from TenancyForm");
	}
	
	
	

}
