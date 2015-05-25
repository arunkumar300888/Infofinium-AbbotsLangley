package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.FormVals;

public interface FormValsService {
	
	public FormVals findFormValsById(long id);
	/*public FormVals findFormValsByUserFormId(long userFormId);*/
	public List<FormVals> findAllFormVals();
	
	public void save(FormVals formVals);

}
