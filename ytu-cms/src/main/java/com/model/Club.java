package com.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.cms.service.UserService;

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
		if (isNew) {
			if (values.containsKey("name") && values.containsKey("advisors") && values.containsKey("president")
					&& values.containsKey("vice_president") && values.containsKey("accountant")) {

				ObjectId president, vicePresident, accountant;
				if (UserService.mailExists(values.getString("president"))) {
					president = UserService.getObjectIdFromMail(values.getString("president"));
				} else {
					president = User.generate(
							new Document().append("mail", values.getString("president")).append("role", "PRESIDENT"),
							true).get_id();
				}

				if (UserService.mailExists(values.getString("vice_president"))) {
					vicePresident = UserService.getObjectIdFromMail(values.getString("vice_president"));
				} else {
					vicePresident = User.generate(new Document().append("mail", values.getString("vice_president"))
							.append("role", "VICE_PRESIDENT"), true).get_id();
				}

				if (UserService.mailExists(values.getString("accountant"))) {
					accountant = UserService.getObjectIdFromMail(values.getString("accountant"));
				} else {
					accountant = User.generate(
							new Document().append("mail", values.getString("accountant")).append("role", "ACCOUNTANT"),
							true).get_id();
				}
				Club club = new Club(new ObjectId(), values.getString("name"), " ", president, vicePresident,
						accountant, values.getList("advisors", ObjectId.class),
						new HashMap<String, PastAdministration>(), new LinkedList<String>());
				return club;
			} else {
				throw new Exception("MissingPropertyException");
			}

		} else {
			HashMap<String, PastAdministration> pastAdministration = new HashMap<String, PastAdministration>();
			values.forEach((key, value) -> {
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

	public Document toDocument(boolean all) throws Exception {// boolean all
		Document doc = new Document()

				.append("name", name).append("description", description)
				.append("president", UserService.getPublicId(president))
				.append("vice_president", UserService.getPublicId(vicePresident))
				.append("accountant", UserService.getPublicId(accountant)).append("communities", communities);
		LinkedList<String> advisorList = new LinkedList<String>();
		advisors.forEach(element -> {
			try {
				advisorList.add(UserService.getPublicId(element));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		doc.append("advisors", advisorList);
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

	public ObjectId get_id() {
		return _id;
	}

	@Override
	public String toString() {
		return "Club [_id=" + _id + ", name=" + name + ", description=" + description + ", president=" + president
				+ ", vicePresident=" + vicePresident + ", accountant=" + accountant + ", advisors=" + advisors
				+ ", pastAdministrations=" + pastAdministrations + ", communities=" + communities + "]";
	}

}
