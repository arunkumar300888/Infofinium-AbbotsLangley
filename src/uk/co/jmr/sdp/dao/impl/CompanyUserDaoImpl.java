package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.CompanyUserDao;
import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

@Repository("companyUserDao")
public class CompanyUserDaoImpl implements CompanyUserDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public CompanyUser findCompanyUserById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(CompanyUser.class, id);
	}

	@Override
	public void save(CompanyUser companyUser) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(companyUser);
	}

	@Override
	public void delete(CompanyUser companyUser) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(companyUser);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyUser> findCompanyUsersForUserId(User user) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from CompanyUser where user=?",user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CompanyUser findCompanyUserByUserAndAttrValue(User user,
			AttributeValue attributeValue) {
		// TODO Auto-generated method stub
		List<CompanyUser> cus= hibernateTemplate.find("from CompanyUser where user=? and attrValue=?",user,attributeValue);
		if(cus.size()>=1)
			return cus.get(0);
		return null;
	}

}
