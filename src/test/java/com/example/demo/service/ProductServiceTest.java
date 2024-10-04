package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    private ProductDTO productDTO;
    private Product product;
    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        productDTO = new ProductDTO();
        productDTO.setProductName("Test Product");
        productDTO.setSupplierID(1L);
        productDTO.setCategoryID(1L);
        productDTO.setUnitPrice(100.0);

        product = new Product();
        product.setProductName("Test Product");

        category = new Category();
        category.setCategoryID(1L);
    }

    @Test
    public void testCreateProduct() {
        when(categoryService.getCategoryById(1L)).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(productDTO);

        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getProductName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testListProducts() {
        productService.listProducts();
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Optional<Product> retrievedProduct = productService.getProductById(1L);

        assertTrue(retrievedProduct.isPresent());
        assertEquals("Test Product", retrievedProduct.get().getProductName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Product> retrievedProduct = productService.getProductById(1L);

        assertFalse(retrievedProduct.isPresent());
    }
}