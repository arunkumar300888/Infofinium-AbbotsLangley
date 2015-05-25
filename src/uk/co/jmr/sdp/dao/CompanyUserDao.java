package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.CompanyUser;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

public interface CompanyUserDao {

	CompanyUser findCompanyUserById(long id);
	
	void save(CompanyUser companyUser);
	
	void delete(CompanyUser companyUser);
	
	List<CompanyUser> findCompanyUsersForUserId(User user);
	
	CompanyUser findCompanyUserByUserAndAttrValue(User user,AttributeValue attributeValue);
}
