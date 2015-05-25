package uk.co.jmr.webforms.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.webforms.db.dao.FormDataDao;
import uk.co.jmr.webforms.db.pojo.FormData;
import uk.co.jmr.webforms.db.pojo.UserForms;
import uk.co.jmr.webforms.db.service.FormDataService;

@Service("formDataService")
public class FormDataServiceImpl implements FormDataService {
	@Autowired
	private FormDataDao formDataDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FormData findFormDataById(long id) {

		return this.formDataDao.findFormDataById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(FormData obj) {

		this.formDataDao.save(obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		FormData obj = this.formDataDao.findFormDataById(id);
		this.formDataDao.delete(obj);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FormData> findAllFormData() {

		return this.formDataDao.findAllFormData();
	}

	@Override
	public List<FormData> findFormDatasByUserForm(UserForms userForms) {
		// TODO Auto-generated method stub
		return this.formDataDao.findFormDatasByUserForm(userForms);
	}
}
