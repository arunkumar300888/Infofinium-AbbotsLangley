package uk.co.jmr.sdp.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;

import uk.co.jmr.sdp.domain.UserSession;
import uk.co.jmr.sdp.service.UserSessionService;

public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		ServletContext servletContext = arg0.getServletContext();
		WebApplicationContext appContext = (WebApplicationContext) servletContext
			.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		UserSessionService userSessionService = (UserSessionService) appContext.getBean("userSessionService");

		userSessionService.removeAll();

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		ServletContext servletContext = arg0.getServletContext();
		WebApplicationContext appContext = (WebApplicationContext) servletContext
			.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		UserSessionService userSessionService = (UserSessionService) appContext.getBean("userSessionService");

		userSessionService.removeAll();

	}

}
