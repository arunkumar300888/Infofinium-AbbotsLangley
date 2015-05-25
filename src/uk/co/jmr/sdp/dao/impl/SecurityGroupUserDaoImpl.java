package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.SecurityGroupUserDao;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupUser;
import uk.co.jmr.sdp.domain.User;

@Repository("securityGroupUserDao")
public class SecurityGroupUserDaoImpl implements SecurityGroupUserDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public SecurityGroupUser findSecurityGroupUser(SecurityGroup secGroup, User user) {

		@SuppressWarnings("unchecked")
		List<SecurityGroupUser> secGroupUser = this.hibernateTemplate.find(
			"from SecurityGroupUser as s where s.securityGroup=? and s.user=?", secGroup, user);
		if (secGroupUser.size() >= 1)
			return secGroupUser.get(0);
		return null;
	}

	@Override
	public void delete(SecurityGroupUser secGroupUser) {

		this.hibernateTemplate.delete(secGroupUser);
	}

}
