package com.enspd.books.admin.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.enspd.books.admin.filieres.FilieresService;
import com.enspd.books.admin.types.TypesService;
import com.enspd.books.common.entity.Book;
import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Types;
import com.enspd.books.common.exception.BookNotFoundException;

@Controller
public class BookController {
	@Autowired
	private BookService bookService;

	@Autowired
	private TypesService typeService;

	@Autowired
	private FilieresService filiereService;

	@GetMapping("/books")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "name", "asc", null, 0);
	}

	@GetMapping("/books/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword,
			@Param("filiereId") Integer filiereId) {
		Page<Book> page = bookService.listByPage(pageNum, sortField, sortDir, keyword, filiereId);
		List<Book> listBooks = page.getContent();

		List<Filieres> listFilieres = filiereService.listAll();

		long startCount = (pageNum - 1) * BookService.BOOKS_PER_PAGE + 1;
		long endCount = startCount + BookService.BOOKS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		if (filiereId != null)
			model.addAttribute("filiereId", filiereId);

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("listBooks", listBooks);
		model.addAttribute("listFilieres", listFilieres);

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
			@RequestParam(name = "detailIDs", required = false) String[] detailIDs,
			@RequestParam(name = "detailNames", required = false) String[] detailNames,
			@RequestParam(name = "detailValues", required = false) String[] detailValues) {

		setBookDetails(detailIDs, detailNames, detailValues, book);

		bookService.save(book);

		ra.addFlashAttribute("message", "Le livre a été enregistré avec succès.");

		return "redirect:/books";
	}

	private void setBookDetails(String[] detailIDs, String[] detailNames, String[] detailValues, Book book) {
		if (detailNames == null || detailNames.length == 0)
			return;

		for (int count = 0; count < detailNames.length; count++) {
			String name = detailNames[count];
			String value = detailValues[count];
			Integer id = Integer.parseInt(detailIDs[count]);

			if (id != 0) {
				book.addDetail(id, name, value);
			} else if (!name.isEmpty() && !value.isEmpty()) {
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

	@GetMapping("/books/edit/{id}")
	public String editBook(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Book book = bookService.get(id);
			List<Types> listTypes = typeService.listAll();

			model.addAttribute("book", book);
			model.addAttribute("listTypes", listTypes);
			model.addAttribute("pageTitle", "Editer le Livre (ID: " + id + ")");

			return "books/book_form";

		} catch (BookNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return "redirect:/books";
		}
	}

	@GetMapping("/books/detail/{id}")
	public String viewBookDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Book book = bookService.get(id);
			model.addAttribute("book", book);

			return "books/book_detail_modal";

		} catch (BookNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return "redirect:/books";
		}
	}
}
