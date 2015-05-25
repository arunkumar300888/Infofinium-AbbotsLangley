package uk.co.jmr.webforms.db.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.webforms.db.dao.ArraysDao;
import uk.co.jmr.webforms.db.pojo.Arrays;

@Repository("arraysDao")
public class ArraysDaoImpl implements ArraysDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Arrays> findAllArrays() {

		return hibernateTemplate.find("from Arrays");
	}

	@Override
	public Arrays findArraysById(long id) {

		return hibernateTemplate.get(Arrays.class, id);
	}

	@Override
	public Arrays findArraysByName(String name) {

		return (Arrays) hibernateTemplate.find("from Arrays as d where d.name=?", name).get(0);
	}

	@Override
	public void save(Arrays obj) {

		hibernateTemplate.saveOrUpdate(obj);
	}

	@Override
	public void delete(Arrays obj) {

		hibernateTemplate.delete(obj);
	}
}
