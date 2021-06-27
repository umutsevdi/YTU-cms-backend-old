package com.cms.service;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.cms.MongoDB;
import com.model.User;
import com.mongodb.client.MongoCursor;

@Service
public class UserService {
	
	public static boolean mailExists(String mail) throws Exception {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		if (mail != null || pattern.matcher(mail).matches())
			return MongoDB.getDatabase().getCollection("users").find(eq("mail", mail)).first()!=null;	
		else 
			throw new Exception("InvalidMailException");
	}

	public static ObjectId getObjectIdFromMail(String mail )throws Exception {
		try {
			return MongoDB.getDatabase().getCollection("users").find(eq("mail", mail)).first()
					.getObjectId("_id");
		} catch (Exception e) {
			throw new Exception("UserNotFoundException");
		}
	}
	
	public static ObjectId getObjectId(String publicId) throws Exception {
		try {
			return MongoDB.getDatabase().getCollection("users").find(eq("public_id", publicId)).first()
					.getObjectId("_id");
		} catch (Exception e) {
			throw new Exception("UserNotFoundException");
		}
	}

	public static String getPublicId(ObjectId id) throws Exception {
		try {
			return MongoDB.getDatabase().getCollection("users").find(eq("_id", id)).first().getString("public_id");
		} catch (Exception e) {
			throw new Exception("UserNotFoundException");
		}
	}

	public static User findUser(String publicId) throws Exception {
		try {
			return User.generate(MongoDB.getDatabase().getCollection("users").find(eq("public_id", publicId)).first(),
					false);
		} catch (Exception e) {
			throw new Exception("UserNotFoundException");
		}
	}

	public static User findUser(ObjectId id) throws Exception {
		try {
			return User.generate(MongoDB.getDatabase().getCollection("users").find(eq("_id", id)).first(), false);
		} catch (Exception e) {
			throw new Exception("UserNotFoundException");
		}
	}

	public static List<Document> getUsers() throws Exception {
		LinkedList<Document> userList = new LinkedList<Document>();
		MongoCursor<Document> userDocuments = MongoDB.getDatabase().getCollection("users").find().iterator();
		while (userDocuments.hasNext()) {
			Document iter = userDocuments.next();
			userList.add(User.generate(iter, false).toDocument(false));
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
			if (iter.equals("fullname") || iter.equals("mail") || iter.equals("password")) {
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
