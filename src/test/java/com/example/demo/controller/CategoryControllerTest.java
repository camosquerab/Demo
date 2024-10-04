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
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
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
    void testCreateCategory() throws IOException {
        CategoryDTO categoryDTO = new CategoryDTO();
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", new byte[0]);
        categoryDTO.setPicture(file);
        Category category = new Category();
        when(categoryService.createCategory(categoryDTO)).thenReturn(category);
        ResponseEntity<Category> response = categoryController.createCategory(categoryDTO);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(categoryService, times(1)).createCategory(any(CategoryDTO.class));
    }

    @Test
    void testCreateCategoryThrowsIOException() throws IOException {
        CategoryDTO categoryDTO = new CategoryDTO();
        when(categoryService.createCategory(categoryDTO)).thenThrow(new IOException("Error"));
        RuntimeException exception = assertThrows
                (RuntimeException.class, () -> categoryController.createCategory(categoryDTO));
        assertEquals("Error guardando la imagen", exception.getMessage());
    }

    @Test
    void testListCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryService.listCategories()).thenReturn(categories);
        ResponseEntity<List<Category>> response = categoryController.listCategories();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(categoryService, times(1)).listCategories();
    }
}