package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.SecurityGroupDao;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.SecurityGroupService;

@Service("securityGroupService")
public class SecurityGroupServiceImpl implements SecurityGroupService {

	@Autowired
	private SecurityGroupDao securityGroupDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SecurityGroup findSecurityGroupById(long sgId) {

		return this.securityGroupDao.findSecurityGroupById(sgId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SecurityGroup findSecurityGroupByName(String name) {

		return this.securityGroupDao.findSecurityGroupByName(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveSecurityGroup(SecurityGroup securityGroup) {

		this.securityGroupDao.saveSecurityGroup(securityGroup);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSecurityGroup(SecurityGroup securityGroup) {

		this.securityGroupDao.deleteSecurityGroup(securityGroup);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SecurityGroup> findSecurityGroupsForUser(User user) {

		return this.securityGroupDao.findSecurityGroupsForUser(user);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SecurityGroup> findAllSecurityGroups() {

		return this.securityGroupDao.findAllSecurityGroups();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SecurityGroup> findAllSecurityGroupsWithInActive() {

		return this.securityGroupDao.findAllSecurityGroupsWithInActive();
	}

}
