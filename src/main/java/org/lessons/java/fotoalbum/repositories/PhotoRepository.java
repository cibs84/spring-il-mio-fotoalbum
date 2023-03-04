package org.lessons.java.fotoalbum.repositories;

import java.util.List;

import org.lessons.java.fotoalbum.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
	// Custom Query
	public List<Photo> findByTitleLike(String keyword);
	
//	@Query("SELECT p.*, cat.* FROM photo c "
//			+ "JOIN photo_categories pc ON pc.photo_id = p.id"
//			+ "WHERE c.title = ?1 AND t.bar = ?2")
//	public List<Photo> findAllByTitleAndCategory(String fooIn, String bar);
}