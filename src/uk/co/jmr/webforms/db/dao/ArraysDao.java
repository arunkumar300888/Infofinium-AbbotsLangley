package uk.co.jmr.webforms.db.dao;

import java.util.List;

import uk.co.jmr.webforms.db.pojo.Arrays;

public interface ArraysDao {
	Arrays findArraysById(long id);

	Arrays findArraysByName(String name);

	void save(Arrays obj);

	void delete(Arrays obj);

	List<Arrays> findAllArrays();
}
