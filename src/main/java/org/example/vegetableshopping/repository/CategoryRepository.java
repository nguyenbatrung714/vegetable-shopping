package org.example.vegetableshopping.repository;

import org.example.vegetableshopping.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);

}
