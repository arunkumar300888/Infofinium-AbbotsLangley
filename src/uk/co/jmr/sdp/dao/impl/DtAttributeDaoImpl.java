package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.DtAttributeDao;
import uk.co.jmr.sdp.domain.dt.Attribute;

@Repository("dtAttributeDao")
public class DtAttributeDaoImpl implements DtAttributeDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public Attribute findDtAttributeById(long id) {

		return hibernateTemplate.get(Attribute.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Attribute findDtAttributeByName(String name) {

		List<Attribute> dtAttributes = hibernateTemplate.find("from Attribute as a where a.name=?", name);
		if (dtAttributes.size() >= 1)
			return dtAttributes.get(0);
		return null;
	}

	@Override
	public void save(Attribute dtAttr) {

		// hibernateTemplate.save(dtAttr);
		hibernateTemplate.saveOrUpdate(dtAttr);
	}

	@Override
	public void delete(Attribute dtAttr) {

		hibernateTemplate.delete(dtAttr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attribute> findAllDtAttrs() {

		return (List<Attribute>) hibernateTemplate.find("from Attribute");
	}

	@Override
	public Attribute findAttributeByOrder(int order) {

		List<Attribute> attrsByOrder = this.hibernateTemplate.find("from Attribute as a where order=?", order);
		if (attrsByOrder.size() >= 1)
			return attrsByOrder.get(0);
		return null;
	}

}
