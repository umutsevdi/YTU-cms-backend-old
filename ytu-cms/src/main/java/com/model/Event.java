package com.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.cms.MongoDB;
import com.mongodb.client.model.Filters;

public class Event {
	public static final List<String> components = List.of("_id", "name", "description", "club", "start_date",
			"end_date", "status", "departments", "representer", "supporter_clubs", "location", "speakers", "companies");

	private final ObjectId _id;
	private final String name;
	private String description;
	private final ObjectId club;
	private Date startDate;
	private Date endDate;
	private StatusType status;
	private HashMap<String, Boolean> departments;
	private String representer; // mail
	private List<ObjectId> supporterClubs;
	private String location;
	private List<String> speakers;
	private List<String> companies;

	private Event(ObjectId _id, String name, String description, ObjectId club, Date startDate, Date endDate,
			StatusType status, HashMap<String, Boolean> departments, String representer, List<ObjectId> supporterClubs,
			String location, List<String> speakers, List<String> companies) {
		this._id = _id;
		this.name = name;
		this.description = description;
		this.club = club;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.departments = departments;
		this.representer = representer;
		this.supporterClubs = supporterClubs;
		this.location = location;
		this.speakers = speakers;
		this.companies = companies;
	}

	public static Event generate(Document values, boolean isNew) throws Exception {
		if (isNew) {
			try {
				boolean isValid = true;
				for (String key : components) {
					if (!(key.equals("_id") || key.equals("status")) && !values.containsKey(key))
						isValid = false;
				}
				if (!isValid)
					throw new Exception("MissingPropertyException");
				// Change this line when location => department is implemented
				HashMap<String, Boolean> map = new HashMap<String, Boolean>();
				values.getList("departments", String.class).forEach((key) -> {
					try {
						map.put(key, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				Event event = new Event(new ObjectId(), values.getString("name"), values.getString("description"),
						values.getObjectId("club"), values.getDate("start_date"), values.getDate("end_date"),
						StatusType.DRAFT, map, values.getString("representer"),
						values.getList("supporter_clubs", ObjectId.class), values.getString("location"),
						values.getList("speakers", String.class), values.getList("companies", String.class));
				MongoDB.getDatabase().getCollection("events").insertOne(event.toDocument(true));
				return event;
			} catch (Exception e) {
				throw new Exception(e.getLocalizedMessage());
			}

		} else {
			HashMap<String, Boolean> map = new HashMap<String, Boolean>();
			((Document) values.get("departments")).forEach((key, value) -> {
				try {
					map.put(key, (Boolean) value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			Event event = new Event(values.getObjectId("_id"), values.getString("name"),
					values.getString("description"), values.getObjectId("club"), values.getDate("start_date"),
					values.getDate("end_date"), StatusType.valueOf(values.getString("status").toUpperCase()), map,
					values.getString("representer"), values.getList("supporter_clubs", ObjectId.class),
					values.getString("location"), values.getList("speakers", String.class),
					values.getList("companies", String.class));
			return event;
		}
	}

	public Document toDocument(boolean all) {

		return new Document().append("_id", _id).append("name", name).append("description", description)
				.append("club", club).append("start_date", startDate).append("end_date", endDate)
				.append("status", status.toString()).append("departments", departments)
				.append("representer", representer).append("supporter_clubs", supporterClubs)
				.append("location", location).append("speakers", speakers).append("companies", companies);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(Boolean accepted) {
		if (accepted) {
			if (!this.status.toString().equals("COMPLETED"))
				this.status = StatusType.values()[this.status.ordinal() + 1];
		} else {
			this.status = StatusType.DRAFT;
		}

	}

	public HashMap<String, Boolean> getDepartments() {
		return departments;
	}

	public void setDepartments(HashMap<String, Boolean> departments) {
		this.departments = departments;
	}

	public String getRepresenter() {
		return representer;
	}

	public void setRepresenter(String representer) {
		this.representer = representer;
	}

	public List<ObjectId> getSupporterClubs() {
		return supporterClubs;
	}

	public void setSupporterClubs(List<ObjectId> supporterClubs) {
		this.supporterClubs = supporterClubs;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<String> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(List<String> speakers) {
		this.speakers = speakers;
	}

	public List<String> getCompanies() {
		return companies;
	}

	public void setCompanies(List<String> companies) {
		this.companies = companies;
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

	@Override
	public String toString() {
		return "Event [_id=" + _id + ", name=" + name + ", description=" + description + ", club=" + club
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + ", departments="
				+ departments + ", representer=" + representer + ", supporterClubs=" + supporterClubs + ", location="
				+ location + ", speakers=" + speakers + ", companies=" + companies + "]";
	}

	public void updateDatabase() {
		MongoDB.getDatabase().getCollection("events").findOneAndReplace(Filters.eq("_id", _id), this.toDocument(true));
	}
}
