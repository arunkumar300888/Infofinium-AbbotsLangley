package uk.co.jmr.sdp.domain.dt;

import java.util.HashSet;
import java.util.Set;

import uk.co.jmr.sdp.domain.Doctype;

public class DtTemplateCombo {

	private long id;
	private Doctype doctype;
	private String templateUrl;
	private String templateName;
	private Set<DtTemplateComboValue> templateComboValues = new HashSet<DtTemplateComboValue>();

	public DtTemplateCombo() {

		this.id = -1;
	}

	public DtTemplateCombo(long id, Doctype doctype, String templateUrl) {

		this.id = -1;
		this.doctype = doctype;
		this.templateUrl = templateUrl;
	}

	public DtTemplateCombo(Doctype doctype, String templateUrl, String templateName) {

		this.id = -1;
		this.doctype = doctype;
		this.templateUrl = templateUrl;
		this.templateName = templateName;
	}

	public long getId() {

		return id;
	}

	protected void setId(long id) {

		this.id = id;
	}

	public Doctype getDoctype() {

		return doctype;
	}

	protected void setDoctype(Doctype doctype) {

		this.doctype = doctype;
	}

	public String getTemplateUrl() {

		return templateUrl;
	}

	protected void setTemplateUrl(String templateUrl) {

		this.templateUrl = templateUrl;
	}

	public Set<DtTemplateComboValue> getTemplateComboValues() {

		return templateComboValues;
	}

	// protected void setTemplateComboValues(
	public void setTemplateComboValues(Set<DtTemplateComboValue> templateComboValues) {

		this.templateComboValues = templateComboValues;
	}

	public String getTemplateName() {

		return templateName;
	}

	public void setTemplateName(String templateName) {

		this.templateName = templateName;
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
		DtTemplateCombo other = (DtTemplateCombo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "DtTemplateCombo [id=" + id + ", doctype=" + doctype + ", templateUrl=" + templateUrl + "templateName="
			+ templateName
			// +", templateComboValues=" + templateComboValues
			+ "]";
	}

}
