package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Feature;
import uk.co.jmr.sdp.domain.Role;

public interface FeatureDao {

	List<Feature> listAllFeatures();

	Feature findFeatureById(long id);

	Feature findFeatureByFeatureName(String featureName);

	void save(Feature feature);

	void delete(Feature feature);

	boolean addAccessForRole(Feature feature, Role role);

	boolean removeAccessForRole(Feature feature, Role role);
}
