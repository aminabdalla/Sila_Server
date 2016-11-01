package com.sila;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.sila.dao.PersonDAO;
import com.sila.dbo.Person;
import com.sila.service.DefaultPersonService;
import com.sila.utils.IOResult;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

	private static final String PERSON_KEY = "personKey";
	private static final String PERSON_NAME = "The_persons_name";
	private DefaultPersonService personService;
	private Person person;
	private IOResult<Exception, Person> personResult;
	private boolean result;
	@Mock
	private PersonDAO personDao;
	private boolean updateResult;

	@Before
	public void setUp() {
		person = new Person(PERSON_KEY);
		personService = new DefaultPersonService(personDao);
	}

	@Test
	public void insertPersonSuccessTest() {
		givenAPerson();
		whenPersonIsInsertedSuccessfully();
		thenReturnedResultIsSuccess();
	}

	@Test
	public void insertPersonErrorTest() {
		givenAPerson();
		whenPersonIsInsertedWithAnError();
		thenReturnedResultIsInError();
	}

	@Test
	public void getPersonSuccessTest(){
		givenAPersonExistsInDB();
		whenPersonIsQueriedFor();
		thenPersonIsReturnedSuccessfully();
	}

	@Test
	public void updatePersonSuccessTest(){
		givenAPersonExistsInDB();
		whenPersonIsUpdated();
		thenTheUpdateReturnsSuccesfully();
	}

	private void thenTheUpdateReturnsSuccesfully() {
		assertThat(updateResult).isTrue();
	}

	private void whenPersonIsUpdated() {
		Mockito.when(personDao.update(Mockito.any())).thenReturn(true);
		updateResult = personDao.update(person);
	}

	private void thenPersonIsReturnedSuccessfully() {
		assertThat(personResult.getResult()).isEqualTo(person);
	}

	private void whenPersonIsQueriedFor() {
		personResult = personService.read(person.getUri());
	}

	private void givenAPersonExistsInDB() {
		Mockito.when(personDao.read(Mockito.anyString())).thenReturn(IOResult.success(person));
	}

	private void thenReturnedResultIsInError() {
		assertThat(result).isFalse();
	}

	private void whenPersonIsInsertedWithAnError() {
		Mockito.when(personDao.insert(Mockito.anyObject())).thenReturn(
				false);
		result = personService.insert(person);
	}

	private void givenAPerson() {
		person = new Person(PERSON_NAME);
	}

	private void thenReturnedResultIsSuccess() {
		assertThat(result).isTrue();
	}

	private void whenPersonIsInsertedSuccessfully() {
		Mockito.when(personDao.insert(Mockito.anyObject())).thenReturn(true);
		result = personService.insert(person);
	}
}
