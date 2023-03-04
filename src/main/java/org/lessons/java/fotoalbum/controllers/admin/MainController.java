package org.lessons.java.fotoalbum.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
	
	@GetMapping()
	public String index() {
		return "guest/index";
	}
	
	@GetMapping("home")
	public String redirectToHome() {
		return "guest/index";
	}
	
	@GetMapping("photos/{id}")
	public String show() {
		return "guest/show";
	}
}