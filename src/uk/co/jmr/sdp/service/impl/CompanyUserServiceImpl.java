package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.CompanyUserDao;
import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.service.CompanyUserService;

@Service("companyUserService")
public class CompanyUserServiceImpl implements CompanyUserService{

	@Autowired
	private CompanyUserDao companyUserDao;
	
	@Override
	public CompanyUser findCompanyUserById(long id) {
		// TODO Auto-generated method stub
		return this.companyUserDao.findCompanyUserById(id);
	}

	@Override
	public void save(CompanyUser companyUser) {
		// TODO Auto-generated method stub
		this.companyUserDao.save(companyUser);
	}

	@Override
	public void delete(CompanyUser companyUser) {
		// TODO Auto-generated method stub
		this.companyUserDao.delete(companyUser);
	}

	@Override
	public List<CompanyUser> findCompanyUsersForUserId(User user) {
		// TODO Auto-generated method stub
		return this.companyUserDao.findCompanyUsersForUserId(user);
	}

	@Override
	public CompanyUser findCompanyUserByUserAndAttrValue(User user,
			AttributeValue attributeValue) {
		// TODO Auto-generated method stub
		return this.companyUserDao.findCompanyUserByUserAndAttrValue(user, attributeValue);
	}

}
