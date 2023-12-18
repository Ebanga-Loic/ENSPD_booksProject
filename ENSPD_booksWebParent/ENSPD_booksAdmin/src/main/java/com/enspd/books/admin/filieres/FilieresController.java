package com.enspd.books.admin.filieres;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.enspd.books.admin.FileUploadUtil;
import com.enspd.books.common.entity.Filieres;

@Controller
public class FilieresController {
	@Autowired
	private FilieresService service;

	@GetMapping("/filieres")
	public String listAll(@Param("sortDir") String sortDir, Model model) {

		if (sortDir == null || sortDir.isEmpty()) {
			sortDir = "asc";
		}

		List<Filieres> listFilieres = service.listAll(sortDir);
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		model.addAttribute("listFilieres", listFilieres);
		model.addAttribute("reverseSortDir", reverseSortDir);

		return "filieres/filieres";
	}

	@GetMapping("/filieres/new")
	public String newFilieres(Model model) {
		model.addAttribute("filieres", new Filieres());
		model.addAttribute("pageTitle", "Créer une nouvelle filieres");

		return "filieres/filiere_form";
	}

	@PostMapping("/filieres/save")
	public String saveFiliere(Filieres filiere, RedirectAttributes ra) throws IOException {

		Filieres savedFiliere = service.save(filiere);

		service.save(savedFiliere);

		ra.addFlashAttribute("message", "La filiere a été enregistrée avec succès.");
		return "redirect:/filieres";
	}

	@GetMapping("/filieres/edit/{id}")
	public String editFiliere(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Filieres filieres = service.get(id);

			model.addAttribute("filieres", filieres);
			model.addAttribute("pageTitle", "Editer la filiere (ID: " + id + ")");

			return "filieres/filiere_form";
		} catch (FiliereNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/filieres";
		}
	}

	@GetMapping("/filieres/{id}/enabled/{status}")
	public String updateFilieresEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		service.updateFiliereEnabledStatus(id, enabled);
		String status = enabled ? "activée" : "désactivée";
		String message = "La filiere ID " + id + " a été " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/filieres";
	}

	@GetMapping("/filieres/delete/{id}")
	public String deleteFilieres(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			String filiereDir = "../filiere-images/" + id;
			FileUploadUtil.removeDir(filiereDir);

			redirectAttributes.addFlashAttribute("message", "La filière ID " + id + " a été supprimée avec succès");
		} catch (FiliereNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/filieres";
	}
}
