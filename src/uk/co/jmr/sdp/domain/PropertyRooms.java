package uk.co.jmr.sdp.domain;

public class PropertyRooms {

	private long id;
	private PropertyDetailsForm propertyDetailsForm;
	private String roomName;
	private char isOccupied;
	private TenancyForm occupiedBy;
	
	public PropertyRooms(){
		super();
		this.id=-1;
	}
	
	public PropertyRooms(PropertyDetailsForm propertyDetailsForm, String roomName,
			char isOccupied, TenancyForm occupiedBy) {
		super();
		this.id=-1;
		this.propertyDetailsForm = propertyDetailsForm;
		this.roomName = roomName;
		this.isOccupied = isOccupied;
		this.setOccupiedBy(occupiedBy);
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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public char getIsOccupied() {
		return isOccupied;
	}

	public void setIsOccupied(char isOccupied) {
		this.isOccupied = isOccupied;
	}

	public TenancyForm getOccupiedBy() {
		return occupiedBy;
	}

	public void setOccupiedBy(TenancyForm occupiedBy) {
		this.occupiedBy = occupiedBy;
	}

	
}
