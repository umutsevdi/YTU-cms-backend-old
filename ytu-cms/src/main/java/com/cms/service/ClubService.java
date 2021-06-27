package com.cms.service;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.cms.MongoDB;
import com.model.Club;
import com.mongodb.client.MongoCursor;

@Service
public class ClubService {
	public static Club findClub(ObjectId id) throws Exception {
		try {
			return Club.generate(MongoDB.getDatabase().getCollection("clubs").find(eq("_id", id)).first(), false);
		} catch (Exception e) {
			throw new Exception("ClubNotFoundException");
		}
	}

	public static List<Document> getClubs() throws Exception {
		LinkedList<Document> clubList = new LinkedList<Document>();
		MongoCursor<Document> clubDocuments = MongoDB.getDatabase().getCollection("clubs").find().iterator();
		while (clubDocuments.hasNext()) {
			Document iter = clubDocuments.next();
			clubList.add(Club.generate(iter, false).toDocument(false));
		}
		return clubList;
	}

	public Club addClub(Club club) throws Exception {
		if (MongoDB.getDatabase().getCollection("clubs").find(eq("_id", club.get_id())).first() == null
				&& MongoDB.getDatabase().getCollection("clubs").find(eq("name", club.getName())).first() == null) {

			MongoDB.getDatabase().getCollection("clubs").insertOne(club.toDocument(true));
			return club;
		} else
			throw new Exception("ClubNotFoundException");

	}
	public Document editClub(ObjectId id, Document clubDocument) throws Exception {

		Document club = findClub(id).toDocument(true);

		for (String iter : Club.components) {
			if(iter.equals("name") || iter.equals("description") || iter.equals("communities")) {
				club.put(iter, clubDocument.get(iter));
			}else {
				throw new Exception("InvalidPropertyRequestException");
			}
		}
		MongoDB.getDatabase().getCollection("clubs").findOneAndReplace(eq("_id", id), club);
		return club;

	}

	public Document deleteClub(ObjectId id) throws Exception {
		try {
			MongoDB.getDatabase().getCollection("clubs").findOneAndDelete(eq("_id", id));
			return new Document().append("Status", "ClubDeleted");
		} catch (Exception e) {
			throw new Exception("ClubNotFoundException");
		}
	}

}
