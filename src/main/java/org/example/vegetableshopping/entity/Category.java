package org.example.vegetableshopping.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "category")
@Setter
@Getter
public class Category extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "image_path")
    private String imagePath;

    @JsonManagedReference
    @OneToMany(mappedBy = "category")
    private Set<Blog> blogs;

    @JsonManagedReference
    @OneToMany(mappedBy = "category")
    private Set<Product> products;
}
