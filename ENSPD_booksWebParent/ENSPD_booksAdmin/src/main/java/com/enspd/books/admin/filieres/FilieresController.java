package com.enspd.books.admin.filieres;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.enspd.books.common.entity.Filieres;

@Controller
public class FilieresController {
	@Autowired
	private FilieresService service;

	@GetMapping("/filieres")
	public String listAll(Model model) {
		List<Filieres> listFilieres = service.listAll();
		model.addAttribute("listFilieres", listFilieres);

		return "filieres/filieres";
	}

	@GetMapping("/filieres/new")
	public String newFilieres(Model model) {
		model.addAttribute("filieres", new Filieres());
		model.addAttribute("pageTitle", "Créer une nouvelle filieres");

		return "filieres/filiere_form";
	}
}
