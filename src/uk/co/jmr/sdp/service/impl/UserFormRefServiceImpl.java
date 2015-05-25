/*package uk.co.jmr.sdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.UserFormRefDao;
import uk.co.jmr.sdp.domain.UserFormRef;
import uk.co.jmr.sdp.service.UserFormRefService;
import uk.co.jmr.webforms.db.pojo.UserForms;

@Service("userFormRefService")
public class UserFormRefServiceImpl implements UserFormRefService{
	
	@Autowired
	private UserFormRefDao userFormRefDao;

	@Override
	public void save(UserFormRef userFormRef) {
		// TODO Auto-generated method stub
		this.userFormRefDao.save(userFormRef);
	}

	@Override
	public UserFormRef findUserFormRefById(long id) {
		// TODO Auto-generated method stub
		return this.userFormRefDao.findUserFormRefById(id);
	}

	@Override
	public UserFormRef findUserFormRefByUserFormId(UserForms userForms) {
		// TODO Auto-generated method stub
		return this.userFormRefDao.findUserFormRefByUserFormId(userForms);
	}

	@Override
	public UserFormRef findUserFormRefByFormDataTable(String formDataTable) {
		// TODO Auto-generated method stub
		return this.userFormRefDao.findUserFormRefByFormDataTable(formDataTable);
	}

}
*/