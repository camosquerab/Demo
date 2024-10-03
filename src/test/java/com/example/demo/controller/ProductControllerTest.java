package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct_Success() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("AWS EC2");
        productDTO.setCategoryID(1L);

        Product product = new Product();
        product.setProductName("AWS EC2");

        when(productService.createProduct(any(Product.class), eq(1L))).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(productDTO);

        assertEquals("AWS EC2", response.getBody().getProductName());
        verify(productService, times(1)).createProduct(any(Product.class), eq(1L));
    }

    @Test
    void testGetProductById_Success() {
        Product product = new Product();
        product.setProductName("AWS EC2");

        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertEquals("AWS EC2", response.getBody().getProductName());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }
}