package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public Product createProduct(ProductDTO productDTO) {

        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setSupplierID(productDTO.getSupplierID());
        product.setQuantityPerUnit(productDTO.getQuantityPerUnit());
        product.setUnitPrice(productDTO.getUnitPrice());
        product.setUnitsInStock(productDTO.getUnitsInStock());
        product.setUnitsOnOrder(productDTO.getUnitsOnOrder());
        product.setReorderLevel(productDTO.getReorderLevel());
        product.setDiscontinued(productDTO.getDiscontinued());
        Category category = categoryService.getCategoryById(productDTO.getCategoryID());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}