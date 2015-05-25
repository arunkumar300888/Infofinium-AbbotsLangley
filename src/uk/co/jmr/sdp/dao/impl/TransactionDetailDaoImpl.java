package uk.co.jmr.sdp.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;



import uk.co.jmr.sdp.dao.TransactionDetailDao;
import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.TransactionDetails;

@Repository("transactionDetailDao")
public class TransactionDetailDaoImpl  implements TransactionDetailDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(TransactionDetails transactionDetails) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(transactionDetails);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionDetails> findTransactionbyUserid(long userId) {
		// TODO Auto-generated method stub
		
		
		return hibernateTemplate.find("from TransactionDetails t where t.userId=?",userId);
	}

	@Override
	public TransactionDetails findTransactionByTenancyId(TenancyForm tenancyForm) {
		// TODO Auto-generated method stub
		List<TransactionDetails> tds=hibernateTemplate.find("from TransactionDetails where tenancyId=? ",tenancyForm);
		if(tds.size()>=1)
			return tds.get(0);
		return null;
	}

	@Override
	public TransactionDetails findTransactionByPropertyDetailsFormId(
			PropertyDetailsForm propertyDetailsForm) {
		// TODO Auto-generated method stub
		List<TransactionDetails> tds=hibernateTemplate.find("from TransactionDetails where propertyId=? ",propertyDetailsForm);
		if(tds.size()>=1)
			return tds.get(0);
		return null;
	}

	@Override
	public TransactionDetails findTransactionDetialsByPaymentTypeAndRentDueDate(
			Date rentDueDate,TenancyForm tenancyId) {
		// TODO Auto-generated method stub
		List<TransactionDetails> tds=hibernateTemplate.find("from TransactionDetails where transactiontype=? and transactiondate=? and tenancyId=?","D",rentDueDate,tenancyId);
		if(tds.size()>=1)
			return tds.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TransactionDetails findTransactionDetialsByTenancyAndTransactionType(
			TenancyForm tenancyForm, String transactionType) {
		// TODO Auto-generated method stub
		List<TransactionDetails> tds=hibernateTemplate.find("from TransactionDetails where transactiontype=? and tenancyId=? order by id DESC",transactionType,tenancyForm);
		if(tds.size()>=1)
			return tds.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionDetails> findTransactionbyUseridandRentReceivedEqualY(long userId) {
		// TODO Auto-generated method stub
		
		
		return hibernateTemplate.find("from TransactionDetails t where t.userId=? order by t.transactiondate",userId);
	}

}
