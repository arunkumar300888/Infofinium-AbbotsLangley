package uk.co.jmr.sdp.domain.dt;

public class DtTemplateComboValue {

	private long id;
	private DtTemplateCombo dtTemplateCombo;
	private Attribute attribute;
	private AttributeValue attributeValue;

	public DtTemplateComboValue() {

		this.id = -1;
	}

	public DtTemplateComboValue(DtTemplateCombo dtTemplateCombo, Attribute attribute, AttributeValue attributeValue) {

		this.id = -1;
		this.dtTemplateCombo = dtTemplateCombo;
		this.attribute = attribute;
		this.attributeValue = attributeValue;
	}

	public long getId() {

		return id;
	}

	public DtTemplateCombo getDtTemplateCombo() {

		return dtTemplateCombo;
	}

	public Attribute getAttribute() {

		return attribute;
	}

	public AttributeValue getAttributeValue() {

		return attributeValue;
	}

	protected void setId(long id) {

		this.id = id;
	}

	protected void setDtTemplateCombo(DtTemplateCombo dtTemplateCombo) {

		this.dtTemplateCombo = dtTemplateCombo;
	}

	protected void setAttribute(Attribute attribute) {

		this.attribute = attribute;
	}

	protected void setAttributeValue(AttributeValue attributeValue) {

		this.attributeValue = attributeValue;
	}

	@Override
	public String toString() {

		return "DtTemplateComboValue [id=" + id + ", dtTemplateCombo=" + dtTemplateCombo + ", attribute=" + attribute
			+ ", attributeValue=" + attributeValue + "]";
	}
}
