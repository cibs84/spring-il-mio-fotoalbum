package org.lessons.java.fotoalbum.controllers.api;

import java.util.List;
import java.util.Optional;

import org.lessons.java.fotoalbum.models.Photo;
import org.lessons.java.fotoalbum.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/photos")
public class PhotoRestController {
	
	@Autowired
	PhotoRepository photoRepository;
	
	@GetMapping
	public ResponseEntity<List<Photo>> index(@RequestParam(name = "nameKeyword", required = false) String nameKeyword,
											 @RequestParam(name = "tagKeyword", required = false) String tagKeyword) {
		
		List<Photo> photoList;
		
		if (tagKeyword!=null && !tagKeyword.isEmpty()) {
			photoList = photoRepository.myFindByTitleAndTagLike(nameKeyword+'%', tagKeyword);
		} else {
			photoList = photoRepository.myFindByTitleLike(nameKeyword+'%');
		}
		
		
		
		if (photoList.size() == 0) {
			return new ResponseEntity<List<Photo>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Photo>>(photoList, HttpStatus.OK);
		}
	}
	
	@GetMapping("{id}")	// SHOW
	public ResponseEntity<Photo> show(@PathVariable("id") Long id) {
		Optional<Photo> res = photoRepository.findById(id);
		if (res.isPresent()) {
			return new ResponseEntity<Photo>(res.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/create")	// CREATE
	public ResponseEntity<Photo> create(@Valid @RequestBody Photo photo) {
		Photo newPhoto = photoRepository.save(photo);
		return new ResponseEntity<Photo>(newPhoto, HttpStatus.CREATED); 
	}
	
	@PutMapping("/update/{id}")	// UPDATE
	public ResponseEntity<Photo> update(@Valid @RequestBody Photo photo,
			@PathVariable("id") Long id) {
		
		Optional<Photo> photoFromDb = photoRepository.findById(id);
		
		if (photoFromDb.isPresent()) {
			photoFromDb.get().setTitle(photo.getTitle());
	        photoFromDb.get().setDescription(photo.getDescription());
	        photoFromDb.get().setUrl(photo.getUrl());
	        photoFromDb.get().setTag(photo.getTag());
	        photoFromDb.get().setVisible(photo.isVisible());
	        Photo modifiedPhoto = photoRepository.save(photoFromDb.get());
	        
	        return new ResponseEntity<Photo>(modifiedPhoto, HttpStatus.OK);
		} else {
			return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")	// DELETE
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Optional<Photo> photoFromDb = photoRepository.findById(id);
		
		if (photoFromDb.isPresent()) {
			photoRepository.deleteById(id);
			return new ResponseEntity<String>("Photo deleted", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>("Photo to delete is not found", HttpStatus.NOT_FOUND) ;
		}
	}
}
