package com.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.cms.controller.UserController;
import com.cms.service.UserService;

public class Club {
	public static final List<String> components = List.of("_id", "name", "description", "picture", "president",
			"vice_president", "accountant", "advisor", "events", "documents");

	private final ObjectId _id;
	private String name;
	private String description;
	private String picture;
	private ObjectId president;
	private ObjectId vicePresident;
	private ObjectId accountant;
	private List<ObjectId> advisor;
	private List<Event> events;
	private List<ClubDocument> documents;
//GENERATE A MAP <YEAR><OBJECT ID>
	// Receives JSON generates a new user
	public User(Document values, boolean isNew) throws Exception {
		/**
		 * Receives a JSON String, generates a new user;
		 */
		if (isNew) {
			if (values.containsKey("name") && values.containsKey("description") && values.containsKey("picture") &&
					values.containsKey("advisor")&& values.containsKey("president") && values.containsKey("vice_president")&&
					values.containsKey("accountant")) {
				this._id = new ObjectId();
				this.name = values.getString("name");
				this.description = values.getString("description");
				this.picture = values.getString("picture");
				this.president= UserService.createClubAccount((Document)values.get("president"),"President",this._id);
				this.vicePresident= UserService.createClubAccount((Document)values.get("vice_president"),"Vice_President",this._id);
				this.accountant= UserService.createClubAccount((Document)values.get("accountant"),"Accountant",this._id);
				
				this.advisor = values.getList("advisor",ObjectId.class);
				this.events=new ArrayList<Event>();
				this.documents=new ArrayList<ClubDocument>();
				
				if (!values.containsKey("picture") || !Model.isURL(values.getString("picture")))
					values.append("picture", "https://i.gifer.com/1uoA.gif");
				this.picture = values.getString("picture");
			} else {
				throw new Exception("MissingPropertyException");
			}
		} else {
			this._id = values.getObjectId("_id");
			this.name = values.getString("name");
			this.description = values.getString("description");
			this.picture = values.getString("picture");
			
			
			
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
