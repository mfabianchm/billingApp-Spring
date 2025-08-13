package com.example.billingApp.controller;

import com.example.billingApp.io.CategoryRequest;
import com.example.billingApp.io.CategoryResponse;
import com.example.billingApp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestBody CategoryRequest request) {
        return categoryService.addCategory(request);
    }

    @GetMapping
    public List<CategoryResponse> fetchCategories() {
        return categoryService.getCategories();
    }
}
