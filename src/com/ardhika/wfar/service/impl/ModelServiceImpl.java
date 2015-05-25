package com.ardhika.wfar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.web.util.UserInfo;

import com.ardhika.wfar.ModelSummary;
import com.ardhika.wfar.WfCase;
import com.ardhika.wfar.WfModel;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.WfUserInfo;
import com.ardhika.wfar.dao.CaseDao;
import com.ardhika.wfar.dao.ModelDao;
import com.ardhika.wfar.service.ModelService;

@Service("modelService")
public class ModelServiceImpl implements ModelService {

	@Autowired
	private ModelDao modelDao;

	@Autowired
	private CaseDao caseDao;

	private Map<String, WfModel> models = new HashMap<String, WfModel>();

	public ModelServiceImpl() {

		// findAllModels();
	}

	private void addModel(WfModel model) {

		models.put(model.getName(), model);
	}

	@Override
	public WfModel findModelById(long id) {

		return this.modelDao.findModelById(id);
	}

	private List<WfModel> findAllModels() {

		List<WfModel> listModels = this.modelDao.findAllModels();
		models.clear();
		for (WfModel m : listModels)
			models.put(m.getName(), m);
		return listModels;
	}

	private List<WfModel> findAllModelsByType(char modelCategory) {

		List<WfModel> listModelsByType = this.modelDao.findAllModelsByType(modelCategory);
		models.clear();
		for (WfModel m : listModelsByType)
			models.put(m.getName(), m);
		return listModelsByType;
	}

	@Override
	public void saveModel(WfModel wfmodel) {

		this.modelDao.saveModel(wfmodel);
		addModel(wfmodel);
	}

	@Override
	public void deleteModel(long id) {

		WfModel model = this.findModelById(id);
		this.modelDao.deleteModel(model);
	}

	@Override
	public WfModel findModelByName(String modelName) {

		return this.modelDao.findModelByName(modelName);
	}

	@Override
	public List<ModelSummary> listModels() {

		List<ModelSummary> msList = new ArrayList<ModelSummary>();
		if (models.isEmpty()) {
			findAllModels();
		}
		for (WfModel m : models.values()) {
			ModelSummary ms = new ModelSummary(m.getId(), m.getName());
			msList.add(ms);
		}
		return msList;
	}

	@Override
	public List<ModelSummary> listModelsByType(char modelCategory) {

		List<ModelSummary> msList = new ArrayList<ModelSummary>();
		if (models.isEmpty()) {
			findAllModelsByType(modelCategory);
		}
		for (WfModel m : models.values()) {
			if (m.getModelCategory() == modelCategory) {
				ModelSummary ms = new ModelSummary(m.getId(), m.getName());
				msList.add(ms);
			}
		}
		return msList;
	}

	public WfStep createCase(String modelName, WfUserInfo userInfo) throws Exception {

		String creator = userInfo.getUserName();
		String creatorGroup = userInfo.getActiveGroup();
		//System.out.println("************************************************");
		if (!models.containsKey(modelName)) {
			WfModel model = findModelByName(modelName);
			//System.out.println("Model-> " + model);
			if (model != null)
				addModel(model);
			else
				return null;
		}
		//System.out.println("************************************************");
		// create a case here
		//System.out.println("==============================================================");
		WfCase newCase = new WfCase(models.get(modelName), creator, creatorGroup);
		//System.out.println("===============Case Id============" + newCase.getId());
		newCase.setDateCreated(new Date());
		caseDao.saveCase(newCase);
		WfStep step = newCase.start();
		caseDao.saveStep(step);
		return step;
	}
    
	@Override
	public List<WfModel> canCreateCase(WfUserInfo userInfo) {

		Set<String> userRoles = userInfo.getRoles();
		List<WfModel> modelsList = this.findAllModels();
		List<WfModel> models = new ArrayList<WfModel>();
		for (WfModel m : modelsList) {
			for (String s : userRoles) {
				if (m.getCreatorRole().equals(s)) {
					models.add(m);
				}
			}
		}
		return models;
	}
}
