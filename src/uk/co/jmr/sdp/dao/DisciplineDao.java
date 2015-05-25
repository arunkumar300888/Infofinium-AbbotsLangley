package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Discipline;

public interface DisciplineDao {
	Discipline findDisciplineById(long id);

	Discipline findDisciplineByDisciplineName(String disciplineName);

	void save(Discipline discipline);

	void delete(Discipline discipline);

	List<Discipline> findAllDiscipline();
}
