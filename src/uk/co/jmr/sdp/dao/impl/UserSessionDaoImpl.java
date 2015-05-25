package uk.co.jmr.sdp.dao.impl;

import java.util.List;
import java.util.ListIterator;

import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.UserSessionDao;
import uk.co.jmr.sdp.domain.Document;
import uk.co.jmr.sdp.domain.UserSession;

@Repository("userSessionDao")
public class UserSessionDaoImpl implements UserSessionDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public long saveUserSession(UserSession userSession) throws HibernateException {

		return (Long) hibernateTemplate.save(userSession);

	}

	@Override
	public void removeUserSession(UserSession userSession) throws HibernateException {

		hibernateTemplate.delete(userSession);

	}

	@Override
	public void removeUserSessionById(long userId) throws HibernateException {

		hibernateTemplate.delete("id", userId);

	}

	@Override
	public UserSession checkForValidSessionById(long userId) throws HibernateException {

		return hibernateTemplate.get(UserSession.class, userId);

	}

	public UserSession checkUserIdSessionId(long userId, String sessionid) throws HibernateException {

		DetachedCriteria criteria = DetachedCriteria.forClass(UserSession.class);
		criteria.add(Restrictions.eq("id", userId));
		criteria.add(Restrictions.eq("sessionid", sessionid));

		List<UserSession> iterator = hibernateTemplate.findByCriteria(criteria);

		if (!iterator.isEmpty()) {

			return iterator.get(0);
		}

		return null;

	}

	public void saveOrUpdateUserSession(UserSession userSession) throws HibernateException {

		hibernateTemplate.saveOrUpdate(userSession);

	}

	public void removeAll() throws HibernateException {

		List<UserSession> userSessionList = hibernateTemplate.find("from UserSession");

		if (!userSessionList.isEmpty()) {

			hibernateTemplate.deleteAll(userSessionList);
		}
	}

}
