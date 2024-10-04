package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product createdProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(createdProduct);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creando el producto", ex);
        }
    }

    @GetMapping("/products/")
    @ResponseBody
    public ResponseEntity<List<Product>> listProducts() {
        return ResponseEntity.ok(productService.listProducts());
    }

    @GetMapping("/productss/{id}/")
    @ResponseBody
    public ResponseEntity<Product> getProductById1(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    @GetMapping("/products/{id}/")
    public String getProductById(@PathVariable Long id, Model model) {
        try {
            Optional<Product> optionalProduct = productService.getProductById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                model.addAttribute("product", product);
                return "productDetail";
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado", ex);
        }
    }
}