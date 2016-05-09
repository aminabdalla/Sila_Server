package com.sila.dbo;

import java.util.HashMap;

public class Person extends PersitantDBO {

	private String name;

	private HashMap<String, Relationship> relations;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void addRelation(final Relationship relation, final Person person) {
		relations.put(person.getKey(), relation);
	}

	public void removeRelation(final Person person) {
		relations.remove(person.getKey());
	}

}
