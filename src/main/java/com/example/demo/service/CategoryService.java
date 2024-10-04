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
import java.util.List;

import static java.lang.String.format;

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
        String originalFilename = imageFile.getOriginalFilename();
        String newFileName = format("%s_%s", System.currentTimeMillis(),originalFilename);
        Path imagePath = Paths.get(imageDirectory, newFileName);
        Files.createDirectories(imagePath.getParent());

        try {
            Files.write(imagePath, imageFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
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