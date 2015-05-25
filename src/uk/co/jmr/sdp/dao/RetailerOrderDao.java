package uk.co.jmr.sdp.dao;

import uk.co.jmr.sdp.domain.RetailerOrder;

public interface RetailerOrderDao {
	
	void save(RetailerOrder retailerOrder);
	
	RetailerOrder findRetailerOrderById(long id);
	
	RetailerOrder findRetailerOrderByUserFormId(long userFormId);

}
