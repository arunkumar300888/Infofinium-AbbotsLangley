package uk.co.jmr.sdp.domain;

public class RetailerOrder {
	
	private long id;
	private long userFormId;
	private String termsAndConditions;
	private String title;
	private String firstName;
	private String lastName;
	private String companyName;
	private String contactNumber;
	private String mobileNumber;
	private String emailAddress;
	private String billingAddress;
	private String shippingAddress;
	private String notes;
	private String preferredMethodOfPayment;
	private String paymentDetails;
	
	private String orderStatus;
	
	
	public RetailerOrder(){
		super();
		this.id=-1;
	}
	
	public RetailerOrder(long userFormId,String termsAndConditions, String title,
			String firstName, String lastName, String companyName,
			String contactNumber, String mobileNumber, String emailAddress,
			String billingAddress, String shippingAddress, String notes,
			String preferredMethodOfPayment, String paymentDetails,
			String orderStatus) {
		super();
		this.id=-1;
		this.userFormId=userFormId;
		this.termsAndConditions = termsAndConditions;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyName = companyName;
		this.contactNumber = contactNumber;
		this.mobileNumber = mobileNumber;
		this.emailAddress = emailAddress;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
		this.notes = notes;
		this.preferredMethodOfPayment = preferredMethodOfPayment;
		this.paymentDetails = paymentDetails;
		this.orderStatus = orderStatus;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPreferredMethodOfPayment() {
		return preferredMethodOfPayment;
	}

	public void setPreferredMethodOfPayment(String preferredMethodOfPayment) {
		this.preferredMethodOfPayment = preferredMethodOfPayment;
	}

	public String getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(String paymentDetails) {
		this.paymentDetails = paymentDetails;
	}



	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTermsAndConditions() {
		return termsAndConditions;
	}
	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public long getUserFormId() {
		return userFormId;
	}

	public void setUserFormId(long userFormId) {
		this.userFormId = userFormId;
	}

}
