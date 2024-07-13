package org.example.vegetableshopping.service;

import org.example.vegetableshopping.dto.request.ProductRequest;
import org.example.vegetableshopping.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    Page<ProductResponse> getProducts(int page, int size, String sort);

    ProductResponse saveProduct(ProductRequest productRequest, MultipartFile multipartFile);

    ProductResponse updateProduct(Integer id, ProductRequest productRequest, MultipartFile multipartFile);

    void removeProduct(Integer id);

    ProductResponse getProduct(Integer id);

    Page<ProductResponse> findByName(String name, int page, int size);

    Page<ProductResponse> findByPrice(double minPrice, double maxPrice, int page, int size);

}
