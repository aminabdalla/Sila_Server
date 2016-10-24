package com.sila.dao;

import com.complexible.common.openrdf.vocabulary.FOAF;
import com.complexible.stardog.api.Connection;
import com.sila.dbo.Person;
import com.sila.utils.IOResult;
import com.sila.vocabulary.Family;
import org.apache.jena.rdf.model.Model;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class PersonDAOTest {

    PersonDAO personDao;
    private String insertPersonUri = "<http://www.sila.com/family#MonaAbdalla>";
    private String personUri = "<http://www.sila.com/family#AminAbdalla>";
    IOResult<Exception, Person> personResult;
    String relatedPersonUri = "http://www.sila.com/family#HabibaAbdalla";
    Connection conn;
    Model model = ModelSupplier.supplyModel();
    Person insertPerson = getInsertPerson();

    private Person getInsertPerson() {
        Person p = new Person(insertPersonUri);
        p.addProperty(personUri, model.getProperty(Family.HAS_SIBLING.stringValue()));
        return p;
    }

    @Test
    public void readPersonByUri() {
        givenADefaultPersonDao();
        whenSearchingForAPersonByUri();
        thenThePersonShouldBeReturned();
    }

    @Test
    public void insertPerson() {
        givenADefaultPersonDao();
        whenAPersonIsInserted();
        thenThePersonShouldBeFoundAgain();
    }

    @Test
    public void removePerson(){
        givenADefaultPersonDao();
        whenAPersonIsInserted();
        whenPersonIsRemoved();
        thenPersonShouldNotBeFound();
    }

    private void thenPersonShouldNotBeFound() {
        IOResult<Exception, Person> deletedPerson = personDao.read(insertPersonUri);
        assertThat(deletedPerson.isSuccess()).isFalse();
    }

    private void whenPersonIsRemoved() {
        IOResult<Exception, Person> person2Delete = personDao.read(insertPersonUri);
        personDao.delete(person2Delete.getResult());
    }

    private void thenThePersonShouldBeFoundAgain() {
        personResult = personDao.read(insertPersonUri);
        assertThat(personResult.getResult().getUri()).isEqualTo(insertPersonUri);
    }

    private void whenAPersonIsInserted() {
        Transactional.doSelectInTransaction(conn -> personDao.insert(insertPerson));
        assertThat(personDao.read(insertPersonUri).isSuccess()).isTrue();
    }


    private void thenThePersonShouldBeReturned() {
        assertThat(personResult.getResult().getUri()).isEqualTo(personUri);
        assertThat(personResult.getResult()
                .getPropertyByPerson(relatedPersonUri))
                .contains(model.getProperty("http://www.sila.com/family#","hasDaughter"));
    }

    private void whenSearchingForAPersonByUri() {
        Transactional.doSelectInTransaction(conn -> {
            personResult = personDao.read(personUri);
        });
    }

    private void givenADefaultPersonDao() {
        personDao = new DefaultPersonDao(ConnectionSupplier.returnConnection());
    }

    @After
    public void cleanUp(){
        personDao.delete(insertPerson);
    }

}