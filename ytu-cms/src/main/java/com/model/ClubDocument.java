package com.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

public class ClubDocument {
	public static final List<String> components = List.of("_id", "name", "description", "club", "date",
			"status", "deparments", "file");
	private final ObjectId _id;
	private final String name;
	private String description;
	private final ObjectId club;
	private Date date;
	private StatusType status;
	private HashMap<ObjectId, Boolean> departments;
	private String file;
	public ClubDocument(ObjectId _id, String name, String description, ObjectId club, Date date, StatusType status,
			HashMap<ObjectId, Boolean> departments, Optional<String> file) {
		super();
		this._id = _id;
		this.name = name;
		this.description = description;
		this.club = club;
		this.date = date;
		this.status = status;
		this.departments = departments;
		this.file = file.orElse(null);
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
	public HashMap<ObjectId, Boolean> getDepartments() {
		return departments;
	}
	public void setDepartments(HashMap<ObjectId, Boolean> departments) {
		this.departments = departments;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
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
	
}
