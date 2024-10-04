package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testFindByCategoryName() {
        Category category = new Category();
        category.setCategoryName("Test Category");
        categoryRepository.save(category);

        Optional<Category> result = categoryRepository.findByCategoryName("Test Category");

        assertTrue(result.isPresent());
        assertEquals("Test Category", result.get().getCategoryName());
    }

    @Test
    void testFindByCategoryNameReturnsEmpty() {
        Optional<Category> result = categoryRepository.findByCategoryName("Nonexistent Category");

        assertFalse(result.isPresent());
    }
}