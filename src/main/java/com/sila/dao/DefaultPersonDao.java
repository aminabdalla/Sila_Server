package com.sila.dao;

import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.SelectQuery;
import com.sila.dbo.Person;
import com.sila.utils.IOResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.util.FileManager;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import java.io.InputStream;
import java.util.stream.Stream;

@Slf4j
public class DefaultPersonDao implements PersonDAO {

    Connection connection;

    public DefaultPersonDao(Connection conn){
        connection = conn;
    }

    @Override
    public IOResult<Exception, Person> delete(DBO dbo) {
        return null;
    }

    @Override
    public IOResult<Exception, Person> insert(DBO dbo) {
        return null;
    }

    @Override
    public IOResult<Exception, Person> read(String key) {
        try {
            SelectQuery l = connection.select(String.format("select * where { %s ?p ?o}",key));
            return IOResult.success(getPersonFromQueryResult(key,(TupleQueryResult) l.execute()));
        } catch (Exception e){
            log.error("Could no read person with uri: {}",key);
            return IOResult.error(e);
        }
    }

    private Person getPersonFromQueryResult(String uri,TupleQueryResult queryResult) {
        Person person = new Person(uri);
        Model model = ModelSupplier.supplyModel();
        while(queryResult.hasNext()){
        BindingSet nextRow = queryResult.next();
            Property prop = model.getProperty(nextRow.getBinding("p").getValue().stringValue());
            String obj = nextRow.getBinding("o").getValue().stringValue();
            person.addRelation(obj,prop);
        }
        return person;
    }



    @Override
    public IOResult<Exception, Person> update(DBO dbo) {
        return null;
    }
}
