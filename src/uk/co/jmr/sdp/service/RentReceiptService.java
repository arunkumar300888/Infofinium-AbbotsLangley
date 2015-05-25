package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.RentReceipt;

public interface RentReceiptService {
	
	public void save(RentReceipt rentReceipt);
	
	List<RentReceipt> findAllRentReceipts();
	
	List<RentReceipt> findAllRentReceiptsForUser(long userId);

}
