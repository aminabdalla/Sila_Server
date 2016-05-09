package com.sila.dao;

import org.springframework.stereotype.Component;

import com.sila.dbo.Person;
import com.sila.utils.IOResult;

@Component
public class StubPersonDao implements PersonDAO {

	@Override
	public IOResult<Exception, Person> insert(final DBO dbo) {
		return IOResult.error(new RuntimeException());
	}

	@Override
	public IOResult<Exception, Person> update(final DBO dbo) {
		return IOResult.error(new RuntimeException());
	}

	@Override
	public IOResult<Exception, Person> delete(final DBO dbo) {
		return IOResult.error(new RuntimeException());
	}

	@Override
	public IOResult<Exception, Person> read(final String key) {
		return IOResult.error(new RuntimeException());
	}

}
