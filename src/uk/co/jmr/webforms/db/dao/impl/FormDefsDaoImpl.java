package uk.co.jmr.webforms.db.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.webforms.db.dao.FormDefsDao;
import uk.co.jmr.webforms.db.pojo.FormDefs;

@Repository("formDefsDao")
public class FormDefsDaoImpl implements FormDefsDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<FormDefs> findAllFormDefs() {

		return hibernateTemplate.find("from FormDefs where active='y' order by name ");
	}

	@Override
	public FormDefs findFormDefsById(long id) {

		return hibernateTemplate.get(FormDefs.class, id);
	}

	@Override
	public FormDefs findFormDefsByName(String name) {

		return (FormDefs) hibernateTemplate.find("from FormDefs as d where d.name=?", name).get(0);
	}

	@Override
	public void save(FormDefs obj) {

		hibernateTemplate.saveOrUpdate(obj);
	}

	@Override
	public void delete(FormDefs obj) {

		hibernateTemplate.delete(obj);
	}
}
