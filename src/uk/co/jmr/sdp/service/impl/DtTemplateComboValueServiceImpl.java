package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.DtTemplateComboDao;
import uk.co.jmr.sdp.dao.DtTemplateComboValueDao;
import uk.co.jmr.sdp.domain.dt.DtTemplateCombo;
import uk.co.jmr.sdp.domain.dt.DtTemplateComboValue;
import uk.co.jmr.sdp.service.DtTemplateComboValueService;

@Service("dtTemplateComboValueService")
public class DtTemplateComboValueServiceImpl implements DtTemplateComboValueService {

	@Autowired
	private DtTemplateComboValueDao dtTemplateComboValueDao;

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(DtTemplateComboValue dtTemplateComboValue) {

		this.dtTemplateComboValueDao.delete(dtTemplateComboValue);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DtTemplateComboValue> findTemplateComboValues(DtTemplateCombo dtTemplateCombo) {

		return this.dtTemplateComboValueDao.findTemplateComboValues(dtTemplateCombo);
	}

}
