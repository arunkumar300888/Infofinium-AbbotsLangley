package uk.co.jmr.sdp.dao.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;



import uk.co.jmr.sdp.dao.UserRoleServiceDao;
import uk.co.jmr.sdp.domain.UserRole;


@Repository("userRoleServiceDao")
public class UserRoleServiceDaoImpl implements UserRoleServiceDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserRole> findUserRoleByUserId(long id){
		List<UserRole> multipleUserRole=hibernateTemplate.find("from UserRole where userId=?",id);
		return multipleUserRole;
	}
	
	
	@Override
	public UserRole findRoleByUserId(long id){
		return hibernateTemplate.get(UserRole.class, id);
	}

}
