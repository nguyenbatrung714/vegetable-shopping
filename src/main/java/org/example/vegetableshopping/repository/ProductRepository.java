package org.example.vegetableshopping.repository;

import org.example.vegetableshopping.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByProductNameContaining(String name, Pageable pageable0);

    Page<Product> findAllByPriceBetween(double minPrice, double maxPrice, Pageable pageable);
    Page<Product> getProductsByCategory_CategoryId(Integer categoryId, Pageable pageable);

}
