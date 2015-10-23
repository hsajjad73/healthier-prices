package com.healthierprices.data;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.LinkedList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ProductsDAO {
	
	private static int MAX_FETCH = 1000;
	
	private static final Logger logger = LogManager.getLogger(ProductsDAO.class);
	
	private static String PRODUCTS_COLL = "products";
	
	private DB db = null;
	
	public ProductsDAO(String host, int port, String dbName)
	{
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(host, port);

			//	// or, to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
			//	MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
			//	                                      new ServerAddress("localhost", 27018),
			//	                                      new ServerAddress("localhost", 27019)));
			
			db = mongoClient.getDB(dbName);
			logger.debug("DB connection established successfully!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public Collection<DBObject> getAllProducts() {
		Collection<DBObject> coll = new LinkedList<DBObject>();
		DBCursor cursor = db.getCollection(PRODUCTS_COLL).find().limit(MAX_FETCH);
		try {
		   while(cursor.hasNext()) {
		       coll.add(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		logger.debug(String.format("Products retrieved [result size: %s]",coll.size()));
		return coll;
	}
	
	public long getProductsCount() {
		return db.getCollection(PRODUCTS_COLL).find().count();
	}	
	
	public Collection<DBObject> getProductById(String id) {
		BasicDBObject query = new BasicDBObject("productcode", id);
		Collection<DBObject> coll = new LinkedList<DBObject>();
		DBCursor cursor = db.getCollection(PRODUCTS_COLL).find(query);
		try {
		   while(cursor.hasNext()) {
		       coll.add(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		logger.debug(String.format("[%s] product(s) retrieved by [id: '%s']",coll.size(), id));
		return coll;
	}

	public Collection<DBObject> getProductsByCategory(String categoryId) {
		BasicDBObject query = new BasicDBObject("category", categoryId);
		Collection<DBObject> coll = new LinkedList<DBObject>();
		DBCursor cursor = db.getCollection(PRODUCTS_COLL).find(query);
		try {
		   while(cursor.hasNext()) {
		       coll.add(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		logger.debug(String.format("[%s] product(s) retrieved for [category: '%s']",coll.size(), categoryId));
		return coll;
	}
}
