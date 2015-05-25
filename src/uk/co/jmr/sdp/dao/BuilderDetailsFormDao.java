package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.BuilderDetailsForm;
import uk.co.jmr.sdp.domain.User;

public interface BuilderDetailsFormDao {

	public void save(BuilderDetailsForm builderDetailform);
	
	public BuilderDetailsForm findBuilderDetailsFormById(long id);
	
	public List<BuilderDetailsForm> findBuilderDetailsFormByCreatedBy(User user);
	
	public List<BuilderDetailsForm> findAllBuilderDetailsForm();
	
	
	
	
}
