package org.lessons.java.fotoalbum.controllers;

import java.util.List;

import org.lessons.java.fotoalbum.models.Category;
import org.lessons.java.fotoalbum.repositories.CategoryRepository;
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

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping()
	public String index(Model model) {	// GET /pizze  OPPURE  /pizze?keyword=xxx
		
		List<Category> categories;
		
		categories = categoryRepository.findAll(Sort.by("name"));
		
		model.addAttribute("categories", categories);
		return "admin/categories/index";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		
		return "admin/categories/create";
	}
	
	@PostMapping("/store")
	public String store(
			@Valid @ModelAttribute("category") Category formCategory,
			BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "admin/categories/edit";
		}
		
		categoryRepository.save(formCategory);
		
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Category category = categoryRepository.getReferenceById(id);
		model.addAttribute("category", category);
		
		return "admin/categories/edit";
	}
	
	@PostMapping("/update/{id}")
	public String update(
//			@Valid @ModelAttribute Category formCategory,
//			BindingResult bindingResult,
//			Model model) {
//		
//		if (bindingResult.hasErrors()) {
//			return "admin/categories/edit";
//		}
		@ModelAttribute Category formCategory,
		Model model) {
		
		categoryRepository.save(formCategory);
		
		return "redirect:/admin/categories";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		
		categoryRepository.deleteById(id);
		
		return "redirect:/admin/categories";
	}
}
