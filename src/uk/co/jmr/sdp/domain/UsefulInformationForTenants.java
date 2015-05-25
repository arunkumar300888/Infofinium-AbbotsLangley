package uk.co.jmr.sdp.domain;

public class UsefulInformationForTenants 
{
	private long id;
	//private long userFormId;
	private String instructions;
	private String companyContactDetails;
	private String companyName;
	private String telephoneNumber;
	private String emailAddress;
	private String address;
	private String maintenanceQuery;
	private String rentQuery;
	
	public UsefulInformationForTenants(){
		super();
		this.id=-1;
	}
	
	public UsefulInformationForTenants( String instructions,
			String companyContactDetails, String companyName, String telephoneNumber,
			String emailAddress, String address, String maintenanceQuery,
			String rentQuery) {
		super();
		this.id=-1;
		//this.userFormId = userFormId;
		this.instructions = instructions;
		this.companyContactDetails = companyContactDetails;
		this.companyName = companyName;
		this.telephoneNumber = telephoneNumber;
		this.emailAddress = emailAddress;
		this.address = address;
		this.maintenanceQuery=maintenanceQuery;
		this.rentQuery = rentQuery;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

/*	public long getUserFormId() {
		return userFormId;
	}

	public void setUserFormId(long userFormId) {
		this.userFormId = userFormId;
	}*/

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getCompanyContactDetails() {
		return companyContactDetails;
	}

	public void setCompanyContactDetails(String companyContactDetails) {
		this.companyContactDetails = companyContactDetails;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMaintenanceQuery() {
		return maintenanceQuery;
	}

	public void setMaintenanceQuery(String maintenanceQuery) {
		this.maintenanceQuery = maintenanceQuery;
	}

	public String getRentQuery() {
		return rentQuery;
	}

	public void setRentQuery(String rentQuery) {
		this.rentQuery = rentQuery;
	}
	
	
}
