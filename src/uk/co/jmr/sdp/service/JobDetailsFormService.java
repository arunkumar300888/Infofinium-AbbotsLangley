package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.JobDetailsForm;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.User;

public interface JobDetailsFormService {
	
	public void save(JobDetailsForm newJobDetailsForm);
	
	public JobDetailsForm findJobDetailsFormById(long id);
	
	public List<JobDetailsForm> findJobDetailsFormByCreatedBy(User createdBy);
	
	public List<JobDetailsForm> findJobDetailsFormByPropertyDetailsForm(PropertyDetailsForm propertyDetailsForm);
	
	public List<JobDetailsForm> findJobDetailsFormByTenancyForm(TenancyForm tenancyForm);
	
	public List<JobDetailsForm> findAllJobDetails();
	
	public List<JobDetailsForm> findJobDetailsFormByBuilderName(User user);

}
