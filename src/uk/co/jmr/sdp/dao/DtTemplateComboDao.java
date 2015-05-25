package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.DtTemplateCombo;

public interface DtTemplateComboDao {

	DtTemplateCombo findTemplateById(long id);

	DtTemplateCombo findTemplateByName(String name);

	void save(DtTemplateCombo dtTemplate);

	void delete(DtTemplateCombo dtTemplate);

	List<DtTemplateCombo> listAllTemplates();

	List<DtTemplateCombo> findMatchForFirstLevel(AttributeValue attrValue);

	List<DtTemplateCombo> findMatchForSecondLevel(AttributeValue attrValue);

	List<DtTemplateCombo> getThirdLevelAttrValues(AttributeValue attrValue);

}
