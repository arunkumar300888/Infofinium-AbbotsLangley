package uk.co.jmr.sdp.service;

import java.util.List;
import java.util.Set;

import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.DtTemplateCombo;

public interface DtTemplateComboService {

	DtTemplateCombo findTemplateById(long id);

	DtTemplateCombo findTemplateByName(String name);

	void save(DtTemplateCombo dtTemplate);

	void delete(DtTemplateCombo dtTemplate);

	List<DtTemplateCombo> listAllTemplates();

	Set<DtTemplateCombo> findMatchForFirstLevelAttrValue(long attrValueId);

	Set<DtTemplateCombo> findMatchForSecondLevelAttrValue(Set<DtTemplateCombo> dtc, AttributeValue secondLevelAttrValue);

	Set<AttributeValue> getSecondLevelAttrValuesFromTemplateCombos(Set<DtTemplateCombo> dtc);

	Set<DtTemplateCombo> findMatchForThirdLevelAttrValue(Set<DtTemplateCombo> dtc, AttributeValue thirdLevelAttrValue);

	Set<AttributeValue> getThirdLevelAttrValuesFromTemplateCombos(Set<DtTemplateCombo> dtc);

	Set<DtTemplateCombo> findMatchForFourthLevelAttrValue(Set<DtTemplateCombo> dtc, AttributeValue fourthLevelAttrValue);

	Set<AttributeValue> getFourthLevelAttrValuesFromTemplateCombos(Set<DtTemplateCombo> dtc);

	Set<DtTemplateCombo> getTemplatesMatchForDoctype(Set<DtTemplateCombo> dtCombo4, long doctype);

	Set<AttributeValue> getFifthLevelAttrValuesFromTemplateCombos(
			Set<DtTemplateCombo> dtc);

	Set<DtTemplateCombo> findMatchForFifthLevelAttrValue(
			Set<DtTemplateCombo> dtc, AttributeValue fourthLevelAttrValue);

}
