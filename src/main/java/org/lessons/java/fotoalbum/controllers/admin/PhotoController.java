package org.lessons.java.fotoalbum.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.lessons.java.fotoalbum.models.Category;
import org.lessons.java.fotoalbum.models.Photo;
import org.lessons.java.fotoalbum.repositories.CategoryRepository;
import org.lessons.java.fotoalbum.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/photos")
public class PhotoController {
	@Autowired
	private PhotoRepository photoRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
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
		return "admin/photos/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		
		Optional<Photo> photo = photoRepository.findById(id);
		if (photo.isEmpty()) {
			return "redirect:/error";
		}
		Photo photo2 = photo.get();
		
		model.addAttribute("photo", photo2);
		
		return "admin/photos/show";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		Photo photo = new Photo();
		model.addAttribute("photo", photo);
		
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		photo.setUrl("https://socialistmodernism.com/wp-content/uploads/2017/07/placeholder-image.png");
		
		return "admin/photos/create";
	}
	
	@PostMapping("/store")
	public String store(
			@Valid @ModelAttribute("photo") Photo formPhoto,
			BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "admin/photos/create";
		}
		
		photoRepository.save(formPhoto);
		
		return "redirect:/admin/photos";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Photo photo = photoRepository.getReferenceById(id);
		model.addAttribute("photo", photo);
		
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		return "admin/photos/edit";
	}
	
	@PostMapping("/update/{id}")
	public String update(
			@Valid @ModelAttribute Photo formPhoto,
			BindingResult bindingResult,
			Model model) {
		
		if (bindingResult.hasErrors()) {
			return "admin/photos/edit";
		}
		
		photoRepository.save(formPhoto);
		
		return "redirect:/admin/photos/" + formPhoto.getId();
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		
		photoRepository.deleteById(id);
		
		return "redirect:/admin/photos";
	}
}
