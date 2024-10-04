package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @PostMapping("/")
    public ResponseEntity<Category> createCategory(@ModelAttribute CategoryDTO categoryDTO) {

        try {
            return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
        } catch (IOException ex) {
            throw new RuntimeException("Error guardando la imagen", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Error creando la categoría", ex);
        }
    }

    @GetMapping("/categories/")
    public ResponseEntity<List<Category>> listCategories() {
        return ResponseEntity.ok(categoryService.listCategories());
    }

}