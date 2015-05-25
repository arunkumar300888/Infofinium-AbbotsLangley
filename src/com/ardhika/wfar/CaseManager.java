package com.ardhika.wfar;

import java.util.HashMap;
import java.util.Map;

public class CaseManager {

	private Map<String, WfModel> models = new HashMap<String, WfModel>();

	public void addModel(WfModel model) {

		models.put(model.getName(), model);
	}

	public WfCase createCase(String modelName, String creator, String creatorGroup) throws Exception {

		if (!models.containsKey(modelName))
			return null;
		// create a case here
		WfCase newCase = new WfCase(models.get(modelName), creator, creatorGroup);
		newCase.start();
		return newCase;
	}
}
