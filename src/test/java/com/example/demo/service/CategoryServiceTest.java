package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory_Success() {
        Category category = new Category();
        category.setCategoryName("SERVIDORES");

        when(categoryRepository.save(category)).thenReturn(category);

        Category createdCategory = categoryService.createCategory(category);

        assertEquals("SERVIDORES", createdCategory.getCategoryName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testListCategories_Success() {
        Category category1 = new Category();
        category1.setCategoryName("SERVIDORES");

        Category category2 = new Category();
        category2.setCategoryName("CLOUD");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Category> categories = categoryService.listCategories();

        assertEquals(2, categories.size());
        verify(categoryRepository, times(1)).findAll();
    }
}