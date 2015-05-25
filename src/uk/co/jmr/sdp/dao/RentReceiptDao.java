package uk.co.jmr.sdp.dao;


import java.util.List;

import uk.co.jmr.sdp.domain.RentReceipt;

public interface RentReceiptDao {
	
	public void save(RentReceipt rentReceipt);
	
	List<RentReceipt> findAllRentReceipts();

	List<RentReceipt> findAllRentReceiptsForUser(long userId);

}
