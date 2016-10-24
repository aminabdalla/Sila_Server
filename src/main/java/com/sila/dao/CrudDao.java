package com.sila.dao;

import com.sila.dbo.Person;
import com.sila.utils.IOResult;

public interface CrudDao<E extends Exception, R extends DBO> {

	boolean delete(R dbo);

	IOResult<E, R> insert(R dbo);

	IOResult<E, R> read(String key);

	IOResult<E, R> update(R dbo);

}
