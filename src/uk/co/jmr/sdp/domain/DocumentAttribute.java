package uk.co.jmr.sdp.domain;

import uk.co.jmr.sdp.domain.dt.Attribute;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

public class DocumentAttribute implements Cloneable {

	private long id;
	private Document document;
	private Attribute attribute;
	private AttributeValue attributeValue;

	public DocumentAttribute() {

		this.id = -1;
	}

	public DocumentAttribute(Document document, Attribute attribute, AttributeValue attributeValue) {

		this.id = -1;
		this.document = document;
		this.attribute = attribute;
		this.attributeValue = attributeValue;
	}

	public long getId() {

		return id;
	}

	public Document getDocument() {

		return document;
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

	protected void setDocument(Document document) {

		this.document = document;
	}

	protected void setAttribute(Attribute attribute) {

		this.attribute = attribute;
	}

	protected void setAttributeValue(AttributeValue attributeValue) {

		this.attributeValue = attributeValue;
	}

	@Override
	public String toString() {

		return "DocumentAttribute [id=" + id + ", document=" + document + ", attribute=" + attribute + ", attributeValue="
			+ attributeValue + "]";
	}

	public DocumentAttribute getCloned() {

		return (DocumentAttribute) clone();

	}

	@Override
	protected Object clone() {

		try {
			return super.clone();
		}
		catch (CloneNotSupportedException e) {

			e.printStackTrace();

			return null;
		}
	}

}
