package com.enspd.books.admin.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.enspd.books.common.entity.Book;

@Controller
public class BookController {
	@Autowired
	private BookService productService;

	@GetMapping("/books")
	public String listAll(Model model) {
		List<Book> listBooks = productService.listAll();

		model.addAttribute("listBooks", listBooks);

		return "books/books";
	}
}
