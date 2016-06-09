package com.cmad.blog.service;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.cmad.blog.dal.PostDao;
import com.cmad.blog.dal.UserDao;
import com.cmad.blog.entities.Post;
import com.cmad.blog.entities.User;

@Path("/blog")
public class BlogService {

	public @Inject UserDao UserDao;

	public @Inject PostDao postDao;

	@GET
	@Path("/test")
	public Response testJUnit(){
		System.out.println("BlogService.testJUnit().......");
		return Response.status(200).entity("Blog posted successfully").build();
	}
	
	/**
	 * User is sending the Blog post content.
	 * 
	 * @return Response with status code and message in json format
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.TEXT_PLAIN)
	public Response addPost(@FormParam("title") String title, @FormParam("content") String body, @FormParam("synopsis") String synopsis, @Context SecurityContext sc) {
		System.out.println("BlogService.addPost()........ synopsis  "+synopsis);
		User user = ((User)sc.getUserPrincipal());
		System.out.println("  user  "+user);
		if(user != null){
			Post post = new Post(title, synopsis, body, user.getFirstName());
			postDao.createPost(post);
			// Now update the user's  blog collection.
			user.getPosts().add(post);
			UserDao.updateUser(user);
			return Response.status(200).entity("Blog posted successfully").build();
		}else{
			return Response.status(404).entity("Blog post failed").build();
		}
	}

	/**
	 * Get all posts
	 * 
	 * @return Response with status code and message in json format
	 */
	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPosts(@Context SecurityContext sc) {
		User user = (User) sc.getUserPrincipal();
		Response response;
		if(user != null){
			List<Post> posts = postDao.getPostList();
			if(posts != null){
				response = Response.status(200).entity(posts).encoding("Got result").build();
			}else{
				response = Response.status(200).entity(posts).encoding("Results not found").build();
			}
			
		}else{
			response =  Response.status(404).encoding("Session is logout, please login").build();
		}
		return response;
	}

	/**
	 * Get all posts
	 * 
	 * @return Response with status code and message in json format
	 */
	@GET
	@Path("/search/{searchString}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON})
	public Response getPosts(@PathParam("searchString") String searchString, @Context SecurityContext sc) {
		User user = (User) sc.getUserPrincipal();
		Response response;
		if(user != null){
			List<Post> posts = postDao.getPost(searchString);
			if(posts != null){
				response = Response.status(200).entity(posts).encoding("Got result").build();
			}else{
				response = Response.status(200).entity(posts).encoding("Results not found").build();
			}
		}else{
			response =  Response.status(404).encoding("Session is logout, please login").build();
		}
		return response;
	}

	@GET
	@Path("/{header}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPost(@PathParam("header") String header, @Context SecurityContext sc){
		User user = (User) sc.getUserPrincipal();
		Response response;
		if(user != null){
			Post post = postDao.getSinglePost(header);
			response = Response.status(200).entity(post).encoding("Got result").build();
		}else{
			response =  Response.status(404).encoding("Session is logout, please login").build();
		}
		return response;
	}
}
