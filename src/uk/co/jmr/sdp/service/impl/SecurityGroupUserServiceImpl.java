package uk.co.jmr.sdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.SecurityGroupUserDao;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupUser;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.SecurityGroupUserService;

@Service("securityGroupUserService")
public class SecurityGroupUserServiceImpl implements SecurityGroupUserService {

	@Autowired
	private SecurityGroupUserDao securityGroupUserDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SecurityGroupUser findSecurityGroupUser(SecurityGroup secGroup, User user) {

		return this.securityGroupUserDao.findSecurityGroupUser(secGroup, user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(SecurityGroupUser secGroupUser) {

		this.securityGroupUserDao.delete(secGroupUser);
	}

}
