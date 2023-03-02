package org.lessons.java.fotoalbum.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Photo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Il campo Titolo deve essere compilato")
	@NotEmpty(message = "Il campo Titolo deve essere compilato")
	@Size(max = 30, message = "Inserire massimo 30 caratteri")
	@Column(nullable=false, length=30, unique = true)
	private String title;
	
	@NotNull(message = "Il campo Descrizione deve essere compilato")
	@NotEmpty(message = "Il campo Descrizione deve essere compilato")
	@Size(max = 300, message = "Inserire massimo 300 caratteri")
	@Column(nullable=false, length=300)
	private String description;
	
	@NotNull(message = "Il campo Url deve essere compilato")
	@NotEmpty(message = "Il campo Url deve essere compilato")
	@Size(max = 400, message = "Inserire massimo 400 caratteri")
	@Column(nullable=false, length=400, unique = true)
	private String url;
	
	@NotNull(message = "Il campo Tag deve essere compilato")
	@NotEmpty(message = "Il campo Tag deve essere compilato")
	@Size(max = 100, message = "Inserire massimo 100 caratteri")
	@Column(nullable=false, length=100)
	private String tag;
	
	@Column(nullable=false)
	private boolean visible;
	
	@ManyToMany
	private List<Category> categories;
	
	@OneToMany(mappedBy = "photo")
	private List<Comment> comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
