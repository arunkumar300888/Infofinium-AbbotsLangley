package uk.co.jmr.sdp.service;

import uk.co.jmr.sdp.domain.StandardProductForm;

public interface StandardProductFormService {

	public void save(StandardProductForm standardProductForm);
	
	public StandardProductForm findStandardProductFormById(long id);
	
	public StandardProductForm findStandardProductFormByUserFormId(long userFormId);
}
