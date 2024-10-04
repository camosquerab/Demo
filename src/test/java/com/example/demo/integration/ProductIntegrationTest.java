package com.example.demo.integration;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class ProductIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testCreateCategoriesAndProducts() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Category category1 = new Category();
        category1.setCategoryName("SERVIDORES");
        Category category2 = new Category();
        category2.setCategoryName("ICLOUD");
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 100000; i++) {
            Product product = new Product();
            product.setProductName("Product " + i);
            product.setCategory((i % 2 == 0) ? category1 : category2);
            products.add(product);
            productRepository.save(product);
        }
        productRepository.saveAll(products);
        entityManager.flush();
    }
}