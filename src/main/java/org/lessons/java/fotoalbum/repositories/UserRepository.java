package org.lessons.java.fotoalbum.repositories;

import java.util.Optional;

import org.lessons.java.fotoalbum.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
