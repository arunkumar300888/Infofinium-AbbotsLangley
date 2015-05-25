package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.dt.ModelCombo;
import uk.co.jmr.sdp.domain.dt.ModelComboValue;

public interface ModelComboValueDao {

	List<ModelComboValue> findModelComboValueByModelCombo(ModelCombo modelCombo);

	void deleteModelComboValue(ModelComboValue modelComboValue);

	void save(ModelComboValue modelComboValue);
}
