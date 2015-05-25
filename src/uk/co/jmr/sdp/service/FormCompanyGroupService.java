package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.FormCompanyGroup;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.webforms.db.pojo.FormDefs;

public interface FormCompanyGroupService {

	void save(FormCompanyGroup formCompanyGroup);
	
	void delete(FormCompanyGroup formCompanyGroup);
	
	FormCompanyGroup findFormCompanyGroupById(long id);
	
	List<FormCompanyGroup> findFormCompanyGroupForFormDef(FormDefs formDefs);
	
	List<FormCompanyGroup> findFormCompanyGroupForCompany(AttributeValue attributeValue);
	
	FormCompanyGroup findFormCompanyGroupForFormDefAndAttributeValue(FormDefs formDefs,AttributeValue attributeValue);
}
