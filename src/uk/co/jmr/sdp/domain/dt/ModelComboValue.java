package uk.co.jmr.sdp.domain.dt;

public class ModelComboValue {

	private long id;
	private ModelCombo modelCombo;
	private Attribute attribute;
	private AttributeValue attributeValue;

	public ModelComboValue() {

		this.id = -1;
	}

	public ModelComboValue(ModelCombo modelCombo, Attribute attribute, AttributeValue attributeValue) {

		this.id = -1;
		this.modelCombo = modelCombo;
		this.attribute = attribute;
		this.attributeValue = attributeValue;
	}

	public long getId() {

		return id;
	}

	public ModelCombo getModelCombo() {

		return modelCombo;
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

	protected void setModelCombo(ModelCombo modelCombo) {

		this.modelCombo = modelCombo;
	}

	protected void setAttribute(Attribute attribute) {

		this.attribute = attribute;
	}

	protected void setAttributeValue(AttributeValue attributeValue) {

		this.attributeValue = attributeValue;
	}

	@Override
	public String toString() {

		return "ModelComboValues [id=" + id + ", modelCombo=" + modelCombo + ", attribute=" + attribute + ", attributeValue="
			+ attributeValue + "]";
	}

}
