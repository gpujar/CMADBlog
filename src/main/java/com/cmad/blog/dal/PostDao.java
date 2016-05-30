package com.cmad.blog.dal;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;
import org.mongodb.morphia.Datastore;
import com.cmad.blog.entities.Post;


@Contract
@Service
public class PostDao {

	public List<Post> getPostList() {
		Datastore dataStore = ServicesFactory.getMongoDB();
		return dataStore.createQuery(Post.class).order("-createdOn").asList();
	}

	public List<Post> getPost(String searchString) {
		Datastore dataStore = ServicesFactory.getMongoDB();
		return dataStore.createQuery(Post.class).field("title").contains(searchString).asList(); //.find(Post.class, "title =", searchString).asList();
	}

	public Post getSinglePost(String header){
		Post post = null;
		Datastore dataStore = ServicesFactory.getMongoDB();
		post = dataStore.createQuery(Post.class).field("title").equal(header).get(); 
		return post;
	}
	
	public void createPost(Post post) {
		Datastore dataStore = ServicesFactory.getMongoDB();
		dataStore.save(post);
	}
}
