package com.cms.models.documents;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "events")
public class Event {
    @Id
    private String id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private HashMap<String,Boolean> departments;  // ObjectId-Bool map. If a department accepts, set to true
    private String location;
    private List<String> supporterClubs; // Object Ids
    private List<String> speakers;
    private List<String> companies;
    public Event() {
    }
    public Event(String name, String description, LocalDate startDate, LocalDate endDate, Status status,
            HashMap<String, Boolean> departments, String location, List<String> supporterClubs, List<String> speakers,
            List<String> companies) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.departments = departments;
        this.location = location;
        this.supporterClubs = supporterClubs;
        this.speakers = speakers;
        this.companies = companies;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public HashMap<String, Boolean> getDepartments() {
        return departments;
    }
    public void setDepartments(HashMap<String, Boolean> departments) {
        this.departments = departments;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public List<String> getSupporterClubs() {
        return supporterClubs;
    }
    public void setSupporterClubs(List<String> supporterClubs) {
        this.supporterClubs = supporterClubs;
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

    
    

}
