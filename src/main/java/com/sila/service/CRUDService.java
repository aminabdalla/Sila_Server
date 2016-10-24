package com.sila.service;

import com.sila.dao.DBO;
import com.sila.utils.IOResult;

public interface CRUDService<E extends Exception, R extends DBO> {

	boolean delete(final R dbo);

	IOResult<E, R> insert(R dbo);

	IOResult<E, R> read(String key);

	IOResult<E, R> update(R dbo);

}
