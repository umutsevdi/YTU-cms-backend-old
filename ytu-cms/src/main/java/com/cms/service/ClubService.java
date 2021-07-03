package com.cms.service;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.cms.MongoDB;
import com.model.Club;
import com.mongodb.client.MongoCursor;

@Service
public class ClubService {
	public static Optional<Club> findClub(ObjectId id) {
		try {
			return Optional
					.of(Club.generate(MongoDB.getDatabase().getCollection("clubs").find(eq("_id", id)).first(), false));
		} catch (Exception e) {
			System.out.println(e);
			return Optional.of(null);
		}
	}

	public static List<Document> getClubs() throws Exception {
		LinkedList<Document> clubList = new LinkedList<Document>();
		MongoCursor<Document> clubDocuments = MongoDB.getDatabase().getCollection("clubs").find().iterator();
		while (clubDocuments.hasNext()) {
			Document iter = clubDocuments.next();
			clubList.add(Club.generate(iter, false).toDocument());
		}
		return clubList;
	}

	public Club addClub(Document document) throws Exception {
		if (MongoDB.getDatabase().getCollection("clubs").find(eq("name", document.getString("name"))).first() == null) {
			return Club.generate(document, true);
		} else
			throw new Exception("ClubNotFoundException");

	}

	public Document editClub(ObjectId id, Document document) throws Exception {

		Document club = findClub(id).get().toDocument();

		for (String iter : document.keySet()) {
			if (iter.equals("name") || iter.equals("description") || iter.equals("communities")) {
				club.put(iter, document.get(iter));
			} else {
				throw new Exception("InvalidPropertyRequestException");
			}
		}
		MongoDB.getDatabase().getCollection("clubs").findOneAndReplace(eq("_id", id), club);
		return club;

	}

	// FIX THIS
	public Document assingLeaders(ObjectId clubId, Document document) throws Exception {
//		Club club = findClub(clubId).get();
//		String mailPresident = (document.containsKey("president")) ? document.getString("president") : null;
//		String mailVP = (document.containsKey("vice_president")) ? document.getString("vice_president") : null;
//		String mailAccountant = (document.containsKey("accountant")) ? document.getString("accountant") : null;
//		if (!(UserService.isMailValid(mailPresident) && UserService.isMailValid(mailVP) && UserService.isMailValid(mailAccountant))) {
//			throw new Exception("InvalidMailException");
//		}
//		
//		ObjectId oldPresident = club.getPresident();
//		ObjectId oldVP = club.getVicePresident();
//		ObjectId oldAccountant = club.getAccountant();
//		club.setPresident(mailPresident);
//		club.setVicePresident(mailVP);
//		club.setAccountant(mailAccountant);
//		
//
//		return club.toDocument();
		return null;
	}

	public Document deleteClub(ObjectId id) throws Exception {
		return MongoDB.getDatabase().getCollection("clubs").findOneAndDelete(eq("_id", id));
	}

}
