package uk.co.jmr.sdp.dao;

import java.util.Date;
import java.util.List;


import uk.co.jmr.sdp.domain.PropertyDetailsForm;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.TransactionDetails;

public interface TransactionDetailDao {
	
	public void save(TransactionDetails transactionDetailDao);
	List<TransactionDetails> findTransactionbyUserid(long userId);
	TransactionDetails findTransactionByTenancyId(TenancyForm tenancyForm);
	TransactionDetails findTransactionByPropertyDetailsFormId(PropertyDetailsForm propertyDetailsForm);
	
	TransactionDetails findTransactionDetialsByTenancyAndTransactionType(TenancyForm tenancyForm,String transactionType);
	List<TransactionDetails> findTransactionbyUseridandRentReceivedEqualY(long userId);
	TransactionDetails findTransactionDetialsByPaymentTypeAndRentDueDate(
			Date rentDueDate, TenancyForm tenancyId);
	
}
