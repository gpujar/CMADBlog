package com.cmad.blog.dal;

import java.util.List;
import org.bson.types.ObjectId;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import com.cmad.blog.entities.Token;
import com.cmad.blog.entities.User;

/**
 * Token used to identify the user session
 */
@Contract
@Service
public class TokenDao {

	public boolean createToken(Token token) {
		Datastore dataStore = ServicesFactory.getMongoDB();
		dataStore.save(token);
		return true;
	}

	public User getUserByTokenString(final String token) {
		User user = null;
		if(token != null){
			Datastore dataStore = ServicesFactory.getMongoDB();
			Query<Token> q = dataStore.createQuery(Token.class).field("token").equal(token);
			Token tokenSession = q.get();
			if(tokenSession != null)
				user = tokenSession.getUser();
		}
		return user;
	}

	public ObjectId getUserIdByToken(Long token) {
		/*Session ses = HibernateUtil.currentSession();
		try {
			Criteria crit = ses.createCriteria(Token.class);
			crit.add(Restrictions.idEq(token));
			Token sessionToken = (Token) crit.uniqueResult();
			return sessionToken.getUser().getId();
		} finally {
			HibernateUtil.closeSession();
		}*/
		return null;
	}

	public boolean deleteTokenByUserId(User userId) {
		Datastore dataStore = ServicesFactory.getMongoDB();
		Query<Token> q = dataStore.createQuery(Token.class).field("user").equal(userId);
		Token tokenSession = q.get();
		dataStore.delete(tokenSession);
		return true;
		
	}

	public List<Token> getUserTokenList() {
		/*Session ses = HibernateUtil.currentSession();
		try {
			return ses.createCriteria(Token.class).list();
		} finally {
			HibernateUtil.closeSession();
		}*/
		return null;
	}
}
