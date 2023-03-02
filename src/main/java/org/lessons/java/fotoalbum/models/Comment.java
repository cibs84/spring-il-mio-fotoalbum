package org.lessons.java.fotoalbum.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Il campo Autore deve essere compilato")
	@NotEmpty(message = "Il campo Autore deve essere compilato")
	@Size(max = 30, message = "Inserire massimo 30 caratteri")
	@Column(nullable=false, length=30)
	private String author;
	
	@NotNull(message = "Il campo Commento deve essere compilato")
	@NotEmpty(message = "Il campo Commento deve essere compilato")
	@Size(max = 400, message = "Inserire massimo 400 caratteri")
	@Column(nullable=false, length=400)
	private String content;
	
	@ManyToOne
	private Photo photo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
}
