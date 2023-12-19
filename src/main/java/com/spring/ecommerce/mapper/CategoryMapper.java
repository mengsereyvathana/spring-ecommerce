package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.entity.Category;

public class CategoryMapper {
    public static CategoryDto mapToCategoryDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setImage(category.getImage());
        categoryDto.setProducts(category.getProducts());
        return categoryDto;
    }
    public static Category mapToCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setImage(categoryDto.getImage());
        category.setProducts(categoryDto.getProducts());
        return category;
    }
}
