package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.dt.DtTemplateCombo;
import uk.co.jmr.sdp.domain.dt.DtTemplateComboValue;

public interface DtTemplateComboValueDao {

	void delete(DtTemplateComboValue dtTemplateComboValue);

	List<DtTemplateComboValue> findTemplateComboValues(DtTemplateCombo dtTemplateCombo);
}