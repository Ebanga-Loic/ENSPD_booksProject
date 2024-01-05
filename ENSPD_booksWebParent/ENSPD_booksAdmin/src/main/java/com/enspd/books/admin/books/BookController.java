package com.enspd.books.admin.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String saveBook(Book book, RedirectAttributes ra,
			@RequestParam(name = "detailNames", required = false) String[] detailNames,
			@RequestParam(name = "detailValues", required = false) String[] detailValues) {


		setBookDetails(detailNames, detailValues, book);
		bookService.save(book);

		ra.addFlashAttribute("message", "Le livre a été enregistré avec succès.");

		return "redirect:/books";
	}

	private void setBookDetails(String[] detailNames, String[] detailValues, Book book) {
		if (detailNames == null || detailNames.length == 0)
			return;

		for (int count = 0; count < detailNames.length; count++) {
			String name = detailNames[count];
			String value = detailValues[count];

			if (!name.isEmpty() && !value.isEmpty()) {
				book.addDetail(name, value);
			}
		}
	}

	@GetMapping("/books/{id}/enabled/{status}")
	public String updateBookEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		bookService.updateBookEnabledStatus(id, enabled);
		String status = enabled ? "activé" : "désactivé";
		String message = "Le livre ID " + id + " a été " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/books";
	}

	@GetMapping("/books/delete/{id}")
	public String deleteBook(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			bookService.delete(id);

			redirectAttributes.addFlashAttribute("message", "Le livre ID " + id + " a été supprimé avec succès.");
		} catch (BookNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/books";
	}
}
