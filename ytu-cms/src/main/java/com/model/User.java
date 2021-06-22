package com.model;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

public class User {
	public static final List<String> components = List.of("_id", "fullname", "mail", "password", "year", "role", "club",
			"public_id", "email_confirmed", "picture");

	private final ObjectId _id;
	private String fullname;
	private String mail;
	private String password;
	private int year;
	private final UserType role;

	private ObjectId club;
	private final String publicId;
	private boolean emailConfirmed;
	private String picture;

	// Receives JSON generates a new user
	public User(String json) throws Exception {
		Document values = Document.parse(json);
		if (values.containsKey("fullname") && values.containsKey("mail") && values.containsKey("password")
				&& values.containsKey("year") && values.containsKey("role") && values.containsKey("club")) {
			this._id = new ObjectId();
			this.fullname = values.getString("fullname");
			this.mail = values.getString("mail");
			this.password = values.getString("password");
			this.year = values.getInteger("year");
			this.role = UserType.valueOf(values.getString("role").toUpperCase());
			this.club = values.getObjectId("club");
			this.publicId = Model.generatePublicId(6);
			if (!values.containsKey("picture") || !Model.isURL(values.getString("picture")))
				values.append("picture", "https://i.gifer.com/1uoA.gif");
			this.picture = values.getString("picture");
			this.emailConfirmed = false;
		} else {
			throw new Exception("MissingPropertyException");
		}
	}

	// Receives a document, reconstructs an object from it;
	public User(Document values) throws Exception {

		this._id = values.getObjectId("_id");
		this.fullname = values.getString("fullname");
		this.mail = values.getString("mail");
		this.password = values.getString("password");
		this.year = values.getInteger("year");
		this.role = UserType.valueOf(values.getString("role").toUpperCase());
		this.club = values.getObjectId("club");
		this.publicId = values.getString("public_id");
		this.picture = values.getString("picture");
		this.emailConfirmed = values.getBoolean("email_confirmed");

	}

	public Document toDocument(boolean all) {// boolean all
		Document doc = new Document();
		if (all) {
			doc.append("_id", _id);
			doc.append("password", password);
		}
		doc.append("fullname", fullname);
		doc.append("mail", mail);
		doc.append("year", year);
		doc.append("role", role);
		doc.append("club", club);
		doc.append("role", role.name());
		doc.append("public_id", publicId);
		doc.append("email_confirmed", emailConfirmed);
		doc.append("picture", picture);
		return doc;
	}

	@Override
	public String toString() {
		return "User " + _id + " [fullname=" + fullname + ", mail=" + mail + ", password=" + password + ", year=" + year
				+ ", role=" + role + ", club=" + club + ", publicId=" + publicId + ", emailConfirmed=" + emailConfirmed
				+ ", picture=" + picture + "]";
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public ObjectId getClub() {
		return club;
	}

	public void setClub(ObjectId club) {
		this.club = club;
	}

	public boolean isEmailConfirmed() {
		return emailConfirmed;
	}

	public void setEmailConfirmed(boolean emailConfirmed) {
		this.emailConfirmed = emailConfirmed;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public ObjectId get_id() {
		return _id;
	}

	public UserType getRole() {
		return role;
	}

	public String getPublicId() {
		return publicId;
	}

}
