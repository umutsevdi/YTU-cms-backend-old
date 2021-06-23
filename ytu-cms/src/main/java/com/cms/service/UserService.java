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
	public ObjectId getObjectId(String publicId) throws Exception {
		try {
			return MongoDB.getDatabase().getCollection("users").find(eq("public_id", publicId)).first()
					.getObjectId("_id");
		} catch (Exception e) {
			throw new Exception("UserNotFoundException");
		}
	}

	public User findUser(String publicId) throws Exception {
		try {
			return new User(MongoDB.getDatabase().getCollection("users").find(eq("public_id", publicId)).first(),
					false);
		} catch (Exception e) {
			throw new Exception("UserNotFoundException");
		}
	}

	public User findUser(ObjectId id) throws Exception {
		try {
			return new User(MongoDB.getDatabase().getCollection("users").find(eq("_id", id)).first(), false);
		} catch (Exception e) {
			throw new Exception("UserNotFoundException");
		}
	}

	public List<Document> getUsers() throws Exception {
		LinkedList<Document> userList = new LinkedList<Document>();
		MongoCursor<Document> userDocuments = MongoDB.getDatabase().getCollection("users").find().iterator();
		while (userDocuments.hasNext()) {
			Document iter = userDocuments.next();
			userList.add(new User(iter, false).toDocument(false));
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

	public Document editUser(String publicId, Document userDocument) throws Exception {

		Document user = findUser(publicId).toDocument(true);

		for (String iter : User.components) {
			if (userDocument.containsKey(iter) && !iter.equals("_id") && !iter.equals("email_confirmed")) {
				user.put(iter, userDocument.get(iter));
			}
		}
		MongoDB.getDatabase().getCollection("users").findOneAndReplace(eq("_id", getObjectId(publicId)), user);
		return user;

	}

	public Document deleteUser(String publicId) throws Exception {
		try {
			MongoDB.getDatabase().getCollection("users").findOneAndDelete(eq("_id", getObjectId(publicId)));
			return new Document().append("Status", "UserDeleted");
		} catch (Exception e) {
			throw new Exception("UserNotFoundException");
		}
	}

}
