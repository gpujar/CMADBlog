package com.cmad.blog.dal;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;

import com.cmad.blog.entities.User;

@Contract
@Service
public class UserDao {

	public List<User> getUserList() {
		Session ses = HibernateUtil.currentSession();
		try {
			return ses.createCriteria(User.class).list();
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public User getUser(Long id) {
		Session ses = HibernateUtil.currentSession();
		try {
			Criteria crit = ses.createCriteria(User.class);
			crit.add(Restrictions.idEq(id));
			User u = (User) crit.uniqueResult();
			return u;
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public void createUser(User user) {
		Session ses = HibernateUtil.currentSession();
		try {
			Transaction tx = ses.beginTransaction();
			ses.save(user);
			tx.commit();
		} finally {
			HibernateUtil.closeSession();
		}
	}
	
	public void updateUser(User u) {
		System.out.println("Updating user: " + u.toString());
		Session ses = HibernateUtil.currentSession();
		try {
			Transaction tx = ses.beginTransaction();
			ses.update(u);
			tx.commit();
		} finally {
			HibernateUtil.closeSession();
		}
	}
	
	public boolean deleteUser(Long id) {
		System.out.println("Deleting user: " + id);
		Session ses = HibernateUtil.currentSession();
		try {
			Transaction tx = ses.beginTransaction();
			User u = (User) ses.load(User.class, id);
			ses.delete(u);
			tx.commit();
			return true;
		} finally {
			HibernateUtil.closeSession();
		}
	}
    
	public User getUserByEmail(final String emailAddress){
		System.out.println("Getting  user by email Address: " + emailAddress);
		Session ses = HibernateUtil.currentSession();
		try {
			Criteria crit = ses.createCriteria(User.class);
			crit.add(Restrictions.eq("emailAddress",emailAddress));
			User u = (User) crit.uniqueResult();
			return u;
		} finally {
			HibernateUtil.closeSession();
		}
	}
}
