package com.spring.ecommerce.service;

import com.spring.ecommerce.entity.Category;
import com.spring.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public boolean findById(Long categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public void editCategory(Long categoryId, Category updateCategory) throws IOException {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category == null){
            throw new IOException("No category is founded");
        }
        category.setName(updateCategory.getName());
        category.setDescription(updateCategory.getDescription());
        category.setImageUrl(updateCategory.getImageUrl());
        categoryRepository.save(category);
    }
}
