package pl.jstk.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import pl.jstk.BookApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookApplication.class)
@AutoConfigureMockMvc
public class BookControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private static final String BOOKS_URL = "/books";
	
	@Test
	@WithMockUser(username = "alex", roles = {"USER"})
	public void shouldReturnValidResponse() throws Exception {
		
		mockMvc.perform(get(BOOKS_URL))
		.andDo(print())
		.andExpect(status().isOk());
		
	}

}
