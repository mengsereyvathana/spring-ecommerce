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
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category editCategory(Long categoryId, Category category) throws IOException {
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);
        if(existingCategory == null){
            throw new IOException("No category is founded");
        }
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setImage(category.getImage());
        return categoryRepository.save(category);
    }

    public void delete() {

    }
}
