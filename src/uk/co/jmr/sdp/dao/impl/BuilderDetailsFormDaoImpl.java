package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.BuilderDetailsFormDao;
import uk.co.jmr.sdp.domain.BuilderDetailsForm;
import uk.co.jmr.sdp.domain.User;

@Repository("builderDetailsFormDao")
public class BuilderDetailsFormDaoImpl implements BuilderDetailsFormDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(BuilderDetailsForm builderDetailform) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(builderDetailform);
	}

	@Override
	public BuilderDetailsForm findBuilderDetailsFormById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(BuilderDetailsForm.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BuilderDetailsForm> findBuilderDetailsFormByCreatedBy(User user) {
		// TODO Auto-generated method stub
		List<BuilderDetailsForm> detailsForms=hibernateTemplate.find("from BuilderDetailsForm where createdBy=?",user);
		/*if(detailsForms.size()>=1)
			return detailsForms.get(0);*/
		return detailsForms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BuilderDetailsForm> findAllBuilderDetailsForm() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from BuilderDetailsForm");
	}
	
}
