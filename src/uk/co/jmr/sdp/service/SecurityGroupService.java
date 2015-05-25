package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.User;

public interface SecurityGroupService {

	SecurityGroup findSecurityGroupById(long sgId);

	SecurityGroup findSecurityGroupByName(String name);

	void saveSecurityGroup(SecurityGroup securityGroup);

	void deleteSecurityGroup(SecurityGroup securityGroup);

	List<SecurityGroup> findSecurityGroupsForUser(User user);

	List<SecurityGroup> findAllSecurityGroups();

	List<SecurityGroup> findAllSecurityGroupsWithInActive();
}
