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

@Named 						// let CDI manage the lifecycle
@Provider 					// register as jersey's provider
public class AuthenticationRequestFilter implements ContainerRequestFilter {

	@Inject
	private TokenDao tokenDao; 					// DAO to access Session

	public void filter(ContainerRequestContext requestContext) throws IOException {	
		final SecurityContext securityContext = requestContext.getSecurityContext();
		// Get the request url path, if it is login or signup request, ignore
		String path = requestContext.getUriInfo().getPath();
		if (path.equals("/1/login") || path.equals("/1/user") || path.equals("/user/testRest")
				|| path.equals("/user/testRest/user") || path.equals("/1/logout") || path.equals("/user/testRest/token")) {
			return;
		}
		// Get the token from the request header
		String encodedString = requestContext.getHeaderString("Authorization");
		// Get the user by this token
		if( StringUtils.isEmpty(encodedString)){
			requestContext.setSecurityContext(new AuthenticationSecurityContext(null));
		}else{
			// Set the user to the securityContext for the request if the user is
			// not null
			// String token = StringUtils.getDecodedBase64(encodedString);
			encodedString = encodedString.substring(6);
			User user = tokenDao.getUserByTokenString(encodedString);
			requestContext.setSecurityContext(new AuthenticationSecurityContext(user));
		}
	}
}
