package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.AttributeValueDao;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

@Repository("attributeValueDao")
public class AttributeValueDaoImpl implements AttributeValueDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public AttributeValue findAttributeValueById(long id) {

		return this.hibernateTemplate.get(AttributeValue.class, id);
	}

	@Override
	public AttributeValue findAttributeValueByName(String name) {
		// TODO Auto-generated method stub
		List<AttributeValue> attributeValues=hibernateTemplate.find("from AttributeValue where value=?",name);
		if(attributeValues.size()>=1)
			return attributeValues.get(0);
		return null;
	}

}
