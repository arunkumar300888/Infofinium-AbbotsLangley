package uk.co.jmr.sdp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.SecurityGroupComboDao;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.SecurityGroup;
import uk.co.jmr.sdp.domain.SecurityGroupCombo;
import uk.co.jmr.sdp.domain.dt.AttributeValue;

@Repository("securityGroupComboDao")
public class SecurityGroupComboDaoImpl implements SecurityGroupComboDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityGroupCombo> findSecurityGroupCombo(
			SecurityGroup secGroup) {

		return this.hibernateTemplate.find(
				"from SecurityGroupCombo s where s.securityGroup=?", secGroup);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SecurityGroupCombo findSecurityGroupComboById(long id) {

		List<SecurityGroupCombo> result = this.hibernateTemplate.find(
				"from SecurityGroupCombo s where s.id=?", id);
		if (result.size() >= 1)
			return result.get(0);
		return null;
		// return
		// this.hibernateTemplate.find("from SecurityGroupCombo s where s.id=?",id);
	}

	@Override
	public void saveSgCombo(SecurityGroupCombo securityGroupCombo) {

		this.hibernateTemplate.saveOrUpdate(securityGroupCombo);
	}

	@Override
	public void deleteSgCombo(SecurityGroupCombo securityGroupCombo) {

		this.hibernateTemplate.delete(securityGroupCombo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SecurityGroup findDefaultSecurityGroupForCombo(
			List<AttributeValue> attrValueList, String sgAttrs, Doctype doctype) {
		DetachedCriteria criteria = extractCriteria(attrValueList, sgAttrs,
				doctype, true);
		List<SecurityGroupCombo> result = this.hibernateTemplate
				.findByCriteria(criteria);
		// System.out.println(result);
		if (result.size() == 0)
			return null;

		SecurityGroupCombo sgc = result.get(0);
		return sgc.getSecurityGroup();
	}

	private DetachedCriteria extractCriteria(
			List<AttributeValue> attrValueList, String sgAttrs,
			Doctype doctype, boolean defaultOnly) {

		DetachedCriteria criteria = DetachedCriteria.forClass(
				SecurityGroupCombo.class).add(Restrictions.conjunction());

		if (defaultOnly)
			criteria.add(Restrictions.eq("defaultSg", "Y"));

		criteria.add(Restrictions.eq("doctype", doctype));

		if (sgAttrs.charAt(0) == 'Y')
			criteria = criteria.add(Restrictions.eq("attributeValue1",
					attrValueList.get(0)));

		if (sgAttrs.charAt(1) == 'Y')
			criteria = criteria.add(Restrictions.eq("attributeValue2",
					attrValueList.get(1)));

		if (sgAttrs.charAt(2) == 'Y') {
			// System.out.println("ATTRVAL1= " + attrValueList.get(0));
			// System.out.println("ATTRVAL2= " + attrValueList.get(1));
			// System.out.println("ATTRVAL3= " + attrValueList.get(2));
			// System.out.println("ATTRVAL4= " + attrValueList.get(3));
			criteria = criteria.add(Restrictions.eq("attributeValue3",
					attrValueList.get(2)));

		}

		/*if (sgAttrs.charAt(3) == 'Y')
			criteria = criteria.add(Restrictions.eq("attributeValue4",
					attrValueList.get(3)));*/

		// System.out.println("Criteria-> " + criteria);
		return criteria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityGroup> findSecurityGroupsForCombo(
			List<AttributeValue> attrValueList, String sgAttrs, Doctype doctype) {

		DetachedCriteria criteria = extractCriteria(attrValueList, sgAttrs,
				doctype, false);
		List<SecurityGroupCombo> result = this.hibernateTemplate
				.findByCriteria(criteria);
		// System.out.println(result);
		List<SecurityGroup> sgs = new ArrayList<SecurityGroup>();
		for (SecurityGroupCombo sgc : result) {
			sgs.add(sgc.getSecurityGroup());
		}
		return sgs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityGroup> findSecurityGroupsFor(
			List<AttributeValue> attrValueList, String sgAttrs, Doctype doctype) {

		DetachedCriteria criteria = extractCriteria(attrValueList, sgAttrs,
				doctype, false);
		List<SecurityGroupCombo> result = this.hibernateTemplate
				.findByCriteria(criteria);
		// System.out.println(result);
		List<SecurityGroup> sgs = new ArrayList<SecurityGroup>();
		for (SecurityGroupCombo sgc : result) {
			sgs.add(sgc.getSecurityGroup());
		}
		return sgs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityGroup> findDefaultSecurityGroups(
			List<AttributeValue> attrValueList, String sgAttrs, Doctype doctype) {

		DetachedCriteria criteria = extractCriteria(attrValueList, sgAttrs,
				doctype, true);
		List<SecurityGroupCombo> result = this.hibernateTemplate
				.findByCriteria(criteria);
		// System.out.println("Result-> " + result);
		List<SecurityGroup> sgs = new ArrayList<SecurityGroup>();
		for (SecurityGroupCombo sgc : result) {
			if (sgc.getSecurityGroup().getIsActive() == 'Y') {
				sgs.add(sgc.getSecurityGroup());
			}
			// sgs.add(sgc.getSecurityGroup());
		}
		return sgs;
	}

	// Added For Security Group Combo Already Exists or Not
	@SuppressWarnings("unchecked")
	@Override
	public SecurityGroupCombo findSecurityGroupComboByAttrAndDoctype(
			List<AttributeValue> attrVals, SecurityGroup secGroup,
			Doctype doctypeObj) {

		List<SecurityGroupCombo> result = this.hibernateTemplate
				.find("from SecurityGroupCombo s where s.securityGroup=? and s.doctype=? and s.attributeValue1=? and s.attributeValue2=? and s.attributeValue3=? ",
						secGroup, doctypeObj, attrVals.get(0), attrVals.get(1),
						attrVals.get(2));
		if (result.size() >= 1)
			return result.get(0);
		return null;
	}

}
