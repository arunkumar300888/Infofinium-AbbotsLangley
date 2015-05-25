package uk.co.jmr.webforms.db.dao;

import java.util.List;

import uk.co.jmr.webforms.db.pojo.Fields;

public interface FieldsDao {
	Fields findFieldsById(long id);

	Fields findFieldsByName(String name);

	void save(Fields obj);

	void delete(Fields obj);

	List<Fields> findAllFields();
}
