package com.model;

import org.bson.Document;
import org.bson.types.ObjectId;

public class PastAdministration {
	private ObjectId president;
	private ObjectId vicePresident;
	private ObjectId accountant;

	public PastAdministration(ObjectId president, ObjectId vicePresident, ObjectId accountant) {
		this.president = president;
		this.vicePresident = vicePresident;
		this.accountant = accountant;
	}

	public Document toDocument() {
		Document response = new Document()
		.append("president", president)
		.append("vicePresident", vicePresident)
		.append("accountant", accountant);
		return response;
	}

}
