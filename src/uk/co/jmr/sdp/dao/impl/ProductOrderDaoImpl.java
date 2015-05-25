package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.ProductOrderDao;
import uk.co.jmr.sdp.domain.ProductOrder;


@Repository("productOrderDao")
public class ProductOrderDaoImpl implements ProductOrderDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(ProductOrder productOrder) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(productOrder);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductOrder> findProductOrderByRetailerOrderId(
			long retailerOrderId) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from ProductOrder where retailerOrderId=? order by fieldName",retailerOrderId);
	}

	@Override
	public ProductOrder findProductOrderById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(ProductOrder.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductOrder findProductOrderByRetailerOrderIdAndFieldName(
			long retailerOrderId, String fieldName) {
		// TODO Auto-generated method stub
		List<ProductOrder> productOrders=hibernateTemplate.find("from ProductOrder where retailerOrderId=? and fieldName=?",retailerOrderId,fieldName);
		if(productOrders.size()>=1)
			return productOrders.get(0);
		return null;
	}

	@Override
	public void delete(List<ProductOrder> productOrders) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(productOrders);
	}
	
	
	

}
