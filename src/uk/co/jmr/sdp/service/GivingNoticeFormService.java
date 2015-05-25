package uk.co.jmr.sdp.service;


import uk.co.jmr.sdp.domain.GivingNoticeForm;
import uk.co.jmr.sdp.domain.User;

public interface GivingNoticeFormService {

	public void save(GivingNoticeForm givingNoticeForm);
	
	public GivingNoticeForm findGivingNoticeDetailsFormById(long id);
	
	public GivingNoticeForm findGivingNoticeDetailsFormByUserFormId(long userFormId);
	
	public GivingNoticeForm findGivingNoticeStatusByUserId(User userId);
	
}
