package com.sila.service;

import com.sila.dao.ConnectionSupplier;
import com.sila.dao.DefaultPersonDao;
import com.sila.vocabulary.Family;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sila.dao.PersonDAO;
import com.sila.dbo.Person;
import com.sila.utils.IOResult;


@Setter
@Service
@Component
public class DefaultPersonService implements PersonService {

	private PersonDAO personDao;

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
		return personDao.read(buildUri(key));
	}

	@Override
	public boolean update(final Person dbo) {
		return personDao.update(dbo);
	}

	private String buildUri(String key) {
		return "<"+ Family.NAMESPACE + key +">";
	}

}
