package uk.co.jmr.sdp.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.domain.UserSession;
import uk.co.jmr.sdp.service.UserSessionService;

public class LoginFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException,
		IOException {

		HttpSession session = req.getSession();

		if (session != null && session.getAttribute("LOGGED_IN_USER") != null) {

			User user = (User) session.getAttribute("LOGGED_IN_USER");

			ServletContext servletContext = session.getServletContext();
			WebApplicationContext appContext = (WebApplicationContext) servletContext
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

			UserSessionService userSessionService = (UserSessionService) appContext.getBean("userSessionService");

			// UserSession userSession = new UserSession();
			// userSession.setId(user.getId());

			UserSession userSession = userSessionService.checkForValidSessionById(user.getId());

			if (userSession != null && !userSession.getSessionid().equals(session.getId())) {

				session.invalidate();
				res.sendRedirect("logout");
				return;

			}

			if (userSession == null) {

				session.invalidate();
				res.sendRedirect("logout");
				return;

			}

			String path = req.getRequestURI().replace(req.getContextPath() + "/", "");

			if (path.equals("")) {

				RequestDispatcher requestDispatcher = req.getRequestDispatcher("/authenticate");

				requestDispatcher.forward(req, res);
				return;

			}

			if (path.equalsIgnoreCase("login") && req.getMethod().equalsIgnoreCase("GET")) {

				RequestDispatcher requestDispatcher = req.getRequestDispatcher("/authenticate");

				requestDispatcher.forward(req, res);
				return;

			}
			else if (path.equalsIgnoreCase("login") && req.getMethod().equalsIgnoreCase("POST")) {

				String url = req.getContextPath() + "/" + "session.jsp";

				res.sendRedirect(url);

				// RequestDispatcher requestDispatcher = req
				// .getRequestDispatcher("/authenticate1");
				//
				// requestDispatcher.forward(req, res);
				return;

			}
						
			chain.doFilter(req, res);
			return;

		}
		else {

			String path = req.getRequestURI().replace(req.getContextPath() + "/", "");

			if (path.equals("logout1")) {

				chain.doFilter(req, res);
				return;

			}

			if (path.equals("") || path.equalsIgnoreCase("logout")) {

				chain.doFilter(req, res);
				return;

			}

			if (path.equalsIgnoreCase("login") && req.getMethod().equalsIgnoreCase("POST")) {

				chain.doFilter(req, res);
				return;
			}
			if (path.equalsIgnoreCase("login") && req.getMethod().equalsIgnoreCase("GET")) {

				res.sendRedirect("logout");
				return;
			}

			if (path.contains("resources")) {

				chain.doFilter(req, res);
				return;
			}

			if (path.contains("refresh.jsp")) {

				chain.doFilter(req, res);
				return;
			}

			if (path.equalsIgnoreCase("changepassword") && req.getMethod().equalsIgnoreCase("GET")) {
				chain.doFilter(req, res);
				return;
			}

			if (path.equalsIgnoreCase("updatePassword")) {
				chain.doFilter(req, res);
				return;
			}

			if (path.equalsIgnoreCase("forgotpassword") && req.getMethod().equalsIgnoreCase("GET")) {
				chain.doFilter(req, res);
				return;
			}

			if (path.equalsIgnoreCase("requestPassword")) {
				chain.doFilter(req, res);
				return;
			}

			else {

				// String url = req.getScheme() + "://" + req.getServerName()
				// + ":" + req.getLocalPort() + req.getContextPath();

				String url = req.getContextPath() + "/" + "refresh.jsp";

				res.sendRedirect(url);
				return;
			}

		}

	}

}
