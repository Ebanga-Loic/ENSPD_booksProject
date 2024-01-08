package com.enspd.books.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Student;

@Controller
public class StudentController {
	@Autowired
	private StudentService service;

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		List<Filieres> listFilieres = service.listAllFilieres();

		model.addAttribute("listFilieres", listFilieres);
		model.addAttribute("pageTitle", "Inscription de l'étudiant");
		model.addAttribute("student", new Student());

		return "register/register_form";
	}
}
