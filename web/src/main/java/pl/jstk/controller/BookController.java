package pl.jstk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@Controller
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping(value = "/books")
	public String allBooks(Model model) {
		List<BookTo> allBooks = bookService.findAllBooks();
		model.addAttribute(ModelConstants.BOOK_LIST, allBooks);
		
		return ViewNames.BOOKS;
	}
	
//	@GetMapping(value = "/books/{book.id}")
//	public String book(@PathVariable("book.id") String id, Model model)	{
//		
//		return ViewNames.BOOK;
//	}
}
