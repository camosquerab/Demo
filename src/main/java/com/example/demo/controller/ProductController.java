package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product product = new Product();
            product.setProductName(productDTO.getProductName());
            product.setSupplierID(productDTO.getSupplierID());
            product.setQuantityPerUnit(productDTO.getQuantityPerUnit());
            product.setUnitPrice(productDTO.getUnitPrice());
            product.setUnitsInStock(productDTO.getUnitsInStock());
            product.setUnitsOnOrder(productDTO.getUnitsOnOrder());
            product.setReorderLevel(productDTO.getReorderLevel());
            product.setDiscontinued(productDTO.getDiscontinued());
            return ResponseEntity.ok(productService.createProduct(product, productDTO.getCategoryID()));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creando el producto", ex);
        }
    }

    @GetMapping("/products/")
    public ResponseEntity<List<Product>> listProducts() {
        return ResponseEntity.ok(productService.listProducts());
    }

    @GetMapping("/products/{id}/")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }
}