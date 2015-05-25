package uk.co.jmr.sdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.GivingNoticeFormDao;
import uk.co.jmr.sdp.domain.GivingNoticeForm;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.GivingNoticeFormService;

@Service("givingNoticeFormService")
public class GivingNoticeFormServiceImpl  implements GivingNoticeFormService {
	
	@Autowired
	private GivingNoticeFormDao givingNoticeFormDao;
	
	@Override
	public void save(GivingNoticeForm givingNoticeForm) {
		// TODO Auto-generated method stub
		this.givingNoticeFormDao.save(givingNoticeForm);
	}
	
	@Override
	public GivingNoticeForm findGivingNoticeDetailsFormById(long id) {
		// TODO Auto-generated method stub
		return this.givingNoticeFormDao.findGivingNoticeDetailsFormById(id);
	}
	
	@Override
	public GivingNoticeForm findGivingNoticeDetailsFormByUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		return this.givingNoticeFormDao.findGivingNoticeDetailsFormByUserFormId(userFormId);
	}

	@Override
	public GivingNoticeForm findGivingNoticeStatusByUserId(User userId){
		// TODO Auto-generated method stub
		return this.givingNoticeFormDao.findGivingNoticeStatusByUserId(userId);
	}
}
