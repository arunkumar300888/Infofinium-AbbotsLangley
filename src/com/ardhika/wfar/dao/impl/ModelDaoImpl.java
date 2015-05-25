package com.ardhika.wfar.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.ardhika.wfar.WfAttribute;
import com.ardhika.wfar.WfAttributeType;
import com.ardhika.wfar.WfModel;
import com.ardhika.wfar.dao.ModelDao;

@Repository("modelDao")
public class ModelDaoImpl implements ModelDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public WfModel findModelById(long id) {

		WfModel model = hibernateTemplate.get(WfModel.class, id);
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfModel> findAllModels() {

		return hibernateTemplate.find("from WfModel");
	}

	@Override
	public void saveModel(WfModel wfmodel) {

		if (wfmodel.getTimeBound() == 'Y' && wfmodel.getAttribute("Target Date") == null) {
			wfmodel.addAttribute(new WfAttribute("Target Date", WfAttributeType.WF_ATTR_DATE));
		}
		hibernateTemplate.saveOrUpdate(wfmodel);
		//System.out.println("saved");
	}

	@Override
	public void deleteModel(WfModel wfmodel) {

		hibernateTemplate.delete(wfmodel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WfModel findModelByName(String modelName) {

		List<WfModel> models = hibernateTemplate.find("from WfModel m where m.name=?", modelName);
		if (models.size() >= 1)
			return models.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfModel> findAllModelsByType(char modelCategory) {

		return hibernateTemplate.find("from WfModel as m where m.model_category=?", modelCategory);
	}

}
