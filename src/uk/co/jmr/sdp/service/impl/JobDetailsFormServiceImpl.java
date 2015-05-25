package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.JobDetailsFormDao;
import uk.co.jmr.sdp.domain.BuilderDetailsForm;
import uk.co.jmr.sdp.domain.JobDetailsForm;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.JobDetailsFormService;


@Service("jobDetailsFormService")
public class JobDetailsFormServiceImpl implements JobDetailsFormService {
	
	@Autowired
	private JobDetailsFormDao jobDetailsFormDao;
	
	
	@Override 
	public void save(JobDetailsForm jobDetailsForm){
		this.jobDetailsFormDao.save(jobDetailsForm);
	}
	
	@Override
	public JobDetailsForm findJobDetailsFormById(long id) {
		// TODO Auto-generated method stub
		return this.jobDetailsFormDao.findJobDetailsFormById(id);
	}

	@Override
	public List<JobDetailsForm> findJobDetailsFormByCreatedBy(User createdBy) {
		// TODO Auto-generated method stub
		return this.jobDetailsFormDao.findJobDetailsFormByCreatedBy(createdBy);
	}

	@Override
	public List<JobDetailsForm> findJobDetailsFormByPropertyDetailsForm(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		return this.jobDetailsFormDao.findJobDetailsFormByPropertyDetailsForm(propertyDetailsForm);
	}

	@Override
	public List<JobDetailsForm> findJobDetailsFormByTenancyForm(TenancyForm tenancyForm) {
		// TODO Auto-generated method stub
		return this.jobDetailsFormDao.findJobDetailsFormByTenancyForm(tenancyForm);
	}

	@Override
	public List<JobDetailsForm> findAllJobDetails() {
		// TODO Auto-generated method stub
		return this.jobDetailsFormDao.findAllJobDetails();
	}
	
	@Override
	public List<JobDetailsForm> findJobDetailsFormByBuilderName(User user){
		// TODO Auto-generated method stub
				return this.jobDetailsFormDao.findJobDetailsFormByBuilderName(user);
	}

}
