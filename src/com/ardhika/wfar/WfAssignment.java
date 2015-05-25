package com.ardhika.wfar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class WfAssignment extends WfNode {
	private WfAttribute attribute;
	private Object attrValue;

	public Object getAttrValue() {

		return attrValue;
	}

	private String attrText;
	private Long attrNumber;
	private Date attrDate;
	private Float attrDecimal;
	private String assignee = "S:SYSTEM";

	public WfAssignment(String name, WfAttribute attribute, Object attrValue) {

		super(name, WfNodeType.WF_ASSIGN_NODE);
		this.attribute = attribute;
		this.attrValue = attrValue;
		//System.out.println("Inside Assign Const..." + attrValue);
	}

	public void assignValue(WfCase wfcase) throws Exception {

		//System.out.println("Attr Value" + this.getAttrValue());
		//System.out.println("Attribute Name...." + wfcase.getAttributeType(this.attribute.getName()) + "=" + this.getAttrDate());
		switch (wfcase.getAttributeType(this.attribute.getName())) {
			case WF_ATTR_NUMBER:
				this.attrNumber = this.getAttrNumber();
			case WF_ATTR_TEXT:
				this.attrText = this.getAttrText();
			case WF_ATTR_DATE:
				this.attrDate = this.getAttrDate();
			case WF_ATTR_DECIMAL:
				this.attrDecimal = this.getAttrDecimal();
		}
	}

	public void setAttrText(String attrText) {

		if (attrText != null)
			this.attrValue = attrText;
	}

	public void setAttrNumber(Long attrNumber) {

		if (attrNumber != null)
			this.attrValue = attrNumber;
	}

	public void setAttrDate(Date attrDate) {

		if (attrDate != null)
			this.attrValue = attrDate;
	}

	public void setAttrDecimal(Float attrDecimal) {

		if (attrDecimal != null)
			this.attrValue = attrDecimal;
	}

	public String getAttrText() {

		if (this.attribute.getType() == WfAttributeType.WF_ATTR_TEXT)
			return (String) this.attrValue;
		return null;

	}

	public Long getAttrNumber() {

		if (this.attribute.getType() == WfAttributeType.WF_ATTR_NUMBER)
			return (Long) this.attrValue;
		return null;
	}

	public Date getAttrDate() {

		if (this.attribute.getType() == WfAttributeType.WF_ATTR_DATE)
			return (Date) this.attrValue;
		return null;
	}

	public Float getAttrDecimal() {

		if (this.attribute.getType() == WfAttributeType.WF_ATTR_DECIMAL)
			return (Float) this.attrValue;
		return null;
	}

	public void setAssignee(String assignee) {

		this.assignee = assignee;
	}

	public WfAttribute getAttribute() {

		return attribute;
	}

	public void setAttribute(WfAttribute attribute) {

		this.attribute = attribute;
	}

	protected WfAssignment() {

		super();
	}

	@Override
	public String getAssignee() {

		// TODO Auto-generated method stub
		return assignee;
	}
}
