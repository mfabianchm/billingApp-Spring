package com.example.billingApp.controller;

import com.example.billingApp.io.Category.CategoryRequest;
import com.example.billingApp.io.Category.CategoryResponse;
import com.example.billingApp.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("category") String categoryString, @RequestPart("file") MultipartFile file) {
        ObjectMapper mapper = new ObjectMapper();
        CategoryRequest request = null;

        try {
            //Parse JSON string to DTO
            request = mapper.readValue(categoryString, CategoryRequest.class);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception occurred while parsing JSON: "+e.getMessage());
        }

        try {
            return categoryService.addCategory(request, file);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error saving file: " + e.getMessage()
            );
        }

    }

    @GetMapping
    public List<CategoryResponse> fetchCategories() {
        return categoryService.getCategories();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
