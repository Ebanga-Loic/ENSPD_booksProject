package com.enspd.books.admin.types;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.enspd.books.common.entity.Types;

@Controller
public class TypesController {

	@Autowired
	private TypesService service;

	@GetMapping("/types")
	public String listAll(Model model) {
		List<Types> listTypes = service.listAll();
		model.addAttribute("listTypes", listTypes);

		return "types/types";
	}
}
