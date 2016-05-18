package com.sila.dao;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;

import com.sila.dbo.Person;
import com.sila.dbo.Relationship;
import com.sila.utils.IOResult;

import javassist.tools.rmi.ObjectNotFoundException;

@Component
public class StubPersonDao implements PersonDAO {
	private static Person yumna = new Person.PersonBuilder("1").withName("Yumna")
			.build();
	
	private static Person amin = new Person.PersonBuilder("2").withName("Amin")
			.withRelation(Relationship.FATHER, yumna).build();
	
	private static Person ahmad = new Person.PersonBuilder("3").withName("Ahmad Abdalla")
			.withRelation(Relationship.FATHER, amin).build();


	
	@Override
	public IOResult<Exception, Person> insert(final DBO dbo) {
		return IOResult.error(new NotImplementedException());
	}

	@Override
	public IOResult<Exception, Person> update(final DBO dbo) {
		return IOResult.error(new NotImplementedException());
	}

	@Override
	public IOResult<Exception, Person> delete(final DBO dbo) {
		return IOResult.error(new NotImplementedException());
	}

	@Override
	public IOResult<Exception, Person> read(final String key) {
		return thePerson(key);
	}

	private IOResult<Exception, Person> thePerson(String key) {
		if(key.equals("1"))
			return IOResult.success(yumna);
		else if (key.equals("2"))
			return IOResult.success(amin);
		else if (key.equals("3"))
			return IOResult.success(ahmad);
		return IOResult.error(new ObjectNotFoundException(key));
	}

}
