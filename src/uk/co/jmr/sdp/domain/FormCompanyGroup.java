package uk.co.jmr.sdp.domain;

import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.webforms.db.pojo.FormDefs;

public class FormCompanyGroup {
	
	private long id;
	private FormDefs formDefs;
	private AttributeValue attributeValue;
	
	public FormCompanyGroup(){
		super();
		this.setId(-1);
	}
	
	public FormCompanyGroup(FormDefs formDefs,AttributeValue attributeValue){
		super();
		this.setId(-1);
		this.setFormDefs(formDefs);
		this.setAttributeValue(attributeValue);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public FormDefs getFormDefs() {
		return formDefs;
	}

	public void setFormDefs(FormDefs formDefs) {
		this.formDefs = formDefs;
	}

	public AttributeValue getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(AttributeValue attributeValue) {
		this.attributeValue = attributeValue;
	}

	
	
}
