package com.enspd.books.admin.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.enspd.books.admin.types.TypesService;
import com.enspd.books.common.entity.Book;
import com.enspd.books.common.entity.Types;

@Controller
public class BookController {
	@Autowired
	private BookService bookService;

	@Autowired
	private TypesService typeService;

	@GetMapping("/books")
	public String listAll(Model model) {
		List<Book> listBooks = bookService.listAll();

		model.addAttribute("listBooks", listBooks);

		return "books/books";
	}

	@GetMapping("/books/new")
	public String newBook(Model model) {
		List<Types> listTypes = typeService.listAll();

		Book book = new Book();
		book.setEnabled(true);
		book.setInStock(true);

		model.addAttribute("book", book);
		model.addAttribute("listTypes", listTypes);
		model.addAttribute("pageTitle", "Créez un nouveau livre");

		return "books/book_form";
	}

	@PostMapping("/books/save")
	public String saveBook(Book book) {
		System.out.println("Book Name: " + book.getName());
		System.out.println("Type ID: " + book.getTypes().getId());
		System.out.println("Filiere ID: " + book.getFilieres().getId());

		return "redirect:/books";
	}
}
