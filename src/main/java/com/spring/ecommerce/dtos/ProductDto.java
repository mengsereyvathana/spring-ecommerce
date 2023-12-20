package com.spring.ecommerce.dtos;

import com.spring.ecommerce.dto.ProductDetailDto;

public record ProductDto(Long id, String name, double price, String description, int quantity, String image1, String image2, String image3, ProductDetailDto detail, CategoryDto category) {
}
