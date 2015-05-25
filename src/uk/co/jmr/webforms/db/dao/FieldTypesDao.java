package uk.co.jmr.webforms.db.dao;

import java.util.List;

import uk.co.jmr.webforms.db.pojo.FieldTypes;

public interface FieldTypesDao {
	FieldTypes findFieldTypesById(long id);

	FieldTypes findFieldTypesByName(String name);

	void save(FieldTypes obj);

	void delete(FieldTypes obj);

	List<FieldTypes> findAllFieldTypes();
}
