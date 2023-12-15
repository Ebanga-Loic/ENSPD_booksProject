package com.enspd.books.admin.filieres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilieresRestController {

	@Autowired
	private FilieresService service;

	@PostMapping("/filieres/check_unique")
	public String checkUnique(@Param("id") Integer id, @Param("name") String name, @Param("alias") String alias) {
		return service.checkUnique(id, name, alias);
	}
}
