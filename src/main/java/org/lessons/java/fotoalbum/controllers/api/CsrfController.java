package org.lessons.java.fotoalbum.controllers.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/csrf")
public class CsrfController {
	
	
	@GetMapping
	public ResponseEntity<CsrfToken> csrf(CsrfToken csrfToken) {
		System.out.println("******************");
		System.out.println("******************");
		System.out.println("csrfToken: " + csrfToken);
		System.out.println("******************");
		System.out.println("******************");
		return new ResponseEntity<CsrfToken>(csrfToken, HttpStatus.OK);
	}
}
