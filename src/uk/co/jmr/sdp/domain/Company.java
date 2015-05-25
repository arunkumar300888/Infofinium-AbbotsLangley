package uk.co.jmr.sdp.domain;

public class Company {
	
	private long id;
	private String companyName;
	
	public Company(){
		super();
		this.setId(-1);
		
	}
	
	public Company(String companyName){
		super();
		this.setId(-1);
		this.setCompanyName(companyName);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	
	
}
