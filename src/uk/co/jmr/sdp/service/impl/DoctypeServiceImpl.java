package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.DoctypeDao;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.service.DoctypeService;

@Service("doctypeService")
public class DoctypeServiceImpl implements DoctypeService {

	@Autowired
	private DoctypeDao doctypeDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Doctype findDoctypeById(long id) {

		return this.doctypeDao.findDoctypeById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Doctype findDoctypeByDoctypeName(String doctypeName) {

		return this.doctypeDao.findDoctypeByDoctypeName(doctypeName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Doctype doctype) {

		this.doctypeDao.save(doctype);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		Doctype doctype = this.doctypeDao.findDoctypeById(id);
		this.doctypeDao.delete(doctype);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Doctype> findAllDoctype() {

		return this.doctypeDao.findAllDoctype();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Doctype> findAllDoctypeWithInActive() {

		return this.doctypeDao.findAllDoctypeWithInActive();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Doctype checkDoctypeExists(String doctypeName, String abbreviation) {

		return this.doctypeDao.checkDoctypeExists(doctypeName, abbreviation);
	}

}
