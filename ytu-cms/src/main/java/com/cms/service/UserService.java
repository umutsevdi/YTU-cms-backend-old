package com.cms.service;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.cms.MongoDB;
import com.model.Model;
import com.model.User;
import com.mongodb.client.MongoCursor;

@Service
public class UserService {

	public static boolean isMailValid(String mail) {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		return (mail != null && pattern.matcher(mail).matches());
	}

	public static Optional<ObjectId> getObjectIdFromMail(String mail) {
		try {
			return Optional
					.of(MongoDB.getDatabase().getCollection("users").find(eq("mail", mail)).first().getObjectId("_id"));
		} catch (Exception e) {
			return Optional.of(null);
		}
	}

	public static Optional<User> findUser(ObjectId id) {
		try {
			return Optional
					.of(User.generate(MongoDB.getDatabase().getCollection("users").find(eq("_id", id)).first(), false));
		} catch (Exception e) {
			return Optional.of(null);
		}
	}

	public static List<Document> getUsers() throws Exception {
		LinkedList<Document> userList = new LinkedList<Document>();
		MongoCursor<Document> userDocuments = MongoDB.getDatabase().getCollection("users").find().iterator();
		while (userDocuments.hasNext()) {
			Document iter = userDocuments.next();
			userList.add(User.generate(iter, false).toDocument());
		}
		return userList;
	}

	public User addUser(Document document) throws Exception {

		if (MongoDB.getDatabase().getCollection("users").find(eq("mail", document.getString("mail"))).first() == null) {
			return User.generate(document, true);
		} else
			throw new Exception("ExistingUserException");

	}

	public Document editUser(ObjectId _id, Document document) throws Exception {

		Document user = findUser(_id).get().toDocument();

		for (String iter : document.keySet()) {
			if (iter.equals("fullname")) {
				user.put(iter, document.get(iter));
			} else if (iter.equals("password")) {
				user.put(iter, Model.getSHA(document.getString(iter)));
			}

		}
		MongoDB.getDatabase().getCollection("users").findOneAndReplace(eq("_id", _id), user);
		return user;

	}

	public Document replaceUpdate(User user) throws Exception {

		MongoDB.getDatabase().getCollection("users").findOneAndReplace(eq("_id", user.get_id()), user.toDocument());
		return user.toDocument();

	}

	public Document deleteUser(ObjectId _id) throws Exception {
		return MongoDB.getDatabase().getCollection("users").findOneAndDelete(eq("_id", _id));
	}

}
