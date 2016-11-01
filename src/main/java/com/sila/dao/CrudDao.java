package com.sila.dao;

import com.sila.utils.IOResult;

public interface CrudDao<E extends Exception, R extends DBO> {

	boolean delete(R dbo);

	boolean insert(R dbo);

	IOResult<E, R> read(String key);

	boolean update(R dbo);

}
