package org.lessons.java.fotoalbum.controllers.admin;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.lessons.java.fotoalbum.models.Category;
import org.lessons.java.fotoalbum.models.Photo;
import org.lessons.java.fotoalbum.repositories.CategoryRepository;
import org.lessons.java.fotoalbum.repositories.PhotoRepository;
import org.lessons.java.fotoalbum.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private PhotoService photoService;
	
	// INDEX
	@GetMapping()
	public String index(
			@RequestParam(name = "nameKeyword", required = false) String nameKeyword,
			@RequestParam(name = "tagKeyword", required = false) String tagKeyword,
			Model model) {
		
		List<Photo> photoList;
		System.out.println("tagKeyword: " + tagKeyword);
		if (tagKeyword!=null && !tagKeyword.isEmpty()) {
			photoList = photoRepository.myFindByTitleAndTagLike(nameKeyword+'%', tagKeyword);
		} else if (nameKeyword!=null && !nameKeyword.isEmpty()) {
			photoList = photoRepository.myFindByTitleLike(nameKeyword+'%');
		} else {
			photoList = photoRepository.findAll();
		}
		
		// Create a tagSet for tagSelect in view
		List<Photo> photoList2 = photoRepository.findAll();
		HashSet<String> tagSet = new HashSet<String>();
		
		for (Photo photo : photoList2) {
			tagSet.add(photo.getTag());
		}
		
		// Prepares categories to string
		for (Photo photo : photoList) {
			photoService.setPhotoCategoriesToString(photo);
		}
		
		model.addAttribute("photos", photoList);
		model.addAttribute("tagSet", tagSet);
		return "admin/photos/index";
	}
	
	// SHOW
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		
		Optional<Photo> photo = photoRepository.findById(id);
		if (photo.isEmpty()) {
			return "redirect:/error";
		}
		photoService.setPhotoCategoriesToString(photo.get());
		model.addAttribute("photo", photo.get());
		
		return "admin/photos/show";
	}
	
	// CREATE
	@GetMapping("/create")
	public String create(Model model) {
		Photo photo = new Photo();
		model.addAttribute("photo", photo);
		
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		photo.setUrl("https://socialistmodernism.com/wp-content/uploads/2017/07/placeholder-image.png");
		
		return "admin/photos/create";
	}
	
	// STORE
	@PostMapping("/store")
	public String store(
			@Valid @ModelAttribute("photo") Photo formPhoto,
			BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			List<Category> categories = categoryRepository.findAll();
			model.addAttribute("categories", categories);
			return "admin/photos/create";
		}
		
		photoRepository.save(formPhoto);
		
		return "redirect:/admin/photos";
	}
	
	// EDIT
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Photo photo = photoRepository.getReferenceById(id);
		model.addAttribute("photo", photo);
		
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		return "admin/photos/edit";
	}
	
	// UPDATE
	@PostMapping("/update/{id}")
	public String update(
			@Valid @ModelAttribute Photo formPhoto,
			BindingResult bindingResult,
			Model model) {
		
		if (bindingResult.hasErrors()) {
			List<Category> categories = categoryRepository.findAll();
			model.addAttribute("categories", categories);
			return "admin/photos/edit";
		}
		
		photoRepository.save(formPhoto);
		
		return "redirect:/admin/photos/" + formPhoto.getId();
	}
	
	// DELETE
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		
		photoRepository.deleteById(id);
		
		return "redirect:/admin/photos";
	}
}
