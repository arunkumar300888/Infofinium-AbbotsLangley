package uk.co.jmr.sdp.dao;

import java.util.List;

import uk.co.jmr.sdp.domain.UserRole;

public interface UserRoleServiceDao {
	
	public List<UserRole> findUserRoleByUserId(long id);
	public UserRole findRoleByUserId(long id);

}
