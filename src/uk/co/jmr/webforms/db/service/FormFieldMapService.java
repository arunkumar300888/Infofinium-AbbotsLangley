package uk.co.jmr.webforms.db.service;

import java.util.List;
import uk.co.jmr.webforms.db.pojo.FormFieldMap;

public interface FormFieldMapService {
	FormFieldMap findFormFieldMapById(long id);

	FormFieldMap findFormFieldMapByName(String name);

	void save(FormFieldMap obj);

	void delete(long id);

	List<FormFieldMap> findAllFormFieldMap();
}
