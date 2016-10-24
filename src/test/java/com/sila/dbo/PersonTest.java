package com.sila.dbo;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class PersonTest {

    String personUri = "http://www.sila.com/family#AminAbdalla";
    String relatedPersonUri = "http://www.sila.com/family#YumnaAbdalla";
    private String anotherRelatedPerson = "http://www.sila.com/family#HabibaAbdalla";
    Property relationShip = new PropertyImpl("http://www.sila.com/family#hasChild");


    @Before
    public void setUp(){
        Model model = ModelFactory.createDefaultModel();
    }

    @Test
    public void ifRelationisAddeditIsFoundAgain(){
        Person person = new Person(personUri);
        person.addProperty(relatedPersonUri,relationShip);
        assertThat(person)
                .extracting(p -> p.getPropertyByPerson(relatedPersonUri))
                .contains(relationShip);
    }

    @Test
    public void ifRelationisRemoveditIsNotThere(){
        Person person = new Person(personUri);
        person.addProperty(relatedPersonUri,relationShip);
        person.removeProperty(relatedPersonUri);
        assertThat(person)
                .extracting(p -> p.getPropertyByPerson(relatedPersonUri))
                .doesNotContain(relationShip);
    }
    
    @Test
    public void iflistRelationsIsCalledRelationsAreReturned(){
        Person person = new Person(personUri);
        person.addProperty(relatedPersonUri,relationShip);
        person.addProperty(anotherRelatedPerson,relationShip);
        List<String> persons = person.listProperties()
                .stream()
                .map(p -> p.toString())
                .collect(Collectors.toList());
        assertThat(persons)
                .contains(Pair.of(relatedPersonUri, relationShip).toString(), Pair.of(anotherRelatedPerson, relationShip).toString());
    }

}