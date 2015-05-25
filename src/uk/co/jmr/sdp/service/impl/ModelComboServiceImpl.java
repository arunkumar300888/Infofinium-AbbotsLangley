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
import uk.co.jmr.sdp.dao.ModelComboDao;
import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.DtTemplateCombo;
import uk.co.jmr.sdp.domain.dt.DtTemplateComboValue;
import uk.co.jmr.sdp.domain.dt.ModelCombo;
import uk.co.jmr.sdp.domain.dt.ModelComboValue;
import uk.co.jmr.sdp.service.ModelComboService;

@Service("modelComboService")
public class ModelComboServiceImpl implements ModelComboService {

	@Autowired
	private ModelComboDao modelComboDao;

	@Autowired
	private DtAttributeValueDao dtAttributeValueDao;

	@Autowired
	private DoctypeDao doctypeDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ModelCombo> listAllModelCombos() {

		return this.modelComboDao.listAllModelCombos();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ModelCombo> listAllModelCombosWithInActive() {

		return this.modelComboDao.listAllModelCombosWithInActive();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<ModelCombo> findMatchForFirstLevelAttrValue(long attrValueId) {

		AttributeValue attrValue = this.dtAttributeValueDao.findDtAttrValueById(attrValueId);

		List<ModelCombo> mc = this.modelComboDao.findMatchForFirstLevel(attrValue);
		Set<ModelCombo> modelComboSet = new HashSet<ModelCombo>();
		for (ModelCombo d : mc) {
			modelComboSet.add(d);
		}
		return modelComboSet;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<ModelCombo> findMatchForSecondLevelAttrValue(Set<ModelCombo> mc, AttributeValue secondLevelAttrValue) {

		Set<ModelCombo> modelComboSet = new HashSet<ModelCombo>();
		for (ModelCombo d : mc) {
			Set<ModelComboValue> mcv = d.getModelComboValues();
			for (ModelComboValue v : mcv) {
				//System.out.println("---2AttributeValue " + v.getAttribute() + " secondLevelAttrValue-> " + secondLevelAttrValue
					//+ "d= " + d);
				if ((v.getAttribute().getOrder() == 2) && (v.getAttributeValue().equals(secondLevelAttrValue))) {
					//System.out.println("IN if1");
					modelComboSet.add(d);
					break;
				}
			}
		}
		return modelComboSet;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<AttributeValue> getSecondLevelAttrValuesFromModelCombos(Set<ModelCombo> mc) {

		Set<AttributeValue> attributes = new HashSet<AttributeValue>();
		for (ModelCombo d : mc) {
			Set<ModelComboValue> mcv = d.getModelComboValues();
			for (ModelComboValue v : mcv) {
				if ((v.getAttribute().getOrder() == 2)) {
					attributes.add(v.getAttributeValue());
				}
			}
		}
		return attributes;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<ModelCombo> findMatchForThirdLevelAttrValue(Set<ModelCombo> mc, AttributeValue thirdLevelAttrValue) {

		Set<ModelCombo> modelComboSet = new HashSet<ModelCombo>();
		for (ModelCombo d : mc) {
			Set<ModelComboValue> mcv = d.getModelComboValues();
			for (ModelComboValue v : mcv) {
				if ((v.getAttribute().getOrder() == 3) && (v.getAttributeValue().equals(thirdLevelAttrValue))) {
					modelComboSet.add(d);
					break;
				}
			}
		}
		return modelComboSet;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<AttributeValue> getThirdLevelAttrValuesFromModelCombos(Set<ModelCombo> mc) {

		Set<AttributeValue> attributes = new HashSet<AttributeValue>();
		for (ModelCombo d : mc) {
			Set<ModelComboValue> mcv = d.getModelComboValues();
			for (ModelComboValue v : mcv) {
				if ((v.getAttribute().getOrder() == 3)) {
					attributes.add(v.getAttributeValue());
				}
			}
		}
		return attributes;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<ModelCombo> findMatchForFourthLevelAttrValue(Set<ModelCombo> mc, AttributeValue fourthLevelAttrValue) {

		Set<ModelCombo> modelComboSet = new HashSet<ModelCombo>();
		for (ModelCombo d : mc) {
			Set<ModelComboValue> mcv = d.getModelComboValues();
			for (ModelComboValue v : mcv) {
				if ((v.getAttribute().getOrder() == 4) && (v.getAttributeValue().equals(fourthLevelAttrValue))) {
					modelComboSet.add(d);
					break;
				}
			}
		}
		return modelComboSet;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<AttributeValue> getFourthLevelAttrValuesFromModelCombos(Set<ModelCombo> mc) {

		Set<AttributeValue> attributes = new HashSet<AttributeValue>();
		for (ModelCombo d : mc) {
			//System.out.println("d ** " + d);
			Set<ModelComboValue> mcv = d.getModelComboValues();
			//System.out.println("dtcv >> " + mcv);
			for (ModelComboValue v : mcv) {
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
	public Set<ModelCombo> findMatchForFifthLevelAttrValue(Set<ModelCombo> mc, AttributeValue fifthLevelAttrValue) {

		Set<ModelCombo> modelComboSet = new HashSet<ModelCombo>();
		for (ModelCombo d : mc) {
			Set<ModelComboValue> mcv = d.getModelComboValues();
			for (ModelComboValue v : mcv) {
				if ((v.getAttribute().getOrder() == 5) && (v.getAttributeValue().equals(fifthLevelAttrValue))) {
					modelComboSet.add(d);
					break;
				}
			}
		}
		return modelComboSet;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Set<AttributeValue> getFifthLevelAttrValuesFromModelCombos(Set<ModelCombo> mc) {

		Set<AttributeValue> attributes = new HashSet<AttributeValue>();
		for (ModelCombo d : mc) {
			//System.out.println("d ** " + d);
			Set<ModelComboValue> mcv = d.getModelComboValues();
			//System.out.println("dtcv >> " + mcv);
			for (ModelComboValue v : mcv) {
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
	public Set<ModelCombo> getModelsMatchForDoctype(Set<ModelCombo> mc, long doctype) {

		Doctype dt = this.doctypeDao.findDoctypeById(doctype);
		Set<ModelCombo> modelComboSet = new HashSet<ModelCombo>();
		for (ModelCombo d : mc) {
			if (d.getIsActive() == 'Y') {
				if (d.getDoctype().equals(dt)) {
					modelComboSet.add(d);
				}
			}
		}
		return modelComboSet;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveModelCombo(ModelCombo modelCombo) {

		this.modelComboDao.saveModelCombo(modelCombo);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ModelCombo findModelComboById(long modelComboId) {

		return this.modelComboDao.findModelComboById(modelComboId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ModelCombo findModelComboByDoctypeAndModelName(Doctype doctype, String modelName) {

		return this.modelComboDao.findModelComboByDoctypeAndModelName(doctype, modelName);
	}

}
