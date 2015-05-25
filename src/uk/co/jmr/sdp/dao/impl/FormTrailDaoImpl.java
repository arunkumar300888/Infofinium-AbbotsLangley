package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.FormTrailDao;
import uk.co.jmr.sdp.domain.FormTrail;

@Repository("formTrailDao")
public class FormTrailDaoImpl implements FormTrailDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(FormTrail formTrail) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(formTrail);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormTrail> findFormTrailForUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		
		
		return hibernateTemplate.find("from FormTrail f where f.userForm.id=?",userFormId);
	}

}
