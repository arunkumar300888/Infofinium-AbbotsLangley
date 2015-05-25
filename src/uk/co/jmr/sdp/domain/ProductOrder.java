package uk.co.jmr.sdp.domain;

public class ProductOrder {
	
	private long id;
	private long retailerOrderId;
	private String fieldName;
	private String value;
	
	
	
	
	
	public ProductOrder() {
		super();
		this.id = -1;
	}




	public ProductOrder(long retailerOrderId, String fieldName, String value) {
		super();
		this.id=-1;
		this.retailerOrderId = retailerOrderId;
		this.fieldName = fieldName;
		this.setValue(value);
		
	}




	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public long getRetailerOrderId() {
		return retailerOrderId;
	}




	public void setRetailerOrderId(long retailerOrderId) {
		this.retailerOrderId = retailerOrderId;
	}




	public String getFieldName() {
		return fieldName;
	}




	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}




	public String getValue() {
		return value;
	}




	public void setValue(String value) {
		this.value = value;
	}




	
	

}
