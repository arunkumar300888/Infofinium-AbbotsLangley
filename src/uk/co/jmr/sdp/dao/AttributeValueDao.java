package uk.co.jmr.sdp.dao;

import uk.co.jmr.sdp.domain.dt.AttributeValue;

public interface AttributeValueDao {

	AttributeValue findAttributeValueById(long id);
	
	AttributeValue findAttributeValueByName(String name);
}
