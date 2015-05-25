package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.RetailerOrderDao;
import uk.co.jmr.sdp.domain.RetailerOrder;


@Repository("retailerOrderDao")
public class RetailerOrderDaoImpl implements RetailerOrderDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(RetailerOrder retailerOrder) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(retailerOrder);
	}

	@Override
	public RetailerOrder findRetailerOrderById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(RetailerOrder.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetailerOrder findRetailerOrderByUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		List<RetailerOrder> retailerOrders=hibernateTemplate.find("from RetailerOrder where userFormId=?",userFormId);
		if(retailerOrders.size()>=1)
			return  retailerOrders.get(0);
		return null;
	}
	
	

}
