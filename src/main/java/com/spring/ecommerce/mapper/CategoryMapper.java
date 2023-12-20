package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.entity.Category;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryMapper {
    private static final Map<Category, CategoryDto> mappedCategoryDto = new HashMap<>();
    private static final Map<CategoryDto, Category> mappedCategory = new HashMap<>();
    public static CategoryDto mapToCategoryDto(Category category){
        if (category == null) {
            return null;
        }

        if (mappedCategoryDto.containsKey(category)) {
            return mappedCategoryDto.get(category);
        }

        CategoryDto categoryDto = new CategoryDto();
        mappedCategoryDto.put(category, categoryDto);

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setImage(category.getImage());
        categoryDto.setProducts(category.getProducts() != null ? category.getProducts().stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList()) : null);

        return categoryDto;
    }
    public static Category mapToCategory(CategoryDto categoryDto){
        if (categoryDto == null) {
            return null;
        }

        if (mappedCategory.containsKey(categoryDto)) {
            return mappedCategory.get(categoryDto);
        }

        Category category = new Category();
        mappedCategory.put(categoryDto, category);

        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setImage(categoryDto.getImage());
        category.setProducts(categoryDto.getProducts() != null ? categoryDto.getProducts().stream()
                .map(ProductMapper::mapToProduct)
                .collect(Collectors.toList()) : null);
        return category;
    }
}
