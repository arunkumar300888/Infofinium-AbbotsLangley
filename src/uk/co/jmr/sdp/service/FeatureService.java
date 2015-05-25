package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.Feature;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.web.util.UserInfo;

public interface FeatureService {
	List<Feature> listAllFeatures();

	Feature findFeatureById(long id);

	Feature findFeatureByFeatureName(String featureName);

	void save(Feature feature);

	void delete(long id);

	boolean addAccessForRole(long featureId, long roleId);

	boolean removeAccessForRole(long featureId, long roleId);

	List<Role> findUnassignedRoles(long featureId);

	boolean canEnterAdmin(UserInfo userInfo);

	boolean canDoAdminForFeature(String featureName, UserInfo userInfo);

}
