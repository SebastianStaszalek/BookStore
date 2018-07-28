package pl.jstk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@Controller
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public String allBooks(Model model) {
		List<BookTo> allBooks = bookService.findAllBooks();
		model.addAttribute("bookList", allBooks);
		
		return "books";
	}
	
}
