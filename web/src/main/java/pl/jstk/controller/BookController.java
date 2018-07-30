package pl.jstk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping(value = "/books/book")
	public String bookById(@RequestParam("id") Long id, Model model)	{
		BookTo bookTo = bookService.getOneById(id);
		model.addAttribute("book", bookTo);
		
		return ViewNames.BOOK;
	}
	
	@GetMapping(value = "/books/add")
	public String addBookDisplay(Model model) {
		model.addAttribute("newBook", new BookTo());
		return ViewNames.ADD_BOOK;
	}

	@PostMapping(value = "/greeting")
	public String createBook(@ModelAttribute BookTo newBook, Model model) {
		bookService.saveBook(newBook);
		
		return allBooks(model);
	}
	
	@GetMapping(value = "/books/deleteBook/book")
	public String deleteBook(@RequestParam("id") Long id) {
		bookService.deleteBook(id);
		
		return ViewNames.WELCOME;
	}
	
	@PostMapping(value = "/books/deleteBook")
	public String deleteBookButton2(@RequestParam("id") Long id, Model model) {
		bookService.deleteBook(id);
		model.addAttribute("deletedBook", "Book was successfully deleted");
		return allBooks(model);
	}
}









