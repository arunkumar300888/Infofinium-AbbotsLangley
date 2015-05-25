package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupForm;


import uk.co.jmr.webforms.db.pojo.FormDefs;

public interface SecurityGroupFormDao {
	
	SecurityGroupForm findSecurityGroupForm(SecurityGroup secGroup, FormDefs formDefs);

	void delete(SecurityGroupForm securityGroupForm);
	
	void save(SecurityGroupForm securityGroupForm);
	
	List<SecurityGroupForm> findSecurityGroupFormByformDefs(FormDefs formDefs);

}
