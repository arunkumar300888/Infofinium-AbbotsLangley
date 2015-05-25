package uk.co.jmr.webforms.db.service;

import java.util.List;
import uk.co.jmr.webforms.db.pojo.Arrays;

public interface ArraysService {
	Arrays findArraysById(long id);

	Arrays findArraysByName(String name);

	void save(Arrays obj);

	void delete(long id);

	List<Arrays> findAllArrays();
}
