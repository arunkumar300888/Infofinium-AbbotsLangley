package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.BuilderDetailsForm;
import uk.co.jmr.sdp.domain.User;

public interface BuilderDetailsFormService {

	public void save(BuilderDetailsForm builderDetailsForm);
	
	public BuilderDetailsForm findBuilderDetailsFormById(long id);
	
	public List<BuilderDetailsForm> findBuilderDetailsFormByCreatedBy(User user);
	
	public List<BuilderDetailsForm> findAllBuilderDetailsForm();
}
