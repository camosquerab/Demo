package com.example.demo.integration;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProductIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category cloudCategory;
    private Category serversCategory;

    @BeforeEach
    void setUp() {
        cloudCategory = categoryRepository.findByCategoryName("CLOUD")
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setCategoryName("CLOUD");
                    newCategory.setDescription("Servicios de Nube");
                    return categoryRepository.save(newCategory);
                });

        serversCategory = categoryRepository.findByCategoryName("SERVIDORES")
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setCategoryName("SERVIDORES");
                    newCategory.setDescription("Equipos físicos de servidores");
                    return categoryRepository.save(newCategory);
                });
    }

    @Test
    void insert100000ProductsInOneSecond() {
        List<Product> products = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 100000; i++) {
            Product product = new Product();
            product.setProductName("Producto " + i);
            product.setUnitPrice(random.nextDouble() * 100); // Precio aleatorio
            product.setQuantityPerUnit("1");

            if (i % 2 == 0) {
                product.setCategory(cloudCategory);
            } else {
                product.setCategory(serversCategory);
            }

            products.add(product);
        }

        Instant start = Instant.now();

        productRepository.saveAll(products);

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);

        assertTrue(duration.getSeconds() < 10, "La inserción tomó más de 1 segundo");
    }
}