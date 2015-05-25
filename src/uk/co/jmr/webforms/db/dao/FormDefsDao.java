package uk.co.jmr.webforms.db.dao;

import java.util.List;

import uk.co.jmr.webforms.db.pojo.FormDefs;

public interface FormDefsDao {
	FormDefs findFormDefsById(long id);

	FormDefs findFormDefsByName(String name);

	void save(FormDefs obj);

	void delete(FormDefs obj);

	List<FormDefs> findAllFormDefs();
}
