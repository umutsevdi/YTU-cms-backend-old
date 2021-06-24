package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.cms.service.UserService;

public class Club {
	public static final List<String> components = List.of("_id", "name", "description", "picture", "president",
			"vice_president", "accountant", "advisors", "communities", "events", "documents");

	private final ObjectId _id;
	private String name;
	private String description;
	private String picture;
	private ObjectId president;
	private ObjectId vicePresident;
	private ObjectId accountant;
	private List<ObjectId> advisors;
	private HashMap<String, PastAdministration> pastAdministrations;
	private List<String> communities;
	private List<ObjectId> events;
	private List<ObjectId> documents;

//GENERATE A MAP <YEAR><OBJECT ID>
	// Receives JSON generates a new user
	public Club(Document values, boolean isNew) throws Exception {
		if (isNew) {
			if (values.containsKey("name") && values.containsKey("description") && values.containsKey("picture")
					&& values.containsKey("advisor") && values.containsKey("president")
					&& values.containsKey("vice_president") && values.containsKey("accountant")) {
				this._id = new ObjectId();
				this.name = values.getString("name");
				this.description = values.getString("description");
				this.picture = values.getString("picture");
				this.president = UserService.createClubAccount((Document) values.get("president"), "President",
						this._id);
				this.vicePresident = UserService.createClubAccount((Document) values.get("vice_president"),
						"Vice_President", this._id);
				this.accountant = UserService.createClubAccount((Document) values.get("accountant"), "Accountant",
						this._id);

				this.advisors = values.getList("advisors", ObjectId.class);
				this.events = new ArrayList<ObjectId>();
				this.documents = new ArrayList<ObjectId>();
				this.communities = new ArrayList<String>();
				this.pastAdministrations = new HashMap<String, PastAdministration>();
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
			this.president = values.getObjectId("president");
			this.vicePresident = values.getObjectId("vice_president");
			this.accountant = values.getObjectId("accountant");
			this.advisors = values.getList("advisors", ObjectId.class);
			this.events = values.getList("events", ObjectId.class);
			this.documents = values.getList("documents", ObjectId.class);
			this.pastAdministrations = new HashMap<String, PastAdministration>();
			this.communities = values.getList("communities", String.class);
			((Document) values.get("past_administration")).forEach((key, value) -> {
				Document tmp = (Document) value;
				pastAdministrations.put(key, new PastAdministration(tmp.getObjectId("president"),
						tmp.getObjectId("vice_president"), tmp.getObjectId("accountant")));
			});
		}

	}

	public Document toDocument(boolean all) {// boolean all
		Document doc = new Document()

				.append("name", name).append("description", description).append("picture", picture)
				.append("president", president).append("vice_president", vicePresident).append("accountant", accountant)
				.append("advisors", advisors).append("events", events).append("documents", documents)
				.append("communities", communities);
		Document tmp = new Document();
		pastAdministrations.forEach((key, value) -> {
			tmp.append(key, value.toDocument());
		});
		doc.append("past_administrations", tmp);

		if (all) {
			doc.append("_id", _id);
		}
		return doc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public ObjectId getPresident() {
		return president;
	}

	public void setPresident(ObjectId president) {
		this.president = president;
	}

	public ObjectId getVicePresident() {
		return vicePresident;
	}

	public void setVicePresident(ObjectId vicePresident) {
		this.vicePresident = vicePresident;
	}

	public ObjectId getAccountant() {
		return accountant;
	}

	public void setAccountant(ObjectId accountant) {
		this.accountant = accountant;
	}

	public List<ObjectId> getAdvisors() {
		return advisors;
	}

	public void setAdvisors(List<ObjectId> advisors) {
		this.advisors = advisors;
	}

	public HashMap<String, PastAdministration> getPastAdministrations() {
		return pastAdministrations;
	}

	public void setPastAdministrations(HashMap<String, PastAdministration> pastAdministrations) {
		this.pastAdministrations = pastAdministrations;
	}

	public List<String> getCommunities() {
		return communities;
	}

	public void setCommunities(List<String> communities) {
		this.communities = communities;
	}

	public List<ObjectId> getEvents() {
		return events;
	}

	public void setEvents(List<ObjectId> events) {
		this.events = events;
	}

	public List<ObjectId> getDocuments() {
		return documents;
	}

	public void setDocuments(List<ObjectId> documents) {
		this.documents = documents;
	}

	public ObjectId get_id() {
		return _id;
	}

	@Override
	public String toString() {
		return "Club [_id=" + _id + ", name=" + name + ", description=" + description + ", picture=" + picture
				+ ", president=" + president + ", vicePresident=" + vicePresident + ", accountant=" + accountant
				+ ", advisors=" + advisors + ", pastAdministrations=" + pastAdministrations + ", communities="
				+ communities + ", events=" + events + ", documents=" + documents + "]";
	}

}
