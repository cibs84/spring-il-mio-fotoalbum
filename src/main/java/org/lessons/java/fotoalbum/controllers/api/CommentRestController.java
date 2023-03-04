package org.lessons.java.fotoalbum.controllers.api;

import java.util.List;
import java.util.Optional;

import org.lessons.java.fotoalbum.models.Comment;
import org.lessons.java.fotoalbum.models.Photo;
import org.lessons.java.fotoalbum.repositories.CommentRepository;
import org.lessons.java.fotoalbum.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/comments")
public class CommentRestController {
	
	@Autowired
	CommentRepository commentRepository;

	@Autowired
	PhotoRepository photoRepository;
	
	@GetMapping
	public ResponseEntity<List<Comment>> index() {
		
		List<Comment> commentList;
		
			commentList = commentRepository.findAll();
		
		if (commentList.size() == 0) {
			return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Comment>>(commentList, HttpStatus.OK);
		}
	}
	
	// Return Photo instance with updated comment list 
	@PostMapping("/photos/{photoId}/create")	// CREATE
	public ResponseEntity<Comment> create(@Valid @RequestBody Comment comment,
			@PathVariable(name = "photoId") Long photoId) {
		
		Optional<Photo> photoFromDb = photoRepository.findById(photoId);
		
		if (photoFromDb.isPresent()) {
			// Associo la photo al commento passato tramite ajax
			comment.setPhoto(photoFromDb.get());
			// Salvo nel db tramite il commentRepository, 
			// il commento passato e associato alla photo di cui si è passato il photoId
	        Comment newComment = commentRepository.save(comment);
			
	        return new ResponseEntity<Comment>(newComment, HttpStatus.OK);
		} else {
			return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
		}
	}
}
