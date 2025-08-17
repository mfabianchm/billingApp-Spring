package com.example.billingApp.service.impl;

import com.example.billingApp.entity.CategoryEntity;
import com.example.billingApp.io.Category.CategoryRequest;
import com.example.billingApp.io.Category.CategoryResponse;
import com.example.billingApp.repository.CategoryRepository;
import com.example.billingApp.service.CategoryService;
import com.example.billingApp.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    public CategoryResponse addCategory(CategoryRequest request, MultipartFile file) throws IOException {
        /*Use this to upload image to AWS
        String imgUrl = fileUploadService.uploadFile(file); */

        //Save image to uploads folder
        String imgUrl = saveImageLocally(file);

        CategoryEntity newCategory = convertToEntity(request);
        newCategory.setImgUrl(imgUrl);
        newCategory = categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll()
        .stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(String id) {
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(id)
            .orElseThrow(() -> new RuntimeException("Category not found: " + id));

        /*Use this method to delete image from AWS
        fileUploadService.deleteFile(existingCategory.getImgUrl());
        */

        //Method for deleting images locally (uploads folder)
        deleteImageLocally(existingCategory);
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

    private String saveImageLocally(MultipartFile file)  throws IOException {
        String fileName = UUID.randomUUID().toString()+"."+ StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        String imgUrl = "http://localhost:8080/api/v1/uploads/"+fileName;
        return imgUrl;
    }

    private void deleteImageLocally(CategoryEntity existingCategory) {
        String imgUrl = existingCategory.getImgUrl();
        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1);
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
