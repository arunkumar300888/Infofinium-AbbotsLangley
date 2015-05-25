package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.CheckingOutFormDao;
import uk.co.jmr.sdp.domain.CheckingOutForm;
import uk.co.jmr.sdp.domain.User;


@Repository("checkingOutFormDao")
public class CheckingOutFormDaoImpl implements CheckingOutFormDao {
	
	
	@Autowired
	
	private HibernateTemplate hibernateTemplate;
	

	@Override
	public void save(CheckingOutForm checkingOutForm) {
		// TODO Auto-generated method stub

		hibernateTemplate.saveOrUpdate(checkingOutForm);
	}

	@Override
	public CheckingOutForm findCheckingOutFormById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(CheckingOutForm.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CheckingOutForm> findCheckingOutFormByCreatedBy(User user) {
		// TODO Auto-generated method stub
		
		List<CheckingOutForm> checkingOutForms=hibernateTemplate.find("from CheckingOutForm where createdBy=?",user);
		/*if(checkingOutForms.size()>=1)
			return checkingOutForms.get(0);*/
		return checkingOutForms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CheckingOutForm> findAllCheckingOut() {
		// TODO Auto-generated method stub
		List<CheckingOutForm> checkingOutForms=hibernateTemplate.find("from CheckingOutForm");
		return checkingOutForms;
	}
	
	

}
