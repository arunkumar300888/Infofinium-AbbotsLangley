package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.DtAttributeDao;
import uk.co.jmr.sdp.dao.DtTemplateComboDao;
import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.DtTemplateCombo;

@Repository("dtTemplateComboDao")
public class DtTemplateComboDaoImpl implements DtTemplateComboDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private DtAttributeDao dtAtributeDao;

	@Override
	public DtTemplateCombo findTemplateById(long id) {

		return this.hibernateTemplate.get(DtTemplateCombo.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DtTemplateCombo findTemplateByName(String name) {

		List<DtTemplateCombo> dtTemplates = this.hibernateTemplate.find("from DtTemplateCombo as a where a.templateName=?", name);
		if (dtTemplates.size() >= 1)
			return dtTemplates.get(0);
		return null;
	}

	@Override
	public void save(DtTemplateCombo dtTemplate) {

		this.hibernateTemplate.save(dtTemplate);
	}

	@Override
	public void delete(DtTemplateCombo dtTemplate) {

		this.hibernateTemplate.delete(dtTemplate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DtTemplateCombo> listAllTemplates() {

		return (List<DtTemplateCombo>) this.hibernateTemplate.find("from DtTemplateCombo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DtTemplateCombo> findMatchForFirstLevel(AttributeValue attrValue) {

		List<DtTemplateCombo> dtc = this.hibernateTemplate.find(
			"select d.dtTemplateCombo from DtTemplateComboValue as d where d.attributeValue=?", attrValue);
		// System.out.println(dtc);

		return dtc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DtTemplateCombo> findMatchForSecondLevel(AttributeValue secondLevelAttrValue) {

		Attribute attribute = this.dtAtributeDao.findAttributeByOrder(2);
		List<DtTemplateCombo> dtc = this.hibernateTemplate.find(
			"select d.dtTemplateCombo from DtTemplateComboValue as d where d.attribute=? and d.attributeValue=?", attribute,
			secondLevelAttrValue);
		return dtc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DtTemplateCombo> getThirdLevelAttrValues(AttributeValue attrValue) {

		Attribute attribute = this.dtAtributeDao.findAttributeByOrder(3);
		List<DtTemplateCombo> dtc = this.hibernateTemplate.find(
			"select d.dtTemplateCombo from DtTemplateComboValue as d where d.attribute=? and d.attributeValue=?", attribute,
			attrValue);
		return dtc;
	}
}
