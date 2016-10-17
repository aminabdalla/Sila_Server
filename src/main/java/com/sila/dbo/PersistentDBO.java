package com.sila.dbo;

import com.sila.dao.DBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PersistentDBO implements DBO {

	private String uri;

}
