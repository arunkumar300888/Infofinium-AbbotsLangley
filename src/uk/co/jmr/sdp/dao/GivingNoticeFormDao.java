package uk.co.jmr.sdp.dao;

import uk.co.jmr.sdp.domain.GivingNoticeForm;
import uk.co.jmr.sdp.domain.User;

public interface GivingNoticeFormDao {
	
	public void save(GivingNoticeForm givingNoticeForm);
	
	public GivingNoticeForm findGivingNoticeDetailsFormById(long id);
	
	public GivingNoticeForm findGivingNoticeDetailsFormByUserFormId(long userFormId);

	public GivingNoticeForm findGivingNoticeStatusByUserId(User userId);
}
