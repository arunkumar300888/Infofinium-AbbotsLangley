package uk.co.jmr.webforms.db.service;

import java.util.List;
import uk.co.jmr.webforms.db.pojo.FieldTypes;

public interface FieldTypesService {
	FieldTypes findFieldTypesById(int id);

	FieldTypes findFieldTypesByName(String name);

	void save(FieldTypes obj);

	void delete(int id);

	List<FieldTypes> findAllFieldTypes();
}
