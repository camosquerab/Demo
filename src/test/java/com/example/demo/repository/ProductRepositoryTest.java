package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setProductName("Test Product");
        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getProductID());
        assertEquals("Test Product", savedProduct.getProductName());
    }

    @Test
    public void testFindById() {
        Product product = new Product();
        product.setProductName("Test Product");
        Product savedProduct = productRepository.save(product);

        Optional<Product> retrievedProduct = productRepository.findById(savedProduct.getProductID());

        assertTrue(retrievedProduct.isPresent());
        assertEquals("Test Product", retrievedProduct.get().getProductName());
    }

    @Test
    public void testFindAll() {
        Product product1 = new Product();
        product1.setProductName("Product 1");

        Product product2 = new Product();
        product2.setProductName("Product 2");

        productRepository.save(product1);
        productRepository.save(product2);

        Iterable<Product> products = productRepository.findAll();
        assertTrue(products.iterator().hasNext());
    }
}