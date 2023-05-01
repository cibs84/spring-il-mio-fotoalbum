package org.lessons.java.fotoalbum.controllers.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/token")
public class TokenController {
	
	
	@PostMapping
	public void token() {
		
	}
}
