package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.FormValsDao;
import uk.co.jmr.sdp.domain.FormVals;

@Repository("formValsDao")
public class FormValsDaoImpl implements FormValsDao{

	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public FormVals findFormValsById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(FormVals.class, id);
	}

/*	@Override
	public FormVals findFormValsByUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		List<FormVals> formVals=hibernateTemplate.find("from FormVals where userFormId=?",userFormId);
		if(formVals.size()>=1)
			return formVals.get(0);
		return null;
	}*/

	@Override
	public List<FormVals> findAllFormVals() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from FromVals");
	}

	@Override
	public void save(FormVals formVals) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(formVals);
	}
	
	
	
}
