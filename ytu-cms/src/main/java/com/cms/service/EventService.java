package com.cms.service;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.cms.MongoDB;
import com.model.Event;
import com.mongodb.client.MongoCursor;
@Service
public class EventService {
	public static List<Event> findClubEvents(ObjectId id) throws Exception{
		LinkedList<Event> documents = new LinkedList<Event>();
		try {
			documents.add(Event.generate(MongoDB.getDatabase().getCollection("events").find(eq("club", id)).first(), false));
			return documents;
		}catch(Exception e) {
			throw new Exception("ClubNotFoundException");
		}
	}
	public static Event findDocument(ObjectId id) throws Exception {
		try {
			return Event.generate(MongoDB.getDatabase().getCollection("events").find(eq("_id", id)).first(), false);
		} catch (Exception e) {
			throw new Exception("EventNotFoundException");
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

	public Event addDocument(Event doc) throws Exception {
		if (MongoDB.getDatabase().getCollection("events").find(eq("_id", doc.get_id())).first() == null
				&& MongoDB.getDatabase().getCollection("events").find(eq("name", doc.getName())).first() == null) {

			MongoDB.getDatabase().getCollection("events").insertOne(doc.toDocument(true));
			return doc;
		} else
			throw new Exception("ExistingEventException");

	}
	
	
	public Document editDocument(ObjectId id, Document clubDocument) throws Exception {

		Document document = findDocument(id).toDocument(true);

		for (String iter : Event.components) {
			if(iter.equals("name") || iter.equals("description")) {
				document.put(iter, clubDocument.get(iter));
			}else {
				throw new Exception("InvalidPropertyRequestException");
			}
		}
		MongoDB.getDatabase().getCollection("events").findOneAndReplace(eq("_id", id), document);
		return document;

	}

	public Document deleteDocument(ObjectId id) throws Exception {
		try {
			MongoDB.getDatabase().getCollection("events").findOneAndDelete(eq("_id", id));
			return new Document().append("Status", "DocumentDeleted");
		} catch (Exception e) {
			throw new Exception("EventNotFoundException");
		}
	}
}
