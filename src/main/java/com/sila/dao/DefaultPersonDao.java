package com.sila.dao;

import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.UpdateQuery;
import com.sila.dbo.Person;
import com.sila.utils.IOResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class DefaultPersonDao implements PersonDAO {

    Connection connection;
    private String SPARQL_SELECT_QUERY = "select * where { %s ?p ?o . ?o rdf:type <http://www.sila.com/family#Person>}";
    private String SPARQL_INSERT_PERSON_QUERY = "INSERT DATA { %s rdf:type <http://www.sila.com/family#Person>}";
    private String SPARQL_INSERT_PERSON_PROPERTIES_QUERY = "INSERT DATA { %s <%s> %s}";
    private String SPARQL_DELETE_PERSON = "DELETE WHERE {%s ?p ?o}";


    public DefaultPersonDao(Connection conn){
        connection = conn;
    }

    @Override
    public boolean delete(Person dbo) {
        UpdateQuery result = connection.update(String.format(SPARQL_DELETE_PERSON, dbo.getUri()));
        result.execute();
        return false;
    }

    @Override
    public IOResult<Exception, Person> insert(Person dbo) {
//        if(wasSuccess(connection.update(SPARQL_INSERT_PERSON_QUERY,dbo.getUri()))){
        UpdateQuery result = connection.update(String.format(SPARQL_INSERT_PERSON_QUERY, dbo.getUri()));
        List<UpdateQuery> propertyUpdate = dbo.listProperties().stream().flatMap(person ->
                person.getRight().stream().map(property ->
                        connection.update(String.format(SPARQL_INSERT_PERSON_PROPERTIES_QUERY, dbo.getUri(), property, person.getKey()))
                )).collect(Collectors.toList());
        propertyUpdate.stream().forEach(query -> query.execute());
        result.execute();
        return IOResult.success(dbo);
//        };
//        return IOResult.error(new IllegalArgumentException("Unable to insert person"));
        };

    private boolean wasSuccess(UpdateQuery update) {
        return false;
    }

    @Override
    public IOResult<Exception, Person> read(String key) {
        try {
            SelectQuery l = connection.select(String.format(SPARQL_SELECT_QUERY,key));
            return getPersonFromQueryResult(key,(TupleQueryResult) l.execute());
        } catch (Exception e){
            log.error("Could no read person with uri: {}",key);
            return IOResult.error(e);
        }
    }

    @Override
    public IOResult<Exception, Person> update(Person dbo) {
        return null;
    }

    private IOResult<Exception,Person> getPersonFromQueryResult(String uri,TupleQueryResult queryResult) {
        if(queryResult.hasNext()) {
            Person person = new Person(uri);
            Model model = ModelSupplier.supplyModel();
            while (queryResult.hasNext()) {
                BindingSet nextRow = queryResult.next();
                Property prop = model.getProperty(nextRow.getBinding("p").getValue().stringValue());
                String obj = nextRow.getBinding("o").getValue().stringValue();
                person.addProperty(obj, prop);
            }
        return IOResult.success(person);
        }
        return IOResult.error(new NoSuchElementException("Unkown Person"));
    }

}
