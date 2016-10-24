package com.sila.vocabulary;

import org.openrdf.model.IRI;
import org.openrdf.model.impl.SimpleValueFactory;

public class Family {

    public static final String NAMESPACE = "http://www.sila.com/family#";
    public static final String PREFIX = "family";
    public static final IRI PERSON;
    public static final IRI HAS_CHILD;
    public static final IRI IS_CHILD_OF;
    public static final IRI IS_DAUGHTER_OF;
    public static final IRI HAS_DAUGHTER;
    public static final IRI HAS_SIBLING;
    public static final IRI HAS_BROTHER;

    static {
        SimpleValueFactory factory = SimpleValueFactory.getInstance();
        PERSON = factory.createIRI(NAMESPACE, "Person");
        HAS_CHILD = factory.createIRI(NAMESPACE, "hasChild");
        IS_CHILD_OF = factory.createIRI(NAMESPACE, "isChildOf");
        IS_DAUGHTER_OF = factory.createIRI(NAMESPACE, "isDaughterOf");
        HAS_DAUGHTER = factory.createIRI(NAMESPACE, "hasDaughter");
        HAS_BROTHER = factory.createIRI(NAMESPACE, "hasBrother");
        HAS_SIBLING = factory.createIRI(NAMESPACE, "hasSibling");
    }
}




