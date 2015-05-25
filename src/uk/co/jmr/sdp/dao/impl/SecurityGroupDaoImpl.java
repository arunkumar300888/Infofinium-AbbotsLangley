package uk.co.jmr.sdp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.SecurityGroupDao;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupUser;
import uk.co.jmr.sdp.domain.User;

@Repository("securityGroupDao")
public class SecurityGroupDaoImpl implements SecurityGroupDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public SecurityGroup findSecurityGroupById(long sgId) {

		return this.hibernateTemplate.get(SecurityGroup.class, sgId);
	}

	@Override
	public void saveSecurityGroup(SecurityGroup securityGroup) {

		this.hibernateTemplate.saveOrUpdate(securityGroup);
	}

	@Override
	public void deleteSecurityGroup(SecurityGroup securityGroup) {

		this.hibernateTemplate.delete(securityGroup);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SecurityGroup findSecurityGroupByName(String name) {

		List<SecurityGroup> securityGroups = hibernateTemplate.find("from SecurityGroup as s where s.name=?", name);
		if (securityGroups.size() >= 1)
			return securityGroups.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityGroup> findSecurityGroupsForUser(User user) {

		List<SecurityGroupUser> securityGroupUsers = hibernateTemplate.find("from SecurityGroupUser as s where s.user=?", user);
		List<SecurityGroup> sgList = new ArrayList<SecurityGroup>();
		for (SecurityGroupUser su : securityGroupUsers) {
			sgList.add(su.getSecurityGroup());
		}
		return sgList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityGroup> findAllSecurityGroups() {

		char isActive = 'Y';
		return hibernateTemplate.find("from SecurityGroup s where s.isActive=?", isActive);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityGroup> findAllSecurityGroupsWithInActive() {

		return hibernateTemplate.find("from SecurityGroup");
	}

}
