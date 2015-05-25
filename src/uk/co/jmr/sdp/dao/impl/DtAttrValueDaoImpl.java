package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.DtAttributeValueDao;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

@Repository("dtAttributeValueDao")
public class DtAttrValueDaoImpl implements DtAttributeValueDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public AttributeValue findDtAttrValueById(long id) {

		return this.hibernateTemplate.get(AttributeValue.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AttributeValue findDtAttrValueByName(String name) {

		List<AttributeValue> dtAttrValues = this.hibernateTemplate.find("from AttributeValue as a where a.value=?", name);
		if (dtAttrValues.size() >= 1)
			return dtAttrValues.get(0);
		return null;
	}

	@Override
	public void save(AttributeValue dtAttrValue) {

		// this.hibernateTemplate.save(dtAttrValue);
		this.hibernateTemplate.saveOrUpdate(dtAttrValue);

	}

	@Override
	public void delete(AttributeValue dtAttrValue) {

		this.hibernateTemplate.delete(dtAttrValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttributeValue> findAllDtAttrValues() {

		char isActive = 'Y';
		// return (List<AttributeValue>)
		// this.hibernateTemplate.find("from AttributeValue");
		return (List<AttributeValue>) this.hibernateTemplate.find("from AttributeValue as a where a.isActive=?", isActive);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttributeValue> findAllDtAttrValuesWithInActive() {

		// char isActive='Y';
		return (List<AttributeValue>) this.hibernateTemplate.find("from AttributeValue");
		// return (List<AttributeValue>)
		// this.hibernateTemplate.find("from AttributeValue as a where a.isActive=?",isActive);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AttributeValue findDtAttrValueByNameAndAbbreviation(String attrValue, String abbreviation) {

		List<AttributeValue> dtAttrValues = this.hibernateTemplate.find(
			"from AttributeValue as a where a.value=? and a.abbreviation=?", attrValue, abbreviation);
		if (dtAttrValues.size() >= 1)
			return dtAttrValues.get(0);
		return null;
	}

}
