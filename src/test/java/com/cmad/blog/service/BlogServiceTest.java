/**
 * 
 */
package com.cmad.blog.service;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cmad.blog.entities.Post;
import com.cmad.blog.entities.User;

/**
 * @author gpujar
 *
 */
public class BlogServiceTest extends JerseyTest{

	private BlogService blogService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		blogService = new BlogService();
	}

	@Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(BlogService.class);
    }
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		blogService = null;
	}

	/**
	 * Test method for {@link com.cmad.blog.service.BlogService#addPost(java.lang.String, java.lang.String, java.lang.String, javax.ws.rs.core.SecurityContext)}.
	 */
	@Test
	public void testAddPost() {
	//	fail("Not yet implemented");
		/*User user = ((User)sc.getUserPrincipal());
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
		}*/
		
		/* Notification notification = new Notification(null, "Invoice was deleted");
	        Response output = target("/notifications")
	                .request()
	                .post(Entity.entity(notification, MediaType.APPLICATION_JSON));
	        assertEquals("Should return status 200", 200, output.getStatus());
	        assertNotNull("Should return notification", output.getEntity());*/
		/*Post blog = new Post();
		blog.setTitle("Test");
		blog.setContent("Testing content");
		blog.setFirstName("junitTester");
		blog.setSynopsis("Junit synopsis testing");
		Response response = target("/blog").request().post(Entity.entity(blog, MediaType.APPLICATION_JSON));
		assertEquals(200, response.getStatus());*/
	}

	/**
	 * Test method for {@link com.cmad.blog.service.BlogService#getPosts(javax.ws.rs.core.SecurityContext)}.
	 */
	@Test
	public void testGetPostsSecurityContext() {
		//pass("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cmad.blog.service.BlogService#getPosts(java.lang.String, javax.ws.rs.core.SecurityContext)}.
	 */
	@Test
	public void testGetPostsStringSecurityContext() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cmad.blog.service.BlogService#getPost(java.lang.String, javax.ws.rs.core.SecurityContext)}.
	 */
	@Test
	public void testGetPost() {
		//fail("Not yet implemented");
	}

}
