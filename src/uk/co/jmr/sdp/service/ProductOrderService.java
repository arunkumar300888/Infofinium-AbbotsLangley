package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.ProductOrder;

public interface ProductOrderService {
	
	void save(ProductOrder productOrder);
	
	List<ProductOrder> findProductOrderByRetailerOrderId(long retailerOrderId);
	
	ProductOrder findProductOrderById(long id);
	
	ProductOrder findProductOrderByRetailerOrderIdAndFieldName(long retailerOrderId,String fieldName);
	
	void delete(List<ProductOrder> productOrders);
}
