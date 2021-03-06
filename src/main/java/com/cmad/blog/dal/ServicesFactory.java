package com.cmad.blog.dal;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class ServicesFactory {
	
	private static ThreadLocal<Datastore> mongoTL = new ThreadLocal<Datastore>();
	
	/**
	 * Method to retrieve a mongo database client from the thread local storage
	 * @return
	 */
	public static Datastore getMongoDB(){
		if(mongoTL.get() == null){
			MongoClientURI connectionString = new MongoClientURI("mongodb://173.36.55.178:27017");
			MongoClient mongoClient = new MongoClient(connectionString);
			Morphia morphia = new Morphia();
			Datastore datastore = morphia.createDatastore(mongoClient, "blogs");
			datastore.ensureIndexes();
			mongoTL.set(datastore);
			return datastore;
		}
		return mongoTL.get();
	}	
}