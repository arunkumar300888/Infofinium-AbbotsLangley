package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.GivingNoticeFormDao;
import uk.co.jmr.sdp.domain.GivingNoticeForm;
import uk.co.jmr.sdp.domain.User;

@Repository("givingNoticeFormDao")
public class GivingNoticeFormDaoImpl implements GivingNoticeFormDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public void save(GivingNoticeForm givingNoticeForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(givingNoticeForm);
	}
	
	@Override				 
	public GivingNoticeForm  findGivingNoticeDetailsFormById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(GivingNoticeForm.class, id);
	}
	
	@Override
	public GivingNoticeForm findGivingNoticeDetailsFormByUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		List<GivingNoticeForm> detailsForms=hibernateTemplate.find("from GivingNoticeForm where userFormId=?",userFormId);
		if(detailsForms.size()>=1)
			return detailsForms.get(0);
		return null;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public GivingNoticeForm findGivingNoticeStatusByUserId(User userId){
		// TODO Auto-generated method stub
		List<GivingNoticeForm> detailsForms=hibernateTemplate.find("from GivingNoticeForm where userId=?", userId);
				if(detailsForms.size()>=1)
					return detailsForms.get(0);
				return null;
	}
}
