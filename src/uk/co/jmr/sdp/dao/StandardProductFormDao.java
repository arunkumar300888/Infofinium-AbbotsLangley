package uk.co.jmr.sdp.dao;

import uk.co.jmr.sdp.domain.StandardProductForm;

public interface StandardProductFormDao {

	public void save(StandardProductForm standardProductForm);
	
	public StandardProductForm findStandardProductFormById(long id);
	
	public StandardProductForm findStandardProductFormByUserFormId(long userFormId);
	
}
