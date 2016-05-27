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
		/*Session ses = HibernateUtil.currentSession();
		try {
			Criteria crit = ses.createCriteria(User.class);
			crit.add(Restrictions.idEq(id));
			User u = (User) crit.uniqueResult();
			return u;
		} finally {
			HibernateUtil.closeSession();
		}*/
		return null;
	}

	public void saveUser(User user) {
		Datastore dataStore = ServicesFactory.getMongoDB();
		dataStore.save(user);
	}
	
	public void updateUser(User u) {
		System.out.println("Updating user: " + u.toString());
		Datastore dataStore = ServicesFactory.getMongoDB();
		ObjectId id = u.getId();
		System.out.println(" id "+id);
		Query<User> query = dataStore.createQuery(User.class).field("id").equal(id);
		System.out.println(" query  "+query);
		Set<Post> posts = u.getPosts();
		System.out.println("posts "+posts);
		UpdateOperations<User> paramUpdateOperations = dataStore.createUpdateOperations(User.class).set("posts", posts);
		System.out.println("paramUpdateOperations "+paramUpdateOperations);
		dataStore.findAndModify(query, paramUpdateOperations); //.update(dataStore.createQuery(User.class), ops);
	}
	
	public boolean deleteUser(Long id) {
		System.out.println("Deleting user: " + id);
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
		System.out.println("Getting  user by email Address: " + emailAddress);
		Datastore dataStore = ServicesFactory.getMongoDB();
		return dataStore.createQuery(User.class).field("emailAddress").equal(emailAddress).get();
	}
}
