package com.enspd.books.admin.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentRestController {
	@Autowired
	private StudentService service;

	@PostMapping("/students/check_unique")
	public String checkUnique(@Param("id") Integer id, @Param("email") String email,
			@Param("matricule") String matricule) {
		return service.checkUnique(id, email, matricule);
	}
}
