package com.cms.models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.mongodb.lang.NonNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clubs")
public class Club {
    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private String name;
    private String description;
    private List<String> communities; //List of communities
    private List<String> advisors;
    private HashMap<Short,Administration> administration; // year,president map
    @CreatedDate
    private LocalDate registrationDate;
    private List<String> events; // event object Ids
    private List<String> documents; // document object Ids

    public Club() {
    }

    public Club(String name, String description, List<String> communities, List<String> advisors,
            HashMap<Short, Administration> administration, LocalDate registrationDate, List<String> events,
            List<String> documents) {
        this.name = name;
        this.description = description;
        this.communities = communities;
        this.advisors = advisors;
        this.administration = administration;
        this.registrationDate = registrationDate;
        this.events = events;
        this.documents = documents;
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

    public List<String> getCommunities() {
        return communities;
    }

    public void setCommunities(List<String> communities) {
        this.communities = communities;
    }

    public List<String> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(List<String> advisors) {
        this.advisors = advisors;
    }

    public HashMap<Short, Administration> getAdministration() {
        return administration;
    }

    public void setAdministration(HashMap<Short, Administration> administration) {
        this.administration = administration;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }
    

}