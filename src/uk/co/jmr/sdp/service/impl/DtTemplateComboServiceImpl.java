package uk.co.jmr.sdp.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.DoctypeDao;
import uk.co.jmr.sdp.dao.DtAttributeDao;
import uk.co.jmr.sdp.dao.DtAttributeValueDao;
import uk.co.jmr.sdp.dao.DtTemplateComboDao;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.DtTemplateCombo;
import uk.co.jmr.sdp.domain.dt.DtTemplateComboValue;
import uk.co.jmr.sdp.service.DtTemplateComboService;

@Service("dtTemplateComboService")
public class DtTemplateComboServiceImpl implements DtTemplateComboService {

	@Autowired
	private DtTemplateComboDao dtTemplateComboDao;

	@Autowired
	private DoctypeDao doctypeDao;

	@Autowired
	private DtAttributeDao dtAtributeDao;

	@Autowired
	private DtAttributeValueDao dtAttributeValue;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DtTemplateCombo findTemplateById(long id) {

		return this.dtTemplateComboDao.findTemplateById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DtTemplateCombo findTemplateByName(String name) {

		return this.dtTemplateComboDao.findTemplateByName(name);
	}

	@Override
	public void save(DtTemplateCombo dtTemplate) {

		this.dtTemplateComboDao.save(dtTemplate);
	}

	@Override
	public void delete(DtTemplateCombo dtTemplate) {

		this.dtTemplateComboDao.delete(dtTemplate);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DtTemplateCombo> listAllTemplates() {

		return this.dtTemplateComboDao.listAllTemplates();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<DtTemplateCombo> findMatchForFirstLevelAttrValue(long attrValueId) {

		AttributeValue attrValue = this.dtAttributeValue.findDtAttrValueById(attrValueId);
		List<DtTemplateCombo> dtc = this.dtTemplateComboDao.findMatchForFirstLevel(attrValue);
		Set<DtTemplateCombo> templateComboSet = new HashSet<DtTemplateCombo>();
		for (DtTemplateCombo d : dtc) {
			templateComboSet.add(d);
		}
		return templateComboSet;
	}

	public Set<AttributeValue> getSecondLevelAttrValuesFromTemplateCombos(Set<DtTemplateCombo> dtc) {

		Set<AttributeValue> attributes = new HashSet<AttributeValue>();
		for (DtTemplateCombo d : dtc) {
			Set<DtTemplateComboValue> dtcv = d.getTemplateComboValues();
			for (DtTemplateComboValue v : dtcv) {
				if ((v.getAttribute().getOrder() == 2)) {
					attributes.add(v.getAttributeValue());
				}
			}
		}
		return attributes;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<DtTemplateCombo> findMatchForSecondLevelAttrValue(Set<DtTemplateCombo> dtc, AttributeValue secondLevelAttrValue) {

		Set<DtTemplateCombo> tempSet = new HashSet<DtTemplateCombo>();
		for (DtTemplateCombo d : dtc) {
			Set<DtTemplateComboValue> dtcv = d.getTemplateComboValues();
			for (DtTemplateComboValue v : dtcv) {
				//System.out.println("---2AttributeValue " + v.getAttribute() + " secondLevelAttrValue-> " + secondLevelAttrValue
			//		+ "d= " + d);
				if ((v.getAttribute().getOrder() == 2) && (v.getAttributeValue().equals(secondLevelAttrValue))) {
					//System.out.println("IN if1");
					tempSet.add(d);
					break;
				}
			}
		}
		return tempSet;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<AttributeValue> getThirdLevelAttrValuesFromTemplateCombos(Set<DtTemplateCombo> dtc) {

		Set<AttributeValue> attributes = new HashSet<AttributeValue>();
		for (DtTemplateCombo d : dtc) {
			Set<DtTemplateComboValue> dtcv = d.getTemplateComboValues();
			for (DtTemplateComboValue v : dtcv) {
				if ((v.getAttribute().getOrder() == 3)) {
					attributes.add(v.getAttributeValue());
				}
			}
		}
		return attributes;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<DtTemplateCombo> findMatchForThirdLevelAttrValue(Set<DtTemplateCombo> dtc, AttributeValue thirdLevelAttrValue) {

		Set<DtTemplateCombo> tempSet = new HashSet<DtTemplateCombo>();
		for (DtTemplateCombo d : dtc) {
			Set<DtTemplateComboValue> dtcv = d.getTemplateComboValues();
			for (DtTemplateComboValue v : dtcv) {
				if ((v.getAttribute().getOrder() == 3) && (v.getAttributeValue().equals(thirdLevelAttrValue))) {
					tempSet.add(d);
					break;
				}
			}
		}
		return tempSet;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<AttributeValue> getFourthLevelAttrValuesFromTemplateCombos(Set<DtTemplateCombo> dtc) {

		Set<AttributeValue> attributes = new HashSet<AttributeValue>();
		for (DtTemplateCombo d : dtc) {
			//System.out.println("d ** " + d);
			Set<DtTemplateComboValue> dtcv = d.getTemplateComboValues();
			//System.out.println("dtcv >> " + dtcv);
			for (DtTemplateComboValue v : dtcv) {
				//System.out.println("Final attrv " + v.getAttributeValue().getId());
				if ((v.getAttribute().getOrder() == 4)) {
					attributes.add(v.getAttributeValue());
				}
			}
		}
		return attributes;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<DtTemplateCombo> findMatchForFourthLevelAttrValue(Set<DtTemplateCombo> dtc, AttributeValue fourthLevelAttrValue) {

		Set<DtTemplateCombo> tempSet = new HashSet<DtTemplateCombo>();
		for (DtTemplateCombo d : dtc) {
			Set<DtTemplateComboValue> dtcv = d.getTemplateComboValues();
			for (DtTemplateComboValue v : dtcv) {
				if ((v.getAttribute().getOrder() == 4) && (v.getAttributeValue().equals(fourthLevelAttrValue))) {
					tempSet.add(d);
					break;
				}
			}
		}
		return tempSet;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<AttributeValue> getFifthLevelAttrValuesFromTemplateCombos(Set<DtTemplateCombo> dtc) {

		Set<AttributeValue> attributes = new HashSet<AttributeValue>();
		for (DtTemplateCombo d : dtc) {
			//System.out.println("d ** " + d);
			Set<DtTemplateComboValue> dtcv = d.getTemplateComboValues();
			//System.out.println("dtcv >> " + dtcv);
			for (DtTemplateComboValue v : dtcv) {
				//System.out.println("Final attrv " + v.getAttributeValue().getId());
				if ((v.getAttribute().getOrder() == 5)) {
					attributes.add(v.getAttributeValue());
				}
			}
		}
		return attributes;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<DtTemplateCombo> findMatchForFifthLevelAttrValue(Set<DtTemplateCombo> dtc, AttributeValue fourthLevelAttrValue) {

		Set<DtTemplateCombo> tempSet = new HashSet<DtTemplateCombo>();
		for (DtTemplateCombo d : dtc) {
			Set<DtTemplateComboValue> dtcv = d.getTemplateComboValues();
			for (DtTemplateComboValue v : dtcv) {
				if ((v.getAttribute().getOrder() == 5) && (v.getAttributeValue().equals(fourthLevelAttrValue))) {
					tempSet.add(d);
					break;
				}
			}
		}
		return tempSet;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<DtTemplateCombo> getTemplatesMatchForDoctype(Set<DtTemplateCombo> dtc, long doctypeId) {

		Doctype doctype = this.doctypeDao.findDoctypeById(doctypeId);
		Set<DtTemplateCombo> tempSet = new HashSet<DtTemplateCombo>();
		for (DtTemplateCombo d : dtc) {
			if (d.getDoctype().equals(doctype)) {
				tempSet.add(d);
				break;
			}
		}
		return tempSet;
	}
}
