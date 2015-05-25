package uk.co.jmr.webforms.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.webforms.db.dao.FieldTypesDao;
import uk.co.jmr.webforms.db.pojo.FieldTypes;
import uk.co.jmr.webforms.db.service.FieldTypesService;

@Service("fieldTypesService")
public class FieldTypesServiceImpl implements FieldTypesService {
	@Autowired
	private FieldTypesDao fieldTypesDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FieldTypes findFieldTypesById(int id) {

		return this.fieldTypesDao.findFieldTypesById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FieldTypes findFieldTypesByName(String name) {

		return this.fieldTypesDao.findFieldTypesByName(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(FieldTypes obj) {

		this.fieldTypesDao.save(obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(int id) {

		FieldTypes obj = this.fieldTypesDao.findFieldTypesById(id);
		this.fieldTypesDao.delete(obj);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FieldTypes> findAllFieldTypes() {

		return this.fieldTypesDao.findAllFieldTypes();
	}
}
