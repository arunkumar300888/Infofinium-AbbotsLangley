package uk.co.jmr.webforms.db.dao;

import java.util.List;

import com.ardhika.wfar.WfModel;

import uk.co.jmr.sdp.domain.FormsModel;
import uk.co.jmr.webforms.db.pojo.Forms;

public interface FormsModelDao {

	void save(FormsModel formsModel);
	
	void delete(FormsModel formsModel);
	
	FormsModel findFormsModelById(long id);
	
	FormsModel findFormsModelByFormIdAndModelId(Forms forms,WfModel wfModel);
	
	List<FormsModel> findAllFormsModel();
	
	
}
