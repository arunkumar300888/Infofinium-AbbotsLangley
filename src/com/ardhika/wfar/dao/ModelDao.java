package com.ardhika.wfar.dao;

import java.util.List;

import com.ardhika.wfar.WfModel;

public interface ModelDao {
	WfModel findModelById(long id);

	WfModel findModelByName(String modelName);

	List<WfModel> findAllModels();

	void saveModel(WfModel wfmodel);

	void deleteModel(WfModel wfmodel);

	List<WfModel> findAllModelsByType(char modelCategory);
}
