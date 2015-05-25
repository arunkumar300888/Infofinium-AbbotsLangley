package uk.co.jmr.webforms.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.webforms.db.dao.FieldsDao;
import uk.co.jmr.webforms.db.pojo.Fields;
import uk.co.jmr.webforms.db.service.FieldsService;

@Service("fieldsService")
public class FieldsServiceImpl implements FieldsService {
	@Autowired
	private FieldsDao fieldsDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Fields findFieldsById(long id) {

		return this.fieldsDao.findFieldsById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Fields findFieldsByName(String name) {

		return this.fieldsDao.findFieldsByName(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Fields obj) {

		this.fieldsDao.save(obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		Fields obj = this.fieldsDao.findFieldsById(id);
		this.fieldsDao.delete(obj);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Fields> findAllFields() {

		return this.fieldsDao.findAllFields();
	}
}
