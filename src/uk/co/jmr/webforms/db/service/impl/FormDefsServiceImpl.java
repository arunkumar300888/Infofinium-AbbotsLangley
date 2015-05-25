package uk.co.jmr.webforms.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.webforms.db.dao.FormDefsDao;
import uk.co.jmr.webforms.db.pojo.FormDefs;
import uk.co.jmr.webforms.db.service.FormDefsService;

@Service("formDefsService")
public class FormDefsServiceImpl implements FormDefsService {
	@Autowired
	private FormDefsDao formDefsDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FormDefs findFormDefsById(long id) {

		return this.formDefsDao.findFormDefsById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FormDefs findFormDefsByName(String name) {

		return this.formDefsDao.findFormDefsByName(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(FormDefs obj) {

		this.formDefsDao.save(obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		FormDefs obj = this.formDefsDao.findFormDefsById(id);
		this.formDefsDao.delete(obj);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormDefs> findAllFormDefs() {

		return this.formDefsDao.findAllFormDefs();
	}
}
