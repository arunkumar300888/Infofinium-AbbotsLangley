package uk.co.jmr.sdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.RetailerOrderDao;
import uk.co.jmr.sdp.domain.RetailerOrder;
import uk.co.jmr.sdp.service.RetailerOrderService;

@Service("retailerOrderService")
public class RetailerOrderServiceImpl implements RetailerOrderService{
	
	@Autowired
	private RetailerOrderDao retailerOrderDao;

	@Override
	public void save(RetailerOrder retailerOrder) {
		// TODO Auto-generated method stub
		this.retailerOrderDao.save(retailerOrder);
	}

	@Override
	public RetailerOrder findRetailerOrderById(long id) {
		// TODO Auto-generated method stub
		return this.retailerOrderDao.findRetailerOrderById(id);
	}

	@Override
	public RetailerOrder findRetailerOrderByUserFormId(long userFormId) {
		// TODO Auto-generated method stub
		return this.retailerOrderDao.findRetailerOrderByUserFormId(userFormId);
	}
	
	

}
