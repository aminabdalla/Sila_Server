package com.sila.dbo;

import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.rdf.model.Property;
import org.assertj.core.util.Lists;

import java.util.*;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
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
        return propertyMap.get(personUri);
    }

    public List<Pair<String, Set<Property>>> listProperties() {
        return propertyMap.entrySet()
                .stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
