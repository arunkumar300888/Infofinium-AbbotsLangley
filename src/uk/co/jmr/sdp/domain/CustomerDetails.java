package uk.co.jmr.sdp.domain;

public class CustomerDetails {
	
	
	private long id;
	private long userFormId;
	private String title;
	private String firstName;
	private String lastName;
	private String contactNumber;
	private String mobileNumber;
	private String emailAddress;
	private String billingAddress;
	private String shippingAddress;
	private String personalHandDelivered;
	private String paymentDetails;
	private String preferredMethodOfPayment;
	private String notes;
	private String mailingList;
	private String orderHistory;
	private String appointmentHistory;
	
	public CustomerDetails(){
		super();
		this.id=-1;
	}
	
	public CustomerDetails(long userFormId,String title, String firstName, String lastName,
			String contactNumber, String mobileNumber, String emailAddress,
			String billingAddress,String shippingAddress, String personalHandDelivered,
			String paymentDetails, String preferredMethodOfPayment,
			String notes, String mailingList, String orderHistory,
			String appointmentHistory) {
		super();
		this.id=-1;
		this.userFormId=userFormId;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
		this.mobileNumber = mobileNumber;
		this.emailAddress = emailAddress;
		this.billingAddress = billingAddress;
		this.shippingAddress=shippingAddress;
		this.personalHandDelivered = personalHandDelivered;
		this.paymentDetails = paymentDetails;
		this.preferredMethodOfPayment = preferredMethodOfPayment;
		this.notes = notes;
		this.mailingList = mailingList;
		this.orderHistory = orderHistory;
		this.appointmentHistory = appointmentHistory;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getPersonalHandDelivered() {
		return personalHandDelivered;
	}
	public void setPersonalHandDelivered(String personalHandDelivered) {
		this.personalHandDelivered = personalHandDelivered;
	}
	public String getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(String paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public String getPreferredMethodOfPayment() {
		return preferredMethodOfPayment;
	}
	public void setPreferredMethodOfPayment(String preferredMethodOfPayment) {
		this.preferredMethodOfPayment = preferredMethodOfPayment;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getMailingList() {
		return mailingList;
	}
	public void setMailingList(String mailingList) {
		this.mailingList = mailingList;
	}
	public String getOrderHistory() {
		return orderHistory;
	}
	public void setOrderHistory(String orderHistory) {
		this.orderHistory = orderHistory;
	}
	public String getAppointmentHistory() {
		return appointmentHistory;
	}
	public void setAppointmentHistory(String appointmentHistory) {
		this.appointmentHistory = appointmentHistory;
	}

	public long getUserFormId() {
		return userFormId;
	}

	public void setUserFormId(long userFormId) {
		this.userFormId = userFormId;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	
	
	
	

}
