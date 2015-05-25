package uk.co.jmr.sdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.GroupRoleDao;
import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.domain.GroupRole;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.GroupRoleService;

@Service("groupRoleService")
public class GroupRoleServiceImpl implements GroupRoleService {

	@Autowired
	private GroupRoleDao groupRoleDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupRole findGroupRoleById(long grId) {

		return this.groupRoleDao.findGroupRoleById(grId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveGroupRole(GroupRole groupRole) {

		this.groupRoleDao.saveGroupRole(groupRole);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<User> getUsersFromGroupRole(GroupRole groupRole) {

		//System.out.println("In Group Role Service");
		return this.groupRoleDao.getUsersFromGroupRole(groupRole);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupRole findGroupRole(Group group, Role role) {

		return this.groupRoleDao.findGroupRole(group, role);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<GroupRole> findAllGroupRoles() {

		return this.groupRoleDao.findAllGroupRoles();
	}

}
