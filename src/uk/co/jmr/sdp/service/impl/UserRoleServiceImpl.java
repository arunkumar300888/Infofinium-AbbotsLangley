package uk.co.jmr.sdp.service.impl;

	import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.jmr.sdp.dao.UserRoleServiceDao;
import uk.co.jmr.sdp.domain.UserRole;
import uk.co.jmr.sdp.service.UserRoleService;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	
	@Autowired 
	private UserRoleServiceDao userRoleServiceDao;
	
	@Override
	public List<UserRole> findUserRoleByUserId(long id){
		return this.userRoleServiceDao.findUserRoleByUserId(id);
	}
	
	@Override
	public UserRole findRoleByUserId(long id){
		return this.userRoleServiceDao.findRoleByUserId(id);
	}


}
