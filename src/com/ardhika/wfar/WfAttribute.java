package com.ardhika.wfar;

import java.util.Date;

public class WfAttribute implements Comparable<WfAttribute> {
	private String name;
	private WfAttributeType type;
	private Object value = null;
	private boolean userEditable;

	// Added by Karthik for util.java remove Wf attribute in update properties
	public WfAttribute(String name) {

		this.name = name;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof WfAttribute)
			return this.name.equals(((WfAttribute) obj).name);

		return false;
	}

	// Added by Karthik for util.java remove Wf attribute in update properties
	// .Ended

	public WfAttribute(String name, WfAttributeType type) {

		this.name = name;
		this.type = type;
		userEditable = true;
	}

	public WfAttribute(String name, WfAttributeType type, Object value, boolean userEditable) {

		this.name = name;
		this.type = type;
		this.userEditable = userEditable;
		this.setValue(value);
	}

	public String getName() {

		return name;
	}

	public WfAttributeType getType() {

		return type;
	}

	public Object getValue() {

		return value;
	}

	public void setValue(Object value) {

		this.value = value;
		// TODO do the check here
	}

	// for Hibernate Mapping and Model management

	private long id = -1;

	protected WfAttribute() {

	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	protected String getAttributeType() {

		return type.toString();
	}

	public void setAttributeType(String type) {

		this.type = WfAttributeType.valueOf(type);
	}

	public Long getAttrNumber() {

		if (this.type == WfAttributeType.WF_ATTR_NUMBER)
			return (Long) this.value;
		return null;
	}

	public Double getAttrDecimal() {

		if (this.type == WfAttributeType.WF_ATTR_DECIMAL)
			return (Double) this.value;
		return null;
	}

	public Date getAttrDate() {

		if (this.type == WfAttributeType.WF_ATTR_DATE)
			return (Date) this.value;
		return null;
	}

	public String getAttrText() {

		if (this.type == WfAttributeType.WF_ATTR_TEXT)
			return (String) this.value;
		return null;
	}

	public void setAttrText(String attrText) {

		if (attrText != null)
			this.value = attrText;
	}

	public void setName(String name) {

		if (name != null)
			this.name = name;
	}

	public void setAttrDate(Date attrDate) {

		if (attrDate != null)
			this.value = attrDate;
	}

	public void setAttrDecimal(Double attrDecimal) {

		if (attrDecimal != null)
			this.value = attrDecimal;
	}

	public void setAttrNumber(Long attrNumber) {

		if (attrNumber != null)
			this.value = attrNumber;
	}

	public String getUserEditable() {

		return userEditable ? "Y" : "N";
	}

	public void setUserEditable(String value) {

		this.userEditable = value.equalsIgnoreCase("Y");
	}

	@Override
	public int compareTo(WfAttribute o) {

		return this.name.compareTo(o.name);
	}

}
