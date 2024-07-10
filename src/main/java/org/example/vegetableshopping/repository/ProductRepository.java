package org.example.vegetableshopping.repository;

import org.example.vegetableshopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
