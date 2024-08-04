package org.example.vegetableshopping.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.converter.CategoryConverter;
import org.example.vegetableshopping.dto.request.CategoryRequest;
import org.example.vegetableshopping.dto.response.CategoryResponse;
import org.example.vegetableshopping.entity.Category;
import org.example.vegetableshopping.exception.ResourceNotFoundException;
import org.example.vegetableshopping.exception.SystemErrorException;
import org.example.vegetableshopping.repository.CategoryRepository;
import org.example.vegetableshopping.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORY = "Category";

    private final CategoryRepository categoryRepo;
    private final S3Service s3Service;

    @Override
    public Page<CategoryResponse> getCategories(int page, int size, String sort) {
        Sort sorting = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            sorting = sort.equalsIgnoreCase("asc")
                    ? Sort.by(Sort.Direction.ASC, "categoryName")
                    : Sort.by(Sort.Direction.DESC, "categoryName");
        }

        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<Category> categories = categoryRepo.findAll(pageable);
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException(CATEGORY, "list", "null");
        }

        return categories.map(
                category -> {
                    CategoryResponse categoryResponse = CategoryConverter.toCategoryResponse(category);
                    categoryResponse.setImagePath(s3Service.generatePresignedUrl(category.getImagePath()));
                    return categoryResponse;
                }
        );
    }

    @Transactional
    @Override
    public CategoryResponse saveCategory(CategoryRequest categoryRequest,
                                         MultipartFile multipartFile) {

        // Upload image to s3
        String s3ImagePath;
        try {
            File fileConvert = FileUtil.convertMultiPartToFile(multipartFile);
            s3ImagePath = s3Service.uploadFileToS3(fileConvert);
            Files.deleteIfExists(fileConvert.toPath());
        } catch (IOException | SystemErrorException e) {
            throw new SystemErrorException("Error uploading to s3");
        }

        categoryRequest.setImagePath(s3ImagePath);
        Category category = CategoryConverter.toCategory(categoryRequest);
        categoryRepo.save(category);

        return CategoryConverter.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Integer id, CategoryRequest categoryRequest, MultipartFile multipartFile) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, "id", "" + id));

        String s3ImagePath;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                File fileConvert = FileUtil.convertMultiPartToFile(multipartFile);
                s3ImagePath = s3Service.uploadFileToS3(fileConvert);
                Files.deleteIfExists(fileConvert.toPath());
                categoryRequest.setImagePath(s3ImagePath);
            } catch (IOException | SystemErrorException e) {
                throw new SystemErrorException("Error uploading to s3");
            }
        }

        CategoryConverter.toCategory(category, categoryRequest);
        categoryRepo.save(category);

        return CategoryConverter.toCategoryResponse(category);
    }

    @Override
    public void removeCategory(Integer categoryId) {
        if (categoryRepo.existsById(categoryId)) {
            categoryRepo.deleteById(categoryId);
        } else {
            throw new ResourceNotFoundException(CATEGORY, "id", "" + categoryId);
        }
    }

    @Override
    public CategoryResponse getCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, "id", "" + categoryId));
        category.setImagePath(s3Service.generatePresignedUrl(category.getImagePath()));

        return CategoryConverter.toCategoryResponse(category);
    }

}
