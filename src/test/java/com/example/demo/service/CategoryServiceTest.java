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
    void testSaveImageWithInvalidExtension() throws IOException {
        MultipartFile imageFile = mock(MultipartFile.class);
        when(imageFile.getOriginalFilename()).thenReturn("test.txt");
        when(imageFile.isEmpty()).thenReturn(false);
        when(imageFile.getBytes()).thenReturn("Contenido de prueba".getBytes());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.saveImage(imageFile);
        });
        assertEquals("Tipo de archivo no soportado: .txt", exception.getMessage());
    }

    @Test
    void testCategoryServiceInitialization() {
        assertNotNull(categoryService);
        assertEquals("/images", categoryService.imageDirectory);
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