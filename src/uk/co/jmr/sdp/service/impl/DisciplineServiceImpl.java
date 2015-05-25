package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.DisciplineDao;
import uk.co.jmr.sdp.domain.Discipline;
import uk.co.jmr.sdp.service.DisciplineService;

@Service("disciplineService")
public class DisciplineServiceImpl implements DisciplineService {

	@Autowired
	private DisciplineDao disciplineDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Discipline findDisciplineById(long id) {

		return this.disciplineDao.findDisciplineById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Discipline findDisciplineByDisciplineName(String disciplineName) {

		return this.disciplineDao.findDisciplineByDisciplineName(disciplineName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Discipline discipline) {

		this.disciplineDao.save(discipline);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		Discipline discipline = this.disciplineDao.findDisciplineById(id);
		this.disciplineDao.delete(discipline);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Discipline> findAllDiscipline() {

		return this.disciplineDao.findAllDiscipline();
	}

}
