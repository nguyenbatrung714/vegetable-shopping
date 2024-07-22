package org.example.vegetableshopping.repository;

import org.example.vegetableshopping.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
}
