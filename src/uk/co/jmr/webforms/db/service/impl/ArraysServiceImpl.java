package uk.co.jmr.webforms.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.webforms.db.dao.ArraysDao;
import uk.co.jmr.webforms.db.pojo.Arrays;
import uk.co.jmr.webforms.db.service.ArraysService;

@Service("arraysService")
public class ArraysServiceImpl implements ArraysService {
	@Autowired
	private ArraysDao arraysDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Arrays findArraysById(long id) {

		return this.arraysDao.findArraysById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Arrays findArraysByName(String name) {

		return this.arraysDao.findArraysByName(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Arrays obj) {

		this.arraysDao.save(obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		Arrays obj = this.arraysDao.findArraysById(id);
		this.arraysDao.delete(obj);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Arrays> findAllArrays() {

		return this.arraysDao.findAllArrays();
	}
}
