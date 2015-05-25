package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.GroupDao;
import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.service.GroupService;

@Service("groupService")
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveGroup(Group group) {

		this.groupDao.saveGroup(group);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteGroup(Group group) {

		this.groupDao.deleteGroup(group);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Group findGroupById(long groupId) {

		return this.groupDao.findGroupById(groupId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Group> findFirstLevelGroups() {

		return this.groupDao.findFirstLevelGroups();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Group findGroupByName(String groupName) {

		return this.groupDao.findGroupByName(groupName);
	}

}
