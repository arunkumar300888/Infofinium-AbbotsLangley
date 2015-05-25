/*package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.UserFormRefDao;
import uk.co.jmr.sdp.domain.UserFormRef;
import uk.co.jmr.webforms.db.pojo.UserForms;

@Repository("userFormRefDao")
public class UserFormRefDaoImpl implements UserFormRefDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(UserFormRef userFormRef) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(userFormRef);
	}

	@Override
	public UserFormRef findUserFormRefById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(UserFormRef.class, id);
	}

	@Override
	public UserFormRef findUserFormRefByUserFormId(UserForms userForms) {
		// TODO Auto-generated method stub
		List<UserFormRef> userFormRefs=hibernateTemplate.find("from UserFormRef where userForms=?",userForms);
		if(userFormRefs.size()>=1)
			return userFormRefs.get(0);
		return null;
	}

	@Override
	public UserFormRef findUserFormRefByFormDataTable(String formDataTable) {
		// TODO Auto-generated method stub
		List<UserFormRef> userFormRefs=hibernateTemplate.find("from UserFormRef where formDataTable=?",formDataTable);
		if(userFormRefs.size()>=1)
			return userFormRefs.get(0);
		return null;
	}

}
*/