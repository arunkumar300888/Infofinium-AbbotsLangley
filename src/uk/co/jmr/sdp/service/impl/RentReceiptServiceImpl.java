package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.RentReceiptDao;
import uk.co.jmr.sdp.domain.RentReceipt;
import uk.co.jmr.sdp.service.RentReceiptService;

@Service("rentReceiptService")
public class RentReceiptServiceImpl implements RentReceiptService {
	
	@Autowired
	private RentReceiptDao rentReceiptDao;
	
	@Override
	public void save(RentReceipt rentReceipt) {
		// TODO Auto-generated method stub
		this.rentReceiptDao.save(rentReceipt);
	}

	@Override
	public List<RentReceipt> findAllRentReceipts() {
		// TODO Auto-generated method stub
		return this.rentReceiptDao.findAllRentReceipts();
	}

	@Override
	public List<RentReceipt> findAllRentReceiptsForUser(long userId) {
		// TODO Auto-generated method stub
		return this.rentReceiptDao.findAllRentReceiptsForUser(userId);
	}

}
