package org.lessons.java.fotoalbum.controllers.api;

import java.util.List;

import org.lessons.java.fotoalbum.models.Category;
import org.lessons.java.fotoalbum.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoryRestController {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping
	public ResponseEntity<List<Category>> index() {
		
		List<Category> categoryList;
		
		categoryList = categoryRepository.findAll(Sort.by("name"));
		
		if (categoryList.size() == 0) {
			return new ResponseEntity<List<Category>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
		}
	}
}
