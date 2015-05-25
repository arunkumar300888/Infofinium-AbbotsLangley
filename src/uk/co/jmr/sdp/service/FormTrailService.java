package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.FormTrail;

public interface FormTrailService {
	
	void save(FormTrail formTrail);
	List<FormTrail> findFormTrailForUserFormId(long userFormId);
}
