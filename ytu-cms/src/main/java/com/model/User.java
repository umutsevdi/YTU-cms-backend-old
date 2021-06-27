package com.model;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.cms.MongoDB;

public class User {
	public static final List<String> components = List.of("_id", "fullname", "mail", "password", "role", "club",
			"public_id", "email_confirmed");
	private final ObjectId _id;
	private String fullname;
	private String mail;
	private String password;
	private UserType role;

	private ObjectId club;
	private final String publicId;
	private boolean emailConfirmed;
	private boolean isActivated;

	private User(ObjectId _id, String fullname, String mail, String password, UserType role, ObjectId club,
			String publicId, boolean emailConfirmed, boolean isActivated) {
		this._id = _id;
		this.fullname = fullname;
		this.mail = mail;
		this.password = password;
		this.role = role;
		this.club = club;
		this.publicId = publicId;
		this.emailConfirmed = emailConfirmed;
		this.isActivated = isActivated;
	}

	public static User generate(Document values, boolean isNew) throws Exception {
		if (isNew) {
			if (values.containsKey("mail") && values.containsKey("role")) {
				try {
					User user = new User(new ObjectId(), " ", values.getString("mail"), Model.generatePublicId(10),
							UserType.valueOf(values.getString("role").toUpperCase()), values.getObjectId("club"),
							values.getString("public_id"), false, false);
					MongoDB.getDatabase().getCollection("users").insertOne(user.toDocument(true));
					return user;
				} catch (Exception e) {
					throw new Exception("MismatchingPropertyException");
				}

			} else {
				throw new Exception("MissingPropertyException");
			}

		} else {
			User user = new User(values.getObjectId("_id"), values.getString("fullname"), values.getString("mail"),
					values.getString("password"), UserType.valueOf(values.getString("role").toUpperCase()),
					values.getObjectId("club"), values.getString("public_id"), values.getBoolean("email_confirmed"),
					values.getBoolean("is_activated"));
			return user;
		}
	}

	public Document toDocument(boolean all) {// boolean all
		Document doc = new Document().append("fullname", fullname).append("mail", mail).append("role", role)
				.append("club", club).append("role", role.name()).append("public_id", publicId)
				.append("email_confirmed", emailConfirmed);

		if (all) {
			doc.append("_id", _id);
			doc.append("password", password);
		}
		return doc;
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

	public String getPublicId() {
		return publicId;
	}

	@Override
	public String toString() {
		return "User [_id=" + _id + ", fullname=" + fullname + ", mail=" + mail + ", password=" + password + ", role="
				+ role + ", club=" + club + ", publicId=" + publicId + ", emailConfirmed=" + emailConfirmed
				+ ", isActivated=" + isActivated + "]";
	}

}
