package uk.co.jmr.sdp.domain;

public class GivingNoticeForm {

	private long id;
	private long tenancyId;
	private long propertyId;
	private long rentaccountsId;
	private String tenantName;
	private String address;
	private String leavingDate;
	private String forwardAddress;
	private String accountName;
	private String accountNumber;
	private String sortCode;
	private char isGivenNotice;
	private User userId;

	
	public GivingNoticeForm(){
		super();
		this.id=-1;
	}
	
	public GivingNoticeForm(long tenancyId, long propertyId,
			long rentaccountsId, String tenantName, String address,
			String leavingDate, String forwardAddress, String accountName,
			String accountNumber, String sortCode, char isGivenNotice, User userId) {
		super();
		this.id=-1;
		this.tenancyId = tenancyId;
		this.propertyId = propertyId;
		this.rentaccountsId = rentaccountsId;
		this.tenantName = tenantName;
		this.address = address;
		this.leavingDate = leavingDate;
		this.forwardAddress = forwardAddress;
		this.accountName = accountName;
		this.accountNumber = accountNumber;
		this.sortCode = sortCode;
		this.isGivenNotice = isGivenNotice;
		this.userId=userId;
		
	}

	
	
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public char getIsGivenNotice() {
		return isGivenNotice;
	}

	public void setIsGivenNotice(char isGivenNotice) {
		this.isGivenNotice = isGivenNotice;
	}
	
	
	public long getTenancyId() {
		return tenancyId;
	}

	public void setTenancyId(long tenancyId) {
		this.tenancyId = tenancyId;
	}

	public long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(long propertyId) {
		this.propertyId = propertyId;
	}

	public long getRentaccountsId() {
		return rentaccountsId;
	}

	public void setRentaccountsId(long rentaccountsId) {
		this.rentaccountsId = rentaccountsId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(String leavingDate) {
		this.leavingDate = leavingDate;
	}

	public String getForwardAddress() {
		return forwardAddress;
	}

	public void setForwardAddress(String forwardAddress) {
		this.forwardAddress = forwardAddress;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}
	
	
	
}
