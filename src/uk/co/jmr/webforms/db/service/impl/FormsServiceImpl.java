package uk.co.jmr.webforms.db.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.webforms.db.dao.FormsDao;
import uk.co.jmr.webforms.db.pojo.FormDefs;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.service.FormsService;

@Service("formsService")
public class FormsServiceImpl implements FormsService {
	@Autowired
	private FormsDao formsDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Forms findFormsById(long id) {

		return this.formsDao.findFormsById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Forms> findFormsByName(String name) {

		return this.formsDao.findFormsByName(name);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Forms> findFormsByFormDefNameByDate(String name, Date date) {

		return this.formsDao.findFormsByFormDefNameByDate(name, date);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Forms obj) {

		this.formsDao.save(obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		Forms obj = this.formsDao.findFormsById(id);
		this.formsDao.delete(obj);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Forms> findAllForms() {

		return this.formsDao.findAllForms();
	}

	@Override
	public Forms findFormsByFormDefId(FormDefs fd) {
		// TODO Auto-generated method stub
		return this.formsDao.findFormsByFormDefId(fd);
	}
}
