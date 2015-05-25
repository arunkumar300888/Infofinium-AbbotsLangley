package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.FeatureDao;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.Feature;

@Repository("featureDao")
public class FeatureDaoImpl implements FeatureDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Feature> listAllFeatures() {

		return hibernateTemplate.find("from Feature");
	}

	@Override
	public Feature findFeatureById(long id) {

		return hibernateTemplate.get(Feature.class, id);
	}

	@Override
	public Feature findFeatureByFeatureName(String featureName) {

		List<Feature> result = (List<Feature>) hibernateTemplate.find("from Feature as f where f.featureName=?", featureName);
		if (!result.isEmpty())
			return (Feature) result.get(0);

		return null;
	}

	@Override
	public void save(Feature feature) {

		hibernateTemplate.saveOrUpdate(feature);
	}

	@Override
	public void delete(Feature feature) {

		hibernateTemplate.delete(feature);
	}

	@Override
	public boolean addAccessForRole(Feature feature, Role role) {

		if (feature.addToRoles(role)) {
			this.save(feature);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeAccessForRole(Feature feature, Role role) {

		if (feature.removeFromRoles(role)) {
			this.save(feature);
		}
		return false;
	}

}
