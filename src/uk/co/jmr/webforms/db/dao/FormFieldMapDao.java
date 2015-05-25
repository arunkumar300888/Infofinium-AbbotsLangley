package uk.co.jmr.webforms.db.dao;

import java.util.List;

import uk.co.jmr.webforms.db.pojo.FormFieldMap;

public interface FormFieldMapDao {
	FormFieldMap findFormFieldMapById(long id);

	FormFieldMap findFormFieldMapByName(String name);

	void save(FormFieldMap obj);

	void delete(FormFieldMap obj);

	List<FormFieldMap> findAllFormFieldMap();
}
