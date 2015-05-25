package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.ModelComboValueDao;
import uk.co.jmr.sdp.domain.dt.ModelCombo;
import uk.co.jmr.sdp.domain.dt.ModelComboValue;

@Repository("modelComboValueDao")
public class ModelComboValueDaoImpl implements ModelComboValueDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void deleteModelComboValue(ModelComboValue modelComboValue) {

		this.hibernateTemplate.delete(modelComboValue);

	}

	@Override
	public List<ModelComboValue> findModelComboValueByModelCombo(ModelCombo modelCombo) {

		@SuppressWarnings("unchecked")
		List<ModelComboValue> result = this.hibernateTemplate.find("from ModelComboValue as m where m.modelCombo=?", modelCombo);
		return result;

	}

	@Override
	public void save(ModelComboValue modelComboValue) {

		this.hibernateTemplate.save(modelComboValue);
	}
}
