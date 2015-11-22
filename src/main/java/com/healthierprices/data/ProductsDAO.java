package com.healthierprices.data;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

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
	
	private static String PRODUCTS_COLL = "pharm_prods";
	
	private DB db = null;
	
	public ProductsDAO(String host, int port, String dbName)
	{
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(host, port);
			db = mongoClient.getDB(dbName);
			logger.debug("DB connection established successfully!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	

	public Object insertProduct(Map map) {
		BasicDBObject doc = new BasicDBObject("pharma_id", map.get("pharma_id"))
        .append("prod_id", map.get("prod_id"))
        .append("name", map.get("name"))
        .append("size", map.get("size"))
        .append("rrp", map.get("rrp"))
        .append("price", map.get("price"));
		db.getCollection(PRODUCTS_COLL).insert(doc);
		logger.debug(doc + "inserted");
		return map;
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
		BasicDBObject query = new BasicDBObject("prod_id", id);
		Collection<DBObject> coll = new LinkedList<DBObject>();
		DBCursor cursor = db.getCollection(PRODUCTS_COLL).find(query).sort(new BasicDBObject("price", 1));
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

}
