package uk.co.jmr.sdp.domain;

public class StandardProductForm {

	private long id;
	private long userFormId;
	private String productName;
	private String productType;
	private String productCode;
	private String productDescription;
	private String sellingPricePerUnit;
	private String picture1;
	private String picture2;
	private String picture3;
	private String picture4;
	private String picture5;
	private String stockLevel;
	private String agentDistributionOfStock;
	private long stockLevelNewSupplierOrderAlert;
	
	public StandardProductForm(){
		super();
		this.id=-1;
	}
	
	public StandardProductForm(long userFormId,String productName, String productType,
			String productCode, String productDescription,
			String sellingPricePerUnit, String picture1, String picture2,
			String picture3, String picture4, String picture5,
			String stockLevel, String agentDistributionOfStock,
			long stockLevelNewSupplierOrderAlert) {
		super();
		this.id=-1;
		this.userFormId=userFormId;
		this.productName = productName;
		this.productType = productType;
		this.productCode = productCode;
		this.productDescription = productDescription;
		this.sellingPricePerUnit = sellingPricePerUnit;
		this.picture1 = picture1;
		this.picture2 = picture2;
		this.picture3 = picture3;
		this.picture4 = picture4;
		this.picture5 = picture5;
		this.stockLevel = stockLevel;
		this.agentDistributionOfStock = agentDistributionOfStock;
		this.stockLevelNewSupplierOrderAlert = stockLevelNewSupplierOrderAlert;
	}
              
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getSellingPricePerUnit() {
		return sellingPricePerUnit;
	}

	public void setSellingPricePerUnit(String sellingPricePerUnit) {
		this.sellingPricePerUnit = sellingPricePerUnit;
	}

	
	public String getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(String stockLevel) {
		this.stockLevel = stockLevel;
	}

	public String getAgentDistributionOfStock() {
		return agentDistributionOfStock;
	}

	public void setAgentDistributionOfStock(String agentDistributionOfStock) {
		this.agentDistributionOfStock = agentDistributionOfStock;
	}

	public long getStockLevelNewSupplierOrderAlert() {
		return stockLevelNewSupplierOrderAlert;
	}

	public void setStockLevelNewSupplierOrderAlert(
			long stockLevelNewSupplierOrderAlert) {
		this.stockLevelNewSupplierOrderAlert = stockLevelNewSupplierOrderAlert;
	}
	public String getPicture1() {
		return picture1;
	}



	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}



	public String getPicture2() {
		return picture2;
	}


	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}

	public String getPicture3() {
		return picture3;
	}

	public void setPicture3(String picture3) {
		this.picture3 = picture3;
	}

	public String getPicture4() {
		return picture4;
	}

	public void setPicture4(String picture4) {
		this.picture4 = picture4;
	}

	public String getPicture5() {
		return picture5;
	}

	public void setPicture5(String picture5) {
		this.picture5 = picture5;
	}

	public long getUserFormId() {
		return userFormId;
	}

	public void setUserFormId(long userFormId) {
		this.userFormId = userFormId;
	}
	
}
