package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.ModelComboValueDao;
import uk.co.jmr.sdp.domain.dt.ModelCombo;
import uk.co.jmr.sdp.domain.dt.ModelComboValue;
import uk.co.jmr.sdp.service.ModelComboValueService;

@Service("modelComboValueService")
public class ModelComboValueServiceImpl implements ModelComboValueService {

	@Autowired
	private ModelComboValueDao modelComboValueDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteModelComboValue(ModelComboValue modelComboValue) {

		this.modelComboValueDao.deleteModelComboValue(modelComboValue);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ModelComboValue> findModelComboValueByModelCombo(ModelCombo modelCombo) {

		return this.modelComboValueDao.findModelComboValueByModelCombo(modelCombo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(ModelComboValue modelComboValue) {

		this.modelComboValueDao.save(modelComboValue);
	}

}
