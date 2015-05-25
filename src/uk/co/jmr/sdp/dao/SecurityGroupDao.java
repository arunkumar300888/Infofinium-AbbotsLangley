package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupCombo;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

public interface SecurityGroupDao {

	SecurityGroup findSecurityGroupById(long sgId);

	SecurityGroup findSecurityGroupByName(String name);

	void saveSecurityGroup(SecurityGroup securityGroup);

	void deleteSecurityGroup(SecurityGroup securityGroup);

	List<SecurityGroup> findSecurityGroupsForUser(User user);

	List<SecurityGroup> findAllSecurityGroups();

	List<SecurityGroup> findAllSecurityGroupsWithInActive();


}
