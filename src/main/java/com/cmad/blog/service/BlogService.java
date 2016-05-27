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

	/**
	 * User is sending the Blog post content.
	 * 
	 * @return Response with status code and message in json format
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.TEXT_PLAIN)
	public Response addPost(@FormParam("title") String title, @FormParam("content") String body, @FormParam("synopsis") String synopsis, @Context SecurityContext sc) {  //Post blogPost,
		System.out.println("BlogService.addPost().......... body  "+body);
		System.out.println(" title  "+title);
		
		User user = ((User)sc.getUserPrincipal());
		
		System.out.println("  user  "+user);
		Post post = new Post(title, synopsis, body, user.getFirstName());
		//post.setUser(user);
		postDao.createPost(post);
		// Now update the user's  blog collection.
		user.getPosts().add(post);
		UserDao.updateUser(user);
		System.out.println("BlogService.addPost()  Returning value ");
		//return Response.status(200).entity("Blog Post has been created").build();
		return Response.status(200).entity("Blog posted successfully").build();
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
		System.out.println("BlogService.getPosts().......   ");
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

	@GET
	@Path("/{header}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Post getPost(@PathParam("header") String header){
		System.out.println("BlogService.getPost()  hrader  "+header);
		Post post = postDao.getSinglePost(header);
		System.out.println(" title "+post.getTitle());
		System.out.println(" content  "+post.getContent());
		return post;
	}
}
