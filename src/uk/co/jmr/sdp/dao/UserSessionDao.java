package uk.co.jmr.sdp.dao;

import org.hibernate.HibernateException;

import uk.co.jmr.sdp.domain.UserSession;

public interface UserSessionDao {

	long saveUserSession(UserSession userSession) throws HibernateException;

	void removeUserSession(UserSession userSession) throws HibernateException;

	void removeUserSessionById(long userId) throws HibernateException;

	UserSession checkForValidSessionById(long userId) throws HibernateException;

	public UserSession checkUserIdSessionId(long userId, String sessionid) throws HibernateException;

	public void saveOrUpdateUserSession(UserSession userSession) throws HibernateException;

	public void removeAll() throws HibernateException;

}
