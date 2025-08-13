package com.example.billingApp.service;

import com.example.billingApp.io.CategoryRequest;
import com.example.billingApp.io.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest request);

    List<CategoryResponse>  getCategories();
}
