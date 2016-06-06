package com.cmad.blog.dal;

import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.cmad.blog.entities.Post;
import com.cmad.blog.entities.User;

@Contract
@Service
public class UserDao {

	public List<User> getUserList() {
		Datastore dataStore = ServicesFactory.getMongoDB();
		return dataStore.createQuery(User.class).asList();
	}

	public User getUser(ObjectId id) {
		Datastore dataStore = ServicesFactory.getMongoDB();
		Query<User> query = dataStore.createQuery(User.class).field("id").equal(id);
		return query.get();
	}

	public void saveUser(User user) {
		Datastore dataStore = ServicesFactory.getMongoDB();
		dataStore.save(user);
	}
	
	public void updateUser(User u) {
		Datastore dataStore = ServicesFactory.getMongoDB();
		ObjectId id = u.getId();
		Query<User> query = dataStore.createQuery(User.class).field("id").equal(id);
		Set<Post> posts = u.getPosts();
		UpdateOperations<User> paramUpdateOperations = dataStore.createUpdateOperations(User.class).set("posts", posts);
		dataStore.findAndModify(query, paramUpdateOperations); //.update(dataStore.createQuery(User.class), ops);
	}
	
	public boolean deleteUser(ObjectId id) {
		/*Session ses = HibernateUtil.currentSession();
		try {
			Transaction tx = ses.beginTransaction();
			User u = (User) ses.load(User.class, id);
			ses.delete(u);
			tx.commit();
			return true;
		} finally {
			HibernateUtil.closeSession();
		}*/
		return false;
	}
    
	public User getUserByEmail(final String emailAddress){
		Datastore dataStore = ServicesFactory.getMongoDB();
		return dataStore.createQuery(User.class).field("emailAddress").equal(emailAddress).get();
	}
}
