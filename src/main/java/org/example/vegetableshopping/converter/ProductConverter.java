package org.example.vegetableshopping.converter;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.dto.request.ProductRequest;
import org.example.vegetableshopping.dto.response.ProductResponse;
import org.example.vegetableshopping.entity.Category;
import org.example.vegetableshopping.entity.Product;

@RequiredArgsConstructor
public class ProductConverter {

    public static Product toProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setImagePath(product.getImagePath());
        product.setWeight(productRequest.getWeight());

        Category category = new Category();
        category.setCategoryId(productRequest.getCategoryId());
        product.setCategory(category);

        return product;
    }

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .weight(product.getWeight())
                .imagePath(product.getImagePath())
                .description(product.getDescription())
                .categoryId(product.getCategory().getCategoryId())
                .build();
    }

    public static void toProduct(Product product, ProductRequest productRequest) {
        if (productRequest.getProductName() != null) {
            product.setProductName(productRequest.getProductName());
        }
        if (productRequest.getQuantity() != null) {
            product.setQuantity(productRequest.getQuantity());
        }
        if (productRequest.getPrice() != null) {
            product.setPrice(productRequest.getPrice());
        }
        if (productRequest.getWeight() != null) {
            product.setWeight(productRequest.getWeight());
        }
        if (productRequest.getImagePath() != null) {
            product.setImagePath(productRequest.getImagePath());
        }
        if (productRequest.getDescription() != null) {
            product.setDescription(productRequest.getDescription());
        }
    }

}
