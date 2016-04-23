package com.cmad.blog.dal;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;

import com.cmad.blog.entities.Token;


/**
 * Token interface mapped to token.xml to manipulate database
 * 
 *
 */
@Contract
@Service
public class TokenDao {
	
	public boolean createToken(Token token){
		System.out.println("TokenDao: Creating Token " + token.toString());
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

	public Long getUserIdByToken(String token){
		return null;
	}

	public boolean deleteTokenByUserId(Long userId){
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
}
