package uk.co.jmr.webforms.db.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.webforms.db.dao.FieldsDao;
import uk.co.jmr.webforms.db.pojo.Fields;

@Repository("fieldsDao")
public class FieldsDaoImpl implements FieldsDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Fields> findAllFields() {

		return hibernateTemplate.find("from Fields");
	}

	@Override
	public Fields findFieldsById(long id) {

		return hibernateTemplate.get(Fields.class, id);
	}

	@Override
	public Fields findFieldsByName(String name) {

		return (Fields) hibernateTemplate.find("from Fields as d where d.name=?", name).get(0);
	}

	@Override
	public void save(Fields obj) {

		hibernateTemplate.saveOrUpdate(obj);
	}

	@Override
	public void delete(Fields obj) {

		hibernateTemplate.delete(obj);
	}
}
