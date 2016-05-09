package com.cmad.blog.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
import com.cmad.blog.rest.entities.Blog;

@Path("/blog")
public class BlogService {

	public @Inject UserDao UserDao;

	public @Inject PostDao postDao;

	/**
	 * User is sending the Blog post content.
	 * 
	 * @return Response with status code and message in json format
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addPost(Blog blogPost, @Context SecurityContext sc) {
		System.out.println("BlogService.addPost()..........  ");
		Post post = new Post(blogPost.getTitle(), blogPost.getContent());
		Long userId = Long.valueOf(((User) sc.getUserPrincipal()).getId());
		User user = UserDao.getUser(userId);
		System.out.println("  user  "+user);
		post.setUser(user);
		postDao.createPost(post);
		// Now update the user's  blog collection.
		user.getPosts().add(post);
		UserDao.updateUser(user);
		System.out.println("BlogService.addPost()  Returning value ");
		return Response.status(201).entity("Blog Post has been created").build();
	}

	/**
	 * Get all posts
	 * 
	 * @return Response with status code and message in json format
	 */
	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Post> getPosts() {
		return postDao.getPostList();
	}

	/**
	 * Get all posts
	 * 
	 * @return Response with status code and message in json format
	 */
	@GET
	@Path("/search/{searchString}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Post> getPosts(@PathParam("searchString") String searchString) {
		System.out.println("BlogService.getPosts(searchString)  "+searchString);
		return postDao.getPost(searchString);
	}

}
