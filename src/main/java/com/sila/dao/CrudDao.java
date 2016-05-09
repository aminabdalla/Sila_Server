package com.sila.dao;

import com.sila.utils.IOResult;

public interface CrudDao<E extends Exception, R extends DBO> {

	IOResult<E, R> delete(DBO dbo);

	IOResult<E, R> insert(DBO dbo);

	IOResult<E, R> read(String key);

	IOResult<E, R> update(DBO dbo);

}
