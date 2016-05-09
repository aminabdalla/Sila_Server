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

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

	private static final String PERSON_KEY = "personKey";
	private static final String PERSON_NAME = "The_persons_name";
	private DefaultPersonService personService;
	private Person person;
	private IOResult<Exception, Person> result;
	@Mock
	private PersonDAO personDao;

	@Before
	public void setUp() {
		personService = new DefaultPersonService(personDao);
	}

	@Test
	public void insertPersonSuccessTest() {
		givenAPerson();
		whenPersonIsInsertedSuccessfully();
		thenReturnedResultIsSuccessAndPersonObjectIsReturned();
	}

	@Test
	public void insertPersonErrorTest() {
		givenAPerson();
		whenPersonIsInsertedWithAnError();
		thenReturnedResultIsInError();
	}

	private void thenReturnedResultIsInError() {
		MatcherAssert.assertThat(result.isSuccess(), Matchers.is(false));
	}

	private void whenPersonIsInsertedWithAnError() {
		Mockito.when(personDao.insert(Mockito.anyObject())).thenReturn(
				IOResult.error(new RuntimeException()));
		result = personService.insert(person);
	}

	private void givenAPerson() {
		person = new Person();
		person.setKey(PERSON_KEY);
		person.setName(PERSON_NAME);
	}

	private void thenReturnedResultIsSuccessAndPersonObjectIsReturned() {
		MatcherAssert.assertThat(result.isSuccess(), Matchers.is(true));
		MatcherAssert.assertThat(result.getResult(), Matchers.is(person));
	}

	private void whenPersonIsInsertedSuccessfully() {
		Mockito.when(personDao.insert(Mockito.anyObject())).thenReturn(
				IOResult.success(person));
		result = personService.insert(person);
	}
}
