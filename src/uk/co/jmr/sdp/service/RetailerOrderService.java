package uk.co.jmr.sdp.service;

import uk.co.jmr.sdp.domain.RetailerOrder;

public interface RetailerOrderService {
	
	void save(RetailerOrder retailerOrder);
	
	RetailerOrder findRetailerOrderById(long id);
	
	RetailerOrder findRetailerOrderByUserFormId(long userFormId);
}
