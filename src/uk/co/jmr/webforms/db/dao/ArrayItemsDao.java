package uk.co.jmr.webforms.db.dao;

import java.util.List;

import uk.co.jmr.webforms.db.pojo.ArrayItems;

public interface ArrayItemsDao {
	ArrayItems findArrayItemsById(long id);

	void save(ArrayItems obj);

	void delete(ArrayItems obj);

	List<ArrayItems> findAllArrayItems();
}
