package com.cmad.blog.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.cmad.blog.dal.TokenDao;
import com.cmad.blog.entities.User;
import com.cmad.blog.util.StringUtils;

@Named // let CDI manage the lifecycle
@Provider // register as jersey's provider
public class AuthenticationRequestFilter implements ContainerRequestFilter {

	@Inject
	private TokenDao tokenDao; // DAO to access Session

	public void filter(ContainerRequestContext requestContext) throws IOException {
		final SecurityContext securityContext = requestContext.getSecurityContext();
		// Get the request url path, if it is login or signup request, ignore
		// them
		String path = requestContext.getUriInfo().getPath();
		if (path.equals("/user/login") || path.equals("/user/signup") || path.equals("/user/testRest/user")
				|| path.equals("/user/testRest/token")) {
			return;
		}
		// Get the token from the request header
		String token = requestContext.getHeaderString("Authorization");
		// Get the user by this token
		User user = StringUtils.isEmpty(token) ? null : tokenDao.getUserByTokenString(token);

		// Set the user to the securityContext for the request if the user is
		// not null
		if (user == null) {
			requestContext.setSecurityContext(new AuthenticationSecurityContext());
		} else {
			requestContext.setSecurityContext(new AuthenticationSecurityContext(user));
			System.out.println("AuthenticationRequestFilter " + user);
		}
	}
}
