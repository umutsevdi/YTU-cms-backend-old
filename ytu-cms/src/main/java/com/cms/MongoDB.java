package com.cms;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class MongoDB {
	private static MongoClient mongoClient;
	private static String database;

	public static void startClient(String db, String uri) {
		database = db;
		mongoClient = MongoClients.create(uri);
		System.out.println("MongoDB Opened");
		ArrayList<String> collectionList = new ArrayList<String>();
		collectionList.addAll(List.of("users","clubs","documents","events","chats"));
		MongoIterable<String> collections = mongoClient.getDatabase(database).listCollectionNames();
		collections.forEach(iter ->{
			System.out.println("DatabaseControl | exist     | "+iter+"\t|");
			collectionList.remove(iter);
		});
		collectionList.forEach(iter->{
			System.out.println("DatabaseControl | not exist | "+iter+"\t|");
			mongoClient.getDatabase(database).createCollection(iter);
			System.out.println("                | create    | done      |");
			
		});

		

	}

	public static MongoClient getMongoClient() {
		return mongoClient;
	}

	public static MongoDatabase getDatabase() {
		return mongoClient.getDatabase(database);
	}
}