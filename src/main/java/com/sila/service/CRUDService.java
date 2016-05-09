package com.sila.service;

import com.sila.dao.DBO;
import com.sila.utils.IOResult;

public interface CRUDService<E extends Exception, R extends DBO> {

	IOResult<E, R> delete(final DBO dbo);

	IOResult<E, R> insert(DBO dbo);

	IOResult<E, R> read(String key);

	IOResult<E, R> update(DBO dbo);

}
