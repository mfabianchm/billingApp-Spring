package com.example.billingApp.service.impl;

import com.example.billingApp.entity.CategoryEntity;
import com.example.billingApp.io.Category.CategoryRequest;
import com.example.billingApp.io.Category.CategoryResponse;
import com.example.billingApp.repository.CategoryRepository;
import com.example.billingApp.service.CategoryService;
import com.example.billingApp.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class CategoryServiceIml implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;

    @Override
    public CategoryResponse addCategory(CategoryRequest request, MultipartFile file) {
        String imgUrl = fileUploadService.uploadFile(file);
        CategoryEntity newCategory = convertToEntity(request);
        newCategory.setImgUrl(imgUrl);
        newCategory = categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll()
        .stream()
        .map(categoryEntity -> convertToResponse(categoryEntity))
        .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(String id) {
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(id)
            .orElseThrow(() -> new RuntimeException("Category not found: " + id));
        fileUploadService.deleteFile(existingCategory.getImgUrl());
        categoryRepository.delete(existingCategory);
    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
        return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        return CategoryResponse.builder()
                .categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imgUrl(newCategory.getImgUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .build();
    }
}
