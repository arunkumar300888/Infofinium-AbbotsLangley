package uk.co.jmr.sdp.domain;

public class RoomDetails {
	
	private long id;
	private PropertyDetailsForm propertyDetailsForm;
	private String roomNo;
	private String roomName;
	
	public RoomDetails(PropertyDetailsForm propertyDetailsForm,
			String roomNo, String roomName) {
		super();
		this.id=-1;
		this.propertyDetailsForm = propertyDetailsForm;
		this.setRoomNo(roomNo);
		this.roomName = roomName;
	}

	public RoomDetails() {
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

	

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	
	
	

}
