package uk.co.jmr.sdp.service;

import java.util.List;

import uk.co.jmr.sdp.domain.UserRole;

public interface UserRoleService {
	
	public List<UserRole> findUserRoleByUserId(long id);
	public UserRole findRoleByUserId(long id);

}
