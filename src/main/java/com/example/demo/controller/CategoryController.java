package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            Category category = new Category();
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setDescription(categoryDTO.getDescription());
            category.setPicture(categoryDTO.getPicture());
            return ResponseEntity.ok(categoryService.createCategory(category));
        } catch (Exception ex) {
            throw new RuntimeException("Error creando la categoría", ex);
        }
    }

    @GetMapping("/categories/")
    public ResponseEntity<List<Category>> listCategories() {
        return ResponseEntity.ok(categoryService.listCategories());
    }
}