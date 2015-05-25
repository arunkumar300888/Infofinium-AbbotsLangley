package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.sun.crypto.provider.DESParameters;

import uk.co.jmr.sdp.dao.FormCompanyGroupDao;
import uk.co.jmr.sdp.domain.FormCompanyGroup;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.forms.FormDefinition;
import uk.co.jmr.webforms.db.pojo.FormDefs;


@Repository("formCompanyGroupDao")
public class FormCompanyGroupDaoImpl implements FormCompanyGroupDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(FormCompanyGroup formCompanyGroup) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(formCompanyGroup);
	}

	@Override
	public void delete(FormCompanyGroup formCompanyGroup) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(formCompanyGroup);
	}

	@Override
	public FormCompanyGroup findFormCompanyGroupById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(FormCompanyGroup.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormCompanyGroup> findFormCompanyGroupForFormDef(
			FormDefs formDefs) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from FormCompanyGroup where formDefs=?",formDefs);
	}

	@Override
	public List<FormCompanyGroup> findFormCompanyGroupForCompany(
			AttributeValue attributeValue) {
		// TODO Auto-generated method stub
		List<FormCompanyGroup> formCompGrps=hibernateTemplate.find("from FormCompanyGroup where attributeValue=?",attributeValue);
		
		return formCompGrps;
	}

	@Override
	public FormCompanyGroup findFormCompanyGroupForFormDefAndAttributeValue(
			FormDefs formDefs, AttributeValue attributeValue) {
		// TODO Auto-generated method stub
		
		List<FormCompanyGroup> formCompGrps=hibernateTemplate.find("from FormCompanyGroup where attributeValue=? and formDefs=?",attributeValue,formDefs);
		if(formCompGrps.size()>=1)
			return formCompGrps.get(0);
		return null;
	}
	
	
		
	

}
