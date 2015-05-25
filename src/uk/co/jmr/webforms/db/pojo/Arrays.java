package uk.co.jmr.webforms.db.pojo;

import java.util.HashSet;
import java.util.Set;

public class Arrays implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String description;
	private Set<Fields> fieldses = new HashSet<Fields>(0);
	private Set<FormFieldMap> formFieldMaps = new HashSet<FormFieldMap>(0);
	private Set<ArrayItems> arrayItemses = new HashSet<ArrayItems>(0);

	public Arrays() {

		super();
		this.id = -1L;
	}

	public Arrays(long id, String name) {

		this.id = id;
		this.name = name;
	}

	public Arrays(long id, String name, String description, Set<Fields> fieldses, Set<FormFieldMap> formFieldMaps,
		Set<ArrayItems> arrayItemses) {

		this.id = id;
		this.name = name;
		this.description = description;
		this.fieldses = fieldses;
		this.formFieldMaps = formFieldMaps;
		this.arrayItemses = arrayItemses;
	}

	public long getId() {

		return this.id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public String getName() {

		return this.name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getDescription() {

		return this.description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public Set<Fields> getFieldses() {

		return this.fieldses;
	}

	public void setFieldses(Set<Fields> fieldses) {

		this.fieldses = fieldses;
	}

	public Set<FormFieldMap> getFormFieldMaps() {

		return this.formFieldMaps;
	}

	public void setFormFieldMaps(Set<FormFieldMap> formFieldMaps) {

		this.formFieldMaps = formFieldMaps;
	}

	public Set<ArrayItems> getArrayItemses() {

		return this.arrayItemses;
	}

	public void setArrayItemses(Set<ArrayItems> arrayItemses) {

		this.arrayItemses = arrayItemses;
	}
}
