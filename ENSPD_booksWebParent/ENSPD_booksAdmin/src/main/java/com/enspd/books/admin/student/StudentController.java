package com.enspd.books.admin.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Student;
import com.enspd.books.common.exception.StudentNotFoundException;

@Controller
public class StudentController {

	@Autowired
	private StudentService service;

	@GetMapping("/students")
	public String listFirstPage(Model model) {
		return listByPage(model, 1, "firstName", "asc", null);
	}

	@GetMapping("/students/page/{pageNum}")
	public String listByPage(Model model, @PathVariable(name = "pageNum") int pageNum,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		Page<Student> page = service.listByPage(pageNum, sortField, sortDir, keyword);
		List<Student> listStudents = page.getContent();

		long startCount = (pageNum - 1) * StudentService.STUDENTS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);

		long endCount = startCount + StudentService.STUDENTS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("listStudents", listStudents);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("endCount", endCount);

		return "students/students";
	}

	@GetMapping("/students/{id}/enabled/{status}")
	public String updateStudentEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		service.updateStudentEnabledStatus(id, enabled);
		String status = enabled ? "activé" : "désactivé";
		String message = "L'étudiant ID " + id + " a été " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/students";
	}

	@GetMapping("/students/detail/{id}")
	public String viewStudent(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Student student = service.get(id);
			model.addAttribute("student", student);

			return "students/student_detail_modal";
		} catch (StudentNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/students";
		}
	}

	@GetMapping("/students/edit/{id}")
	public String editStudent(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Student student = service.get(id);
			List<Filieres> filieres = service.listAllFilieres();

			model.addAttribute("listFilieres", filieres);
			model.addAttribute("student", student);
			model.addAttribute("pageTitle", String.format("Editer L'étudiant (ID: %d)", id));

			return "students/student_form";

		} catch (StudentNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/students";
		}
	}

	@PostMapping("/students/save")
	public String saveStudent(Student student, Model model, RedirectAttributes ra) {
		service.save(student);
		ra.addFlashAttribute("message", "L'étudiant ID " + student.getId() + " a été mis à jour avec succès.");
		return "redirect:/students";
	}

	@GetMapping("/students/delete/{id}")
	public String deleteStudent(@PathVariable Integer id, RedirectAttributes ra) {
		try {
			service.delete(id);
			ra.addFlashAttribute("message", "L'étudiant ID " + id + " a été supprimé avec succès.");

		} catch (StudentNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/students";
	}

}
