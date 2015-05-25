package uk.co.jmr.webforms.db.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.webforms.db.dao.FieldTypesDao;
import uk.co.jmr.webforms.db.pojo.FieldTypes;

@Repository("fieldTypesDao")
public class FieldTypesDaoImpl implements FieldTypesDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<FieldTypes> findAllFieldTypes() {

		return hibernateTemplate.find("from FieldTypes");
	}

	@Override
	public FieldTypes findFieldTypesById(long id) {

		return hibernateTemplate.get(FieldTypes.class, id);
	}

	@Override
	public FieldTypes findFieldTypesByName(String name) {

		return (FieldTypes) hibernateTemplate.find("from FieldTypes as d where d.name=?", name).get(0);
	}

	@Override
	public void save(FieldTypes obj) {

		hibernateTemplate.saveOrUpdate(obj);
	}

	@Override
	public void delete(FieldTypes obj) {

		hibernateTemplate.delete(obj);
	}
}
