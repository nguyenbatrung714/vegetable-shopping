package org.example.vegetableshopping.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.converter.ProductConverter;
import org.example.vegetableshopping.dto.request.ProductRequest;
import org.example.vegetableshopping.dto.response.ProductResponse;
import org.example.vegetableshopping.entity.Product;
import org.example.vegetableshopping.exception.ResourceNotFoundException;
import org.example.vegetableshopping.exception.SystemErrorException;
import org.example.vegetableshopping.repository.CategoryRepository;
import org.example.vegetableshopping.repository.ProductRepository;
import org.example.vegetableshopping.service.ProductService;
import org.example.vegetableshopping.service.S3Service;
import org.example.vegetableshopping.utils.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;

    @Override
    public Page<ProductResponse> getProducts(int page, int size, String sort) {
        Sort sorting = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            sorting = sort.equalsIgnoreCase("asc")
                    ? Sort.by(Sort.Direction.ASC, "price")
                    : Sort.by(Sort.Direction.DESC, "price");
        }

        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<Product> products = productRepository.findAll(pageable);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product", "list", "empty");
        }

        return products.map(
                product -> {
                    product.setImagePath(s3Service.generatePresignedUrl(product.getImagePath()));
                    return ProductConverter.toProductResponse(product);
                }
        );
    }

    @Override
    public ProductResponse saveProduct(ProductRequest productRequest, MultipartFile multipartFile) {
        categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product", "categoryId", "" + productRequest.getCategoryId())
                );

        String s3ImagePath;
        try {
            File fileConverter = FileUtil.convertMultiPartToFile(multipartFile);
            s3ImagePath = s3Service.uploadFileToS3(fileConverter);
            Files.deleteIfExists(fileConverter.toPath());
        } catch (IOException e) {
            throw new SystemErrorException("Error uploading to s3");
        }

        productRequest.setImagePath(s3ImagePath);
        Product product = ProductConverter.toProduct(productRequest);
        productRepository.save(product);

        return null;
    }

    @Override
    public ProductResponse updateProduct(Integer id,
                                         ProductRequest productRequest,
                                         MultipartFile multipartFile) {
        if (productRequest.getCategoryId() != null) {
            categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Product", "categoryId", "" + productRequest.getCategoryId())
                    );
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", "" + id));

        String s3ImagePath;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                File fileConverter = FileUtil.convertMultiPartToFile(multipartFile);
                s3ImagePath = s3Service.uploadFileToS3(fileConverter);
                Files.deleteIfExists(fileConverter.toPath());
                productRequest.setImagePath(s3ImagePath);
            } catch (IOException e) {
                throw new SystemErrorException("Error uploading to s3");
            }
        }

        ProductConverter.toProduct(product, productRequest);
        productRepository.save(product);

        return ProductConverter.toProductResponse(product);
    }

    @Override
    public void removeProduct(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product", "id", "" + id);
        }
    }

    @Override
    public ProductResponse getProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", "" + id));
        product.setImagePath(s3Service.generatePresignedUrl(product.getImagePath()));

        return ProductConverter.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> findByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByProductNameContaining(name, pageable);

        return products.map(
                product -> {
                    product.setImagePath(s3Service.generatePresignedUrl(product.getImagePath()));
                    return ProductConverter.toProductResponse(product);
                }
        );
    }

    @Override
    public Page<ProductResponse> findByPrice(double minPrice, double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByPriceBetween(minPrice, maxPrice, pageable);

        return products.map(
                product -> {
                    product.setImagePath(s3Service.generatePresignedUrl(product.getImagePath()));
                    return ProductConverter.toProductResponse(product);
                }
        );
    }

    @Override
    public Page<ProductResponse> getProductByCategory(Integer categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.getProductsByCategory_CategoryId(categoryId, pageable);

        return products.map(
                product -> {
                    product.setImagePath(s3Service.generatePresignedUrl(product.getImagePath()));
                    return ProductConverter.toProductResponse(product);
                }
        );
    }

    public int updateQuantity(Integer id, int quantity) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setQuantity(quantity);
        return quantity;
    }

}
