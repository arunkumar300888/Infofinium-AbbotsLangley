package uk.co.jmr.sdp.service.impl;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jmr.sdp.dao.UserDao;
import uk.co.jmr.sdp.dao.UserSessionDao;
import uk.co.jmr.sdp.domain.UserSession;
import uk.co.jmr.sdp.service.UserSessionService;

@Service("userSessionService")
public class UserSessionServiceImpl implements UserSessionService {

	@Autowired
	private UserSessionDao userSessionDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public long saveUserSession(UserSession userSession) throws HibernateException {

		return userSessionDao.saveUserSession(userSession);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeUserSession(UserSession userSession) throws HibernateException {

		userSessionDao.removeUserSession(userSession);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeUserSessionById(long userId) throws HibernateException {

		userSessionDao.removeUserSessionById(userId);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserSession checkForValidSessionById(long userId) throws HibernateException {

		return userSessionDao.checkForValidSessionById(userId);

	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserSession checkUserIdSessionId(long userId, String sessionid) throws HibernateException {

		return userSessionDao.checkUserIdSessionId(userId, sessionid);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrUpdateUserSession(UserSession userSession) throws HibernateException {

		userSessionDao.saveOrUpdateUserSession(userSession);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void removeAll() throws HibernateException {

		userSessionDao.removeAll();

	}

}
