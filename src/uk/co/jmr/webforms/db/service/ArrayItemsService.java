package uk.co.jmr.webforms.db.service;

import java.util.List;
import uk.co.jmr.webforms.db.pojo.ArrayItems;

public interface ArrayItemsService {
	ArrayItems findArrayItemsById(long id);

	void save(ArrayItems obj);

	void delete(long id);

	List<ArrayItems> findAllArrayItems();
}
