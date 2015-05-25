package uk.co.jmr.webforms.db.service;

import java.sql.Date;
import java.util.List;

import uk.co.jmr.webforms.db.pojo.FormDefs;
import uk.co.jmr.webforms.db.pojo.Forms;

public interface FormsService {
	Forms findFormsById(long id);

	List<Forms> findFormsByName(String name);

	List<Forms> findFormsByFormDefNameByDate(String name, Date date);

	void save(Forms obj);

	void delete(long id);

	List<Forms> findAllForms();

	Forms findFormsByFormDefId(FormDefs fd);
}
