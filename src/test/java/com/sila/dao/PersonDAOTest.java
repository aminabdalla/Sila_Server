package com.sila.dao;

import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.Query;
import com.sila.dbo.Person;
import com.sila.utils.IOResult;
import org.apache.jena.rdf.model.Model;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class PersonDAOTest {

    PersonDAO personDao;
    private String personUri = "<http://www.sila.com/family#AminAbdalla>";
    IOResult<Exception, Person> personResult;
    String relatedPersonUri = "http://www.sila.com/family#HabibaAbdalla";
    Connection conn;
    Model model = ModelSupplier.supplyModel();

    @Test
    public void readPersonByUri() {
        givenADefaultPersonDao();
        whenSearchingForAPersonByUri();
        thenThePersonShouldBeReturned();
    }

    private void thenThePersonShouldBeReturned() {

        assertThat(personResult.getResult().getUri()).isEqualTo(personUri);
        assertThat(personResult.getResult().getRelationByPerson(relatedPersonUri)).isEqualTo(model.getProperty("http://www.sila.com/family#","hasDaughter"));
    }

    private void whenSearchingForAPersonByUri() {
        Transactional.doSelectInTransaction(conn -> {
            personResult = personDao.read(personUri);
        });
    }

    private void givenADefaultPersonDao() {
        personDao = new DefaultPersonDao(ConnectionSupplier.returnConnection());
    }

}