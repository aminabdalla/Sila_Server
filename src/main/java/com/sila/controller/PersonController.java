package com.sila.controller;

import com.sila.utils.IOResult;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sila.dbo.Person;
import com.sila.service.DefaultPersonService;

@RestController
@Slf4j
@RequestMapping("/Person")
public class PersonController {

	@Autowired
	private DefaultPersonService person;

	@RequestMapping(method = RequestMethod.GET)
	public Person index(@RequestParam("id") String id) {
        IOResult<Exception, Person> result = person.read(id);
        if(result.isSuccess()){
        return result.getResult();
        } else {
        result.getError().printStackTrace();
		return result.getResult();
        }
	}

}
