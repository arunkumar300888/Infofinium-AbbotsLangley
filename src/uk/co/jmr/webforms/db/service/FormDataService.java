package uk.co.jmr.webforms.db.service;

import java.util.List;
import uk.co.jmr.webforms.db.pojo.FormData;
import uk.co.jmr.webforms.db.pojo.UserForms;

public interface FormDataService {
	
	List<FormData> findFormDatasByUserForm(UserForms userForms);
	
	FormData findFormDataById(long id);

	void save(FormData obj);

	void delete(long id);

	List<FormData> findAllFormData();
}
