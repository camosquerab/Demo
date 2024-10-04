package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);
        ResponseEntity<Product> response = productController.createProduct(productDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testListProducts() {
        List<Product> products = new ArrayList<>();
        when(productService.listProducts()).thenReturn(products);
        ResponseEntity<List<Product>> response = productController.listProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    void testGetProductById1Found() {
        Long productId = 1L;
        Product product = new Product();
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        ResponseEntity<Product> response = productController.getProductById1(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testGetProductById1NotFound() {
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            productController.getProductById1(productId);
        });
        assertEquals("404 NOT_FOUND \"Producto no encontrado\"", exception.getMessage());
    }

    @Test
    void testGetProductByIdFound() {
        Long productId = 1L;
        Product product = new Product();
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        Model model = mock(Model.class);
        String viewName = productController.getProductById(productId, model);
        verify(model).addAttribute("product", product);
        assertEquals("productDetail", viewName);
    }

    @Test
    void testGetProductByIdNotFound() {
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());
        Model model = mock(Model.class);
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            productController.getProductById(productId, model);
        });
        assertEquals("404 NOT_FOUND \"Producto no encontrado\"", exception.getMessage());
    }
}