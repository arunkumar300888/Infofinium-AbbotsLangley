package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.DtTemplateComboValueDao;
import uk.co.jmr.sdp.domain.dt.DtTemplateCombo;
import uk.co.jmr.sdp.domain.dt.DtTemplateComboValue;

@Repository("dtTemplateComboValueDao")
public class DtTemplateComboValueDaoImpl implements DtTemplateComboValueDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public void delete(DtTemplateComboValue dtTemplateComboValue) {

		this.hibernateTemplate.delete(dtTemplateComboValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DtTemplateComboValue> findTemplateComboValues(DtTemplateCombo dtTemplateCombo) {

		return (List<DtTemplateComboValue>) this.hibernateTemplate.find(
			"from DtTemplateComboValue as d where d.dtTemplateCombo=?", dtTemplateCombo);
	}
}
