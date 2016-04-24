package com.cmad.blog.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cmad.blog.dal.TokenDao;
import com.cmad.blog.dal.UserDao;
import com.cmad.blog.entities.BlogPost;

@Path("/blog")
public class BlogService {

	public @Inject UserDao UserDao;

	public @Inject TokenDao TokenDao;

	/**
	 * User is sending the Blog post content.
	 * 
	 * @return Response with status code and message in json format
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addPost(BlogPost post) {
		
		return Response.status(404).entity("User not found").build();
	}

	/**
	 * User is sending the Blog post content.
	 * 
	 * @return Response with status code and message in json format
	 */
	@POST
	@Path("/get")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPosts() {
		// String email = user.getEmailAddress();
		// String password = user.getPassword();
		// User userGot = getUserByEmail(email);
		// System.out.println("Got user :" + userGot);
		// if (checkPassword(userGot, password)) {
		// Token token = tokenService.createToken(userGot);
		// userGot.setToken(token);
		// UserDao.updateUser(userGot);
		// TokenDao.createToken(token);
		// return Response.status(200).entity("{\"token\":\"" + token +
		// "\"}").build();
		// } else {
		// return Response.status(404).entity("User not found").build();
		// }
		return Response.status(404).entity("User not found").build();
	}

}
