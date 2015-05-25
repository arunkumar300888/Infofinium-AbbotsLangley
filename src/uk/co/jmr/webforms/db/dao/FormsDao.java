package uk.co.jmr.webforms.db.dao;

import java.sql.Date;
import java.util.List;

import uk.co.jmr.webforms.db.pojo.FormDefs;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.pojo.UserForms;

public interface FormsDao {
	Forms findFormsById(long id);

	List<Forms> findFormsByName(String name);

	List<Forms> findFormsByFormDefNameByDate(String name, Date date);

	void save(Forms obj);

	void delete(Forms obj);

	List<Forms> findAllForms();

	Forms findFormsByFormDefId(FormDefs fd);

}
