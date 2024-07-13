package org.example.vegetableshopping.repository;

import org.example.vegetableshopping.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<User> findAllByUsernameContaining(String username, Pageable pageable);

}
