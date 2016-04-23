package com.cmad.blog.security;

public class AuthenticationSecurityContext {
	
}
//implements javax.ws.rs.core.SecurityContext {
//
//	private final User user;
//	private final Session session;
//
//	public AuthenticationSecurityContext() {
//		user = null;
//		session = null;
//	}
//
//	public AuthenticationSecurityContext(Session session, User user) {
//		this.session = session;
//		this.user = user;
//	}
//
//	public String getAuthenticationScheme() {
//		return SecurityContext.BASIC_AUTH;
//	}
//
//	public Principal getUserPrincipal() {
//		return user;
//	}
//
//	public boolean isSecure() {
//		return (null != session) ? session.isSecure() : false;
//	}
//
//	public boolean isUserInRole(String role) {
//		if (null == session || !session.isActive()) {
//			// Forbidden
//			Response denied = Response.status(Response.Status.FORBIDDEN).entity("Permission Denied").build();
//			throw new WebApplicationException(denied);
//		}
//		return false;
//	}
//}
