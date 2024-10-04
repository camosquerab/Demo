package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Value("${image.upload.dir}")
    String imageDirectory;

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryDTO categoryDTO) throws IOException {
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());
        MultipartFile imageFile = categoryDTO.getPicture();

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            category.setPicturePath(imagePath);
        } else {
            category.setPicturePath(null);
        }

        return categoryRepository.save(category);
    }

    String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        String originalFilename = imageFile.getOriginalFilename();
        assert originalFilename != null;

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        List<String> validExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp");

        if (!validExtensions.contains(extension)) {
            throw new IllegalArgumentException("Tipo de archivo no soportado: " + extension);
        }

        String newFileName = String.format("%s_%s%s", System.currentTimeMillis(), UUID.randomUUID(), extension);
        Path imagePath = Paths.get(imageDirectory, newFileName);
        Files.createDirectories(imagePath.getParent());

        try {
            Files.write(imagePath, imageFile.getBytes());
        } catch (IOException e) {
            throw new IOException("Error al guardar la imagen: " + e.getMessage());
        }

        return imagePath.toString();
    }

    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryID) {
        return categoryRepository.findById(categoryID).orElse(null);
    }
}