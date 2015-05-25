package uk.co.jmr.sdp.domain.dt;

import java.util.HashSet;
import java.util.Set;

import uk.co.jmr.sdp.domain.Doctype;

public class ModelCombo implements Comparable<ModelCombo> {

	private long id;
	private String modelName;
	private String description;
	private Doctype doctype;
	private char isActive;

	public char getIsActive() {

		return isActive;
	}

	public void setIsActive(char isActive) {

		this.isActive = isActive;
	}

	private Set<ModelComboValue> modelComboValues = new HashSet<ModelComboValue>();

	public ModelCombo() {

		this.id = -1;
	}

	// public ModelCombo(String modelName, Doctype doctype) {
	// this.id = -1;
	// this.modelName = modelName;
	// this.doctype = doctype;
	// }
	// public ModelCombo(String modelName, String description, Doctype doctype)
	// {
	// this.id = -1;
	// this.modelName = modelName;
	// this.description = description;
	// this.doctype = doctype;
	// }
	public ModelCombo(String modelName, String description, Doctype doctype, char isActive) {

		this.id = -1;
		this.modelName = modelName;
		this.description = description;
		this.doctype = doctype;
		this.isActive = isActive;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public long getId() {

		return id;
	}

	public String getModelName() {

		return modelName;
	}

	public Doctype getDoctype() {

		return doctype;
	}

	public Set<ModelComboValue> getModelComboValues() {

		return modelComboValues;
	}

	protected void setId(long id) {

		this.id = id;
	}

	protected void setModelName(String modelName) {

		this.modelName = modelName;
	}

	protected void setDoctype(Doctype doctype) {

		this.doctype = doctype;
	}

	protected void setModelComboValues(Set<ModelComboValue> modelComboValues) {

		this.modelComboValues = modelComboValues;
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
		ModelCombo other = (ModelCombo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "ModelCombo [id=" + id + ", modelName=" + modelName + ", doctype=" + doctype + "]";
	}

	@Override
	public int compareTo(ModelCombo o) {

		return this.getModelName().compareTo(o.getModelName());

	}

}
