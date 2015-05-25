package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.Group;

public interface GroupService {

	void saveGroup(Group group);

	void deleteGroup(Group group);

	Group findGroupById(long groupId);

	Group findGroupByName(String groupName);

	List<Group> findFirstLevelGroups();

}
