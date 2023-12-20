package com.spring.ecommerce.dtos;

import com.spring.ecommerce.dto.ProductDto;

import java.util.List;

public record CategoryDto(Long id, String name, String description, String image, List<ProductDto> products) {
}
