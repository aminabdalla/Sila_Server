package com.sila;

import com.sila.dao.DefaultPersonDao;
import com.sila.service.DefaultPersonService;
import org.glassfish.jersey.server.Uri;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sila.controller.PersonController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ComponentScan("/com/sila/")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class PersonControllerTest {

	private MockMvc mvc;

	private static String HABIBA = "HabibaAbdalla";
	private static String JSON_LOCATION = "src/test/resources/Habiba.json";


	@Test
	public void getHello() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/Person")
						.param("id",HABIBA)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(
								MockMvcResultMatchers.content().string(
								Matchers.equalTo(readFromJson())));
	}

	private String readFromJson() throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(JSON_LOCATION)));
		return content;
	}

	@Before
	public void setUp() throws Exception {
		DefaultPersonDao personDao = new DefaultPersonDao();
		PersonController personController = new PersonController();
		personController.setPerson(new DefaultPersonService(personDao));
		mvc = MockMvcBuilders.standaloneSetup(personController).build();
	}
}
