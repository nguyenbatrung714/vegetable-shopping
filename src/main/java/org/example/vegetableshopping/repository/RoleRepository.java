package org.example.vegetableshopping.repository;

import org.example.vegetableshopping.entity.Role;
import org.example.vegetableshopping.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleType roleType);

}
