package uk.co.jmr.webforms.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.webforms.db.dao.UserFormsDao;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;
import uk.co.jmr.webforms.db.service.UserFormsService;

@Service("userFormsService")
public class UserFormsServiceImpl implements UserFormsService {
	@Autowired
	private UserFormsDao userFormsDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserForms findUserFormsById(long id) {

		return this.userFormsDao.findUserFormsById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(UserForms obj) {

		this.userFormsDao.save(obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		UserForms obj = this.userFormsDao.findUserFormsById(id);
		this.userFormsDao.delete(obj);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserForms> findAllUserForms() {

		return this.userFormsDao.findAllUserForms();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserForms findUserFormsByCaseId(long caseId) {

		return this.userFormsDao.findUserFormsByCaseId(caseId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserForms> findUserFormsByFormsAndStatus(Forms forms,
			char status) {
		// TODO Auto-generated method stub
		return this.userFormsDao.findUserFormsByFormsAndStatus(forms, status);
	}

	@Override
	public UserForms findUserFormsByTitle(String title) {
		// TODO Auto-generated method stub
		return this.userFormsDao.findUserFormsByTitle(title);
	}
}
