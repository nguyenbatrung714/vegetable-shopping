package org.example.vegetableshopping.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.dto.request.ProductRequest;
import org.example.vegetableshopping.dto.response.ProductResponse;
import org.example.vegetableshopping.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductApi {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProduct(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "15") int size,
            @RequestParam(value = "sort", required = false) String sort) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getProducts(page, size, sort));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> saveProduct(@Valid @ModelAttribute ProductRequest productRequest,
                                                       @RequestParam(value = "file") MultipartFile multipartFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.saveProduct(productRequest, multipartFile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Integer id,
                                                         @ModelAttribute ProductRequest productRequest,
                                                         @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.updateProduct(id, productRequest, multipartFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("id") Integer id) {
        productService.removeProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getProduct(id));
    }

    @GetMapping("/search/by-price")
    public ResponseEntity<Page<ProductResponse>> findByPrice(@RequestParam(required = false) Double minPrice,
                                                             @RequestParam(required = false) Double maxPrice,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "15") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findByPrice(minPrice, maxPrice, page, size));
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<Page<ProductResponse>> findByName(@RequestParam(required = false) String productName,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "15") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findByName(productName, page, size));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Page<ProductResponse>> getProductByCategory(@PathVariable Integer id,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "4") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getProductByCategory(id, page, size));
    }

}
