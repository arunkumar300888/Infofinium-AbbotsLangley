package uk.co.jmr.webforms.db.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.webforms.db.dao.FormFieldMapDao;
import uk.co.jmr.webforms.db.pojo.FormFieldMap;

@Repository("formFieldMapDao")
public class FormFieldMapDaoImpl implements FormFieldMapDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<FormFieldMap> findAllFormFieldMap() {

		return hibernateTemplate.find("from FormFieldMap");
	}

	@Override
	public FormFieldMap findFormFieldMapById(long id) {

		return hibernateTemplate.get(FormFieldMap.class, id);
	}

	@Override
	public FormFieldMap findFormFieldMapByName(String name) {

		return (FormFieldMap) hibernateTemplate.find("from FormFieldMap as d where d.name=?", name).get(0);
	}

	@Override
	public void save(FormFieldMap obj) {

		hibernateTemplate.saveOrUpdate(obj);
	}

	@Override
	public void delete(FormFieldMap obj) {

		hibernateTemplate.delete(obj);
	}
}
