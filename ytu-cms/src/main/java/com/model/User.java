package com.model;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.cms.MongoDB;
import com.mongodb.client.model.Filters;

public class User {
	public static final List<String> components = List.of("_id", "fullname", "mail", "password", "role", "club",
			"email_confirmed");
	private final ObjectId _id;
	private String fullname;
	private String mail;
	private String password;
	private UserType role;

	private ObjectId club;
	private boolean emailConfirmed;
	private boolean isActivated;

	private User(ObjectId _id, String fullname, String mail, String password, UserType role, ObjectId club,
			boolean emailConfirmed, boolean isActivated) {
		this._id = _id;
		this.fullname = fullname;
		this.mail = mail;
		this.password = password;
		this.role = role;
		this.club = club;
		this.emailConfirmed = emailConfirmed;
		this.isActivated = isActivated;
	}

	public static User generate(Document values, boolean isNew) {
		if (isNew) {
			String password = Model.generateRandomNumber(10); // Send this to user mail

			if (values.containsKey("mail") && values.containsKey("role")) {
				User user = new User(new ObjectId(), " ", values.getString("mail"), Model.getSHA(password),
						UserType.valueOf(values.getString("role").toUpperCase()), values.getObjectId("club"), false,
						false);
				MongoDB.getDatabase().getCollection("users").insertOne(user.toDocument());
				return user;

			} else {
				return null;
			}

		} else {
			User user = new User(values.getObjectId("_id"), values.getString("fullname"), values.getString("mail"),
					values.getString("password"), UserType.valueOf(values.getString("role").toUpperCase()),
					values.getObjectId("club"), values.getBoolean("email_confirmed"),
					values.getBoolean("is_activated"));
			return user;
		}
	}

	public Document toDocument() {// boolean all
		return new Document().append("_id", _id).append("password", password).append("fullname", fullname)
				.append("mail", mail).append("role", role).append("club", club).append("role", role.name())
				.append("email_confirmed", emailConfirmed);
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

	public UserType getRole() {
		return role;
	}

	public void setRole(UserType role) {
		this.role = role;
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

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public ObjectId get_id() {
		return _id;
	}

	@Override
	public String toString() {
		return "User [_id=" + _id + ", fullname=" + fullname + ", mail=" + mail + ", password=" + password + ", role="
				+ role + ", club=" + club + ", emailConfirmed=" + emailConfirmed + ", isActivated=" + isActivated + "]";
	}

	public void updateDatabase() {
		MongoDB.getDatabase().getCollection("users").findOneAndReplace(Filters.eq("_id", _id), this.toDocument());
	}

}
