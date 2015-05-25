package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.ModelComboDao;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.ModelCombo;

@Repository("modelComboDao")
public class ModelComboDaoImpl implements ModelComboDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<ModelCombo> listAllModelCombos() {

		char isActive = 'Y';
		// return (List<ModelCombo>)
		// this.hibernateTemplate.find("from ModelCombo");
		return (List<ModelCombo>) this.hibernateTemplate.find("from ModelCombo as m where m.isActive=?", isActive);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ModelCombo> listAllModelCombosWithInActive() {

		return (List<ModelCombo>) this.hibernateTemplate.find("from ModelCombo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ModelCombo> findMatchForFirstLevel(AttributeValue attrValue) {

		List<ModelCombo> mc = this.hibernateTemplate.find(
			"select m.modelCombo from ModelComboValue as m where m.attributeValue=?", attrValue);
		// System.out.println(dtc);
		return mc;
	}

	@Override
	public void saveModelCombo(ModelCombo modelCombo) {

		this.hibernateTemplate.saveOrUpdate(modelCombo);

	}

	@Override
	public ModelCombo findModelComboById(long modelComboId) {

		@SuppressWarnings("unchecked")
		List<ModelCombo> result = this.hibernateTemplate.find("from ModelCombo as  m where m.id=?", modelComboId);
		if (result.size() >= 1)
			return result.get(0);
		return null;

	}

	@Override
	public ModelCombo findModelComboByDoctypeAndModelName(Doctype doctype, String modelName) {

		@SuppressWarnings("unchecked")
		List<ModelCombo> result = this.hibernateTemplate.find("from ModelCombo as m where m.doctype=? and m.modelName=?",
			doctype, modelName);
		if (result.size() >= 1)
			return result.get(0);
		return null;

	}

}
