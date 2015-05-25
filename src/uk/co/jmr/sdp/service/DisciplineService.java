package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.Discipline;

public interface DisciplineService {
	Discipline findDisciplineById(long id);

	Discipline findDisciplineByDisciplineName(String disciplineName);

	void save(Discipline discipline);

	void delete(long id);

	List<Discipline> findAllDiscipline();
}
