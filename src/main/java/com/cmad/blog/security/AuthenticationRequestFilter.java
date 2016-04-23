package com.cmad.blog.security;

import javax.inject.Named;
import javax.ws.rs.ext.Provider;

@Named // let CDI manage the lifecycle
@Provider // register as jersey's provider
public class AuthenticationRequestFilter {
}
//implements ContainerRequestFilter {
//
//	@Inject
//	private SessionDao sessionRepository; // DAO to access Session
//
//	@Inject
//	private UserDaoImpl userRepository; // DAO to access User
//
//	public void filter(ContainerRequestContext requestContext) throws IOException {
//		final SecurityContext securityContext = requestContext.getSecurityContext();
//		// Get the request url path, if it is login or signup request, ignore
//		// them
//		String path = requestContext.getUriInfo().getPath();
//		if (path.equals("/user/login") || path.equals("/user/signup") || path.equals("/user/testRest")) {
//			return;
//		}
//		// Get the token from the request header
//		String token = requestContext.getHeaderString("Authorization");
//		// Get the user by this token
//		User user = StringUtils.isEmpty(token) ? null : SessionDao.getUserByToken(token);
//
//		// Set the user to the securityContext for the request if the user is
//		// not null
//		if (user == null) {
//			// requestContext.abortWith(
//			// Response.status(Response.Status.BAD_REQUEST).entity("User must
//			// log in first").build()
//			// );
//			requestContext.setSecurityContext(new AuthenticationSecurityContext());
//		} else {
//			requestContext.setSecurityContext(new AuthenticationSecurityContext(user));
//			System.out.println("securitycontext: " + requestContext.getSecurityContext().getUserPrincipal().getName());
//		}
//	}
//}
