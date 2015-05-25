package uk.co.jmr.sdp.domain;

import uk.co.jmr.sdp.domain.dt.AttributeValue;

public class SecurityGroupCombo {

	// Only insert and delete Allowed, No update
	private long id = -1;
	private SecurityGroup securityGroup;
	private Doctype doctype;
	private AttributeValue attributeValue1;
	private AttributeValue attributeValue2;
	private AttributeValue attributeValue3;
	/*private AttributeValue attributeValue4;*/
	private char defaultSg;

	public SecurityGroupCombo() {

		this.id = -1;
	}

	public SecurityGroupCombo(SecurityGroup securityGroup, Doctype doctype, AttributeValue attributeValue1,
		AttributeValue attributeValue2, AttributeValue attributeValue3, char defaultSg) {

		this.id = -1;
		this.securityGroup = securityGroup;
		this.doctype = doctype;
		this.attributeValue1 = attributeValue1;
		this.attributeValue2 = attributeValue2;
		this.attributeValue3 = attributeValue3;
		/* this.attributeValue4 = attributeValue4;*/
		this.defaultSg = defaultSg;
	}

	// protected long getId() {
	public long getId() {

		return id;
	}

	protected void setId(long id) {

		this.id = id;
	}

	public SecurityGroup getSecurityGroup() {

		return securityGroup;
	}

	protected void setSecurityGroup(SecurityGroup securityGroup) {

		this.securityGroup = securityGroup;
	}

	// protected Doctype getDoctype() {
	public Doctype getDoctype() {

		return doctype;
	}

	protected void setDoctype(Doctype doctype) {

		this.doctype = doctype;
	}

	// protected AttributeValue getAttributeValue1() {
	public AttributeValue getAttributeValue1() {

		return attributeValue1;
	}

	protected void setAttributeValue1(AttributeValue attributeValue1) {

		this.attributeValue1 = attributeValue1;
	}

	// protected AttributeValue getAttributeValue2() {
	public AttributeValue getAttributeValue2() {

		return attributeValue2;
	}

	protected void setAttributeValue2(AttributeValue attributeValue2) {

		this.attributeValue2 = attributeValue2;
	}

	// protected AttributeValue getAttributeValue3() {
	public AttributeValue getAttributeValue3() {

		return attributeValue3;
	}

	protected void setAttributeValue3(AttributeValue attributeValue3) {

		this.attributeValue3 = attributeValue3;
	}

	// protected AttributeValue getAttributeValue4() {
	/*public AttributeValue getAttributeValue4() {

		return attributeValue4;
	}

	protected void setAttributeValue4(AttributeValue attributeValue4) {

		this.attributeValue4 = attributeValue4;
	}*/

	protected char getDefaultSg() {

		return defaultSg;
	}

	protected void setDefaultSg(char defaultSg) {

		this.defaultSg = defaultSg;
	}

	@Override
	public String toString() {

		return "SecurityGroupCombo [id=" + id + ", securityGroup=" + securityGroup + ", doctype=" + doctype
			+ ", attributeValue1=" + attributeValue1 + ", attributeValue2=" + attributeValue2 + ", attributeValue3="
			+ attributeValue3 + "]";
	}
}
