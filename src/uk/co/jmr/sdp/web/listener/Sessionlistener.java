package uk.co.jmr.sdp.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;

import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.UserSession;
import uk.co.jmr.sdp.service.UserSessionService;
import uk.co.jmr.sdp.service.impl.UserSessionServiceImpl;

public class Sessionlistener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {

		// TODO Auto-generated method stub

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

		HttpSession session = se.getSession();

		if (session.getAttribute("LOGGED_IN_USER") != null) {

			User user = (User) session.getAttribute("LOGGED_IN_USER");

			ServletContext servletContext = se.getSession().getServletContext();
			WebApplicationContext appContext = (WebApplicationContext) servletContext
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

			UserSessionService userSessionService = (UserSessionService) appContext.getBean("userSessionService");

			UserSession userSession = userSessionService.checkUserIdSessionId(user.getId(), session.getId());

			if (userSession != null) {
				userSessionService.removeUserSession(userSession);

			}

		}

	}

}
