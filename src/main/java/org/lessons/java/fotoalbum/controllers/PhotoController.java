package org.lessons.java.fotoalbum.controllers;

import java.util.List;
import java.util.Optional;

import org.lessons.java.fotoalbum.models.Photo;
import org.lessons.java.fotoalbum.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/photos")
public class PhotoController {
	@Autowired
	private PhotoRepository photoRepository;
	
	@GetMapping()
	public String index(
			@RequestParam(name = "keyword", required = false) String keyword, 
			Model model) {	// GET /photos  OPPURE  /photos?keyword=xxx
		
		List<Photo> photoList;
		
		if (keyword!=null && !keyword.isEmpty()) {
			photoList = photoRepository.findByTitleLike(keyword + "%");
		} else {
			photoList = photoRepository.findAll(Sort.by("title"));
		}
		
		model.addAttribute("photos", photoList);
		return "photos/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		
		Optional<Photo> photo = photoRepository.findById(id);
		if (photo.isEmpty()) {
			return "redirect:/error";
		}
		Photo photo2 = photo.get();
		
		model.addAttribute("photo", photo2);
		
		return "photos/show";
	}
}
