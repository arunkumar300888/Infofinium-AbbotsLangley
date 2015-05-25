package uk.co.jmr.sdp.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.FeatureDao;
import uk.co.jmr.sdp.dao.RoleDao;
import uk.co.jmr.sdp.domain.Feature;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.service.FeatureService;
import uk.co.jmr.sdp.web.util.UserInfo;

@Service("featureService")
public class FeatureServiceImpl implements FeatureService {

	@Autowired
	private FeatureDao featureDao;

	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Feature> listAllFeatures() {

		return this.featureDao.listAllFeatures();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Feature findFeatureById(long id) {

		return this.featureDao.findFeatureById(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Feature findFeatureByFeatureName(String featureName) {

		return this.featureDao.findFeatureByFeatureName(featureName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Feature feature) {

		this.featureDao.save(feature);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {

		Feature feature = this.featureDao.findFeatureById(id);
		this.featureDao.delete(feature);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean addAccessForRole(long featureId, long roleId) {

		Feature feature = this.featureDao.findFeatureById(featureId);
		Role role = this.roleDao.findRoleById(roleId);
		return this.featureDao.addAccessForRole(feature, role);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean removeAccessForRole(long featureId, long roleId) {

		Feature feature = this.featureDao.findFeatureById(featureId);
		Role role = this.roleDao.findRoleById(roleId);
		return this.featureDao.removeAccessForRole(feature, role);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Role> findUnassignedRoles(long featureId) {

		Feature feature = this.featureDao.findFeatureById(featureId);
		List<Role> availableRoles = roleDao.findAllRoles();
		for (Role r : feature.getRoles()) {
			availableRoles.remove(r);
		}
		return availableRoles;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean canEnterAdmin(UserInfo userInfo) {

		Feature feature = this.featureDao.findFeatureByFeatureName("ADMIN_NONE");
		for (Role r : feature.getRoles()) {
			String roleName = r.getRoleName();
			if (userInfo.getRoles().contains(roleName))
				return false;
		}
		List<Feature> features = this.featureDao.listAllFeatures();
		Set<String> adminRoles = new HashSet<String>();
		for (Feature f : features) {
			if (f.getFeatureName().startsWith("ADMIN_") && !(f.getFeatureName().equals("ADMIN_NONE"))) {
				for (Role r : f.getRoles()) {
					adminRoles.add(r.getRoleName());
				}
			}
		}
		for (String userRole : userInfo.getRoles()) {
			if (adminRoles.contains(userRole))
				return true;
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean canDoAdminForFeature(String featureName, UserInfo userInfo) {

		Feature feature = this.featureDao.findFeatureByFeatureName(featureName);
		if (feature != null) {
			for (Role r : feature.getRoles()) {
				String roleName = r.getRoleName();
				if (userInfo.getRoles().contains(roleName))
					return true;
			}
		}

		return false;
	}

}
