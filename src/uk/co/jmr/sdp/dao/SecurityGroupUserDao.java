package uk.co.jmr.sdp.dao;

import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupUser;
import uk.co.jmr.sdp.domain.User;

public interface SecurityGroupUserDao {

	SecurityGroupUser findSecurityGroupUser(SecurityGroup secGroup, User user);

	void delete(SecurityGroupUser secGroupUser);
}
