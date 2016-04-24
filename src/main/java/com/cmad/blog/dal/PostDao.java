package com.cmad.blog.dal;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cmad.blog.entities.Post;

public class PostDao {

	public List<Post> getPostList() {
		Session ses = HibernateUtil.currentSession();
		try {
			Criteria crit = ses.createCriteria(Post.class);
			crit.addOrder(Order.desc("createdOn"));
			return crit.list();
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public Post getPost(Long id) {
		Session ses = HibernateUtil.currentSession();
		try {
			Criteria crit = ses.createCriteria(Post.class);
			crit.add(Restrictions.idEq(id));
			Post u = (Post) crit.uniqueResult();
			return u;
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public List<Post> getPost(String searchString) {
		Session ses = HibernateUtil.currentSession();
		try {
			Criteria crit = ses.createCriteria(Post.class);
			crit.add(Restrictions.ilike("title", searchString + "%"));
			crit.add(Restrictions.ilike("title", "%" + " " + searchString + "%"));
			return crit.list();
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public void createPost(Post post) {
		Session ses = HibernateUtil.currentSession();
		try {
			Transaction tx = ses.beginTransaction();
			ses.save(post);
			tx.commit();
		} finally {
			HibernateUtil.closeSession();
		}
	}
}
