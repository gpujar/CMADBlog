/**
 * 
 */
package com.cmad.blog.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.bson.types.ObjectId;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.mockito.Mockito;

import com.cmad.blog.dal.TokenDao;
import com.cmad.blog.dal.TokenUtil;
import com.cmad.blog.dal.UserDao;
import com.cmad.blog.entities.Token;
import com.cmad.blog.entities.User;
import com.cmad.blog.security.AuthenticationSecurityContext;

/**
 * @author gpujar
 *
 */
public class UserServiceTest extends JerseyTest{

	UserService userService = new UserService();
	
	@Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(UserService.class).register(SecurityContextFilter.class);
    }
	
	 @PreMatching
	    public static class SecurityContextFilter implements ContainerRequestFilter {
	     //   @Override
	        public void filter(ContainerRequestContext requestContext) throws IOException {
	            requestContext.setSecurityContext(new AuthenticationSecurityContext(new User()){
	                public Principal getUserPrincipal() {
	                    return new User() {
	                        public String getName() { return "giri.in.java@gmail.com"; };
	                        
	                    };
	                }
	                public boolean isUserInRole(String role) { return false; }
	                public boolean isSecure() { return true;}
	                public String getAuthenticationScheme() {
	                	return SecurityContext.BASIC_AUTH; }
	            });  
	        }  
	    }
	 
	 
	 AuthenticationSecurityContext context = new AuthenticationSecurityContext(new User()){
         public Principal getUserPrincipal() {
             return new User() {
                 public String getName() { return "giri.in.java@gmail.com"; }
             };
         }
         public boolean isUserInRole(String role) { return false; }
         public boolean isSecure() { return true;}
         public String getAuthenticationScheme() {
         	return SecurityContext.BASIC_AUTH; }
     };

     
     AuthenticationSecurityContext contextNull = new AuthenticationSecurityContext(new User()){
         public Principal getUserPrincipal() {
             return null;
         }
         public boolean isUserInRole(String role) { return false; }
         public boolean isSecure() { return true;}
         public String getAuthenticationScheme() {
         	return SecurityContext.BASIC_AUTH; }
     };
     
	/**
	 * Test method for {@link com.cmad.blog.service.UserService#signUp(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSignUp() {
		userService.userDao = Mockito.mock(UserDao.class);
		userService.tokenDao = Mockito.mock(TokenDao.class);
		userService.tokenService = Mockito.mock(TokenUtil.class);
		Token token = Mockito.mock(Token.class);
		Mockito.doNothing().when(userService.userDao).saveUser((User) Mockito.any());
		Mockito.doReturn(true).when(userService.tokenDao).createToken((Token) Mockito.any());
		Mockito.doReturn(token).when(userService.tokenService).createToken((User) Mockito.any());
		Response response = userService.signUp("First Name", "Last Name", "giri.in.java@gmail.com", "1234");
		assertEquals(response.getStatus(), 201);
	}

	
	/*public Response login(@FormParam("email") String email,@FormParam("password") String password) {
		User userGot = getUserByEmail(email);
		if (userGot != null && checkPassword(userGot, password)) {
			Token token = tokenService.createToken(userGot);
			token.setUser(userGot);
			tokenDao.createToken(token);
			// Setting the token in user also.
			userGot.setToken(token);
			MailUtil util = new MailUtil(); //.mailSend();
			util.mailSend();
			return Response.status(200).entity("{\"token\":\"" + token.getToken() + "\"}").build();
		} else {
			return Response.status(404).entity("{\"token\":\"" + "Test" + "\"}").build();
		}
	}*/
	/**
	 * Test method for {@link com.cmad.blog.service.UserService#login(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testLogin_User_Null() {
		userService.userDao = Mockito.mock(UserDao.class);
		userService.tokenDao = Mockito.mock(TokenDao.class);
	//	User user = Mockito.mock(User.class);
		String email = "giri.in.java@gmail.com";
		Mockito.doReturn(null).when(userService.userDao).getUserByEmail(email);
		Response response = userService.login(email, "1234");
		assertEquals(response.getStatus(), 404);
	}

	
	/*public Response login(User user) {
		String email = user.getEmailAddress();
		String password = user.getPassword();
		User userGot = getUserByEmail(email);
		if (checkPassword(userGot, password)) {
			Token token = tokenService.createToken(userGot);
			userDao.updateUser(userGot);
			tokenDao.createToken(token);
			token.setUser(userGot);
			return Response.status(200).entity("{\"token\":\"" + token.getToken() + "\"}").build();
		} else {
			return Response.status(404).entity("User not found").build();
		}
	}*/
	/**
	 * Test method for {@link com.cmad.blog.service.UserService#login(com.cmad.blog.entities.User)}.
	 */
	@Test
	public void testLoginUser() {
		userService.userDao = Mockito.mock(UserDao.class);
		userService.tokenDao = Mockito.mock(TokenDao.class);
		String email = "giri.in.java@gmail.com";
		User user = new User();
		user.setEmailAddress(email);
		user.setPassword("1234");
		Mockito.doReturn(null).when(userService.userDao).getUserByEmail(email);
		Response response = userService.login(user);
		assertEquals(response.getStatus(), 404);
	}

	/**
	 * Test method for {@link com.cmad.blog.service.UserService#logout(javax.ws.rs.core.SecurityContext)}.
	 */
	@Test
	public void testLogout() {
		userService.tokenDao = Mockito.mock(TokenDao.class);
		Mockito.doReturn(true).when(userService.tokenDao).deleteTokenByUserId((User)Mockito.any());
		Response response = userService.logout(context);
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testLogout_No_User() {
		userService.tokenDao = Mockito.mock(TokenDao.class);
		Response response = userService.logout(contextNull);
		assertEquals(404, response.getStatus());
	}
	
	/**
	 * Test method for {@link com.cmad.blog.service.UserService#getUserById(org.bson.types.ObjectId)}.
	 */
	@Test
	public void testGetUserById() {
		userService.userDao = Mockito.mock(UserDao.class);
		ObjectId id = new ObjectId();
		User user = new User();
		Mockito.doReturn(user).when(userService.userDao).getUser(id);
		Response response = userService.getUserById(id);
		assertEquals(200, response.getStatus());
	}
	
	@Test
	public void testGetUserById_Null() {
		userService.userDao = Mockito.mock(UserDao.class);
		ObjectId id = new ObjectId();
		Mockito.doReturn(null).when(userService.userDao).getUser(id);
		Response response = userService.getUserById(id);
		assertEquals(404, response.getStatus());
	}

	/*public Response updatePassword(User user) {
		String email = user.getEmailAddress();
		String password = user.getPassword();
		User userGot = getUserByEmail(email);
		if (userGot != null) {
			resetPassword(userGot, password);
			return Response.status(200).entity("Update password successfully").build();
		} else {
			return Response.status(404).entity("This user can not be updated").build();
		}
	}*/
	/**
	 * Test method for {@link com.cmad.blog.service.UserService#updatePassword(com.cmad.blog.entities.User)}.
	 */
	@Test
	public void testUpdatePassword() {
		userService.userDao = Mockito.mock(UserDao.class);
		userService.tokenDao = Mockito.mock(TokenDao.class);
		String email = "giri.in.java@gmail.com";
		User user = new User();
		user.setEmailAddress(email);
		user.setPassword("1234");
		Mockito.doReturn(new User()).when(userService.userDao).getUserByEmail(email);
		Response response = userService.updatePassword(user);
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testUpdatePassword_No_User() {
		userService.userDao = Mockito.mock(UserDao.class);
		userService.tokenDao = Mockito.mock(TokenDao.class);
		String email = "giri.in.java@gmail.com";
		User user = new User();
		user.setEmailAddress(email);
		user.setPassword("1234");
		Mockito.doReturn(null).when(userService.userDao).getUserByEmail(email);
		Response response = userService.updatePassword(user);
		assertEquals(404, response.getStatus());
	}
	
}
