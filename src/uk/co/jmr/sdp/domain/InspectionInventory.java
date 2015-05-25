package uk.co.jmr.sdp.domain;

public class InspectionInventory {

	private long id;
	private long userFormId;
	private String tenantName;
	private String employeeName;
	private String address;
	private String room;
	private String furnishingsList;
	private String ok;
	private String notes;
	private String photos;
	private String propertyForm;
	private String tenancyForm;
	
	public InspectionInventory(){
		super();
		this.id=-1;
	}
	
	public InspectionInventory(long userFormId,String tenantName, String employeeName,
			String address, String room, String furnishingsList, String ok,
			String notes, String photos, String propertyForm, String tenancyForm) {
		super();
		this.id=-1;
		this.userFormId=userFormId;
		this.tenantName = tenantName;
		this.employeeName = employeeName;
		this.address = address;
		this.room = room;
		this.furnishingsList = furnishingsList;
		this.ok = ok;
		this.notes = notes;
		this.photos = photos;
		this.propertyForm = propertyForm;
		this.tenancyForm = tenancyForm;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserFormId() {
		return userFormId;
	}

	public void setUserFormId(long userFormId) {
		this.userFormId = userFormId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getFurnishingsList() {
		return furnishingsList;
	}

	public void setFurnishingsList(String furnishingsList) {
		this.furnishingsList = furnishingsList;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getPropertyForm() {
		return propertyForm;
	}

	public void setPropertyForm(String propertyForm) {
		this.propertyForm = propertyForm;
	}

	public String getTenancyForm() {
		return tenancyForm;
	}

	public void setTenancyForm(String tenancyForm) {
		this.tenancyForm = tenancyForm;
	}
	
}
