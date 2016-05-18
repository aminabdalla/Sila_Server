package com.sila.dbo;

import java.util.HashMap;

public class Person extends PersistantDBO {

	private Person(String key){
		this.setKey(key);
	}
	
	private String name;

	private HashMap<String, Relationship> relations = new HashMap<>();

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
	
	public static class PersonBuilder {
		
		private Person person;
		
		public PersonBuilder(String key){
			this.person = new Person(key);
		}
		
		public PersonBuilder withName(String name){
			this.person.setName(name);
			return this;
		}
		
		public PersonBuilder withRelation(Relationship rel,Person person){
			this.person.addRelation(rel, person);
			return this;
		}
		
		public Person build(){
			return this.person;
		}
	}

}
