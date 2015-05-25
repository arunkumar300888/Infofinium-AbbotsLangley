package uk.co.jmr.sdp.domain;

public class RentReceipt {

	private long id;
	private TenancyForm tenancyId;
	private PropertyDetailsForm propertyId;
	private String paymenttype;
	private String reference;
	private String pay_date;
	private String rent_amount;
	public TenancyForm getTenancyId() {
		return tenancyId;
	}

	public void setTenancyId(TenancyForm tenancyId) {
		this.tenancyId = tenancyId;
	}

	public PropertyDetailsForm getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(PropertyDetailsForm propertyId) {
		this.propertyId = propertyId;
	}

	private String rentreceived;
	private long userId;
	
	public RentReceipt(){
		super();
		this.id=-1;
	}
	
	public RentReceipt(TenancyForm tenancyId, PropertyDetailsForm propertyId, String paymenttype, String reference, String pay_date, String rent_amount,String rentreceived, long userId){
		super();
		this.id=-1;
		this.tenancyId = tenancyId;
		this.propertyId = propertyId;
		this.paymenttype = paymenttype;
		this.reference = reference;
		this.pay_date = pay_date;
		this.rent_amount = rent_amount;
		this.rentreceived = rentreceived;
		this.userId = userId;
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

	

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getPay_date() {
		return pay_date;
	}

	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}

	public String getRent_amount() {
		return rent_amount;
	}

	public void setRent_amount(String rent_amount) {
		this.rent_amount = rent_amount;
	}

	public String getRentreceived() {
		return rentreceived;
	}

	public void setRentreceived(String rentreceived) {
		this.rentreceived = rentreceived;
	}
	
	
}
