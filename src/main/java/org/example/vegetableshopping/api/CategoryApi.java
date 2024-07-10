package org.example.vegetableshopping.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.dto.request.CategoryRequest;
import org.example.vegetableshopping.dto.response.CategoryResponse;
import org.example.vegetableshopping.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryApi {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getCategories(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "size", defaultValue = "15") int size,
                                                                @RequestParam(value = "sort", required = false) String sort) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.getCategories(page, size, sort));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@Valid @ModelAttribute CategoryRequest categoryRequest,
                                                         @RequestParam("file") MultipartFile multipartFile) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.saveCategory(categoryRequest, multipartFile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Integer id,
                                                           @ModelAttribute CategoryRequest categoryRequest,
                                                           @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.updateCategory(id, categoryRequest, multipartFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer id) {
        categoryService.removeCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.getCategory(id));
    }

}
