package com.sila.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public boolean delete(final Person dbo) {
		return personDao.delete(dbo);
	}

	@Override
	public boolean insert(final Person dbo) {
		return personDao.insert(new Person(""));
	}

	@Override
	public IOResult<Exception, Person> read(final String key) {
		return personDao.read(key);
	}

	@Override
	public boolean update(final Person dbo) {
		return personDao.update(dbo);
	}

}
