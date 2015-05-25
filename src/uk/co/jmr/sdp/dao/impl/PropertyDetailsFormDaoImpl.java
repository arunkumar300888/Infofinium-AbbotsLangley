package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.PropertyDetailsFormDao;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.User;


@Repository("propertyDetailsFormDao")
public class PropertyDetailsFormDaoImpl implements PropertyDetailsFormDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(propertyDetailsForm);
	}

	@Override
	public PropertyDetailsForm findPropertyDetailsFormById(long id) {
		// TODO Auto-generated method stub
		
		return hibernateTemplate.get(PropertyDetailsForm.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PropertyDetailsForm> findPropertyDetailsFormByCreatedBy(
			User user) {
		// TODO Auto-generated method stub
		List<PropertyDetailsForm> propertyDetailsForms=hibernateTemplate.find("from PropertyDetailsForm where createdBy=?",user);
			/*if(propertyDetailsForms.size()>=1)
				return propertyDetailsForms.get(0);*/
		return propertyDetailsForms;
	}

	@Override
	public void delete(PropertyDetailsForm propertyDetailsForms) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(propertyDetailsForms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PropertyDetailsForm> findAllPropertyDetailsForm() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from PropertyDetailsForm where status=?","Property Submitted");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PropertyDetailsForm> findAllPropertyDetailsFormWithAllStatus() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from PropertyDetailsForm ");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PropertyDetailsForm> findAllPropertyDetailsFormWithDraftStatus() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from PropertyDetailsForm where status=?","Property Created");
	}
	
	
	

	
	
}
