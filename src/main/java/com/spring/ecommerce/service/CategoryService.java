package com.spring.ecommerce.service;

import com.spring.ecommerce.entity.Category;
import com.spring.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public boolean findById(Long categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
    public void create(Category category) {
        categoryRepository.save(category);
    }

    public void edit(Long categoryId, Category updateCategory) throws IOException {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category == null){
            throw new IOException("No category is founded");
        }
        category.setName(updateCategory.getName());
        category.setDescription(updateCategory.getDescription());
        category.setImageUrl(updateCategory.getImageUrl());
        categoryRepository.save(category);
    }

    public void delete() {

    }
}
