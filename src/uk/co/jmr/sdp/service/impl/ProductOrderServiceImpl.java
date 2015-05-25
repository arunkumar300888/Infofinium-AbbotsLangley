package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.ProductOrderDao;
import uk.co.jmr.sdp.domain.ProductOrder;
import uk.co.jmr.sdp.service.ProductOrderService;


@Service("productOrderService")
public class ProductOrderServiceImpl implements ProductOrderService{
	
	@Autowired
	private ProductOrderDao productOrderDao;

	@Override
	public void save(ProductOrder productOrder) {
		// TODO Auto-generated method stub
		this.productOrderDao.save(productOrder);
	}

	@Override
	public List<ProductOrder> findProductOrderByRetailerOrderId(
			long retailerOrderId) {
		// TODO Auto-generated method stub
		return this.productOrderDao.findProductOrderByRetailerOrderId(retailerOrderId);
	}

	@Override
	public ProductOrder findProductOrderById(long id) {
		// TODO Auto-generated method stub
		return this.productOrderDao.findProductOrderById(id);
	}

	@Override
	public ProductOrder findProductOrderByRetailerOrderIdAndFieldName(
			long retailerOrderId, String fieldName) {
		// TODO Auto-generated method stub
		return this.productOrderDao.findProductOrderByRetailerOrderIdAndFieldName(retailerOrderId, fieldName);
	}

	@Override
	public void delete(List<ProductOrder> productOrders) {
		// TODO Auto-generated method stub
		this.productOrderDao.delete(productOrders);
	}
	
	
	
	

}
