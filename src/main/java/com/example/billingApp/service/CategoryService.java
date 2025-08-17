package com.example.billingApp.service;

import com.example.billingApp.io.Category.CategoryRequest;
import com.example.billingApp.io.Category.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest request, MultipartFile file);

    List<CategoryResponse>  getCategories();

    void deleteCategory(String id);
}
