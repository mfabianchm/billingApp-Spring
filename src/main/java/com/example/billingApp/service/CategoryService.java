package com.example.billingApp.service;

import com.example.billingApp.io.CategoryRequest;
import com.example.billingApp.io.CategoryResponse;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest request);
}
