package org.lessons.java.fotoalbum.repositories;

import java.util.List;

import org.lessons.java.fotoalbum.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	public List<Comment> findAllByOrderByIdDesc();
}
