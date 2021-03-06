package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.dt.AttributeValue;

public interface DtAttributeValueDao {
	AttributeValue findDtAttrValueById(long id);

	AttributeValue findDtAttrValueByName(String name);

	void save(AttributeValue dtAttrValue);

	void delete(AttributeValue dtAttrValue);

	List<AttributeValue> findAllDtAttrValues();

	List<AttributeValue> findAllDtAttrValuesWithInActive();

	AttributeValue findDtAttrValueByNameAndAbbreviation(String attrValue, String abbreviation);
}
