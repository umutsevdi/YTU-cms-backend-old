package com.cms.service;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.cms.MongoDB;
import com.model.ClubDocument;
import com.mongodb.client.MongoCursor;

@Service
public class DocumentService {

	public static List<ClubDocument> findClubDocuments(ObjectId id) throws Exception {
		LinkedList<ClubDocument> documents = new LinkedList<ClubDocument>();
		try {
			documents.add(ClubDocument
					.generate(MongoDB.getDatabase().getCollection("documents").find(eq("club", id)).first(), false));
			return documents;
		} catch (Exception e) {
			throw new Exception("ClubNotFoundException");
		}
	}

	public static Optional<ClubDocument> findDocument(ObjectId id) {
		try {
			return Optional.of(ClubDocument
					.generate(MongoDB.getDatabase().getCollection("documents").find(eq("_id", id)).first(), false));
		} catch (Exception e) {
			return Optional.of(null);
		}
	}

	public static List<Document> getDocuments() throws Exception {
		LinkedList<Document> docList = new LinkedList<Document>();
		MongoCursor<Document> clubDocuments = MongoDB.getDatabase().getCollection("documents").find().iterator();
		while (clubDocuments.hasNext()) {
			Document iter = clubDocuments.next();
			docList.add(ClubDocument.generate(iter, false).toDocument());
		}
		return docList;
	}

	public ClubDocument addDocument(Document document) throws Exception {
		return ClubDocument.generate(document, true);
	}

	public Document editDocument(ObjectId id, Document doc) throws Exception {

		Document document = findDocument(id).get().toDocument();

		for (String iter : doc.keySet()) {
			if (iter.equals("name") || iter.equals("description")) {
				document.put(iter, doc.get(iter));
			} else {
				throw new Exception("InvalidPropertyRequestException");
			}
		}
		MongoDB.getDatabase().getCollection("documents").findOneAndReplace(eq("_id", id), document);
		return document;

	}

	public Document deleteDocument(ObjectId id) throws Exception {
		return MongoDB.getDatabase().getCollection("documents").findOneAndDelete(eq("_id", id));
	}

}
