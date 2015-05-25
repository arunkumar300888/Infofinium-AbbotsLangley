package uk.co.jmr.sdp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.GroupDao;
import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.domain.Role;

@Repository("groupDao")
public class GroupDaoImpl implements GroupDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void saveGroup(Group group) {

		this.hibernateTemplate.saveOrUpdate(group);
	}

	@Override
	public void deleteGroup(Group group) {

		this.hibernateTemplate.delete(group);
	}

	@Override
	public Group findGroupById(long groupId) {

		return this.hibernateTemplate.get(Group.class, groupId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> findFirstLevelGroups() {

		return (List<Group>) this.hibernateTemplate.find("from Group g where g.parentGroup=null ");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Group findGroupByName(String groupName) {

		String query = "from Group as g where g.groupName=?";
		List<Group> result = (List<Group>) hibernateTemplate.find(query, groupName);
		if (result.size() >= 1)
			return result.get(0);
		return null;
	}

}
