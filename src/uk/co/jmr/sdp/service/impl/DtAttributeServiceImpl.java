package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.DtAttributeDao;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.service.DtAttributeService;

@Service("dtAttributeService")
public class DtAttributeServiceImpl implements DtAttributeService {

	@Autowired
	private DtAttributeDao dtAttributeDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Attribute findDtAttributeById(long id) {

		return this.dtAttributeDao.findDtAttributeById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Attribute findDtAttributeByName(String name) {

		return this.dtAttributeDao.findDtAttributeByName(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Attribute dtAttr) {

		this.dtAttributeDao.save(dtAttr);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Attribute dtAttr) {

		this.dtAttributeDao.delete(dtAttr);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Attribute> findAllDtAttrs() {

		return this.dtAttributeDao.findAllDtAttrs();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Attribute findAttributeByOrder(int order) {

		//System.out.println("In Attribute service ORDER-> " + order);
		return this.dtAttributeDao.findAttributeByOrder(order);
	}

}
