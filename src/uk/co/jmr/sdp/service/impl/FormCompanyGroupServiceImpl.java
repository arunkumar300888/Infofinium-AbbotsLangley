package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.FormCompanyGroupDao;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.FormCompanyGroup;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.forms.FormDefinition;
import uk.co.jmr.sdp.service.FormCompanyGroupService;
import uk.co.jmr.webforms.db.pojo.FormDefs;

@Service("formCompanyGroupService")
public class FormCompanyGroupServiceImpl implements FormCompanyGroupService{
	
	@Autowired
	private FormCompanyGroupDao formCompanyGroupDao;

	@Override
	public void save(FormCompanyGroup formCompanyGroup) {
		// TODO Auto-generated method stub
		this.formCompanyGroupDao.save(formCompanyGroup);
	}

	@Override
	public void delete(FormCompanyGroup formCompanyGroup) {
		// TODO Auto-generated method stub
		this.formCompanyGroupDao.delete(formCompanyGroup);
	}

	@Override
	public FormCompanyGroup findFormCompanyGroupById(long id) {
		// TODO Auto-generated method stub
		return this.formCompanyGroupDao.findFormCompanyGroupById(id);
	}

	@Override
	public List<FormCompanyGroup> findFormCompanyGroupForFormDef(
			FormDefs formDefs) {
		// TODO Auto-generated method stub
		return this.formCompanyGroupDao.findFormCompanyGroupForFormDef(formDefs);
	}

	@Override
	public List<FormCompanyGroup> findFormCompanyGroupForCompany(
			AttributeValue attributeValue) {
		// TODO Auto-generated method stub
		return this.formCompanyGroupDao.findFormCompanyGroupForCompany(attributeValue);
	}

	@Override
	public FormCompanyGroup findFormCompanyGroupForFormDefAndAttributeValue(
			FormDefs formDefs, AttributeValue attributeValue) {
		// TODO Auto-generated method stub
		return this.formCompanyGroupDao.findFormCompanyGroupForFormDefAndAttributeValue(formDefs, attributeValue);
	}

	
}
