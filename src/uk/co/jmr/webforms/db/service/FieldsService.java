package uk.co.jmr.webforms.db.service;

import java.util.List;
import uk.co.jmr.webforms.db.pojo.Fields;

public interface FieldsService {
	Fields findFieldsById(long id);

	Fields findFieldsByName(String name);

	void save(Fields obj);

	void delete(long id);

	List<Fields> findAllFields();
}
