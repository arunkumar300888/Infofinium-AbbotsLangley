package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Doctype;

public interface DoctypeDao {
	Doctype findDoctypeById(long id);

	Doctype findDoctypeByDoctypeName(String doctypeName);

	void save(Doctype doctype);

	void delete(Doctype doctype);

	List<Doctype> findAllDoctype();

	List<Doctype> findAllDoctypeWithInActive();

	Doctype checkDoctypeExists(String doctypeName, String abbreviation);
}
