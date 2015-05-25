package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupCombo;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

public interface SecurityGroupComboDao {

	void saveSgCombo(SecurityGroupCombo securityGroupCombo);

	void deleteSgCombo(SecurityGroupCombo securityGroupCombo);

	SecurityGroup findDefaultSecurityGroupForCombo(List<AttributeValue> attrValueList, String sgAttrs, Doctype doctype);

	List<SecurityGroup> findSecurityGroupsForCombo(List<AttributeValue> attrValueList, String sgAttrs, Doctype doctype);

	List<SecurityGroup> findSecurityGroupsFor(List<AttributeValue> attrValueList, String sgAttrs, Doctype doctype);

	List<SecurityGroup> findDefaultSecurityGroups(List<AttributeValue> attrValueList, String sgAttrs, Doctype doctype);

	List<SecurityGroupCombo> findSecurityGroupCombo(SecurityGroup secGroup);

	SecurityGroupCombo findSecurityGroupComboById(long id);

	//Added For Security Group Combo Already Exists or Not
	SecurityGroupCombo findSecurityGroupComboByAttrAndDoctype(
			List<AttributeValue> attrVals, SecurityGroup secGroup,
			Doctype doctypeObj);

}
