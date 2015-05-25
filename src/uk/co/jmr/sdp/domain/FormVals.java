package uk.co.jmr.sdp.domain;

public class FormVals {
	
	private long id;
	/*private long userFormId;
	private long formId;*/
	private String value;
	
	public FormVals(){
		super();
		this.setId(-1);
	}
	
	public FormVals(String value){
		super();
		this.id=-1;
		/*this.setUserFormId=userFormId;
		this.setFormId=formId;*/
		this.value=value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/*public long getUserFormId() {
		return userFormId;
	}

	public void setUserFormId(long userFormId) {
		this.userFormId = userFormId;
	}

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}
*/
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
