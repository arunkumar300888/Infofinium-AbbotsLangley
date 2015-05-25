package com.ardhika.wfar.service;

import java.util.List;

import uk.co.jmr.sdp.web.util.UserInfo;

import com.ardhika.wfar.ModelSummary;
import com.ardhika.wfar.WfModel;
import com.ardhika.wfar.WfStep;
import com.ardhika.wfar.WfUserInfo;

public interface ModelService {
	WfModel findModelById(long id);

	WfModel findModelByName(String modelName);

	void saveModel(WfModel wfmodel);

	void deleteModel(long id);

	List<ModelSummary> listModels();

	WfStep createCase(String modelName, WfUserInfo userInfo) throws Exception;

	List<WfModel> canCreateCase(WfUserInfo userInfo);

	// Model Category
	List<ModelSummary> listModelsByType(char modelCategory);

}
