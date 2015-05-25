package uk.co.jmr.sdp.service;

import java.util.List;
import java.util.Set;

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.ModelCombo;

public interface ModelComboService {
	List<ModelCombo> listAllModelCombos();

	List<ModelCombo> listAllModelCombosWithInActive();

	Set<ModelCombo> findMatchForFirstLevelAttrValue(long attrValueId);

	Set<ModelCombo> findMatchForSecondLevelAttrValue(Set<ModelCombo> mc, AttributeValue secondLevelAttrValue);

	Set<AttributeValue> getSecondLevelAttrValuesFromModelCombos(Set<ModelCombo> mc);

	Set<ModelCombo> findMatchForThirdLevelAttrValue(Set<ModelCombo> mc, AttributeValue thirdLevelAttrValue);

	Set<AttributeValue> getThirdLevelAttrValuesFromModelCombos(Set<ModelCombo> mc);

	Set<ModelCombo> findMatchForFourthLevelAttrValue(Set<ModelCombo> mc, AttributeValue fourthLevelAttrValue);

	Set<AttributeValue> getFourthLevelAttrValuesFromModelCombos(Set<ModelCombo> mc);
	
	Set<ModelCombo> findMatchForFifthLevelAttrValue(Set<ModelCombo> mc, AttributeValue fourthLevelAttrValue);

	Set<AttributeValue> getFifthLevelAttrValuesFromModelCombos(Set<ModelCombo> mc);

	Set<ModelCombo> getModelsMatchForDoctype(Set<ModelCombo> dtCombo4, long doctype);

	// Save Model Combo
	void saveModelCombo(ModelCombo modelCombo);

	ModelCombo findModelComboById(long modelComboId);

	ModelCombo findModelComboByDoctypeAndModelName(Doctype doctype, String modelName);

}
