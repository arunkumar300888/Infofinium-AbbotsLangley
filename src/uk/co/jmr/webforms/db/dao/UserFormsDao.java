package uk.co.jmr.webforms.db.dao;

import java.util.List;

import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;

public interface UserFormsDao {
	void save(UserForms obj);

	void delete(UserForms obj);

	UserForms findUserFormsById(long id);

	List<UserForms> findAllUserForms();

	List<UserForms> findUserFormsByUserId(long userId);

	List<UserForms> findUserFormsByUserIdByStatus(long userId, char status);

	UserForms findUserFormsByCaseId(long caseId);
	
	List<UserForms> findUserFormsByFormsAndStatus(Forms forms,char status);

	UserForms findUserFormsByTitle(String title);
}
