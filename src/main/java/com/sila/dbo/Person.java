package com.sila.dbo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.ext.com.google.common.collect.Lists;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
public class Person extends PersistentDBO {

	public Person(String personURI){
		super(personURI);
	}
	
	private HashMap<String, Property> relations = new HashMap<>();

	private String name;

	public void addRelation(final String personURI,final Property relation) {
		relations.put(personURI, relation);
	}

	public void removeRelation(final String personURI) {
		relations.remove(personURI);
	}

	public Property getRelationByPerson(final String personUri) { return relations.get(personUri); }

	public List<Pair<String,Property>> listRelations() {
		return relations.entrySet()
				.stream()
				.map(entry -> Pair.of(entry.getKey(),entry.getValue()))
				.collect(Collectors.toList());
	}
}
