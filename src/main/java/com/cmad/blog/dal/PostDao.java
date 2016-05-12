package com.cmad.blog.dal;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;

import com.cmad.blog.entities.Post;


@Contract
@Service
public class PostDao {

	public List<Post> getPostList() {
		System.out.println("PostDao.getPostList().......   ");
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
		System.out.println("PostDao.getPost()  searchString  "+searchString);
		Session ses = HibernateUtil.currentSession();
		searchString =  "%"+searchString+"%";
		System.out.println("  searchString  "+searchString);
		try {
			Criteria crit = ses.createCriteria(Post.class);
			crit.add(Restrictions.ilike("title",searchString));
			//crit.addOrder(Order.desc("createdOn"));
			System.out.println("  crit.list()  "+crit.list());
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
