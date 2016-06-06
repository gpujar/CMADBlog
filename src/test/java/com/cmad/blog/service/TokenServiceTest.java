/**
 * 
 */
package com.cmad.blog.service;

import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.cmad.blog.entities.User;
import com.cmad.blog.service.UserServiceTest.SecurityContextFilter;

/**
 * @author gpujar
 *
 */
public class TokenServiceTest extends JerseyTest {

	TokenService tokenService = new TokenService();
	
	@Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(TokenService.class).register(SecurityContextFilter.class);
    }
	/**
	 * Test method for {@link com.cmad.blog.service.TokenService#createToken(com.cmad.blog.entities.User)}.
	 */
	@Test
	public void testCreateToken() {
		User user = new User();
		assertNotNull(tokenService.createToken(user));
	}

}
