package uk.co.jmr.sdp.domain;

public class CheckingOutRooms {
	
	private long id;
	private CheckingOutForm checkingOutForm;
	private String roomId;
	private String roomName;
	
	
	public CheckingOutRooms(){
		super();
		this.id=-1;
	}
	
	public CheckingOutRooms(CheckingOutForm checkingOutForm, String roomId,
			String roomName) {
		super();
		this.id=-1;
		this.checkingOutForm = checkingOutForm;
		this.roomId = roomId;
		this.roomName = roomName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CheckingOutForm getCheckingOutForm() {
		return checkingOutForm;
	}

	public void setCheckingOutForm(CheckingOutForm checkingOutForm) {
		this.checkingOutForm = checkingOutForm;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	
	
	

}
