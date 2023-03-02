package org.lessons.java.fotoalbum.repositories;

import java.util.List;

import org.lessons.java.fotoalbum.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
	// Custom Query
	public List<Photo> findByTitleLike(String keyword);
}