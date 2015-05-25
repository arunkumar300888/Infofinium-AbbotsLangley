package uk.co.jmr.sdp.domain;

public class Mortgage {
	
	private long id;
	private PropertyDetailsForm propertyDetailsForm;
	private String description;
	


	private long total;
	
	
	public Mortgage(PropertyDetailsForm propertyDetailsForm,
			String description, long total) {
		super();
		this.id=-1;
		this.propertyDetailsForm = propertyDetailsForm;
		this.description = description;
		this.total = total;
	}
	
	public Mortgage() {
		super();
		this.id = -1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PropertyDetailsForm getPropertyDetailsForm() {
		return propertyDetailsForm;
	}

	public void setPropertyDetailsForm(PropertyDetailsForm propertyDetailsForm) {
		this.propertyDetailsForm = propertyDetailsForm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	
	
	
	

}
