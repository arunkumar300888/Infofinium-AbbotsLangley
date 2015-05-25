package uk.co.jmr.webforms.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ardhika.wfar.WfModel;

import uk.co.jmr.sdp.domain.FormsModel;
import uk.co.jmr.webforms.db.dao.FormsModelDao;
import uk.co.jmr.webforms.db.pojo.Forms;
import uk.co.jmr.webforms.db.service.FormsModelService;

@Service("formsModelService")
public class FormsModelServiceImpl implements FormsModelService{
	
	@Autowired
	private FormsModelDao formsModelDao;

	@Override
	public void save(FormsModel formsModel) {
		// TODO Auto-generated method stub
		formsModelDao.save(formsModel);
	}

	@Override
	public void delete(FormsModel formsModel) {
		// TODO Auto-generated method stub
		formsModelDao.delete(formsModel);
	}

	@Override
	public FormsModel findFormsModelById(long id) {
		// TODO Auto-generated method stub
		return formsModelDao.findFormsModelById(id);
	}

	@Override
	public FormsModel findFormsModelByFormIdAndModelId(Forms forms,
			WfModel wfModel) {
		// TODO Auto-generated method stub
		return formsModelDao.findFormsModelByFormIdAndModelId(forms, wfModel);
	}

	@Override
	public List<FormsModel> findAllFormsModel() {
		// TODO Auto-generated method stub
		return formsModelDao.findAllFormsModel();
	}
	
	

}
