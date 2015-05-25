package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.dt.AttributeValue;
import uk.co.jmr.sdp.domain.dt.ModelCombo;

public interface ModelComboDao {

	List<ModelCombo> listAllModelCombos();

	List<ModelCombo> listAllModelCombosWithInActive();

	List<ModelCombo> findMatchForFirstLevel(AttributeValue attrValue);

	void saveModelCombo(ModelCombo modelCombo);

	ModelCombo findModelComboById(long modelComboId);

	ModelCombo findModelComboByDoctypeAndModelName(Doctype doctype, String modelName);

}
