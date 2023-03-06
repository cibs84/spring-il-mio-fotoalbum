package org.lessons.java.fotoalbum.repositories;

import java.util.List;

import org.lessons.java.fotoalbum.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
	// Custom Query
	public List<Photo> findByTitleLike(String keyword);
	
	@Query(value = "select * from photo as p where p.title like :titleKeyword and p.tag = :tagKeyword and p.visible = 1", 
		   nativeQuery = true)
	public List<Photo> myFindByTitleAndTagLike(@Param(value = "titleKeyword") String keyword, 
											   @Param(value = "tagKeyword") String tagKeyword);

	@Query(value = "select * from photo as p where p.title like :keyword and p.visible = 1", 
			nativeQuery = true)
	public List<Photo> myFindByTitleLike(@Param(value = "keyword") String keyword);
}