package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupCombo;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

public interface SecurityGroupComboService {

	void saveSgCombo(SecurityGroupCombo securityGroupCombo);

	void deleteSgCombo(SecurityGroupCombo securityGroupCombo);

	SecurityGroup findDefaultSecurityGroupForCombo(Document document, String sgAttrs);

	List<SecurityGroup> findSecurityGroupsForCombo(Document document, String sgAttrs);

	List<SecurityGroup> findSecurityGroupsFor(List<Long> attrValIds, String sgAttrs, long doctypeId);

	SecurityGroup findSecurityGroup(List<AttributeValue> attrVals, String sgAttrs, Doctype doctype);

	List<SecurityGroup> findDefaultSecurityGroups(List<AttributeValue> attrVals, String sgAttrs, Doctype doctype);

	// For Security Group Combo Assignment List & delete
	List<SecurityGroupCombo> findSecurityGroupCombo(SecurityGroup secGroup);

	SecurityGroupCombo findSecurityGroupComboById(long id);
	
	//To Check Sec Group Combo Exists or Not
	SecurityGroupCombo findSecurityGroupComboByAttrAndDoctype(List<AttributeValue> attrVals,SecurityGroup secGroup,Doctype doctypeObj);
}
