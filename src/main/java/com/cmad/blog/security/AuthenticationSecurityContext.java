package com.cmad.blog.security;

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;
import com.cmad.blog.entities.User;

public class AuthenticationSecurityContext implements javax.ws.rs.core.SecurityContext {

	private final User user;

	public AuthenticationSecurityContext() {
		user = null;
	}

	public AuthenticationSecurityContext(User user) {
		System.out
				.println("AuthenticationSecurityContext.AuthenticationSecurityContext()");
		this.user = user;
	}

	/**
     * User entity implements Principal
     * @return user
     */
    public Principal getUserPrincipal() {
        System.out.println("princeple: " + user);
        return this.user;
    }

    public boolean isUserInRole(String role) {
        return false;
    }

    public boolean isSecure() {
        return false;
    }

    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
