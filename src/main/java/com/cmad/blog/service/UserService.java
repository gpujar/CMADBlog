package com.cmad.blog.service;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.cmad.blog.dal.TokenDao;
import com.cmad.blog.dal.UserDao;
import com.cmad.blog.entities.Token;
import com.cmad.blog.entities.User;
import com.cmad.blog.util.EncryptionKit;

/**
 * Service class that handles REST request about token, which is for user
 * authentication check.
 */
@Path("/user")
public class UserService {

	
	public @Inject UserDao UserDao;

	public @Inject TokenDao TokenDao;

	public @Inject TokenService tokenService;

	
	/*
	 * Create new user account by form
	 * 
	 * @param user
	 * 
	 * @return Response with status code and message in json format
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces({ MediaType.TEXT_HTML })
	public Response signUp(@FormParam("firstName") String firstName,@FormParam("lastName") String lastName,@FormParam("email") String email,
			@FormParam("password") String password) {
		User user = new User(firstName,lastName,email);
		resetPassword(user, password);
		UserDao.saveUser(user);
		//create the token and save to db.
		Token token = tokenService.createToken(user);
		
		token.setUser(user);		
		TokenDao.createToken(token);
		
		System.out.println("Created user: " + user.toString());
		return Response.status(201).entity("{\"token\":\"" + token.getToken() + "\"}").build();
	}

	
	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response login(@FormParam("email") String email,@FormParam("password") String password) {
		User userGot = getUserByEmail(email);
		System.out.println("Got user :"+userGot);
		if (checkPassword(userGot, password)) {
			Token token = tokenService.createToken(userGot);
			token.setUser(userGot);
			TokenDao.createToken(token);
			
			System.out.println("Updated user: " + userGot.toString());
			return Response.status(200).entity("{\"token\":\"" + token.getToken() + "\"}").build();
		} else {
			return Response.status(404).entity("User not found").build();
		}
	}
	
	
	/**
	 * Log in, check the user by email and password, if there is a user with
	 * this password and email, create a token
	 * 
	 * @return Response with status code and message in json format
	 */
	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response login(User user) {
		String email = user.getEmailAddress();
		String password = user.getPassword();
		User userGot = getUserByEmail(email);
		System.out.println("Got user :"+userGot);
		if (checkPassword(userGot, password)) {
			Token token = tokenService.createToken(userGot);
			
			UserDao.updateUser(userGot);
			TokenDao.createToken(token);
			token.setUser(userGot);
			return Response.status(200).entity("{\"token\":\"" + token.getToken() + "\"}").build();
		} else {
			return Response.status(404).entity("User not found").build();
		}
	}

	/**
	 * Log out, delete the token
	 * 
	 * @return Response with status code and message in json format
	 */
	@POST
	@Path("/logout")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response logout(@Context SecurityContext sc) {
		Long userId = Long.valueOf(((User)sc.getUserPrincipal()).getId());
		boolean  flag = TokenDao.deleteTokenByUserId(userId);
		if (flag) {
			return Response.status(200).entity("Logged out the user successfully").build();
		} else {
			return Response.status(404).entity("This user can not be deleted").build();
		}
	}

	/**
	 * Get user by user id
	 * 
	 * @return Response with status code and message or user entity in json
	 *         format
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUserById(@PathParam("id") Long id) {
		User user = UserDao.getUser(id);
		if (user == null) {
			return Response.status(404).entity("User with this id not found").build();
		} else {
			return Response.status(200).entity(user).build();
		}
	}

	/********************************* UPDATE PASSWORD***********************************/


	/**
	 * Update user password
	 * 
	 * @param user
	 *            , with new password
	 * @return Response with status code and message or user entity in json
	 *         format
	 */
	@PUT
	@Path("updatePassword")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updatePassword(User user) {
		String email = user.getEmailAddress();
		String password = user.getPassword();
		User userGot = getUserByEmail(email);
		System.out.println("Got user :"+userGot);
		if (userGot != null) {
			resetPassword(userGot, password);
			return Response.status(200).entity("Update password successfully").build();
		} else {
			return Response.status(404).entity("This user can not be updated").build();
		}
	}

	/**
	 * Delete user by id
	 * 
	 * @param id
	 *            , user id
	 * @return Response with status code and message or user entity in json
	 *         format
	 */
	@DELETE
	@Path("id")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteUserByid(@PathParam("id") Long id) {
		boolean deleted = UserDao.deleteUser(id);
		if (deleted) {
			return Response.status(204).entity("User with the id" + id + "has been deleted").build();
		} else {
			return Response.status(404).entity("User can not be deleted").build();
		}
	}

	/**
	 * Find user by email
	 * 
	 * @param email
	 * @return user
	 */
	public User getUserByEmail(String email) {
		User user = UserDao.getUserByEmail(email);
		return user;
	}

	/**
	 * Encrypt the giving password, check it whether equals to the password
	 * stored in the database
	 * 
	 * @param password
	 * @return user
	 */
	private boolean checkPassword(User user, String password) {
		String salt = user.getSalt();
		String psw = EncryptionKit.md5Encrypt(password + salt);
		return psw.equals(user.getPassword());

	}

	/**
	 * Reset the salt and the password
	 * 
	 * @param password
	 */
	private void resetPassword(User user, String password) {
		user.setSalt(EncryptionKit.md5Encrypt(UUID.randomUUID().toString()));
		user.setPassword(EncryptionKit.md5Encrypt(password + user.getSalt()));
	}

	/**
	 * For test purpose without filter by AuthenticationResponseFilter
	 */
	@GET
	@Path("testRest")
	public Response getTest() {
		String output = "Hello world!";
		return Response.ok(output).build();
	}
	
	
	/**
	 * @return User list
	 */
	@GET
	@Path("testRest/user")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<User> getUserList() {
		return UserDao.getUserList();
	}

	/**
	 * @return Token list
	 */
	@GET
	@Path("testRest/token")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Token> getTokenList() {
		return TokenDao.getUserTokenList();
	}

}
