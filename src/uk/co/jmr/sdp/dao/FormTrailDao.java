package uk.co.jmr.sdp.dao;

import java.util.List;


import uk.co.jmr.sdp.domain.FormTrail;

public interface FormTrailDao {
	
	void save(FormTrail formTrail);
	List<FormTrail> findFormTrailForUserFormId(long userFormId);
	
}
