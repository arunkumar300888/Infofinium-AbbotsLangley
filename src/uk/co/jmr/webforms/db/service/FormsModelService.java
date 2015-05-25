package uk.co.jmr.webforms.db.service;

import java.util.List;

import uk.co.jmr.sdp.domain.FormsModel;
import uk.co.jmr.webforms.db.pojo.Forms;

import com.ardhika.wfar.WfModel;

public interface FormsModelService {

void save(FormsModel formsModel);
	
	void delete(FormsModel formsModel);
	
	FormsModel findFormsModelById(long id);
	
	FormsModel findFormsModelByFormIdAndModelId(Forms forms,WfModel wfModel);
	
	List<FormsModel> findAllFormsModel();
}
