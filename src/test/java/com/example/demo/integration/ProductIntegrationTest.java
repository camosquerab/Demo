package com.example.demo.integration;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class ProductIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testCreateCategoriesAndProducts() {
        long startTime = System.currentTimeMillis();

        // Crear categorías
        Category category1 = new Category();
        category1.setCategoryName("SERVIDORES");
        Category category2 = new Category();
        category1.setCategoryName("ICLOUD");

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        // Crear productos
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 100000; i++) {
            Product product = new Product();
            product.setProductName("Product " + i);
            product.setCategory((i % 2 == 0) ? category1 : category2);
            products.add(product);
        }

        productRepository.saveAll(products);
        entityManager.flush(); // Asegúrate de que todos los cambios se escriban en la base de datos

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Comprobar que la duración sea menor a 10 segundos
        Assertions.assertTrue(duration < 10000, "La prueba excedió el tiempo límite de 10 segundos");

        // Comprobar que las categorías y los productos se han guardado
        Assertions.assertEquals(2, categoryRepository.count());
        Assertions.assertEquals(100000, productRepository.count());
    }
}