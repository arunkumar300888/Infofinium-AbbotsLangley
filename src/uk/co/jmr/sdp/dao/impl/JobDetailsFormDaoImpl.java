package uk.co.jmr.sdp.dao.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.JobDetailsFormDao;
import uk.co.jmr.sdp.domain.JobDetailsForm;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.User;

@Repository("jobDetailsFormDao")
public class JobDetailsFormDaoImpl implements JobDetailsFormDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(JobDetailsForm jobDetailsForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(jobDetailsForm);
	}

	
	@Override
	public JobDetailsForm findJobDetailsFormById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(JobDetailsForm.class, id);
	}
	
	@Override
	public List<JobDetailsForm> findJobDetailsFormByCreatedBy(User createdBy) {
		// TODO Auto-generated method stub
		List<JobDetailsForm> detailsForms= hibernateTemplate.find("from JobDetailsForm where createdBy=?",createdBy);
		/*if(detailsForms.size()>=1)
			return detailsForms.get(0);*/
		return detailsForms;
	}


	@Override
	public List<JobDetailsForm> findJobDetailsFormByPropertyDetailsForm(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		List<JobDetailsForm> detailsForms=hibernateTemplate.find("from JobDetailsForm where linkOfPropertyForm=?",propertyDetailsForm);
		/*if(detailsForms.size()>=1)
			return detailsForms.get(0);*/
		return detailsForms;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<JobDetailsForm> findJobDetailsFormByTenancyForm(TenancyForm tenancyForm) {
		// TODO Auto-generated method stub
		List<JobDetailsForm> detailsForms=hibernateTemplate.find("from JobDetailsForm where linkOfTenantForm=?",tenancyForm);
		/*if(detailsForms.size()>=1)
			return detailsForms.get(0);*/
		return detailsForms;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<JobDetailsForm> findAllJobDetails() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from JobDetailsForm");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JobDetailsForm> findJobDetailsFormByBuilderName(User user) {
		// TODO Auto-generated method stub
		List<JobDetailsForm> detailsForms=hibernateTemplate.find("from JobDetailsForm where builder=?",user);
		/*if(detailsForms.size()>=1)
			return detailsForms.get(0);*/
		return detailsForms;
	}

}
