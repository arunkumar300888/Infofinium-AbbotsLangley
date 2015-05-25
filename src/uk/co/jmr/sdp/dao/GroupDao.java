package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.Group;

public interface GroupDao {

	Group findGroupById(long groupId);

	Group findGroupByName(String groupName);

	void saveGroup(Group group);

	void deleteGroup(Group group);

	List<Group> findFirstLevelGroups();

}
