package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.DisciplineDao;
import uk.co.jmr.sdp.domain.Discipline;

@Repository("disciplineDao")
public class DisciplineDaoImpl implements DisciplineDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Discipline> findAllDiscipline() {

		return hibernateTemplate.find("from Discipline");
	}

	@Override
	public Discipline findDisciplineById(long id) {

		return hibernateTemplate.get(Discipline.class, id);
	}

	@Override
	public Discipline findDisciplineByDisciplineName(String disciplineName) {

		return (Discipline) hibernateTemplate.find("from Discipline as d where d.disciplineName=?", disciplineName).get(0);
	}

	@Override
	public void save(Discipline discipline) {

		hibernateTemplate.saveOrUpdate(discipline);

	}

	@Override
	public void delete(Discipline discipline) {

		hibernateTemplate.delete(discipline);

	}

}
