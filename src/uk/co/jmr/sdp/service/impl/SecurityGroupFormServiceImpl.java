package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.SecurityGroupFormDao;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupForm;
import uk.co.jmr.sdp.service.SecurityGroupFormService;
import uk.co.jmr.webforms.db.pojo.FormDefs;

@Service("securityGroupFormService")
public class SecurityGroupFormServiceImpl implements SecurityGroupFormService{
	
	@Autowired
	private SecurityGroupFormDao securityGroupFormDao;

	@Override
	public SecurityGroupForm findSecurityGroupForm(SecurityGroup secGroup,
			FormDefs formDefs) {
		// TODO Auto-generated method stub
		return this.securityGroupFormDao.findSecurityGroupForm(secGroup, formDefs);
	}

	@Override
	public void delete(SecurityGroupForm securityGroupForm) {
		// TODO Auto-generated method stub
		this.securityGroupFormDao.delete(securityGroupForm);
	}

	@Override
	public List<SecurityGroupForm> findSecurityGroupFormByformDefs(
			FormDefs formDefs) {
		// TODO Auto-generated method stub
		return this.securityGroupFormDao.findSecurityGroupFormByformDefs(formDefs);
	}

	@Override
	public void save(SecurityGroupForm securityGroupForm) {
		// TODO Auto-generated method stub
		this.securityGroupFormDao.save(securityGroupForm);
	}

}
