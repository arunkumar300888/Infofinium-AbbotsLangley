package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.FormVals;

public interface FormValsDao {

	
	public FormVals findFormValsById(long id);
	/*public FormVals findFormValsByUserFormId(long userFormId);*/
	public List<FormVals> findAllFormVals();
	public void save(FormVals formVals);
}
