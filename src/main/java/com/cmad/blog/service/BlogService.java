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
	@Produces({ MediaType.TEXT_HTML })
	public Response addPost(Blog blogPost, @Context SecurityContext sc) {
		Post post = new Post(blogPost.getTitle(), blogPost.getContent());
		postDao.createPost(post);
		Long userId = Long.valueOf(((User) sc.getUserPrincipal()).getId());
		User user = UserDao.getUser(userId);
		user.getPosts().add(post);
		UserDao.updateUser(user);
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
	@Path("/search")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Post> getPosts(@PathParam("keyword") String searchString) {
		return postDao.getPost(searchString);
	}

}
