package uk.co.jmr.webforms.db.service;

import java.util.List;

import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;

public interface UserFormsService {
	UserForms findUserFormsById(long id);

	void save(UserForms obj);

	void delete(long id);

	List<UserForms> findAllUserForms();

	UserForms findUserFormsByCaseId(long caseId);
	
	List<UserForms> findUserFormsByFormsAndStatus(Forms forms,char status);
	
	UserForms findUserFormsByTitle(String title);
}
