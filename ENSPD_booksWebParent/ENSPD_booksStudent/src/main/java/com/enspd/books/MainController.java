package com.enspd.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.filiere.FiliereService;

@Controller
public class MainController {

	@Autowired
	private FiliereService filiereService;

	@GetMapping("")
	public String viewHomePage(Model model) {
		List<Filieres> listFilieres = filiereService.listFilieres();
		model.addAttribute("listFilieres", listFilieres);

		return "index";
	}

}
