package com.sila.dbo;

import com.sila.dao.DBO;

public class PersistantDBO implements DBO {

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

}
