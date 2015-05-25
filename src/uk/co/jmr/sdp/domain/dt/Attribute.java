package uk.co.jmr.sdp.domain.dt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Attribute {

	private long id;
	private String name;
	private int order;
	private Set<AttributeValue> attrValues = new HashSet<AttributeValue>();
	private Map<Long, AttributeValue> attrMap = new HashMap<Long, AttributeValue>();

	public Attribute() {

		this.id = -1;
	}

	public Attribute(String name, int order) {

		this.id = -1;
		this.name = name;
		this.order = order;
	}

	public long getId() {

		return id;
	}

	public String getName() {

		return name;
	}

	public int getOrder() {

		return order;
	}

	// protected void setId(long id) {
	public void setId(long id) {

		this.id = id;
	}

	// Change for Admin module
	// protected void setName(String name) {
	public void setName(String name) {

		this.name = name;
	}

	// protected void setId(long id) {
	// this.id = id;
	// }
	// protected void setName(String name) {
	// this.name = name;
	// }

	protected void setOrder(int order) {

		this.order = order;
	}

	protected void setAttrValues(Set<AttributeValue> attrValues) {

		this.attrValues = attrValues;
	}

	public Set<AttributeValue> getAttrValues() {

		return attrValues;
	}

	public boolean addToAttrValue(AttributeValue attrValue) {

		if (attrMap.containsKey(attrValue.getId()))
			return false;
		attrMap.put(attrValue.getId(), attrValue);
		this.attrValues.add(attrValue);
		return true;
	}

	public void removeFromAttrValue(AttributeValue attrValue) {

		if (attrMap.containsKey(attrValue.getId())) {
			attrMap.remove(attrValue).getId();
			this.attrValues.remove(attrValue);
		}
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
		Attribute other = (Attribute) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "Attribute [id=" + id + ", name=" + name + ", order=" + order + "]";
	}

}
