package pl.jstk.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	public String bookById(@RequestParam("id") Long id, Model model) {
		
		BookTo bookTo = bookService.getOneById(id);
		model.addAttribute(ModelConstants.BOOK, bookTo);

		return ViewNames.BOOK;
	}

	@GetMapping(value = "/books/add")
	public String addBookDisplay(Model model) {
		
		model.addAttribute(ModelConstants.NEW_BOOK, new BookTo());
		
		return ViewNames.ADD_BOOK;
	}

	@PostMapping(value = "/greeting")
	public String createBook(@ModelAttribute BookTo newBook, Model model) {
		
		bookService.saveBook(newBook);

		return allBooks(model);
	}

	@GetMapping("/books/find")
    public String findBook(Model model) {
		
        model.addAttribute(ModelConstants.BOOK, new BookTo());
        
        return ViewNames.FIND;
    }
	
	 @PostMapping("/books/find")
	    public String find(@ModelAttribute("book") BookTo book, Model model) {
		 
	        List<BookTo> books = bookService.findBooksByParams(book);
	        model.addAttribute(ModelConstants.BOOKS, books);
	        
	        if (books.size() == 0) {
	            model.addAttribute(ModelConstants.NOT_FOUND, "No books were found.");
	        }

	        return ViewNames.FIND;
	    }

	@RolesAllowed("ROLE_ADMIN")
	@DeleteMapping(value = "/books/deleteBook")
	public String deleteBookButton2(@RequestParam("id") Long id, Model model) {
		
		bookService.deleteBook(id);
		model.addAttribute(ModelConstants.DELETED_BOOK, "Book was successfully deleted");
		
		return allBooks(model);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public String accessDenied(Model model) {
		
		model.addAttribute(ModelConstants.ERROR, "Access denied. You are not allowed to delete book");
		
		return ViewNames.ACCESS_DENIED;
	}

}
