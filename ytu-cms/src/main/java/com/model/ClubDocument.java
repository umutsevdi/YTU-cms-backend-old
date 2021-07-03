package com.model;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.cms.MongoDB;
import com.mongodb.client.model.Filters;

public class ClubDocument {
	public static final List<String> components = List.of("_id", "name", "description", "club", "date", "status",
			"path");
	private final ObjectId _id;
	private final String name;
	private String description;
	private final ObjectId club;
	private Date date;
	private StatusType status;
	private String path;

	private ClubDocument(ObjectId _id, String name, String description, ObjectId club, Date date, StatusType status,
			String path) {
		this._id = _id;
		this.name = name;
		this.description = description;
		this.club = club;
		this.date = date;
		this.status = status;
		this.path = path;
	}

	public static ClubDocument generate(Document values, boolean isNew) throws Exception {
		if (isNew) {
			try {
				boolean isValid = true;
				for (String key : components) {
					if (!(key.equals("_id") || key.equals("status")) && !values.containsKey(key))
						isValid = false;
				}
				if (!isValid)
					throw new Exception("MissingPropertyException");

				ClubDocument document = new ClubDocument(values.getObjectId("_id"), values.getString("name"),
						values.getString("description"), values.getObjectId("club"), values.getDate("date"),
						StatusType.DRAFT, "");
				MongoDB.getDatabase().getCollection("documents").insertOne(document.toDocument());
				return document;
			} catch (Exception e) {
				throw new Exception(e.getLocalizedMessage());
			}

		} else {

			ClubDocument document = new ClubDocument(values.getObjectId("_id"), values.getString("name"),
					values.getString("description"), values.getObjectId("club"), values.getDate("date"),
					StatusType.valueOf(values.getString("status").toUpperCase()), values.getString("path"));
			return document;
		}
	}

	public Document toDocument() {
		return new Document().append("_id", _id).append("name", name).append("description", description)
				.append("club", club).append("date", date).append("status", status.toString()).append("path", path);
	}

	public File getFile() {
		Path path = Paths.get(this.path);
		return path.toFile();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ObjectId get_id() {
		return _id;
	}

	public String getName() {
		return name;
	}

	public ObjectId getClub() {
		return club;
	}

	public void updateDatabase() {
		MongoDB.getDatabase().getCollection("documents").findOneAndReplace(Filters.eq("_id", _id), this.toDocument());
	}

}
