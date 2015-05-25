package uk.co.jmr.sdp.domain;

public class InventoryRooms {

	private long id;
	private Inventory inventory;
	private String fieldName;
	private String fieldValue;
	
	
	
	
	public InventoryRooms() {
		super();
		this.id = -1;
	}




	public InventoryRooms(Inventory inventory, String fieldName,
			String fieldValue) {
		super();
		this.id=-1;
		this.inventory = inventory;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}




	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public Inventory getInventory() {
		return inventory;
	}




	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}




	public String getFieldName() {
		return fieldName;
	}




	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}




	public String getFieldValue() {
		return fieldValue;
	}




	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	
	
}
