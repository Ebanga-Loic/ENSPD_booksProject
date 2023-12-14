package com.enspd.books.admin.filieres;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/filieres/save")
	public String saveCategory(Filieres filiere, @RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		filiere.setImage(fileName);

		Filieres savedFiliere = service.save(filiere);
		String uploadDir = "../filiere-images/" + savedFiliere.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		ra.addFlashAttribute("message", "La filiere a été enregistrée avec succès.");
		return "redirect:/filieres";
	}
}
