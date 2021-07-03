package com.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.cms.MongoDB;
import com.cms.service.UserService;
import com.mongodb.client.model.Filters;

public class Club {
	public static final List<String> components = List.of("_id", "name", "description", "president", "vice_president",
			"accountant", "advisors", "communities", "past_administrations");

	private final ObjectId _id;
	private String name;
	private String description;
	private ObjectId president;
	private ObjectId vicePresident;
	private ObjectId accountant;
	private List<ObjectId> advisors;
	private HashMap<String, PastAdministration> pastAdministrations;
	private List<String> communities;

	private Club(ObjectId _id, String name, String description, ObjectId president, ObjectId vicePresident,
			ObjectId accountant, List<ObjectId> advisors, HashMap<String, PastAdministration> pastAdministrations,
			List<String> communities) {
		this._id = _id;
		this.name = name;
		this.description = description;
		this.president = president;
		this.vicePresident = vicePresident;
		this.accountant = accountant;
		this.advisors = advisors;
		this.pastAdministrations = pastAdministrations;
		this.communities = communities;
	}

	// User will send president: mail
	public static Club generate(Document values, boolean isNew) throws Exception {
		System.out.println("generate club : " + isNew + "\n" + values.toJson());
		if (isNew) {
			if (values.containsKey("name") && values.containsKey("advisors")) {

				Club club = new Club(new ObjectId(), values.getString("name"), " ", new ObjectId(), new ObjectId(),
						new ObjectId(), values.getList("advisors", ObjectId.class),
						new HashMap<String, PastAdministration>(), new LinkedList<String>());
				MongoDB.getDatabase().getCollection("clubs").insertOne(club.toDocument());
				return club;
			} else {
				throw new Exception("MissingPropertyException");
			}

		} else {
			HashMap<String, PastAdministration> pastAdministration = new HashMap<String, PastAdministration>();
			((Document) values.get("past_administrations")).forEach((key, value) -> {
				pastAdministration.put(key,
						new PastAdministration(((Document) value).getObjectId("president"),
								((Document) value).getObjectId("vice_president"),
								((Document) value).getObjectId("accountant")));
			});

			Club club = new Club(values.getObjectId("_id"), values.getString("name"), values.getString("description"),
					values.getObjectId("president"), values.getObjectId("vicePresident"),
					values.getObjectId("accountant"), values.getList("advisors", ObjectId.class), pastAdministration,
					values.getList("communities", String.class));

			return club;
		}

	}

	public Document toDocument() {
		Document doc = new Document().append("_id", _id).append("name", name).append("description", description)
				.append("president", president).append("vice_president", vicePresident).append("accountant", accountant)
				.append("communities", communities).append("advisors", advisors);
		Document tmp = new Document();
		pastAdministrations.forEach((key, value) -> {
			tmp.append(key, value.toDocument());
		});
		doc.append("past_administrations", tmp);
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

	public ObjectId getPresident() {
		return president;
	}

	public void setPresident(String mail) {
		president = UserService.getObjectIdFromMail(mail)
				.orElse(User.generate(new Document().append("mail", mail).append("role", "PRESIDENT"), true).get_id());
	}

	public ObjectId getVicePresident() {
		return vicePresident;
	}

	public void setVicePresident(String mail) {
		vicePresident = UserService.getObjectIdFromMail(mail).orElse(
				User.generate(new Document().append("mail", mail).append("role", "VICE_PRESIDENT"), true).get_id());
	}

	public ObjectId getAccountant() {
		return accountant;
	}

	public void setAccountant(String mail) throws Exception {
		accountant = UserService.getObjectIdFromMail(mail)
				.orElse(User.generate(new Document().append("mail", mail).append("role", "ACCOUNTANT"), true).get_id());

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

	public ObjectId get_id() {
		return _id;
	}

	@Override
	public String toString() {
		return "Club [_id=" + _id + ", name=" + name + ", description=" + description + ", president=" + president
				+ ", vicePresident=" + vicePresident + ", accountant=" + accountant + ", advisors=" + advisors
				+ ", pastAdministrations=" + pastAdministrations + ", communities=" + communities + "]";
	}

	public void updateDatabase() {
		MongoDB.getDatabase().getCollection("clubs").findOneAndReplace(Filters.eq("_id", _id), this.toDocument());
	}
}
