package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.Doctype;

public interface DoctypeService {
	Doctype findDoctypeById(long id);

	Doctype findDoctypeByDoctypeName(String doctypeName);

	void save(Doctype doctype);

	void delete(long id);

	List<Doctype> findAllDoctype();

	List<Doctype> findAllDoctypeWithInActive();

	// For Check Doctype
	Doctype checkDoctypeExists(String doctypeName, String abbreviation);
}
