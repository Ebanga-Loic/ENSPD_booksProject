package com.enspd.books.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.enspd.books.common.entity.Book;
import com.enspd.books.common.entity.Filieres;
import com.enspd.books.filiere.FiliereService;

@Controller
public class BookController {
	@Autowired
	private BookService bookService;
	@Autowired
	private FiliereService filiereService;

	@GetMapping("/f/{filiere_alias}")
	public String viewFiliereFirstPage(@PathVariable("filiere_alias") String alias, Model model) {
		return viewFiliereByPage(alias, 1, model);
	}

	@GetMapping("/f/{filiere_alias}/page/{pageNum}")
	public String viewFiliereByPage(@PathVariable("category_alias") String alias, @PathVariable("pageNum") int pageNum,
			Model model) {
		Filieres filiere = filiereService.getFiliere(alias);
		if (filiere == null) {
			return "error/404";
		}

		Page<Book> pageBooks = bookService.listByFiliere(pageNum, filiere.getId());
		List<Book> listBooks = pageBooks.getContent();

		long startCount = (pageNum - 1) * BookService.BOOKS_PER_PAGE + 1;
		long endCount = startCount + BookService.BOOKS_PER_PAGE - 1;
		if (endCount > pageBooks.getTotalElements()) {
			endCount = pageBooks.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageBooks.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageBooks.getTotalElements());
		model.addAttribute("pageTitle", filiere.getName());
		model.addAttribute("listBooks", listBooks);
		model.addAttribute("filiere", filiere);

		return "books_by_filiere";
	}
}
