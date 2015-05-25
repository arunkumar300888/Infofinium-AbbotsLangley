package uk.co.jmr.webforms.db.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.webforms.db.dao.ArrayItemsDao;
import uk.co.jmr.webforms.db.pojo.ArrayItems;

@Repository("arrayItemsDao")
public class ArrayItemsDaoImpl implements ArrayItemsDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<ArrayItems> findAllArrayItems() {

		return hibernateTemplate.find("from ArrayItems");
	}

	@Override
	public ArrayItems findArrayItemsById(long id) {

		return hibernateTemplate.get(ArrayItems.class, id);
	}

	@Override
	public void save(ArrayItems obj) {

		hibernateTemplate.saveOrUpdate(obj);
	}

	@Override
	public void delete(ArrayItems obj) {

		hibernateTemplate.delete(obj);
	}
}
