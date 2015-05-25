package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.dt.Attribute;

public interface DtAttributeService {

	Attribute findDtAttributeById(long id);

	Attribute findDtAttributeByName(String name);

	void save(Attribute dtAttr);

	void delete(Attribute dtAttr);

	List<Attribute> findAllDtAttrs();

	Attribute findAttributeByOrder(int order);

}
