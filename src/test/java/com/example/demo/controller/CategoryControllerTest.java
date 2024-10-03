package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory_Success() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("CLOUD");

        Category category = new Category();
        category.setCategoryName("CLOUD");

        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        ResponseEntity<Category> response = categoryController.createCategory(categoryDTO);

        assertEquals("CLOUD", response.getBody().getCategoryName());
        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    void testListCategories_Success() {
        Category category1 = new Category();
        category1.setCategoryName("CLOUD");

        Category category2 = new Category();
        category2.setCategoryName("SERVIDORES");

        when(categoryService.listCategories()).thenReturn(Arrays.asList(category1, category2));

        ResponseEntity<List<Category>> response = categoryController.listCategories();

        assertEquals(2, response.getBody().size());
        verify(categoryService, times(1)).listCategories();
    }
}