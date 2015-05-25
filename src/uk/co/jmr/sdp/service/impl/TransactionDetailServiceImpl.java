package uk.co.jmr.sdp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.service.TransactionDetailService;
import uk.co.jmr.sdp.dao.TransactionDetailDao;

import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.TransactionDetails;

@Service("transactionDetailService")
public class TransactionDetailServiceImpl implements TransactionDetailService {
	
@Autowired
private TransactionDetailDao transactionDetailDao;


@Override
public void save(TransactionDetails transactionDetails) {
	// TODO Auto-generated method stub
	this.transactionDetailDao.save(transactionDetails);
}

@Override
public List<TransactionDetails> findTransactionbyUserid(long userId) {
	// TODO Auto-generated method stub
	return this.transactionDetailDao.findTransactionbyUserid(userId);
}

@Override
public TransactionDetails findTransactionByTenancyId(TenancyForm tenancyForm) {
	// TODO Auto-generated method stub
	return this.transactionDetailDao.findTransactionByTenancyId(tenancyForm);
}

@Override
public TransactionDetails findTransactionByPropertyDetailsFormId(
		PropertyDetailsForm propertyDetailsForm) {
	// TODO Auto-generated method stub
	return this.transactionDetailDao.findTransactionByPropertyDetailsFormId(propertyDetailsForm);
}

@Override
public TransactionDetails findTransactionDetialsByPaymentTypeAndRentDueDate(
		Date rentDueDate,TenancyForm tenancyId) {
	// TODO Auto-generated method stub
	return this.transactionDetailDao.findTransactionDetialsByPaymentTypeAndRentDueDate(rentDueDate,tenancyId);
}

@Override
public TransactionDetails findTransactionDetialsByTenancyAndTransactionType(
		TenancyForm tenancyForm, String transactionType) {
	// TODO Auto-generated method stub
	return this.transactionDetailDao.findTransactionDetialsByTenancyAndTransactionType(tenancyForm, transactionType);
}


@Override
public List<TransactionDetails> findTransactionbyUseridandRentReceivedEqualY(long userId) {
	// TODO Auto-generated method stub
	return this.transactionDetailDao.findTransactionbyUseridandRentReceivedEqualY(userId);
}

}
