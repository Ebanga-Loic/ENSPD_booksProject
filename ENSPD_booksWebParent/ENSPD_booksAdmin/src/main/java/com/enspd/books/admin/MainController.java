package com.enspd.books.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.enspd.books.admin.user.UserService;
import com.enspd.books.common.entity.User;

@Controller
public class MainController {

	@GetMapping("")
	public String viewHomePage(Model model) {

		return "index";
	}

	@GetMapping("/login")
	public String viewLoginPage() {

		return "login";
	}

}
