package com.sila.service;

import com.sila.dao.DBO;
import com.sila.utils.IOResult;

public interface CRUDService<E extends Exception, R extends DBO> {

	boolean delete(final R dbo);

	boolean insert(R dbo);

	IOResult<E, R> read(String key);

	boolean update(R dbo);

}
