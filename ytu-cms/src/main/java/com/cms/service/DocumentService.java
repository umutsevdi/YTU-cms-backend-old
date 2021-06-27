package com.cms.service;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.cms.MongoDB;
import com.model.ClubDocument;
import com.mongodb.client.MongoCursor;

public class DocumentService {
	
	public static List<ClubDocument> findClubDocuments(ObjectId id) throws Exception{
		LinkedList<ClubDocument> documents = new LinkedList<ClubDocument>();
		try {
			documents.add(ClubDocument.generate(MongoDB.getDatabase().getCollection("documents").find(eq("club", id)).first(), false));
			return documents;
		}catch(Exception e) {
			throw new Exception("ClubNotFoundException");
		}
	}
	public static ClubDocument findDocument(ObjectId id) throws Exception {
		try {
			return ClubDocument.generate(MongoDB.getDatabase().getCollection("documents").find(eq("_id", id)).first(), false);
		} catch (Exception e) {
			throw new Exception("DocumentNotFoundException");
		}
	}

	public static List<Document> getDocuments() throws Exception {
		LinkedList<Document> docList = new LinkedList<Document>();
		MongoCursor<Document> clubDocuments = MongoDB.getDatabase().getCollection("documents").find().iterator();
		while (clubDocuments.hasNext()) {
			Document iter = clubDocuments.next();
			docList.add(ClubDocument.generate(iter, false).toDocument(false));
		}
		return docList;
	}

	public ClubDocument addDocument(ClubDocument doc) throws Exception {
		if (MongoDB.getDatabase().getCollection("documents").find(eq("_id", doc.get_id())).first() == null
				&& MongoDB.getDatabase().getCollection("documents").find(eq("name", doc.getName())).first() == null) {

			MongoDB.getDatabase().getCollection("documents").insertOne(doc.toDocument(true));
			return doc;
		} else
			throw new Exception("ExistingUserException");

	}
	
	
	public Document editDocument(ObjectId id, Document clubDocument) throws Exception {

		Document document = findDocument(id).toDocument(true);

		for (String iter : ClubDocument.components) {
			if(iter.equals("name") || iter.equals("description")) {
				document.put(iter, clubDocument.get(iter));
			}else {
				throw new Exception("InvalidPropertyRequestException");
			}
		}
		MongoDB.getDatabase().getCollection("documents").findOneAndReplace(eq("_id", id), document);
		return document;

	}

	public Document deleteDocument(ObjectId id) throws Exception {
		try {
			MongoDB.getDatabase().getCollection("documents").findOneAndDelete(eq("_id", id));
			return new Document().append("Status", "DocumentDeleted");
		} catch (Exception e) {
			throw new Exception("DocumentNotFoundException");
		}
	}

}
