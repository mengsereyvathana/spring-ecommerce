package com.spring.ecommerce.service;

import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.entity.Category;
import com.spring.ecommerce.entity.Product;
import com.spring.ecommerce.mapper.CategoryMapper;
import com.spring.ecommerce.repository.CategoryRepository;
import com.spring.ecommerce.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::mapToCategoryDto).collect(Collectors.toList());
    }

    public boolean findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.mapToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.mapToCategoryDto(savedCategory);
    }

    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) throws IOException {
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);
        if(existingCategory == null){
            throw new IOException("No category is founded");
        }
        existingCategory.setName(categoryDto.getName());
        existingCategory.setDescription(categoryDto.getDescription());
        existingCategory.setImage(categoryDto.getImage());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.mapToCategoryDto(updatedCategory);
    }

    public String deleteCategory(Long categoryId) throws Exception {
        Optional<Category> optionalProduct  = categoryRepository.findById(categoryId);

        if (optionalProduct.isEmpty()) {
            throw new Exception("Category is not exist.");
        }

        categoryRepository.deleteById(categoryId);
        return "Category successfully deleted " + categoryId;
    }


//    private CategoryDto categoryToDto(Category category){
//        CategoryDto categoryDto = new CategoryDto();
//
//        categoryDto.setId(category.getId());
//        categoryDto.setName(category.getName());
//        categoryDto.setDescription(category.getDescription());
//        categoryDto.setImage(category.getImage());
//        categoryDto.setProducts(category.getProducts());
//
//        return categoryDto;
//    }
}
