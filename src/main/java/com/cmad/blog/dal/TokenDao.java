package com.cmad.blog.dal;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;

import com.cmad.blog.entities.Token;
import com.cmad.blog.entities.User;

/**
 * Token used to identify the user session
 */
@Contract
@Service
public class TokenDao {

	public boolean createToken(Token token) {
		System.out.println("TokenDao.Creating Token " + token.toString());
		Session ses = HibernateUtil.currentSession();
		try {
			Transaction tx = ses.beginTransaction();
			ses.save(token);
			tx.commit();
			return true;
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public User getUserByTokenString(final String token) {
		System.out.println("TokenDao.Getting user by token string :" + token);
		Session ses = HibernateUtil.currentSession();
		// Query query = ses.createQuery("SELECT t FROM Token t JOIN FETCH
		// t.user WHERE t.token = :token");
		// query.setParameter("token", token);
		// Token sessionToken= (Token)query.uniqueResult();
		// System.out.println("TokenDao.getUserByTokenString() "+sessionToken);
		// try {
		// return sessionToken.getUser();
		// } finally {
		// HibernateUtil.closeSession();
		// }
		try {
			Criteria crit = ses.createCriteria(Token.class);
			crit.add(Restrictions.eq("token", token));
			Token sessionToken = (Token) crit.uniqueResult();
			System.out.println("TokenDao.getUserByTokenString() " + sessionToken);
			return sessionToken.getUser();
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public Long getUserIdByToken(Long token) {
		Session ses = HibernateUtil.currentSession();
		try {
			Criteria crit = ses.createCriteria(Token.class);
			crit.add(Restrictions.idEq(token));
			Token sessionToken = (Token) crit.uniqueResult();
			return sessionToken.getUser().getId();
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public boolean deleteTokenByUserId(Long userId) {
		Session ses = HibernateUtil.currentSession();
		try {
			Transaction tx = ses.beginTransaction();
			Token u = (Token) ses.load(Token.class, userId);
			ses.delete(u);
			tx.commit();
			return true;
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public List<Token> getUserTokenList() {
		Session ses = HibernateUtil.currentSession();
		try {
			return ses.createCriteria(Token.class).list();
		} finally {
			HibernateUtil.closeSession();
		}
	}
}
