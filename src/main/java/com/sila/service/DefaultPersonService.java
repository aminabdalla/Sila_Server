package com.sila.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sila.dao.DBO;
import com.sila.dao.PersonDAO;
import com.sila.dbo.Person;
import com.sila.utils.IOResult;

@Service
public class DefaultPersonService implements PersonService {

	private final PersonDAO personDao;

	@Autowired
	public DefaultPersonService(final PersonDAO personDao) {
		this.personDao = personDao;
	}

	@Override
	public IOResult<Exception, Person> delete(final DBO dbo) {
		return personDao.delete(dbo);
	}

	@Override
	public IOResult<Exception, Person> insert(final DBO dbo) {
		return personDao.insert(dbo);
	}

	@Override
	public IOResult<Exception, Person> read(final String key) {
		return personDao.read(key);
	}

	@Override
	public IOResult<Exception, Person> update(final DBO dbo) {
		return personDao.update(dbo);
	}

}
