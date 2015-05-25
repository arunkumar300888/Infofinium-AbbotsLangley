package uk.co.jmr.sdp.domain;

import uk.co.jmr.webforms.db.pojo.FormDefs;

public class SecurityGroupForm {
	
	private long id;
	private SecurityGroup securityGroup;
	private FormDefs formDefs;
	
	
	public SecurityGroupForm(){
		super();
		this.setId(-1);
	}
	
	public SecurityGroupForm(SecurityGroup securityGroup,FormDefs formDefs){
		this.setId(-1);
		this.setSecurityGroup(securityGroup);
		this.setFormDefs(formDefs);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SecurityGroup getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(SecurityGroup securityGroup) {
		this.securityGroup = securityGroup;
	}

	public FormDefs getFormDefs() {
		return formDefs;
	}

	public void setFormDefs(FormDefs formDefs) {
		this.formDefs = formDefs;
	}

}
