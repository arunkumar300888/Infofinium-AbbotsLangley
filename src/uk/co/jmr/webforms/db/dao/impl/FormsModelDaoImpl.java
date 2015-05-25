package uk.co.jmr.webforms.db.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.ardhika.wfar.WfModel;

import uk.co.jmr.sdp.domain.FormsModel;
import uk.co.jmr.webforms.db.dao.FormsModelDao;
import uk.co.jmr.webforms.db.pojo.Forms;

@Repository("formsModelDao")
public class FormsModelDaoImpl implements FormsModelDao{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(FormsModel formsModel) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(formsModel);
	}

	@Override
	public void delete(FormsModel formsModel) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(formsModel);
	}

	@Override
	public FormsModel findFormsModelById(long id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(FormsModel.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public FormsModel findFormsModelByFormIdAndModelId(Forms forms,
			WfModel wfModel) {
		// TODO Auto-generated method stub
		List<FormsModel> formModels=hibernateTemplate.find("from FormsModel where forms=? and wfModel=?",forms,wfModel);
		if(formModels.size()>=1)
			return formModels.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormsModel> findAllFormsModel() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from FormsModel");
	}
	
	
	

}
