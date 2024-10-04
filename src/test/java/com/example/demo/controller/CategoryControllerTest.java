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
        categoryDTO.setCategoryName("SERVIDORES");
        categoryDTO.setDescription("Descripción de la categoría");
        categoryDTO.setPicture(new byte[]{});

        Category category = new Category();
        category.setCategoryName("SERVIDORES");

        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        ResponseEntity<Category> response = categoryController.createCategory(categoryDTO);

        assertEquals("SERVIDORES", response.getBody().getCategoryName());
        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    void testCreateCategory_Failure() {
        // Datos de entrada
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("SERVIDORES");
        categoryDTO.setDescription("Descripción de la categoría");

        // Simulación de excepción
        when(categoryService.createCategory(any(Category.class))).thenThrow(new RuntimeException("Error creando la categoría"));

        // Ejecución del método del controlador con verificación de excepción
        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryController.createCategory(categoryDTO);
        });

        assertEquals("Error creando la categoría", exception.getMessage());
    }

    @Test
    void testListCategories_Success() {
        Category category1 = new Category();
        category1.setCategoryName("SERVIDORES");

        Category category2 = new Category();
        category2.setCategoryName("CLOUD");

        when(categoryService.listCategories()).thenReturn(List.of(category1, category2));

        ResponseEntity<List<Category>> response = categoryController.listCategories();

        assertEquals(2, response.getBody().size());
        verify(categoryService, times(1)).listCategories();
    }
}