package uk.co.jmr.sdp.domain.dt;

public class AttributeValue implements Comparable<AttributeValue> {

	private long id;
	private String value;
	private Attribute attr;
	private String abbreviation;
	private char isActive;

	public char getIsActive() {

		return isActive;
	}

	public void setIsActive(char isActive) {

		this.isActive = isActive;
	}

	public AttributeValue() {

		this.id = -1;
	}

	public AttributeValue(String value, Attribute attr) {

		this.id = -1;
		this.value = value;
		this.attr = attr;
	}

	public AttributeValue(String value, Attribute attr, String abbreviation) {

		this.id = -1;
		this.value = value;
		this.attr = attr;
		this.abbreviation = abbreviation;
	}

	public AttributeValue(String value, Attribute attr, String abbreviation, char isActive) {

		this.id = -1;
		this.value = value;
		this.attr = attr;
		this.abbreviation = abbreviation;
		this.isActive = isActive;
	}

	public String getAbbreviation() {

		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {

		this.abbreviation = abbreviation;
	}

	public long getId() {

		return id;
	}

	public String getValue() {

		return value;
	}

	public Attribute getAttr() {

		return attr;
	}

	protected void setId(long id) {

		this.id = id;
	}

	// protected void setValue(String value) {
	public void setValue(String value) {

		this.value = value;
	}

	protected void setAttr(Attribute attr) {

		this.attr = attr;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeValue other = (AttributeValue) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "AttributeValue [id=" + id + ", value=" + value +
		// ", dtAttr=" + dtAttr +
			"]";
	}

	@Override
	public int compareTo(AttributeValue o) {

		return this.getValue().compareTo(o.getValue()); // For Attribute value
														// comparison sorted
														// order by Kadamba
		// return this.attr.getOrder() - o.getAttr().getOrder();
	}
}
