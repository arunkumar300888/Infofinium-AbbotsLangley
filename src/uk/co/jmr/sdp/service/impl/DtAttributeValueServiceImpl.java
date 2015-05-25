package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.DtAttributeValueDao;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.service.DtAttributeValueService;

@Service("dtAttributeValueService")
public class DtAttributeValueServiceImpl implements DtAttributeValueService {

	@Autowired
	private DtAttributeValueDao dtAttributeValueDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AttributeValue findDtAttrValueById(long id) {

		return this.dtAttributeValueDao.findDtAttrValueById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AttributeValue findDtAttrValueByName(String name) {

		return this.dtAttributeValueDao.findDtAttrValueByName(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(AttributeValue dtAttrValue) {

		this.dtAttributeValueDao.save(dtAttrValue);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(AttributeValue dtAttrValue) {

		this.dtAttributeValueDao.delete(dtAttrValue);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AttributeValue> findAllDtAttrValues() {

		return this.dtAttributeValueDao.findAllDtAttrValues();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AttributeValue> findAllDtAttrValuesWithInActive() {

		return this.dtAttributeValueDao.findAllDtAttrValuesWithInActive();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AttributeValue findDtAttrValueByNameAndAbbreviation(String attrValue, String abbreviation) {

		return this.dtAttributeValueDao.findDtAttrValueByNameAndAbbreviation(attrValue, abbreviation);
	}

}
