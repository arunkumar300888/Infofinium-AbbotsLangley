package uk.co.jmr.sdp.domain;

import java.util.Date;

public class TransactionDetails {
	
	private long id;
	private PropertyDetailsForm propertyId;
	private TenancyForm tenancyId;
	private String transactiontype;
	private Date transactiondate;
	private String amount;
	private String rentreceived;
	private long userId;
	private String transactionMode;
	
	public TransactionDetails(){
		super();
		this.id=-1;
	}
	
	/*public TransactionDetails(TenancyForm tenancyId, PropertyDetailsForm propertyId, String transactiontype,	String transactiondate, String amount, String rentreceived, long userId){
		super();
		this.id=-1;
		this.setTenancyId(tenancyId);
		this.setPropertyId(propertyId);
		this.transactiontype = transactiontype;
		this.transactiondate = transactiondate;
		this.amount = amount;
		this.rentreceived = rentreceived;
		this.userId = userId;
	}*/
	
	public TransactionDetails(TenancyForm tenancyId, PropertyDetailsForm propertyId, String transactiontype,	Date transactiondate, String amount, String rentreceived, long userId, String transactionMode){
		super();
		this.id=-1;
		this.tenancyId = tenancyId;
		this.propertyId = propertyId;
		this.transactiontype = transactiontype;
		this.setTransactiondate(transactiondate);
		this.amount = amount;
		this.rentreceived = rentreceived;
		this.userId = userId;
		this.transactionMode = transactionMode;
	}

	
	
	public String getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRentreceived() {
		return rentreceived;
	}

	public void setRentreceived(String rentreceived) {
		this.rentreceived = rentreceived;
	}

	public PropertyDetailsForm getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(PropertyDetailsForm propertyId) {
		this.propertyId = propertyId;
	}

	public TenancyForm getTenancyId() {
		return tenancyId;
	}

	public void setTenancyId(TenancyForm tenancyId) {
		this.tenancyId = tenancyId;
	}

	public Date getTransactiondate() {
		return transactiondate;
	}

	public void setTransactiondate(Date transactiondate) {
		this.transactiondate = transactiondate;
	}
	
	
}
