package uk.co.jmr.webforms.db.service;

import java.util.List;
import uk.co.jmr.webforms.db.pojo.FormDefs;

public interface FormDefsService {
	FormDefs findFormDefsById(long id);

	FormDefs findFormDefsByName(String name);

	void save(FormDefs obj);

	void delete(long id);

	List<FormDefs> findAllFormDefs();
}
