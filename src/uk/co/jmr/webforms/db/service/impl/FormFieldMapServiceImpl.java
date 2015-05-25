package uk.co.jmr.webforms.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.webforms.db.dao.FormFieldMapDao;
import uk.co.jmr.webforms.db.pojo.FormFieldMap;
import uk.co.jmr.webforms.db.service.FormFieldMapService;

@Service("formFieldMapService")
public class FormFieldMapServiceImpl implements FormFieldMapService {
	@Autowired
	private FormFieldMapDao formFieldMapDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FormFieldMap findFormFieldMapById(long id) {

		return this.formFieldMapDao.findFormFieldMapById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FormFieldMap findFormFieldMapByName(String name) {

		return this.formFieldMapDao.findFormFieldMapByName(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(FormFieldMap obj) {

		this.formFieldMapDao.save(obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		FormFieldMap obj = this.formFieldMapDao.findFormFieldMapById(id);
		this.formFieldMapDao.delete(obj);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormFieldMap> findAllFormFieldMap() {

		return this.formFieldMapDao.findAllFormFieldMap();
	}
}
