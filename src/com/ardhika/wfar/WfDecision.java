package com.ardhika.wfar;

import java.util.Date;

public class WfDecision extends WfNode {
	private WfAttribute attribute;
	private WfDecisionOperator operator;
	private Object checkValue;
	private String assignee = "S:SYSTEM";

	public WfDecision(String name, WfAttribute attribute, WfDecisionOperator operator, Object checkValue) {

		super(name, WfNodeType.WF_DECISION_NODE);
		this.attribute = attribute;
		this.operator = operator;
		this.checkValue = checkValue;
	}

	public WfAttribute getAttribute() {

		return attribute;
	}

	Object getChekcValue() {

		return checkValue;
	}

	public boolean evaluate(WfCase wfcase) throws Exception {

		Object attrVal = wfcase.getAttribute(this.attribute.getName());

		switch (wfcase.getAttributeType(this.attribute.getName())) {
			case WF_ATTR_NUMBER:
				return evalNumberDecision(attrVal);
			case WF_ATTR_TEXT:
				return evalTextDecision(attrVal);
			case WF_ATTR_DATE:
				return evalDateDecision(attrVal);
			case WF_ATTR_DECIMAL:
				return evalDecimalDecision(attrVal);
			default:
				return false;
		}
	}

	@Override
	public String toString() {

		return super.toString() + "WfDecision [attribute=" + attribute + ", operator=" + operator + ", chekcValue=" + checkValue
			+ "]";
	}

	// For Hibernate
	protected WfDecision() {

	}

	protected void setAttribute(WfAttribute attribute) {

		this.attribute = attribute;
	}

	public String getOperator() {

		return operator.toString();
	}

	public void setOperator(String operator) {

		this.operator = WfDecisionOperator.valueOf(operator);
	}

	public Long getChkNumber() {

		if (this.attribute.getType() == WfAttributeType.WF_ATTR_NUMBER)
			return (Long) this.checkValue;
		return null;
	}

	public Double getChkDecimal() {

		if (this.attribute.getType() == WfAttributeType.WF_ATTR_DECIMAL)
			return (Double) this.checkValue;
		return null;
	}

	public Date getChkDate() {

		if (this.attribute.getType() == WfAttributeType.WF_ATTR_DATE)
			return (Date) this.checkValue;
		return null;
	}

	public String getChkText() {

		if (this.attribute.getType() == WfAttributeType.WF_ATTR_TEXT)
			return (String) this.checkValue;
		return null;
	}

	public void setChkText(String chkText) {

		if (chkText != null)
			this.checkValue = chkText;
	}

	public void setChkDate(Date chkDate) {

		if (chkDate != null)
			this.checkValue = chkDate;
	}

	public void setChkDecimal(Double chkDecimal) {

		if (chkDecimal != null)
			this.checkValue = chkDecimal;
	}

	public void setChkNumber(Long chkNumber) {

		if (chkNumber != null)
			this.checkValue = chkNumber;
	}

	public String getAssignee() {

		return assignee;
	}

	public void setAssignee(String assignee) {

		this.assignee = assignee;
	}

	@Override
	public boolean addAction(WfAction action) {

		action.setReasonRequired('N');
		return super.addAction(action);
	}

	private boolean evalTextDecision(Object attrVal) {

		String attrValue = (String) attrVal;
		String chekValue = (String) checkValue;
		switch (operator) {
			case WF_DECISION_OP_EQ:
				return attrValue.equals(chekValue);
			case WF_DECISION_OP_NE:
				return !(attrValue.equals(chekValue));
			default:
				return false;
		}
	}

	private boolean evalNumberDecision(Object attrVal) {

		long attrValue = (Long) attrVal;
		long chekValue = (Long) checkValue;
		switch (operator) {
			case WF_DECISION_OP_EQ:
				return (attrValue == chekValue);
			case WF_DECISION_OP_NE:
				return !(attrValue == chekValue);
			case WF_DECISION_OP_LT:
				return (attrValue < chekValue);
			case WF_DECISION_OP_LE:
				return (attrValue <= chekValue);
			case WF_DECISION_OP_GT:
				return (attrValue > chekValue);
			case WF_DECISION_OP_GE:
				return (attrValue <= chekValue);

			default:
				return false;
		}
	}

	private boolean evalDecimalDecision(Object attrVal) {

		float attrValue = (Float) attrVal;
		float chekValue = (Float) checkValue;
		switch (operator) {
			case WF_DECISION_OP_EQ:
				return (attrValue == chekValue);
			case WF_DECISION_OP_NE:
				return !(attrValue == chekValue);
			case WF_DECISION_OP_LT:
				return (attrValue < chekValue);
			case WF_DECISION_OP_LE:
				return (attrValue <= chekValue);
			case WF_DECISION_OP_GT:
				return (attrValue > chekValue);
			case WF_DECISION_OP_GE:
				return (attrValue <= chekValue);

			default:
				return false;
		}
	}

	private boolean evalDateDecision(Object attrVal) {

		Date attrValue = (Date) attrVal;
		Date chekValue = (Date) checkValue;
		switch (operator) {
			case WF_DECISION_OP_BE:
				return (attrValue.before(chekValue));
			case WF_DECISION_OP_NE:
				return !(attrValue.after(chekValue));

			default:
				return false;
		}
	}

}
