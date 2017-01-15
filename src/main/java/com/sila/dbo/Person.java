package com.sila.dbo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(value = "propertyMap")
public class Person extends PersistentDBO {

    public Person(String personURI) {
        super(personURI);
    }

    private HashMap<String, Set<Property>> propertyMap = new HashMap<>();

    private String name;

    public void addProperty(final String personURI, final Property relation) {
        if (propertyMap.putIfAbsent(personURI, Sets.newHashSet(relation)) != null) {
            Set<Property> props = propertyMap.get(personURI);
            Set<Property> newProps = Sets.newHashSet(relation);
            props.forEach(e -> newProps.add(e));
            propertyMap.replace(personURI, newProps);
        }
    }

    public void removeProperty(final String personURI) {
        propertyMap.remove(personURI);
    }

    public Set<Property> getPropertyByPerson(final String personUri) {
        return propertyMap.get(format(personUri));
    }


    public List<Pair<String, Set<Property>>> listProperties() {
        return propertyMap.entrySet()
                .stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
//    @JsonGetter
    public List<Pair<String, List<String>>> getProperties(){
        return listProperties()
                .stream()
                .map(entry -> Pair.of(entry.getLeft(),transform(entry.getRight()))).collect(Collectors.toList());
    }

    private List<String> transform(Set<Property> properties) {
        return properties.stream().map(Resource::toString).collect(Collectors.toList());
    }

    private String format(String personUri) {
        return (personUri.startsWith("<")) ? personUri : "<"+personUri+">";
    }
}
