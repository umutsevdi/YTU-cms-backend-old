package com.cms.service;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.cms.MongoDB;
import com.model.Event;
import com.mongodb.client.MongoCursor;

@Service
public class EventService {
	public static List<Event> findClubEvents(ObjectId id) throws Exception {
		LinkedList<Event> documents = new LinkedList<Event>();
		try {
			documents.add(
					Event.generate(MongoDB.getDatabase().getCollection("events").find(eq("club", id)).first(), false));
			return documents;
		} catch (Exception e) {
			throw new Exception("ClubNotFoundException");
		}
	}

	public static Optional<Event> findEvent(ObjectId id) {
		try {
			return Optional.of(
					Event.generate(MongoDB.getDatabase().getCollection("events").find(eq("_id", id)).first(), false));
		} catch (Exception e) {
			return Optional.of(null);
		}
	}

	public static List<Document> getDocuments() throws Exception {
		LinkedList<Document> docList = new LinkedList<Document>();
		MongoCursor<Document> clubDocuments = MongoDB.getDatabase().getCollection("events").find().iterator();
		while (clubDocuments.hasNext()) {
			Document iter = clubDocuments.next();
			docList.add(Event.generate(iter, false).toDocument(false));
		}
		return docList;
	}

	public Event addDocument(Document document) throws Exception {
		if (MongoDB.getDatabase().getCollection("events").find(eq("name", document.getString("name")))
				.first() == null) {
			return Event.generate(document, true);
		} else
			throw new Exception("ExistingEventException");

	}

	public Document editDocument(ObjectId id, Document document) throws Exception {

		Document event = findEvent(id).get().toDocument(true);

		for (String iter : document.keySet()) {
			if (iter.equals("name") || iter.equals("description")) {
				document.put(iter, document.get(iter));
			} else {
				throw new Exception("InvalidPropertyRequestException");
			}
		}
		MongoDB.getDatabase().getCollection("events").findOneAndReplace(eq("_id", id), event);
		return document;

	}

	public Document deleteDocument(ObjectId id) throws Exception {
		return MongoDB.getDatabase().getCollection("events").findOneAndDelete(eq("_id", id));
	}
}
