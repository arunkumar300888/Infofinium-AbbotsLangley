package uk.co.jmr.sdp.domain;

import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

public class CompanyUser {

	private long id;
	
	private AttributeValue attrValue;
	private User user;
	
	
	public CompanyUser(){
		super();
		this.id=-1;
	}
	
	public CompanyUser(AttributeValue attrValue,User user){
		super();
		this.id=-1;
		this.attrValue=attrValue;
		this.user=user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public AttributeValue getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(AttributeValue attrValue) {
		this.attrValue = attrValue;
	}
	
	
}
