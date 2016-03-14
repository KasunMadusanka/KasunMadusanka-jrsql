package com.jrsql.mongo;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.MongoClient;

public class SimpleMongoOperations {
	
	private MongoOperations mongoOperations;
	
	public SimpleMongoOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
	
	public SimpleMongoOperations(MongoDbFactory mongoDbFactory) {
		this.mongoOperations = new MongoTemplate(mongoDbFactory);
	}
	
	public SimpleMongoOperations(MongoClient mongoClient, String dbName) {
		this.mongoOperations = new MongoTemplate(new SimpleMongoDbFactory(mongoClient, dbName));
	}
	
	public SimpleMongoOperations(String dbHost, int dbPort, String dbName) throws UnknownHostException {
		this.mongoOperations = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(dbHost, dbPort), dbName));
	}
	
	public List<?> find(Query query, String collectionName) {
		return mongoOperations.find(query, Map.class, collectionName);
	}
	
	public <T> List<T> find(Query query, Class<T> entityClass, String collectionName) {
		return mongoOperations.find(query, entityClass, collectionName);
	}
}
