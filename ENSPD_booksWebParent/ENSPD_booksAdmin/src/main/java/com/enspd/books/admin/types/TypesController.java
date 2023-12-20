package com.enspd.books.admin.types;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.enspd.books.admin.filieres.FilieresService;
import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Types;

@Controller
public class TypesController {

	@Autowired
	private TypesService service;

	@Autowired
	private FilieresService filieresService;

	@GetMapping("/types")
	public String listAll(Model model) {
		List<Types> listTypes = service.listAll();
		model.addAttribute("listTypes", listTypes);

		return "types/types";
	}

	@GetMapping("/types/new")
	public String newBrand(Model model) {
		List<Filieres> listFilieres = filieresService.listAll();

		model.addAttribute("listFilieres", listFilieres);
		model.addAttribute("types", new Types());
		model.addAttribute("pageTitle", "Créer un nouveau type de livre");

		return "types/type_form";
	}
}
