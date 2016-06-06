/**
 * 
 */
package com.cmad.blog.service;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.security.Principal;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.mockito.Mockito;
import com.cmad.blog.dal.PostDao;
import com.cmad.blog.dal.UserDao;
import com.cmad.blog.entities.Post;
import com.cmad.blog.entities.User;
import com.cmad.blog.security.AuthenticationSecurityContext;

/**
 * @author gpujar
 *
 */
public class BlogServiceTest extends JerseyTest{

	BlogService service = new BlogService();
	
	
	@Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(BlogService.class).register(SecurityContextFilter.class);
    }
	
	 @PreMatching
	    public static class SecurityContextFilter implements ContainerRequestFilter {
	     //   @Override
	        public void filter(ContainerRequestContext requestContext) throws IOException {
	            requestContext.setSecurityContext(new AuthenticationSecurityContext(new User()){
	                public Principal getUserPrincipal() {
	                    return new User() {
	                        public String getName() { return "giri.in.java@gmail.com"; }
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
	 * Test method for {@link com.cmad.blog.service.BlogService#addPost(java.lang.String, java.lang.String, java.lang.String, javax.ws.rs.core.SecurityContext)}.
	 */
	@Test
	public void testAddPost() {
		Post blog = getTestPost();
		service.postDao = Mockito.mock(PostDao.class);
		service.UserDao =  Mockito.mock(UserDao.class);
		Mockito.doNothing().when(service.postDao).createPost(blog);;
		Response response = service.addPost("test", "test", "test", context);
		assertEquals(response.getStatus(), 200);
	}
	
	private Post getTestPost(){
		Post blog = new Post();
		blog.setTitle("Test");
		blog.setContent("Testing content");
		blog.setFirstName("junitTester");
		blog.setSynopsis("Junit synopsis testing");
		return blog;
	}

	@Test
	public void testAddPost_Null_User() {
		Post blog = getTestPost();
		service.postDao = Mockito.mock(PostDao.class);
		service.UserDao =  Mockito.mock(UserDao.class);
		Mockito.doNothing().when(service.postDao).createPost(blog);;
		Response response = service.addPost("test", "test", "test", contextNull);
		assertEquals(response.getStatus(), 404);
	}
	
	/**
	 * Test method for {@link com.cmad.blog.service.BlogService#getPosts(javax.ws.rs.core.SecurityContext)}.
	 */
	@Test
	public void testGetPostsSecurityContext() {
		service.postDao = Mockito.mock(PostDao.class);
		List<Post> results = new ArrayList<Post>();
		Mockito.doReturn(results).when(service.postDao).getPostList();
		Response response =  service.getPosts(context);
		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void testGetPostsSecurityContext_NullContext() {
		Response response = service.getPosts(contextNull);
		assertEquals(response.getStatus(), 404);
	}

	@Test
	public void testGetPostsSecurityContext_NullPost() {
		service.postDao = Mockito.mock(PostDao.class);
		List<Post> results = null;
		Mockito.doReturn(results).when(service.postDao).getPostList();
		Response response =  service.getPosts(context);
		assertEquals(response.getStatus(), 200);
	}
	
	/**
	 * Test method for {@link com.cmad.blog.service.BlogService#getPosts(java.lang.String, javax.ws.rs.core.SecurityContext)}.
	 */
	@Test
	public void testGetPostsStringSecurityContext() {
		String search = "testing";
		service.postDao = Mockito.mock(PostDao.class);
		List<Post> results = new ArrayList<Post>();
		Mockito.doReturn(results).when(service.postDao).getPost(search);
		Response response =  service.getPosts(search, context);
		assertEquals(response.getStatus(), 200);
		assertNotNull(response.getEntity().toString());
	}

	/**
	 * Test method for {@link com.cmad.blog.service.BlogService#getPost(java.lang.String, javax.ws.rs.core.SecurityContext)}.
	 */
	@Test
	public void testGetPost() {
		String search = "header";
		Post post = getTestPost();
		service.postDao = Mockito.mock(PostDao.class);
		Mockito.doReturn(post).when(service.postDao).getSinglePost(search);
		Response response = service.getPost(search, context);
		assertEquals(response.getStatus(), 200);
		assertNotNull(response.getEntity().toString());
	}

}
