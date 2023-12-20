package com.enspd.books.admin.types;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@PostMapping("/types/save")
	public String saveType(Types types, RedirectAttributes ra) throws IOException {

		service.save(types);

		ra.addFlashAttribute("message", "Le type de livre a été enregistré avec succès.");
		return "redirect:/types";
	}

	@GetMapping("/types/edit/{id}")
	public String editType(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Types types = service.get(id);
			List<Filieres> listFilieres = filieresService.listAll();

			model.addAttribute("types", types);
			model.addAttribute("listFilieres", listFilieres);
			model.addAttribute("pageTitle", "Editer type de livre (ID: " + id + ")");

			return "types/type_form";
		} catch (TypesNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/types";
		}
	}

	@GetMapping("/types/delete/{id}")
	public String deleteType(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);

			redirectAttributes.addFlashAttribute("message",
					"Le type de livre ID " + id + " a été supprimé avec succès");
		} catch (TypesNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/types";
	}
}
