package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Page<User> findByUsernameContainsIgnoreCase(String username, Pageable pageable);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByUsernameAndEmail(String username, String email);
    User findUserByUsername(String username);
}
