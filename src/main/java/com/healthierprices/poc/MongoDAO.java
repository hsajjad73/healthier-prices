package com.healthierprices.poc;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.LinkedList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDAO {
	
	private static String DB_NAME = "healthierprices";
	private static String PRODUCTS_COLL = "products";
	private static int MAX_FETCH = 1000;
	
	private static DB db;
	
	static
	{
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );

			//	// or, to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
			//	MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
			//	                                      new ServerAddress("localhost", 27018),
			//	                                      new ServerAddress("localhost", 27019)));
			
			db = mongoClient.getDB(DB_NAME);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Collection<DBObject> getAllProducts() {
		Collection<DBObject> coll = new LinkedList<DBObject>();
		DBCursor cursor = db.getCollection(PRODUCTS_COLL).find().limit(MAX_FETCH);
		try {
		   while(cursor.hasNext()) {
		       coll.add(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		return coll;
	}
	
	public static long getProductsCount() {
		return db.getCollection(PRODUCTS_COLL).find().count();
	}	
	
	public static Collection<DBObject> getProductById(String id) {
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
		return coll;
	}

	public static Collection<DBObject> getProductsByCategory(String categoryId) {
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
		return coll;
	}
}
