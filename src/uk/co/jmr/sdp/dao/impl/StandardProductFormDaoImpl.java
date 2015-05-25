package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.StandardProductFormDao;
import uk.co.jmr.sdp.domain.StandardProductForm;

@Repository("standardProductFormDao")
public class StandardProductFormDaoImpl implements StandardProductFormDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(StandardProductForm standardProductForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(standardProductForm);
	}

	@Override
	public StandardProductForm findStandardProductFormById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(StandardProductForm.class, id);
	}

	@Override
	public StandardProductForm findStandardProductFormByUserFormId(
			long userFormId) {
		// TODO Auto-generated method stub
		List<StandardProductForm> standardProductForms=hibernateTemplate.find("from StandardProductForm where userFormId=?",userFormId);
		if(standardProductForms.size()>=1)
			return standardProductForms.get(0);
		return null;
	}
	
	
}
