package pl.jstk.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.jstk.BookApplication;
import pl.jstk.constants.ViewNames;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration
public class BookControllerTest {
	
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private static final String BOOKS_URL = "/books";
	private static final String BOOK_URL = "/books/book";
	private static final String ADD_VIEW_URL = "/books/add";
	private static final String FIND_BOOK_URL = "/books/find";
	private static final String DELETE_BOOK_URL = "/books/deleteBook";
	private static final String ADD_BOOK_URL = "/greeting";
	
	private static final String TITLE = "title";
	private static final String ID = "id";
	private static final String AUTHORS = "authors";
	private static final String STATUS = "status";
	
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new BookController()).build();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	

	@Test
	@WithMockUser(username = "alex", roles = { "USER" })
	public void shouldReturnValidResponseBooks() throws Exception {

		//when 
		ResultActions resultActions = mockMvc.perform(get(BOOKS_URL)).andDo(print());
		
		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(view().name(ViewNames.BOOKS));

	}

	@Test
	@WithMockUser(username = "alex", roles = { "USER" })
	public void shouldReturnValidResponseForBookDetails() throws Exception {

		//when
		ResultActions resultActions = mockMvc.perform(get(BOOK_URL).param(ID, "1")).andDo(print());
				
		//then
			resultActions
				.andExpect(status().isOk())
				.andExpect(view().name(ViewNames.BOOK));

	}

	@Test
	@WithMockUser(username = "alex", roles = { "USER" })
	public void shouldCreateNewBook() throws Exception {
		
		//when
		ResultActions resultActions = mockMvc.perform(post(ADD_BOOK_URL)
				.param(TITLE, "Lord")
				.param(AUTHORS, "Tolkien")
				.param(STATUS, "FREE"))
				.andDo(print());
			
		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(view().name(ViewNames.BOOKS));
	}
	
	@Test
	@WithMockUser(username = "alex", roles = { "USER" })
	public void shouldFindBookByParameters() throws Exception {
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(FIND_BOOK_URL)
				.param(TITLE, "Third book")
				.param(AUTHORS, "Janusz Jankowski"))
				.andDo(print());
		
		//then
		resultActions
				.andExpect(status().isOk())
				.andExpect(view().name(ViewNames.FIND));
	}
	
	@Test
	@WithMockUser(username = "alex", roles = { "ADMIN" })
	public void shouldDeleteBook() throws Exception {

		// when
		ResultActions resultActions = mockMvc.perform(delete(DELETE_BOOK_URL).param(ID, "2"));

		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name(ViewNames.BOOKS));
	}
	
	@Test
	@WithMockUser(username = "alex", roles = { "USER" })
	public void shouldReturnValidViewForAddingBooks() throws Exception {

		// when
		ResultActions resultActions = mockMvc.perform(get(ADD_VIEW_URL));
		
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name(ViewNames.ADD_BOOK));
	}
	
	@Test
	@WithMockUser(username = "alex", roles = { "USER" })
	public void shouldReturnValidViewForFindingBooks() throws Exception {

		// when
		ResultActions resultActions = mockMvc.perform(get(FIND_BOOK_URL));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name(ViewNames.FIND));
	}

	
	//TODO: dlaczego w tescie zwraca status 200? a w aplikacji wyrzuca 403?!
	@Test
	@WithMockUser(username = "alex", roles = { "USER" })
	public void shouldReturnAccessDeniedError() throws Exception {

		// when
		ResultActions resultActions = mockMvc.perform(delete(DELETE_BOOK_URL).param(ID, "3"));

		// then
		resultActions.andExpect(status().is4xxClientError());
	}


}























