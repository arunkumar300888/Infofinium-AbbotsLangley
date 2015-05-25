package uk.co.jmr.sdp.domain;

import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

public class CompanyAttributeCombo {
	
	private long id;
	private Company company;
	private Attribute attribute;
	private AttributeValue attributeValue;
	
	public CompanyAttributeCombo(){
		super();
		this.setId(-1);
	}
	
	public CompanyAttributeCombo(Company company,Attribute attribute,AttributeValue attributeValue){
		super();
		this.setId(-1);
		this.setCompany(company);
		this.setAttribute(attribute);
		this.setAttributeValue(attributeValue);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public AttributeValue getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(AttributeValue attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	

}
