package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.KeepAliveDao;
import uk.co.jmr.sdp.domain.KeepAlive;

@Repository("keepAliveDao")
public class KeepAliveDaoImpl implements KeepAliveDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<KeepAlive> findAllKeepAlive() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from KeepAlive");
	}

	@Override
	public KeepAlive findKeepAliveMessageById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(KeepAlive.class, id);
	}

	@Override
	public void save(KeepAlive keepAlive) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(keepAlive);
	}

	@Override
	public void delete(KeepAlive keepAlive) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(keepAlive);
	}

	@Override
	public void deleteAll(List<KeepAlive> keepAlive) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(keepAlive);
	}

}
