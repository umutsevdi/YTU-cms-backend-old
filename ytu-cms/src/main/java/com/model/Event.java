package com.model;

import java.time.LocalDate;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Event {
	public static final List<String> components = List.of("_id", "name", "description", "start_date", "end_date","picture","club_id",
			"status", "location", "supporter_clubs", "speakers", "advisor", "representer", "companies",
			"departments");

	private final ObjectId _id;
	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private String picture;
	private ObjectId clubId;
	private StatusType status;
	private String location;
	private String[] supporterClubs;
	private String[] speakers;
	private ObjectId advisor;
	private String representer;
	private String[] companies;


	// Receives JSON generates a new user
	public Event(Document values, boolean isNew) throws Exception {
		/**
		 * Receives a JSON String, generates a new user;
		 */
		if (isNew) {
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
		} else {
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

}
