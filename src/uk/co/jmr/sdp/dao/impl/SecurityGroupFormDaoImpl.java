package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.SecurityGroupFormDao;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupForm;
import uk.co.jmr.sdp.domain.SecurityGroupUser;
import uk.co.jmr.webforms.db.pojo.FormDefs;

@Repository("securityGroupFormDao")
public class SecurityGroupFormDaoImpl implements SecurityGroupFormDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public SecurityGroupForm findSecurityGroupForm(SecurityGroup secGroup,
			FormDefs formDefs) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<SecurityGroupForm> secGroupForm = this.hibernateTemplate.find(
			"from SecurityGroupForm as s where s.securityGroup=? and s.formDefs=?", secGroup, formDefs);
		if (secGroupForm.size() >= 1)
			return secGroupForm.get(0);
		return null;
	}

	@Override
	public void delete(SecurityGroupForm securityGroupForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(securityGroupForm);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityGroupForm> findSecurityGroupFormByformDefs(
			FormDefs formDefs) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from SecurityGroupForm where formDefs=?",formDefs);
	}

	@Override
	public void save(SecurityGroupForm securityGroupForm) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(securityGroupForm);
	}

}
