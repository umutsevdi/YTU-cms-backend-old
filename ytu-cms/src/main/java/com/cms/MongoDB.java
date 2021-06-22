package com.cms;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
	private static MongoClient mongoClient;
	private static String database;

	public static void startClient(String db,String uri) {
		database=db;
		mongoClient = MongoClients.create(uri);
		System.out.println("MongoDB Opened");
			
	}

	public static MongoClient getMongoClient() {
		return mongoClient;
	}

	public static MongoDatabase getDatabase() {
		return mongoClient.getDatabase(database);
	}
}