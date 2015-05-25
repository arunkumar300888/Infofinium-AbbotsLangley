package uk.co.jmr.sdp.domain;

import com.ardhika.wfar.WfModel;

import uk.co.jmr.webforms.db.pojo.Forms;

public class FormsModel {

	private long id;
	private Forms forms;
	private WfModel wfModel;

	public FormsModel() {

		this.id = -1;
	}

	public FormsModel(Forms forms, WfModel wfModel) {

		super();
		this.id = -1;
		this.forms = forms;
		this.wfModel = wfModel;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public Forms getForms() {

		return forms;
	}

	public void setForms(Forms forms) {

		this.forms = forms;
	}

	public WfModel getWfModel() {

		return wfModel;
	}

	public void setWfModel(WfModel wfModel) {

		this.wfModel = wfModel;
	}

	@Override
	public String toString() {

		return "FormName=" + forms.getFormDefs().getName() + ", Model Name=" + wfModel.getName();
	}

}
