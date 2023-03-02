package org.lessons.java.fotoalbum.repositories;

import org.lessons.java.fotoalbum.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
