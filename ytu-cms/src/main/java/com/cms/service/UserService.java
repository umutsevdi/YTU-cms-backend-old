package com.cms.service;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.cms.MongoDB;
import com.model.User;
import com.mongodb.client.MongoCursor;

@Service
public class UserService {
	public User findUser(String publicId) throws Exception {
		return new User(MongoDB.getDatabase().getCollection("users").find(eq("public_id", publicId)).first());
	}

	public User findUser(ObjectId id) throws Exception {
		return new User(MongoDB.getDatabase().getCollection("users").find(eq("_id", id)).first());
	}

	public List<Document> getUsers() throws Exception {
		LinkedList<Document> userList = new LinkedList<Document>();
		MongoCursor<Document> userDocuments = MongoDB.getDatabase().getCollection("users").find().iterator();
		while (userDocuments.hasNext()) {
			Document iter = userDocuments.next();
			userList.add(new User(iter).toDocument(false));
		}
		return userList;
	}

	public User addUser(User user) throws Exception {
		if (MongoDB.getDatabase().getCollection("users").find(eq("mail", user.getMail())).first() == null && MongoDB
				.getDatabase().getCollection("users").find(eq("public_id", user.getPublicId())).first() == null) {

			MongoDB.getDatabase().getCollection("users").insertOne(user.toDocument(true));
			return user;
		} else
			throw new Exception("ExistingUserException");

	}

}
