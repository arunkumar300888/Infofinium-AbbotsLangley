package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.RentReceiptDao;
import uk.co.jmr.sdp.domain.RentReceipt;

@Repository("rentReceiptDao")
public class RentReceiptDaoImpl implements RentReceiptDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(RentReceipt rentReceipt) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(rentReceipt);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RentReceipt> findAllRentReceipts() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from RentReceipt");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RentReceipt> findAllRentReceiptsForUser(long userId) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from RentReceipt where userId=?",userId);
	}

}
