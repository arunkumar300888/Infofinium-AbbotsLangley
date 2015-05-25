package uk.co.jmr.webforms.db.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.webforms.db.dao.UserFormsDao;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;

@Repository("userFormsDao")
public class UserFormsDaoImpl implements UserFormsDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(UserForms obj) {

		hibernateTemplate.saveOrUpdate(obj);
	}

	@Override
	public void delete(UserForms obj) {

		hibernateTemplate.delete(obj);
	}

	@Override
	public UserForms findUserFormsById(long id) {

		return hibernateTemplate.get(UserForms.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserForms> findAllUserForms() {

		return hibernateTemplate.find("from UserForms");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserForms> findUserFormsByUserId(long userId) {

		return hibernateTemplate.find("from UserForms where user_id=?", userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserForms> findUserFormsByUserIdByStatus(long userId, char status) {

		return hibernateTemplate.find("from UserForms where userId = ? and status = ? and active=? order by createdOn", userId, status,'Y');
	}

	@Override
	public UserForms findUserFormsByCaseId(long caseId) {

		@SuppressWarnings("unchecked")
		List<UserForms> userForms = hibernateTemplate.find("from UserForms where case_id=?", caseId);
		if (userForms.size() >= 1)
			return userForms.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserForms> findUserFormsByFormsAndStatus(Forms forms,
			char status) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from UserForms where forms=? and status=?",forms,status);
	}

	@Override
	public UserForms findUserFormsByTitle(String title) {
		// TODO Auto-generated method stub
		List<UserForms> userForms = hibernateTemplate.find("from UserForms where title=?", title);
		if (userForms.size() >= 1)
			return userForms.get(0);
		return null;
	}

}
