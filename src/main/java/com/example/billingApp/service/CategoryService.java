package com.example.billingApp.service;

import com.example.billingApp.io.Category.CategoryRequest;
import com.example.billingApp.io.Category.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest request, MultipartFile file) throws IOException;

    List<CategoryResponse>  getCategories();

    void deleteCategory(String id);
}
