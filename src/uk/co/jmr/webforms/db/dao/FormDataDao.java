package uk.co.jmr.webforms.db.dao;

import java.util.List;

import uk.co.jmr.webforms.db.pojo.FormData;
import uk.co.jmr.webforms.db.pojo.UserForms;

public interface FormDataDao {
	
	List<FormData> findFormDatasByUserForm(UserForms userForms);
	
	FormData findFormDataById(long id);

	void save(FormData obj);

	void delete(FormData obj);

	List<FormData> findAllFormData();
}
