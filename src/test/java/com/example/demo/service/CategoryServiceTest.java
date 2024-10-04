package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private MultipartFile imageFile;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService.imageDirectory = "/images";
    }

    @Test
    void testCreateCategoryWithImage() throws IOException {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Test Category");
        categoryDTO.setDescription("Test Description");
        categoryDTO.setPicture(imageFile);

        when(imageFile.isEmpty()).thenReturn(false);
        when(imageFile.getOriginalFilename()).thenReturn("image.jpg");
        doReturn(new byte[0]).when(imageFile).getBytes();
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category createdCategory = categoryService.createCategory(categoryDTO);

        assertEquals("Test Category", createdCategory.getCategoryName());
        assertEquals("Test Description", createdCategory.getDescription());
        assertNotNull(createdCategory.getPicturePath());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testCreateCategoryWithoutImage() throws IOException {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Test Category");
        categoryDTO.setDescription("Test Description");

        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category createdCategory = categoryService.createCategory(categoryDTO);

        assertNull(createdCategory.getPicturePath());
        assertEquals("Test Category", createdCategory.getCategoryName());
        assertEquals("Test Description", createdCategory.getDescription());
    }

    @Test
    void testSaveImage() throws IOException {
        when(imageFile.getOriginalFilename()).thenReturn("image.jpg");
        doReturn(new byte[0]).when(imageFile).getBytes();

        String imagePath = categoryService.saveImage(imageFile);

        assertTrue(imagePath.contains("image.jpg"));
        verify(imageFile, times(1)).getBytes();
    }

    @Test
    void testSaveImageThrowsIOException() throws IOException {
        when(imageFile.getOriginalFilename()).thenReturn("image.jpg");
        doThrow(new IOException("Error")).when(imageFile).getBytes();

        IOException exception = assertThrows(IOException.class, () -> categoryService.saveImage(imageFile));
        assertEquals("Error saving the image: Error", exception.getMessage());
    }

    @Test
    void testListCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.listCategories();

        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategoryById() {
        Category category = new Category();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCategoryByIdReturnsNull() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Category result = categoryService.getCategoryById(1L);

        assertNull(result);
        verify(categoryRepository, times(1)).findById(1L);
    }
}