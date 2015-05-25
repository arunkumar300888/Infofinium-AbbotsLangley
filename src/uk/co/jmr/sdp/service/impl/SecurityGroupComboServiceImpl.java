package uk.co.jmr.sdp.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.AttributeValueDao;
import uk.co.jmr.sdp.dao.DoctypeDao;
import uk.co.jmr.sdp.dao.SecurityGroupComboDao;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.DocumentAttribute;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupCombo;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.service.SecurityGroupComboService;

@Service("securityGroupComboService")
public class SecurityGroupComboServiceImpl implements SecurityGroupComboService {

	@Autowired
	private SecurityGroupComboDao securityGroupComboDao;
	@Autowired
	private AttributeValueDao attributeValueDao;
	@Autowired
	private DoctypeDao doctypeDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SecurityGroupCombo> findSecurityGroupCombo(SecurityGroup secGroup) {

		return this.securityGroupComboDao.findSecurityGroupCombo(secGroup);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SecurityGroupCombo findSecurityGroupComboById(long id) {

		return this.securityGroupComboDao.findSecurityGroupComboById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveSgCombo(SecurityGroupCombo securityGroupCombo) {

		this.securityGroupComboDao.saveSgCombo(securityGroupCombo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSgCombo(SecurityGroupCombo securityGroupCombo) {

		this.securityGroupComboDao.deleteSgCombo(securityGroupCombo);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SecurityGroup findDefaultSecurityGroupForCombo(Document document, String sgAttrs) {

		List<AttributeValue> attrVals = extractAttrValues(document);

		return this.securityGroupComboDao.findDefaultSecurityGroupForCombo(attrVals, sgAttrs, document.getDoctype());
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	private List<AttributeValue> extractAttrValues(Document document) {

		Set<DocumentAttribute> dts = document.getDocumentAttributes();
		List<AttributeValue> attrVals = new ArrayList<AttributeValue>();

		for (DocumentAttribute d : dts) {
			attrVals.add(d.getAttributeValue());
		}
		for (AttributeValue av : attrVals) {
			//System.out.println(av);
		}
		Collections.sort(attrVals);
		/*System.out.println("1. " + attrVals.get(0));
		System.out.println("2. " + attrVals.get(1));
		System.out.println("3. " + attrVals.get(2));
		System.out.println("4. " + attrVals.get(3));*/
		return attrVals;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SecurityGroup> findSecurityGroupsForCombo(Document document, String sgAttrs) {

		List<AttributeValue> attrVals = extractAttrValues(document);

		return this.securityGroupComboDao.findSecurityGroupsForCombo(attrVals, sgAttrs, document.getDoctype());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SecurityGroup> findSecurityGroupsFor(List<Long> attrValIds, String sgAttrs, long doctypeId) {

		List<AttributeValue> attrVals = new ArrayList<AttributeValue>();

		for (long l : attrValIds) {
			AttributeValue av = this.attributeValueDao.findAttributeValueById(l);
			attrVals.add(av);
		}

		Doctype doctype = this.doctypeDao.findDoctypeById(doctypeId);

		return this.securityGroupComboDao.findSecurityGroupsFor(attrVals, sgAttrs, doctype);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SecurityGroup findSecurityGroup(List<AttributeValue> attrVals, String sgAttrs, Doctype doctype) {

		return this.securityGroupComboDao.findDefaultSecurityGroupForCombo(attrVals, sgAttrs, doctype);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SecurityGroup> findDefaultSecurityGroups(List<AttributeValue> attrVals, String sgAttrs, Doctype doctype) {

		return this.securityGroupComboDao.findDefaultSecurityGroups(attrVals, sgAttrs, doctype);
	}

	//Added For Security Group Combo Already Exists or Not
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SecurityGroupCombo findSecurityGroupComboByAttrAndDoctype(List<AttributeValue> attrVals, SecurityGroup secGroup,Doctype doctypeObj) {

		return this.securityGroupComboDao.findSecurityGroupComboByAttrAndDoctype(attrVals,secGroup, doctypeObj);
	}
	
	
	
	
}
