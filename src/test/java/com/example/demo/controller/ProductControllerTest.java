package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {
        productDTO = new ProductDTO();
        productDTO.setProductName("Test Product");
        productDTO.setSupplierID(1L);
        productDTO.setCategoryID(1L);
        productDTO.setUnitPrice(100.0);

        product = new Product();
        product.setProductID(1L);
        product.setProductName("Test Product");
    }

    @Test
    public void testCreateProductSuccess() throws Exception {
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);

        mockMvc.perform(post("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productName\":\"Test Product\", \"supplierID\":1, \"categoryID\":1, \"unitPrice\":100.0}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }

    @Test
    public void testListProducts() throws Exception {
        List<Product> productList = Arrays.asList(product);
        when(productService.listProducts()).thenReturn(productList);

        mockMvc.perform(get("/product/products/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Test Product"));
    }

    @Test
    public void testGetProductByIdSuccess() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/product/productss/1/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/product/productss/1/"))
                .andExpect(status().isNotFound());
    }
}