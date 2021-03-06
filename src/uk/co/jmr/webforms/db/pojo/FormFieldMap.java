package uk.co.jmr.webforms.db.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * FormFieldMap generated by hbm2java
 */
public class FormFieldMap implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private FormFieldMap formFieldMap;
	private Fields fields;
	private Forms formsBySubformId;
	private Forms formsByFormId;
	private Arrays arrays;
	private int rank;
	private String name;
	private String description;
	private char isMandatory;
	private char onNewpage;
	private short attributes;
	private String validationJs;
	private Set<FormFieldMap> formFieldMaps = new HashSet<FormFieldMap>(0);
	private Set<HtmlFormDef> htmlFormDefs = new HashSet<HtmlFormDef>(0);

	public FormFieldMap() {

		super();
		this.id = -1L;
	}

	public FormFieldMap(long id, Fields fields, Forms formsByFormId, int rank, char isMandatory, char onNewpage) {

		this.id = id;
		this.fields = fields;
		this.formsByFormId = formsByFormId;
		this.rank = rank;
		this.isMandatory = isMandatory;
		this.onNewpage = onNewpage;
	}

	public FormFieldMap(long id, FormFieldMap formFieldMap, Fields fields, Forms formsBySubformId, Forms formsByFormId,
		Arrays arrays, int rank, String name, String description, char isMandatory, char onNewpage, short attributes,
		String validationJs, Set<FormFieldMap> formFieldMaps, Set<HtmlFormDef> htmlFormDefs) {

		this.id = id;
		this.formFieldMap = formFieldMap;
		this.fields = fields;
		this.formsBySubformId = formsBySubformId;
		this.formsByFormId = formsByFormId;
		this.arrays = arrays;
		this.rank = rank;
		this.name = name;
		this.description = description;
		this.isMandatory = isMandatory;
		this.onNewpage = onNewpage;
		this.attributes = attributes;
		this.validationJs = validationJs;
		this.formFieldMaps = formFieldMaps;
		this.htmlFormDefs = htmlFormDefs;
	}

	public long getId() {

		return this.id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public FormFieldMap getFormFieldMap() {

		return this.formFieldMap;
	}

	public void setFormFieldMap(FormFieldMap formFieldMap) {

		this.formFieldMap = formFieldMap;
	}

	public Fields getFields() {

		return this.fields;
	}

	public void setFields(Fields fields) {

		this.fields = fields;
	}

	public Forms getFormsBySubformId() {

		return this.formsBySubformId;
	}

	public void setFormsBySubformId(Forms formsBySubformId) {

		this.formsBySubformId = formsBySubformId;
	}

	public Forms getFormsByFormId() {

		return this.formsByFormId;
	}

	public void setFormsByFormId(Forms formsByFormId) {

		this.formsByFormId = formsByFormId;
	}

	public Arrays getArrays() {

		return this.arrays;
	}

	public void setArrays(Arrays arrays) {

		this.arrays = arrays;
	}

	public int getRank() {

		return this.rank;
	}

	public void setRank(int rank) {

		this.rank = rank;
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

	public char getIsMandatory() {

		return this.isMandatory;
	}

	public void setIsMandatory(char isMandatory) {

		this.isMandatory = isMandatory;
	}

	public char getOnNewpage() {

		return this.onNewpage;
	}

	public void setOnNewpage(char onNewpage) {

		this.onNewpage = onNewpage;
	}

	public short getAttributes() {

		return this.attributes;
	}

	public void setAttributes(short attributes) {

		this.attributes = attributes;
	}

	public String getValidationJs() {

		return this.validationJs;
	}

	public void setValidationJs(String validationJs) {

		this.validationJs = validationJs;
	}

	public Set<FormFieldMap> getFormFieldMaps() {

		return this.formFieldMaps;
	}

	public void setFormFieldMaps(Set<FormFieldMap> formFieldMaps) {

		this.formFieldMaps = formFieldMaps;
	}

	public Set<HtmlFormDef> getHtmlFormDefs() {

		return this.htmlFormDefs;
	}

	public void setHtmlFormDefs(Set<HtmlFormDef> htmlFormDefs) {

		this.htmlFormDefs = htmlFormDefs;
	}
}
