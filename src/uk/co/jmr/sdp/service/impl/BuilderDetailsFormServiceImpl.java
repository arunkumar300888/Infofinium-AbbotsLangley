package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.BuilderDetailsFormDao;
import uk.co.jmr.sdp.domain.BuilderDetailsForm;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.BuilderDetailsFormService;

@Service("builderDetailsFormService")
public class BuilderDetailsFormServiceImpl implements BuilderDetailsFormService{

	@Autowired
	private BuilderDetailsFormDao builderDetailsFormDao;

	@Override
	public void save(BuilderDetailsForm builderDetailsForm) {
		// TODO Auto-generated method stub
		this.builderDetailsFormDao.save(builderDetailsForm);
	}

	@Override
	public BuilderDetailsForm findBuilderDetailsFormById(long id) {
		// TODO Auto-generated method stub
		return this.builderDetailsFormDao.findBuilderDetailsFormById(id);
	}

	@Override
	public List<BuilderDetailsForm> findBuilderDetailsFormByCreatedBy(User user) {
		// TODO Auto-generated method stub
		return this.builderDetailsFormDao.findBuilderDetailsFormByCreatedBy(user);
	}

	@Override
	public List<BuilderDetailsForm> findAllBuilderDetailsForm() {
		// TODO Auto-generated method stub
		return this.builderDetailsFormDao.findAllBuilderDetailsForm();
	}
}
